package com.bso.companycob.domain.exception;

public class ContractAlreadyExistsException extends DomainException {
    private final String number;

    public ContractAlreadyExistsException(String number) {
        super(String.format("Contract with number '%s' already exists.", number));
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public static void throwsWhen(boolean condition, String number) {
        if (condition) throw new ContractAlreadyExistsException(number);
    }
}
