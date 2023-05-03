package com.citymanagement.gameobjects;
import com.customgraphicinterface.core.GameObject;
import com.customgraphicinterface.core.Transform;

/**
 * A class should implement this interface if it want to be able to host different population of {@code GameObject}, and make them interact between them.
 */
public interface IWorld {
    /**
     * Get the tranform of the world. Used to set everthing below it relative to it positionwise.
     * @return the current tranform.
     */
    public Transform getTransform();

    /**
     * Add a population to the world.
     * @param p The population to add.
     */
    public void addPopulation(IPopulation<? extends GameObject> p);

    /**
     * Get the population of a certain type.
     * @param type The type of {@code GameObject} in that population.
     * @return The population hosting objects of the type.
     */
    public IPopulation<? extends GameObject> getPopulation(Class<? extends GameObject> type);

    /**
     * Remove the population of a certain type.
     * @param type The type of {@code GameObject} in that population.
     * @return If the population was removed.
     */
    public boolean removePopulation(Class<? extends IPopulation<? extends GameObject>> type);
}
