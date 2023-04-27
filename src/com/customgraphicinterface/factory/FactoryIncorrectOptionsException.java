/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

package com.customgraphicinterface.factory;

public class FactoryIncorrectOptionsException extends RuntimeException{

    public FactoryIncorrectOptionsException(String s, Throwable err){
        super(s, err); 
    }

    public FactoryIncorrectOptionsException(String s){
        this(s, null);
    }

    public FactoryIncorrectOptionsException(){
        this(null, null);
    }
}
