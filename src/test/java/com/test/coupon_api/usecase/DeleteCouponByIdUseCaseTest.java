package com.test.coupon_api.usecase;

import com.test.coupon_api.exception.model.CouponAlreadyDeletedException;
import com.test.coupon_api.exception.model.CouponNotFoundException;
import com.test.coupon_api.service.CouponService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteCouponByIdUseCaseTest {

    @Mock
    private CouponService couponService;

    @InjectMocks
    private DeleteCouponByIdUseCase deleteCouponByIdUseCase;

    @Test
    void shouldDeleteCouponById() {

        String couponId = "123";

        doNothing().when(couponService)
                .deleteCouponById(any(String.class));

        deleteCouponByIdUseCase.deleteById(couponId);

        verify(couponService, times(1))
                .deleteCouponById(any(String.class));
    }

    @Test
    void shouldThrowCouponNotFoundException() {

        String couponId = "123";

        doThrow(new CouponNotFoundException(
                "Cupom não encontrado. Verifique o ID enviado"
        )).when(couponService)
                .deleteCouponById(any(String.class));

        assertThrows(
                CouponNotFoundException.class,
                () -> deleteCouponByIdUseCase.deleteById(couponId)
        );

        verify(couponService, times(1))
                .deleteCouponById(any(String.class));
    }

    @Test
    void shouldThrowCouponAlreadyDeletedException() {

        String couponId = "123";

        doThrow(new CouponAlreadyDeletedException(
                "O cupom de ID: 123 ja esta deletado!"
        )).when(couponService)
                .deleteCouponById(any(String.class));

        assertThrows(
                CouponAlreadyDeletedException.class,
                () -> deleteCouponByIdUseCase.deleteById(couponId)
        );

        verify(couponService, times(1))
                .deleteCouponById(any(String.class));
    }
}