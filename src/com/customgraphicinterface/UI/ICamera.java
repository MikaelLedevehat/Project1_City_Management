/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

package com.customgraphicinterface.UI;

import com.customgraphicinterface.core.ITransformable;

public interface ICamera extends ITransformable{
    public void update();
    public void bindCameraToCanvas(ICanvas c);
}
