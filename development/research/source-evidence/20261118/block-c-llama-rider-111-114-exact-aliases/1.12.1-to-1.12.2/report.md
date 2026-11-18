# 1.12.1 → 1.12.2 Llama rider placement (intermediate)

## Scope and result

Compared the ordinary adult default Llama’s direct placement of a local player rider: exact Y expression and numeric types/order, horizontal offset inputs, rider offset, callback dispatch, and the applicable rider/supertype guards. Other animals and motion are excluded.

**Observed source result:** no change in this operation. The complete `LlamaEntity.java` and `Entity.java` files have identical SHA-256 across the two local source trees. The player `getRideHeight()` body is also textually identical at `PlayerEntity.java:943–945`; the rest of `PlayerEntity.java` differs, so its full-file hashes are different.

**Evidence gate:** this is a provisional no-difference observation, not an accepted exact-pair verdict. The local `1.12.1/ornithe-feather/` tree has no provenance, ready, artifact, or source-hash sidecars. A freshly computed source-file digest identifies its current bytes but cannot establish the exact source publication/mapping inputs. `1.12.2` has intact provenance sidecars. An exact `1.12.1` source identity is still needed to close the pair.

## Exact source identity

- Older path/namespace: `decompiled_minecraft/1.12.1/ornithe-feather/` (`ornithe-feather`, Feather mapping family). Local source-tree presence only; no adjacent provenance metadata was present.
- Newer path/namespace: `decompiled_minecraft/1.12.2/ornithe-feather/` (`ornithe-feather`, Feather mapping family), exact release ID `1.12.2` in `ornithe-feather.provenance.json`, `ornithe-feather.ready.json`, and `ornithe-feather.verification.json`. No `latest` alias was used. This pair has Feather sources; no Yarn or Mojmap source tree was used.
- 1.12.2 decompile inputs recorded in provenance: `feather-gen2-1.12.2+build.2-mergedv2.jar` plus `feather-gen2-1.12.2+build.2.tiny`; mapping artifact SHA-256 values are `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca` (merged jar) and `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58` (Tiny).
- 1.12.2 raw client SHA-256: `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`.
- 1.12.2 tool/runtime identity recorded: Java `25.0.3+9-LTS`, Gradle `9.7.1`, Vineflower `1.12.0`, ASM `9.10.1`, MappingIO `0.9.1`, Gson `2.14.0`, TinyRemapper `0.14.1`. The local provenance records versions, not individual tool-binary SHA-256 values.
- 1.12.2 source manifest SHA-256: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; artifact manifest SHA-256: `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`. The verification record reports all 2,050 source files and raw input artifacts matching; its one derived remapped-jar difference is documented there and does not change the published source tree.

## Compared operation and source evidence

All paths below are relative to each release’s `ornithe-feather/` root.

- `net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java:50,62,144–155`: Llama extends `ChestedHorseEntity`; its constructor sets size to `(0.9F, 1.87F)`. `updateRiderPositon(Entity rider)` first gates on `this.hasPassenger(rider)`, computes `float f = cos(bodyYaw * (float)(PI / 180.0))` and `float g = sin(bodyYaw * (float)(PI / 180.0))`, then places at `x + 0.3F*g`, `y + getMountHeight() + rider.getRideHeight()`, `z - 0.3F*f`. Its `getMountHeight()` returns `height * 0.67`.
- `net/minecraft/entity/Entity.java:1582–1601`: rider `rideTick()` zeroes rider velocity, ticks the rider, then if still riding calls the mount’s `updateRiderPositon(this)`. The base implementation also gates with `hasPassenger(rider)` and calls `setPosition(x, y + mountHeight + rideHeight, z)`; Llama overrides it.
- `net/minecraft/entity/living/player/PlayerEntity.java:943–945`: player ride height is the `double` literal `-0.35`.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:80` extends `ClientPlayerEntity`; `ClientPlayerEntity.java:21` extends `PlayerEntity`, confirming the local rider inherits the above offset.
- `net/minecraft/entity/living/mob/passive/animal/HorseBaseEntity.java:945–961`: inherited horse callback calls `super.updateRiderPositon`, updates body yaw only under `rider instanceof MobEntity`, then has the separate angry-animation placement branch. Llama’s override does **not** call this superclass method: its ordinary placement uses only the `hasPassenger` guard, and does not run those superclass branches for a player.

For an adult default Llama and ordinary local player, the unchanged Y expression is left-associative Java `double` arithmetic: `llamaY + (llamaHeightFloat * 0.67D) + (-0.35D)`. The float height is promoted for multiplication by the `double` literal; the sum with `llamaY` and player offset stays double. No Y cast or reordered intermediate is present. The `0.3F * f/g` horizontal products are computed as floats before promotion when added to double `x/z`.

## Source digests

SHA-256 of current source bytes (paths include release and mapping root):

| Relative source | 1.12.1 | 1.12.2 |
| --- | --- | --- |
| `net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java` | `487c7209a69391f1a19dce5fbdf34d609d2e9bc53c47b04a225e9c0d0c93cf7e` | `487c7209a69391f1a19dce5fbdf34d609d2e9bc53c47b04a225e9c0d0c93cf7e` |
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |

The 1.12.2 source-manifest entries confirm its Llama, Entity, and Player hashes equal the computed values above. The 1.12.1 values are local byte hashes only because that output lacks its manifest.
