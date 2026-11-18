# Independent source audit verdict: 1.18.2 → 1.19.0

**Verdict: ACCEPT — bounded no-difference claim confirmed.** The reviewed claim is limited to the local player on an ordinary dry, airborne, nonpassenger tick, with authorized creative flight enabled, an enabled Elytra in the chest slot, and shared flag 7 already true. This verdict does not establish complete pair coverage or an exact intermediate patch boundary.

## Audited behavior

The reachable tick dispatch is the same: `LocalPlayer.aiStep` calls `Player.aiStep`, which delegates to `LivingEntity.aiStep`; `LivingEntity.aiStep` updates fall-flying state and then dispatches `travel(new Vec3(...))`. For the stated client case, `updateFallFlying` does not itself clear flag 7: its final write is gated by `!level.isClientSide`.

Both `Player.travel` methods take the same `abilities.flying && !isPassenger()` branch. They save the incoming Y velocity and flying speed, temporarily set the flying speed, and call `super.travel`. With the Elytra glide flag active and dry conditions, `LivingEntity.travel` runs its same fall-flying calculations and calls `move(MoverType.SELF, getDeltaMovement())` before returning. The actual glide displacement therefore occurs before the Player-level postprocessing.

After that move, both releases retain glide-computed X/Z velocity, replace Y with the saved incoming Y times `0.6`, restore flying speed, call `resetFallDistance()` (which assigns `0.0F`), and then directly clear shared flag 7. The glide branch can first set fall distance to `1.0F` when incoming Y is greater than `-0.5`; the later reset follows the completed move and overwrites it. That post-move fall-distance reset is separate from the flag-7 clear. The glide branch's own landing clear is gated by `onGround && !level.isClientSide`, so it is not the clear in this local airborne case.

The paired source slices agree on branch gates, operations, order, arithmetic, and state writes. No source difference is supported for this bounded operation.

## Pinned inputs and evidence

The original source report was hashed before it was opened; its SHA-256 is `61facb6e93a221ebf37d09eca6ee30293d117646647843db3a71cf12c16595a6`.

Canonical roots: `decompiled_minecraft/1.18.2/mojmap/` and `decompiled_minecraft/1.19/mojmap/`. Ready records identify exact `versionId` values 1.18.2 and 1.19 and `mapping: mojmap`; the latter is the 1.19.0 release label used in the report. Readiness is `ready` on both sides, and the declared source-manifest hashes match the actual manifests.

| Input | SHA-256 |
| --- | --- |
| 1.18.2 `mojmap.ready.json` | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` |
| 1.18.2 `mojmap.provenance.json` | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` |
| 1.18.2 `mojmap.sources.sha256` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |
| 1.18.2 `artifacts.sha256` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` |
| 1.19 `mojmap.ready.json` | `b00a68db8600ec8cdc3917357aff59eb0537be6f382e621e5ed3eeb900898b88` |
| 1.19 `mojmap.provenance.json` | `833cf50d6eab0f24475dcc11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a` |
| 1.19 `mojmap.sources.sha256` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` |
| 1.19 `artifacts.sha256` | `eee4a52f029446e075ff10ef761cea9a621b40df6e66240b6323d1596ca60ebf` |

Compared source files (each digest independently recalculated and matched to its source-manifest entry):

| Release/source | SHA-256 |
| --- | --- |
| 1.18.2 `net/minecraft/world/entity/player/Player.java` | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` |
| 1.18.2 `net/minecraft/world/entity/LivingEntity.java` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |
| 1.18.2 `net/minecraft/client/player/LocalPlayer.java` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |
| 1.18.2 `net/minecraft/world/entity/Entity.java` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |
| 1.19 `net/minecraft/world/entity/player/Player.java` | `4b5d3d71ac1c4b64efdeb1ad86a4b6f84afffe210e272850e620339430942de1` |
| 1.19 `net/minecraft/world/entity/LivingEntity.java` | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` |
| 1.19 `net/minecraft/client/player/LocalPlayer.java` | `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805` |
| 1.19 `net/minecraft/world/entity/Entity.java` | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` |

## Claim identity and signature

Audited original: `c-mid-1182-119-ability-flight-glide-clear-source/report.md`, SHA-256 `61facb6e93a221ebf37d09eca6ee30293d117646647843db3a71cf12c16595a6`.

Signed: `/root/c_mid_1182_119_ability_flight_glide_clear_audit`, independent exact-source reviewer, 2026-11-18.
