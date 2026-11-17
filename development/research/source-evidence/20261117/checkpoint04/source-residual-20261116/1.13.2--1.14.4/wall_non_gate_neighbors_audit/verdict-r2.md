# Independent audit r2: wall non-gate neighbor predicate

## Bound inputs and artifact verification

Audited `build/source-residual-20261116/1.13.2--1.14.4/wall_non_gate_neighbors/report.md`, SHA-256 `8ce87936eb92040f4926d7b3ca6169d3c5e4f805098c50b1a38d839abcce442a`, matching the assigned corrected revision.

The 1.13.2 and 1.14.4 Feather source-manifest files hash to `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`; the exact ready markers identify the corresponding releases/mapping and repeat these manifest hashes. Direct SHA-256 checks matched every source file cited by the report and each corresponding source-manifest row: 1.13.2 `WallBlock.java` and `Block.java`; 1.14.4 `WallBlock.java`, `Block.java`, `state/BlockState.java`, `TntBlock.java`, and `Blocks.java`.

The corrected raw artifact identity is verified directly. `.minecraft-decompile-cache/1.14.4/client.jar` hashes to `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`; this equals the SHA-256 in `.minecraft-decompile-cache/1.14.4/client.jar.pin.json`, the 1.14.4 Feather ready marker, and `development/research/source-evidence/20261103/checkpoint03/1.14.4-resource-manifest.json` (that manifest file hashes to `24c10aac4d25dbb468d6005e7aa277ec101f673ba102518505a6b126746eaaf5`). This confirms the raw vanilla client pin, not the separately mapped Feather client artifact.

The extraction record at `build/source-residual-20261116/1.13.2--1.14.4/wall_resource_membership/extraction-record.md` hashes to the assigned SHA-256 `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`. I independently read both exact ZIP entries in-memory from the pinned raw client and compared them byte-for-byte with the saved payloads:

- `data/minecraft/tags/blocks/leaves.json`: archive and payload SHA-256 `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991`; six values; `minecraft:tnt` absent.
- `data/minecraft/tags/blocks/walls.json`: archive and payload SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; fourteen values; `minecraft:tnt` absent.

Both payload hashes also match the pinned resource manifest. The target resource gate is therefore closed for these vanilla client tag contents.

## Predicate and call-context verdict

**Accept the bounded claim:** for vanilla TNT as a horizontal neighbor, the old wall arm predicate is false and the 1.14.4 predicate is true.

In 1.13.2, `WallBlock.shouldConnectTo` accepts a non-exception `FaceShape.SOLID` or its middle-pole case (`WallBlock.java:51-54`). `Block.getFaceShape` defaults to `SOLID` (`Block.java:383-384`), and TNT is explicitly in `WallBlock.isExceptionForConnection` (`WallBlock.java:57-65`), so the solid-face arm rejects TNT; TNT is not a fence gate/middle-pole case. Old result: false.

In 1.14.4, `WallBlock.m_71853799` returns `!isExceptionForAttachment(block) && bl || bl2` (`WallBlock.java:48-52`), where `bl` is the state’s face-solid predicate and `bl2` is WALLS membership or directionally eligible fence gate. `Block.isExceptionForAttachment` does not include TNT (`Block.java:260-267`); the packaged tag bytes show TNT is absent from WALLS and LEAVES. `BlockState.m_87223014` reaches `Block.isFaceSolid` (`state/BlockState.java:326-327`); that predicate excludes LEAVES states and otherwise requires a full collision face (`Block.java:402-410`). TNT is registered as `TntBlock` without `.noCollision()` (`Blocks.java:390`), extends `Block` (`TntBlock.java:23`), and inherits the full block shape and collision-enabled default (`Block.java:371-377, 811`; registration does not call the `noCollision` mutator). Thus `bl` is true, `bl2` is false, and target result is true.

The placement path reads the same four horizontal neighbors and computes the four arm bits in both releases (1.13.2 `WallBlock.java:69-91`; 1.14.4 `:55-80`). On horizontal neighbor updates, each recomputes the corresponding bit while preserving other arms; DOWN delegates to the superclass (1.13.2 `:96-116`; 1.14.4 `:82-104`). The changed stored bit selects the wall collision shape in both versions (`WallBlock.java:25-40`). This is a concrete source-grounded wall-state/collision-geometry difference reachable by wall placement or horizontal TNT-neighbor update, not a measured player trajectory.

## Limits

Acceptance is limited to the exact 1.13.2 → 1.14.4 Feather source comparison and the vanilla 1.14.4 raw-client tag payloads for this TNT witness. It does not establish the full non-gate neighbor inventory, custom datapack tag behavior, pair freeze, an exact intermediate patch boundary, or runtime movement parity.
