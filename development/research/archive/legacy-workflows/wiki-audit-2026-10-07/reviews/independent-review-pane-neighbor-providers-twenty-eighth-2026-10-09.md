# Independent review: pane neighbor-provider slice 28

Verdict: **ACCEPT** (bounded source classification and witness)

## Bound inputs

- Memo: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-twenty-eighth.md`
- Memo SHA-256: `c1b2efc95877bbbd441bc79e1e1695735a21e4d213d44f37cad3ec96a43f890`
- Review base: `8f97dda72e0775c1e39990b087433b2e552d5564`
- Exact sources: cited 1.8.9/1.9.4 Feather ready trees and their readiness, source/artifact manifests and verification records.

## Independent checks

Ready markers and manifest hashes match the memo. Every cited source hash matches its exact ready-tree file and source-manifest entry. Verification records confirm equal source trees and raw inputs, while listing a regenerated derived remapped-JAR difference at each endpoint; the memo correctly preserves the original derived-JAR equivalence limitation.

Registrations 50–52 are correctly identified in the cited `Block.java` ranges. Torch and Fire return false from the old/new pane-relevant predicates, so neither adds the east arm. The Mob Spawner registration constructs the same `MobSpawnerBlock(Material.STONE)` on both sides. In 1.8.9 its `isSolidRender()` override returns false; base construction caches that virtual result into `opaqueCube`, which `PaneBlock.shouldConnectTo` reads via `isOpaque()`. In 1.9.4 the class still returns false from `isSolidRender(BlockState)`, but has no `isCube(BlockState)` override, so the base cube result is true; `PaneBlock.shouldConnectTo` reads `defaultState().isCube()`. The pane resolver tests each cardinal neighbor. Thus, with only Mob Spawner east of the pane, the E arm changes from absent to present as claimed.

## Scope and remaining limits

The witness proves the predicate replacement's bounded registration-level effect; it does not close the census or all reachable masks. No runtime validation was performed, and derived-JAR equivalence remains open.

## Checkpoint journal

- Before review checkpoint: phase was independent source review; HEAD was `2139be28`; no unrelated dirty path was reported; no `git`, `git-lfs` or `sh` process was running at the process check.
- Inputs: the exact memo above and only its cited endpoint source files and readiness/manifests/verification records.
- After review checkpoint, before report commit: verdict ACCEPT; source files unchanged; next action is to commit this report by exact path, then review slice 29.
