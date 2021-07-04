package com.bso.companycob.application.events;

import java.util.stream.Stream;

import com.bso.companycob.domain.events.Event;
import com.bso.companycob.domain.events.EventHandler;
import com.bso.companycob.domain.events.EventRaiser;

import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

@Component
public class SimpleEventRaiser implements EventRaiser {

    private final ApplicationContext context;

    public SimpleEventRaiser(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public <T extends Event> void raise(T event) {
        ResolvableType type = ResolvableType.forClassWithGenerics(EventHandler.class, event.getClass());
        String[] beanNames = context.getBeanNamesForType(type);
        Stream.of(beanNames)
            .map(this::mapToEventHandler)
            .forEach(handler -> handler.handle(event));
    }

    @SuppressWarnings(value = "unchecked")
    private <T extends Event> EventHandler<T> mapToEventHandler(String beanName) {
        return (EventHandler<T>) context.getBean(beanName);
    }
    
}
