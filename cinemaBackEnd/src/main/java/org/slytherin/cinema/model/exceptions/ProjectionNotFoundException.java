package org.slytherin.cinema.model.exceptions;

public class ProjectionNotFoundException extends RuntimeException {
    public ProjectionNotFoundException(String message) {
            super(message);
        }
}
