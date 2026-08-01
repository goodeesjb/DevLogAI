export interface Commit {
  hash: string
  message: string
  author: string
  date: string
}

export interface AiGenerateRequest {
  commits: Commit[]
  memo: string
}

export interface AiRefineRequest {
  previousContent: string
  refineRequest: string
}

export interface AiGenerateResponse {
  content: string
}
