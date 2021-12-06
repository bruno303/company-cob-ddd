package com.bso.companycob.registration.exception;

import com.bso.companycob.domain.exception.BankNotFoundException;
import com.bso.companycob.domain.exception.ContractNotFoundException;
import com.bso.companycob.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class CompanyCobExceptionHandler {

    @ExceptionHandler(value = { BankNotFoundException.class, ContractNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public CompanyCobResponseError domainNotFoundException(Exception ex) {
        return new CompanyCobResponseError(LocalDate.now(), ex.getMessage());
    }

    @ExceptionHandler(value = { DomainException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CompanyCobResponseError domainException(DomainException ex) {
        return new CompanyCobResponseError(LocalDate.now(), ex.getMessage());
    }

}
