package com.devlogai.backend.ai.service;

import com.devlogai.backend.ai.client.OpenAiClient;
import com.devlogai.backend.ai.dto.AiGenerateRequest;
import com.devlogai.backend.ai.dto.AiGenerateResponse;
import com.devlogai.backend.ai.dto.AiRefineRequest;
import com.devlogai.backend.git.dto.CommitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {

    private final OpenAiClient openAiClient;

    public AiGenerateResponse generate(AiGenerateRequest request) {
        String prompt = buildPrompt(request);
        String content = openAiClient.generate(prompt);
        return new AiGenerateResponse(content);
    }

    public AiGenerateResponse refine(AiRefineRequest request) {
        String prompt = buildRefinePrompt(request.previousContent(), request.refineRequest());
        String content = openAiClient.generate(prompt);
        return new AiGenerateResponse(content);
    }

    private String buildPrompt(AiGenerateRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("너는 시니어 개발자이다. 아래 Git Commit 목록과 개발 메모를 분석하여 Markdown 형식의 개발 기록을 작성한다.\n\n");
        sb.append("반드시 아래 항목을 포함해야 한다:\n");
        sb.append("1. 오늘 한 일\n");
        sb.append("2. 어려웠던 점\n");
        sb.append("3. 해결 과정\n");
        sb.append("4. 배운 점\n");
        sb.append("5. 다음 작업\n\n");

        sb.append("## Git Commit 목록\n");
        request.commits().forEach(commit ->
                sb.append(String.format("- [%s] %s (%s, %s)\n",
                        commit.hash().substring(0, Math.min(7, commit.hash().length())),
                        commit.message(),
                        commit.author(),
                        commit.date()))
        );

        sb.append("\n## 개발 메모\n");
        sb.append(request.memo()).append("\n\n");
        sb.append("위 내용을 바탕으로 Markdown 형식의 개발 기록을 작성해줘. 코드 블록 없이 순수 Markdown만 출력해.");

        return sb.toString();
    }

    private String buildRefinePrompt(String previousContent, String refineRequest) {
        return "아래는 기존에 생성된 개발 기록이다.\n\n"
                + "## 기존 개발 기록\n"
                + previousContent + "\n\n"
                + "## 수정 요청\n"
                + refineRequest + "\n\n"
                + "수정 요청 사항을 반영하여 개발 기록을 다시 작성해줘. 코드 블록 없이 순수 Markdown만 출력해.";
    }
}
