package com.utsav.authservice.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;

public class Utils {

    public static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T getOrDefault(T value, T defaultValue) {
        if(value == null) {
            return defaultValue;
        }
        return value;
    }


}
