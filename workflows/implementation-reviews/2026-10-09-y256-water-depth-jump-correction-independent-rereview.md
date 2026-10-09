# Y=256 water-depth jump correction independent re-review

## Verdict

**ACCEPT — the correction removes the unsupported airborne restriction and preserves the source-supported jump-input gate.** The previously reported grounded case is now eligible when jump input is true and retained depth exceeds `0.4`; no remaining actionable issue was found in this bounded technical review. This accepts the code correction only. Build, target mixin application, runtime trajectory, the 1.13.2 bytecode identity and an exact protection cutover remain unverified or open as recorded below.

## Exact candidate and report bindings

| Item | Identity |
|---|---|
| Grounded-state correction | `f8d757828064c9186e7c1e353177c168cf5f9a67` (`fix: preserve grounded stale-depth water jump`) |
| Candidate handoff tip reviewed | `8f8e381b67ce435c82b805dc6318d4c13e552bdc`, tree `2c1e89414527e622508ccdf148d0f6f6baa3a6a7` |
| Corrected gate blob | `src/main/java/me/wolfii/legacyparkourcompat/change/common/WaterDepthJumpGate.java` blob `a61f5fb4510e5b2fa770a286fe8575226b76a24d` |
| Input-bearing hook blob | `src/main/java/me/wolfii/legacyparkourcompat/mechanic/hook/StaleWaterDepthJumpBehavior.java` blob `f0fe51747e31269e78e29d99ccf2dd642399c7f8` |
| Jump bridge blob | `src/main/java/me/wolfii/legacyparkourcompat/mixin/LivingEntityMixin.java` blob `08ae94aa9da37c91e5fca3f20e4a8efd443263b5` |
| Producer bridge blob | `src/main/java/me/wolfii/legacyparkourcompat/mixin/EntityMixin.java` blob `bc6d1f2a77e070fd7924891c4759436e02e9387f` |
| Catalog blob | `src/main/java/me/wolfii/legacyparkourcompat/change/MovementChangeCatalog.java` blob `a180a6df4b35fbd66de3a4b77ca25346add7bc68` |
| Prior independent REQUEST CHANGES report | commit `4494e91504811ff0e9deb0e41a14fab3b4873610`, report blob `36ff5e851379cf659b96e717e11b0d6d2c85102d`, raw SHA-256 `90e753c229052b11b0e9a10adbe32296954d242736d87649a47358eb4ed71492` |

The original request-changes binding remains unchanged. Its isolated branch has since merged current `main` at `e9cbd9d1d8a82c8aa46f26ab3481c3fd6d0f9df3` in merge commit `6ba1fbd860a7ed5eb278a71c36274e3bac43c787`; that incoming commit adds preparation, orchestration and pane-review documentation, with no Java diff. The review worktree is `D:\Javastuff\LegacyParkourCompat\.task-worktrees\y256-technical-binding-2026-10-09` on `fix/y256-technical-binding-2026-10-09`, rooted at the repository and clean before this report.

## Re-review findings

The correction deletes only `&& !player.onGround()` from `WaterDepthJumpGate.shouldJump`. The resulting conditions are jump input, retained depth `> 0.4`, not currently in water or lava, not a passenger, and `floor(deflate(0.001).minY) >= 256`. This matches the independently checked consumer ordering: exact 1.13.0 `LivingEntity.mobTick()` and 1.15.2 `LivingEntity.aiStep()` choose the water-liquid jump when `jumping` and depth exceeds `0.4`; `onGround` redirects only the zero/shallow-depth branch. The corrected gate therefore allows both grounded and airborne players, while false jump input remains suppressed.

Producer timing remains aligned with the accepted mechanism: the player-only cache updates after the modern fluid refresh below the `<256` preflight boundary and preserves the prior value when the contracted-box floor is at least 256. The same contraction, floor operation, boundary and strict `> 0.4` threshold are preserved. The candidate dispatches the narrow water-jump operation and restores the jump delay around the modern AI branch. No extra movement loop or altered jump impulse was found.

The one-operation hook remains independently registered: V1_12 no-op, V1_13/V1_14/V1_15/V1_15_2 active behavior, and V1_16 protection. `ChangeResolver`'s closest-later rule selects the intended registration for these groups; current profile has no historical change. Adjacent sneak-edge, position-packet and sprint-air changes from the merged main parent remain independent in the catalog/mixins. No semantic conflict was found.

## Source identities and limits

The accepted inputs remain the introduction memo at `360b629cdb30450904c00625bee97bfd5390b4fa` (blob `3c40a634db190f60e1ced59e3e97eaa0db851b9b`, raw SHA-256 `7d4682f5dc560c528351107b9ae7151d14289b1c3c93c5e4c777d935596f6009`) and protection memo at `ec665da6da065d0099dec6ccb469ac523d4f771e` (blob `1bb2892719213cb625506e0c3dbe38eb956c2bbf`, raw SHA-256 `536c9dca9dd2b750232d6f729439545e9d296cdb94a9fa7169a346ffb2a0aaf7`), independently accepted in `30e3caac80bb776403f034ee8884666865c696d2`. The 1.13.0 Feather `LivingEntity.java` source hash is `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` under source-manifest hash `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`. The 1.15.2 Mojmap `LivingEntity.java` source hash is `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` under source-manifest hash `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`.

The 1.13.2 Feather source files were verified, but its mapped client JAR identity still mismatches the artifact manifest (`b28c33e0…` actual versus `d118ff5b…` expected); no 1.13.2 bytecode or original-JAR equivalence claim is made. Source supports the bounded behavior through exact 1.15.2 and protection at exact 1.16.0; it does not identify the first protected release in between. MCPK's runtime jump trajectory is not validated.

Only read-only Git/code/source review and documentation static checks were used. No implementation files or shared sources were written; no build, tests, runtime, client/TAS/Gym/server, Docker, decompilation or push was run. No process remains active. The report is committed separately on the isolated technical-review branch.
