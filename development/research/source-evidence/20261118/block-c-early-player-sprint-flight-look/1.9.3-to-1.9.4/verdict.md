# Independent audit verdict: 1.9.3 → 1.9.4

**Verdict:** Accept the frozen report’s bounded no-difference conclusions for the inspected local sprint timers/transitions, keyboard sneak scaling and ability-flight input, and player ability-flight `fallDistance` reset. This does not establish complete pair coverage or runtime parity.

## Input and source integrity

- Frozen input: `c-early-193-194-sprint-flight-input/report.md`, SHA-256 `77d8d021eb9b680f234ebb6badfbb37129bb64bed3d781004403c8b292db24d0`.
- Canonical source roots: `decompiled_minecraft/1.9.3/ornithe-feather/` and `decompiled_minecraft/1.9.4/ornithe-feather/`.
- I independently hashed all 1,819 files declared by each source set: **0 mismatches** for 1.9.3 and **0 mismatches** for 1.9.4. The 1.9.3 provenance file SHA-256 is `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`. The 1.9.4 source manifest SHA-256 is `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`, matching its ready marker; its provenance file SHA-256 is `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd`.
- The 1.9.3 local client jar, version metadata, Feather merged jar, Feather Tiny mapping, and Feather metadata bytes currently in the cache match the report’s pinned SHA-256 values. For 1.9.4, the canonical artifact manifest and saved verification metadata declare the 1.9.4 client (`23e90103…`), Feather merged jar (`49a38d0a…`) and Tiny mapping (`9e21708d…`). The raw 1.9.4 artifacts are absent from the current cache, so I could not independently re-hash those input artifacts. Its saved verification record reports no raw-input differences and identical published source files; it also records one changed *derived* remapped jar, explaining that it was regenerated during verification. The byte-level source-tree verification above is independent of that historical record.
- `LocalClientPlayerEntity.java`, `KeyboardInput.java`, `LivingEntity.java` and `Entity.java` are byte-identical across the pair. `PlayerEntity.java` differs elsewhere, but `moveRelative(float,float)` lines 1372–1390 normalize to the same UTF-8/LF SHA-256 in both releases: `352f16d2ceb93ea1f2ae5c71410cda46042bfc912d98ca70bd9bdeee72623072`.

## Claim checks

### Sprint timer and transition boundary

`LocalClientPlayerEntity.mobTick()` increments `sprintTimer` each tick. `setSprinting(boolean)` delegates to `super` and then resets `sprintTimer` to zero. A source-tree search finds no other `sprintTimer` references; it is not the double-tap timeout. `doubleTapSprintTime` is decremented at the start of `mobTick`, cleared when using an item while not riding, set to 7 on the eligible grounded forward-input edge when sprint is not held, and read on the next eligible double-tap path. Held sprint has its own start gate; sprint stops when forward input drops below `0.8F`, horizontal collision is present, or the shared eligibility predicate becomes false. These operations and order are in the byte-identical local-player source.

The shared eligibility value reads `getFoodLevel() > 6.0F || abilities.canFly`. That bounds this claim to the sprint decision reading existing food/ability state. Food production and environmental producers are outside this audit; neither is evidence of a difference in the compared local sprint operations.

### Sneak and ability-flight input

`KeyboardInput.tick()` builds float movement components from key presses, sets jump/sneak state, then applies sneak scaling sideways first and forward second with `(float)(component * 0.3)`. `LocalClientPlayerEntity.mobTick()` snapshots the previous jump/sneak values, calls `input.tick()`, and later—under `abilities.flying && isCamera()`—divides sideways then forward movement by the double literal `0.3` with float casts when sneaking. It subtracts `getFlySpeed() * 3.0F` from vertical velocity in that branch, then applies a separate jump addition of the same term. This is before `super.mobTick()` and its travel phase. The paired input and local-player source files are byte-identical.

### Ability-flight fall-distance reset and phase

`PlayerEntity.moveRelative(float,float)` gates the reset on `abilities.flying && !isRiding()`. In that branch it saves vertical velocity and air speed, sets flight air speed using sprint state, calls `super.moveRelative`, restores vertical velocity (`savedY * 0.6`) and air speed, sets `fallDistance = 0.0F`, then clears flag 7. There is no creative-mode, food, or environmental predicate in this reset branch. The paired method bodies match despite the differing enclosing `PlayerEntity` files.

`LivingEntity.tick()` invokes virtual `mobTick()`. The local-player override processes its sprint and flight input before `super.mobTick()`. The shared `LivingEntity.mobTick()` runs its AI/jump work, then its travel phase attenuates sideways/forward speed, calls `flyingTick()`, and dispatches virtual `moveRelative()`. Thus this reset occurs during travel, after local input adjustment.

## Evidence references

- `decompiled_minecraft/1.9.3/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: lines 405–409, 604–608, 645–688, 715–724.
- `decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: same lines and identical full-file hash.
- `decompiled_minecraft/{1.9.3,1.9.4}/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`: lines 13–50, identical full-file hash.
- `decompiled_minecraft/{1.9.3,1.9.4}/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`: `moveRelative(float,float)`, lines 1372–1390; normalized body hash above.
- `decompiled_minecraft/{1.9.3,1.9.4}/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`: lines 1551, 1645–1712, identical full-file hash.
- 1.9.4 source metadata: `ornithe-feather.ready.json`, `ornithe-feather.sources.sha256`, `artifacts.sha256`, `ornithe-feather.verification.json`, and `ornithe-feather.provenance.json` under `decompiled_minecraft/1.9.4/`.
