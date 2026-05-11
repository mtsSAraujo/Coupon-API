package com.test.coupon_api.controller;

import com.test.coupon_api.dto.request.CouponRequestDTO;
import com.test.coupon_api.dto.response.CouponResponseDTO;
import com.test.coupon_api.usecase.CreateCouponUseCase;
import com.test.coupon_api.usecase.DeleteCouponByIdUseCase;
import com.test.coupon_api.usecase.FindCouponByIdUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController implements SwaggerCouponController {

    private final CreateCouponUseCase createCouponUseCase;
    private final DeleteCouponByIdUseCase deleteCouponByIdUseCase;
    private final FindCouponByIdUseCase findCouponByIdUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> getCouponById(
            @PathVariable @NotBlank(message = "O ID do cupom é obrigatório") String id){
        return ResponseEntity.ok(findCouponByIdUseCase.findById(id));
    }

    @PostMapping
    public ResponseEntity<CouponResponseDTO> createCoupon(
            @RequestBody @Valid CouponRequestDTO coupon) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createCouponUseCase.createCoupon(coupon));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCouponById(
            @PathVariable @NotBlank(message = "O ID do cupom é obrigatório") String id) {
        deleteCouponByIdUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
