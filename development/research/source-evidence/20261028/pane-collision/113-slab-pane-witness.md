# 1.13.0 slab and pane collision outcome witness

## Verdict

With the same admissible block states, actor box, query and requested displacement, the two-slab witness differs between 1.13.0 and 1.13.1/1.13.2: 1.13.0 returns the positive gap (`4.9999999918171056e-8`), while 1.13.1 and 1.13.2 return `0.0`. The pane-center witness has the same outcome in all three releases: it returns the full requested X displacement (`0.003125`). The pane collector representation changes, but it does not change this outcome.

This is a source-derived solver result for the named input cases. It does not establish input-only reachability, runtime parity, or a natural-tick trajectory.

## Equal inputs

The block registrations and state geometry needed by both cases are equal across the named releases:

- `Block.java` registers `stone_slab` as `SlabBlock` in 1.13 (`:1574`) and 1.13.1/1.13.2 (`:1555`). `SlabBlock` defaults `HALF` to `BOTTOM` and returns `Block.block(0,0,0,16,8,16)`, i.e. the local box `(0,0,0)-(1,0.5,1)` (`SlabBlock.java:27,30-33,51-60`). This file has the same SHA-256 in all three exports.
- `IronBarsBlock` uses `super(1.0F,1.0F,16.0F,16.0F,16.0F,...)` and defaults all four connection properties false (`IronBarsBlock.java:15-19`). `PaneBlock` produces the center post from bounds `(7,0,7)-(9,16,9)` and selects the shape from the state (`PaneBlock.java:38-47,82-84`). These files have the same SHA-256 in all three exports.
- For the slab case, use bottom slabs at `(2,64,0)` and `(3,64,0)`, with no other collision shapes or nearby border. Use the same player AABB in every release: `minY=64.1`, `maxY=65.9`, Z overlapping `[0,1]`, and `maxX=1.99999995`; request `(dx,dy,dz)=(2,0,0)`. The first slab is X-ordered before the second and has positive gap `2 - 1.99999995 = 4.9999999918171056e-8` (decimal input subtraction in double precision).
- For the pane case, use an iron-bars state with all four connections false at `(0,64,0)`, the same player box as the accepted pane finding: center `(0.13749,64.2,0.13750005)`, width `0.6F`, height `1.8F`; request `(dx,dy,dz)=(0.003125,0,0)`. The resulting box has `maxX=0.43749001192092896`, a `9.98807907104382e-6` gap to pane min X `0.4375`, and `maxZ=0.43750006192092894`, a positive `6.192092893986256e-8` Z overlap. Y overlaps the pane. No other collision shapes/entities or nearby border are present.

Entity collision routes are present in all releases: 1.13.0's default `WorldView.getCollisions` joins entity shapes for a non-null entity in ordinary movement; 1.13.1/1.13.2's `World.getCollisions` appends them. The stated cases have no other entities in range, so these routes add no candidate and hold the actor/collision inputs equal.

## Outcomes and reason

| Case | 1.13.0 | 1.13.1 | 1.13.2 | Outcome comparison |
|---|---:|---:|---:|---|
| Two adjacent bottom slabs, requested `dx=2.0` | `4.9999999918171056e-8` | `0.0` | `0.0` | 1.13.0 differs from 1.13.1/1.13.2 |
| Pane center post, requested `dx=0.003125` | `0.003125` | `0.003125` | `0.003125` | Same result |

### Two slabs

In 1.13.0, `WorldView.getCollisions(Entity,Box,dx,dy,dz)` makes the padded sweep and passes it with the original box to `getBlockCollisions` (`WorldView.java:191-196`). The block collector scans X ascending; for each partial (non-`VoxelShapes.block()` singleton) collision shape it ORs translated shapes into the per-X/per-Z accumulators and then into one `voxelShape2`, finally optimizing and returning that combined block shape (`WorldView.java:113-185`). Both bottom slabs pass the sweep overlap predicate and are represented within that one returned voxel shape. `Entity.move` calls `m_84966497` once on that shape for X (`Entity.java:578-590`). The voxel-axis routine visits the first occupied X interval at x=2, clips to `x=2 - box.maxX = 4.9999999918171056e-8`, and returns from that single shape calculation (`VoxelShapes.java:207-265`). There is no second outer shape to trigger a second-iteration near-zero gate.

In 1.13.1 and 1.13.2, `WorldView.getBlockCollisions` maps block positions to candidates and returns each accepted non-full shape as its own stream element; only shapes identical to the full-block singleton are put in the shared `IntVoxelShape` (`WorldView.java:112-151`). `BlockPos.Mutable.iterateRegionMutable` increments X first, then resets X and advances Y, then resets X/Y and advances Z (`BlockPos.java:212-237`), so the stated x=2 slab precedes x=3. `VoxelShapes.calculateMaxOffset` clips against the first shape to the same positive gap, then before consuming the next shape its strict `abs(maxDist) < 1.0E-7` check returns zero (`VoxelShapes.java:194-205`). The `Entity.move` axis order is X against this stream (`Entity.java:578-590`).

Thus this slab pair is an outcome-level witness for the collector-to-axis-consumer change, beyond a representation difference. It is limited to the stated two partial shapes: a preceding unrelated collider or altered collector order changes the bounded calculation.

### Pane center post

The pane shape/state and player box are equal as stated above. In 1.13.0 the collector's original-box exclusion check is false because the actor's max X is still `9.98807907104382e-6` short of the post's min X. Its padded swept query intersects the post: X moves across the gap, and the added `1.0E-7` padding makes the positive Z overlap exceed the shape-merger tolerance. The post enters the combined collision shape. In 1.13.1/1.13.2 the candidate is emitted as its own shape; the same original-box exclusion is false and the same padded query retains it.

All three voxel clippers use the same `1.0E-7` inset on orthogonal axes (`VoxelShapes.java` 1.13.0 `m_14725303`, lines 211-231; 1.13.1/1.13.2 `VoxelShape.java` `calculateMaxDistance`, lines 188 onward). For X clipping they index Z using `box.maxZ - 1.0E-7 = 0.43749996192092894`, which is below the pane post's min Z `0.4375`; the post therefore supplies no X-axis cell overlapping the indexed Z range. The requested X delta remains `0.003125`, regardless of whether the collector supplies the post in one aggregate shape or one stream element.

This pane comparison corrects the tempting inference that changing the collector's representation necessarily changes the solver result. It does not generalize to other pane placements or collision shapes.

## Exact source identities and selected hashes

Read-only canonical root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. All three exports use `ornithe-feather`. The recorded coordinates are Feather gen2 `1.13+build.2`, `1.13.1+build.2`, and `1.13.2+build.2`; mapping artifact binaries were not reread in this assignment. Ready-record source-manifest hashes match the root `*.sources.sha256` files. The targeted Java-file hashes below were recomputed from the source exports and match their corresponding manifests.

| Release | Provenance file SHA-256 | Ready file SHA-256 | Source-manifest SHA-256 |
|---|---|---|---|
| 1.13 | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 1.13.1 | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |
| 1.13.2 | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |

Targeted source SHA-256 by relative path under `<release>/ornithe-feather/`:

| Source | 1.13 | 1.13.1 | 1.13.2 |
|---|---|---|---|
| `net/minecraft/world/WorldView.java` | `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| `net/minecraft/world/World.java` | `6e3d94e6cdcfbb15472c4ee981a1c12500eaf7cd5d5dc20d75bcde91174c2eb5` | `119ac6f61b93cf352b2026934e9f95877f55dc2f582b45d75c2bdae3652532b6` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| `net/minecraft/world/EntityView.java` | not used by the 1.13.0 default collector | `2b5b59eef6efa93bb51c1d838c7a1cf64725be96c55c47f76894fde11e13d6de` | same as 1.13.1 |
| `net/minecraft/entity/Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `net/minecraft/block/Block.java` | `e5be72b77a28e89595009289964bb031bbb187d15d5698570615e6fc9a21a476` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| `net/minecraft/block/SlabBlock.java` | `2e7ff3fcfa5faa83aa4af7ea9b68f07af8d8fe7047e08ee3a9d7d37b78c7128f` | same | same |
| `net/minecraft/block/IronBarsBlock.java` | `df6d956bb2745241e59294056f31513151b976dbd9ceed3f5478fd72b66c68a0` | same | same |
| `net/minecraft/block/PaneBlock.java` | `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c` | same | same |
| `net/minecraft/block/shape/VoxelShapes.java` | `6ae1a6fa0ab76098252208fad00a6d4f3d3f1cf2023d76f63d450855df00a330` | `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95` | same as 1.13.1 |
| `net/minecraft/block/shape/VoxelShape.java` | `3a085bf46c35869851c1db0966d596c90ec3949532a361bb90c78191a7b76f0c` | `d95b0607647da11595e24e1e26e9615cb7da7705ea7885908544362df62275c1` | same as 1.13.1 |
| `net/minecraft/block/shape/SimpleIndexMerger.java` | `2cc1977429e1d2ed0a40b512c24377102600282bb2387b6563412caf3f98be60` | `5557e978b8e817a53d965f3917267e25bda0d08055139d4df2cb086120d354d1` | same as 1.13.1 |
| `net/minecraft/util/math/Box.java` | `0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d` | same | same |
| `net/minecraft/util/math/BlockPos.java` | `ee71cfdedcaa27458f48168ab761b435cf9c878b04f21e971ddbafb6254aa3fd` | same | same |

The 1.13.1 and 1.13.2 versions of the targeted movement, collector, shape, and state files have identical hashes. Source references above are to the exact files identified by these hashes. No sources were generated or edited. No tests, builds, game launches, or runtime checks were run.
