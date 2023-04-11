package com.customgraphicinterface.core;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.customgraphicinterface.UI.Camera;
import com.customgraphicinterface.geometry.CustomShape;
import com.customgraphicinterface.pubsub.EventManager;
import com.customgraphicinterface.pubsub.ISubsciber;
import com.customgraphicinterface.utilities.Vector2;

public abstract class GameObject implements ISubsciber, ITransformable{

	private static int _gameObjectIdCounter = 0;
	private long _gameObjectId;
	private final ArrayList<CustomShape> _meshObjects;
	private final Transform _transform;
	private int _zIndex;

	public int getZIndex(){
		return _zIndex;
	}

	protected void setZIndex(int z){
		_zIndex = z;
	}
	
	@Override
	public Transform getTransform(){
		return _transform;
	}

	protected CustomShape getMesh(int index){
		return _meshObjects.get(index);
	}
	
	protected void addMesh(CustomShape s){
		if(_meshObjects.size() == 0) EventManager.getInstance().subscribe("draw",this);
		_meshObjects.add(s);
	}

	protected void removeMesh(int index){
		CustomShape c = _meshObjects.remove(index);
		if(c!=null) c.destroy();
		if(_meshObjects.size() == 0) EventManager.getInstance().unsubscribe("draw",this);
	}

	protected void removeMesh(CustomShape s){
		_meshObjects.remove(s);
		s.destroy();
	}

	private void destroyMeshes(){
		for(int i=0; i<_meshObjects.size();i++){
			removeMesh(i);
		}
	}

	public GameObject() {
		//_meshObject = s;
		_meshObjects = new ArrayList<>();
		_transform = new Transform(new Vector2(), 0, new Vector2(1f,1f));
		_gameObjectId = GameObject._gameObjectIdCounter;
		GameObject._gameObjectIdCounter ++;

		EventManager.getInstance().subscribe("update",this);
		
	}
	
	public long getId() {
		return _gameObjectId;
	}

	public void destroy(){
		EventManager.getInstance().unsubscribe("update", this);
		destroyMeshes();
	}

	@Override
	public void onEventRecieved(String type, Object... payload){
		if(type.equals("update"))
			update();
		else if(type.equals("draw")){
			if(payload.length != 2) 
				throw new IllegalArgumentException("Draw call arguments numbers incorrects! ");
			draw((Graphics2D)payload[0],(Camera)payload[1]);
		}
	}

	public final void draw(Graphics2D g2d, Camera c){
		
		if(_meshObjects == null) return;
		for(int i=0; i < _meshObjects.size();i++){
			_meshObjects.get(i).drawShape(g2d, c, getTransform().getWorldCoord());
		}
	}

	public abstract void update();
}
