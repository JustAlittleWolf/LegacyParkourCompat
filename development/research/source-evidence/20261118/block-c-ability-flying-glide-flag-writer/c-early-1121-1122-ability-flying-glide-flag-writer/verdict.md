# Independent source audit verdict

## Verdict

**Accepted for the bounded no-difference result, with one caller-name correction.** The reviewed claim is that in both 1.12.1 and 1.12.2 a locally controlled player whose flight ability is active while flag 7 still marks an airborne valid Elytra glide runs the inherited glide movement, then the player override clears flag 7 after that movement. I independently confirmed the relevant behavior and no difference in these two releases. This is not evidence of a version boundary.

The frozen report calls the line-1847 caller `LivingEntity.tickMovement`. Its exact name is **`LivingEntity.mobTick()`** in both sources. Correct that citation/name; the operation and line reference are otherwise accurately described.

## Immutable inputs and local source identity

Frozen report SHA-256: `c87ad544f3dd6adfd659311d4322cce4d896b934c5e07dd83e412cbae47f6a60`.

All three named source-file hashes in the report match the local files on both sides:

| Source file, relative to `decompiled_minecraft/<version>/ornithe-feather/` | 1.12.1 SHA-256 | 1.12.2 SHA-256 |
|---|---|---|
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |

The full `LivingEntity.java` SHA-256 is identical. The bounded `PlayerEntity.moveRelative` lines 1386–1403 and `LocalClientPlayerEntity.move` lines 841–846 (1.12.1), 846–851 (1.12.2) were compared directly and match text-for-text.

For 1.12.1, local `.decompile-provenance.json` SHA-256 is `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`; its embedded input pins include Feather Gen2 `1.12.1+build.2` mergedv2 `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7` and the exact-version metadata hash `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b`. This legacy-shaped manifest has no top-level version or namespace; its pinned input URLs and the version/mapping directory identify the source set.

For 1.12.2, the local metadata is in the version directory (not inside the `ornithe-feather` source subdirectory): `ornithe-feather.provenance.json` SHA-256 `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`, `ornithe-feather.sources.sha256` SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`, and `ornithe-feather.ready.json` SHA-256 `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a`. The provenance names version `1.12.2`, namespace `ornithe-feather`, and the two Feather Gen2 build.2 artifacts. The source manifest matches the three file hashes above. Its ready marker pins client `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` and the artifact manifest `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`; that manifest pins the mergedv2 jar `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca` and Tiny mapping `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`. The local verification marker reports zero source-file differences and no raw-input artifact differences; it notes a regenerated derived TinyRemapper jar difference, which does not alter the published source hashes.

## Independent trace

- **Ordinary local ability-flight entry:** `LocalClientPlayerEntity.mobTick()` allows a player with `abilities.canFly` to toggle `abilities.flying` on the normal double-tap-jump path (1.12.1 lines 742–753; 1.12.2 lines 747–758). It then calls `super.mobTick()` later in that same method (1.12.1 line 807; 1.12.2 line 812). The Elytra-start packet condition separately requires `!abilities.flying`; this does not prevent toggling creative flight during an already-live glide.
- **Caller order and flag authority:** `LivingEntity.tick()` calls virtual `this.mobTick()` (line 1681). The local-player `mobTick` calls through `PlayerEntity.mobTick()` (line 430) to `LivingEntity.mobTick()`. In the travel section, `LivingEntity.mobTick()` calls `flyingTick()` at line 1846 and then virtual `moveRelative(...)` at line 1847. `flyingTick()` retains a true flag for an airborne, non-riding player with an Elytra for which `ElytraItem.canFly(itemStack)` is true (lines 1854–1868). It calls `setFlag(7, bl)` only under `!world.isClient` (lines 1870–1871), so this local tick does not clear the live client flag before movement.
- **Player override and inherited glide movement:** `PlayerEntity.moveRelative()` checks `abilities.flying && !isRiding()` (lines 1386–1393). It sets fly speed, calls `super.moveRelative`, restores saved vertical velocity multiplied by `0.6`, restores `speedInAir`, sets `fallDistance = 0.0F`, and calls `setFlag(7, false)` (lines 1394–1399). The superclass `LivingEntity.moveRelative()` allows the locally controlled branch and, with ability flight, bypasses its water/lava rejection; it tests `isFallFlying()` (the flag-7 getter at line 2184), executes the glide velocity operations, and calls `this.move(MoverType.SELF, ...)` at line 1464. Its separate grounded clear is gated by `onGround && !world.isClient` at lines 1475–1476, so it does not account for the local post-super clear.
- **Actual local displacement:** `LocalClientPlayerEntity.move()` delegates to `super.move(...)` before auto-jump (1.12.1 lines 841–846; 1.12.2 lines 846–851). The inherited `this.move(...)` in glide therefore performs the normal local-player displacement before `PlayerEntity.moveRelative()` clears flag 7.

## Boundaries

This verdict establishes only the normal local-player path with ability flight enabled and an already-live, valid airborne Elytra flag. It does not claim to establish packet timing, latency, server correction, or a networked end-to-end trajectory. The direct `fallDistance` write is noted as part of the owning method, not accepted as a separate finding. NBT, startup, unrelated flag writers, A/B behavior, damage, and other transitions are outside this audit.
