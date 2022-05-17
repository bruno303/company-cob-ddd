package com.bso.companycob.infrastructure.transaction;

import com.bso.companycob.application.function.Callable;
import com.bso.companycob.application.transaction.TransactionExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.support.TransactionTemplate;

@RequiredArgsConstructor
public class TransactionExecutorSpringAdapter implements TransactionExecutor {

    private final TransactionTemplate transactionTemplate;

    @Override
    public <T> T execute(Callable<T> callable) {
        return transactionTemplate.execute(status -> callable.call());
    }

    @Override
    public void executeWithoutResult(Runnable runnable) {
        transactionTemplate.executeWithoutResult(status -> runnable.run());
    }
}
