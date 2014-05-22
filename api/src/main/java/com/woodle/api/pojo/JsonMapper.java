package com.woodle.api.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * Json数据处理
 * User: wuqingchao
 * Time: 14-5-19 下午11:38
 */
public class JsonMapper {
    private final ObjectMapper objectMapper;

    public JsonMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void writeValue(Writer writer, Object obj) throws IOException {
        Preconditions.checkNotNull(writer);

        try {
            objectMapper.writeValue(writer, obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("jackson format error: " + obj.getClass(), e);
        }
    }

    public String writeValueAsString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("jackson format error: " + obj.getClass(), e);
        }
    }

    public <T> T readValue(String json, Class<T> type) {

        try {
            return (T) objectMapper.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException("jackson parse error :" + json.substring(0, Math.min(100, json.length())), e);
        }
    }

    public <T> T readValue(Reader json, Class<T> type) throws IOException {

        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("jackson parse error.", e);
        }
    }

    public <T> T readValue(String json, TypeReference<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException("jackson parse error.", e);
        }
    }

    public <T> T readValue(Reader json, TypeReference<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException("jackson parse error.", e);
        }
    }
}
