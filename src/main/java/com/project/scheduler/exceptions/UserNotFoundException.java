package com.project.scheduler.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final long id) {
        super("There is no user with id " + id);
    }

    public UserNotFoundException(final String email) {
        super("There is no user with email " + email);
    }

}
