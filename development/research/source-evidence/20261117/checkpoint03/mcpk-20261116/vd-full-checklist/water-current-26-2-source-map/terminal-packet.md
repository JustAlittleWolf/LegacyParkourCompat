# 26.2 water-current source map (source-only terminal packet)

Assignment input

- Accepted historical finding: `development/research/findings/1.12.2--1.13.2/flowing-water-current-level-and-depth.md`, SHA-256 `242fdf2f38f1e9c7cce581ea7312ce8aea5bea18c8d05fcba81a29c90368def7`.
- Acceptance input: `build/mcpk-20261116/vd-full-checklist/accept-water-current-corrected/acceptance.md`, SHA-256 `45bad09584c7882fb834d7c706de7ad4a338ef470aedfbeb9270621aa5e47fc0`.
- Compared source: canonical `decompiled_minecraft/26.2/unobfuscated/`, exact version `26.2`, mapping namespace `unobfuscated`.

## Source identity

`decompiled_minecraft/26.2/unobfuscated.ready.json`: versionId 26.2, mapping unobfuscated, status ready, 7,055 source files; source-manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; artifact-manifest SHA-256 `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`; diagnostics SHA-256 `ba6fd6c5b1c77b3ee2988f0c54968cc5b915680670fdb66699dbcbf8b9fe21d3`.

- Provenance file `decompiled_minecraft/26.2/unobfuscated.provenance.json` SHA-256 `a328f5333a1b27aaddee4422b23159a323ae219ccd26f11207c1a5461366f27d1`; it records Vineflower 1.12.0, Java 25.0.3+9-LTS, exactRun version 26.2, unobfuscated mapping, 2026-10-07 creation.
- Ready marker SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`.
- The source hashes below match their entries in `unobfuscated.sources.sha256`.

## Reachable client path and common operation

1. `ClientLevel.tickNonPassenger(Entity)` at `net/minecraft/client/multiplayer/ClientLevel.java:467-472` calls `entity.tick()`; source hash `f0255138128f19fb354e1a208f457e7fdb2df986f3103ec91f52bcabfa076631`.
2. The local player has an additional direct entry: `LocalPlayer.tick()` at `net/minecraft/client/player/LocalPlayer.java:227-230` checks `connection.hasClientLoaded()` then calls `super.tick()`. `LocalPlayer` extends `AbstractClientPlayer`; the inherited `LivingEntity.tick()` at `LivingEntity.java:2765-2767` calls `super.tick()`; `Entity.tick()` at `Entity.java:506-508` calls `baseTick()`.
3. `LivingEntity.baseTick()` at `LivingEntity.java:408-420` calls `super.baseTick()`. In shared `Entity.baseTick()` at `Entity.java:510-532`, line 531 invokes `this.updateFluidInteraction()` after setting `wasEyeInWater`.
4. `Entity.updateFluidInteraction()` at `Entity.java:1645-1669` calls the scan first (`fluidInteraction.update(this, !this.isPushedByFluid())`, line 1646); reads water/lava tracker state (1647-48); sets `wasTouchingWater` (1656); then, under `isPushedByFluid()` (1657), invokes the water application only if `inWater` (1658-60): `fluidInteraction.applyCurrentTo(FluidTags.WATER, this, 0.014)` (1659). Lava application is a distinct branch (1662-64). Water scale is the double literal `0.014`.

## Current scan and aggregation

`net/minecraft/world/entity/EntityFluidInteraction.java`, source SHA-256 `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62`.

- `public void update(final Entity entity, final boolean ignoreCurrent)` (lines 32-89) resets the trackers; obtains `entity.getFluidInteractionBox()`; floors minimum coordinates and uses `ceil(max)-1` for maxima (36-41); performs its loaded/has-fluid preflight before any fluid-cell read (42); then loops x, y, z (52-55).
- For each non-empty `FluidState`, it computes `fluidBottom` as double and `fluidTop = fluidBottom + fluidState.getHeight(level, mutablePos)` (58-59). It skips only when `fluidTop < box.minY` (60), finds the tag tracker (61-67), records eye-fluid state (68-70), and updates maximum depth as `max(fluidTop - entity.getBoundingBox().minY, tracker.height)` (72). If not ignoring current, it gets the native per-cell flow, scales it by current maximum depth when `< 0.4` (double literal), then accumulates it (73-80).
- `private static boolean hasFluidAndLoaded(final Level level, final int x0, ... final int z1)` (91-119) checks all affected chunk coordinates with `getChunk(chunkX, chunkZ, ChunkStatus.FULL, false)` and returns false on the first null chunk (100-105). It checks section `hasFluid()` and returns that boolean (107-118). The call bounds include x/z expanded one block around the scan bounds (42). Thus no scan/cell reads occur if this preflight fails. This is native behavior to leave intact.
- `public void applyCurrentTo(final TagKey<Fluid> fluid, final Entity entity, final double scale)` (132-137) finds the tracker and delegates to its `applyCurrentTo` (133-136).
- Private nested `Tracker.accumulateCurrent(final Vec3 flow)` (166-169) adds each vector to `accumulatedCurrent` and increments an `int currentCount`.
- Private nested `Tracker.applyCurrentTo(final Entity entity, final double scale)` (171-188) gates on nonzero count and `!(lengthSqr() < 1.0E-5F)`; non-`Player` vectors are normalized, whereas a `Player` vector is averaged via `accumulatedCurrent.scale(1.0 / currentCount)` (174-178). It reads old movement, scales the impulse, then applies an extra minimum-current rule: if `abs(oldMovement.x) < 0.003`, `abs(oldMovement.z) < 0.003`, and `impulse.length() < 0.0045000000000000005`, it normalizes and raises impulse length to that threshold (180-185), then `entity.addDeltaMovement(impulse)` (187). The local `double min = 0.003` at 182 is unused. The explicit float literal `1.0E-5F` is compared against the double result of `lengthSqr()`; flow/impulse scaling, division, coordinates, surface, and depth calculations are doubles unless expressed otherwise.

`net/minecraft/world/entity/Entity.java`, source SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.

- `public @Nullable AABB getFluidInteractionBox()` (4088-4097) starts with `getBoundingBox().deflate(0.001)`, and lets a passenger's vehicle modify that box. This matches the accepted near-full-box scan geometry for ordinary unmounted players; passenger-specific box behavior is an enclosing modern gate/dependency.
- `protected boolean updateFluidInteraction()` is the water-current dispatch operation above (1645-1669).
- `public boolean isPushedByFluid()` defaults true (3277-3279); player behavior below overrides it.

`net/minecraft/world/entity/player/Player.java`, SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`: `public boolean isPushedByFluid()` at 1693-1696 returns `!this.abilities.flying`. This is the current player gate: flying players cause `update(..., ignoreCurrent=true)` and do not enter current application; non-flying players do. It matches the accepted old `PlayerEntity.hasLiquidCollision() == !abilities.flying` condition. The water tracker is populated even when current is ignored, so this flag affects only flow accumulation/application, not the fluid-height scan.

## Lowest operation and dispatch point

The narrow water-current operation site is the `Entity.updateFluidInteraction()` water call at line 1659; it occurs only after `EntityFluidInteraction.update()` has returned, including its native chunk/section loaded-area preflight. A client-only local-player response can be dispatched at that water-current application site, guarded for the local player/client and retaining the existing `inWater` and `isPushedByFluid()` gates. Leave the `update()` / `hasFluidAndLoaded()` path native. The operation's lowest direct velocity writer is `Tracker.applyCurrentTo()` line 187 (`Entity.addDeltaMovement(impulse)`); its player-vector averaging at 177 is the nearest lower-level implementation of the accepted historical averaging behavior, but the nested tracker method is private. `Entity.updateFluidInteraction()` is the public/protected seam that still exposes the water tag and `.014` scale without changing the scan preflight. This is only a source-map dispatch suggestion, not a product-design or implementation conclusion.

Historical accepted semantics relevant to the mapping: 1.13 water scan qualifies tagged fluid surface at/above contracted box minY, keeps maximum immersion depth, scales contributing flows by running depth below 0.4, averages player flow without normalization, and applies `0.014` when nonzero; the player eligibility gate excludes flying. The 26.2 source has these same scan/average components, while `Tracker.applyCurrentTo` also applies the modern low-movement impulse minimum described above. This report does not establish product readiness or a new source verdict.

## Client/server split

There is no `isClientSide()` condition in `Entity.baseTick()`/`updateFluidInteraction()`: it is shared native entity logic and is reached on both sides. Client entity ticks enter through `ClientLevel.tickNonPassenger` (467-472); `LocalPlayer.tick` also has the `connection.hasClientLoaded()` gate (227-230) before its superclass tick. Server entity ticks enter through `ServerLevel.tickNonPassenger` at `ServerLevel.java:826-833`. Server player ticks enter through `ServerPlayer.doTick()` at `ServerPlayer.java:648-652`, where `super.tick()` follows the server's spectator/unloaded-chunk guard. `ServerLevel.java` SHA-256 `06c436ebb91c04f8c30b58bdf115f87da8b5d0d14d5551c6b33e228744037cb9`; `ServerPlayer.java` SHA-256 `37e617a5beccbf3558c8cf8f6624ba9d704593ccc5d6633bedf71cf4f792e82e`.

A client response therefore must not dispatch based only on the shared `Entity.updateFluidInteraction` hook: gate to the logical client and local player. The server retains vanilla water current. This source map did not inspect product code or establish where any existing mod hook is ready.

## Source paths and hashes

- `net/minecraft/client/multiplayer/ClientLevel.java`: `f0255138128f19fb354e1a208f457e7fdb2df986f3103ec91f52bcabfa076631`
- `net/minecraft/client/player/LocalPlayer.java`: `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`
- `net/minecraft/server/level/ServerLevel.java`: `06c436ebb91c04f8c30b58bdf115f87da8b5d0d14d5551c6b33e228744037cb9`
- `net/minecraft/server/level/ServerPlayer.java`: `37e617a5beccbf3558c8cf8f6624ba9d704593ccc5d6633bedf71cf4f792e82e`
- `net/minecraft/world/entity/Entity.java`: `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`
- `net/minecraft/world/entity/EntityFluidInteraction.java`: `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62`
- `net/minecraft/world/entity/LivingEntity.java`: `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` (consulted only for shared tick call chain).
- `net/minecraft/world/entity/player/Player.java`: `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`

No product sources, product readiness, backlog/wiki/other lanes, Git, source generation, builds, tests, or runtime were consulted.

