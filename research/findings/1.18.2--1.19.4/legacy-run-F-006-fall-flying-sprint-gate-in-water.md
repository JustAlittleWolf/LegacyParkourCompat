# F-006: Fall-flying sprint gate changes underwater drag after entering water

- **1.18.2**, mojmap: `1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 698–705.
- **1.19.4**, mojmap: `1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 696–698.

## Difference

The local sprint decision occurs in `LocalPlayer.aiStep()` before `super.aiStep()` runs the living-entity travel tick.

If the player enters water while already fall-flying, the glide update preserves the shared flag in both versions while the enabled Elytra remains equipped.

Under the stated input conditions 1.18.2 sets sprinting; 1.19.4's added fall-flying gate does not.

The subsequent water branch therefore multiplies horizontal velocity by `0.9F` in 1.18.2 and `0.8F` in 1.19.4 when Depth Strider and Dolphin's Grace are absent.

## Movement meaning

For this source-reachable state, 1.19.4 retains less of the already-moved horizontal velocity during water travel.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.18.2--1.19.4/findings/F-006-fall-flying-sprint-gate-in-water.md).
