package com.veryan.springbootapi.service;

/**
 * an exception for if the input is invalid
 */
public class InvalidInputException extends Exception{
    InvalidInputException(String msg){
        super(msg);
    }
    InvalidInputException(String msg, Exception e){
        super(msg, e);
    }
}
