package com.test.coupon_api.utils;

import org.junit.jupiter.api.Test;

import static com.test.coupon_api.utils.CouponUtils.sanitizeCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CouponUtilsTest {

    @Test
    void shouldSanitizeCode() {

        String code = "ABC-123@#$";

        String result = sanitizeCode(code);

        assertNotNull(result);
        assertEquals("ABC123", result);
    }

    @Test
    void shouldReturnOnlyLettersAndNumbers() {

        String code = "COUPON_2026!!";

        String result = sanitizeCode(code);

        assertEquals("COUPON2026", result);
    }

    @Test
    void shouldReturnEmptyStringWhenCodeContainsOnlySpecialCharacters() {

        String code = "@#$%¨&*()";

        String result = sanitizeCode(code);

        assertNotNull(result);
        assertEquals("", result);
    }

    @Test
    void shouldReturnSameCodeWhenCodeIsAlreadySanitized() {

        String code = "ABC123";

        String result = sanitizeCode(code);

        assertEquals("ABC123", result);
    }

    @Test
    void shouldReturnNullWhenCodeIsNull() {

        String result = sanitizeCode(null);

        assertNull(result);
    }
}