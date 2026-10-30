# 1.20.6 → 1.21.1 movement resource and consumer slice

## Bounded result

The source-side effect registrations, consumer defaults, and data-generation tag declarations were compared. The vanilla tag-provider declarations for Soul Speed blocks and armor enchantability are the same at both endpoints. The 1.21.1 source registers Depth Strider, Swift Sneak, and Soul Speed as attribute effects; the 1.20.6 source reads those enchantments directly in the local movement consumer (and produces Soul Speed modifiers on the logical server).

The exact default packaged resources and effective tag inheritance remain unexamined: the pinned client JARs are absent from every checked local cache/root. The source declarations below are not treated as proof of the packaged JSON contents or the effective runtime tags. No independent packaged-resource difference is accepted from this slice.

## Source identity and archive availability

Read-only canonical sources are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.20.6,1.21.1}/mojmap/`. The parent `mojmap.provenance.json`, `mojmap.ready.json`, and `mojmap.sources.sha256` digests match the identities recorded in `.native-source/20261028/frontier-c/1206-1211-local-tick/coverage.md`. Ready records identify 1.20.6 / Mojmap with client SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`, and 1.21.1 / Mojmap with client SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`.

For each source file examined, its bytes were rehashed and matched the corresponding entry in `mojmap.sources.sha256`:

| Source file | 1.20.6 SHA-256 | 1.21.1 SHA-256 |
|---|---|---|
| `net/minecraft/world/item/enchantment/Enchantments.java` | `26eeafdf667fa57b711fab3112aa9636f20a9a38cadfbff42740e66a14c38381` | `7848a2cd677aa0700434f85de37d8a0c47597741d2d1f26c8dc270f73d65b266` |
| `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| `net/minecraft/world/item/enchantment/EnchantmentHelper.java` | `e0b4c410e0aa9499d67b38f57b34467628e882be960dce6958d4bc8ef717a6b6` | `9e2f9acd5bc6a8f3b5292d720db81464954d41c9afb635f1a7c6cb31d5a2bf84` |
| `net/minecraft/world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e` | `a9a19f556bb77fc218b5b2f4831eb6b285f4398c24fa2a3519009ca8dad2db7c` |
| `net/minecraft/resources/RegistryDataLoader.java` | — | `e3dca3eaf9adf7652c7fb3830a049bb218ca92d005d23bdfd64d561f0b0cba15` |
| `net/minecraft/data/tags/VanillaBlockTagsProvider.java` | `a0ab3c2a12f8e93ae7e6231069051712cf0f6fcaf3d06e86caa0f9b2d1af310e` | `819d133e6ee8f82485b3d84fcfcfa48e4ee2824431379b93de48c7dddc7b32f0` |
| `net/minecraft/data/tags/VanillaItemTagsProvider.java` | `decfcb06faeae28065216735147a5a1f734597136dea162cbf9e00ad058e3319` | `49538ef3c7f9a704bd9e6ddc29e9226f4057dd4fc465fd5836887e7dac6fee45` |

Archive lookup was limited to read-only local paths. `.minecraft-decompile-cache` has 1.20, 1.20.3, and 1.21 directories but no 1.20.6 or 1.21.1 directory. Both ready-record archive output directories under `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready/` are absent. The canonical root build, the C: worktree build, and `C:/Users/Wolfi/.gradle/caches/fabric-loom` contain no 1.20.6 or 1.21.1 client JAR; the Fabric Loom cache is versioned only for 26.2. The local Minecraft launcher `versions` directory has 26.3 and 26.4-snapshot-3 only. No archive with either ready-record SHA was read.

## Compared source registrations, declarations, and consumers

### Defaults and ordinary consumers

- `1.20.6/net/minecraft/world/item/enchantment/EnchantmentHelper.java:168-170,184-186` exposes Swift Sneak as `level * 0.15F` and Depth Strider as an integer enchantment level. `1.20.6/net/minecraft/client/player/LocalPlayer.java:666` consumes the Swift Sneak value from base `0.3F` with a `[0,1]` clamp; the previously accepted local travel formula audit is not repeated here.
- `1.21.1/net/minecraft/world/entity/ai/attributes/Attributes.java:71-73,86-87,101-103` declares syncable defaults: `MOVEMENT_EFFICIENCY = 0`, `SNEAKING_SPEED = 0.3` with range `[0,1]`, and `WATER_MOVEMENT_EFFICIENCY = 0` with range `[0,1]`. `Player.createAttributes()` includes `SNEAKING_SPEED` at `Player.java:222-234`. `LocalPlayer.aiStep()` reads it at `LocalPlayer.java:663`.
- Built-in 1.21.1 Depth Strider applies `LevelBasedValue.perLevel(0.33333334F)` to `WATER_MOVEMENT_EFFICIENCY` (`Enchantments.java:344-368`). Swift Sneak applies `LevelBasedValue.perLevel(0.15F)` to `SNEAKING_SPEED` (`Enchantments.java:559-582`). These are default built-in source definitions; their client-visible effective values still depend on the received attribute state.

The existing coverage already records the equivalent ordinary Swift Sneak values for standard levels I–III and the Depth Strider water arithmetic finding. This slice neither recalculates nor changes either verdict.

### Soul Speed consumer path

- In 1.20.6, `LivingEntity.getBlockSpeedFactor()` returns `1.0F` when the movement-support block is in `BlockTags.SOUL_SPEED_BLOCKS` and the entity has Soul Speed (`LivingEntity.java:489-496`). `tryAddSoulSpeed()` adds a `MOVEMENT_SPEED` modifier of `0.03F * (1.0F + level * 0.35F)` when the entity has Soul Speed and stands on that tag (`LivingEntity.java:511-524`); server-only production and equipment updates are outside the equal-received-state consumer comparison.
- In 1.21.1, `LivingEntity.getBlockSpeedFactor()` interpolates the superclass block factor toward `1.0F` using `MOVEMENT_EFFICIENCY` (`LivingEntity.java:485-488`). Its `onChangedBlock(ServerLevel, ...)` runs location-changed enchantment effects (`LivingEntity.java:514-516`). The built-in Soul Speed registration has a movement-speed location effect with `perLevel(0.0405F, 0.0105F)` (`Enchantments.java:439-505`) and a `MOVEMENT_EFFICIENCY = 1.0F` location effect when movement is affected by `SOUL_SPEED_BLOCKS` (`Enchantments.java:506-522`). The client consumer consequently depends on the received movement-efficiency value rather than directly checking Soul Speed equipment in `getBlockSpeedFactor()`.

These are distinct producer/consumer arrangements. Under an equal received `MOVEMENT_SPEED` / `MOVEMENT_EFFICIENCY` state and equal active movement-tag result, the old and new client consumers can be compared without reproducing server enchantment production. This does not establish equality of transition timing or how quickly attributes update when crossing a block boundary.

### Tag declarations inspected

- Both `VanillaBlockTagsProvider` definitions add exactly `SOUL_SAND` and `SOUL_SOIL` to `SOUL_SPEED_BLOCKS` (1.20.6 line 687; 1.21.1 line 732).
- Both `VanillaItemTagsProvider` definitions populate `FOOT_ARMOR` with leather, chainmail, golden, iron, diamond, and netherite boots, and `LEG_ARMOR` with the corresponding six leggings (1.20.6 lines 286-289; 1.21.1 lines 284-287). Both then compose `FOOT_ARMOR_ENCHANTABLE` from `FOOT_ARMOR` and `LEG_ARMOR_ENCHANTABLE` from `LEG_ARMOR` (both line 362-363). The built-in definitions use those enchantability keys for Depth Strider/Soul Speed and Swift Sneak, respectively. Thus these source-declared default inheritance paths resolve to the same six vanilla item IDs on each side.
- The provider declarations and their checked source inheritance are equal. They do not prove the packaged JSON bytes, pack overlay order, or effective tags after datapack reload.

## Dynamic inputs and remaining gates

The old Soul Speed block-factor consumer reads the active client block tag directly. In 1.21.1, the location-effect producer evaluates that tag server-side and the client block-factor consumer reads the resulting syncable attribute. The client attribute values are also dynamic: equipment and loaded enchantment definitions determine them, and transitions determine when they are refreshed. Under the requested equal-received-state assumption, the relevant comparison inputs are the effective `SOUL_SPEED_BLOCKS` result where the old client reads it, and the same entity/tick's received `MOVEMENT_SPEED`, `MOVEMENT_EFFICIENCY`, `SNEAKING_SPEED`, and `WATER_MOVEMENT_EFFICIENCY` values. Default source values alone do not close those state/timing dependencies.

Custom or server-selected datapacks/resource packs can change tag membership and, in 1.21.1, enchantment definitions/effects: `RegistryDataLoader.java:96` loads the `ENCHANTMENT` registry via `Enchantment.DIRECT_CODEC`. Such values are not static movement invariants. The scoped claim can cover the vanilla default pack only after the exact pinned client archives are available and their resource payloads/effective tag references are compared. It cannot claim parity for arbitrary datapack overlays or unequal received attribute/tag state. This is a bounded dynamic-resource limitation, not a blanket exclusion of block tags.

**Next exact action:** make the 1.20.6 JAR whose SHA-256 is `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2` and 1.21.1 JAR whose SHA-256 is `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99` available in an authorized read-only cache, then inspect the corresponding default enchantment/tag resources and tag-reference inheritance, checking each archive hash before reading it. No download, source generation, tests, build, game launch, or decompilation was performed.
