package com.shipment.track.shipment_tracker_pojo.pojo.exceptions;

public class IndexAllReadyFouncException extends RuntimeException {
    /**
     * @param message
     * @param cause
     */
    public IndexAllReadyFouncException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public IndexAllReadyFouncException(String message) {
        super(message);
    }

    /**
     *
     */
    public IndexAllReadyFouncException() {
        super();
    }
}
