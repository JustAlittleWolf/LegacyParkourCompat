# Old trapped chest (numeric block ID 146): independent source closure

## Scope and result

This finding closes the source-coverage row for old 1.8.9 trapped chest ID 146. It establishes that ID 146 is its own `ChestBlock(type=1)` singleton, separate from normal chest ID 54, and independently follows the old ray-write and movement collision-consumer paths. It does not claim DDA equivalence, movement parity, or that ID 54 evidence transfers to ID 146.

## Old 1.8.9 source

Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8.9\ornithe-feather` (manifest and file digests in `source-hashes.json`).

- `net/minecraft/block/Block.java:1142` registers numeric ID 146 as `new ChestBlock(1)` under `trapped_chest`; `Block.java:942` separately registers ID 54 as a fresh `new ChestBlock(0)`. These are different object instances, not one instance reused under two IDs.
- `net/minecraft/block/ChestBlock.java:30-37` stores the constructor parameter in the instance field `type` and initializes mutable instance bounds to `[1/16,0,1/16]..[15/16,14/16,15/16]`. Thus the trapped and normal chest objects have separate mutable bound fields. The trapped flag affects redstone (`isSignalSource`, lines 378-380), while the shown shape-update body has no `type` conditional.
- `ChestBlock.java:56-67` recomputes the instance bounds on every `updateShape` call. It probes north, south, west, east in that `if/else-if` order and only recognizes a neighbor whose block object is identical to `this`. For ID 146, this means only another trapped-chest singleton qualifies. The first matching side sets the corresponding one-block extension; otherwise it restores the single-chest bounds.
- `net/minecraft/block/Block.java:456-459` calls virtual `updateShape(world,pos)` before clipping the ray against the instance min/max fields. The update is therefore a mutable bounds write even when the ray later misses.
- `Block.java:339-350` obtains the collision box from the same instance min/max fields and appends it only when it intersects the query box. `net/minecraft/world/World.java:891-923` scans block positions and calls `addCollisions`; `net/minecraft/entity/Entity.java:439-459` consumes that list for the ordinary move axes, and lines 469 onward repeat collision collection for step-up movement. This independently confirms ID 146 reaches the old movement collision consumer through the shared base implementation.
- `ChestBlock.java:434-450` defines the old state as horizontal `FACING`; metadata directions on the vertical axis normalize to NORTH. The mutable geometry update does not read FACING, so this bounded ray-write/collision-shape finding is independent of the facing value.

No `ChestBlock` override of the base ray or collision-shape methods was found in the audited class. This conclusion is limited to ID 146's registered singleton and these inspected base/class paths.

## Current 26.2 source

Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated` (manifest and file digests in `source-hashes.json`).

- `net/minecraft/util/datafix/fixes/ItemIdFix.java:139` maps numeric ID 146 to `minecraft:trapped_chest`; `net/minecraft/references/BlockItemIds.java:571` declares that key.
- `net/minecraft/world/level/block/Blocks.java:2883-2887` registers the key with a `TrappedChestBlock` constructor and ordinary colliding block properties (there is no `.noCollision()` call in this registration).
- `TrappedChestBlock.java:19,27-34` is its own `ChestBlock` subclass and uses the trapped-chest block entity type. It inherits the chest shape implementation; its signal-source override at lines 41-44 is not a collision-shape override.
- `ChestBlock.java:188-197` connects only to a same-block neighbor (`blockState.is(this)`) and returns the state-selected `SHAPE` or direction-specific half-shape. `BlockBehaviour.java:327-328` uses the state shape as collision shape when the block has collision. This establishes the current trapped-chest collision provider independently; it is not the old mutable per-block bounds mechanism.
- `BlockStateData.java:6694-6697` contains current trapped-chest rows with facing north/south/west/east and type single. This is evidence for the listed current state forms only; it is not asserted here as a complete old-metadata migration table.

## Bounded conclusion

The ID 146 source row is closed for old object identity, constructor flag, metadata/state encoding, neighbor identity/priority, mutable bounds write, old movement collision consumer, and the current registry/shape provider. ID 54 cannot stand in for ID 146: ID 146 uses a distinct `ChestBlock(1)` instance, and its neighbor topology compares against that distinct instance. No claim is made about current-vs-old shape parity, traversal visitation equivalence, exhaustive migration of metadata, or a DDA adapter.

## Sources and hashes

See `source-hashes.json` for exact SHA-256 hashes of every inspected source and the source manifests/readiness/provenance records. Each selected source digest was checked against its release manifest entry.
