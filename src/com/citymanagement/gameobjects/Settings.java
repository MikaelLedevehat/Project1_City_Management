package com.citymanagement.gameobjects;

import java.awt.Color;

/**
 * Depreciated class use to represent a collection of graphic setting: Fill color, border color, border width, is the shape locked on the screen.
 */
public class Settings {
    public Color borderColor;
    public Color fillColor;
    public int borderSize;
    public boolean lockedOnScreen;

    /**
     * Create a new setting.
     * @param borderColorArg the border color of the mesh.
     * @param fillColorArg the fill color of the mesh.
     * @param borderSizeArg the border width of the mesh.
     * @param lockedOnScreenArg is the mesh locked on screen.
     */
    public Settings(Color borderColorArg, Color fillColorArg, int borderSizeArg, boolean lockedOnScreenArg){
			this.borderColor = borderColorArg;
			this.fillColor = fillColorArg;
			this.borderSize = borderSizeArg;
			this.lockedOnScreen = lockedOnScreenArg;
		}
}
