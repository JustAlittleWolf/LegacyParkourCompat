# Pane-provider checkpoint index recovery record

This records the assigned worktree state before the bounded census continuation. It preserves the exact staged deletion and its unstaged reversal without changing the accepted checkpoint content.

- Worktree: `C:/Users/Wolfi/.codex/worktrees/pane-providers-third-2026-10-09/LegacyParkourCompat`
- Branch and HEAD: `fix/pane-neighbor-providers-third-2026-10-09`, commit `57eb11151f08bde4adaab46d16b4d8e5c11b058c`.
- Checkpoint path: `workflows/wiki-audit-2026-10-07/CHECKPOINT.md`.
- Initial index blob: `ea2ce69015666d0749eca4036e19eb1458c187db` (the frozen checkpoint blob at `8f97dda72e0775c1e39990b087433b2e552d5564`).
- Committed and working-copy blob: `20d1db18de7c06fc7517a9942effe85c1b1eaf1e` (the checkpoint at `57eb11151f08bde4adaab46d16b4d8e5c11b058c`); working-copy raw SHA-256 `62c8a1c8eedd559842cd0a244b02cbb882767288927a1f111be54bb7ce929b53`.
- Initial status was `MM` on this path. The staged diff deleted the 12-line `Pane-provider integration/review package` subsection; the unstaged diff restored that exact subsection. No other path was dirty.
- Exact recovery comparison: `git diff --no-ext-diff 8f97dda72e0775c1e39990b087433b2e552d5564 57eb11151f08bde4adaab46d16b4d8e5c11b058c -- workflows/wiki-audit-2026-10-07/CHECKPOINT.md`.

After this note is recorded and the committed/working-copy blob equality is verified, reconcile only the checkpoint index entry to the working copy. Keep the preserved accepted checkpoint section; do not restore the staged deletion.
