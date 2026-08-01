package com.devlogai.backend.ai.controller;

import com.devlogai.backend.ai.dto.AiGenerateRequest;
import com.devlogai.backend.ai.dto.AiGenerateResponse;
import com.devlogai.backend.ai.dto.AiRefineRequest;
import com.devlogai.backend.ai.service.AiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/generate")
    public ResponseEntity<AiGenerateResponse> generate(@RequestBody @Valid AiGenerateRequest request) {
        return ResponseEntity.ok(aiService.generate(request));
    }

    @PostMapping("/refine")
    public ResponseEntity<AiGenerateResponse> refine(@RequestBody @Valid AiRefineRequest request) {
        return ResponseEntity.ok(aiService.refine(request));
    }
}
