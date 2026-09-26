# MD-01: Depth Strider changes water movement

- Older version A: 1.7.10
- Newer version B: 1.8.9
- Mechanic / coverage slice IDs: stages 3.2 and 6.1
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.7.10, 1.8.9]
- Runtime validation: not performed

## Paired evidence

- A artifact A; `../../../../decompiled_minecraft/1.7.10/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; `net.minecraft.entity.living.LivingEntity.moveRelative(float,float)`; lines 1177-1188; SHA-256 `5C1C5CF82D98C2F57A65B857FE02975B749C0562E4C1437AB5F072E202CA941C`. The liquid movement branch applies its input factor, moves, multiplies X/Y/Z velocity by `0.8F`, and subtracts `0.02` from Y.
- A absence path: `../../../../decompiled_minecraft/1.7.10/ornithe-feather/net/minecraft/enchantment/Enchantment.java` has no Depth Strider registration; `EnchantmentHelper.java` and `LivingEntity.java` contain no matching level query. File hashes: `36927FF668597F890EBE69B729CD50863D498084D466FC5E407DF40311D05F43` and `B85ACA527B0636E6DE8E73082698F627CB1123DABDD9FC3961E5B96EAFEBD9AD`.
- B artifact B; `../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; `LivingEntity.moveRelative(float,float)`; lines 1199-1225; SHA-256 `082831C6578E3A70FA6CEA5B90BC3EEFC26678259B66334470DE22B90B5B0E4E`. The liquid branch reads Depth Strider level, caps it at three, halves it off ground, and for positive levels adjusts the input factor toward `getSpeed()` and X/Z drag toward `0.54600006F` before applying those values around movement.
- B dependencies: `Enchantment.java:28` registers `DEPTH_STRIDER`; `DepthStriderEnchantment.java:5-22` assigns feet armor category and max level 3; `EnchantmentHelper.java:182-184` obtains the highest level from `entity.getEquipment()`. SHA-256 values: `F42E778392EDDD8A4499B3AE074B059AD0984DCF4A1D775E5933914AE854DD74`, `D0225DAA535309D85A83284A69BB6499617DEE89D3592542AF25427E46496B5D`, and `71CD5B3766A30B1A5CAB0245719E75BE0CEF1B62DF82DBE3E326F94857716D99`.

## Source-level difference

For a living player in water, B consults the highest Depth Strider level among equipped items and changes the horizontal input scale and post-move X/Z drag. At level zero B retains `g = 0.02F` and `f = 0.8F`; positive levels change those values, with airborne players using half the level before interpolation. A has no such registration, query or consumer.

## Reachability and dependencies

`LocalClientPlayerEntity.tick()` -> player/living tick -> `LivingEntity.mobTick()` -> `moveRelative(sidewaysSpeed, forwardSpeed)` -> water movement branch -> `EnchantmentHelper.getDepthStriderLevel(this)` -> `getEquipment()` aggregation. B registers the enchantment for feet armor and caps its level at three. The source proves the client movement consumer and its conditions. The exact equipment value is an entity input; packet synchronization and application remain queued in the run manifest.

## Consequence and uncertainty

Source-proven: a positive equipped level changes water input acceleration and horizontal drag, with a half-level factor while airborne. Inference: with equal input and environment, this can change water displacement and retained horizontal velocity. No trajectory was measured. The introduction release is unknown within the endpoint interval.

## Handoff

Treat Depth Strider as a B-era, equipment-conditioned player water movement mechanic. Preserve the float constants, cap, airborne multiplier, interpolation order and velocity update order. Verify synchronized equipment before closing the condition. Runtime validation is deferred.
