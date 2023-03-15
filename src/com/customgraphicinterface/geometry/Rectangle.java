package com.customgraphicinterface.geometry;
import java.awt.Color;

import com.customgraphicinterface.utilities.Vector2;

public class Rectangle extends CustomShape {

	private final java.awt.Rectangle _rectangle;

	public int getHeight() {
		return _rectangle.height;
	}

	public void setHeight(int height) {
		this._rectangle.height = height;
	}

	public int getWidth() {
		return _rectangle.width;
	}

	public void setWidth(int width) {
		this._rectangle.width = width;
	}
	public Rectangle(int height,int width, Vector2 offset, Float baseRotation,  Color borderColor, int borderSize, Color fillColor, boolean lockedOnScreen ) {
		// TODO Auto-generated constructor stub
		super(new java.awt.Rectangle());
		_rectangle = (java.awt.Rectangle) getShape();
		setLockedOnScreen(lockedOnScreen);
		setWidth(width);
		setHeight(height);
		setBorderColor(borderColor);
		setFillColor(fillColor);
		setBorderSize(borderSize);
		setOffset(offset);
		setBaseRotation(baseRotation);

	}

	public Rectangle(){
		this(10,10,new Vector2(),0f,Color.black,2,Color.white,false);
	}
}
