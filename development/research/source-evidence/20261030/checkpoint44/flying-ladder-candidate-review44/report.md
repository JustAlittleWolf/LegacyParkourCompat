# Independent candidate review: flying ladder suppression

**Verdict: REVISE BEFORE INTEGRATION**

Candidate commit: `c1222df918f23de4ebef4110f93dbc730f8ab7b2` (`Add historical flying ladder descent suppression`). Accepted finding SHA-256 supplied by coordinator: `61cf320903eb570fe44b4b693f04dcb016f719f3bdaaec9384865b2b81aa0d62`.

## Confirmed

- `LadderSlideSuppression` implements the old predicate with only `player.isShiftKeyDown()`. This matches 1.15.2 Mojmap `LivingEntity.isSuppressingSlidingDownLadder()` (`LivingEntity.java:2686–2688`), which returns the virtual `isShiftKeyDown()` value. `LocalPlayer.isShiftKeyDown()` reads the shift input (`LocalPlayer.java:591–593`).
- The adjacent 1.16 Mojmap override is `LocalPlayer.isSuppressingSlidingDownLadder()` (`LocalPlayer.java:347–349`) and adds `!abilities.flying`, matching the candidate’s intended reversion for old profiles.
- The hook is independently discoverable through `@MechanicType`; changes are public, annotated and no-arg constructible. The existing resolver chooses the smallest `emulates` ordinal greater than or equal to the selected profile.
- The redirect matches the target operation in the compiled 26.2 merged deobfuscated JAR: `LivingEntity.handleOnClimbable(Vec3):Vec3` has an `invokevirtual LivingEntity.isSuppressingSlidingDownLadder:()Z`. The `@Redirect` target owner and descriptor `Lnet/minecraft/world/entity/LivingEntity;isSuppressingSlidingDownLadder()Z` match.
- The mixin is in the client mixin list. Non-`LocalPlayer` entities return native behavior; `MovementRuntime.find` also gates on client-side player and enabled movement profile. The candidate retains native fallback when no hook resolves.
- The hook changes only the suppression predicate. The historical negative-Y, scaffolding, Player, clamp and move-order gates remain in vanilla `handleOnClimbable`, so it does not duplicate the movement loop.

## Required correction

`NativeLadderSlideSuppression` is annotated `V1_15` and returns `nativeValue`. With resolver rule `smallest emulates >= selected`, both the `V1_15` profile and all earlier profiles that resolve to this fence bypass the shift-only implementation. This is incorrect for at least the `V1_15` profile: exact 1.15.0 and 1.15.1 Mojmap `LivingEntity.isSuppressingSlidingDownLadder()` at lines 2686–2688 returns `isShiftKeyDown()`, and the same helper conditionally zeroes negative Y at lines 1996–1998. The 26.2 LocalPlayer override still includes the flying gate. Thus V1_15’s no-op fence fails to restore the historical behavior the finding establishes. Resolve this before integration by making the applicable 1.15 change use the historical predicate or by placing a no-op fence only at a source-verified profile boundary where native behavior actually matches. Any still-earlier profile coverage needs source evidence or an explicit bounded scope; the accepted 1.15.2→1.16 finding alone does not prove a lower boundary.

No other source-fidelity, hook-target, client/server, arithmetic-gate, or registration blocker found. This review is source/code only; no build, tests, game launch, or parity claim was made.
