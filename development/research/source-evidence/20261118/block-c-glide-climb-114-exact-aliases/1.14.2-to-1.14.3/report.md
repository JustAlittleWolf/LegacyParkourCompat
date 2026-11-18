# 1.14.2 → 1.14.3 Elytra glide at ladder/vine feet

## Scope and verdict

Compared the exact local Yarn sources for an already-active Elytra glide on the logical client, with default non-creative flight, dry and on foot, at a ladder or vine under the player’s feet. Covered active flag update/clear, initiation/request authority, player-to-living travel dispatch, glide versus climb cleanup, and the pre-move fall-distance assignment. **No movement-behavior difference was found for this case.** The 1.14.2 and 1.14.3 methods retain the same gates and operation order; changed names are Yarn symbol names (including `Blocks.field_9983`/`LADDER`, `field_10597`/`VINE`, `field_16492`/`SCAFFOLDING`, `EquipmentSlot.field_6174`/`CHEST`, and `MovementType.field_6308`/`SELF`).

## Exact source identities

Paths are relative to `decompiled_minecraft/<version>/yarn/`; each listed source SHA-256 was checked against the `sources` entry in that tree’s `.decompile-provenance.json`, and the current file bytes match the manifest.

| Version | Exact mapping | ClientPlayerEntity.java | PlayerEntity.java | LivingEntity.java | ServerPlayNetworkHandler.java | ServerPlayerEntity.java |
|---|---|---|---|---|---|---|
| 1.14.2 | Yarn 1.14.2+build.7; intermediary 1.14.2 | `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947` | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` | `554278bbc6bed6b776312d35d1e4c964288cb349f4c06b2e9ff8f01fabc8c20a` | `5dd585383d9cb52bf1db7304fc755ae392d48a6d1513ff9143e3b49934340381` |
| 1.14.3 | Yarn 1.14.3+build.13; intermediary 1.14.3 | `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae` | `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` | `0467b26f218892f6ed2851688cc20ecde313fa76f6d84419af775c849eb568c3` | `45b2ea744bf16153c51e3cd31719e746f6e6a821616383f9a4e683b1046a8f3a` |

The Yarn artifact URLs and hashes recorded in the manifests are `https://maven.fabricmc.net/net/fabricmc/yarn/1.14.2+build.7/yarn-1.14.2+build.7-v2.jar` (`3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b`) and `https://maven.fabricmc.net/net/fabricmc/yarn/1.14.3+build.13/yarn-1.14.3+build.13-v2.jar` (`ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`). The corresponding exact client inputs are pinned in those manifests as well.

## Source comparison

- `net/minecraft/client/network/ClientPlayerEntity.java`, 1.14.2 lines 607–704 and 1.14.3 lines 607–704: local jump-edge handling sends `START_FALL_FLYING` only when the player is airborne, descending (`velocity.y < 0.0`), not already gliding, not in ability flight, and has a usable Elytra in the chest slot. This is an initiation request; it is not an unconditional local activation. For the already-active case in scope, this block does not re-initiate.
- `net/minecraft/server/network/ServerPlayNetworkHandler.java`, 1.14.2 lines 1095–1104 and 1.14.3 lines 1096–1105: the server repeats airborne/descending/not-active/not-in-water checks and validates the chest Elytra before `ServerPlayerEntity.method_14243()` sets tracked flag 7 true. The server owns acceptance and the tracked active flag. This is authority context, not a server-side replay of historical physics.
- `net/minecraft/entity/LivingEntity.java`, 1.14.2 lines 1222–1232 and 1.14.3 lines 1233–1243: `isClimbing()` recognizes ladder, vine, and scaffolding (or the trapdoor case) in both. `tickMovement()` calls `initAi()` before `travel()` (1.14.2 lines 2208–2244; 1.14.3 lines 2218–2254). `initAi()` derives a local flag from current flag 7, airborne/on-foot state, and usable chest Elytra; only `!world.isClient` writes it back. Therefore an already-active local client flag is not canceled by this server-authoritative update routine.
- `net/minecraft/entity/player/PlayerEntity.java`, 1.14.2 lines 1362–1388 and 1.14.3 lines 1364–1390: with default `abilities.flying == false` and no vehicle, the player delegates to `LivingEntity.travel(arg)`. The alternate ability-flight branch clears fall distance and flag 7; it is outside the scoped default-flight case.
- `net/minecraft/entity/LivingEntity.java`, 1.14.2 lines 1769–1841 and 1.14.3 lines 1779–1851: the active `isFallFlying()` branch is selected before ordinary walking movement and has no climbable-position guard. Before glide-vector computation and before `move`, `if (velocity.y > -0.5)` writes `fallDistance = 1.0F`. Equality at `-0.5` does not write it. This assignment and ordering are identical in both versions. The branch later calls `move`; its landing clear is gated by `onGround && !world.isClient`, so it does not clear the local active flag on this client path.
- The alternate, non-gliding path invokes the private `method_18801` after velocity adjustment and before `move`; it detects `isClimbing()`, clears `fallDistance` to `0.0F`, and applies ladder/vine-style velocity clamps. The glide branch bypasses that climb cleanup in both releases. Thus the ladder/vine underfoot does not divert an already-active glide into ordinary climb movement.
- As a separately checked alternate, `net/minecraft/entity/LivingEntity.java`’s glide branch still clears flag 7 on ground only on the non-client side, and `initAi()` clears it server-side when grounded, riding, or lacking usable chest Elytra. Those authority/cancel gates are the same in both sources.

## Boundary

This is a bounded no-difference comparison, not full-pair tick/inventory coverage, an independent audit, or evidence for intermediate patch boundaries. No implementation, gameplay check, or runtime parity claim was made.
