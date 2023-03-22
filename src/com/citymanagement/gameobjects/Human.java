package com.citymanagement.gameobjects;

import java.awt.Color;
import java.util.Comparator;
import java.util.HashMap;

import com.citymanagement.gameobjects.Needs;
import com.citymanagement.gameobjects.Needs.Need;
import com.customgraphicinterface.core.GameObject;
import com.customgraphicinterface.geometry.Rectangle;
import com.customgraphicinterface.utilities.Vector2;

public abstract class Human extends GameObject {

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
	private boolean _doInteract = true;
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

	protected Human getCurrentPartner(){
		return _currentPartner;
	}

	protected void setCurrentPartner(Human h){
		_currentPartner = h;
	}

	protected Needs getNeeds(){
		return _needs;
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
		
		_needs = new Needs();
		_needs.addNeed("hunger",0, 200, 0.2f, ()->findNearestFood());
		_needs.addNeed("thirst",0, 200, 0.2f, ()->findNearestWaterSource());
		_needs.addNeed("reproductiveUrge",0, 200, 0.2f, ()->findMate());

		setMesh(new Rectangle(HUMANHEIGHT,HUMANWIDTH, new Vector2(-HUMANWIDTH/2f,-HUMANHEIGHT/2f), 0f, BORDER_COLOR, BORDER_SIZE, color, false));
		getTransform().setPos(pos==null?new Vector2():pos);
		setZIndex(ZINDEX);
	}
		
	@Override
	public void update() {
		_needs.updateAllNeeds();
		checkIfAlive();

		if( _dest != null) {
			if(arrived()){
				if(_interaction != InteractionState.DONE && _doInteract) interact();
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
		if(_needs.getReproductiveUrge() > 50 && _dest == null){
			p = findMate();
			if(p != null){
				setDestination(p, true, null);
				setGoal(GoalType.EAT);
			}
		}

		/*float tot = this._needs._hunger/Needs.NEED_MAX_CAP + this._needs._thirst/Needs.NEED_MAX_CAP + this._needs._reproductiveUrge/Needs.NEED_MAX_CAP;
		float[] proba;
		if(tot < 100f){
			proba = new float[4];
			proba[0] =  100f - tot;
			proba[1] = proba[0] + this._needs._thirst/Needs.NEED_MAX_CAP;
			proba[2] = proba[1] + this._needs._hunger/Needs.NEED_MAX_CAP;
			proba[3] = proba[2] + this._needs._reproductiveUrge/Needs.NEED_MAX_CAP;
			
		}else{
			proba = new float[3];
			proba[0] = this._needs._thirst/(Needs.NEED_MAX_CAP*(tot/100f));
			proba[1] = proba[0] + this._needs._hunger/(Needs.NEED_MAX_CAP*(tot/100f));
			proba[2] = proba[1] + this._needs._reproductiveUrge/(Needs.NEED_MAX_CAP*(tot/100f));
		}

		float tirage = (float)Math.random();
		for(int i =0;i<proba.length;i++){
			if(proba[i] > tirage){
				setGoal(GoalType.values()[i]);
			}
		}*/

		if(_dest == null){
			setDestination(selectRadomPlaceAround(200), true, null);
			setGoal(GoalType.WANDER);
			_doInteract = false;
		}
	}

	protected void setMate(Human h){
		setCurrentPartner(h);
		setDestination(h.getTransform().getPos(), false, _currentPartner);
		setGoal(GoalType.REPRODUCE);
	}

	protected void executeGoal(){
		if(_currentGoal != null) _goalWorkers[_currentGoal.ordinal()].execute();
		_interaction = InteractionState.NONE;
		_doInteract = true;
		_dest = null;
	}

	protected boolean drink(){
		_needs.setThirst(0);
		return true;
	}

	protected boolean eat(){
		_needs.setHunger(0);
		return true;
	}

	protected abstract Vector2 findMate();

	protected abstract boolean reproduce();

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

	protected void endInteraction(){
		_interaction = InteractionState.DONE;
	}

	protected boolean wander(){
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

	protected Vector2 selectRadomPlaceOnWorld(int w, int h, int x, int y) {
		return new Vector2((float)Math.random()*w + x,
				(float)Math.random()*h + y);
	}

	protected Vector2 selectRadomPlaceAround(float maxRadius) {
		float angle = (float)(Math.random()*Math.PI*2);
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
