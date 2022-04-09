package com.bso.companycob.application.core.handlers.query;

import com.bso.companycob.application.core.bus.request.ContractGetAllRequest;
import com.bso.companycob.application.core.bus.response.ContractGetAllResponse;
import com.bso.companycob.application.core.handlers.requests.ContractGetAllRequestHandler;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.entity.contract.Quota;
import com.bso.companycob.domain.entity.contract.QuotaCollection;
import com.bso.companycob.domain.repositories.ContractReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ContractGetAllRequestHandlerTest {

    private ContractGetAllRequestHandler handler;
    private final ContractReaderRepository contractReaderRepositoryMock = Mockito.mock(ContractReaderRepository.class);

    @BeforeEach
    public void setup() {
        handler = new ContractGetAllRequestHandler(contractReaderRepositoryMock);
    }

    @Test
    public void testGetAllCallFindAllMethodFromRepository() {
        var contract = Mockito.mock(Contract.class);
        var quotaCollection = Mockito.mock(QuotaCollection.class);
        var quota = Mockito.mock(Quota.class);

        when(contract.getQuotas()).thenReturn(quotaCollection);
        when(quotaCollection.getQuotas()).thenReturn(List.of(quota));

        when(contractReaderRepositoryMock.findAll()).thenReturn(List.of(contract));
        List<ContractGetAllResponse> all = handler.handle(new ContractGetAllRequest());
        assertThat(all).hasSize(1);

        verify(contractReaderRepositoryMock, times(1)).findAll();
    }
}