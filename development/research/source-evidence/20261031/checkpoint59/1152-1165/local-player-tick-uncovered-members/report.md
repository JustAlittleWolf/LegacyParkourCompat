# Residual local-player tick members — 1.15.2 → 1.16.5 Mojmap

## Scope and source identity

Source-only audit of exact changed `LocalPlayer.java` members and inherited `LivingEntity` / `Player` movement members omitted or explicitly left open by the reused pool33/finish43 tick, fluid, collision, and suffocation closures. Pair: Minecraft 1.15.2 Mojmap → 1.16.5 Mojmap. Canonical roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap` and `...\1.16.5\mojmap`.

The corrected canonical full-tree manifests revalidated by `.native-source/20261030/1152-1165-pool33/manifest-verification.json` have SHA-256 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` (1.15.2, 3,332 files) and `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` (1.16.5, 3,523 files), with zero missing, unlisted, or mismatched paths/content. The three class hashes below match the corrected direct manifests and `tick-residual/source-hashes.tsv`.

## Independent movement candidates

### LocalPlayer shift input clears the sprint-start timer

- 1.15.2 Mojmap `net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 625–671: the tick decrements positive `sprintTriggerTime`; while using an item it clears the timer. It has no shift-key clear.
- 1.16.5 Mojmap same file, `aiStep`, lines 627–681: after closest-space handling, `if (bl2) this.sprintTriggerTime = 0;` is added, where `bl2` is the shift bit captured earlier in the tick. The subsequent automatic sprint gate excludes `bl2`, and starts a seven-tick trigger window when the timer is nonpositive; with a positive timer it starts sprint immediately.

Concrete conditional movement path: a first forward tap has left `sprintTriggerTime > 0`; a sampled shift-held tick clears it only in 1.16.5. After shift is released, a second forward press before the old timer would expire starts sprint immediately in 1.15.2, but begins a fresh seven-tick window in 1.16.5. Once the sprint flag changes, `Player.aiStep` / `LivingEntity.travel` consume the sprint state for movement speed. The shift-held gate prevents sprint start during the clearing tick itself; the difference is the retained double-tap state on a later tick. This is a local movement-state delta, with no tag or packet dependency.

### Ability-flying player shift descent in water

- 1.15.2 Mojmap `LocalPlayer.java`, `aiStep`, line 723: calls `goDownInWater()` when `isInWater() && input.shiftKeyDown`.
- 1.16.5 Mojmap same member, line 733: adds `&& isAffectedByFluids()` to that gate. `Player.isAffectedByFluids()` at 1.16.5 `world/entity/player/Player.java:1005–1007` returns `!abilities.flying`; old Player's corresponding `isPushedByWater()` similarly returns `!abilities.flying`, but it did not gate this direct LocalPlayer descent call.
- `LivingEntity.goDownInWater()` is otherwise identical: it adds `(0.0, -0.04F, 0.0)` to delta movement (old `:1819–1821`, new `:1898–1900`).

For an ability-flying local player in water with shift held, the old local tick applies the `-0.04F` vertical velocity adjustment and the new tick skips it. `Player.travel` captures the incoming Y velocity before its flying branch and restores `i * 0.6` after `super.travel`, so this direct input response is retained as a different vertical movement request. This is distinct from the shared water-current branch, which both player versions gate off while ability-flying.

### Ability-flying player jump selection in LivingEntity.aiStep

- 1.15.2 Mojmap `world/entity/LivingEntity.java`, `aiStep`, lines 2265–2277: every `jumping` state enters water/lava/ground jump selection; on dry ground with `noJumpDelay == 0`, it calls `jumpFromGround()` and sets the ten-tick delay.
- 1.16.5 Mojmap same member, lines 2427–2449: the selection is guarded by `jumping && isAffectedByFluids()`. Otherwise it resets `noJumpDelay` without jumping. During inherited `LivingEntity.aiStep`, virtual `serverAiStep` copies the LocalPlayer sampled jump input into `this.jumping` before the jump-selection block; the inherited `Player` override above makes the guard false during ability-flight.
- In clear air, for a grounded ability-flying LocalPlayer whose jump input has been copied to `jumping` by `serverAiStep` and whose delay is zero, the old code writes the ground-jump impulse while the new code does not. `Player.travel`'s ability-flight branch captures the incoming vertical velocity and later retains 60% of it, so the old impulse reaches the local move on that tick. This is an independent local movement gate, not the ordinary nonflying fluid-height branch closed by the reused fluid report.

These two ability-flight cases arise from separate operations (LocalPlayer water descent and inherited jump selection). Intermediate introduction patches are unknown within the endpoint interval.

## Remaining named callers and dispositions

- `LocalPlayer.updateAutoJump` changes the collision query from `getCollisions(this, box, Collections.emptySet())` to `getCollisions(this, box, entity -> true)` (old lines 924–925, new lines 936–937). `CollisionGetter`/`EntityGetter` receiver closures explain the overload/filter migration; local-player entity candidates are same-form. The finish43 collision-collection report still leaves a narrow canonical-full-block `Shapes.block()` grid-snap witness in the shared block-collision fast path. Auto-jump uses that collector too, but this audit did not establish a separate auto-jump-specific coordinate witness; keep the collector's existing movement candidate open without duplicating it as an accepted finding.
- The `LocalPlayer.isSuppressingSlidingDownLadder` override and `LivingEntity.handleOnClimbable` consumer are already detailed and conditionally closed by `tick-residual/coverage-correction.md` and `coverage-tag-gates.md`; no new member delta is asserted here.
- `LocalPlayer.moveTowardsClosestSpace` / `suffocatesAt` and block suffocation providers are covered by the named closest-space and suffocation closures. Its new directional iteration and predicate-before-shape route are not reopened here.
- `Player.maybeBackOffFromEdge` / `isAboveGround` have a separate source candidate and independent audit in pool33's sneak-edge-backoff files. `Player.tryToStartFallFlying`'s Levitation gate has its separate clear-air local-start audit in `elytra-levitation-findings-audit/audit.md`. The remaining changed Player/LivingEntity methods examined in the named movement residuals were same-form for equal fixed movement state, server-only, vehicle/non-player, modern-only, damage/health/food/statistical, or excluded presentation paths.

This report adds no claim of pair freeze, acceptance, intermediate boundary, implementation, or runtime parity. No product source, canonical decompilation, tests, build, or runtime was changed or used.
