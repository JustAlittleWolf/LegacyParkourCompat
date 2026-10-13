# Review: static movement-change catalog and sprint hook split

**Verdict: REQUEST CHANGES**

Reviewed frozen author commit `e9e5a0592d9488aa8d667ab2301fe4dc34439af1` against baseline `d8f3956602da94bf0cf67753cc0a9f4665397729` (`main`). This is a static review; no tests, build, or runtime execution was performed.

## Blocking finding

**[P1] Existing registry tests still call APIs deleted by this commit.** `src/test/java/me/wolfii/legacyparkourcompat/impl/MovementChangeRegistryImplTest.java` is unchanged in the patch, but `mechanicTypesCollectsEveryHookInterface` calls the removed `MovementChangeRegistryImpl.mechanicTypes(Both.class)` at line 36. The two registration tests call the removed `register(Object)` overload at lines 47 and 61. Those references do not resolve against the updated `MovementChangeRegistryImpl` / `MovementChangeRegistry`, so the existing test source no longer compiles. Update or remove the obsolete test coverage as part of the migration; do not restore reflective discovery.

## Static review evidence

- The diff removes all 25 per-version `MovementChanges` provider classes and the 25 Fabric custom entrypoint entries, then routes `MovementControllerImpl.initialize()` through `MovementChangeCatalog.register()` before its final rebuild and networking initialization.
- The catalog has 108 typed registration sites versus 104 old class-to-mechanic mappings. The net four additions are the two new hook keys for each of V1_12 and V1_13. The former 25 version groupings and registrations map to the catalog; the V1_19_4 `GroundFrictionBlockBehavior` override remains explicitly registered at V1_19_4.
- Multi-hook registrations reuse one implementation object for each former multi-hook class, including V1_8 boat input, pose/dimensions and sprint duration; V1_12 water jump, water travel and dimensions; V1_13 dimensions; V1_14 soul sand; V1_15_2 fluid jump; V1_18_2 air speed; and V1_19_4 fall-distance reset.
- V1_8 fence and pane IDs still come from their existing `BLOCK_IDS`; V26_1 still scans registered slime and bed blocks and throws if a selected block has no identifier.
- The new sprint mechanic keys are distinct (`player.sprint.inputStart.shallowWater`, `.doubleTap`, `.key`). The mixin dispatches the former three operations to the matching new hook and keeps the vanilla result as the fallback. `ChangeResolver` is unchanged; `CURRENT` still resolves no changes and each mechanic resolves independently.
- A tracked-tree reference sweep found no deleted provider, old sprint interface, removed `registry()` API or `register(Object)` reference in production source, build scripts, or current guidance. Remaining provider wording appears in dated historical workflow reports; the old sprint API is also named in its dated implementation history. The unresolved test references above are the only compile-time stale references found.
- `git diff --check` reported no whitespace diagnostics.

Re-review the corrected frozen snapshot before accepting this migration.
