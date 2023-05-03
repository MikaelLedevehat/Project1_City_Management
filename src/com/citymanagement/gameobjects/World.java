package com.citymanagement.gameobjects;

import java.util.HashMap;

import com.customgraphicinterface.core.GameObject;
import com.customgraphicinterface.core.Transform;
import com.customgraphicinterface.utilities.Vector2;

/**
 * A class used to represent a world containing different population of GameObjects. If two populations are in the same world they may interact between them.
 */
public class World implements IWorld {

    private final Transform _transform;
    private HashMap<Class<? extends GameObject>,IPopulation<? extends GameObject>> _populationList;

    /**
     * Create a new world, with a empty list of populations.
     * The starting transform of a world is always (0,0) in position, 0 in rotation, and (1,1) in scale.
     */
    //TODO give world constroctor tranform ?
    public World(){
        _transform = new Transform(new Vector2(), 0, new Vector2(1,1));
        _populationList = new HashMap<Class<? extends GameObject>,IPopulation<? extends GameObject>>();
    }

    /**
     * Get the transform of this world.
     * @return the transform of this world.
     */
    @Override
    public Transform getTransform() {
        return _transform;
    }

    /**
     * Add a given population to the world's collection. If a population of that type already exist, merge the given one into the existing ont.
     * @param p the population to add into the world.
     */
    @Override
    public void addPopulation(IPopulation<? extends GameObject> p) {

        IPopulation<? extends GameObject> oldP = _populationList.get(p.getPopulationType());
        if(oldP == null) _populationList.put(p.getPopulationType(), p);
        else _populationList.merge((Class<? extends GameObject>) p.getClass(), p, (v1,v2)-> {((IPopulation<GameObject>)v1).mergePopulation((IPopulation<GameObject>) v2); return v1;} );
        p.setWorld(this);
    }

    /**
     * Get a specific population corresponding to the given type.
     * @return if the population exist, return the population. Else, return null.
     */
    @Override
    public IPopulation<? extends GameObject> getPopulation(Class<? extends GameObject> type) {
        return _populationList.get(type);
    }

    /**
     * Remove a specific population corresponding to the given type.
     * @return true if the population was sucessfully removed, false otherwise.
     */
    @Override
    public boolean removePopulation(Class<? extends IPopulation<? extends GameObject>> type) {
        IPopulation<? extends GameObject> removedP =  _populationList.remove(type);
        if( removedP == null) return false;
        else return true;
    }
}
