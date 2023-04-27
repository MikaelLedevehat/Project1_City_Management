/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

package com.customgraphicinterface.core;

import com.customgraphicinterface.utilities.Vector2;

public class Transform{

    private Vector2 _pos;
    private float _rot;
    private Vector2 _scale;
    private Transform _parent;

    public Transform(Vector2 p, float r, Vector2 s){
        _pos = p;
        _rot = r;
        _scale = s;
    }

    public Transform(Transform t){
        _pos = t.getPos();
        _rot = t.getRot();
        _scale = t.getScale();
    }

    public Transform getWorldCoord(){
        Transform t = new Transform(this);
        Transform p = this._parent;

        while( p != null){
            t.add(p);
            p = p.getParent()==p?null:p.getParent();
        }

        return t;
    }

    public void add(Transform t){
        _pos = _pos.plus(t.getPos());
        _rot = _rot + t.getRot();
        _scale = _scale.plus(t.getScale());
    }

    public Vector2 getPos(){ return _pos;}
    public void setPos(Vector2 p){ _pos = p;}
    public float getRot(){ return _rot;}
    public void setRot(float p){ _rot = p;}
    public Vector2 getScale(){ return _scale;}
    public void setScale(Vector2 p){ _scale = p;}
    public Transform getParent(){ return _parent;}
    public void setParent(Transform p){ _parent = p;}


}