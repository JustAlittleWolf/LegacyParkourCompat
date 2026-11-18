# Independent blind source audit: 1.12.1 → 1.12.2 glide/climb gate

## Immutable claim reviewed

Assigned report: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-climb-1121-1122-alias-gate/report.md`.

Frozen SHA-256: `2a4ca2936e1cd1d40d5d961f269c723fa6a65659034fae820fde1e5d14a5f8be`. The final read after review still matched this digest.

Bounded claim: in 1.12.1 and 1.12.2, an already-set local fall-flying flag selects the glide movement branch before ladder/vine climb handling. The client-side updater cannot locally clear/set the synchronized flag. In that branch, `velocityY > -0.5` strictly assigns `fallDistance = 1.0F` before the first `move`; the ordinary climb branch's `fallDistance = 0.0F` is skipped. The report claims no difference in this bounded path across the pair, not full-pair closure.

## Exact local source identity and integrity

Canonical namespace: `ornithe-feather`.

- 1.12.1 source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.12.1/ornithe-feather/`. The hidden `.decompile-provenance.json` SHA-256 is `0ff5246e8d29c04554cc9ecdc93a9b4abef539652325b6c0ac822851d7186a9c8`. It identifies the pinned 1.12.1 client, Feather 1.12.1 build.2 merged-v2 mapping and exact release metadata. Actual SHA-256 values for each reviewed Java file matched its manifest entry.
- 1.12.2 source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.12.2/ornithe-feather/`. Adjacent `ornithe-feather.provenance.json` SHA-256 `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` names exact `versionId: 1.12.2`, `namespace: ornithe-feather`, and Feather build.2. `ornithe-feather.ready.json` SHA-256 `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` pins the source sidecar digest `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; the sidecar's local SHA-256 matched. Actual reviewed Java file hashes matched its entries. The exact local verification sidecar SHA-256 `7f836ebc38f06b3cffe663d0899360028e9d695a04606fea1a9ff185e84651c0` records 2,050 sources verified, zero source differences, zero missing source files, zero raw-input artifact differences, and one regenerated derived remap-jar difference. This source audit consumes the published local files only; no generation or network lookup was performed.

| Java source (relative to each mapping root) | 1.12.1 SHA-256 | 1.12.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | same |
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | same |
| `net/minecraft/item/ElytraItem.java` | `16e02a70181c063c0643b7dfdbe17516ad46cd38053a2d88cc3fb97400652c7c` | same |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |

The whole `PlayerEntity` and `LocalClientPlayerEntity` files differ, so I checked the relevant bodies directly: `PlayerEntity.moveRelative`, `PlayerEntity.getEquipment`, and `LocalClientPlayerEntity.isLocallyControlled` match. Both local-player methods return `true`; both chest-equipment accessors return the armor inventory slot for `EquipmentSlot.CHEST`.

## Independent trace and verdict

1. In `LivingEntity.travel()`, speed scaling precedes `flyingTick()`, which precedes `moveRelative(...)` (both versions: `LivingEntity.java:1842–1848`). A normal local player passes the `isLocallyControlled()` gate (`LocalClientPlayerEntity.java:484–486` in 1.12.1; `:485–487` in 1.12.2). The ordinary abilities assumption excludes the creative-flight override in `PlayerEntity.moveRelative()` (`PlayerEntity.java:1386–1401` in both versions); dry movement passes the water/lava gates (`LivingEntity.java:1425–1429`).
2. `flyingTick()` starts with shared flag 7 (`LivingEntity.java:1854–1855`); while it is set, airborne, not riding, and equipped with chest-slot `Items.ELYTRA`, it retains its local candidate only if `ElytraItem.canFly(stack)` succeeds (`:1856–1868`). `canFly` is exactly `stack.getDamage() < stack.getMaxDamage() - 1` (`ElytraItem.java:29–31`); an ordinary undamaged Elytra passes. The only updater write is guarded by `!world.isClient` (`LivingEntity.java:1870–1872`). The flag read/write uses bit `1 << index` in `SHARED_FLAGS` (`Entity.java:1826–1836`), and `isFallFlying()` reads flag 7 (`LivingEntity.java:2184–2186`). Therefore the client updater's candidate calculation does not change the already-synchronized local flag before movement, including when it calculates false for grounded/riding/no-usable-Elytra state.
3. For ladder/vine at the feet, `isClimbing()` floors x, `getShape().minY`, and z, then returns true for either block (`LivingEntity.java:1006–1018`; it also allows eligible climbable trapdoors). In `moveRelative()`, `isFallFlying()` selects the glide branch before the `else` ordinary branch and its `isClimbing()` reset (`:1425–1478`, `:1499–1514`). The glide branch checks `velocityY > -0.5` and writes `fallDistance = 1.0F` at `:1429–1432`, then performs glide calculations and its first `move(...)` at `:1464`. At exactly `-0.5` or below that assignment is not taken. It assigns fall distance, not vertical velocity. The ordinary climb reset at `:1499–1504` is bypassed for this call.
4. The bounded `LivingEntity`, `Entity`, and `ElytraItem` files are byte-identical between releases. The compared player/local-player wrapper bodies also match. I found no 1.12.1 → 1.12.2 difference in this already-active local glide versus climb-reset operation.

**Verdict: ACCEPT the report's bounded no-difference claim.** This supports only the reviewed active-glide path for this exact pair; it does not prove full-pair coverage or an intermediate patch boundary. Startup eligibility, terminal glide/landing, and the A/B lanes remain outside this audit. No product implementation, tests, runtime launch, Git, wiki or network source was examined.