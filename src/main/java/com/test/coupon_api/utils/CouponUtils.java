package com.test.coupon_api.utils;

public class CouponUtils {

    private CouponUtils() {
        /* This utility class should not be instantiated */
    }

    public static String sanitizeCode(String code) {

        if (code == null) {
            return null;
        }

        return code.replaceAll("[^a-zA-Z0-9]", "");
    }
}
