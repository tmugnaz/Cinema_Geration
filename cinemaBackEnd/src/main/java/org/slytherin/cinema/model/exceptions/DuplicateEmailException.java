package org.slytherin.cinema.model.exceptions;

public class DuplicateEmailException extends Exception{
    public DuplicateEmailException(String message) {
        super(message);
    }
}
