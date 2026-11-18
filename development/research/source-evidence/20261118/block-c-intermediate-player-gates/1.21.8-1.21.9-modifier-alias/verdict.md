# Independent source audit: 1.21.8 → 1.21.9 modifier alias

## Immutable input

Audited report: `c-queue-1218-1219-modifier-alias/report.md`  
SHA-256 at audit start: `6c777b1d41a6d2ea4ecd11d9b8058740be764a299967157e565948a5791cbf93`  
Mapping family: Mojmap on both sides. Sources read from the canonical local `decompiled_minecraft/` trees, read-only.

## Verdict

**Accept the bounded no-difference result, with one evidence-boundary clarification.** I independently checked modifier lifecycle, equipment attribute transfer, Player/LivingEntity supplier defaults and direct Player movement-attribute consumers. The report's source behavior and conclusion are supported: these checked Java operations have no movement-relevant 1.21.8→1.21.9 difference. This accepts only that bounded slice. It does not establish full pair coverage, an exact intermediate introduction boundary, runtime parity, or readiness of the entire source publication.

The bounded operations include server-gated effect attach/update/remove; equipment modifier replacement and removal; sprint modifier transfer; the Player `0.1F` movement-speed supplier and float-cast consumers; LocalPlayer input slowdown gates; and the Java-declared movement-related attribute/enchantment defaults. The sole observed gate spelling change in the effect callbacks is field access `level().isClientSide` to accessor `level().isClientSide()`; its boolean meaning and operation placement are unchanged.

## Independently checked evidence

- `LivingEntity.java`: effect add/force-add at 1.21.8 lines 978–1023 and 1.21.9 lines 985–1030; callbacks at 1044–1089 / 1051–1096; equipment transfer at 2637–2676 / 2667–2706; sprint modifier at 2132–2140 / 2162–2170; supplier at 317–335 / 324–342; location-effect removal at 3523–3531 / 3553–3561. These retain the same gates, ordering and attribute modifier lifecycle.
- `MobEffect.java` lines 155–172, `MobEffectInstance.java`, `MobEffects.java` lines 15–27, `Attributes.java` lines 63–96, and `EnchantmentHelper.java` lines 320–333 are byte-identical between the two source trees. `ItemStack.java` lines 964–974 / 968–978 retains item-modifier then enchantment-modifier enumeration.
- `Player.java`: attribute supplier at 1.21.8 lines 252–263 and 1.21.9 lines 213–224 retains `.add(Attributes.MOVEMENT_SPEED, 0.1F)` and builder order; tick movement-speed casts at 579 / 469; `getSpeed` casts at 1482–1484 / 1339–1341; ability walking-speed assignment at 792 / 655.
- `LocalPlayer.java` movement-input gate at 1.21.8 lines 628–638 and 1.21.9 lines 632–642 retains `0.98F`, item-use/passenger gating before `isMovingSlowly`, then float-cast attribute scaling.
- `CopperGolem.java` is absent from 1.21.8 and appears at 1.21.9 lines 100–101 with movement speed `0.2F`. This is a new non-player entity supplier, outside player movement parity; it is not a Player default change.

## Source integrity and readiness boundary

The assigned report is accurate that the exact 1.21.9 `mojmap/.decompile-provenance.json` exists and the adjacent `1.21.9/mojmap.ready.json` does not. I independently matched all 11 listed 1.21.9 Java file hashes (including the absent-on-1.21.8 Copper Golem claim) against the hidden manifest's `sources` map. The listed 1.21.8 files also match their `mojmap.sources.sha256` entries; the manifest has 6,105 rows. The hidden provenance pins 1.21.9 inputs, including the package metadata, `client.jar`, and `client.txt` hashes. The manifest itself has SHA-256 `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`; the 1.21.8 ready marker exists, while the 1.21.9 ready marker is absent.

Therefore the missing 1.21.9 ready marker is a **publication/campaign readiness artifact gap**, not evidence that the inspected Java bytes are mismatched or that this bounded source comparison is invalid. The selected hashes establish local byte-to-manifest consistency; this audit does not certify the entire decompile run or replace the canonical preparation owner's readiness check. Do not use the neighboring staging directory as evidence.

The report's resource boundary should be read narrowly: the two canonical Mojmap trees contain no packaged JSON/data resource files. Java declarations such as `Enchantments.java` can support claims about Java bootstrap/default declarations, but resource-provided runtime datapack/tag/enchantment defaults were **unavailable and unverified**, not proved identical or absent in the game. No resource-derived claim should be promoted from this audit.

## Remaining bounds

The report correctly leaves the water-movement consumer, other player tick graph slices, collision/shape areas and the independent full-pair coverage audit unexamined. No pair freeze follows from this verdict.
