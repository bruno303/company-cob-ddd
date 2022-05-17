package com.bso.companycob.application.lock;

import com.bso.companycob.application.function.Callable;

public interface LockManager {
    boolean tryLock(Lockeable object);
    void unlock(Lockeable object);
    void lockAndConsume(Lockeable object, Runnable runnable);
    <T> T lockAndProcess(Lockeable object, Callable<T> callable);
}
