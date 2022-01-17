package com.bso.companycob.application.core.events;

import com.bso.companycob.domain.events.Event;
import com.bso.companycob.domain.events.EventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SimpleEventRaiserTest {

    private static class DummyEvent implements Event {
        //
    }

    private interface DummyHandler1 extends EventHandler<DummyEvent> {
        //
    }

    private interface DummyHandler2 extends EventHandler<DummyEvent> {
        //
    }

    private SimpleEventRaiser simpleEventRaiser;
    private final ApplicationContext contextMock = Mockito.mock(ApplicationContext.class);
    private final EventHandler<DummyEvent> handler1Mock = Mockito.mock(DummyHandler1.class);
    private final EventHandler<DummyEvent> handler2Mock = Mockito.mock(DummyHandler2.class);

    @BeforeEach
    public void setup() {
        simpleEventRaiser = new SimpleEventRaiser(contextMock);
    }

    @Test
    public void testRaiseDummyEventWithSingleHandler() {

        final String handler1Name = "handler1";

        when(contextMock.getBeanNamesForType(any(ResolvableType.class)))
            .thenReturn(new String[] { handler1Name });

        when(contextMock.getBean(handler1Name)).thenReturn(handler1Mock);

        DummyEvent event = new DummyEvent();
        simpleEventRaiser.raise(event);

        verify(contextMock, times(1)).getBeanNamesForType(any(ResolvableType.class));
        verify(contextMock, times(1)).getBean(handler1Name);
        verify(handler1Mock, times(1)).handle(event);
    }
    
    @Test
    public void testRaiseDummyEventWithMultipleHandlers() {

        final String handler1Name = "handler1";
        final String handler2Name = "handler2";

        when(contextMock.getBeanNamesForType(any(ResolvableType.class)))
            .thenReturn(new String[] { handler1Name , handler2Name });

        when(contextMock.getBean(handler1Name)).thenReturn(handler1Mock);

        when(contextMock.getBean(handler2Name)).thenReturn(handler2Mock);

        DummyEvent event = new DummyEvent();
        simpleEventRaiser.raise(event);

        verify(contextMock, times(1)).getBeanNamesForType(any(ResolvableType.class));
        verify(contextMock, times(1)).getBean(handler1Name);
        verify(contextMock, times(1)).getBean(handler2Name);
        verify(handler1Mock, times(1)).handle(event);
        verify(handler2Mock, times(1)).handle(event);
    }

    @Test
    public void testRaiseDummyEventWithoutHandlers() {
        when(contextMock.getBeanNamesForType(any(ResolvableType.class)))
            .thenReturn(new String[] { });

        DummyEvent event = new DummyEvent();
        simpleEventRaiser.raise(event);

        verify(contextMock, times(1)).getBeanNamesForType(any(ResolvableType.class));
        verify(contextMock, times(0)).getBean(anyString());
    }

    @Test
    public void testRaiseDummyEventWithoutHandlersAndContextReturnNull() {
        when(contextMock.getBeanNamesForType(any(ResolvableType.class)))
            .thenReturn(null);

        DummyEvent event = new DummyEvent();
        simpleEventRaiser.raise(event);

        verify(contextMock, times(1)).getBeanNamesForType(any(ResolvableType.class));
        verify(contextMock, times(0)).getBean(anyString());
    }
}
