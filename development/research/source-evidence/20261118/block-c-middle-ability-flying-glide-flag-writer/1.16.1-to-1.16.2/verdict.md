# Independent audit verdict: 1.16.1 → 1.16.2 ability flight with active Elytra glide

**Verdict: ACCEPTED for the bounded source claim.** The author report is accurate for the specified dry, airborne, non-passenger local player with an enabled chest Elytra, flag 7 already set, and a permitted creative ability-flight toggle. The described ability-flight wrapper runs an actual nested Elytra glide move; only after delegated travel returns does `Player.travel` restore vertical velocity, restore flying speed, reset `fallDistance`, and directly clear shared flag 7. The fall-distance reset and shared-flag clear are separate writes, in that order. I found no 1.16.1 → 1.16.2 source difference for this slice.

This verdict closes only the bounded claim in the author report. It does not assert complete player-tick coverage or full-pair freeze.

## Independent checks

- `Player.travel(Vec3)` in each release gates the wrapper on `abilities.flying && !isPassenger()`. It saves prior vertical velocity and flying speed, invokes `super.travel(vec3)`, reads resulting movement, writes the original vertical velocity scaled by `0.6`, restores flying speed, writes `fallDistance = 0.0F`, and then calls `setSharedFlag(7, false)`. The sequence matches in both versions.
- The delegated `LivingEntity.travel(Vec3)` executes for a `LocalPlayer` because `LocalPlayer.isEffectiveAi()` returns true. In the dry path, with flag 7 set, `isFallFlying()` selects the glide branch. That branch updates glide velocity and calls `move(MoverType.SELF, this.getDeltaMovement())` before returning. Its separate flag clear requires `onGround && !level.isClientSide`, so it does not clear the flag on the logical client in this airborne scenario.
- `LivingEntity.isFallFlying()` reads shared flag 7. The start path verifies the flag can be established with an enabled Elytra in `EquipmentSlot.CHEST` when airborne, dry, and not levitating. The local ability toggle is gated by `abilities.mayfly`; the local player toggles `abilities.flying` only when that permission is present. Airborne state avoids the later grounded cleanup of ability flight. Thus the joint precondition is source-reachable and lawful for creative flight.
- The nested glide branch can set `fallDistance` to `1.0F` before its move when vertical velocity is greater than `-0.5`; `Entity.move` also performs fall-distance accounting. The outer ability-flight branch’s `fallDistance = 0.0F` occurs after both, and separately before the flag-7 clear.
- Operation order and the relevant gates are equivalent in 1.16.1 and 1.16.2. No added or removed statement changes the requested path.

## Frozen evidence and claim binding

The canonical exact sources are read-only `decompiled_minecraft/<release>/mojmap` trees. Each ready marker identifies the matching exact release and Mojmap mapping; each ready marker's source-manifest digest matches the independently hashed manifest, and each source file listed here matches its entry in that manifest.

| Evidence | SHA-256 |
| --- | --- |
| 1.16.1 ready marker | `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb` |
| 1.16.1 Mojmap source manifest | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |
| 1.16.2 ready marker | `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214` |
| 1.16.2 Mojmap source manifest | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` |
| Author claim/report (verified before reading) | `33787834adf5b3ec1af32133835c3133e4a9a43a3d550128d55800fa7682c089` |

| Source file | 1.16.1 SHA-256 | 1.16.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `net/minecraft/world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |

## Boundaries

The report’s operation-level conclusion is confirmed. I did not examine or accept adjacent player tick, collision-provider, modifier, fluid, or direct movement-response differences; there is no full-pair inventory or independent coverage freeze in this verdict.

**Signed:** `/root/c_mid_1161_1162_ability_flight_glide_clear_audit` — independent source auditor. Verdict is bound to the immutable author-report SHA-256 and source identities listed above.
