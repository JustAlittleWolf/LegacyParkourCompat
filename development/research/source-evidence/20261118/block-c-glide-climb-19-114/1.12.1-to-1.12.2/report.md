# Glide/climb gate: 1.12.1 → 1.12.2

## Exact source identity

Both sides use the canonical local `ornithe-feather` namespace. The source directories examined were `decompiled_minecraft/1.12.1/ornithe-feather/` and `decompiled_minecraft/1.12.2/ornithe-feather/`; no generation or network lookup was performed.

- **1.12.1:** hidden `ornithe-feather/.decompile-provenance.json`, SHA-256 `0ff5246e8d29c04554cc9ecdc93a9b4abef539652325b6c0ac822851d7186a9c8`. Its pinned inputs name the official 1.12.1 client (`19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`), Feather 1.12.1 build.2 merged-v2 (`f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`), and exact 1.12.1 Feather metadata (mapping URL SHA-256 `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b`; release-metadata JSON SHA-256 `a83e8ab7e3e78fd8dfa8fdbd0216b090ef7298fb44637b79093fe663bf2d6814`). The manifest's hashes for examined source files match their current bytes.
- **1.12.2:** adjacent `ornithe-feather.provenance.json` identifies `versionId: 1.12.2`, `namespace: ornithe-feather`, and Feather build.2 artifacts. `ornithe-feather.provenance.json` SHA-256 `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`; `ornithe-feather.ready.json` SHA-256 `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a`, identifies 2,050 source files and pins `ornithe-feather.sources.sha256` to `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; the local sidecar hash matches. Its artifact-manifest pin also matches the adjacent `artifacts.sha256` (SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`). The verification sidecar SHA-256 is `7f836ebc38f06b3cffe663d0899360028e9d695a04606fea1a9ff185e84651c0`; it reports all 2,050 published source files verified with zero source differences; its one noted artifact difference is the regenerated derived remap jar, not a source-tree or raw-input difference. The adjacent success log confirms the exact-version Feather decompile completed. The 1.12.1 version directory also contains a hidden retained `.ornithe-feather.previous-*` directory; it was not used as evidence.

SHA-256 of the relevant local Java files, confirmed against each version's source manifest:

| Source file | 1.12.1 | 1.12.2 |
|---|---|---|
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | same |
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | same |
| `net/minecraft/item/ElytraItem.java` | `16e02a70181c063c0643b7dfdbe17516ad46cd38053a2d88cc3fb97400652c7c` | same |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |

The two differing whole-class hashes above do not imply a change in the bounded methods below: the compared `PlayerEntity.moveRelative`, `PlayerEntity.getEquipment`, and local-player `isLocallyControlled` bodies match across the pair. `LivingEntity`, which contains the updater, climb predicate, and glide branch, is byte-identical across the pair.

## Bounded trace

- `LocalClientPlayerEntity.isLocallyControlled()` returns `true` (1.12.1 lines 484–486; 1.12.2 lines 485–487). With ordinary player abilities (`abilities.flying == false`) and no ride, `PlayerEntity.moveRelative()` delegates to `super.moveRelative()` (1.12.1/1.12.2 lines 1386–1401); the creative-flight override is outside this trace.
- In `LivingEntity.travel()`, `flyingTick()` runs before `moveRelative(...)` (both releases lines 1842–1848). `flyingTick()` reads synchronized flag 7; if it is already set, it retains a local candidate only while airborne, not riding, and wearing a chest-slot `Items.ELYTRA` for which `ElytraItem.canFly(stack)` is true. `canFly` is exactly `stack.getDamage() < stack.getMaxDamage() - 1` (both `ElytraItem.java` lines 29–31). The updater writes flag 7 only under `!world.isClient`, so its client-side candidate does not clear or set the synchronized bit locally (both `LivingEntity.java` lines 1854–1872). `isFallFlying()` reads that bit (lines 2184–2186); `Entity.getFlag`/`setFlag` use bit `1 << index` in `SHARED_FLAGS` (both `Entity.java` lines 1826–1836).
- At a ladder/vine feet position, `isClimbing()` floors x/z and `getShape().minY`, then returns true for `Blocks.LADDER` or `Blocks.VINE` (except spectators; both `LivingEntity.java` lines 1006–1018). With flag 7 already true, local control and dry movement gates pass, and the glide `if (isFallFlying())` at `LivingEntity.moveRelative()` lines 1425–1464 is selected **before** the ordinary branch's `isClimbing()` handling at lines 1478–1517. Thus this call bypasses the ordinary climb `fallDistance = 0.0F` reset at line 1503.
- Before the first gliding `move(...)` in that call (line 1464), the strict test `velocityY > -0.5` writes `fallDistance = 1.0F` (lines 1429–1432). At exactly `-0.5` or below there is no such write. It is a fall-distance assignment, not a vertical-velocity assignment.
- If this local tick is already grounded, `flyingTick()` computes its candidate false, but the client guard still prevents a local flag write. While the synchronized flag remains true for the call, `moveRelative()` takes the same glide branch; therefore the strict pre-move fall-distance rule can still apply for that call. This is a source-order consequence, not runtime verification.

## Pair result and limits

No 1.12.1 → 1.12.2 difference was found in this already-active local glide versus ordinary ladder/vine climb-reset path. The key `LivingEntity` and `Entity` source files are byte-identical; the relevant wrapper/accessor bodies in the two differing player classes were also checked and match. This closes only the assigned bounded slice. Elytra startup, terminal glide/landing behavior, and the A/B lanes were not investigated; no full-pair coverage claim is made.


