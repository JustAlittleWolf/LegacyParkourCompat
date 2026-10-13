# Independent blind source review — MC1204-1206 boundary — 2026-10-08

## Decision and exact binding

**ACCEPT for the bounded source-boundary claims in this memo.** The immutable memo is commit `e277edd19eb8f8ce63681ab7985a5d92df8a259f`, path `workflows/fix-implementation/reconciliations/MC1204-1206-boundary-2026-10-08.md`, Git blob `cda4a40cadde768c36b5523cc5c11a3a56adc78f` (8,841 bytes), raw-byte SHA-256 `a97e20b5abaa131f9ec090d1d186eae63592d839e8cd9c3c254b80acdea8de82`. The digest was recomputed from the exact committed blob.

This review verifies only the memo's stated source boundaries across 1.20.4, 1.20.5, and 1.20.6. It does not re-adjudicate the prior accepted endpoint findings MC1204-1206-01 or MC1204-1206-02, does not select implementation behavior, and does not close pair discovery or claim runtime parity.

## Ready source and artifact identities

For each release, the ready marker's embedded source-manifest and artifact-manifest digests match the exact files on disk. The marker, source-manifest, and artifact-manifest raw SHA-256 values are:

| Release | Ready marker | Source manifest | Artifact manifest |
|---|---|---|---|
| 1.20.4 | `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1` | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` |
| 1.20.5 | `4a587caa86e4ddd68ba63a5431417933dc4df4822e33aaf29ce77ebda53947c2` | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` | `f2dbd7a2e6c3fb452b0c28c93c9815e43ca24c179815f3d4fca34e484d465bb6` |
| 1.20.6 | `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41` | `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` | `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31` |

I recomputed the six cited vanilla source-file hashes in each release against the corresponding manifest rows. Every row matches. This includes the newly published 1.20.5 Mojmap tree: `LivingEntity.java` `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`, `LocalPlayer.java` `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`, `Player.java` `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`, `Entity.java` `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`, `Mth.java` `f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d`, and `Vec3.java` `dc05337c94e64d5c137138ef98b7f10768a420af23bffb0812b68a8320c7c813`.

The 1.20.5 publication is bound to metadata ID `1.20.5`. Its provenance digest is `1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242`; the original client JAR (`49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510`), official client mappings (`9afdb3b7fcc4c6acbec3bc6ccb46192bfc6460e12bf9e268e29bfee46bc7fbac`), and mapped client JAR (`39494c7ed61b0ddda63b79ff2477c1a1c33d3770fa83321c2500db116d07efad`) rehash to their artifact-manifest rows and provenance entries. The exact source rows also match that release's source manifest, so the evidence is tied to the published tree rather than only to its ready label.

## Source boundary findings

### Jump-power cutoff

The 1.20.4 `LivingEntity.jumpFromGround()` reads current movement, calls `setDeltaMovement(x, getJumpPower(), z)`, conditionally adds sprint velocity, and then sets `hasImpulse`. The 1.20.5 and 1.20.6 methods first compute a float jump power and enter the movement-write branch only under `! (power <= 1.0E-5F)`. Equality is included in the skipped set; because the source negates the `<=` comparison, NaN follows the branch that performs writes. The 1.20.5 and 1.20.6 `LivingEntity.java` files are byte-identical by SHA-256. The source-level cutoff first appears in 1.20.5 within the inspected release interval.

The ordinary player route is reachable through `LivingEntity.aiStep()` when jump input is active, the on-ground or low-fluid condition is met, and `noJumpDelay == 0`; `Player.jumpFromGround()` delegates to the living-entity method in each release. This confirms the consumer and its call path, not that every threshold value is naturally produced.

**Input eligibility remains open.** The memo binds the 1.20.4 formula `0.42F * getBlockJumpFactor() + getJumpBoostPower()` and the 1.20.5/1.20.6 formula using `Attributes.JUMP_STRENGTH`, but it does not establish a vanilla 1.20.4 player producer or reachable old-era map state with jump power `<= 1.0E-5F`. The old formula reads block jump factors and jump-boost state; the newer formula additionally reads the newer generic jump-strength attribute. Do not project that newer attribute input onto old maps. Treat practical eligibility of the cutoff delta for a vanilla 1.20.4 player as an open dependency for later reconciliation. The conditional consumer boundary itself is correctly stated and remains accepted.

### Sprint-jump arithmetic

The sprint branch retains the same float angle computation in all three versions: yaw multiplied by `(float)(Math.PI / 180.0)`. The same float `Mth.sin(float)` and `Mth.cos(float)` lookup expressions feed the impulse. In 1.20.4, each trigonometric float is multiplied by float literal `0.2F` before widening to the double parameters of `Vec3.add(double,double,double)`. In 1.20.5/1.20.6, unsuffixed `0.2` makes the multiplication double. Both paths first write vertical jump velocity, then add the sprint vector to the current vector, then set `hasImpulse`; `Entity.addDeltaMovement(Vec3)` delegates to component-wise `Vec3` addition. The arithmetic difference is confined to the sprinting branch, and the memo preserves the cast and operation distinctions.

### Grounded flight-toggle jump

In 1.20.4, the mayfly double-tap branch toggles `flying`, updates abilities, and clears its trigger timer without calling `jumpFromGround()`. In 1.20.5/1.20.6, after the toggle, the method calls `jumpFromGround()` only when the resulting `flying` flag is true and the player is on ground; the call precedes the ability update and timer clear. The route requires the mayfly path, non-always-flying mode, a new jump press completing the double-tap window, non-swimming state, and activation of flight while grounded. The code also suppresses this toggle branch for the automatic-jump sample. The call dispatches through `Player.jumpFromGround()` to the living-entity method, so its actual movement writes remain conditional on the jump-power cutoff above. The call first appears in 1.20.5 and remains byte-identical in 1.20.6.

## Limits

These checks establish source correspondence and the three code-level cutovers only. They do not establish an exact intermediate snapshot date, a full 1.20.4–1.20.6 movement pair, low-power input availability in old vanilla maps, implementation registration, or runtime behavior. No implementation, reconciliation code, normal pair report, Wiki, MCPK material, test, build, or runtime path was inspected or run.