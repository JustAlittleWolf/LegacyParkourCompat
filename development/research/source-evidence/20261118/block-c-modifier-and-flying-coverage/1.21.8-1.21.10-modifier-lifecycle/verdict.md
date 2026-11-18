# Independent S6 modifier audit verdict

## Result

**COMPLETED — accept the bounded Java-source claims in the immutable modifier report.** No movement-relevant Java difference was found in the audited effect-modifier lifecycle, player/default attribute suppliers, or equipment modifier attach/remove path. Keep the data-driven enchantment-default dependency open: the exact packaged registry inputs are absent from the canonical source trees inspected here, so Java helper parity does not establish equal effective defaults.

## Immutable input and source identity

Audited input: `c-queue-1218-12110-state-modifiers/report-modifiers.md`, SHA-256 `8d52a382eca0d659dd3062a2835c4c7fb2db6b143a9deec04adf03f78353f289` (matches the assignment).

Pair: Minecraft 1.21.8 → 1.21.10, Mojmap. Canonical source trees: `decompiled_minecraft/1.21.8/mojmap/` and `decompiled_minecraft/1.21.10/mojmap/`. Local ready/provenance declarations identify both exact releases and Mojmap; the ready markers cite source-manifest SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008` and `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`, respectively. For all twelve selected source files listed in the input report, I independently checked file bytes against the corresponding manifest entry. All matched.

## Claim-by-claim verdict

- **Effect modifier lifecycle — accepted, no Java difference found.** `LivingEntity.addEffect`, `forceAddEffect`, removal and the `onEffectAdded` / `onEffectUpdated` / `onEffectsRemoved` paths preserve the same gates and order: apply on first add; on amplifier update remove then re-add; on removal remove; refresh dirty attributes after updates/removals. The client-side spelling changes from `level().isClientSide` to `level().isClientSide()` in the 1.21.10 sources; the branch condition is equivalent. The selected `MobEffect`, `MobEffectInstance`, `MobEffects`, `AttributeMap`, and `AttributeInstance` files are byte-identical across the pair.
- **Player and living-entity suppliers — accepted, no player change.** `LivingEntity.createLivingAttributes` preserves declaration order. `Player.createAttributes` preserves all entries, including `MOVEMENT_SPEED` base `0.1F`, and `DefaultAttributes` still maps `EntityType.PLAYER` to `Player.createAttributes().build()`.
- **Equipment modifier lifecycle — accepted, no Java difference found.** `collectEquipmentChanges` retains slot iteration, change detection, removal of old stack effects, then enumeration of each new unbroken stack's modifiers with remove-by-ID before transient add. The item stack continues to enumerate item modifiers before `EnchantmentHelper` modifiers. The cleanup method removes stack modifiers before delegating enchantment cleanup. The corresponding `forEachModifier` and `EnchantmentHelper.forEachModifier` bodies preserve order and slot gates.
- **New default-provider entries — separate non-player additions.** `DefaultAttributes` adds `COPPER_GOLEM` using `CopperGolem.createAttributes()` and `MANNEQUIN` using `LivingEntity.createLivingAttributes()`. Neither replaces nor alters the player provider; neither is a player default.
- **Packaged enchantment defaults — unresolved, correctly preserved by the report.** The canonical Mojmap trees and their Java source manifests contain no `data/minecraft/enchantment/*.json` inputs (the checked enchantment-resource directories do not exist). The unchanged Java helper proves iteration logic only; it cannot prove that packaged registry definitions or effective default membership are equal. Keep this gate open until the exact packaged data for both releases and its provenance are available.

The `ItemStack` component-validation call removals noted in the input report are outside the enumerator bodies checked here; the reviewed evidence does not establish a movement-modifier consequence from those changes.

## Bounds

This verdict covers only the claims and dependencies enumerated above for the specified endpoint pair. It does not close exact intermediate patch boundaries, establish runtime parity, or close the packaged enchantment-default dependency. I did not inspect wiki inventories, product implementations or diffs, Git history, or backlog records; I did not revisit the sprint-predicate or state-writer residual. No source was generated or edited, and no tests or launches were run.