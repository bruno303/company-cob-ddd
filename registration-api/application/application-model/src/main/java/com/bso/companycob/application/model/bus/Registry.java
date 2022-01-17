package com.bso.companycob.application.model.bus;

import java.util.Optional;

public interface Registry {
    <Q extends Request<R>, R> Optional<RequestHandler<Q, R>> getRequestHandler(Q request);
    <T extends Command> Optional<CommandHandler<T>> getCommandHandler(T command);
}
