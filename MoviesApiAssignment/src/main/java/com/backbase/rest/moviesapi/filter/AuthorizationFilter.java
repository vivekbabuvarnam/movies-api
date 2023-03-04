package com.backbase.rest.moviesapi.filter;

import static java.util.Arrays.stream;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.backbase.rest.moviesapi.context.UserContext;
import com.backbase.rest.moviesapi.exception.InvalidJwtToken;
import com.backbase.rest.moviesapi.orchestrator.MovieInfoOrchestrator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {


    @Value("${dynamicinput.algorithm.key}")
    private String algorithmKey;
    @Value("${dynamicinput.algorithm.claim}")
    private String claimName;
    @Resource
    private UserContext userContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/login") || request.getServletPath().equals("/console"))
        {
            filterChain.doFilter(request, response);
        }
        else
        {
            String authorizationHeader =  request.getHeader(HttpHeaders.AUTHORIZATION);
            if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer "))
            {
                try{
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256(algorithmKey.getBytes());
                    JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = jwtVerifier.verify(token);
                    String userName = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim(claimName).asArray(String.class);
                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    userContext.setUserName(userName);
                    //request.setAttribute("userKey",userName);
                    filterChain.doFilter(request, response);

                }catch(Exception ex)
                {
                    log.error("Issue with the Bearer Token Passed : {}", ex.getMessage());
                    response.setHeader("error", ex.getMessage());
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    Map<String, String> error= new HashMap<>();
                    error.put("error_message", ex.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(),error);
                    throw new InvalidJwtToken("Issue with the Bearer Token Passed"+ ex.getMessage());
                }
            }else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
