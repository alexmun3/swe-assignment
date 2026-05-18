package com.digitalid.exception;

/**
 * Thrown when an invalid identity operation
 * or invalid identity data is encountered.
 */
public class InvalidIdentityException
        extends RuntimeException {

    public InvalidIdentityException(
            String message
    ) {

        super(message);
    }
}