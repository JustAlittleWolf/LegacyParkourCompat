# 1.15.1 → 1.15.2 ability-flight / Elytra trace

## Result

For the assigned fixed-profile path—logical-client local player, `abilities.flying` already true, ordinary dry air, airborne and not a passenger, valid chest Elytra, shared flag 7 already true—there is **no source difference in the actual movement or its post-move glide clear** between 1.15.1 and 1.15.2. The flow performs the Elytra movement through `LivingEntity.move`, then `Player.travel` clears flag 7. It separately resets fall distance. The only nearby movement-input delta found is a new `!this.onLadder()` gate on starting fall flying in 1.15.2; it does not affect the already-active ability-flight path (and that start gate also requires `!abilities.flying`). This is bounded source coverage, not pair freeze.

## Trace and comparison

- `LivingEntity.aiStep` calls `updateFallFlying()` immediately before virtual `this.travel(...)` (both versions: `net/minecraft/world/entity/LivingEntity.java:2282-2288`). With flag 7 set, airborne/nonpassenger state and enabled Elytra in the chest slot, `updateFallFlying` retains the flag; its client path does not rewrite it (`LivingEntity.java:2299-2318`, both versions).
- `Player.travel` selects its ability-flight branch on `abilities.flying && !isPassenger()`, saves the current Y velocity and flying speed, computes the ability flying speed (doubled when sprinting), and calls `super.travel(vec3)` (`net/minecraft/world/entity/player/Player.java:1471-1478` in 1.15.1; `1463-1474` in 1.15.2). Those ranges are bytewise-equivalent in behavior and order.
- For dry air, `LivingEntity.travel` passes its water/lava gates and, while flag 7 is true, takes the Elytra branch. It applies the glide velocity math, writes velocity, and calls `move(MoverType.SELF, this.getDeltaMovement())` (`net/minecraft/world/entity/LivingEntity.java:1840-1871`, both versions). This is real position movement. Its ground-stop clear is separately gated by `onGround && !level.isClientSide` (`1872-1884`), so it is not the client clear in this assigned airborne path.
- After `super.travel` returns, `Player.travel` keeps the X/Z velocity produced by the real move, replaces Y with the saved value times `0.6`, restores `flyingSpeed`, assigns `fallDistance = 0.0F`, then calls `setSharedFlag(7, false)` (`Player.java:1476-1480` in 1.15.1; `1468-1472` in 1.15.2). Fall-distance reset and glide-flag clear are distinct consecutive writes; the clear is unconditional within the active ability-flight/nonpassenger branch and follows the real move.
- Adjacent delta: 1.15.1 `LocalPlayer.aiStep` permits the Elytra start attempt under `input.jumping && !bl8 && !bl && !abilities.flying && !isPassenger()` (`net/minecraft/client/player/LocalPlayer.java:715-719`). 1.15.2 adds `&& !this.onLadder()` at line 715. This rejects a *new* start attempt while on a ladder. The assigned already-active ability-flight path cannot reach that start body because it requires `!abilities.flying`; the change does not alter its subsequent `Player.travel` operation.
- `ElytraItem.java` is identical by SHA-256 in both releases; `Player.java`, `LivingEntity.java`, and `LocalPlayer.java` differ as whole files, but the compared travel and `updateFallFlying` slices above have no semantic delta. Full-file `git diff --no-index` found only unrelated bed-range/entity-query edits in `Player.java`, unrelated effect/death edits in `LivingEntity.java`, and the stated ladder gate in `LocalPlayer.java`.

## Evidence identity and integrity

Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`; mapping family: Mojmap. Both ready markers say `status: ready`; each source manifest hash was recomputed and matched its ready marker. Each cited source-file SHA-256 was recomputed and matched its line in `mojmap.sources.sha256`.

| Version | Provenance / preparation identity | Ready-marker SHA-256 | Source-manifest SHA-256 | Client SHA-256 | Source count / bytes |
|---|---|---|---|---|---|
| 1.15.1 | run `prep-1.15-1.15.1-mojmap-2026-10-09-r1`; exact run uses Mojmap, batch `1.15,1.15.1`; provenance file SHA-256 `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461` | `afbb16c3cb832ffee8e4763bf9933d9635ecd811c9fbd7166554b78e59788504` | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` | `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358` | 3,329 / 17,524,592 |
| 1.15.2 | exact run is Mojmap, batch `1.15.2,1.16.5,1.17.1,1.18.2`; provenance file SHA-256 `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c` | 3,332 / 17,543,871 |

| Source file | 1.15.1 SHA-256 | 1.15.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |
| `net/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` |
| `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` |
| `net/minecraft/world/item/ElytraItem.java` | `701e25f13e9ba8db74aca774f404a5281b2a58f4f7dc72e3aeabac76bbf28b43` | `701e25f13e9ba8db74aca774f404a5281b2a58f4f7dc72e3aeabac76bbf28b43` |

Compared/closed slices: local jump-to-start gate; `LivingEntity.aiStep` travel call and `updateFallFlying`; player ability-flight `travel`; dry-air Elytra `LivingEntity.travel` branch; `ElytraItem` identity. Unexamined: the rest of the complete player tick/inventory and dependencies outside this assigned path. No implementation, tests, runtime, source generation, or product/backlog/wiki inspection performed.