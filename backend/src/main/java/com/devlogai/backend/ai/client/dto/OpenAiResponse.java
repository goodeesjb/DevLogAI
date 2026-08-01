package com.devlogai.backend.ai.client.dto;

import java.util.List;

public record OpenAiResponse(List<Choice> choices) {

    public record Choice(Message message) {}

    public record Message(String role, String content) {}

    public String extractText() {
        if (choices == null || choices.isEmpty()) {
            return "";
        }
        String content = choices.get(0).message().content();
        return content != null ? content : "";
    }
}