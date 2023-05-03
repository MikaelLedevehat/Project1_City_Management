package com.citymanagement.gameobjects;

import com.customgraphicinterface.core.GameObject;
import com.customgraphicinterface.utilities.Vector2;

//TODO implement durability
/**
 * A class used to represent a Ressource, usable by other game objects.
 */
public abstract class Ressource extends GameObject{

    /**
     * Create a new Ressource.
     * @param pos the position the ressource will be created at.
     */
    public Ressource( Vector2 pos){
        getTransform().setPos(pos);
    }

    /**
     * Called each frame.
     */
    @Override
    public void update() {
        
    }

    /**
     * Abstract method use to implement how the resource will be use.
     */
    public abstract void use();
}
