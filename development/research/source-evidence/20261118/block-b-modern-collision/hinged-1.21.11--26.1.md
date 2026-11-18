# Source discovery report: ordinary fixed-block collision

Status: COMPLETED for the assigned bounded slice. No individual difference findings were drafted because the four paired shape implementations, state selectors, and relevant rotation helpers have no movement-affecting change in this comparison.

## Evidence identity and readiness

- Older source: exact `1.21.11`, Mojmap, `decompiled_minecraft/1.21.11/mojmap`. `mojmap.ready.json` says `ready`, version `1.21.11`, mapping `mojmap`; `mojmap.provenance.json` records the exact decompile batch and toolchain. `Get-FileHash -LiteralPath ...\mojmap.sources.sha256 -Algorithm SHA256` matched `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`.
- Newer source: exact `26.1`, published unobfuscated, `decompiled_minecraft/26.1/unobfuscated`. `unobfuscated.ready.json` says `ready`, version `26.1`, mapping `unobfuscated`; `unobfuscated.provenance.json` records exact metadata/client digest and the interrupted-download preservation/retry. `Get-FileHash -LiteralPath ...\unobfuscated.sources.sha256 -Algorithm SHA256` matched `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba`.
- Per-file body hashes for the compared classes and shared dispatch/rotation sources matched their respective entries in those verified source manifests. The complete pair is not frozen by this bounded report.

## Paired family coverage

Paths below are relative to each mapping root; 1.21.11 uses Mojmap names, 26.1 uses published unobfuscated names.

| Family | 1.21.11 evidence | 26.1 evidence | Result |
|---|---|---|---|
| Slab | `net/minecraft/world/level/block/SlabBlock.java`, `SHAPE_BOTTOM`, `SHAPE_TOP`, `getShape` lines 35–65 | same path, lines 35–65 | Same fixed state selector: bottom occupies Y 0–8, top Y 8–16, double uses full block. `WATERLOGGED` is not read by shape selection. |
| Stair | `net/minecraft/world/level/block/StairBlock.java`, shape constants and `getShape` lines 37–89 | same path, lines 37–89 | Same unions, rotation tables, and selector: HALF chooses top/bottom table; SHAPE chooses straight/outer/inner; FACING is adjusted for inner-left/outer-right. |
| Door | `net/minecraft/world/level/block/DoorBlock.java`, shape map and `getShape` lines 52, 79–82 | same path, lines 52, 79–84 | Same 3/16 panel source shape, horizontal facing map, and open/hinge direction selection; HALF does not alter the panel shape. |
| Trapdoor | `net/minecraft/world/level/block/TrapDoorBlock.java`, shape map and `getShape` lines 48, 71–72 | same path, lines 48, 71–72 | Same all-direction map: open chooses FACING; closed chooses UP or DOWN by HALF. `WATERLOGGED` is not read by shape selection. |

`Shapes.rotate`, `rotateHorizontal`, and `rotateAll` preserve the same transforms and direction tables: 1.21.11 `net/minecraft/world/phys/shapes/Shapes.java` lines 291–305, 372–419; 26.1 same path lines 322–336, 403–450. The returned shapes are static and do not depend on position, entity, neighboring blocks, or fluid state for these families.

## Ordinary collision context and cache

`CollisionGetter.getBlockCollisions(entity, box)` creates `CollisionContext.of(entity)` in both releases (1.21.11 `net/minecraft/world/level/CollisionGetter.java` lines 96–105; 26.1 lines 96–105). `BlockCollisions` passes that context to `context.getCollisionShape(state, getter, pos)` (1.21.11 `net/minecraft/world/level/BlockCollisions.java` lines 31–40, 77–85; 26.1 lines 34–52, 89–99). The block shapes above ignore that context. `BlockBehaviour` creates the per-state collision-shape cache using `CollisionContext.empty()` for non-dynamic shapes (1.21.11 `net/minecraft/world/level/block/state/BlockBehaviour.java` lines 481–486, 895–926; 26.1 lines 509–514, 924–947). These registrations do not mark the blocks dynamic; cached and entity-context paths therefore return the same fixed shapes. The paired general dispatch/cache logic has no relevant behavioral change.

## Registration choices / aliases checked

The registry sources identify ordinary slabs through `SlabBlock::new`, stairs through `registerLegacyStair` or `registerStair` (both construct `StairBlock` with a base state's properties), and door/trapdoor variants through their corresponding class constructors. The four WeatheringCopper subclasses extend the same four base classes on both sides and do not replace the inherited shape implementation. Thus material/property aliases do not create another collision-shape implementation in this assignment. Block states remain vanilla; this review does not compare state production beyond confirming the selectors consumed by the shape methods.

## Boundaries still open

This report closes only fixed ordinary dry collision shapes for slabs, stairs, doors and trapdoors across `1.21.11 → 26.1`. It does not establish complete pair coverage or an independent full-inventory audit; those remain required for pair freeze. It does not cover fence gates, 26.1.2→26.2, state production except shape selector inputs, AutoJump, support/step, fluids/effects, other block families, implementation, compilation, or runtime parity.
