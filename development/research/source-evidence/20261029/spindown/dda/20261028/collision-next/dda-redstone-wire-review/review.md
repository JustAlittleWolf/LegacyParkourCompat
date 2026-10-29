# Independent review: Redstone Wire ID 55 source closure

## Verdict

**ACCEPT**, bounded to the source claims in the reviewed finding: legacy ID 55 and current `minecraft:redstone_wire`; old metadata and neighbor-derived connection inputs; old null movement collision and inherited fixed ray bounds; current empty collision and state-derived outline. This does not establish full state migration, DDA visitation/order, first-hit parity, or player reachability.

## Reviewed inputs and hashes

- Finding: `../dda-redstone-wire-55/FINDING.md`
- Finding SHA-256: `8B8A99F707BD883686D64539F935F55A6CBE498C236C4278D875AD55BB02455D`
- Source ledger: `../dda-redstone-wire-55/source-hashes.json`
- Source ledger SHA-256: `C677A7F5BF340E4E8DBE7BF96E0F2C2EEE4DCD375E701505DBDED64D57AE1324`

Both exact release identity rows were rechecked. For 1.8.9/ornithe-feather and 26.2/unobfuscated, the ready-marker status and source-file counts match; ready marker, provenance, and source-manifest file digests match the ledger; each ready marker declares the same source-manifest digest. All nine selected source files were rehashed from the canonical source trees and match both the ledger digest and the corresponding `.sources.sha256` row (9/9, zero mismatches).

I separately checked `26.2/unobfuscated/net/minecraft/world/level/block/state/properties/BlockStateProperties.java:120`, because `RedStoneWireBlock.POWER` aliases this shared property. It defines POWER as 0–15. Its canonical source SHA-256 is `C6E4B49663A09110E96A3B51628F92DEFD83DA9267A5D569A8AC8FC0C8893CD0`; the digest matches `26.2/unobfuscated.sources.sha256`. This auxiliary file is not among the finding's nine selected-source rows.

## Source checks

- `Block.java:943` registers legacy ID 55 as `redstone_wire`. `RedstoneWireBlock` defines POWER 0–15; metadata decode/encode reads/writes POWER, while `resolveVirtualProperties` separately reconstructs directional connection properties from world context. Its neighbor checks include horizontal states, below/above positions, the wire-above state, and repeater direction/signal-source checks. The report correctly treats those states as separate inputs, rather than metadata.
- The old `getCollisionShape` returns null. The constructor sets the shared ray bounds to the full block footprint with height 1/16. The old class has no rayTrace/updateShape override; inherited `Block.rayTrace` dispatches to the inherited no-op `updateShape`, so the default clip uses those constructor bounds.
- `BlockItemIds.REDSTONE_DUST` explicitly uses `minecraft:redstone_wire`; `Blocks.REDSTONE_WIRE` registers `RedStoneWireBlock` with `.noCollision()`. `BlockBehaviour` confirms that this disables the base movement collision shape. The current wire's shape is precomputed from the four connection properties and returned through `getShape`; the finding correctly distinguishes this state-dependent outline from movement collision and from the old fixed ray box.
- `EntityBlockStateFix` maps the legacy name `minecraft:redstone_wire` to ID 55 in its entity block-state conversion table, and `DataFixers` registers `RedstoneWireConnectionsFix`. The latter changes isolated `none` connection properties into a cross-shaped state. The finding properly says this documents a datafix rule, not that every live state has passed through it.

## Limits

The finding's exclusions are appropriate. These checks establish neither that a particular current picking caller selects outline geometry nor that the datafix ran for a live block. They do not establish a source-compatible runtime state view, DDA visitation/order, first-hit parity, or movement consequence. No product code, tests, builds, or runtime were inspected or changed.
