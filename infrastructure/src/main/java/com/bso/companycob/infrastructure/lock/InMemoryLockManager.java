package com.bso.companycob.infrastructure.lock;

import com.bso.companycob.application.model.lock.LockManager;
import com.bso.companycob.application.model.lock.Lockeable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
@Slf4j
public class InMemoryLockManager implements LockManager {

    private final Map<String, ReadWriteLock> locks = new HashMap<>();

    @Override
    public boolean tryLock(Lockeable object) {
        log.debug("Acquiring lock for object '{}'", object.getLockKey());
        ReadWriteLock lock = getLock(object);
        boolean locked = doLock(lock);
        log.debug("lock for object '{}' {} acquired", object.getLockKey(), locked ? "" : "not");
        return locked;
    }

    private boolean doLock(ReadWriteLock lock) {
        try {
            return lock.writeLock().tryLock(10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public void unlock(Lockeable object) {
        ReadWriteLock lock = getLock(object);
        lock.writeLock().unlock();
    }

    @Override
    public void lockAndConsume(Lockeable object, Runnable runnable) {
        this.tryLock(object);
        try {
            runnable.run();
        } finally {
            this.unlock(object);
        }
    }

    private synchronized ReadWriteLock getLock(Lockeable lockeable) {
        ReadWriteLock lock = locks.get(lockeable.getLockKey());
        if (lock == null) {
            lock = new ReentrantReadWriteLock();
            locks.put(lockeable.getLockKey(), lock);
        }
        return lock;
    }
}
