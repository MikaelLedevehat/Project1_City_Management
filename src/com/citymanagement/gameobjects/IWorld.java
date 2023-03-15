package com.citymanagement.gameobjects;
import com.customgraphicinterface.core.Transform;

public interface IWorld {
    
    public Transform getTransform();
    public boolean addPopulation(IPopulation p);
    public IPopulation getPopulation(Class<? extends IPopulation> type);
    public boolean removePopulation(Class<? extends IPopulation> type);
}
