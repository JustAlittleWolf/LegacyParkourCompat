# 1.20.4 → 1.20.5: active Elytra glide route and fall cap

## Bounded result

For an ordinary local player in dry conditions with an already-set fall-flying flag and a valid default chest-slot Elytra, there is no change in the active-flag checks, Elytra validity gate, glide-versus-climbable branch ordering, or fall-distance cap before the first glide move. This holds with ordinary feet and with ladder/vine climbable feet. The climbable feet do not reset fall distance on this path because the glide branch is selected before the ordinary movement helper that calls `handleOnClimbable`.

There is a nearby gravity-source refactor: 1.20.4 uses local `0.08`; 1.20.5 calls `getGravity()`, whose ordinary living-entity attribute default is also `0.08`. With an ordinary player (gravity attribute unchanged, no no-gravity flag), the first-glide gravity value is therefore unchanged. Attribute/no-gravity variants are outside this bounded ordinary-player conclusion.

## Exact source findings

- The local player reaches inherited movement: `LocalPlayer.aiStep` calls `super.aiStep()` in both versions (1.20.4 `LocalPlayer.java:646-800`; 1.20.5 `:648-807`). `LivingEntity.aiStep` then calls `updateFallFlying()` before constructing travel input and calling `travel`: 1.20.4 `LivingEntity.java:2593-2603`; 1.20.5 `LivingEntity.java:2673-2683`.
- `updateFallFlying()` begins with shared flag 7 and keeps a requested active state only while airborne, not a passenger, and without Levitation. It then reads `EquipmentSlot.CHEST`; the gate is `stack.is(Items.ELYTRA) && ElytraItem.isFlyEnabled(stack)`. Otherwise it computes false. The shared flag is written only when `!level().isClientSide`. Thus on a local-client tick the method's computed cancellation is not persisted there; the travel test still directly reads `isFallFlying()`/shared flag 7. Relevant bounds: 1.20.4 `LivingEntity.java:2641-2661`, `:3086-3088`; 1.20.5 `LivingEntity.java:2721-2741`, `:3165-3167`.
- The exact chest-item durability validity helper is unchanged: `ElytraItem.isFlyEnabled` returns `damageValue < maxDamage - 1` in both versions. Bounds: 1.20.4 and 1.20.5 `world/item/ElytraItem.java:17-19`.
- In `LivingEntity.travel`, after the water and lava branches, `else if (isFallFlying())` is evaluated before the ordinary movement path. The glide branch calls `checkSlowFallDistance()`, calculates glide velocity, then calls `move`. In the non-glide path, `handleRelativeFrictionAndCalculateMovement` calls `handleOnClimbable`, which resets fall distance. Therefore when the already-active flag selects glide, ladder/vine climbable feet do not reach that climbable cleanup before the glide move. Bounds: 1.20.4 `LivingEntity.java:2035-2160` (glide branch), `:2201-2233` (ordinary helper/cleanup); 1.20.5 `LivingEntity.java:2104-2227`, `:2268-2300`.
- The cap is identical and executes before that move: if pre-move `deltaMovement.y() > -0.5` and `fallDistance > 1.0F`, set `fallDistance = 1.0F`. Bounds: 1.20.4 `Entity.java:2313-2318`; 1.20.5 `Entity.java:2320-2325`.
- Gravity input is the one adjacent source change on the glide path: `travel` uses `double gravity = 0.08` in 1.20.4, versus `getGravity()` in 1.20.5. `LivingEntity.getDefaultGravity()` reads `Attributes.GRAVITY`; that attribute's default is `0.08`, while `Entity.getGravity()` returns zero under no-gravity. Hence ordinary default-player value matches, but the general attribute/no-gravity path differs. Bounds: 1.20.4 `LivingEntity.java:2035-2040`; 1.20.5 `LivingEntity.java:2100-2107`, `Entity.java:1101-1107`, `ai/attributes/Attributes.java:42-44`.

## Provenance and source integrity

Both canonical `decompiled_minecraft/<version>/mojmap/` trees have ready markers whose `versionId` and mapping fields match the requested exact release and Mojmap namespace. The relevant Java file SHA-256 values below match their corresponding entries in `mojmap.sources.sha256`; the source-manifest SHA values match the ready marker. The 1.20.4 provenance is a batch run containing exact version IDs `1.20.1, 1.20.2, 1.20.4, 1.20.6`; 1.20.5 is its own exact run. Both identify official Mojang mappings and Vineflower 1.12.0.

- 1.20.4 `mojmap.ready.json` source manifest SHA-256: `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`; actual `mojmap.sources.sha256` SHA-256 matches. Ready marker SHA-256: `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1`.
- 1.20.5 `mojmap.ready.json` source manifest SHA-256: `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`; actual `mojmap.sources.sha256` SHA-256 matches. Ready marker SHA-256: `4a587caa86e4ddd68ba63a5431417933dc4df4822e33aaf29ce77ebda53947c2`.

Relevant source SHA-256:

| Exact source path | SHA-256 |
|---|---|
| `decompiled_minecraft/1.20.4/mojmap/net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |
| `decompiled_minecraft/1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| `decompiled_minecraft/1.20.4/mojmap/net/minecraft/world/entity/Entity.java` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `decompiled_minecraft/1.20.4/mojmap/net/minecraft/world/item/ElytraItem.java` | `fa4d677a5b109ebf323458f88f05eacb9ffdeca66d952c1a5eb8a7114a911889` |
| `decompiled_minecraft/1.20.5/mojmap/net/minecraft/client/player/LocalPlayer.java` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |
| `decompiled_minecraft/1.20.5/mojmap/net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| `decompiled_minecraft/1.20.5/mojmap/net/minecraft/world/entity/Entity.java` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| `decompiled_minecraft/1.20.5/mojmap/net/minecraft/world/item/ElytraItem.java` | `aafc9e310847fccd7ffffca5773d7df8b55285a940c185e95096baccbee2605f` |
| `decompiled_minecraft/1.20.5/mojmap/net/minecraft/world/entity/ai/attributes/Attributes.java` | `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e` |


