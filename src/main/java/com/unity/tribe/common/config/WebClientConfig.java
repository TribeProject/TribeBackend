package com.unity.tribe.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.unity.tribe.common.model.enums.ExternalApiUrl;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .defaultHeader("Content-Type", "application/json");
    }

    @Bean("naverWebClient")
    public WebClient naverWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(ExternalApiUrl.GET_NAVER_PROFILE.getBaseUrl())
                .build();
    }

}
