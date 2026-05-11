package com.test.coupon_api.model.mapper;

import com.test.coupon_api.dto.request.CouponRequestDTO;
import com.test.coupon_api.dto.response.CouponResponseDTO;
import com.test.coupon_api.exception.model.InvalidCouponCodeException;
import com.test.coupon_api.model.Coupon;
import com.test.coupon_api.model.enums.CouponStatus;
import org.springframework.stereotype.Component;

import static com.test.coupon_api.utils.CouponUtils.sanitizeCode;

@Component
public class CouponMapper {
    private CouponMapper() {
        /* This utility class should not be instantiated */
    }

    public static Coupon toEntity(CouponRequestDTO dto) {

        String sanitizedCode =
                sanitizeCode(dto.code());

        if (sanitizedCode.length() != 6) {
            throw new InvalidCouponCodeException("Campo 'code' invalido. O campo deve ter 6 caracteres, relevando caracteres especiais");
        }

        return Coupon.builder()
                .code(sanitizedCode)
                .description(dto.description())
                .discountValue(dto.discountValue())
                .expirationDate(dto.expirationDate())
                .status(CouponStatus.ACTIVE)
                .published(Boolean.TRUE.equals(dto.published()))
                .redeemed(false)
                .build();
    }

    public static CouponResponseDTO toResponse(Coupon coupon) {

        return new CouponResponseDTO(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDescription(),
                coupon.getDiscountValue(),
                coupon.getExpirationDate(),
                coupon.getStatus(),
                coupon.isPublished(),
                coupon.isRedeemed()
        );
    }
}
