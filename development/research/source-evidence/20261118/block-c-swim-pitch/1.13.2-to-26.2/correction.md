# Correction: pitch lookup can change swimming vertical input

## Bound identities

This correction supplements, and does not modify, the author report whose exact SHA-256 is `676d98d08a99735c2cca1c5e5642945d0d8806f1dedc39af741d5e4c00ded76d` (`report.md`). It is bound to the independent audit `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-swim-pitch-vertical-1132-262-destination-audit\verdict.md`, SHA-256 `4eca17c836a3d57c941fd6a155a5b5c494648047b4d0a916b4f9b0c1ec44580a`.

## Correction to the original claim

The ordinary dry-to-water local-player setup and the matching swimming gate/sample can be reachable. The original report went too far in saying the look-vector vertical helpers were source-equivalent and in treating the same trig operation as proof the direct vertical result was equal for this unspecified pitch. The two releases use different sine lookup arithmetic and table construction. Equality at the audit's `-30.0F` example is only witness-specific; the original report did not fix a pitch, so its described setup alone establishes no equal look-Y result.

## Reproduced legal-pitch counterexample

Use current player pitch `-38.594970703125F` (within the ordinary `[-90F,90F]` pitch range), any yaw, initial `velocityY = +0.0D`, `jumping == false`, and the same active swimming/non-riding local-player state. Choose an interior position in a vanilla water cell so the old sampled block state's fluid state and the modern sampled fluid state are both nonempty. The fluid predicate then makes the final OR gate true on both releases. Both look-Y values are greater than `-0.2`, so both choose `0.06D`.

The source operations produce:

| Stage | 1.13.2 Ornithe Feather | 26.2 unobfuscated |
| --- | ---: | ---: |
| Pitch radians | `-0.6736093163490295F` | `-0.6736093163490295F` promoted to double for `Mth.sin(double)` |
| LUT-scale product | float `-7025.99951171875F` | double `-7026.00002992212D` |
| Truncated index, then low 16 bits | `58511` | `58510` |
| Look-Y float | `+0.6237360835075378F` | `+0.6238110065460205F` |
| Direct `velocityY` result from zero | `+0.037424165010452266D` | `+0.03742866039276123D` |

The modern-minus-old vertical-velocity difference is `+0.000004495382308962459D` before inherited movement. These results follow the actual component sign: the lookup gives sine of the negative pitch radians, and each rotation-vector method negates that sine to form Y. For 1.13.2 the index uses float multiplication by `10430.378F` before truncation. For 26.2 the float radians are promoted to double, multiplied by `10430.378350470453`, and converted to `long` before masking. Table entries also come from different expressions: `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)` versus `(float)Math.sin(i / 10430.378350470453)`.

The direct velocity update itself retains the same gate structure and double expression for this witness: old `velocityY + (lookY - velocityY) * 0.06D`, modern `movement.add(0.0, (lookAngleY - movement.y) * 0.06D, 0.0)`. Both perform it before inherited input acceleration and the real move in their respective ordinary swimming paths. The fluid sampling expressions use `x, y + 1.0 - 0.1, z`; 1.13.2 constructs `new BlockPos(...)` and reads the block state's fluid state, while 26.2 uses `BlockPos.containing(...)` and reads the level fluid state. With the sample kept inside the same water cell, both predicates are true. These similarities do not erase the pitch-dependent LUT difference.

## Source binding

Exact source identity is the same pair cited by the bound report: 1.13.2 Ornithe Feather and 26.2 unobfuscated, each with ready-marker source-manifest SHA matching its source manifest. The directly relevant trig files are:

- 1.13.2 `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/util/math/MathHelper.java`, SHA-256 `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69`; `sin(float)` and `SINE_TABLE` construction.
- 26.2 `decompiled_minecraft/26.2/unobfuscated/net/minecraft/util/Mth.java`, SHA-256 `30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17`; `sin(double)` and `SIN` construction.
- Player call sites and their exact hashes remain pinned in the bound report: 1.13.2 `PlayerEntity.java` (`4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`) and 26.2 `Player.java` (`8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`).

## Revised conclusion

The direct swimming correction's branch, fluid gate, constants and update order match for the selected ordinary local-player path, but look-Y is not generally source-equivalent. The legal pitch above is a reachable source-level counterexample with a nonzero direct movement delta. The original `-30.0F` arithmetic equality remains a valid single-input observation; the original broad equivalence claim is corrected. This is a source witness only, not runtime parity or full-pair coverage.
