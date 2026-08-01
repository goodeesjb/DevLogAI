package com.devlogai.backend.git.service;

import com.devlogai.backend.common.exception.BusinessException;
import com.devlogai.backend.git.dto.CommitDto;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class GitService {

    private static final String LOG_FORMAT = "--pretty=format:%H|%s|%an|%ad";
    private static final String DATE_FORMAT = "--date=short";
    private static final int MAX_COMMITS = 10;

    public List<CommitDto> getRecentCommits(String repoPath) {
        File directory = new File(repoPath);

        if (!directory.exists() || !directory.isDirectory()) {
            throw new BusinessException("경로가 존재하지 않습니다: " + repoPath);
        }

        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "git", "-C", repoPath, "log",
                    "-" + MAX_COMMITS,
                    LOG_FORMAT,
                    DATE_FORMAT
            );
            builder.redirectErrorStream(false);

            Process process = builder.start();

            List<CommitDto> commits = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|", 4);
                    if (parts.length == 4) {
                        commits.add(new CommitDto(parts[0], parts[1], parts[2], parts[3]));
                    }
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new BusinessException("Git 저장소를 찾을 수 없습니다: " + repoPath);
            }

            if (commits.isEmpty()) {
                throw new BusinessException("커밋 이력이 없습니다.");
            }

            return commits;

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("Git 명령 실행 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}