# MCPK clean re-review candidate identities — 2026-10-08

Status: **mixed clean-review dispositions**. Big Dripleaf and swimming are accepted. The edge-backoff r2 has been superseded by a corrected r3 awaiting follow-up review. The slipperiness source content is accurate; its blob identity is corrected below, with post-correction confirmation pending. The MCPK audit remains partial.

## Review provenance

The prior bounded review is commit `4fa5f57f7e70df066dbf8b9106c103ba8d6acbf2`, with `main` merge `fcfd4480bfa2af6fa4a94a4c5ace863406709321`. It technically accepted the edge-backoff and Big Dripleaf source claims and requested the flying-player/call-order correction for swimming and exact-case Soul Sand path correction. That review recorded contamination in its preliminary repository search, so its dispositions were not formal acceptance. A later clean review is recorded below and supersedes that blocked process status.

The earlier direct MCPK page fetch returned HTTP 403. Claim wording and page revision are carried forward from the lane's preserved catalog. The exact-source comparisons below do not independently verify live MCPK page content or revision metadata.

## Immutable corrected snapshots

All four files were committed together at `78683ba65928004ce8b7b6b9371359164a68d43b`. Paths are repository-relative; blob IDs are Git SHA-1 object IDs and file hashes are SHA-256.

| Finding | Exact commit:path | Git blob | Content SHA-256 | Supersedes |
|---|---|---|---|---|
| 1.16.1→1.16.2 sneak edge-backoff predicate | `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.16.2-step-down-snapshot-r2.md` | `3ea28bac8e53ec5d6be2ee99d98001841102eb02` | `d2dae102961157b575d871918c5112243b2a89eb2b412885131facc77e28ad79` | `8c83d93:workflows/wiki-audit-2026-10-07/mcpk-1.16.2-step-down-snapshot.md` |
| 1.17 Big Dripleaf player-relevant collision timing | `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.17-big-dripleaf-snapshot-r2.md` | `75cc259fe3559a9bc2ce0f8415ea26bc4b9d4b51` | `89c43e33dc7c5d9581950cf1f09e9b9d811e6d210899cee54eac01013f47f6ee` | `f4d5dcf:workflows/wiki-audit-2026-10-07/mcpk-1.17-big-dripleaf-snapshot.md` |
| 1.16.5→1.17.1 swimming entry gate | `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.17-swimming-snapshot-r2.md` | `d166be4e626498950f1181c8bc034829968f1c7b` | `17284668382004ed3101a20c9f810d3b5cdb42721bc3efca8a7d5dfc641b3a32` | `3de1093:workflows/wiki-audit-2026-10-07/mcpk-1.17-swimming-snapshot.md` |
| 1.14.4→1.15.2 slipperiness sample examples | `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.15-slipperiness-examples-snapshot-r2.md` | `96ca89de5ef2b4e84e2fd591296ab620651ee7c3` | `1edaad992b3993fad310d568490421e48f6d4d32d836647f137f71261c67988c` | `bb05061:workflows/wiki-audit-2026-10-07/mcpk-1.15-slipperiness-examples-snapshot.md` |

The original snapshot paths remain unchanged and immutable. The corrected r2 snapshots contain the prior snapshot's blob and SHA-256 identity in their supersession sections.

## Clean-review dispositions and r3 identity

Review report: `6e045f3d50f9248ab329c5bd698caa935e8a9be3:workflows/wiki-audit-2026-10-07/mcpk-clean-rereview-dispositions-2026-10-08.md`.

- **Big Dripleaf:** ACCEPT. Its r2 commit:path, Git blob, and content SHA row above remain unchanged.
- **Swimming:** ACCEPT. Its r2 commit:path, Git blob, and content SHA row above remain unchanged.
- **Slipperiness:** the review found the source content accurate and the SHA-256 matching; it requested only an exact Git blob correction. `git rev-parse 78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.15-slipperiness-examples-snapshot-r2.md` returns `96ca89de5ef2b4e84e2fd591296ab620651ee7c3`. The snapshot bytes and SHA-256 are unchanged. The review report has no later post-fix disposition.
- **Edge-backoff:** REQUEST CHANGES on r2; corrected r3 below awaits follow-up review.

### Edge-backoff r3 immutable identity

| Exact commit:path | Git blob | Content SHA-256 | Supersedes |
|---|---|---|---|
| `10d4937470804e6ffd75b35930a9c3d62044b9be:workflows/wiki-audit-2026-10-07/mcpk-1.16.2-step-down-snapshot-r3.md` | `2c7eeff1b8c1e9ab2913035a647a5f455befa20b` | `182edbae3bed0e878f890c2a98ee402e51d0b8de91e3a954ce8030e6a77e9857` | `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.16.2-step-down-snapshot-r2.md` |

## Corrections captured

- Edge-backoff r2 had one reversed collision-result sentence. The corrected r3 says the airborne branch passes only when the candidate box collides with a non-empty collision shape; it retains the bounded `onGround` → `isAboveGround()` delta and caller/probe scope.
- Big Dripleaf is scoped to a player-relevant contact path, not an exclusive player-only callback. The first lower collision shape follows one 10-block-tick delay.
- Swimming keeps the first-entry/continuation distinction and adds the unchanged flying-player override plus fluid-state → eye-fluid-state → virtual `updateSwimming()` call order.
- Slipperiness cites the exact ready-tree path `net/minecraft/world/level/block/SoulsandBlock.java`, and includes `BlockPos`/`Vec3i` flooring evidence.

This record does not establish broad pair coverage, implementation coverage, runtime parity, or live MCPK page verification.