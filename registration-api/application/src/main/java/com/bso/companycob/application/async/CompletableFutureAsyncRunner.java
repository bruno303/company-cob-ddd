package com.bso.companycob.application.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class CompletableFutureAsyncRunner implements AsyncRunner {

    @Override
    public CompletableFuture<Void> run(Runnable runnable) {
        return CompletableFuture.runAsync(runnable);
    }

    @Override
    public CompletableFuture<Void> run(Runnable runnable, Executor executor) {
        return CompletableFuture.runAsync(runnable, executor);
    }

    @Override
    public <T> CompletableFuture<T> run(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier);
    }

    @Override
    public <T> CompletableFuture<T> run(Supplier<T> supplier, Executor executor) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }
}
