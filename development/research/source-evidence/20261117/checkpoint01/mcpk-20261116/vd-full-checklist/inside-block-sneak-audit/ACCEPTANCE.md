# Independent source acceptance: inside-block sneaking input slowdown

Verdict: **ACCEPT the core player-movement claim as source-supported.** In the compared stable endpoints the 1.14.4 sneak key directly triggers the horizontal input factor even when the sneak-sized pose does not fit. Starting in 1.15.0, the factor is reached through a local-player slow-movement predicate that first requires a crouching-sized player box to fit. Thus, when the crouching pose does not fit and visual crawling is false, holding sneak leaves horizontal control impulses unscaled. The player pose selection can also fall back rather than appear crouched. The changed quantity is player movement input, consumed by player travel. This is consistent with the MCPK clause and provides its source mechanism; it is not a runtime measurement of the described jump distance.

Boundary: the first stable release in this checked set with the fit-gated predicate is **1.15.0**, bounded by 1.14.4 and 1.15.0. It remains in 1.15.1, 1.15.2, 1.16.0, 1.16.1, 1.16.2 and 1.16.5. This does not establish a prerelease boundary or uninspected patch releases.

## Hash-bound source identities

All source roots are canonical `decompiled_minecraft/<version>/<mapping>/` trees, not copied lane exports. For every row, `ready.json` says `status=ready`; its `versionId` matches the canonical source folder (`1.15` is the stable release represented as 1.15.0 in the assignment), and its mapping matches the row. SHA-256 recomputed from `*.sources.sha256` matches `ready.json.sourceManifestSha256`. SHA-256 values below were recomputed from the source-manifest and Java bytes on disk. `1.15` is the exact ready-manifest version ID for the stable release commonly written as 1.15.0 in pair notation.

| Exact release | Mapping | Source-manifest SHA-256 |
|---|---|---|
| 1.14.4 | Mojmap | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` |
| 1.15.0 | Mojmap | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` |
| 1.15.1 | Mojmap | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` |
| 1.15.2 | Mojmap | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` |
| 1.16.0 | Mojmap | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` |
| 1.16.1 | Mojmap | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |
| 1.16.2 | Mojmap | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` |
| 1.16.5 | Mojmap | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` |

Key source-file SHA-256 fingerprints (repeated fingerprints share identical bytes; each relevant path was matched against that release's source manifest and independently hashed):

| Release(s) | `client/player/LocalPlayer.java` | Input file (`KeyboardInput.java`) | `world/entity/Entity.java` | `world/entity/player/Player.java` | Collision query file |
|---|---|---|---|---|---|
| 1.14.4 | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` | `33daa0833a95e09e728c1b8f509020dd13b70e38aba922e2b1e10ec5c9b7739a` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` | `LevelReader`: `1f86a029e4404b91aa532ca33ab0dbb63b085334c561ce44e0d0875aa50fdbc9` |
| 1.15.0, 1.15.1 | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` | `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` | `CollisionGetter`: `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd` |
| 1.15.2 | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` | same as 1.15.0 | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `1ba2724c22163862b8f7fdfdea5a04a6e4db26a119d7e5360ba024724a34793` | same as 1.15.0 |
| 1.16.0, 1.16.1 | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | same as 1.15.0 | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` | `CollisionGetter`: `fc7f175b10c2c6683913a903a366bd775facecf3c58f04cc6e9cfc95ddceed98` |
| 1.16.2, 1.16.5 | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | same as 1.15.0 | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | `CollisionGetter`: `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac` |

Entity-collision providers were also checked against manifests: 1.14.4 `EntityGetter.java` `dd7745c85c83bf327346008e815c64aa5d0e0ba253ef9640be48223824f0dfc6`; 1.15.0-.2 `EntityGetter.java` `f2c614b216f498069b0e4a033705afef96509599c42eb21a6b612387207fcd2c`; 1.16.5 `EntityGetter.java` `12b4ad28f0a92414ec1478dd42b6a41ff6cd0a0a00650ae37490ea8cd0a1bba5`. 1.16.0/1.16.1/1.16.2 collision files were separately validated as manifest members; their behavior and relevant declarations are captured below. No 26.2 comparison was needed for this bounded 1.14–1.16 verdict.

## Source trace and call order

- **1.14.4:** `client/player/LocalPlayer.java`, `isVisuallySneaking()` 593–596 and `aiStep()` 618–630, passes visual sneaking/crawling to `Input.tick`. `client/player/KeyboardInput.java`, `tick(boolean, boolean)` 13–25, forms directional impulses and refreshes `sneakKeyDown`, then multiplies both horizontal impulses by `(float)(impulse * 0.3)` if not spectator and (`sneakKeyDown || visualPredicate`). Thus a failed pose fit can hide the visual sneak posture without disabling the input factor while sneak is held. `Entity.canEnterPose` 1641–1643 tests `level.noCollision` on `getBoundingBoxForPose`; `LevelReader.noCollision/getCollisions` 110–131 combines block and entity collision shapes.
- **1.15.0/.1/.2:** `LocalPlayer.isCrouching()` 596–600 now requires `canEnterPose(Pose.CROUCHING)` before shift (or a standing-fit fallback) can make it true; `isMovingSlowly()` 602–604 adds visual crawling. `LocalPlayer.aiStep()` 625–636 calls `input.tick(this.isMovingSlowly())`. `KeyboardInput.tick(boolean)` 13–25 creates the raw horizontal impulses, reads the current shift key, and conditionally multiplies both impulses by `(float)(impulse * 0.3)`. The predicate is evaluated before `tick` refreshes `shiftKeyDown`; it observes the stored shift state at that point. With shift continuously held, failed crouch fit suppresses the factor. After this scaling, LocalPlayer's item-use branch applies `0.2F` to both impulses when applicable (637–640); `serverAiStep()` copies them into `xxa`/`zza` (607–613); `LivingEntity.aiStep()` passes those into `travel(new Vec3(...))` (2284–2288); grounded travel invokes `moveRelative` (1886–1891). `Entity.canEnterPose(Pose)` 1605–1607 tests collision on a pose-sized AABB. `CollisionGetter.noCollision/getCollisions` 55–65 combines block and entity shapes; `EntityGetter.getEntityCollisions` 53–66 provides overlapping entity collision boxes. `Player.updatePlayerPose()` 357–384 separately tries the desired pose, then crouching, then swimming. `LocalPlayer.isCrouching()` overrides inherited `Entity.isCrouching()` (1803–1805, which merely compares current pose); `canEnterPose` and `getBoundingBoxForPose` are inherited from `Entity`.
- **1.16.0/.1:** `LocalPlayer.aiStep()` 652–666 assigns its `crouching` field only if not flying/swimming, `canEnterPose(CROUCHING)` succeeds, and shift or the existing non-sleeping/standing-fit fallback holds; only then does it call `input.tick(isMovingSlowly())`. `isCrouching()` returns the field (625–627); the input factor is still the 0.3 multiplication in `KeyboardInput.tick(boolean)` 13–25.
- **1.16.2/.5:** same predicate and input order in `LocalPlayer.aiStep()` 627–642 and `isMovingSlowly()` 600–606; `KeyboardInput.tick(boolean)` 13–25 retains the factor. `Entity.canEnterPose(Pose)` 1640–1642 now tests `getBoundingBoxForPose(pose).deflate(1.0E-7)`; that epsilon changes the fit volume but does not remove the crouch-fit gate. `CollisionGetter.noCollision` 44–55 still checks block and entity collisions. `Player.updatePlayerPose()` 350–376 retains desired-pose → crouching → swimming fallback. `serverAiStep()` 609–615 and `LivingEntity.aiStep()` → `travel` continue consuming the resulting inputs.

## Boat qualifier and limits

The generic fit query can account for entity shapes as well as block shapes, so an overlapping boat collision could affect the crouching-fit result. The inspected player slowdown predicate has no boat-specific branch. The separate `LocalPlayer.rideTick()` boat path only forwards directional keys to `Boat.setInput` for a player whose `getVehicle()` is a boat (1.15: 825–832; 1.16.5: 837–844), which is vehicle control. These sources do not demonstrate that a player merely standing on top of a boat has a boat collision shape intersecting the candidate crouching AABB, nor that this overlap causes the wiki's reported state. Therefore the boat qualifier remains **unresolved in this bounded source check**, not contradicted. Do not infer boat movement from player movement-input evidence.

Exact source can establish the missing/present horizontal input factor and its route to `travel`; it does not quantify a jump distance or establish the described trajectory. Named block examples are not independently enumerated here: the accepted source rule is the generic pose-fit/collision predicate.

## Exact evidence locations

- 1.14.4: `client/player/LocalPlayer.java` 593–596, 618–630; `client/player/KeyboardInput.java` 13–25; `world/entity/Entity.java` 1641–1643, 2325–2330; `world/level/LevelReader.java` 110–131.
- 1.15.0/.1/.2: `client/player/LocalPlayer.java` 596–604, 607–613, 625–640, 825–832; `client/player/KeyboardInput.java` 13–25; `world/entity/Entity.java` 1605–1607, 1803–1805, 2320–2325; `world/entity/player/Player.java` 357–384; `world/level/CollisionGetter.java` 55–65; `world/level/EntityGetter.java` 53–66; `world/entity/LivingEntity.java` 1886–1891, 2284–2288.
- 1.16.0/.1: `client/player/LocalPlayer.java` 622–666; `client/player/KeyboardInput.java` 13–25; `world/entity/Entity.java` 1640–1642; `world/entity/player/Player.java` 350–376; collision query methods in `world/level/CollisionGetter.java` and `EntityGetter.java`.
- 1.16.2/.5: `client/player/LocalPlayer.java` 597–642; `client/player/KeyboardInput.java` 13–25; `world/entity/Entity.java` 1640–1642; `world/entity/player/Player.java` 350–376; collision query methods in `world/level/CollisionGetter.java` and `EntityGetter.java`.


