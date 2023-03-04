package com.backbase.rest.moviesapi.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EhCacheConfigTest {
    @Test
    public void test() {
        EhCacheConfig cacheConfig = new EhCacheConfig();
        assertNotNull(cacheConfig.cacheManager());
        assertNotNull(cacheConfig.ehCacheManagerFactoryBean());
    }
}
