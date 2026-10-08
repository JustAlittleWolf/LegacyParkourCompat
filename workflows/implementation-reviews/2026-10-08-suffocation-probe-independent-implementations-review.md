# Independent review: versioned suffocation probes

**Verdict:** ACCEPT the code commit `42885fae364acfb20e8e03f84d0a3eeb756b2e7c` and its binding proof `dba9a4b2458d91561adbd8ba5e24ff050b65ca8f`.

**Scope:** Compared the frozen code commit with its exact parent, primary `main` at `d8f3956602da94bf0cf67753cc0a9f4665397729`. The implementation diff is 4 files, +45/−19 lines: the new common Y-column helper and the v1_15_2, v1_16, and v1_8 suffocation implementations. Also read the proof commit and checked its baseline/post-change bindings. No production code was changed by this review.

**Findings:** 0 functional findings (0 block, 0 important, 0 minor); 0 unverified findings. All six review angles were completed below.

## Source bindings

The code commit is a direct child of the reviewed base (`42885fae` parent `d8f39566`). Baseline and post-change Git blob IDs independently resolved from the trees:

| Source | Before (`d8f39566`) | After (`42885fae`) |
|---|---|---|
| `change/v1_15_2/SuffocationProbe.java` | `95069ec856431f075bda404622ba48b54e801831` | `e7d1dd0142699e8cd909b967d7efddc2a40b6c1e` |
| `change/v1_16/SuffocationProbe.java` | `a50ff725959b5e9eb4215e4f488e4d8cb85b450b` | `e50cde5276e2e61e0b096cb3c5bddd54ecd43b7c` |
| `change/v1_8/EndPortalFramePlayerEjection.java` | `01367390b8690415a3ea5cee0c40d059005e9d1b` | `d94e31939af0b81fbd77a37f5777c4785407bd51` |
| `change/common/SuffocationProbeColumn.java` | absent | `ab7370af56043c9f2e2df0afc29d4be03382a24b` |
| `mechanic/hook/SuffocationProbeBehavior.java` | `fba5329371ce5906666c8a205f64787cd1885a53` | same |
| client `LocalPlayerMixin.java` | `aca3809d94c4f31ab2a23ba963862676b6e90dc1` | same |
| `impl/ChangeResolver.java` | `9a6a57efd94aad45c2cd62ff452650528664577c` | same |
| `impl/MovementChangeRegistryImpl.java` | `504d735d21c578c8dc896c6030f10140a3053b8a` | same |
| `mechanic/MovementRuntime.java` | `3911ac5967890a166f23198eb00e39da5213022f` | same |
| `api/ActiveMovementProfile.java` | `7099d545c0d381aed8bdc6ff66007ff321afaf84` | same |
| `impl/MovementControllerImpl.java` | `549332705710ce4272cab094e0b49a4c2f4ef0ff` | same |
| `change/v1_16/MovementChanges.java` | `8ee0c15243b1779982e8b6b7aff1574cc49d7a24` | same |
| `change/v1_8/MovementChanges.java` | `20af171e2441eab9da9a3c529edf07296df51a18` | same |
| `change/v1_15_2/MovementChanges.java` | `a7a6ad1036239f8e6b4448e8bf40dbf59090012a` | same |

The hook caller, hook interface, closest-change resolver, mechanic registration logic, and affected provider registrations therefore remain byte-for-byte unchanged. A commit-tree search finds three direct `SuffocationProbeBehavior` implementations and no class extending a versioned `SuffocationProbe`.

## Verified behavior

- **Y-column iteration:** Before, v1_16 used `Mth.floor(boundingBox.minY)`, `Mth.ceil(boundingBox.maxY)`, a mutable cursor initialized with the input X/Z, and an ascending `for (y = minY; y < maxY; y++)` loop, returning on first true. After, `SuffocationProbeColumn.any` contains those exact bounds, cursor construction, order, and short circuit. The per-version predicates run synchronously on each cursor position; the mutable cursor is not retained.
- **v1_16 predicate:** The prior `getBlockState(pos).isSuffocating(level, pos)` predicate is still supplied by the v1_16 implementation. The helper contains no version-specific rule or block predicate.
- **v1_15.2 behavior and target bound:** The ShulkerBox special case still returns true only when `target == ParkourVersion.V1_15_2`. It does not claim the behavior for an adjacent or otherwise unreviewed target. For the fallback, the old override read `BlockState` to inspect the block, then called the superclass predicate, which read the state again. The new implementation preserves both reads and their order before applying the same vanilla predicate.
- **v1_8 portal-frame behavior:** The exact `target == V1_8` guard, `minY + 0.5D` calculation, `BlockPos.containing` conversion, sample-cell check followed by `above()` check, and short-circuit order are unchanged. On a miss it now calls the neutral Y-column helper with the same vanilla suffocation predicate formerly reached through the v1_16 superclass.
- **Callback and native paths:** All three prior implementations ignored the `VanillaFn<Boolean>` value for their historical result; the v1_8 implementation merely forwarded it to the superclass, which also ignored it. All three new implementations likewise do not evaluate the supplier. `LocalPlayerMixin` still supplies `callback::getReturnValue` at the same RETURN injection and replaces the return only when `MovementRuntime.find` returns a behavior. That lookup still falls through for native/current or disabled emulation and non-player cases.
- **Resolver, registration, and errors:** Provider registration and resolver code are unchanged. The same `SuffocationProbeBehavior` mechanic key is discovered because each class directly implements the hook. The resolver still picks the closest registered applicable version. `MovementControllerImpl.applies` still disables emulation for current/native selections; `MovementRuntime.find` then returns empty, leaving the vanilla result alone. No exception handling, catch, or error-suppression path was added; the helper propagates predicate failures.

## Six review angles

1. **Requirement — clear.** The independent v1_15.2 implementation and removal of v1_8 cross-version inheritance are both present, with only a neutral Y-column helper shared.
2. **Correctness and edge cases — clear.** Bounds, traversal, predicate order, target guards, and portal-frame short circuits match the baseline behavior described above.
3. **Missing changes — clear.** The repository-wide consumer census found the single LocalPlayerMixin caller; hook, providers, registry, resolver, and native fallback need no companion edits.
4. **Conventions and duplication — clear.** The helper centralizes only mechanical iteration. Version predicates stay in their version packages; no copied movement loop or whole physics class was introduced.
5. **Permissions and visibility — clear.** No permission boundary, API visibility outside the implementation types, default selection, or native behavior changes. The public hook contract remains unchanged.
6. **Security — clear.** No new external input boundary, deserialization, logging, path, or privilege behavior was introduced.

## Not verified / not reviewed

No compile, tests, runtime, decompilation, client/server, TAS, Gym, Docker, or push was run, as requested. This review establishes static preservation against the frozen baseline; it does not establish broader historical source coverage or runtime behavior. Generated/vendor files were not part of the diff.

**Next step:** No code change is indicated by this review; the report is the complete review result.
