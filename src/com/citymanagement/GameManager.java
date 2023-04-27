/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

package com.citymanagement;

import com.citymanagement.gameobjects.Human;
import com.citymanagement.gameobjects.PopulationFactory;
import com.citymanagement.gameobjects.World;
import com.customgraphicinterface.utilities.Vector2;

public final class GameManager{

	public final static int INITIAL_POPULATION = 5;
	World w;

	public GameManager() {
		w = new World();
		//w.addPopulation(PopulationFactory.build().createHumanPopulation(1, new Vector2(200, 200)));
		w.addPopulation(PopulationFactory.build().make(Human.class,1, new Vector2(200, 200)));
		//w.addPopulation(PopulationFactory.build().createRessourcePopulation(20, new Vector2(200, 200)));
	}
}
