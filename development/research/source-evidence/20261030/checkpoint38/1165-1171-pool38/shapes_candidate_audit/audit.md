# Independent audit: 1.16.5 → 1.17.1 Shapes conversion candidate

## Verdict

**Retain as a conditional in-scope collision-query geometry difference; do not claim a proven collision response or trajectory difference.** The 1.17.1 `Shapes.create` conversion can replace an exact-coordinate array shape with a grid-rounded cube shape for a reachable LocalPlayer swept collision box. A conditional numeric example below changes the query bounds by `5e-8` per upper face. Whether a particular registered block shape turns that sliver into a different intersection result, and whether that changes displacement, remains unexamined.

There is no `create` varargs overload in either `Shapes.java`. 1.17.1 adds a fixed-arity six-`double` overload; `create(AABB)` delegates to it. The AABB overload and `findBits` are the compared operation.

## Exact evidence identity and hash verification

Canonical read-only roots:

- Older: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap` (release `1.16.5`, Mojmap).
- Newer: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.17.1\mojmap` (release `1.17.1`, Mojmap).

Source-manifest SHA-256: older `mojmap.sources.sha256` = `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`; newer = `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`. Ready-marker SHA-256: older `mojmap.ready.json` = `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee`; newer = `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b`. The ready markers identify the named release and `mapping=mojmap` (older source count 3,523; newer 4,142).

I rehashed every Java file cited below; each full SHA-256 matched its exact release's `mojmap.sources.sha256` entry:

| Relative Java path | 1.16.5 SHA-256 | 1.17.1 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/phys/shapes/Shapes.java` | `ec218dca68cad3525364967aadfbb8c0bead0cc44aa3ee2beba31e00e55ae2d4` | `8249d3e406ba400bd81258aa19be1fad4f78b451b2add85eea4f4b2209649a9e` |
| `net/minecraft/world/level/CollisionSpliterator.java` | `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297` | `c4f8158bd6778159946ebf16c22a6c12f5a4bccb40e2f1215f4fc930bb218bd0` |
| `net/minecraft/world/level/CollisionGetter.java` | `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac` | `eb707479e75cf200731df4546a546fb984be33cf3e4a8e17d3065a916497f747` |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` |

## Paired source facts

- **1.16.5 `Shapes.java:51–105`:** `box` constructs an `AABB` and calls `create(AABB)`. That overload calls `findBits` per axis. `findBits` gates bounds to approximately `[-1e-7, 1.0000001]`, tries resolutions `i=0..3`, and accepts each scaled coordinate when `abs(value - floor(value)) < 1e-7`. If any axis fails, `create(AABB)` returns `ArrayVoxelShape` with the exact min/max doubles. If all indices are zero, it returns `block()` only when the AABB contains `(0.5,0.5,0.5)`, else `empty()`; otherwise it rounds scaled endpoints and builds a cube shape.
- **1.17.1 `Shapes.java:53–118`:** `box` validates ordering and calls the new fixed-arity `create(double d, double e, double f, double g, double h, double i)`. That overload returns `empty()` when any extent is `< 1e-7`; otherwise it calls `findBits`. A failed axis returns `ArrayVoxelShape` using the supplied bounds. Zero indices return `block()` directly. Nonzero indices pass `Math.round` endpoints into `BitSetDiscreteVoxelShape.withFilledBounds` and return `CubeVoxelShape`. The AABB overload at lines 96–98 forwards its min/max values to this overload. `findBits` keeps the same outer coordinate gate and tries `i=0..3`, but compares to `Math.round` with tolerance `1e-7 * (1 << i)` instead of comparing to `floor` with fixed `1e-7`.
- This is a conversion/tolerance change, not a change to `AABB` bounds. `create(AABB)` no longer has its own zero-resolution center containment test; the new six-coordinate overload has an extent check and a direct `block()` result for three zero indices. I do not infer consequences for malformed/non-player AABBs from this.

## LocalPlayer collision-query trace

The source call chain is present at both endpoints: `LocalPlayer.aiStep` calls `super.aiStep` (1.16.5 `LocalPlayer.java:627,788`; 1.17.1 `:649,810`); `LivingEntity.aiStep` calls `travel` (`LivingEntity.java:2368,2459`; newer `:2455,2546`); travel uses `Entity.move` (`LivingEntity.java:1947` in the older movement branch and `:2036` in the newer corresponding branch). LocalPlayer's `move` override delegates to `super.move` (`LocalPlayer.java:863,866`; newer `:893,896`). `Entity.move` reaches its collision routine (`Entity.java:485,668`; newer `:534,750`). When the heuristic needs the multi-axis block-collision path, it calls `level.getBlockCollisions(entity, aABB.expandTowards(vec3))` (`Entity.java:721`; newer `:799`). `CollisionGetter.getBlockCollisions` constructs `CollisionSpliterator` (older `CollisionGetter.java:58–59`; newer `:61–63`). Its constructor sets `entityShape = Shapes.create(aABB)` (older `CollisionSpliterator.java:41`; newer `:42`), then the iterator tests `joinIsNotEmpty(movedBlockShape, entityShape, AND)` (older `:82`; newer `:83`). Thus the changed result is consumed as a block-collision query shape on a reachable LocalPlayer movement path.

`Player.java` gives `Pose.SWIMMING` and `Pose.FALL_FLYING` dimensions `0.6F × 0.6F` at both endpoints (older lines 116–117; newer 128–129). For either pose, a player bounding box can have all three dimensions `0.6F`; a positive three-axis movement makes the swept AABB the input to the changed conversion. The exact pose/environment and collision consequence below are conditional, not claimed as observed runtime state.

## Conditional numeric witness

Condition the collision query on a swimming or fall-flying player whose current box has min `(0.25, 0.25, 0.25)` and dimensions `0.6F` on all axes (`0.6000000238418579` as the float widened to double), and a positive movement vector of about `(0.02500002616, 0.02500002616, 0.02500002616)`. `expandTowards` then supplies a collision query AABB approximately `[0.25, 0.87500005]` on each axis; all extents exceed `1e-7`, and all bounds pass the outer `findBits` coordinate gate.

At resolution `i=3`, each upper endpoint scales to `0.87500005 × 8 = 7.0000004`. In 1.16.5, its distance from `floor` is `4e-7`, greater than the fixed `1e-7`, so no resolution accepts both endpoints; the result is `ArrayVoxelShape` with exact upper bound `0.87500005`. In 1.17.1, the distance from `round` is `4e-7`, less than the scaled tolerance `8e-7`, so all axes resolve to 3; rounded bounds become 2 through 7, yielding a `CubeVoxelShape` with upper bound `7/8 = 0.875`. That is a `5e-8` difference on the three upper faces of the shape passed to the block-shape `AND` query.

This establishes a conditional change to collision-query geometry. It does **not** establish that a real block state's collision shape intersects the sliver, that `joinIsNotEmpty` returns differently for any particular block, or that `Entity.move` changes its accepted displacement. Those require checking concrete block shape providers and the downstream shape-engine operations, which are outside this bounded audit.

## Dependencies and next action

- **Source facts established:** exact endpoint implementation and hashes; changed approximation tolerance and representation; reachable LocalPlayer swept-AABB collision-query consumer; conditional query-geometry witness.
- **Conditional state:** swimming/fall-flying pose, chosen position, and chosen 3-axis movement vector. The source allows the pose dimensions and movement chain; no runtime trajectory was tested.
- **Unexamined shape-engine/runtime semantics:** concrete registered block shape overlap with the `5e-8` sliver; `joinIsNotEmpty` comparison details for these mixed representations; final collision displacement/parity. Do not mark a collision response difference accepted until a concrete block provider plus its exact downstream comparison closes these dependencies.
- **Next bounded action:** inspect exact paired block collision shapes that can occupy the changed upper-face sliver and the exact `joinIsNotEmpty`/shape-coordinate operations used by this query. If no block shape can distinguish the changed geometry, reject the candidate as a movement finding and retain only the source-level implementation note.

No tests, builds, runtime launches, decompilation, wiki, product code, or product Git history were consulted.
