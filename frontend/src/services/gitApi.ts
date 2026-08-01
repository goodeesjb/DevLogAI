import apiClient from './apiClient'
import type { Commit } from '../types'

export const fetchCommits = async (path: string): Promise<Commit[]> => {
  const response = await apiClient.get<Commit[]>('/git/commits', {
    params: { path },
  })
  return response.data
}
