package com.example.pbl7_backend.exception;

import com.example.pbl7_backend.dto.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleSecurityException(HttpServletRequest req, Exception e) {
        String title = "Unknown internal server error.";
        String path = req.getRequestURI();
        int status = 500;
        String errorMessage = e.getMessage();
        Map<String, String> validationErrors = new HashMap<>();

        // User Credentials
        if (e instanceof UsernameExistedException) {
            title = HttpStatus.BAD_REQUEST.getReasonPhrase();
            status = HttpStatus.BAD_REQUEST.value();
            errorMessage = "Username has already been used";
        }

        if (e instanceof BadCredentialsException) {
            title = HttpStatus.BAD_REQUEST.getReasonPhrase();
            status = HttpStatus.BAD_REQUEST.value();
            errorMessage = "Incorrect username or password";
        }

        if (e instanceof AccountStatusException) {
            title = HttpStatus.FORBIDDEN.getReasonPhrase();
            status = HttpStatus.FORBIDDEN.value();
            errorMessage = "Your account is locked";
        }
        if (e instanceof AccessDeniedException) {
            title = HttpStatus.FORBIDDEN.getReasonPhrase();
            status = HttpStatus.FORBIDDEN.value();
            errorMessage = "You are not authorized to access this resource";
        }

        // JWT
        if (e instanceof SignatureException) {
            title = HttpStatus.FORBIDDEN.getReasonPhrase();
            status = HttpStatus.FORBIDDEN.value();
            errorMessage = "The JWT signature is invalid";
        }

        if (e instanceof ExpiredJwtException) {
            title = HttpStatus.FORBIDDEN.getReasonPhrase();
            status = HttpStatus.FORBIDDEN.value();
            errorMessage = "The JWT has expired";
        }

        // Validation
        if (e instanceof MethodArgumentNotValidException) {
            title = HttpStatus.FORBIDDEN.getReasonPhrase();
            status = HttpStatus.FORBIDDEN.value();
            errorMessage = "Validation failed";
            for (FieldError fieldError : ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors()) {
                validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }

        ErrorResponse resBody = new ErrorResponse(title, status, validationErrors, errorMessage, path);
        return ResponseEntity
                .status(status)
                .body(resBody);
    }
}
