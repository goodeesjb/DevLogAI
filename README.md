# DevLog AI

> AI를 활용하여 Git Commit과 개발 메모를 분석하고 개발 기록을 자동
> 생성하는 웹 서비스

## 1. 프로젝트 개요

개발자가 매일 반복하는 개발 기록(회고, 노션, 블로그 초안 등) 작성을
자동화하는 것이 목표입니다.

## 2. 문제 정의

-   Git Commit만으로 하루 작업을 설명하기 어렵다.
-   개발 메모를 사람이 다시 정리해야 한다.
-   반복적인 문서 작성에 시간이 소요된다.

## 3. MVP 목표

프로젝트 폴더를 선택하면 최근 Git Commit을 자동으로 읽고, 사용자가 짧은
메모를 입력하면 AI가 개발 기록을 Markdown으로 생성한다.

## 4. 핵심 기능

### 프로젝트 선택

-   Git Repository 폴더 선택

### Git Commit 자동 조회

-   최근 Commit 10개 조회

### 개발 메모 입력

-   오늘 있었던 문제나 메모 입력

### AI 개발 기록 생성

생성 항목 - 오늘 한 일 - 어려웠던 점 - 해결 과정 - 배운 점 - 다음 작업

### 수정 요청

생성 결과가 마음에 들지 않으면 AI에게 수정 요청

### Markdown 저장

생성 결과를 md 파일로 저장

## 5. 사용자 플로우

1.  프로젝트 폴더 선택
2.  Git Commit 자동 조회
3.  개발 메모 입력
4.  AI 분석
5.  개발 기록 생성
6.  수정 요청(선택)
7.  Markdown 저장

## 6. 화면 구성

### Home

-   프로젝트 선택
-   Commit 목록
-   메모 입력
-   생성 버튼

### Result

-   AI 결과
-   다시 생성
-   수정 요청
-   Markdown 저장

## 7. 시스템 아키텍처

React → Spring Boot → Git Commit 조회 → Gemini API → Markdown 생성 →
React 출력

## 8. 기술 스택

### Frontend

-   React
-   TypeScript
-   Tailwind CSS

### Backend

-   Spring Boot
-   Java 21

### AI

-   Gemini API

### Library

-   JGit
-   CommonMark

## 9. 프로젝트 구조

frontend/ - pages - components - services

backend/ - controller - service - ai - git - dto

## 10. 구현 순서

-   [ ] React 프로젝트 생성
-   [ ] Spring Boot 프로젝트 생성
-   [ ] Gemini API 연동
-   [ ] Git Commit 조회 기능
-   [ ] 메모 입력 기능
-   [ ] AI Prompt 작성
-   [ ] 결과 출력
-   [ ] Markdown 저장
-   [ ] 수정 요청 기능
-   [ ] UI 개선

## 11. AI Prompt 초안

너는 시니어 개발자이다. 아래 Git Commit과 개발 메모를 분석하여 Markdown
형식의 개발 기록을 작성한다.

반드시 포함 1. 오늘 한 일 2. 어려웠던 점 3. 해결 과정 4. 배운 점 5. 다음
작업

## 12. AI 활용 전략

-   ChatGPT : 기획 및 프롬프트 개선
-   Claude Code : 구현 및 리팩터링
-   Gemini API : 개발 기록 생성

## 13. 향후 확장

-   PDF 저장
-   Notion 업로드
-   README 자동 생성
-   주간 개발 리포트
-   GitHub API 연동

## 14. 과제 평가 포인트

-   반복적인 개발 기록 작성 자동화
-   AI와 협업하는 구조
-   AI 결과 수정 및 재생성 지원
-   실제 동작 가능한 MVP 구현
