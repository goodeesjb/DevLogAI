package com.devlogai.backend.ai.client.dto;

import java.util.List;

public record GeminiResponse(List<Candidate> candidates) {

    public record Candidate(Content content) {}

    public record Content(List<Part> parts) {}

    public record Part(String text) {}

    public String extractText() {
        if (candidates == null || candidates.isEmpty()) {
            return "";
        }
        List<Part> parts = candidates.get(0).content().parts();
        if (parts == null || parts.isEmpty()) {
            return "";
        }
        return parts.get(0).text();
    }
}
