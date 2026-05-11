package com.test.coupon_api.exception;

import com.test.coupon_api.exception.model.CouponAlreadyDeletedException;
import com.test.coupon_api.exception.model.CouponNotFoundException;
import com.test.coupon_api.exception.model.InvalidCouponCodeException;
import com.test.coupon_api.exception.response.ErrorApiResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tools.jackson.databind.exc.InvalidFormatException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @Test
    void shouldHandleGenericException() {

        Exception exception = new Exception("Erro interno");

        var response =
                globalExceptionHandler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ErrorApiResponse body = response.getBody();

        assertNotNull(body);
        assertEquals("Erro interno", body.getMessage());
        assertEquals(500, body.getCode());
    }

    @Test
    void shouldHandleValidationException() {

        ValidationException exception =
                new ValidationException("Erro de validação");

        var response =
                globalExceptionHandler.handleValidationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorApiResponse body = response.getBody();

        assertNotNull(body);
        assertEquals("Erro de validação", body.getMessage());
        assertEquals(400, body.getCode());
    }

    @Test
    void shouldHandleCouponAlreadyDeletedException() {

        CouponAlreadyDeletedException exception =
                new CouponAlreadyDeletedException("Cupom já deletado");

        var response =
                globalExceptionHandler.handleCouponAlreadyDeletedException(exception);

        assertEquals(HttpStatus.UNPROCESSABLE_CONTENT, response.getStatusCode());

        ErrorApiResponse body = response.getBody();

        assertNotNull(body);
        assertEquals("Cupom já deletado", body.getMessage());
        assertEquals(422, body.getCode());
    }

    @Test
    void shouldHandleCouponNotFoundException() {

        CouponNotFoundException exception =
                new CouponNotFoundException("Cupom não encontrado");

        var response =
                globalExceptionHandler.handleCouponNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorApiResponse body = response.getBody();

        assertNotNull(body);
        assertEquals("Cupom não encontrado", body.getMessage());
        assertEquals(404, body.getCode());
    }

    @Test
    void shouldHandleInvalidCouponCodeException() {

        InvalidCouponCodeException exception =
                new InvalidCouponCodeException("Código inválido");

        var response =
                globalExceptionHandler.handleInvalidCouponCodeException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorApiResponse body = response.getBody();

        assertNotNull(body);
        assertEquals("Código inválido", body.getMessage());
        assertEquals(400, body.getCode());
    }

    @Test
    void shouldHandleConstraintViolationException() {

        ConstraintViolationException exception =
                new ConstraintViolationException("Erro constraint", null);

        var response =
                globalExceptionHandler.handleConstraintViolation(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorApiResponse body = response.getBody();

        assertNotNull(body);
        assertEquals("Erro constraint", body.getMessage());
        assertEquals(400, body.getCode());
    }

    @Test
    void shouldHandleMethodArgumentNotValidException() {

        FieldError fieldError = new FieldError(
                "couponRequestDTO",
                "code",
                "O código é obrigatório"
        );

        when(methodArgumentNotValidException.getBindingResult())
                .thenReturn(bindingResult);

        when(bindingResult.getFieldErrors())
                .thenReturn(List.of(fieldError));

        ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problemDetail.setDetail("Erro de validação");

        when(methodArgumentNotValidException.getBody())
                .thenReturn(problemDetail);

        var response =
                globalExceptionHandler.handleValidationExceptions(
                        methodArgumentNotValidException
                );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorApiResponse body = response.getBody();

        assertNotNull(body);

        assertEquals("Erro de validação", body.getMessage());
        assertEquals(400, body.getCode());

        assertNotNull(body.getErrors());
        assertEquals(
                "O código é obrigatório",
                body.getErrors().get("code")
        );
    }

    @Test
    void shouldHandleInvalidLocalDateTimeFormat() {

        InvalidFormatException invalidFormatException =
                new InvalidFormatException(
                        null,
                        "Formato inválido",
                        "2025/10/10",
                        LocalDateTime.class
                );

        HttpMessageNotReadableException exception =
                new HttpMessageNotReadableException(
                        "JSON inválido",
                        invalidFormatException,
                        mock(HttpInputMessage.class)
                );

        var response =
                globalExceptionHandler.handleInvalidFormat(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorApiResponse body = response.getBody();

        assertNotNull(body);

        assertEquals(
                "A data de expiração possui um formato inválido. Utilize o padrão: yyyy-MM-ddTHH:mm:ss",
                body.getMessage()
        );

        assertEquals(400, body.getCode());
    }

    @Test
    void shouldHandleGenericInvalidFormat() {

        HttpMessageNotReadableException exception =
                new HttpMessageNotReadableException(
                        "JSON inválido",
                        mock(HttpInputMessage.class)
                );

        var response =
                globalExceptionHandler.handleInvalidFormat(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorApiResponse body = response.getBody();

        assertNotNull(body);

        assertEquals(
                "O corpo da requisição possui campos inválidos",
                body.getMessage()
        );

        assertEquals(400, body.getCode());
    }
}