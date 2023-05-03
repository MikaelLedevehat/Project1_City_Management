package com.citymanagement.gameobjects;

import java.util.ArrayList;

import com.customgraphicinterface.core.GameObject;

/**
 * The IPopulation interface should be implemented by a class that desire to be a Population to contain a list of {@code GameObject}
 * and is ratached to a IWorld. The population contains only the same type of element, so a different population has to
 * be created and added to the world for a different type of {@code GameObject}.
 */
public interface IPopulation<T extends GameObject>{
    /**
     * Get the list with all the objects currently in the population.
     * @return The list with all the objects currently in the population.
     */
    public ArrayList<T> getPopulationList();

    /**
     * Get the exact type of {@code GameObject} inside the population.
     * @return
     */
    public Class<? extends GameObject> getPopulationType();

    /**
     * Merge two population toghether, if they have the same type.
     * @param v2 The population that will be merged.
     */
    public void mergePopulation(IPopulation<T> v2);

    /**
     * Get the current world the population belong to.
     * @return The current IWorld.
     */
    public IWorld getWorld();

    /**
     * Set the current world.
     * @param w The new world the population will belong to.
     */
	public void setWorld(IWorld w);
}
