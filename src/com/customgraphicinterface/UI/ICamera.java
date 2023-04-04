package com.customgraphicinterface.UI;

import com.customgraphicinterface.core.ITransformable;

import javax.swing.JPanel;

public interface ICamera extends ITransformable{
    public void update();
    public void bindCameraToCanvas(JPanel c);
}
