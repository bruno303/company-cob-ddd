package com.bso.companycob.infrastructure.bus;

import com.bso.companycob.application.model.bus.Command;
import com.bso.companycob.application.model.bus.CommandHandler;
import com.bso.companycob.application.model.bus.Registry;
import com.bso.companycob.application.model.bus.Request;
import com.bso.companycob.application.model.bus.RequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@Slf4j
public class SpringRegistry implements Registry {

    private final ApplicationContext ctx;

    private final Map<Class<?>, CommandHandler<? extends Command>> commandHandlers = new HashMap<>();
    private final Map<Class<?>, RequestHandler<? extends Request<?>, ?>> requestHandlers = new HashMap<>();
    private boolean initialized = false;

    public SpringRegistry(ApplicationContext ctx) {
        this.ctx = ctx;
        initialize();
    }

    private synchronized void initialize() {
        if (initialized) {
            return;
        }
        log.info("Initializing springRegistry");

        String[] names = ctx.getBeanNamesForType(RequestHandler.class);
        log.info("Found {} request handlers", names.length);
        Stream.of(names).forEach(this::registerRequestHandler);

        names = ctx.getBeanNamesForType(CommandHandler.class);
        log.info("Found {} command handlers", names.length);
        Stream.of(names).forEach(this::registerCommandHandler);

        initialized = true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <Q extends Request<R>, R> Optional<RequestHandler<Q, R>> getRequestHandler(Q request) {
        var handler = (RequestHandler<Q,R>) requestHandlers.get(request.getClass());
        return handler == null ? Optional.empty() : Optional.of(handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Command> Optional<CommandHandler<T>> getCommandHandler(T command) {
        var handler =  (CommandHandler<T>) commandHandlers.get(command.getClass());
        return handler == null ? Optional.empty() : Optional.of(handler);
    }

    private void registerRequestHandler(String beanName) {
        var handler = (RequestHandler<?, ?>)ctx.getBean(beanName);
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handler.getClass(), RequestHandler.class);
        if (generics != null) {
            Class<?> requestType = generics[0];
            log.info("Registering request handler '{}'", handler.getClass().getSimpleName());
            requestHandlers.putIfAbsent(requestType, handler);
        }
    }

    private void registerCommandHandler(String beanName) {
        var handler = (CommandHandler<?>)ctx.getBean(beanName);
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handler.getClass(), RequestHandler.class);
        if (generics != null) {
            Class<?> requestType = generics[0];
            log.info("Registering command handler '{}'", handler.getClass().getSimpleName());
            commandHandlers.putIfAbsent(requestType, handler);
        }
    }
}
