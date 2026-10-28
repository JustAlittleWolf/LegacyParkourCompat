# Neutral candidate contract: historical lava-presence latch

## Scope

This candidate covers one operation: the local client player's `isInLava()` result for the 1.16 / 1.16.1 profile. Mojmap 1.16.1 is the old side and Mojmap 1.16.2 is the new side. The accepted source comparison places the old behavior in 1.16 and 1.16.1, and the new native behavior in 1.16.2–1.16.5.

This is a lifecycle contract, not a lava-current, eye-fluid, damage, or server-state change. It makes no trajectory or runtime-parity claim. It does not claim full 1.16.1→1.16.2 pair coverage.

## Source-bound behavior

In 1.16.1, `Entity.isInLava()` returns a boolean latch. During `Entity.move`, after collision resolution and fall-damage handling, the method clears the latch and calls `checkInsideBlocks`. That query scans the current bounding box inset by `0.001` on each face only when `level.hasChunksAt(minPos, maxPos)` admits the entire position range. If admission fails, no block callbacks run and the just-cleared result remains false.

For each visited block, the old `LiquidBlock.entityInside` callback sets the latch only when that block's fluid is in `FluidTags.LAVA` and the source predicate holds:

```text
surfaceY = blockPos.y + blockState.getFluidState().getHeight(level, blockPos)
box.minY < surfaceY || surfaceY > box.maxY
```

For an ordered AABB, the second disjunct implies the first; preserve the source expression or its equivalent `box.minY < surfaceY`. Do not replace it with a conventional two-sided interval-overlap test. Any qualifying callback sets the result true; later non-lava callbacks do not clear it. The latch retains the last movement scan's result until the next `Entity.move` reaches its reset phase. Calls to `isInLava()` before that next reset read the retained result; this includes the following tick's base-tick lava/fall-distance check and subsequent player AI/travel consumers.

In 1.16.2, the callback latch is absent. The native query is `!firstTick && fluidHeight.getDouble(FluidTags.LAVA) > 0.0`, with cache refresh in `baseTick`. Keep that native operation active for 1.16.2 and later profiles.

## Candidate operation contract

Implement one independently resolved `LavaPresenceHook` operation, represented by a public no-argument `@MovementChange(emulates = ParkourVersion.V1_16)` change. It supplies the value read by `isInLava()` and the lifecycle updates needed to retain the historical latch. The chosen API must make these three phases explicit:

1. **Read:** return the stored historical boolean for the eligible local client player. Do not derive it from current fluid height or recompute it at the read site.
2. **Reset:** at the historical post-collision, post-fall-damage movement callback phase, set the per-player value to false before the admitted inside-block scan.
3. **Observe:** for each old-style `LiquidBlock` callback in the scan, set the value to true only under the exact lava-tag and surface predicate above. Observing any other block leaves the current value unchanged.

The state belongs to the local-player instance and is initialized false. Do not use a static/global entity map, and do not synchronize the old latch with `fluidHeight`, `isUnderWater`, or other fluid trackers. Preserve the old `hasChunksAt` admission and false-on-rejected-scan result. Preserve scan timing and the actual post-movement box. If the integration cannot identify the source-defined scan phase, bounds, or legacy block/fluid inputs, it must retain native behavior for that unsupported domain rather than inventing a substitute query.

The change is limited to logical-client local-player reads and lifecycle updates. Logical servers, remote players, non-player entities, disabled profiles, and profiles resolving to native 1.16.2+ behavior keep their native result and state.

## Integration requirements

- Keep the client mixin thin and route the read/update operation through `MovementRuntime` and automatic hook discovery.
- Preserve native behavior when no historical change resolves. The closest-later resolver must leave the 1.16.2+ profile on the native cached-height predicate.
- Do not duplicate or alter the numeric fluid-height scan, water-first/lava-current ordering, eye-fluid cache, `firstTick`, `lavaHurt`, or damage/fire behavior.
- Do not run the old scan on every predicate read; one reset and the source-ordered callback observations belong to each eligible movement completion.
- Change-source comments should cite both exact Mojmap versions, classes, and methods: 1.16.1 `Entity.move`, `Entity.checkInsideBlocks`, `Entity.isInLava`, `LiquidBlock.entityInside`; 1.16.2 `Entity.baseTick`, `Entity.updateInWaterStateAndDoFluidPushing`, `Entity.isInLava`, and the local-player travel/jump consumers.

## Evidence and limits

- Source report: `.native-source/20261028/lava-presence-client-consumers/REPORT.md`, SHA-256 `d3bbd6c106a0051396aca17aa0269e17fc217542a44f07656ada428a99454b05`.
- Independent audit: `.native-source/20261028/lava-presence-client-consumers-audit/audit.md`, SHA-256 `02e6604f2c3115247747c43eb06c0fe041fd017a6f4cbc88b10442b4b1466b6e`.

The source audit accepts the bounded lifecycle, local-client movement consumers, and 1.16.2–1.16.5 operation alias. Original cached binaries could not be rehashed for 1.16.1/1.16.2; 1.16.3/1.16.4 source identity relies on embedded provenance. The candidate does not expand those claims or infer whole-pair coverage.
