# 1.9.3 → 1.9.4: local Elytra glide versus climb cleanup

## Bounded result

**No source difference in the assigned operation.** For an ordinary dry, on-foot local player with a usable chest Elytra and the fall-flying flag active, the adjacent pair resolves the same `LivingEntity.moveRelative` path. In each version, the glide branch takes precedence over the non-glide branch containing ladder/vine cleanup. The strict `velocityY > -0.5` test writes `fallDistance = 1.0F` immediately before the glide math and `move(...)`. The ensuing `Entity.move` collision/fall-damage step is the first immediate writer that can adjust it: a landing resets it to `0.0F`; otherwise negative resolved `dy` adds `-dy`. The glide branch itself does not run the climb clamp or its `fallDistance = 0.0F` cleanup.

Scope is only this active glide/climb gate and its immediate fall-distance boundary, with default abilities (no creative flight), no water/lava, no riding, and a fixed local-client profile. This is not full-pair coverage.

## Exact local source identities

Both trees are Feather/Ornithe Feather Gen2 build 2, not Yarn. The canonical source directories are `decompiled_minecraft/1.9.3/ornithe-feather/` and `decompiled_minecraft/1.9.4/ornithe-feather/`.

- **1.9.3 input identity:** raw client `b6985b0d3a0520dfb6f17eeb1e8ba58ce9577061`, SHA-256 `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b`; Feather metadata SHA-256 `353c13519171cbba1a96872aebcb8b38248d5d5963b4c4613c23337adf028985`; Feather mapping `feather-gen2-1.9.3+build.2-mergedv2.jar`, SHA-256 `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`; exact release metadata `1.9.3.json`, SHA-256 `2ec3b7abc12f0ad4d4e3c777dc0c429fa948ffd4d1bdbb89a81aecbd30021be5`. The tree has the hidden `ornithe-feather/.decompile-provenance.json` (actual file SHA-256 `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`); it records those inputs, the pinned decompiler/toolchain, and per-source hashes. This confirms local provenance despite the absent outer ready-marker/source-manifest sidecars.
- **1.9.4 input identity:** raw client SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`; Feather mapping artifacts `feather-gen2-1.9.4+build.2-mergedv2.jar` and `.tiny`, recorded in `ornithe-feather.provenance.json` and `artifacts.sha256` (artifact SHA-256s `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125` and `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`). Its ready marker records the exact version id and a source-manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. The local verification sidecar says `verified-identical-source-and-input-artifacts`: all 1,819 published sources verified, with 0 source differences. Its one artifact difference is the regenerated intermediate remapped JAR, not the published source tree; the sidecar records the explanation and retained original hash. The local provenance and ready-marker file SHA-256s are respectively `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd` and `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6`.

Actual source SHA-256s, checked against the 1.9.3 provenance `sources` map and the 1.9.4 `.sources.sha256` manifest:

| Relative source | 1.9.3 SHA-256 | 1.9.4 SHA-256 | Result |
|---|---|---|---|
| `net/minecraft/entity/living/LivingEntity.java` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | byte-identical |
| `net/minecraft/entity/Entity.java` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | byte-identical |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` | byte-identical |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `ef96c8a00443f724eadad05a6a4dcc8a8a2e23cd4c3390b9e0f1f78c9ff4d1af` | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` | file differs outside this checked override; override text checked equal |

## Paired source evidence and gate

- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `tick()` lines 143–156 calls `super.tick()` for the loaded local player; `isLocallyControlled()` lines 446–449 returns `true`. Its `mobTick()` lines 604–615 and 755–759 handles local input then calls `super.mobTick()`.
- `net/minecraft/entity/living/LivingEntity.java`, `tick()` lines 1489–1552 calls `mobTick()`. `mobTick()` lines 1645–1712 reaches travel, calls `flyingTick()` and then `moveRelative(sidewaysSpeed, forwardSpeed)`. At lines 1302–1306, the updater guard is `isLocallyControlled() || isLogicalSideForUpdatingMovement()`, followed by the water/lava exceptions and then `if (isFallFlying())`. `isFallFlying()` lines 2052–2054 reads flag 7. `flyingTick()` lines 1718–1736 validates flag 7, airborne/on-foot state and a usable CHEST Elytra; only the non-client writes the flag. Thus the local client simulates from its synchronized active flag; it is not the server simulating historical physics.
- **1.9.3:** same `LivingEntity.java` lines 1302–1355: the glide arm first checks strict `velocityY > -0.5` and writes float `1.0F` at 1307–1309; it calls `move(...)` at 1341. The `else` non-glide arm starts at 1355; climb cleanup at 1376–1389 clamps horizontal velocity, zeroes fall distance, caps downward Y, and applies the sneaking-player stop. It moves at 1391; horizontal collision then sets climb Y to `0.2` at 1392–1394.
- **1.9.4:** same `LivingEntity.java` lines and text, confirmed by exact equal file SHA-256 above.
- **1.9.3 and 1.9.4:** same `Entity.java` lines 441 onward for movement and 795–805 for `checkFallDamage`; after resolving collision/ground state, `move` calls `checkFallDamage(dy, onGround, ...)` at line 645. `checkFallDamage` resets on landing at 801 or, if not landed and resolved `dy < 0.0`, adds `-dy` via `(float)(fallDistance - dy)` at 802–804. Exact file SHA-256 is equal across the pair.
- **Player override:** `PlayerEntity.moveRelative(float,float)` lines 1372–1387 has the same paired source text: default abilities skip the creative-flight branch and delegate to `super.moveRelative`. The full PlayerEntity source hash differs, so only this operation's override was compared here.

## Conclusion boundary

This exact adjacent comparison establishes no 1.9.3→1.9.4 change for the assigned local dry glide-versus-climb gate. It does not establish the introduction boundary before 1.9.3, later patch behavior, runtime parity, or complete 1.9.3/1.9.4 movement coverage.
