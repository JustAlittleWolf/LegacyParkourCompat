# Blind source result: ordinary mouse pitch writer, 1.20.5 → 1.20.6

## Scope and source identity

This is a bounded Mojmap comparison of the local ordinary mouse-to-player pitch write and its next ordinary fall-flying movement read. It is not full-pair movement coverage. No product code, wiki inventory, backlog, Git history, runtime, game, or tests were examined.

Both canonical roots have `status: ready`, the exact version IDs `1.20.5` and `1.20.6`, and `mapping: mojmap`. The SHA-256 of each `mojmap.sources.sha256` matches its root `mojmap.ready.json` (`1.20.5`: `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`; `1.20.6`: `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`). Each artifact-manifest hash also matches its ready marker. For every source file listed below, the file's SHA-256 was checked against that release's source manifest. The corresponding file hashes are identical across the pair:

| Mojmap source | SHA-256 in both roots |
| --- | --- |
| `net/minecraft/client/Minecraft.java` | `755dc0ae2b95f1f32b876e249b88875fd3d04c6892b8412cfb8a7ad830ed901c` |
| `net/minecraft/client/MouseHandler.java` | `ed1248f1d62a927907e6e10fae87a0d4ab0a99d91e44a149aeba40d05fbda1e1` |
| `net/minecraft/client/Options.java` | `c020b31bd2650a0c6575de544831111323f7edb20ae431fc06faf4cb4c6fd8cc` |
| `net/minecraft/world/entity/Entity.java` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| `net/minecraft/world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| `net/minecraft/util/Mth.java` | `f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d` |

## Source trace and result

In both versions, `Minecraft` calls `mouseHandler.handleAccumulatedMovement()` at line 1190. `MouseHandler.setup` queues cursor callbacks through `onMove`; `onMove` stores the first coordinates and, on later moves while the window is active, adds `newY - ypos` to the `double accumulatedDY` at lines 187–203. `handleAccumulatedMovement` calls `turnPlayer` only while the mouse is grabbed and a player exists, then clears both double accumulators (lines 205–233). The ordinary unsmoothed, unscoped branch computes sensitivity in doubles: `s = option * 0.6F + 0.2F`, `s³`, then `8*s³`; it multiplies the double accumulated Y by that double scale, applies the integer invert-Y sign, and passes the double delta to `player.turn` (lines 235–267). There is no earlier pitch read or narrowing cast in this mouse path.

`Entity.turn(double yaw, double pitch)` is inherited by `Player` in this route. It first narrows pitch to `float` and multiplies by `0.15F`; only then does it read the current pitch and call `setXRot(current + delta)`. It writes yaw next, rereads pitch for the clamp/write, then updates and clamps the old-pitch field `xRotO` (1.20.5 and 1.20.6 `Entity.java:400–412`). `Entity.setXRot(float)` rejects a non-finite argument and otherwise assigns it (`Entity.java:3324–3330`). `Mth.clamp(float, float, float)` returns `min(value, max)` above the range (`Mth.java:101–103`). The call order is material: there is no saved pre-setter pitch reused for the second write. If the first candidate is infinite, the setter retains the then-current `xRot`; the subsequent clamp reads that retained value. Separately, positive-infinite `xRotO` clamps to `90.0F`.

A lawful finite **source-level** witness for the narrowing point is `accumulatedDY = 1.0e39` (finite as a Java `double`). For example, the first `onMove` stores `ypos = 0.0`, then a later active-window callback supplies finite `y = 1.0e39`; the source subtraction/addition leaves that finite delta. With the source default sensitivity `0.5` (`Options.java:101–107`), the ordinary branch's scale is approximately `1.00000009` (the `0.6F` and `0.2F` literals are float values promoted to double), so `turnPlayer` passes a finite double still far above the float range to `Entity.turn`. The cast `(float)1.0e39` overflows to positive infinity; multiplying by `0.15F` remains infinite. Consequently, for an initially finite pitch the attempted authoritative pitch assignment is discarded, the following clamp sees the retained pitch, and `xRotO` becomes `90.0F`. This witness uses only finite values accepted by the Java source method path; it does **not** claim that an operating system or physical mouse emits such coordinates. Device behavior is outside this review.

On the next ordinary player travel path, `Player.travel` reaches `super.travel` (unless the separate abilities-flying branch is active; this witness assumes an ordinary non-abilities-flying player). When `LivingEntity.travel` takes its `isFallFlying()` branch, it reads `getXRot()` into radians and uses that pitch in the cosine and sine terms that update movement velocity (`Player.java:1473–1494`; `LivingEntity.java:2168–2194`). It consumes `xRot`, not `xRotO`; thus the source witness above leaves the fall-flying calculation using the retained pitch. This is the live movement consumer, not an inferred visual-camera effect.

## Difference disposition

No 1.20.5→1.20.6 source difference was found in the scoped path. All seven source files above are byte-identical by manifest-verified SHA-256, including the mouse accumulation/scaling path, `Entity.turn`/`setXRot`, the clamp, and the `Player`/`LivingEntity` consumer. The finite-overflow behavior is present on both sides and is therefore not a version-boundary finding. This result closes only this bounded source slice; it does not establish full-pair coverage, runtime parity, or the release at which any behavior originated.


