package com.customgraphicinterface.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.customgraphicinterface.pubsub.EventManager;
import com.customgraphicinterface.pubsub.EventManager.Channel;
import com.customgraphicinterface.pubsub.ISubsciber;

import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;

public class CustomDrawingPanel extends JPanel{
	private ICamera _mainCamera;
	private Channel _drawChannel;

	public CustomDrawingPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        createCamera();
		createDrawChannel();
    }

	private void createDrawChannel(){
		_drawChannel = EventManager.getInstance().createChannel("draw");
		if(_drawChannel == null) 
			throw new NullPointerException("Can't create 'draw' channel!");
	}

	private void createCamera(){
		_mainCamera = new Camera();
		if(_mainCamera == null)
			throw new NullPointerException("Camera is null!");

		_mainCamera.bindCameraToCanvas(this);
	}

    public Dimension getPreferredSize() {
        return new Dimension(1400,800);
    }

	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

		Graphics2D g2d = (Graphics2D)g;
		
        for (ISubsciber e : _drawChannel.subs) {
			AffineTransform old = g2d.getTransform();
			e.onEventRecieved(_drawChannel.name, g2d, _mainCamera);
			g2d.setTransform(old);
		}
    }

	public ICamera getMainCamera() {
		return _mainCamera;
	}
}
