# Pitch-directed vertical swimming adjustment: 1.13 → 1.13.1

## Bound result

The `PlayerEntity.moveRelative` pitch-directed vertical-velocity adjustment has identical source behavior in 1.13 and 1.13.1. Its arithmetic, casts, and call order do not change. The surrounding `PlayerEntity.updateSwimming` pose-flag gate does change: for a non-flying player, 1.13 may skip the inherited swimming-state reevaluation while already swimming; 1.13.1 always invokes it. Thus a changed input flag can affect whether the unchanged adjustment is eligible on a later travel call. This report covers that flag dependency only; block/fluid predicates below are recorded as inputs, with no claim about their physical behavior.

## Exact source identity

Both trees report `ready`, mapping `ornithe-feather`, and the same preparation run `prep-1.13-1.13.1-feather-2026-10-09-r1`. Ready-marker source-manifest digests match the actual manifest files; ready-marker provenance digests match the provenance files; each selected Java file's SHA-256 matches its source-manifest entry.

- 1.13: Feather `net.ornithemc:feather-gen2:1.13+build.2`; client SHA-256 `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e`; source-manifest SHA-256 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`.
- 1.13.1: Feather `net.ornithemc:feather-gen2:1.13.1+build.2`; client SHA-256 `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87`; source-manifest SHA-256 `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`.

Primary sources are `decompiled_minecraft/<version>/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `LivingEntity.java`, `Entity.java`, and `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`.

| Release | `PlayerEntity.java` SHA-256 | Relevant methods |
| --- | --- | --- |
| 1.13 | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` | `moveRelative` 1435–1461; `updateSwimming` 1464–1473 |
| 1.13.1 | `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` | `moveRelative` 1442–1468; `updateSwimming` 1471–1478 |

Dependency source hashes verified against their manifests: 1.13 `LivingEntity.java` `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864`, `Entity.java` `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642`; 1.13.1 `LivingEntity.java` `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`, `Entity.java` `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.

## Adjustment, controls, and call order

In both `PlayerEntity.moveRelative` versions, before invoking `super.moveRelative`:

1. The extra adjustment is gated by `isSwimming() && !isRiding()`.
2. It reads `double g = this.getLookVector().y` and selects `double h = g < -0.2 ? 0.085 : 0.06`.
3. It updates `velocityY` exactly when `g <= 0.0 || this.jumping || !world.getBlockState(new BlockPos(x, y + 1.0 - 0.1, z)).getFluidState().isEmpty()`; the assignment is `velocityY = velocityY + (g - velocityY) * h`.

There are no explicit casts in this adjustment. Its constants and intermediate values are doubles, including the `y + 1.0 - 0.1` position expression. For a positive look-vector Y, either jumping or the sampled block's nonempty-fluid predicate admits this update; for nonpositive look-vector Y the inner predicate short-circuits true. The movement arguments do not control this adjustment; `jumping`, look-vector Y and the listed state predicates do.

`LivingEntity.tick` calls `moveRelative(sidewaysSpeed, verticalSpeed, forwardSpeed)` after scaling the horizontal speeds. `LocalClientPlayerEntity.isLocallyControlled()` returns true in both releases, so the inherited `LivingEntity.moveRelative` local-control gate admits the ordinary local-player path. `PlayerEntity` then delegates to that inherited method (the non-flying, non-riding branch for the witness below), which applies movement input and reaches `move(MoverType.SELF, velocityX, velocityY, velocityZ)` after the pitch correction. Therefore the correction precedes the inherited actual movement call in this witness.

The exact ordinary local witness is: a local player, not flying and not riding, whose swim flag is true; the look vector has positive Y; and either `jumping` is true or the sampled block at `(x, y + 0.9, z)` has nonempty fluid state. The conditional correction then runs before inherited movement. This is a source-reachable witness, not a trajectory or collision claim.

## Adjacent pose-state difference

`Entity` calls `updateSwimming()` after its water-state update sequence in both releases. In `PlayerEntity.updateSwimming`, both releases clear swimming if abilities are flying. For non-flying players:

- 1.13 invokes `super.updateSwimming()` when `!isSwimming()` or when `doesNotSuffocate(blockPos) && !world.getBlockState(blockPos.up()).getMaterial().blocksMovement()`.
- 1.13.1 unconditionally invokes `super.updateSwimming()`.

The inherited `Entity.updateSwimming` uses current swimming state to choose its sprint/water predicate. Hence, when already swimming and the 1.13 added conjunction is false, 1.13 leaves the swim flag untouched for this update; 1.13.1 reevaluates and may change it. This is the only observed difference in the bounded pose-to-adjustment dependency. The predicate meanings are intentionally not analyzed here.

## Coverage boundary

Compared the exact `PlayerEntity.moveRelative` and `updateSwimming` methods, the inherited `LivingEntity.moveRelative` travel call and move site, the `Entity` updateSwimming caller/base implementation, and the local player's `isLocallyControlled` witness. The pitch-directed adjustment itself has no 1.13→1.13.1 delta. This does not claim full movement-pair coverage, close other fluid or collision dependencies, or prove runtime parity.