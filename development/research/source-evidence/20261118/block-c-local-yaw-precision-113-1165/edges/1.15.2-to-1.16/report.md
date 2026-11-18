# C — 1.15.2 → 1.16 Mojmap: local `Entity.turn` yaw slice

## Bounded result

**No source difference found in the assigned yaw operation.** In both named trees, the ordinary local mouse path passes a `double` yaw delta to `Entity.turn(double, double)`. The method multiplies it by the double literal `0.15`, adds that double to the existing float yaw, casts the sum to `float`, and then performs the analogous previous-yaw update. The bodies are text-identical. The direct ground/air relative-movement entry also reads the resulting stored `float yRot` through the same `moveRelative` body in both versions. This is a no-difference result for this bounded slice, not full-pair coverage or a release-wide equivalence claim.

## Source identity and byte verification

The checked source roots are `decompiled_minecraft/1.15.2/mojmap/` and `decompiled_minecraft/1.16/mojmap/` in the primary checkout. The path labels identify the trees as Mojmap. These are legacy decompile outputs: neither tree contains `.decompile-provenance.json`. I verified the cited source-file bytes with SHA-256 below, but this checkout does not establish the exact mapping artifact/build or the original decompiler input identity. Treat that provenance gate as open; do not promote this note to source acceptance/freeze without the preparation owner's immutable source-set identity.

SHA-256 of cited complete files:

| Release | Relative source file | SHA-256 |
|---|---|---|
| 1.15.2 | `net/minecraft/world/entity/Entity.java` | `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E` |
| 1.16 | `net/minecraft/world/entity/Entity.java` | `27E3BB01B5D03C09161891E717C16477899768333082E81EBFAAC4E525616576` |
| 1.15.2 | `net/minecraft/client/MouseHandler.java` | `925EF4E75C1DA041E078EB71B6BC79180EBBB1329CE9D18EFB5203777FD1FE5B` |
| 1.16 | `net/minecraft/client/MouseHandler.java` | `2611DCEB1078A63A33D621BF02F466AE70F6D015C902813935B9836F39415949` |
| 1.15.2 | `net/minecraft/world/entity/LivingEntity.java` | `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54` |
| 1.16 | `net/minecraft/world/entity/LivingEntity.java` | `B6EA2B9B037B79FC3EA6E1F6BCD2C4C1D89D4A96315968A8F247641F6C335F26` |
| 1.15.2 | `net/minecraft/world/entity/player/Player.java` | `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793` |
| 1.16 | `net/minecraft/world/entity/player/Player.java` | `A849960D47BF00A2CDDFB6636C78C518532A11967BA08829F19C5EBCED08681` |
| 1.15.2 | `net/minecraft/client/Options.java` | `5BF3F2EF925C282C8AC9C6DF57B362013B295B6946B006EE72A008495C485634` |
| 1.16 | `net/minecraft/client/Options.java` | `EFCAF563621E2C6AD2E547D5273492E7DBEB13A6E0B9C8639AA0E056A84435BB` |

## Compared operation and call path

- `1.15.2` `Entity.turn(double d, double e)`, `Entity.java:326–338`; `1.16` the same method, `Entity.java:332–344`. Each declares `double f = e * 0.15` and `double g = d * 0.15`. For yaw, it executes `this.yRot = (float)(this.yRot + g)`, then later `this.yRotO = (float)(this.yRotO + g)`. It clamps pitch only. There is no yaw wrapping, finite-value check, or yaw clamp. The update order is current yaw before previous yaw; both additions use the same precomputed `double g`, and each result is narrowed once to float.
- The local mouse call is `MouseHandler.java:222` in 1.15.2 and `:242` in 1.16: `this.minecraft.player.turn(j, k * n)`. In the surrounding ordinary path, `j` and `k` are doubles; the sensitivity expression and accumulated-delta multiplication are double-valued. `n` is an `int` for invert-Y and affects only the second (pitch) argument. The path checks grabbed mouse/window-active and non-null player; it has no finite check before `turn`. The relevant expressions and gates match across versions (line offsets differ). `Options.sensitivity` is `double` with default `0.5` in both trees.
- A lawful finite witness is ordinary, unsmoothed input with `accumulatedDX = 1.0`, finite sensitivity `0.5`, an active grabbed mouse and non-null local player. The sensitivity arithmetic and `j = accumulatedDX * g` remain finite, so the call stores a finite yaw from a finite double delta. Starting with both yaw fields at `0.0F`, the current and previous yaw each receive the corresponding float-narrowed `0.15 * j` value. This witness exercises finite narrowing and the same write order in both versions; it does not claim a difference.
- The first ordinary ground/air relative-movement read is `Entity.moveRelative(float, Vec3)`: both releases pass `this.yRot` to `getInputVector` and add its returned vector to delta movement (`Entity.java:1063–1065` in 1.15.2; `:1073–1075` in 1.16). The dry non-fall-flying `LivingEntity.travel` path reaches this call in ground and air states. In 1.15.2 the caller is at `LivingEntity.java:1888–1891`; in 1.16 it reaches `handleRelativeFrictionAndCalculateMovement` at `:2060–2063`, which calls `moveRelative` at `:2061`. `Player.travel` delegates to `super.travel` in both (`Player.java:1448–1480` and `:1380–1412`). Thus yaw's first stored-state contribution is the angle argument to input-vector rotation. `getInputVector`'s trig and the ground/air speed/acceleration procedures are recorded only as the downstream boundary and were not assessed for this assignment.

## Coverage and limits

Checked: direct local mouse-to-`Entity.turn` yaw argument width and call gates; the yaw multiplier, addition, float narrowing, write order, and finite behavior; and the first stored-yaw read in ordinary dry ground/air relative movement. No difference found in these slices. Pitch, device-event accumulation details, vehicle behavior, collision, trig implementation, movement acceleration, and other tick dependencies were not reviewed. No builds, tests, game launches, or source generation were run.
