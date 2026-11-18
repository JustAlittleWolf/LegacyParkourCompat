# Independent source audit verdict: 1.18.0 → 1.18.1 ability flight / glide clear

**Verdict: ACCEPTED — no source difference in the assigned operation.** The draft's bounded trace, version labeling, post-move ordering, and no-difference conclusion match the exact Mojmap source. This accepts only the named operation; it does not close pair coverage or establish an implementation boundary.

## Frozen claim and review identity

- Draft: `c-mid-118-1181-ability-flight-clear-source/report.md`
- Draft SHA-256: `e8107af3a75be099ab3f2992682437bf004cd33785fbc8eb415d3a48355f79a2`
- Older exact release ID: `1.18` (the release called 1.18.0 in the draft), Mojmap.
- Newer exact release ID: `1.18.1`, Mojmap.
- Shared provenance run: `prep-1.18-1.18.1-mojmap-2026-10-09-r1`.

## Independent trace

For the stated local dry, airborne, nonpassenger player with enabled chest Elytra, mayfly-authorized ability flight, and flag 7 set, `LivingEntity.aiStep` calls `updateFallFlying()` immediately before `travel`. On the client, `updateFallFlying` does not overwrite the flag; its revalidation leaves it true under the stated Elytra/airborne/nonpassenger gates. `Player.travel` selects `abilities.flying && !isPassenger()`, saves the incoming vertical velocity, calls `super.travel`, then sets vertical velocity from the saved value times `0.6`, restores `flyingSpeed`, calls `resetFallDistance()`, and writes `setSharedFlag(7, false)`, in that order. The superclass `LivingEntity.travel` therefore enters its active Elytra branch before those player-level writes, computes glide acceleration/drag, and calls `move(MoverType.SELF, ...)`. Its own flag-7 clear is guarded by `onGround && !level.isClientSide`, so it does not clear the flag on this airborne logical-client path. The subsequent `Player.travel` clear is independent of the fall-distance reset; `Entity.resetFallDistance()` sets `fallDistance` to `0.0F` after the glide move returns.

The reviewed files `LocalPlayer.java`, `LivingEntity.java`, `Entity.java`, `Player.java`, and `ElytraItem.java` have identical bytes between these two source sets. Their hashes also match each side's source manifest. The gates, real move, server-only grounded clear, player post-move velocity adjustment, fall-distance reset, and direct shared-flag clear therefore establish no difference for this operation.

## Exact evidence identity

| Source set | Provenance SHA-256 | Ready sidecar SHA-256 | Source manifest SHA-256 |
|---|---|---|---|
| `decompiled_minecraft/1.18/mojmap/` | `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5` | `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` |
| `decompiled_minecraft/1.18.1/mojmap/` | `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139` | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` |

| Relative source path | SHA-256 in both releases |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` |
| `net/minecraft/world/entity/LivingEntity.java` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| `net/minecraft/world/entity/Entity.java` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |
| `net/minecraft/world/entity/player/Player.java` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` |
| `net/minecraft/world/item/ElytraItem.java` | `f6859e9d245a2b34c4e2c24af5bf6a2bb956f05ba8eaf3dbef74ba543ea0993c` |

**Signed:** independent exact-source audit, `/root/c_mid_118_1181_ability_flight_glide_clear_audit`.