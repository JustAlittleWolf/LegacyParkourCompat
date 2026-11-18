# Independent source audit: player movement effect consumers

## Verdict

All six requested local movement-effect claims are source-confirmed with no movement-semantic difference between 26.1.2 and 26.2 in the examined source. One representation changed: Dolphin's Grace's `0.96F` water-drag value moved from an inline literal in 26.1.2 to `DOLPHINS_GRACE_WATER_DRAG = 0.96F` in 26.2. The value remains `float`, and its assignment remains after the water-walker adjustment and before movement.

Effect insertion/update/removal order in `LivingEntity` is unchanged in the compared bodies. `addEffect` first rejects an ineligible effect; for a new effect it inserts into `activeEffects`, calls `onEffectAdded`, sets `changed`, then invokes `newEffect.onEffectAdded`; for an existing effect it calls `effect.update(newEffect)` and, if true, calls `onEffectUpdated(effect, true, source)`. It calls `newEffect.onEffectStarted(this)` after either accepted branch. `forceAddEffect` puts first, then calls added or copies blend state and updates. Removal deletes with `removeEffectNoUpdate` before `onEffectsRemoved`. The add/update/remove attribute-modifier callbacks are gated by `!level().isClientSide()` in both releases. This review does not cover the network packet path that supplies client effect state.

### Per-effect verdicts

| Effect | 26.1.2 → 26.2 source comparison | Verdict |
|---|---|---|
| Speed | `MobEffects.SPEED` applies a `0.2F` `ADD_MULTIPLIED_TOTAL` modifier to `MOVEMENT_SPEED` in both. No direct `SPEED` effect branch occurs in the examined `LivingEntity`, `Player`, or `LocalPlayer` sources. `Player.aiStep()` reads `MOVEMENT_SPEED` and copies it to the movement-speed field used by local movement; `LivingEntity.getSpeed()` returns that field. | No change in modifier or local movement consumer. |
| Slowness | `MobEffects.SLOWNESS` applies `-0.15F` with `ADD_MULTIPLIED_TOTAL` to `MOVEMENT_SPEED` in both; as for Speed, `Player.aiStep()` copies the resulting movement-speed attribute into the local movement-speed field. | No change in modifier or local movement consumer. |
| Jump Boost | `LivingEntity.getJumpBoostPower()` uses `hasEffect(JUMP_BOOST) ? 0.1F * (amplifier + 1.0F) : 0.0F` in both. `LocalPlayer`'s auto-jump obstacle check uses `jumpHeight += (amplifier + 1) * 0.75F` in both. | No change in effect gate, primitive math, or order. |
| Slow Falling | `getEffectiveGravity()` tests `deltaMovement.y <= 0.0` before `hasEffect(SLOW_FALLING)`; when both hold it returns `Math.min(getGravity(), 0.01)`, otherwise gravity. | No change in gate or math. |
| Levitation | Air travel reads the effect once; if non-null it applies `movementY += (0.05 * (amplifier + 1) - movement.y) * 0.2`, otherwise it enters the ordinary gravity/client-chunk branches. | No change in read, branch order, or double-precision literals/math. |
| Dolphin's Grace | Water travel computes sprint/default slowdown and water-walker adjustments first, then overrides slowdown to `0.96F` when the effect is present, before `moveRelative` and drag application. | No movement-semantic change; 26.2 names the same float constant. |

## Exact inputs and qualifications

Both inputs are the canonical local `decompiled_minecraft/<version>/unobfuscated/` trees, mapping namespace `unobfuscated`; mapping family is therefore published/unobfuscated source, not Yarn or Mojmap.

- **26.1.2:** ready marker SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`; marker says `status=ready`, `versionId=26.1.2`, `mapping=unobfuscated`, 6,882 source files, and source-manifest SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`. Actual manifest file hash matches. The manifest-listed client artifact SHA-256 is `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`. Provenance records Vineflower 1.12.0, Java 25.0.3, the exact version run, and `successLogKind=full-gradle-output`; provenance file SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`.
- **26.2:** ready marker SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`; marker says `status=ready`, `versionId=26.2`, `mapping=unobfuscated`, 7,055 source files, and source-manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Actual manifest file hash matches. The manifest-listed client artifact SHA-256 is `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`. Provenance file SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` declares `successLogKind=summary-excerpt-only` and `mappingArtifacts=null`. Accordingly, the ready marker and source manifest plus matching per-file digests support identity/integrity for the exact reviewed local source files, but provenance does **not** establish a complete Gradle run log or complete mapping-artifact input record for 26.2. The source comparison is bounded to these locally published files; it does not upgrade that provenance claim.

The source-manifest entries below were checked against fresh SHA-256 hashes of the corresponding files. `MobEffects.java` and `AttributeMap.java` have identical digests across the pair; the changed `LivingEntity.java`, `LocalPlayer.java`, `Player.java`, `MobEffect.java`, `MobEffectInstance.java`, and `AttributeInstance.java` digests are retained below so this verdict binds to the actual pair contents rather than assuming whole-file identity.

| Relative source file | 26.1.2 SHA-256 | 26.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| `net/minecraft/world/effect/MobEffects.java` | `532df5c7cd47edd3d9d091c2eae0962c279224c8f5f206860e9df45bc3b0c684` | `532df5c7cd47edd3d9d091c2eae0962c279224c8f5f206860e9df45bc3b0c684` |
| `net/minecraft/world/effect/MobEffect.java` | `5412fe3210df040882f2b5860a9fb3e1d66de151453740ebcdeaa22374acf28c` | `07f7c99dc3a6d00d684f5ef88d1861fcada7fe83f80c718bb172ac33056d5555` |
| `net/minecraft/world/effect/MobEffectInstance.java` | `f7bba4691d48273990eae848238189f84ff4f8de6807961c929c7faefbf6a321` | `a9bfa80bbfeb7e5fcb561fad90d464112da5f9e63cea581cda0072ae28eb5af7` |
| `net/minecraft/world/entity/ai/attributes/AttributeMap.java` | `675b87b1757a73c6354db2dcfe52ec65c903bd9b3f5fcb97a648be252ea3f7f6` | `675b87b1757a73c6354db2dcfe52ec65c903bd9b3f5fcb97a648be252ea3f7f6` |
| `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `ca262f3d973642a2887b235a549b14f335706413f9551b417059e72e6bcf8cfd` | `c7cbe38bfdc1b4aafcf623bd1b604200cc6190641091369ee9bb9303e8f07ad5` |

## Exact source locations

All paths below are relative to each version's `unobfuscated/` root; line ranges refer to the decompiled files whose hashes are listed above.

- `net/minecraft/world/effect/MobEffects.java`: Speed lines 15–21; Slowness lines 22–28; Jump Boost lines 50–56; Levitation line 94; Slow Falling line 105; Dolphin's Grace line 107. Speed, Slowness, Jump Boost, Levitation, Slow Falling, and Dolphin's Grace declarations are the same in both versions.
- `net/minecraft/world/entity/LivingEntity.java`: effect storage/read/add/update/remove flow lines 976–1113 (26.1.2) and 997–1134 (26.2); jump effect lines 2344–2348 / 2381–2385; effective gravity lines 2388–2389 / 2426–2427; air levitation branch lines 2430–2448 / 2468–2488; water Dolphin's Grace branch lines 2467–2482 / 2516–2531; effect-dependent fall/flying gates lines 3066 and 3149 / 3133 and 3216. `26.2` also declares `DOLPHINS_GRACE_WATER_DRAG = 0.96F` at line 203.
- `net/minecraft/client/player/LocalPlayer.java`: Jump Boost use in the local auto-jump obstacle check lines 1039–1040 in both releases.
- `net/minecraft/world/entity/player/Player.java`: `aiStep()` copies `(float)getAttributeValue(MOVEMENT_SPEED)` to `speed`, lines 456–457 in both versions. `net/minecraft/world/entity/ai/attributes/AttributeMap.java` and `AttributeInstance.java`: adjacent attribute storage/modifier machinery was included as a dependency check; relevant methods have source-hashed evidence above. No extra conclusion about all attribute semantics is asserted.

## Audit boundary

This is a bounded independent source audit of the six named effects' ordinary player movement consumption, effect lifecycle ordering, adjacent attribute-modifier declarations, and primitive/order details for 26.1.2 versus 26.2. It excludes packet receiver/writer semantics, effect-source creation/application outside the local movement consumer, custom movement systems, health/food, and unrelated movement differences in the larger source files. It does not infer the patch at which any behavior first changed, nor establish runtime parity.

Frozen tool/report input SHA-256 (read as bytes only; content not consulted): `0a5c3404dcf354d79d5b28e66b26bc97ffd198198a37c367a774a81df41031f7`.



