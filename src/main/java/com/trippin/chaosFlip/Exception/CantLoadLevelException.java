package com.trippin.chaosFlip.Exception;

public class CantLoadLevelException extends ChaosFlipException {

    private static final long serialVersionUID = 1L;

    public CantLoadLevelException(String msg, Throwable innerException) {
        super(msg, innerException);
    }
}
