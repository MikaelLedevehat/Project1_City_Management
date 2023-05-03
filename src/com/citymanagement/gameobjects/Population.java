package com.citymanagement.gameobjects;

import java.util.ArrayList;

import com.customgraphicinterface.core.GameObject;

/**
 * Class used to create a population of a type of {@code GameObject}. The population hold a list with all the objects, and also the type of object it's holding in.
 */
public class Population<T extends GameObject> implements IPopulation<T>{

    private final ArrayList<T> _population;
	private final Class<? extends GameObject> _memberType;
	private IWorld _world;

	/**
	 * Public builder used to create a population. Since the contructor might be confusing, a builder was created to reduce error occurencies.
	 * @param <T> must extend {@code GameObject}.
	 * @param type the type of object that will compose the population.
	 * @return a newly created population of that type.
	 */
	public static <T extends GameObject> IPopulation<T> build(Class<T> type) {
		return new Population<T>(type);
	} 

	/**
	 * Get the current world this population is ratached to.
	 * @return the current world.
	 */
	public IWorld getWorld(){
		return _world;
	}

	/**
	 * Set the new world to replace the current one.
	 * @param the new world.
	 */
	public void setWorld(IWorld w){
		_world = w;
	}

	/**
	 * Get the list containing all the population members.
	 * @return the population list.
	 */
	@Override
	public ArrayList<T> getPopulationList(){
		return _population;
	}

	/**
	 * Create a new population with a specific type (must extend {@code GameObject}).
	 * @param memberType the type of member the population will hold.
	 */
    private Population(Class<? extends GameObject> memberType){
        _population = new ArrayList<>();
		_memberType = memberType;
    }

	/**
	 * Merge the given population and add all the members at the end of this population. The given population type must be the exact same type as this population type.
	 * @param p the given population that will be merged.
	 */
	@Override
	public void mergePopulation(IPopulation<T> p) {
		if(p.getClass() != this.getClass())
			throw new UnsupportedOperationException("Unimplemented method 'mergePopulation'");

		this._population.addAll(p.getPopulationList());
	}

	/**
	 * Get the type of member the population hold.
	 * @return the type of the population.
	 */
	@Override
	public Class<? extends GameObject> getPopulationType() {
		return _memberType;
	}
    
}
