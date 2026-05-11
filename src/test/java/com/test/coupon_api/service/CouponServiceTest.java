package com.test.coupon_api.service;

import com.test.coupon_api.dto.request.CouponRequestDTO;
import com.test.coupon_api.exception.model.CouponAlreadyDeletedException;
import com.test.coupon_api.exception.model.CouponNotFoundException;
import com.test.coupon_api.exception.model.InvalidCouponCodeException;
import com.test.coupon_api.model.Coupon;
import com.test.coupon_api.model.enums.CouponStatus;
import com.test.coupon_api.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponService couponService;

    @Test
    void shouldCreateCoupon() {

        CouponRequestDTO requestDTO = new CouponRequestDTO(
                "ABC123",
                "Cupom desconto",
                BigDecimal.TEN,
                LocalDateTime.now().plusDays(10),
                true
        );

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

        when(couponRepository.save(any(Coupon.class))).thenReturn(coupon);

        Coupon result = couponService.createCoupon(requestDTO);

        assertNotNull(result);

        assertEquals("123", result.getId());
        assertEquals("ABC123", result.getCode());

        verify(couponRepository, times(1))
                .save(any(Coupon.class));
    }

    @Test
    void shouldFindCouponById() {

        String couponId = "123";

        Coupon coupon = Coupon.builder()
                .id(couponId)
                .status(CouponStatus.ACTIVE)
                .build();

        when(couponRepository.findById(couponId))
                .thenReturn(Optional.of(coupon));

        Coupon result = couponService.findCouponById(couponId);

        assertNotNull(result);
        assertEquals(couponId, result.getId());

        verify(couponRepository, times(1))
                .findById(couponId);
    }

    @Test
    void shouldThrowExceptionWhenCouponNotFound() {

        String couponId = "123";

        when(couponRepository.findById(couponId))
                .thenReturn(Optional.empty());

        CouponNotFoundException exception =
                assertThrows(
                        CouponNotFoundException.class,
                        () -> couponService.findCouponById(couponId)
                );

        assertEquals(
                "Cupom não encontrado. Verifique o ID enviado",
                exception.getMessage()
        );

        verify(couponRepository, times(1))
                .findById(couponId);
    }

    @Test
    void shouldDeleteCouponById() {

        String couponId = "123";

        Coupon coupon = Coupon.builder()
                .id(couponId)
                .status(CouponStatus.ACTIVE)
                .build();

        when(couponRepository.findById(couponId))
                .thenReturn(Optional.of(coupon));

        when(couponRepository.save(any(Coupon.class)))
                .thenReturn(coupon);

        couponService.deleteCouponById(couponId);

        assertEquals(CouponStatus.DELETED, coupon.getStatus());

        verify(couponRepository, times(1))
                .findById(couponId);

        verify(couponRepository, times(1))
                .save(coupon);
    }

    @Test
    void shouldThrowExceptionWhenCouponAlreadyDeleted() {

        String couponId = "123";

        Coupon coupon = Coupon.builder()
                .id(couponId)
                .status(CouponStatus.DELETED)
                .build();

        when(couponRepository.findById(couponId))
                .thenReturn(Optional.of(coupon));

        CouponAlreadyDeletedException exception =
                assertThrows(
                        CouponAlreadyDeletedException.class,
                        () -> couponService.deleteCouponById(couponId)
                );

        assertEquals(
                String.format(
                        "O cupom de ID: %s ja esta deletado!",
                        couponId
                ),
                exception.getMessage()
        );

        verify(couponRepository, times(1))
                .findById(couponId);

        verify(couponRepository, never())
                .save(any(Coupon.class));
    }

    @Test
    void shouldThrowExceptionWhenCouponCodeHasLessThan6Characters() {
        CouponRequestDTO requestDTO = new CouponRequestDTO(
                "AB123",
                "Cupom desconto",
                BigDecimal.TEN,
                LocalDateTime.now().plusDays(10),
                true
        );

        assertThrows(
                InvalidCouponCodeException.class,
                () -> couponService.createCoupon(requestDTO)
        );
    }
}