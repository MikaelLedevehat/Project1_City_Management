package com.customgraphicinterface.core;

import java.awt.Graphics2D;

import com.customgraphicinterface.UI.Camera;
import com.customgraphicinterface.geometry.CustomShape;
import com.customgraphicinterface.pubsub.ISubsciber;
import com.customgraphicinterface.utilities.Vector2;

public abstract class GameObject implements ISubsciber{

	private static int _gameObjectIdCounter = 0;
	private long _gameObjectId;
	private CustomShape _meshObject;
	private final Transform _transform;
	private int _zIndex;

	public int getZIndex(){
		return _zIndex;
	}

	protected void setZIndex(int z){
		_zIndex = z;
	}
	
	public Transform getTransform(){
		return _transform;
	}

	protected CustomShape getMesh(){
		return _meshObject;
	}
	
	protected void setMesh(CustomShape d){
		_meshObject = d;
	}

	public GameObject() {
		//_meshObject = s;
		_transform = new Transform(new Vector2(), 0, new Vector2(1f,1f));
		_gameObjectId = GameObject._gameObjectIdCounter;
		GameObject._gameObjectIdCounter ++;

		subscribe("update");
		subscribe("draw");
	}
	
	public long getId() {
		return _gameObjectId;
	}

	public void destroy(){
		unsubscribe("update");
		unsubscribe("draw");

		_meshObject.destroy();
	}

	@Override
	public void onEventRecieved(String type, Object... payload){
		if(type.equals("update")) update();
		else if(type.equals("draw")){
			if(payload.length != 2) throw new IllegalArgumentException("Draw call arguments numbers incorrects! ");
			draw((Graphics2D)payload[0],(Camera)payload[1]);
		}
	}

	public final void draw(Graphics2D g2d, Camera c){
		
		if(_meshObject != null) _meshObject.drawShape(g2d, c, getTransform().getWorldCoord());
	}

	public abstract void update();
}
