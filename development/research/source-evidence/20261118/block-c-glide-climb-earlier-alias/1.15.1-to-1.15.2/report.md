# 1.15.1 → 1.15.2: Elytra/climbable gate

## Verdict

No source-confirmed behavioral difference for the bounded case. The `LocalPlayer` input gate, chest-Elytra validation, `Player.tryToStartFallFlying`, local travel eligibility, Elytra travel branch, climbable cleanup placement, and strict pre-move fall-distance write are the same in both exact Mojmap source trees. Do not create a difference finding for this pair/slice.

## Reachable case and call chain

- A local, on-foot player with a standard chest-slot Elytra may start fall flying from the jump input path. `LocalPlayer.tick` checks jump/no flight/passenger gates, requires `Items.ELYTRA` and `ElytraItem.isFlyEnabled`, then calls `tryToStartFallFlying`; `Player.tryToStartFallFlying` independently requires `!onGround && !isFallFlying() && !isInWater()` and repeats the item and durability-enabled check before setting shared flag 7. `ElytraItem.isFlyEnabled` is `damage < maxDamage - 1`. 1.15.1: `client/player/LocalPlayer.java:717`, `world/entity/player/Player.java:1586-1599`, `world/item/ElytraItem.java:19-21`; 1.15.2: same members at lines 717, 1578-1591, and 19-21.
- `Entity.isControlledByLocalInstance` returns true for an entity controlled by a local player; `LivingEntity.travel` enters the movement body when `isEffectiveAi() || isControlledByLocalInstance()`. 1.15.1 and 1.15.2: `world/entity/Entity.java:2521-2524`, `world/entity/LivingEntity.java:1831-1833`.
- At ladder/vine feet, `LivingEntity.onLadder()` returns true (also scaffolding and eligible open trapdoors), unless spectator. The ordinary, non-Elytra movement branch calls `handleOnClimbable`, which resets `fallDistance` and clamps vertical velocity; that call is below the `if (isFallFlying())` branch. Therefore an already-active Elytra flag takes the Elytra branch without ordinary climbable cleanup, even with a ladder/vine under the feet. 1.15.1 and 1.15.2: `LivingEntity.java:1226-1238`, `1837-1890`, `1989-2005`.
- In that branch, `Vec3 vec36 = getDeltaMovement()` is followed by strict **double** test `vec36.y > -0.5`; on true it writes `fallDistance = 1.0F`, before the look-angle calculations and `move(MoverType.SELF, ...)`. Equality at `-0.5` does not write. The code then uses the regular Elytra motion path and may clear the flag only in its existing on-ground/server-gated state update; no climbable check is inserted. 1.15.1 and 1.15.2: `LivingEntity.java:1842-1884`.
- The start gate does not reject ladder/vine state. The later `updateFallFlying` validation is separately gated by the shared flag, `!onGround`, and `!isPassenger()`, then checks chest Elytra/durability; it writes the shared flag back only when `!level.isClientSide`. Thus it does not add a client-side pre-travel climbable rejection. 1.15.1 and 1.15.2: `LivingEntity.java:2299-2316`.

The inference is limited to the stated already-active local player case: with those predicates true and ladder/vine at feet, travel reaches the Elytra path and skips `handleOnClimbable`. No trajectory beyond the source operation sequence is claimed.

## Exact source identity and integrity

Canonical adjacent release IDs were checked in the ready markers and provenance: `versionId` and `versionMetadataId` are exactly `1.15.1` and `1.15.2`, respectively; both are `mojmap`, Vineflower, Java 25. The 1.15.1 provenance also pins raw client SHA-256 `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358`; the 1.15.2 ready marker pins `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c`. The 1.15.1 source was prepared in a batch containing 1.15 and 1.15.1; 1.15.2 in a separate batch containing 1.15.2, 1.16.5, 1.17.1, and 1.18.2. These are exact release IDs, not aliases. Both source trees exist at canonical `decompiled_minecraft/<version>/mojmap/` paths and current file hashes match the per-tree source manifests.

Source manifest SHA-256: 1.15.1 `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d`; 1.15.2 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`.

| Relative source file | 1.15.1 SHA-256 | 1.15.2 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` |
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` |
| `net/minecraft/world/entity/player/Player.java` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |
| `net/minecraft/world/item/ElytraItem.java` | `701e25f13e9ba8db74aca774f404a5281b2a58f4f7dc72e3aeabac76bbf28b43` | `701e25f13e9ba8db74aca774f404a5281b2a58f4f7dc72e3aeabac76bbf28b43` |

## Coverage boundary

Checked only the dry local on-foot chest-Elytra start/active-flying gate and its interaction with ladder/vine feet, including the in-scope fall-distance write and climbable cleanup bypass. No claims about startup/final state, damage, fluids, collision-shape behavior, other climbables, intermediate patches, or the remainder of the player tick.
