package com.example.auth.clients.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.auth.exceptions.CustomFeignClientException;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.ErrorDecoder;

@Configuration
public class MovieClientConfig {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String TOKEN_TYPE = "Bearer";

    @Value("${the_movie_db_api.token}")
    public String token;

    @Bean
    RequestInterceptor requestInterceptor() {
        return (RequestTemplate requestTemplate) -> requestTemplate.header(AUTHORIZATION_HEADER,
                String.format("%s %s", TOKEN_TYPE, token));
    }

    @Bean
    ErrorDecoder errorDecoder() {
        return (String methodKey, Response response) -> new CustomFeignClientException(response.reason(), response);
    }
}
