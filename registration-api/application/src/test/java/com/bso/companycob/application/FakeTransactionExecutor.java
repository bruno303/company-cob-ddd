package com.bso.companycob.application;

import com.bso.companycob.application.function.Callable;
import com.bso.companycob.application.transaction.TransactionExecutor;

public class FakeTransactionExecutor implements TransactionExecutor {

    @Override
    public <T> T execute(Callable<T> callable) {
        return callable.call();
    }

    @Override
    public void executeWithoutResult(Runnable runnable) {
        runnable.run();
    }
}
