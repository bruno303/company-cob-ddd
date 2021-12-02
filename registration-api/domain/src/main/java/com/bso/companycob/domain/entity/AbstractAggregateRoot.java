package com.bso.companycob.domain.entity;

import com.bso.companycob.domain.events.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractAggregateRoot implements AggregateRoot {

    private final List<Event> domainEvents = new ArrayList<>();

    @Override
    public List<Event> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void addEvent(Event event) {
        domainEvents.add(event);
    }
}
