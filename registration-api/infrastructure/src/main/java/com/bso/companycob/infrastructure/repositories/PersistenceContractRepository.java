package com.bso.companycob.infrastructure.repositories;

import com.bso.companycob.infrastructure.entities.Contract;

import java.util.Optional;

public interface PersistenceContractRepository extends PersistenceRepository<Contract> {

    Optional<Contract> findByNumber(String number);

}
