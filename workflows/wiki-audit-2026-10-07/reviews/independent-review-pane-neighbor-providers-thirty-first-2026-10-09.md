# Independent review: pane neighbor-provider slice 31

Verdict: **ACCEPT** (bounded source classification only)

## Bound inputs

- Memo: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-thirty-first.md`
- Memo SHA-256: `020cf11f42fe65d09005de4e47f8410d04ebe54700a692e52739727361bce448`
- Review base: `8f97dda72e0775c1e39990b087433b2e552d5564`
- Exact sources: cited 1.8.9/1.9.4 Feather ready trees and their readiness, source/artifact manifests and verification records.

## Independent checks

Ready markers and manifest hashes match the memo. Every cited source hash matches its exact ready-tree file and source-manifest entry. Verification records report identical source trees and raw inputs, plus one regenerated derived remapped-JAR difference per endpoint; the memo correctly does not assert original-JAR equivalence.

The `Block.java` registration ranges correctly identify wheat, farmland and furnace (IDs 59–61). Wheat inherits the explicit false plant predicates on both sides. Farmland's old and new solid-render/cube predicates are false; the 1.9.4 opacity assignment is a separate opacity field and does not make `defaultState().isCube()` true. Furnace uses the base true predicates. Thus wheat and farmland do not add the east arm, while furnace does, on both sides. The stated outcomes and no-change conclusion follow.

## Scope and remaining limits

This accepts the three registration/default predicate outcomes and one-east-neighbor witness only. Census and reachable-mask closure remain open; no runtime validation was performed.

## Checkpoint journal

- Before review checkpoint: phase was independent source review; HEAD was `6d4bcaf8`; no unrelated dirty path was reported; no `git`, `git-lfs` or `sh` process was running at the process check.
- Inputs: the exact memo above and only its cited endpoint source files and readiness/manifests/verification records.
- After review checkpoint, before report commit: verdict ACCEPT; sources unchanged; next action is to commit this report by exact path, then review slice 32.
