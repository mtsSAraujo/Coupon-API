package com.test.coupon_api.exception.model;

public class CouponAlreadyDeletedException extends RuntimeException {
    public CouponAlreadyDeletedException(String message) {
        super(message);
    }
}
