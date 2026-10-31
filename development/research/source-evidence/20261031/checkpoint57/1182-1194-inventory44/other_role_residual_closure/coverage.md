# Residual `other` path-role classification (1.18.2–1.19.4)

## Scope and integrity

This bounded closure covers every `role=other` path in the frozen exact Mojmap changed-path roster: 1,642 changed edge/path rows across 1,014 unique case-sensitive paths. `path-dispositions.tsv` records each path's changed edge/status and the exact old/new source SHA-256 values from the canonical source roster. It assigns a path-level source role; it does not assert that every unrelated source member received complete semantic method review. Adjacent edges are 1.18.2→1.19, 1.19→1.19.1, 1.19.1→1.19.2, 1.19.2→1.19.3, 1.19.3→1.19.4.

Source roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`. The six source-manifest SHA-256 identities from the whole-source inventory are: 1.18.2 `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`; 1.19 `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26`; 1.19.1 `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1`; 1.19.2 `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`; 1.19.3 `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; 1.19.4 `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`. Rehashed all 9,202 nonempty old/new hash entries in the 5,074-row canonical changed-path roster: zero missing files or mismatches. Inputs: `changed-path-roles.csv` SHA-256 `8A3F647F94F5B9A556537C0592A32DB9FAB34AAB87F35E71D85F16E2BFE041EF`; `changed-paths.tsv` SHA-256 `FC00141CB9C9ED40AA24B59429DFBB4DB7AEDE3C9657AF82F55E429C59511606`.

## Primary path dispositions

Counts are unique paths by primary disposition; existing sibling lanes are referenced, not re-audited.

- 516 paths — explicit server/state/worldgen/data/command/test production excluded
- 166 paths — prior item/inventory closure; item/menu/container state, no re-review
- 104 paths — UI/network/advancement progress production; no direct local movement consumer
- 89 paths — general utility/version/crash/locale/recipe-book metadata; no direct local movement consumer identified
- 47 paths — rendering/audio/font/graphics presentation dependency; no direct local movement consumer
- 41 paths — prior core/resource/tag closure; registry/resource/tag plumbing
- 19 paths — serialization/persistence production; no direct local movement consumer
- 13 paths — damage/health calculation and death messaging excluded
- 12 paths — score/stat/sound metadata or event output; no direct local movement consumer
- 4 paths — prior effect/attribute closure; changed APIs/registration/health or movement effect consumers covered there
- 2 paths — health/food production excluded; movement gate dependency terminally checked below
- 1 paths — prior movement math closure

Changed rows per edge:

- 1.18.2->1.19: 582 rows
- 1.19->1.19.1: 126 rows
- 1.19.1->1.19.2: 5 rows
- 1.19.2->1.19.3: 547 rows
- 1.19.3->1.19.4: 382 rows

## Food gate dependency terminal check

`LocalPlayer.hasEnoughFoodToStartSprinting()` reads `getFoodData().getFoodLevel() > 6.0F` in 1.19.3 and 1.19.4. The provider is in this `other` slice. Exact source hashes: `FoodData.java` 1.19.3 `6b60d0df297c059ddfc0a45599cb689b954046f41e6070c86821007b8ea4ccfc`, 1.19.4 `3288f1d1287f782164a3e55cf5bb2b08debaa36e79a19398d19c2c6eaedcd59d`; `FoodConstants.java` 1.19.3 `7a67ef073d107327656dab9a74398f0fafc1ff97307fd7279e9ef7fc040fe41f`, 1.19.4 `37c79b5429c5a30cc83c19983c9b5de4d18ce3023040151aab0d4b48124218e6`. Exact diff: `FoodData.tick` changes only the starvation damage-source invocation (`DamageSource.STARVE`→`player.damageSources().starve()`); `FoodConstants` removes `EXHAUSTION_DAMAGE`. The `foodLevel` field and `getFoodLevel`, `setFoodLevel`, and `addExhaustion` bodies are unchanged. This is a real movement-facing provider dependency with no changed getter/exhaustion contract. Food/health production remains excluded. The LocalPlayer sprint branch itself changes in 1.19.4 and belongs to the client movement lane, not this provider closure.

Other bounded source-text hits: `GameTestHelper` names `LocalPlayer` for test fixtures (test driver); `ServerPlayer` reads food level (server state); `MobEffect` accesses food data (effects lane). No other path in this `other` slice names `LocalPlayer`, `hasEnoughFoodToStartSprinting`, or `getFoodData`. The direct-name scan is a candidate filter, not proof that all possible indirect callbacks are absent.

## Residual limitations

The ledger classifies every changed path into its primary source role, but this lane did **not** perform member-by-member semantic review of all 1,014 paths. Remaining path classes with only path-level classification are explicitly listed per path in `path-dispositions.tsv`: chiefly server/command/data/worldgen and utility/datafix producers; NBT/persistence; Realms, advancement, UI and rendering/font/audio; stats/sounds/score metadata; and miscellaneous version/crash/locale/recipe-book helpers. Server/state/data production, damage/health, rendering/UI, and nonmovement output are excluded by scope. The table separately points to prior item, inventory, core/resource/tag, movement-math, effect/attribute, client, and block/fluid/provider/callback closures. No pair-freeze claim is made.
