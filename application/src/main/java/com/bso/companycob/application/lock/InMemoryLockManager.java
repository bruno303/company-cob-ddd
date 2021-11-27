package com.bso.companycob.application.lock;

import org.springframework.stereotype.Component;

@Component
public class InMemoryLockManager implements LockManager {

    @Override
    public boolean tryLock(Lockeable object) {
        return false;
    }

    @Override
    public void unlock(Lockeable object) {
        // todo
    }

    @Override
    public void lockAndConsume(Lockeable lockeable, Runnable runnable) {
        this.tryLock(lockeable);
        try {
            runnable.run();
        } finally {
            this.unlock(lockeable);
        }
    }
}
