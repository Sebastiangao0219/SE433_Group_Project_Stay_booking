package com.depaul.se433_project_stay_booking.exception;

public class ReservationCollisionException extends RuntimeException {
    public ReservationCollisionException(String message) {
        super(message);
    }
}
