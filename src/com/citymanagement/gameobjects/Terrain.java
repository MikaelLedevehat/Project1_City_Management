package com.citymanagement.gameobjects;

import java.awt.Color;

import com.customgraphicinterface.core.GameObject;
import com.customgraphicinterface.geometry.Rectangle;
import com.customgraphicinterface.utilities.Vector2;


public class Terrain extends GameObject{

	public enum Type {
		GRASS,
		WATER
	}

	Type _type;

	private Settings[] _settings = new Settings[]{
		new Settings(new Color(0,153,76), new Color(0,255,128),4, true),
		new Settings(new Color(0,0,204), new Color(51,51,204),4, false),
	};

	public int getHeight(){
		return ((Rectangle)getMesh()).getHeight();
	}

	public void setHeight(int h){
		((Rectangle)getMesh()).setHeight(h);
	}

	public int getWidth(){
		return ((Rectangle)getMesh()).getWidth();
	}

	public void setWidth(int w){
		((Rectangle) getMesh()).setWidth(w);
	}

	public Terrain(Type type, int height, int width, Vector2 pos) {

		setMesh(new Rectangle(height,width,new Vector2(), 0f,_settings[type.ordinal()].borderColor,_settings[type.ordinal()].borderSize,_settings[type.ordinal()].fillColor,_settings[type.ordinal()].lockedOnScreen));
		_type = type;
		getTransform().setPos(pos);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
