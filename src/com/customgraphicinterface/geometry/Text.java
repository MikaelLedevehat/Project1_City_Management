package com.customgraphicinterface.geometry;

import java.awt.geom.RectangularShape;


public class Text  extends CustomShape{

    private String _str = "";

    public String getText(){
        return _str;
    }

    public void setText(String s){
        _str = s;
    }

    public Text(RectangularShape s, String str) {
        super(s);
        this._str = str;
        //TODO Auto-generated constructor stub
    }
}
