package com.devlogai.backend.ai.client;

import com.devlogai.backend.ai.client.dto.OpenAiRequest;
import com.devlogai.backend.ai.client.dto.OpenAiResponse;
import com.devlogai.backend.common.exception.ExternalApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenAiClient {

    private final RestClient openAiRestClient;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    public String generate(String prompt) {
        log.info("OpenAI API 요청 시작");
        try {
            OpenAiRequest request = OpenAiRequest.of(model, prompt);

            OpenAiResponse response = openAiRestClient.post()
                    .uri("/v1/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(OpenAiResponse.class);

            if (response == null) {
                throw new ExternalApiException("OpenAI API 응답이 없습니다.");
            }

            String text = response.extractText();
            if (text.isEmpty()) {
                throw new ExternalApiException("OpenAI API 응답 텍스트가 없습니다.");
            }

            log.info("OpenAI API 요청 성공");
            return text;

        } catch (ExternalApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("OpenAI API 오류: {}", e.getMessage());
            throw new ExternalApiException("OpenAI API 호출 중 오류가 발생했습니다.");
        }
    }
}