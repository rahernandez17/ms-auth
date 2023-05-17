package com.example.auth.exceptions;

import com.example.auth.responses.SimpleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdviceHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public SimpleResponse<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        return SimpleResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .path(request.getContextPath())
                .build();
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<SimpleResponse<Object>> handleInternalErrorException(InternalErrorException ex, WebRequest request) {
        return new ResponseEntity<>(SimpleResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .path(request.getContextPath())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomFeignClientException.class)
    public ResponseEntity<SimpleResponse<Object>> handleCustomFeignClientException(CustomFeignClientException ex, WebRequest request) {
        return new ResponseEntity<>(SimpleResponse.builder()
                .code(ex.getResponse().status())
                .message(ex.getMessage())
                .path(request.getContextPath())
                .build(), HttpStatus.valueOf(ex.getResponse().status()));
    }
}
