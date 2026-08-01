package com.devlogai.backend.git.dto;

public record CommitDto(String hash, String message, String author, String date) {
}