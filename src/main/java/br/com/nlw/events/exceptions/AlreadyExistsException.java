package br.com.nlw.events.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message){
        super(message);
    }
}
