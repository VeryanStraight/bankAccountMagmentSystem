package com.veryan.springbootapi.service;

public class AlreadyExistsException extends Exception{
    AlreadyExistsException(String msg){
        super(msg);
    }
    AlreadyExistsException(String msg, Exception e){
        super(msg, e);
    }
}
