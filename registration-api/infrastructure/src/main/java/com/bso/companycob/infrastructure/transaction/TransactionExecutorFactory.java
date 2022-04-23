package com.bso.companycob.infrastructure.transaction;

import com.bso.companycob.application.transaction.TransactionConfiguration;
import com.bso.companycob.application.transaction.TransactionExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.security.InvalidParameterException;

@Component
@RequiredArgsConstructor
public class TransactionExecutorFactory {

    private final PlatformTransactionManager platformTransactionManager;

    public TransactionExecutor createInstance() {
        return createInstance(TransactionConfiguration.getDefault());
    }

    public TransactionExecutor createInstance(TransactionConfiguration configuration) {
        var template = new TransactionTemplate(platformTransactionManager);
        template.setPropagationBehavior(toSpringPropagation(configuration.getPropagation()));
        template.setReadOnly(configuration.isReadOnly());
        template.setTimeout(configuration.getTimeout());
        template.setIsolationLevel(toSpringIsolation(configuration.getIsolation()));
        return new TransactionExecutorSpringAdapter(template);
    }

    private int toSpringPropagation(TransactionConfiguration.Propagation propagation) {
        switch (propagation) {
            case REQUIRED:
                return TransactionDefinition.PROPAGATION_REQUIRED;
            case REQUIRES_NEW:
                return TransactionDefinition.PROPAGATION_REQUIRES_NEW;
            case SUPPORTS:
                return TransactionDefinition.PROPAGATION_SUPPORTS;
            default:
                throw new InvalidParameterException("Could not find an equivalent transaction propagation for " + propagation);
        }
    }

    private int toSpringIsolation(TransactionConfiguration.Isolation isolation) {
        switch (isolation) {
            case DEFAULT:
                return TransactionDefinition.ISOLATION_DEFAULT;
            case READ_COMMITTED:
                return TransactionDefinition.ISOLATION_READ_COMMITTED;
            case READ_UNCOMMITTED:
                return TransactionDefinition.ISOLATION_READ_UNCOMMITTED;
            default:
                throw new InvalidParameterException("Could not find an equivalent transaction isolation for " + isolation);
        }
    }
}
