# MD-06: Creative flight resets accumulated fall distance

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice ID: stage 3.2.6 player creative-flight wrapper
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player movement while `abilities.flying` is true and the player is not riding
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A `PlayerEntity.moveRelative()` lines 1279-1295 saves vertical velocity, changes `speedInAir`, calls `super.moveRelative()`, restores damped vertical velocity and `speedInAir`, then updates movement stats. The flying branch does not clear `fallDistance`. Source `../../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, SHA-256 `E66CB294FC93118148A444BBAFDF4DD57CBF66A23D69B1E8892CEFCCC690AB88`.
- B `PlayerEntity.moveRelative()` lines 1372-1390 performs the same flying movement and restoration, then sets `fallDistance = 0.0F` and clears entity flag 7. Source `../../../../../decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, SHA-256 `D658A0D95452D12BB7E347BFD802240EEECAF7F938E10DCD43E2640434387F85`.
- In the shared `Entity.move()`/`LivingEntity.moveRelative()` path, a descending displacement increments `fallDistance`; B's wrapper resets it after the movement call on each non-riding flight tick. A has no corresponding reset in this wrapper. The source hashes for `Entity` and `LivingEntity` are recorded in `3.2.1`.

## Source-level difference

Each B creative-flight movement tick clears accumulated fall distance after the shared movement calculation. A preserves it. This is separate from the previously cataloged flight-plus-sneaking input rescaling (MD-02).

## Reachability and dependencies

The wrapper branch is gated by `abilities.flying` and the player not riding. The local-player tick toggles that ability for players with `canFly`; both versions expose the flight ability and input path. The flag-7 clear is the newer fall-flying state and is modern-only, but the fall-distance reset runs on the reachable legacy creative-flight path as well.

## Consequence and uncertainty

Source confirms a different `fallDistance` state after flight movement. A later fall-damage-eligible state could therefore observe a different accumulated distance if flight permission is removed before landing. While `canFly` remains true, player fall damage is suppressed. No claim is made about a specific damage event or trajectory, and none was tested.

## Handoff

Independent delta: the 1.9.4 player movement wrapper clears fall distance during active flight; 1.8.9 leaves the shared movement accumulator untouched. Preserve this as a separate delta from input rescaling and Elytra-only behavior.
