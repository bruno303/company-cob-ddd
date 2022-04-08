package com.bso.companycob.application.model.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public interface AsyncRunner {
    CompletableFuture<Void> run(Runnable runnable);
    CompletableFuture<Void> run(Runnable runnable, Executor executor);
    <T> CompletableFuture<T> run(Supplier<T> supplier);
    <T> CompletableFuture<T> run(Supplier<T> supplier, Executor executor);
}
