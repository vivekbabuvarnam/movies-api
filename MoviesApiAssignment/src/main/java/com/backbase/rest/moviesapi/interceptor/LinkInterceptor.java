package com.backbase.rest.moviesapi.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backbase.rest.moviesapi.context.ApiUriContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

public class LinkInterceptor implements HandlerInterceptor {

    @Value("${dynamicinput.protocol}")
    private String protocol;
    @Resource
    private ApiUriContext reservationApiUriContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        final String host = "localhost";
        final int port = request.getLocalPort();
        String root = request.getContextPath() + "/";

        reservationApiUriContext.setUriComponentsBuilder(UriComponentsBuilder.newInstance().scheme(protocol).host(host).port(port).path(root));

        return true;
    }

}
