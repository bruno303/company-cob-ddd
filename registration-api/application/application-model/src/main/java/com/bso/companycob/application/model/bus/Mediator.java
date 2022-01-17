package com.bso.companycob.application.model.bus;

public interface Mediator {
    <T extends Command> void dispatch(T command);
    <T extends Request<R>, R> R dispatch(T request);
}
