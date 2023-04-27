/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

package com.citymanagement.gameobjects;

public class NoSuchFactoryException extends RuntimeException{
    public NoSuchFactoryException(String s, Throwable err){
        super(s,err);
    }

    public NoSuchFactoryException(String s){
        super(s);
    }
}