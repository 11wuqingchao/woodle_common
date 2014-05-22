package com.woodle.api.pojo;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.woodle.api.enums.JsonFeatures;

import java.util.concurrent.ExecutionException;

/**
 * ObjectMapper是线程安全的，可以共用相同Feature的ObjectMapper
 * User: wuqingchao
 * Time: 14-5-19 下午11:42
 */
public class JsonMapperBuilder {

    private static LoadingCache<Long, JsonMapper> jsonMapperCache = CacheBuilder.newBuilder()
            .maximumSize(1000).build(new CacheLoader<Long, JsonMapper>() {
                @Override
                public JsonMapper load(Long key) throws Exception {
                    return buildMapper(key) ;
                }
            }) ;


    public static JsonMapper buildMapper(long features) {
        ObjectMapper om = new ObjectMapper();

        for (JsonFeatures jf : JsonFeatures.values()) {
            configure(om, jf.getFeature(), jf.isEnabled(features));
        }

        return new JsonMapper(om);
    }


    private static void configure(ObjectMapper om, Object feature, boolean state) {
        if (feature instanceof SerializationFeature)
            om.configure((SerializationFeature) feature, state);
        else if (feature instanceof DeserializationFeature)
            om.configure((DeserializationFeature) feature, state);
        else if (feature instanceof JsonParser.Feature)
            om.configure((JsonParser.Feature) feature, state);
        else if (feature instanceof JsonGenerator.Feature)
            om.configure((JsonGenerator.Feature) feature, state);
        else if (feature instanceof MapperFeature)
            om.configure((MapperFeature) feature, state);
        else if (feature instanceof Include) {
            if (state) {
                om.setSerializationInclusion((Include) feature);
            }
        }
    }

    private long features = JsonFeatures.defaults();

    public static JsonMapperBuilder create() {
        return new JsonMapperBuilder();
    }

    public JsonMapperBuilder enable(JsonFeatures jf) {
        features = jf.enable(features);
        return this;
    }

    public JsonMapperBuilder disable(JsonFeatures jf) {
        features = jf.disable(features);
        return this;
    }

    public JsonMapperBuilder configure(JsonFeatures jf, boolean state) {
        if (state)
            enable(jf);
        else
            disable(jf);
        return this;
    }

    public JsonMapperBuilder configure(JsonFeatures[] enabled, JsonFeatures[] disabled) {
        for (JsonFeatures jf : enabled) {
            enable(jf);
        }
        for (JsonFeatures jf : disabled) {
            disable(jf);
        }
        return this;
    }

    public JsonMapper build() {
        try {
            return jsonMapperCache.get(features);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
