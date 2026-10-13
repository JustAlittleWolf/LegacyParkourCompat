# F-4: Mounted boat clips the newer lava scan above a reachable Player fluid cell

- **1.21.11**, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/Entity.java`; lines 501-507.
- **26.1.2**, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java`; lines 522-528.

## Difference

In 1.21.11, the mounted non-underwater boat guard skips only WATER-current scanning.

The separate LAVA scan still receives the complete deflated Player box.

In 26.1.2, the boat clips the passenger interaction box's minimum Y to the boat's `maxY` and the shared WATER/LAVA tracker discards any lava cell whose top is strictly below that clipped minimum.

This can remove an otherwise overlapping lava cell from the Player's LAVA height/current input before Player current application.

## Movement meaning

Use a fixed source state in a vanilla Nether, without simulating vehicle motion.

Place a normal `OAK_BOAT` at `(100.5D, 65.5D, 100.5D)` with yaw `0`, no passengers initially, and the vanilla STONE floor at Y=64.

The boat has dimensions `1.375F x 0.5625F`, so its box is `[99.8125,65.5,99.8125]` to `[101.1875,66.0625,101.1875]`.

There is no WATER.

The paired boat status scan therefore does not return a water status; no collision lies in the boat's thin ground-friction probe below Y=65.5, so `getStatus()` yields `IN_AIR` and `isUnderWater()` is false.

The existing paired `AbstractBoat.interact()` path can mount a non-flying Player; the boat has no passengers and the Player width `0.6F` is below the boat width `1.375F`.

For this single-passenger, yaw-zero boat, `rideHeight = 0.5625F / 3.0F = 0.1875F`, the boat-side Z offset is zero, and the Player vehicle attachment is `(0,0.6,0)`.

Applying the paired `positionRider()` formula to the fixed boat position yields Player position `(100.5D, 65.5D + 0.1875D - 0.6D, 100.5D)`, approximately `(100.5, 65.0875, 100.5)`.

The Player is standing: it is a passenger, is not sleeping/swimming/fall-flying/spin-attacking or crouching, and its `0.6F x 1.8F` standing box clears the Y=64 floor.

Its box is approximately X/Z `[100.2,100.8]`, Y `[65.0875,66.8875]`; deflating by `0.001` gives minimum Y `65.0885`. 1.21.11 queries Y cells 65-66 and only X/Z cell 100. 26.1.2's box modifier clips minimum Y to boat.maxY `66.0625`, so its cell scan covers Y=66 and only X/Z cell 100.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-4-mounted-player-lava-box.md).
