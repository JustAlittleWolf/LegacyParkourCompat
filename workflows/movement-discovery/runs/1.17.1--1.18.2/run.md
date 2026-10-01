# Discovery: 1.17.1 to 1.18.2

- Status: partial
- Scope: client player movement; older A = exact 1.17.1; newer B = exact 1.18.2
- Repository revision and start date: `c133c29`; 2026-09-26
- Naming: aligned official Mojang names. Both sides explicitly generated with `mojmap`, using their own exact version's Mojang mappings.
- A source command (from owner-provided run context): `decompileMinecraft --versions=1.17.1 --mappings=mojmap`; successful generation and artifacts verified by coordinator; no A raw Gradle log copied into this worktree.
- B source command: `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.18.2 --mappings=mojmap`; output confirmed exact requested/resolved 1.18.2, official mapping applied, finished with mojmap, build successful. Live tool log is not persisted.
- Toolchain: Gradle 9.7.1; Java 25.0.3+9-LTS decompiler JVM; B requires Java 17 bytecode. Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; Gson 2.14.0; ASM 9.10.1. Vineflower warning severity; several lambda “processed twice” warnings noted at B generation, to disposition if relevant.
- Shared source output is accessed through a verified worktree junction to `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. No source tree is tracked.

## Artifact manifest

Paths in this manifest are relative to the respective `decompiled_minecraft/<version>/mojmap/` source root. SHA-1 values are publisher identities; SHA-256 values were computed locally.

### A: Minecraft 1.17.1

- Exact version JSON ID: `1.17.1`; source owner cache: its isolated `build/minecraft-decompile-cache/1.17.1/`.
- Original client URL: `https://piston-data.mojang.com/v1/objects/8d9b65467c7913fcf6f5b2e729d44a1e00fde150/client.jar`; publisher SHA-1 `8d9b65467c7913fcf6f5b2e729d44a1e00fde150`; local SHA-256 `A49B4A56C5BBE15C9ED9FE53EFA9A591F265A1F5BA7D6AA9739A58EA7A92B79D`.
- Official mappings URL: `https://piston-data.mojang.com/v1/objects/e4d540e0cba05a6097e885dffdf363e621f87d3f/client.txt`; publisher SHA-1 `e4d540e0cba05a6097e885dffdf363e621f87d3f`; local SHA-256 `2B28DED68F8602AAF2F35EF92DD10EE41BA2CF9723E29570155D60E1723848E9`.
- Remapped jar local SHA-256 `7277878475794E10CC0169A3E6F431C039186F366B9A5FF0741331EE50E0847C`.
- Shared source root `decompiled_minecraft/1.17.1/mojmap/`; verified B-side access to A `LocalPlayer.java` SHA-256 `C9A91CB6CB57806BC8D22E5BFE2D97DAAF21D5D2A48F34A6E2C53164C61C5812`.
- Never regenerate A; its owner resolved a prior remapped-jar lock and supplied the verified artifacts/source.

### B: Minecraft 1.18.2

- Exact resolved version `1.18.2`; version JSON release time `2022-02-28T11:42:45+00:00`; client bytecode Java 17.
- Original client URL: `https://piston-data.mojang.com/v1/objects/2e9a3e3107cca00d6bc9c97bf7d149cae163ef21/client.jar`; publisher SHA-1 `2e9a3e3107cca00d6bc9c97bf7d149cae163ef21`; SHA-256 `1D09E3639644B6B2254499469D0765CC005A286D19F3FA595B0ED8FB07971EC7`.
- Official mappings URL: `https://piston-data.mojang.com/v1/objects/a661c6a55a0600bd391bdbbd6827654c05b2109c/client.txt`; publisher SHA-1 `a661c6a55a0600bd391bdbbd6827654c05b2109c`; SHA-256 `A2AA6EE1030BFEF79E9B2E08E79DE1637FDD7ECB5BF8891CF2E9A4B186042543`.
- Remapped jar SHA-256 `2D0C4B2EAC022E43DBE4706B7FE18C51547E4FBED86B675E92AB2040A9CF51D4`.
- Source root `decompiled_minecraft/1.18.2/mojmap/`; 4,236 Java source files.

## Source hash index

SHA-256; every path is relative to that version's Mojmap source root. Extend this inventory for every cited method/resource.

| Source path | A hash | B hash |
|---|---|---|
| `net/minecraft/client/player/KeyboardInput.java` | `EA41065C909E53F1A2CC29ECDB6A9A8F9265D2801CD8B95B996E182C318ECD69` | `281622F8481654035196A7BC1554D5251C1040518375E3AC6F6439E5EC894A75` |
| `net/minecraft/client/player/Input.java` | `367C3A9B0B21D8F106A21FD2C73A3018685DBF07D9C8A9340E2D4C9D73359201` | `EB50A4E268EC5FF8423D2805499CA3C7BAE33765CB44CFECEF808E38FA6DE3C3` |
| `net/minecraft/world/entity/player/Player.java` | `724BB298499DABE489DFFD5CE7EC81C9773619A8D2C70044911EAE4C9E8FF481` | `BF639C1962FF90D69E4569B2B18F6FCF57AC46EF80B19686F0FBC1687FCA744A` |
| `net/minecraft/world/entity/LivingEntity.java` | `33FD081AADB2B6FDC9EBF487DB6DA5B38C54F4B8676572790EE2203690D15E6F` | `DB4168D531CAF18F22E3FEFD073365E776DA4075CE01452BB9F7671D9B458782` |
| `net/minecraft/util/Mth.java` | `24515C4549E01E985017227DCCF7159166A675B232BE9135D5F896022A9CB113` | `32747C5B09FC184BAAE356E39A0088FD66C9F08F69D9E98B67C19E1DE6F1BB2E` |
| `net/minecraft/world/entity/Entity.java` | `AB28E1FBA924771EC048140DFD293EE5A46A7DFE81F71A1A0B1AECC1927232DE` | `2228FDACA5793171CBD94038306D571A6ADA78CA96F5734EFB4CADA5B744C10A` |
| `net/minecraft/client/player/LocalPlayer.java` | `C9A91CB6CB57806BC8D22E5BFE2D97DAAF21D5D2A48F34A6E2C53164C61C5812` | `99C2D18BCD23243AFB8F95C5BAFB21FB0BE7EA04AACBB14FCF7BE7CED2C9C095` |
| `net/minecraft/world/phys/AABB.java` | `DD143CCD01D0D05E50619910BCEB98A0CEC0CA526E161E9812AE1E0DB6EB7B7E` | `12134682C7F0C19A4DF431E4509D661B866B84F680B6DB82194AF8ABA7D0A50F` |
| `net/minecraft/world/phys/shapes/VoxelShape.java` | `6B53EB54933AFACDA1934C9F1E3EC3584294AA3DEC09E5FBF4EEAA52B82D76C4` | `99F8B6E44E6C249B251D98A99E38158EBCB459733B26CC98BAE15D51D3B87417` |
| `net/minecraft/world/level/block/Block.java` | `01FA40798C7A4AF538C29601A6AD52C82F3C6364F74D00E955A0A41A0A0D88B4` | `57C42EE375691755EF5D47FAD3F226F34A2043558704EC332C1A7E092FDABBA6` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `920896E6BC9D7F8794ABA3C5F325D9DFFD9C2C9422A0BE2E5DD0B7474E980515` | `3D82B89F13ED3108E09B64226D98FD673E5FEDD9A933AFE888AE580DB486DD89` |
| `net/minecraft/world/level/block/Blocks.java` | `87D72A113A3F8937A6A585EF917A4FD29CC5B335C00A858F6800E38F3C1BF7A8` | `CC6B87D2C5897E71E5244B889444AC040E3FA0A139E392523E87FEC99805A0F2` |
| `net/minecraft/world/level/block/SlimeBlock.java` | `4410396E11DBEF4843F874F9CC7563801259791C4E4B34A3A0BFEDA4EA7C482B` | `4E552C1D1AA49B115F1549A8F19415B0C9F81C0524C0BF4AFED37277D75F6388` |
| `net/minecraft/world/level/block/BedBlock.java` | `385BFC7F5C916FA897F34E5F2BB0311C4FC872733436A1A1FA2C8EDF44C234A3` | `D7EE6F4243947FF95ECF2F25DB0A04B1906509E7B18F1A1DEA50B76131527E3D` |
| `net/minecraft/world/level/material/FlowingFluid.java` | `93FAC0AA0B44DCD42A9E05783EEDE16E6E1E72CBC7CBF00D34F6E5A55B67F4C6` | `BBFB661B524AC92F74579CD4B61C1A25DF00ECC4BFE775A5516F7E4A7C8768F3` |
| `net/minecraft/world/effect/MobEffects.java` | `64B3B592A48EAC1016C3A307B85C3E201689662DEA68C80B6D642CFFA35FD289` | `92BDAB264537C8ACF1AF38A25BBBCEEF557A4CD24E248446C6463FA9812A524E` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `839E9274A4AE91DC81802914B6F4F5F354D54FFA9005AA630DA83F070B17EFAA` | `C41860B83315D5265632E9A90978E38794D83D1A0CD996DBB9C7FD8E56560DF5` |
| `net/minecraft/world/item/enchantment/EnchantmentHelper.java` | `5527514A661B14AAF8FF2A979380218C8DEDD17C24B097851160BE5D27FC4081` | `73D83D685F1F7872B5B85DE26E274FC547C6094C4E2C2FA13291FB8CDE712105` |
| `net/minecraft/world/item/enchantment/Enchantments.java` | `C9D388097FD4BBF03609258486DC02D7D24BF18AB813F20F4F0DB8D86E3DBDB8` | `BB945530CB616FFE8C23156EC0BDD5819094C92D4FB808BD9254EDFDB7953BF2` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | pending | `E718016022C2AE86A2C354AF2D34FE4DE7D6E36DC8792D2E2C1F08ABB6B77B7B` |

## Correspondence and call order

See [Navigation index](#navigation-index). Resolve actual members, descriptors, inheritance and state reads/writes by paired source inspection; no symbol-only correspondence is accepted.

## Coverage ledger

- Stage 1 input and tick ordering: findings; F-001 covers the only movement-relevant difference found in the inspected `LocalPlayer.tick()`/`aiStep()` and input producer slice. A/B `Input` structure, forward-impulse sprint threshold, key-to-impulse assignments, sampling order and `aiStep()` order were checked. Keyboard slowdown changes from double multiplication plus float cast to float multiplication, but the vanilla producer supplies only -1/0/1 before the 0.3 slowdown, yielding the same representable float results; no behavioral delta was retained.
- Stage 2 player-specific state and gates: in-progress; checked pose/dimension/crouching subset is recorded in [Navigation index](#navigation-index); broader gates remain open.
- Stage 3 living movement integration: in-progress; F-002 covers fall-flying lift coefficient. Remaining travel branches and dependencies pending.
- Stage 4 entity movement and collision: in-progress; `Entity.move()` collision flag producer and B local-player classifier examined for F-001; broader axes/step/support/callback slices pending.
- Stage 5 blocks and fluids: in-progress; checked base movement property defaults are recorded in [Navigation index](#navigation-index); registrations, shapes, fluids and resources remain open.
- Stage 6 effects, enchantments, attributes and equipment: pending; B resources still need inspection.
- Stage 7 external influences and dependency closure: pending; anchors indexed.

## Dependency queue and blockers

- B jar resource entries/tags/defaults for stages 5–6: not inspected yet.
- Any source warnings affecting movement members: review per slice; global lambda warnings do not by themselves invalidate unrelated methods.
- New dependencies discovered by slices: add with parent slice and resolution evidence.
- Stage 2: complete player pose/dimension/swim/sprint gates, item-use and ability state; verify all source hashes.
- Stage 3: finish branch-by-branch travel, attribute, depth-strider, effects and block-property closure; keep B/A exact operation order.
- Stage 4: compare full axis resolution, step-up, edge sneaking, support state, callbacks, shapes, fluids and collision tie breaks; resolve F-001's classifier dependencies.
- Stage 5: compare relevant block/fluid registrations, overrides and collision shapes, including neighboring-state logic, plus original-jar tag/resource entries; classify new blocks as modern-only where appropriate.
- Stage 6: compare Speed/Slowness/Jump Boost/Levitation/Slow Falling/Dolphin's Grace/Blindness; Depth Strider/Soul Speed/Frost Walker/Riptide; attributes, applicability, equipment and server/data inputs. Inspect both jars' relevant tags/data.
- Stage 7: compare player packet velocity/position correction handling, knockback/push, explosions, piston displacement, mount transitions and launch items; close all discovered player-state writers.

## Findings

None yet. Candidates require a concrete precondition and reachable client-player chain.

## Resume checkpoint

- Completed: exact pair artifact/source anchor checks, ordered B-side index, stage-1 bounded slice and findings F-001/F-002.
- Next: stage 2 player-specific gates; later resolve stage-4 collision classifier and continue remaining travel, block/resource, effect and external-input slices.
- Runtime validation: not performed.

## Source audit closure

- Coverage counts: 1 terminal (`findings`); 4 in-progress; 2 pending; two findings recorded.
- Overall audit is partial: unresolved slices and dependencies above are not evidence of no difference.
- Paired evidence limited to files named in the source hash inventory; add every cited source/resource hash before closing a slice.
- Unresolved dependencies remain open; this is not a complete audit.

## Implementation target source provenance

- Current build target: `minecraft_version=26.2` in `gradle.properties`.
- Native exact source was published by the shared preparation owner with readiness marker `D:/Javastuff/LegacyParkourCompat/build/major-movement-preparation/26.2--unobfuscated.json`; marker status `ready`, resolved ID `26.2`, mapping family `unobfuscated`, successful command used `--decompiler-heap=4G`.
- Exact published client jar SHA-256: `40896EE9F1E2BEC3C934DAAC7E93D41E9E3D9C2F8AE0CA366D52FFBFD1AFA290` (publisher SHA-1 recorded in the marker).
- Relevant native source `net/minecraft/world/entity/LivingEntity.java`, SHA-256 `7FFD9C70966EDC50C9CB4D9A8FE17A518E2678FF44C8026E763D0B94AC0AE51A`, was read from `decompiled_minecraft/26.2/unobfuscated/`. Its `travelFallFlying(Vec3)` calls `updateFallFlyingMovement(Vec3)` before entity movement; the helper contains `double liftForce = Mth.square(Math.cos(leanAngle));`. The matching source methods are present and intact; marker diagnostics concern unrelated methods/lambdas.
- Relevant native source `net/minecraft/client/player/LocalPlayer.java`, SHA-256 `8D089AA09217E3607B38590F7C1623385562800943AC6DFD3D17804E041DA6D6`, was read and hashed from the same ready source root for native client-side injection context.
- Exact 1.17.1 and 1.18.2 endpoint markers both report `ready` for Mojmap, with their source inventories, jar identities, mapping identities, and all listed source-file hashes locally reverified against this run's artifact manifest on 2026-10-01. The inventory's formerly pending A/B source-file hashes are now filled in above.
- Verified paired F-001 source: A's non-swimming sprint-stop predicate includes any `horizontalCollision`; B includes `horizontalCollision && !minorHorizontalCollision`. B's collision classification is produced in `Entity.move()` and LocalPlayer's `isHorizontalCollisionMinor(Vec3)`; the exact source hashes are in the table.
- Verified paired F-002 source: A uses `float n = Mth.cos(j); n = (float)(n * (n * Math.min(1.0, m / 0.4)));`; here `m` is the look-angle vector length, not horizontal movement speed. B uses `double n = Math.cos(pitch); n = n * n * Math.min(1.0, lookAngle.length() / 0.4);`. The adjacent lift and dive expressions use that value on both sides.
- The endpoints still do not identify the first changed release. Exact `1.18` and `1.18.1` Mojmap sources are required to establish whether the registered boundary is `V1_18` or `V1_18_2`; they are not yet published/verified.


## Supporting audit evidence

### Navigation index

#### 1.17.1 → 1.18.2 source navigation index

Both sides use official Mojang names and version-specific mappings. This is a bounded research index, not a complete correspondence. Paired class/member conclusions must follow inspected callers, inheritance and exact bodies.

##### Stage 1 — Local input and tick ordering

- A/B: `net.minecraft.client.player.LocalPlayer`, `KeyboardInput`, `Input`.
- Compare client tick ordering, superclass tick, input sampling, travel dispatch, movement input normalization, item-use/sneak scaling, sprint transitions, jump timing and previous/current flags.
- Read member bodies with line numbers; follow options/keybind callers only if they feed movement state. Record per-member hashes in `run.md`.
- Verified bounded delta: local sprint stop ignores B `minorHorizontalCollision`; see [F-001](findings/F-001-minor-horizontal-collision-sprint.md). Its flag producer/classifier is queued for stage 4.
- Verified bounded delta: fall-flying lift coefficient changes precision/trigonometric source; see [F-002](findings/F-002-elytra-lift-trig-precision.md).
- The inspected `LocalPlayer.tick()` call order and movement-relevant `aiStep()` order match; the key input producers assign only -1/0/1, and the 0.3 slowdown yields the same float results under the two expression types. `Input` move-vector and forward threshold expressions match. These checked slices found no further delta.

##### Stage 2 — Player-specific state and gates

- A/B seeds: `net.minecraft.world.entity.player.Player`, `LivingEntity`, player abilities, `FoodData`, pose/dimension helpers.
- Trace pose, sprint gates, swimming/crawling, flight, active item, edge sneaking, dimensions/eye height, air-speed storage, initialization and reset.
- Checked subset: A/B `Entity.setPose()` stores the same `DATA_POSE`; `LivingEntity.getDimensions(Pose)` uses the same sleeping special case and scaled superclass dimensions; `LocalPlayer.isShiftKeyDown()`, `isCrouching()`, `isMovingSlowly()` and the crouching predicate in `aiStep()` match. Broader pose collision clearance, swimming and effect/food gates remain open.

##### Stage 3 — Living movement integration

- A/B: `LivingEntity`, `Attributes`, vector/math helpers reached directly.
- Trace travel dispatch, acceleration, friction, gravity, jump and sprint impulse, climb/water/lava/glide branches, velocity cutoffs, post-travel timing, attributes and helpers.
- Checked subset: `jumpFromGround()` has the same `0.42F * getBlockJumpFactor()` base, jump-effect addition and sprint impulse. In `travel()`, water/lava and ordinary ground branches appear operation-for-operation aligned in the inspected bodies; slow-fall reset is a direct `fallDistance = 0.0F` in A and B's `resetFallDistance()` helper, whose B implementation directly performs that same assignment. Fall-flying coefficient differs in F-002. Complete branch/dependency closure, especially depth-strider and movement attribute paths, remains open.

##### Stage 4 — Entity movement and collision

- A/B: `Entity`, `AABB`, voxel shape classes, collision query owner(s), support and fluid helpers.
- Trace movement axis order, step candidates/ties, edge probes, grounding, support, velocity cancellation/restitution, collision callbacks and fluid push.
- Checked subset: `Entity.move()` in A sets horizontal collision from requested/resolved X/Z. B additionally calls `isHorizontalCollisionMinor()` and stores that result. F-001 uses this field; B `LocalPlayer` supplies the angle classifier. Full collision-axis, step, shape and callback comparison remains open.

##### Stage 5 — Blocks and fluids

- A/B registrations and overrides: block/state bases; slime/bed bounce; soul sand/ice friction; web/honey/powder snow slowdown; climbables; fluids/bubble columns; pistons; historical partial shapes.
- Trace registrations, shapes, block properties, relevant neighboring-state rules and original client-jar tags/resources. Record modern-only blocks separately.
- B-side anchors include base `Block`, `BlockBehaviour`, `Blocks`, slime/bed/soul sand/honey/ice/web/powder snow/climbable/piston classes and `FlowingFluid`. `BlockBehaviour.Properties` defaults match in the checked lines: friction `0.6F`, speed factor `1.0F`, jump factor `1.0F`; targeted registry values and overrides still require systematic paired review. The original jar resources have not yet been enumerated.

##### Stage 6 — Effects, enchantments, attributes and equipment

- Trace consumer → attribute/helper → aggregation → registration/application/removal → equipment/tags/resources.
- Provenance boundary: Java source alone is insufficient for the data/resource portions. Inspect both original client jars for applicable tags/default data before closing these slices. No resource-level findings are claimed yet.
- Explicit dispositions: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Blindness; Depth Strider, Soul Speed, Frost Walker, Riptide; Elytra, item-use slowdown and relevant equipment. Server-supplied data stays distinguished from client behavior.

##### Stage 7 — External influences and dependency closure

- A/B seeds: `ClientPacketListener`, relevant `Entity` and player callers.
- Trace incoming velocity/position corrections, knockback/push, explosions, pistons, mount transitions and launch items insofar as they mutate local player state. Close all dependencies and revisit callers.

##### Stage 1 provisional class/member correspondence

Shared official names identify candidate classes only. The checked A/B source hashes are in `run.md`. `LocalPlayer.aiStep()` exists on both sides and the sprint-stop predicate is directly paired. Input producer bodies have not yet been fully dispositioned. No rename/split claim is needed for the same-named entry classes; inheritance and remaining member bodies still require line-level comparison.
