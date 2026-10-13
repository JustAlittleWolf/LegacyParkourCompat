# disabling ability flight suppresses same-tick Elytra start

1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 692–711.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 684–719.

Difference: For a player who is ability-flying, is airborne and descending, has an enabled Elytra, and presses jump on a fresh edge while eligible to toggle flight off, 1.14.4 toggles flight off and then reaches its Elytra request branch in the same `aiStep`. 1.15.2 also toggles flight off, but the `bl8` transition marker prevents the later Elytra branch from running that tick. This establishes a one-tick difference in whether the local client sends `START_FALL_FLYING`; it does not establish server acceptance or a later glide trajectory.

Movement meaning: The condition is reachable when the player has may-fly ability, flight is currently enabled and not forced by always-flying mode, the player is not swimming, the previous sampled jump was released, the newly sampled jump is pressed, and the player is descending with an enabled Elytra.
