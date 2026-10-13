# Source resume ref binding erratum — 2026-10-09

Preserve the original ownership audit at cf6fabe795259125e954bb36446761007482d678 unchanged.

Its 1.21.5--1.21.8 table row omits one character from the checkpoint SHA. The actual retained branch feat/source-discovery-movement-source-1-21-5-1-21-8-resume-2026-10-08 resolves to ec84e77c333b75768d5ce44ce2979ba7f1c9ffd6.

The recorded 39-character token is not a valid full binding. The exact verified commit and branch above supersede that token only; no source behavior, finding acceptance or pair-coverage status changes.

Verified every other recorded 40-character interval checkpoint with git cat-file -e <sha>^{commit}; all 25 resolve. All 26 current interval checkpoint refs resolve either as retained local branches or under refs/archive/branch-retirement-2026-10-09/<original-branch>. Their exact current heads and pending or assigned owners are preserved in the coordinator's authoritative queue.json.

The original audit is a dated snapshot. Completed eye-height corrections and newly resumed owners are tracked in the current queue instead of repeating stale next-action descriptions from that snapshot.
