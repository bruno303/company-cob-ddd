package com.bso.companycob.registration.exception;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CompanyCobResponseError {
    private final LocalDate date;
    private final String message;
}
