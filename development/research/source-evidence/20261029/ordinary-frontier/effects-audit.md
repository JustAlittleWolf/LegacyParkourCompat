# Independent audit of 1.17.1 → 1.18.2 effect/attribute/equipment/pose closure

## Verdict

**The author report's bounded no-movement-difference conclusion is independently supported for the reviewed input and consumer methods.** No pair freeze or whole-pair closure is implied. Existing full-pair audit already records other open movement deltas and dependencies.

This review was source-only and used the canonical read-only Mojmap trees plus the existing full-pair ledger. I wrote `independent-conclusion.md` before reading the assigned closure report. That prior conclusion SHA-256 is `06feb372f9acc7225f0dbbcaed1dce8cad53a98c07604a89140b5b69fb6c35d1`.

## Author input integrity and comparison

- Author `report.md` SHA-256: `dcb46ac947333dec91d9cf4e24891143e3aa2f81904c7f3b45a02db8c1487a05`.
- Author `source-hashes.tsv` SHA-256: `216117b5bc6833a279bc2ca6807e3a830d9d329f29fa0b199fbad2908696beef`.
- Independently recomputed all 48 rows in the author's selected-source hash ledger against the canonical exact-release paths: **48 checked, 0 mismatches**.
- Reused full inventory inputs: `inventory.md` SHA-256 `8379db16e7e252f6c140d2dce33bb8560493e4279be98abdcbcb284c302f5e1a`; `java-path-classification.tsv` SHA-256 `11f7bc1d88b5947b0de13962dee93be8339028165f2d083b1448078f1bbae3ff`.

## Claim audit

| Author claim | Verdict | Independent evidence / limit |
|---|---|---|
| Client effect add/remove handlers apply received active effect state in the same order | **PASS** | Independently retraced both `handleUpdateMobEffect` and `handleRemoveMobEffect`: same-thread gate, entity/type lookup, effect ID resolution and null case, constructor payload fields, no-counter flag, `forceAddEffect`, and `removeEffectNoUpdate`. Exact methods at old 1387/1562 and new 1329/1490. |
| Effect duration, hidden-effect promotion, and movement effect readers match | **PASS for reviewed methods** | Compared `MobEffectInstance.tick`, `tickDownDuration`, hidden fallback and `LivingEntity` jump boost/slow falling/levitation/Dolphin's Grace/Depth Strider consumer gates. Same state updates and numeric order. In particular, promotion occurs only after duration reaches zero, then copies hidden details and calls the same callback. This does not cover packet serialization or every effect state acquisition path. |
| Server-produced effect attribute modifiers are not client-side movement state | **PASS** | `LivingEntity.onEffectAdded`, `onEffectUpdated`, and `onEffectRemoved` all apply effect attribute modifiers only under `!level.isClientSide` at both endpoints. Client effect receipt and received attribute snapshots are separate writes. No server producer behavior is imported into this client source conclusion. |
| Received attribute snapshots replace existing modifiers in the same order; effective-value arithmetic matches | **PASS** | `handleUpdateAttributes` performs entity/type lookup, instance lookup, missing-attribute warning, base replacement, modifier clearing, then transient-modifier addition in both. `AttributeInstance.calculateValue` retains base + addition, multiply-base against that sum, sequential multiply-total and sanitize order. |
| Received equipment, depth-strider and Soul Speed consumers retain behavior | **PASS for checked paths** | `handleSetEquipment` still applies packet slot/item pairs in iteration order; depth-strider lookup still delegates to the same enchantment-level helper; `tryAddSoulSpeed` uses the same non-air support gate, enchantment/eligible-block gates, movement-speed additive modifier, and durability condition. Upstream server packet production and all item/tag providers remain out of scope. |
| Movement speed, ability and pose/dimension consumers match | **PASS for checked methods** | `Player.getSpeed` remains a float cast of `getAttributeValue(MOVEMENT_SPEED)`. The received abilities handler assigns the same flying/instabuild/invulnerable/mayfly fields and flying/walking speed values. `Player.updatePlayerPose` preserves candidate priority and fallback order. `Entity.canEnterPose` retains the same deflated candidate-box no-collision check; `refreshDimensions` retains its established resize/position behavior. No complete collision-provider proof is claimed. |
| No relevant ambient-effect change | **QUALIFIED** | The author report does not call out the changed ambience aggregation. Independent review found `LivingEntity.areAllEffectsAmbient`: 1.17.1 returns false for any non-ambient active effect; 1.18.2 ignores non-visible effects. This only feeds effect particle/ambience metadata in the inspected path, not the reviewed movement predicates, attributes, equipment, pose, or collision gates. It is a real source difference but not a movement delta in this scope. |
| Bounded closure proves pair completion | **NOT CLAIMED** | Author report correctly limits the result. Packet payload encoding/order, entity attribute defaults, other changed class members, client inventory paths, providers/collision, fluids/blocks, and remaining full-pair coverage are open. The existing independent full audit's fall-flying precision and sprint-stop findings remain separate. |

## Disposition

Accept the author report only as a bounded consumer/source closure for 1.17.1 → 1.18.2. Preserve the ambience aggregation change as a non-movement source note. Do not mark the exact pair frozen from this closure. This audit binds the immutable author artifacts and independently written pre-comparison conclusion named above; edits to any of them require renewed review.
