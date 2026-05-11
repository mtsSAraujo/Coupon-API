package com.test.coupon_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test.coupon_api.dto.request.CouponRequestDTO;
import com.test.coupon_api.dto.response.CouponResponseDTO;
import com.test.coupon_api.model.enums.CouponStatus;
import com.test.coupon_api.usecase.CreateCouponUseCase;
import com.test.coupon_api.usecase.DeleteCouponByIdUseCase;
import com.test.coupon_api.usecase.FindCouponByIdUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = CouponController.class
)
class CouponControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @MockitoBean
    private CreateCouponUseCase createCouponUseCase;

    @MockitoBean
    private DeleteCouponByIdUseCase deleteCouponByIdUseCase;

    @MockitoBean
    private FindCouponByIdUseCase findCouponByIdUseCase;

    @Test
    void shouldFindCouponById() throws Exception {

        String couponId = "123";

        CouponResponseDTO responseDTO = new CouponResponseDTO(
                couponId,
                "ABC123",
                "Cupom desconto",
                BigDecimal.TEN,
                LocalDateTime.now().plusDays(10),
                CouponStatus.ACTIVE,
                true,
                false
        );

        when(findCouponByIdUseCase.findById(couponId))
                .thenReturn(responseDTO);

        mockMvc.perform(get("/coupon/{id}", couponId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(couponId))
                .andExpect(jsonPath("$.code").value("ABC123"))
                .andExpect(jsonPath("$.description").value("Cupom desconto"));
    }

    @Test
    void shouldCreateCoupon() throws Exception {

        CouponRequestDTO requestDTO = new CouponRequestDTO(
                "ABC123",
                "Cupom desconto",
                BigDecimal.TEN,
                LocalDateTime.now().plusDays(10),
                true
        );

        CouponResponseDTO responseDTO = new CouponResponseDTO(
                "123",
                "ABC123",
                "Cupom desconto",
                BigDecimal.TEN,
                LocalDateTime.now().plusDays(10),
                CouponStatus.ACTIVE,
                true,
                false
        );

        when(createCouponUseCase.createCoupon(any(CouponRequestDTO.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.code").value("ABC123"));
    }

    @Test
    void shouldDeleteCouponById() throws Exception {

        String couponId = "123";

        doNothing().when(deleteCouponByIdUseCase)
                .deleteById(couponId);

        mockMvc.perform(delete("/coupon/{id}", couponId))
                .andExpect(status().isNoContent());

        verify(deleteCouponByIdUseCase, times(1))
                .deleteById(couponId);
    }

    @Test
    void shouldReturn400WhenIdIsBlank() throws Exception {

        mockMvc.perform(get("/coupon/ "))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenCodeIsBlank() throws Exception {

        CouponRequestDTO requestDTO = new CouponRequestDTO(
                "",
                "Descricao",
                BigDecimal.TEN,
                LocalDateTime.now().plusDays(10),
                true
        );

        mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenDiscountValueIsLessThanMinimum() throws Exception {

        CouponRequestDTO requestDTO = new CouponRequestDTO(
                "ABC123",
                "Descricao",
                new BigDecimal("0.1"),
                LocalDateTime.now().plusDays(10),
                true
        );

        mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenExpirationDateIsPast() throws Exception {

        CouponRequestDTO requestDTO = new CouponRequestDTO(
                "ABC123",
                "Descricao",
                BigDecimal.TEN,
                LocalDateTime.now().minusDays(1),
                true
        );

        mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

}