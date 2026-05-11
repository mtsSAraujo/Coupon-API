package com.test.coupon_api.usecase;

import com.test.coupon_api.dto.response.CouponResponseDTO;
import com.test.coupon_api.exception.model.CouponNotFoundException;
import com.test.coupon_api.model.Coupon;
import com.test.coupon_api.model.enums.CouponStatus;
import com.test.coupon_api.service.CouponService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindCouponByIdUseCaseTest {

    @Mock
    private CouponService couponService;

    @InjectMocks
    private FindCouponByIdUseCase findCouponByIdUseCase;

    @Test
    void shouldFindCouponById() {

        Coupon coupon = Coupon.builder()
                .id("123")
                .code("ABC123")
                .description("Cupom desconto")
                .discountValue(BigDecimal.TEN)
                .expirationDate(LocalDateTime.now().plusDays(10))
                .status(CouponStatus.ACTIVE)
                .published(true)
                .redeemed(false)
                .build();

        when(couponService.findCouponById(any(String.class)))
                .thenReturn(coupon);

        CouponResponseDTO result =
                findCouponByIdUseCase.findById("123");

        assertNotNull(result);

        assertEquals("123", result.id());
        assertEquals("ABC123", result.code());
        assertEquals(CouponStatus.ACTIVE, result.status());

        verify(couponService, times(1))
                .findCouponById(any(String.class));
    }

    @Test
    void shouldThrowCouponNotFoundException() {

        when(couponService.findCouponById(any(String.class)))
                .thenThrow(new CouponNotFoundException(
                        "Cupom não encontrado. Verifique o ID enviado"
                ));

        assertThrows(
                CouponNotFoundException.class,
                () -> findCouponByIdUseCase.findById("123")
        );

        verify(couponService, times(1))
                .findCouponById(any(String.class));
    }
}