# Passenger-yaw duplicate worktree audit

## Attachment identities

| Role | Git root | Branch | HEAD | Git / LFS state |
|---|---|---|---|---|
| Clean attachment | `C:/Users/Wolfi/.codex/worktrees/movement-passenger-yaw-2026-10-08` | `feat/movement-passenger-yaw-2026-10-08` | `c6ac4ed05c879b1a342422e329935471476a8e99` | Read-only check: empty porcelain-v2 status; `git lfs status` reported empty staged and unstaged object lists. |
| Duplicate attachment | `D:/Javastuff/LegacyParkourCompat/.task-worktrees/passenger-yaw-task` | `feat/movement-passenger-yaw-2026-10-08` | `c6ac4ed05c879b1a342422e329935471476a8e99` | 674 staged paths, 0 unstaged paths, 0 untracked paths. LFS status failed during `git update-index --refresh`; see evidence. |

The duplicate's populated index is not empty or stale metadata. Its 98,756-byte raw index has SHA-256 `DE002246EC0175902E4B0B7879FCE65DD226F2AEBDAC58BD89B5290AA65B7253`. Its index tree is `986d8bbe119f2a47828dc431d52e46cb317d877f`, exactly the tree of reachable historical commit `32c8b0a7e089e32d2b755a513241ac0e58d1d562` (`Merge branch 'main' into feat/movement-passenger-yaw-2026-10-08`, 2026-10-08 15:49 +0200), which is an ancestor of assigned HEAD `c6ac4ed05c879b1a342422e329935471476a8e99`. The staged index snapshot and working files match that historical committed tree exactly. Thus these staged entries contain no tree content absent from reachable history, while they do represent a real checkout state rewound from the assigned HEAD.

## Path and patch accounting

Compared with duplicate HEAD, the index stages 674 paths. Compared with current local `main` (`d8cf22dc5a47951ddbfd1358adf742b1a059a3e5` at inspection), it differs at 681 paths; the union contains 681 paths: 531 are deleted in both comparisons, 117 modified in both, 26 added in both, and 7 equal to duplicate HEAD but different from current `main`. The complete per-path status matrix is in `passenger-recovery-retirement-evidence/duplicate-path-dispositions-vs-head-and-main.tsv`.

The duplicate working files match the staged index (`git diff` exit 0), and the index differs from duplicate HEAD (`git diff --cached` exit 1). There are no unstaged or untracked changes. Evidence files preserve the byte-for-byte index copy, complete staged binary patch, empty unstaged binary patch, porcelain-v2 status, and full path manifests. The source index SHA-256 was identical before and after the failed LFS status attempt and evidence capture.

The captured artifacts are:

- `passenger-recovery-retirement-evidence/duplicate-index.bin` — raw index snapshot, SHA-256 `DE002246EC0175902E4B0B7879FCE65DD226F2AEBDAC58BD89B5290AA65B7253`.
- `passenger-recovery-retirement-evidence/duplicate-staged.patch` — exact staged patch, SHA-256 `BDCDDDF924811215B469979D111450A2E3C333E0C19B50C787AA3A09255C72BD`.
- `passenger-recovery-retirement-evidence/duplicate-unstaged.patch` — empty patch, SHA-256 `E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855`.
- `passenger-recovery-retirement-evidence/duplicate-status.porcelain-v2.txt`, staged/index-vs-HEAD and index-vs-main name-status manifests, path disposition matrix, and LFS error transcript.

## Recommendation and current disposition

Keep both attachments and the shared branch pending coordinator audit. Do not reset, checkout, clean, detach, delete the branch, or otherwise reconcile the duplicate. Its staged tree is an exact ancestor commit rather than unique unpublished content; if later cleanup is authorized after audit, retain this evidence and act only on the confirmed redundant D attachment while leaving the clean C attachment intact. No code, tests, builds, runtime, push, primary checkout, or branch changes were made for this audit.
