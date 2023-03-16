package com.citymanagement.gameobjects;

import java.awt.Color;
import com.customgraphicinterface.utilities.Vector2;

public class PopulationFactory {

    public enum HumanType {
        DEFAULT_MALE, 
        DEFUALT_FEMALE,
    }

    public static final int OPTIONS_LENGHT = 4;

    private PopulationFactory(){}

    public static IPopulation createHumanPopulation(int number, Object... options){
        IPopulation p = new HumanPopulation();

        fillPopulation(p,number, options);

        return p;
    }

    private static void fillPopulation(IPopulation p, int n, Object... options){
        for(int i=0;i<n;i++){
            HumanType t = HumanType.values()[(int)Math.round(Math.random())];
            p.addToPopulation(getHuman((HumanPopulation)p,t,options));
        }
    }

    public static Human getHuman(HumanPopulation p, HumanType t, Object... options){
        Human g = null;
        Vector2 pos = null;
        int width = 0;
        int height = 0;
        Color color = null;

        Object[] o = new Object[OPTIONS_LENGHT];

        if(options != null) System.arraycopy(options, 0, o, 0, options.length>OPTIONS_LENGHT?OPTIONS_LENGHT:options.length);

        try {
            pos = (Vector2)(o[0]!=null?o[0]:null);
            width = (int)(o[1]!=null?o[1]:0);
            height = (int)(o[2]!=null?o[2]:0);
            color = (Color)(o[3]!=null?o[3]:null);
        } catch (Exception e) {
            System.err.println("GameObject options are invalid: " + e);
            return null;
        }

        switch (t){
            case DEFAULT_MALE: g = new HumanMale(p, pos==null?new Vector2():pos, color==null?HumanMale.DEFAULT_COLOR:color, HumanMale.DEFAULT_ATTARCTIVNESS);
            break;

            case DEFUALT_FEMALE: g = new HumanFemale(p, pos==null?new Vector2():pos, color==null?HumanFemale.DEFAULT_COLOR:color);
            break;
        }

        return g;
    }
    
}
