package com.devlogai.backend.git.controller;

import com.devlogai.backend.git.dto.CommitDto;
import com.devlogai.backend.git.service.GitService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/git")
@RequiredArgsConstructor
@Validated
public class GitController {

    private final GitService gitService;

    @GetMapping("/commits")
    public ResponseEntity<List<CommitDto>> getCommits(
            @RequestParam @NotBlank(message = "프로젝트 경로를 입력해주세요.") String path) {
        return ResponseEntity.ok(gitService.getRecentCommits(path));
    }
}