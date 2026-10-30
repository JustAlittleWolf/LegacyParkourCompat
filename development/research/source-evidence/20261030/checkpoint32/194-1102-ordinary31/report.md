# 1.9.4 → 1.10.2 ordinary local-player movement slice

## Evidence identity and provenance

Read-only canonical roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.9.4\ornithe-feather\` and `...\1.10.2\ornithe-feather\`. Both `ready.json` markers identify exact releases and `ornithe-feather` / Vineflower output. Parent `ornithe-feather.provenance.json` records separate exact-version runs, Java 25.0.3+9-LTS, Gradle 9.7.1, Vineflower 1.12.0, and Feather Gen2 mapping artifacts (`feather-gen2-1.9.4+build.2` and `feather-gen2-1.10.2+build.2`). The ready source-manifest SHA-256 values are `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` and `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`. The mapping JAR / Tiny-map SHA-256 pairs from the parent artifact manifests are `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125` / `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027` for 1.9.4 and `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0` / `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1` for 1.10.2. The exact-pair verification manifests report all 1,819 and 1,845 source files match their independently decompiled trees, with zero source-file differences; they note only regenerated intermediate remapped jars, not published source differences.

The permitted source-only predecessor inventory `.native-source/20261028/frontier-c/194-1102-tick-boundary/report.md` was used as a coverage lead, then this slice was checked against canonical sources. It already binds the movement source-root hashes and exact client/mapping inputs. This report does not claim full-pair coverage or freeze.

## Compared ordinary movement path

- Input setup: `Input.java`, `KeyboardInput.java`; local input-to-state copy in `LocalClientPlayerEntity.serverTickAi` and `mobTick`.
- Tick order: `LivingEntity.mobTick` (jump then travel path), `PlayerEntity.mobTick`, `LocalClientPlayerEntity.mobTick`, and player `tick`/`updatePlayerPose`.
- Movement math and state writers: `PlayerEntity.jump`, `PlayerEntity.moveRelative`, `LivingEntity.jump`, `LivingEntity.moveRelative`, `Entity.move` call boundary, velocity/position/box state, pose dimensions.
- Movement inputs: movement speed attribute registration and reads/writes; player flight speed, sprinting, jump boost and movement-effect consumers; equipment/effect inputs; `Entity` gravity/fluid predicates.
- Direct local-player event response: local `move` override and post-move auto-jump detection.

### Finding draft: local auto-jump queues a jump after a detected step

1.9.4, Ornithe Feather Gen2: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick` at line 604; no local `move` override or auto-jump state/check exists.

1.10.2, Ornithe Feather Gen2: same file, `ticksToNextAutojump` at line 106, `mobTick` at lines 620–778, and added `move` / auto-jump detection at lines 812–920; `net/minecraft/client/entity/living/player/Input.java`, added `getMovement` at lines 18–20.

Difference: 1.10.2 refreshes `autoJump` from options and, after `super.move`, checks for enabled auto-jump while grounded, not sneaking/riding, with nonzero movement input and no queued auto-jump. It predicts a forward sweep from displacement (using yaw-rotated input when displacement is near zero), considers jump-boost height, and queries collision shapes/world collision boxes for an obstacle rise above `0.5F` and no greater than `1.2F`. A match sets `ticksToNextAutojump = 1`; the following local `mobTick` sets `input.jumping = true` and decrements the timer. A separate `bl4` gate prevents this synthetic jump press from toggling flight.

Movement meaning: ordinary grounded movement toward an eligible step can queue a jump that is consumed on the next local tick, changing jump and subsequent travel state.

## No-difference terminals and dependencies

- `KeyboardInput` source hash is identical in both releases. `Input` movement-vector calculation is only added to support the auto-jump look-ahead.
- Player jump and flight `moveRelative` retain their movement math/order in the inspected bodies. The `LivingEntity.moveRelative` source differs by a constant water movement multiplier being routed through a newly introduced overridable helper returning the same `0.8F`; the only source override found is polar-bear-specific, so this does not change ordinary player acceleration.
- The 1.10.2 liquid-travel branch newly checks `isNoGravity()`. That entity feature is new in 1.10.2 and is excluded as a modern-only feature for the 1.9.4 profile comparison; default ordinary-player behavior is unchanged.
- Player pose updates retain the same target dimensions and collision-fit gate; changed statements are assignment arrangement only. Player `moveRelative` and `getSpeed` continue to use the movement-speed attribute and configured air/fly speed in the same order.
- The 1.10.2 auto-jump detector depends on `BlockState.getCollisionShape`, `World.getCollisions`, segment/bounding-box tests, and vector helpers. Shape providers/world collision traversal and `Entity.move` collision response are explicitly owned by collision coverage and remain unresolved dependencies here.
- Effects, equipment, received state, and attribute consumers were checked at the ordinary player call sites; no additional eligible independent player movement change was established in these checked slices. Damage/food production, server player replay and collision geometry are not claimed in this lane.

## Coverage boundary / next closure

This closes the assigned ordinary local-player tick/input/travel/pose/state/velocity/box-writer and movement-modifier slice, with the auto-jump collision-provider and `Entity.move` internals still open under the collision worker. Other external direct responses and callback paths are not closed by this bounded slice. Intermediate patch introduction remains unknown within 1.9.4–1.10.2. No pair-freeze, acceptance, implementation or runtime-parity claim.
