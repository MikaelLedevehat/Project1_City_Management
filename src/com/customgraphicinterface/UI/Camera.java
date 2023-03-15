package com.customgraphicinterface.UI;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import com.customgraphicinterface.utilities.Vector2;

public class Camera extends MouseInputAdapter {
	private Vector2 _pos = new Vector2();
	private Vector2 _lastDragPos = new Vector2();
	private Vector2 _currentDragPos = new Vector2();
	
	public Vector2 getPos() {
		return _pos;
	}
	public void setPos(Vector2 pos) {
		_pos.x = pos.x;
		_pos.y = pos.y;
	}
	
	public Camera(Vector2 pos) {
		setPos(pos);
	}
	
	public Camera(int x, int y) {
		this(new Vector2(x,y));
	}
	
	public Camera() {
		this(new Vector2(0,0));
	}
	
	public void mousePressed(MouseEvent e) {
		//System.out.println("Yop");
		_lastDragPos = new Vector2(e.getX(),e.getY());
		_currentDragPos = new Vector2(e.getX(),e.getY());
	}
	
	public void mouseDragged(MouseEvent e) {
		_currentDragPos = new Vector2(e.getX(),e.getY());
	}

	public void mouseReleased(MouseEvent e) {
		//System.out.println("Yap");
	}
	
	public void update() {
		Vector2 drif = _currentDragPos.minus(_lastDragPos);
		_pos = _pos.plus(drif);
		_lastDragPos = _currentDragPos.clone();
	}
}