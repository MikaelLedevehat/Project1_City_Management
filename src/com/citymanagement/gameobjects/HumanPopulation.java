package com.citymanagement.gameobjects;

import java.util.ArrayList;

import com.customgraphicinterface.core.GameObject;

public class HumanPopulation implements IPopulation{

    private ArrayList<Human> _population;


	public ArrayList<Human> getPopulationList(){
		return _population;
	}

    public HumanPopulation(){
        _population = new ArrayList<>();
    }

	@Override
	public void addToPopulation(GameObject g) {
		_population.add((Human)g);
	}

	@Override
	public void removeFromPopulation(GameObject g) {
		_population.remove(g);
	}

	@Override
	public void removeFromPopulationAt(int index) {
		// TODO Auto-generated method stub
		_population.remove(index);
	}

	@Override
	public GameObject getMember(int index) {
		// TODO Auto-generated method stub
		return _population.get(index);
	}

	@Override
	public GameObject getMember(GameObject g) {
		// TODO Auto-generated method stub
		return _population.get(_population.indexOf(g));
	}

	@Override
	public void mergePopulation(IPopulation p) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'mergePopulation'");
	}
    
}
