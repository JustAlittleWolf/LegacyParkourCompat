# jump press can start fall-flying before descent

1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 707–711.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 715–719.

Difference: For an airborne player with an enabled Elytra who presses jump after releasing it, while still ascending or at zero vertical velocity, 1.14.4 does not send a start request because its gate requires Y velocity below zero. 1.15.2 has no descent requirement; provided the player is not riding, on a ladder, or in ability flight, its helper sets the fall-flying shared flag locally and then sends the request. 1.15.2's helper additionally rejects water and an already active fall-flying state. These gates can change both whether the request is sent and the local player state before the server response.

Movement meaning: The path is client `LocalPlayer.aiStep` -> Elytra slot/enabled check -> 1.15.2 `Player.tryToStartFallFlying` -> shared flag 7 -> `LivingEntity.travel` fall-flying branch.

## Exact intermediate local climbable gate

The first checked stable Mojmap local start branch with a ladder gate is 1.15.2. In 1.14.4, `LocalPlayer.aiStep` lines 707–711 uses `this.input.jumping && !bl && !this.onGround && this.getDeltaMovement().y < 0.0 && !this.isFallFlying() && !this.abilities.flying`, then checks the enabled chest Elytra and sends the start request. In 1.15 and 1.15.1, the local branch at lines 715–719 uses `this.input.jumping && !bl8 && !bl && !this.abilities.flying && !this.isPassenger()`; it no longer requires descending Y and calls `this.tryToStartFallFlying()` before sending. Neither local branch contains `onLadder()`. The exact `LocalPlayer.java` and `Player.java` source hashes for 1.15 and 1.15.1 match their respective ready source manifests; both files are identical across those two publications.

In 1.15.2, the same local branch at `LocalPlayer.aiStep`, lines 715–719, adds `&& !this.onLadder()` alongside the existing passenger guard; this is the first checked stable gate cutover for climbable state. `Player.tryToStartFallFlying`, lines 1578–1588, remains the helper operation: it requires airborne, not already fall-flying and not in water, checks the enabled chest Elytra, calls `startFallFlying`, then returns true. `startFallFlying` sets shared flag 7. Exact Mojmap source identities are `1.14.4`, `1.15`, `1.15.1` and `1.15.2`, confirmed by each ready marker's version id and source-manifest hashes for `LocalPlayer.java` and `Player.java`; in each case the relevant file bytes match the manifest.
