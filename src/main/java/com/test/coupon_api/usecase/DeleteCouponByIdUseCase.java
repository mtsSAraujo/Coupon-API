package com.test.coupon_api.usecase;

import com.test.coupon_api.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCouponByIdUseCase {
    private final CouponService couponService;

    public void deleteById(String id) {
        couponService.deleteCouponById(id);
    }
}
