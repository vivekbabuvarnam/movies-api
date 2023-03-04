package com.backbase.rest.moviesapi.interceptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backbase.rest.moviesapi.context.ApiUriContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.util.UriComponentsBuilder;

@ExtendWith(MockitoExtension.class)
public class LinkInterceptorTest {

    @Mock(lenient = true)
    private HttpServletRequest request;
    @Mock(lenient = true)
    private HttpServletResponse response;
    @Mock(lenient = true)
    private HandlerMethod handler;
    @Spy
    private ApiUriContext reservationApiUriContext;
    @InjectMocks
    private LinkInterceptor linkInterceptor;

    @Test
    public void testPreHandle() throws Exception {
        int localPort = 8080;
        String contextPath = "/mypath/";
        when(request.getHeader("localPort")).thenReturn(String.valueOf(localPort));
        when(request.getParameter("contextPath")).thenReturn(contextPath);

        linkInterceptor.preHandle(request, response, handler);
        verify(reservationApiUriContext,times(1)).setUriComponentsBuilder(any());
    }

}
