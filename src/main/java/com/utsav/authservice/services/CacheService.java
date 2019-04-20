package com.utsav.authservice.services;


import com.fasterxml.jackson.core.type.TypeReference;

import java.util.function.Supplier;

public interface CacheService {

    String get(String key);

    String set(String key, String value);

    String set(String key, String value, long ttl);

    <T> T getValue(final String key, final TypeReference<T> objectType);

    <T> T setValue(final String key, final T value);

    <T> T setValue(final String key, final T value, long ttl);

    <T> T conditionalSetAndGetValue(final String key, final TypeReference<T> objectType, Supplier<T> operation);

    <T> T conditionalSetAndGetValue(final String key, final TypeReference<T> objectType, Supplier<T> operation,
                                    long ttl);

    void deleteKey(final String key);

}
