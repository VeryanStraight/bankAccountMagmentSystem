package com.veryan.springbootapi.service;

public class NoSuchRecordException extends Exception{
    NoSuchRecordException(String msg, Exception e){
        super(msg, e);
    }
    NoSuchRecordException(String msg){
        super(msg);
    }
}
