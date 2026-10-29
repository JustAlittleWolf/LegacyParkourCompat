# Independent review: DDA age-crop family source closure

Status: **ACCEPT**, bounded to the five named aliases' individual movement-collision providers and valid age states. This review does not establish ray/outline equivalence or DDA traversal parity.

## Reviewed inputs

- Finding: `../dda-age-crop-family-59-104-105-141-142/finding.md`
- Finding SHA-256: `B07A0BED4D65DE14C25D1202378B5F81E5A6DAE6550C053FA9C2EC870AC97C0B`
- Source manifest: `../dda-age-crop-family-59-104-105-141-142/sources.sha256`
- Source manifest SHA-256: `54A1D815BE7AEDF2F44B59D8D5C15E27F4ED2979526C11854001354B5F432829`

All 14 declared source files were rehashed against the manifest; their bytes and canonical source-manifest entries match.

## Findings

The historical registrations and state/provider chain are supported by the listed 1.8.9 files. `Block.java` registers IDs 59, 104, 105, 141, and 142 as wheat, pumpkin stem, melon stem, carrots, and potatoes. `WheatBlock` defines AGE 0–7; carrot and potato inherit that age property and metadata decoder. `StemBlock` defines AGE 0–7 and horizontal FACING values, with the default state and virtual fruit-neighbor direction resolution described by the finding. `PlantBlock.getCollisionShape` returns null unconditionally, and `Block.addCollisions` only appends non-null boxes. The conclusion that these five legacy block classes contribute no player movement collision box for valid ages is therefore source-supported.

The 26.2 key aliases are explicit in `references/BlockItemIds.java:739,741–742,748–749`: the five block keys are `wheat`, `carrots`, `potatoes`, `pumpkin_stem`, and `melon_stem`. `Blocks.java:1302–1310,2393–2428,2756–2775` binds those keys to the listed crop/stem classes and supplies `.noCollision()` for each registration. `BlockBehaviour.Properties.noCollision()` clears `hasCollision`, and the base `getCollisionShape` returns `Shapes.empty()` when that flag is false (`state/BlockBehaviour.java:327–328,1094–1097`). Thus each current alias also contributes no movement collision shape, independently of AGE and stem FACING.

The finding correctly separates the historical mutable bounds and current outline shapes from the movement-collision provider. Its limits are appropriate: the provider comparison does not establish which provider a ray operation selects, visited-block order, early-hit behavior, or any other DDA equivalence. No unsupported DDA or outline claim is accepted here.

## Conclusion

Accept this finding as source closure for the five explicitly listed aliases, their valid legacy AGE range, and their individual movement-collision provider results. Do not widen the conclusion to neighboring plant families, ray/outline shapes, malformed metadata, or DDA visitation and hit behavior.
