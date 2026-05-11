package com.test.coupon_api.service;

import com.test.coupon_api.dto.request.CouponRequestDTO;
import com.test.coupon_api.exception.model.CouponAlreadyDeletedException;
import com.test.coupon_api.exception.model.CouponNotFoundException;
import com.test.coupon_api.model.Coupon;
import com.test.coupon_api.model.enums.CouponStatus;
import com.test.coupon_api.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.test.coupon_api.model.mapper.CouponMapper.toEntity;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public Coupon createCoupon(CouponRequestDTO couponDto) {
        return couponRepository.save(toEntity(couponDto));
    }

    public Coupon findCouponById(String id) {
        return couponRepository.findById(id).orElseThrow(
                    () -> new CouponNotFoundException("Cupom não encontrado. Verifique o ID enviado")
                );
    }

    public Coupon deleteCouponById(String id) {
        Coupon coupon = findCouponById(id);
        if(coupon.getStatus() == CouponStatus.DELETED) {
            throw new CouponAlreadyDeletedException(String.format("O cupom de ID: %s ja esta deletado!", id));
        }
        coupon.setStatus(CouponStatus.DELETED);

        return couponRepository.save(coupon);
    }
}
