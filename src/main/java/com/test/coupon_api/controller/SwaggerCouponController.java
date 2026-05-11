package com.test.coupon_api.controller;

import com.test.coupon_api.dto.request.CouponRequestDTO;
import com.test.coupon_api.dto.response.CouponResponseDTO;
import com.test.coupon_api.exception.response.ErrorApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
        name = "Coupons",
        description = "Endpoints responsáveis pelo gerenciamento de cupons"
)
public interface SwaggerCouponController {
    @Operation(
            summary = "Buscar cupom por ID",
            description = "Retorna os dados de um cupom a partir do ID informado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupom encontrado"),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorApiResponse.class
                        )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorApiResponse.class
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Erro genérico da API",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorApiResponse.class
                            )
                    )
            )
    })
    ResponseEntity<CouponResponseDTO> getCouponById(
            @Parameter(
                    description = "ID do cupom",
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable @NotBlank(message = "O ID do cupom é obrigatório") String id
    );

    @Operation(
            summary = "Criar um novo cupom",
            description = "Cria um novo cupom no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cupom criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorApiResponse.class
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Erro genérico da API",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorApiResponse.class
                            )
                    )
            )
    })
    ResponseEntity<CouponResponseDTO> createCoupon(
            @RequestBody @Valid CouponRequestDTO coupon
    );

    @Operation(
            summary = "Remover cupom",
            description = "Remove um cupom do sistema a partir do ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cupom removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorApiResponse.class
                            )
                    )
            ),
            @ApiResponse(responseCode = "422", description = "O cupom com o ID enviado ja esta deletado!",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorApiResponse.class
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Erro genérico da API",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorApiResponse.class
                            )
                    )
            )
    })
    ResponseEntity<Void> deleteCouponById(
            @Parameter(
                    description = "ID do cupom",
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable @NotBlank(message = "O ID do cupom é obrigatório") String id
    );
}
