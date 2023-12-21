package com.kv.carrier.vt.demo.consumer.exception;

public class VTConsumerThreadException extends RuntimeException{
    public VTConsumerThreadException() {
    }

    public VTConsumerThreadException(String message) {
        super(message);
    }

    public VTConsumerThreadException(String message, Throwable cause) {
        super(message, cause);
    }

    public VTConsumerThreadException(Throwable cause) {
        super(cause);
    }

    public VTConsumerThreadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
