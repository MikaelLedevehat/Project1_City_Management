package com.citymanagement.gameobjects;

import com.customgraphicinterface.core.GameObject;
import com.customgraphicinterface.geometry.Circle;
import com.customgraphicinterface.utilities.Vector2;
import java.awt.Color;

public class Ressource extends GameObject{

    private Settings[] _settings = new Settings[]{
		new Settings(new Color(150,150,0), new Color(255,255,0),4, false),
	};

    public enum RessourceType{
        FOOD,
    }

    public static final float RADIUS = 10f;

    RessourceType _type;

    public RessourceType get_ressourceType(){
        return _type;
    }

    public Ressource(RessourceType t, Vector2 pos){
        _type = t;
        addMesh(new Circle(RADIUS, new Vector2(-RADIUS,-RADIUS),0f,_settings[t.ordinal()].fillColor,_settings[t.ordinal()].borderColor,_settings[t.ordinal()].borderSize,_settings[t.ordinal()].lockedOnScreen ));
        getTransform().setPos(pos);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }

    public void used(){

    }
}
