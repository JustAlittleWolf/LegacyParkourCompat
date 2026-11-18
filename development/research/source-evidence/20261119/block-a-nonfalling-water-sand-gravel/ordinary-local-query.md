# Ordinary local sand/gravel current query witness (1.8)

Verdict: **SOURCE-SUPPORTED SAME-TICK LOCAL QUERY**. The previous audit correctly rejected a *static* sand/gravel block with water directly underneath it. A stable ordinary witness does not require that geometry: place dry sand, red sand, or gravel at the east side of a source-water probe, supported by stone below; get the nonzero flow from a separate level-1 water neighbor to the north. A normal block-item interaction can predict the dry side block locally before the player’s water-collision check later in the same `Minecraft.tick()` call.

This is an exact 1.8 Feather witness only. It reuses the already frozen 24-release material audit and hashes its own 1.8 tick, placement, liquid, and block sources in [ordinary-query-hashes.tsv](ordinary-query-hashes.tsv). Every listed raw source hash matches `1.8/ornithe-feather/.decompile-provenance.json`. No source generation or runtime action occurred.

## Ordinary supported fixture

Use a loaded world and a standing local player centered at `(0.5,64,0.5)`, unmounted and with `abilities.flying == false`. Preserve the accepted glass audit's exact player dimensions and liquid-scan bounds: the drag scan visits water at `(0,64,0)` and not the north cell at `z=-1` or the east dry side at `x=1`.

Relevant cells:

| Position | State |
|---|---|
| `(0,64,0)` | source water, level `0` (the only water cell sampled directly by the player scan) |
| `(0,64,-1)` | water, level `1`, on stone and enclosed by stone north/east/west |
| `(1,64,0)` | dry `sand` state (plain or red metadata), or `gravel` |
| `(0,63,0)`, `(0,63,-1)`, `(1,63,0)` | stone support |
| `(0,64,1)`, `(-1,64,0)` and their lower cells | dry/stone, so they add no water gradient |

The level-1 north water is a reachable settled state from ordinary flow. With the north cell supported by stone, the center source as its sole adjacent level-0 source, and the other horizontal exits stone-blocked, `FlowingLiquidBlock.tick` computes level `1` again and calls `convertToSource`, which installs `Blocks.WATER` while retaining `LEVEL == 1`. The north block is then a water `LiquidSourceBlock`; water source blocks do not tick randomly, and with the enclosure unchanged they neither flow out nor receive a neighbor update. The center level-0 source and north level-1 source therefore supply an ordinary stable gradient without putting liquid under sand/gravel.

At the center source, `LiquidBlock.getFlow` has center depth `0`. The north level-1 neighbor contributes `-Z * (1 - 0)`, which normalizes to northward flow. The east neighbor is dry. When it is sand/gravel, the dry-side branch reads its `Material.SAND.blocksMovement() == true` and skips `side.down()`; its below block is stone, so this side contributes exactly zero. The other dry horizontal neighbors also have stone below. Thus `getFlow` for the center source returns a nonzero northward vector while the sand/gravel side is queried and contributes zero. `World.applyLiquidDrag` applies normalized flow at `0.014` because the unmounted, non-flying local player’s `hasLiquidCollision()` is true.

## Local placement before player query

The item registry maps `sand` to `VariantBlockItem` (preserving metadata 0/1 for plain/red sand) and `gravel` to the ordinary `BlockItem` fallback. Click the west face of a solid block at `(2,64,0)`. The non-replaceable clicked block makes `BlockItem.useOn` offset west to `(1,64,0)`. Stone at `(1,63,0)` supports the placed block, so `FallingBlock.canFallThrough` is false. The player box ends at `x=0.8`; the target begins at `x=1`, so `World.canPlace`'s unobstructed/entity check passes. The survival interaction-manager ray reach is 4.5 blocks; the clicked block center is about 2.1 blocks from the player position. `Entity.rayTrace` passes `allowLiquids == false`, so the standing water does not block aiming at that solid face. For a normal non-spawn-protected server position, the 1.8 server handler also accepts block use within squared distance 64 of the clicked block center.

In 1.8, `Minecraft.tick()` first refreshes the crosshair target, then handles the use-key click and calls `doUse()` before its later `world.tickEntities()` call. `doUse()` invokes `ClientPlayerInteractionManager.useBlock`; for a block item, that method sends the use packet and then synchronously calls `ItemStack.useOn`. `BlockItem.useOn` checks placement, calls `world.setBlockState` for `(1,64,0)`, and consumes the item in survival. The client `World.setBlockState` writes to its local chunk; it has no server-only guard around the state write. Therefore, in the local call order, the dry sand/gravel state is visible before the subsequent entity tick.

`World.tickEntities()` walks the entity list. The local player is a `LocalClientPlayerEntity`; its `tick()` proceeds through `ClientPlayerEntity`/`PlayerEntity`/`LivingEntity` to `Entity.baseTick()`, where `checkWaterCollisions()` runs. `Minecraft.tick()` invokes `world.tickEntities()` after the item-use block above. `LocalClientPlayerEntity.tick()` also requires the player’s chunk to be loaded, which is part of this fixture.

The solid support makes sand/gravel stable on both client and server; the client-only `if (!world.isClient)` falling guard is an additional gate. This closes the static-support objection for the ordinary query witness. The placement packet is sent before local prediction, and server/network response timing can race on separate execution; this source audit establishes the local placement-before-local-player-tick order, not a network scheduling guarantee or runtime parity result.

## Source references

All paths are relative to `decompiled_minecraft/1.8/ornithe-feather/`:

- `net/minecraft/item/Item.java:301–302,665`; `VariantBlockItem.java:5–23`: sand/red metadata and gravel have ordinary block items.
- `net/minecraft/client/Minecraft.java:1166–1208,1272,1282–1284,1543–1563,1570–1594`; `net/minecraft/client/render/GameRenderer.java:305–312`; `net/minecraft/entity/Entity.java:1040–1043`: crosshair pick, use input and local tick order; ray tracing ignores liquids. `ClientPlayerInteractionManager.java:247–250` gives 4.5-block survival reach.
- `net/minecraft/client/ClientPlayerInteractionManager.java:280–317`; `net/minecraft/item/ItemStack.java:102–108`: block interaction dispatch and local `useOn` call.
- `net/minecraft/item/BlockItem.java:28–63,101–108`; `net/minecraft/world/World.java:2229–2236,235–279`: face offset, placement validation, and client-local state write.
- `net/minecraft/world/World.java:1139–1220`; `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:111–118`; `net/minecraft/entity/living/player/PlayerEntity.java:185–230`; `net/minecraft/entity/living/LivingEntity.java:1255–1256`; `net/minecraft/entity/Entity.java:244–300,745–750`: local entity iteration, inherited tick chain and water query.
- `net/minecraft/block/LiquidBlock.java:53–59,129–165`; `net/minecraft/world/World.java:1462–1495`; `net/minecraft/entity/living/player/PlayerEntity.java:1612–1614`: depth, direct material gate, flow contribution, scan and non-flying drag response.
- `net/minecraft/block/FlowingLiquidBlock.java:20–21,25–83,115–160,192–200`; `net/minecraft/block/LiquidSourceBlock.java:11–34`: level-1 settlement and stable water-source behavior.
- `net/minecraft/block/FallingBlock.java:14–42,66–73`; `net/minecraft/block/Block.java:185–205`: support and client falling gates. `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java:438–459` shows the server block-use distance check and spawn-protection gate.

The original 24-release material/alias verdict remains in `evidence.md` at this scratch path; its source hash table remains `source-hashes.tsv`. The accepted glass geometry/vector dependency is the supplied audit at `C:/Users/Wolfi/.codex/visualizations/2026/11/18/01a1f01a-5c56-7280-8791-914a15288686/legacy-fluid/water-material-glass-audit/evidence.md` (commit `f2298d2fd3f5b8959b741e502b6c367e2f213080`). No other operation, release-boundary claim, or runtime result is included here.
