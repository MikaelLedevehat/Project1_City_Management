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
