package com.bso.companycob.domain.repositories;

import com.bso.companycob.domain.entity.contract.Contract;

import java.util.Optional;
import java.util.UUID;

public interface ContractRepository extends Repository<Contract> {

    Optional<Contract> findByNumber(String number);

}