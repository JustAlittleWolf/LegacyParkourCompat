# Correction: epsilon gate and the ordinary local-player move path

## Result

This supplement clarifies the bounded claims in the exact reports listed under **Bound reports**. The gate is absent from the selected 1.14.0, 1.14.1, and 1.14.2 Yarn `EntityView` helpers and present in 1.14.3 Yarn. In 1.14.3, an ordinary local player's non-spectator `SELF` movement reaches that gate through `Entity.move`; the `noClip` branch bypasses it, while the special zero-result early return applies only to `PISTON` requests. The movement request's later zero-displacement check does not prevent the query.

For the previously documented finite-coordinate caveat, retain this distinction: arbitrary finite coordinates admitted through inherited setters or edited entity data can round all box extents to zero, but that is not an ordinary local-player movement witness. The ordinary local-player paths reviewed in the reachability report keep X/Z bounded; a very large finite Y can collapse the stored Y extent while the two horizontal extents keep `averageDimension()` well above `1.0E-7`. This supplement does not broaden that source-only conclusion to arbitrary setter state, modified saves, or custom code.

## Independent source trace

All source paths below are relative to `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

| Version | Yarn `EntityView` result | `Entity` movement candidate query |
|---|---|---|
| 1.14.0 | The old `getCollisionShapes(Entity, VoxelShape, Set)` only returns an empty stream when its supplied shape is empty; there is no average-dimension epsilon comparison. | `method_17835` constructs `boundingBox.stretch(arg).expand(1.0E-7)` and calls `world.getEntities` directly. |
| 1.14.1 | Same old shape-based helper; its `EntityView.java` is byte-identical to 1.14.0. | `method_17835` uses the same direct expanded swept-box entity lookup. |
| 1.14.2 | Same old shape-based helper, with no average-dimension predicate. | `method_17835` uses the same direct expanded swept-box entity lookup. |
| 1.14.3 | `method_20743` tests the unexpanded box's `averageDimension() < 1.0E-7`, returns `Stream.empty()` on true, and only then expands by `1.0E-7` and queries entities (`world/EntityView.java:45-59`). `IWorld.method_20743` delegates to this default implementation (`world/IWorld.java:74-75`). | `handleCollisions` calls `world.method_20743(this, boundingBox.stretch(arg), ImmutableSet.of())` before creating the reusable entity/world-border collision stream (`entity/Entity.java:653-662`). |

The previous alias report's boundary is therefore supported for these selected Yarn methods: absent through 1.14.2 and first present in 1.14.3. The older shape-empty return is a separate predicate on a `VoxelShape`, not the swept entity-query box.

### Ordinary local-player route and early exits

- `ClientPlayerEntity.tickMovement` calls `super.tickMovement` (`client/network/ClientPlayerEntity.java:607,761`). `ClientPlayerEntity.canMoveVoluntarily()` returns `true` (`:471-474`), so the `LivingEntity.travel` guard `canMoveVoluntarily() || isLogicalSideForUpdatingMovement()` does not suppress local-player travel (`entity/LivingEntity.java:1779-1781`).
- `PlayerEntity.travel` delegates its normal and flying branches to `super.travel` (`entity/player/PlayerEntity.java:1364-1389`). The ordinary `LivingEntity.travel` land-movement branch calls `move(MovementType.SELF, velocity)` (`entity/LivingEntity.java:1833-1840`); `ClientPlayerEntity.move` forwards to `super.move` (`client/network/ClientPlayerEntity.java:834-838`).
- In `Entity.move`, `noClip` takes an offset-and-return path before collision handling (`entity/Entity.java:441-445`). Spectator players set `noClip` in `PlayerEntity.tick` (`entity/player/PlayerEntity.java:191-195`); that path bypasses the epsilon query. The only request-type early return before collision handling is under `arg == MovementType.PISTON`, after `applyPistonMovement` returns zero (`Entity.java:446-451`). Ordinary player travel uses `SELF`, not `PISTON`.
- Once the ordinary non-`noClip` path continues, movement scaling and `clipSneakingMovement` run, then `handleCollisions` invokes the entity query (`Entity.java:453-462`). The later `lv.lengthSquared() > 1.0E-7` condition controls whether the bounding box is offset; it is after the gate call. A zero or sneak-clipped movement vector therefore does not itself bypass the gate.

### Finite-coordinate qualification

The gate is evaluated on the swept stored box before its epsilon expansion. `Box.stretch` cannot reduce an axis's extent when the box has representable, ordered endpoints; however, prior endpoint construction at sufficiently large finite coordinates can round distinct intended edges together. The reports' setter-level counterexample remains valid for arbitrary finite state and is not revoked here. For the ordinary local-player reachability covered in `reachability.md`, the player positioning paths clamp X/Z to about `±3.0E7`, while Y is not equivalently bounded. The minimum player horizontal width is 0.2; at those bounded X/Z magnitudes its two horizontal extents remain vastly larger than the gate threshold, even if extreme finite Y makes the vertical extent zero. Thus this caveat does not establish an ordinary local-player gate witness.

This is a source-only inference from the cited movement and positioning paths. It does not claim that arbitrary direct setters, edited NBT, or custom code preserve the horizontal bound, and it does not rely on runtime experiments.

## Provenance and integrity

Canonical read-only release parents and mapping roots:

| Release parent | Parent entries observed | Yarn mapping root | Provenance SHA-256 | Yarn / client input SHA-256 |
|---|---|---|---|---|
| `1.14/` | `yarn/` plus separate Ornithe Feather release-parent files/directories; no Yarn-specific parent ready marker | `1.14/yarn/` | `ea70a955a12f377cd3b9e8d5f346f4625b044d55da726b6bc472405ae91f4341` | Yarn `1.14+build.21`: `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64`; client: `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a` |
| `1.14.1/` | `yarn/` | `1.14.1/yarn/` | `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` | Yarn `1.14.1+build.10`: `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`; client: `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` |
| `1.14.2/` | `yarn/` | `1.14.2/yarn/` | `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` | Yarn `1.14.2+build.7`: `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b`; client: `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25` |
| `1.14.3/` | `yarn/` | `1.14.3/yarn/` | `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a` | Yarn `1.14.3+build.13`: `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`; client: `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07` |

The current hashes of the selected `Entity.java` and `EntityView.java` files match their mapping-level `.decompile-provenance.json` entries in all four Yarn trees. For the 1.14.3 local-player path, `ClientPlayerEntity.java`, `PlayerEntity.java`, and `LivingEntity.java` also match their provenance entries. The 1.14.0 Yarn mapping root has provenance and selected-file integrity, but no Yarn-specific release-parent ready marker; the existing alias audit separately records an unrelated failed `RecipesProvider` method for that publication. Accordingly, this supplement supports only the selected movement/query methods there and does not certify the full 1.14.0 Yarn tree as complete or ready.

Selected source SHA-256 values independently recomputed for this supplement:

| Release | `Entity.java` | `EntityView.java` | Additional path sources |
|---|---|---|---|
| 1.14.0 | `f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a` | `fef34e5144d87f77a777e4dc1b62ace7a92af2ef9dba7ceb25a80c7068170eea` | — |
| 1.14.1 | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` | `fef34e5144d87f77a777e4dc1b62ace7a92af2ef9dba7ceb25a80c7068170eea` | — |
| 1.14.2 | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` | `fef34e5144d87f77a777e4dc1b62ace7a92af2ef9dba7ceb25a80c7068170eea` | — |
| 1.14.3 | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` | `d5f906a1b0332f9ff978e9c0a92bb913cdff68ac173a33aad44aedccc9516ebb` | `IWorld.java` `0930f466c86ebb07b77ecbf85661ea519ab43d884d5874edeaf52f21d534947a`; `ClientPlayerEntity.java` `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae`; `PlayerEntity.java` `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963`; `LivingEntity.java` `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` |

## Bound reports

This correction is tied to these exact report bytes (SHA-256):

- `AD-empty-stream-gate/empty-stream-epsilon-gate.md` — `2ae0e88c85fc4159215001a8b48d57b1e7f290b45d59a8a40e0b752fd3414098`
- `AD-empty-stream-gate-audit/audit.md` — `66982e343f7e3fb5857c21c1eb7315241cd341b545fb7038302df680f58b5b1b`
- `AD-empty-stream-y-reachability/reachability.md` — `c22184f612c96736e1d4d0dd96e4e0f7a8c4df634e137cc805603df5e2853bf6`
- `AD-empty-stream-y-reachability-audit/audit.md` — `ab383535c45705474bf32d361509175adf0624404a34ac13039b80f2aea30dcf`
- `AD114-empty-stream-patch-alias/alias-report.md` — `771ff97475b3ffdc0fd56f30de2f6f5add00e20b3661722dd956de242514d710`
- `AD114-empty-stream-patch-alias-audit/audit.md` — `17288791cca7114871e501d2985777bfffc968702485eb587c984921275b696e`

The source comparison and report-byte checks were read-only; no tests, builds, launches, Git operations, source generation, or canonical-source writes were performed.
