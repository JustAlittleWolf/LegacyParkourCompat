# Local player tick gate and same-tick world-limit clamp (r2)

Pair: `1.9.2--1.9.3`; namespace `ornithe-feather`.

## Source identity

- 1.9.2 mapping-local source manifest SHA-256: `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`.
- 1.9.3 mapping-local source manifest SHA-256: `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`.
- Independent audit verdict identity: `A065D6583B5403F853A60324E0C02209F6CD692FFF9188D1D886F13065B16B01`.

## Exact path and gates

In 1.9.2, `World.isChunkLoaded(BlockPos, boolean)` first checks `contains(pos)` and returns false when x/z lies outside `[-30,000,000, 30,000,000)` (or y is outside world height). In 1.9.3, that overload directly queries `isChunkLoadedAt(x >> 4, z >> 4, allowEmpty)`. The one-argument overload passes `true`; `ClientWorld.isChunkLoadedAt` returns true for `allowEmpty` in both versions. The removed bounds guard therefore changes the result for an out-of-range local-player tick query, not the world chunk contents.

`LocalClientPlayerEntity.tick()` queries this predicate at `new BlockPos(x, 0.0, z)` before `super.tick()`. If false, the entire local tick body is skipped, including movement and the subsequent client packet sends. If true, it calls `super.tick()` and then either sends the riding-specific angles/input/vehicle packets or, when not riding, calls `sendMovementToServer()` for the local player movement state. This finding concerns the local player tick and its on-foot route; server simulation and vehicle physics are outside scope.

`PlayerEntity.tick()` sets `noClip = isSpectator()` at entry. After `super.tick()` has run movement, the same method clamps x and z to `[-29,999,999, 29,999,999]` and calls `setPosition` if either coordinate differs. The clamp is present in both releases; the changed local gate determines whether this same-tick player tick and clamp execute.

## Movement result

For a local player already outside the x/z world-coordinate range, 1.9.2 fails the local chunk gate and skips the tick, leaving the out-of-range position unchanged for that tick. In 1.9.3 the gate admits the tick; `PlayerEntity.tick()` runs and clamps x/z to ±29,999,999 before the tick returns. This is a one-tick clamp back to the limit, not continuing movement beyond it. Spectator mode selects the no-clip movement path, but the same-tick player clamp still follows movement. The finding is limited to this client local-player gate, same-tick movement/clamp order, and associated local send branch; it does not claim changes to packets' semantics, server physics, world generation, or block-state behavior.
