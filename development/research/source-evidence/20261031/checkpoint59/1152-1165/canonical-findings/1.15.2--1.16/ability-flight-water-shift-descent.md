# Ability flight skips the water shift-descent impulse

1.15.2 (Mojmap): `net/minecraft/client/player/LocalPlayer.java`, `aiStep` (723–725) calls `goDownInWater()` when the player is in water and sampled shift is held; `net/minecraft/world/entity/LivingEntity.java`, `goDownInWater` (1819–1821) adds `(0.0, -0.04F, 0.0)` to velocity.
1.16 (Mojmap): `net/minecraft/client/player/LocalPlayer.java`, `aiStep` (758–759) adds `isAffectedByFluids()` to that gate; `net/minecraft/world/entity/player/Player.java`, `isAffectedByFluids` (1005–1007) returns `!abilities.flying`; `LivingEntity.goDownInWater` (1896–1898) retains the same velocity addition. This operation form persists unchanged in 1.16.1–1.16.5. Boundary: 1.15.2 → 1.16.

Difference: For an ability-flying local player in water with shift held, the old LocalPlayer tick applies the `-0.04F` vertical velocity adjustment and the new tick skips it. This is separate from the water-current branch, which is also gated during ability flight.

Movement meaning: `Player.travel` saves incoming Y before flying travel and restores 60% afterward (1.16 `Player.java:1395–1402`), so the direct descent impulse preserves the direct Y-velocity difference scaled by `0.6` (the source operations are `0.6 * (-0.04F)`) under equal sampled state. This claim is limited to the direct velocity response and does not assert a fixed position difference independent of fluid travel and collision.
