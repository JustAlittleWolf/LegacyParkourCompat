# Swimming entry requires block-position water in 1.17.1

1.17, Mojmap: `1.17/mojmap/net/minecraft/world/entity/Entity.java`, `updateSwimming`.
1.17.1, Mojmap: `1.17.1/mojmap/net/minecraft/world/entity/Entity.java`, `updateSwimming`.

Difference: 1.17 enters swimming when sprinting, underwater and not a passenger. Adjacent stable 1.17.1 also requires the fluid state at the entity's block position to be water. An existing swimmer retains the sprinting/in-water/non-passenger predicate in both versions.

Movement meaning: The local player's native `Player.updateSwimming` first vetoes ability-flying, then calls the inherited entity operation. `LocalPlayer.isUnderWater` supplies its retained underwater flag. The new entry predicate can therefore reject swimming even with equal sprint, passenger and underwater-flag inputs when the current block-position fluid differs. The flying veto and underwater-flag production are separate operations.
