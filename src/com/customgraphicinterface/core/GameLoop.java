/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

package com.customgraphicinterface.core;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.customgraphicinterface.pubsub.EventManager;
import com.customgraphicinterface.pubsub.EventManager.Channel;

public class GameLoop {

	public static final int DEFAULT_LOOP_DELAY = 16;

	private Channel _updateChannel;
	@SuppressWarnings("unused")
	private Channel _drawChannel;
	private Runnable _repaintCalls;
	private Thread _loopWorker;
	private int _loopDelay = DEFAULT_LOOP_DELAY;
	private ArrayList<Component> _linkedComponents;


	public void setLoopDelay(int loopDelay){
		if(loopDelay <= 0) _loopDelay =1;
		else _loopDelay = loopDelay;
	}

	public GameLoop(int loopDelay){
		setLoopDelay(loopDelay);
		createListComponent();
		createRepaintRunnable();
		createUpdateChannel();
		initializeLoop();
	}

	public GameLoop(){
		this(DEFAULT_LOOP_DELAY);
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
						System.out.println("_drawChannel");
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
