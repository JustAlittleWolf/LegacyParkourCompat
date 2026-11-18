# 1.11.2 → 1.12 Feather: Llama passenger Y placement

## Result and boundary

No Llama passenger-position change was found in the compared source slices. The `LlamaEntity.updateRiderPositon` body and its `getMountHeight` override are identical in the local 1.11.2 and 1.12 Feather trees. This is a bounded no-difference result, not full-pair coverage or an accepted exact-source finding: the 1.11.2 tree has no provenance manifest or local pinned input/artifact cache, so its publication identity cannot be verified from this checkout.

Ordinary adult Llama constructor sizing is `setSize(0.9F, 1.87F)` in both files. For a Player, the direct Y assignment is:

`rider.y = llama.y + llama.height * 0.67 + rider.getRideHeight()`

`height` is a float field set from `1.87F`; the multiplier `0.67` is a double literal, so the float height is promoted to double for multiplication. `PlayerEntity.getRideHeight()` overrides the Entity default and returns `-0.35` (double). Thus default-adult Y is `llama.y + (double)1.87F * 0.67 + (-0.35)`, approximately `llama.y + 0.902900003194809`. The additions are left-associative and remain double operations.

The same Llama callback computes `f = cos(bodyYaw * (float)(PI / 180.0))`, then `g = sin(bodyYaw * (float)(PI / 180.0))`, each stored as float; X is `llama.x + 0.3F * g`, Z is `llama.z - 0.3F * f`. Its local `float h = 0.3F` is unused in the shown position expression. The `hasPassenger(rider)` guard encloses the operation. Llama overrides `HorseBaseEntity.updateRiderPositon` without calling `super`, so the HorseBase angry-animation rider reposition does not affect this Llama path.

## Tick/callback path and authority

In both versions, `World` selects `entity.rideTick()` for an in-chunk riding entity; `Entity.rideTick()` zeros its velocity, ticks it, then if still riding dispatches `mount.updateRiderPositon(this)`. `PlayerEntity.rideTick()` delegates to that base path unless the server-side sneaking dismount guard fires (`!world.isClient && isSneaking() && isRiding()`). The client `LocalClientPlayerEntity.rideTick()` delegates to `PlayerEntity` and then supplies boat input. The Llama position write therefore occurs from the rider's local tick through virtual dispatch to the mount's Llama override. The generic world/entity riding path itself is not client-guarded; keep any historical emulation client-only per project invariant. This comparison does not imply server historical physics.

## Adjacent carrier bounds

The generic `Entity.updateRiderPositon` placement uses `(mount.x, mount.y + mount.getMountHeight() + rider.getRideHeight(), mount.z)` under the same passenger-membership check. `MinecartEntity` returns mount height `0.0` and uses that base placement. `HorseBaseEntity` adds an angry-animation reposition after its base placement; Llama bypasses it. `BoatEntity` has a separate multi-passenger callback whose Y expression first casts `(removed ? 0.01F : getMountHeight()) + rider.getRideHeight()` to float. These callbacks are not Llama deltas. Boat/minecart movement and other vehicle physics are excluded; no vehicle-physics finding is claimed.

## Exact local evidence

Paths below are relative to `decompiled_minecraft/<version>/ornithe-feather/`.

| Slice | 1.11.2 | 1.12 |
|---|---:|---:|
| `net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java` constructor and placement | 60–62, 144–155 | 60–62, 144–155 |
| `net/minecraft/entity/Entity.java` riding dispatch and base placement | 1556–1586 | 1582–1612 |
| `net/minecraft/entity/living/player/PlayerEntity.java` rider tick; `getRideHeight` | 379–398, 947–950 | 375–394, 942–945 |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` rider tick | 798–806 | 815–823 |
| `net/minecraft/world/World.java` riding tick selection | 1418–1425 | 1427–1434 |
| `net/minecraft/entity/living/mob/passive/animal/HorseBaseEntity.java` alternate rider callback | 939–956 | 945–962 |
| `net/minecraft/entity/vehicle/BoatEntity.java` alternate rider callback | 549–574 | 581–606 |
| `net/minecraft/entity/vehicle/MinecartEntity.java` mount height | 136–138 | 136–138 |

The two `MinecartEntity.java` files have identical SHA-256 (`05b022c3c63e34035084b3369f33f9fc679db537f8c74aba7a1ae535ef71ba5a`).

### Source bytes and publication identity

1.12 has `decompiled_minecraft/1.12/ornithe-feather/.decompile-provenance.json`. Each inspected file's current SHA-256 matches its manifest entry:

- `Entity.java` `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`
- `PlayerEntity.java` `6a9d5f420b13625195b149c0d9468cf1284d5eb7dc1b2a46cf01b952a27ee063`
- `LlamaEntity.java` `487c7209a69391f1a19dce5fbdf34d609d2e9bc53c47b04a225e9c0d0c93cf7e`
- `HorseBaseEntity.java` `63a3ee9dfa5e6d8e7313d0427a7d7f0a9678ce71f7b9e9425baeef40ad552acf`
- `LocalClientPlayerEntity.java` `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1`
- `World.java` `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8`
- `BoatEntity.java` `9fb612de08de935a71f299410dc201abd7e9b522f60b0c9e9f982d3e06f59747`
- `MinecartEntity.java` `05b022c3c63e34035084b3369f33f9fc679db537f8c74aba7a1ae535ef71ba5a`

That manifest pins exact Minecraft 1.12 client bytes `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`, Feather `1.12+build.2` merged artifact bytes `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`, and Feather metadata bytes `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4`.

1.11.2 local source-file SHA-256 values (bytes observed; no publication manifest to bind them to a pinned decompilation):

- `Entity.java` `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`
- `PlayerEntity.java` `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`
- `LlamaEntity.java` `1577cf77a5c1d0e47dde0e13327dc333fcd65aa86c7e9073f49b2c678f0257b1`
- `HorseBaseEntity.java` `0d7d737682af149548acf7e2e202ed666e0865acb12688dc2a963d863a958143`
- `LocalClientPlayerEntity.java` `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`
- `World.java` `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58`
- `BoatEntity.java` `0156b9cf2d028f53f2bc4c2ab6610d602e656e6f1dd33525b316b86588057f4d`
- `MinecartEntity.java` `05b022c3c63e34035084b3369f33f9fc679db537f8c74aba7a1ae535ef71ba5a`

No `decompiled_minecraft/1.11.2/ornithe-feather/.decompile-provenance.json` or `.minecraft-decompile-cache/1.11.2/` exists in this checkout. Exact 1.11.2 raw-client and Feather artifact pins/readiness are therefore unresolved. Do not treat the directory label or matching code as provenance.

