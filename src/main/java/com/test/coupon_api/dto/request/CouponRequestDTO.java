package com.test.coupon_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(
        name = "CouponRequestDTO",
        description = "Objeto utilizado para criação de um cupom"
)
public record CouponRequestDTO(

        @Schema(
                description = "Código do cupom",
                example = "ABC-123"
        )
        @NotBlank(message = "O código do cupom é obrigatório")
        String code,

        @Schema(
                description = "Descrição do cupom",
                example = "Cupom de desconto de 10%"
        )
        @NotBlank(message = "A descrição do cupom é obrigatória")
        String description,

        @Schema(
                description = "Valor do desconto",
                example = "10.5"
        )
        @NotNull(message = "O valor de desconto é obrigatório")
        @DecimalMin(value = "0.5", message = "O valor do cupom deve ser no mínimo 0.5")
        BigDecimal discountValue,

        @Schema(
                description = "Data de expiração do cupom",
                example = "2026-12-31T23:59:59"
        )
        @NotNull(message = "A data de expiração é obrigatória")
        @Future(message = "A data de expiração enviada não é uma data de expiração válida")
        LocalDateTime expirationDate,

        @Schema(
                description = "Define se o cupom será publicado",
                example = "false",
                defaultValue = "false"
        )
        Boolean published
) {
}
