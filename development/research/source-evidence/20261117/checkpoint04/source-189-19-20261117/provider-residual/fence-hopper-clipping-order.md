# Fence and Hopper collision clipping-order dependency

Pair: Minecraft 1.8.9 → 1.9, exact Ornithe Feather sources. This report closes only the bounded multipart-box decomposition/order dependency for the local-player collision resolver. It does not establish full provider equivalence or freeze the pair.

## Source identities and hashes

- 1.8.9 source manifest SHA-256: `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; provenance SHA-256: `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.
- 1.9 mapping-local `.decompile-provenance.json` SHA-256: `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.

| Source | 1.8.9 SHA-256 | 1.9 SHA-256 |
| --- | --- | --- |
| `block/FenceBlock.java` | `8d6a803988d77fc51f364520f2e0cd5567b1d5c29a4d28d01cdcf93e6d5890d9` | `e1874cad83f73cbe7749fc08439df6b90c67bac95bdfe27b48e62b2a9bcb4761` |
| `block/HopperBlock.java` | `a7dd1ef92b4729a98877cf800e6d728bfab0ef32e58b1ef575a84809e465be1f` | `ffdc308f5eeb514b53fdd66b0742c0643aad5dc893eae952621c8176f6d08901` |
| `block/Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` | `91c053e11d6bedaac292b6be2f5670e5a5c4522f175c1e227163eeb5499780b2` |
| `world/World.java` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` | `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` |
| `entity/Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| `util/math/Box.java` | `8fc7bb57b1132afc7ae9539c30ff9c2585f0e36c2b0e59c60946cb1e3cee9eea` | `cb0f30c72b7925fa0c0b133dffd051cf8260f4321f7c4a093c64ef5a5f500e62` |

The listed class hashes were recomputed from the read-only source trees and match the 1.8.9 `.sources.sha256` rows and the 1.9 provenance `sources` entries.

## Collision collection and movement caller

The default collision collector intersects each provider box against the movement query before appending it: 1.8.9 `Block.addCollisions`/`getCollisionShape` (lines 339–350), and 1.9 `Block.addCollisions`/`addCollision` (lines 313–328). The old collector materializes the block's mutable bounds in world coordinates; the new collector translates local boxes by `BlockPos`. For equal coordinates, both use strict `Box.intersects` overlap before the box enters the list.

The local player reaches this list through `LivingEntity` travel → `Entity.move`. Both versions clip requested movement against the returned collision list in Y, then X, then Z, moving the player box after each axis: 1.8.9 `Entity.move` (lines 371–459), 1.9 (438–528). Step-up alternatives likewise iterate each axis in Y/X/Z order (1.8.9 lines 460–511; 1.9 lines 529–587). The paired `Box.intersectX/Y/Z` methods use the same inequalities and update the positive limit only to a smaller gap, or the negative limit only to a larger gap (1.8.9 lines 93–151; 1.9 lines 137–195). Thus, for a fixed moving box and axis, permuting an equal set of collision boxes cannot change the resulting clipped limit.

## FenceBlock

1.8.9 `FenceBlock.addCollisions` (lines 36–81) resolves north/south/west/east connection booleans from neighboring blocks. It emits at most two 1.5-high boxes: a north/south strip when either north or south connects, and an east/west strip when either east or west connects or when there is no north/south connection. Each strip includes the center post. In 1.9 the collision constants (lines 44–48) represent a 1.5-high center post and four separate 1.5-high directional arms; `addCollisions` appends the post followed by connected north, east, south and west arms (lines 57–75).

For any identical north/east/south/west bitmask, the union is the same. The old north/south strip fills the center post and whichever north/south arms connect; the old east/west strip fills the center post and whichever east/west arms connect. The new center plus arms partition those same rectangles along their shared faces. This remains true for no connections (the old fallback strip is only the center post), one connection, opposite connections, and mixed axes. With the same movement query and block cell included, strict intersection admits the same occupied region; the axis clipper's monotone min/max update makes the different subdivision and list order immaterial. This closes only the per-cell decomposition/order effect for matching connection bits.

**Connection-state dependency remains open.** The old `shouldConnectTo` (lines 128–137) tests `block.isCube()` in its solid-material branch, while 1.9's method (119–129) tests `blockState.isCube()`. The per-cell proof above assumes both providers resolve the same connection bitmask; it does not prove equal results for every neighboring block state or arbitrary state/world input.

## HopperBlock

1.8.9 `HopperBlock.addCollisions` (lines 49–62) emits the 5/8-high bottom, then the west, north, east and south 1/8-thick full-height walls. 1.9 constants and `addCollisions` (constants lines 38–42; method lines 55–61) emit the same five coordinate boxes in the order bottom, west, east, north, south. The state properties `FACING` and `ENABLED` do not select these boxes in either method.

The coordinate set is identical; only the east/north pair's list order swaps. `Block.addCollisions` applies the same intersection inclusion rule, and the paired `Box.intersectX/Y/Z` methods reduce each axis limit monotonically as described above. Therefore, if both collision queries include this hopper cell and use the same player box/requested movement, the changed list order cannot change per-axis clipping or the resulting box. No Hopper-specific decomposition/order delta was found.

## Scope and remaining gates

This is a bounded source observation, not a claim of whole-query equivalence. `World.getCollisions` scans different X/Z/Y envelopes and has border/corner gates (1.8.9 lines 891–945; 1.9 lines 893–955). Those differences can change which cells and other colliders enter the list; this report assumes the particular Fence/Hopper cell is included on both sides. Fence connection resolution can also differ if the `isCube` answer differs. The report does not close either dependency, all other provider/state inputs, or full-pair coverage. No gameplay, trajectory or runtime result is claimed. Frozen pane and piston-head geometry was not inspected.
