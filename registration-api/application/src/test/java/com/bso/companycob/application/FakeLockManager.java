package com.bso.companycob.application;

import com.bso.companycob.application.function.Callable;
import com.bso.companycob.application.lock.LockManager;
import com.bso.companycob.application.lock.Lockeable;

public class FakeLockManager implements LockManager {
    @Override
    public boolean tryLock(Lockeable object) {
        return true;
    }

    @Override
    public void unlock(Lockeable object) { }

    @Override
    public void lockAndConsume(Lockeable object, Runnable runnable) {
        runnable.run();
    }

    @Override
    public <T> T lockAndProcess(Lockeable object, Callable<T> callable) {
        return callable.call();
    }
}
