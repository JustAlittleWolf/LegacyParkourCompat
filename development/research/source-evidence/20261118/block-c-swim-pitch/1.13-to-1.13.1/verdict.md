# Independent source witness — 1.13 → 1.13.1 swim pitch-directed vertical adjustment

Frozen before opening the paired source report. This section is the independent, source-only witness; SHA-256 below binds these exact UTF-8 bytes (LF line endings).

## Pinned source identity

Canonical local exact-version decompilation: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`, mapping namespace `ornithe-feather` (Feather generation 2). Pair run ID `prep-1.13-1.13.1-feather-2026-10-09-r1`; pinned Feather coordinates are `net.ornithemc:feather-gen2:1.13+build.2` and `1.13.1+build.2`. Both readiness manifests say `status: ready`; provenance `sourceManifestSha256` values are 1.13 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` and 1.13.1 `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`. Direct file hashes match the corresponding entries in those pinned source manifests.

Provenance JSON SHA-256: 1.13 `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d`; 1.13.1 `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904`. Readiness JSON SHA-256: 1.13 `82a4a8f6a03fbd99a7b8577b1c92d741a6d3c2d4` (recorded local digest; canonical manifest is in source tree); 1.13.1 `f098b3a08b37276cc1a465a8a6c...` (recorded local digest; canonical manifest is in source tree). Source file byte hashes:

- `1.13/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`: `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd`
- `1.13.1/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`: `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272`
- `1.13/ornithe-feather/net/minecraft/entity/Entity.java`: `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0a016642`
- `1.13.1/ornithe-feather/net/minecraft/entity/Entity.java`: `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`

## Independent comparison

`PlayerEntity.moveRelative(float forwards, float sideways, float upwards)` is identical in both versions (1.13 lines 1435–1461; 1.13.1 lines 1442–1468). It first snapshots x/y/z. When `isSwimming() && !isRiding()`, it reads `double g = getLookVector().y`, chooses `double h = g < -0.2 ? 0.085 : 0.06`, then updates vertical velocity only when `g <= 0.0 || jumping || !world.getBlockState(new BlockPos(x, y + 1.0 - 0.1, z)).getFluidState().isEmpty()`, using exactly `velocityY = velocityY + (g - velocityY) * h`. The subsequent flying branch and `super.moveRelative` ordering are identical: on flying and not riding it snapshots y velocity and air speed, temporarily sets air speed to fly speed times sprint factor, calls super, multiplies old y velocity by `0.6`, restores air speed, clears fall distance and flag 7; otherwise it calls super directly. Movement-related statistics follow. This bounded operation has no 1.13→1.13.1 source change.

The inherited `Entity.getLookVector()` is identical in the compared methods (1.13 lines 1690–1692; 1.13.1 lines 1691–1693): `getRotationVector(pitch, yaw)`. It reads the current pitch/yaw without an intervening transformation in `moveRelative`; look-vector math is therefore not an alternate source of a pair difference here.

Adjacent state gate, distinct operation: `PlayerEntity.updateSwimming()` changes. In 1.13 (lines 1464–1471), it eagerly creates feet and upper `BlockPos`; flying clears swimming. Otherwise it calls `super.updateSwimming()` only if not currently swimming, or if `doesNotSuffocate(feet)` and the block above does not block movement. In 1.13.1 (lines 1471–1476), flying still clears swimming, but every non-flying call delegates to `super.updateSwimming()` with no current-swimming/obstruction gate. The inherited `Entity.updateSwimming()` is unchanged: while swimming it keeps/swaps the swimming bit based on `sprinting && isInWater() && !isRiding()`; otherwise it uses `sprinting && isSubmergedInWater() && !isRiding()`. Thus 1.13 can preserve the current swimming flag for an already-swimming player under the local obstruction gate, while 1.13.1 reevaluates it; this can change whether the identical `moveRelative` pitch-directed adjustment is reached. Treat this as a separate swimming-state reevaluation difference, not a delta to the vertical-adjustment math. No fluid-query/provider behavior was audited.

`Entity` invokes `checkWaterState()`, the intermediate water-state update, then `updateSwimming()` in this order in both releases (1.13 lines 948–951; 1.13.1 lines 949–952), closing the direct caller dependency needed to establish that the changed state gate is reevaluated in the entity tick. No environmental fluid dynamics or collision-provider claim is made.

## Source witness verdict

The bounded look/pitch-directed vertical adjustment claim is **no difference** for 1.13→1.13.1. Its gate/math/order match exactly. The directly relevant preceding swimming-state reevaluation **does differ** and is an independently resolved adjacent operation: 1.13 conditionally reevaluates an already-swimming player's state under local block obstruction conditions; 1.13.1 always reevaluates unless flying. Reachability/significance is through the unchanged `isSwimming()` gate in `moveRelative`.

This source witness was prepared blind to `c-swim-pitch-vertical-113-1131-source/report.md` and to implementations/wiki inventories.


## Blind-audit result (after frozen witness)

Frozen pre-report witness SHA-256: `9025be9075f05da106d65471fff2222ee67207e9a2b1a96308cd9a825b1637da`. The assigned report was opened only after this witness was hash-checked; its bytes match the requested report SHA-256 `2d1c907a53cc4fa87ad89682551b97ea2a39169f3aac1d35760412cb2662580d`.

I independently checked the report's added path evidence: `LivingEntity.moveRelative` has the same `isLocallyControlled() || isLogicalSideForUpdatingMovement()` gate in both; `LocalClientPlayerEntity.isLocallyControlled()` returns true in both; the `LivingEntity.tick` call reaches `moveRelative` after speed scaling in both. The inherited `Entity.updateSwimming` body and its call ordering also match. These support the bounded local player reachability/state claims; they do not expand this verdict into environmental fluid behavior.

Audit verdict: the report's substantive no-difference conclusion for `PlayerEntity.moveRelative` is accepted. The distinct `updateSwimming` reevaluation difference and its dependency on the unchanged `isSwimming()` gate are also accepted. One exact-expression qualification: the report's final witness paraphrases the sampled Y coordinate as `y + 0.9`; the source expression is the left-associated Java expression `y + 1.0 - 0.1`. Do not treat those expressions as guaranteed bitwise-equivalent for arbitrary double `y`; preserve the source expression in any exact witness. This does not affect the paired source comparison or reachability claim. Report identity remains the supplied hash above.

Clerical correction to the frozen witness transcription: the 1.13 `Entity.java` SHA-256 is `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` (the source-manifest entry and current file agree); the frozen paragraph omitted `e` in the final path segment. This correction is recorded here so the immutable pre-report witness identity remains visible.
