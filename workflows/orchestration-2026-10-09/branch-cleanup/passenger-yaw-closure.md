# Passenger-yaw branch closure — 2026-10-09

## Preserved duplicate-checkout evidence

The shared branch originally resolved to c6ac4ed05c879b1a342422e329935471476a8e99 in both attachments. The C attachment was clean. The D attachment had 674 staged paths, no unstaged or untracked paths, and a 98,756-byte index whose tree was exactly the existing ancestor commit 32c8b0a7e089e32d2b755a513241ac0e58d1d562. The D working files matched that staged tree. This was a populated historical snapshot, with no novel staged blobs.

The original D index bytes and exact staged/unstaged patches are preserved under passenger-recovery-retirement-evidence/ in the dedicated report branch. Their bindings are:

- Raw index SHA-256: DE002246EC0175902E4B0B7879FCE65DD226F2AEBDAC58BD89B5290AA65B7253.
- Staged patch SHA-256: BDCDDDF924811215B469979D111450A2E3C333E0C19B50C787AA3A09255C72BD.
- Empty unstaged patch SHA-256: E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855.
- Before/after tracked-path manifest SHA-256: 31F497C99676743AE61059A26369D2EF6A63B9F487C12BA38DF3325B6F4B6332.

The manifest covers the 1,151-path union of the original c6 and historical 32c8 trees. It records 620 present files and 531 paths already absent in D before the transition. Before and after rows are identical; no checkout, reset, clean, or working-tree update operation was used. D’s index remains byte-identical to the preserved raw index and resolves to tree 986d8bbe119f2a47828dc431d52e46cb317d877f.

## Branch identity and archive

Original branch: feat/movement-passenger-yaw-2026-10-08 at c6ac4ed05c879b1a342422e329935471476a8e99.

The merge-base with current main is bb67d465ae02ea0438eca1dad01792a86c1a3f33. All four paths changed by c6 from that base have identical blob IDs in current main; exact bindings are in passenger-recovery-retirement-evidence/c6-path-blob-bindings.tsv. The comparison was repeated after main advanced to 9c61db3178bf801807308f0402ba59a5f080cf6e.

The branch ancestry is retained at refs/archive/task-closure-2026-10-09/feat/movement-passenger-yaw-2026-10-08, resolving to c6. The original refs/heads/feat/movement-passenger-yaw-2026-10-08 ref is deleted. No source or recovery file was removed.

## Retained attachments

- C:/Users/Wolfi/.codex/worktrees/movement-passenger-yaw-2026-10-08 remains at detached HEAD c6ac4ed05c879b1a342422e329935471476a8e99.
- D:/Javastuff/LegacyParkourCompat/.task-worktrees/passenger-yaw-task remains at detached HEAD 32c8b0a7e089e32d2b755a513241ac0e58d1d562, with index tree 986d8bbe119f2a47828dc431d52e46cb317d877f.

Both worktree directories remain in place; both report clean Git and Git LFS status. C’s files remain at their original c6 checkout state. D’s files and pre-existing absences match the committed before-manifest exactly after the HEAD/index-only transition.

The report branch history retains the preservation checkpoint 18c3a4e60acd1453687063468416553267bb6588, the pre-transition manifest checkpoint b8647d947c7c2ce8352984646687f8534757a445, and the subsequent evidence bindings. The archive ref refs/archive/recovery-evidence-2026-10-09/passenger-duplicate-checkout is created after this closure record commit and points to the final report-branch tip, retaining the full evidence ancestry without adding the large raw artifacts to main.

No build, tests, game/runtime, push, or primary main checkout/index operation was performed. The current local main ref remains 9c61db3178bf801807308f0402ba59a5f080cf6e; its latest commits were merged into this dedicated report branch before this record.
