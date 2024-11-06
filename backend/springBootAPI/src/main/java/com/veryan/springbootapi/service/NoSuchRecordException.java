package com.veryan.springbootapi.service;

/**
 * an exception for when a given item doesn't exist in the database
 */
public class NoSuchRecordException extends Exception{
    NoSuchRecordException(String msg, Exception e){
        super(msg, e);
    }
    NoSuchRecordException(String msg){
        super(msg);
    }
}
