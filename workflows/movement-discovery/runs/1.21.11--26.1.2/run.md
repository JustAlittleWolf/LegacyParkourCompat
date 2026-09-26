# Discovery: 1.21.11 to 26.1.2

- Status: partial
- Scope: client player movement; older A = 1.21.11; newer B = 26.1.2
- Repository revision at start: `c1b613fd1c5b3301cb67bf0ccf86f98614b51fd9`; date: 2026-09-26
- Selected naming namespace: Mojang official names. A uses the release's official Mojmap; B is published unobfuscated and already uses official names. The workflow's revised alignment rule expressly permits this pairing.
- Source preparation commands:
  - A (source reused from the preceding discovery run): `gradlew.bat decompileMinecraft --versions=1.21.11,26.1.2 --mappings=mojmap`; this overall invocation failed later when 26.1.2 had no Mojmap artifact, but first logged `Finished 1.21.11 using mojmap`. The raw log was not retained; a concise transcript extract is at `build/movement-discovery-1.21.11-mojmap-log-extract.txt` and is labeled as a transcription, not the raw log.
  - B: `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=26.1.2 --mappings=unobfuscated`; raw successful log: `build/movement-discovery-26.1.2-unobfuscated.log`.
- Toolchain: Gradle 9.7.1; JDK 25.0.3+9-LTS decompiler JVM; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; default 4G heap and repository decompiler options. B completed with `BUILD SUCCESSFUL`; the log contains one processed-twice notice for `CarvingMask`, unrelated to movement, and no decompiler error summary.

The old blocked mapping conclusion in the parent version of this file is superseded by the revised official-name alignment rule and this successful retry. This run is partial because only the slices listed below have been inspected. It makes no claim that movement is equivalent across the releases.

## Artifact manifest

Hashes are SHA-256 unless labeled publisher SHA-1. Cached jars and sources are ignored local artifacts; they are not included in this commit.

### A — 1.21.11

- Exact requested/resolved release: `1.21.11` / `1.21.11` (exact resolution and successful `Finished 1.21.11 using mojmap` were confirmed in the preceding decompiler task output).
- Client jar: `build/minecraft-decompile-cache/1.21.11/client.jar`; SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`; Mojang publisher SHA-1 `ba2df812c2d12e0219c489c4cd9a5e1f0760f5bd`; version JSON SHA-256 `13e195800429ad001c3d897dd646638b2bc9a9fc5ce01d840d440eb0f2ea5351`.
- CLI mode / naming namespace: `mojmap` / Mojang official names.
- Official mapping artifact: Mojang `client.txt`, release-specific URL recorded in version JSON; publisher SHA-1 `031a68bebf55d824f66d6573d8c752f0e1bf232a`; cached file `build/minecraft-decompile-cache/1.21.11/client_mappings.txt`, SHA-256 `517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd`.
- Remapped jar: `build/minecraft-decompile-cache/1.21.11/client-mojmap.jar`; SHA-256 `853daa46088f8f5c6924d08a4019394c1647d847015fe399c791872aaac5f26f`.
- Source root: `decompiled_minecraft/1.21.11/mojmap/`; it existed from the previous successful run. The client, mapping, remapped-jar, and version-JSON hashes match the preceding run manifest, establishing provenance. It was not regenerated during this retry.

### B — 26.1.2

- Exact requested/resolved release: `26.1.2` / `26.1.2` (`Decompiling Minecraft 26.1.2 (requested '26.1.2')` in the retained successful log).
- Client jar: `build/minecraft-decompile-cache/26.1.2/client.jar`; SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`; Mojang publisher SHA-1 `4e618f09a0c649dde3fdf829df443ce0b8831e65`; version JSON SHA-256 `2e7b23dcfb78ab3921ea663347be48508cbb286756d2b46027b47008a006c85c`.
- CLI mode / naming namespace: `unobfuscated` / Mojang official names. The exact version JSON has no `downloads.client_mappings`; the decompiler accepted the original jar in `unobfuscated` mode, and the success log confirms the selected output.
- Mapping file and remapped jar: not applicable; published unobfuscated client jar was decompiled directly.
- Source root: `decompiled_minecraft/26.1.2/unobfuscated/` (`Finished 26.1.2 using unobfuscated`). Before the run, this target directory did not exist.

## Correspondence and call order

The class chain is A `LocalPlayer.java:105 -> AbstractClientPlayer.java:26 -> Player.java:124 -> Avatar.java:12 -> LivingEntity.java:145`; B `LocalPlayer.java:108 -> AbstractClientPlayer.java:19 -> Player.java:125 -> Avatar.java:13 -> LivingEntity.java:142`. `LivingEntity` extends `Entity` on both sides. The inheritance/member descriptors were inspected directly. The same names corroborate structure but do not prove behavioral identity; compared method bodies and call paths below were opened independently.

- Input: `KeyboardInput.tick() -> ClientInput` fields `keyPresses` / `moveVector -> LocalPlayer.aiStep()`. The ordered key reads, boolean-to-impulse mapping, `Vec2(left, forward).normalized()` and forward-impulse threshold match in the inspected code. `ClientInput.makeJump()` sets only jump while preserving the other six input fields on both sides.
- Local-player tick path: `LocalPlayer.aiStep() -> AbstractClientPlayer.aiStep() -> Player.aiStep() -> LivingEntity.aiStep() -> this.travel(input)` when local movement simulation is active. Call anchors: A `AbstractClientPlayer.java:96`, `Player.java:452`, `LivingEntity.java:2877,2974`; B `AbstractClientPlayer.java:76`, `Player.java:442`, `LivingEntity.java:2976,3073`. `AbstractClientPlayer.aiStep()` updates bob then delegates; `Player.aiStep()` updates jump trigger, regeneration and inventory, handles flying fall reset, then delegates; `LivingEntity.aiStep()` conditionally dispatches travel. Input sampling, crouch state, sprint trigger, creative flight toggle, fall-flying request, water descent, vertical flight input, ride-jump accumulation and final superclass call were compared in `LocalPlayer.aiStep()`.
- Travel dispatch: `Player.travel(input)` handles passenger, swimming pitch impulse, and flight-Y preservation before delegating to `LivingEntity.travel(input)`; the compared branch order and expressions match. `LivingEntity.travel` dispatches fluid, fall-flying, or air movement. The client player inherits this Player/LivingEntity route.
- Jump impulse: `LivingEntity.jumpFromGround()` reads `getJumpPower()`, applies `Math.max(jumpPower, movement.y)`, then the sprint impulse from yaw using `0.2`; the same expression and `needsSync` write occur in both versions. `getJumpPower()` inputs/registrations remain outside the inspected closure.
- Edge probing: `Entity.move()` calls virtual `maybeBackOffFromEdge`; Player overrides it. The Player override and its `isAboveGround` / `canFallAtLeast` helpers were checked on both sides. The conditions, 0.05 step, axis order, 1.0E-7 inset, AABB construction and returned Y component match. `LocalPlayer` inherits this Player behavior.

Class-source hashes for the additional call-chain sources: A `AbstractClientPlayer.java` SHA-256 `afe84ab1491a17aeeccf895ca4ecb17e941b8e517c980e31281b3d463fc593bd`; `Avatar.java` `a3f54b9ff81ee203f77efcf5dcab50d69ec9a3d2c5430ce4c4b2ae0abb4bfca9`. B `AbstractClientPlayer.java` `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc`; `Avatar.java` `01a8092bc2637e7d42a261fb564bffcabadd28625cc02b120e943559e2d4fe78`.

## Coverage ledger

- Slice 1.1 / stage 1 / keyboard sampling and diagonal normalization: `compared-no-difference`. A: `decompiled_minecraft/1.21.11/mojmap/net/minecraft/client/player/KeyboardInput.java:10-37` and `ClientInput.java:10-32`; B: corresponding files under `decompiled_minecraft/26.1.2/unobfuscated/`, same lines. SHA-256: A KeyboardInput `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`; B `b4bbb410650444c30d2a62d70fd3c6cd1aefa104b8a12e474e2a478a62089c39`; ClientInput both `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`. Conclusion: no change in these input transformations; controller-specific input not inspected.
- Slice 1.2 / stage 1 / local tick input-to-sprint, crouch, jump-toggle and ride-jump ordering: `compared-no-difference` for the inspected `LocalPlayer.aiStep()` body. A: `LocalPlayer.java:728-880`, SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`; B: `LocalPlayer.java:767-919`, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666eb`. Conclusion: call order, guards, constants and state writes inspected match; this does not disposition all LocalPlayer tick/input paths.
- Slice 3.1 / stage 3 / Player travel wrapper: `compared-no-difference`. A `Player.java:1360-1383`; B `Player.java:1381-1404`. Passenger dispatch, swimming vertical impulse (`-0.2`, `0.085`, `0.06`), fluid test position, creative-flight Y preservation and `0.6` multiplier match. The class source hashes are A `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`; B `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`.
- Slice 3.2 / stage 3 / ground jump power and impulse application: `compared-no-difference` for `LivingEntity.getJumpPower(float)`, `getJumpBoostPower()` and `jumpFromGround()`. A `LivingEntity.java:2260-2281`; B `LivingEntity.java:2343-2364`. The attribute-times-multiplier-times-block-factor plus jump-boost sum, amplifier formula, threshold, max operation, sprint-angle math, `0.2` impulse, and synchronization write match. Dynamic attribute/effect values, application/removal and block-factor registrations remain open.
- Slice 3.3 / stage 3 / travel branch and direct air, fluid and glide integration: `compared-no-difference` for the inspected `Player.travel`, `LivingEntity.travel`, `travelFlying`, `travelInAir`, `travelInFluid`, `travelInWater`, `travelInLava`, `jumpOutOfFluid`, `travelFallFlying`, `updateFallFlyingMovement`, `handleFallFlyingCollisions`, and `handleRelativeFrictionAndCalculateMovement` bodies. A: `Player.java:1360-1383`, `LivingEntity.java:2309-2497`, `2534-2544`; B: `Player.java:1381-1404`, `LivingEntity.java:2392-2586`, `2623-2633`. The order, guards, casts, constants, math expressions and state writes inspected match (including fluid slowdowns, gravity adjustments, glide math, and collision handoff). Dependency values such as attributes/effects, gravity, block friction, fluid state and collision results are not established by this slice.
- Slice 4.1 / stage 4 / player edge-backoff loop: `compared-no-difference` for `Player.isStayingOnGroundSurface`, `maybeBackOffFromEdge`, `isAboveGround`, and `canFallAtLeast`. A: `Player.java:309`, `889-952`; B: `Player.java:299`, `880-950`. Virtual call evidence: A `Entity.java:711`, B `Entity.java:730`; the LocalPlayer inheritance chain reaches Player's override. The checked guard, shift predicate, step size, axis reduction order and support AABB expressions match. Full `Entity.move`, collision candidate selection, block callbacks and supporting-block updates remain unreviewed.
- Slices 1.3-1.6 / stage 1 remaining input providers, tick initialization, auto-jump producers, correction handling and flight/riding transitions: `pending`.
- Slices 2.1-2.6 / stage 2 pose/dimensions, swimming transitions, abilities, hunger, item use, sprint eligibility, jump-power providers and movement-state defaults: `pending`.
- Slices 3.4-3.9 / stage 3 movement attributes and aggregation, gravity source, effects, water/lava defaults, climb, levitation/slow-falling, glide equipment and post-travel updates: `pending`.
- Slices 4.2-4.7 / stage 4 full entity collision/step-up, axis ordering, support, velocity restitution, fluid contact, callbacks and packet updates: `pending`.
- Slices 5.1-5.6 / stage 5 friction/speed/jump block values, shapes, fluid flow, registries and resources/tags: `pending`.
- Slices 6.1-6.6 / stage 6 movement effects/enchantments/equipment, attributes, tags, components and server-synchronized inputs: `pending`.
- Slices 7.1-7.5 / stage 7 knockback/corrections, explosions, pistons, launch items, riding transitions and external movement inputs: `pending`.

## Dependency queue and unresolved candidates

- `DEP-PLAYER-INPUT`: keyboard entry is checked; alternate input sources and all state writers before `LocalPlayer.aiStep` remain open. Continue stage 1.
- `DEP-JUMP-ATTR`: equal `jumpFromGround` application does not establish equal `getJumpPower`, jump boost effect/attribute aggregation, or equipment/data inputs. Continue stage 2/6.
- `DEP-TRAVEL-INPUTS`: direct travel algorithms match for equal inputs; `getFrictionInfluencedSpeed`, `getFluidFallingAdjustedMovement`, block friction, movement attributes, gravity, effects, fluid state/tags, collision shapes/results, and application timing remain open. Continue stages 3-6.
- `DEP-COLLISION`: edge probing methods match, but `Entity.move` and dependent collision/support helpers have not been fully compared. Continue stage 4.
- `DEP-DATA`: source saver omits resources; version-matched client jar resource entries and server-synchronized inputs have not been inventoried. Continue stages 5-7.
- Candidate observed while browsing `Player.causeFallDamage`: A `Player.java:1409-1437` contains current-impulse fall-damage context handling; B `Player.java:1430-1440` delegates directly to `super.causeFallDamage`. This is a source-visible difference in fall-damage processing, but its effect on movement state or reachable client movement has not been traced. Leave it unresolved and inspect the impulse producers/consumers before classifying or discarding it.

## Finding index

No independently confirmed movement delta was found in the five bounded slices above; therefore there are no finding files yet. This is not a claim of overall equivalence.

## Resume checkpoint

- Last completed: source provenance and mapping alignment; stage 1 keyboard and LocalPlayer input/sprint/jump slice; stage 3 direct jump/travel integration; stage 4 player edge-backoff loop.
- Next: resolve `DEP-PLAYER-INPUT`, `DEP-JUMP-ATTR`, and `DEP-TRAVEL-INPUTS`; then inspect full Entity movement/collision in stage 4 before stages 5-7.
- Current limits: input providers, player state defaults, data-driven effects/attributes/blocks/resources, collision closures and external inputs are uninspected. `Player.causeFallDamage` remains a candidate outside the confirmed movement findings.

## Source audit closure

- Coverage: 6 bounded slices compared with no difference; all remaining navigation slices pending; 0 behavioral findings; 0 full stages closed.
- Status: partial; the remaining scope and dependencies are listed above.
- Evidence/hash/correspondence audit: exact releases, client hashes, A mapping hash/remapped jar provenance, B native-unobfuscated mode, successful B raw log and source roots recorded. Hashes cover every source file cited in the bounded comparisons. A's prior successful log was not retained as raw output; the available transcript extract is explicitly identified. Method correspondence is based on inspected signatures, call chains and bodies, not name equality alone.
- Runtime validation: not performed (separate workflow).
