package com.citymanagement.gameobjects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import com.customgraphicinterface.utilities.Vector2;

/**
 * Class inheriting Human, used to represent a male human able to have a distinct reproduction strategie, 
 * to send advances to a number of nearby female to reproduce.
 */
public class HumanMale extends Human {
    public final static float DEFAULT_ATTARCTIVNESS = 10f;
    public final static Color DEFAULT_COLOR = Color.blue;


    private float _attractivness = 10;

    /**
     * Get the current acttractivness. Male only.
     * @return The current acttractivness.
     */
    public float getAttractivness(){
        return _attractivness;
    }

    /**
     * Create a HumanMale in the population given (IPopulation), at the location given (Vector2), with the color given (Color), and with the given attractiveness (float).
     * The default values for: pos is {@code Vector2(0,0)}, color is {@code DEFAULT_COLOR}.
     * @param pop The population it will be in.
     * @param pos The starting position. Default (0,0).
     * @param color The mesh color. Default is {@code DEFAULT_COLOR}
     * @param attractivness Represent how likely it is for advances to be accepted. Default is {@code DEFAULT_ATTARCTIVNESS}
     */
    public HumanMale(IPopulation<Human> pop, Vector2 pos, Color color, float attractivness) {
        super(pop, SexType.MALE,pos, color);

        this._attractivness = attractivness;
    }

     /**
     * Create a HumanMale in the population given (IPopulation), at the location given (Vector2), with the color given (Color), and with the given attractiveness (float).
     * The default values for: pos is {@code Vector2(0,0)}, color is {@code DEFAULT_COLOR}.
     * @param pop The population it will be in.
     * @param pos The starting position. Default (0,0).
     * @param color The mesh color. Default is {@code DEFAULT_COLOR}
     */
    public HumanMale(IPopulation<Human> pop, Vector2 pos, Color color) {
        this(pop, pos, color, DEFAULT_ATTARCTIVNESS);
    }

     /**
     * Create a HumanMale in the population given (IPopulation), at the location given (Vector2), with the color given (Color), and with the given attractiveness (float).
     * The default values for: pos is {@code Vector2(0,0)}, color is {@code DEFAULT_COLOR}.
     * @param pop The population it will be in.
     * @param pos The starting position. Default (0,0).
     */
    public HumanMale(IPopulation<Human> pop, Vector2 pos) {
        this(pop, pos, DEFAULT_COLOR, DEFAULT_ATTARCTIVNESS);
    }

     /**
     * Create a HumanMale in the population given (IPopulation), at the location given (Vector2), with the color given (Color), and with the given attractiveness (float).
     * The default values for: pos is {@code Vector2(0,0)}, color is {@code DEFAULT_COLOR}.
     * @param pop The population it will be in.
     */
    public HumanMale(IPopulation<Human> pop) {
        this(pop, new Vector2(0,0), DEFAULT_COLOR, DEFAULT_ATTARCTIVNESS);
    }

    /**
     * Used to send avance to a list of HumanFemale. If the are male, they are ignored. If one is successfull, they become mates to each other.
     * @param lf The list of Human to send advances to.
     * @return
     */
    public Human sendAdvances(ArrayList<Human> lf){
        for(Human f : lf){
            if(((HumanFemale)f).receiveAdvance(this)){
                setCurrentMate(f);
                return f;
            }
        }
        return null;
    }
    
    /**
     * Used to reproduce. Reset the need {@code reproductiveUrge} to 0. Reset current mate to null.
     */
    @Override
    protected void reproduce() {
        getNeeds().getNeed("reproductiveUrge").setCurrentValue(0f);
        setCurrentMate(null);
    }

    /**
     * Used to find all the possible Human that have an opposite sex, sorted from the nearest. Then {@code sendAdvances()} to that list.
     */
    @Override
    protected void findMate() {
        ArrayList<Human> sortedHumanFemales = new ArrayList<>();
        addAllFemalesIntoList(sortedHumanFemales);

        sortListTowardNearest(sortedHumanFemales);

        Human f = sendAdvances(sortedHumanFemales);

        if(f != null){
            setGoal(()->reproduce(),100);
            setCurrentMate(f);
            setDestination(f);
        }
    }

    /**
     * Sort a List of Human from nearest to farest.
     * @param sortedHumanFemales The human list to sort
     */
    private void sortListTowardNearest(ArrayList<Human> listHuman) {

        Comparator<Human> c = new Comparator<Human>() {

            @Override
            public int compare(Human o1, Human o2) {
                if(Vector2.dist(getTransform().getPos(), o1.getTransform().getPos()) > Vector2.dist(getTransform().getPos(), o2.getTransform().getPos())){
                    return 1;
                }
                else{
                    return 0;
                }
            }
            
        };
        listHuman.sort(c);
    }

    /**
     * List all the HumanFemale in its population.
     * @param sortedHumanFemales The list to add HumanFemale to.
     */
    private void addAllFemalesIntoList(ArrayList<Human> listHumanFemales) {
        for(Human h : population.getPopulationList()){
			if(h == null) continue;
			if(h.getSex() != getSex()){
				listHumanFemales.add(h);
			}
		}
    }
}
