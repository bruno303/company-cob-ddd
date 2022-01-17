package com.bso.companycob.infrastructure.repositories;

import com.bso.companycob.infrastructure.entities.Quota;

import java.util.List;
import java.util.UUID;

public interface PersistenceQuotaRepository extends PersistenceRepository<Quota> {

    List<Quota> findByContractId(UUID contractId);

}
