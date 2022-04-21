package com.bso.companycob.infrastructure.lock;

import com.bso.companycob.application.async.AsyncRunner;
import com.bso.companycob.application.async.CompletableFutureAsyncRunner;
import com.bso.companycob.application.lock.Lockeable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryLockManagerTest {

    private InMemoryLockManager lockManager;
    private final Lockeable xpto = () -> "XPTO";
    private final AsyncRunner asyncRunner = new CompletableFutureAsyncRunner();

    @BeforeEach
    public void setup() {
        lockManager = new InMemoryLockManager();
    }

    @Test
    public void tryLockWithSuccess() {
        boolean locked = lockManager.tryLock(xpto);
        assertThat(locked).isTrue();
    }

    @Test
    public void tryLockWithSuccessForSameObjectAndSameThread() {
        boolean locked = lockManager.tryLock(xpto);
        boolean locked2 = lockManager.tryLock(xpto);
        assertThat(locked).isTrue();
        assertThat(locked2).isTrue();
    }

//    @Test
    // TODO fix test
    public void tryLockFailForLockedObject() {
        CompletableFuture<Boolean> futureLocked1 = asyncRunner.run(() -> lockManager.tryLock(xpto));
        CompletableFuture<Boolean> futureLocked2 = asyncRunner.run(() -> lockManager.tryLock(xpto));

        Boolean locked1 = getFutureValue(futureLocked1);
        Boolean locked2 = getFutureValue(futureLocked2);

        assertThat(locked1).isNotEqualTo(locked2);
    }

    @Test
    public void lockAndConsumeTest() {
        lockManager.lockAndConsume(xpto, () -> {
            CompletableFuture<Boolean> futureLocked = asyncRunner.run(() -> lockManager.tryLock(xpto));
            assertThat(getFutureValue(futureLocked)).isFalse();
        });
    }

    private <T> T getFutureValue(CompletableFuture<T> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("Error when capturing value from CompletableFuture!");
        }
    }
}
