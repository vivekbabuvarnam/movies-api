package com.backbase.rest.moviesapi.context;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ApiUriContext {

    private UriComponentsBuilder uriComponentsBuilder;

    public UriComponentsBuilder getUriComponentsBuilder() {
        return uriComponentsBuilder;
    }

    public void setUriComponentsBuilder(UriComponentsBuilder uriComponentsBuilder) {
        this.uriComponentsBuilder = uriComponentsBuilder;
    }

    public String getBasePath() {
        return uriComponentsBuilder.build().toString();
    }
}
