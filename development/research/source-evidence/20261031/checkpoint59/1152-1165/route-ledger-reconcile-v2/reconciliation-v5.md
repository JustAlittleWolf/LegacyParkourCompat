# Reconciled movement-route ledger v5 — 1.15.2 → 1.16.5

## Disposition

This source-only overlay reconciles exact member evidence and independent reviews with the corrected ordinal path ledger. It preserves all 2,617 changed-path rows and corrected route IDs. This ledger does not claim pair freeze, implementation acceptance beyond individually accepted findings, or runtime parity.

The route crosswalk remains **188 movement-reachable, 584 no-movement-dependency, and 1,845 excluded**. Mechanical disposition counts are **354 closed in scope, 425 open non-movement route rows, 1,838 unchanged** (2,617 total). There are **zero movement-reachable OPEN/PARTIAL rows** in this reconciliation. The open rows are non-movement route/dependency annotation tasks; they are not movement source blockers.

## Newly closed movement dependency

`net/minecraft/client/player/LocalPlayer.java` is now `CLOSED_IN_SCOPE` for its last listed dependency: `updateAutoJump`'s applicability to the canonical full-block collision collector fast-path difference. The combined independent review has verdict **ACCEPT** and SHA-256 `9acfc37c8a280dc5886171946043027370da9608d7bcc7e9c691d42ddfa11085`. It binds the current main proof `fastpath-exclusion-proof.md` SHA-256 `9d9a396c672fb2dc8a3102fc57f8e2ba7268b851c1cc7795ded769306bd8d87b`, low-pose supplement `autojump-pose-scope/report.md` SHA-256 `f146e2ae8cdd496d455aa0c5d637cfd6df7a150e312314ce3c2906ab2de64357`, and collector report SHA-256 `3a67f32fdc97d048cd31a1ee735ff5668f486c15d2c44583dc1605cc6bd3eb7c`.

For standing, crouching, fall-flying, swimming and spin-attack dimensions, a tested side-segment hit implies query overlap well above the sub-`1.0E-7` rounded-grid fringe. For sleeping/dying dimensions (height `0.2`), the scaled vertical endpoints cannot both lie on any supported `findBits` grid, so the older rounded-grid branch is bypassed; the exact AABB fallback retains overlap. Thus this particular full-block fast-path discrepancy cannot change the tested `updateAutoJump` segment result across vanilla poses.

This is a caller/dependency-scoped closure only. It does not imply equivalence for every `updateAutoJump` block provider, entity collision, state production, or other route. Those remain bounded by their existing exact evidence, including the separately closed EntityGetter and fixed-state provider routes. No whole-LocalPlayer-class proof or pair freeze is claimed.

## Carried route closures and exclusions

The exact-member closures recorded in v4 and earlier overlays remain carried forward: accepted client respawn receiver sprint-attribute copy, Level/LevelAccessor/LevelReader movement-facing members, LocalPlayer-source EntityGetter semantics, movement-facing tick/response members, and accepted suffocation/provider findings. The Wall/IronBars erratum continues to exclude generated-state connection predicates while retaining the equal-supplied-state collision-provider closures. FishingHook rows remain classified as direct movement-reachable client impulse routes under equal synchronized hook/owner inputs.

Server/state producers, packet cadence/timing, input-device/configuration plumbing, logical-server movement, and unrelated same-class methods are outside these paired source claims. Equal raw logical resource/tag inputs are assumed; producer generation is not a movement-source closure prerequisite.

## Artifacts

- `reconciled-route-ledger-v5.tsv`: full 2,617-row overlay.
- `remaining-movement-open-v5.tsv`: header only; no movement-reachable open/partial rows.
- `supplemental-evidence-hashes-v5.tsv`: prior supplemental pins plus the current proof, supplement, combined ACCEPT review, and collector report.

No product, wiki, backlog, build, test, runtime, decompilation, or server files were changed or used.
