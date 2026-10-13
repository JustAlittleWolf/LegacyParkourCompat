# jump press can start fall-flying before descent

1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 707–711.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 715–719.

Difference: For an airborne player with an enabled Elytra who presses jump after releasing it, while still ascending or at zero vertical velocity, 1.14.4 does not send a start request because its gate requires Y velocity below zero. 1.15.2 has no descent requirement; provided the player is not riding, on a ladder, or in ability flight, its helper sets the fall-flying shared flag locally and then sends the request. 1.15.2's helper additionally rejects water and an already active fall-flying state. These gates can change both whether the request is sent and the local player state before the server response.

Movement meaning: The path is client `LocalPlayer.aiStep` -> Elytra slot/enabled check -> 1.15.2 `Player.tryToStartFallFlying` -> shared flag 7 -> `LivingEntity.travel` fall-flying branch.
