# 1.9.4 → 1.10.2: ordinary client player tick and travel boundary

## Evidence identity

Read-only canonical sources: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather\`. Both release-parent directories contain their `ornithe-feather.ready.json`, `ornithe-feather.provenance.json`, `ornithe-feather.sources.sha256`, and `artifacts.sha256` files. The ready markers declare exact `versionId` values `1.9.4` and `1.10.2`, mapping `ornithe-feather`, Vineflower, Java 25, and these client inputs:

| Release | version metadata id / client SHA-256 | Mapping build declaration | Ready source manifest SHA-256 |
|---|---|---|---|
| 1.9.4 | `1.9.4` / `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908` | `feather-gen2-1.9.4+build.2-mergedv2.jar`, `feather-gen2-1.9.4+build.2.tiny` | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` |
| 1.10.2 | `1.10.2` / `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9` | `feather-gen2-1.10.2+build.2-mergedv2.jar`, `feather-gen2-1.10.2+build.2.tiny` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` |

`artifacts.sha256` binds the 1.9.4 mapping JAR/Tiny map to `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125` / `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027` and the 1.10.2 files to `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0` / `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1`. The provenance files declare those mapping artifact names and exact-version runs. These are the Feather Gen2 mappings for both sides.

Selected Java source hashes below are the entries in the matching release's source manifest; source hashes were used to prune before body review.

| Source root | 1.9.4 SHA-256 | 1.10.2 SHA-256 |
|---|---|---|
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `68f6e640808d8b31c1a99d1470075606a42290600fd45435aa86c15eb8db5ef0` | `d9f0ec60a318ac51fd49198aaccd5c3e2b3af95cd3e2df577bb25226f3e0e4e4` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` |
| `net/minecraft/client/entity/living/player/Input.java` | `206dea2ff5977599596c907c54f36300c4e72d07d6778d82e8850f57bb0fb009` | `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` |
| `net/minecraft/client/entity/living/player/KeyboardInput.java` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` | same |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` | `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` |
| `net/minecraft/entity/living/LivingEntity.java` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| `net/minecraft/entity/Entity.java` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |

## Tick graph and reviewed slices

Coverage is the direct local-player control path: `LivingEntity.mobTick` selects local AI, calls `LocalClientPlayerEntity.serverTickAi` (copies player input into movement/jump state), then local `mobTick` updates sprint/jump input and calls `super.mobTick`; the base method applies jump before travel and invokes `moveRelative`; `PlayerEntity.moveRelative` supplies flight overrides; `LivingEntity.moveRelative` applies ground/air/fluid acceleration and velocity; position changes call `Entity.move`. `PlayerEntity.tick`/`updatePlayerPose`, player jump, input class, and relevant `Entity` dependencies were checked by paired source. `KeyboardInput.java` has the same manifest hash on both sides. `ClientPlayerEntity.java` changes only the `WorldSettings.GameMode` to `GameMode` symbol in `isSpectator`/`isCreative`; the compared mode predicates are otherwise identical.

### Finding: local auto-jump queues a jump after a detected step

1.9.4, Ornithe Feather Gen2: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick` lines 645–755; no local-player `move` override or auto-jump check exists in the class.

1.10.2, Ornithe Feather Gen2: same file, `mobTick` lines 661–778 and new `move`/`autoJump` methods lines 808–920; `net/minecraft/client/entity/living/player/Input.java`, new `getMovement` lines 15–17.

Difference: 1.10.2 stores `options.autoJump`, intercepts local movement after `super.move`, and checks the resulting position while enabled, grounded, not sneaking/riding, and with nonzero input. It predicts a forward sweep from displacement (falling back to yaw-rotated input for near-zero displacement), considers jump-boost height, and uses block collision shapes plus world collision boxes to detect an obstacle with a rise above `0.5F` and at most `1.2F`. On a match it sets `ticksToNextAutojump = 1`. At the next local `mobTick`, the timer sets `input.jumping = true`; the added `bl4` gate prevents that synthetic press from toggling flight. The 1.9.4 path has no equivalent state or detection.

Movement meaning: with auto-jump enabled (initialized true and refreshed from client options), ordinary grounded forward motion into an eligible step can produce a jump one tick after the move that found it. This changes jump/ground-air state and subsequent player travel. The lower-level collision traversal is not audited here.

### Disposition: swim/lava gravity predicates and movement multiplier

1.9.4, `net/minecraft/entity/living/LivingEntity.java`, `moveRelative` lines 1302–1456. 1.10.2, same file, lines 1332–1493. The air/land branch retains its `0.8F` behavior; 1.10.2 routes the water multiplier through `getBaseMovementSpeedMultiplier()` (new method at lines 1328–1330, returning `0.8F`; only the polar bear overrides it in this source set). The 1.10.2 liquid travel branches also skip their gravity subtraction when `isNoGravity()` is true.

The latter predicate is new entity state in 1.10.2 `Entity.java` (`NO_GRAVITY`, `isNoGravity`, `setNoGravity`, and NBT persistence); 1.9.4 has no general entity-level predicate. For an ordinary local player with the default false state, water/lava vertical response is unchanged. The non-default no-gravity path depends on a 1.10.2-only entity feature and is not accepted as a 1.9.4 player difference under the modern-only-feature exclusion. Player jump and `PlayerEntity.moveRelative` bodies otherwise preserve their movement math/order; player pose changes only rearrange equivalent initial assignments.

## Dependencies and boundaries

- Auto-jump calls `BlockState.getCollisionShape`, `World.getCollisions`, and bounding-box segment/intersection helpers. Their shape providers, DDA/world collision internals and registration are assigned to the collision pool and were not audited here.
- Its calculations also call `Input.getMovement`, `PlayerEntity.getSpeed`, `Entity.getRotation3d`, `MathHelper.fastInvSqrt`, `Vec3d` vector operations, jump-boost effect state and the local option. The input helper and movement speed/jump roots were checked; unchanged math dependencies are not independent findings.
- `Entity.move` itself contains changed collision-response code between these versions. It is also a collision-pool responsibility and remains an open dependency for this lane; no claim is made about its body.
- This is a bounded source review, not the pair's complete inventory or independent coverage audit. Intermediate patch boundaries are unknown within 1.9.4–1.10.2. No pair-freeze, source acceptance, implementation or runtime-parity claim is made.
