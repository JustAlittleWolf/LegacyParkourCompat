# Scoped yaw source comparison: 1.13.1 → 1.13.2

**Disposition:** scoped no-difference result for the direct local-player camera yaw update and its first ordinary dry ground/air movement use. This does not close the pair or cover other movement operations.

## Authenticated source inputs

Both sides are Ornithe Feather decompilations (Feather Gen2 build.2). The 1.13.1 provenance identifies `net.ornithemc:feather-gen2:1.13.1+build.2`; its ready marker records client SHA-256 `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` and source-manifest SHA-256 `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`. The 1.13.2 ready marker identifies `feather-gen2-1.13.2+build.2`, client SHA-256 `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`, and source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

The published 1.13.2 tree's verification record reports 2,711 source files verified, zero source-file differences, zero missing published files, and zero raw-input-artifact differences. It notes one regenerated TinyRemapper intermediate-jar difference; the published source tree and raw source inputs verified exactly. The 1.13.1 and 1.13.2 source-manifest files themselves match the SHA-256 values above.

## Compared slices and result

For each cited class below, the per-file SHA-256 row in the corresponding authenticated source manifest matches across releases, and the decompiled file bytes match across releases:

- `net/minecraft/client/MouseHandler.java`, SHA-256 `b045dc9eaf5b5ab1d9f13dd795465a106f52571dda9e5c837f5c2bbbdfddf4ce`.
- `net/minecraft/entity/Entity.java`, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
- `net/minecraft/entity/living/LivingEntity.java`, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- `net/minecraft/util/math/MathHelper.java`, SHA-256 `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69`.

At `Entity.java:318–329`, `updateLocalPlayerCamera(double cursorDeltaX, double cursorDeltaY)` computes `e = cursorDeltaX * 0.15` as a double, then independently stores `(float)(this.yaw + e)` and `(float)(this.lastYaw + e)`. Thus both current and prior yaw receive the same double delta, but each addition rounds back to float. There is no yaw clamp, normalization, or finite-value gate in this method. The adjacent pitch path is float-cast and clamped; it is outside this yaw-only slice. `MouseHandler.java:211` passes cursor deltas directly to the player method. Neither call nor method body differs between the compared releases.

The first ordinary local dry ground/air movement use is also unchanged. `LocalClientPlayerEntity.java:635–640` copies local input into float movement-speed fields in `serverTickAi`. `LivingEntity.java:1885–1930` runs local AI and the travel section, which calls `moveRelative` with those fields. In the dry non-fall-flying branch (`LivingEntity.java:1478–1580`), ground acceleration uses `getSpeed() * (0.16277137F / (slipperiness * 0.91F)^3)` while air acceleration uses `speedInAir`; both call `Entity.updateVelocity(float, float, float, float)` at line 1057. That operation's float input-length threshold/normalization and scale are unchanged, and its X/Z acceleration uses current yaw through `MathHelper.sin/cos(yaw * (float)(PI / 180))`. The helper is the same float lookup-table implementation in both versions (`MathHelper.java:10–30`); the resulting velocity then reaches `LivingEntity.moveRelative`'s `move(MoverType.SELF, ...)` collision path. This establishes the first dry ground/air yaw dependency without treating collision resolution or other movement operations as part of this result.

The checked yaw turn widths, casts, paired current/prior writes, absence of yaw finite/clamp gates, dry ground-versus-air acceleration-scale selection, and yaw trig dependency are byte-identical in this exact source pair. **No scoped 1.13.1→1.13.2 yaw difference was found.**
