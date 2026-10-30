# Independent source-only audit: Anvil and Hopper target closure

## Immutable verdict

**Accept both target-closure geometry verdicts as narrowly stated: the Anvil collision-provider occupancy is equal across 1.13, 1.13.1, 1.13.2, and 26.2 for all valid horizontal facings; Hopper collision-provider occupancy is equal across those releases for all five valid facings and both ENABLED values.** The Hopper draft correctly distinguishes the collision provider from its interaction shape and confirms that the 10/16–11/16 full-footprint band persists in the target releases. This is source-only, same-input geometry/provider equivalence; it does not establish full movement or runtime parity.

The corrected permanent Hopper finding at commit `d5f4a6567890edfb4a8f13b2e7eac2908118b4b9` includes the band addition and funnel statement correctly. The commit's blob content and the current finding content have SHA-256 `8a9ca0791abf31d88e5ae4ec430e5d0853c07d312dadc270a8bf1dd44f82b272`. This review binds that corrected revision; the target-closure draft's cited `650dac…` is the earlier finding revision used by the prior review and is not the hash of the corrected finding.

## Immutable inputs reviewed

| Input | SHA-256 |
|---|---|
| `anvil-hopper-target-closure/ANVIL.md` | `e7c8f835834fbba9617696dac065389e4d0a4f461a7a5a8129837f50b23d7821` |
| `anvil-hopper-target-closure/HOPPER.md` | `7df555960eb895cbe669fd3495aaafae78f13811f5f1038d9c0dbc57acf147e2f` |
| `collision-pool/geom-nonwalls-review/review.md` | `4f0786929fe751474613d258ca097d5fb5bcec8fba075164f7fca89b130f4c45` |
| `collision-pool/anvil/FINDING.md` | `ac737707aa9286d6c63aa333fadaad7a58e613d98e5bfb4cf878191a88264f23` |
| Permanent Anvil finding | `0b06933542a585cbb7edf64e10d34596631077505a8058d212b8092dfe8df410` |
| Prior-review Hopper finding revision | `650dac8b4961cca6389db8e3f3e978933f55c20837520c54091bbe98c8d0cb3a` |
| Corrected Hopper finding, current and at commit `d5f4a656` | `8a9ca0791abf31d88e5ae4ec430e5d0853c07d312dadc270a8bf1dd44f82b272` |



## Exact source identity and hashes

Canonical source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`, read-only. Ready markers identify 1.13, 1.13.1 and 1.13.2 as Ornithe Feather Gen2 build 2 with the corresponding exact client hashes below; 26.2 is the ready unobfuscated exact-version tree. Each listed source-manifest hash was recomputed against its canonical manifest. Each source-file hash below was independently recomputed. The cited hashes in both closure drafts and their shared review match, except the explicitly historical Hopper finding hash noted above.

| Release | Mapping / client SHA-256 | Source-manifest SHA-256 |
|---|---|---|
| 1.13 | Ornithe Feather Gen2 build 2 / `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 1.13.1 | Ornithe Feather Gen2 build 2 / `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |
| 1.13.2 | Ornithe Feather Gen2 build 2 / `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |
| 26.2 | unobfuscated / ready exact-version tree | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` |

Core SHA-256 values (the 1.13-family paths are `net/minecraft/block/...`; 26.2 paths are `net/minecraft/world/level/block/...` unless specified):

| Source | 1.13 | 1.13.1 and 1.13.2 | 26.2 |
|---|---|---|---|
| AnvilBlock | `156505373606a0cf2c0df329e170465c207b72b2ab59798272cbec389373092e` | same | `a15b5ef5067f098a2e6b83d3b934730f376a3420a4ce47c798a6a80d800b9391` |
| HopperBlock | `c43588fd0bdf0c17ec7356090e67af8101335b5ee2a565088837d6d75a3a73ae` | same | `e6687c0f3a34a796e5faf7bb04004c9b3cadc700119f417087eae4eb6aeb298a` |
| inventory Hopper / block-entity Hopper | `875ea61c315306699ac6d0e9d4551037515d4f9408521a391f6140044218b820` | same | inline construction in HopperBlock |
| 1.13 Block / modern Block | `e5be72b77a28e89595009289964bb031bbb187d15d5698570615e6fc9a21a476` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab` |
| Blocks registrations | 1.13 `Block.java` contains registration; see hash above | 1.13.1/.2 `Block.java` same hash above | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` |
| Shapes | — | — | `6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891` |
| BlockBehaviour | — | — | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` |
| BlockStateProperties | — | — | `c6e4b49663a09110e96a3b51628f92defd83da9267a5d569a8ac8fc0c8893cd0` |
| CollisionGetter | — | — | `8e7d1a54d27e0f187667439539c59b8892f966f1b9c8107eb162c6d43f830f6c` |
| BlockCollisions | — | — | `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e` |



## Retrace findings

### Anvil

The six 1.13 components and axis dispatch in `AnvilBlock.java:29–37,71–74` give the box unions stated in ANVIL.md. The 26.2 seed union in `AnvilBlock.java:35–37`, `Block.column` (`Block.java:177–184`) and `Shapes.rotateHorizontalAxis` (`Shapes.java:379–384`) yields those same boxes: Axis.Z uses the `(8×10,Y4..5)`, `(4×8,Y5..10)`, and `(10×16,Y10..16)` components; Axis.X applies the centered quarter turn, with the common 12×12 base unchanged. Horizontal facing domains and NORTH defaults agree. 1.13 `Block.java:1303–1305` registers all three of `anvil`, `chipped_anvil`, and `damaged_anvil` as `AnvilBlock`; 26.2 `Blocks.java:2853–2881` registers all three using `AnvilBlock::new`. Chipped/damaged tier does not choose another provider. The default collision provider is enabled and delegates to the state shape at 1.13 and 26.2; the Anvil override depends only on facing and ignores collision context.

### Hopper

The 1.13 base is exactly `(full 0..16 footprint,Y10..16) ∪ (center 4..12 footprint,Y4..10)`, minus the inside `(2..14 footprint,Y11..16)`. Consequently all coordinates across the footprint remain occupied at Y10..11/16; the central funnel from Y4 to Y10/16 is not subtracted. 1.13 outlet unions are the four-unit side strips at Y4..8 and the downward 4×4 outlet at Y0..4. In 26.2 `HopperBlock.java:64–69`, `Block.column` has matching base/funnel dimensions; its seed north spout extends inward to Z=8, but the inward Z4..8 half is inside the base funnel. After clipping the spout to `Shapes.block()`, only the old outward outlet remains beyond the base. `Shapes.rotateAll` around `(8,6,8)` maps the spout to each horizontal facing and DOWN; the clipped DOWN box is `[6,0,6]..[10,6,10]`, whose Y4..6 portion is already inside the base, leaving the same old Y0..4 outlet. This verifies equality of the collision unions, not just the visible component dimensions.

Both versions allow DOWN plus the four horizontal facings and exclude UP (1.13 `Properties.java:55`; 26.2 `BlockStateProperties.java:56`). Both defaults are DOWN and ENABLED=true (`HopperBlock.java:48–51` / `53–57`). The modern `makeShapes` lambda reads only FACING; `getShapeForEachState(..., ENABLED)` normalizes ENABLED to the property's first possible value, true (`BooleanProperty.VALUES` is `[true,false]`), before lookup. Thus either ENABLED value uses the same shape. Hopper remains collision-enabled: 1.13 properties omit `.noCollision()` and 26.2 properties default `hasCollision=true`, with `.noOcclusion()` only affecting occlusion.

Interaction geometry is separately provided by `getInteractionShape`/`interactionShapes` (1.13 `HopperBlock.java:42–46,72–86`; 26.2 `HopperBlock.java:58–61,78–80`). It is not the entity movement collision provider. 1.13 world movement asks `getCollisionShape`; the default block provider returns `state.getShape` or empty by its collision-enabled flag. In 26.2 entity collision iteration carries an entity-derived `CollisionContext` through `CollisionGetter` and `BlockCollisions`, but the default `BlockBehaviour.getCollisionShape` discards it when calling `state.getShape`; Hopper's shape getter returns its precomputed FACING map and ignores context. The target provider paths therefore select the same occupancy for the same state and query box.

## Scope and limits

Accepted: both immutable target-closure drafts, for their stated source-only 1.13-family-to-26.2 collision-provider equivalence. Accepted: the corrected 1.12.2–1.13.2 Hopper finding at the bound `d5f4a656` revision includes the added upper band and retained funnel. The closures do not change the earlier 1.12.2–1.13.2 acceptance, locate an intermediate snapshot boundary, establish complete movement-tick coverage, or prove trajectory/runtime parity.
