package com.customgraphicinterface.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.customgraphicinterface.pubsub.EventManager;
import com.customgraphicinterface.pubsub.ISubsciber;

import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;

public class CustomDrawingPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private static Camera _mainCamera;

	public CustomDrawingPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setMouseEvents();
        
		_mainCamera = new Camera();
		addMouseListener(_mainCamera);
		addMouseMotionListener(_mainCamera);
    }
	
	private void setMouseEvents() {
		 addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent e) {
	                //moveSquare(e.getX(),e.getY());
	            }
	        });

	        addMouseMotionListener(new MouseAdapter() {
	            public void mouseDragged(MouseEvent e) {
	                //moveSquare(e.getX(),e.getY());
	            }
	        });
	}

    public Dimension getPreferredSize() {
        return new Dimension(1400,800);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

		Graphics2D g2d = (Graphics2D)g;
		
		
		Camera camera = CustomDrawingPanel.getMainCamera();
		if(camera == null) {
			System.err.println("Error : Camera is null");
			return;
		}

        for (ISubsciber e : EventManager.getInstance().getChannel("draw")) {
			AffineTransform old = g2d.getTransform();
			e.onEventRecieved("draw",g2d, camera);
			g2d.setTransform(old);
		}
    }

	public static Camera getMainCamera() {
		return _mainCamera;
	}
}
