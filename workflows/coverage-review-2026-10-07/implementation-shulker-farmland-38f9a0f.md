# Incremental static review: open shulker escape and existing farmland coverage

Reviewed implementation commit `38f9a0f68c2d021c06734238f144bb9521f34318` (`fix: preserve 1.15.2 open shulker escape`) on `feat/movement-impl-1-15-2-1-16-5`, against admitted finding `d859478b9f6de63ba4a121a1f31d6dc90ad4f51e` and independent acceptance `0b494993e7b9b40b5526c80e36c99407e7e405f3`. Verified the finding blob SHA-256 matches `11860ba106197cae687735e641db4c9ef7057ddadcddeafe5c16f012e8e657e4`. Diff scope: five Java files, 38 insertions and 5 deletions. Static review only; no tests, build, game, or runtime validation.

## Summary

No confirmed functional blocker found in the shulker implementation for the accepted V1_15_2 source claim. The generic hook now receives the selected profile version at the existing `LocalPlayer.suffocatesAt` return injection; both implementations use the updated signature. The base y-cell scan and injection phase are unchanged. The V1_15_2 registration overrides the suffocation predicate only for shulker blocks and preserves the 1.15.2 unconditional suffocation rule.

The source handoff is deliberately bounded to V1_15_2. The resolver also selects the V1_15_2 implementation when V1_15 or V1_15.1 is selected, but the explicit `target == V1_15_2` guard falls through to the base predicate for those profiles. Their open-shulker behavior is not established by the accepted finding; this review does not infer or claim it.

The requested farmland check found no new implementation change: the full-height V1_9/V1_10 behavior and V1_10_1 conversion-push behavior already existed at original campaign baseline `0c16f9a2d8030858a8293b6f2bf8fea4a84b4344`. The accepted F001 review `804375e6929fd6549a25aab756cea429f329c1d3` validates the bounded source finding; it does not make the existing implementation new work in this range.

## Shulker hook and source mapping

The route is `LocalPlayerMixin.suffocatesAt` return → `MovementRuntime.find(SuffocationProbeBehavior.class, player)` → `behavior.suffocatesAt(player, MovementRuntime.profile(player).target(), pos, callback::getReturnValue)`. The version argument is the entity-specific selected profile, including per-player selection.

There are exactly two `SuffocationProbeBehavior` implementations in the reviewed commit tree:

- `v1_15_2.SuffocationProbe`, registered from the V1_15_2 provider and inheriting the shared scan from the V1_16 implementation. At target V1_15_2, it returns true for any `ShulkerBoxBlock` regardless of open state, matching the admitted 1.15.2 `ShulkerBoxBlock.isSuffocating()` source. Other blocks continue through the base predicate.
- `v1_16.SuffocationProbe`, registered from the V1_16 provider. It uses the current block state's `isSuffocating` predicate.

The resolver groups by the shared `player.client_unstuck.suffocation` mechanic key and chooses the closest eligible registration. V1_15_2 resolves the subclass; V1_16 resolves the base implementation; V1_16_2 and later have no V1_16-or-later registration and keep the native query. `CURRENT` resolves no changes. The V1_15_2 subclass carries the version annotation used by registration, while the registry discovers its inherited mechanic interface; these registrations have distinct emulated versions and do not collide.

The diff leaves the V1_16 base method's `minY`/`maxY` computation, integer y scan, return behavior, and `LocalPlayer.suffocatesAt` return injection point unchanged. It replaces only the scanned state's predicate call with a protected method so V1_15_2 can specialize it. With no applicable hook, the mixin leaves the vanilla return untouched. The client-local-player scope remains the existing movement path.

## Existing farmland coverage check

At baseline `0c16f9a`, `FarmlandFullCollision` was already registered at `V1_10` and returned `Shapes.block()`. Resolver selection therefore gives a full collision cube for V1_9 and V1_10. At V1_10_1 (which groups 1.10.1 and 1.10.2), that older shape registration is excluded, so the native 15/16 (0.9375) collision shape remains.

The V1_10_1 `FarmlandConversion` behavior suppresses player push-up in the scoped `FarmlandBlock.turnToDirt` → `Block.pushEntitiesUp` path. Its context is active only around that farmland call and checks `entity instanceof Player`; `MovementRuntime.find` resolves the historical player profile, while CURRENT/disabled returns no behavior and preserves vanilla. The corresponding classes have since been reorganized into version packages on the integration branch, but the behavior predates the original campaign baseline. No duplicate patch is warranted.

## Six review angles

1. Requirement: the V1_15_2 open-shulker source claim is implemented at its registered sample predicate; farmland behavior is pre-existing coverage.
2. Correctness: no confirmed mismatch for V1_15_2; the shulker predicate is unconditional there as admitted source requires.
3. Missing paths: no other suffocation-hook implementation or call site missed the new version argument. V1_15/V1_15.1 are left unclaimed by this exact-snapshot handoff.
4. Conventions: the change remains an independent versioned delta and reuses the existing generic hook and scan.
5. Visibility: only the existing local-player escape query changes for the selected V1_15_2 profile; no permission, protocol, or UI behavior changes.
6. Security: no new trust boundary or externally supplied data path is introduced.

## Not verified

- No runtime open-shulker witness, build, or test was run.
- First changed release remains unknown within `(1.15.2, 1.16.5]`; no claim is made for V1_15/V1_15.1 from this accepted snapshot.
- Farmland runtime trajectories were not checked; the review only verifies existing route composition and the accepted source boundary.
