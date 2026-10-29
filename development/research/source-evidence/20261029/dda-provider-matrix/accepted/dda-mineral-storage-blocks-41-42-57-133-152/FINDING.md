# Legacy full-cube storage blocks: source closure

## Scope and evidence

This source-only closure covers exact 1.8.9 and 26.2 provider facts for legacy registry IDs 41, 42, 57, 133, and 152. It verifies old registry identity and metadata defaulting, current registry keys/classes, historical mutable ray bounds, and current/default movement collision shape. It does not claim DDA visitation, hit ordering, complete adapter coverage, or overall movement parity.

Canonical sources are read-only: 1.8.9 Feather Gen2 build.2 merged-v2 at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8.9\ornithe-feather`, and 26.2 unobfuscated at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated`. Ready-marker, provenance, source-manifest, and selected Java hashes are recorded in `source-hashes.json`; all seven selected Java hashes matched their release `.sources.sha256` entries.

## Identities and state projection

| 1.8.9 ID/key | 1.8.9 implementation | 26.2 key and implementation |
|---|---|---|
| 41 `gold_block` | `Block` | `minecraft:gold_block`, generic `Block` |
| 42 `iron_block` | `Block` | `minecraft:iron_block`, generic `Block` |
| 57 `diamond_block` | `Block` | `minecraft:diamond_block`, generic `Block` |
| 133 `emerald_block` | `Block` | `minecraft:emerald_block`, generic `Block` |
| 152 `redstone_block` | `RedstoneBlock` | `minecraft:redstone_block`, `PoweredBlock` |

The old numeric registrations and names are in 1.8.9 `net/minecraft/block/Block.java:896–916,945–957,1120–1134,1158–1171`. `RedstoneBlock` extends `Block` and only overrides signal-source/signal behavior (`RedstoneBlock.java:10–23`); it adds no state property or geometry provider. In the current source, `BlockItemIds.java:126–128,403,553` declares the same five lowercase block keys, and `Blocks.java:1050–1069,1292–1295,2671–2680,2906–2916` registers them. The four mineral blocks use the ordinary properties-only registration, which resolves to `Block::new` (`Blocks.java:5991–5999`); the redstone block uses `PoweredBlock::new`. `PoweredBlock.java:9–29` adds signal behavior only. These are exact same-key aliases; no name-similarity inference or changed-key datafix is needed for this set.

All five old registrations use propertyless base states. The inherited `Block.getStateFromMetadata` returns the default state for any metadata input (`Block.java:182–184`), and the inherited metadata encoder returns 0 when the state has no properties (`Block.java:186–192`). Thus metadata does not select shape or another state for these blocks; serialization normalizes to metadata 0. Current registrations likewise add no shape/state properties for these block instances.

## Historical and current provider behavior

The old `Block` constructor initializes all six mutable bounds to the unit cube and installs the state-definition default (`Block.java:198–206`). Its default `updateShape` is empty (`Block.java:596–598`), default ray provider updates then clips against the instance bounds (`Block.java:456–516`), and default `getCollisionShape` builds an AABB from those same six bounds (`Block.java:346–350`). `addCollisions` appends that box when it intersects the query (`Block.java:339–344`). None of these five registrations uses a custom ray, `updateShape`, or collision-shape writer. The 1.8.9 source classes are the generic `Block` for IDs 41/42/57/133 and `RedstoneBlock` for 152; the latter's only overrides are the signal methods described above. Therefore each contributes a full unit-cube movement box and uses a full-cube ray box.

In 26.2, `BlockBehaviour.getShape` defaults to `Shapes.block()` (`BlockBehaviour.java:323–325`). Its default collision provider returns the state's shape when `hasCollision` is true (`BlockBehaviour.java:327–328`); `Properties.hasCollision` defaults true (`BlockBehaviour.java:985`), while `.noCollision()` is the opt-out (`BlockBehaviour.java:1094–1097`). The four properties-only registrations do not call `.noCollision()` and their generic `Block` provider has no custom shape override. The redstone registration uses `PoweredBlock`, which likewise has no shape override. All five therefore retain full-cube current outline and movement collision shapes.

The `redstone_block` class changed from `RedstoneBlock` to `PoweredBlock`, but both implementations supply redstone signal behavior without changing this bounded shape result. This finding does not compare all signal/update semantics.

## Bounded conclusion

For these five IDs, source proves unchanged registry-key identity, old metadata collapse to the default state/metadata 0, no legacy per-query bounds writer, and full-unit-cube ray/movement provider geometry at both endpoints. This closes only these five provider cases; it does not make the full old-world ray visitor or mutable-bound history adapter complete.
