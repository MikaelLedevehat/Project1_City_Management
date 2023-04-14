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
