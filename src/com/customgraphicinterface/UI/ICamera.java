package com.customgraphicinterface.UI;

import com.customgraphicinterface.core.ITransformable;

public interface ICamera extends ITransformable{
    public void update();
    public void bindCameraToCanvas(ICanvas c);
}
