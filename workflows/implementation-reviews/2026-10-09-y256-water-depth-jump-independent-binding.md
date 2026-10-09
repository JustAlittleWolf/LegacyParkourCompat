# Y=256 retained water-depth jump independent technical binding

## Verdict

**REQUEST CHANGES — the corrected implementation still rejects an accepted historical jump case.** The added `jumpInput` requirement is supported by the source path. The added `!player.onGround()` requirement is not: exact 1.13.0 and 1.15.2 consumer code selects the water jump for `jumping == true` and water depth `> 0.4` even when `onGround == true`. A player standing on top of a block at y=255 can have a contracted bounding-box minimum whose floor is 256, so the rejected case is reachable at the build-height boundary. Remove the airborne restriction or provide exact source evidence that changes this branch; the accepted evidence presently points the other way.

## Immutable review binding

| Item | Identity |
|---|---|
| Initial implementation | `8bf7943366b54a156b4a2542baace0761a5b3069` |
| Main merge in candidate history | `803f97c3cd38d91f40e0d29f109d6d70d2fa8530`, second parent `be97698305315c5ac7c7a3c2dbdab2bf436a9881` |
| Corrected implementation | `6401f7939992941950157c23d75ee5b9d0339218`, tree `4a23da5e5e98aa10a355baba658ff9fad06f5038` |
| Candidate handoff tip reviewed | `3f361357423ca2d7f4abb9a8833957000bf938e5`, tree `d0476da33130ecde3893bd9b711ca93fd7d8566e` |
| Candidate review-record blob | `44d268b05225011146ce26807350fa6ea3288254` |
| Independent review checkout base | `dc5ee06f7afaf307b77174b5b66a8d0c9ac88858`, tree `013a5e96820c9df1c6f2fdea80d829f50c3a954c` |

The corrected implementation files are unchanged between `6401f793` and candidate tip `3f361357`; the latter adds documentation. Candidate blobs reviewed: `WaterDepthJumpGate.java` `cb86661745af7548c4e47550348dde8de77c12c5`; `StaleWaterDepthJumpBehavior.java` `f0fe51747e31269e78e29d99ccf2dd642399c7f8`; `LivingEntityMixin.java` `08ae94aa9da37c91e5fca3f20e4a8efd443263b5`; `EntityMixin.java` `bc6d1f2a77e070fd7924891c4759436e02e9387f`; `MovementChangeCatalog.java` `a180a6df4b35fbd66de3a4b77ca25346add7bc68`.

The assigned review branch was created from current `main` at `dc5ee06f7afaf307b77174b5b66a8d0c9ac88858` in `D:\Javastuff\LegacyParkourCompat\.task-worktrees\y256-technical-binding-2026-10-09`. Its Git root and branch were verified before work. The branch started clean. The four commits after candidate merge parent `be976983` through this base change documentation only; no later movement-code change altered the candidate's integration context.

## Accepted source evidence and provenance

- Introduction memo: `360b629cdb30450904c00625bee97bfd5390b4fa:workflows/source-campaign-2026-10-07/source-memos/y256-fluid-depth-1.12.2-1.13.2-introduction-2026-10-09.md`, blob `3c40a634db190f60e1ced59e3e97eaa0db851b9b`, raw SHA-256 `7d4682f5dc560c528351107b9ae7151d14289b1c3c93c5e4c777d935596f6009`.
- Protection memo: `ec665da6da065d0099dec6ccb469ac523d4f771e:workflows/source-campaign-2026-10-07/source-memos/y256-fluid-depth-1.15-1.16-cutover-2026-10-09.md`, blob `1bb2892719213cb625506e0c3dbe38eb956c2bbf`, raw SHA-256 `536c9dca9dd2b750232d6f729439545e9d296cdb94a9fa7169a346ffb2a0aaf7`.
- Independent accepted source reviews: `30e3caac80bb776403f034ee8884666865c696d2`; introduction review blob `d179d852809c8e5bdf52c0e38605ff601cf50a60`, protection review blob `9c83e280416cf2ba8094493ebd8bd077c2c595ec`.
- Exact source roots are read-only under `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready`. The 1.13.0 Feather `LivingEntity.java` bytes have SHA-256 `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864`, matching row `net/minecraft/entity/living/LivingEntity.java` in `1.13/ornithe-feather.sources.sha256`; its source-manifest SHA-256 is `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`. The 1.15.2 Mojmap `LivingEntity.java` bytes have SHA-256 `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`, matching `1.15.2/mojmap.sources.sha256`; its source-manifest SHA-256 is `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`. The 1.16.0 producer/consumer source remains bound by the accepted protection review and its exact ready manifest.
- Preserve the accepted caveat: the 1.13.2 source files and source-manifest rows were verified, but the mapped client JAR hash does not match its artifact-manifest hash (`b28c33e0…` actual versus `d118ff5b…` manifest). No bytecode claim or Feather-to-original-JAR equivalence is made for 1.13.2. Runtime trajectory and the first protected release within the accepted 1.15.2-to-1.16.0 bracket remain unknown.

## Technical findings

**P1 — airborne condition changes the historical branch.** In 1.13.0 `LivingEntity.mobTick()` the jump branch first checks `this.jumping`; its water-depth condition is `!(depth > 0.0) || this.onGround && !(depth > 0.4)`. If depth is `> 0.4`, the condition is false regardless of `onGround`, and the `else` calls the water-liquid jump. The 1.15.2 `LivingEntity.aiStep()` has the same operation order and threshold. Therefore grounded, jump-input, retained-depth `> 0.4` selects the water jump. Candidate `WaterDepthJumpGate.shouldJump` adds `&& !player.onGround()` before the threshold and prevents it. The source review's airborne state is a sufficient example, not a necessary precondition. The old consumer also confirms the jump-input requirement: the whole selection is nested under `if (this.jumping)`.

**Producer timing and Y preflight match the accepted mechanism.** Candidate `EntityMixin` refreshes its player-only retained-depth cache after `updateInWaterStateAndDoFluidPushing()`, updating it while `floor(deflate(0.001).minY) < 256` and leaving it unchanged at or above 256. Its active gate repeats the historical `deflate(0.001)`, floor, and `>= 256` boundary. Accepted sources describe the failed loaded-area preflight returning before the historical depth-field store, while clearing current water state. This review accepts the timing and threshold as an implementation of the bounded stale-field mechanism; it does not claim an exact runtime trajectory.

**Impulse arithmetic matches the inspected operation.** The 1.13.0 `m_74407200(Tag<Fluid>)` body performs `velocityY += 0.04F`. The available 26.2 unobfuscated `LivingEntity.jumpInLiquid(TagKey<Fluid>)` body adds `0.04F` to vertical delta movement. The candidate invokes that narrow vanilla operation; no broader movement loop or altered floating-point formula was found. The 1.13.2 bytecode caveat above remains.

**Input, player scope, hook, and fallback.** The mixin passes its `jumping` state at the pre-fluid-jump decision after the player input path; candidate dispatch is restricted to `Player`, checks immobility and the existing fluid-jump eligibility hook, and uses the usual absent-hook fallback. The cached producer is player-only. The retained-depth gate excludes current water/lava, passengers, and non-build-height positions. No action was found in this review to widen those exclusions; the concrete mismatch is the added airborne test.

**Resolver, registration, and integrations.** `StaleWaterDepthJumpBehavior` is an independent hook. The single catalog registers the V1_12 no-op, active changes at V1_13/V1_14/V1_15/V1_15_2, and V1_16 protection; each implemented class is registered under that interface. Given `ChangeResolver`'s closest later change rule, the groups resolve as intended and `CURRENT` gets no historical change. The V1_15_2 catalog retains the separate sneak-edge and fluid-jump registrations. The candidate merge's catalog conflict retained the independent F007/F008/F012/F3 work visible in its main parent; no semantic overlap was found in those adjacent hooks. The candidate is not part of current `main` at the review base, and this report does not claim it is integrated.

## Verification and open limits

Read-only source, manifest, Git tree/blob, resolver, catalog, mixin, and adjacent-change inspection only. `git diff --check` passed for the documentation added by candidate tip. No code was edited, no decompilation or shared-source writes were performed, and no build, tests, runtime, client/TAS/Gym/server, Docker, or push was run. Compilation, target mixin application, and runtime parity remain unverified. No background process was started.

Journal: primary checkout began at clean `main` `dc5ee06f`; review worktree was created from that same commit and clean; evidence review is complete; the REQUEST CHANGES report is committed on `fix/y256-technical-binding-2026-10-09`; no process remains active.
