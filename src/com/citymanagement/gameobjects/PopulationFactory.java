package com.citymanagement.gameobjects;

import com.citymanagement.gameobjects.HumanFactory.HumanType;
import com.customgraphicinterface.core.GameObject;
import com.customgraphicinterface.factory.IFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * This class is used to create a factory that make population of a given type, and fill it if desired.
 */
@SuppressWarnings("unchecked")
public final class PopulationFactory implements IFactory<IPopulation<? extends GameObject>>{

    //TODO create commentary
    //TODO delete other factory methods
    //TODO Ressources type should not be there.
    public enum RessourceType {
        FOOD,
    }

    /**
     * Create a new Population factory.
     * @return a new PopulationFactory.
     */
    public static PopulationFactory build(){
        return new PopulationFactory();
    }

    /**
     * Create a Population that hold ressources.
     * @param number the number of ressources that will already be filled inside the population.
     * @param options options concerning Ressource: 
     * @return
     */
    public IPopulation<? extends GameObject> createRessourcePopulation(int number, Object... options){
        IPopulation<? extends GameObject> p = Population.build(Ressource.class);

        //fillPopulation(p,Human.class,RessourceType.values(),number, options);

        return p;
    }

    public IPopulation<? extends GameObject> createHumanPopulation(int number, Object... options){
        IPopulation<? extends GameObject> p = Population.build(Human.class);

        try {
            fillPopulation(p,Human.class,HumanType.values(),number, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        return p;
    }

    private void fillPopulation(IPopulation<? extends GameObject> p, Class<? extends GameObject> c, Enum<? extends Object>[] values , int n, Object... options)
    throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{


        try {
            System.out.println("-----------------------------------------------------------------------");
            Class<IFactory<? extends GameObject>> factory = (Class<IFactory<? extends GameObject>>)(Class.forName(PopulationFactory.class.getPackageName()+"." + c.getSimpleName()+"Factory"));
            //IFactory<? extends GameObject> f = (IFactory<? extends GameObject>)(factory.getMethod("getInstance").invoke(null));
            IFactory<? extends GameObject> f = factory.getConstructor().newInstance();
            System.out.println("coucou");
            //if(factory == null);
            for(int i=0;i<n;i++){

                /*Method method = PopulationFactory.class.getMethod("get"+c.getSimpleName(), IPopulation.class, HumanType.class, Object[].class);
                
                GameObject g = (GameObject)method.invoke(null, p,t,options);*/

                GameObject g = f.make();
                ((IPopulation<? super GameObject>)p).getPopulationList().add(g);
            }
        } catch (ClassNotFoundException e) {
            throw new NoSuchFactoryException("Error: Can't access " + c.getSimpleName() + " factory!",e);
        }catch (NoSuchMethodException e) {
            throw new NoSuchFactoryException("Error: Can't access " + c.getSimpleName() + " factory methode 'getInstance()'!",e);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    
    @Override
    public IPopulation<? extends GameObject> make(Object... options) {

        Object[] o = checkOptions(options, new Class[]{Class.class, Integer.class, Object[].class});

        IPopulation<? extends GameObject> p = Population.build((Class<? extends GameObject>)o[0]);
        return p;
    }

}
