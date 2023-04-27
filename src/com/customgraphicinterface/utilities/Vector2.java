/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

package com.customgraphicinterface.utilities;

public class Vector2
{              
    // Members
    public float x;
    public float y;
       
    // Constructors
    public Vector2() {
        this.x = 0.0f;
        this.y = 0.0f;
    }
       
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }
       
    // Compare two vectors
    public boolean equals(Vector2 other) {
        return (this.x == other.x && this.y == other.y);
    }
    
    public Vector2 plus(Vector2 v) {
    	return new Vector2(x+v.x, y+v.y);
    }
    public Vector2 minus(Vector2 v) {
    	return new Vector2(x-v.x, y-v.y);
    }
    public Vector2 multiply(float n) {

    	return new Vector2(x*n, y*n);
    }
    public Vector2 divide(float n) {
    	if(n == 0)return new Vector2(0,0);
    	return new Vector2(x/n, y/n);
    }
    
    public String toString() {
    	return "( "+this.x+" , " + this.y + " )";
    }
    
    public Vector2 clone() {
    	return new Vector2(this.x, this.y);
    }
    
    public static float dist(Vector2 v1, Vector2 v2) {
    	return (float)Math.sqrt((v2.x-v1.x)*(v2.x-v1.x) + (v2.y-v1.y)*(v2.y-v1.y));
    }
    
    public static float magnetude(Vector2 v1) {
    	return (float)Math.sqrt((v1.x)*(v1.x) + (v1.y)*(v1.y));
    }
    
    public static Vector2 normalize(Vector2 v) {
    	return v.divide(Vector2.magnetude(v));
    }
}