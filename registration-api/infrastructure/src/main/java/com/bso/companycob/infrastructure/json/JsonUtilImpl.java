package com.bso.companycob.infrastructure.json;

import com.bso.companycob.application.json.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonUtilImpl implements JsonUtil {

    private final ObjectMapper objectMapper;

    @Override
    public <T> T fromJson(String json, Class<T> type) {
        return run(() -> objectMapper.readValue(json, type));
    }

    @Override
    public <T> String toJson(T object) {
        return run(() -> objectMapper.writeValueAsString(object));
    }

    private <T> T run(RunnableWithReturn<T> runnable) {
        try {
            return runnable.run();
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Json mapping failed!");
        }
    }

    @FunctionalInterface
    private interface RunnableWithReturn<T> {
        T run() throws JsonProcessingException;
    }
}
