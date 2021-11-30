package com.bso.companycob.domain.events;

import java.util.Collection;

public interface EventRaiser {
    <T extends Event> void raise(T event);
    <T extends Event> void raise(Collection<? extends T> events);
}
