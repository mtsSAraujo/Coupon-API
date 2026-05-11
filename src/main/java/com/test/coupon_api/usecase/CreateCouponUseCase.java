package com.test.coupon_api.usecase;

import com.test.coupon_api.dto.request.CouponRequestDTO;
import com.test.coupon_api.dto.response.CouponResponseDTO;
import com.test.coupon_api.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.test.coupon_api.model.mapper.CouponMapper.toResponse;

@Service
@RequiredArgsConstructor
public class CreateCouponUseCase {

    private final CouponService couponService;

    public CouponResponseDTO createCoupon(CouponRequestDTO couponRequestDTO) {
        return toResponse(couponService.createCoupon(couponRequestDTO));
    }
}
