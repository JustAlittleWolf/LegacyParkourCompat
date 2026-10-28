# Independent source audit: local-player movement gates

## Verdict

**Supported with one scope qualification.** The closure's source-level descriptions of crouch caching, sprint-trigger reset, Elytra climb exclusion, auto-jump query argument, pose-fit deflation, water-descent predicate, and caller/tick order match the exact canonical Mojmap sources. The Levitation condition is also present exactly as reported, but the evidence does **not** establish a local-client movement effect: `updateFallFlying()` writes the recalculated shared flag only when `!level.isClientSide`. Treat this as a source gate / server-authoritative flag-retention difference pending separate applicability evidence; historical movement on logical servers is out of scope.

This is a bounded review of the submitted closure, not a full-pair inventory audit, acceptance of trajectory consequences, or pair freeze.

## Input identity and mechanical hashes

The submitted closure SHA-256 matches the assigned digest:

- `.native-source/20261028/frontier-c/1152-1161-local-player-gates-closure/closure.md`: `f73a1a4b6c417ebcc28b91e7ed811af4828bad6c4e8ee16ec27f71727302387f`

The canonical source trees are the paths stated in that closure. Their `mojmap.ready.json` files identify exact releases `1.15.2` and `1.16.1`, mapping `mojmap`, Vineflower `1.12.0`, and source counts 3,332 / 3,572. The ready markers' source-manifest SHA-256 values match the actual manifests:

- 1.15.2 `mojmap.sources.sha256`: `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`
- 1.16.1 `mojmap.sources.sha256`: `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`

I recomputed the four file hashes in the closure and matched each corresponding manifest row. The complete per-file SHA-256 table is in `inputs.sha256` alongside hashes for the closure and both source manifests.

## Claim audit

1. **Crouch predicate becomes an `aiStep` snapshot — verified.** In 1.15.2 `LocalPlayer.isCrouching()` computes the flight, swimming, crouch-fit and shift/standing-fit conditions when queried (596–600); `isMovingSlowly()` calls it (602–603), and `aiStep()` calls `input.tick(this.isMovingSlowly())` (625–636). In 1.16.1 `aiStep()` assigns `crouching` using the corresponding conditions (662–665) before `input.tick(...)` (666), while `isCrouching()` returns the field (625–627). This supports the described query-to-snapshot timing change. Raw input production/timing and collision-fit results remain unresolved.

2. **Sneak clears a pending sprint trigger — verified.** Both versions snapshot `input.shiftKeyDown` into `bl2` near the start of `aiStep()` (1.15.2 632–635; 1.16.1 659–666). Only 1.16.1 writes `sprintTriggerTime = 0` when `bl2` is true (688–690), after the four `checkInBlock` calls and before the double-tap sprint branch. That branch already requires `!bl2` in both versions. The stated pending-window reset is supported; raw input semantics remain unresolved.

3. **Elytra start uses `onClimbable()` rather than `onLadder()` — verified.** The final gate is `!onLadder()` in 1.15.2 `LocalPlayer.aiStep()` (715) and `!onClimbable()` in 1.16.1 (750), with the surrounding jump, flight-toggle, flying, passenger, chest-slot and Elytra checks retained. 1.15.2 `LivingEntity.onLadder()` checks ladder/vine/scaffolding plus usable trapdoors (1226–1236). 1.16.1 `onClimbable()` checks `BlockTags.CLIMBABLE` plus the same trapdoor path and records `lastClimbablePos` (1361–1377). Effective tag contents and their applicability remain unresolved.

4. **Levitation is added to `updateFallFlying()` — gate verified; local movement consequence unproven.** The 1.16.1 outer condition adds `!hasEffect(MobEffects.LEVITATION)` (2476–2490); 1.15.2 has no such term (2299–2313). In both versions, `setSharedFlag(7, bl)` is guarded by `!level.isClientSide` (1.15.2 2315–2317; 1.16.1 2492–2494), and `LivingEntity.aiStep()` calls `updateFallFlying()` immediately before `travel(...)` (1.15.2 2282–2288; 1.16.1 2452–2458). Therefore a local-client invocation recalculates a local temporary but does not itself clear the synchronized fall-flying flag. The source difference is real, but this bounded evidence does not prove changed local-client travel. Effect/flag provenance and exact applicability remain open.

5. **Auto-jump collision query argument changes — verified; provider meaning open.** The call is `getCollisions(this, aABB, Collections.emptySet())` in 1.15.2 `LocalPlayer.updateAutoJump()` (914–916) and `getCollisions(this, aABB, entity -> true)` in 1.16.1 (951–953). The report correctly leaves the provider/query semantics open; no auto-jump trajectory or equivalence conclusion follows from this check.

6. **Pose fit deflates the candidate AABB in 1.16.1 — verified.** `Entity.canEnterPose()` passes the unmodified pose box in 1.15.2 (1605–1607), and `.deflate(1.0E-7)` in 1.16.1 (1620–1622). `Player.updatePlayerPose()` keeps the same pose priority and fallback order (1.15.2 357–385; 1.16.1 350–378), and `Player.getDimensions(Pose)` uses the same pose map/default (1.15.2 2047–2049; 1.16.1 1987–1989). The complete `Entity.refreshDimensions()` bodies match in operation order and math; the line offsets differ only (1.15.2 2258–2286; 1.16.1 2298–2326). Exact fit consequences remain dependent on collision/noCollision providers and world-border behavior.

7. **Water-descent gate consults `isAffectedByFluids()` — verified.** The 1.15.2 local-player branch checks `isInWater()` and `input.shiftKeyDown` (722–725). The 1.16.1 branch additionally checks `isAffectedByFluids()` (758–760); `Player.isAffectedByFluids()` returns `!abilities.flying` (1005–1007). This confirms the predicate change only; water/fluid response semantics remain open.

8. **Tick and caller order — verified for this slice.** `LocalPlayer.tick()` calls `super.tick()` in both versions (1.15.2 179–182; 1.16.1 183–186). `Player.tick()` calls `super.tick()` and later `updatePlayerPose()` at its end (1.15.2 236–237, 281–284; 1.16.1 233–234, 274–277). `LivingEntity.tick()` dispatches `this.aiStep()` (1.15.2 2024–2025, 2103–2104; 1.16.1 2122–2123, 2168–2169), so the LocalPlayer override runs before the later pose update. Within LocalPlayer `aiStep()`, the 1.16.1 crouch assignment precedes input tick; the sneak reset follows `checkInBlock`; Elytra start precedes water descent; and `super.aiStep()` follows those local branches. The corresponding sequence is unchanged in 1.15.2 except for the audited additions and changed predicates.

## Explicitly unresolved

Raw input sampling and tick semantics; tag membership/effect/ability and synchronized Elytra state; collision and fluid providers; the exact patch cutover between the two endpoints; trajectory consequences; runtime parity; and full-pair inventory/freeze. Auto-jump provider semantics remain open. No product source, product history, wiki lane, backlog, generated source, test, build, game or server was consulted or run.
