import { useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import ReactMarkdown from 'react-markdown'
import { refineLog } from '../services/aiApi'
import type { Commit } from '../types'

interface LocationState {
  content: string
  commits: Commit[]
  memo: string
}

export default function ResultPage() {
  const navigate = useNavigate()
  const location = useLocation()
  const state = location.state as LocationState | null

  const [content, setContent] = useState(state?.content ?? '')
  const [refineRequest, setRefineRequest] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  if (!state) {
    return (
      <div className="flex min-h-screen items-center justify-center bg-gray-50">
        <div className="text-center">
          <p className="text-gray-500">잘못된 접근입니다.</p>
          <button
            onClick={() => navigate('/')}
            className="mt-4 rounded-lg bg-blue-600 px-4 py-2 text-sm text-white hover:bg-blue-700"
          >
            홈으로
          </button>
        </div>
      </div>
    )
  }

  const handleRefine = async () => {
    if (!refineRequest.trim()) {
      setError('수정 요청 내용을 입력해주세요.')
      return
    }
    setError('')
    setLoading(true)
    try {
      const response = await refineLog({
        previousContent: content,
        refineRequest: refineRequest.trim(),
      })
      setContent(response.content)
      setRefineRequest('')
    } catch (e) {
      setError(e instanceof Error ? e.message : '수정 요청에 실패했습니다.')
    } finally {
      setLoading(false)
    }
  }

  const handleDownload = () => {
    const date = new Date().toISOString().slice(0, 10)
    const blob = new Blob([content], { type: 'text/markdown;charset=utf-8' })
    const url = URL.createObjectURL(blob)
    const a = document.createElement('a')
    a.href = url
    a.download = `devlog-${date}.md`
    document.body.appendChild(a)
    a.click()
    document.body.removeChild(a)
    URL.revokeObjectURL(url)
  }

  return (
    <div className="min-h-screen bg-gray-50 px-4 py-10">
      <div className="mx-auto max-w-2xl space-y-6">
        <div className="flex items-center justify-between">
          <h1 className="text-2xl font-bold text-gray-900">개발 기록</h1>
          <button
            onClick={() => navigate('/')}
            className="rounded-lg border border-gray-300 px-3 py-1.5 text-sm text-gray-600 hover:bg-gray-100"
          >
            ← 다시 생성
          </button>
        </div>

        {/* Markdown 결과 */}
        <div className="rounded-xl bg-white p-6 shadow-sm ring-1 ring-gray-200">
          <article className="prose prose-sm max-w-none text-gray-800">
            <ReactMarkdown>{content}</ReactMarkdown>
          </article>
        </div>

        {/* 수정 요청 */}
        <div className="rounded-xl bg-white p-6 shadow-sm ring-1 ring-gray-200 space-y-3">
          <h2 className="text-sm font-medium text-gray-700">수정 요청</h2>
          <textarea
            value={refineRequest}
            onChange={(e) => setRefineRequest(e.target.value)}
            placeholder="예: 배운 점을 더 구체적으로 작성해줘"
            rows={3}
            className="w-full rounded-lg border border-gray-300 px-3 py-2 text-sm outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-100 resize-none"
          />
          {error && <p className="text-sm text-red-500">{error}</p>}
          <button
            onClick={handleRefine}
            disabled={loading}
            className="w-full rounded-lg bg-gray-800 py-2 text-sm font-medium text-white hover:bg-gray-900 disabled:opacity-50"
          >
            {loading ? '수정 중...' : '수정 요청'}
          </button>
        </div>

        {/* MD 저장 */}
        <button
          onClick={handleDownload}
          className="w-full rounded-xl bg-blue-600 py-3 text-sm font-semibold text-white hover:bg-blue-700"
        >
          Markdown 저장
        </button>
      </div>
    </div>
  )
}
