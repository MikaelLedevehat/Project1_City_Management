package com.citymanagement.gameobjects;

import java.awt.Color;
import java.util.Comparator;

import com.citymanagement.gameobjects.Needs.Need;
import com.customgraphicinterface.core.GameObject;
import com.customgraphicinterface.geometry.Rectangle;
import com.customgraphicinterface.geometry.Text;
import com.customgraphicinterface.utilities.Vector2;

public abstract class Human extends GameObject {

	protected interface GoalExecute{
		public void execute();
	}

	public class Destination {
		public Vector2 pos;
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

	public enum SexType {
		MALE,
		FEMALE,
	}

	private enum InteractionState{
		NONE,
		STARTED,
		DONE;
	}

	private enum StatusState{
		ALIVE,
		DEAD,
	}

	public static final int HUMANHEIGHT = 10;
	public static final int HUMANWIDTH = 10;
	public static final int ZINDEX = 10;
	public static final Color INTERACTION_COLOR = Color.green;
	public static final Color BORDER_COLOR = Color.BLACK;
	public static final int BORDER_SIZE = 2;
	public static final float SPEED = 3f;
	public static final float INTERATION_SPEED = 1f;

	public final IPopulation<Human> population;

	private SexType _sex;
	private final Destination _dest;
	private Needs _needs;
	private GoalExecute _currentGoal;
	private float _interactionTimer = 0;
	private InteractionState _interaction = InteractionState.NONE;
	private Color _colorSave;
	private final String _name;
	private Human _currentPartner;
	private int _interactionDuration = 0;
	private StatusState _status = StatusState.ALIVE;


	public String getName(){
		return _name;
	}

	public void setDestination(Vector2 dest) {
		_dest.pos = dest;
		_dest.target = null;
	}
	
	public void setDestination(GameObject t) {
		if(t != null) _dest.pos = t.getTransform().getPos();
		else _dest.pos = null;
		_dest.target = t;
	}

	protected Destination getDestination(){
		return _dest;
	}

	public void setGoal(GoalExecute fc, int duration){
		_currentGoal = fc;
		_interactionDuration = duration > 0? duration:0;
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

	public Human(IPopulation<Human> pop, SexType sex, Vector2 pos, Color color) {
		this.population = pop;
		_sex = sex;
		_dest = new Destination();
		_needs = new Needs();
		_name = generateName();

		_needs.addNeed("hunger",0, 200, 0.2f, ()->findNearestFood(), ()->die());
		_needs.addNeed("thirst",0, 200, 0.2f, ()->findNearestWaterSource(), ()->die());
		_needs.addNeed("reproductiveUrge",100, 200, 0.2f, ()->findMate(),null);

		addMesh(new Rectangle(HUMANHEIGHT,HUMANWIDTH, new Vector2(-HUMANWIDTH/2f,-HUMANHEIGHT/2f), 0f, BORDER_COLOR, BORDER_SIZE, color, false, false));
		addMesh(new Text(_name,new Vector2(-15,-30), 15,0f,false, true));
		addMesh(new Rectangle(5, 50, new Vector2(-15,-50), 0f, Color.red, 0, Color.red, false, true));
		addMesh(new Rectangle(5, 50, new Vector2(-15,-60), 0f, Color.red, 0, Color.red, false, true));
		addMesh(new Rectangle(5, 50, new Vector2(-15,-70), 0f, Color.red, 0, Color.red, false, true));

		getTransform().setPos(pos==null?new Vector2():pos);
		setZIndex(ZINDEX);
	}
		
	@Override
	public void update() {
		_needs.updateAllNeeds();
		if(_status == StatusState.DEAD) return;
		updateNeedsUI();
		if( _dest.pos != null) {
			if(arrived()){
				if(_interaction != InteractionState.DONE && _interactionDuration > 0) interact();
				else {
					executeGoal();
					_interaction = InteractionState.NONE;
				}
			} 
			else {
				updateDestination();
				moveTowardDestination();
			}
		}else {
			selectDestination();
		}
	}

	public String generateName(){

		final String lexicon = "abcdefghijklmnopqrstuvwxyz";
		final java.util.Random rand = new java.util.Random();
		StringBuilder builder = new StringBuilder();

		while(builder.toString().length() == 0) {
			int length = rand.nextInt(5)+5;
			for(int i = 0; i < length; i++) {
				builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
			}
		}
		
		return builder.toString().substring(0,1).toUpperCase() + builder.toString().substring(1);
	}

	private void updateNeedsUI() {
		updateNeedUI("thirst", 2);
		updateNeedUI("reproductiveUrge", 3);
		updateNeedUI("hunger", 4);
	}

	private void updateNeedUI(String name, int meshIndex){
		try {
			Rectangle r = (Rectangle)this.getMesh(meshIndex);
			float coef = getNeeds().getNeed(name).getMaxCap()/ 50;
			r.setWidth((int)(getNeeds().getNeed(name).getCurrentValue()/coef));
			float colorCoef = getNeeds().getNeed(name).getCurrentValue()/getNeeds().getNeed(name).getMaxCap();
			r.setFillColor(new Color(colorCoef, 1-colorCoef, 0f));
			r.setBorderColor(r.getFillColor());
		} catch (Exception e) {
			System.out.println("Can't update human need " + name +" UI!");
		}
		
	}

	protected void updateDestination() {
		if(_dest.target == null) return;
		setDestination( _dest.target);
	}

	protected void selectDestination() {
		Need[] nArr = _needs.sortNeedsByPriority();

		for(Need n : nArr){
			if(n==null) continue;
			if(n.getCurrentValue() > 50f) {
				if(n.executeActionToFulfillNeed() == false) continue;
			}
			
			if(_dest.pos != null) break;
		}

		if(_dest.pos == null){
			setDestination(selectRadomPlaceAround(200));
			setGoal(()->wander(), 0);
			//_doInteract = false;
		}

	}

	protected void executeGoal(){
		if(_currentGoal != null){
			_currentGoal.execute();
		}
		_interaction = InteractionState.NONE;
		_currentGoal = null;
		setDestination((GameObject)null);
	}

	protected boolean drink(){
		_needs.getNeed("thrist").setCurrentValue(0);
		return true;
	}

	protected boolean eat(){
		_needs.getNeed("hunger").setCurrentValue(0);
		return true;
	}

	protected abstract void findMate();

	protected abstract boolean reproduce();

	protected void interact() {
		if(_interactionTimer > _interactionDuration){
			getMesh(0).setFillColor(_colorSave);
			_interactionTimer = 0;
			_interaction = InteractionState.DONE;
		}else{
			if(getMesh(0).getFillColor() != Human.INTERACTION_COLOR) {
				_colorSave = getMesh(0).getFillColor();
				getMesh(0).setFillColor(Human.INTERACTION_COLOR);
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

	protected void findNearestWaterSource(){
		Vector2 nearestSource = null;
		IWorld w =  this.population.getWorld();
		if(w == null) return;

		IPopulation<? extends GameObject> p = w.getPopulation(Ressource.class);
		//System.out.println(p);

		/*for(Terrain t : world.getWaterSources()){
			if(nearestSource == null) nearestSource = t.getTransform().pos;
			else if(Vector2.dist(getTransform().pos, nearestSource) > Vector2.dist(getTransform().pos, t.getTransform().pos)) nearestSource = t.getTransform().pos;
		}*/
		//return nearestSource;
	}
	
	protected void findNearestFood(){
		Vector2 nearestFood = null;
		
		/*for(Ressource r : world.getRessources()){
			if(r.get_ressourceType() == RessourceType.FOOD){
				if(nearestFood == null) nearestFood = r.getTransform().pos;
				else if(Vector2.dist(getTransform().pos, nearestFood) > Vector2.dist(getTransform().pos, r.getTransform().pos)) nearestFood = r.getTransform().pos;
			}
		}*/
		//return nearestFood;
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
		_status = StatusState.DEAD;
		population.removeFromPopulation(this);
		destroy();
	}
}