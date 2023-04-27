/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

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
		return ((Rectangle)getMesh(0)).getHeight();
	}

	public void setHeight(int h){
		((Rectangle)getMesh(0)).setHeight(h);
	}

	public int getWidth(){
		return ((Rectangle)getMesh(0)).getWidth();
	}

	public void setWidth(int w){
		((Rectangle) getMesh(0)).setWidth(w);
	}

	public Terrain(Type type, int height, int width, Vector2 pos) {

		addMesh(new Rectangle(height,width,new Vector2(), 0f,_settings[type.ordinal()].borderColor,_settings[type.ordinal()].borderSize,_settings[type.ordinal()].fillColor,_settings[type.ordinal()].lockedOnScreen,false));
		_type = type;
		getTransform().setPos(pos);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
