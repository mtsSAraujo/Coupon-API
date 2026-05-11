package com.test.coupon_api.exception.model;

public class InvalidCouponCodeException extends RuntimeException {
    public InvalidCouponCodeException(String message) {
        super(message);
    }
}
