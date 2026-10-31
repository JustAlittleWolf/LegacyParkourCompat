# Ability flight skips the water shift-descent impulse

1.15.2 (Mojmap): `net/minecraft/client/player/LocalPlayer.java`, `aiStep` (723–725) calls `goDownInWater()` when the player is in water and sampled shift is held; `net/minecraft/world/entity/LivingEntity.java`, `goDownInWater` (1819–1821) adds `(0.0, -0.04F, 0.0)` to velocity.
1.16.5 (Mojmap): `net/minecraft/client/player/LocalPlayer.java`, `aiStep` (733–735) adds `isAffectedByFluids()` to that gate; `net/minecraft/world/entity/player/Player.java`, `isAffectedByFluids` (1004–1007) returns `!abilities.flying`. `LivingEntity.goDownInWater` (1898–1900) retains the same velocity addition.

Difference: For an ability-flying local player in water with shift held, the old LocalPlayer tick applies the `-0.04F` vertical velocity adjustment and the new tick skips it. This is separate from the water-current branch, which is also gated during ability flight.

Movement meaning: `Player.travel` saves incoming Y before flying travel and restores 60% afterward, so the direct descent impulse preserves a `-0.024` difference in post-travel Y velocity under equal sampled state. This claim is limited to the direct velocity response and does not assert a fixed position difference independent of fluid travel and collision.
The exact intermediate release introducing this change was not checked.
