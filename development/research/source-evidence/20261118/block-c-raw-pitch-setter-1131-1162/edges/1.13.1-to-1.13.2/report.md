# 1.13.1 to 1.13.2: local pitch write and active glide read

## Source identity

- 1.13.1, Ornithe Feather `1.13.1+build.2`: ready marker `decompiled_minecraft/1.13.1/ornithe-feather.ready.json`, status `ready`; source manifest SHA-256 `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` (recomputed and matched the ready marker).
- 1.13.2, Ornithe Feather `1.13.2+build.2`: ready marker `decompiled_minecraft/1.13.2/ornithe-feather.ready.json`, status `ready`; source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` (recomputed and matched the ready marker).
- The manifest-listed full-file SHA-256 values for `net/minecraft/entity/Entity.java`, `net/minecraft/client/MouseHandler.java`, and `net/minecraft/entity/living/LivingEntity.java` were recomputed on both sides and matched. Each of those files is byte-identical across this pair.

## Compared operation

1.13.1 and 1.13.2 `net/minecraft/entity/Entity.java`, `Entity.updateLocalPlayerCamera(double cursorDeltaX, double cursorDeltaY)`, lines 318–330. The method text is identical (SHA-256 of the extracted UTF-8 method text: `6e2aeb3df09b3836138bc1e795a1db5461beed6db7cc331094d20518c7ec997d`). The full `Entity.java` SHA-256 is also identical: `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.

The first input-driven pitch write is `this.pitch = (float)(this.pitch + d)`, where `d` is the double `cursorDeltaY * 0.15`. The addition therefore occurs in double precision and is narrowed to float at the assignment. Next the method writes yaw, then clamps current pitch to `[-90.0F, 90.0F]`. It then independently increments `lastPitch` by the same double delta (again narrowing to float), increments `lastYaw`, and clamps `lastPitch`. It does not copy current pitch into previous pitch. The sequence and types are unchanged between releases.

`MathHelper.clamp(float, float, float)` (`net/minecraft/util/math/MathHelper.java`, lines 89–94) returns the lower bound when `x < min`, otherwise the upper bound when `x > max`, otherwise `x`. As a finite large source-level witness, take `accumulatedDY = 1.0e40` and normal positive finite sensitivity: `MouseHandler.turnPlayer` computes finite `k = accumulatedDY * g`, passes it as `cursorDeltaY`, and multiplying by `0.15` remains a finite double above `Float.MAX_VALUE`. The cast in the current and previous pitch writes yields positive infinity; the outer clamp then yields `90.0F` for both. This witness demonstrates the cast-plus-clamp path for finite inputs; it does not claim that physical mouse hardware can emit a displacement of that magnitude.

## Reachability and next active read

The real local input route is present on both sides: `MouseHandler.onMove` adds finite cursor-position deltas to `accumulatedDY` (1.13.2 lines 168–177); `MouseHandler.turnPlayer` scales the accumulated delta and calls `minecraft.player.updateLocalPlayerCamera(j, k * n)` (lines 181–217, call at 211). The caller and both caller source files are byte-identical across the pair.

The next active glide physics read is `LivingEntity.moveRelative` (both versions lines 1478 onward): after the locally controlled/logical movement gate and fluid checks, the `isFallFlying()` branch calls `getLookVector()` and reads `this.pitch` again to compute `j = this.pitch * (float)(Math.PI / 180.0)` (lines 1488–1495). This branch uses the live clamped field, not `lastPitch` or an interpolated pitch. `LivingEntity.mobTick` reaches `moveRelative` through the `travel` section at line 1926. These source files are byte-identical across the pair.

## Result

No 1.13.1-to-1.13.2 change was found in the bounded local pitch writer, its actual mouse caller, the float clamp, or the next active Elytra pitch read. This pair establishes no cutover for this operation. The inspection does not cover callback batching, rendering, teleport/network pitch paths, or swim-specific pitch.