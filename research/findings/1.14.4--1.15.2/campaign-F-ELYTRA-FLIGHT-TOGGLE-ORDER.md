# F-ELYTRA-FLIGHT-TOGGLE-ORDER: disabling ability flight suppresses same-tick Elytra start

- **1.14.4**, mojmap: `1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 692-711.
- **1.15.2**, mojmap: `1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 684-719.

## Difference

For a player who is ability-flying, is airborne and descending, has an enabled Elytra, and presses jump on a fresh edge while eligible to toggle flight off, 1.14.4 toggles flight off and then reaches its Elytra request branch in the same `aiStep`. 1.15.2 also toggles flight off, but the `bl8` transition marker prevents the later Elytra branch from running that tick.

This establishes a one-tick difference in whether the local client sends `START_FALL_FLYING`; it does not establish server acceptance or a later glide trajectory.

## Movement meaning

The condition is reachable when the player has may-fly ability, flight is currently enabled and not forced by always-flying mode, the player is not swimming, the previous sampled jump was released, the newly sampled jump is pressed, and the player is descending with an enabled Elytra.

In 1.15.2, the toggle branch sets `bl8` before the Elytra test; 1.14.4 has no corresponding transition marker.

The existing `F-ELYTRA-START` evidence covers the Elytra slot and enable check; this finding is limited to the ability-toggle ordering and its same-tick suppression.

The endpoint sources prove the different command-request path under these preconditions.

They do not prove the server's response or any exact release between 1.14.4 and 1.15.2.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-ELYTRA-FLIGHT-TOGGLE-ORDER.md).
