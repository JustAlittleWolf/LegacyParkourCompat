# Independent audit — jump local prediction r2

**Verdict: ACCEPT** — bounded conditional source path and no-difference comparison.

## Binding and source identity

Audited report: `build/later-source-20261116/1.20.2--1.20.4/jump_local_prediction_revision_r2/coverage-r2.md`, SHA-256 `713EBE34022A1DE73CF0430DBC466B7BB4ED3B3AEF71881BC2CBE177FFF5ACE4`.

Both endpoints are exact Mojmap publications. `decompiled_minecraft/1.20.2/mojmap.ready.json` and `decompiled_minecraft/1.20.2/mojmap.provenance.json` declare version `1.20.2`, mapping/namespace `mojmap`; the ready marker's source manifest SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` matches the current `mojmap.sources.sha256`. Provenance file SHA-256: `1DAD9F725F6CB25B6568700FA0294F83B66EA9633B0D62ED354D7BD8B45D4F05`. The corresponding 1.20.4 files declare `1.20.4` / `mojmap`; ready source-manifest SHA `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` matches current manifest; provenance file SHA-256 `10914D9B7B3FC29DE066CA24F7D905E4D4F0A451A6C81729A2D1981ADDFC1385`.

For each endpoint, current bytes match its `mojmap.sources.sha256` entries for `KeyboardInput.java`, `LocalPlayer.java`, `Player.java`, and `LivingEntity.java`. Hashes are exactly those listed in the audited report. `KeyboardInput.java` and `LocalPlayer.java` are byte-identical across these endpoints; `Player.java` and `LivingEntity.java` differ elsewhere, while the cited jump-gate and player fluid-predicate bodies inspected here match in operation and ordering.

## Audit findings

The corrected r2 chain is source-supported:

1. `KeyboardInput.tick()` assigns the key state to `input.jumping` (line 28); `LocalPlayer.aiStep()` refreshes input (line 664) and the auto-jump timer can also set `input.jumping = true` (lines 672–677).
2. The entry is reachable from the local player tick: `LocalPlayer.tick()` calls `super.tick()`; inherited `LivingEntity.tick()` then invokes virtual `this.aiStep()` when not removed. `LocalPlayer.aiStep()` runs the input update and later calls `super.aiStep()`, followed by `Player.aiStep()` into `LivingEntity.aiStep()`.
3. In `LivingEntity.aiStep()`, the immobile branch clears `jumping`; otherwise, the effective-AI branch calls `serverAiStep()` before the jump gate. `LocalPlayer.isEffectiveAi()` overrides the generic client-side false result and returns `true`. `LocalPlayer.serverAiStep()` copies `input.jumping` into the entity `jumping` field only while `isControlledCamera()` is true (camera entity identity equals this local player).
4. The jump branch requires the entity's `jumping` field and `isAffectedByFluids()`. `Player.isAffectedByFluids()` is `!abilities.flying`, not a fluid-contact test. Thus an ordinary non-flying player satisfies it on dry ground. With no water/lava contact, the nested branches reach `jumpFromGround()` when `onGround()` is true and `noJumpDelay == 0`; the method then sets the ten-tick delay. The normal player override delegates to `LivingEntity.jumpFromGround()`.

The earlier report's negative result was invalid for two independent reasons: this fluid predicate does not exclude dry ground, and the local player overrides `isEffectiveAi()` to make the `serverAiStep()` assignment path reachable. The r2 correction fixes both. The audited gates are retained: loaded local-player tick, player not removed or immobile, local player is controlled camera, non-flying, on ground, jump signal true, and delay zero after the tick's preceding decrement. No fluid contact is required.

The cited relevant bodies and assignment order are the same in 1.20.2 and 1.20.4. The r2 bounded **NO_DIFF** conclusion is accepted. It does not claim that every user-input timing, jump source, movement dependency, whole pair, or runtime outcome was audited.

## Scope limits

This verdict covers only the static local input/auto-jump signal path to `jumpFromGround()` for the two exact endpoint source trees. It makes no runtime-parity claim and does not close the 1.20.2→1.20.4 pair. No implementation, test, build, game, or runtime work was performed.
