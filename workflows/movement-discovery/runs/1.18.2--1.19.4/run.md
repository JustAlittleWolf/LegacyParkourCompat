# Discovery: 1.18.2 to 1.19.4

- Status: partial (paired source identity verified; selected player movement slices compared; stages 1–7 are not closed)
- Scope: client player movement; older A = 1.18.2; newer B = 1.19.4
- Repository revision and start date: `c133c29`; 2026-09-26
- Selected naming namespace: Mojang official names. Both sides use release-specific Mojmap; paired A/B alignment is verified against the 1.18.2 owner's artifact manifest and local source hashes.
- Source preparation command: `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.19.4 --mappings=mojmap`; successful console transcript was returned by the task session. Raw log file was not retained. The exact release request/resolution and completion message are recorded below.
- Toolchain: Gradle 9.7.1; Loom 1.17.21; JDK 25.0.3+9-LTS decompiler JVM; target bytecode Java 17; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; repository decompiler defaults (4G heap, WARN logger, standard repository options). Other buildSrc dependencies are in the revision-pinned version catalog.
- Worktree: managed `movement-discovery-1-19`, branch `feat/movement-discovery-1-19`, based on `c133c29`. Its ignored `decompiled_minecraft` entry is a verified junction to the shared primary-checkout source root.

## Artifact manifest

### A — 1.18.2

- Exact request: `1.18.2`. The coordinating 1.18.2 owner reports successful exact Mojmap decompilation and is recording artifact hashes and anchors.
- Source root, client/mapping/remapped hashes, successful log and per-file hashes: awaiting the owner's committed manifest. No 1.18.2 output was generated or overwritten by this worktree.
- Mapping alignment: expected Mojmap-to-Mojmap; not certified until the A manifest and selected file hashes are checked.

### B — 1.19.4

- Exact requested/resolved release: `1.19.4` / `1.19.4`, confirmed in successful task output (`Decompiling Minecraft 1.19.4 (requested '1.19.4')`; `Finished 1.19.4 using mojmap`).
- Source root: `decompiled_minecraft/1.19.4/mojmap/` (through the verified shared-root junction).
- CLI mode / naming namespace: `mojmap` / Mojang official names.
- Version JSON: `build/minecraft-decompile-cache/1.19.4/version.json`; SHA-256 `DABA0C50674934924B15007863ADE2C0A8697F370781CD15E7F5AD950176CAB6`.
- Original client jar: `build/minecraft-decompile-cache/1.19.4/client.jar`; SHA-256 `0E79CF7F07C107E9A1FE22ED703E472372B44149E64114944F0811ABBD25F3EC`; Mojang publisher SHA-1 `958928a560c9167687bea0cefeb7375da1e552a8`.
- Mojang client mappings: `build/minecraft-decompile-cache/1.19.4/client_mappings.txt`; SHA-256 `5EF270B938F89CFC77371E0DEEE9F9044C41D9A373FA11DCB1FD1923272C4606`; publisher SHA-1 `f14771b764f943c154d3a6fcb47694477e328148`; version JSON identifies artifact as `client.txt` at the matching object URL.
- Remapped jar: `build/minecraft-decompile-cache/1.19.4/client-mojmap.jar`; SHA-256 `3E40F8B67DC696F4E08F2F7AFD4D4C2E263B2A86221D13141953C7FDAA80375D`.
- Successful completion: `BUILD SUCCESSFUL in 4m 26s`; 3 actionable tasks executed. Vineflower emitted processed-twice notices for ModelBakery, PalettedContainer, and CarvingMask. No decompiler error summary appeared. Loom emitted one access-fix warning for OptionInstance.ValueSet during the preceding project configuration/remapping; assess relevant method bodies if they enter a slice.
- Source hashes (SHA-256), for initial navigation anchors:
  - `net/minecraft/client/player/LocalPlayer.java`: `8E7DA18F42D09FBB994F522C2B0E65FCB2BB83CABB21024360299D44D9674C58`
  - `net/minecraft/client/player/KeyboardInput.java`: `A8064906872955A3520398AB5B2A326552D424F41887D1294AA6A038E2623FF0`
  - `net/minecraft/world/entity/player/Player.java`: `5E4436AFCCB361156F8184E7A5CFD91D5B12DCFE8F5937B4AC23DD3EDDA737A2`
  - `net/minecraft/world/entity/LivingEntity.java`: `C8D91AF61F87AAA1666DE79696D7CD9D4A8212D05F873BDAF28D7BB2926BF165`
  - `net/minecraft/world/entity/Entity.java`: `3667FEE610CBC5F58012E3A8FB8D6C4849F649FB7FE5300595158112D8B4B58B`
  - `net/minecraft/world/phys/AABB.java`: `594239BB50298D34533FC8EFC113F4C4B5312A41D3DC1713CC85130D5020EE5E`
  - `net/minecraft/world/phys/shapes/VoxelShape.java`: `99F8B6E44E6C249B251D98A99E38158EBCB459733B26CC98BAE15D51D3B87417`
  - `net/minecraft/world/level/material/FlowingFluid.java`: `D84CEB3C0B83DA25FB68599E16CC2298106E745A9EF0C9F32F616D097A8EC56C`
  - `net/minecraft/world/effect/MobEffects.java`: `9266FEF24206A32B67E6977580377E8FB92D860932B2EB909756E387E7DF943C`
  - `net/minecraft/world/item/enchantment/Enchantments.java`: `35FA4D1F9DA93971FA70BF6EC2F9218A8825FF343038B51143AE1404867F38E3`
  - `net/minecraft/world/item/enchantment/EnchantmentHelper.java`: `C98B9E538AAE7FEA33A125560400F346745BAC993E859FFD3912DA58F3CC1128`
  - `net/minecraft/world/level/block/Blocks.java`: `A4F2DF87C2CAE5A3827A818F39F7AD123EE5B18905EAD9947EFE654421C30FF5`
  - `net/minecraft/world/level/block/SlimeBlock.java`: `F5CE06A58CB3E46E6F3C900BDF521E2984765846DC55CA701812194D09D65F54`
  - `net/minecraft/world/level/block/SoulSandBlock.java`: `F6CC9CA6E841B16C4E739E840AC4EBCE46B5B8BD40564473911F2246D1AAB7D8`
  - `net/minecraft/client/multiplayer/ClientPacketListener.java`: `BCC74E52A32D20A3E993EBE4E08FFFE8CACA7481FAD8F326DACDC93796B2B715`
- Jar resources: the client jar contains `data/minecraft/tags/blocks/*.json` and advancement data. Resource inclusion does not establish source of all runtime data; relevant tags and values must be individually inspected and hashed per slice. Server datapacks/synchronized values remain explicit external inputs.

## B-side navigation index

See [b-navigation.md](b-navigation.md). It records only B-side class/member discovery and a reusable order/state map; it contains no A/B behavioral conclusion.

## Correspondence and call order

- Overall correspondence and paired member descriptors: pending A manifest, inheritance and call-path verification on both versions.
- B-side inheritance anchors observed: `LocalPlayer extends AbstractClientPlayer`; `Player extends LivingEntity`; `LivingEntity extends Entity`.
- B-side source call anchors and navigation notes are in `b-navigation.md`.

## Coverage ledger

The detailed paired coverage updates below supersede this initial ledger. Terminal bounded slices are marked `findings` or `compared-no-difference`; stage closure is not claimed.

## Dependency queue and blockers

- A provenance and file-hash verification are complete for every paired path cited so far. No A or B output was regenerated by this run.
- B resource closure: inspect exact client-jar resources (tags/data) referenced by movement consumers as slices reveal them; client jar may not supply server-controlled tags or datapacks.
- Decompiler warning review: if any processed-twice class or OptionInstance access warning touches a cited movement member, verify via mapped bytecode before classifying that slice.

## Finding index

See F-001 through F-008 in `findings/`. Findings are scoped to the stated player conditions; they do not establish first introduction releases inside the interval.

## Resume checkpoint

- Last completed: paired A/B exact-source verification, five scoped findings, and several bounded no-difference slices.
- Next: close the remaining stage 1–7 dependencies listed below, beginning with full sprint eligibility and local-player tick/input order, then acceleration/drag, collision candidate ordering and callbacks, block/fluid/effect dependencies, and packet/mount influences.
- Do not regenerate A or B during this run. Any output regeneration requires explicit invalidation/re-hashing of dependent evidence.

## Source audit closure

- Coverage: 8 source-confirmed findings (F-001–F-008) and 14 bounded `compared-no-difference` slices; additional sub-slices remain pending or in progress.
- Unresolved gaps: stages 1–7 are not closed; block/effect/attribute inputs to ground/air acceleration and friction; world fluid layout/flow data, fluid propagation and other swimming, climbing, gliding and riding branches; full jump/pose dependencies; collision and step callbacks; remaining inside-block consumers; shapes, registrations and resource/tag closures; local-player packet and mount influences; relevant decompiler-warning bytecode review.
- Runtime validation: not performed (separate workflow).

## A provenance verification update

The 1.18.2 owner manifest at `feat/movement-discovery-1-18:workflows/movement-discovery/runs/1.17.1--1.18.2/run.md` was read. It confirms exact requested/resolved 1.18.2, explicit release-specific Mojmap, and the following local hashes: client `1D09E3639644B6B2254499469D0765CC005A286D19F3FA595B0ED8FB07971EC7`, mappings `A2AA6EE1030BFEF79E9B2E08E79DE1637FDD7ECB5BF8891CF2E9A4B186042543`, remapped jar `2D0C4B2EAC022E43DBE4706B7FE18C51547E4FBED86B675E92AB2040A9CF51D4`. The owner's source-hash index for all shared files used here was independently recomputed against `decompiled_minecraft/1.18.2/mojmap/`; all listed 1.18.2 hashes matched. The A and B sides therefore have verified exact-source identities in aligned official names.

Selected B files not in the initial anchor inventory: `net/minecraft/client/player/Input.java` SHA-256 `B302FFBC45C5F900EA18A4D4AF2DF6FA0454EA7CB7744A0D249061E5FCB97FBB`; `net/minecraft/world/item/enchantment/SwiftSneakEnchantment.java` SHA-256 `6D68936DA23F57A2D9725155035B0A01D3B5017A899B5F9EC3C69F3E2256D64F`.

## Current paired coverage

- Stage 1 / input crouch-scaling slice: `findings`; see F-001. Paired path, helper, registration and equipment-slot closure are source checked.
- Stage 1 / remaining input and tick order: in progress; see the bounded sprint-start interaction in F-006. Auto-jump triggering and complete tick/state-writer order remain pending.
- Stages 2–7: see the paired updates below; stage closure and multiple dependency categories remain pending.
- Runtime validation: not performed.

- Stage 3 / fall-flying slow-fall-distance slice: `findings`; see F-002. The exact helper and player-reachable branch are paired. Fall-distance accumulation, Elytra entry/exit, and server-side consequences remain dependent slices.

## Stage 1 coverage update — keyboard direction and threshold

- Slice 1.1 / key sampling, impulse sign, move-vector representation, and forward-impulse threshold: `compared-no-difference` within these methods. A `KeyboardInput.tick(boolean)` and B `KeyboardInput.tick(boolean, float)` read `up`, `down`, `left`, `right`, jump and shift in the same order, and both use the same `calculateImpulse` body. A `Input.getMoveVector()` and B equivalent construct `Vec2(leftImpulse, forwardImpulse)`; `hasForwardImpulse()` uses `forwardImpulse > 1.0E-5F` on both. Evidence: paired files `decompiled_minecraft/<version>/mojmap/net/minecraft/client/player/{KeyboardInput,Input}.java`; SHA-256 values in the artifact manifest/source hash index. The sole difference in these sampled bodies is crouch-scale parameterization, separately recorded in F-001. This does not disposition gamepad/controller producers or downstream vector normalization.
- Slice 1.2 / crouch and visual-crawl input multiplier: `findings`; F-001.
- Slice 1.3 / local sprint gates and sprint-state lifetime: `in-progress`. B factors start conditions through `canStartSprinting()` and introduces `vehicleCanSprint()` plus a fall-flying gate; F-006 records a reachable water-travel consequence of the latter. Passenger eligibility and remaining flag consumers are open. The removed A `sprintTime` field is currently a discarded candidate: full-tree search finds only its A declaration, increment and reset, with no reader; it is not evidence of a movement behavior delta.
- Remaining Stage 1 work: finish sprint gates and flag consumers; compare auto-jump timers and jump delay/cooldown callers, plus the complete local tick/state-writer order; then close the coverage row or retain dependencies.

## Stage 1 coverage update — ability toggles and horse-jump input

- Slice 1.4 / mayfly toggle and fall-flying command input: `compared-no-difference` for the inspected local input predicates and command sequence. Both `LocalPlayer.aiStep()` bodies decrement `jumpTriggerTime`, require a jump-key rising edge and no auto-jump to toggle flight, suppress the second-tap toggle while swimming, notify abilities on a toggle, and issue START_FALL_FLYING only when the same jump, ability, passenger, climbable, Elytra and `tryToStartFallFlying()` predicates pass. This is an input/command comparison only; the glide travel equations and packet/server authority remain separate. Evidence: paired `client/player/LocalPlayer.java` hashes A `99C2D18BCD23243AFB8F95C5BAFB21FB0BE7EA04AACBB14FCF7BE7CED2C9C095`, B `8E7DA18F42D09FBB994F522C2B0E65FCB2BB83CABB21024360299D44D9674C58`.
- Slice 1.5 / mounted horse-jump charge input: `compared-no-difference` for the common 1.18.2 jumpable vehicle. A processes charge while `isPassenger()` and the vehicle implements `PlayerRideableJumping` with `canJump()` true; B requires a controlled vehicle implementing that interface, `canJump()` true, and `getJumpCooldown() == 0`. The only shared implementation is `AbstractHorse`: it remains the only 1.18.2 implementer; in B the interface's default cooldown is zero, and `AbstractHorse` does not override it. `AbstractHorse.canJump()` is `isSaddled()` on both sides. B's other implementer, Camel, and its nonzero cooldown behavior are modern-only and outside the historical feature intersection. The compared client blocks retain the same charge counter, scale arithmetic, release call and packet call. Evidence: paired `LocalPlayer.java` hashes above; `world/entity/PlayerRideableJumping.java` A `7F9D0612D15EBF84046F08490C9FA1BC8D3D837D0D7C4C3DDC2254DAC621B82A`, B `93506ACE1F4DDDCCC059FBD1728941AD69EDB89EA10DA92CAE236AABB8AC04B5`; `animal/horse/AbstractHorse.java` A `2AB94AE9C55DC5413D5A13F6CD2DCC432F97544FA6D9DB6D07C793CE572EBBC9`, B `538E06F092B59532E36CBC6724002CAD43BCBA5D1AE61289225807D339D6CC8E`.
- Other Stage 1 slices still pending: complete sprint eligibility and tick-order/state-writer closure; auto-jump triggering and edge-space probes; jump delay/cooldown callers; ability flight movement; mounted input beyond the common horse charge path.

- Stage 3 / airborne sprint speed numeric value and state lookup: `findings`; see F-003 and F-004. The stable-sprint float expression and sprint-transition timing are separate findings with the shared player-travel call chain recorded.
- Stage 1 / fall-flying sprint-start predicate and its water-travel consequence: `findings`; see F-006. Passenger eligibility and broader sprint-state lifetime remain open.
- Stage 1 / auto-jump probe normalization: `findings`; see F-007. Timer/input and collision-shape dependencies remain open.

## Stage 2 coverage update — player pose geometry

- Slice 2.1 / common player pose dimensions and eye heights: `compared-no-difference` for the A/B intersection of player poses. `Player.POSES` retains the same standing, sleeping, fall-flying, swimming, spin-attack, crouching and dying dimensions; `getDimensions(Pose)`, `getStandingEyeHeight(Pose, EntityDimensions)` and `updatePlayerPose()` use the same player logic and relevant values. Paired evidence: `decompiled_minecraft/<version>/mojmap/net/minecraft/world/entity/player/Player.java` (hash A `BF639C1962FF90D69E4569B2B18F6FCF57AC46EF80B19686F0FBC1687FCA744A`, B `5E4436AFCCB361156F8184E7A5CFD91D5B12DCFE8F5937B4AC23DD3EDDA737A2`) and `world/entity/Pose.java`. B adds mob-specific enum values; they are not in the player pose map and are outside this bounded claim. Entity dimensions scaling and pose-transition callers remain to inspect.
- Other player-specific gates/state: pending, including remaining sprint eligibility (stage 1), ability flight movement, water/climb transitions, active-item state, and initialization/reset timing. Base ability values and unmounted sprint hunger input are covered in slice 2.2 below.

## Stage 2 coverage update — ability values and sprint hunger gate

- Slice 2.2 / ability flying speed and hunger values used by the local sprint gate: `compared-no-difference` for the inspected values and consumer predicates. `Abilities.java` is byte-for-byte identical (SHA-256 `A4F952ADA7BC3B21406FEAF13C171BFA22D36B01E265E3B987612F28B5609EDC` on both sides), including default `flyingSpeed = 0.05F`, stored flying/mayfly flags and speed accessors. Both local sprint gates use food level `> 6.0F` or `mayfly` for an unmounted player; B's separate passenger allowance is handled in the stage 1 sprint-gate dependency and is modern-mount-only in the inspected entity registrations. `FoodData` remains identical for the sprint-relevant food level, exhaustion and saturation updates; its only source diff in this class changes the starvation `DamageSource` API call. Evidence: `world/entity/player/Abilities.java` identical hash above; `world/food/FoodData.java` A `6B60D0DF297C059DDFC0A45599CB689B954046F41E6070C86821007B8EA4CCFC`, B `3288F1D1287F782164A3E55CF5BB2B08DEBAA36E79A19398D19C2C6EAEDCD59`.
- This does not disposition movement-speed attribute modifiers, the other effects/enchantments, or server-synchronized ability/attribute values.

## Stage 3 coverage update — jump impulse core

- Slice 3.1 / ground jump power, Jump Boost arithmetic, and sprint jump impulse: `compared-no-difference` for the paired core methods. `LivingEntity.getJumpPower()` remains `0.42F * getBlockJumpFactor()`; `getJumpBoostPower()` remains `0.1F * (amplifier + 1)` when Jump Boost is active; `jumpFromGround()` retains the same double addition, vertical velocity write, yaw cast to float radians, and sprint impulse `0.2F` operation order. `Player.jumpFromGround()` still delegates then applies the same exhaustion values. Evidence: paired `world/entity/LivingEntity.java` hashes A `DB4168D531CAF18F22E3FEFD073365E776DA4075CE01452BB9F7671D9B458782`, B `C8D91AF61F87AAA1666DE79696D7CD9D4A8212D05F873BDAF28D7BB2926BF165`; paired `world/entity/player/Player.java` hashes as above. This result covers the formulas only: block jump-factor implementations/registrations, Jump Boost registration and other jump gates remain in the dependency queue.
- Slice 3.2 / fall-flying fall-distance guard: `findings`; F-002.
- Slice 3.3 / ordinary airborne sprint speed: `findings`; F-003 and F-004.
- Remaining Stage 3 work: block/effect/attribute inputs to acceleration and friction, world fluid layout/flow values and propagation, and swimming, climbing, glide and riding branches; resolve all helpers and attributes.

## Stage 3 coverage update — input transform and ordinary gravity branch

- Slice 3.4 / `Entity.getInputVector` and local ground/air travel input transform: `compared-no-difference` for the shared arithmetic. Both versions return zero when input length squared is below `1.0E-7`, normalize only when length squared is greater than `1.0`, scale by the supplied speed, and rotate X/Z using the same sine/cosine and operation order. In the ordinary non-fluid, non-gliding travel branch, both use block friction to select `friction * 0.91F` on ground or `0.91F` in air, call the same relative-friction movement method, apply the same Levitation/void/gravity alternatives, and multiply post-move X/Z by that factor and Y by `0.98F` unless `shouldDiscardFriction()`. `LocalPlayer.isEffectiveAi()` returns true in both versions, so the changed outer travel guard still enters this body for the local player. Evidence: `world/entity/Entity.java` A SHA-256 `2228FDACA5793171CBD94038306D571A6ADA78CA96F5734EFB4CADA5B744C10A`, B `3667FEE610CBC5F58012E3A8FB8D6C4849F649FB7FE5300595158112D8B4B58B`; `world/entity/LivingEntity.java` hashes in F-002/F-003. The input speed value itself remains independently covered by F-003/F-004; source friction values and effects are separate dependencies.
- Remaining Stage 3 work: close block/effect/attribute inputs to ground speed and friction, world fluid layout/flow values and propagation, swimming, climbing, levitation/slow-falling, glide and ride branches. Do not generalize slice 3.4 to those branches.

## Stage 3 coverage update — player water/lava travel

- Slice 3.6 / shared water and lava travel equations: `compared-no-difference` for the inspected local-player branches. Both `LivingEntity.travel(Vec3)` methods use the same water sprint slowdown (`0.9F`) or `getWaterSlowDown()` (`0.8F`), Depth Strider cap/airborne halving and interpolation, Dolphin's Grace override (`0.96F`), input scale, movement, horizontal-climb correction, `(waterSlowdown, 0.8F, waterSlowdown)` drag, fluid falling adjustment and collision escape test. Both use the same lava input scale, fluid-height threshold, `(0.5, 0.8F, 0.5)` drag or `0.5` scale, quarter-gravity, and collision escape test. The local-player travel body is entered in both versions; glide's separate F-002 behavior is not covered here. Evidence: paired `world/entity/LivingEntity.java` hashes A `DB4168D531CAF18F22E3FEFD073365E776DA4075CE01452BB9F7671D9B458782`, B `C8D91AF61F87AAA1666DE79696D7CD9D4A8212D05F873BDAF28D7BB2926BF165`; `getFluidFallingAdjustedMovement`, `getWaterSlowDown` and `canStandOnFluid` bodies are byte-for-byte equal. This compares formulas only, not the fluid-current prepass or water/lava flow data.
- Slice 3.7 / underwater boat passenger water-current gate: `findings`; see F-008. The A/B branch and player velocity consequence are scoped in that finding.
- Remaining Stage 3 work: close world fluid layout/flow values and propagation, block/effect/attribute inputs to acceleration and friction, and swimming, climbing, levitation/slow-falling, glide and other riding branches. Do not generalize slice 3.6 beyond the inspected water/lava travel equations.

## Stages 3 and 6 coverage update — water-travel modifiers

- Slice 3.8 / Depth Strider and Dolphin's Grace inputs: `compared-no-difference` for the inspected client movement inputs and vanilla registrations. `EnchantmentHelper.getDepthStrider(LivingEntity)`, its `getEnchantmentLevel` traversal of the enchantment's equipped slot items, and the per-stack level lookup have the same bodies. Both register `depth_strider` as a rare WaterWalker enchantment over the same armor-slot array; `WaterWalkerEnchantment` uses the same costs, max level 3 and compatibility rule. Both register `dolphins_grace` as effect ID 30, beneficial category, color 8954814, with no attribute modifier; `LivingEntity.travel` consumes the effect through the already compared `0.96F` water slowdown override. Evidence: `world/item/enchantment/EnchantmentHelper.java` A SHA-256 `73D83D685F1F7872B5B85DE26E274FC547C6094C4E2C2FA13291FB8CDE712105`, B `C98B9E538AAE7FEA33A125560400F346745BAC993E859FFD3912DA58F3CC1128`; `Enchantments.java` A `BB945530CB616FFE8C23156EC0BDD5819094C92D4FB808BD9254EDFDB7953BF2`, B `35FA4D1F9DA93971FA70BF6EC2F9218A8825FF343038B51143AE1404867F38E3`; `WaterWalkerEnchantment.java` is byte-for-byte equal (SHA-256 `B7B2F93857B699E492446F995A235EF88163F5CC8B2FF359C589EA0A8817FD17` on both); `MobEffects.java` A `92BDAB264537C8ACF1AF38A25BBBCEEF557A4CD24E248446C6463FA9812A524E`, B `9266FEF24206A32B67E6977580377E8FB92D860932B2EB909756E387E7DF943C`. This closes only these water-travel modifiers and registrations, not other effect, attribute, or inventory-synchronization inputs.

## Stage 3 coverage update — fluid-current sampling and flow calculation

- Slice 3.9 / water/lava current sampling and flow-vector calculation: `compared-no-difference` for the inspected source algorithms. Both `Entity.updateFluidHeightAndDoFluidPushing()` methods scan the same `getBoundingBox().deflate(0.001)` integer bounds, accumulate fluid surface height, average qualifying flow vectors, apply the same low-velocity minimum-current rule, and write the same delta-movement addition. Both `FlowingFluid.getFlow()` methods build the same horizontal neighbor-height gradient, apply the same falling-fluid face correction and normalize the result; `affectsFlow`, `isSolidFace`, `hasSameAbove`, height helpers and `FluidState` delegators are identical. The water/lava `getAmount` and `isSource` state overrides also match pairwise. Evidence: `Entity.java` hashes are in slice 3.7/F-008; `world/level/material/FlowingFluid.java` A `BBFB661B524AC92F74579CD4B61C1A25DF00ECC4BFE775A5516F7E4A7C8768F3`, B `D84CEB3C0B83DA25FB68599E16CC2298106E745A9EF0C9F32F616D097A8EC56C`; `FluidState.java` A `A39D52CDAEBCFF391ABD094D17C0D8C190DF27AF7AEB5AC3810370ECA017C459`, B `0D6D1D24DEB58F056163ACC3AE8976D2C7B3394ACA2AE37FA43BC560C9D33B7C`; `WaterFluid.java` A `4FF811FB14B0AE0F67A9A9068B286BC0DD7667AC0BC6A4964507B63D538DDA50`, B `3E10320356B11D415D8E4F3EA2B7FBB27E5BD8ABDC45D524AFB18B834E613E5F`; `LavaFluid.java` A `117B1AE75B42432963928458503C87649A351F6C577A2B417075A8CB99C44F50`, B `1D239A6E0C99AEE386B5DFB0244A29F4A48689808F9BE64DDA89C9A63B9FC6DA`. This closes calculation code only. World fluid layout/state evolution and block face-sturdiness inputs remain outside the claim.

## Stage 5 coverage update — common ice friction registrations

- Slice 5.2 / registered slipperiness for common ice blocks: `compared-no-difference` for ICE, PACKED_ICE, FROSTED_ICE and BLUE_ICE. Both `Blocks.java` versions assign friction `0.98F` to the first three and `0.989F` to blue ice. The `Block.getFriction()` accessor returns the stored property unchanged on both sides, and `BlockBehaviour.Properties` defaults friction to `0.6F` on both. Evidence: `world/level/block/Blocks.java` A SHA-256 `CC6B87D2C5897E71E5244B889444AC040E3FA0A139E392523E87FEC99805A0F2`, B `A4F2DF87C2CAE5A3827A818F39F7AD123EE5B18905EAD9947EFE654421C30FF5`; `world/level/block/Block.java` A `57C42EE375691755EF5D47FAD3F226F34A2043558704EC332C1A7E092FDABBA6`, B `440B55E32F468572B4C078AB0CE5BB9B94F1C7ABCAA03B03A9CE7FACCB646857`. This covers only these common registrations and the stored-friction accessor; it does not close friction values for other blocks or block collision shapes.

## Stage 4/5 coverage update — collision and contact slices

- Slice 4.1 / axis collision order and step-up candidate ordering: pending full call/member correspondence; initial paired `collide()` inspection shows the same Y, lower-magnitude-horizontal-first, other-horizontal axis order and candidate tie comparisons. The player step-height accessor change must be resolved through `LivingEntity.maxUpStep()` before closing.
- Slice 4.2 / support position and careful-step callbacks: in-progress. `Entity.move()` changed `getOnPos()` to `getOnPosLegacy()`; B uses `0.2F`, matching A's inline support offset. `stepOn()` now dispatches for careful movement but relevant B callbacks add/move the careful-step guard into `MagmaBlock`, `RedStoneOreBlock`, `SlimeBlock` and `TurtleEggBlock`; compare their call paths and callbacks before disposition. B-only sculk step callbacks are modern additions.
- Slice 4.3 / inside-block query bounds and cobweb slowdown: `findings`; see F-005. Query-bound dependencies on honey, bubble columns, powder snow and sweet-berry bush are pending.
- Slice 5.1 / block-contact implementations, shapes, registrations and data: pending; include the callback dependencies above and all remaining navigation stage 5 categories.

## Stage 4 support-position follow-up — player consumers

- Slice 4.2 / feet-state lookup used by shared player movement: `compared-no-difference` for the inspected player-reachable consumers. A's protected `getBlockStateOn()` samples `getOnPos()` at the historical `0.2F` offset. B keeps that offset in `getOnPosLegacy()` / `getBlockStateOnLegacy()`, and changes the shared `LivingEntity` consumers `tryAddSoulSpeed()`, `tryAddFrost()` and the soul-speed removal check to call the legacy method. The player climbing and powder-snow movement paths in `LivingEntity` continue to use the same cached `getFeetBlockState()`; paired bodies were inspected. Evidence: paired `world/entity/Entity.java` and `world/entity/LivingEntity.java` (hashes in the artifact manifest and selected-file inventory). This closes only these support-state consumers; it does not close the block callback categories in F-005.
- B's new no-argument `getOnPos()` uses `1.0E-5F`, but player `Entity.move()` uses `getOnPosLegacy()` for fall/support resolution. The other inspected player call sites of `getBlockStateOn()` are STEP and HIT_GROUND game-event contexts; those carry state to event listeners and are not used by the inspected player movement formulas. Their modern sculk listeners are outside the 1.18.2 map feature set. This is not evidence that all event behavior is equivalent.

## Stages 3, 5 and 6 resource coverage — client-default tag membership

- Slice 3.5 / movement-consuming fluid and equipment tags: compared-no-difference for the vanilla client-jar values of water, lava and freeze-immune wearable tags. A water tag contains minecraft:water and minecraft:flowing_water (SHA-256 698E1662335B6241879B380A58D478EE019A1E789362B004050B5CCAC421AD18); B contains those values (SHA-256 DCFA69A748D03DBF788D8F7B0E5EB6C8DE6355A527FCAF74B9210FE4BE2A3004). Lava is minecraft:lava and minecraft:flowing_lava in both (A F3A67622F1F6A4C69E7792F1E3731B04236962B5BE1821671ACA0D1B59C316DD, B 71F50FB9092D78260BC7434731FC5FD426A44E5284A6AD084EC71CB725630C6B). freeze_immune_wearables lists leather boots, leggings, chestplate, helmet and horse armor in both (A 40CDDD054182B337E5A25538ECF333B853AA3E081A5FC8CCBFAA77612583F5E6, B 8DF0FA68F14A7F9705DF39043A43C0B07ABF3C30642608EC47C52F78A3EE0C3D). These are exact ZIP-entry SHA-256 values from the corresponding client jars in the artifact manifest.
- Slice 5.3 / climbable and Soul Speed block tags: compared-no-difference for vanilla client-jar memberships. climbable has ladder, vine, scaffolding, weeping/twisting vines and plants, and cave vines and plants (A 7BA8E23FAF48885E91AF7458E0928C21FA74B20D2F1C5CE9A1161A4F481FD45C, B D0E3E76D7457F3F3F3D7219FE218C7746E4B2E7626D5C388FCF193089069E365). soul_speed_blocks has soul sand and soul soil (A C86FBD7BCFA2B94C881F0BA9980A694B28150A5B0B628AEC0CFEDC552F68994F, B 8BA7EAC6F7C74D25601EF4455B71E3947FDB8E51B8FAFD55DDDEE48397B144B6). Direct consumers inspected: LivingEntity.onClimbable reads BlockTags.CLIMBABLE; onSoulSpeedBlock reads BlockTags.SOUL_SPEED_BLOCKS; LivingEntity.isFreezing checks each equipment slot against ItemTags.FREEZE_IMMUNE_WEARABLES on both sides.
- A serializes replace=false in these resource entries; B omits the key. A Tag.Builder.addFromJson reads it with GsonHelper.getAsBoolean(..., false); B TagFile.CODEC uses optionalFieldOf('replace', false). Thus the omitted field has the same default. Source hashes: A net/minecraft/tags/Tag.java 11ABE747058B26283DB9B7CAF06EC6D1061211464AB4BE25C2E64C60D28F0726; B net/minecraft/tags/TagFile.java 6F57641C31A8E983E28B48031F9EB3F06BB2C0EE240E753A48C0AE12C1B01AB1.
- Boundary: these hashes certify only the bundled vanilla client defaults. Server-supplied tag data/datapacks can replace or extend live tag contents; this run does not certify those external values. This closes only these tag inputs, not fluid-height/flow algorithms, the full freezing/slowness pipeline, or other effect/enchantment registrations.

## Current audit status

This run remains partial. Confirmed findings: F-001 through F-008. Bounded no-difference slices are recorded for input sampling, shared player pose geometry, ground-jump core formulas, support-state consumers, mayfly/fall-flying input predicates, common horse-jump input, ability/hunger values at the sprint gate, ordinary travel input/gravity formulas, player water/lava travel equations and modifiers, fluid-current sampling and flow calculation, common ice friction registrations and the cited movement-tag defaults. The outstanding stage coverage is listed explicitly below; no claim of complete movement parity is made.
