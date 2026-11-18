# Ordinary contact movement witness: cobweb corner

Status: source-only witness for an in-scope player movement consequence; no implementation or runtime parity claim.

## Exact source identity

The roots are `decompiled_minecraft/1.21.3/mojmap/` and `decompiled_minecraft/26.2/unobfuscated/`. Their version-root ready markers are `ready` and name the exact version/mapping pair. Ready-marker SHA-256 values are `72385aa99d249a56d742b9c07823c1155c74c39dda1fbc6eacc12c980cd12e02` (1.21.3) and `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` (26.2). Source-manifest SHA-256 values, also recorded in those markers, are `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce` and `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. The earlier full manifest check matched every listed source file. Every source hash in the table below was rechecked against its entry in the corresponding version-root manifest.

## Ordinary LocalPlayer route and movement writer

A loaded `LocalPlayer.tick` enters `super.tick`; `LivingEntity.tick` runs travel and then invokes `applyEffectsFromBlocks()` for the locally authoritative client (1.21.3 `LocalPlayer.java:189-192`, `LivingEntity.java:2768-2776`; 26.2 `LocalPlayer.java:227-245`, `LivingEntity.java:3137-3145`). Unmounted `Player.travel` delegates to `LivingEntity.travel` (1.21.3 `Player.java:1445-1467`; 26.2 `Player.java:1402-1424`). On the ordinary air route, `LivingEntity.handleRelativeFrictionAndCalculateMovement` makes one `move(SELF, getDeltaMovement())`; one ordinary tick therefore needs only one movement record, not synthesized repeated moves (26.2 `LivingEntity.java:2430-2437`, `2468-2497`, `2676-2685`; 1.21.3 `LivingEntity.java:2178-2214`, `2351-2358`, and `Player.java:1445-1467`). The 26.2 air path applies gravity to the resulting vertical velocity and drag, with no terminal-velocity clamp in these source lines, so a downward SELF movement greater than one block in a tick is reachable after an ordinary fall.

1.21.3 `Entity.move` does not record ordinary moves; post-travel `Entity.applyEffectsFromBlocks()` sends the tick-old/current span to the callback (Entity.java:736-752). 26.2 `Entity.move` records actual pre-move position to collision-resolved endpoint and attaches the edge-adjusted request as axis metadata (Entity.java:711-755). Its ordinary callback consumer axis-splits this record when the metadata is present (Entity.java:1282-1301). The 0.6 by 1.8 player dimensions are explicit in 1.21.3 `EntityType.java:1053-1062` and 26.2 `EntityTypes.java:1163-1173`.

## Witness setup and callback consequence

Use a normal, non-weaving LocalPlayer, not removed and with physics enabled, in an empty volume except for a cobweb at block `(0,64,1)`. During one ordinary unmounted air-travel tick let the collision-resolved SELF movement be `(d,-1.2,d)` with `d=0.004`, from `(1.296,65,0.699)` to `(1.300,63.8,0.703)`. The same construction works for any positive equal horizontal component `d` by starting at `(1.3-d,65,0.7-d/4)` and ending at `(1.3,63.8,0.7+3d/4)`. `Entity.move` accepts an unobstructed request/resolution match; cobweb has no collision on both sides, so it does not clip this vector (1.21.3 `Blocks.java:701-712`; 26.2 `Blocks.java:716-727`).

The 0.6-wide player box does not overlap the cobweb at the start: its maximum Z is `1-d/4`, below the block's Z minimum 1. At the diagonal midpoint, `(1.3-d/2,64.4,0.7+d/4)`, the box overlaps the cobweb: its minimum X is `1-d/2 < 1`, maximum Z is `1+d/4 > 1`, and its Y interval intersects `[64,65]`. The old `BlockGetter.boxTraverseBlocks` sweeps the target box along the span only when `span.lengthSqr() >= square(0.99999F)`; this span is about 1.44 squared blocks, so the sweep branch is reached (1.21.3 `BlockGetter.java:172-189`). The old block-inside test checks the full default entity-inside shape along that span (Entity.java:1038-1061, 1080-1084; `BlockBehaviour.java:353-355`). It calls `WebBlock.entityInside`, which sets the player's stuck multiplier to `(0.25,0.05,0.25)` when the player lacks WEAVING (WebBlock.java:25-33).

In 26.2, the one actual move records the same endpoints and original delta `(d,-1.2,d)`. Equal absolute X/Z chooses `Y,X,Z` (Direction.java:48-49, 378-380). The Y leg is at the starting X/Z, where the cobweb is not overlapped; the X leg remains at starting Z, also outside it; the final Z leg is at X=1.300, where the target box is deflated and begins east of X=1. Thus the native segmented consumer does not call the cobweb callback (Entity.java:1282-1301, 1315-1352; `BlockGetter.java:175-209`). The vanilla 26.2 entity-inside shape is full-block by default, just as in 1.21.3 (`BlockBehaviour.java:331-333`).

The consequence is an ordinary movement-state write. On the following tick, the first non-piston `Entity.move` multiplies its requested movement by `stuckSpeedMultiplier` when its squared length exceeds `1.0E-7`, then clears that multiplier and delta movement (1.21.3 `Entity.java:633-637`; 26.2 `Entity.java:728-735`). Both versions implement `WebBlock.entityInside -> Entity.makeStuckInBlock`; both `makeStuckInBlock` methods reset fall distance and store the supplied multiplier (1.21.3 `Entity.java:2525-2528`; 26.2 `Entity.java:2943-2946`). Thus the old callback writes a factor that scales the next ordinary LocalPlayer move; the native 26.2 callback misses this block for the same geometric span, leaving that write absent.

## Attribution limits and correction

Within 26.2's same callback consumer and fixed endpoints, record metadata is directly causal: the actual three-field movement record takes the Y-X-Z branch; a two-field record with the same endpoints takes the direct-span branch and reaches the cobweb. The two-field version here is a consumer counterfactual, not a claim that vanilla creates that record for this normal move. Across the actual versions, traversal code also changed (`boxTraverseBlocks` versus `forEachBlockIntersectedBetween`), so attribute the cross-version result to the combined old-span/new-axis-record path, not to provenance as the only changed source.

Correction for the prior immutable comparison: its short `(0.004,0,0.004)` fire example does not pass the 1.21.3 `boxTraverseBlocks` length gate; that method returns only the target box's block positions for spans shorter than `0.99999`. Do not use that fire example as an old-version paired callback witness. This cobweb witness has a `-1.2` falling component and does pass the historical sweep gate. The large downward movement is a vanilla ordinary fall route, not a piston, packet writer, or fabricated second move. This remains endpoint-only source evidence and makes no runtime-parity claim.

## SHA-256 source roster

Paths are relative to the named exact source root. Each code-file hash matched the source-manifest entry at review time.

| Version / mapping | Relative source file | SHA-256 |
|---|---|---|
| 1.21.3 / mojmap | `net/minecraft/world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| 1.21.3 / mojmap | `net/minecraft/world/entity/LivingEntity.java` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| 1.21.3 / mojmap | `net/minecraft/client/player/LocalPlayer.java` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| 1.21.3 / mojmap | `net/minecraft/world/entity/player/Player.java` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| 1.21.3 / mojmap | `net/minecraft/world/entity/EntityType.java` | `433a855503eff559bf0d9c009080b4ff1dec73c497a455a1d22d6812ab9e7656` |
| 1.21.3 / mojmap | `net/minecraft/world/level/block/WebBlock.java` | `ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414` |
| 1.21.3 / mojmap | `net/minecraft/world/level/block/Blocks.java` | `28a66da36ce98800226ec6d8e5fb4904fc7b137322a31f6d7ec822450374e62a` |
| 1.21.3 / mojmap | `net/minecraft/world/level/block/state/BlockBehaviour.java` | `4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133` |
| 1.21.3 / mojmap | `net/minecraft/world/level/BlockGetter.java` | `5d6fde6fef7928603ff97f4a4015efbb48442f9e583f1d0cfe368c5168e01c39` |
| 26.2 / unobfuscated | `net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.2 / unobfuscated | `net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| 26.2 / unobfuscated | `net/minecraft/client/player/LocalPlayer.java` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| 26.2 / unobfuscated | `net/minecraft/world/entity/player/Player.java` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| 26.2 / unobfuscated | `net/minecraft/world/entity/EntityTypes.java` | `1b1b09812ed04d143f487d754aff5ed10ed696e8da04ce836ccbdbe0794d5a6c` |
| 26.2 / unobfuscated | `net/minecraft/core/Direction.java` | `30fb61592f1a154bb8fc239ab8df1cd2b209f63317e0c7dde4d5b9b3e260d754` |
| 26.2 / unobfuscated | `net/minecraft/world/level/block/WebBlock.java` | `8afb61bea4d6fc8502971d0729c6a2f87f4f55d618ae6ed3251b95141f524af5` |
| 26.2 / unobfuscated | `net/minecraft/world/level/block/Blocks.java` | `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435` |
| 26.2 / unobfuscated | `net/minecraft/world/level/block/state/BlockBehaviour.java` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` |
| 26.2 / unobfuscated | `net/minecraft/world/level/BlockGetter.java` | `623a44206db93d8fb2eefc9353345689cc35e0c1742b5d61789eab2b098b687d` |
