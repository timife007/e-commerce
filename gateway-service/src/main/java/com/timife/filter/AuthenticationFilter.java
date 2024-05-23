package com.timife.filter;

import com.timife.JwtUtil;
import com.timife.feign.AuthFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.ext.ParamConverter;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    //Giving circular dependency with AuthFilter and GatewayAutoConfiguration
//    @Autowired
//    private AuthFeignClient authFeignClient;



    public AuthenticationFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        log.error("GATEWAYFILTER code was triggered");
        return ((exchange, chain) -> {
            Boolean check = routeValidator.isSecured.test(exchange.getRequest());
            log.error(check + " code was triggered");

            if (check) {
                //header contains token or not;
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing auth header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(org.springframework.http.HttpHeaders.AUTHORIZATION).getLast();

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                log.error(authHeader + " code was triggered");

                try {
                    //Make requests to the auth service

                    //Quite similar to the token validation present in the JwtAuthFilter in the auth service;
                    //However, if auth service should go down, I might not be able to proceed
                    //Issue is with the service communication.
                    restTemplate.getForObject("http://AUTHENTICATION/auth/validate/{token}", String.class, authHeader);
//                    jwtUtil.validateToken(authHeader);
//                    authFeignClient.validateToken(authHeader);
                } catch (Exception e) {
                    System.out.println("invalid token access...");
                    throw new RuntimeException("Unauthorized access to the app" + e.getLocalizedMessage());
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
