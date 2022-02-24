package com.parking.exception;

public class NoAvailableSpaceException extends RuntimeException {

    private static final long serialVersionUID = -7842251223561640341L;

    public NoAvailableSpaceException(String message) {
        super(message);
    }
}
