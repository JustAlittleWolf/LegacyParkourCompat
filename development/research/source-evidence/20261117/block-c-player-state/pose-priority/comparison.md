# Desired-pose priority and client ownership

## Verdict

In 1.21.1, .2, .3 and .4, the desired player-pose chain tests fall-flying, then sleeping, then swimming. In 1.21.5 it tests sleeping, then swimming, then fall-flying. The ordering between sleeping and swimming is unchanged; both now precede fall-flying. Thus an externally stipulated overlap yields sleeping over swimming over fall-flying in 1.21.5, while 1.21.1–.4 yield fall-flying over sleeping over swimming.

Desired selection is separate from fit resolution. In all five versions, `updatePlayerPose` first gates all work on fitting the swimming pose; if that fails it leaves the current pose untouched. Otherwise it chooses a desired pose, then selects that pose for spectators/passengers or when it fits; if it does not fit, it tries crouching and finally swimming. A difference in desired priority matters only after the outer swimming-fit gate passes and the competing predicates overlap; requested-pose fit fallback may still replace the desired result.

## Exact modern sources

All five files are Mojmap `Player.java`. The 1.21.1/.3/.4/.5 provenance sidecars identify exact versions and a shared decompile run for 1.21.1, 1.21.3, 1.21.4, 1.21.5. There is no 1.21.2 parent provenance/ready sidecar in the checkout; its source-tree `.decompile-provenance.json` identifies the 1.21.2 client artifact by official object hash and records the per-source hash. I matched that embedded source hash against the file. This is an evidence packaging gap, not a source-body gap.

| Version | Source range | SHA-256 of full source file |
|---|---|---|
| 1.21.1 | `decompiled_minecraft/1.21.1/mojmap/net/minecraft/world/entity/player/Player.java`, 401–433 | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| 1.21.2 | `decompiled_minecraft/1.21.2/mojmap/net/minecraft/world/entity/player/Player.java`, 431–463 | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| 1.21.3 | `decompiled_minecraft/1.21.3/mojmap/net/minecraft/world/entity/player/Player.java`, 431–463 | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| 1.21.4 | `decompiled_minecraft/1.21.4/mojmap/net/minecraft/world/entity/player/Player.java`, 434–466 | `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1` |
| 1.21.5 | `decompiled_minecraft/1.21.5/mojmap/net/minecraft/world/entity/player/Player.java`, 449–481 | `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036` |

The first four method bodies have the same predicate order and fit-resolution logic. 1.21.5 extracts desired selection into private `getDesiredPose()` at 465–477; its ordered checks are sleeping (466–467), swimming (468–469), fall-flying (470–471), then spin attack and crouch/standing. `updatePlayerPose()` at 449–463 retains the same outer gate and fit resolution.

## Historical enum / selection path

The exact Feather source in each of 1.13, 1.13.1 and 1.13.2 has `PlayerEntity.updatePlayerPose()` with the same order: fall-flying, sleeping, swimming-or-spin, sneaking, standing. It has no `Pose.java` or `EntityPose.java`; this path selects float width/height values directly and only calls `setSize` when the candidate bounding box has no collisions. Ranges: 1.13 lines 333–360; 1.13.1 and 1.13.2 lines 334–361. Full `PlayerEntity.java` SHA-256 values respectively: `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd`, `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272`, `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`. Provenance identifies Feather Gen2 `1.13+build.2`, `1.13.1+build.2`, `1.13.2+build.2`.

In 1.14, both independent mappings contain the pose enum and the selection chain. Feather Gen2 `1.14+build.2`: `PlayerEntity.updatePlayerPose`, lines 324–352; SHA-256 `f73879bd42103fa45f39cfe178ac82c7574c6306f00555af7f7e795976275dd7`; `Pose.java` includes `FALL_FLYING`, `SLEEPING`, `SWIMMING` (enum source SHA-256 `88996dc2f6fba703ab9c5be3dacf6c53bac2485f9bc5409b888e810370a8a618`). Yarn `1.14+build.21`: `PlayerEntity.updateSize`, lines 326–354; SHA-256 `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319`; `EntityPose.java` is present (SHA-256 `3b669e6d9f951310a1f92d84f419ef2dec1769aae9dbaedfc198176f38042217`). Despite mapping-name differences, both enumerate fall-flying before sleeping before swimming and have the same separation of compact-pose fit gate, desired selection and fallback selection. Feather's tracked source identifiers in the gate/set calls are left in their original decompilation; I did not substitute a guessed Yarn name.

The source interval establishes that the enum/desired-selection path is present by 1.14 and absent in the inspected 1.13.x releases. It does not establish the exact introduction commit/version within that interval.

## Local-player path and overlap limits

In 1.21.1 and 1.21.5, `LocalPlayer.tick()` calls `super.tick()` (1.21.1 line 193; 1.21.5 line 195); `Player.tick()` then calls `updatePlayerPose()` (1.21.1 line 319; 1.21.5 line 344). The priority method is inherited shared player code, not a separate local-player override. The 1.13.2 and 1.14 Feather local-player tick methods likewise call `super.tick()` (1.13.2 line 183; 1.14 line 180), and `PlayerEntity.tick()` calls `updatePlayerPose()` (1.13.2 line 254; 1.14 line 262).

The modern predicates are independently read by the selection chain. `LivingEntity.isSleeping()` is backed by sleeping-position state; `LivingEntity.isFallFlying()` reads shared flag 7; `Entity.isSwimming()` reads the swimming flag. The vanilla local player sends a `START_FALL_FLYING` command (1.21.1 `LocalPlayer.java` line 744; 1.21.5 line 771); the flying flag is authoritative/synchronized state, not set by a local pose-selection method. Swimming is updated by entity water/movement state logic. Sleeping is established through the sleeping path. I found no local-player setter that jointly asserts these states, and the pose method itself contains no mutual-exclusion guard among them. Therefore the reordered branch outcomes are proven for simultaneous predicate truth supplied as state, but this source-only inspection does not prove that ordinary vanilla trajectories reach each overlap. No trajectory or runtime experiment was performed.

Scope excluded bed-entry eligibility and collision-shape details. No implementation, tests, runtime, or game launch was performed.