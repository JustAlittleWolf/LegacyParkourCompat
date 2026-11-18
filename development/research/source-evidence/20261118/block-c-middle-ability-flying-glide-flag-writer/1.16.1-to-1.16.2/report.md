# 1.16.1 → 1.16.2 ability flight with an active Elytra glide

## Bounded result

No source behavior difference was found for the assigned path. In both exact Mojmap releases, a non-passenger player with `abilities.flying` enters the ability-flight branch of `Player.travel`; it invokes `super.travel` first, then restores vertical velocity from the pre-travel value at `0.6`, restores `flyingSpeed`, sets `fallDistance` to `0.0F`, and directly clears shared flag 7. The caller's clear occurs after the delegated movement returns. It is independent of the fall-distance reset.

For the assigned dry, airborne, local player with flag 7 already set and an ordinary enabled Elytra in the chest slot, `LivingEntity.travel` selects its `isFallFlying()` branch during `super.travel`. That branch computes glide velocity and calls `move(MoverType.SELF, this.getDeltaMovement())` before returning. The direct `setSharedFlag(7, false)` in `Player.travel` therefore follows the real glide move. On a logical client, the glide branch's separate `onGround && !level.isClientSide` clear does not run. The later `Player.travel` clear is unconditional within the ability-flight branch. `fallDistance` can be set to `1.0F` by the glide branch when vertical velocity is above `-0.5`, and can also be changed by `Entity.move`'s fall-damage accounting; the ability-flight caller then resets it to zero after `super.travel` returns.

The same statements and operation order are present in 1.16.1 and 1.16.2. This closes only the assigned ability-flight/glide-clear slice; it is not a full-pair coverage claim or pair freeze.

## Exact evidence

All references are to the canonical read-only sources under `decompiled_minecraft/<version>/mojmap/`.

- `net/minecraft/world/entity/player/Player.java`, `Player.travel(Vec3)`: 1.16.1 lines 1380–1410; 1.16.2 lines 1387–1417. The gate is `abilities.flying && !isPassenger()`. The branch delegates to `super.travel`, reads the resulting movement, writes `(vec33.x, i * 0.6, vec33.z)`, restores flying speed, sets fall distance to zero, then clears flag 7. The else branch delegates without these writes. The post-branch movement statistics call is unchanged.
- `net/minecraft/world/entity/LivingEntity.java`, `LivingEntity.travel(Vec3)`: 1.16.1 lines 1912–2022; 1.16.2 lines 1914–2024. The outer `isEffectiveAi() || isControlledByLocalInstance()` gate includes local-player execution. In the active-glide branch, `isFallFlying()` is checked before the glide calculation and `move(MoverType.SELF, this.getDeltaMovement())` at lines 2006 (1.16.1) / 2008 (1.16.2) occurs before the branch's on-ground clear at lines 2017 / 2019. That clear is gated by `!level.isClientSide`.
- `net/minecraft/world/entity/player/Player.java`, Elytra start: `tryToStartFallFlying()` at lines 1513–1523 (1.16.1) / 1520–1530 (1.16.2) checks airborne, not already gliding, dry, not levitating, and an enabled Elytra in `EquipmentSlot.CHEST`. `startFallFlying()` sets flag 7 true at lines 1525–1527 / 1532–1534. The assigned already-active state begins after this gate; this was checked to disambiguate entry from continued glide.
- `net/minecraft/client/player/LocalPlayer.java`: `isLocalPlayer()` returns true at lines 342–344 (1.16.1) / 343–345 (1.16.2), and `isEffectiveAi()` returns true at lines 519–521 / 494–496. `Entity.isControlledByLocalInstance()` also resolves a player through `Player.isLocalPlayer()` at `Entity.java` lines 2549–2552 / 2557–2560. The local player therefore passes the delegated `LivingEntity.travel` execution gate.
- `net/minecraft/world/entity/Entity.java`, `Entity.move(MoverType, Vec3)`: lines 485 onward in both releases; its movement path resolves collision, updates position, and calls `checkFallDamage`. `checkFallDamage(double, boolean, BlockState, BlockPos)` at lines 894–903 changes or clears `fallDistance` based on collision/fall state. These changes happen inside the delegated move, before the outer `Player.travel` fall-distance reset.
- `net/minecraft/world/entity/LivingEntity.java`, `isFallFlying()`: flag 7 is read directly at lines 2875–2877 (1.16.1) / 2891–2893 (1.16.2).

## Provenance and readiness pins

Both ready markers report `status: ready`, exact version IDs `1.16.1` and `1.16.2`, `mapping: mojmap`, Vineflower, Java 25, 3,572 / 3,516 source files, and the source manifest SHA-256 shown below. Their provenance records identify the exact paired run `.\gradlew.bat decompileMinecraft --versions=1.16.1,1.16.2 --mappings=mojmap --decompiler-heap=4G`, with Vineflower 1.12.0, TinyRemapper 0.14.1, Mapping IO 0.9.1, Gradle 9.7.1, and Java 25.0.3+9-LTS. Ready marker `sourceManifestSha256` values match the independently computed manifest file hashes. Each cited Java file digest also matches its entry in that release's `mojmap.sources.sha256`.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 |
| --- | --- | --- | --- |
| 1.16.1 Mojmap | `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb` | `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |
| 1.16.2 Mojmap | `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214` | `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c` | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` |

### Cited source file SHA-256

| Source | 1.16.1 | 1.16.2 |
| --- | --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `net/minecraft/world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |

## Coverage boundary

Compared the two `Player.travel` implementations and their directly required `LivingEntity.travel`, local-control predicates, Elytra start/state access, `Entity.move`, and fall-distance accounting for the specified ability-flight plus already-active-glide path. No other player tick, collision-provider, equipment, modifier, fluid, or movement-response slices were audited here.
