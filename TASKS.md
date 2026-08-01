# DevLogAI Task 목록

## Task Table

| Task ID | 목표 | 구현 내용 | 완료 조건 | 의존 Task | 상태 |
|---------|------|-----------|-----------|-----------|------|
| T01 | 백엔드 의존성 설정 | Gemini API(RestClient), build.gradle 정리 | 빌드 성공 | - | ✅ |
| T02 | Git Commit 조회 | ProcessBuilder로 `git log` 실행, 최근 커밋 10개 반환 (`GitService`, `CommitDto`) | `/api/git/commits?path=...` → 커밋 목록 JSON 반환 | T01 | ✅ |
| T03 | Gemini API 클라이언트 | `GeminiClient` 구현, API Key 환경변수 관리, Timeout/예외 처리 | Gemini 호출 성공, API Key 코드 노출 없음 | T01 | ✅ |
| T04 | AI 개발 기록 생성 | 커밋 목록 + 메모 → 프롬프트 조합 → Gemini 호출 → Markdown 반환 (`AiService`) | `/api/ai/generate` POST → Markdown 텍스트 반환 | T02, T03 | ✅ |
| T05 | 수정 요청 기능 | 기존 결과 + 수정 요청 메시지 → Gemini 재호출 | `/api/ai/refine` POST → 수정된 Markdown 반환 | T04 | ✅ |
| T06 | Global Exception Handler | Validation/비즈니스/외부API 예외 통일된 형식으로 처리 | 에러 시 `{ status, message }` 형식 반환 | T01 | ✅ |
| T07 | 프론트엔드 기본 설정 | Tailwind CSS, React Router, Axios 설치 및 설정, Axios 공통 인스턴스 | `npm run dev` 정상 실행, Tailwind 적용 확인 | - | ✅ |
| T08 | Home 페이지 | 폴더 경로 입력, 커밋 목록 조회/표시, 메모 입력, 생성 버튼 | 경로 입력 → 커밋 목록 렌더링 → 메모 입력 → 생성 버튼 동작 | T07 | ✅ |
| T09 | Result 페이지 | AI 결과 Markdown 렌더링, 다시 생성, 수정 요청 입력, MD 파일 저장 | 결과 표시 → 수정 요청 → 재생성 → 파일 다운로드 동작 | T08 | ✅ |
| T10 | API 서비스 레이어 | `gitApi.ts`, `aiApi.ts` 분리, 에러 핸들링, 타입 정의 | API 호출 성공/실패 처리, TypeScript 오류 없음 | T07 | ✅ |

## 개발 순서

1. T01 — 백엔드 의존성
2. T06 — Exception Handler
3. T02 — Git Commit 조회
4. T03 — Gemini Client (T02와 병렬 가능)
5. T04 — AI 생성
6. T05 — 수정 요청
7. T07 — 프론트 기본 설정 (백엔드 작업 중 병렬 가능)
8. T10 — API 서비스 레이어
9. T08 — Home 페이지
10. T09 — Result 페이지

## 상태 범례

- ⬜ 미시작
- 🔄 진행 중
- ✅ 완료