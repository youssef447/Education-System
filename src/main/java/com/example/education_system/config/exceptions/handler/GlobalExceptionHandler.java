package com.example.education_system.config.exceptions.handler;

import com.example.education_system.config.exceptions.classes.*;
import com.example.education_system.config.response.ApiResponseBody;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import org.apache.http.HttpStatus;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleValidationErrors(BindException ex) {
        int statusCode = HttpStatus.SC_BAD_REQUEST;

        String errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ApiResponseBody response = new ApiResponseBody(errorMessages, false);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityErrors(DataIntegrityViolationException ex) {
        int statusCode = HttpStatus.SC_BAD_REQUEST;
        ApiResponseBody response = new ApiResponseBody(ex.getMessage(), false);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameErrors(UsernameNotFoundException ex) {
        int statusCode = HttpStatus.SC_NOT_FOUND;
        ApiResponseBody response = new ApiResponseBody(ex.getMessage(), false);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        int statusCode = HttpStatus.SC_BAD_REQUEST;
        ApiResponseBody response = new ApiResponseBody(ex.getMessage(), false);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleUsernameAlreadyExistErrors(UsernameAlreadyExistsException ex) {
        int statusCode = HttpStatus.SC_CONFLICT;
        ApiResponseBody response = new ApiResponseBody(ex.getMessage(), false);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<?> handleCategoryAlreadyExistErrors(CategoryAlreadyExistsException ex) {
        int statusCode = HttpStatus.SC_CONFLICT;
        ApiResponseBody response = new ApiResponseBody(ex.getMessage(), false);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(RegisteredAlreadyException.class)
    public ResponseEntity<?> handleRegisteredAlreadyErrors(RegisteredAlreadyException ex) {
        int statusCode = HttpStatus.SC_CONFLICT;
        ApiResponseBody response = new ApiResponseBody(ex.getMessage(), false);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(NoEventFoundException.class)
    public ResponseEntity<?> handleNoEventErrors(NoEventFoundException ex) {
        int statusCode = HttpStatus.SC_NOT_FOUND;
        ApiResponseBody response = new ApiResponseBody(ex.getMessage(), false);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(CouponAlreadyExistsException.class)
    public ResponseEntity<?> handleCouponAlreadyExistsErrors(CouponAlreadyExistsException ex) {
        int statusCode = HttpStatus.SC_CONFLICT;
        ApiResponseBody response = new ApiResponseBody(ex.getMessage(), false);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(NoReminderFoundException.class)
    public ResponseEntity<?> handleNoReminderErrors(NoReminderFoundException ex) {
        int statusCode = HttpStatus.SC_NOT_FOUND;
        ApiResponseBody response = new ApiResponseBody(ex.getMessage(), false);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(CouponAlreadySentToMail.class)
    public ResponseEntity<?> handleNoCouponAlreadySentToMailErrors(CouponAlreadySentToMail ex) {
        int statusCode = HttpStatus.SC_BAD_REQUEST;
        ApiResponseBody response = new ApiResponseBody(ex.getMessage(), false);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(CouponAlreadyRedeemed.class)
    public ResponseEntity<?> handleNoCouponAlreadyRedeemedErrors(CouponAlreadyRedeemed ex) {
        int statusCode = HttpStatus.SC_BAD_REQUEST;
        ApiResponseBody response = new ApiResponseBody(ex.getMessage(), false);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(CouponNotFound.class)
    public ResponseEntity<?> handleNoCouponNotFoundErrors(CouponNotFound ex) {
        int statusCode = HttpStatus.SC_BAD_REQUEST;
        ApiResponseBody response = new ApiResponseBody(ex.getMessage(), false);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(CouponWelcomeNotActive.class)
    public ResponseEntity<?> handleCouponWelcomeNotActive(CouponWelcomeNotActive ex) {
        int statusCode = HttpStatus.SC_BAD_REQUEST;
        ApiResponseBody response = new ApiResponseBody(ex.getMessage(), false);
        return ResponseEntity.status(statusCode).body(response);
    } @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentErrors(IllegalArgumentException ex) {
        int statusCode = HttpStatus.SC_BAD_REQUEST;
        ApiResponseBody response = new ApiResponseBody(ex.getMessage(), false);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<?> handleStripeExceptionErrors(StripeException ex) {
        int statusCode = HttpStatus.SC_BAD_REQUEST;
        ApiResponseBody response = new ApiResponseBody(ex.getMessage(), false);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(SignatureVerificationException.class)
    public ResponseEntity<?> handleSignatureVerificationErrors(SignatureVerificationException ex) {
        int statusCode = HttpStatus.SC_BAD_REQUEST;
        ApiResponseBody response = new ApiResponseBody(ex.getMessage(), false);
        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied() {
        int statusCode = HttpStatus.SC_FORBIDDEN;

        return ResponseEntity.status(statusCode)
                .body("You're not allowed to access this resource.");
    }
}