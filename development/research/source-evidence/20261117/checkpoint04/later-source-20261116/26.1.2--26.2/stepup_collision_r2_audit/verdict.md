# Independent blind audit — step-up collision correction r2

Candidate: `build/later-source-20261116/26.1.2--26.2/stepup_collision_revision_r2/report-r2.md`
Candidate SHA-256: `fa27179cb0f301e6be740695a5b795e500adee6d3126da820d0fdea845da011f`
Verdict: **ACCEPT — bounded corrected no-difference claim**.

## Exact source identity checked

Both ready markers declare their exact version and `unobfuscated` mapping, with Vineflower and Java 25. The ready and provenance sidecar hashes match the report:

- 26.1.2: ready `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`; provenance `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`.
- 26.2: ready `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`; provenance `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`.

For each endpoint, the actual source-manifest and artifact-manifest hashes match the values declared by the ready marker. The actual `Entity.java` hash matches its source-manifest entry: 26.1.2 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`; 26.2 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`. The candidate report's actual SHA-256 also matches the bound SHA above.

## Independent comparison

In 26.1.2 `Entity.collide(Vec3)` at `Entity.java:1077–1106`, the step-up retry passes `this` to `collectColliders` at line 1092. In 26.2, `Entity.collide(Vec3)` at `Entity.java:1141–1170` passes `this` to `collectCollidersIgnoringWorldBorder` at line 1156. The call-site type is `Entity`; it selects the nullable-`Entity` overload, not 26.2's separate `CollisionContext` overload.

The 26.1.2 entity collector at `Entity.java:1143–1159` and the 26.2 entity collector at `Entity.java:1214–1230` have the same body and operation order: create the builder; add nonempty entity colliders; obtain the world border; compute `source != null && worldBorder.isInsideCloseToBorder(source, boundingBox)`; add `worldBorder.getCollisionShape()` if that gate passes; add `level.getBlockCollisions(source, boundingBox)`; build and return the list. Thus the helper rename does not change the nullable-entity border gate, border collision-shape inclusion, or block-collision ordering for the step-up call.

I also compared the scoped step-up eligibility, AABB preparation, vertical candidate filtering/sorting, retry collision and horizontal-distance selection in the report's ranges. They match, with only source-line offsets and the collector method rename.

## Limits

The verdict supports only this inspected step-up caller/helper slice. The report correctly leaves the dependency implementations behind `WorldBorder.isInsideCloseToBorder`, `WorldBorder.getCollisionShape`, `Level.getBlockCollisions` and their shape providers open. It does not establish a whole-method `Entity.move` or full player-tick no-difference, complete pair coverage, intermediate patch boundary, source-pair freeze, implementation acceptance or runtime parity. The distinct 26.2 `CollisionContext` collector overload omits the border check, but is not selected by this `Entity` call.
