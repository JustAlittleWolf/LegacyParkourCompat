# Historical movement implementation review — corrected 2026-10-08

Scope: static review of four implementation patches against their exact accepted findings and vanilla sources. No implementation files were changed; no build, tests, or runtime were run. The coordinator handoff was read from commit `1f5fe40ec77f2003f7245cac6da91a996d88bd69`; that file is absent from `feat/movement-completeness-final`, but exists at the handoff commit. The TICK-01 blind review is a separate record from its source finding snapshot.

## TICK-01 sprint timeout — ACCEPT

Patch `3c531180287b3cecff49e557083db73352b258f7` matches the bounded finding. Source snapshot: commit `1dbafbb7899c57ddf1718e421aa2c32e283fc7c7`, `workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/TICK-01-sprint-timeout.md`, SHA-256 `f7fb9e8aaf8685c65a2af1ce23c43f9d52e44393280c4047a5fd4d6cfc0788d5`. Blind decision: commit `304b6a58384a9a724d3ab9ad3f3bab5650bacdf5`, `workflows/coverage-review-2026-10-08/TICK-01.md`, accepts the timer delta only; it is not the source snapshot.

The patch moves `SprintTickBehavior` to `LocalPlayer.aiStep` HEAD, before local AI and movement. That matches 1.8.9 `LocalClientPlayerEntity.mobTick`, which decrements the positive 600-tick timer first and stops sprinting at zero. The V1_8 state/tick instance seeds, decrements and clears the timer; later profiles do not resolve this V1_8 change. Exact source pair hashes: A `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`; B `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`. The canonical 26.2 `LocalPlayer.java` source hash is `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; the handoff records the HEAD ordering and exact target method review.

Limits: first changed release is unknown within `(1.8.9, 1.9.4]`; no runtime trajectory or other sprint gates are covered.

## Boat passenger yaw refresh — REQUEST CHANGES

Patch `8722bc476159b79f7e249f48b0e1076f209ced12` (handoff tip `48003fe`) against accepted source snapshot `064fc24e5e3d8b4dab4f12c2000dcb13d37514ca`, `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-006-boat-passenger-yaw-refresh.md`, SHA-256 `3994115e3789a8280aa61228b2b092a007e57985cde72492f19731a8f150db95`. Blind decision `4ff925e4e4186adb704d57d9b3471c559945436c` accepts the incremental finding, limited to movement-facing yaw on the specified refresh.

`ChangeResolver` admits changes with `emulates >= selected` and chooses the closest eligible version. The V1_17_1 callback’s exact-version guard makes V1_17 a no-op; V1_18_2 does not resolve the V1_17_1 change. The patch’s `yRot` write matches the accepted movement scope: current 26.2 `Entity.moveRelative` builds its input vector from `getYRot()`. The accepted evidence excludes rendering. Although 1.17.1 also writes `yRotO` and `yHeadRot` on direct boat mount, no movement consumer for those fields was established here; requiring those writes would broaden the finding without movement evidence.

One mismatch remains: the patch captures and rechecks `Boat.hasIndirectPassenger(player)`. In 1.17.1, `LocalPlayer.startRiding(Entity, boolean)` writes yaw when the player successfully starts riding an entity that is itself a `Boat`. For a player nested on another passenger, rebuilding the boat’s list remounts that other entity; it does not call the local player’s direct `startRiding(Boat, ...)`. The patch therefore changes nested passengers’ movement yaw in a state where the accepted A source does not. Restrict this emulation to the direct local-player-on-Boat case, or revise the accepted finding with exact source evidence for a nested transition.

Finding source hashes: LocalPlayer A/B `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` / `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095`; packet listener A/B `a59ba067bb0b9cdf026159a87534fd456ed766ec287cfd23e24356f84f078a5a` / `e718016022c2ae86a2c354af2d34fe4de7d6e36dc8792d2e2c1f08abb6b77b7b`.

## 1.13 pose resize fit — REQUEST CHANGES

Patch `e6b61bd7f1334b7e4fa49a9bd0a5929e833338a8` (in `build/codex-worktrees/movement-posefit`) against accepted source snapshot `448934e826fb41266dc79a313ef1187899d76382`, `workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F002-pose-selection-and-collision-aware-resize.md`, SHA-256 `baa5c6b30167eaa8024d34b39186440be944115061c8f39fdf5e17555b8ec67e`. Blind decision `10aa24e1114200397ed2d042a33f269113faf179` accepts the bounded source finding; world collision outcomes remain conditional.

The `Player.updatePlayerPose()V` HEAD hook matches the current shared player pose method. V1_13 registration and the V1_14 no-op provide the intended profile boundary: V1_13 uses the 1.13.2 behavior; V1_14 and later use native pose selection. The candidate box anchor and desired-pose precedence follow the cited 1.13.2 body. A/B `PlayerEntity.java` hashes: `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` / `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df`.

The fit-change gate is not tick-equivalent. A compares requested float dimensions against the entity’s float `width` and `height` fields (`f != this.width || g != this.height`). The patch compares float dimensions against `AABB.getXsize()/getYsize()` double extents. Those extents can round independently, changing whether the collision query and resize path runs. Compare using the entity’s dimensions with the historical float semantics. No specific tight-space outcome is claimed.

## Elytra jump start — REQUEST CHANGES

Patch `bedf96068bde90d295b6446aa4501b91ebf8f319` plus comparator correction `a5d07341d941ce2c0b9df1e392af9a40b1fbb9e9` on `fix/elytra-start-nan-gate` against accepted source snapshot `4a0c35f2008d785867c00360a7b72726e3506435`, `workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-ELYTRA-START.md`, SHA-256 `b6d5091e95e7cc0d35eb696dea6d06f720aaad91540ad88915ddc61dad576e59`. Blind decision `960ce5644ceacd8e27bd3cb9145bd4eaf221379c` accepts the bounded player-movement finding and historical emulation eligibility. The corrected `!(y < 0.0)` exactly preserves A’s strict descent comparison, including NaN behavior.

The hook binding is valid for the actual target. Canonical 26.2 `LocalPlayer.java` hash is `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; the ready artifact manifest identifies `26.2/client.jar` as `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`. Its `LocalPlayer.aiStep()V` calls `Player.tryToStartFallFlying()Z` under `jump && !justToggledCreativeFlight && !wasJumping && !onClimbable`. The wrapped helper matches that descriptor. In 26.2, the helper additionally requires `!isFallFlying()`, `canGlide()`, and `!isInWater()`, then sets shared flag 7. Player `canGlide()` rejects ability flight; LivingEntity `canGlide()` requires airborne, not a passenger, no Levitation, and a gliding-capable equipped item. The patch clears the shared flag after a successful helper call, preserving A’s no-local-flag result on this path.

The wrapped call cannot recover cases rejected before or inside the 26.2 helper. Compared with A’s accepted 1.14.4 direct gate, 26.2 adds the climbable and creative-flight-toggle exclusions at the caller and passenger, water, and Levitation exclusions through the helper chain. For example, A’s `!abilities.flying` gate permits a descending jump on the tick creative flight is toggled off; 26.2 blocks every toggle tick. Either dispatch where the full old eligibility is available or narrow the implementation contract to the overlapping eligible states. This review corrects the earlier report’s use of the 1.15.2 caller as if it were the current target caller.

Current helper source hashes: `Player.java` `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; `LivingEntity.java` `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; `Entity.java` `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dfaff869bb811d5`.

Accepted finding source hashes: A/B `LocalPlayer.java` `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` / `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`; B `Player.java` `1ba2724c22163862b8f7fdfdea5a04a6e4db26a119d7e5360ba024724a34793`; B `LivingEntity.java` `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`.

Limits: first changed release is unknown within `(1.14.4, 1.15.2]`; server acceptance and glide trajectory are outside the finding and this review.

## Review limits

The decisions assess source correspondence and static patch structure. They do not establish runtime parity, complete movement coverage for any version pair, or a first changed release inside an endpoint interval.
