package com.citymanagement.gameobjects;

import java.awt.Color;
import com.customgraphicinterface.utilities.Vector2;

/**
 * Class inheriting Human, used to represent a female human able to have a distinct reproduction strategie, 
 * to bear and birth children.
 */
public class HumanFemale extends Human {

    public final static Color DEFAULT_COLOR = Color.pink;
    public final static int DEFAULT_GESTATION_TIME = 200;
    public final static Color DEFAULT_GESTATION_COLOR = Color.red;

    private boolean _isPregnant = false;

    /**
     * Create a HumanFemale in the population given (IPopulation), at the location given (Vector2), and with the color given (Color).
     * The default values for: pos is {@code Vector2(0,0)}, color is {@code DEFAULT_COLOR}
     * @param pop the population it will be in.
     * @param pos the position it start at.
     * @param color the color it's representing mesh will be.
     */
    public HumanFemale(IPopulation<Human> pop, Vector2 pos, Color color) {
        super(pop, SexType.FEMALE, pos, color);
    }

    /**
     * Create a single human at it's location. The {@code SexType} of the human is chosen randomly.
     */
    //TODO Make birth of multiple human possible
    private void birth(){
        int r = (int)(Math.random() * 2);
		population.getPopulationList().add(HumanFactory.build().make(population));
        this.getMesh(0).setFillColor(DEFAULT_COLOR);
        this.getNeeds().removeNeed("birth");
        _isPregnant = false;
    }

    /**
     * Enter gestation mode. Will automatically cancel incomming advances while in this mode.
     * A new birth need is added, representing the time left until birth.
     * The mesh color is changet
     */
    private void gestate(){
        this.getMesh(0).setFillColor(DEFAULT_GESTATION_COLOR);
        this.getNeeds().addNeed("birth", 0, DEFAULT_GESTATION_TIME, 1, null, ()->birth());
        _isPregnant = true;
    }

    /**
     * Receive the advances of another HumanMale. If successfull, then set the pretendant to be the currentMate, and adding a
     * new goal to reproduce with it.
     * @param h the pretendant {@code HumanMale}
     * @return if the advance was sucessfull
     */
    public boolean receiveAdvance(HumanMale h){
        if(getCurrentPartner() != null) return false;
        if(_isPregnant) return false;

        //boolean r = Math.sqrt(h.getAttractivness() / 100f) > Math.random() + 0.5f;
        boolean r = true;
        if(r){
            setGoal(()->reproduce(),100);
            setCurrentMate(h);
            setDestination(h);
        }
        return r;
    }

    /**
     * Used to reproduce when close to the currentMate.
     * Reset the {@code reproductiveUrge} to 0, and forgetting the currentMate right after.
     * Make the HumanFemale {@code gestate()}.
     */
    //TODO gestation should be chance based, not every time
    @Override
    protected void reproduce() {
        getNeeds().getNeed("reproductiveUrge").setCurrentValue(0);
        setCurrentMate(null);
        gestate();
    }

    /**
     * Used to find, or help find a mate. Does nothing for a {@code HumanFemale}
     */
    @Override
    protected void findMate() {
        //return null;
    }
    
}
