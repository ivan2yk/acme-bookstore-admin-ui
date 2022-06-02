package com.acme.bookstore.acmebookstoreadminui.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper;

    private JsonUtil() {
    }

    static {
        objectMapper = new ObjectMapper();
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        /* for installments-simulation */
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static String writeJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e.getCause());
            throw new RuntimeException(e.getCause());
        }
    }

    public static <T> T convertTo(String json, Class<T> aClass) {
        try {
            return objectMapper.readValue(json, aClass);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e.getCause());
            throw new RuntimeException(e.getCause());
        }
    }

    public static <T> T convertTo(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e.getCause());
            throw new RuntimeException(e.getCause());
        }
    }

}
