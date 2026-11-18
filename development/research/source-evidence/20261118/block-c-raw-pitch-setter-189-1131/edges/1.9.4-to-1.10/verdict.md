# Independent source audit: local pitch update, 1.9.4 → 1.10

## Verdict

**No source-confirmed movement delta in the assigned operation.** Reject a claim that the ordinary direct local-camera pitch update changed between these endpoints. Both versions save the old current pitch, update current pitch with the same double-intermediate expression, clamp current pitch, and only then add the clamped current-pitch difference to `lastPitch`. The float clamp helper is byte-identical across these source trees. The audited Elytra movement consumer reads the current stored pitch through the same local, dry, active-gliding path in both versions.

This is a bounded verdict on this operation and consumer, not a complete 1.9.4–1.10 pair freeze.

## Exact source identities and byte checks

- **1.9.4 / Ornithe Feather:** version-root `ornithe-feather.ready.json` says `ready`, exact version `1.9.4`, mapping `ornithe-feather`, and records raw client SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`. `ornithe-feather.provenance.json` records the Feather mapping option and build.2 merged-v2/Tiny names. The pinned mapping artifact hashes in `artifacts.sha256` are merged-v2 `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125` and Tiny `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`. The ready marker's source-manifest and artifact-manifest SHA-256 values match the current manifests.
- **1.10 / Ornithe Feather:** mapping-root `.decompile-provenance.json` pins the exact 1.10 version metadata URL/hash, client URL/hash, Feather 1.10 metadata hash `a1eb1ccff25e2b76fa1d08e9b515508e97ac4837fbcacbd7f77b3a01026d3950`, and Feather Gen2 `1.10+build.2` merged-v2 JAR hash `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd`.
- Current Java bytes match their source manifests: 1.9.4 `Entity.java` `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`, `LivingEntity.java` `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`; 1.10 `Entity.java` `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`, `LivingEntity.java` `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`. `MathHelper.java` is byte-identical in both trees: `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151`.

## Pitch update arithmetic

- 1.9.4 `net/minecraft/entity/Entity.java`, `updateLocalPlayerCamera(float,float)`, lines 286–297.
- 1.10 `net/minecraft/entity/Entity.java`, same method, lines 291–302.

The compared statements are identical: `float f = this.pitch`; `this.pitch = (float)(this.pitch - pitch * 0.15)`; `this.pitch = MathHelper.clamp(this.pitch, -90.0F, 90.0F)`; `this.lastPitch = this.lastPitch + (this.pitch - f)`. The unsuffixed `0.15` is a Java `double` literal, so subtraction and multiplication use double precision before the cast stores the current value as float. The subsequent delta and addition to `lastPitch` are float operations. `MathHelper.clamp(float,float,float)` is the same ordered `< min`, then `> max`, else-return implementation at lines 80–85 in both versions.

Finite width witness, initial `pitch=0F`, `lastPitch=0F`, input delta `-599.96954345703125F`: the pinned expression stores current pitch `89.99542999267578F` (within the clamp bounds), and then stores the same value in `lastPitch`. If the multiply were first rounded as float using `0.15F`, current pitch would instead be `89.99543762207031F`. This confirms that the source's double intermediate is observable for finite inputs; it is not a release difference.

Finite clamp-order witness, initial `pitch=0F`, `lastPitch=0F`, input delta `1000F`: the raw current result is `-150F`; current pitch is clamped to `-90F` before the previous-angle update, so `lastPitch` becomes `-90F`, not `-150F`. No clamp is applied to `lastPitch` itself. This order is identical in both versions.

## Stored-pitch Elytra consumer trace

For a non-riding local player in dry conditions with fall-flying active, 1.10's local tick calls `LivingEntity.moveRelative` (1.10 `LivingEntity.java`, line 1747), through `PlayerEntity.moveRelative` (lines 1391–1405). `LocalClientPlayerEntity.isLocallyControlled()` returns true (lines 457–460). The inherited movement method enters its gliding branch only after the local-control-or-logical-update gate, both water/lava checks (unless player abilities are flying), and `isFallFlying()` (lines 1332–1342). The branch reads `this.pitch` directly as `float i = this.pitch * (float)(Math.PI / 180.0)` and also obtains `this.getLookVector()`.

`LivingEntity.getLookVector()` returns `this.getRotationVector(1.0F)` (1.10 lines 1852–1855); `Entity.getRotationVector(1.0F)` passes current `this.pitch` and `this.yaw` directly (lines 1138–1145). Thus this gliding movement consumer uses current stored pitch, not `lastPitch` interpolation. The corresponding 1.9.4 `LivingEntity.moveRelative` gliding branch (lines 1302–1341) and `getLookVector` (lines 1817–1819) have the same relevant gates, pitch read, and operation sequence.

A reachable local start request is visible in 1.10 `LocalClientPlayerEntity.java`, lines 730–734: while jumping, airborne, descending, not already fall-flying, and not flying, an Elytra that `ElytraItem.canFly` accepts triggers `START_FALL_FLYING`. This is consistent with the active flag path; it does not change the conclusion that stored-pitch consumption is unchanged across the pair.
## Phase two comparison

Rehashed and compared the authorized frozen source report (`report.md`, SHA-256 `35b63c728f9283fd5d43aa4c6488eeB08c93e6f86d1762aeb220765e17d00448`) against this audit. The report's bounded verdict and evidence agree with mine: the direct setter uses the same double-intermediate current-pitch update, clamps before adjusting `lastPitch`, and the active dry Elytra consumer reads the current stored pitch with the same relevant gates and sequence in both releases. I found no contradictory claim requiring a verdict change. The report records the 1.10 nested `.decompile-provenance.json` identity and explicitly notes the missing version-root readiness markers; my original verdict claimed nested manifest/source pins only and did not claim version-root readiness, so this caveat is consistent with the coordinator's acceptance of that exact nested identity.