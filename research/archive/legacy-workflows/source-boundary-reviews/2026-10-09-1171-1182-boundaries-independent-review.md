# Independent blind review: 1.17.1–1.18.2 F-001/F-002 boundaries

Date: 2026-10-09
Review basis: exact candidate commit `14443f43fbcc30152916ac75796846d796cf0268`, whose parent is the assigned base `64d8bc740de0860a43ecc691db0efbdcaef7c19b`.
Candidate path: `workflows/source-boundary-reviews/1171-1182-F001-F002-boundary-evidence-2026-10-09.md`
Candidate Git blob: `8892a7533358c6e7053527c24dacd0a2acce5ae9`; raw SHA-256: `66f5659dd17ff8441d10a02d95920ecd5d4e190c53c2c0893eac79efec36c6cb`.

## Disposition

**ACCEPT the bounded source observations:** F-001's changed sprint-stop predicate is first present at exact 1.18 among the four assigned releases; F-002's cosine type/precision change is first present at exact 1.18.2 among them. The two boundaries are independent source findings. This disposition does not complete the broader 1.17.1–1.18.2 movement-coverage pair, accept the separate F-002 metadata correction, or make a runtime/parity claim.

One non-substantive correction is required when the candidate is revised: its 1.18 ready-marker SHA-256 is missing the final `f`. The actual `mojmap.ready.json` SHA-256 is `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f`. The 1.18 source-manifest and artifact-manifest hashes in the candidate are correct. This transcription error does not affect the source evidence or boundary findings; the marker itself and its metadata were independently checked here.

## Independent source identity checks

I read only the four canonical ready Mojmap source trees for this assignment and the exact candidate source report. For each release, I recomputed SHA-256 for `mojmap.ready.json`, `mojmap.sources.sha256`, and `artifacts.sha256`; checked the ready marker's `status`, `versionId`, `mapping`, and canonical output path; then recomputed SHA-256 for `LocalPlayer.java`, `Entity.java`, `LivingEntity.java`, `Player.java`, and `ElytraItem.java`. All 20 source-file hashes match both the candidate table and their per-release source-manifest entries. All four source/artifact manifest hashes match the candidate. The ready-marker hashes match except for the 1.18 transcription defect described above. The markers identify the exact requested release and `mojmap` namespace.

## F-001 — sprint stop on horizontal collision

The exact `LocalPlayer#aiStep` gate confirms the reported boundary:

- **1.17.1** (`LocalPlayer.java:715–725`): the non-swimming stop predicate includes `horizontalCollision` directly: `bl7 = bl6 || horizontalCollision || inWater && !underWater`.
- **1.18 and 1.18.1** (`LocalPlayer.java:713–723`): it becomes `$$5 || horizontalCollision && !minorHorizontalCollision || inWater && !underWater`. The two source trees have identical hashes for the reviewed `LocalPlayer`, `Entity`, and classifier source files.
- **1.18.2** (`LocalPlayer.java:708–718`): the same minor-collision exemption remains.

The state dependency is supported by the exact movement sources. In 1.17.1, `Entity#move` writes `horizontalCollision` from the resolved X/Z movement deltas (`Entity.java:561–563`), and a search of the player/entity hierarchy source files found no `minorHorizontalCollision` field, write, or classifier. In 1.18 onward, `Entity` adds the flag and sets it from `isHorizontalCollisionMinor(resolvedDelta)` after horizontal collision, clearing it when there is no horizontal collision (`1.18`/`1.18.1 Entity.java:568–574`; `1.18.2:581–588`). The base classifier returns false; `LocalPlayer` overrides it. Its body compares rotated player input with resolved horizontal movement, treats near-zero magnitudes as not minor, and classifies an angle below `0.13962634F` as minor. Searches across the sampled 1.18+ player/entity sources found the field writes only at that `Entity#move` producer.

The surrounding sprint-start and sprint-stop ordering is retained in the inspected `aiStep` ranges. The source-level change is the collision exemption for collisions classified minor; the existing forward-input/permission and water stop conditions remain. Therefore **1.18 is the first observed exact release among 1.17.1, 1.18, 1.18.1, and 1.18.2**. This review does not broaden the claim to uninspected releases or geometry/trajectory behavior.

## F-002 — fall-flying cosine coefficient

The exact `LivingEntity#travel` fall-flying branch confirms the reported boundary:

- **1.17.1** (`LivingEntity.java:2068–2084`): pitch is narrowed to float radians; `Mth.cos(float)` produces a float; the coefficient product is explicitly narrowed back to float.
- **1.18 and 1.18.1** (`LivingEntity.java:2070–2086`): the same float cosine and float coefficient operation/order remain. The two `LivingEntity.java` files have identical hashes.
- **1.18.2** (`LivingEntity.java:2074–2090`): pitch conversion remains float, then `Math.cos($$15)` promotes its argument to double and the squared cosine / `Math.min` coefficient remains double without the earlier narrowing cast.

Across these sources, the operation stays under the same `else if (this.isFallFlying())` branch, and the coefficient is consumed in the same subsequent lift/pull sequence. The source proves the type/precision boundary; it does not by itself establish a resulting velocity or trajectory difference. Therefore **1.18.2 is the first observed exact release among the four sampled versions**.

I also confirmed the activation path in all four versions: `LocalPlayer#aiStep` checks jump and the existing flight/passenger/climbable guards, requires an enabled chest-slot Elytra, and calls `Player#tryToStartFallFlying`; the player method requires airborne state, not already fall-flying, not in water, and no Levitation. `ElytraItem.isFlyEnabled` retains the same damage-below-maximum-minus-one predicate. This supports reachability of the coefficient branch without changing the precision-boundary conclusion.

## Scope limits

This review accepts only the two bounded source boundaries and their direct state/activation dependencies. It does not approve implementation, claim tick-level parity, expand the concrete F-001 collision case, infer numerical F-002 trajectory effects, review other operations in the pair, or revisit the previously accepted endpoint snapshots. No build, tests, decompilation, runtime, game, TAS, Gym, server, or Docker activity was performed.
