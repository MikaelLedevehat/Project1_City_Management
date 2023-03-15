package com.citymanagement;

import javax.swing.SwingUtilities;

import com.customgraphicinterface.UI.MainWindow;

public class Launcher{

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainWindow mainWindow = MainWindow.getInstance();
				GameManager gm = new GameManager();
				mainWindow.run();
			}
		});
	}
}