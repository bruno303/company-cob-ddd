package com.bso.companycob.infrastructure.bus;

import com.bso.dracko.mediator.contract.Request;
import com.bso.dracko.mediator.contract.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * This proxy encapsulate the original request handler and add specific framework settings, like @Transactional annotation.
 *
 * This way we can have the original implementation isolated in application layer and at same time use the advantages
 * offered by frameworks, like transactional management, cache, async processing and others.
 *
 */
@RequiredArgsConstructor
public class SpringRequestHandlerProxy<T extends Request<R>, R> implements RequestHandler<T, R> {
    private final RequestHandler<T, R> originalHandler;

    @Transactional
    @Override
    public R handle(T t) {
        return originalHandler.handle(t);
    }

    @Override
    public Class<T> getType() {
        return originalHandler.getType();
    }
}
