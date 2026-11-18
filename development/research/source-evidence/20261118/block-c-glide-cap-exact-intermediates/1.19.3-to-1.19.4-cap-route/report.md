# Active Elytra route, 1.19.3 → 1.19.4

## Evidence identity

Exact decompiled sources: Mojmap `1.19.3` and `1.19.4` from the adjacent pair's canonical ready trees. Each `.provenance.json` names its exact `versionId`, `namespace: mojmap`, shared exact run for `1.19.2, 1.19.3, 1.19.4`, and the same decompiler/tool inputs. The ready markers report `status: ready` and identify these source-manifest SHA-256 values:

- `1.19.3/mojmap.sources.sha256`: `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`
- `1.19.4/mojmap.sources.sha256`: `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`

The manifest-file hashes match those marker values. Examined-source hashes (matching each source manifest):

| Source | 1.19.3 SHA-256 | 1.19.4 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` |
| `net/minecraft/world/entity/LivingEntity.java` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` |
| `net/minecraft/client/player/LocalPlayer.java` | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` |

## Bounded comparison

**Local travel gate.** `LivingEntity.travel(Vec3)` in 1.19.3 enters its movement body when `isEffectiveAi() || isControlledByLocalInstance()` (`LivingEntity.java:2052-2053`); 1.19.4 checks only `isControlledByLocalInstance()` (`1983-1984`). For a local player, `Entity.isControlledByLocalInstance()` resolves the controlling player and returns that player's `isLocalPlayer()` in both releases (`Entity.java:2742-2745`, `2795-2797`); therefore this gate still admits the local player in both. The removed alternative changes the gate for actors admitted only by effective AI, outside this ordinary local-player assignment.

**Fall-flight update and direct flag write.** `LivingEntity.updateFallFlying()` is otherwise textually the same in the inspected bodies (`1.19.3 LivingEntity.java:2637-2660`; `1.19.4:2588-2611`). With the shared fall-flight flag true and the actor airborne, unmounted, without Levitation, and wearing an enabled chest Elytra, its local `$$0` remains true. A failed gate or invalid chest item sets only that local boolean false. The final `setSharedFlag(7, $$0)` is guarded by `!level.isClientSide`, so this method does not directly clear the flag on the logical client. `LivingEntity.aiStep` calls `updateFallFlying()` immediately before `travel(...)` in both (`1.19.3:2597-2599`; `1.19.4:2543`, with the travel call at `2550` after 1.19.4's ridden routing). No release difference was found in this bounded update behavior.

**Active glide versus climbable route.** In the dry, non-fluid case, `LivingEntity.travel` selects `else if (isFallFlying())` before its ordinary friction/ground branch (`1.19.3:2117-2143`; `1.19.4:2048-2074`). The active glide branch checks slow-fall distance, computes glide velocity, applies `setDeltaMovement`, then reaches its first `move(MoverType.SELF, ...)`. A climbable feet state does not redirect this already-active glide branch. The ordinary branch instead reaches `handleRelativeFrictionAndCalculateMovement`, which runs `handleOnClimbable` before its `move` (`1.19.3:2199-2204`; `1.19.4:2150-2155`). These branch relationships are unchanged. Water/lava precedence and all collision effects are excluded here.

**Fall-distance cap and phase.** `Entity.checkSlowFallDistance()` in 1.19.3 tests `this.deltaMovement.y() > -0.5 && this.fallDistance > 1.0F`; 1.19.4 tests `this.getDeltaMovement().y() > -0.5 && this.fallDistance > 1.0F`. Both set `fallDistance = 1.0F` only when both strict comparisons pass (`Entity.java:2147-2150` / `2167-2170`). Thus the cap is exactly 1.0F, not a general clamp: it leaves fall distance at or below 1.0F unchanged and does nothing at vertical motion `-0.5` or below. The glide branch calls it before calculating glide motion and before its first `move` (`LivingEntity.java:2118` then `2143`; `2049` then `2074`). The source change is direct-field access to the getter; the threshold, assignment and phase are the same. Final per-tick fall-distance accumulation is outside this report.

## Bounds

This covers only a local client player with ordinary dry travel, default chest-slot enabled Elytra, an already-active shared fall-flight flag, and the active-glide route at climbable or ordinary feet. Start eligibility, final tick accumulation, fluids, collision-response details, other entities, and all remaining movement/tick slices were not audited. This report is source evidence for the assigned slice, not pair-completion coverage or an independent acceptance verdict.