# Independent source audit: Elytra glide versus climbable handling, 1.15.2 → 1.16

## Verdict

**Behavioral claim supported; frozen report needs a provenance correction.** The bounded no-difference claim is supported for an ordinary local player in a dry state, already fall-flying, not grounded or riding, with an enabled Elytra in the chest slot and ladder or vine at the feet, with no Levitation effect. Both exact Mojmap sources preserve the updater-before-travel order, the client-side flag authority, dry-glide precedence over the ordinary climbable movement branch, and the glide threshold, write, arithmetic order and move call. However, the frozen report's stated SHA-256 for the 1.16 ready marker is incorrect (details below). Therefore the behavioral claim is independently supported, but the report's evidence identity is not accepted exactly as written until its marker hash is corrected. This does not freeze the 1.15.2→1.16 pair or establish runtime parity.

## Independent trace and A/B evidence

The trace is bounded from player tick into the movement operation and to its final travel movement call. In both releases `Player.tick()` calls `super.tick()`; `LivingEntity.tick()` then invokes `this.aiStep()` (A: 2103; B: 2168). `Player.aiStep()` calls `super.aiStep()` (A: `Player.java` 514; B: `Player.java` 500), reaching `LivingEntity.aiStep()`. That method calls `updateFallFlying()`, captures the bounding box, then calls `travel(...)`. Thus the compared operation is reached in the normal player tick path. The endpoint here is the glide branch's `move` call; later same-tick bookkeeping and outer game-loop scheduling are outside this audit.

| Slice | 1.15.2 Mojmap (A) | 1.16 Mojmap (B) | Audit result |
| --- | --- | --- | --- |
| Player/upstream tick | `world/entity/player/Player.java`: `tick` 210–237; `aiStep` calls `super.aiStep()` at 514. `world/entity/LivingEntity.java`: `tick` begins 2024 and invokes `aiStep()` at 2103; `aiStep` calls updater at 2285 and then `travel` at 2287. | `world/entity/player/Player.java`: `tick` 207–234; `aiStep` calls `super.aiStep()` at 500. `world/entity/LivingEntity.java`: `tick` begins 2122 and invokes `aiStep()` at 2168; `aiStep` calls updater at 2455 and then `travel` at 2457. | Same relevant call order. |
| Active-flight state and updater authority | `LivingEntity.java` 2299–2317 reads shared flag 7, validates not-grounded/not-passenger and exact `Items.ELYTRA` plus `ElytraItem.isFlyEnabled`; only `!level.isClientSide` writes the computed `bl` back with `setSharedFlag(7, bl)`. `isFallFlying()` reads shared flag 7 at 2690–2691. | `LivingEntity.java` 2476–2494 has the same reads, checks and server-only write, with `!hasEffect(MobEffects.LEVITATION)` added to the validation gate. `isFallFlying()` reads shared flag 7 at 2875–2876. | With the report's no-Levitation precondition, the new gate is true and changes no value. On the logical client the updater does not write `bl` to flag 7, so the already-true flag remains the travel selector. The additional condition is an updater gate, not a client flag clear in this bounded case. |
| Elytra usability | `world/item/ElytraItem.java`, `isFlyEnabled` 19–20: damage value `< max damage - 1`. | `world/item/ElytraItem.java`, `isFlyEnabled` 17–18: same strict comparison. | Same enabled-item boundary. |
| Feet/climbable predicate and ordinary cleanup | `LivingEntity.java` 1226–1235: `onLadder()` reads feet state and directly accepts `Blocks.LADDER` and `Blocks.VINE`. `travel` 1885–1895 enters the normal branch only when not fall-flying; that branch invokes `handleOnClimbable` at 1890 and later applies the horizontal-collision/jump ladder response at 1893–1895. | `LivingEntity.java` 1361–1375: `onClimbable()` reads feet state and checks the climbable tag or usable trapdoor. `travel` 2020–2024 enters the ordinary branch only when not fall-flying; its helper `handleRelativeFrictionAndCalculateMovement` calls climbable cleanup at 2060–2066. | The active glide branch bypasses these ordinary cleanup and response calls. In A, ladder/vine membership is explicit in the predicate. In B, the predicate uses the climbable tag; this verdict concerns the stated vanilla ladder/vine feet case, not tag-resource changes generally. |
| Dry travel branch and final glide move | `LivingEntity.java` 1840–1846 passes water/lava gates for a dry player, tests fall-flying, captures current delta movement, and writes `fallDistance = 1.0F` only when `y > -0.5`. Glide arithmetic follows at 1848–1870 in the stated order; `move(SELF, getDeltaMovement())` is at 1871. | `LivingEntity.java` 1921–1922 queries fluid state and enters the water branch only if in water and affected by fluids and unable to stand on that fluid; lava is similarly gated at 1957. Dry travel reaches the fall-flying branch at 1977. It captures delta and applies the same strict `y > -0.5` / `1.0F` write at 1978–1981. Glide arithmetic follows at 1983–2005 in the same order; `move(SELF, getDeltaMovement())` is at 2006. | For the dry case, glide precedes the normal climbable branch in both versions. Equality `y == -0.5` does not write `fallDistance`; values strictly greater do. The final in-scope travel operation is the same movement call after the same ordered glide calculation. |

The 1.15.2 and 1.16 glide bodies match operation by operation: look-angle horizontal length; horizontal speed from the captured velocity; look-vector length; cosine/pitch scaling with `Math.min(1.0, lookLength / 0.4)`; gravity adjustment; downward-look lift; upward-pitch adjustment; horizontal speed correction; `multiply(0.99F, 0.98F, 0.99F)`; then `move(MoverType.SELF, getDeltaMovement())`. Local variable names differ. The client-only consequence is supported by the updater's server-only shared-flag write; server fall damage and grounded flag cleanup are gated to `!level.isClientSide` and are outside this local-player movement comparison.

## Exact evidence identity

Frozen claim input:

- Report: `c-glide-climb-1152-116-alias-gate/report.md`, SHA-256 `0a699ed1a52bd2f6df1f853240c8ddb7dad557d07e6fb012e195a51c75502b5c`.

Canonical source identities, independently checked against each ready marker and source manifest:

- `decompiled_minecraft/1.15.2/mojmap`: exact version `1.15.2`, mapping `mojmap`; source-manifest SHA-256 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; ready-marker SHA-256 `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0`.
- `decompiled_minecraft/1.16/mojmap`: exact version `1.16`, mapping `mojmap`; source-manifest SHA-256 `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a`; actual ready-marker SHA-256 `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2`.

The frozen report instead gives the 1.16 ready-marker SHA as `5b6cb24f95e83b856c53f646d6477f2edae2276737dee56dd07c5bd745e4a2`. It differs from the independently computed marker hash above. The marker's embedded source-manifest SHA does match the independently hashed manifest, and all four cited Java file hashes match their manifest entries and recomputed hashes; the observed defect is this marker-hash transcription only.

The four compared Java bodies match their source-manifest entries and independently recomputed SHA-256 values:

- 1.15.2 `LivingEntity.java`: `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`.
- 1.15.2 `ElytraItem.java`: `701e25f13e9ba8db74aca774f404a5282a58f4f7dc72e3aeabac76bbf28b43`.
- 1.16 `LivingEntity.java`: `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`.
- 1.16 `ElytraItem.java`: `d20a679b8822eaa57e5216ce14ed755c1f86a160eb5dad0dc9e3d8f4b5ea018d`.

## Limits

No runtime test or trajectory claim was performed. Levitation-enabled trajectories, non-dry travel, ground/passenger/invalid-Elytra states, server behavior, unrelated movement operations, and the full pair inventory remain outside this verdict. No source files were modified.
