import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import CommitList from '../components/CommitList'
import { fetchCommits } from '../services/gitApi'
import { generateLog } from '../services/aiApi'
import type { Commit } from '../types'

export default function HomePage() {
  const navigate = useNavigate()

  const [path, setPath] = useState('')
  const [commits, setCommits] = useState<Commit[]>([])
  const [memo, setMemo] = useState('')
  const [error, setError] = useState('')
  const [loadingCommits, setLoadingCommits] = useState(false)
  const [loadingGenerate, setLoadingGenerate] = useState(false)

  const handleFetchCommits = async () => {
    if (!path.trim()) {
      setError('프로젝트 경로를 입력해주세요.')
      return
    }
    setError('')
    setLoadingCommits(true)
    try {
      const data = await fetchCommits(path.trim())
      setCommits(data)
    } catch (e) {
      setError(e instanceof Error ? e.message : '커밋 조회에 실패했습니다.')
      setCommits([])
    } finally {
      setLoadingCommits(false)
    }
  }

  const handleGenerate = async () => {
    if (commits.length === 0) {
      setError('먼저 커밋 목록을 조회해주세요.')
      return
    }
    if (!memo.trim()) {
      setError('개발 메모를 입력해주세요.')
      return
    }
    setError('')
    setLoadingGenerate(true)
    try {
      const response = await generateLog({ commits, memo: memo.trim() })
      navigate('/result', { state: { content: response.content, commits, memo } })
    } catch (e) {
      setError(e instanceof Error ? e.message : 'AI 생성에 실패했습니다.')
    } finally {
      setLoadingGenerate(false)
    }
  }

  return (
    <div className="min-h-screen bg-gray-50 px-4 py-10">
      <div className="mx-auto max-w-2xl space-y-8">
        <div className="text-center">
          <h1 className="text-3xl font-bold text-gray-900">DevLog AI</h1>
          <p className="mt-2 text-sm text-gray-500">
            Git Commit과 메모로 개발 기록을 자동 생성합니다
          </p>
        </div>

        {/* 경로 입력 */}
        <div className="rounded-xl bg-white p-6 shadow-sm ring-1 ring-gray-200">
          <label className="mb-2 block text-sm font-medium text-gray-700">
            프로젝트 경로
          </label>
          <div className="flex gap-2">
            <input
              type="text"
              value={path}
              onChange={(e) => setPath(e.target.value)}
              onKeyDown={(e) => e.key === 'Enter' && handleFetchCommits()}
              placeholder="예: C:\Users\dev\my-project"
              className="flex-1 rounded-lg border border-gray-300 px-3 py-2 text-sm outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-100"
            />
            <button
              onClick={handleFetchCommits}
              disabled={loadingCommits}
              className="rounded-lg bg-blue-600 px-4 py-2 text-sm font-medium text-white hover:bg-blue-700 disabled:opacity-50"
            >
              {loadingCommits ? '조회 중...' : '조회'}
            </button>
          </div>
        </div>

        {/* 커밋 목록 */}
        {commits.length > 0 && (
          <div className="rounded-xl bg-white p-6 shadow-sm ring-1 ring-gray-200">
            <h2 className="mb-3 text-sm font-medium text-gray-700">
              최근 커밋 ({commits.length}개)
            </h2>
            <CommitList commits={commits} />
          </div>
        )}

        {/* 메모 입력 */}
        <div className="rounded-xl bg-white p-6 shadow-sm ring-1 ring-gray-200">
          <label className="mb-2 block text-sm font-medium text-gray-700">
            개발 메모
          </label>
          <textarea
            value={memo}
            onChange={(e) => setMemo(e.target.value)}
            placeholder="오늘 작업하면서 있었던 문제나 특이사항을 자유롭게 적어주세요."
            rows={5}
            className="w-full rounded-lg border border-gray-300 px-3 py-2 text-sm outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-100 resize-none"
          />
        </div>

        {/* 에러 */}
        {error && (
          <p className="text-sm text-red-500">{error}</p>
        )}

        {/* 생성 버튼 */}
        <button
          onClick={handleGenerate}
          disabled={loadingGenerate}
          className="w-full rounded-xl bg-blue-600 py-3 text-sm font-semibold text-white hover:bg-blue-700 disabled:opacity-50"
        >
          {loadingGenerate ? 'AI가 개발 기록을 생성하고 있습니다...' : '개발 기록 생성'}
        </button>
      </div>
    </div>
  )
}
