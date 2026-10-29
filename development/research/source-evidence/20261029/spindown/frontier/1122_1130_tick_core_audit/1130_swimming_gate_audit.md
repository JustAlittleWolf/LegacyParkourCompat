# Independent audit: 1.13 swimming refresh gate

## Verdict and scope

The bounded source claim in `.native-source/20261028/frontier-c/1122_1130-tick-core/1.13.0-first-stable-boundary.md` (assigned SHA-256 `d62c20410908404a35d2554cc66ff03b66348eaa9fb2fb6d2bb7639ef7f687b7`) is supported by the canonical read-only Feather sources for 1.13, 1.13.1 and 1.13.2. I independently checked release/mapping identities, manifest pointers and selected source hashes, then retraced the stated methods and their tick call order. This is a bounded finding audit; it does not claim pair freeze, complete tick coverage, implementation acceptance or runtime parity. Collision/auto-jump aggregation remains excluded from this review.

## Source identity and integrity

All three release-parent ready markers identify the release metadata and `ornithe-feather` source tree. The companion source and artifact manifests exist beside the mapping-root folder; their file hashes match the ready-marker declarations. The selected source hashes below were recomputed from files and match the source manifest entries.

| Release | Ready identity | Ready marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Provenance SHA-256 |
| --- | --- | --- | --- | --- | --- |
| 1.13.0 (metadata ID `1.13`) | mapping `ornithe-feather`; Feather Gen2 `1.13+build.2` | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `a2c17075b601d9b83703f7ee188b04c6d9f68cac08d97b1a4f838fe8cd2e2c24` | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` |
| 1.13.1 | mapping `ornithe-feather`; Feather Gen2 `1.13.1+build.2` | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `471177ba7bcff332e93ab5bf0127d5c689c3d34fe4f826976207ccfa3bf7b51c` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` |
| 1.13.2 | mapping `ornithe-feather`; Feather Gen2 `1.13.2+build.2` | `1d1c644c1de5b0c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` |

The mapping coordinates and namespace are present in the .0/.1 provenance records; the .2 provenance names the mapping artifacts, and its release-parent `artifacts.sha256` manifest supplies their hashes. The identity note in the audited report is accurate. One precision correction: the 1.13.2 ready marker does not contain a `provenanceSha256` field, so its provenance file's actual hash matches the audit report's computed value but is not anchored by a ready-marker declaration. The .2 marker does bind the source and artifact manifest hashes listed above.

Recomputed `PlayerEntity.java` SHA-256 values, each matching its source manifest: 1.13 `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd`; 1.13.1 `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272`; 1.13.2 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`. `Entity.java` likewise matches the report's hashes: 1.13 `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642`; 1.13.1 and 1.13.2 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.

## Method and call-order retrace

- `PlayerEntity.updateSwimming()` in 1.13 (source lines 1464–1471) first constructs `BlockPos(this)` and its upper position. Flying clears swimming. Otherwise, it invokes `super.updateSwimming()` only when `!isSwimming()` or when `doesNotSuffocate(currentPos)` and the upper block material does not block movement. `doesNotSuffocate(pos)` checks that the current block is not solid and the upper block is not solid. Thus, when already swimming, a solid or movement-blocking upper position can short-circuit the inherited recomputation. The audited report describes the gate and its order correctly.
- In 1.13.1 and 1.13.2, the same override retains the flying branch but every non-flying call invokes `super.updateSwimming()`; the position/material gate is absent.
- `Entity.updateSwimming()` is text-identical across all three releases. If already swimming it sets the flag from `isSprinting() && isInWater() && !isRiding()`; otherwise it requires `isSprinting() && isSubmergedInWater() && !isRiding()`.
- The call site is the private `Entity.m_03231680()`: `checkWaterState()`, then `m_61708540()` (assigning `submergedInWater = isSubmergedIn(WATER)`), then virtual `updateSwimming()`. `Entity.tick()` calls that helper after `tickSprintingEffect()`. This establishes that a `PlayerEntity` instance dispatches through the override after those water-state refreshes.
- `PlayerEntity.moveRelative()` (1.13 lines 1435 onward; .1/.2 lines 1442 onward) has an unchanged swimming-and-not-riding branch that reads look-vector Y and may write vertical velocity as `velocityY + (lookY - velocityY) * h`, where `h` is `0.085` for lookY below `-0.2`, otherwise `0.06`. The write is gated by looking downward/nonpositive, jumping, or fluid above. It is a valid downstream movement consumer of the retained/cleared swimming flag; no runtime trajectory is inferred.

A concrete source-level consequence follows for a player already swimming and no longer sprinting: in 1.13, if the upper-position gate fails, the parent updater is skipped and the swimming flag is retained for that refresh; in .1/.2 the parent updater runs and clears the flag. That state difference can alter whether the swimming-specific `moveRelative` velocity adjustment runs.

## 1.13.1 → 1.13.2 roots

The selected movement roots `LocalClientPlayerEntity`, `ClientPlayerEntity`, `KeyboardInput`, `Input`, `Entity`, and `LivingEntity` have identical .1/.2 source hashes. `PlayerEntity.java` differs only at the NBT `DataVersion` constant (`1628` → `1631`); its `updateSwimming`, `moveRelative`, `Entity.updateSwimming`, and water-refresh helper bodies compare identical between .1 and .2. This supports the report's bounded assertion that the swimming boundary is .0→.1 and that no new .1→.2 movement-root delta was found in these slices.

No assertion is made about the excluded auto-jump collision/shape dependency, pair-wide closure, or runtime behavior.
