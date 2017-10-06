package com.trippin.chaosFlip.Exception;

public class ChaosFlipException extends Exception {

    private static final long serialVersionUID = 1L;

    public ChaosFlipException(String msg, Throwable innerException) {
        super(msg, innerException);
    }
}
