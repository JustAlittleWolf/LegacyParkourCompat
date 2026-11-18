# Independent audit verdict: 1.21.5 → 1.21.6 modifier lifecycle

Verdict: **ACCEPT** for the report’s bounded source claims. The exact Mojmap source evidence, reported hashes, lifecycle order/gates, direct player movement-attribute consumers, and stated exclusions were independently checked. No player-movement modifier lifecycle delta was found in the examined operations. This does not close complete pair coverage or establish runtime parity.

## Inputs and identity

- Audited report: `c-queue-1215-1216-modifier-lifecycle/report.md`; SHA-256 `b6689d357120001fad6037be94bcf25cb17d5f0401f49c45e7a03ba84b0bcbea` (matches assigned input hash).
- Source namespace: Mojmap, exact directories `decompiled_minecraft/1.21.5/mojmap/` and `decompiled_minecraft/1.21.6/mojmap/`.
- 1.21.5 sidecars are at the **version root**, not inside `mojmap/`: `mojmap.ready.json`, `mojmap.provenance.json`, and `mojmap.sources.sha256`. Their SHA-256s respectively are `5f65fb143209abcc6e28e77a7150aecefdbc9de00b6ef3fc924e53bc0e1a667e`, `b0832a4e1507bd74ac300fae2fca78668964b21ef2d05be8cff01b9d1d60da8e`, and `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`. The ready marker says ready, exact version `1.21.5`, mapping `mojmap`; the client hash is `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`.
- 1.21.6 provenance: `decompiled_minecraft/1.21.6/mojmap/.decompile-provenance.json`, SHA-256 `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e`. Its pinned inputs identify exact client artifact `.../740a125b83dd3447feaa3c5e891ead7fbb21ae28/client.jar` (SHA-256 `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a`), client mappings `.../848855615bc81e3db1c85e69b6afb150807a1261/client.txt` (SHA-256 `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261`), and exact metadata `.../8dd125560431a618094435343acccd45191bb006/1.21.6.json` (SHA-256 `ea1c91ccb9760c9f7e697c29ed679427dd20cdac9ce35937125e5690973df6dd`).
- Recomputed every source entry in both manifests: 5,921/5,921 for 1.21.5 and 6,103/6,103 for 1.21.6; zero missing files or digest mismatches.

## Independent source findings

- **Effects:** `LivingEntity.onEffectAdded`, `.onEffectUpdated`, `.onEffectsRemoved`, and `refreshDirtyAttributes` are the same operation sequence and logical-client gates at 1.21.5 lines 1019–1075 and 1.21.6 lines 1044–1100. In each, attach is guarded by `!level().isClientSide`; an update that replaces modifiers removes then adds the amplifier-derived modifier before refreshing; removal loops through modifiers then refreshes once. `MobEffect.removeAttributeModifiers` / `addAttributeModifiers` are byte-identical, lines 155–174. `MobEffectInstance.java` is byte-identical. Reported hashes match local files.
- **Equipment and gliding:** `LivingEntity.collectEquipmentChanges`/`detectEquipmentUpdates` preserve old-item location-effect stopping and new-item modifier remove-then-add ordering: 1.21.5 lines 2574–2623; 1.21.6 lines 2626–2675. `onEquippedItemBroken` and `stopLocationBasedEffects` match at 3462–3475 / 3518–3531. `canGlideUsing` matches at 3622–3629 / 3678–3685. The Elytra declaration is in `Items.java` 989–1004 / 1040–1055 with GLIDER and chest EQUIPPABLE components and no attribute modifier declaration. The `Items.java` hashes match the report.
- **Direct movement consumers checked:** `Player` obtains `MOVEMENT_SPEED` as a float (`Player.java` 1470–1471 / 1482–1483), and sets its base from walking ability (569 / 579; 783 / 792); `LocalPlayer` reads `SNEAKING_SPEED` at line 634 in both. `LivingEntity` block speed interpolation, sprint modifier, jump-power expression, gravity, water movement efficiency and speed reads are at 473–508, 2101–2111, 2160–2168, 2205, 2256–2263, 2446–2450 in 1.21.5 and corresponding lines 487–522, 2133–2143, 2192–2200, 2237, 2308–2315, 2498–2502 in 1.21.6. These checked expressions/gates match. The Soul Speed declaration and attribute effect are in byte-identical `Enchantments.java`: 1.21.5 lines 502–519; 1.21.6 lines 522 onward (same text shifted by surrounding declaration changes). It adds movement speed with `perLevel(0.0405F, 0.0105F)` and movement efficiency with constant `1.0F`, both `ADD_VALUE`.
- **Scope boundary confirmed:** neither canonical decompilation tree contains data/resource JSON definitions; only provenance/ready sidecars are JSON. This supports the report’s explicit no-claim boundary for resource-defined enchantment defaults; it does **not** establish that packaged client resources lack such definitions. `ElytraItem.java` is absent on both sides, but the relevant exact declaration and glide predicate are present in `Items.java` and `LivingEntity.java`. The report properly excludes the 1.21.6 non-player harness/Happy Ghast additions. `LocalPlayer.java` differs elsewhere; the report only makes the narrower SNEAKING_SPEED-consumer claim and explicitly leaves its input-state changes unassessed.

## Examined source hashes

| Relative file | 1.21.5 SHA-256 | 1.21.6 SHA-256 |
| --- | --- | --- |
| `world/entity/LivingEntity.java` | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| `world/effect/MobEffect.java` | `a59b16b493d0c1fe164dc09e28a00050f4bf47ad95df0ed67e205b46b10a0e66` | same |
| `world/effect/MobEffectInstance.java` | `8b35c542a25090f7a45fa019a5263ea51cc1beb2d58605dd4ed65e805035bc36` | same |
| `world/entity/ai/attributes/AttributeInstance.java` | `6e557daca10617e6a8f4af17d6436a8d1165cd175048e98ea795629a274c44cb` | `c2d4f89e7ae2abbe856e7e1aa5bff2ca613073cbda282c1975636e479ba8e346` |
| `world/entity/ai/attributes/AttributeMap.java` | `60651acdcd2cc66c590b1203978b26e2b253892861f1156004d5040d728210bf` | `06088d7aeedd65b468b807c3a500da7a3a16d787917812a55dd7b81ad60cd079` |
| `world/item/Items.java` | `d258c31e88d6e7208b7c196a7b7408172f7c54050e11442e54894a1d31f4f5d5` | `fe3b56b3f1949467eaa496522b37a630adf6c3b973c468a8e58aedeea5480d59` |
| `world/item/enchantment/Enchantments.java` | `ee37699b95dc530fdd91dadef27d19a54bc7c4febe79bca40cc1ee438a626356` | same |
| `world/item/enchantment/effects/EnchantmentAttributeEffect.java` | `c1e2edd5c870e1ecba364cbee07ae2f12720e47681dd1dbf16ac081e392f4270` | same |
| `world/entity/player/Player.java` | `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `client/player/LocalPlayer.java` | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |

Audit outputs: this `verdict.md`. No product files, Git state, source generation, tests, or launches were changed/run.
Audit-body SHA-256 (all preceding text, excluding this line): `9db3085dde848cb239a44511d648660d1c435d5f4b5acdc09d2cf0760255dd7a`.
