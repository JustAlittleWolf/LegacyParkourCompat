# Blind Mojmap source audit: 1.21.10 → 1.21.11

## Frozen scope and verdict

Bounded slice: ordinary direct, non-passenger `LocalPlayer` tick entry; `Entity` position-difference speed cache (`lastKnownPosition` / `lastKnownSpeed`); every source reader and dispatch route; and the reset boundary through dimension/pose refresh. This is not a complete movement-pair freeze.

**Source verdict:** 1.21.11 adds a tick-boundary position-difference cache to `Entity`; the paired 1.21.10 `Entity` has no corresponding field or methods. A direct local player reaches the cache update each tick through its inherited `Entity.baseTick`. The reset in `reapplyPosition` means the first subsequent sample is zero, including after `refreshDimensions` (the pose-sync route). The complete 1.21.11 reader/call-site inventory has no reachable ordinary client-local movement-state consumer. The two nonlocal consumer families are a server AI predicate and a server-only kinetic-weapon combat path; neither establishes a local movement-parity effect for this slice. No source finding is proposed from this bounded audit.

## Exact input and integrity

Source roots: `decompiled_minecraft/1.21.10/mojmap/` and `decompiled_minecraft/1.21.11/mojmap/` (Mojmap / Mojang mappings). Root metadata identifies exact `versionId` values 1.21.10 and 1.21.11. Both `mojmap.provenance.json` sidecars record the exact batch command `decompileMinecraft --versions=1.21.8,1.21.10,1.21.11 --mappings=mojmap --decompiler-heap=4G`, with Mapping IO 0.9.1, Vineflower 1.12.0, Java 25.0.3+9-LTS, Gradle 9.7.1 and Tiny Remapper 0.14.1. The canonical mapping directories do not contain `.decompile-provenance.json`; the version-root `mojmap.provenance.json`, `mojmap.ready.json`, `artifacts.sha256` and `mojmap.sources.sha256` are the available published sidecars.

Each ready marker's `versionId`, `mapping`, `versionMetadataId`, client SHA-256 and source file count agree with the corresponding provenance/source-root identity. Ready-marker source-manifest digest matches the actual manifest bytes: 1.21.10 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`; 1.21.11 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`. Both artifact manifests match their ready-marker digest `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`.

Pinned client inputs from ready markers / artifact manifest: 1.21.10 `client.jar` SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`; 1.21.11 `client.jar` SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`. Mapping files: 1.21.10 `client_mappings.txt` SHA-256 `2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39`; 1.21.11 `client_mappings.txt` SHA-256 `517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd`.

The source-manifest SHA-256 entries were checked against the current bytes for every cited Java source below. Primary file digests:

- 1.21.10 `Entity.java`: `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18`
- 1.21.10 `LocalPlayer.java`: `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`
- 1.21.10 `LivingEntity.java`: `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`
- 1.21.10 `Player.java`: `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`
- 1.21.11 `Entity.java`: `32314478c6036fa9f3f3cc409c61c622eeffc5a282d60e1011a1a33cc29cf18a3`
- 1.21.11 `LocalPlayer.java`: `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`
- 1.21.11 `LivingEntity.java`: `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`
- 1.21.11 `Player.java`: `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`
- 1.21.11 `FollowBoatGoal.java`: `5709c8043eb518b801ac7fd1d711aed17e0a57603a16ce546f6d90f77ca54d67`
- 1.21.11 `ItemStack.java`: `a40daaf8a0e31dbf1f0f5c5413c14265d52fa0117132e0aeb1bb8fa10487e01a`
- 1.21.11 `KineticWeapon.java`: `c24230e6d3ba0abcae8b23be898a17aae658867d7136b0a2388b815bd2cc443b`
- 1.21.11 `ServerPlayer.java`: `5cd23824185202d9367674c9c208415f0f5f9922a247f0749c28849e295694b0`

## Paired source evidence

1.21.10 `net/minecraft/world/entity/Entity.java`, `baseTick()` lines 479–532: no speed sampling. Whole-file inventory finds none of `lastKnownPosition`, `lastKnownSpeed`, `computeSpeed`, `getKnownSpeed` or `hasMovedHorizontallyRecently` in this release's paired `Entity` / `LocalPlayer` sources.

1.21.11 `net/minecraft/world/entity/Entity.java`:

- Fields lines 279–280 introduce `lastKnownSpeed = Vec3.ZERO` and nullable `lastKnownPosition`.
- `reapplyPosition()` lines 458–461 clears only `lastKnownPosition`, then reapplies the current position and bounding box.
- `baseTick()` line 487 invokes `computeSpeed()` before other base-tick work.
- `computeSpeed()` lines 540–547 seeds a null prior position from the current position, stores `position().subtract(lastKnownPosition)`, and then stores current position as the next prior sample. It does not divide by elapsed time. Thus first construction/reset sample is zero; later samples are the three-dimensional displacement between consecutive base-tick entries.
- `hasMovedHorizontallyRecently()` lines 850–852 tests `abs(lastKnownSpeed.horizontalDistance()) > 1.0E-5F`.
- `getKnownSpeed()` lines 3918–3919 delegates to a live controlling passenger that is a `Player`; otherwise it returns this entity's cached speed.

Direct local-player route: 1.21.11 `client/player/LocalPlayer.tick()` lines 211–235 enters `super.tick()` when the connection reports client-loaded; `world/entity/player/Player.tick()` lines 238–266 calls `super.tick()`; `world/entity/LivingEntity.tick()` lines 2610–2611 calls `super.tick()`; inherited `Entity.tick()` calls `baseTick()` (Entity lines 480–487). This same route is present in 1.21.10 (`LocalPlayer.tick()` lines 195–220, `Player.tick()` lines 240 onward, `LivingEntity.tick()` lines 2542 onward, `Entity.tick()` / `baseTick()` lines 475 onward), but its `baseTick()` has no cache update. For a normal non-passenger `LocalPlayer`, the cache sample therefore happens at the start of each inherited base tick, measuring displacement since the previous sample point; the current tick's travel updates position after that sample.

Pose/dimension reset boundary in 1.21.11 `Entity.java`: `setPose()` lines 413–415 updates synchronized `DATA_POSE`; `onSyncedDataUpdated(DATA_POSE)` lines 3211–3215 calls `refreshDimensions()`; `refreshDimensions()` lines 3226–3242 updates dimensions and eye height, then calls `reapplyPosition()` at line 3232. That clears the prior-position cache. Consequently the next `computeSpeed()` seeds from the then-current position and reports zero for that sample, rather than a displacement spanning the pose/dimension refresh. General `setPos()` itself does not clear this cache; the invalidation occurs at `reapplyPosition()`.

## Complete bounded cache reader/dispatch inventory

Exact-source searches over both full Mojmap trees for `lastKnownPosition`, `lastKnownSpeed`, `computeSpeed`, `getKnownSpeed` and `hasMovedHorizontallyRecently` found these 1.21.11 accesses only:

1. `Entity.computeSpeed()` reads/writes `lastKnownPosition` and writes `lastKnownSpeed`; `Entity.reapplyPosition()` resets `lastKnownPosition`.
2. `Entity.hasMovedHorizontallyRecently()` reads the cache. Its only call sites are `FollowBoatGoal.canUse()` lines 23–35 and `canContinueToUse()` lines 43–45. The goal is a `PathfinderMob` AI consumer, not the direct local player's tick/travel path.
3. `Entity.getKnownSpeed()` reads the cache when no live controlling Player dispatch applies. Its call sites are `KineticWeapon.getMotion()` lines 78–84 and the `ServerPlayer.getKnownSpeed()` vehicle delegation at lines 2146–2149. `ServerPlayer` overrides the getter to return its packet-derived `lastKnownClientMovement` except when delegating to a vehicle. `ItemStack.onUseTick()` lines 1093–1104 invokes `KineticWeapon.damageEntities()` only under `!level.isClientSide()`. That path's movement-derived value contributes to kinetic-weapon hit conditions/damage, within the excluded combat/damage lane, and does not consume the ordinary client `LocalPlayer` cache.

No other reader, reset, or dispatch site for this cache exists in the exact 1.21.11 source tree. The new `LocalPlayer` input/travel speed path (`getSpeed()`, input modifiers and travel) is separate; `getKnownMovement()` is also a distinct delta-movement API. Neither was counted as a cache consumer. Rendering, flight/swim, passenger/riding mechanics, new-only items and attack/damage behavior are not used as witnesses for the assigned ordinary local movement slice.

## Method-body digests used

SHA-256 of UTF-8 bytes from each exact decompiled method signature through its closing brace:

| Release | Source member | Lines | SHA-256 |
|---|---|---:|---|
| 1.21.10 | `Entity.baseTick` | 479–532 | `795e02b5745f34de67660b9201a437de3d95a42d2e79d00d90e8df8e4fc57346` |
| 1.21.10 | `LocalPlayer.tick` | 195–220 | `9f0bbe3102dc5ffc51978c16b231df3eb1a366bd30a1139c2ff68c6e8def5889` |
| 1.21.11 | `Entity.baseTick` | 484–538 | `485833c837b7bc050fe7cd464ff4c9825cdd9f9fb19c6044c43863e0a6705164` |
| 1.21.11 | `Entity.computeSpeed` | 540–547 | `eaeacea9d73df7fe292711c6d424b1d41d8cf6cd1e53544b2df14727b6563db6` |
| 1.21.11 | `Entity.reapplyPosition` | 458–461 | `3b222968d1fe6e05ac8b85f16234245fb42d9fe26a31e9b55754274b34ad454b` |
| 1.21.11 | `Entity.refreshDimensions` | 3226–3242 | `f8afbd0828402b1dba06bb941e3a75528989a8f775af0c997aa7806e2540b96e` |
| 1.21.11 | `Entity.hasMovedHorizontallyRecently` | 850–852 | `b461d79fae7d13e73d11a2594985f0d8498af07fac0d67ca5f06037b893be68d` |
| 1.21.11 | `Entity.getKnownSpeed` | 3918–3920 | `f315d744c9c8f32e92ed70cad0f3e902b223a4cb21344f83b13d09b988767d32` |
| 1.21.11 | `FollowBoatGoal.canUse` | 23–35 | `c44ffad2bd66602cfd1f918359211f41c8f555b0db19d1919ca7d224dc3ecc36` |
| 1.21.11 | `FollowBoatGoal.canContinueToUse` | 43–45 | `72f3fd8c688c35efd332700ca7b5194a6888007c97f4cab522aa131fa56a8a5e` |
| 1.21.11 | `KineticWeapon.getMotion` | 78–84 | `5520bbb9a6fdc0b9111c95bdb3548ddde5458253e7cf96c462c42fb099c193d6` |
| 1.21.11 | `KineticWeapon.damageEntities` | 98–140 | `010127d36d285174167a8f87606fd07ebe0a1a17b45377f2f0b6891929754fd9` |
| 1.21.11 | `ItemStack.onUseTick` | 1093–1105 | `90428921705a35795f4cc9b83af511768734ec67c12e95c65cda724a73bf6fef` |
| 1.21.11 | `ServerPlayer.getKnownSpeed` | 2146–2149 | `29259388bef0b54d2eec9b8e905a6e149b127c1258ec0df37887c4ed8cd283c0` |