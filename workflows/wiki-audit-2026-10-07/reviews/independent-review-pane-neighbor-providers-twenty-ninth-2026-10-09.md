# Independent review: pane neighbor-provider slice 29

Verdict: **REQUEST CHANGES** (registration source ranges do not support the stated providers)

## Bound inputs

- Memo: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-twenty-ninth.md`
- Memo SHA-256: `342d7a41b5940c1a695cfca185b1237e6562dcc6dcc159069b17eecb29e1f482`
- Review base: `8f97dda72e0775c1e39990b087433b2e552d5564`
- Exact sources: cited 1.8.9/1.9.4 Feather ready trees and their readiness, source/artifact manifests and verification records.

## Finding

The memo classifies `oak_stairs`, `chest` and `redstone_wire` (IDs 53–55), but its `Block.java` source table cites 1.8.9 lines 944–955 and 1.9.4 lines 838–849. Those ranges are the registrations for IDs 56–58 (`diamond_ore`, `diamond_block`, `crafting_table`), which are the next memo's providers. Correct registration ranges are 1.8.9 `Block.java` 941–943 and 1.9.4 `Block.java` 835–837. Update both rows and recompute the memo snapshot identity/hash after revision. This prevents accepting provider identities whose cited registrations point to different blocks.

The cited provider-class hashes themselves match the ready files and source manifests. Their false old/new pane predicates support the stated no-east-arm outcome if the registrations are corrected. Both ready markers and manifest hashes match; verification records support equal source trees and raw inputs but show one regenerated derived-JAR difference per endpoint, a limitation the memo correctly retains.

## Scope and remaining limits

The change request is limited to the two registration citations and resulting snapshot identity. No runtime validation was performed. Census, all-mask coverage and original derived-JAR equivalence remain open.

## Checkpoint journal

- Before review checkpoint: phase was independent source review; HEAD was `a529490e`; no unrelated dirty path was reported; no `git`, `git-lfs` or `sh` process was running at the process check.
- Inputs: the exact memo above and only its cited endpoint source files and readiness/manifests/verification records.
- After review checkpoint, before report commit: verdict REQUEST CHANGES; source files unchanged; next action is to commit this report by exact path, then review slice 30.
