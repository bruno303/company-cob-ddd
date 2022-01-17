package com.bso.companycob.infrastructure.bus;

import com.bso.companycob.application.model.bus.Command;
import com.bso.companycob.application.model.bus.CommandHandler;
import com.bso.companycob.application.model.bus.Mediator;
import com.bso.companycob.application.model.bus.Registry;
import com.bso.companycob.application.model.bus.Request;
import com.bso.companycob.application.model.bus.RequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class InMemoryMediator implements Mediator {

    private final Registry registry;

    @Override
    public <T extends Command> void dispatch(T command) {
        Optional<CommandHandler<T>> commandHandlerOpt = registry.getCommandHandler(command);
        if (commandHandlerOpt.isEmpty()) {
            log.warn("No command handlers found for command '{}'", command.getClass().getSimpleName());
            return;
        }

        commandHandlerOpt.get().handle(command);
    }

    @Override
    public <T extends Request<R>, R> R dispatch(T request) {
        Optional<RequestHandler<T, R>> handlerOpt = registry.getRequestHandler(request);
        if (handlerOpt.isEmpty()) {
            log.warn("No request handlers found for request '{}'", request.getClass().getSimpleName());
            return null;
        }

        return handlerOpt.get().handle(request);
    }
}
