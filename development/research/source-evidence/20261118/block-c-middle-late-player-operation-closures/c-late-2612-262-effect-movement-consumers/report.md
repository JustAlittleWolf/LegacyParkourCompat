# Lead C: 26.1.2 → 26.2 effect movement consumers

## Evidence identity and limits

Both adjacent source trees are `unobfuscated`. Their ready markers identify exact releases 26.1.2 and 26.2, and report 6,882 and 7,055 source files. The ready markers' source-manifest SHA-256 values match the bytes of `unobfuscated.sources.sha256`: 26.1.2 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`; 26.2 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. The corresponding ready-marker declarations and the actual source-file digests listed below were checked against the local files.

Adjacent provenance identifies Vineflower 1.12.0, Java 25.0.3+9-LTS, Gradle 9.7.1, Mapping-IO 0.9.1 and Tiny Remapper 0.14.1. The 26.1.2 provenance records a full Gradle output; 26.2 records only a summary excerpt and has `mappingArtifacts: null`. Thus this comparison is tied to the exact local release-labelled trees and their source manifests, but the 26.2 adjacent provenance is incomplete as an independent artifact-input audit. No source was generated or modified.

Relevant source digests match their manifest entries:

| File | 26.1.2 SHA-256 | 26.2 SHA-256 |
| --- | --- | --- |
| `world/effect/MobEffects.java` | `532df5c7cd47edd3d9d091c2eae0962c279224c8f5f206860e9df45bc3b0c684` | same |
| `world/effect/MobEffect.java` | `5412fe3210df040882f2b5860a9fb3e1d66de151453740ebcdeaa22374acf28c` | `07f7c99dc3a6d00d684f5ef88d1861fcada7fe83f80c718bb172ac33056d5555` |
| `world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| `world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| `client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |

Paths below are relative to each version's `unobfuscated/` directory. Findings are limited to these direct consumers and the exact listed source branches; this is not full-pair coverage or independent acceptance.

## Six bounded claims

1. **SPEED — no difference in the checked movement modifier path.** `world/effect/MobEffects.java:15–21` is byte-identical: SPEED applies `+0.2F` to `Attributes.MOVEMENT_SPEED` with `ADD_MULTIPLIED_TOTAL`. `world/effect/MobEffect.java:142–179,206–209` keeps the same `amount * (amplifier + 1)` construction and remove-before-add modifier update; `world/entity/LivingEntity.java` keeps the same add/update/remove attribute lifecycle (`26.1.2:1054–1100`, `26.2:1075–1119`). Ordinary `Player.getSpeed()` returns the movement-speed attribute (`Player.java:1425–1427` / `1446–1448`), which is the local player's inherited movement speed input. No changed SPEED formula or local read was found.

2. **SLOWNESS — no difference in the checked movement modifier path.** The same byte-identical registry source (`MobEffects.java:22–28`) applies `-0.15F` to `MOVEMENT_SPEED` with `ADD_MULTIPLIED_TOTAL`; amplifier scaling and the checked modifier lifecycle are as in claim 1. The direct player speed read remains the same attribute lookup. No changed SLOWNESS formula or local read was found.

3. **JUMP_BOOST — no difference in direct jump consumers.** `LivingEntity.getJumpBoostPower()` remains `hasEffect(JUMP_BOOST) ? 0.1F * (amplifier + 1.0F) : 0.0F`, added after `(float) JUMP_STRENGTH * multiplier * getBlockJumpFactor()` in `getJumpPower()` (`LivingEntity.java:2343–2349` / `2380–2385`). The local player's `updateAutoJump` also retains its direct `jumpHeight += (amplifier + 1) * 0.75F` branch (`LocalPlayer.java:1037–1041` in both). The effect registration is byte-identical. Neither checked jump read changes.

4. **SLOW_FALLING — no difference in gravity or tick reset reads.** `getEffectiveGravity()` keeps the `deltaMovement.y <= 0.0` gate and returns `min(getGravity(), 0.01)` only while SLOW_FALLING is active (`LivingEntity.java:2387–2390` / `2425–2428`). The tick branch still resets fall distance when SLOW_FALLING or LEVITATION is present (`3064–3068` / `3131–3135`). The selected player's inherited falling/gravity branch has no change in these expressions or order.

5. **LEVITATION — no difference in direct travel/read branches.** Air travel still reads the effect before gravity and adjusts vertical movement as `(0.05 * (amplifier + 1) - movement.y) * 0.2`; otherwise it applies effective gravity subject to the same client/chunk gates (`LivingEntity.java:2430–2453` / `2468–2497`). The tick reset branch and `canGlide()` exclusion for levitating entities are unchanged (`3148–3160` / `3215–3227`). The local player's inherited travel therefore encounters the same levitation expression and gates.

6. **DOLPHINS_GRACE — no difference in the water-travel override.** In `travelInWater`, both releases first compute sprint/water slowdown and water-walker adjustment, then test DOLPHINS_GRACE and set slowdown to `0.96F`, then call `moveRelative`, move, and multiply horizontal movement by slowdown (`LivingEntity.java:2467–2493` / `2516–2543`). The effect registration is byte-identical. The literal, gate, and operation order checked for the ordinary player's inherited water travel are unchanged.

## Coverage boundary

This assignment checked the six specified effects' movement modifier formulas and direct player-inherited consumers, including local auto-jump's jump-effect read. It excludes effect add/update/remove packet receivers, other effect readers, health/damage behavior, environmental surface production and other actors. No claim is made about unexamined methods or intervening patches.
