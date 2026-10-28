# Independent product review: 1.13 slab collision bundle

## Verdict

**ACCEPT**, for the explicitly bounded slab-only block collection routes: exact 1.13.0 aggregate and exact 1.13.1/.2 ordered-stream operations, under the adapter's fail-closed gates. This review does not claim all-block or general runtime parity.

Reviewed product HEAD: `fdc11bf34b6202cd2e533f6d208bcc77a3c05d21` (product commit `a4a31b55702f21ee20da10211b332ce2a6be3b21` merged with main `6caf6a4b8bd62c04ac242a3ad3cea0ff0140c63c`). Product base: `e486c008`; requested comparison main: `6caf6a4b`. The worktree was clean. `git diff --check 6caf6a4b HEAD` reports no whitespace errors in the product delta; the comparison from the older base includes unrelated upstream documentation EOF warnings only.

## Review findings

- The two resolved changes match the source distinction. The 1.13.0 change folds X/Z/Y partial shapes into the historical accumulator, optimizes it, then appends the single canonical-full-cell aggregate. The 1.13.1/.2 change preserves X/Y/Z iteration for partial shapes and adds one canonical-full tail. The collector implementations retain the source perimeter/loaded-cell exclusions, old bounds envelope, exclusion overlap, and bounds intersection checks. The 1.13.0 route stores its single aggregate for the existing axis clipping hook; the ordered 1.13.1/.2 route leaves native per-shape clipping in place under the accepted ordered-grid slab contract.
- The movement adapter associates the collection with the exact primary `Entity.move` starting box, requested vector and swept query, and requires the current query to match. It rejects adjusted vectors, non-LocalPlayer calls, absent/zero movement queries, entity candidates, border checks/collisions, and routes that can enter step-up. The step-up wrapper also disables historical collection for its sweep. Context fields are restored in `finally`, including nested scopes.
- Shape-bit conversions are computed from the source/current formulas for the start, padded sweep and direction-shifted start; invalid or unequal results fall back to native. Provider equivalence is computed per scanned cell from the actual registry key, state properties, exact shape bounds/canonical identity and hook presence. Collector flags that are true are guarded by those runtime checks rather than accepted from caller-supplied data.
- Chunk reads use cached `Level.getChunkForCollisions` getters. State and shape reads use that same non-loading getter; a null collision chunk becomes the source-equivalent `VOID_AIR` state and empty shape. Shape neighbor reads go through a `BlockGetter` view using the same non-loading path. No `Level.getBlockState` fallback can load or silently substitute a different column. The 1.13 aggregate's column-level chunk check and the 1.13.1/.2 per-cell chunk check follow their respective source forms.
- The admitted non-slab empties are only `air`, `cave_air`, and `void_air`; every other nonempty or unproven key rejects the whole historical route. Slabs must match the source-backed 19-key roster, `SlabBlock` class, `TYPE` and `WATERLOGGED` properties, exact half-slab box or canonical full-shape identity. The corrected alias map accepts current `smooth_stone_slab` for historical `stone_slab` and excludes current `stone_slab`.
- Resolver and discovery are per operation and use the existing hook. The 1.13.0 aggregate alone uses the existing `AxisClippingHook`; 1.13.1/.2 collection uses its separate versioned hook. The main merge leaves these paths and imports coherent. Mixin redirects retain native fallback suppliers for unsupported paths.

## Alias correction binding

The original source closure and review remain unchanged and are bound by `.native-source/20261028/collision-next/slab-1130-all-slab-alias-correction/correction.md` and its `source-hashes.sha256`. The correction binds 1.13 and 26.2 source-manifest identities, exact `DataFixers.java` and `BlockRenameFix.java` hashes, the version-1802 `stone_slab` → `smooth_stone_slab` rename, and the complete 19-key old-to-current table. All 11 source hash entries were rechecked against the canonical read-only source tree. No other key in the 1.13 roster is renamed by the relevant data fixer. Current slab registrations retain the matching type-based collision provider and bottom/top/double bounds.

The corrected product mapping has 19 unique current keys and does not accept current `stone_slab` as the historical block. I found no additional key alias discrepancy in the reviewed roster.

## Scope and checks

Source evidence reviewed includes the accepted 1.13.0 collector contract, union-math audit, complete slab state alias closure and review, adapter/query gates, shape conversion, stream equivalence, step-up and loaded-chunk gates, product seam reconciliation and contract review. Product review also inspected both collector changes, the hook, provider adapter, movement context, mixin call paths and corrected alias map.

The accepted envelope is limited to block-only slab arrangements with no entity candidate, border collision, step-up route, unsupported block hooks, unknown nonempty block, or non-equivalent bounds/provider input. Such paths remain native. This review did not run a build, tests, game, or runtime; compilation and movement parity remain separate validation steps for the root integration owner.
