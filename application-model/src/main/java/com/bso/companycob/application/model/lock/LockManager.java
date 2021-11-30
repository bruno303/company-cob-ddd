package com.bso.companycob.application.model.lock;

public interface LockManager {
    boolean tryLock(Lockeable object);
    void unlock(Lockeable object);
    void lockAndConsume(Lockeable object, Runnable runnable);
}
