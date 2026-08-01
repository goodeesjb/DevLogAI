import apiClient from './apiClient'
import type { AiGenerateRequest, AiGenerateResponse, AiRefineRequest } from '../types'

export const generateLog = async (request: AiGenerateRequest): Promise<AiGenerateResponse> => {
  const response = await apiClient.post<AiGenerateResponse>('/ai/generate', request)
  return response.data
}

export const refineLog = async (request: AiRefineRequest): Promise<AiGenerateResponse> => {
  const response = await apiClient.post<AiGenerateResponse>('/ai/refine', request)
  return response.data
}
