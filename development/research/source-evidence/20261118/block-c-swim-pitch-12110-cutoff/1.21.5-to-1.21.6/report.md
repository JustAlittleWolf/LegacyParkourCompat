# Swim pitch vertical adjustment: 1.21.5 → 1.21.6

## Scope and result

Blind exact Mojmap source comparison for the ordinary active, nonpassenger local-player swim pitch vertical adjustment. **No change found in this bounded operation.** `Player.travel` reads the look vector once; the pitch-to-look-Y math, correction coefficient and gates, direct delta-Y write, and subsequent movement order match across the pair. No changed-expression numeric witness is applicable.

## Source identity and integrity

All sources below are local canonical decompilations and were read only.

- **1.21.5 / Mojmap:** published `mojmap.ready.json` SHA-256 `5f65fb143209abcc6e28e77a7150aecefdbc9de00b6ef3fc924e53bc0e1a667e`; `mojmap.provenance.json` SHA-256 `b0832a4e1507bd74ac300fae2fca78668964b21ef2d05be8cff01b9d1d60da8e`; `mojmap.sources.sha256` SHA-256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`. The ready marker identifies version `1.21.5`, Mojmap, and source-manifest digest above.
- **1.21.6 / Mojmap:** `.decompile-provenance.json` SHA-256 `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e`; it embeds per-source SHA-256 values and identifies the exact client/mapping inputs. There is no `1.21.6/mojmap.ready.json` or separate `mojmap.sources.sha256` in this canonical tree, so a standalone ready publication marker is unavailable. The five cited source files’ current hashes match the embedded values.

Cited source SHA-256 values (`1.21.5` / `1.21.6`):

| Source | 1.21.5 | 1.21.6 |
| --- | --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `net/minecraft/world/entity/Entity.java` | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| `net/minecraft/util/Mth.java` | `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da` | `962c7439a4d0eacc23965d67af13a03e2d95f703247dce19074c2ae134a1cf9b` |
| `net/minecraft/world/entity/LivingEntity.java` | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| `net/minecraft/client/player/LocalPlayer.java` | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |

## Reconstructed path and exact operation

The regular `LivingEntity` travel stage dispatches to virtual `travel(input)` when the entity is not routed through `travelRidden` and can simulate movement (1.21.5 `LivingEntity.java:2763–2766`; 1.21.6 `:2815–2818`, adding `isEffectiveAi()`). `Entity.canSimulateMovement()` and `isEffectiveAi()` both return `isLocalInstanceAuthoritative()` in each version, so the local authoritative player meets these gates. `LocalPlayer` inherits the `Player.travel` implementation through its player hierarchy.

At the start of `Player.travel`, passenger state is sampled first. A passenger immediately takes `super.travel(input)` and skips the swim correction. For a nonpassenger, the single `isSwimming()` check gates one read into `double lookY = this.getLookAngle().y`; the local `lookY` is reused for both the coefficient selection and the update gate. Both versions use the same operation order:

```java
double lookY = this.getLookAngle().y;
double amount = lookY < -0.2 ? 0.085 : 0.06;
if (lookY <= 0.0 || this.jumping || !this.level().getFluidState(
        BlockPos.containing(this.getX(), this.getY() + 1.0 - 0.1, this.getZ())
    ).isEmpty()) {
    Vec3 movement = this.getDeltaMovement();
    this.setDeltaMovement(movement.add(0.0, (lookY - movement.y) * amount, 0.0));
}
```

The source spells the fluid-state condition over lines 1430–1438 in 1.21.5 and 1442–1450 in 1.21.6. The coefficient comparison is strict `< -0.2`; the write gate is `<= 0.0`, jumping, or nonempty fluid at the sampled position. The `Vec3` read occurs only after that gate, and the vertical write uses that sampled `movement.y`. It is a velocity correction, not an immediate position move. Next, flying players save the then-current Y, call `super.travel`, then replace Y with the saved value times `0.6`; ordinary nonflying swimmers call `super.travel` directly. This ordering is also unchanged.

`Entity.getLookAngle()` passes the current (noninterpolated) `getXRot()` and `getYRot()` to `calculateViewVector`. Both versions compute float radians in order as `pitch * (float)(Math.PI / 180.0)` and `-yaw * (float)(Math.PI / 180.0)`, then call `Mth.cos(yawRadians)`, `Mth.sin(yawRadians)`, `Mth.cos(pitchRadians)`, `Mth.sin(pitchRadians)` and return `(sinYaw*cosPitch, -sinPitch, cosYaw*cosPitch)`. Therefore the swim sample is `lookY = -Mth.sin(pitch * (float)(Math.PI / 180.0))`; yaw does not affect Y.

In both `Mth.java` files, `SIN` is a `float[65536]` initialized in index order by `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)` (double table-angle arithmetic followed by float rounding). `sin(float a)` returns `SIN[(int)(a * 10430.378F) & 65535]`: multiplication uses float operands, Java’s float-to-int conversion truncates toward zero, then the mask wraps the index. `cos(float a)` uses the same lookup with `+ 16384.0F` before the cast and mask. These table construction, scale, cast and index semantics are unchanged.

For a water-travel path, `LivingEntity.travel` selects `travelInFluid` under the same `(isInWater() || isInLava()) && isAffectedByFluids() && !canStandOnFluid(fluidState)` gate. The water branch calls `moveRelative`, then virtual `move(MoverType.SELF, getDeltaMovement())`, then reads the post-move delta and applies slowdown/fluid-fall adjustment. `LocalPlayer.move` overrides only to sample starting X/Z, call `super.move`, and update auto-jump/walk distance (identically in both versions); the inherited base movement ultimately runs `Entity.move`. Thus the direct-Y correction precedes the real collision-aware move in both releases.

## Exact compared slices

- `Player.travel(Vec3)`: 1.21.5 `Player.java:1425–1449`; 1.21.6 `:1437–1461`.
- `Entity.getLookAngle()` / `calculateViewVector(float,float)`: 1.21.5 `Entity.java:1669–1677, 2188–2190`; 1.21.6 `:1746–1754, 2356–2358`.
- `Mth` table setup / `sin` / `cos`: both `Mth.java:28–54`.
- `LivingEntity` travel caller and fluid movement: 1.21.5 `LivingEntity.java:2763–2766, 2213–2222, 2249–2299`; 1.21.6 `:2815–2818, 2245–2254, 2301–2351`.
- `LocalPlayer.move(MoverType,Vec3)`: both `LocalPlayer.java:916–924`.

**Finding status:** source comparison reports no difference for this operation. This is a bounded operation result, not full-pair inventory/freeze or runtime-parity evidence. The missing 1.21.6 ready marker is the outstanding provenance limitation.
