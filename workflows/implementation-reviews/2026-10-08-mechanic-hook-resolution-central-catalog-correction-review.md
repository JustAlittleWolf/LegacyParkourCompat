# Re-review: static movement-change catalog correction

**Verdict: ACCEPT**

Reviewed corrected frozen commit `dbc088f70a1bbec73f7b6ff53a7820d3e19be09c` against baseline `d8f3956602da94bf0cf67753cc0a9f4665397729`, including its correction relative to the previously reviewed `e9e5a0592d9488aa8d667ab2301fe4dc34439af1`. This is a static review; no tests, build, or runtime execution was performed.

## Correction verified

The unchanged test-source blocker from the first review is resolved. The existing `MovementChangeRegistryImplTest` now uses the remaining typed `register(Class<T>, ParkourVersion, T)` API throughout. Its three existing tests verify: one explicit registration does not infer a second implemented hook; the same implementation object can be registered explicitly under two hook types; and a later registration overrides only its own hook. It no longer refers to `mechanicTypes` or `register(Object)`. The number of existing `@Test` methods remains three; this correction adds no tests.

## Static review evidence

- The original migration remains intact: 25 version providers and 25 Fabric custom entrypoint registrations are replaced by the central typed catalog. The catalog has 108 registration sites versus 104 old class-to-mechanic mappings; the four added mappings are the additional independent hook keys for V1_12 and V1_13.
- All 25 per-version registrations retain their prior change classes and mechanic types. The V1_19_4 `GroundFrictionBlockBehavior` override remains explicit. Multi-hook classes reuse one instance across their separately registered interfaces. V1_8 fence/pane variants still use their existing lists; V26_1 still scans registered bed/slime blocks and fails if an eligible block has no ID.
- The three sprint-start mechanic IDs are distinct, each mixin call site queries its matching interface, and vanilla results remain the fallback. `ChangeResolver` is unchanged. Its closest-version behavior remains independent by key, and `CURRENT` still resolves no historical changes.
- Initializer ordering remains catalog registration, controller rebuild, then network registration. The old provider interface, Object registration overload, public registry accessor, and Fabric entrypoint metadata are removed. The routing audit, public API docs, implementation guide, and migration record describe the static catalog.
- A full tracked-tree name sweep finds remaining removed-provider/entrypoint/sprint-interface names only in dated historical implementation records or the migration record describing what was removed. No stale references remain in production, test, build, resource, or current-guidance sources.
- `git diff --check` reported no whitespace diagnostics.

The previous REQUEST CHANGES report remains bound to its original snapshot and commit; this ACCEPT applies only to corrected commit `dbc088f70a1bbec73f7b6ff53a7820d3e19be09c`.
