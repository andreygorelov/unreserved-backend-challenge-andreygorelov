package com.unreserved.challenge.exception;

public class ListingNotFoundException extends RuntimeException{
    public ListingNotFoundException(String message, String id){
        super(message);
    }
}
