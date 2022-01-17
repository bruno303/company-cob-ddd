package com.bso.companycob.infrastructure.async;

import com.bso.companycob.application.model.async.AsyncRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Component
public class CompletableFutureAsyncRunner implements AsyncRunner {

    @Override
    public CompletableFuture<Void> run(Runnable runnable) {
        return CompletableFuture.runAsync(runnable);
    }

    @Override
    public <T> CompletableFuture<T> run(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier);
    }

}
