package com.citymanagement.gameobjects;

import java.awt.Color;
import java.util.Comparator;

import com.citymanagement.gameobjects.Needs.Need;
import com.customgraphicinterface.core.GameObject;
import com.customgraphicinterface.geometry.Rectangle;
import com.customgraphicinterface.geometry.Text;
import com.customgraphicinterface.utilities.Vector2;


public abstract class Human extends GameObject {

	/**
	 * Contain the action to execute when the destination is reached.
	 * exemple: the object (Human) execute "drink" when reaching the destination "water source".
	 */
	protected interface GoalExecute{
		public void execute();
	}


	/**
	 * Used to represent the destination of this object. Either a target is given, in that case the target position will be used,
	 * or a direct position is given, in that case the target is null;
	 * 
	 * @param pos The position in space
	 * @param target the target position
	 */
	public class Destination {
		public Vector2 pos;
		public GameObject target;
	}

	/**
	 * Used to compare two Human between them by IDs
	 */
	public static class HumanComparator implements Comparator<Human>{

		@Override
		public int compare(Human arg0, Human arg1) {
			// TODO Auto-generated method stub
			if(arg0.getId() == arg1.getId()) return 0;
			else if(arg0.getId() < arg1.getId()) return 1;
			else return -1;
		}
	}

	/**
	 * Used to represent the different sex types possible an Human can have.
	 */
	public enum SexType {
		MALE,
		FEMALE,
	}

	/**
	 * Used to represent the different state of interaction an Human can be in
	 */
	private enum InteractionState{
		NONE,
		STARTED,
		DONE;
	}

	/**
	 * Used to represent the different status an Human can be in
	 */
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
	private Human _currentMate;
	private int _interactionDuration = 0;
	private StatusState _status = StatusState.ALIVE;

	/**
	 * Get the current name of this Human
	 * @return the current name 
	 */
	public String getName(){
		return _name;
	}

	/**
	 * Set the destination with a position. Target will be null.
	 * @param dest The vector2 location to move toward.
	 */
	public void setDestination(Vector2 dest) {
		_dest.pos = dest;
		_dest.target = null;
	}
	
	/**
	 * Set the destination with a target. The position with be the position of the target.
	 * If the target move, it will update it's location automatically.
	 * @param t The target to move toward.
	 */
	public void setDestination(GameObject t) {
		if(t != null) _dest.pos = t.getTransform().getPos();
		else _dest.pos = null;
		_dest.target = t;
	}

	/**
	 * Get the current destination of this Human
	 * @return current destination
	 */
	protected Destination getDestination(){
		return _dest;
	}

	/**
	 * Set the current goal to execute when reaching the destination
	 * @param fc The action to execute
	 * @param duration The duration of the interaction
	 */
	public void setGoal(GoalExecute fc, int duration){
		_currentGoal = fc;
		_interactionDuration = duration > 0? duration:0;
	}

	/**
	 * Get the current partner of this Human
	 * @return the current partner
	 */
	protected Human getCurrentPartner(){
		return _currentMate;
	}

	/**
	 * Set the current partner. Can only be the opposite sex.
	 * @param h Human to mate with
	 */
	protected void setCurrentMate(Human h){
		if(h != null && h.getSex() == getSex()) return;
		_currentMate = h;
	}

	/**
	 * Get all the needs of this Human
	 * @return an object conaining all the needs
	 */
	protected Needs getNeeds(){
		return _needs;
	}

	/**
	 * Get the sexe of this Human
	 * @return the current sexetype
	 */
	public SexType getSex(){
		return _sex;
	}

	/**
	 * Set the sextype of this Human
	 * @param t Sextype tobe defined with
	 */
	private void setSex(SexType t){
		_sex = t;
	}

	/**
	 * Create a Human that have needs (hunger, thrist, reproductiveUrge), that can move toward a destination or a target.
	 * Once the target reached, it execute the action defined by it's current goal.
	 * @param pop The population this Human is in. Can't be null.
	 * @param sex The sexe of this Human
	 * @param pos The starting pos of this Human. Default (0,0)
	 * @param color The color of this Human. Default color depend on sexe.
	 * @throws NullPointerException() if population or sex is null!
	 */
	public Human(IPopulation<Human> pop, SexType sex, Vector2 pos, Color color) {
		if(pop == null) 
			throw new NullPointerException("Human population can't be null!");
		this.population = pop;
		
		_dest = new Destination();
		_needs = new Needs();
		_name = generateName();

		setSex(sex);

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
	
	/**
	 * Update method: called x number of time per seconds.
	 */
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

	/**
	 * Used to generate a String name from sratch. The first letter will be in upperCase while the rest will be in lower.
	 * The name lenght will be between 5 and 10 included.
	 * @return The generated name
	 */
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

	/**
	 * Used to update all the basic needs of this Human (thrist, hunger, reproductiveUrge)
	 */
	//TODO complete!
	private void updateNeedsUI() {
		updateNeedUI("thirst", 2);
		updateNeedUI("reproductiveUrge", 3);
		updateNeedUI("hunger", 4);
	}
	
	/**
	 * Update the UI of the need in argument, if exist
	 * @param name The string name of the need
	 * @param meshIndex the index of the mesh(rectangle) in the mesh list binded to this human
	 */
	//TODO replace sysout with throw
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

	/**
	 * Update the destination if the destination is a target. Do nothing if it's not.
	 */
	protected void updateDestination() {
		if(_dest.target == null) return;
		setDestination( _dest.target);
	}

	/**
	 * Select a new destination based on the needs priority (at least above 50).
	 * The {@code executeActionToFulfillNeed()} of the needs will be call in order of priority, until a destination is set.
	 * If no destination is set, then the Human will have the goal {@code wander()}
	 */
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

	/**
	 * Used to exectute the current goal, then reset the {@code InteractionState}, the goal, and the destination
	 */
	protected void executeGoal(){
		if(_currentGoal != null){
			_currentGoal.execute();
		}
		_interaction = InteractionState.NONE;
		_currentGoal = null;
		setDestination((GameObject)null);
	}

	/**
	 * Goal to satisfy the need {@code thirst} and set it to 0.
	 * @return
	 */
	protected void drink(){
		_needs.getNeed("thrist").setCurrentValue(0);
	}

	/**
	 * Goal to satisfy the need {@code hunger} and set it to 0.
	 * @return
	 */
	protected void eat(){
		_needs.getNeed("hunger").setCurrentValue(0);
	}

	/**
	 * Abstract: Used to obtain a new mate.
	 */
	protected abstract void findMate();

	/**
	 * Goal to satisfy the need {@code reproductiveUrge}.
	 */
	protected abstract void reproduce();

	/**
	 * Used to simule interaction when executing a goal, making the Human wait until the end of the duration to get a new destination.
	 * The color while interacting is changed.
	 */
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

	/**
	 * Used to end the interaction.
	 */
	protected void endInteraction(){
		_interaction = InteractionState.DONE;
	}

	/**
	 * Default goal. Do nothing, and immediately set a new destination. No interaction.
	 */
	protected void wander(){
		
	}

	/**
	 * Used to set the destination to the nearest water ressource.
	 * The ressource population must be in the same world as this Human population.
	 */
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
	
	/**
	 * Used to set the destination to the nearest food ressource.
	 * The ressource population must be in the same world as this Human population.
	 */
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

	/**
	 * Select a random Vector2 location inside a given rectangle
	 * @param w width of the rectangle
	 * @param h height of the rectangle
	 * @param x coordinate of the rectangle
	 * @param y coordinate of the rectangle
	 * @return the Vector2 location randomly chosen
	 */
	protected Vector2 selectRadomPlaceOnWorld(int w, int h, int x, int y) {
		return new Vector2((float)Math.random()*w + x,
				(float)Math.random()*h + y);
	}

	/**
	 * Selet a random Vector2 location around this Human between a max radius.
	 * @param maxRadius the radius around this Human
	 * @return the Vector2 location randomly chosen
	 */
	protected Vector2 selectRadomPlaceAround(float maxRadius) {
		float angle = (float)(Math.random()*Math.PI*2);
		float radius = (float)(Math.random()*maxRadius);
		return getTransform().getPos().plus( new Vector2((float)(Math.cos(angle) * radius), (float)(Math.sin(angle) * radius)));
	}

	/**
	 * Check if the destination is reached. 
	 * @return true if it is, false else
	 */
	protected boolean arrived() {
		// TODO Auto-generated method stub
		if(_dest.pos == null) return false;
		if(Math.abs(_dest.pos.x - getTransform().getPos().x) < 1f+SPEED && Math.abs(_dest.pos.y -getTransform().getPos().y) < 1f+SPEED) return true;
		else return false;
	}
	
	/**
	 * Move this Human toward the destination. The movement is proportional to it's speed and the framerate.
	 */
	protected void moveTowardDestination() {
		if(_dest == null ||_dest.pos == null) return;
		Vector2 distNormalized = Vector2.normalize(_dest.pos.minus(getTransform().getPos()));
		getTransform().setRot((float)Math.atan2(distNormalized.y, distNormalized.x));
		getTransform().setPos(getTransform().getPos().plus(distNormalized.multiply(SPEED)));
	}

	/**
	 * Make this Human die, removing it from the world and destroying it.
	 */
	protected void die(){
		_status = StatusState.DEAD;
		population.getPopulationList().remove(this);
		destroy();
	}
}