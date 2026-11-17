# Frozen source census: 1.16.4 → 1.16.5 solid-provider slice

**Review scope:** exact Mojmap decompiled Java sources for 1.16.4 and 1.16.5 only. This report freezes the assigned solid-world census; it is not a full movement-pair freeze or a runtime-parity verdict.

## Source identity and integrity

- 1.16.4 source root: `decompiled_minecraft/1.16.4/mojmap/`. Its hidden `.decompile-provenance.json` embeds 3,523 relative source paths and SHA-256 values. All 3,523 listed source files were rehashed and matched. SHA-256 of the provenance file: `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`.
- 1.16.4 mapping input recorded in that provenance: Mojang `client.txt`, SHA-256 `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`; version metadata input identifies 1.16.4. This tree uses Mojmap names.
- 1.16.5 source root: `decompiled_minecraft/1.16.5/mojmap/`. Provenance is asymmetric: there is no hidden `.decompile-provenance.json` in this tree. The sibling `mojmap.ready.json` identifies version `1.16.5`, mapping `mojmap`, and 3,523 source files; it pins the sibling `mojmap.sources.sha256` manifest to SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`. The observed manifest digest matches the marker, and all 3,523 listed files were rehashed and matched. The sibling artifact manifest records `1.16.5/client_mappings.txt` SHA-256 `7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c`.
- Both inventories contain 3,523 Java source files. Comparing the two verified source-hash inventories yielded 0 added paths, 0 removed paths, and 4 changed Java files. Identical source hashes are paired evidence for all other source files.

## Frozen eligible roster

| Census slice | Changed / added / removed eligible entries | Paired coverage |
| --- | ---: | --- |
| Collision-shape providers | 0 | Complete within the source inventory |
| Block-support-shape providers | 0 | Complete within the source inventory |
| Ordinary player state and movement consumers of solid shapes | 0 | Complete within the source inventory |
| Directional, query, or step consumers affecting solid movement | 0 | Complete within the source inventory |
| Affected registered solid-provider defaults / state legality candidates | 0 | None introduced by this pair |

The following representative provider, dispatch, world-query, player-state, and shape files were also directly hash-compared and are identical: `world/level/block/state/BlockBehaviour.java`, `BlockState.java`, `world/level/block/Block.java`, `Blocks.java`, `world/level/CollisionGetter.java`, `BlockGetter.java`, `world/entity/Entity.java`, `LivingEntity.java`, `world/entity/player/Player.java`, `client/player/LocalPlayer.java`, `world/phys/shapes/VoxelShape.java`, and `world/phys/AABB.java`. Because no solid-provider shape/state delta exists, there is no changed solid state whose property legality needs follow-up.

## All four source differences and disposition

1. `net/minecraft/DetectedVersion.java`: the 1.16.4 and 1.16.5 constants differ in version name, world version (`2584` → `2586`), and release target. Version metadata only; excluded.
2. `net/minecraft/core/Direction.java`: 1.16.5 adds `Direction.Plane.getRandomAxis(Random)` (line 428), returning `Util.getRandom(this.axis, random)`. A whole-tree call search finds one caller: `BaseFireBlock.isPortal`; no solid movement query or step consumer uses the added method. Excluded with the portal-placement change below.
3. `net/minecraft/world/entity/ai/village/poi/PoiSection.java`: duplicate POI registration now throws only when `SharedConstants.IS_RUNNING_IN_IDE`; otherwise it logs the mismatch and proceeds to put the record. This is POI bookkeeping, not player movement or block collision; excluded.
4. `net/minecraft/world/level/block/BaseFireBlock.java`: `getShape` is unchanged in both versions (line 45 returns `DOWN_AABB`). The changed `isPortal` method replaces the 1.16.4 direct counter-clockwise axis call (line 185) with horizontal-axis selection for horizontal input or a random horizontal axis for vertical input (1.16.5 lines 189–192). This changes portal-shape eligibility during fire placement, not collision/support behavior. The registered `FIRE` block uses `.noCollission()` in `Blocks.java` (around line 456). Excluded.

No changed file adds, removes, or changes an eligible solid collision/support provider, ordinary player movement consumer, or such provider's registered state defaults.

## Source and archive limits

This is a decompiled-source-only census. The source roots provide Java classes, not the original client JAR contents or Minecraft data/resource assets; in particular, this report does not independently compare resource-pack JSON, tags, or other non-Java defaults. The artifact/provenance records identify and hash source/mapping inputs but were not used to inspect archive internals. No product files, wiki inventories, backlog data, Git history, builds, tests, decompilation, or runtime behavior were consulted in producing this census.
