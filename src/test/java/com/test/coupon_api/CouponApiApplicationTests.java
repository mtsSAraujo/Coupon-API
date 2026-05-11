package com.test.coupon_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class CouponApiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void shouldStartApplication() {

		assertDoesNotThrow(() ->
				CouponApiApplication.main(new String[]{})
		);
	}
}
