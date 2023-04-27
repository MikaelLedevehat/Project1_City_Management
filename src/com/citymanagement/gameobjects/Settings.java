/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

package com.citymanagement.gameobjects;

import java.awt.Color;

public class Settings {
    public Color borderColor;
    public Color fillColor;
    public int borderSize;
    public boolean lockedOnScreen;

    public Settings(Color borderColorArg, Color fillColorArg, int borderSizeArg, boolean lockedOnScreenArg){
			this.borderColor = borderColorArg;
			this.fillColor = fillColorArg;
			this.borderSize = borderSizeArg;
			this.lockedOnScreen = lockedOnScreenArg;
		}
}
