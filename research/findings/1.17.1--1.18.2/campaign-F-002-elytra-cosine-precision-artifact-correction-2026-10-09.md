# F-002: Fall-flying coefficient changes from float-table to double cosine

- **1.17.1**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-002-elytra-cosine-precision-artifact-correction-2026-10-09.md); lines 2074-2090.
- **1.18.2**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-002-elytra-cosine-precision-artifact-correction-2026-10-09.md); lines 2080-2099.

## Difference

1.17.1 LivingEntity#travel lines 2074-2090cos` into a float before using it in the fall-flying lift/pull equations.

1.18.2 LivingEntity#travel lines 2080-2099cos` and retains the double result in those equations.

The branch is guarded by `isFallFlying()` in both versions.

## Movement meaning

LocalPlayer#aiStep 1.17.1 lines 747-751 / 1.18.2 lines 740-744 reads jump input and, when the player is airborne, not already fall-flying, not ability-flying, not a passenger and not on a climbable, checks the chest-slot item and `ElytraItem.isFlyEnabled`.

On a valid Elytra it calls `tryToStartFallFlying()` and sends the same START_FALL_FLYING command.

LocalPlayer.java hashes are 1.17.1  / 1.18.2 .

Player#tryToStartFallFlying 1.17.1 line 1542 / 1.18.2 line 1555 sets the shared fall-flying flag only when the player is not on ground, not already fall-flying, not in water, and has no Levitation effect; it also requires a fly-enabled chest Elytra. `ElytraItem.isFlyEnabled` 1.17.1/1.18.2 line 21 requires damage value < max damage - 1 (class hashes 1.17.1  / 1.18.2 ).

The server command handler repeats `tryToStartFallFlying` and stops the flag if the request is not eligible.

The paired client and server activation paths therefore reach the guarded travel branch with an intact chest Elytra and the same entry requirements.

1.17.1 reachable setup is a player with a fly-enabled Elytra in the chest slot, falling outside water and without Levitation, who presses jump while not passenger, ability-flying or on a climbable.

The identical guards activate fall-flying before subsequent travel ticks.

Once the guarded branch is active, the same lift/pull formula receives a float-table cosine in 1.17.1 and a double cosine in 1.18.2.

This proves a historical arithmetic change in player travel. 1.17.1 particular resulting velocity or trajectory delta is inferred and was not numerically simulated; modifier data and unrelated movement branches remain outside this bounded finding.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-002-elytra-cosine-precision-artifact-correction-2026-10-09.md).
