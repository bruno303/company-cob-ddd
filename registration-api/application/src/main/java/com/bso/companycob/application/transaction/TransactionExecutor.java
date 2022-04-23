package com.bso.companycob.application.transaction;

import com.bso.companycob.application.function.Callable;

public interface TransactionExecutor {

    <T> T execute(Callable<T> callable);
    void executeWithoutResult(Runnable runnable);
}
