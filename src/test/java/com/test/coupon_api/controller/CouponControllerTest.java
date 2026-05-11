package com.test.coupon_api.controller;

import com.test.coupon_api.dto.request.CouponRequestDTO;
import com.test.coupon_api.dto.response.CouponResponseDTO;
import com.test.coupon_api.model.enums.CouponStatus;
import com.test.coupon_api.usecase.CreateCouponUseCase;
import com.test.coupon_api.usecase.DeleteCouponByIdUseCase;
import com.test.coupon_api.usecase.FindCouponByIdUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponControllerTest {

    @Mock
    private CreateCouponUseCase createCouponUseCase;

    @Mock
    private DeleteCouponByIdUseCase deleteCouponByIdUseCase;

    @Mock
    private FindCouponByIdUseCase findCouponByIdUseCase;

    @InjectMocks
    private CouponController couponController;

    @Test
    void shouldFindCouponById() {

        String couponId = "123";

        CouponResponseDTO responseDTO = new CouponResponseDTO(
                couponId,
                "ABC123",
                "Cupom de desconto",
                BigDecimal.TEN,
                LocalDateTime.now().plusDays(10),
                CouponStatus.ACTIVE,
                true,
                false
        );

        when(findCouponByIdUseCase.findById(couponId))
                .thenReturn(responseDTO);

        ResponseEntity<CouponResponseDTO> response =
                couponController.getCouponById(couponId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals(couponId, response.getBody().id());
        assertEquals("ABC123", response.getBody().code());

        verify(findCouponByIdUseCase, times(1))
                .findById(couponId);
    }

    @Test
    void shouldCreateCoupon() {

        CouponRequestDTO requestDTO = new CouponRequestDTO(
                "ABC123",
                "Cupom de desconto",
                BigDecimal.TEN,
                LocalDateTime.now().plusDays(10),
                true
        );

        CouponResponseDTO responseDTO = new CouponResponseDTO(
                "123",
                "ABC123",
                "Cupom de desconto",
                BigDecimal.TEN,
                LocalDateTime.now().plusDays(10),
                CouponStatus.ACTIVE,
                true,
                false
        );

        when(createCouponUseCase.createCoupon(requestDTO))
                .thenReturn(responseDTO);

        ResponseEntity<CouponResponseDTO> response =
                couponController.createCoupon(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals("123", response.getBody().id());

        verify(createCouponUseCase, times(1))
                .createCoupon(requestDTO);
    }

    @Test
    void shouldDeleteCouponById() {

        String couponId = "123";

        doNothing().when(deleteCouponByIdUseCase)
                .deleteById(couponId);

        ResponseEntity<Void> response =
                couponController.deleteCouponById(couponId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(deleteCouponByIdUseCase, times(1))
                .deleteById(couponId);
    }
}