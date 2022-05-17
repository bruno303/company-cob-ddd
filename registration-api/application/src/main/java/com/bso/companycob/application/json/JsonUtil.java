package com.bso.companycob.application.json;

public interface JsonUtil {
    <T> T fromJson(String json, Class<T> type);
    <T> String toJson(T object);
}
