# Piston callback origin comparison: 1.21.3 → 26.2

Status: exact-source comparison completed; source-only finding, not an independent acceptance, implementation, or runtime-parity verdict.

## Inputs and integrity

- Older source: exact Minecraft `1.21.3`, Mojmap, `decompiled_minecraft/1.21.3/mojmap/`.
  - `mojmap.ready.json` SHA-256 `72385aa99d249a56d742b9c07823c1155c74c39dda1fbc6eacc12c980cd12e02`; identifies `versionId=1.21.3`, `mapping=mojmap`, status `ready`, 5,655 source files, source-manifest SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`.
  - `mojmap.provenance.json` SHA-256 `24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7`; records exact run `1.21.1,1.21.3,1.21.4,1.21.5`, Mojmap, Java 25.0.3+9-LTS, Vineflower 1.12.0, Gradle 9.7.1, and source generation working tree.
  - `mojmap.sources.sha256` SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`.
- Newer source: exact Minecraft `26.2`, published unobfuscated names, `decompiled_minecraft/26.2/unobfuscated/`.
  - `unobfuscated.ready.json` SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`; identifies `versionId=26.2`, `mapping=unobfuscated`, status `ready`, 7,055 source files, source-manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.
  - `unobfuscated.provenance.json` SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`; records exact run `26.2`, unobfuscated, Java 25.0.3+9-LTS, Vineflower 1.12.0, Gradle 9.7.1.
  - `unobfuscated.sources.sha256` SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.
- The exact source hashes below were recomputed from files and match their declared source-manifest entries.

| Source | 1.21.3 Mojmap SHA-256 | 26.2 unobfuscated SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| `net/minecraft/world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| `net/minecraft/world/entity/LivingEntity.java` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java` | `426442149f89a28fc5973a52b8156aec7c18d76faf8b62eecae9f120b2fdba16` | `706bab1a13ba99bbd334aad853b99f72df58fce5b6b67d30d41f902b63dd8e06` |

## Compared operation and source facts

1.21.3, `PistonMovingBlockEntity.moveEntityByPiston` (lines 182–186): set piston noclip direction, call `Entity.move(PISTON, delta)`, call no-argument `applyEffectsFromBlocks()`, then clear the thread-local direction. The call is unconditional after `move` returns. `Entity.applyEffectsFromBlocks()` (Entity lines 736–767) supplies `oldPosition()` as `from` and current `position` as `to`. Under its `isAffectedByBlocks()` gate (`!isRemoved() && !noPhysics`), it performs the grounded `stepOn` callback, appends that one span to `movementThisTick`, scans the accumulated list, then clears both `movementThisTick` and `blocksInside`. For `LocalPlayer`, the 1.21.3 source tree's only `recordMovementThroughBlocks` call sites are in `ServerGamePacketListenerImpl`; there is no local-player client call site, so its ordinary list is empty absent external modification. The source-backed default branch for this piston call is therefore the prior-tick `oldPosition()` to post-piston current position span.

26.2, `PistonMovingBlockEntity.moveEntityByPiston` (lines 192–199): set noclip, sample `previousPos = entity.position()` immediately before the move, call `move(PISTON, delta)`, call explicit `applyEffectsFromBlocks(previousPos, entity.position())`, unconditionally call `removeLatestMovementRecording()`, then clear noclip. The explicit overload wraps exactly that pair in a one-element movement list (Entity lines 945–975); its block-effects gate is again `!isRemoved() && !noPhysics`. The inside-block scan input is thus the immediate pre-piston sample to the resulting position, including an equal-position pair if the move did not change position. No source branch skips the explicit callback for a zero displacement. The grounded `stepOn` callback remains separately based on the current support block in both versions.

The ordinary 26.2 path is separately queue-backed (Entity lines 903–975). `move` records a movement in the non-`noPhysics` branch when its movement acceptance condition is met (lines 711–755). No-argument `applyEffectsFromBlocks()` copies `movementThisTick` to `finalMovementsThisTick`, clears the live queue, falls back to `oldPosition()` → current position when the copy is empty, or appends a final connector when the last recorded endpoint differs from current position by squared distance greater than `9.9999994E-11F`; it then applies that copied/augmented list. `removeLatestMovementRecording()` removes the latest live record only when that queue is nonempty (lines 920–938).

## Difference and bounded movement meaning

The piston-triggered environmental scan changes from a no-argument, tick-origin-to-current scan in 1.21.3 to an explicit immediate-piston-origin-to-current scan in 26.2. This is a source-confirmed change to the span supplied to inside-block scanning and related block callbacks; it narrows the piston callback input to the piston displacement instead of reusing the full tick span. In 26.2 the piston movement record is then removed from the live ordinary queue, so a later ordinary scan does not include that piston segment when the normal movement record was added. These claims concern callback inputs only; they do not predict block-specific outcomes or tick scheduling.

Zero/no-record branch: both versions' piston helper invokes block effects even after `move` returns. In 26.2 `Entity.move` returns before adding a record when piston limiting produces `Vec3.ZERO` (lines 719–724); its `noPhysics` path also bypasses the record addition (lines 712–718). The explicit callback is still called (and its `isAffectedByBlocks` gate suppresses processing while `noPhysics` is true), after which `removeLatestMovementRecording()` still runs. Therefore, if a prior ordinary record is queued when a piston move adds none, that call removes the prior record; this follows from the source branches and is not a runtime observation. In the normal recorded-move branch it removes the just-added piston record. In 1.21.3 the no-argument scan clears the accumulated queue either way.

The inherited ordinary `LivingEntity.aiStep` gate remains logical-client aware: 1.21.3 calls the no-argument scan when `!level.isClientSide() || isControlledByLocalInstance()` (LivingEntity lines 2774–2776); 26.2 uses `!level.isClientSide() || isLocalInstanceAuthoritative()` (lines 3143–3145). `LocalPlayer` extends `AbstractClientPlayer` in both versions and its `move` override delegates to `super.move`; this comparison does not inspect unrelated server-player or non-player behavior.

## Coverage and limits

Compared the paired piston helper, inherited local-player ordinary callback gate, entity move record insertion, both no-argument and explicit block-effect overloads, queue copy/clear/removal, and `isAffectedByBlocks()` guard. Exact source and hashes establish the source-level span difference and the no-record queue branch. Not assessed: runtime callback outcomes, tick ordering, block-specific behavior, source lineages outside these two releases, or parity. The exact change-introduction patch is unknown within `1.21.3`–`26.2`.