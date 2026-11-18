# Powder snow frost modifier: movement-speed arithmetic

## Bounded result

Compared the frost modifier's ordinary movement-speed arithmetic at exactly five anchors: 1.20.5, 1.21, 1.21.2, 1.21.4, and native 26.2. No numeric or operation-order movement difference is visible in these source trees. This is a bounded source comparison, not a runtime-parity claim.

At each anchor, `LivingEntity.tryAddFrost` reads the current frozen percentage as a `float`, computes `-0.05F * percentFrozen` as a `float`, then adds that amount to `Attributes.MOVEMENT_SPEED` with `AttributeModifier.Operation.ADD_VALUE`. The method gate is a non-air `getBlockStateOnLegacy()` and `getTicksFrozen() > 0`, with an early return if the speed attribute instance is absent. This report treats `getPercentFrozen()` as an input read boundary; it does not inspect how frozen state is produced or scheduled.

`AttributeInstance.calculateValue` in all five trees starts from a `double` base, sums `ADD_VALUE` modifiers into it, then applies `ADD_MULTIPLIED_BASE` modifiers using that post-additive value, then successively applies each `ADD_MULTIPLIED_TOTAL` factor, and sanitizes the result. `Player.getSpeed()` returns `(float) getAttributeValue(MOVEMENT_SPEED)`. The player tick also copies the effective attribute value through `(float)` into the living-entity speed field. The frost additive therefore precedes speed, slowness, and sprint's total multipliers in this calculation, with a final double-to-float conversion on the player read paths.

The modifier identity representation changes across the anchors: 1.20.5 uses UUID `1eaf83ff-7207-4596-b37a-d7a07b3ec4ce` plus a name; 1.21 onward uses the valid default-namespace identifier `minecraft:powder_snow` (named `ResourceLocation` through 1.21.4 and `Identifier` in 26.2). The same identity is used by the matching remove path within each anchor. This source-visible representation change does not alter the arithmetic or demonstrate a movement delta.

## Numeric witnesses

Witness conditions: base movement speed is the player default `0.1F`, frozen percentage input is `1.0F`, hence frost is the computed float `-0.05F`; for combined effects, Speed I (`+0.2F` total), Slowness I (`-0.15F` total), and sprint (`+0.3F` total) are concurrently present. Inputs are deliberately fixed at read boundaries. Values below follow the source's float constants widened to double for the double attribute calculation, then the player's float cast. All anchors use the same expressions and values.

| Active modifiers | Effective attribute value (double) | `Player.getSpeed()` (float) |
| --- | ---: | ---: |
| Frost | 0.05000000074505806 | 0.05 |
| Frost + Speed I | 0.060000001043081286 | 0.060000002 |
| Frost + Slowness I | 0.04250000033527612 | 0.0425 |
| Frost + sprint | 0.06500000156462193 | 0.065000005 |
| Frost + Speed I + Slowness I + sprint | 0.06630000129565597 | 0.066300005 |

For the combined witness the three total factors commute mathematically; their possible finite-double ordering variation is below the shown final float precision. This is an arithmetic witness only, not a claim that the combination can be established through any particular producer or scheduling sequence.

## Per-anchor evidence and provenance

All line numbers are one-based. Every listed source hash is SHA-256 of the exact local `.java` file as read during this comparison.

| Anchor / mapping tree | Frost method; attribute calculation; player read paths | Provenance status | Source SHA-256: LivingEntity / Player / AttributeInstance / MobEffects |
| --- | --- | --- | --- |
| 1.20.5 / Mojmap | `LivingEntity.java:542,552`; `AttributeInstance.java:140-157`; `Player.java:533,1511-1513` | Version-level ready/provenance metadata identifies version 1.20.5, official Mojang mappings, and a pinned client SHA. Adjacent `mojmap.sources.sha256` has SHA-256 `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`; its rows for all four consumed Java files match the local SHA-256 values in this table. | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` / `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` / `5d060c1740336835c74138d3c5fe3ae5f3b7f6a1c2f16f6844289f18ef90782a` / `9b24dee8a23a4b8730fdfa7ea9cbd560c9370599cc282fac1c0755f5894e7645` |
| 1.21 / Mojmap | `LivingEntity.java:499,509`; `AttributeInstance.java:142-159`; `Player.java:535,1494-1496` | Root provenance manifest present. It pins version metadata and official `client.txt` mapping input; manifest's source hashes include these exact files (`LivingEntity`: `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`; `Player`: `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`; `AttributeInstance`: `16a79843c53aabf5ae34b3230596995c70932197223482ad998085d6031ffee0`). | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` / `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` / `16a79843c53aabf5ae34b3230596995c70932197223482ad998085d6031ffee0` / `35d97c1741ec1379b05f41b8452229870c87a75633d312cd0b20d7fd901b4d08` |
| 1.21.2 / Mojmap | `LivingEntity.java:514,524`; `AttributeInstance.java:152-169`; `Player.java:556,1489-1491` | Root provenance manifest present. It pins version metadata and official `client.txt` mapping input, and lists the exact relevant source hashes. | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` / `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` / `2a5ed44096b45b3b050a8df947cd44c788e6a35d51eb81d4c023f331f6f62870` / `35d97c1741ec1379b05f41b8452229870c87a75633d312cd0b20d7fd901b4d08` |
| 1.21.4 / Mojmap | `LivingEntity.java:514,524`; `AttributeInstance.java:152-169`; `Player.java:559,1492-1494` | Version-level ready/provenance metadata identifies version 1.21.4, Mojmap, and a pinned client SHA. Adjacent `mojmap.sources.sha256` has SHA-256 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`; its rows for all four consumed Java files match the local SHA-256 values in this table. | `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` / `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1` / `2a5ed44096b45b3b050a8df947cd44c788e6a35d51eb81d4c023f331f6f62870` / `35d97c1741ec1379b05f41b8452229870c87a75633d312cd0b20d7fd901b4d08` |
| 26.2 / unobfuscated | `LivingEntity.java:531,541`; `AttributeInstance.java:148-165`; `Player.java:456,1446-1448` | Version-level ready/provenance metadata identifies version 26.2 and native unobfuscated output. Adjacent `unobfuscated.sources.sha256` has SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; its rows for all four consumed Java files match the local SHA-256 values in this table. | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` / `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` / `c7cbe38bfdc1b4aafcf623bd1b604200cc6190641091369ee9bb9303e8f07ad5` / `532df5c7cd47edd3d9d091c2eae0962c279224c8f5f206860e9df45bc3b0c684` |

The 1.21 and 1.21.2 manifest hashes for all three listed source files were checked against the local files and match.

## Verdict and scope limits

No separate attributable movement difference was established for the requested frost-modifier arithmetic across the five inspected trees. The 1.21 and 1.21.2 local Java hashes, including `MobEffects.java`, match their root provenance manifests. For 1.20.5, 1.21.4, and 26.2, the adjacent source-manifest digests match their ready/provenance records and the rows for all four consumed Java files match their local hashes. Exact version/mapping/client or native-source identities and used Java hashes are therefore supported at all five anchors. No claim is made about the producer of frozen percentage, powder-snow contact scheduling, tick ordering outside the stated read gates, other effects, general movement, implementation, or runtime parity.