# Camera-flight jump+sneak arithmetic closure (1.13.2 → 1.14.4)

## Scope and verdict

This closes only the simultaneous jump+sneak camera-flight arithmetic in the exact paired Ornithe Feather Gen2 build 2 sources. At the default fly speed, 1.13.2 can leave a nonzero binary64 residual for a source-reachable incoming vertical velocity. The residual is passed to same-tick movement. 1.14.4's integer counter is zero for the same two inputs and skips its vertical velocity update. This establishes an arithmetic and movement-input delta, not a changed final position, collision, or sustained trajectory.

The incoming value is a practical default-flight value, not a bound on all possible entity velocity state: fly speed can be changed, and this closure does not exhaust external velocity producers.

## Paired operation and operand types

In 1.13.2 `LocalClientPlayerEntity.mobTick`, lines 797–806, the camera/flying branch performs sneak first and jump second. Each assignment updates `double velocityY`; `PlayerAbilities.getFlySpeed()` returns `float` (`PlayerAbilities.java:44–45`), and `3.0F` is also `float`. Therefore the product is rounded as binary32 before it is promoted to binary64 for each `velocityY ± product` operation. The subtract and add are separate assignments and binary64 operations; the second operation consumes the rounded result of the first.

In 1.14.4 `LocalClientPlayerEntity.mobTick`, lines 728–742, `j` is `int`; sneak decrements it and jump increments it. With both true, `j` becomes zero and `j != 0` is false, so the `Vec3d.add` velocity update is skipped. For either single input, `j * getFlySpeed() * 3.0F` is evaluated in binary32 before widening as the `Vec3d` Y argument. `PlayerAbilities.getFlySpeed()` returns `float` in this version too (`PlayerAbilities.java:44–45`).

## Exact default-speed witness and reachability

Both `PlayerAbilities` classes initialize `flySpeed` to `0.05F` (line 11). In 1.13.2 the field is declared `double`, but the initializer is the float literal `0.05F`, the getter casts back to `float`, and its product with `3.0F` is binary32. Thus the shared operand is:

```text
getFlySpeed() = 0.05000000074505806f  (binary32 bits 0x3D4CCCCD)
p             = 0.15000000596046448f  (binary32 bits 0x3E19999A)
widen(p)      = 0.1500000059604644775390625 (binary64)
```

A source-reachable default-flight sequence is available on the spectator camera path: applying `GameMode.SPECTATOR` sets `canFly` and `flying` (`GameMode.java:39–43`); the local-player code also sets flying when `canFly` and spectator are true (old `LocalClientPlayerEntity.java:760–765`; new `:691–696`). The branch requires this player to be the active camera (old `isCamera()` `:648–650`; new `:614–616`), and the post-`super.mobTick()` ground check does not clear flight for spectators (old `:837–840`; new `:773–776`). Spectators are no-clip in `PlayerEntity` (old `:177`; new `:209`), so from a zero-Y-velocity spectator-camera state the sequence can be carried out without collision: use sneak only for one tick, then no vertical input for one tick. The 1.13.2 camera branch subtracts `p`; `PlayerEntity.moveRelative` preserves the post-branch Y for movement, then applies the flying Y damping `i * 0.6` after `super.moveRelative` (`PlayerEntity.java:1454–1461`). These two ticks produce, respectively, `-0.09000000357627869` and `-0.05400000214576721` as the next tick's incoming Y velocity. This proves source reachability under the stated spectator-camera conditions, not that every game-mode transition initializes this state.

For `v = -0.05400000214576721` (`binary64 bits 0xBFABA5E366666666`), the 1.13.2 operations are:

```text
round64(v - widen(p)) = -0.2040000081062317  (0xBFCA1CAC1999999A)
round64(result + widen(p)) = -0.05400000214576722 (0xBFABA5E366666668)
residual = oldResult - v = -1.3877787807814457e-17 (0xBC70000000000000)
```

The 1.14.4 result before movement remains exactly `v`, because `j == 0` skips the update. The 1.13.2 residual is two binary64 ULPs at this magnitude; it is not a real-number-only artifact.

For context on the practical default-flight range, the flying `PlayerEntity.moveRelative` path applies `nextY = round64(0.6 * (currentY + inputSign * p))`, with input sign −1, 0, or +1. From zero under repeated default input, it approaches but does not reach the steady values `±(0.6p/0.4) = ±0.22500000894069672`; transient values remain within this ordinary-input envelope, subject to the exact per-tick rounding. This is a conditional envelope for default fly speed and that flight path, not a global clamp on `velocityY`.

## Handoff to movement and effect limit

The source order sends this value into movement in the same tick. `LivingEntity.tick` calls `mobTick()` before its travel phase (1.13.2 `LivingEntity.java:1752`, then `:1920–1926`; 1.14.4 `:2046`, then `:2221–2227`). The local-player `mobTick` camera branch runs before its `super.mobTick()` continuation (old `LocalClientPlayerEntity.java:797–806, 837`; new `:728–742, 773`). During travel, dynamic dispatch reaches `PlayerEntity.moveRelative`: the old override saves the just-updated Y as `i`, calls `super.moveRelative`, then damps `i` (`1.13.2 PlayerEntity.java:1454–1461`); new does the analogous save/call/reset with the velocity vector (`1.14.4 PlayerEntity.java:1394–1403`). The base relative-movement paths move using the current velocity (old `LivingEntity.java:1575`; new `:1844–1846`). Thus the old residual is present in the vertical movement input while the new path uses the unchanged incoming value.

The difference is about `1.39e-17` blocks per movement call. Source establishes that it reaches the move operation; it does not establish that it survives position-coordinate rounding, changes a collision result, or accumulates into a different visible trajectory. Those outcomes depend on position and collision context and remain outside this arithmetic closure.

## Source references and hashes

Line ranges above refer to these complete source files, SHA-256:

| Version | File | SHA-256 |
| --- | --- | --- |
| 1.13.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerAbilities.java` | `03c506732bb599c2a6d5f6c7d6f13175b9d27a0abfd9058811cf443f51af012d` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.14.4 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce` |
| 1.14.4 | `net/minecraft/entity/living/player/PlayerAbilities.java` | `706f0011836698a9be661cda922d42c7143fdb385b7086ed79143ef3d68874ee` |
| 1.14.4 | `net/minecraft/entity/living/player/PlayerEntity.java` | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` |
| 1.14.4 | `net/minecraft/entity/living/LivingEntity.java` | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` |
| 1.13.2 and 1.14.4 | `net/minecraft/world/GameMode.java` | `f245fb32c78232d3e936edfd517c76e727591c93001049f4e5e94ce86ed48971` |

The permitted ordinary-gate input note was read at SHA-256 `2c70c46506dddef309ae52dbff41bf9c1bc7ecc519486f71c7c5734dc08e436c`; no other reports or project/wiki/backlog sources were used.
