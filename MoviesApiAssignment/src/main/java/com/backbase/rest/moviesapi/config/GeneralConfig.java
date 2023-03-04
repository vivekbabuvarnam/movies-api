package com.backbase.rest.moviesapi.config;

import java.util.Arrays;
import java.util.List;

import com.backbase.rest.moviesapi.interceptor.LinkInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GeneralConfig implements WebMvcConfigurer {

    private static final String ALL_PATH_PATTERNS = "/**";
    private static final List<String> EXCLUDE_PATH_PATTERNS = Arrays.asList("/swagger-ui.html", "/swagger-resources/**");

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(linkInterceptor()).addPathPatterns(ALL_PATH_PATTERNS).excludePathPatterns(EXCLUDE_PATH_PATTERNS);
    }

    @Bean
    public LinkInterceptor linkInterceptor() {
        return new LinkInterceptor();
    }

}
