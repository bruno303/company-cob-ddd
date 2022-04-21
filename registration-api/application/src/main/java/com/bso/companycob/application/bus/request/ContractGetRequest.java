package com.bso.companycob.application.bus.request;

import com.bso.companycob.application.bus.response.ContractGetResponse;
import com.bso.dracko.mediator.contract.Request;
import lombok.Data;

import java.util.Optional;
import java.util.UUID;

@Data
public class ContractGetRequest implements Request<Optional<ContractGetResponse>> {
    private final UUID id;
}
