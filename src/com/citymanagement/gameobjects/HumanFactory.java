/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

package com.citymanagement.gameobjects;

import java.awt.Color;

import com.customgraphicinterface.factory.IFactory;
import com.customgraphicinterface.utilities.Vector2;

public final class HumanFactory implements IFactory<Human>{

    public enum HumanType {
        DEFAULT_MALE, 
        DEFUALT_FEMALE,
    }

    public static HumanFactory build(){
        return new HumanFactory();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Human make(Object... options) {
        Human g = null;

        Object[] o = checkOptions(options, new Class[]{IPopulation.class, Vector2.class, HumanType.class, Color.class});

        if(o[2] == null)
            o[2] = HumanType.values()[(int)(Math.random()*HumanType.values().length)];

        switch ((HumanType)o[2]){
            case DEFAULT_MALE: g = new HumanMale((IPopulation<Human>)o[0], (Vector2)o[1]==null?new Vector2():(Vector2)o[1], (Color)o[3]==null?HumanMale.DEFAULT_COLOR:(Color)o[3], HumanMale.DEFAULT_ATTARCTIVNESS);
            break;

            case DEFUALT_FEMALE: g = new HumanFemale((IPopulation<Human>)o[0], (Vector2)o[1]==null?new Vector2():(Vector2)o[1], (Color)o[3]==null?HumanMale.DEFAULT_COLOR:(Color)o[3]);
            break;
        }

        return g;
    }

    /*public static IFactory<Human> getInstance() {
        if(_hf == null){
            synchronized(_hf){
                if(_hf == null){
                    _hf = new HumanFactory();
                }
            }
        }
        
        return _hf;
    }*/

}
