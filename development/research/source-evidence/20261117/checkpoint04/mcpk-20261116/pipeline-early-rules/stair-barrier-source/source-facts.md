# Hash-bound source facts: wall/pane stair faces and pane/barrier connection

## Assignment boundary

Source-only inspection of exact decompiled Minecraft 1.11.2, 1.12, 1.12.1, and 1.12.2, Ornithe Feather namespace. This packet addresses only: (1) wall/pane collision connection to directional stair faces; (2) pane connection to adjacent barrier blocks. It does not reassess fence-to-stair or perpendicular fence-gate/wall-to-gate claims, nor establish independent acceptance, pair coverage, or implementation.

## Immutable source identities

All relevant `.java` entries below were SHA-256 checked against the source manifest/provenance for that release. The 1.11.2 and 1.12.2 ready markers each report `status=ready`, matching `versionId`, `mapping=ornithe-feather`; their declared `sourceManifestSha256` matched the manifest bytes on disk.

| Release | In-tree source identity | Mapping publication identity | Identity hashes |
|---|---|---|---|
| 1.11.2 | `decompiled_minecraft/1.11.2/ornithe-feather` | `feather-gen2-1.11.2+build.2-mergedv2.jar`, `feather-gen2-1.11.2+build.2.tiny` (ready/provenance) | ready JSON `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`; provenance JSON `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6`; source manifest `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` |
| 1.12 | `decompiled_minecraft/1.12/ornithe-feather` | Feather Gen2 1.12 build.2 mergedv2 JAR; input metadata `https://meta.ornithemc.net/v3/versions/feather/1.12` | mapping JAR input SHA-256 `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`; metadata SHA-256 `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4`; local provenance `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` |
| 1.12.1 | `decompiled_minecraft/1.12.1/ornithe-feather` | Feather Gen2 1.12.1 build.2 mergedv2 JAR; input metadata `https://meta.ornithemc.net/v3/versions/feather/1.12.1` | mapping JAR input SHA-256 `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`; metadata SHA-256 `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b`; local provenance `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8` |
| 1.12.2 | `decompiled_minecraft/1.12.2/ornithe-feather` | `feather-gen2-1.12.2+build.2-mergedv2.jar`, `feather-gen2-1.12.2+build.2.tiny` (ready/provenance) | ready JSON `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a`; provenance JSON `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`; source manifest `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |

The 1.12 and 1.12.1 `WallBlock.java`, `PaneBlock.java`, `StairsBlock.java`, `BarrierBlock.java`, and `Block.java` source hashes are identical; 1.12.2 has the same hashes for those five files. The exact hashes per release are recorded below. Each 1.12/1.12.1 file hash matched the mapping-local provenance `sources` entry; each 1.11.2/1.12.2 hash matched its SHA-256 source manifest entry.

| File, relative to mapping root | 1.11.2 | 1.12 / 1.12.1 / 1.12.2 |
|---|---|---|
| `net/minecraft/block/WallBlock.java` | `17da6d881365e4dce5134f88cb9a9e5273d1fa1734812d13410dc6924a3acbfd` | `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50` |
| `net/minecraft/block/PaneBlock.java` | `1e8968e7d642358cce90f0d3156f07f19c5bca28d2008d5d2d4bf0eccc7266a9` | `83862f6633b8241c3788e9c9a7e4fd0275c010ec790019b8003908996f4814b3` |
| `net/minecraft/block/StairsBlock.java` | `5df1782da6eb7acdb9935b3910a0484d6417661bfd6230ecf81df0de6d782627` | `c52a011a759b6f3ef6d45162d3b61f464ceceee5d2d8735de6f6e25a56e55611` |
| `net/minecraft/block/BarrierBlock.java` | `57b4fb0ef1f0feda7dec76d62136e8cf302548cb5c9cd47ebd55d66db114b94b` | `57b4fb0ef1f0feda7dec76d62136e8cf302548cb5c9cd47ebd55d66db114b94b` |
| `net/minecraft/block/Block.java` | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| `net/minecraft/world/World.java` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | 1.12 `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8`; 1.12.1 `d182122a9b052f57803672c21b0941cb7e7d41ed756cd0a4ea8c0858684c8259`; 1.12.2 `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |

## Source facts

### Walls and panes adjacent to back-facing stairs

- **1.11.2:** `StairsBlock.isCube` returns false (`StairsBlock.java:145-147`). `WallBlock.shouldConnectTo(WorldView, BlockPos)` accepts its own block, a fence gate, or otherwise requires solid-blocking material and `blockState.isCube()` (`WallBlock.java:148-157`). `PaneBlock.shouldConnectTo(Block)` requires `block.defaultState().isCube()` or one of its other explicit block cases (`PaneBlock.java:136-143`). Thus neither a wall nor pane acquires an arm toward a stair under these old predicates.
- **1.12, 1.12.1, 1.12.2:** `WallBlock.resolveVirtualProperties` sets `NORTH/EAST/SOUTH/WEST` from four neighbor queries with the face direction toward the wall (`WallBlock.java:190-196`). `WallBlock.shouldConnectTo` tests that neighbor state's directional `FaceShape`; an unexcluded `SOLID` face connects, as do `MIDDLE_POLE_THICK` faces (and `MIDDLE_POLE` for a fence gate) (`WallBlock.java:146-158`). The shared attachment-exception list names shulker boxes, leaves, trapdoors, beacon/glass/glowstone/ice/sea-lantern/stained-glass, and piston variants, but not stairs (`Block.java:219-234`), so a stair face is not filtered by that branch. Stairs implement `getFaceShape`; for `SHAPE=STRAIGHT`, a horizontal queried face is `SOLID` exactly when `FACING == face`, otherwise `UNDEFINED`. Inner shapes have their own directional solid faces; outer shapes return `UNDEFINED` (`StairsBlock.java:140-161`). Therefore an adjacent straight stair with `FACING` toward the wall supplies a wall connection; this is the precise selector condition meant here by a back-facing stair.
- The pane follows the same directional face test. Its `resolveVirtualProperties` computes each directional boolean from the adjacent state's face shape (`PaneBlock.java:109-113`); `shouldConnectTo` accepts an unexcluded `SOLID` face or `MIDDLE_POLE_THIN` (`PaneBlock.java:136-160`). Accordingly, an adjacent straight stair with `FACING` toward the pane supplies a pane connection from 1.12 onward. Wall and pane `addCollisions` consume the resolved booleans: wall uses the connection combination to select a 1.5-high collision box (`WallBlock.java:90-102`), while pane adds its center plus each enabled directional arm (`PaneBlock.java:54-76`). These are actual collision boxes, not only outline/render geometry.

### Pane adjacent to barriers

- **1.11.2:** `PaneBlock.resolveVirtualProperties` feeds each adjacent block into `shouldConnectTo` (`PaneBlock.java:109-114`). The predicate accepts `block.defaultState().isCube()` (`PaneBlock.java:136-143`). `BarrierBlock` does not override `isCube`, so the inherited `Block.isCube` value is true (`BarrierBlock.java:9-31`; `Block.java:234-236`). A barrier neighbor therefore sets the pane's corresponding directional property true, and that property adds an ordinary collision arm (`PaneBlock.java:54-76`).
- **1.12, 1.12.1, 1.12.2:** `PaneBlock.shouldConnectTo(Block)` adds `Blocks.BARRIER` to its exception list (`PaneBlock.java:142-160`). A barrier's face shape is inherited `Block.getFaceShape`, which returns `FaceShape.SOLID` (`BarrierBlock.java:9-31`; `Block.java:362-364`). The pane predicate's solid-face branch is guarded by `!shouldConnectTo(block)`, while its separate `MIDDLE_POLE_THIN` branch does not apply to the barrier (`PaneBlock.java:136-160`). Thus an adjacent barrier no longer sets the corresponding connection property and no pane arm collision box is emitted in that direction.

## Player collision reachability

`Entity.move` obtains collision lists through `world.getCollisions(this, ...)` (for example `Entity.java:567-613`); this is inherited by player entities. `World.getCollisions` visits block states intersecting the queried box and invokes `blockState3.addCollisions(..., false)` (`World.java:964-1019`; call at line 1001). The false force-shape argument allows wall/pane `addCollisions` to resolve their virtual directional properties from neighboring block states before producing collision boxes. This closes source reachability to the ordinary player movement collision path for the wall/pane arm behavior described above.

## Bounded conclusion and limits

Among the four exact stable releases checked, the directional wall/pane stair-face behavior and the pane barrier exclusion first appear in **1.12**; it persists unchanged in the examined 1.12.1 and 1.12.2 block source. This identifies the first checked release with these source changes, not a claim about unexamined development snapshots or other game versions. No trajectory magnitude or runtime parity is asserted. The packet makes no claim about rendering, non-player physics, or the already accepted fence/gate variants.

