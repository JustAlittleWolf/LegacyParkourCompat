# Local branch cleanup — 2026-10-09

Removed 52 redundant local branch refs from the initial inventory of 119. The remaining 67 include main, unfinished or paused campaign assignments, and refs whose unique content has not been proved preserved. No remote refs were changed.

Deletion groups:

- 30 unattached refs whose tips are ancestors of main: branch-cleanup-candidates.json.
- 10 unattached refs whose changed files match main exactly: entries with an empty Missing array in branch-cleanup-equivalence.json.
- Three completed F005/F007/F012 implementation refs: branch-cleanup-completed.json.
- Four completed, imported memo/review/policy refs: branch-cleanup-imported-completed.json.
- Two completed F3 implementation refs: branch-cleanup-f3-completed.json.
- Three obsolete prep/review refs: branch-cleanup-obsolete-prep-review.json.

Every deleted ref's full commit ID is preserved in those immutable records. Attached completed worktrees were detached at the identical commit; tracked files, untracked files, ignored outputs and caches were preserved. No worktree directories were removed by this cleanup. For the obsolete prep/review group, tracked changes and matching processes were both zero; only .codex and Gradle cache directories were untracked.

Main ancestry was checked immediately before deleting merged refs. For imported refs, every file changed from the merge base was compared by Git blob identity against main immediately before deletion. Worktree root, branch and HEAD were checked before each detach. Active workers and unfinished source-discovery branches were excluded.

The initial broad attached-worktree action was rejected by automatic approval review because it could disrupt active or paused assignments. Subsequent operations were narrowed to completed or obsolete workers and approved after additional status, content and process checks.

All 52 retired tips are now protected under refs/archive/branch-retirement-2026-10-09/<original-branch-name>. retired-history-archive.json records each exact mapping. These refs preserve commit ancestry against garbage collection while keeping the local branch list clear; they do not represent active tasks.

To recover a deleted branch, use its archival ref or recorded full commit ID to recreate the branch. Reattaching a preserved checkout requires checking its current root, HEAD and dirty state first. Finished workers are terminal; resuming unfinished assignments still uses their preserved branch contracts.
