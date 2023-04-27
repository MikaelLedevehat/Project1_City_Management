/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

package com.citymanagement.gameobjects;
import com.customgraphicinterface.core.GameObject;
import com.customgraphicinterface.core.Transform;

public interface IWorld {
    
    public Transform getTransform();
    public boolean addPopulation(IPopulation<? extends GameObject> p);
    public IPopulation<? extends GameObject> getPopulation(Class<? extends GameObject> type);
    public boolean removePopulation(Class<? extends IPopulation<? extends GameObject>> type);
}
