package com.customgraphicinterface.geometry;
import java.awt.Color;
import java.awt.geom.Ellipse2D;

import com.customgraphicinterface.utilities.Vector2;


public class Circle extends CustomShape{

    private final Ellipse2D _circle;
    private float _radius;

    public float getRadius(){
        return _radius;
    }

    public void setRadius(float r){
        _radius = r;
        _circle.setFrame(0,0, 2*_radius,2*_radius);
    }

    public Circle(float radius, Vector2 offset, float baseRot, Color fillColor, Color borderColor, int borderSize, boolean lockedOnScreen){
        super(new Ellipse2D.Double());
        _circle = (Ellipse2D) getShape();
        setBorderColor(borderColor);
        setFillColor(fillColor);
        setBorderSize(borderSize);
        setRadius(radius);
        setOffset(offset);
        setBaseRotation(baseRot);
        setLockedOnScreen(lockedOnScreen);
    }   

    public Circle(){
        this(10f,new Vector2(), 0f,Color.white,Color.black,2, false);
    }
}
