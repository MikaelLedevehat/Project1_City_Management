package com.citymanagement.gameobjects;

import java.util.HashMap;

import com.customgraphicinterface.core.GameObject;
import com.customgraphicinterface.core.Transform;
import com.customgraphicinterface.utilities.Vector2;

public class World implements IWorld {

    private final Transform _transform;
    private HashMap<Class<? extends GameObject>,IPopulation<? extends GameObject>> _populationList;

    public World(){
        _transform = new Transform(new Vector2(), 0, new Vector2(1,1));
        _populationList = new HashMap<Class<? extends GameObject>,IPopulation<? extends GameObject>>();
    }

    @Override
    public Transform getTransform() {
        return _transform;
    }

    @Override
    public boolean addPopulation(IPopulation<? extends GameObject> p) {

        IPopulation<? extends GameObject> oldP = _populationList.get(p.getPopulationType());
        if(oldP == null) _populationList.put((Class<? extends GameObject>)p.getClass(), p);
        else _populationList.merge((Class<? extends GameObject>) p.getClass(), p, (v1,v2)-> {((IPopulation<GameObject>)v1).mergePopulation((IPopulation<GameObject>) v2); return v1;} );

        return true;
    }

    @Override
    public IPopulation<? extends GameObject> getPopulation(Class<? extends IPopulation<? extends GameObject>> type) {
        return _populationList.get(type);
    }

    @Override
    public boolean removePopulation(Class<? extends IPopulation<? extends GameObject>> type) {
        IPopulation<? extends GameObject> removedP =  _populationList.remove(type);
        if( removedP == null) return false;
        else return true;
    }
}
