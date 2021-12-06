package com.bso.companycob.domain.entity;

import com.bso.companycob.domain.events.Event;

import java.util.List;

public interface AggregateRoot {
    List<Event> getDomainEvents();
}
