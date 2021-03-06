package com.bso.companycob.application.bus.handler;

import com.bso.companycob.application.bus.request.ContractGetAllRequest;
import com.bso.companycob.application.bus.response.ContractGetAllResponse;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.entity.contract.Quota;
import com.bso.companycob.domain.entity.contract.QuotaCollection;
import com.bso.companycob.domain.repositories.ContractReaderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class ContractGetAllRequestHandlerTest {

    private ContractGetAllRequestHandler handler;
    private final ContractReaderRepository contractReaderRepositoryMock = Mockito.mock(ContractReaderRepository.class);

    @BeforeEach
    public void setup() {
        handler = new ContractGetAllRequestHandler(contractReaderRepositoryMock);
    }

    @Test
    void testGetAllCallFindAllMethodFromRepository() {
        var contract = Mockito.mock(Contract.class);
        var quotaCollection = Mockito.mock(QuotaCollection.class);
        var quota = Mockito.mock(Quota.class);

        Mockito.when(contract.getQuotas()).thenReturn(quotaCollection);
        Mockito.when(quotaCollection.getQuotas()).thenReturn(List.of(quota));

        Mockito.when(contractReaderRepositoryMock.findAll()).thenReturn(List.of(contract));
        List<ContractGetAllResponse> all = handler.handle(new ContractGetAllRequest());
        Assertions.assertThat(all).hasSize(1);

        Mockito.verify(contractReaderRepositoryMock, Mockito.times(1)).findAll();
    }
}
