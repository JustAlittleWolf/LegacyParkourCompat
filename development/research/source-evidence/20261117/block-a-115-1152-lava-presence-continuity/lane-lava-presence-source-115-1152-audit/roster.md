# Independent audit roster: lava presence, 1.15–1.15.2

## Assignment and frozen scope

Audit the bounded source comparison for ordinary local-player lava presence across exact Minecraft 1.15, 1.15.1, and 1.15.2 Mojmap sources. This roster fixes the audit slices before source-to-claim verification. It does not claim full player-tick inventory or pair freeze. The assigned boundaries are: (A) move callback latch/flag production, side, AABB geometry, loaded-region gate and move bypass; (B) ordinary lava cell/fluid state and surface witness; (C) local-player tick order, held-jump state/consumer and direct non-flying travel consumer; (D) separate water-height state and exclusions needed to avoid conflating the flag with fluid height.

## Inputs pinned

Primary checkout: `D:\Javastuff\LegacyParkourCompat`; exact sources under `decompiled_minecraft/<version>/mojmap/`. Mapping family: official Mojmap. Exact versions: 1.15, 1.15.1 (required middle alias), and 1.15.2. Candidate draft identity: `comparison.md` SHA-256 `08e3fbb5c402f872af193dc5206ac3417f307730822bf213b57653c26a221a6a`; author roster SHA-256 `4aeff8e5f8558f728811d56794afd92b523e9dc8a36545dd18b8ea4ae90c6758`; author manifest SHA-256 `5e06777b416751760900c260524e9738ffcf16ef583e2bb5397461e577000948` as assigned. These identities are independent audit inputs, not evidence of correctness.

## Exact paired inventory to audit

For each of the three versions, inspect and hash-check:

- `net/minecraft/world/entity/Entity.java`: `move`, `checkInsideBlocks`, `setInLava`, `isInLava`, `baseTick`, `updateWaterState`, `updateInWaterState`, `checkAndHandleWater`, `isControlledByLocalInstance`.
- `net/minecraft/world/level/block/state/BlockState.java`: `entityInside`.
- `net/minecraft/world/level/block/LiquidBlock.java`: static fluid setup and `entityInside`.
- `net/minecraft/world/level/block/Blocks.java`: lava registration.
- `net/minecraft/world/level/material/Fluids.java`, `LavaFluid.java`, `FlowingFluid.java`, `FluidState.java`; `net/minecraft/data/tags/FluidTagsProvider.java`: source-lava and tag-height witness premises.
- `net/minecraft/client/player/LocalPlayer.java`: `tick`, `isEffectiveAi`, `serverAiStep`, `aiStep`.
- `net/minecraft/world/entity/player/Player.java`: `aiStep`, `travel`.
- `net/minecraft/world/entity/LivingEntity.java`: `tick`, `aiStep`, held-jump section, `travel`, `jumpInLiquid`.
- `net/minecraft/core/BlockPos.java` and `net/minecraft/util/Mth.java`: double-coordinate flooring used by the visited-cell witness.

Trace callers/order and enclosing gates for each listed slice, compare all three releases, then verify author's source-file and numbered-slice manifests against the actual source bytes and recompute the required audit input digests. A claimed identical slice must be confirmed from source text and operation order, not only a reported digest.

## Explicit boundaries

No implementation/product/wikis/backlog/Git history, no other lanes, no releases beyond 1.15.2, no exhaustive movement inventory, and no conclusions about damage, rendering, or non-player physics. Build, tests, runtime, network, and source generation are outside this audit.
