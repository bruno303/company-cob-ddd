package com.bso.companycob.application.lock;

public interface LockManager {
    boolean tryLock(Lockeable object);
    void unlock(Lockeable object);
    void lockAndConsume(Lockeable object, Runnable runnable);
    <T> T lockAndProcess(Lockeable object, Callable<T> callable);
}
