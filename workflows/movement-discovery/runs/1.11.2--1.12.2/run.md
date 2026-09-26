# Discovery: 1.11.2 to 1.12.2

- Status: partial
- Scope: client player movement; older A = 1.11.2; newer B = 1.12.2
- Repository revision and start date: `c133c2999b6673874e35bbdb26759548407f3e11`; 2026-09-26
- Selected naming namespace: Ornithe Feather on both releases, using release-specific artifacts.
- B source command: `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.12.2 --mappings=feather`; successful output confirmed requested/resolved 1.12.2, `Applying ornithe feather (1.12.2+build.2)`, `Finished 1.12.2 using ornithe-feather`, and `BUILD SUCCESSFUL`. Raw log not retained; command output is in task transcript.
- A source command: owner reports successful exact 1.11.2 Feather run. Raw log not retained; see cross-task provenance below.
- B toolchain: Gradle 9.7.1; decompiler JVM 25.0.3+9-LTS; target bytecode Java 8; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1 (repository version catalog).

## Artifact manifest

### A — 1.11.2

- Requested/resolved: 1.11.2 / 1.11.2 (owner's successful log transcript).
- Source root: `decompiled_minecraft/1.11.2/ornithe-feather`.
- Client SHA-256 `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`; SHA-1 `db5aa600f0b0bf508aaf579509b345c4e34087be`.
- Mapping family/mode: Feather; coordinate `net.ornithemc:feather-gen2:1.11.2+build.2`; Tiny SHA-256 `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`.
- Remapped jar SHA-256 `19200acf9fdd0395535cc8a880f6ab9f6db427131c6c11259f7a3e1b07845daf`.
- Owner reports remapper invalid-access warnings and fixes for 1 class / 34 members; relevance to movement sources is being checked.

### B — 1.12.2

- Requested/resolved: 1.12.2 / 1.12.2 (successful decompiler output).
- Source root: `decompiled_minecraft/1.12.2/ornithe-feather`; the worktree junction target was verified as `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.
- Original client URL object/SHA-1: `0f275bc1547d01fa5f56ba34bdc87d981ee12daf`; SHA-256 `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`.
- Mapping family/mode: Feather; coordinate `net.ornithemc:feather-gen2:1.12.2+build.2`; cached merged Tiny mapping `build/minecraft-decompile-cache/yarn/feather-gen2-1.12.2+build.2.tiny`, SHA-256 `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`.
- Remapped jar `build/minecraft-decompile-cache/1.12.2/client-ornithe-feather.jar`; SHA-256 `d9639e8ba8a34c1f9ad8186b80575bd68440bec29b750b42eb3220cf824429a`.
- Remapper emitted invalid-access warnings and fixed 1 class / 33 members. B glide methods were independently checked from mapped bytecode; warning targets/log remain unavailable for a full warning audit.

### Cross-task provenance

A provenance was relayed by the adjacent-version owner from `C:\Users\Wolfi\.codex\worktrees\movement-discovery-1-11\LegacyParkourCompat\workflows\movement-discovery\runs\1.10.2--1.11.2\run.md`. Do not regenerate 1.11.2. The owner reports exact successful completion in 3m7s, but no raw log or source-hash inventory. This run independently hashes each A source cited.

## Correspondence and call order

- `KeyboardInput.tick()` writes directional booleans, jumping/sneaking, and sideways/forward movement impulses. `LocalClientPlayerEntity.mobTick()` samples prior flags, invokes input sampling, then handles item slowdown, autojump, sprint and movement dispatch. A/B Feather classes and the local player inheritance chain match in naming; full owner/member correspondence still needs to be recorded.
- In B, the local tick invokes `Tutorial.onPlayerInput(input)` after sampling. `Tutorial` dispatches to `MovementTutorialStep.onPlayerInput`, which only sets the tutorial `moved` boolean; it does not modify player input or movement state.
- Full player -> living -> entity movement call graph and state writers remain open.

## Coverage ledger
- Slice 1.1 / stage 1 / keyboard input sampling and sneak scaling: `compared-no-difference`. Both source paths `net/minecraft/client/entity/living/player/KeyboardInput.java`, lines 1-47. SHA-256 on both sides: `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`. Directional booleans and impulses, jump/sneak sampling, and `0.3` sneak scaling match textually.
- Slice 1.2 / stage 1 / local player input-to-sprint/autojump integration: `compared-no-difference` for the inspected movement logic in `LocalClientPlayerEntity.mobTick()`. A source SHA-256 `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed`, movement region starts at line 673; B SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`, region starts at line 694. Input flags, item slowdown, autojump/sprint conditions and their writes match in the inspected body. B adds `Tutorial.onPlayerInput` after input sampling. B `Tutorial` (SHA-256 `c5281a3f8000275b1e2387f97e88d6e0af409e3cedbff8c1668a775d3a780945`) dispatches to `TutorialStep.onPlayerInput` (SHA-256 `3150f62d0b96c83fecb231dabdecd0c618f6970c8f8ff093f27547ecb0b866fa`); only `MovementTutorialStep` overrides it (SHA-256 `44f8769ebc81ffe6f368b085987e7c8879ef5db86388741310979bc94a80de17`) and sets tutorial progress boolean `moved` only. Scoped to the inspected movement gates, not every local-player tick behavior.
- Slice 1.3-1.6 / stage 1 / alternate input providers, correction handling, autojump producers, flight and riding transitions: `pending`; paired sources and member correspondence not yet inventoried.
- Slice 2.1 / stage 2 / player abilities and hunger state: `compared-no-difference` for `PlayerAbilities.java` and `HungerManager.java`, byte-identical A/B (SHA-256 `b0350ca7819f995e4ede358a9d71270cc7a073a9d8817ce218d363cca7ea0cb0` / `09d82e6c04753f67d90c89c7a0cd93f87c228cca5c040715c3e448b6fcfffc84`). Sprint/flight gate closure in local `mobTick` is slice 1.2; other player-state slices remain open.
- Slice 2.2-2.6 / stage 2 / pose and dimensions, swimming transitions, item use, sprint eligibility, jump-power providers and movement-state defaults: `pending`; paired sources and state writers not yet inventoried.
- Slice 3.1 / stage 3 / player relative-acceleration inputs: `compared-no-difference` for the player call path. A `LivingEntity.moveRelative(float sideways,float forwards)` at `LivingEntity.java:1388` and travel call at `1803`; B `moveRelative(float forwards,float sideways,float upwards)` at `LivingEntity.java:1425` and call at `1847`. A LivingEntity SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`; B LivingEntity `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`; A/B PlayerEntity SHA-256 `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` / `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`; A/B Entity `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` / `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`. B adds `verticalSpeed`, and `Entity.updateVelocity` adds it to the normalization and Y component. The local player path supplies `sidewaysSpeed, verticalSpeed, forwardSpeed`; `PlayerEntity.moveRelative` forwards all three arguments. Tree search found the only assignment `MobEntity.java:475`, so player `verticalSpeed` retains default `+0.0F`. The added Y component is zero; B's normalization computes `(sideways² + 0) + forward²`, preserving the 1.11.2 two-input result for finite movement inputs, and horizontal axis expressions retain the same sideways/forward mapping. This disposition is player-only; non-player 3D movement is out of scope. MobEntity writer/class and exact method line closure still need hashing before terminal closure.
- Slice 3.2 / stage 3 / fall-flying view vector: `findings`, see [MOVE-001](findings/MOVE-001-fall-flying-look-yaw.md). The A/B call and implementation paths were inspected. `javap` verifies A dispatches through `getRotationVector(float)` and B `Entity.getLookVector()` directly loads pitch/yaw. A raw remapper log is unavailable; bytecode verifies the cited mapped methods.
- Slice 3.3 / stage 3 / ground jump impulse and jump effect registration: `compared-no-difference` for `LivingEntity.getJumpStrength()` / `jump()` (A `LivingEntity.java:1357-1374`, B `1394-1411`) and `PlayerEntity.jump()` (A `PlayerEntity.java:1376`, B `1375`). The `0.42F` jump strength, amplifier addition, sprint impulse math and dirty flag match in the inspected bodies. `StatusEffect.java` and `StatusEffects.java` are byte-identical A/B (SHA-256 `b7953929609d29d36fb4714013870f9d3b6362b71682f3c06b597536de3eb2db` / `65a621ba5a80957f742aa14f109224088388340d0b28b94023440ad0f8f74dd2`). Attribute application and effect timing remain open.
- Slice 3.4-3.9 / stage 3 / complete ground/air/fluid/glide/climb integration, movement attributes, gravity, effects, fluids and post-travel updates: `pending`; apart from listed bounded slices, paired methods and dependency closure are incomplete.
- Slice 4.1 / stage 4 / collision callback entry for landing: `compared-no-difference` for the inspected callback dispatch: both `Entity.move()` implementations call the supporting block's `beforeCollision` when vertical movement is clipped. A/B source hashes are in MOVE-002. Full axis resolution, step candidate ordering and support updates remain open.
- Slice 4.2-4.7 / stage 4 / full axis resolution, step-up candidate order, edge/support lookup, velocity cancellation, fluid contact and block callback closure: `pending`; the bounded bed callback route does not close these slices.
- Slice 5.1 / stage 5 / bed bounce: `findings`, see [MOVE-002](findings/MOVE-002-bed-bounce.md). The bed's 0.5625-high collision box matches; B adds player Y-velocity reflection in its `beforeCollision` override.
- Slice 5.2 / stage 5 / selected landing/friction blocks: `compared-no-difference` for `SlimeBlock`, `IceBlock`, `PackedIceBlock`, `FrostedIceBlock` and `SoulSandBlock`, each byte-identical A/B. SHA-256 respectively `38e77cdaaf3681fe3a2357ebc0dce7a86d658429463bc1e06e6e1167fe627c8a`, `c92bb850b67f5b3857d150eb6cc1c1d17d351ef0a553613a09fffe543c1d7952`, `a396a37d65103a8fbd762ec0abb8292cc69dc58c95707ac764571bb0393cc7f9`, `27bbe9206e78f1b737883403169ab3a7f8783b45062478fed6329aae3a578b9b`, `d07145d0a4bdc40e7b1ac43a09a43dd7957a4bf145d9be45e9e0ed1970041448`. Registrations/defaults and other block classes remain open.
- Slice 5.3-5.5 / stage 5 / remaining movement-block and fluid classes, registration/default properties, and modern-only block applicability: `pending`; the bed finding and selected identical block classes are bounded only.
- Slice 5.6 / stage 5 / data-driven block/effect resources in the client jars: `not-applicable` for the inspected exact artifacts. `jar tf` found zero `data/` entries in both original client jars. A SHA-256 is `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`; B SHA-256 is `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`. This does not disposition server-provided world states, equipment or synchronized effect state; those remain external movement inputs.
- Slice 6.1 / stage 6 / selected movement effect/enchantment definitions: `compared-no-difference` for `PlayerAbilities`, `StatusEffect`, `StatusEffects`, `DepthStriderEnchantment` and `FrostWalkerEnchantment`; hashes are recorded above and Depth Strider/Frost Walker are byte-identical SHA-256 `dd39c3a98d6d468dbb8b9f21288a788eef9934c061549acba120a8fed5d5b739` / `be9ba7e04598e863de7193cbd899059f42e28b152632b0b6ae492140f4b5ed98`. `EnchantmentHelper.getDepthStriderLevel` and per-entity `getLevel` retain the same max-across-equipped-stacks iteration. `ItemStack.getEnchantments` changes A null-without-NBT to B empty list; `EnchantmentHelper.getLevel(item)` maps A null and B empty to level zero. Source hashes: A/B `ItemStack.java` `dc929fcc42e94dacb1f2d2a32572c00612c4f9b39d4cb551634fd3dd290467d8` / `61964c5fd859eb16cdaa548575a1f348ce73748f45d8671afc8dd317ba5eac26`; `EnchantmentHelper.java` `be67a6c43e624fc5eebfe2f57b0680cf2b537c23f76df3ca2beb691071f7f8c4` / `226243b031824fbea660520a891f461272482808318787f6c04679e9a241d9c9`; `Enchantments.java` byte-identical A/B SHA-256 `aa59739e5b7d22cb355c478158e29258d2f2925e0a3bd39c3fae55b65829f995`. Equipment applicability and Frost Walker world/server behavior remain open.
- Slice 6.2-6.6 / stage 6 / effect application/removal, attribute aggregation, equipment slots/applicability and external synchronized inputs: `pending`; selected registration and Depth Strider helper paths do not close the full dependency chain.
- Slice 7.1 / stage 7 / selected explosion and living-entity knockback impulses: `compared-no-difference` for the inspected paths. `Explosion.java` is byte-identical A/B SHA-256 `f19842a09492536a3461f386ad3108b05aa7cb6b140c1eb3b97d7c3fe5d7a95e`; player explosion impulse adds the same `aa * ag`, `ab * ag`, `ac * ag` components at lines 142-144. `LivingEntity.applyKnockback(Entity,float,double,double)` bodies match at A line 943 / B 971; enclosing source hashes are recorded in slice 3.1. This is a bounded no-difference result; other external impulse sources remain open.
- Slice 7.2-7.5 / stage 7 / piston movement, launch items, riding transitions, network corrections and other external impulse sources: `pending`; explosion and living-entity knockback are bounded slices only.

## Dependency queue and blockers

- `DEP-A-WARNINGS`: owner has not provided the warning target list and raw log was not retained. `javap` directly verifies the cited A glide look-vector implementation and head-yaw dispatch; warning impact on other uninspected methods remains unknown.
- `DEP-B-WARNINGS`: warning target list/raw log was not retained. `javap` directly verifies the cited B `Entity.getLookVector` and `BedBlock.beforeCollision` bytecode; warning impact on other uninspected methods remains unknown.
- `DEP-MAPPING-HASH`: resolved for B; A Tiny hash/build were supplied by its owner.
- `DEP-SOURCE-HASHES`: cited source hashes are recorded; hash additional paired files as remaining slices are inspected.
- `DEP-DATA`: client data-resource inventory is resolved for both endpoints (no `data/` entries). Server-provided block states, equipment, effects and synchronized movement attributes remain uninspected external inputs.

## Finding index

- [MOVE-001](findings/MOVE-001-fall-flying-look-yaw.md): fall-flying direction uses body yaw in B; source-confirmed.
- [MOVE-002](findings/MOVE-002-bed-bounce.md): beds rebound non-sneaking players in B; source-confirmed.
- Discarded tutorial input callback: traced to tutorial progress boolean only, no player movement-state write.

## Resume checkpoint

- Completed: exact B provenance; aligned Feather family verified; keyboard sampler and local movement-gate slice compared; tutorial callback traced; bounded ability/hunger, jump, acceleration, selected block, effect/enchantment, collision callback and explosion/knockback slices; two independent movement changes recorded (glide yaw source and bed rebound).
- Next: follow the remaining pending rows in source-navigation order, starting with stage 1.3-1.6, then stages 2-7.
- Outstanding: full remapper-warning target lists/raw logs, all remaining navigation slices, and full movement call-chain correspondence.

## Source audit closure

- Coverage: 9 compared-no-difference; 2 findings; 1 client-resource slice not-applicable; remaining navigation slices pending; 2 independent confirmed movement deltas; no full navigation stage closed.
- Status: partial. Source hashes/correspondence audit incomplete; raw logs unavailable. A provenance is relayed from its owner, B provenance is independently confirmed. Runtime validation: not performed.
