# Independent audit verdict: 1.21.9 → 1.21.10 modifier alias

## Verdict

**Accept the bounded no-difference conclusion** for effect modifier lifecycle, equipment modifier attach/removal, player and inherited living-entity attribute suppliers, and direct player movement-attribute consumers. The compared Mojmap source files in these operations are byte-identical across the exact 1.21.9 and 1.21.10 trees. The claim is scoped to this operation slice; it does not freeze the full movement pair or establish an intermediate patch boundary.

The immutable claim reviewed is `report.md`, SHA-256 `89c9414b53a9386d24778858734a67e575d44b758ac67f321c4c9f65fc1841f8`.

## Source identity and integrity

- Older source root: `decompiled_minecraft/1.21.9/mojmap/` (Mojmap). The hidden `.decompile-provenance.json` SHA-256 is `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`. Its source map contains 6,386 Java entries and binds version metadata `1.21.9.json` (`3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5`), Mojang `client.txt` (`f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`) and client jar (`40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`). I confirmed the provenance entries for the compared source files against the local files.
- Newer source root: `decompiled_minecraft/1.21.10/mojmap/` (Mojmap). Adjacent `decompiled_minecraft/1.21.10/mojmap.ready.json` identifies version `1.21.10`, mapping `mojmap`, and 6,386 sources; marker SHA-256 `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0`. Its `mojmap.sources.sha256` manifest SHA-256 is `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`, matching the marker. I confirmed the manifest entries for the compared files against the local files.
- The source pair has the same 6,386 Java paths. Whole-file SHA-256 equality for the relevant lifecycle, supplier, and consumer files is direct evidence of unchanged bodies, gates, types, arithmetic, and operation order.

## Reviewed operations and evidence

Ranges below were inspected in 1.21.9 and confirmed identical in 1.21.10 by whole-file hashes. Paths are relative to each version's `mojmap/` root.

| Operation | Source path and range | SHA-256 in both trees |
|---|---|---|
| Effect modifier template, creation and add/remove order | `net/minecraft/world/effect/MobEffect.java:135-170,197-200` | `a59b16b493d0c1fe164dc09e28a00050f4bf47ad95df0ed67e205b46b10a0e66` |
| Effect ticking and server-side add/update/remove callback gates/order | `net/minecraft/world/entity/LivingEntity.java:828-837,1041-1096` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| Attribute instance add, replace, removal and dirty state | `net/minecraft/world/entity/ai/attributes/AttributeInstance.java:75-132` | `c2d4f89e7ae2abbe856e7e1aa5bff2ca613073cbda282c1975636e479ba8e346` |
| Equipment slot traversal/change gate, old-effect stop, new modifier attach, broken-item removal | `net/minecraft/world/entity/LivingEntity.java:2670-2710,3548-3561` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| Component then enchantment modifier supplier | `net/minecraft/world/item/ItemStack.java:968-978` | `07b284d7a7e680f0fc5cbff1c758afd3e245eea11825435f4226d7d4acdfb74e` |
| Attribute defaults and player/inherited supplier declarations | `net/minecraft/world/entity/ai/attributes/Attributes.java:47-68`; `net/minecraft/world/entity/LivingEntity.java:324-345`; `net/minecraft/world/entity/player/Player.java:213-228` | `b941c0556d9ed3c3ce1ffd4c993c9902287a690230e1510696ff13f9efdf2020`; `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`; `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |
| Player speed writes and direct speed getter | `net/minecraft/world/entity/player/Player.java:466-470,655,1338-1341` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |
| Local-player nonzero-input, item-use/passenger and slow-movement gates and sneaking-speed float cast | `net/minecraft/client/player/LocalPlayer.java:627-642` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` |
| Inherited movement-efficiency, powder-snow, sprint, jump-strength and water-efficiency consumers | `net/minecraft/world/entity/LivingEntity.java:493-496,502-522,2162-2170,2222-2232,2331-2347` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |

As a cross-check of modifier providers and default component wiring, these additional complete files also match byte-for-byte: `net/minecraft/world/effect/MobEffects.java` (`59460ae5a2087122e88eb4e6f799466ed3ea313a04a9e9162a9abf76dea440ae`), `net/minecraft/world/item/enchantment/Enchantments.java` (`fadcf460d09813684bb6c2449feb9d0897201b262b505e7936b032cebd244f3b`), `net/minecraft/world/item/component/ItemAttributeModifiers.java` (`de6e2f7df09d37c20a653915b6db9910335da16fbb6b48fd471b901f18a7f289`), `net/minecraft/core/component/DataComponents.java` (`1829f8a5a69e4cbe5ee15ecc5a48c1a331a0c2949608d41f9c9b189736d0ebd6`), and `net/minecraft/world/entity/ai/attributes/DefaultAttributes.java` (`489a82beabfc695af6fbc5d018ab1ed2efa7d945cf13a0dace95033dc7725556`). Relevant declarations include Speed/Slowness modifiers (`MobEffects.java:15-27`), Depth Strider, Soul Speed and Swift Sneak movement effects (`Enchantments.java:360-368,502-517,570-578`), and component/default attribute wiring. No delta was found.

## Separately bounded packaged defaults

Packaged data defaults remain **unreviewed**. These canonical decompiled source directories do not contain packaged data resources: 1.21.9 has 6,386 Java sources plus its provenance JSON; 1.21.10 has 6,386 Java sources and no data JSON. The paths matching `net/minecraft/data/` are Java decompiler sources, not packaged default data. Therefore this verdict makes no claim about packaged enchantment or item default values beyond the unchanged Java provider/wiring sources listed above. No external or downloaded resources were used.

## Audit boundary

No implementation, product checkout, Git history, wiki evidence, network, tests, launches, or source generation was used. This verdict is an independent static source audit of the immutable report and specified slice only. It does not establish runtime parity, full-pair movement coverage, or the exact historical introduction point of any behavior.
