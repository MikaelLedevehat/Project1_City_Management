package com.citymanagement.gameobjects;

import com.citymanagement.gameobjects.HumanFactory.HumanType;
import com.customgraphicinterface.core.GameObject;
import com.customgraphicinterface.factory.IFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PopulationFactory implements IFactory<IPopulation<? extends GameObject>>{

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

        Class<IFactory<?>> factory = (Class<IFactory<?>>)(Class.forName(c.getSimpleName() + "Factory"));
        //if(factory == null);
        for(int i=0;i<n;i++){

            /*Method method = PopulationFactory.class.getMethod("get"+c.getSimpleName(), IPopulation.class, HumanType.class, Object[].class);
            
            GameObject g = (GameObject)method.invoke(null, p,t,options);*/

            GameObject g = factory
            ((IPopulation<? super GameObject>)p).addToPopulation(g);
        }
    }

    @Override
    public IPopulation<? extends GameObject> make(Object... options) {
        IPopulation<? extends GameObject> p = Population.builder(GameObject.class);
        return p;
    }

}
