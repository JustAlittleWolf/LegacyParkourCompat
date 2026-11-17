# 1.16.1 → 1.16.2 fluid travel dependency closure (source-only, partial)

## Bound input identities

Both endpoints are exact Mojmap source publications described in `build/source-inputs-115-116-20261117/README.md`. The 1.16.1 source root is `decompiled_minecraft/1.16.1/mojmap/`, with complete source-manifest SHA-256 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` and provenance SHA-256 `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98`. The 1.16.2 root is `decompiled_minecraft/1.16.2/mojmap/`, with source-manifest SHA-256 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` and provenance SHA-256 `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`. The input locator reports complete, verified inventories (3,572 and 3,516 Java paths). No canonical sources were modified.

This report closes only the fluid-contact/provider caller slice named here. It is not a full pair coverage or finding acceptance.

## Exact source bindings checked

All hashes below match the named endpoint manifest entries and the corresponding disk files.

| Source file | 1.16.1 SHA-256 | 1.16.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `net/minecraft/world/level/block/LiquidBlock.java` | `e7b138668f426eaf7c2f89b34dd0448f6ba0eb133963929ebf12d64840f22fb3` | `24f1cfbd036cca77ec99d855a028194ed39f583699ce91bc2a4c61957ff22fb7` |
| `net/minecraft/tags/FluidTags.java` | `29756a98a15f0ab06c33eb6b414883c13c352c803e8c6d42608cbe7960905c58` | `75d072cdc3e9073fc3fa481e4ccadd802e77a880ec776412f69bc865c4c78267` |
| `net/minecraft/world/level/material/Fluids.java` | `7ee6840d600b06d6c803c8c741e39efdf5add797879f656a9c3abd9348fc7c64` | same |
| `net/minecraft/world/level/material/FluidState.java` | `665b4024a32f659d92ce1259b25f529616e44b6f3df8a6aab4a4a2c57c7bc26d` | same |
| `net/minecraft/world/level/material/FlowingFluid.java` | `a85a5cd6625b1b4a047bd693399e1f78c2978ae207af927d4abfac3b34229552` | same |

## Caller order and movement relevance

`LocalPlayer.tick()` delegates to `LivingEntity.tick()`, which delegates to `Entity.tick()` and `Entity.baseTick()`. `Entity.baseTick()` calls `updateInWaterStateAndDoFluidPushing()` before `updateFluidOnEyes()` and `updateSwimming()` (1.16.1 lines 346–374; 1.16.2 lines 354–382). A later locally controlled `LivingEntity.aiStep()` makes the fluid jump decision and invokes virtual `travel(new Vec3(xxa, yya, zza))` (1.16.1 lines 2366–2458; 1.16.2 lines 2368–2460); `LocalPlayer.aiStep()` delegates through `super.aiStep()` (1.16.1 line 813; 1.16.2 line 788). These establish a player path from the per-tick fluid state refresh into fluid jump/travel decisions.

The body of `LivingEntity.travel(Vec3)` is byte-for-byte equal across the endpoints when compared at the same method bounds (1.16.1 lines 1912–2045; 1.16.2 lines 1914–2047). Its water branch is checked before its lava branch. The branch body is unchanged, but its `isInLava()` and `getFluidHeight(FluidTags.LAVA)` inputs come from the changed Entity tracking below. The fluid-sensitive part of `LivingEntity.aiStep()` is also equal at the paired method bounds (1.16.1 lines 2366–2471; 1.16.2 lines 2368–2473): when `isInLava()` is true it chooses lava height for `k`, and may call `jumpInLiquid(FluidTags.LAVA)`; otherwise water height and water jump selection use `FluidTags.WATER`. Thus the changed Entity state feeds existing player jump/travel math without a `LivingEntity.travel` body delta in this slice.

## Changed fluid tracking and direct-contact path

In 1.16.1, `Entity.updateInWaterStateAndDoFluidPushing()` clears `fluidHeight`, updates water flow/contact, then returns immediately when `isInWater()` is true; it scans lava only after that early return (Entity lines 947–956). `isInLava()` reads the separate `isTouchingLava` boolean (lines 1065–1071). `Entity.move()` resets that boolean before `checkInsideBlocks()` (around lines 551–553), and the old `LiquidBlock.entityInside()` calls `Entity.setInLava()` for lava-fluid contact under its AABB-height test (LiquidBlock lines 177–186; setter lines 1065–1067). This is a block-callback-derived contact flag, separate from the `fluidHeight` map.

In 1.16.2, the update method still clears `fluidHeight` and updates water, but unconditionally scans `FluidTags.LAVA` afterward and returns `isInWater() || bl` (Entity lines 957–963). `isInLava()` now reads `!firstTick && fluidHeight.getDouble(FluidTags.LAVA) > 0.0` (lines 1071–1073). `LiquidBlock.entityInside()` and `Entity.setInLava()` are absent. Therefore the source changes both the lava-contact producer and the state consumed by the player jump/travel branches.

The `updateFluidHeightAndDoFluidPushing(Tag<Fluid>, double)` method itself is text-identical at the paired bounds (1.16.1 lines 2633–2701; 1.16.2 lines 2641–2709). It scans the entity bounding box deflated by `0.001`, checks each occupied fluid state against the supplied tag, accumulates the maximum fluid-surface depth, reads each matching cell's flow vector, and adds a scaled average to velocity. It normalizes that vector only for entities that are not `Player`; this exception makes the same unchanged provider relevant to player velocity. The caller difference is that 1.16.2 performs the LAVA-tag scan even after water was found, while 1.16.1 skipped that scan on the water path.

`FluidTags` declares the same symbolic `WATER = bind("water")` and `LAVA = bind("lava")` names at both endpoints, although its helper setup differs. `Fluids.java` is identical and registers empty, flowing/source water, and flowing/source lava. `FluidState.java` and `FlowingFluid.java` are identical, so this paired source slice identifies no provider-body or registration delta. No tag resource payload was inspected: the actual `FluidTags.WATER` and `FluidTags.LAVA` resource membership and exact fluid-tag JSON inputs remain open and are not inferred from the declarations.

## Scope disposition and remaining gate

The source-defined delta is a changed player-reachable fluid state/contact path: the old lava flag came from the removed LiquidBlock callback, while the new map-based lava height is refreshed every base tick even when water contact is also present. The unchanged `LivingEntity.aiStep()` and `travel()` then consume the changed `isInLava`/lava-height state for liquid jump selection and lava travel. This is a source-level movement dependency, not a claim about a particular trajectory or a proven vanilla resource-state witness.

This packet does not establish whether the packaged vanilla water/lava tag membership and exact fluid arrangement produce a separating ordinary player state, nor close all fluid-driven callers, tick ordering beyond the checked chain, block/provider dependencies, other callbacks, or the pair. The source report’s other open scopes remain open. No resource extraction, runtime test, release-onset inference, or pair freeze is claimed.

## R2 call-graph correction (supplements caller-order section)

The player dispatch path includes the following exact intermediate overrides; the abbreviated chain in the original report omitted them. The cited `Player.tick()`, `Player.aiStep()`, and `Player.travel(Vec3)` method bodies and `LocalPlayer.tick()` are unchanged across 1.16.1→1.16.2, even though the enclosing source files differ. `LocalPlayer.aiStep()` itself differs: 1.16.1 uses `checkInBlock(...)` for its four suffocation probes and 1.16.2 uses `moveTowardsClosestSpace(...)`; the `super.aiStep()` call is present in both. That separate response is covered by the paired `local-player-suffocation-escape.md` candidate described in the original report, not treated as an unchanged method.

- `LocalPlayer.tick()` first checks `level.hasChunkAt(new BlockPos(getX(), 0.0, getZ()))`; only when true does it call `super.tick()` (1.16.1 lines 183–186; 1.16.2 lines 184–187). The inherited `Player.tick()` override calls `super.tick()` (lines 207 and 234 on both endpoint sources), which reaches `LivingEntity.tick()` and its `super.tick()`, then `Entity.tick()`→`baseTick()`. The checked fluid refresh therefore occurs on the ordinary loaded-chunk local-player tick path, subject to that existing gate.
- `LocalPlayer.aiStep()` calls `super.aiStep()` (1.16.1 line 813; 1.16.2 line 788). The inherited `Player.aiStep()` override performs its player-specific work then calls `super.aiStep()` (lines 483 and 500 on both endpoints), reaching `LivingEntity.aiStep()`. Its fluid jump section then dispatches `this.travel(new Vec3(this.xxa, this.yya, this.zza))` virtually (1.16.1 line 2457; 1.16.2 line 2459).
- That virtual call resolves to `Player.travel(Vec3)` (1.16.1 lines 1380–1410; 1.16.2 lines 1387–1417), not directly to `LivingEntity.travel`. `Player.travel` has a swimming pre-operation that can adjust vertical velocity, wraps `super.travel(vec3)` (the LivingEntity water/lava/travel branches) in ordinary and flying branches, performs flying post-operations when applicable, and then calls movement-statistic accounting. Both ordinary and flying branches call `super.travel(vec3)` at 1.16.1 lines 1399/1406 and 1.16.2 lines 1406/1413. The fluid-state dependency described above therefore feeds the existing superclass fluid handling inside the player wrapper; it does not establish a change to the wrapper itself.

The exact `Player.java` source bindings, each matching its endpoint manifest, are 1.16.1 `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` and 1.16.2 `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`. `LocalPlayer.java` bindings are 1.16.1 `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` and 1.16.2 `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`, likewise matching their manifest entries. These exact class hashes and method bindings are from the existing audit; no whole-source-tree rehash was repeated for r2.

This correction only completes the local player dispatch chain. Packaged fluid-tag membership, a concrete separating state witness, complete pair coverage, runtime behavior, and pair freeze remain open.
