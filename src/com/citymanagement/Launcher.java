/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

package com.citymanagement;

import com.customgraphicinterface.UI.CustomWindow;
import com.customgraphicinterface.UI.IWindow;
import com.customgraphicinterface.core.GameLoop;

public class Launcher{

	public static void main(String[] args) {

		GameLoop g = new GameLoop(1000);
		IWindow mainWindow = new CustomWindow(true);
		g.linkComponent(mainWindow.getComponent());
		GameManager gm = new GameManager();
		g.start();
	}
}