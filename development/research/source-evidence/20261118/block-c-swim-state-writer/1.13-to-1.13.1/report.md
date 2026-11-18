# 1.13 → 1.13.1 swimming-state writer trace

## Scope and result

Exact Feather comparison, limited to `PlayerEntity.updateSwimming`, its relevant inherited predicate, the update call order, and direct same-tick player reads of `isSwimming()`. Result: a concrete source-level reachable difference for an already-swimming, non-flying player. This is not a runtime playback claim.

## Exact source identity and integrity

Both exact source trees are `decompiled_minecraft/<version>/ornithe-feather` (Ornithe Feather). Ready manifests identify exact Minecraft IDs `1.13` / `1.13.1` and mapping coordinates `net.ornithemc:feather-gen2:1.13+build.2` / `net.ornithemc:feather-gen2:1.13.1+build.2`, respectively. Both ready records say `status: ready`, `sourceFileCount` 2702 / 2710, with matching source and artifact manifest, diagnostics, and provenance SHA-256 values. Provenance files match the ready-record digests. The ready/provenance records locate the preparation output under the recorded `build/movement-campaign-2026-10-07/ready` pair; the canonical read-only source trees examined here are the two `decompiled_minecraft` trees.

The ready-record source-manifest SHA-256 values are 1.13 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` and 1.13.1 `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`; artifact-manifest values are `a2c17075b601d9b83703f7ee188b04c6d9f68cac08d97b1a4f838fe8cd2e2c24` and `471177ba7bcff332e93ab5bf0127d5c689c3d34fe4f826976207ccfa3bf7b51c`. The inspected PlayerEntity, Entity, LivingEntity, and LocalClientPlayerEntity byte hashes matched their entries in each source manifest. The relevant source hashes are:

| Version | PlayerEntity.java | Entity.java | LivingEntity.java | LocalClientPlayerEntity.java |
|---|---|---|---|
| 1.13 | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` |
| 1.13.1 | `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |

## Writer and exact predicate change

- 1.13 `PlayerEntity.java:1464–1471`: create `blockPos = new BlockPos(this)` and `blockPos2 = blockPos.up()`. If `abilities.flying`, clear the stored swim flag. Otherwise call `super.updateSwimming()` only when `!isSwimming()` **or** `doesNotSuffocate(blockPos) && !world.getBlockState(blockPos2).getMaterial().blocksMovement()`.
- 1.13 `PlayerEntity.java:1474–1480`: `doesNotSuffocate(pos)` is `shouldSuffocate(pos) && !world.getBlockState(pos.up()).isSolid()`; `shouldSuffocate(pos)` is `!world.getBlockState(pos).isSolid()`.
- 1.13.1 `PlayerEntity.java:1471–1477`: flying still clears the stored swim flag. Every non-flying player now calls `super.updateSwimming()`; the existing-swimming block-clearance gate and its two `BlockPos` reads are gone.
- In both releases `Entity.updateSwimming()` is unchanged (`Entity.java:952–958` in 1.13; `953–959` in 1.13.1): if already swimming, store `isSprinting() && isInWater() && !isRiding()`; if not already swimming, store `isSprinting() && isSubmergedInWater() && !isRiding()`.
- In both releases `PlayerEntity.isSwimming()` is unchanged (`PlayerEntity.java:1814–1815` in 1.13; `1819–1820` in 1.13.1): it reports false while flying or spectator, otherwise delegates to the synchronized stored flag. The witness below is non-flying and non-spectating, so the reported value follows that flag.

The 1.13 gate uses `BlockState.isSolid()` at the player's current block and the block above it, plus `Material.blocksMovement()` for the above block. These are distinct vanilla predicates. It does not test collision-shape volume or player dimensions. On an already-swimming player, when this compound gate is false, 1.13 leaves the old swim flag untouched without reevaluating sprinting, water, or riding. In 1.13.1 the base method always reevaluates those conditions for a non-flying player.

## Tick ordering and movement read

In both versions `Entity.tick()` calls `baseTick()` (`Entity.java:332–338`). The common base-tick sequence invokes the helper which runs `checkWaterState()`, updates submerged-water state, then calls virtual `updateSwimming()` (`Entity.java:946–950` in 1.13; `947–951` in 1.13.1; invocation from `baseTick` at line 394). `LivingEntity.tick()` first calls `super.tick()` (`LivingEntity.java:1670+` in 1.13; `1689+` in 1.13.1), and its movement path later calls virtual `moveRelative(sidewaysSpeed, verticalSpeed, forwardSpeed)` (`LivingEntity.java:1907` / `1926`). PlayerEntity overrides `moveRelative` and its initial swim branch reads `isSwimming()` before ordinary relative acceleration (`PlayerEntity.java:1435–1460` / `1442–1467`). Thus the updateSwimming flag result is available to this same tick's voluntary movement path.

That branch, unchanged in both releases, applies a swim vertical adjustment when `isSwimming() && !isRiding()`: with `g = getLookVector().y`, choose `h = 0.085` when `g < -0.2`, else `0.06`; if `g <= 0`, `jumping`, or the block at `(x, y + 1.0 - 0.1, z)` has nonempty fluid, set `velocityY += (g - velocityY) * h`. The block/fluid test is treated here only as an input to the consumer.

## Ordinary local witness (source-predicted)

A non-flying, non-spectating local player is already swimming, remains in water, is not riding, has stopped sprinting by the time the base-tick update runs, while remaining in water in a cramped space where 1.13's `doesNotSuffocate(blockPos) && !material.blocksMovement()` evaluates false (for example, the block at the floored player position is solid, or the block above has movement-blocking material). At a base-tick update where the sprint flag is already false:

- 1.13 skips its inherited update and retains `swimming=true` despite sprint no longer being held.
- 1.13.1 calls the inherited already-swimming branch, which stores false because `isSprinting()` is false.

When the subsequent same-tick voluntary move reads the state, hold other movement inputs and water/fluid inputs equal and choose a downward look (`g <= 0`): 1.13 applies the `velocityY` swim adjustment, while 1.13.1 does not enter the swim branch. This is a concrete predicted movement-state consequence of the writer difference. The same state read also feeds the player's 0.4F swim eye-height branch (`PlayerEntity.java:1854–1864` / `1859–1869`); this report does not claim separate collision-box parity effects.

Coverage is intentionally bounded to this operation and its direct tick/movement dependency. No implementation, gameplay launch, simulation, or runtime witness was performed.


