package com.bso.companycob.application.lock;

public interface LockManager {
    boolean tryLock(Lockeable object);
    void unlock(Lockeable object);
    void lockAndConsume(Lockeable lockeable, Runnable runnable);
}
