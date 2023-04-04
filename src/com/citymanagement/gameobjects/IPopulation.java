package com.citymanagement.gameobjects;

import java.util.ArrayList;

import com.customgraphicinterface.core.GameObject;

public interface IPopulation<T extends GameObject> {
    public ArrayList<T> getPopulationList();
    public Class<? extends GameObject> getPopulationType();
    public void addToPopulation(T g);
    public void removeFromPopulation(T g);
    public void removeFromPopulationAt(int index);
    public T getMember(int index);
    public T getMember(T g);
    public void mergePopulation(IPopulation<T> v2);
}
