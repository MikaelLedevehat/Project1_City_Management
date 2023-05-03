package com.citymanagement.gameobjects;

/**
 * Exeption for when a factory is not present or does not have a specific key method.
 */
public class NoSuchFactoryException extends RuntimeException{
    /**
     * Create a new NoSuchFactoryException, indicating a missing factory, or method within said factory.
     * @param s custom message log.
     * @param err original cause of error.
     */
    public NoSuchFactoryException(String s, Throwable err){
        super(s,err);
    }

     /**
     * Create a new NoSuchFactoryException, indicating a missing factory, or method within said factory.
     * @param s custom message log.
     */
    public NoSuchFactoryException(String s){
        super(s);
    }
}