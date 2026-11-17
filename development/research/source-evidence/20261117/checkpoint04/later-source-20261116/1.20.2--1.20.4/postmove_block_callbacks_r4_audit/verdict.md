# Independent bound source verdict: post-move block callbacks r4

Verdict: **ACCEPTED — bounded NO_DIFF claim is supported for the assigned normal-player post-move block-contact slice.** This verdict accepts only the source claim in the bound candidate below. It does not freeze or close the 1.20.2--1.20.4 pair and does not establish implementation or runtime parity.

## Bound candidate

- Candidate: `build/later-source-20261116/1.20.2--1.20.4/postmove_block_callbacks_revision_r4/report-r4.md`
- Candidate SHA-256: `E7DD4BD4CA0AEB4F674565076E6595C44CE25CE1A52A7E6DA2D865B13E545213`

## Source identity checked

The canonical read-only Mojmap decompilations identify exact versions 1.20.2 and 1.20.4. Ready markers and provenance agree on version, mapping, client artifact, and the exact `decompileMinecraft` batch (`1.20.1,1.20.2,1.20.4,1.20.6`, Mojmap). Ready-marker manifest hashes match the source and artifact manifests on disk. Source file hashes cited in the candidate were recomputed against the canonical trees, including both `Entity.java` files and every callback file named in the report.

| Version | Source manifest SHA-256 | Artifact manifest SHA-256 | Client SHA-256 |
|---|---|---|---|
| 1.20.2 / Mojmap | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` | `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54` | `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda` |
| 1.20.4 / Mojmap | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` | `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838` |

## Review basis

- In both `Entity.move` bodies, `noPhysics` branches directly to position assignment and bypasses physics, the piston limiter, the stuck multiplier consumer, and post-move block callbacks. Otherwise, a `MoverType.PISTON` request first passes through `limitPistonMovement`; if its limited vector equals `Vec3.ZERO`, the method returns before the multiplier gate. Only after these branches does it test `stuckSpeedMultiplier.lengthSqr() > 1.0E-7`. On that gate it component-multiplies requested movement, clears the stored vector, and zeroes delta movement, in that order.
- After resolved movement, both versions preserve the callback sequence: conditional `updateEntityAfterFallOn`, grounded `stepOn`, then movement-emission bookkeeping, `tryCheckInsideBlocks`, and `getBlockSpeedFactor` application. `checkInsideBlocks` uses the same AABB min/max `1.0E-7` insets, `hasChunksAt` gate, ascending x/y/z inclusive loops, alive check, and per-cell `entityInside` then `onInsideBlock` order. Both base `onInsideBlock` implementations are empty; `LocalPlayer.java` is byte-identical across endpoints and local players do not use the server-player override.
- The paired movement-affecting callbacks and consumer evidence named in the candidate are consistent with canonical source: powder snow and sweet berry assign the same stuck vectors under the same gates; web assigns the same vector; honey slide and bubble-column player-response paths match; slime bounce callbacks match. The changed button callback is server-side arrow activation. The removed stair delegation reaches ordinary registered base blocks in vanilla registration, while the cited slime velocity callback itself is unchanged. Other listed callback changes have no direct player-movement write in this bounded slice.
- The narrowed persistence statement is accurate: a post-move callback can assign the shared multiplier for a later movement call, but that call consumes it only if it bypasses `noPhysics`, does not take the zero-limited piston return, and passes the squared-length threshold. An intervening eligible `makeStuckInBlock` callback can overwrite the shared vector before consumption. The report does not claim the next arbitrary movement call necessarily consumes it.

## Limits retained

The verdict preserves the report's explicit gates: provider-specific collision geometry and shape dependencies, wider callback categories, packet/knockback and entity-entity collision paths, modded callback overrides, and mod-created stairs remain outside this bounded review. Pair-wide terminal-slice coverage and independent full-pair closure are still required. No implementation, build, test, decompile, or runtime behavior was examined.
