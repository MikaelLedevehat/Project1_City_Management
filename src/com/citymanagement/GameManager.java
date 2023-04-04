package com.citymanagement;

import com.citymanagement.gameobjects.PopulationFactory;
import com.citymanagement.gameobjects.World;
import com.customgraphicinterface.utilities.Vector2;

public final class GameManager{

	public final static int INITIAL_POPULATION = 5;
	World w;

	public GameManager() {
		w = new World();
		w.addPopulation(PopulationFactory.createHumanPopulation(2, new Vector2(500, 500)));
		//w.addPopulation(PopulationFactory.createRessourcePopulation(20, new Vector2(500, 500)));
	}
}
