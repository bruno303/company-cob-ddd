package com.bso.companycob.application.events;

import java.util.Collection;
import java.util.stream.Stream;

import com.bso.companycob.domain.events.Event;
import com.bso.companycob.domain.events.EventHandler;
import com.bso.companycob.domain.events.EventRaiser;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleEventRaiser implements EventRaiser {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEventRaiser.class);

    private final ApplicationContext context;

    @Override
    public <T extends Event> void raise(T event) {
        ResolvableType type = ResolvableType.forClassWithGenerics(EventHandler.class, event.getClass());
        String[] beanNames = context.getBeanNamesForType(type);

        if (beanNames == null || beanNames.length == 0) {
            LOGGER.info("Found 0 handlers for event '{}'. Try create a @Component that implements EventHandler<>", event.getClass().getSimpleName());
            return;
        }

        LOGGER.info("Found {} handlers for event '{}'", beanNames.length, event.getClass().getSimpleName());

        Stream.of(beanNames)
            .map(this::mapToEventHandler)
            .forEach(handler -> handler.handle(event));
    }

    @Override
    public <T extends Event> void raise(Collection<? extends T> events) {
        events.forEach(this::raise);
    }

    @SuppressWarnings(value = "unchecked")
    private <T extends Event> EventHandler<T> mapToEventHandler(String beanName) {
        return (EventHandler<T>) context.getBean(beanName);
    }
    
}
