# 1.12 brewing-stand AutoJump alias corroboration

## Verdict

**No difference found across exact 1.12, 1.12.1, and 1.12.2 Ornithe Feather source for the bounded old-side witness.** The requested stand box order, box-intersection append rule, World collision/entity collector context, bottom-slab geometry, and AutoJump first-hit and timer logic are source-identical in all three versions. The accepted loaded, dry bottom-slab + brewing-stand + air fixture therefore has the same old-side source result in each alias: at probe Y=1.01 from feet Y=0.5, the stand stem is the first qualifying box (appended before its plate), its maxY gives rise 1.375, and `u > 1.2` returns without arming the timer.

This is alias corroboration for the old side of the author witness; it does not independently re-review the newer-side union decomposition or establish complete-pair coverage.

## Exact evidence identity and rehash

All sides use the same mapping family, **Ornithe Feather gen2 build.2**. Exact client, metadata, and mapping input hashes were read from the retained hidden provenance for the first two versions and the ready/provenance records for 1.12.2.

- **1.12:** hidden `.ornithe-feather.previous-799a08bb-c1f0-44b2-8d8f-6b2334a44a8c/.decompile-provenance.json` SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`. Its pinned inputs identify Mojang 1.12 metadata (`.../1.12.json`, SHA-256 `33cc40a05466b4e34e04e65def732094388e9149eb6bd37e5daa227c06cbde4b`), client (`909823f9.../client.jar`, `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`), Feather metadata (`.../feather/1.12`, `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4`), and `feather-gen2-1.12+build.2-mergedv2.jar` (`73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`). The three canonical root source files below rehash to their retained provenance entries.
- **1.12.1:** hidden `.ornithe-feather.previous-d0fbab78-2176-466b-af5b-fe37679e260f/.decompile-provenance.json` SHA-256 `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`. Its pinned inputs identify Mojang 1.12.1 metadata (`.../1.12.1.json`, SHA-256 `a83e8ab7e3e78fd8dfa8fdbd0216b090ef7298fb44637b79093fe663bf2d6814`), client (`83385d.../client.jar`, `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`), Feather metadata (`.../feather/1.12.1`, `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b`), and `feather-gen2-1.12.1+build.2-mergedv2.jar` (`f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`). The three canonical root source files below rehash to their retained provenance entries.
- **1.12.2:** `ornithe-feather.ready.json` identifies exact version 1.12.2 and mapping `ornithe-feather`; client SHA-256 `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`, source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`, and artifact-manifest SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`. The source manifest and artifact manifest were rehashed and match those recorded values. `ornithe-feather.provenance.json` lists `feather-gen2-1.12.2+build.2-mergedv2.jar` and `.tiny`.

The canonical source-file SHA-256 values were checked against the relevant retained hidden-provenance source entries for 1.12/1.12.1 and `ornithe-feather.sources.sha256` for 1.12.2:

| Source | 1.12 | 1.12.1 | 1.12.2 |
|---|---|---|---|
| `block/BrewingStandBlock.java` | `e875022b9a47ca6336878bcec205459462687bb35d567ee183f7ec2f1924a4fd` | same | same |
| `world/World.java` | `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8` | `d182122a9b052f57803672c21b0941cb7e7d41ed756cd0a4ea8c0858684c8259` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| `client/entity/living/player/LocalClientPlayerEntity.java` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` | same | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |

The whole-file World and LocalClientPlayerEntity hashes differ across patch releases, so this verdict is limited to the source-extracted paired methods/scopes below, which were independently rehashed and are byte-for-byte equal across all three versions. The 1.12 and 1.12.1 canonical roots have no adjacent ready/source-manifest marker; their scoped canonical files match the retained hidden provenance entries, but this does not authenticate every file in those full roots.

## Paired source scopes

- `net/minecraft/block/BrewingStandBlock.java:36-37,70-76`: `SHAPE` is `(0,0,0)..(1,0.125,1)` and `COLLISION_SHAPE` is `(0.4375,0,0.4375)..(0.5625,0.875,0.5625)`. `addCollisions` calls `addCollision` with `COLLISION_SHAPE` before `SHAPE`; its raw method-slice SHA-256 is `95996fd6196056bbd20c34169e13003257a4f984a3717c2af5804d09d3937058` in each version. `getShape` returns the plate `SHAPE`.
- `net/minecraft/block/Block.java:376-381`: `addCollision` skips only `EMPTY_BLOCK_SHAPE`, translates the box by block position, and appends it iff the query `shape.intersects(box)`. Raw method-slice SHA-256 in all three: `8e5d60b9d5d167279368a6a0ac6e4db37fc9ba4a06aaa9dc09ab1d579d60606c`.
- `net/minecraft/block/SlabBlock.java:20,39-43`: bottom slab shape has Y range `0..0.5`; the bottom state selects `BOTTOM_SHAPE`. The `getShape` method-slice SHA-256 in all three: `fda985cbc8901ca0061477583e392c6b320f4f519bcec1b0ec16244cb8b07c24`.
- `net/minecraft/world/World.java:964-1015,1017-1037`: block collision collection loops X, then Z, then Y and calls each state’s `addCollisions`, appending in visitation order. `getCollisions(entity, shape)` first fills that block list, then appends intersecting entity collision shapes in `getEntities` result order. With the witness's loaded area and no other entities, only block boxes matter. The entity query loops chunk X then chunk Z and delegates into each chunk's entity collector. The raw block collector, player wrapper, and `getEntities(..., filter)` method-slice hashes are identical across the three versions: respectively `dfdec2ce08f74e710a1004a9edd4690965c092cc46a400655244417a4230917e`, `227b1be34ca8602bdbf15283fdd5b212d004409e41524d5ae2c0a838d0b3b9a5`, and `9b71f1027e7f17b3fbc197212ab5940bc3af48e3683dea6b0d6e159ba7ded423`.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:852-952` (1.12/.1), `:857-957` (1.12.2): the raw `autoJump` method slice is byte-identical in all three, SHA-256 `5b124378adc6f3e0116153987d752d813a48d5c4916d07731993d10e537b4aa3`. It retains enabled, timer `<=0`, on-ground, not-sneaking/not-riding, nonzero movement, direction-dot `>= -0.15`, and two empty-head-cell gates. It constructs the swept box and two lateral rays raised by `0.51F`, iterates collision boxes in list order, takes the first box intersecting either ray, scans clearance, then arms `ticksToNextAutojump = 1` only when rise `>0.5F` and `<=1.2F`.

The exact-source match confirms the witness's old-side first candidate for the slab-floor geometry: the stand stem is appended before its plate and both intersect the Y=1.01 ray; the slab's top at Y=0.5 is below that probe. The first qualifying `s` is therefore the stem's world maxY `1.875`, yielding `u=1.375`, which fails the same `u > o` (`o=1.2`) check in 1.12, 1.12.1, and 1.12.2. No alias gap was found in the bounded behavior. Provenance/readiness gap is limited to the full-root metadata for 1.12 and 1.12.1, as stated above.

## Witness binding

The author witness was read only after this independent source comparison. Its SHA-256 matches the assigned identity: `502a93ed7fcdd065d20900d8d70103c37d87f245bea7ec463018e123d672fd53` (`early-autojump-brewing-stand-witness/witness.md`).

