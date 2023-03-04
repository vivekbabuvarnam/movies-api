package com.backbase.rest.moviesapi.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JacksonConfigTest {

    @InjectMocks
    private JacksonConfig jacksonConfig;

    @Test
    public void testJacksonConfig() {
        ObjectMapper objectMapper = jacksonConfig.objectMapper();

        assertFalse(objectMapper==null);
        assertFalse(objectMapper.getSerializationConfig().getSerializationFeatures() == 0);
        assertFalse(objectMapper.getDeserializationConfig().getDeserializationFeatures() == 0);
        assertFalse(objectMapper.getRegisteredModuleIds() == null);
    }
}
