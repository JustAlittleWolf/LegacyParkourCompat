# F-GROUND-ACCEL — 1.13.2 changes the grounded acceleration scalar

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: TRAVEL-MODIFIERS, BLOCK-FRICTION, CLIENT-TICK.
- Classification: changed behavior.
- Confidence: source-confirmed (the exact source path and finding-specific dependencies below are closed; independent snapshot review is pending).
- Applicability: historical player movement while locally controlled, grounded, with nonzero movement input and speed, outside water/lava and fall-flying branches.
- First changed release: unknown within (1.12.2, 1.13.2].
- Runtime validation: not performed.

## Paired evidence

### A artifact identity

- Evidence artifact record ID: EA-FEATHER-R1-A.
- Publication status: revised-derived.
- Revision ID: feather-r1-2026-10-07.
- Immutable evidence path: revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/client-ornithe-feather.jar.
- Evidence artifact SHA-256: fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87.
- Evidence manifest path / SHA-256: revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/artifact.sha256 / 162170b94be0fa6393a515ea3beed10210b2c86a006366cf9d351eba136bae2d.
- Original artifact-manifest path / SHA-256: ready/1.12.2/artifacts.sha256 / 8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c.
- Original derived-artifact availability and SHA-256 or expected hash: unavailable; expected original mapped JAR SHA-256 65a08f15d18c4ec2bd0f05b89dfc1ba7ea6b8280ba93ed136245f39e63ec8a2b.
- Source/raw-input hash relation and verification reference: source tree and raw inputs are recorded identical to original in revision.json; the source tree and 37/37 original raw inputs except the unavailable derived JAR were independently rehashed, as recorded in run.md. Source-manifest SHA-256 b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da.
- Revised-to-original derived-artifact equivalence and evidence reference: unverified; original mapped JAR unavailable. Revision record SHA-256 2be8645d57ca5c00411b037e7617f9860ac700f7fa8ef50d28220c2e1b0c60dc.
- Provenance limitations: the revised immutable snapshot is verified, but original mapped-bytecode identity and metadata-only equivalence are unproven.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: ready/1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java; net.minecraft.entity.living.LivingEntity#moveRelative(FFF)V; grounded branch lines 1479-1496; SHA-256 190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6. `Block.java` default slipperiness is 0.6F at line 70; SHA-256 e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1.

### B artifact identity

- Evidence artifact record ID: EA-FEATHER-R1-B.
- Publication status: revised-derived.
- Revision ID: feather-r1-2026-10-07.
- Immutable evidence path: revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar.
- Evidence artifact SHA-256: b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c.
- Evidence manifest path / SHA-256: revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/artifact.sha256 / 4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40625601868692c7dd8.
- Original artifact-manifest path / SHA-256: ready/1.13.2/artifacts.sha256 / fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e.
- Original derived-artifact availability and SHA-256 or expected hash: unavailable; expected original mapped JAR SHA-256 d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5.
- Source/raw-input hash relation and verification reference: source tree and raw inputs are recorded identical to original in revision.json; the source tree and 41/41 original raw inputs except the unavailable derived JAR were independently rehashed, as recorded in run.md. Source-manifest SHA-256 2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211.
- Revised-to-original derived-artifact equivalence and evidence reference: unverified; original mapped JAR unavailable. Revision record SHA-256 a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5.
- Provenance limitations: the revised immutable snapshot is verified, but original mapped-bytecode identity and metadata-only equivalence are unproven.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: ready/1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java; net.minecraft.entity.living.LivingEntity#moveRelative(FFF)V; grounded branch lines 1538-1558; SHA-256 bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c. `Block.java` default slipperiness is 0.6F in the property default and all A-era registered friction values are enumerated at lines 1068-1949; SHA-256 735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4.

## Source-level difference

- A enters the ordinary land/air branch after its locally-controlled/logical-side, not-water, not-lava, and not-fall-flying guards. When `onGround`, it reads the block slipperiness, multiplies by `0.91F`, and computes `0.16277136F / (s * s * s)`; it then multiplies by `getSpeed()` and passes that value to `updateVelocity`. When not grounded, the branch uses `speedInAir` instead.
- B has the corresponding guards and ordinary branch. When `onGround`, it reads the block slipperiness through `getSlipperiness()`, multiplies by `0.91F`, and computes `0.16277137F / (t * t * t)` before multiplying by `getSpeed()` and calling `updateVelocity`; the non-ground branch still uses `speedInAir`.
- `BLOCK-FRICTION` establishes the shared 0.6F default for corresponding A-era blocks, with matching 0.98F ice/packed-ice and 0.8F slime values. For the shared default friction, evaluating the source-ordered IEEE-754 binary32 operations gives A ground multiplier `0x3f7ffffd` and B `0x3f7ffffe`, one representable float apart. This is a static evaluation of the cited expressions, not a measured movement trajectory. No claim is made that every friction denominator produces a distinct quotient.

## Reachability and dependencies

- The local player path copies sampled input into movement fields in `LocalClientPlayerEntity.serverTickAi()` (A lines 631-642 / B 635-646). `LivingEntity.mobTick()` invokes the local player's virtual `serverTickAi()` and later calls `moveRelative()` (A call sites lines 1822 and 1847; B lines 1898 and 1926). In `moveRelative`, both sides allow the locally controlled player into this branch; the grounded condition selects the changed scalar. `PlayerEntity.getSpeed()` supplies the movement-speed attribute (A line 1408 / B line 1489); its normal player base is 0.1F on both sides.
- Finding-specific dependencies closed: source branch and player call path above; `BLOCK-FRICTION` proves the shared default coefficient and all historical explicit slipperiness values. This finding is limited to the grounded acceleration scalar and does not claim closure of other travel branches, attributes/effect production, collision shapes, or the full pair.
- Server-synchronized movement-speed attributes and non-default effect/equipment states remain external inputs; they can change `getSpeed()` but do not change the source literal difference.

## Consequence and uncertainty

The scalar supplied to `updateVelocity` differs for the concrete shared default-friction case. The change may propagate into later velocity and position calculations for nonzero movement input and speed; no trajectory or final-position difference was measured. The first release containing the changed literal is unknown within (1.12.2, 1.13.2].

## Handoff

- Independent delta: grounded movement acceleration scalar literal and resulting default-friction ground multiplier.
- Related finding IDs: none.
- Applicability constraints: grounded local-player ordinary movement with nonzero input and speed, outside water, lava, and fall-flying branches.
- Evidence manifest paths / SHA-256s: see artifact identity records EA-FEATHER-R1-A and EA-FEATHER-R1-B above.
- Original artifact-manifest paths / SHA-256s: ready/1.12.2/artifacts.sha256 / 8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c; ready/1.13.2/artifacts.sha256 / fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e.
- Original derived-artifact availability/hash: unavailable; expected original mapped JAR hashes are recorded above.
- Source/raw-input hash relation: unchanged source trees and raw inputs recorded in revision metadata and independently verified as listed above.
- Revised-to-original equivalence: unverified.
- Provenance limitations: revised derived artifacts are verified; original mapped-bytecode equivalence is not.
- Source-proven historical behavior boundary/evidence: grounded branch in `LivingEntity.moveRelative(FFF)V`, reached from local-player `mobTick`; exact guards and callers are cited above.
- Finding-specific closed dependency IDs/evidence: TRAVEL-MODIFIERS grounded branch; BLOCK-FRICTION; bounded CLIENT-TICK input transfer/call path cited above.
- Independent blind source reviewer and decision date: pending.
- Pair run status and commit at handoff: active; immutable finding snapshot commit/hash to be recorded in `run.md` after commit.
- Pair complete: no.
- Implementation handoff: blocked pending independent source review of this exact snapshot; no implementation details were inspected.
- Replaces/supersedes snapshot ID and reason: none.
