# Independent source audit: Mojmap 1.21.3 → 1.21.4 modifier dependencies

**Verdict: ACCEPTED within the scoped Java-source claim; resource dependency remains open.**

## Immutable input

Audited `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-queue-1213-1214-modifier-dependencies/report.md`, SHA-256 `a10c1e60c43ab025b79cb7947a74d6d60c1aef5d6749440c3ded1a1785c16716` (matches the assigned identity).

Canonical inputs are `decompiled_minecraft/1.21.3/mojmap/` and `decompiled_minecraft/1.21.4/mojmap/`. The ready markers identify those exact releases and Mojmap; their source-manifest hashes match the report. I independently checked that `Attributes.java`, `AttributeSupplier.java`, `AttributeInstance.java`, `MobEffects.java`, `MobEffect.java`, `MobEffectInstance.java`, `Enchantment.java`, `EnchantmentHelper.java`, `world/item/component/ItemAttributeModifiers.java`, and `ClientboundUpdateAttributesPacket.java` are byte-identical across the pair. (The item component's actual source path is `world/item/component/ItemAttributeModifiers.java`, rather than directly under `world/item/`.)

## Claims reviewed

The evidence supports no change in the ordinary Java modifier paths covered by the report:

- Player construction still uses the shared living-attribute supplier and sets movement speed to `0.1F`; the player registration in `DefaultAttributes` remains present. The shared registry defaults for movement speed (`0.7`) and jump strength (`0.42F`) and the calculation order in `AttributeInstance.calculateValue()` are unchanged.
- Speed and Slowness still install amplifier-scaled total-multiplicative movement-speed modifiers; Jump Boost remains a safe-fall-distance modifier with upward jump power read directly. The reported effect update/removal lifecycle and ordinary direct movement reads are consistent with the compared source.
- Item component and enchantment modifier definitions are byte-identical. `ItemStack.forEachModifier` and `LivingEntity.collectEquipmentChanges` are reported at corresponding locations in both sources; the latter enclosing file differs, so whole-file equality is not evidence, but the scoped method comparison in the input describes the same old/new removal, replacement and location-effect sequence. No contradictory movement change was found in this slice.
- The 1.21.4 `AttributeMap.resetBaseValue` is a distinct new operator reset path wired from `AttributeCommand`; it does not alter an ordinary supplier default or ordinary modifier calculation. The moved Creaking provider entries are likewise unrelated to the player supplier.
- The report's server dirty-attribute and client update handling is appropriately scoped to the compared synchronization path; packet class is byte-identical. This does not establish all server/client tick behavior.

## Limits / gates left open

The canonical Mojmap directories expose Java source (`com` and `net`), not packaged data/resources. Therefore this audit cannot close resource-, registry- or tag-driven attribute overrides. Keep that dependency explicitly open; do not generalize this verdict to exhaustive effective player attributes under every packaged resource or datapack. Also outside this assigned slice: sprint-specific effects, full tick graph, block/fluid/provider dependencies and full-pair coverage/freeze. The report's claim is valid only as a scoped ordinary Java-path no-difference result, not as pair acceptance or movement parity proof.

No source generation, product inspection, Git/backlog access, tests, game launch or code changes were performed.

**Terminal result: COMPLETED.**
