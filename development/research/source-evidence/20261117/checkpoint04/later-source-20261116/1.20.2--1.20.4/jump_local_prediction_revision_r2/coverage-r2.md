# Dry-ground local jump-prediction route coverage — r2

**Scope.** Corrected source-only trace of the local jump-key signal through the 1.20.2 and 1.20.4 Mojmap player update paths and into `jumpFromGround`. This revises the prior negative conclusion after finding that `LocalPlayer` overrides the generic effective-AI predicate. It does not repeat the jump-formula comparison, support selection, or `Entity.move`, and makes no runtime-parity or full-pair-coverage claim.

## Revision lineage and exact source identity

This supersedes the conclusion in `build/later-source-20261116/1.20.2--1.20.4/jump_local_prediction/coverage.md` (SHA-256 `B184585CF7AF04E39B7C9A767A0BD0839DB4145443612F430F2D7DBCA7A1F905`), which received REQUEST CHANGES in `jump_local_prediction_audit/audit.md` (SHA-256 `41B2752660583159DCD616B000D90BB5467FFADBB4FACF0A87E58DA0F0EFAB9F`). The audit correctly identified the invalid fluid-gate rationale, but its client-path rationale overlooked `LocalPlayer.isEffectiveAi()` overriding the generic `Entity.isEffectiveAi()` result.

Both exact-release publications use Mojmap. Each publication's identity files are at its version directory root: `decompiled_minecraft/<version>/mojmap.ready.json`, `mojmap.provenance.json`, and `mojmap.sources.sha256`; Java sources are under `decompiled_minecraft/<version>/mojmap/`. Provenance SHA-256 values are 1.20.2 `1DAD9F725F6CB25B6568700FA0294F83B66EA9633B0D62ED354D7BD8B45D4F05` and 1.20.4 `10914D9B7B3FC29DE066CA24F7D905E4D4F0A451A6C81729A2D1981ADDFC1385`. The ready markers and source manifests were already independently bound by the prior audit; the relevant files were rechecked against the same manifest entries and current bytes for this revision.

| Exact Mojmap source | 1.20.2 SHA-256 | 1.20.4 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/KeyboardInput.java` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | same |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | same |
| `net/minecraft/world/entity/player/Player.java` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| `net/minecraft/world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |

## Corrected local-client trace

1. `KeyboardInput.tick()` writes the jump key to `input.jumping` (`KeyboardInput.java:28`). `LocalPlayer.aiStep()` ticks that input (`LocalPlayer.java:664`) and may also set `input.jumping = true` for its auto-jump timer (`:676`). It then calls `super.aiStep()` (`:800`), which reaches `Player.aiStep()` and `LivingEntity.aiStep()`.
2. `LivingEntity.aiStep()` calls `serverAiStep()` when `!isImmobile()` and `isEffectiveAi()` (`LivingEntity.java:2549–2555` in 1.20.2; `:2551–2557` in 1.20.4). The generic `Entity.isEffectiveAi()` returns `!level().isClientSide`, but that is not the effective result for the local player: `LocalPlayer.isEffectiveAi()` overrides it and returns `true` (`LocalPlayer.java:487–489` in both releases). Thus this call is reachable on the ordinary client-local-player path.
3. `LocalPlayer.serverAiStep()` copies `input.jumping` to the entity `jumping` field when `isControlledCamera()` (`LocalPlayer.java:610–621` in both releases; the predicate is `minecraft.getCameraEntity() == this` at `:623–625`). With the local player as the camera and the player not immobile, the same `LivingEntity.aiStep()` then reaches its jump gate. The ordinary player override `Player.isAffectedByFluids()` returns `!abilities.flying` (`Player.java:1042–1044` in 1.20.2; `:1043–1045` in 1.20.4), so this gate is true for a non-flying player even while dry. When on ground and `noJumpDelay == 0`, the inner gate calls `jumpFromGround()` (`LivingEntity.java:2561–2574` in 1.20.2; `:2563–2576` in 1.20.4). The local keyboard/auto-jump signal can therefore reach the ground-jump operation without a fluid contact.
4. `Player.aiStep()` delegates to `super.aiStep()` in both versions (`Player.java:522` in 1.20.2; `:523` in 1.20.4). The `Player.jumpFromGround()` override delegates to `LivingEntity.jumpFromGround()` and applies player-specific follow-up (`Player.java:1439–1444` in 1.20.2; `:1440–1445` in 1.20.4). The separate server packet listener path remains distinct and is not required for this local path.

## Bounded conclusion

The corrected source trace finds a dry-ground local-client `jumpFromGround()` route in both 1.20.2 and 1.20.4: keyboard or auto-jump updates `input.jumping`; `LocalPlayer` is effective AI on the client; its controlled-camera `serverAiStep()` copies the signal to `jumping`; and the ordinary non-flying player's fluid predicate permits the same-tick on-ground/no-delay jump gate. The earlier negative conclusion was incorrect. This is a conditional static source-path result; it does not establish runtime behavior, input timing outside the shown call chain, or whole-pair equivalence. No difference in this bounded route was found between the two exact releases.
