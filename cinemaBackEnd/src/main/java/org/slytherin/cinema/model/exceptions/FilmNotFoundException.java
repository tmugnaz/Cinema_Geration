package org.slytherin.cinema.model.exceptions;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException(String message) {
        super(message);
    }
}
