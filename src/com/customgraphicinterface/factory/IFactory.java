/*Copyright 2021 Google LLC
*
*Author: Mikael Le Devehat
*Use of this source code is governed by an MIT-style
*license that can be found in the LICENSE file or at
https://opensource.org/licenses/MIT.
*/

package com.customgraphicinterface.factory;

public interface IFactory<T> {

    public default Object[] checkOptions(Object[] optsArg, Class<?>[] optsRef){
        Object[] r = new Object[optsRef.length];
        try {
            int length = optsArg.length<optsRef.length?optsArg.length:optsRef.length;
            for(int i=0;i<length;i++){
                if(optsArg.getClass() == optsRef[i]) r[i] = optsArg[i];
                else throw new ClassCastException();
            }
        } catch (Exception e) {
            throw new FactoryIncorrectOptionsException("Mismatching options!", e);
        }
        
        return r;
    }

    public T make(Object... options);
}
