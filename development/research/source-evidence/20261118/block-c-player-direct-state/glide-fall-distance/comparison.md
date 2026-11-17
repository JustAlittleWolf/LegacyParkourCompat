# Elytra fall-distance source comparison

## Scope and source set

Blind source-only comparison of exact Mojmap decompilations in the primary checkout's canonical `decompiled_minecraft/` tree: 1.18.2 against 1.19, 1.19.1, 1.19.2, 1.19.3 and 1.19.4. The sources were read-only. No wiki, product source, Git history, backlog, builds, tests or game launches were consulted.

The reachable witness is a locally controlled player already in the ordinary fall-flying state while airborne in dry air, with no Slow Falling or Levitation. The classic `Items.ELYTRA` item is present in each examined release (see `net/minecraft/world/item/Items.java:778` in 1.18.2, `:809` in 1.19/1.19.1/1.19.2, `:767` in 1.19.3 and `:790` in 1.19.4). Activation/input, effect/gravity paths and flight equations are not assessed here. The witness isolates the state write gated by `isFallFlying()` and vertical delta; the assignment itself has no water/effect check.

## Findings

`Entity.checkFallDamage` is the ordinary accumulator in every examined release. If the resolved move is not grounded and its vertical displacement `dy` is negative, it performs `fallDistance -= (float) dy`; on ground it invokes block fall handling for positive distance and resets the value. `Entity.resetFallDistance()` writes `0.0F`.

In 1.18.2 and the 1.19, 1.19.1 and 1.19.2 sources, the locally controlled `LivingEntity.travel` glide branch first captures current delta movement, then, when `deltaMovement.y > -0.5`, unconditionally assigns `fallDistance = 1.0F`. The write precedes look-vector reads and the remaining glide velocity calculation. When `y <= -0.5`, this branch leaves the current distance alone. Thus, during the dry ordinary Elytra witness, a subsequent travel tick with `y > -0.5` replaces any previously accumulated distance with exactly `1.0F`; the rule is decoupled from the negative-displacement accumulation in `Entity.checkFallDamage`.

In 1.19.3 and 1.19.4, `LivingEntity.travel` instead calls `Entity.checkSlowFallDistance()` at the same start of the glide branch, before capturing the delta vector and doing glide math. The helper caps `fallDistance` to `1.0F` only when `deltaMovement.y > -0.5` **and** the existing distance is greater than `1.0F`. It preserves an existing value from 0 through 1, unlike the earlier unconditional assignment. Subsequent negative resolved displacement still accumulates by the same `Entity.checkFallDamage` operation. The source-level helper reads `deltaMovement.y()` directly in 1.19.3 and `getDeltaMovement().y()` in 1.19.4; both are gated by the same strict `>-0.5` and `fallDistance > 1.0F` comparisons.

Therefore the ordinary fall-distance state difference begins somewhere after 1.19.2 and by 1.19.3; this endpoint set does not establish the exact intervening patch introduction. Accumulation and zero-reset are unchanged across the inspected releases.

## Source references

All paths below are relative to `decompiled_minecraft/<version>/mojmap/`.

| Release | `Entity.checkFallDamage` and reset | Glide travel state operation |
| --- | --- | --- |
| 1.18.2 | `net/minecraft/world/entity/Entity.java:979-991`, reset at `2114-2116` | `net/minecraft/world/entity/LivingEntity.java:2009-2015, 2074-2080`; assignment at `2077` |
| 1.19 | `net/minecraft/world/entity/Entity.java:991-1003`, reset at `2138-2140` | `net/minecraft/world/entity/LivingEntity.java:2042-2048, 2107-2113`; assignment at `2110` |
| 1.19.1 | `net/minecraft/world/entity/Entity.java:991-1003`, reset at `2138-2140` | `net/minecraft/world/entity/LivingEntity.java:2042-2048, 2107-2113`; assignment at `2110` |
| 1.19.2 | `net/minecraft/world/entity/Entity.java:991-1003`, reset at `2138-2140` | `net/minecraft/world/entity/LivingEntity.java:2042-2048, 2107-2113`; assignment at `2110` |
| 1.19.3 | `net/minecraft/world/entity/Entity.java:1000-1012`, helper at `2147-2151`, reset at `2153-2155` | `net/minecraft/world/entity/LivingEntity.java:2052-2058, 2117-2123`; helper call at `2118` |
| 1.19.4 | `net/minecraft/world/entity/Entity.java:1002-1014`, helper at `2167-2171`, reset at `2173-2175` | `net/minecraft/world/entity/LivingEntity.java:1983-1989, 2048-2054`; helper call at `2049` |

### Damage calculation disposition

**Excluded operation:** fall-damage calculation and damage application. `LivingEntity.causeFallDamage` delegates to `calculateFallDamage`, then plays sounds and applies damage for a positive result (`LivingEntity.java:1468-1480` in 1.18.2; `1497-1509` in 1.19/1.19.1/1.19.2; `1499-1511` in 1.19.3; `1488-1500` in 1.19.4). The 1.19.4 `calculateFallDamage` also checks a fall-damage-immunity entity tag. Those are damage-resolution details, outside the player movement-state assignment. This disposition does not exclude `fallDistance` writes/accumulation as movement state.

## Immutable source identities and provenance bounds

Each version's ready marker declares `status: ready`, the exact release and `mapping: mojmap`, with the decompiler source manifest and artifact manifest pinned. The SHA-256 of both relevant source files below matches its line in `mojmap.sources.sha256`. Ready-marker and source-manifest digests identify the exact published source set. Provenance records identify Vineflower 1.12.0, Java 25.0.3+9-LTS and Gradle 9.7.1 for these runs; each run used the exact `versionId` listed below. 1.18.2 and 1.19.2-1.19.4 were part of exact-version batches dated 2026-10-07; 1.19 and 1.19.1 were exact-version runs dated 2026-10-10. No source was regenerated for this comparison.

| Exact release | Ready marker SHA-256 | Provenance JSON SHA-256 | Source manifest SHA-256 | `Entity.java` SHA-256 | `LivingEntity.java` SHA-256 | Client SHA-256 |
| --- | --- | --- | --- | --- | --- |
| 1.18.2 | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` | `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7` |
| 1.19 | `b00a68db8600ec8cdc3917357aff59eb0537be6f382e621e5ed3eeb900898b88` | `833cf50d6eab0f24475dcc11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` | `ffd4fb3037d4110085b4d3d2dc47b760695528eb14292c3e5f7c758983c6d764` |
| 1.19.1 | `c7b5373078eb777895a4f4b10d8c99eb289cb14fa1835004c1eb4563b6a8a690` | `de0de05299c59d3b05d96e744b27f5a5cef377880ae4a7b402337aa0ffe00245` | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` | `146d4018cfed50c5e77483940653091f186d2eea9ad42fc40939f5c72d6b7f52` |
| 1.19.2 | `90f5a351c60a1aab160b640567b716bbc563e62d92e7e555df55c4ce7952492c` | `c0c148d3c3a0092adc59c29c25f2106f3a5398156a4f266882384804b3534280` | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` | `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db` |
| 1.19.3 | `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6` | `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` | `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6` |
| 1.19.4 | `59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0` | `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` | `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec` |

## Limits

This bounded comparison establishes source behavior only for the six named exact releases. It does not locate the change within the interval after 1.19.2 through 1.19.3, establish full-pair coverage, or claim runtime parity. It does not assess glide activation, gravity selection, effects, or damage outcomes.