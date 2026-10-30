# Correction review: integration fixes through 25e84811

## Summary

Reviewed the correction commits `01392e29` and `25e84811f9b89c8481848bc57370215286a9c595` on top of the original enum-order change. **ACCEPT; no actionable issues found.** This is an appended review artifact; the original `review.md` is unchanged.

The metadata-path correction uses `versionCache.resolve("version.json")`, which is the same path passed to `download(...)` when `versionJsonPath` is created in `decompileVersion`. Thus the 1.7.10 verifier hashes the exact already-pinned metadata file, without adding a new path or source.

The graph correction now counts direct `[I` `GETSTATIC`s only when the helper owner differs from the current consumer class. That excludes the helper’s own initializer accesses from the 21 external-site count while still validating every referenced field. Unique helper-to-consumer edges remain deduplicated for the 14 dependency count. Initial reversed-edge count remains diagnostic and no longer gates acceptance: the canonical digest deliberately excludes ZIP entry ordering, while the stable topological ordering uses the actual input positions and handles any valid acyclic order.

The correction leaves intact the raw/version/mapping/Tiny pins, canonical ordinary-remap digest, flat-class requirement, 1,803 class / 14 helper / 14 dependency / 21 external-site checks, duplicate-path checks, field binding checks, cycle/completeness check, and ordered output path/content identity comparison. Strict decompile, failure, required-source, and provenance gates are unchanged. This review did not run the reported in-progress decompile, builds, tests, or game; no source/cache writes were made.

## Review angles

1. **Requirement:** Both corrections address the concrete failures from the first strict build attempt without weakening the intended gates.
2. **Correctness and edge cases:** The hashed metadata path is the exact download destination. Excluding self-owned reads matches dependency semantics; repeated external field sites remain counted, while edges remain one per helper/consumer pair. Varying ZIP class order no longer causes a false rejection and is corrected by the ordering stage.
3. **Missing work:** No missing changes found in the inspected paths. The byte/content and graph safeguards continue to precede ordering and decompilation.
4. **Conventions and duplication:** Corrections are localized to the existing pin and graph verifier; no parallel helper/order path was introduced.
5. **Permissions and visibility:** No permission or public-output change. Existing output reuse/force safeguards are unaffected.
6. **Security:** No new trust boundary or security issue found.

## Not verified

- Strict decompile and build results remain owned by the root integration agent.
- The exact cached graph and pin behavior are not re-executed here; this was a static code review of the fixes and their invariants.
