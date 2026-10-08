# MCPK clean re-review candidate identities — 2026-10-08

Status: **candidate identity record only**. None of these four snapshots is formally accepted. Keep the MCPK audit partial pending a clean, isolated reviewer pass.

## Review provenance

The prior bounded review is commit `4fa5f57f7e70df066dbf8b9106c103ba8d6acbf2`, with `main` merge `fcfd4480bfa2af6fa4a94a4c5ace863406709321`. It technically accepted the edge-backoff and Big Dripleaf source claims and requested the flying-player/call-order correction for swimming and exact-case Soul Sand path correction. The review also records contamination in its preliminary repository search, which blocks formal acceptance of all four. The r2 files below are prepared for a clean re-review; do not treat the earlier technical dispositions as final acceptance.

The earlier direct MCPK page fetch returned HTTP 403. Claim wording and page revision are carried forward from the lane's preserved catalog. The exact-source comparisons below do not independently verify live MCPK page content or revision metadata.

## Immutable corrected snapshots

All four files were committed together at `78683ba65928004ce8b7b6b9371359164a68d43b`. Paths are repository-relative; blob IDs are Git SHA-1 object IDs and file hashes are SHA-256.

| Finding | Exact commit:path | Git blob | Content SHA-256 | Supersedes |
|---|---|---|---|---|
| 1.16.1→1.16.2 sneak edge-backoff predicate | `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.16.2-step-down-snapshot-r2.md` | `3ea28bac8e53ec5d6be2ee99d98001841102eb02` | `d2dae102961157b575d871918c5112243b2a89eb2b412885131facc77e28ad79` | `8c83d93:workflows/wiki-audit-2026-10-07/mcpk-1.16.2-step-down-snapshot.md` |
| 1.17 Big Dripleaf player-relevant collision timing | `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.17-big-dripleaf-snapshot-r2.md` | `75cc259fe3559a9bc2ce0f8415ea26bc4b9d4b51` | `89c43e33dc7c5d9581950cf1f09e9b9d811e6d210899cee54eac01013f47f6ee` | `f4d5dcf:workflows/wiki-audit-2026-10-07/mcpk-1.17-big-dripleaf-snapshot.md` |
| 1.16.5→1.17.1 swimming entry gate | `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.17-swimming-snapshot-r2.md` | `d166be4e626498950f1181c8bc034829968f1c7b` | `17284668382004ed3101a20c9f810d3b5cdb42721bc3efca8a7d5dfc641b3a32` | `3de1093:workflows/wiki-audit-2026-10-07/mcpk-1.17-swimming-snapshot.md` |
| 1.14.4→1.15.2 slipperiness sample examples | `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.15-slipperiness-examples-snapshot-r2.md` | `96ca89de5ef2b4e84e2fd591296ab620651ee7c7` | `1edaad992b3993fad310d568490421e48f6d4d32d836647f137f71261c67988c` | `bb05061:workflows/wiki-audit-2026-10-07/mcpk-1.15-slipperiness-examples-snapshot.md` |

The original snapshot paths remain unchanged and immutable. The corrected r2 snapshots contain the prior snapshot's blob and SHA-256 identity in their supersession sections.

## Corrections captured

- Edge-backoff retains its bounded `onGround` → `isAboveGround()` predicate delta and the review's caller/probe closure; it does not claim arbitrary airborne movement.
- Big Dripleaf is scoped to a player-relevant contact path, not an exclusive player-only callback. The first lower collision shape follows one 10-block-tick delay.
- Swimming keeps the first-entry/continuation distinction and adds the unchanged flying-player override plus fluid-state → eye-fluid-state → virtual `updateSwimming()` call order.
- Slipperiness cites the exact ready-tree path `net/minecraft/world/level/block/SoulsandBlock.java`, and includes `BlockPos`/`Vec3i` flooring evidence.

This record does not establish broad pair coverage, implementation coverage, runtime parity, or live MCPK page verification.