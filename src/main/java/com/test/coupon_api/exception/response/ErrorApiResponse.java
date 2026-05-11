package com.test.coupon_api.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorApiResponse {
    @Schema(
            description = "Código HTTP",
            example = "400"
    )
    private Integer code;

    @Schema(
            description = "Mensagem principal do erro",
            example = "Erro de validação"
    )
    private String message;

    @Schema(
            description = "HashMap detalhado de campos e de seus respectivos erros",
            example = """
                {
                  "code": "O código do cupom é obrigatório",
                  "discountValue": "O valor mínimo é 0.5"
                }
                """
    )
    private Map<String, String> errors;

    @Schema(
            description = "Lista detalhada de erros",
            example = """
                    [
                       "O código do cupom é obrigatório"
                    ]
                    """
    )
    private List<String> validationErrors;

    @Schema(
            description = "Status HTTP retornado",
            example = "400 BAD REQUEST"
    )
    private HttpStatus status;

    @Schema(
            description = "Data/hora que o erro foi lançado",
            example = "2026-05-11T00:05:38.568Z"
    )
    private LocalDateTime timestamp;
}
