package com.customgraphicinterface.factory;

public interface IFactory<T> {

    public static Object[] checkOptions(Object[] optsArg, Class<?>[] optsRef){
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

    public static <T> IFactory<T> getInstance(){
        if(hf == null){
            synchronized(hf){
                if(hf == null){
                    hf = new HumanFactory();
                }
            }
        }
    }

    public T make(Object... options);
}
