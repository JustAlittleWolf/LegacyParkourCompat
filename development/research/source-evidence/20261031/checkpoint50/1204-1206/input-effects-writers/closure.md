# Residual local input and movement effect writer slice

Pair: Minecraft 1.20.4 Mojmap → 1.20.5 Mojmap; 1.20.6 Mojmap checked as later context. This bounded slice found no new source-supported ordinary-player movement delta. It does not claim pair freeze.

## Source readiness

The exact Mojmap ready markers and manifests were read from the shared read-only source set. The `.4` ready marker names source-manifest SHA-256 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` and client SHA-256 `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`. The `.5` marker names source-manifest SHA-256 `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` and client SHA-256 `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510`. The `.6` marker names source-manifest SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` and client SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`.

## Local input checks

`net/minecraft/client/player/LocalPlayer.java`, 1.20.4 lines 596–598 and 1.20.5 lines 598–600: `isShiftKeyDown` returns `input != null && input.shiftKeyDown` on both sides. `isMovingSlowly` remains `isCrouching() || isVisuallyCrawling()` (1.20.4 lines 605–607; 1.20.5 lines 607–609).

The use-item slowdown in `aiStep` is unchanged: when using an item and not a passenger, multiply both left and forward impulse by `0.2F` and reset `sprintTriggerTime` (1.20.4 lines 666–670; 1.20.5 lines 669–673). The sprint stop/start checks, underwater condition, and shift-held swimming gate preserve their operation and inputs; the diff here is temporary-local renaming. `LocalPlayer` does have the separately assigned 1.20.5 grounded flight-toggle `jumpFromGround()` call (1.20.5 lines 732–735); that is covered in the jump/flight draft and is not repeated as a finding here.

## Existing movement effects and effect-state updates

`MobEffectInstance.update` retains the same amplifier precedence, hidden-effect handling, duration replacement, ambient update, visibility and icon gates (1.20.4 lines 92–135; 1.20.5 lines 132–175). The changed key comparison is object identity on `MobEffect` versus `Holder.equals`; this check warns on a mismatching effect and normal vanilla registry-backed instances use their corresponding built-in holder. The exposed getter changes from `MobEffect` to `Holder<MobEffect>`.

The new `MobEffectInstance.tick` removes an effect if an invoked `applyEffectTick` returns false (1.20.5 lines 217–233). The movement-affecting vanilla effects examined (`MOVEMENT_SPEED`, `JUMP`, `LEVITATION`, `SLOW_FALLING`, `CONDUIT_POWER`, and `DOLPHINS_GRACE`) are registered as plain `MobEffect` values in both releases (`MobEffects.java`: 1.20.4 lines 10, 37, 66, 77–79; 1.20.5 lines 16, 43, 76, 87–89). Their base `shouldApplyEffectTickThisTick` returns false (1.20.4 `MobEffect.java` lines 46–48; 1.20.5 lines 72–74), so they do not enter the new removal branch. Speed attribute values and jump/travel consumers are assigned to sibling lanes. Periodic non-movement effects and their food/damage consequences are outside this slice/project scope.

## File identity

SHA-256 is over the compared decompiled Java source file. LocalPlayer: 1.20.4 `BB5CBFB03656A1866BB77C00431618BEFE792081223B35DB4BD121ECBB151FD5`; 1.20.5 and 1.20.6 `6B429DFA6E0681251EC985DDA1627F808652A7BBE5B70DC85C8FA0FE0ED46FFA`. `MobEffects.java`: 1.20.4 `E8974D2E12C774AAB348A65D0ADE44FD3A8522270C403CB5DCC2C6DB3C25FBAE`; 1.20.5 and 1.20.6 `9B24DEE8A23A4B8730FDFA7EA9CBD560C9370599CC282FAC1C0755F5894E7645`. `MobEffect.java`: 1.20.4 `FE3D18C51352A1E84CF96BCF6386FD19B4FA6BF6D7BD667B813699FAB57EFAE5`; 1.20.5 and 1.20.6 `E5EF74736E7A4B8CD5103FB5BBBE10B1AEB0BE3531CF5600A39E08761C3746E2`. `MobEffectInstance.java`: 1.20.4 `E1AAC04C3F5714C5C47F84375F1D930204BD177218C05D8C0B590D70381AA249`; 1.20.5 and 1.20.6 `E14F5BB6BDCCD3F300F092EBEE3FFFCAF875E01051987494A8D25CCF306EDEC1`.

## Limits and next action

This slice did not audit or close collision-owned block callbacks, packet/server effect production, all external velocity writers, the travel/effect consumers assigned to the travel lane, or the pair-wide tick and resource/tag inventory. No implementation, tests, build, launch, or decompilation was performed.
