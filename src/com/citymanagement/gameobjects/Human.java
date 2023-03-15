package com.citymanagement.gameobjects;

import java.awt.Color;
import java.util.Comparator;

import com.customgraphicinterface.core.GameObject;
import com.customgraphicinterface.geometry.Rectangle;
import com.customgraphicinterface.utilities.Vector2;

public abstract class Human extends GameObject {

	private class Needs{

		public static final float NEED_MAX_CAP = 200;
		public static final float HUNGER_GROW_RATE = 0.05f;
		public static final float THIRST_GROW_RATE = 0.1f;
		public static final float REPRODUCTIVE_URGE_GROW_RATE = 0.05f;

		private float _thirst;
		private float _hunger;
		private float _reproductiveUrge;

		public Needs(int t, int h, int r){
			setHunger(h);
			setReproductiveUrge(r);
			setThirst(t);
		}

		public float getHunger(){
			return _hunger;
		}
		public float getThirst(){
			return _thirst;
		}
		public float getReproductiveUrge(){
			return _reproductiveUrge;
		}

		public void setHunger(float h) {
			if(h < 0) this._hunger = 0;
			else if(Needs.NEED_MAX_CAP < h) this._hunger = Needs.NEED_MAX_CAP;
			else this._hunger = h;
		}
		public void setThirst(float t) {
			if(t < 0) this._thirst = 0;
			else if(Needs.NEED_MAX_CAP < t) this._thirst = Needs.NEED_MAX_CAP;
			else this._thirst = t;
		}
		public void setReproductiveUrge(float r) {
			if(r < 0) this._reproductiveUrge = 0;
			else if(Needs.NEED_MAX_CAP < r) this._reproductiveUrge = Needs.NEED_MAX_CAP;
			else this._reproductiveUrge = r;
		}

		public void updateAllNeeds(){
			setHunger(getHunger() + Needs.HUNGER_GROW_RATE);
			setThirst(getThirst() + Needs.THIRST_GROW_RATE);
			setReproductiveUrge(getReproductiveUrge() + Needs.REPRODUCTIVE_URGE_GROW_RATE);
		}
	}

	public class Destination {
		public Vector2 pos;
		public boolean fixe;
		public GameObject target;
	}

	public static class HumanComparator implements Comparator<Human>{

		@Override
		public int compare(Human arg0, Human arg1) {
			// TODO Auto-generated method stub
			if(arg0.getId() == arg1.getId()) return 0;
			else if(arg0.getId() < arg1.getId()) return 1;
			else return -1;
		}
	}

	private interface GoalWorker{
		void execute();
	}

	public enum SexType {
		MALE,
		FEMALE,
	}

	private enum GoalType{
		WANDER,
		DRINK,
		EAT,
		REPRODUCE,
	}

	private enum InteractionState{
		NONE,
		STARTED,
		DONE;
	}

	public static final int HUMANHEIGHT = 10;
	public static final int HUMANWIDTH = 10;
	public static final int ZINDEX = 10;
	public static final Color INTERACTION_COLOR = Color.green;
	public static final Color BORDER_COLOR = Color.BLACK;
	public static final int BORDER_SIZE = 2;
	public static final float SPEED = 3f;
	public static final float INTERATION_SPEED = 0.8f;

	public final HumanPopulation population;

	private SexType _sex;
	private Destination _dest;
	private Needs _needs;
	private GoalType _currentGoal;
	private float _interactionTimer = 0;
	private InteractionState _interaction = InteractionState.NONE;
	private Color _colorSave;
	private Human _currentPartner;
	private GoalWorker[] _goalWorkers = new GoalWorker[]{
		new GoalWorker(){ public void execute() { wander(); } },
		new GoalWorker(){ public void execute() { drink(); } },
		new GoalWorker(){ public void execute() { eat(); } },
		new GoalWorker(){ public void execute() { reproduce(); } },
	};

	public void setDestination(Vector2 dest , boolean fixe, GameObject t) {
		_dest = new Destination();
		_dest.pos = dest;
		_dest.fixe = fixe;
		_dest.target = t;
	}
	
	public void setGoal(GoalType g){
		_currentGoal = g;
	}

	public SexType getSex(){
		return _sex;
	}

	public void setSex(SexType t){
		_sex = t;
	}

	public Human(HumanPopulation pop, SexType sex, Vector2 pos, Color color) {
		this.population = pop;
		_sex = sex;
		_needs = new Needs(0,0,0);
		setMesh(new Rectangle(HUMANHEIGHT,HUMANWIDTH, new Vector2(-HUMANWIDTH/2f,-HUMANHEIGHT/2f), 0f, BORDER_COLOR, BORDER_SIZE, color, false));
		getTransform().setPos(pos);
		setZIndex(ZINDEX);
	}
		
	@Override
	public void update() {
		_needs.updateAllNeeds();
		checkIfAlive();

		if( _dest != null) {
			if(arrived()){
				if(_interaction != InteractionState.DONE) interact();
				else {
					executeGoal();
					_interaction = InteractionState.NONE;
				}
			} 
			else {
				if(_dest.fixe == false) updateDestination();
				moveTowardDestination();
			}
		}else {
			selectDestination();
		}
	}

	protected void updateDestination() {
		setDestination(_dest.target.getTransform().getPos(), false, _currentPartner);
	}

	protected void checkIfAlive() {
		if(_needs.getHunger() >= Needs.NEED_MAX_CAP) die();
		if(_needs.getThirst() >= Needs.NEED_MAX_CAP) die();
	}

	protected void selectDestination() {
		//getTransform().rot += 0.1f;
		Vector2 p;
		if(_needs.getThirst() > 50 && _dest == null){
			p = findNearestWaterSource();
			if(p != null){
				setDestination(p, true, null);
				setGoal(GoalType.DRINK);
			}
		} 
		if(_needs.getHunger() > 50 && _dest == null){
			p = findNearestFood();
			if(p != null){
				setDestination(p, true, null);
				setGoal(GoalType.EAT);
			}
		} 
		if(_needs.getReproductiveUrge() > 100 && _dest == null){
			p = findSuitableNearestPartner();
			if(p != null){
				setDestination(p, false, _currentPartner);
				setGoal(GoalType.REPRODUCE);
			}
		}

		if(_dest == null){
			setDestination(selectRadomPlaceAround(200), true, null);
			setGoal(GoalType.WANDER);
		}
	}

	protected void executeGoal(){
		if(_currentGoal != null) _goalWorkers[_currentGoal.ordinal()].execute();
	}

	protected boolean drink(){
		_needs.setThirst(0);
		return true;
	}

	protected boolean eat(){
		_needs.setHunger(0);
		return true;
	}

	protected boolean reproduce(){
		_needs.setReproductiveUrge(0);
		if(_sex == SexType.FEMALE) population.addToPopulation(new HumanMale(population, null, BORDER_COLOR, BORDER_SIZE));
		return true;
	}

	protected void interact() {
		if(_interactionTimer > 100){
			getMesh().setFillColor(_colorSave);
			_interactionTimer = 0;
			_dest = null;
			_interaction = InteractionState.DONE;
		}else{
			if(getMesh().getFillColor() != Human.INTERACTION_COLOR) {
				_colorSave = getMesh().getFillColor();
				getMesh().setFillColor(Human.INTERACTION_COLOR);
			}
			_interactionTimer += Human.INTERATION_SPEED;
			_interaction = InteractionState.STARTED;
		}
	}

	protected boolean wander(){
		_dest = null;
		return true;
	}

	protected Vector2 findNearestWaterSource(){
		Vector2 nearestSource = null;
		/*for(Terrain t : world.getWaterSources()){
			if(nearestSource == null) nearestSource = t.getTransform().pos;
			else if(Vector2.dist(getTransform().pos, nearestSource) > Vector2.dist(getTransform().pos, t.getTransform().pos)) nearestSource = t.getTransform().pos;
		}*/
		return nearestSource;
	}
	
	protected Vector2 findNearestFood(){
		Vector2 nearestFood = null;
		
		/*for(Ressource r : world.getRessources()){
			if(r.get_ressourceType() == RessourceType.FOOD){
				if(nearestFood == null) nearestFood = r.getTransform().pos;
				else if(Vector2.dist(getTransform().pos, nearestFood) > Vector2.dist(getTransform().pos, r.getTransform().pos)) nearestFood = r.getTransform().pos;
			}
		}*/
		return nearestFood;
	}

	protected Vector2 findSuitableNearestPartner(){
		
		Vector2 nearestParner = null;
		for(Human h : population.getPopulationList()){
			if(h.getSex() != _sex){
				if(nearestParner == null){
					nearestParner = h.getTransform().getPos();
					_currentPartner = h;
				}
				else if(Vector2.dist(getTransform().getPos(), nearestParner) > Vector2.dist(getTransform().getPos(), h.getTransform().getPos())){
					nearestParner = h.getTransform().getPos();
					_currentPartner = h;
				} 
			}
		}
		
		return nearestParner;
	}

	protected Vector2 selectRadomPlaceOnWorld(int w, int h, int x, int y) {
		return new Vector2((float)Math.random()*w + x,
				(float)Math.random()*h + y);
	}

	protected Vector2 selectRadomPlaceAround(float maxRadius) {
		float angle = (float)(Math.random()*Math.PI);
		float radius = (float)(Math.random()*maxRadius);
		return getTransform().getPos().plus( new Vector2((float)(Math.cos(angle) * radius), (float)(Math.sin(angle) * radius)));
	}

	protected boolean arrived() {
		// TODO Auto-generated method stub
		if(_dest.pos == null) return false;
		if(Math.abs(_dest.pos.x - getTransform().getPos().x) < 1f+SPEED && Math.abs(_dest.pos.y -getTransform().getPos().y) < 1f+SPEED) return true;
		else return false;
	}
	
	protected void moveTowardDestination() {
		if(_dest == null ||_dest.pos == null) return;
		Vector2 distNormalized = Vector2.normalize(_dest.pos.minus(getTransform().getPos()));
		getTransform().setRot((float)Math.atan2(distNormalized.y, distNormalized.x));
		getTransform().setPos(getTransform().getPos().plus(distNormalized.multiply(SPEED)));
	}

	protected void die(){
		population.removeFromPopulation(this);
		destroy();
	}
}
