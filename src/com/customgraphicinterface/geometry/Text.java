package com.customgraphicinterface.geometry;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Font;

import com.customgraphicinterface.UI.ICamera;
import com.customgraphicinterface.core.Transform;
import com.customgraphicinterface.utilities.Vector2;


public class Text  extends CustomShape{

    private String _str = "";
    private int _fontSize = 10;

    public String getText(){
        return _str;
    }

    public void setText(String s){
        _str = s;
    }

    public int getFontSize(){
        return _fontSize;
    }

    public void setFontSize(int s){
        _fontSize = s;
    }

    public Text(String str, Vector2 offset, int fontSize, Float baseRotation, boolean lockedOnScreen, boolean lockedRotation) {
        super(null);
        setLockedOnScreen(lockedOnScreen);
        setLockedRotation(lockedRotation);
		setOffset(offset);
		setBaseRotation(baseRotation);
        this._str = str;
        this._fontSize = fontSize;
    }

    @Override
    public void drawShape(Graphics2D g2d, ICamera camera, Transform t){
        AffineTransform old = g2d.getTransform();
		g2d.translate(t.getPos().x  + (getLockedOnScreen() == false ? camera.getTransform().getPos().x:0),t.getPos().y   + (getLockedOnScreen() == false ? camera.getTransform().getPos().y:0));
		if(getLockedRotation() == false) g2d.rotate(getBaseRotation() + t.getRot());
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, _fontSize)); 
		g2d.drawString(_str,getOffset().x,getOffset().y);
        g2d.setTransform(old);
    }
}
