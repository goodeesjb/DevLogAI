package com.devlogai.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class OpenAiConfig {

    @Value("${openai.api.url}")
    private String openAiApiUrl;

    @Bean
    public RestClient openAiRestClient() {
        return RestClient.builder()
                .baseUrl(openAiApiUrl)
                .build();
    }
}