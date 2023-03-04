package com.backbase.rest.moviesapi.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.stereotype.Component;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JacksonConfig {


    @Bean
    public ObjectMapper objectMapper() {
        final Jackson2ObjectMapperFactoryBean bean = new Jackson2ObjectMapperFactoryBean();
        bean.setIndentOutput(true);
        bean.afterPropertiesSet();
        final ObjectMapper objectMapper = bean.getObject();
        if (objectMapper != null) {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.configure(JsonParser.Feature.STRICT_DUPLICATE_DETECTION, true);
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.setSerializationInclusion(Include.NON_EMPTY);
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
        }
        return objectMapper;
    }
}
