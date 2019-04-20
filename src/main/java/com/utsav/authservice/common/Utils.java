package com.utsav.authservice.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.function.Function;

public class Utils {

    public static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T getOrDefault(T value, T defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value;
    }


    public static <T, R> List<R> toList(Collection<T> source, Function<T, R> func) {
        List<R> target = new ArrayList<>();
        for (T item : source) {
            if (null == item) {
                continue;
            }
            target.add(func.apply(item));
        }
        return target;
    }

    public static <T, R> Set<R> toSet(Collection<T> source, Function<T, R> func) {
        Set<R> target = new LinkedHashSet<>();
        for (T item : source) {
            if (null == item) {
                continue;
            }
            target.add(func.apply(item));
        }
        return target;
    }

}
