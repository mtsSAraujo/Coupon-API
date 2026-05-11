package com.test.coupon_api.dto.response;

import com.test.coupon_api.model.enums.CouponStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(
        name = "CouponResponseDTO",
        description = "Objeto retornado pela API contendo os dados do cupom"
)
public record CouponResponseDTO(
        @Schema(
                description = "ID do cupom",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        String id,

        @Schema(
                description = "Código do cupom",
                example = "ABC123"
        )
        String code,

        @Schema(
                description = "Descrição do cupom",
                example = "Cupom de desconto de 10%"
        )
        String description,

        @Schema(
                description = "Valor do desconto",
                example = "10.5"
        )
        BigDecimal discountValue,

        @Schema(
                description = "Data de expiração",
                example = "2026-12-31T23:59:59"
        )
        LocalDateTime expirationDate,

        @Schema(
                description = "Status atual do cupom. Pode ser: ACTIVE, INACTIVE ou DELETED",
                example = "ACTIVE"
        )
        CouponStatus status,

        @Schema(
                description = "Indica se o cupom está publicado",
                example = "false"
        )
        boolean published,

        @Schema(
                description = "Indica se o cupom já foi resgatado",
                example = "false"
        )
        boolean redeemed
) {
}
