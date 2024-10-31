package com.veryan.springbootapi.service;

public class InvalidInputException extends Exception{
    InvalidInputException(String msg){
        super(msg);
    }
    InvalidInputException(String msg, Exception e){
        super(msg, e);
    }
}
