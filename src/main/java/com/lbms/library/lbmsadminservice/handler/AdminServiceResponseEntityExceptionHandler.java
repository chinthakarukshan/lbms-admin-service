package com.lbms.library.lbmsadminservice.handler;

import com.lbms.library.core.dto.ErrorResponse;
import com.lbms.library.core.dto.ValidationErrorResponse;
import com.lbms.library.core.exception.LBMSException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class AdminServiceResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errorMessages =  ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        errorResponse.setErrorCode("VALERROR-0001");
        errorResponse.setErrorMessages(errorMessages);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {LBMSException.class})
    protected ResponseEntity<Object> handleLBMS(LBMSException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ex.getThrowableError().getCode());
        errorResponse.setErrorMessage(ex.getThrowableError().getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
}
