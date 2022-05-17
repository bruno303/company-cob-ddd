package com.bso.companycob.infrastructure.async;

import com.bso.companycob.application.async.AsyncRunner;
import com.bso.companycob.application.async.CompletableFutureAsyncRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsyncRunnerConfig {

    @Bean
    public AsyncRunner asyncRunner() {
        return new CompletableFutureAsyncRunner();
    }
}
