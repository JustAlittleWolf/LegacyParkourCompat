# Independent source audit verdict

## Verdict

**ACCEPT — no difference detected for the bounded 1.10 → 1.10.1 operation.** The authorized source comparison's bounded claim is supported by an independent trace. This verdict applies only to the frozen roster in `roster.md` (SHA-256 `77ceffac76968495fd8b4623648d20be9a3dfadfb12856252b7411fc04c15bab`) and to the exact inputs below. It is not a full-pair freeze, an intermediate patch boundary, or runtime parity evidence.

## Immutable reviewed input

Authorized comparison input: `lane-falling-water-fence-110-1101/comparison.md`, SHA-256 `128f414e4247a3cb53f9accbdbadd7eff6a647c1ea68e5be977af27765d161`. This exactly matches the assigned hash. The blind roster was frozen before opening that input.

Pair identity is the canonical Ornithe Feather output for 1.10 and 1.10.1. Exact client artifacts, mapping artifacts, mapping metadata, and provenance-manifest hashes are recorded in `roster.md`. The source files cited here matched their respective `.decompile-provenance.json` entries. Relevant identities include:

- `LiquidBlock.java`: both `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e`.
- `FenceBlock.java`: both `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909`.
- `Block.java`: both `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01`.
- `Material.java`: both `5d682e9ea5a30540e62a68879fbdabd2dc1fb79781a8350c61370c1166654a7d`.
- `Entity.java`: both `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`.
- `LivingEntity.java`: both `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`.
- `LocalClientPlayerEntity.java`: both `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`.
- `ClientPlayerEntity.java`: both `d9f0ec60a318ac51fd49198aaccd5c3e2b3af95cd3e2df577bb25226f3e0e4e4`.
- `World.java`: 1.10 `827fea19d95ddc0a2847859f0c288bf0b6d6dc3692feb30b52b84c60fa19d877`; 1.10.1 `41fd1edc24b39a5b9a979d15d54fbd3ebf9818651628109f232fcc24c7e28d0a`.

## Independent trace

### Falling-water cue beside ordinary dry oak fence

In both sources, `LiquidBlock.getFlow` (`net/minecraft/block/LiquidBlock.java:131–171`) first accumulates horizontal flow from neighboring liquid depths. For a state with `LEVEL >= 8`, it then iterates horizontal directions and checks both the same-height adjacent position and that position one block above (`:160–165`). The direction argument remains horizontal for both calls. If either `isFaceSolid` call succeeds, it normalizes the accumulated vector, adds `(0.0, -6.0, 0.0)`, breaks, and normalizes again on return (`:164–171`). `applyMaterialDrag` adds the returned flow to the current accumulated vector (`:175–176`).

For this exact cue, `LiquidBlock.isFaceSolid` returns false for the same liquid and ice, and otherwise delegates for horizontal faces (`:82–90`). The ordinary oak fence is registered with `Material.WOOD` (`net/minecraft/block/Block.java:925`); the fence's non-cube status is irrelevant because inherited `Block.isFaceSolid` reads the neighboring state's material and calls `isSolid()` (`Block.java:332–333`). `Material.isSolid()` defaults to true (`material/Material.java:63–65`). Thus a fence at the horizontal neighbor position or at that position one block higher supplies the same solid-face cue. The paired `FenceBlock`, `Block`, `Material`, and `LiquidBlock` files are byte-identical by SHA-256, so this operation did not change across the endpoints.

The frozen roster also included fence virtual-property, shape and collision methods and their state predicates. `FenceBlock.java` and `BlockState.java` are byte-identical across the pair; no additional difference was found there. Those methods do not determine the liquid face predicate above.

### Local client player water-current path and gates

`LocalClientPlayerEntity.tick` (`net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:152–154`) calls `super.tick()` only when the player's current chunk is loaded. The superclass chain reaches `Entity.baseTick`, which calls `checkWaterCollisions` (`net/minecraft/entity/Entity.java:312–366`). Except for a boat mount, that method passes the entity box grown by `(0.0, -0.4F, 0.0)` and contracted by `0.001`, water material, and the player to `World.applyLiquidDrag` (`Entity.java:862–877`).

`World.applyLiquidDrag` (`net/minecraft/world/World.java:1564–1605`) computes floor/ceil bounds and returns false before scanning if `isAreaLoaded(..., true)` fails. It scans X, then Y, then Z; for water-material cells whose liquid surface reaches the entity bounds, it calls `applyMaterialDrag` and accumulates each result. For nonzero aggregate flow with `entity.hasLiquidCollision()`, it normalizes the aggregate once and adds `0.014` times each component to velocity X/Y/Z. Although `World.java` differs as a whole, the complete `applyLiquidDrag`, `isAreaLoaded`, `isChunkLoaded`, and `getBlockState` method bodies match exactly across this pair. `ClientWorld.java` and `WorldChunk.java` also have identical source hashes, and the client `isChunkLoadedAt` body matches. These close the relevant loaded-query path.

One applicability detail beyond the comparison note: `PlayerEntity` overrides the default `Entity.hasLiquidCollision()` with `!this.abilities.flying` (`net/minecraft/entity/living/player/PlayerEntity.java:1736–1738`). Therefore the current impulse is skipped for a flying player, while `applyLiquidDrag` can still report water overlap and `checkWaterCollisions` can still mark the player in water. This override is present in both releases; the paired method bodies match. The comparison's statement about the inherited default `true` is accurate for the base implementation, but should not be read as saying every local player always receives the impulse.

There is no client-only condition in this vanilla shared liquid-collision path. This audit follows the local client player's ordinary tick caller only; it does not authorize or claim historical movement simulation on logical servers.

## Scope caveats

- The cue result is limited to the exact ordinary dry oak fence registered in these sources and horizontal neighbor positions. It does not generalize to other fence materials or variants.
- The `LEVEL >= 8` falling-flow branch and both same-height/upper-neighbor queries are part of the precondition; no claim is made for other liquid levels or vertical neighbor positions.
- The pair is 1.10 → 1.10.1 only. It does not identify when the behavior was introduced or prove behavior for 1.10.2 or other releases.
- This is source acceptance for the bounded claim only. No build, tests, gameplay, recording, or runtime-parity check was performed or authorized.