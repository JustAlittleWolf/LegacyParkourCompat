# Independent source audit: 1.13.2 → 1.14 active glide/climb alias gate

## Verdict

**Accepted as a bounded no-difference result for the assigned ordinary, dry, locally controlled player case with an already-active fall-flying flag and usable chest Elytra.** The pre-move strict threshold (`vertical velocity > -0.5`) and `1.0F` assignment match; the glide branch precedes ordinary climbing logic in both releases. The local-client validator retains the active flag because its write is server-only. Normal ladder and vine states are recognized as climbing in both versions, but that ordinary climb reset is bypassed while the active glide branch is taken.

This is not full-pair coverage. Startup acceptance, post-move/final effects, A/B profiles, and exact patch introduction remain outside this audit.

**Qualification to the draft:** its statement that incoming fall distance is preserved at or below the threshold assumes no Slow Falling effect. Both sources reset `fallDistance` to `0.0F` for nonpositive vertical velocity with Slow Falling before reaching the glide test (1.13.2 `LivingEntity.java:1481-1484`; 1.14 `LivingEntity.java:1766-1770`). This is the same behavior on both sides and does not change the no-difference verdict. The threshold result itself remains: without that earlier effect branch, `y <= -0.5` leaves the current value unchanged in this pre-move slice; `y > -0.5` sets it to `1.0F`.

## Bound input identity

Frozen claim checked: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-climb-1132-114-alias-gate/report.md`.

SHA-256 of the exact report bytes: `3ca6e02921b07fd9968d79012eee1fdaa46f362afc136585d1ccbeff0b2d1e48` (matches the assigned identity).

## Canonical source and metadata identities

All hashes below were recomputed from the published tree under `decompiled_minecraft/`; no source generation was run.

- **1.13.2, Ornithe Feather**: ready marker identifies exact version `1.13.2`, namespace `ornithe-feather`, Feather Gen2 build.2, and 2,711 sources. `decompiled_minecraft/1.13.2/ornithe-feather.ready.json` SHA-256 `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`; `ornithe-feather.verification.json` SHA-256 `5fd1e406a0ccc1c7a57e18cd7566f23332e1bf3c3322b59a4b25ae2d1f2d7bba` reports all 2,711 sources verified, zero source differences and zero missing sources. The one reported artifact difference is the regenerated intermediate remap archive; the record says published source files and raw source inputs match. `ornithe-feather.provenance.json` SHA-256 `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` names mapping inputs `feather-gen2-1.13.2+build.2-mergedv2.jar` and `feather-gen2-1.13.2+build.2.tiny`. `artifacts.sha256` SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` pins those to `317384d4faccc2939c3b14252993d31745eb42aad59f2f0ea78893cbe4e7283b` and `b3fd787448aed2c6e115d965d9edbb437ce47794ba63c7883e9014fd41262f71`. Source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` includes both cited source hashes.
- **1.14, Yarn**: `decompiled_minecraft/1.14/yarn/.decompile-provenance.json` SHA-256 `ea70a955a12f377cd3b9e8d5f346f4625b044d55da726b6bc472405ae91f4341`; its pinned inputs identify client version 1.14, Yarn `1.14+build.21-v2` artifact SHA-256 `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64`, and Intermediary `1.14` artifact SHA-256 `45e354d58c74473865054faa62a229e30f46ba2019000affdb3fb748df3fde17`. It records Vineflower 1.12.0 tool SHA-256 `1dfcfe974395734fa467ce620661c7623d05ba83670de0529b1fbd63ff548b9d` and the three source hashes below.

Exact source SHA-256 values independently recomputed:

- 1.13.2 `net/minecraft/entity/living/LivingEntity.java`: `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- 1.13.2 `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
- 1.14 `net/minecraft/entity/LivingEntity.java`: `8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6`.
- 1.14 `net/minecraft/client/network/ClientPlayerEntity.java`: `92d0f97a9293f7bd57d49af00b70f13d78fc8b2202e7ed67edcfed917e0071be`.
- 1.14 `net/minecraft/block/Blocks.java`: `62acdf407d1fa85095da0351d441b5ece10681b036505f7b9972bc21f77b0a0d`.

## Independent source trace

- **Client authority and update order:** 1.13.2 `LivingEntity.java:1478-1479` guards movement with `isLocallyControlled() || isLogicalSideForUpdatingMovement()`. `LocalClientPlayerEntity.java:489-490` returns true for `isLocallyControlled()`. The caller runs `flyingTick()` before `moveRelative()` (`LivingEntity.java:1920-1926`); validation reads flag 7 and only writes it when `!world.isClient` (`1938-1956`). In 1.14, `LivingEntity.java:1763-1764` guards travel with `canMoveVoluntarily() || isLogicalSideForUpdatingMovement()`; `ClientPlayerEntity.java:472-474` returns true for `canMoveVoluntarily()`. The caller runs `initAi()` before `travel()` (`LivingEntity.java:2202-2204`); validation likewise writes flag 7 only when `!world.isClient` (`2216-2234`). Under the scoped local-client case, both movement bodies execute, and an already-active flag is not locally cleared by these validators. Their equipment checks are respectively exact chest `Items.ELYTRA` plus `ElytraItem.canFly`, and the corresponding chest Elytra plus `ElytraItem.isUsable` test.
- **Dry glide precedence and climbing:** 1.13.2 checks `isFallFlying()` in the dry travel branch at `LivingEntity.java:1486-1489`; the glide move is at `1523`, and its `else` starts at `1537`. The ordinary climb fall-distance reset is inside that else at `1560-1564`. In 1.14 the corresponding dry branch and glide test are at `1772-1776`; the glide move is at `1802-1803`, and the ordinary branch starts at `1817`. Climb handling (including fall-distance reset) is in ordinary helper `method_18801` at `1921-1933`. The 1.13.2 `isClimbing()` includes ladder and vine at `LivingEntity.java:1046-1058`; 1.14 includes those plus scaffolding at `1216-1225`, with ladder and vine identities at `Blocks.java:474,753-755`. Thus for ordinary ladder/vine positions, active glide bypasses the climb reset in both.
- **Threshold and operation order:** 1.13.2 tests `this.velocityY > -0.5`, then assigns `this.fallDistance = 1.0F` at `LivingEntity.java:1488-1491`, before its first glide `move` at `1523`. 1.14 tests `lv5.y > -0.5`, then makes the same float assignment at `LivingEntity.java:1774-1778`, before the first glide `move` at `1803`. Both vertical components are double-valued and the literal is a strict comparison; exactly `-0.5` does not assign. The 1.14 velocity vector is obtained before the test (`1775`) and used by subsequent glide calculations, preserving the relevant pre-move order.
- **Early Slow Falling gate:** 1.13.2 checks nonpositive `velocityY` plus Slow Falling and clears fall distance before dry/glide branch selection (`1481-1484`). 1.14 checks nonpositive `getVelocity().y` plus the effect and clears it before the same branch (`1766-1770`). This is a matched qualification, not a difference.

The exact source comparison supports no independent finding for this bounded active pre-move gate. The report's launch/final/A/B exclusions and unknown patch-boundary statement are retained.
