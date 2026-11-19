# Independent audit: mixed stone and top slab AutoJump ordering

## Scope and verdict

**Accepted, bounded source-path claim:** for the specified dry, loaded, entity-free, border-interior witness, 1.14 and 1.14.1 enumerate the stone and top-slab candidates in different orders. On the first grounded AutoJump call, the first candidate arms the base `ticksToNextAutojump` counter in 1.14, while the first candidate in 1.14.1 reaches a rise-over-cap return before arming it.

**Conditional applicability only for 1.13.1 and 1.13.2:** their supplied AutoJump callers and `WorldView` collector support the same partial-before-full ordering if the per-cell providers are exactly a top-half slab collision shape and a full-block stone collision shape. The exact Feather Gen2 sources also establish those providers for the corresponding top-slab state and ordinary stone default. That closes the provider/collector prerequisite for this particular pair of block states; it does not establish the original 1.13 release, other aliases, all neighboring-state constructions, or a 1.13 mixed-provider outcome for an unverified setup.

This is source and geometry review, not gameplay/TAS evidence or runtime parity acceptance. The y=8 first contact is a reachable falling/landing-shaped precondition, but the review pins the requested motion as a bounded witness; it does not claim every ordinary travel tick emits these exact deltas.

## Immutable input and source identities

Author claim reviewed: `mixed-candidate-slab-source.md`, SHA-256 `429042759bfe629ac34644f270d7f4499e9d2b940fc04864feb4ccddb996ef4`.
Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft` (read only).

| Exact input | Provenance SHA-256 | Source manifest / embedded mapping identity |
|---|---|---|
| 1.13.1 Feather Gen2 `1.13.1+build.2` | `1.13.1/ornithe-feather.provenance.json` `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `ornithe-feather.sources.sha256` `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |
| 1.13.2 Feather Gen2 `1.13.2+build.2` | `1.13.2/ornithe-feather.provenance.json` `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` | `ornithe-feather.sources.sha256` `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |
| 1.14 Feather Gen2 `1.14+build.2` | `1.14/ornithe-feather.provenance.json` `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa` | `ornithe-feather.sources.sha256` `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98` |
| 1.14.1 Yarn `1.14.1+build.10` | `1.14.1/yarn/.decompile-provenance.json` `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` | Embedded exact Yarn artifact SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`; exact client SHA-256 `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` |

Compared source-file hashes were checked against the immutable trees. Principal files: 1.14 AutoJump `LocalClientPlayerEntity.java` `2b1ad3b3416949a9da2607a3ec2251aa69b2d9eebd76638c92e20749d625338f`; `WorldView.java` `1a9d57363b8123e48334065c363c5610e83dd353fe66edc2f08fed3ec09ddcb0`; `SlabBlock.java` `9e0eb825d3224c4720ca5fa94616bef65b691226dc090e2594bf801dfd54c322`; `Block.java` `0308b22d47062863ee36db68b907d42c7a95e060d5841f4d6e9cf67eda66fd6a`; `Entity.java` `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9`. 1.14.1 AutoJump `ClientPlayerEntity.java` `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933`; `ViewableWorld.java` `67e88826a77d5ed6dbdf7701acab287bd6b34f1e394625a6a701c1181556fd2e`; `CuboidBlockIterator.java` `d80701350196f78ee8ffd6083f9d53d53b9734825201a085c6b6e62a459edf1c`; `SlabBlock.java` `3ebf0457f6af0cf81aa6a66927b25a8bd213c3aecbad61bfc79f504ae6abb72f`; `Block.java` `f5ef3ec7cecd508d948ac5f4a09ce04a4882da000e70a518a1882a00946d56fe`; `Entity.java` `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b`.

The compared 1.13.1/.2 `LocalClientPlayerEntity.java` files have identical bytes, SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`; `WorldView.java` files have identical bytes, SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`. Their identical `Block.java`, `BlockState.java`, and `SlabBlock.java` hashes are respectively `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`, `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70`, and `2e7ff3fcfa5faa83aa4af7ea9b68f07af8d8fe7047e08ee3a9d7d37b78c7128f`.

## Independent source trace

### 1.14

- `LocalClientPlayerEntity.java:858-884` gates on AutoJump enabled, counter `<= 0`, `onGround`, not sneaking/riding, nonzero movement input, a nontrivial movement vector (with input fallback), and facing dot product at least `-0.15`. `:886-891` requires the current head cell and next cell to have empty collision shapes. `:898-915` computes the candidate range, grows the query box by width, and flattens `getCollisions(...).flatMap(shape -> shape.getBoundingBoxes().stream())`. `:918-945` tests the two lateral segments and breaks on the first hit. `:925-941` checks integer offsets `t=1` while `t<o`, and returns if the sampled top exceeds the rise cap. `:948-951` arms the next-auto-jump counter only for rise `>0.5F` and `<=o`.
- `WorldView.java:127-153,179-194` queries per-cell collision shapes, accumulates only shapes identical to `VoxelShapes.block()` in a voxel set, yields the other overlapping shapes from `stream4`, then concatenates the single aggregated `IntVoxelShape`. Hence an overlapping top slab is presented ahead of every aggregated full cube, regardless of the stone's lower cell coordinates.
- `SlabBlock.java:27-54` defines the top-half shape from y=8 through 16 and returns it for `TOP`. `Block.java:360-366` supplies the full block default and delegates collision to the state shape. The witness's dry top slab is therefore individually yielded; ordinary stone goes into the full-cube aggregate.

### 1.14.1

- `ClientPlayerEntity.java:846-872` retains the movement/input/facing gates; `:874-879` checks headroom; `:886-903` computes the same range and flattened collision-box iterator; `:906-934` exits at the first intersecting candidate, samples `t=1`, and returns on rise greater than the cap; `:936-940` arms only on a rise in `(0.5F, q]`.
- `ViewableWorld.java:129-138,155-185,193` emits each overlapping block's offset collision shape as the block iterator visits it, then appends entity collision shapes. For the assumed entity-free area, no entity candidate precedes the blocks.
- `CuboidBlockIterator.java:24-48` initializes its lower corner, increments X first, then resets X and increments Y, then resets X/Y and increments Z. Among the witness's two candidates, same X and Y with stone at Z=0 and slab at Z=1, stone is visited first.
- `SlabBlock.java:26-53` returns the y=8-to-16 top-half outline. `Block.java:366-372` makes ordinary collidable stone's default collision shape the full-cube outline and delegates slab collision to its outline shape.

### 1.13.1/.2 applicability only

- Each `LocalClientPlayerEntity.java:882-908` has the bounded AutoJump gates and movement-vector calculation; `:921-940` grows the query box and obtains flattened collision boxes; `:938-965` stops at its first probe intersection. The two exact patch files are byte-identical.
- `WorldView.java:112-151,166-177` forwards `getCollisions(entity, Box)` to the shape overload, which returns `getBlockCollisions`; the latter maps per-cell collision shapes, emits non-full shapes as encountered, and appends the full-cube `IntVoxelShape` afterward. This caller route does not add an entity-shape stream.
- `BlockState.java:205-210` delegates collision-shape lookup to the block. `Block.java:388-394` returns the full block by default and delegates collision shape to the state shape. `SlabBlock.java:51-59` returns `TOP_SHAPE` for a top-half slab and `VoxelShapes.block()` for a double slab. With the stated **top-half stone slab state** and ordinary stone state, the slab is the individually emitted partial shape and stone joins the trailing aggregate in both supplied patches. This is a state-specific closure of the generic ordering prerequisite, not a finding about an original 1.13 source tree or all slab/fluid configurations.

## Bounded first-grounded witness and margins

Assume the relevant chunks and cells are already loaded; no entities; well inside the border; dry blocks; AutoJump enabled and counter zero; no jump-boost effect; no sneaking/vehicle; nonzero +X input and forward-compatible look. World height boundaries are distant: all fixture cells are y=7 through y=10, and the 1.14 collision collector explicitly drops out-of-height cells (`WorldView.java:160-162`).

Fixture: support stone at `(0,7,0)` and `(0,7,1)`. Begin a descending move centered at `(0.59,8.1,1.1)` with request `(0.1,-0.2,0)`. Put full stone at `(1,8,0)` and `(1,9,0)`, dry top stone slab at `(1,8,1)`, leave `(1,9,1)` empty, and leave the current headroom cells `(0,9,1)` and `(0,10,1)` empty. The current cells and all candidate cells stay away from world-height and border boundaries.

Entity movement resolves collision before updating the player state: `Entity.java:433-463` in 1.14 and `:432-462` in 1.14.1 set `onGround` exactly when vertical motion was clipped and requested y was negative. The support top is y=8; the requested descent from y=8.1 clips to about -0.1, lands at y=8, and grounds this first move. The player's width is `0.6F`; at x=0.69 its max x is `0.990000011920929`, leaving `0.009999988079071` before the x=1 candidates, so requested dx is not clipped. The post-move AutoJump argument is the float cast of the actual x delta, `0.10000000149011612F`.

Using source-prescribed casts and helpers: `g=(float)(dxF*dxF)=0.010000000707805157`; the source's double fast-inverse-square-root approximation is `9.982504524854832`, then `l=(float)result=9.982504844665527`; normalized X is `0.9982504993416441`. With speed `0.1F`, `max(speed*7.0F,1.0F/l)=0.699999988079071`. The candidate segment reaches x `1.4887753391291936`, penetrating `0.4887753391291936` into x=1. `q*0.5F` for width `0.6F` is `0.30000001192092896`; its lateral z offsets are `0.29947516322135925`. At center z=1.1, probe z is `0.8005248367786408` through stone z=[0,1] and `1.3994751632213593` through slab z=[1,2], with positive face margins `0.19947516322135916` and `0.39947516322135934`. The y probe is `8 + 0.51F = 8.509999990463257`, `0.009999990463257` above the slab's lower face. Thus both candidate boxes are crossed with positive x/z/y margins; float casts do not erase the intended intersections.

- **1.14 result:** the sole partial provider is visited before the full-stone aggregate. The slab box's center maps to `(1,8,1)`; its `t=1` sample `(1,9,1)` is empty and the sampled rise remains 1. With base cap `o=1.2F`, rise 1 passes `>0.5F && <=o`, so the counter becomes 1.
- **1.14.1 result:** X-fast cell visitation yields stone `(1,8,0)` before slab `(1,8,1)`. Stone's box center maps to `(1,8,0)`; `t=1` samples stone at `(1,9,0)`, setting candidate top to y=10. Rise 2 exceeds the base `1.2F` cap, so AutoJump returns before setting the counter. No other candidate can supersede the first hit.

No build, tests, launch, product/Git/backlog/wiki inspection, or source generation was performed. Only this audit file was written in the assigned audit directory.
