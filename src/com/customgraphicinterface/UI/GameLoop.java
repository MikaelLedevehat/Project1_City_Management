package com.customgraphicinterface.UI;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.customgraphicinterface.pubsub.EventManager;
import com.customgraphicinterface.pubsub.EventManager.Channel;

public class GameLoop {

	private Channel _updateChannel;
	private Channel _drawChannel;
	private Runnable _repaintCalls;
	private Thread _loopWorker;
	private int _loopDelay = 16;
	private ArrayList<Component> _linkedComponents;

	public GameLoop(){
		createListComponent();
		createRepaintRunnable();
		createUpdateChannel();
		initializeLoop();
	}

	public void linkComponent(Component c){
		_linkedComponents.add(c);
	}

	private void createListComponent() {
		_linkedComponents = new ArrayList<>();
	}

	public void start(){
		_loopWorker.start();
		try {
			_loopWorker.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void createRepaintRunnable(){
		_repaintCalls = new Runnable() {

			@Override
			public void run() {
				for(int i=0;i<_linkedComponents.size();i++){
					_linkedComponents.get(i).repaint();
				}
			}
			
		};
	}

	private void createUpdateChannel(){
		_updateChannel = EventManager.getInstance().createChannel("update", false);
		_drawChannel = EventManager.getInstance().createChannel("draw", true);
	}
    
    private void initializeLoop(){
		_loopWorker = new Thread(new Runnable() {

			@Override
			public void run() {
				while(true){
					try {
						
						_updateChannel.sendNotification();
						SwingUtilities.invokeLater(_repaintCalls);
						Thread.sleep(_loopDelay);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		});
	}
}
