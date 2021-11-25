package com.bso.companycob.domain.events;

public interface EventRaiser {
    <T extends Event> void raise(T event);
}
