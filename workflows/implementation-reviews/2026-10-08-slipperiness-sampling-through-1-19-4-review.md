# Independent implementation review — slipperiness sampling through 1.19.4

**Decision: ACCEPT** — static implementation review of `dd725b49a343a478b26a88528b2e3c61698a752e` at merged feature tip `5acdad5baa7261251cb442b00c6890e65f583b29`.

**Reviewed feature branch:** `fix/slipperiness-sampling-through-1-19-4-oct8`  
**Current main included:** `d4c4f154a0c2487dd6dd7d20d92eb57b3d8ab1ae`  
**Review branch:** `fix/review-slipperiness-range-1-19-4-2026-10-08`  
**Review worktree:** `D:/Javastuff/LegacyParkourCompat/.task-worktrees/review-slipperiness-range-1-19-4-2026-10-08`

## Scope and outcome

Against current `main`, the feature adds one registration in `change/v1_19_4/MovementChanges.java` and its implementation handoff. It reuses the existing `V1_15_2` `GroundFrictionSamplePoint`; it does not change that sampler, the resolver, the shared mixin hook, or block state. No functional findings were confirmed. Review is static correspondence and resolver analysis; it does not claim runtime parity or full movement-discovery coverage.

## Source identity and limits

The exact-source snapshot is commit `5688e68528861c91031a021f484f80a90c887f2f`, path `workflows/source-campaign-2026-10-07/slipperiness-source-range-evidence-2026-10-08.md`, Git blob `17f0906b1fa7d5e6c7995835786fb9516fc5a6d7`, in the local source object store at `D:/Javastuff/LegacyParkourCompat/.task-worktrees/review-mcpk-slipperiness-1-15-2-2026-10-08/.git`. Independent source-only acceptance is commit `65b711d6a5145dbcc979d982de334e2a762f401c`, path `workflows/source-campaign-2026-10-07/reviews/slipperiness-source-range-review-2026-10-08.md`, in that same object store. That verdict accepts source evidence only; implementation resolution was reviewed separately here.

The accepted review prints raw snapshot SHA-256 `dd7d640f7fb206eb5f69bf73bb5b80d929889d03c7e94a9c8a9918efb8357c8e`. Recomputing the hash from the snapshot blob yields `dd7d64037fb206eb5f69bf73bb5b80d929889d03c7e94a9c8a9918efb8357c8e` (third character after the `dd7d640` prefix differs). The immutable commit, path, and blob ID match, and the `Entity.java` hashes in the ten-row source table were independently rechecked against the ready source tree. This is a transcription discrepancy in the raw-hash field, not a source-identity ambiguity.

The source snapshot confirms the direct double-offset sample `BlockPos.containing(x, boundingBox.minY - 0.5000001, z)` at its ready endpoints from 1.15.2 through 1.19.4, with the 1.20.1 source using the support cache / `getOnPos(0.500001F)` family. The snapshot does not locate the exact transition within `(1.14.4, 1.15.2]` or `(1.19.4, 1.20.1]`; this review preserves both limits. For 1.17 and 1.18 profile groups, the ready source representatives are 1.17.1 and 1.18.2 respectively. No intra-group patch cutover is claimed.

## Implementation and resolution

`change/v1_15_2/GroundFrictionSamplePoint.java` returns `BlockPos.containing(entity.getX(), entity.getBoundingBox().minY - 0.5000001, entity.getZ())`. The `0.5000001` literal is a `double`; expression shape and operand order preserve the source arithmetic before block-coordinate flooring. The ordinary `V1_15_2` registration remains in `change/v1_15_2/MovementChanges.java`. `change/v1_19_4/MovementChanges.java:12-16` explicitly registers the same object as `GroundFrictionBlockBehavior` emulating `V1_19_4`.

`ChangeResolver.resolve` filters registrations older than the selected profile and picks the nearest eligible registration per mechanic key; it returns no changes for `CURRENT`. The ground-friction key has the existing V1_14 support-cell implementation, the V1_15_2 direct sampler, and this V1_19_4 registration. The result is:

- `V1_8` through `V1_14`: existing V1_14 `minY - 1.0` sampler.
- `V1_15`, `V1_15_2`: existing V1_15_2 direct-double sampler.
- `V1_16`, `V1_16_2`, `V1_17`, `V1_17_1`, `V1_18`, `V1_18_2`, `V1_19`, `V1_19_4`: V1_19_4 registration of that same direct-double sampler.
- `V1_20` and later historical profiles: no eligible registration; vanilla fallback.
- `CURRENT`: resolver explicitly returns no historical changes.

The `LivingEntityMixin` redirect at line 415 scopes dispatch to the ground-friction lookup in `travelInAir`, delegates through `MovementRuntime.find`, and uses the vanilla callback when no behavior resolves. It does not replace other callers of the shared block-position helper. Registration uniqueness was checked: the two registrations intentionally have distinct emulated versions, and no third `GroundFrictionBlockBehavior` implementation conflicts with the key.

## Current-main integration and six review angles

The branch tip already contains current main; merging `main` into the isolated review branch was already up to date. The accepted main hook remains compatible with the added provider registration. The focused diff from current main contains no changes to shared hooks or resolver behavior.

1. **Requirements and scope — PASS.** The one requested sampler is extended to the V1_19_4 boundary; the 1.14/1.15 and 1.20+ boundaries remain intact.
2. **Correctness and edge cases — PASS.** The exact double subtraction, operand order, and vanilla fallback match the checked sources and shared hook contract.
3. **Coverage and version boundaries — PASS with source limits.** Resolver behavior maps the requested profiles to the registration. Source evidence does not establish unobserved patch-level changes inside grouped profiles or either outer interval.
4. **Architecture and conventions — PASS.** The change is a granular mechanic registration reusing the existing implementation; no physics loop, mixin, block state, or resolver refactor was added.
5. **Permissions and user-visible behavior — PASS.** No permissions, configuration, network protocol, or UI behavior changed.
6. **Security — PASS.** No new external input, persistence, trust boundary, or resource access was introduced.

No tests, build, client, TAS, server, Gym, or Docker operation was run.

Reviewer: isolated static implementation review. Decision date: 2026-10-08.
