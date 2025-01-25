package com.shipment.track.shipment_tracker_pojo.pojo.exceptions;

public class NotSupportedOsmDataException extends RuntimeException {

    public NotSupportedOsmDataException() {
        super();
    }

    /**
     * @param message
     */
    public NotSupportedOsmDataException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public NotSupportedOsmDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
