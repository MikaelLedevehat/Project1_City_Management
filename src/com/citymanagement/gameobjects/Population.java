package com.citymanagement.gameobjects;

import java.util.ArrayList;

import com.customgraphicinterface.core.GameObject;

public class Population<T extends GameObject> implements IPopulation<T>{

    private final ArrayList<T> _population;
	private final Class<? extends GameObject> _memberType;
	private IWorld _world;

	public static <T extends GameObject> Population<T> builder(Class<T> type) {
		return new Population<T>(type);
	} 

	public IWorld getWorld(){
		return _world;
	}

	public void setWorld(IWorld w){
		_world = w;
	}

	@Override
	public ArrayList<T> getPopulationList(){
		return _population;
	}

    private Population(Class<? extends GameObject> memberType){
        _population = new ArrayList<>();
		_memberType = memberType;
    }

	@Override
	public void addToPopulation(T g) {
		_population.add(g);
	}

	@Override
	public void removeFromPopulation(T g) {
		_population.remove(g);
	}

	@Override
	public void removeFromPopulationAt(int index) {
		_population.remove(index);
	}

	@Override
	public T getMember(int index) {
		return _population.get(index);
	}

	@Override
	public T getMember(T g) {
		return _population.get(_population.indexOf(g));
	}

	@Override
	public void mergePopulation(IPopulation<T> p) {
		if(p.getClass() != this.getClass())
			throw new UnsupportedOperationException("Unimplemented method 'mergePopulation'");

		this._population.addAll(p.getPopulationList());
	}

	@Override
	public Class<? extends GameObject> getPopulationType() {
		return _memberType;
	}
    
}
