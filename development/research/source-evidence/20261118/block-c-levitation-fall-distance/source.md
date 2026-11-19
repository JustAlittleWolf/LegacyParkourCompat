# Levitation fall-distance writer fence

## Verdict

**Candidate-ready adjacent source fence: 1.12.2 → 1.13.0.** The Levitation effect branch in the regular non-water/non-lava/non-gliding movement route has no direct fall-distance write in exact 1.12.2, and writes `fallDistance = 0.0F` immediately after its vertical-velocity adjustment in exact 1.13.0. The same writer is present in 1.13.1 and 1.13.2. The `1.12`, `1.12.1`, and `1.12.2` slices are identical and have no Levitation-gated writer. Exact stable releases 1.9 through 1.11.2 (including all listed aliases) also have Levitation but no direct writer. This closes the requested adjacent release boundary for this operation; it does not supply an independent reviewer verdict or source-pair-wide audit.

## Exact adjacent evidence

Mapping: Ornithe Feather. Paths below are relative to `decompiled_minecraft/<release>/ornithe-feather/`.

| Release | Source SHA-256 for `net/minecraft/entity/living/LivingEntity.java` | Exact checked slice |
|---|---|---|
| 1.12.2 | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `moveRelative` 1515–1523: after movement, the `StatusEffects.LEVITATION` branch adjusts `velocityY`, then continues to the `else`; it contains no `fallDistance` write. |
| 1.13 | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` | `moveRelative` begins at 1459; local/authoritative route guard at 1460 and water branch at 1467. After `move(...)`, Levitation branch at 1561–1565 adjusts vertical velocity then writes `fallDistance = 0.0F` at 1564. |
| 1.13.1 | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | `moveRelative` 1478 onward; after `move(...)`, Levitation predicate and writer at 1580–1583. |
| 1.13.2 | same `LivingEntity.java` hash as 1.13.1 | Same Levitation predicate/writer at 1580–1583. |

The 1.12.2 Slow Falling field and lookup are absent in `net/minecraft/entity/living/effect/StatusEffects.java`; Levitation is registered. In 1.13, `StatusEffects.java` declares and resolves both effects (field declarations at lines 6 and 9; registry lookups at lines 52 and 55). Thus the 1.13.0 Levitation writer is a new reset operation for an already-existing effect, not an effect-introduction artifact.

## Pre-fence stable release coverage

The Levitation predicate and no-write gravity adjustment were checked in every exact available release below the fence. The relevant body hashes collapse into four groups; each listed identity has its own embedded provenance and release-specific client/Feather input hashes.

| Exact releases | `LivingEntity.java` SHA-256 | Levitation branch line slice |
|---|---|---|
| 1.9 | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` | 1383–1387 |
| 1.9.1, 1.9.2 | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | 1387–1391 |
| 1.9.3, 1.9.4 | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | 1396–1400 |
| 1.10, 1.10.1, 1.10.2 | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | 1426–1430 |
| 1.11 | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` | 1480–1484 |
| 1.11.1, 1.11.2 | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | 1482–1486 |
| 1.12, 1.12.1, 1.12.2 | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | 1519–1523 |

In every row the Levitation branch adjusts vertical velocity and contains no fall-distance write. Other direct fall-distance resets (for example the separate climb reset) are outside this operation and are not attributed to Levitation.

## Evidence identities

For 1.12.2 and 1.13–1.13.2, top-level ready metadata names the exact release and mapping and lists the source-manifest digest. The computed `LivingEntity.java` hashes above match their `*.sources.sha256` entries. 1.12 and 1.12.1 lack top-level ready markers; their mapping-local `.decompile-provenance.json` files pin the exact client and Feather artifacts and each records the matching `LivingEntity.java` hash above. The older 1.9–1.11.1 slices also lack top-level ready markers; their mapping-local provenance pins release-specific client/Feather inputs and the listed per-file source hashes. These are local provenance records; no online mapping/source lookup was used.

## Slow Falling boundary refinement

The exact 1.13.0 source already contains the Slow Falling direct writer, gated by `velocityY <= 0.0` and active Slow Falling before water-route selection (`moveRelative` 1459–1468). It remains in 1.13.1 and 1.13.2 (writer line 1483 in both). `StatusEffects` has no Slow Falling declaration/lookup in 1.12.2, and has it in 1.13.0. Therefore the earlier broad 1.13.2 lower-bound finding refines to the exact sampled feature release 1.13.0; no 1.13 patch boundary remains open for these three snapshots.

## Scope limits

- Before 1.9, the effect is absent; 1.8.9 `LivingEntity.java` SHA-256 is `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`, and its entity/living source tree has no `LEVITATION` predicate. No earlier release was searched.
- This does not establish intermediate patch behavior beyond the exact listed stable aliases, nor cover non-player/server routes, riding, gliding, other fall-distance writers, damage, or effect production.
- No product source, wiki, Git history, build, tests, or runtime was consulted.
