/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

package com.customgraphicinterface.geometry;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.customgraphicinterface.UI.ICamera;
import com.customgraphicinterface.core.Transform;
import com.customgraphicinterface.utilities.Vector2;

import java.awt.Color;
import java.awt.BasicStroke;

abstract public class CustomShape {
	private static int _shapeIdCounter = 0;
	private final long _shapeId;

	private Color _fillColor = Color.white;
	private Color _borderColor = Color.BLACK;
	private int _borderSize = 1;
	private boolean _lockedOnScreen = false;
	private boolean _lockedRotation = false;
	private java.awt.geom.RectangularShape _shape;
	private Vector2 _offset = new Vector2();
	private float _baseRotation = 0f;


	public Vector2 getOffset(){
		return _offset;
	}

	public void setOffset(Vector2 o){
		_offset = o;
	}

	public float getBaseRotation(){
		return _baseRotation;
	}

	public void setBaseRotation(float r){
		_baseRotation = r;
	}

	protected java.awt.geom.RectangularShape getShape(){
		return _shape;
	}

	protected void setShape(java.awt.geom.RectangularShape s){
		_shape = s;
	}

	public Color getFillColor() {
		return this._fillColor;
	}

	public void setFillColor(Color _fillColor) {
		this._fillColor = _fillColor;
	}

	public Color getBorderColor() {
		return _borderColor;
	}

	public void setBorderColor(Color _borderColor) {
		this._borderColor = _borderColor;
	}

	public int getBorderSize() {
		return _borderSize;
	}

	public void setBorderSize(int _borderSize) {

		this._borderSize = _borderSize;
	}
	
	public boolean getLockedOnScreen() {
		return _lockedOnScreen;
	}

	public void setLockedOnScreen(Boolean l) {
		this._lockedOnScreen = l;
	}

	public boolean getLockedRotation() {
		return _lockedRotation;
	}

	public void setLockedRotation(Boolean l) {
		this._lockedRotation = l;
	}

	public CustomShape(java.awt.geom.RectangularShape s) {
		_shapeId = CustomShape._shapeIdCounter;
		_shape = s;
		CustomShape._shapeIdCounter++;
	}
	
	public long getShapeId() {
		return _shapeId;
	}

	public void destroy(){
	}

	public void drawShape(Graphics2D g2d, ICamera camera, Transform t){
	
		AffineTransform old = g2d.getTransform();
		g2d.translate(t.getPos().x  + (getLockedOnScreen() == false ? camera.getTransform().getPos().x:0),t.getPos().y   + (getLockedOnScreen() == false ? camera.getTransform().getPos().y:0));
		if(_lockedRotation == false) g2d.rotate(getBaseRotation() + t.getRot());
		g2d.translate(getOffset().x, getOffset().y);
		if(getFillColor() != null) {
			g2d.setColor(getFillColor());
			g2d.fill(_shape);
		}
		
		if(getBorderColor() != null) {
			g2d.setColor(getBorderColor());
			g2d.setStroke(new BasicStroke(getBorderSize()));
			g2d.draw(_shape);
		}
		
		
		_shape.getBounds().x = (int)t.getPos().x;
		_shape.getBounds().y = (int)t.getPos().y;
		g2d.setTransform(old);
	}
}
