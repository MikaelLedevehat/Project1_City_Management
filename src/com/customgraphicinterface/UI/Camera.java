/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

package com.customgraphicinterface.UI;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

import com.customgraphicinterface.core.Transform;
import com.customgraphicinterface.utilities.Vector2;

public class Camera extends MouseInputAdapter implements ICamera{
	//private Vector2 _pos = new Vector2();
	private final Transform _transform;
	private Vector2 _lastDragPos = new Vector2();
	private Vector2 _currentDragPos = new Vector2();
	
	@Override
	public Transform getTransform(){
		return _transform;
	}

	public Camera(Vector2 pos) {
		_transform = new Transform(pos,0f,new Vector2(1,1));
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
	
	@Override
	public void update() {
		Vector2 drif = _currentDragPos.minus(_lastDragPos);
		_transform.setPos(_transform.getPos().plus(drif));
		_lastDragPos = _currentDragPos.clone();
	}

	@Override
	public void bindCameraToCanvas(ICanvas c) {
		c.getComponent().addMouseListener(this);
		c.getComponent().addMouseMotionListener(this);
	}
}