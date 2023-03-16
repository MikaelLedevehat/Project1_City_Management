package com.citymanagement.gameobjects;

import java.awt.Color;

import com.citymanagement.gameobjects.PopulationFactory.HumanType;
import com.customgraphicinterface.utilities.Vector2;

public class HumanFemale extends Human {

    public final static Color DEFAULT_COLOR = Color.pink;
    public final static int DEFAULT_GESTATION_TIME = 200;
    public final static Color DEFAULT_GESTATION_COLOR = Color.red;

    private Human _babyFather;

    public HumanFemale(HumanPopulation pop, Vector2 pos, Color color) {
        super(pop, SexType.FEMALE, pos, color);
        //TODO Auto-generated constructor stub
    }

    private void birth(){
        int r = (int)Math.round(Math.random() * 2);
		population.addToPopulation(PopulationFactory.getHuman(population, HumanType.values()[r], this.getTransform().getPos()));
        this.getMesh().setFillColor(DEFAULT_COLOR);
    }

    private void gestate(){

    }

    public boolean receiveAdvance(HumanMale h){
        boolean r = Math.sqrt(h.getAttractivness() / 100f) > Math.random() + 0.5f;
        if(r){
            setCurrentPartner(h);
        }
        return r;
    }

    @Override
    protected boolean reproduce() {
        getNeeds().setReproductiveUrge(0);
        _babyFather = getCurrentPartner();
        gestate();
        return true;
    }

    @Override
    protected Vector2 findMate() {
        return null;
    }
    
}
