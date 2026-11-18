# 1.18.2 → 1.19.0: ability flight while fall-flying

## Result

For the bounded case—local player, ordinary dry airborne state, not a passenger, creative flight authorized and enabled, Elytra equipped in the chest slot, shared flag 7 already true—no movement-source difference was found between Minecraft 1.18.2 and 1.19.0. Both versions perform the same glide travel and actual `move` before `Player.travel` applies the ability-flight postprocessing. That postprocessing separately scales vertical velocity, resets fall distance, and clears glide flag 7 in that order.

The decompiler source directory labels the exact 1.19.0 release as `1.19`; the provenance and ready records both identify `versionId`/`versionMetadataId` as `1.19`. Mapping family is Mojmap on both sides.

## Reachable tick path and behavior

In both versions, `LocalPlayer.aiStep` calls `super.aiStep` (1.18.2 `LocalPlayer.java:803`; 1.19 `LocalPlayer.java:887`). `Player.aiStep` calls `super.aiStep` (1.18.2 `Player.java:508`; 1.19 `Player.java:516`), and `LivingEntity.aiStep` invokes `travel(new Vec3(this.xxa, this.yya, this.zza))` (1.18.2 `LivingEntity.java:2552`; 1.19 `LivingEntity.java:2585`; see exact source and manifest hashes below). The local player is therefore dispatched through `Player.travel` during its movement tick.

The local Elytra-start input is gated by `!getAbilities().flying` before checking chest-slot Elytra, its enabled state, and `tryToStartFallFlying` (1.18.2 `LocalPlayer.java:740-744`; 1.19 `LocalPlayer.java:824-828`). This scope begins with flag 7 already active and creative flight lawful and enabled; it does not claim the ordinary input path starts both modes in one step. `Player.tryToStartFallFlying` requires airborne, not already fall-flying, not in water, no levitation, and an enabled chest Elytra (1.18.2 `Player.java:1555-1565`; 1.19 `Player.java:1592-1602`). `startFallFlying` sets shared flag 7 true in both (1.18.2 `Player.java:1567-1569`; 1.19 `Player.java:1604-1606`).

On the ordinary dry airborne path, `Player.travel` reaches `if (abilities.flying && !isPassenger())` in both sources (1.18.2 `Player.java:1437-1449`; 1.19 `Player.java:1474-1486`). It saves the pre-super vertical component, temporarily sets flying speed to `abilities.getFlyingSpeed() * (isSprinting() ? 2 : 1)`, and calls `super.travel`. With water/lava branches excluded by the stated dry condition and flag 7 active, `LivingEntity.travel` enters its `isFallFlying()` branch in both (1.18.2 `LivingEntity.java:2074-2116`; 1.19 `LivingEntity.java:2107-2149`). That branch computes the same glide velocity, applies `0.99F, 0.98F, 0.99F` damping, and calls `move(MoverType.SELF, this.getDeltaMovement())` (1.18.2 lines 2080-2103; 1.19 lines 2113-2136): the position move is real and precedes the Player-level override.

After that move returns, both `Player.travel` implementations get the resulting delta movement, replace only its Y with the saved pre-super Y multiplied by `0.6`, and retain the glide-computed X/Z. They then restore the prior `flyingSpeed`, call `resetFallDistance()`, and directly call `setSharedFlag(7, false)` (1.18.2 `Player.java:1437-1446`; 1.19 `Player.java:1474-1483`). Thus the glide-generated vertical velocity is discarded after the glide displacement has already been applied. The subsequent flag clear is not the cause of that displacement; it ends fall-flying state for later movement/state reads. Fall distance is independent of flag 7: the glide branch may set `fallDistance = 1.0F` before the move when pre-glide Y velocity is greater than `-0.5`, but the later ability-flight `resetFallDistance()` resets it after the move. On the local client, the glide branch's own landing-only clear is gated by `!level.isClientSide`; in this airborne case it does not supply the flag clear. The unconditional Player ability-flight clear does.

The operations, gates, arithmetic, order, and resulting movement-state distinction described above match across the compared source slices. No pair difference is submitted for this bounded operation.

## Source readiness and immutable evidence

Canonical read-only source roots: `decompiled_minecraft/1.18.2/mojmap/` and `decompiled_minecraft/1.19/mojmap/`.

| Release | Provenance SHA-256 | Ready SHA-256 | Source-manifest SHA-256 (also declared by ready marker) |
| --- | --- | --- | --- |
| 1.18.2 | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |
| 1.19.0 (`versionId: 1.19`) | `833cf50d6eab0f24475dcc11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a` | `b00a68db8600ec8cdc3917357aff59eb0537be6f382e621e5ed3eeb900898b88` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` |

Both ready markers say `status: ready`, `decompiler: Vineflower`, Java runtime 25, and pin exact release metadata. Diagnostics confirm exact metadata ID and required movement source anchors. The 1.19 provenance notes a corrected package-private access on an unrelated client `OptionInstance$ValueSet` class and processed-twice notices for synthetic/lambda methods; it reports no decompiler errors. Neither note concerns the reviewed movement methods.

### Compared source files (SHA-256)

| Source | SHA-256 |
| --- | --- |
| 1.18.2 `net/minecraft/world/entity/player/Player.java` | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` |
| 1.18.2 `net/minecraft/world/entity/LivingEntity.java` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |
| 1.18.2 `net/minecraft/client/player/LocalPlayer.java` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |
| 1.19 `net/minecraft/world/entity/player/Player.java` | `4b5d3d71ac1c4b64efdeb1ad86a4b6f84afffe210e272850e620339430942de1` |
| 1.19 `net/minecraft/world/entity/LivingEntity.java` | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` |
| 1.19 `net/minecraft/client/player/LocalPlayer.java` | `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805` |

The three source-file hashes per release match their entries in that release's pinned source manifest.

