/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

package com.citymanagement.gameobjects;

import com.customgraphicinterface.core.GameObject;
import com.customgraphicinterface.geometry.Circle;
import com.customgraphicinterface.utilities.Vector2;
import java.awt.Color;

public abstract class Ressource extends GameObject{

    public Ressource( Vector2 pos){
        getTransform().setPos(pos);
    }

    @Override
    public void update() {
        
    }

    public abstract void use();
}
