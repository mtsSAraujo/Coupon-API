package com.test.coupon_api.model;

import com.test.coupon_api.model.enums.CouponStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    private String code; // Valor padrao com 6 caracteres e sem caracteres especiais

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    @DecimalMin(value = "0.5", message = "O valor do cupom deve ser no mínimo 0.5")
    BigDecimal discountValue;

    @NotNull
    @Future(message = "A data de expiração enviada não é uma data de expiração válida")
    LocalDateTime expirationDate; // Nao pode ser uma data anterior a atual

    @NonNull
    private CouponStatus status;

    @Builder.Default
    private boolean published = false;

    @Builder.Default
    private boolean redeemed = false;
}
