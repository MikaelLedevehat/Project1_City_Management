package com.citymanagement.gameobjects;

import com.customgraphicinterface.core.GameObject;

public interface IPopulation {
    public void addToPopulation(GameObject g);
    public void removeFromPopulation(GameObject g);
    public void removeFromPopulationAt(int index);
    public GameObject getMember(int index);
    public GameObject getMember(GameObject g);
    public void mergePopulation(IPopulation p);
}
