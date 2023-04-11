package com.citymanagement;

import com.customgraphicinterface.UI.GameLoop;
import com.customgraphicinterface.UI.MainWindow;

public class Launcher{

	public static void main(String[] args) {

		GameLoop g = new GameLoop();
		MainWindow mainWindow = MainWindow.getInstance();
		g.linkComponent(mainWindow.getMainCanvas().getCanvasComponent());
		mainWindow.setSize(1500, 500);
		GameManager gm = new GameManager();
		mainWindow.run();
		g.start();
	}
}