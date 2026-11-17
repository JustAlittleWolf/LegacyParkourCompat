# Independent source audit: End portal frame collision provider

**Verdict: ACCEPT, bounded provider/state/query claim.** This accepts only the immutable report and evidence listed below for exact edge 1.11 → 1.11.1. It does not close pair coverage or establish a movement outcome for any trajectory.

## Reviewed input identity

- Report: `build/source-early-patches-20261117/1.11--1.11.1/providers/end-portal-frame-shapes.md`
- Report SHA-256: `1A4D5AE521F4719C0590513FFADBAAA6F62BF89960C5923AC63814E7F25D08DC`
- 1.11 provider source: `decompiled_minecraft/1.11/ornithe-feather/net/minecraft/block/EndPortalFrameBlock.java`, SHA-256 `B9396746F87A619C89DCAD660D434ABB57E1943A6FBEBB63A75D30A05F752965`.
- 1.11.1 provider source: `decompiled_minecraft/1.11.1/ornithe-feather/net/minecraft/block/EndPortalFrameBlock.java`, SHA-256 `E4E9B5AECA29EFF111A8ECCE69C7267A6BC1D0458A9E528F9522D08F58584AF4`.
- Exact source-list and provenance sidecar hashes stated in the report were recomputed and match: 1.11 sources `8B3E5DA58494F1DBA77410960753B0BF646425A1ADA491D8242CC71E6C7A59CA`, provenance `6F775C806FD1401A4D1756E9EF88DA8D73185FE1D13598235F85C424405F4C40`; 1.11.1 sources `0D5F4697D4E4CE53B36D0DBFEC89D44830EB76753A6643B49E42F0857753066D`, provenance `0F6C92B8FBF4B1053A26A4CB61DC8F20C695E74A1963B49D911B8C7FBF01A055`. The EndPortalFrame manifest rows match each direct source hash. Readiness sidecars also match the hashes in the report.

## Audit findings

- `EndPortalFrameBlock` defines `FACING` as the horizontal facing property and `EYE` as a boolean. `HorizontalFacingBlock.FACING` is `DirectionProperty.of("facing", Direction.Plane.HORIZONTAL)`, so the declared legal product is the four horizontal facings × two eye values.
- Both versions return the same `SHAPE` from `getShape`: `(0, 0, 0)–(1, 0.8125, 1)`. Both collision bodies add that box and, when `world.getBlockState(pos).get(EYE)` is true, also add the same centered eye box `(0.3125, 0.8125, 0.3125)–(0.6875, 1, 0.6875)`. Neither shape depends on facing. `Block.addCollision` moves the local box to the block position and adds it only when it intersects the query box. The base and eye boxes meet at Y=0.8125.
- The only reviewed provider signature change is the additional `boolean forceShape` parameter in 1.11.1. Its method body does not read the parameter and retains the same box additions and eye-state lookup. `World.getCollisions(entity, shape)` calls provider callbacks with `forceShape=false` in 1.11.1. The 1.11 query passes the same entity and query box without that added parameter.
- Local-player applicability is established at the query boundary: `LocalClientPlayerEntity` extends `ClientPlayerEntity`, which extends `PlayerEntity`, then `LivingEntity`, then `Entity`. `Entity.move` calls `world.getCollisions(this, this.getShape().expanded(x, y, z))`; therefore an ordinary local-player movement query whose box intersects an EndPortalFrame collision box reaches this provider. This is only a source-level provider/query-path statement, not a particular player trajectory or resulting displacement.

## Limits

This audit does not assess portal activation, redstone, item insertion, rendering, world generation, other provider/state dependencies, or the rest of the 1.11 → 1.11.1 player tick graph. No pair freeze is inferred.
