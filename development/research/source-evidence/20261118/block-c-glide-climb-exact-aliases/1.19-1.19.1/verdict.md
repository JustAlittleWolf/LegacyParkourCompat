# Independent frozen review: 1.19 → 1.19.1 glide/climbable gate

## Verdict

**Source claim accepted; active implementation fidelity rejected for the bounded witness.** The frozen report correctly finds no 1.19-to-1.19.1 difference in the dry, already-active Elytra branch of `LivingEntity.travel(Vec3)`: both releases use the strict signed double comparison `deltaMovement.y > -0.5`, assign `fallDistance = 1.0F` before glide-vector updates and `move`, and do not gate that assignment on climbability. An ordinary, default chest Elytra with a ladder or vine at the feet reaches this branch when fall-flying is already active and the enclosing AI/local-control gate is true.

The current product path cannot be treated as implementing that operation for this witness. The v1.21 limiter-stage change, selected for a 1.19 profile by closest-later resolution, excludes `player.onClimbable()`. Consequently it leaves the native limiter at `UPDATE_FALL_FLYING` and does not call the v1.19 `GlideFallDistance` hook at `TRAVEL_FALL_FLYING`. Independently, that v1.19 hook also has `!player.onClimbable()` around its 1.0F write. The strict double threshold and float literal match, but both active climbability gates suppress the historical pre-move write for ladder and vine feet. This is a narrow implementation discrepancy; it does not change the accepted no-difference result between the two source releases.

## Frozen input and source identity

Reviewed input: `c-glide-climb-119-1191-alias-gate/report.md`, SHA-256 `62eeb88e913f8d4aec58eb747128df4e0231d06aacf61b7425aa385ddfe1afd9` (verified).

Exact source family: Mojmap. Readiness markers identify `1.19` and `1.19.1`, both `status: ready`, with the requested `mojmap` mapping. Marker and provenance hashes below match the frozen report and the local bytes. I independently checked each source manifest against all files in its Mojmap tree: 1.19, 4,430 files / 21,697,172 bytes / 0 mismatches; 1.19.1, 4,480 files / 21,921,512 bytes / 0 mismatches. Both `mojmap/.decompile-provenance.json` files are absent, so this verdict makes no hidden-provenance claim. The provenance records disclose the package-private Mojmap access correction and Vineflower processed-twice notices, with no decompiler errors logged.

| Release | Readiness marker SHA-256 | Provenance SHA-256 | `LivingEntity.java` SHA-256 | `BlockTagsProvider.java` SHA-256 |
|---|---|---|---|---|
| 1.19 | `b00a68db8600ec8cdc3917357aff59eb0537be6f382e621e5ed3eeb900898b88` | `833cf50d6eab0f24475dcc11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a` | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` | `7386e263290f48276ad9ed1a099cb4cb630529ebac22e9dfd94d748007411ea0` |
| 1.19.1 | `c7b5373078eb777895a4f4b10d8c99eb289cb14fa1835004c1eb4563b6a8a690` | `de0de05299c59d3b05d96e744b27f5a5cef377880ae4a7b402337aa0ffe00245` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` | `7386e263290f48276ad9ed1a099cb4cb630529ebac22e9dfd94d748007411ea0` |

The readiness marker also binds the source, artifact and diagnostics manifests; their hashes agree with the report. The exact manifests are `decompiled_minecraft/<version>/mojmap.sources.sha256`, `artifacts.sha256`, and `movement-diagnostics.txt`.

## Paired source trace

For both releases, `decompiled_minecraft/<version>/mojmap/net/minecraft/world/entity/LivingEntity.java`:

- `travel(Vec3)`, lines 2042–2045, enters under `isEffectiveAi() || isControlledByLocalInstance()`.
- Lines 2107–2111 show the dry-branch `else if (this.isFallFlying())`, read of `getDeltaMovement()` into `Vec3`, strict `$$13.y > -0.5`, and direct `this.fallDistance = 1.0F` assignment.
- Lines 2113–2136 continue glide-vector calculations, damp and set delta movement, then call `move(MoverType.SELF, this.getDeltaMovement())`. The assignment precedes that move call in both releases.
- The feet-state predicate `onClimbable()`, lines 1462–1477, checks `BlockTags.CLIMBABLE`; `BlockTagsProvider.java`, lines 607–619, includes `Blocks.LADDER` and `Blocks.VINE` in that tag in both releases.
- `handleRelativeFrictionAndCalculateMovement`, lines 2194–2197, and `handleOnClimbable`, lines 2222–2234, form the separate ordinary friction path whose climbable branch resets fall distance. The fall-flying branch reaches its own move at line 2136 without entering that path. No claim is made about `move` internals or the eventual end-of-tick value.

There is no source difference in these compared members and gates. This verdict closes only this bounded operation and pair, not full-pair inventory, intermediate patch boundaries, upstream glide activation or final tick state.

## Active implementation trace

For the ordinary default chest Elytra witness, `HistoricalElytra.applies` in `src/client/java/me/wolfii/legacyparkourcompat/change/common/HistoricalElytra.java:15–22` accepts selected profiles from 1.9 onward, the chest `Items.ELYTRA`, `canGlideUsing`, and the default Elytra max-damage value.

The climbability query is restored for the historical witness despite the current 26.2 native glide-through gate. `src/client/java/me/wolfii/legacyparkourcompat/mixin/LivingEntityMixin.java:216–223` wraps `isFallFlying()` inside `onClimbable()`. The closest-later hook `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_5/GlideClimbableExemption.java:23–37` returns false for a vine in selected profiles at or after 1.9, so the native early return is suppressed for a vine; a ladder is not in current `CAN_GLIDE_THROUGH`. Current anchors are `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java:1722–1738` and `VanillaBlockTagsProvider.java:252–255,293–299`: both blocks are `CLIMBABLE`, while only vines in the listed family enter `CAN_GLIDE_THROUGH`. Therefore the historical profile's active `player.onClimbable()` is true for these two witnesses.

`src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java:28–46` selects the closest change whose `emulates` version is not older than the selected profile; the only local `GlideFallDistanceLimiterStageHook` implementation is `change/v1_21/GlideFallDistanceLimiterStage.java`. Its `eligible` method explicitly returns false for `player.onClimbable()` (`:34–48`), and `atStage` therefore chooses `UPDATE_FALL_FLYING` (`:25–28`). `LivingEntityMixin.java:660–684` runs the native limiter at that update stage and offers the v1.19 hook only inside the travel-stage callback. With this witness the latter callback is not selected.

Even considered alone, `src/client/java/me/wolfii/legacyparkourcompat/change/v1_19/GlideFallDistance.java:37–43` writes `1.0F` only when `!player.onClimbable()` and `player.getDeltaMovement().y > -0.5`. Its numeric operator, signed double operand, strictness, and float literal match the reviewed source; the added climbability gate does not.

## Scope limits

No profile activation, glide-start behavior, post-move collision behavior, subsequent tick writers or end-of-tick value is adjudicated. No runtime, test, build, generation, network or Git operation was performed.
