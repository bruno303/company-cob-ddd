package com.bso.companycob.application.model.bus;

public interface CommandHandler<T extends Command> {
    void handle(T command);
}
