package com.test.coupon_api.Utils;

public class CouponUtils {

    public static String sanitizeCode(String code) {

        if (code == null) {
            return null;
        }

        return code.replaceAll("[^a-zA-Z0-9]", "");
    }
}
