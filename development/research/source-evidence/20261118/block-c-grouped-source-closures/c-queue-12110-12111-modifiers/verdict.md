# Independent audit: 1.21.10 → 1.21.11 movement modifiers

Terminal: **COMPLETED** for this bounded independent Java-source audit. This verdict is bound to the immutable source report SHA-256 `6a1585eac753e6104a2024e3ae6da4bf4153124be59b176f8bffbb030fed472e`. **The default packaged enchantment resource membership and values remain open.** No Java movement difference was found in the audited slices. This does not freeze the full pair and does not establish runtime parity.

## Evidence identity

Read-only exact Mojmap/Vineflower trees under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.10\mojmap` and `...\1.21.11\mojmap`. Both ready markers identify their exact release, Mojmap, Vineflower and Java 25 output. The source-manifest SHA-256 values are respectively `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` and `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; the manifest files match the ready markers. The shared artifact manifest matches `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` on both sides. Per-file SHA-256 checks matched the source manifests for `Attributes`, `DefaultAttributes`, `LivingEntity`, `Player`, `LocalPlayer`, `MobEffects`, `MobEffect`, `ItemStack`, `EnchantmentHelper`, `ClientPacketListener` and `ServerEntity` in both versions.

## Independent result

- **Movement attributes and player defaults:** `Attributes` retains `jump_strength` 0.42F, `movement_efficiency` 0.0, `movement_speed` 0.7 and `sneaking_speed` 0.3 with the same ranges and syncability. `LivingEntity.createLivingAttributes` and `Player.createAttributes` include the same relevant attributes/defaults, and `DefaultAttributes` maps player to the same supplier. Only registration naming changes (`ResourceLocation` → `Identifier`) among the checked definitions. Local movement reads retain the checked sneaking speed, movement efficiency and jump strength inputs. Jump boost remains `0.1F * (amplifier + 1.0F)`; slow falling's effective-gravity cap remains gated by nonpositive Y velocity; levitation's vertical adjustment is unchanged.
- **Effect modifiers and lifecycle:** Speed and Slowness retain movement-speed modifiers +0.2F and -0.15F using `ADD_MULTIPLIED_TOTAL`; Jump Boost retains +1.0 to safe-fall distance using `ADD_VALUE`. Levitation and Slow Falling remain without Java-declared attribute modifiers. Template scaling by amplifier plus one, replacement/removal by modifier identity, entity effect add/update/remove handling, and dirty-attribute refresh ordering show no behavioral delta in the paired methods. Client receipt still follows the packet's blend/skip gate and force-add path; attribute snapshots remain a distinct path.
- **Equipment/enchantment and received state:** Item modifiers precede enchantment modifiers in `ItemStack.forEachModifier`; enchantment effect application remains gated by applicable slots. Equipment replacement removes old location-based modifiers, then applies modifiers from a nonempty, nonbroken new stack; changed equipment is sent in the equipment packet and dirty syncable attributes in attribute packets. The client applies received attribute base values/modifiers. The paired Java gates and ordering show no relevant delta.

The endpoint source names and registration identifier API differ, but the compared movement values, operations, gates and ordering do not. I found no independent counterexample requiring a finding against the source report.

## Limits

The exact pinned client artifacts are not available in the local provenance cache described by the source report, and this audit did not generate or download them. Therefore default enchantment JSON/data membership, movement-affecting enchantments, their values and slot coverage remain **open**; generic Java support cannot establish which packaged defaults use it. This audit covers only the assigned movement-attribute defaults/reads, named effect modifier lifecycle and generic equipment/enchantment modifier application plus received effect/attribute/equipment state paths. Other movement topics and the remainder of the pair-wide tick graph are unexamined. No product, wiki, Git-history, test, runtime, or generation evidence was used.

## Terminal identity

- Source report SHA-256: `6a1585eac753e6104a2024e3ae6da4bf4153124be59b176f8bffbb030fed472e`
- Independent verdict SHA-256: recorded after writing as the file digest for this `verdict.md`.