package com.devlogai.backend.ai.dto;

import com.devlogai.backend.git.dto.CommitDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AiGenerateRequest(
        @NotEmpty(message = "커밋 목록이 비어있습니다.")
        List<CommitDto> commits,

        @NotBlank(message = "개발 메모를 입력해주세요.")
        String memo
) {
}
