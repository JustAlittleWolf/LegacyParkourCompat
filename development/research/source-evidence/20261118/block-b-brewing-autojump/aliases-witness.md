# AutoJump brewing stand alias extension: 1.11.2–1.12.1

## Source-only verdict

The approved bottom-slab Brewing Stand AutoJump witness extends unchanged to 1.11.2, 1.12, and 1.12.1. Each alias has the same ordered collision append (stem, then plate), the same AutoJump ray/fallback and first-intersecting-box loop, and the same timer bounds as 1.12.2. On the approved slab arrangement, the first hit is the stem at rise 1.375, so AutoJump does not arm. On the full-block-floor control the first hit is the stem at rise 0.875 and the timer arms. Thus all three aliases are on the pre-union side of the already-reviewed 1.12.2 → 1.13 source difference. This does not identify the precise intermediate patch boundary.

## Input identity

- Approved witness: `b01-brewing-source-input/witness.md`, SHA-256 `502a93ed7fcdd065d20900d8d70103c37d87f245bea7ec463018e123d672fd53`.
- Approved independent verdict: `b01-brewing-source-input/verdict.md`, SHA-256 `03a1ac52a30a116071036775fc18e5cd48a9b38d47ebda4fefef68e45e5da59a`.

## Exact source identity and integrity

All source paths below are relative to `decompiled_minecraft/<version>/ornithe-feather/`.

- **1.11.2, Ornithe Feather gen2 build.2:** source-manifest SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`, artifact-manifest SHA-256 `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`; the ready marker records exact release 1.11.2 and client SHA-256 `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`.
- **1.12, Ornithe Feather gen2 build.2:** mapping-local `.decompile-provenance.json` SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`.
- **1.12.1, Ornithe Feather gen2 build.2:** mapping-local `.decompile-provenance.json` SHA-256 `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`.
- **1.12.2 control:** source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- **1.13 control:** source-manifest SHA-256 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`.

For 1.11.2, the cited AutoJump, BrewingStandBlock, SlabBlock, Block, World, PlayerEntity and LivingEntity source files match entries in the pinned source manifest. For 1.12 and 1.12.1, the cited AutoJump, BrewingStandBlock, SlabBlock, Block and World files match each mapping-local provenance source digest; the AutoJump, stand, slab and Block file hashes are identical between those two aliases. The cited control files for 1.12.2 and 1.13 match their pinned source manifests. The 1.12.2/1.13 control manifests and retained witness establish the new 1.13 union decomposition and plate-first order.

## Compared source

- `1.11.2`, `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:835-930`: `move` calls `autoJump` with actual horizontal displacement. AutoJump gates on enabled, timer `<=0`, grounded, not sneaking/riding, and nonzero movement input; it uses actual displacement unless squared magnitude is `<= 0.001F`, when it rebuilds the horizontal direction from movement input and yaw. It rejects view dot `< -0.15F`, uses probe reach `max(f*7,1/l)`, raises two lateral rays by `0.51F`, iterates the world collision list in order and breaks at the first AABB intersecting either ray. After clearance checks it arms only for rise `>0.5F && <=1.2F`.
- `1.12` and `1.12.1`, same class, `:852-943`: same operation order, casts, `0.001F` fallback threshold, yaw formula, ray offset, ordered first-hit loop and timer bounds. The full source file hash is the same for these exact releases.
- `1.11.2`, `1.12`, `1.12.1`, `net/minecraft/block/BrewingStandBlock.java:36-72`: stand plate is `(0,0,0)..(1,0.125,1)` and central stem is `(0.4375,0,0.4375)..(0.5625,0.875,0.5625)`. `addCollisions` appends `COLLISION_SHAPE` (stem) before `SHAPE` (plate). It does not branch on bottle-state properties, so bottle contents/state do not change collision geometry.
- Those versions, `net/minecraft/block/SlabBlock.java:20-43`: bottom slab collision/standing shape reaches local Y `0.5`.
- Those versions, `net/minecraft/block/Block.java:500-505` in 1.11.2 and `:519-524` in 1.12/1.12.1: default `canBePlaced` delegates to a check that the target cell's current material is replaceable. BrewingStandBlock provides no support/survival restriction; its default state starts with all three bottle flags false. Keep the player away while placing the stand in its air cell above the slab; no brewing item or inserted bottle is needed to create this legal state.
- Those versions, `net/minecraft/world/World.java:960-1015` in 1.11.2 and `:964-1019` in 1.12/1.12.1: the collision query admits block boxes by appending them to a list while visiting the query's block positions; each stand box is admitted through `addCollisions` in the order above. With this witness's one stand obstacle, no other candidate precedes it. Supporting slab boxes end at the query minimum Y and do not intersect the raised rays.
- Sprint speed inputs are stable across these aliases: `PlayerEntity` initializes movement speed to `0.1F`; `LivingEntity` applies the `0.3F` sprint modifier. The resulting `f=0.13` gives reach `p=0.91`.

## Reachability and first hit

Use the approved arrangement: bottom slabs at `(-1,0,0)`, `(-1,0,1)`, `(0,0,0)` and Brewing Stand at `(0,1,0)`. Start the player at `(-0.3,0.5,0.8)`, on ground. The initial player box (width `0.6`, height `1.8`) is X `[-0.6,0]`, Y `[0.5,2.3]`, Z `[0.5,1.1]`: it is supported by the two x=-1 slabs, merely edge-touches the stand at X=0, and is clear of its collision volume. Place the stand while the player is elsewhere. The movement-space and collision tests then keep both start and post-move positions lawful.

Set sideways input 0, forward input 1, sprinting true, yaw `-90°`, pitch 0, AutoJump enabled, timer 0; do not sneak or ride. The view and intended direction point +X. The player's attempted +X move clips at the stand's full-width plate, whose X-min is 0, leaving actual `dx=dz=0` in 1.11.2, 1.12 and 1.12.1. This enters AutoJump's `g<=0.001F` fallback, which turns forward input and yaw into +X. The post-move player box still has the same bounds above. AutoJump's swept query box begins from the player feet and spans the raised-height candidate region; its lateral rays at Z=0.5 and Z=1.1 are the two width-offset probes. The first runs through the stand and the second clears it. The player-height and overhead cells checked from the player's maxY are empty.

The ray Y is `0.5 + 0.51F = 1.01`. The stand is translated from block Y=1, so both stem (world maxY 1.875) and plate (world maxY 1.125) intersect the first ray. The first admitted collision box is the stem. Its center floors to `(0,1,0)` and the overhead cell `(0,2,0)` is empty, so the selected rise is `1.875 - 0.5 = 1.375`. Since this exceeds `1.2F`, AutoJump returns without setting `ticksToNextAutojump`.

For the full-block-floor control, use feet Y=1 with the stand still at `(0,1,0)` and otherwise keep the same direction/input. The raised ray is Y=1.51: it misses the plate and hits the stem, giving rise `1.875 - 1 = 0.875`. The rise passes both timer bounds and sets `ticksToNextAutojump = 1` in each alias. This matches the 1.12.2 control. The 1.13 control's plate-first union decomposition is the reviewed change: on the bottom-slab fixture the plate's rise is `1.125 - 0.5 = 0.625`, which arms the timer; on the full-block control only the upper stem intersects, so it still arms at rise `0.875`.

## Scope and residual gates

This is a bounded source-only alias extension and witness; it is not full-pair discovery coverage, a complete tick inventory, an independent full-pair audit, implementation acceptance or runtime parity. The exact patch introducing the source difference remains unknown within 1.12.2–1.13. No product source was changed and no tests, builds, game launches or decompilation were run.

## Alias-by-alias result and floor control start box

| Exact release | Stand collision-list order | Bottom-slab first hit / timer | Full-block first hit / timer |
|---|---|---|---|
| 1.11.2 | stem, then plate | stem; rise 1.375; no timer | stem; rise 0.875; timer set to 1 |
| 1.12 | stem, then plate | stem; rise 1.375; no timer | stem; rise 0.875; timer set to 1 |
| 1.12.1 | stem, then plate | stem; rise 1.375; no timer | stem; rise 0.875; timer set to 1 |

For the full-block control, put full blocks at the same support positions, with the stand still at `(0,1,0)`. Start at `(-0.3,1,0.8)`. The player box is X `[-0.6,0]`, Y `[1,2.8]`, Z `[0.5,1.1]`; it is supported by the blocks and only touches the stand's X face. The source fallback for the slab case computes `h=f*sideways=0`, `i=f*forward=0.13`, `j=sin(-90°)=-1`, `k=cos(-90°)≈0`, hence X=`h*k-i*j=+0.13`, Z=`i*k+h*j≈0`. The actual +X clip on the full-block control likewise keeps its qualifying ray direction +X.
