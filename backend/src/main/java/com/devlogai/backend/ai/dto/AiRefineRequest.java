package com.devlogai.backend.ai.dto;

import jakarta.validation.constraints.NotBlank;

public record AiRefineRequest(
        @NotBlank(message = "기존 개발 기록이 없습니다.")
        String previousContent,

        @NotBlank(message = "수정 요청 내용을 입력해주세요.")
        String refineRequest
) {
}