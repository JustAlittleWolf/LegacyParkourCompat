# Step-up collision source-slice correction (r2) — 26.1.2 to 26.2

This immutable correction supersedes the bounded verdict in `build/later-source-20261116/26.1.2--26.2/stepup_collision/REPORT.md` (candidate SHA-256 `3b6d9520542f6de8457f3d5b52774f8d31aeee03285d84ff4dea14ae8b1ea508`). It corrects only the conclusion about the step-up collector call and helper bodies. It does not establish a broader `Entity.move` no-difference result.

## Exact source identity and integrity

Both endpoints are canonical local `unobfuscated` decompilations (Vineflower, Java 25). I read the root-sidecar ready and provenance records and verified the ready-declared source-manifest hashes, the `Entity.java` source-manifest entries, and the actual `Entity.java` hashes.

| Endpoint | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 (declared = actual) | Artifact manifest SHA-256 (declared = actual) | `Entity.java` SHA-256 (manifest entry = actual) |
|---|---|---|---|---|---|
| 26.1.2 | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| 26.2 | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |

Paths are `decompiled_minecraft/<version>/unobfuscated.ready.json`, `unobfuscated.provenance.json`, `unobfuscated.sources.sha256`, `artifacts.sha256`, and `unobfuscated/net/minecraft/world/entity/Entity.java`. Ready identities report version IDs `26.1.2` and `26.2`, mapping `unobfuscated`, and source counts 6,882 and 7,055 respectively.

## Corrected bounded source finding

In `Entity.collide(Vec3)`, 26.1.2 (`Entity.java:1077–1105`) calls `collectColliders(this, this.level, entityColliders, stepUpAABB)` at line 1092. The newer endpoint (`Entity.java:1141–1169`) calls `collectCollidersIgnoringWorldBorder(this, this.level, entityColliders, stepUpAABB)` at line 1156. The call-site name changed.

The exact matching helper bodies show that this rename does not establish the claimed world-border behavior change for this call:

- 26.1.2 `collectColliders(@Nullable Entity, Level, List<VoxelShape>, AABB)`, `Entity.java:1143–1159`, copies the entity colliders when present, gets the level world border, tests `source != null && worldBorder.isInsideCloseToBorder(source, boundingBox)`, adds `worldBorder.getCollisionShape()` when true, then adds `level.getBlockCollisions(source, boundingBox)` and builds the list.
- 26.2 `collectCollidersIgnoringWorldBorder(@Nullable Entity, Level, List<VoxelShape>, AABB)`, `Entity.java:1214–1230`, performs the same operations in the same order with the same gate and calls to `getCollisionShape()` and `getBlockCollisions()`.

The 26.2 `Entity` overload is the one supplied at this call site (`this` has static type `Entity`). The separate `CollisionContext` overload at lines 1232–1242 omits the border check, but that is not evidence that the step-up call uses it.

The scoped step-up eligibility/box-preparation conditions and candidate-height filtering and selection expressions remain the same in the compared ranges (26.1.2 `Entity.java:1081–1128`; 26.2 `Entity.java:1145–1192`). No movement-affecting difference is established by the collector rename or by the compared helper bodies. This is a bounded no-difference result for these inspected step-up caller/helper operations, not for `Entity.move` as a whole and not a finding that all movement behavior is unchanged.

## Remaining gates and coverage limit

The broader collector/provider dependency closure remains open: this correction did not compare the implementations and dependencies of `WorldBorder.isInsideCloseToBorder`, `WorldBorder.getCollisionShape`, `Level.getBlockCollisions`, or their shape providers across the endpoints. Other movement operations and the full ordered player-tick inventory also remain outside this slice. `S-COLLISION-STEP` and full-pair coverage remain partial; no onset, pair freeze, source acceptance, implementation, or runtime-parity claim is made.

