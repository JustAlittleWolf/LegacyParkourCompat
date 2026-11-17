# 1.16.4 → 1.16.5 ordinary-player source coverage (partial)

## Source identity

Both endpoints use Mojmap. Input readiness is recorded in `build/source-inputs-115-116-20261117/README.md`; its inventory reports complete, verified source maps with 3,523 Java files per endpoint and zero source-hash mismatches.

- **1.16.4:** `decompiled_minecraft/1.16.4/mojmap/`; its mapping-local `decompiled_minecraft/1.16.4/mojmap/.decompile-provenance.json#sources` map is bound by provenance SHA-256 `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`. The provenance identifies the exact `1.16.4.json` metadata and Mojmap artifact (mapping identity SHA-256 `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`).
- **1.16.5:** `decompiled_minecraft/1.16.5/mojmap/`; root source manifest `decompiled_minecraft/1.16.5/mojmap.sources.sha256` SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`; provenance `decompiled_minecraft/1.16.5/mojmap.provenance.json` SHA-256 `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d`, with `versionId=1.16.5`, `namespace=mojmap`, and mapping identity SHA-256 `7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c`.

The source map comparison used the 1.16.4 `sources` object in the hidden mapping-local provenance, not an assumed root `.sources.sha256` file. I verified the cited changed and movement-path file hashes against their source bindings and disk bytes.

## Ordered movement slice checked

The compared source hashes for the following player path are identical across these exact releases. Since the entire listed Java files are byte-identical, the referenced operations and their override/delegation structure are unchanged:

1. Client tick entry `Minecraft.tick()` (`net/minecraft/client/Minecraft.java:1376`) → `LocalPlayer.tick()` (`net/minecraft/client/player/LocalPlayer.java:184`), `LocalPlayer.aiStep()` (`:627`), and local movement override `LocalPlayer.move(MoverType, Vec3)` (`:863`).
2. Player-specific `Player.tick()` (`net/minecraft/world/entity/player/Player.java:207`) and `Player.aiStep()` (`:483`); jump dispatch passes through `Player.jumpFromGround()` (`:1376`) to its superclass, and travel passes through `Player.travel(Vec3)` (`:1387`) and its player-specific gates before `LivingEntity.travel(Vec3)` (`net/minecraft/world/entity/LivingEntity.java:1914`). `LivingEntity.aiStep()` (`:2368`) contains the ordinary jump/travel call sites (`:2440`, `:2459`).
3. Travel reaches `Entity.move(MoverType, Vec3)` (`net/minecraft/world/entity/Entity.java:485`) and its collision resolution (`Entity.collide(Vec3)`, `:668`); the selected shape sources include `CollisionGetter.getEntityCollisions` (`net/minecraft/world/level/CollisionGetter.java:52`) and `VoxelShape` (`net/minecraft/world/phys/shapes/VoxelShape.java`).
4. External velocity input via `ClientPacketListener.handleSetEntityMotion` (`net/minecraft/client/multiplayer/ClientPacketListener.java:563`) is also unchanged. `ServerEntity` and `Player` sender files are byte-identical in this pair.

| Source file | 1.16.4 SHA-256 | 1.16.5 SHA-256 |
|---|---|---|
| `net/minecraft/client/Minecraft.java` | `3fc48eb93d2dc001496c11b66071db939cdaa3f1ff7c4e5b2b391e25a715facc` | same |
| `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | same |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | same |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | same |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | same |
| `net/minecraft/world/level/CollisionGetter.java` | `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac` | same |
| `net/minecraft/world/phys/shapes/VoxelShape.java` | `6747647a5b94e69747340d432932b77ce5ad701c8f06e141e15b3dd68f8c3bde` | same |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b` | same |
| `net/minecraft/world/effect/MobEffectInstance.java` | `4999d12cd468e402506f067dbd8694b1c8125dd9131e4f8a2fcc1848f616f180` | same |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e` | same |
| `net/minecraft/server/level/ServerEntity.java` | `be366b94da3225bc71859138ab702b1f3b2cb4322d241e67737693487c3c81de` | same |

## Complete Java path/hash delta inventory

I compared the two bound source maps: 3,523 paths each; no additions or removals; four source hashes differ. I inspected the changed bodies and found no player movement delta in this slice:

| Source file | 1.16.4 SHA-256 | 1.16.5 SHA-256 | Disposition |
|---|---|---|---|
| `net/minecraft/DetectedVersion.java` | `bfab32ecc94e80eedc7ea9ee99567a114a85cdead5e1f8a41e2e3c27043c18e0` | `04f36b764e5a4d6d918b5928c418f0a27459ac64aca7c805dbf06aabd3397b6f` | Release name, world version, and release target metadata only. |
| `net/minecraft/core/Direction.java` | `f4fc12510d85d033f5c748c1b95f071d672dad936830d588a9782f45ca6abb39` | `6a41ecf2ae93c70f67a58e2bbe8806605645b55f2bade113173c2bde9ab17751` | Adds `Direction.Plane.getRandomAxis(Random)` (`1.16.5:428`), returning a random axis from the plane's stored axis array. |
| `net/minecraft/world/entity/ai/village/poi/PoiSection.java` | `faebffe45bdf074650a045bd6cfb01133b0c4d9007342e77c2e845d2cdc5dec8` | `3197833be1a6eabf93ea2b566d089fe88ca263d65241126865f3051204da850c` | `add`, `remove`, and `refresh` change POI mismatch handling/refresh bookkeeping; no player movement operation. |
| `net/minecraft/world/level/block/BaseFireBlock.java` | `16e2b7e971ead9be15b29ba1bbe3d230a1599dfaa7a31a6e0e5677783d2e9a79` | `97520e4da57172cf1633e7a09166d611fa4794304d887f053eec54e53c34b23f` | `isPortal` (`:170`) now uses the counter-clockwise direction's axis for horizontal input, but selects a random horizontal axis for vertical input (`1.16.5:182–193`). The two direct player-use callers, `FlintAndSteelItem.useOn` and `FireChargeItem.useOn`, pass `UseOnContext.getHorizontalDirection()` (`:38`, `:30` respectively) at both endpoints, so this branch does not change their ordinary player input path. The remaining vanilla `canBePlacedAt` caller is `DispenseItemBehavior` (`1.16.5:403`), a dispenser/non-player producer. This changed source affects portal-fire placement eligibility and resulting world blocks, outside the movement slice and non-player physics scope; no player movement finding is claimed from it. The added random-axis helper is only called in this vertical-input branch. |

## Coverage and open gates

Bounded result: **NO MOVEMENT DELTA FOUND in the compared client player tick/travel/move/collision and velocity-receive files; PARTIAL pair coverage.** The only potentially adjacent changed code in this inventory is portal-fire placement eligibility, with the vertical case reachable from the vanilla dispenser caller rather than the two vanilla player-use callers. The examined files do not establish every tick dependency or terminal slice. Fluid contact/travel, full block shape/provider and registration closure, tag/resource membership, all player-response callbacks, and independent full-pair inventory/audit remain open. No resources were extracted. No runtime or trajectory claim is made, and this report does not freeze the pair or establish an exact movement patch boundary.
