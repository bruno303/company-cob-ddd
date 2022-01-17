package com.bso.companycob.application.model.bus;

public interface RequestHandler<T extends Request<R>, R> {
    R handle(T request);
}
