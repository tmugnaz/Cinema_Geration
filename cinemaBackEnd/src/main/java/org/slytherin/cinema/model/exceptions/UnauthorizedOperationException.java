package org.slytherin.cinema.model.exceptions;

public class UnauthorizedOperationException extends RuntimeException {
    public UnauthorizedOperationException(String message){
        super(message);
    }
}
