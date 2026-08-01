import type { Commit } from '../types'

interface Props {
  commits: Commit[]
}

export default function CommitList({ commits }: Props) {
  return (
    <ul className="divide-y divide-gray-200 rounded-lg border border-gray-200 bg-white">
      {commits.map((commit) => (
        <li key={commit.hash} className="flex items-start gap-3 px-4 py-3">
          <span className="mt-0.5 shrink-0 font-mono text-xs text-gray-400">
            {commit.hash.slice(0, 7)}
          </span>
          <span className="flex-1 text-sm text-gray-800">{commit.message}</span>
          <span className="shrink-0 text-xs text-gray-400">{commit.date}</span>
        </li>
      ))}
    </ul>
  )
}
