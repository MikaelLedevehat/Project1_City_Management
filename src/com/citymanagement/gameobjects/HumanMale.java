package com.citymanagement.gameobjects;

import java.awt.Color;

import com.customgraphicinterface.utilities.Vector2;

public class HumanMale extends Human {
    public final static float DEFAULT_ATTARCTIVNESS = 10f;
    public final static Color DEFAULT_COLOR = Color.blue;


    private float _attractivness = 10;

    public float getAttractivness(){
        return _attractivness;
    }

    public HumanMale(HumanPopulation pop, Vector2 pos, Color color, float attractivness) {
        super(pop, SexType.MALE,pos, color);

        this._attractivness = attractivness;
    }

    public boolean sendAdvances(HumanFemale f){
        return false;
    }
    
}
