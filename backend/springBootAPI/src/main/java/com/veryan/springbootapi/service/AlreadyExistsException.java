package com.veryan.springbootapi.service;

/**
 * an exception for when an item that a method is trying to create already exists
 */
public class AlreadyExistsException extends Exception{
    AlreadyExistsException(String msg){
        super(msg);
    }
}
