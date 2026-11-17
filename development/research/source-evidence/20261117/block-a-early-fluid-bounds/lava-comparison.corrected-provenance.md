# Early lava-presence scan bounds

## Finding

`Entity.isInLava()` queries `World.containsMaterial(getShape().grown(-0.1F, -0.4F, -0.1F), Material.LAVA)`. In 1.8, the material scan computes each exclusive upper loop bound as `MathHelper.floor(bounds.max* + 1.0)`; in 1.9 it computes `MathHelper.ceil(bounds.max*)`. Lower bounds remain `MathHelper.floor(bounds.min*)`, all loops use `< upper`, and the scan returns true when the scanned block state's material equals the requested `Material.LAVA`.

For non-integer `max*`, these upper-bound formulas give the same index at ordinary world coordinates. At an exact integer `max* = n`, the old formula gives `n + 1` and scans coordinate `n`, while the new formula gives `n` and excludes it. This is a real integer-boundary difference in ordinary entity lava detection.

## Reachable player witness

Use an ordinary 0.6F-wide player and set its center to the exact representable double

`x = 1.0 - (double) 0.3F + (double) 0.1F = 0.7999999895691872` (`0x3FE9999994000000`), `y = 0.0`, `z = 0.5`.

`Entity.setPosition` forms the horizontal half-width from `width / 2.0F`; for this player, that is `0.30000001192092896`. `Box.grown` applies the widened float `-0.1F` (`-0.10000000149011612`) to the maximum. The grown scan bounds are `minX = 0.5999999791383743` and `maxX = 1.0` exactly. Put a lava-material block at `(1, 0, 0)` and keep the player's shrunken box clear of water. In 1.8, x begins at `floor(0.5999999791383743) = 0` and ends at `floor(1.0 + 1.0) = 2` (exclusive), so x=1 is visited. In 1.9, x ends at `ceil(1.0) = 1` (exclusive), so only x=0 is visited.

With `jumping = true`, `onGround = true`, and `jumpingCooldown = 0`, the 1.8 query selects `jumpInLava()` (`velocityY += 0.04F`) and does not take the ground-jump branch. The 1.9 query misses the boundary lava block and selects `jump()` plus cooldown 10. This isolates a native player jump predicate consequence; it does not depend on predicting later trajectory. The candidate center is a representable ordinary player position and the scan itself is reached by the normal `isInLava()` path.

## Local-player scope and adjacent control gates

The local-client player overrides `isLocallyControlled()` to return true in both 1.8 (`LocalClientPlayerEntity.java:389-391`) and 1.9 (`:443-445`). `LivingEntity.moveRelative` changes its outer gate from only `isLocallyControlled()` in 1.8 (`:1115-1118`) to `isLocallyControlled() || isLogicalSideForUpdatingMovement()` in 1.9 (`:1289-1292`). The added term does not alter the local-client player's truth value; its `isInLava()` condition remains reachable. The jump selection itself is in the ordinary `LivingEntity.tick()` flow on both sides (`1.8:1427-1439`, `1.9:1678-1690`).

## Version sweep and patch boundary

All inspected trees use the local `ornithe-feather` family. The scan is old-form in 1.8 and in the available 1.8.9 tree; it is new-form in 1.9, 1.9.1, 1.9.2, 1.9.3, 1.9.4, and 1.10.2. All eight requested source trees are release-attributed. The 1.8, 1.9, 1.9.1, 1.9.2, and 1.9.3 mapping-root manifests authenticate their local source files. The 1.8.9, 1.9.4, and 1.10.2 trees are authenticated through version-root `ornithe-feather.ready.json`, `.provenance.json`, `.sources.sha256`, and `.verification.json` sidecars. Each ready marker identifies its exact version, `ornithe-feather` mapping and Feather Gen2 build.2 artifacts; the ready marker's source-manifest digest matches both the actual version-root source manifest and the verification record. The actual hashes of the seven cited movement source files in each tree match that version's source manifest. Verification reports 1,612, 1,819, and 1,845 verified files respectively, with zero source differences and zero missing sources.

The version sweep therefore authenticates the old scan form through 1.8.9 and the new form beginning at 1.9; within the inspected sequence, the patch onset is between 1.8.9 and 1.9. The version-root verification records one artifact difference per version: the derived remapped client intermediary jar was regenerated during verification. It records 36 published artifact files verified and describes the remapped-jar difference as an intermediate output, while retaining the original hash in the artifact manifest; this does not change the source-file or raw-input verification. No version beyond the assigned list was inspected.

| Release | Local tree scan | Provenance/source identity | `World.java` SHA-256 |
|---|---|---|---|
| 1.8 | `floor(max + 1.0)` | manifest present; Feather Gen2 1.8 build.2; cited-source hashes match | `afbc701ef8b66020bdbb2831fd80683705d20237c68fbf23a4001c020876fc6d` |
| 1.8.9 | `floor(max + 1.0)` | version-root sidecars verified; Feather Gen2 build.2; 1,612 sources exact | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` |
| 1.9 | `ceil(max)` | manifest present; Feather Gen2 1.9 build.2; cited-source hashes match | `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` |
| 1.9.1 | `ceil(max)` | manifest present; Feather Gen2 1.9.1 build.2; cited-source hashes match | `cdda70c97f0e4d6f6c454c8f6d3c0d47f0e0ae612e75062d8fe93ddd3963e6b0` |
| 1.9.2 | `ceil(max)` | manifest present; Feather Gen2 1.9.2 build.2; cited-source hashes match | `cdda70c97f0e4d6f6c454c8f6d3c0d47f0e0ae612e75062d8fe93ddd3963e6b0` |
| 1.9.3 | `ceil(max)` | manifest present; Feather Gen2 1.9.3 build.2; cited-source hashes match | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` |
| 1.9.4 | `ceil(max)` | version-root sidecars verified; Feather Gen2 build.2; 1,819 sources exact | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` |
| 1.10.2 | `ceil(max)` | version-root sidecars verified; Feather Gen2 build.2; 1,845 sources exact | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` |

For 1.8, 1.9, 1.9.1, 1.9.2, and 1.9.3, the actual SHA-256 values for `World.java`, `Entity.java`, `LivingEntity.java`, `PlayerEntity.java`, `LocalClientPlayerEntity.java`, `Box.java`, and `MathHelper.java` match their mapping-root `.decompile-provenance.json` source entries. Manifest SHA-256 values: 1.8 `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05`; 1.9 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`; 1.9.1 `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`; 1.9.2 `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`; 1.9.3 `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`.

For 1.8.9, 1.9.4, and 1.10.2, those same seven actual source-file hashes match the corresponding version-root `ornithe-feather.sources.sha256` entries. The source manifest SHA-256 agrees with both `ready.json.sourceManifestSha256` and `verification.json.sourceManifestSha256FromReadyMarker`; the artifact manifest SHA-256 agrees with `ready.json.artifactManifestSha256`.

| Version | Feather mapping artifacts | `ready.json` SHA-256 | `.provenance.json` SHA-256 | `.sources.sha256` SHA-256 | `.verification.json` SHA-256 | `artifacts.sha256` SHA-256 |
|---|---|---|---|---|---|---|
| 1.8.9 | `feather-gen2-1.8.9+build.2-mergedv2.jar`, `feather-gen2-1.8.9+build.2.tiny` | `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95` | `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `a4206eee9ffb45a6f54f93371c8275b7a6c37cf9ab1ca93d5fa0f5d889881620` | `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` |
| 1.9.4 | `feather-gen2-1.9.4+build.2-mergedv2.jar`, `feather-gen2-1.9.4+build.2.tiny` | `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6` | `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd` | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | `9ef993b06552037873604f912cce078845241b6e6f3b9799a83795abd1adf688` | `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77` |
| 1.10.2 | `feather-gen2-1.10.2+build.2-mergedv2.jar`, `feather-gen2-1.10.2+build.2.tiny` | `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d` | `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | `f79b28657bb1e93c03710414d6172165d5fdb6680c71ee9b202734816fe3230f` | `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116` |

## Exact source ranges

All paths are relative to each version's `ornithe-feather` source root.

| Release | Source evidence |
|---|---|
| 1.8 | `net/minecraft/world/World.java:1504-1523`; `net/minecraft/entity/Entity.java:839-841`; `net/minecraft/entity/living/LivingEntity.java:1427-1439`, `:1111-1118`; `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:389-391`; `net/minecraft/entity/living/player/PlayerEntity.java:411` |
| 1.8.9 | `net/minecraft/world/World.java:1529-1549`; `net/minecraft/entity/Entity.java:840-842`; `net/minecraft/entity/living/LivingEntity.java:1431-1441` |
| 1.9 | `net/minecraft/world/World.java:1598-1620`; `net/minecraft/entity/Entity.java:939-941`; `net/minecraft/entity/living/LivingEntity.java:1678-1690`, `:1285-1292`; `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:443-445`; `net/minecraft/entity/Entity.java:2237-2240` |
| 1.9.1 | `net/minecraft/world/World.java:1598-1620`; `net/minecraft/entity/Entity.java:939-941`; `net/minecraft/entity/living/LivingEntity.java:1683-1695` |
| 1.9.2 | `net/minecraft/world/World.java:1598-1620`; `net/minecraft/entity/Entity.java:939-941`; `net/minecraft/entity/living/LivingEntity.java:1683-1695` |
| 1.9.3 | `net/minecraft/world/World.java:1604-1626`; `net/minecraft/entity/Entity.java:943-945`; `net/minecraft/entity/living/LivingEntity.java:1692-1703` |
| 1.9.4 | `net/minecraft/world/World.java:1604-1626`; `net/minecraft/entity/Entity.java:943-945`; `net/minecraft/entity/living/LivingEntity.java:1692-1703` |
| 1.10.2 | `net/minecraft/world/World.java:1614-1636`; `net/minecraft/entity/Entity.java:956-958`; `net/minecraft/entity/living/LivingEntity.java:1728-1739` |

Numeric support for the witness is in 1.8 `Entity.java:225-232` (position/box construction), `Entity.java:839-841` (shrunken lava query), `PlayerEntity.java:411` (0.6F width), `Box.java:59-67` (grown arithmetic), and `MathHelper.java:36-38,63-65` (floor/ceil integer rules). The newer loop and material check are in 1.9 `World.java:1598-1620`; the same `MathHelper` floor/ceil implementation remains at `:40-42,67-69`.