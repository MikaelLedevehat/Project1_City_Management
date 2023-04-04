package com.citymanagement.gameobjects;
import com.customgraphicinterface.core.GameObject;
import com.customgraphicinterface.core.Transform;

public interface IWorld {
    
    public Transform getTransform();
    public boolean addPopulation(IPopulation<? extends GameObject> p);
    public IPopulation<? extends GameObject> getPopulation(Class<? extends IPopulation<? extends GameObject>> type);
    public boolean removePopulation(Class<? extends IPopulation<? extends GameObject>> type);
}
