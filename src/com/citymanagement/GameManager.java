package com.citymanagement;

import com.citymanagement.gameobjects.IPopulation;
import com.citymanagement.gameobjects.PopulationFactory;
import com.citymanagement.gameobjects.World;
import com.customgraphicinterface.utilities.Vector2;

public final class GameManager{

	public final static int INITIAL_POPULATION = 5;
	
	private IPopulation p;


	public GameManager() {

		World w = new World();
		
		createNewHumanPopulation();
	}


	private void createNewHumanPopulation() {
		PopulationFactory.createHumanPopulation(10, new Vector2(10, 20));
	}
	
}
