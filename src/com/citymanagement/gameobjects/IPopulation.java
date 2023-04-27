/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

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
    public IWorld getWorld();
	public void setWorld(IWorld w);
}
