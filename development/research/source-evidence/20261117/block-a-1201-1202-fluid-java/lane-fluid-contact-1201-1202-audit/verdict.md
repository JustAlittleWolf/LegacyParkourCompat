# Independent verdict — LocalPlayer ordinary water/lava contact, 1.20.1 → 1.20.2

## Immutable inputs

- Authorized claim: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-ac27-7712-9259-3041950bc340/lane-fluid-contact-1201-1202/comparison.md`
- Verified claim SHA-256 (exact file bytes): `9544bc0156b101f13e3d21175abe8527fff3fd09fe4f9d82db2d936f5222e586` (matches assigned identity).
- Frozen pre-claim roster: [`source-roster.md`](source-roster.md), SHA-256 `b67fe32d009a9002d0683f241d12390f1a61244f21715b3969394e4810949fc7`.
- Read-only source tree: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/`, exact release directories 1.20.1 and 1.20.2.
- The frozen roster hashes for Entity.java, LivingEntity.java, LocalPlayer.java, FluidState.java, and FluidTags.java match the cited hashes in the authorized claim. No claim hash mismatch was found for those files.

## Verdict by assigned slice

### 1. Body fluid admission and current push — partial; no difference in the frozen core

In paired `Entity.java`, the fluid refresh clears the tag-keyed depth map, processes water before lava, and passes the same coefficients (`0.014` for water; `0.007` in ultra-warm dimensions and `0.0023333333333333335` otherwise for lava). The body scan's unloaded-chunk early return, `0.001` AABB deflation, floor/ceil bounds, half-open X/Y/Z loops, requested-tag check, surface-vs-minY admission, max-depth accumulation, per-cell flow sampling, depth scaling below `0.4`, count averaging, player-vs-non-player normalization distinction, low-horizontal-velocity minimum impulse gate, velocity addition, depth-map write, and boolean return have the same expression and order in both versions. `touchingUnloadedChunk()` likewise has the same inflated-box and inclusive `hasChunksAt` query expression. Paired `FluidState.java` bytes are identical.

The `Entity`-owned admission and push aggregation show no difference in this pair. This is not a complete verdict on effective player current behavior: the frozen roster did not include `Player.isPushedByFluid`, `LevelReader.hasChunksAt`, or `FlowingFluid.getFlow`. The claim's player-specific push eligibility and actual flow-vector generation therefore remain independently unverified. In particular, unchanged Java tag keys do not establish resource tag membership.

### 2. Water/lava vertical response and upward collision exit clearance — no difference in reviewed Java operations

Paired `LivingEntity.java` has the same controlled-local-instance gate, fluid and `canStandOnFluid` checks, water input/drag/depth-strider/dolphins-grace ordering, move-before-drag sequence, `0.8F` vertical multiplier, falling-adjustment call, and upward clearance expression `velocityY + 0.6F - currentY + priorY`; a successful probe writes Y velocity `0.3F`. The lava branch has the same gates and move order, depth-threshold split, low-depth `(0.5, 0.8F, 0.5)` scaling and falling adjustment, high-depth `0.5` vector scaling, and quarter-gravity subtraction. `getFluidFallingAdjustedMovement` preserves the same sprint/no-gravity bypass, `0.003` comparisons, `-0.003` terminal value and gravity/16 subtraction.

Paired `Entity.java` has the same `isFree` displacement-box probe and requires both `noCollision` and absence of liquid. These reviewed caller and `Entity.isFree` operations show no difference. The delegated `Level` collision/liquid-query implementations were not in the frozen roster, so this verdict does not independently establish their deeper collision-provider behavior.

### 3. Depth-cache handoff to held-jump dispatch — no difference in reviewed Java operations

Paired `Entity.baseTick` refreshes fluid state before updating eye-fluid/swimming state. `LivingEntity.tick` reaches `aiStep` after its superclass tick/base-tick path, so the refreshed map precedes the jump-dispatch block. Paired `LivingEntity.aiStep` dispatches the same `serverAiStep` before its fluid-jump block. Paired `LocalPlayer.serverAiStep` copies `input.jumping` into `jumping` under the same controlled-camera gate; the jump block then reads the same lava-or-water stored depth, water submersion predicate, threshold comparisons, ground jump/no-jump-delay path, and `jumpInLiquid` branch. Paired `jumpInLiquid` adds `0.04F` Y velocity. No Java operation/order difference is present in the frozen files.

The claim's wider player/type behavior still depends on the excluded Player-specific fluid eligibility method, and effective `minecraft:water` / `minecraft:lava` membership remains a separate open data dependency.

## Dependency and source-identity limits

- Effective versioned membership of fluid states in `minecraft:water` and `minecraft:lava` is unresolved. The frozen Java evidence establishes keys and call sites only; no tag JSON/data was in scope. Do not treat identical `FluidTags.java` as proving equal membership.
- The frozen roster omitted `Player.java`, `LevelReader.java`, and `FlowingFluid.java`, although the authorized claim cites them. I did not open those additional sources after seeing the claim. Thus the fluid-current behavior is not fully independently audited, and the methods' claim-table hashes were not checked against the local source tree.
- The roster verified that no `.decompile-provenance.json` exists inside either `.../<version>/mojmap/` source directory, but did not inspect release-root metadata files. Its sentence saying upstream pinned provenance was unavailable in the checkout is therefore stronger than the check performed. The claim's ready/provenance/source-manifest identities remain unverified by this reviewer; only the listed Java source-file byte hashes were independently checked.
- These are bounded source verdicts only: not full-pair coverage/freeze, runtime parity, tag applicability acceptance, or implementation acceptance.

## Terminal disposition

The three paired slices show no source-level difference in the reviewed frozen operations. Slices 2 and 3 receive a no-difference verdict within the frozen boundary. Slice 1 is partial because the player fluid-push override and actual flow-vector implementation were excluded from the pre-claim roster. Close these exact dependencies and the tag-data membership before treating the entire assigned fluid-contact behavior as independently accepted.