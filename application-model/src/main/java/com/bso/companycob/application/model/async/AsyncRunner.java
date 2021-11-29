package com.bso.companycob.application.model.async;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public interface AsyncRunner {
    CompletableFuture<Void> run(Runnable runnable);
    <T> CompletableFuture<T> run(Supplier<T> supplier);
}
