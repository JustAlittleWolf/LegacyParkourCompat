# Independent source-only review: 1.12.2--1.13.2 component collision drafts

Reviewed four supplied drafts only: `anvil-collision-shape-components.md`, `brewing-stand-collision-base-footprint.md`, `cauldron-collision-basin-depth.md`, and `hopper-collision-funnel-and-facing.md`. Evidence was retraced in canonical read-only `ornithe-feather` sources at 1.12.2, 1.13, 1.13.1, and 1.13.2. No implementation, wiki, or mixed reconciliation evidence was used.

The reviewed draft revisions are bound by SHA-256:

| Draft | SHA-256 |
|---|---|
| `anvil-collision-shape-components.md` | `0b06933542a585cbb7edf64e10d34596631077505a8058d212b8092dfe8df410` |
| `brewing-stand-collision-base-footprint.md` | `f80e23049eb84d037c2e1a8b87e2bffa30e11d005eefe3ff4fecd622ce0d7ff4` |
| `cauldron-collision-basin-depth.md` | `88d18709e846a26232e5567762b642aa1384038743767225a0dc3d59836b6ad0` |
| `hopper-collision-funnel-and-facing.md` | `650dac8b4961cca6389db8e3f3e978933f55c20837520c54091bbe98c8d0cb3a` |

## Evidence identity and integrity

All four source trees identify the requested exact version and `ornithe-feather` namespace. Their Feather Gen2 mapping coordinates are 1.12.2+build.2, 1.13+build.2, 1.13.1+build.2, and 1.13.2+build.2. The source-manifest SHA-256 values below were recomputed from the canonical manifest files and match each ready marker. The per-block Java hashes below were also recomputed and match their entries in those manifests.

| Exact version | Client SHA-256 | Source manifest SHA-256 |
|---|---|---|
| 1.12.2 | `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13 | `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 1.13.1 | `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |
| 1.13.2 | `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |

Per-source SHA-256 for `net/minecraft/block/{AnvilBlock,BrewingStandBlock,CauldronBlock,HopperBlock}.java`:

| Operation | 1.12.2 | 1.13 | 1.13.1 | 1.13.2 |
|---|---|---|---|---|
| AnvilBlock | `80729f34a74ff8b1b4b2d59e55c5d8dd9d61fff94c47f862c61c12d9947eb8bb` | `156505373606a0cf2c0df329e170465c207b72b2ab59798272cbec389373092e` | `156505373606a0cf2c0df329e170465c207b72b2ab59798272cbec389373092e` | `156505373606a0cf2c0df329e170465c207b72b2ab59798272cbec389373092e` |
| BrewingStandBlock | `e875022b9a47ca6336878bcec205459462687bb35d567ee183f7ec2f1924a4fd` | `0ff465b18dc0377c1a1a123ad083f04d07f77cea8a0bc88a218a67d750f886d3` | `0ff465b18dc0377c1a1a123ad083f04d07f77cea8a0bc88a218a67d750f886d3` | `0ff465b18dc0377c1a1a123ad083f04d07f77cea8a0bc88a218a67d750f886d3` |
| CauldronBlock | `0aff4a0de147e0c677f4b931cd9a881154666bb288f128c9d8041b70c38d63f9` | `81cf1275a962c024bcfdf569286e967088f2f8d06a6ab5e8eb88b8bad7ab33c8` | `6948b2e208e1d7a52123fece400aa717b6fa5c35189fff1137490a96c170a3ac` | `6948b2e208e1d7a52123fece400aa717b6fa5c35189fff1137490a96c170a3ac` |
| HopperBlock | `3510f351f1a6b913dd834d1c8696d49e888d3a7cd9dd307388d3551c9cabfb6c` | `c43588fd0bdf0c17ec7356090e67af8101335b5ee2a565088837d6d75a3a73ae` | `c43588fd0bdf0c17ec7356090e67af8101335b5ee2a565088837d6d75a3a73ae` | `c43588fd0bdf0c17ec7356090e67af8101335b5ee2a565088837d6d75a3a73ae` |

The 1.13→1.13.1→1.13.2 hashes establish whole-file aliases for anvil, brewing stand, and hopper. CauldronBlock's whole-file hash changes from 1.13 to 1.13.1, but the inspected shape constants, `ONLY_FIRST` operation, state definition, and `getShape` body are identical in 1.13.1 and 1.13.2; the changed 1.13.1 file hash does not invalidate the geometry alias. 1.13.1 and 1.13.2 WorldView and Entity files also hash identically, while their 1.13 files differ.

## Per-operation verdicts

### Anvil — accept; geometry, state selection, and endpoint alias confirmed

1.12.2 `AnvilBlock.java:29-32,92-95` defines facing and damage state and returns one full-height box per horizontal axis. Its default `Block.getCollisionShape` delegates to `state.getShape` (`Block.java:387-389`). In 1.13, `AnvilBlock.java:28-37,71-73,107-108` composes base, two intermediate pieces, and top plate; horizontal `FACING` has four valid values, and `getShape` selects by its axis. The axis orientation sentence in the draft is correct: X-axis facing returns `SHAPE_Z`, whose top plate spans X; Z-axis facing returns `SHAPE_X`, whose top plate spans Z. Chipped and damaged anvil registrations remain anvil block variants in 1.13 (`Blocks.java:334-337,943-946`) and use the same facing-only geometry provider.

Conditional movement consequence: if a player's expanded movement box reaches an interior portion of the old full-height cuboid that is outside all four new components, the 1.12.2 collision query can include that obstacle while 1.13's query omits it; `Entity.move` then clamps the affected axis against different collision shapes (1.12.2 `Entity.java:567-600`; 1.13 `Entity.java:578-598`). This is a query/resolution consequence, not a complete trajectory claim.

### Brewing stand — accept; geometry and state independence confirmed

1.12.2 `BrewingStandBlock.java:33-42,69-77,175-176` registers three bottle booleans and calls `addCollision` for a 2/16-high full-footprint plate plus a centered 2/16-square post. In 1.13 `BrewingStandBlock.java:28-37,56-57,123-125`, the plate is 14/16 by 14/16 inset 1/16 on each side and the same centered post reaches 14/16 high. The state properties remain the three bottle booleans and do not select geometry. The 1.13 source file is byte-identical in 1.13.1 and 1.13.2.

Conditional movement consequence: a player moving beside the stand at a horizontal offset that overlaps the old outer 1/16 plate rim but avoids the centered post can have the rim present in the old expanded movement query and absent in the 1.13 query; the corresponding horizontal axis clamp can differ. Player `Entity.move` obtains the world collision query and resolves Y, X, then Z in both endpoint APIs (old `Entity.java:567-600`; new `Entity.java:578-598`).

### Cauldron — accept; basin bottom and state independence confirmed

1.12.2 `CauldronBlock.java:34-57,243-244` calls `addCollision` for the full-footprint base through 5/16 high and four full-height 2/16 wall strips; its `LEVEL` range is 0–3 and does not gate those boxes. 1.13 `CauldronBlock.java:33-44,228-230` subtracts an inside shape beginning at 4/16 from the unit block; all LEVEL states return that same shape. The draft correctly distinguishes its basin collision shape from the 1.13 interaction shape (`getInteractionShape`, lines 53-55). The relevant shape statements repeat identically in 1.13.1/.2 despite the 1.13.1 whole-file hash change.

Conditional movement consequence: with a player's downward collision box horizontally overlapping the basin floor, a descending request whose foot would enter the 4/16–5/16 band is clamped at the old 5/16 base top but can continue to the new 4/16 base top; the 1/16 difference enters the player Y-axis resolution. `WorldView.getBlockCollisions` obtains each state's collision shape (1.13 `WorldView.java:134-145`), and `Entity.move` uses the resulting shape for Y offset calculation (`Entity.java:578-584`).

### Hopper — accept the change and boundary; revise the geometric summary to include an added band

1.12.2 `HopperBlock.java:32-46,56-62,210-211` admits DOWN plus four horizontal facings, but its custom collision method ignores facing and calls `addCollision` for the full-footprint 10/16-high lower box and four full-height 2/16 perimeter strips. In 1.13 `HopperBlock.java:31-50,54-68,200-202`, the state domain still excludes UP (`Properties.java:55`), and the provider chooses one facing-specific shape; the default switch arm is outside valid vanilla states. The named 1.13 shape and `Hopper.INSIDE` source is in `HopperBlock.java:33-41` and `Hopper.java:9-12`, byte-identical through 1.13.2.

Required correction: the draft describes the removed broad lower volume and facing outlet, but omits volume newly added by the 1.13 construction. `TOP_SHAPE` is the whole block from Y=10/16 to 16/16; `Hopper.INSIDE` starts only at Y=11/16. Therefore `BASE_SHAPE` retains a *full-footprint solid band from 10/16 to 11/16*, whereas 1.12.2 has only the 2/16 perimeter strips above 10/16. `ONLY_FIRST` removes the upper interior above 11/16; it does not hollow the funnel from 4/16 to 10/16. For DOWN facing, the added 10/16–11/16 center band is not in the old lower slab (which ends at 10/16) or its old side strips; for horizontal facings the same base band is present before the side outlet is unioned. Include this addition when stating the player movement meaning.

Conditional movement consequence: a player's collision box overlapping the hopper center at Y=10/16–11/16 receives a new obstacle there in 1.13, so a move query may gain a collision and an axis clamp where 1.12.2's lower slab has ended and its perimeter strips do not reach the center. Conversely, the broad lower collision footprint is removed/reduced, as the draft says. The world collision query and `Entity.move` axis resolver consume these shapes (old `World.java:1001,1017-1020`, `Entity.java:567-600`; new `WorldView.java:134-150`, `Entity.java:578-598`).

## Acceptance boundary and open gates

The four endpoint comparisons confirm shape-provider changes first observed in 1.13 and unchanged at 1.13.1/1.13.2 in the inspected operations. The exact patch that introduced each change within 1.12.2→1.13 remains unlocated. This review establishes source geometry, vanilla state domains, and conditional player collision-query/resolution consequences; it does not establish a complete movement trajectory, exhaustive pair coverage, intermediate-release boundary, or runtime parity. Hopper draft revision must include the added 10/16–11/16 band before that wording is accepted as complete.
