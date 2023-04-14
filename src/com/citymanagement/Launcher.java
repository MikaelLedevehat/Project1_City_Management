package com.citymanagement;

import com.customgraphicinterface.UI.CustomWindow;
import com.customgraphicinterface.UI.IWindow;
import com.customgraphicinterface.core.GameLoop;

public class Launcher{

	public static void main(String[] args) {

		GameLoop g = new GameLoop();
		IWindow mainWindow = new CustomWindow(true);
		g.linkComponent(mainWindow.getComponent());
		GameManager gm = new GameManager();
		g.start();
	}
}