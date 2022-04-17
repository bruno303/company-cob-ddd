package com.bso.companycob.application.model.bus.request;

import com.bso.companycob.application.model.bus.response.ContractCreationResponse;
import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.enums.QuotaStatus;
import com.bso.dracko.mediator.contract.Request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ContractCreationRequest implements Request<ContractCreationResponse> {
    private final String number;
    private final LocalDate date;
    private final UUID bankId;
    private final List<QuotaData> quotas;
    private final CalcType calcType;

    public ContractCreationRequest(String number, LocalDate date, UUID bankId, List<QuotaData> quotas, CalcType calcType) {
        this.number = number;
        this.date = date;
        this.bankId = bankId;
        this.quotas = quotas;
        this.calcType = calcType;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getDate() {
        return date;
    }

    public UUID getBankId() {
        return bankId;
    }

    public List<QuotaData> getQuotas() {
        return quotas;
    }

    public CalcType getCalcType() {
        return calcType;
    }

    public static class QuotaData {
        private final int number;
        private final BigDecimal amount;
        private final LocalDate date;
        private final QuotaStatus status;

        public QuotaData(int number, BigDecimal amount, LocalDate date, QuotaStatus status) {
            this.number = number;
            this.amount = amount;
            this.date = date;
            this.status = status;
        }

        public int getNumber() {
            return number;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public LocalDate getDate() {
            return date;
        }

        public QuotaStatus getStatus() {
            return status;
        }
    }
}
