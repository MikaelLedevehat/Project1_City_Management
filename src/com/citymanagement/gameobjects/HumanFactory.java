package com.citymanagement.gameobjects;

import java.awt.Color;

import com.customgraphicinterface.factory.IFactory;
import com.customgraphicinterface.utilities.Vector2;

/**
 * A class to produce human without having to give all the informations.
 */
//TODO make human population not mandatory
public final class HumanFactory implements IFactory<Human>{

    /**
     * Used to represent all the human type that can be created.
     */
    public enum HumanType {
        DEFAULT_MALE, 
        DEFUALT_FEMALE,
    }

    /**
     * Static builder to build a new factory
     * @return a new HumanFactory
     */
    public static HumanFactory build(){
        return new HumanFactory();
    }

    /**
     * Default mthode in a IFactory implementation: used to make a new Human. The option are not mandatory, but they must be in order:
     * The population the human is in (IPopulation), the position the human start at (Vector2), the type of Human (HumanType), the color of the Human (Color).
     */
    //TODO check if population is null, what to do
    @SuppressWarnings("unchecked")
    @Override
    public Human make(Object... options) {
        Human g = null;

        Object[] o = checkOptions(options, new Class[]{IPopulation.class, Vector2.class, HumanType.class, Color.class});

        if(o[2] == null)
            o[2] = HumanType.values()[(int)(Math.random()*HumanType.values().length)];

        switch ((HumanType)o[2]){
            case DEFAULT_MALE: g = new HumanMale((IPopulation<Human>)o[0], (Vector2)o[1]==null?new Vector2():(Vector2)o[1], (Color)o[3]==null?HumanMale.DEFAULT_COLOR:(Color)o[3], HumanMale.DEFAULT_ATTARCTIVNESS);
            break;

            case DEFUALT_FEMALE: g = new HumanFemale((IPopulation<Human>)o[0], (Vector2)o[1]==null?new Vector2():(Vector2)o[1], (Color)o[3]==null?HumanMale.DEFAULT_COLOR:(Color)o[3]);
            break;
        }

        return g;
    }

    /*public static IFactory<Human> getInstance() {
        if(_hf == null){
            synchronized(_hf){
                if(_hf == null){
                    _hf = new HumanFactory();
                }
            }
        }
        
        return _hf;
    }*/

}
