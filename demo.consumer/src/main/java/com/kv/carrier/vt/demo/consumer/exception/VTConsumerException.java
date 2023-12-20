package com.kv.carrier.vt.demo.consumer.exception;

public class VTConsumerException extends RuntimeException{
    public VTConsumerException() {
    }

    public VTConsumerException(String message) {
        super(message);
    }

    public VTConsumerException(String message, Throwable cause) {
        super(message, cause);
    }

    public VTConsumerException(Throwable cause) {
        super(cause);
    }

    public VTConsumerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
