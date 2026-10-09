# Independent review: pane neighbor-provider slice 32

Verdict: **ACCEPT** (bounded source classification only)

## Bound inputs

- Memo: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-thirty-second.md`
- Memo SHA-256: `43f7217359b08e54e8cb68be816698f5f315d0d40205ef51b5debc78ca38fd9a`
- Review base: `8f97dda72e0775c1e39990b087433b2e552d5564`
- Exact sources: cited 1.8.9/1.9.4 Feather ready trees and their readiness, source/artifact manifests and verification records.

## Independent checks

Ready markers and manifest hashes match the memo. All cited source hashes match exact ready-tree files and their source-manifest entries. Verification records support identical source trees and raw input artifacts, while recording a regenerated derived remapped-JAR difference per endpoint; the memo leaves original-JAR equivalence open.

The stated `Block.java` registration ranges correctly identify lit furnace, standing sign and wooden door (IDs 62–64). The lit furnace inherits true base predicates. Standing sign inherits SignBlock's false predicates, and wooden door returns false from its solid-render and cube predicates. The 1.8.9 cached opacity and 1.9.4 default-state cube outcomes therefore agree for each registration. Only lit furnace supplies the east arm in the one-east-neighbor arrangement; the other two do not. No changed mask is supported.

## Scope and remaining limits

This accepts only the three bounded registration predicates and one directional witness. Full census and reachable-mask closure remain open; no runtime validation was performed.

## Checkpoint journal

- Before review checkpoint: phase was independent source review; HEAD was `ab8d68fb`; no unrelated dirty path was reported; no `git`, `git-lfs` or `sh` process was running at the process check.
- Inputs: the exact memo above and only its cited endpoint source files and readiness/manifests/verification records.
- After review checkpoint, before report commit: verdict ACCEPT; source files unchanged; next action is to commit this report by exact path, then audit the eight report identities and branch/worktree state.
