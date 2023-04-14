package com.citymanagement.gameobjects;

import java.awt.Color;

import com.customgraphicinterface.factory.IFactory;
import com.customgraphicinterface.utilities.Vector2;

public final class HumanFactory implements IFactory<Human>{

    public enum HumanType {
        DEFAULT_MALE, 
        DEFUALT_FEMALE,
    }

    public static final int OPTIONS_LENGHT = 4;
    private static HumanFactory hf;
    

    private HumanFactory(){

    }

    @SuppressWarnings("unchecked")
    @Override
    public Human make(Object... options) {
        Human g = null;

        Class<?>[] c = new Class[]{
            IPopulation.class, Vector2.class, HumanType.class, Color.class
        };

        Object[] o = IFactory.checkOptions(options, c);

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

    @Override
    public IFactory<Human> getInstance() {
        if(hf == null){
            synchronized(hf){
                if(hf == null){
                    hf = new HumanFactory();
                }
            }
        }
        
        return hf;
    }

}
