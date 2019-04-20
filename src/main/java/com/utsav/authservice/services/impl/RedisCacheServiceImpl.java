package com.utsav.authservice.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.utsav.authservice.common.Constants;
import com.utsav.authservice.common.Utils;
import com.utsav.authservice.services.CacheService;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.function.Supplier;

@Service
@Slf4j
public class RedisCacheServiceImpl implements CacheService {

    private final RedisAsyncCommands<String, String> asyncCommands;

    private final RedisCommands<String, String> syncCommands;

    public RedisCacheServiceImpl(StatefulRedisConnection<String, String> connection) {
        this.asyncCommands = connection.async();
        this.syncCommands = connection.sync();
    }

    private String getQualifiedKey(String key) {
        return String.join(Constants.CACHE_DELIMETER, Constants.CACHE_KEY_PREFIX, key);
    }

    @Override
    public String get(String key) {
        key = getQualifiedKey(key);
        return syncCommands.get(key);
    }

    @Override
    public String set(String key, String value) {
        asyncCommands.set(getQualifiedKey(key), value);
        return value;
    }

    @Override
    public String set(String key, String value, long ttl) {
        asyncCommands.set(getQualifiedKey(key), value);
        return value;
    }

    @Override
    public <T> T getValue(String key, TypeReference<T> objectType) {
        String rawKey = key;
        key = getQualifiedKey(key);
        String value = syncCommands.get(key);
        LOG.debug("Got value: {} for key: {} from redis cache", key, value);
        if (value != null) {
            try {
                return Utils.OBJECT_MAPPER.readValue(value, objectType);
            } catch (IOException e) {
                asyncCommands.del(rawKey);
                LOG.warn("Unable to parse value: {} to type: {}. Invalidating cache and returning null",
                        value, objectType.getType().getTypeName(), e);
            }
        }
        return null;
    }

    @Override
    public <T> T setValue(String key, T value) {
        try {
            key = getQualifiedKey(key);
            String jsonValue = Utils.OBJECT_MAPPER.writeValueAsString(value);
            LOG.debug("Setting key: {} with value: {}", key, jsonValue);
            asyncCommands.set(key, jsonValue);
        } catch (JsonProcessingException e) {
            LOG.warn("Unable to convert value: {} to its json string. Unable to set value for the key: {}", value,
                    key, e);
        }
        return value;
    }

    @Override
    public <T> T setValue(String key, T value, long ttl) {
        try {
            key = getQualifiedKey(key);
            String jsonValue = Utils.OBJECT_MAPPER.writeValueAsString(value);
            LOG.debug("Setting key: {} with value: {}", key, jsonValue);
            asyncCommands.set(key, jsonValue);
        } catch (JsonProcessingException e) {
            LOG.warn("Unable to convert value: {} to its json string. Unable to set value for the key: {}", value,
                    key, e);
        }
        return value;
    }

    @Override
    public <T> T conditionalSetAndGetValue(String key, TypeReference<T> objectType, Supplier<T> operation) {
        T value = getValue(key, objectType);
        return (null == value)? setValue(key, operation.get()):value;
    }

    @Override
    public <T> T conditionalSetAndGetValue(String key, TypeReference<T> objectType, Supplier<T> operation, long ttl) {
        T value = getValue(key, objectType);
        return (null == value)? setValue(key, operation.get(), ttl):value;
    }

    @Override
    public void deleteKey(String key) {
        key = getQualifiedKey(key);
        LOG.debug("Invalidating cache key: {}", key);
        asyncCommands.del(key);
    }
}
