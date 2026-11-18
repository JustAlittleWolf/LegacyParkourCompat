# 1.18.1 → 1.18.2 Elytra glide pitch-cosine factor

## Finding

In the active fall-flying branch of `LivingEntity.travel(Vec3)`, 1.18.1 calculates the pitch-cosine factor in single precision from the `Mth` sine lookup table, then rounds the squared/capped result to `float`. In 1.18.2 the same factor is calculated with `Math.cos` and retained as `double` through its square and cap. This is a source-confirmed player-movement delta; the 1.18.2 result can differ by a few ULPs and feeds glide gravity, downward lift, and (for negative pitch) the pitch-up contribution.

- **1.18.1 Mojmap:** `LivingEntity.travel(Vec3)` lines 2005–2100; fall-flying branch lines 2070–2100. It gets `$$14` (`Vec3`) from `getLookAngle()`, computes `$$15` as `float getXRot() * (float)(Math.PI / 180.0)`, and reads length and horizontal components as `double`. `$$19` is `float Mth.cos($$15)`, followed by `$$19 = (float)($$19 * ($$19 * Math.min(1.0, $$18 / 0.4)))`. The cast applies after the complete product; the cosine itself already has table/float precision.
- **1.18.2 Mojmap:** `LivingEntity.travel(Vec3)` lines 2009–2104; fall-flying branch lines 2074–2104. The angle remains the same `float`, but `$$19` is `double Math.cos($$15)` and `$$19 = $$19 * $$19 * Math.min(1.0, $$18 / 0.4)` remains double. Thus both the cosine source and the factor's intermediate/result precision change. `Math.min` is the double overload in both releases because `1.0` and `$$18 / 0.4` are doubles.
- `Mth.cos(float)` is the float lookup-table function in both releases (`Mth.java` lines 26–49; `cos` indexes `SIN[(int)(angle * 10430.378F + 16384.0F) & 65535]`). The 1.18.2 glide branch stops using it for this factor. The separate `Mth.sin($$15)` used for negative-pitch lift remains unchanged.

## Reachability and move path

For the ordinary local player, `LocalPlayer.aiStep()` calls `super.aiStep()` (1.18.1 `LocalPlayer.java:647–808` in 1.18.1 and `LocalPlayer.java:642–803` in 1.18.2). The inherited `LivingEntity.aiStep()` runs `updateFallFlying()` and then `this.travel(new Vec3(this.xxa, this.yya, this.zza))` (`LivingEntity.java:2457–2548` in 1.18.1 and `LivingEntity.java:2461–2552` in 1.18.2). `Player.travel(Vec3)` only does the swimming / ability-flying adjustments, then delegates to `super.travel`; with ordinary walking abilities and dry, non-swimming state it reaches this `LivingEntity.travel` fall-flying branch (`Player.java:1409–1437` in 1.18.1 and `Player.java:1422–1448` in 1.18.2). A valid active Elytra flag selects `else if (this.isFallFlying())` after the water and lava branches.

That branch writes the velocity through `setDeltaMovement`, applies `(0.99F, 0.98F, 0.99F)` drag, then calls `this.move(MoverType.SELF, this.getDeltaMovement())` (1.18.1 `LivingEntity.java:2098–2099`; 1.18.2 `:2102–2103`). For a local player this dispatches through the `LocalPlayer.move` override, which delegates to `super.move` before its auto-jump update (`LocalPlayer.java:891–895` in 1.18.1 and `LocalPlayer.java:889–893` in 1.18.2), and then to `Entity.move` (`Entity.java:541+`). With unobstructed space, the resulting movement vector is applied to position. The changed factor therefore reaches the real local-player move, rather than only an unused intermediate.

The denominator is not a differentiating assumption for a normal view vector. At the witness's pitch/yaw, `getLookAngle()` uses `Entity.calculateViewVector` and the same Mth trig table (`Entity.java:1311–1319, 1813–1814`): its length is approximately `0.999999983`, so `Math.min(1.0, length / 0.4)` selects exactly the cap 1.0. This is the native default look vector, not an artificially scaled input.

## Lawful numeric witness

Use an active glide in dry, empty space, with no collision, no fluids, no potion/equipment modifiers, normal gravity, yaw 0°, pitch +45°, and starting velocity `(0, -0.1, 0.3)`. The glide gate is reachable; at this pitch `$$15 < 0` is false, while the downward-velocity correction gate is true. The table-generated view has equal-magnitude vertical/forward components, nonzero horizontal look length, and length 1, so the factor cap is 1.

With Java float semantics for the 1.18.1 angle conversion and Mth index, the index is 24576 and `Mth.cos($$15)` is `0.7071067690849304F`; the capped squared factor is `0.4999999701976776F`. In 1.18.2 `Math.cos($$15)` squared is `0.49999997814430497` as a double. The factor difference is `7.946627356147928E-9`.

Using the source's gravity literal `0.08` and the selected initial vertical velocity, apply the source's operations in order: `y += 0.08 * (-1.0 + factor * 0.75)`; since y remains negative, `y += y * -0.1 * factor`; then the Y component is multiplied by `0.98F`. The resulting unobstructed move Y is approximately `-0.13965000210285186` in 1.18.1 versus `-0.13965000154213786` in 1.18.2, a delta of about `5.60714003E-10` blocks on this tick. This is a numeric source witness, not a claim that the rounded position will differ for every starting position or Java implementation.

## Exact source identity

Both trees are exact-release Mojmap/Vineflower outputs. The 1.18.1 source manifest SHA-256 is `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` and its provenance JSON SHA-256 is `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139`; manifest run ID is `prep-1.18-1.18.1-mojmap-2026-10-09-r1`, exact version 1.18.1, mapping `mojmap`, Vineflower 1.12.0. The 1.18.2 source manifest SHA-256 is `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`; provenance JSON SHA-256 is `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9`; its manifest identifies exact version 1.18.2, mapping `mojmap`, Vineflower 1.12.0. Actual source-file bytes were checked against each tree's `mojmap.sources.sha256` entries.

| Source member | 1.18.1 file SHA-256 | 1.18.2 file SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/LivingEntity.java` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |
| `net/minecraft/world/entity/Entity.java` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |
| `net/minecraft/world/entity/player/Player.java` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` |
| `net/minecraft/client/player/LocalPlayer.java` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` | `99c2d18bcd23243afbf8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |
| `net/minecraft/util/Mth.java` | `10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f` | `32747c5b09fc184baae356e39a0088fd66c9f08f69d9e98b67c19e1de6f1bb2e` |