package com.bso.companycob.infrastructure.mapper;

import com.bso.companycob.domain.enums.QuotaStatus;
import com.bso.companycob.infrastructure.entities.Quota;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QuotaMapper {

    public com.bso.companycob.domain.entity.contract.Quota toDomainQuota(Quota quota) {
        return new com.bso.companycob.domain.entity.contract.Quota(quota.getId(), quota.getNumber(), quota.getAmount(),
                quota.getUpdatedAmount(), quota.getDate(), QuotaStatus.fromValue(quota.getStatus()), quota.getDateUpdated());
    }

    public Quota toPersistenceQuota(com.bso.companycob.domain.entity.contract.Quota quota, UUID contractId) {
        var persistenceQuota = new Quota();
        persistenceQuota.setId(quota.getId());
        persistenceQuota.setNumber(quota.getNumber());
        persistenceQuota.setAmount(quota.getAmount());
        persistenceQuota.setUpdatedAmount(quota.getUpdatedAmount());
        persistenceQuota.setDate(quota.getDate());
        persistenceQuota.setStatus(quota.getStatus().getValue());
        persistenceQuota.setDateUpdated(quota.getDateUpdated());
        persistenceQuota.setContractId(contractId);

        return persistenceQuota;
    }

}
