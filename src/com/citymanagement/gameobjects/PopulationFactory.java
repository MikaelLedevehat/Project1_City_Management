package com.citymanagement.gameobjects;

import java.awt.Color;

import com.customgraphicinterface.core.GameObject;
import com.customgraphicinterface.utilities.Vector2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PopulationFactory {

    public enum HumanType {
        DEFAULT_MALE, 
        DEFUALT_FEMALE,
    }

    public enum RessourceType {
        FOOD,
    }

    


    public static final int OPTIONS_LENGHT = 4;

    private PopulationFactory(){}

    public static IPopulation<? extends GameObject> createRessourcePopulation(int number, Object... options){
        IPopulation<? extends GameObject> p = Population.builder(Ressource.class);

        //fillPopulation(p,Human.class,RessourceType.values(),number, options);

        return p;
    }

    public static IPopulation<? extends GameObject> createHumanPopulation(int number, Object... options){
        IPopulation<? extends GameObject> p = Population.builder(Human.class);

        try {
            fillPopulation(p,Human.class,HumanType.values(),number, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        return p;
    }

    private static void fillPopulation(IPopulation<? extends GameObject> p, Class<? extends GameObject> c, Enum<? extends Object>[] values , int n, Object... options)
    throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        for(int i=0;i<n;i++){
            HumanType t = HumanType.values()[(int)Math.round(Math.random())];
            //Human h = getHuman((IPopulation<Human>)p,t,options);

            Method method = PopulationFactory.class.getMethod("get"+c.getSimpleName(), IPopulation.class, HumanType.class, Object[].class);
            
            GameObject g = (GameObject)method.invoke(null, p,t,options);
            ((IPopulation<? super GameObject>)p).addToPopulation(g);
        }
    }

    @SuppressWarnings("unused")
    public static Human getHuman(IPopulation<Human> p, HumanType t, Object... options){
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
        } catch (RuntimeException e) {
            throw new NullPointerException("The options for creating this game object (Human) are invalid!");
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
