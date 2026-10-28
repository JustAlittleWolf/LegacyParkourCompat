# DDA provider closure: legacy rail family IDs 27, 28, 66 and 157

## Source identity

The canonical old source tree is Minecraft 1.8.9, `ornithe-feather` (ready marker version `1.8.9`, mapping `ornithe-feather`, Feather Gen2 `1.8.9+build.2`). The current tree is Minecraft 26.2, `unobfuscated`. Both ready markers and provenance/source manifests are included in `sources.sha256`; every listed Java hash was rechecked against the exact source manifest. Canonical trees were read-only.

## Old-ID and current-key aliases

Old `Block.java` registers ID 27 as `golden_rail`/`PoweredRailBlock`, ID 28 as `detector_rail`/`DetectorRailBlock`, ID 66 as `rail`/`RailBlock`, and ID 157 as `activator_rail`/`PoweredRailBlock` (including the class used for both old powered variants). In 26.2 the corresponding keys are `powered_rail`, `detector_rail`, `rail`, and `activator_rail`; all four have distinct registrations and `BlockItemIds` entries. The old `golden_rail` spelling is a real rename: `BlockStateData` pairs current `powered_rail` states with historical `golden_rail` states, whereas the other three names are unchanged. Do not infer current key identity from ID or class names alone.

## Metadata and state bounds

Old and current rail shape enums share the ten serialized names and order: `north_south`, `east_west`, the four `ascending_*` forms, then four corner forms. The regular rail property admits all ten. The old normal-rail metadata decoder calls `Shape.byId(metadata)`; out-of-range values reset to ID 0, so metadata 10–15 decode to `north_south`.

Old powered rails, detector rails and activator rails decode shape as `Shape.byId(metadata & 7)` and `powered` from bit 3. Their shape property excludes all four corner forms. The old `BlockState` setter rejects a value outside the property’s allowed set, so metadata 6, 7, 14 and 15 throw rather than produce an old state. For the other values, shape IDs 0–5 and the powered bit are preserved. Current `PoweredRailBlock` and `DetectorRailBlock` use the corresponding straight-only shape property plus `POWERED`; current `ACTIVATOR_RAIL` also constructs `PoweredRailBlock`. Current rail states add `WATERLOGGED`; it does not affect the local geometry described below. This closes state-key/provider input only, not neighbor-driven rail updates or redstone behavior.

## Movement collision provider

The old `AbstractRailBlock.getCollisionShape` returns `null` for all four registrations and every rail state; `Block.addCollisions` only adds a non-null box. In 26.2, all four `Blocks` registrations use `Properties.noCollision()`, and `BlockBehaviour.getCollisionShape` returns `Shapes.empty()` whenever `hasCollision` is false. Thus the movement collision provider result is empty on both sides across the valid mapped rail states. This is a collision-provider finding, not a claim that the rest of the rail behavior matches.

## Outline/ray-query bounds and mutable cache

The separate old ray-query path is state-dependent and writes shared block-instance bounds. `AbstractRailBlock` starts with a full-width box of height `0.125F`; before ray tracing, `updateShape` reads the state’s `SHAPE` and sets height `0.625F` for any ascending rail, otherwise `0.125F`. Its `rayTrace` calls `updateShape`, then `Block.rayTrace` calls it again before intersecting the current `min/max` fields. The final shared bounds are not reset by this path. `Block.setShape` stores these bounds as mutable fields.

Current `BaseRailBlock` returns a state-derived immutable shape: `SHAPE_FLAT = Block.column(16, 0, 2)` (height `2/16 = 0.125`) and `SHAPE_SLOPE = Block.column(16, 0, 8)` (height `8/16 = 0.5`); `getShape` selects by `RailShape.isSlope()`. `Block.column` divides its coordinates by 16. Flat and corner outlines therefore have the same vertical range, while every ascending state has old height `10/16` and current height `8/16`. Both use the unit X/Z footprint. This closure identifies a per-block ray-query shape/cache difference; it does not establish a concrete DDA visitation, camera reachability or movement outcome.

## Scope and remaining dependencies

The report covers only the four registry aliases, metadata-to-shape/powered state decoding, no-collision movement provider, and the local outline-bounds provider. It does not close rail neighbor/connection derivation, powered-state production, current-only waterlogging behavior, ray traversal order/early-hit behavior, camera-tick reachability, other rail-adjacent blocks or runtime parity. The 1.8.9 invalid metadata exceptions are explicitly retained; they are not silently coerced to a valid straight state.

## Exact source locations

- 1.8.9 `Block.java:880-881,966,1173`: old ID/name/class registrations; `Block.java:292-298,340-348,456-462,596-597`: mutable bounds, movement collision, ray intersection and base update hook.
- 1.8.9 `AbstractRailBlock.java:34-62,479-529`: initial shape, null collision provider, ray-time update and old shape enum.
- 1.8.9 `RailBlock.java:11-36`; `PoweredRailBlock.java:13-27,38-42,154-168`; `DetectorRailBlock.java:24-36,161-175`: metadata/property definitions and decoders.
- 1.8.9 `StateDefinition.java:119-127`; `EnumProperty.java:15-24`: property values and invalid set rejection.
- 26.2 `BlockStateData.java:1048-1090,3540-3549,7035-7074`: current/historical state pairs, including the `golden_rail` rename.
- 26.2 `Blocks.java:709-714,1447-1449,2945-2947`; `BlockItemIds.java:643-646`: distinct current keys/registrations.
- 26.2 `RailShape.java:5-37`; `BlockStateProperties.java:36,44,74-82` straight/all shape property declarations; `PoweredRailBlock.java:17-32`; `DetectorRailBlock.java:31-48`; `RailBlock.java:16-22`: current shape/property schemas.
- 26.2 `BaseRailBlock.java:29-30,53-55`; `Block.java:167-184`; `BlockBehaviour.java:327-328,1094`: exact outline geometry and no-collision provider.

No product code, runtime, build or tests were used.

