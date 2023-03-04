package com.backbase.rest.moviesapi.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@ExtendWith(MockitoExtension.class)
public class GeneralConfigTest {

    @InjectMocks
    private GeneralConfig generalConfig;

    @Test
    public void testInterceptor() {
        InterceptorRegistry interceptorRegistry = new InterceptorRegistry();
        generalConfig.addInterceptors(interceptorRegistry);
    }

    @Test
    public void testLinkInterceptor() {
        assertFalse(generalConfig.linkInterceptor() ==null);
    }
}
