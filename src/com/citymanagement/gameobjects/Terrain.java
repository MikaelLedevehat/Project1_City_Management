package com.citymanagement.gameobjects;

import java.awt.Color;

import com.customgraphicinterface.core.GameObject;
import com.customgraphicinterface.geometry.Rectangle;
import com.customgraphicinterface.utilities.Vector2;

/**
 * A class used to represent a zone on the ground with a certain size. different terrain hold different caracteristics.
 */
public class Terrain extends GameObject{

	//TODO describe how types affect terrain.
	//TODO delete settings, create new childs class.
	/**
	 * The type of terrain allowed.
	 */
	public enum Type {
		GRASS,
		WATER
	}

	Type _type;

	private Settings[] _settings = new Settings[]{
		new Settings(new Color(0,153,76), new Color(0,255,128),4, true),
		new Settings(new Color(0,0,204), new Color(51,51,204),4, false),
	};

	/**
	 * Get the height of the terrain.
	 * @return the height of the terrain.
	 */
	public int getHeight(){
		return ((Rectangle)getMesh(0)).getHeight();
	}

	/**
	 * Set the new height of the terrain.
	 * @param h the new height.
	 */
	public void setHeight(int h){
		((Rectangle)getMesh(0)).setHeight(h);
	}

	/**
	 * Get the width of the terrain.
	 * @return the width of the terrain.
	 */
	public int getWidth(){
		return ((Rectangle)getMesh(0)).getWidth();
	}

	/**
	 * Set the new width of the terrain.
	 * @param w the new width.
	 */
	public void setWidth(int w){
		((Rectangle) getMesh(0)).setWidth(w);
	}

	/**
	 * Create a new Terrain with a given size and position. The type will determine how it behaves.
	 * @param type the type of terrain.
	 * @param height the height of the terrain.
	 * @param width the width of the terrain.
	 * @param pos the position of the terrain.
	 */
	public Terrain(Type type, int height, int width, Vector2 pos) {

		addMesh(new Rectangle(height,width,new Vector2(), 0f,_settings[type.ordinal()].borderColor,_settings[type.ordinal()].borderSize,_settings[type.ordinal()].fillColor,_settings[type.ordinal()].lockedOnScreen,false));
		_type = type;
		getTransform().setPos(pos);

	}

	/**
	 * Called each frame.
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
