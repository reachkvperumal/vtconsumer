package com.kv.carrier.vt.demo.consumer.exception;

public class VTConsumerSubTaskException extends RuntimeException{
    public VTConsumerSubTaskException() {
    }

    public VTConsumerSubTaskException(String message) {
        super(message);
    }

    public VTConsumerSubTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public VTConsumerSubTaskException(Throwable cause) {
        super(cause);
    }

    public VTConsumerSubTaskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
