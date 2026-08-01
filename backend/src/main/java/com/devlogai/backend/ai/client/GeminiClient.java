package com.devlogai.backend.ai.client;

import com.devlogai.backend.ai.client.dto.GeminiRequest;
import com.devlogai.backend.ai.client.dto.GeminiResponse;
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
public class GeminiClient {

    private final RestClient geminiRestClient;

    @Value("${gemini.api.key}")
    private String apiKey;

    public String generate(String prompt) {
        log.info("Gemini API 요청 시작");
        try {
            GeminiRequest request = GeminiRequest.of(prompt);

            GeminiResponse response = geminiRestClient.post()
                    .uri("/v1beta/models/gemini-2.0-flash:generateContent?key={key}", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(GeminiResponse.class);

            if (response == null) {
                throw new ExternalApiException("Gemini API 응답이 없습니다.");
            }

            String text = response.extractText();
            if (text.isEmpty()) {
                throw new ExternalApiException("Gemini API 응답 텍스트가 없습니다.");
            }

            log.info("Gemini API 요청 성공");
            return text;

        } catch (ExternalApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Gemini API 오류: {}", e.getMessage());
            throw new ExternalApiException("Gemini API 호출 중 오류가 발생했습니다.");
        }
    }
}
