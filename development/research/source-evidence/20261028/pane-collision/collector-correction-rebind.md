# Independent rebind: 1.13 entity-collision collector correction

Verdict: **ACCEPT for the correction supplement.** This accepts its bounded correction and caller/gate claims; it does not alter the prior **REVISE** verdict on the original evidence revision.

## Inputs bound

- Original source evidence: `.native-source/20261028/collision-dependencies/axis-primitive-aliases/evidence.md`, SHA-256 `F20BD238ABA21C93AD664C094D0752F9284AA3CF4398BF68099B9402E155BCDC`.
- Prior independent audit: `.native-source/20261028/collision-dependencies/axis-alias-audit/audit.md`, SHA-256 `0692F9834AFDFACB3B7B3AFB1E0E9D91F8FFF3594E8EEE641DD9D87161CF7470`.
- Correction supplement: `.native-source/20261028/collision-dependencies/axis-primitive-aliases/collector-correction-supplement.md`, SHA-256 `B1DF01E4701051429D75294FAD33B1916FFC6253811BFE5B741E65E9195BAC3D`.
- Its identity companion remains `.native-source/20261028/collision-dependencies/axis-primitive-aliases/identity-supplement.md`, SHA-256 `B039284B2761D93CDA1F05A213B64032015ED2E4DA1C3DFB6AFBB93066A7F7AE`.

## Independent source checks

The ready-marker, source-manifest, and Java-file hashes listed in the correction supplement match on-disk files for 1.13, 1.13.1, and 1.13.2. The relevant Java source hashes also occur in each version's source manifest.

- **1.13:** `WorldView.java:203-247` first collects blocks. For non-null entity callers, it joins the result of `m_19803363` when early-existence mode is disabled or the block result is empty. The helper scans entities within query bounds expanded by `0.25`, skips explicitly excluded entities and shared-vehicle entities, and considers both the other entity's collision shape and the caller's reciprocal response shape. It accepts only non-null boxes with strict `Box.intersects(queryBounds)` and stops after the first accepted candidate in early-existence mode. `hasNoCollisions` at `WorldView.java:270-275` calls this collector with early-existence mode and checks `isEmpty()`, so entity shapes are considered for a non-null caller if no block shape was found.
- **1.13 caller:** `Entity.move` invokes `world.hasNoCollisions(this, candidate)` for its step-height checks (`Entity.java:536,548,558`) and invokes `world.getCollisions(this, shape, x, y, z)` for the ordinary and alternate movement collision queries (`:579-580,611-612`). These pass `this`, so the 1.13 `WorldView` entity integration is reachable in those calls.
- **1.13.1 and 1.13.2:** `WorldView` supplies the block stream and its `hasNoCollisions` defaults test that stream. The concrete `World` override at `World.java:1755-1758` concatenates that stream with `getEntityCollisions` only for non-null entities. `EntityView.getEntityCollisions` (`EntityView.java:21-38`) searches within bounds expanded by `0.25`, filters excluded and shared-vehicle entities, emits both collision and reciprocal response shapes, removes nulls, and requires strict `Box.intersects`. The `Entity.move` source bytes are identical across 1.13.1 and 1.13.2; movement and step-height call sites pass `this`, and interface dispatch to `getCollisions` reaches the concrete `World` override for `hasNoCollisions` as well.

## Scope and limits

The correction accurately limits its effect to collector contents and caller-visible collision/`hasNoCollisions` results. It does not change the axis primitive math, epsilon or strict touching conclusions. It makes no movement-trajectory, full-pair coverage, implementation, or runtime claim. No tests, builds, launches, or source generation were run.
