package com.devlogai.backend.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AiGenerateRequest(
        @NotEmpty(message = "커밋 목록이 비어있습니다.")
        List<CommitSummary> commits,

        @NotBlank(message = "개발 메모를 입력해주세요.")
        String memo
) {
    public record CommitSummary(String hash, String message, String author, String date) {}
}
