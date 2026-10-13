# F-001: Swift Sneak increases local sneaking input scale

- **1.18.2**, mojmap: `1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 652–657.
- **1.19.4**, mojmap: `1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 654–660.

## Difference

In 1.18.2 the local player applies a fixed `0.3F` crouching/slow-movement scale.

In 1.19.4 the local player adds `0.15F` for each eligible Swift Sneak level, clamps the result to `[0.0F, 1.0F]`, and passes that scale to the input provider.

With no Swift Sneak, 1.19.4 evaluates the same base scale.

With level 1, 2, or 3 in an eligible leg slot and `isMovingSlowly()` true, the scale expression is respectively `0.45F`, `0.6F`, or `0.75F` (subject to the explicit clamp).

Swift Sneak is a 1.19.4-only mechanic in this endpoint pair, so this documents its modern movement effect and does not imply the older maps can contain it.

## Movement meaning

Client keyboard state flows through `LocalPlayer.aiStep()` to `KeyboardInput.tick()`. `LocalPlayer.isMovingSlowly()` is true for crouching or visually crawling.

The input-scale effect therefore changes locally simulated horizontal impulses in those states.

The 1.19.4 helper reads equipment slots and the registered Swift Sneak level; actual equipped item/enchantment state may be supplied by the connected server. 1.18.2 client source finding does not determine how a server chooses or authorizes that state.

Source proves a larger input multiplier for crouch/visual-crawl movement when Swift Sneak is equipped in 1.19.4.

The class-level effect is bounded to the available release endpoints.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.18.2--1.19.4/findings/F-001-swift-sneak-input-scale.md).
