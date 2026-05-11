package com.test.coupon_api.exception;

import com.test.coupon_api.exception.model.CouponAlreadyDeletedException;
import com.test.coupon_api.exception.model.CouponNotFoundException;
import com.test.coupon_api.exception.model.InvalidCouponCodeException;
import com.test.coupon_api.exception.response.ErrorApiResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import tools.jackson.databind.exc.InvalidFormatException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApiResponse> handleGenericException(Exception exception) {
        ErrorApiResponse errorApiResponse = buildErrorApiResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorApiResponse);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorApiResponse> handleValidationException(HandlerMethodValidationException exception) {
        List<String> errors = exception.getAllErrors()
                .stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .toList();

        ErrorApiResponse response = buildErrorApiResponse("Erro de validação", HttpStatus.BAD_REQUEST);

        response.setValidationErrors(errors);

        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorApiResponse> handleValidationException(ValidationException exception) {
        ErrorApiResponse errorApiResponse = buildErrorApiResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);

        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorApiResponse);
    }

    @ExceptionHandler(CouponAlreadyDeletedException.class)
    public ResponseEntity<ErrorApiResponse> handleCouponAlreadyDeletedException(CouponAlreadyDeletedException exception) {
        ErrorApiResponse errorApiResponse = buildErrorApiResponse(exception.getMessage(), HttpStatus.UNPROCESSABLE_CONTENT);

        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(errorApiResponse);
    }

    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<ErrorApiResponse> handleCouponNotFoundException(CouponNotFoundException exception) {
        ErrorApiResponse errorApiResponse = buildErrorApiResponse(exception.getMessage(), HttpStatus.NOT_FOUND);

        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorApiResponse);
    }

    @ExceptionHandler(InvalidCouponCodeException.class)
    public ResponseEntity<ErrorApiResponse> handleInvalidCouponCodeException(InvalidCouponCodeException exception) {
        ErrorApiResponse errorApiResponse = buildErrorApiResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);

        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorApiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorApiResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        ErrorApiResponse errorApiResponse = buildErrorApiResponse(exception.getBody().getDetail(), HttpStatus.BAD_REQUEST);
        errorApiResponse.setErrors(errors);

        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorApiResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorApiResponse> handleConstraintViolation(ConstraintViolationException exception) {
        ErrorApiResponse errorApiResponse = buildErrorApiResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);

        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorApiResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorApiResponse> handleInvalidFormat(
            HttpMessageNotReadableException exception
    ) {

        String message = "O corpo da requisição possui campos inválidos";

        Throwable cause = exception.getCause();

        if (cause instanceof InvalidFormatException invalidFormat && invalidFormat.getTargetType()
                    .equals(LocalDateTime.class)) {

                message = "A data de expiração possui um formato inválido. Utilize o padrão: yyyy-MM-ddTHH:mm:ss";
            }

        ErrorApiResponse errorApiResponse = buildErrorApiResponse(message, HttpStatus.BAD_REQUEST);

        log.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorApiResponse);
    }

    private static ErrorApiResponse buildErrorApiResponse(String message, HttpStatus status) {
        ErrorApiResponse errorApiResponse = new ErrorApiResponse();

        errorApiResponse.setMessage(message);
        errorApiResponse.setCode(status.value());
        errorApiResponse.setStatus(status);
        errorApiResponse.setTimestamp(LocalDateTime.now());

        return errorApiResponse;
    }
}
