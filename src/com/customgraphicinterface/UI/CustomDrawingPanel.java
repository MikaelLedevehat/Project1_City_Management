package com.customgraphicinterface.UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.customgraphicinterface.pubsub.EventManager;
import com.customgraphicinterface.pubsub.EventManager.Channel;
import java.awt.Graphics2D;

public class CustomDrawingPanel extends JPanel implements ICanvas{
	private ICamera _mainCamera;
	private Channel _drawChannel;

	public CustomDrawingPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        createCamera();
		getDrawChannel();
    }

	private void getDrawChannel(){
		_drawChannel = EventManager.getInstance().accessChannel("draw");
	}

	private void createCamera(){
		_mainCamera = new Camera();
		if(_mainCamera == null)
			throw new NullPointerException("Camera is null!");

		_mainCamera.bindCameraToCanvas(this);
	}

	@Override
    public Dimension getPreferredSize() {
        return new Dimension(1300,700);
    }

	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
		_mainCamera.update();
		Graphics2D g2d = (Graphics2D)g;
		_drawChannel.sendNotification(g2d, _mainCamera);
    }

	@Override
	public ICamera getMainCamera() {
		return _mainCamera;
	}

	@Override
	public Component getComponent() {
		return this;
	}
}
