package com.citymanagement.gameobjects;

import java.util.HashMap;

import com.customgraphicinterface.core.Transform;
import com.customgraphicinterface.utilities.Vector2;

public class World implements IWorld {

    private final Transform _transform;
    private HashMap<Class<? extends IPopulation>,IPopulation> _populationList;

    public World(){
        _transform = new Transform(new Vector2(), 0, new Vector2(1,1));
        _populationList = new HashMap<Class<? extends IPopulation>,IPopulation>();
    }

    @Override
    public Transform getTransform() {
        return _transform;
    }

    @Override
    public boolean addPopulation(IPopulation p) {

        IPopulation oldP = _populationList.get(p.getClass());
        if(oldP == null) _populationList.put(p.getClass(), p);
        else _populationList.merge(p.getClass(), p, (v1,v2)-> {v1.mergePopulation(v2); return v1;} );

        return true;
    }

    @Override
    public IPopulation getPopulation(Class<? extends IPopulation> type) {
        return _populationList.get(type);
    }

    @Override
    public boolean removePopulation(Class<? extends IPopulation> type) {
        IPopulation removedP =  _populationList.remove(type);
        if( removedP == null) return false;
        else return true;
    }
}
