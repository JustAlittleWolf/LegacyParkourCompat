# Discovery: 26.1.2 to 26.2

- Status: active
- Scope: exact adjacent endpoint comparison, source-only player movement; older A = 26.1.2; newer B = 26.2. Direct player velocity/impulse/knockback response is in scope even when combat can trigger it; attack/damage resolution and health/food-state production are excluded. Non-player movement and vehicle physics are excluded. No runtime Java implementation, wiki browsing, release-notes mechanics, tests, client/TAS/Gym/server/Docker launches. Runtime validation is not authorized.
- Repository revision and start date: branch started at main `002137b227676caea77f6832b9f4c8d0b6200bff`; dedicated branch `feat/source-discovery-movement-source-26-1-2-26-2`; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: both endpoints are native Java 26 unobfuscated sources in Mojang official names; CLI `unobfuscated` for A and B. Current A and B readiness markers verify `versionId=26.1.2` and `versionId=26.2` respectively. Each is native `unobfuscated`; pair alignment is verified. Prior source report was used as navigation only; none of its hashes or conclusions are current evidence.
- Source preparation owner / command / log / readiness marker: source owner is the campaign's single shared-source owner; personal name not present in this worker handoff. A provenance `ready/26.1.2/unobfuscated.provenance.json` records `.\gradlew.bat decompileMinecraft --versions=26.1.2 --mappings=unobfuscated --decompiler-heap=4G --output-root=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\staging\unobfuscated-26.1.2-3db218180ac44a64ad496eeb8e05ac18\output --cache-directory=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\staging\unobfuscated-26.1.2-3db218180ac44a64ad496eeb8e05ac18\cache`; `unobfuscated.success.log` reports exit code 0 and the full Gradle log is persisted under the staging root. B `ready/26.2/unobfuscated.provenance.json` records the exact command, and its success excerpt says `BUILD SUCCESSFUL`; the initial full stdout was streamed but not persisted. This worker has not run decompilation.
- Toolchain/decompiler/remapper versions and options: both provenance records agree on Vineflower 1.12.0, Java 25.0.3+9-LTS, Gradle 9.7.1, Mapping IO 0.9.1, ASM 9.10.1, Tiny Remapper 0.14.1 and Gson 2.14.0; both use a 4G decompiler heap and native unobfuscated input. The source-owner personal name is not present, and B's full Gradle output was not persisted; exact preparation identity/toolchain are otherwise resolved.
- Discovery author(s): source-only worker for this exact pair.
- Independent reviewer (must differ from discovery authors): not assigned in the available task instructions.

## Artifact manifest

### A — 26.1.2

- Exact release: readiness JSON `ready/26.1.2/unobfuscated.ready.json`, `versionId=26.1.2`, status `ready`; source root `build/movement-campaign-2026-10-07/ready/26.1.2/unobfuscated/`; native `unobfuscated` namespace; original client jar is the decompiler input. Ready JSON reports Vineflower, Java runtime 25, 6,882 source files / 32,992,615 bytes.
- Original client jar `ready/26.1.2/artifacts.sha256` entry `26.1.2/client.jar`, SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`; the jar in the exact run's isolated staging cache at `staging/unobfuscated-26.1.2-3db218180ac44a64ad496eeb8e05ac18/cache/26.1.2/client.jar` was rehashed and matches. Version metadata says ID 26.1.2. Mappings and remapped jar: not applicable, published unobfuscated.
- Source manifest `ready/26.1.2/unobfuscated.sources.sha256`, SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`.
- Artifact manifest `ready/26.1.2/artifacts.sha256`, SHA-256 `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`.
- Movement-method diagnostics `ready/26.1.2/movement-diagnostics.txt`, SHA-256 `b4bd3875263a7155ac32cffd64cfacabc7bd54c82c5fe4082f1ef8a809024c02`. It lists Entity.move/moveRelative, LivingEntity.jumpFromGround/travel/aiStep and Player.aiStep/travel; diagnostics are anchor discovery only and do not establish complete coverage.
- Direct A source hashes, recomputed and matching the A source manifest: `net/minecraft/client/player/KeyboardInput.java` `b4bbb410650444c30d2a62d70fd3c6cd1aefa104b8a12e474e2a478a62089c39`; `net/minecraft/client/player/LocalPlayer.java` `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; `net/minecraft/world/entity/LivingEntity.java` `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`; `net/minecraft/world/entity/Entity.java` `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`; `net/minecraft/world/entity/player/Player.java` `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`; `net/minecraft/world/entity/ai/attributes/Attributes.java` `8eee57d8375c7af39525ccd25593618fcbcbe0348da6a8738556baaabea3c1d5`.
- Additional source hashes cited by the slime landing finding: `net/minecraft/client/player/AbstractClientPlayer.java` `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc`; `net/minecraft/world/entity/Avatar.java` `01a8092bc2637e7d42a261fb564bffcabadd28625cc02b120e943559e2d4fe78`; `net/minecraft/world/level/block/SlimeBlock.java` `84d22cf526d6bf1b4ec4b0b642a76fc2c90380a71f05ab92a7de9935f0d1c38e`; `net/minecraft/world/level/block/Blocks.java` `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f`.
- Additional A source hashes cited by the external-impulse review: `net/minecraft/client/multiplayer/ClientPacketListener.java` `eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82`; `net/minecraft/world/entity/ai/attributes/Attribute.java` `854d1c12ab0b49a2136d5807db70676fe041671d40d44140cc2fda7c4ab4db59` (also identical in B).
- Additional A source hashes cited by the player push and local unstuck slices: `net/minecraft/client/player/RemotePlayer.java` `b9154c415720ecf330e1aeeb87b6de6f26b03168bc652fbff5ac1cc08ff6be94`; `net/minecraft/world/entity/EntitySelector.java` `0735f67b48018b7fc12737e06085cdf194a138edb75f5f95b83c0a9451be589b`; `net/minecraft/world/level/Level.java` `77cc835fc1a79cd970bc3f4897fe5f398958fcbc874400c297f465f0c7819f01`; `net/minecraft/world/level/CollisionGetter.java` `dddff4897e8d5d01e3ac132d85c474e6a3b9bae928e48b8c907aa9eabdb7fb01`; `net/minecraft/world/level/BlockCollisions.java` `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e`; `net/minecraft/world/entity/player/Abilities.java` `0be56964201d7cae7ff4ba7c296afd568f9a65cd74f8926145c15714f82edae7`.
- Additional A source hash cited by the bed landing finding: `net/minecraft/world/level/block/BedBlock.java` `996e4ca63f4bfaea14b215648f2318d52c49f6c802317381a670d87c0a6d0b03`.
- Additional A source hashes cited by the creative-flight speed input: `net/minecraft/client/MouseHandler.java` `887e11f879c5172c773d35020ea9ae5c98452b184607ec19b684a26c357a60dd`; `net/minecraft/network/protocol/game/ClientboundPlayerAbilitiesPacket.java` `2c13a4dcf511c99ab48b84b204c413835e3ccee3d8ad8b10d5a7bd13133f2c69` (also identical in B).
- Additional A source hash cited by the piston movement slice: `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java` `67bceb946c172c5c022663fda32d123a6bbb85ca4171705ca9dc2c54105ab6e6`.
- Additional A source hashes cited by the post-move block-speed slice: `net/minecraft/world/level/block/Block.java` `1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977`; `net/minecraft/world/level/block/state/BlockBehaviour.java` `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13`.
- Additional A source hashes cited by local input modifier inventory: `net/minecraft/world/item/component/UseEffects.java` `18525e459d066a046a20dce6176b3a9764de09f0d91e79c61c2203709744c8dd`; `net/minecraft/core/component/DataComponents.java` `64c8592c21c83c35dee0d4cc780b273d18ff30c4fea92c0868f296a164b2a11e`; `net/minecraft/world/item/Item.java` `a92a71ef9acd0a98e26914b61382e87f1ba098f779587d452f7373dfa8690ba4`; `net/minecraft/world/item/enchantment/Enchantments.java` `87c4d7799516cf33a0399398f4e7b503dc8ef1ebb8588fa2d5decfc33fa12157`.

### B — 26.2

- Exact release: readiness JSON `ready/26.2/unobfuscated.ready.json`, `versionId=26.2`, status `ready`; source root `build/movement-campaign-2026-10-07/ready/26.2/unobfuscated/`; native `unobfuscated` namespace; original client jar is the decompiler input. Ready JSON reports Vineflower, Java runtime 25, 7,055 sources / 33,813,717 bytes.
- Original client jar `build/movement-campaign-2026-10-07/artifacts/26.2/client.jar`, SHA-256 `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`; rehashed file matches marker and artifact-manifest entry. Version metadata says ID 26.2. Mappings and remapped jar: not applicable, published unobfuscated.
- Provenance `ready/26.2/unobfuscated.provenance.json` records exact command `.\gradlew.bat decompileMinecraft --versions=26.2 --mappings=unobfuscated --decompiler-heap=4G --output-root=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\staging\26.2-unobfuscated-3f17fe9da1bd4a17918a5b39990fcad5 --cache-directory=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts`; toolchain versions match A. The concise success excerpt is persisted as `unobfuscated.success.log`; the full initial stdout was not persisted.
- Source manifest `ready/26.2/unobfuscated.sources.sha256`, SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.
- Artifact manifest `ready/26.2/artifacts.sha256`, SHA-256 `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`.
- Movement-method diagnostics `ready/26.2/movement-diagnostics.txt`, SHA-256 `ba6fd6c5b1c77b3ee2988f0c54968cc5b915680670fdb66699dbcbf8b9fe21d3`. It lists decompiled anchors for Entity.move/moveRelative, LivingEntity travel/jump paths, Player.travel and LocalPlayer.aiStep. The cited file hashes and bodies have been checked on B; method anchors do not establish pair coverage or the integrity of unlisted methods.
- Direct B source hashes, each recomputed and matching the B source manifest: `net/minecraft/client/player/KeyboardInput.java` `b4bbb410650444c30d2a62d70fd3c6cd1aefa104b8a12e474e2a478a62089c39`; `net/minecraft/client/player/LocalPlayer.java` `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; `net/minecraft/world/entity/LivingEntity.java` `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; `net/minecraft/world/entity/Entity.java` `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; `net/minecraft/world/entity/player/Player.java` `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; `net/minecraft/world/entity/player/Abilities.java` `e96537b6f633aa2b16e6ffd7b324c06f75ee0dd15597e7c583db833a6acb1a32`. Add source/resource hashes when further files are cited.
- Additional source hashes cited by the slime landing finding: `net/minecraft/client/player/AbstractClientPlayer.java` `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc`; `net/minecraft/world/entity/Avatar.java` `01a8092bc2637e7d42a261fb564bffcabadd28625cc02b120e943559e2d4fe78`; `net/minecraft/world/level/block/SlimeBlock.java` `e0fc3087b66777a2800676aaeee47a6e98395ea4c964d5a301f3a73b3577c754`; `net/minecraft/world/level/block/Blocks.java` `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435`; `net/minecraft/world/entity/LivingEntity.java::getEffectiveGravity()` is covered by the already listed LivingEntity hash.
- Additional B source hashes cited by the external-impulse review: `net/minecraft/client/multiplayer/ClientPacketListener.java` `9cb0cc8afeba9e4f42f428a52719c645817d03893dc371e6711c7bd16eac1b6`; `net/minecraft/world/entity/ai/attributes/Attributes.java` `4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c`; `net/minecraft/world/entity/ai/attributes/Attribute.java` `854d1c12ab0b49a2136d5807db70676fe041671d40d44140cc2fda7c4ab4db59` (also identical in A).
- Additional B source hashes cited by the player push and local unstuck slices: `net/minecraft/client/player/RemotePlayer.java` `b9154c415720ecf330e1aeeb87b6de6f26b03168bc652fbff5ac1cc08ff6be94`; `net/minecraft/world/entity/EntitySelector.java` `0735f67b48018b7fc12737e06085cdf194a138edb75f5f95b83c0a9451be589b`; `net/minecraft/world/level/Level.java` `1ae4f565ad909f4e90786089b5b4f125e8a7d18e2ef7d8c376172a5787fc5639`; `net/minecraft/world/level/CollisionGetter.java` `8e7d1a54d27e0f187667439539c59b8892f966f1b9c8107eb162c6d43f830f6c`; `net/minecraft/world/level/BlockCollisions.java` `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e`.
- Additional B source hashes cited by the bed landing finding: `net/minecraft/world/level/block/BedBlock.java` `22f515c272d52eebd75456e4a78eb7f38708d682f8d2cb5fdc170e7c3105af2b`; `net/minecraft/tags/BlockTags.java` `9834ecbe2facd79cf3d9fc79babd452ed0e000684029b2bb5e9f90aa4f51529e`.
- Additional B source hash cited by the creative-flight speed input: `net/minecraft/client/MouseHandler.java` `049c4f21b6e1b724e1f0ed8b6784e55eae11c50ccd88c2a483a7253429ef8c15`.
- Additional B source hash cited by the piston movement slice: `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java` `706bab1a13ba99bbd334aad853b99f72df58fce5b6b67d30d41f902b63dd8e06`.
- Additional B source hashes cited by the post-move block-speed slice: `net/minecraft/world/level/block/Block.java` `cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab`; `net/minecraft/world/level/block/state/BlockBehaviour.java` `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e`.
- Additional B source hashes cited by local input modifier inventory: `net/minecraft/world/item/component/UseEffects.java` `18525e459d066a046a20dce6176b3a9764de09f0d91e79c61c2203709744c8dd`; `net/minecraft/core/component/DataComponents.java` `717ec4347940ff05f93116c74ecaac3adbafd3df0f732860464ad259d6b8b0b2`; `net/minecraft/world/item/Item.java` `215fb193bc9fc45702f55a19572ced3f03cc567a851c19050619255b2e59d01d`; `net/minecraft/world/item/enchantment/Enchantments.java` `9af5f89778ad9bd8667953049045027d8426fc692121a6842fe59178b6767ba0`.
- Original client-jar resource inspected at `data/minecraft/tags/block/suppresses_bounce.json`: only `minecraft:honey_block` is listed; entry SHA-256 `a477a87ac4bcb97971cb0b445f4cc9b6b8e02cd31ba3d01bc842b17a6a8477a8`.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: full source-only pair not frozen. Incremental candidate snapshot commit `8eb345d5973158e7e1bc26a2044d63fb77b45e19` contains only `F-26.2-SLIME-LANDING-RESTITUTION`; finding SHA-256 `c14302ffa5b0a773d1e27adbb7f8fbe12188066c80763bdac4a8a02ef0446a05`. Independent review is pending; pair status remains active.
- Evidence inventory and finding IDs included at freeze: full pair not frozen; the incremental snapshot contains `F-26.2-SLIME-LANDING-RESTITUTION` only.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; neither implementation nor wiki/audit output has been opened. No wiki browsing.
- Source/mapping hashes covered by freeze: pending both-side inventory closure.

## Correspondence and call order

Pair-specific comparison is underway. `S-IN-01`, `S-IN-BASE` and the bounded snapshot range are dispositioned; remaining B anchors below are inventory seeds, not guessed A/B equivalence. All other class/member descriptors and complete caller/dependency chains must be checked on both sides.

Initial B-only call-order anchors inspected (all SHA-256 values in B manifest above):

- `KeyboardInput.tick()` samples key presses, creates move impulses and normalizes a `Vec2` (`KeyboardInput.java:14-36`). A tick producer/consumer chain still needs tracing on both sides.
- `LocalPlayer.aiStep()` snapshots jump/shift/forward flags before `input.tick()`, computes crouch, applies auto-jump and unstuck probes, then applies sprint gates, flight and riding-jump work before `super.aiStep()` (`LocalPlayer.java:767-919`).
- `LivingEntity.aiStep()` applies a player-specific horizontal velocity cutoff at `3060-3083`, input and jump handling at `3084-3123`, travel dispatch at `3125-3141`, block effects at `3143-3145`, and client animation at `3147-3149`. The nearby non-player per-axis cutoff (`3069-3076`) is out of movement-emulation scope but its branch guard is recorded so it is not confused with the player path.
- `LivingEntity.travel()` dispatches fluid, fall-flying, or air travel (`2430-2437`); `Player.travel()` adds swimming and ability-flight handling (`Player.java:1402-1429`). Exact callees and post-travel work remain to inventory and compare.
- `Entity.move()` performs edge adjustment and collision, movement/position and collision flags, fall damage check, collision restitution, movement emission, then block-speed velocity scaling (`Entity.java:711-800`); its collision resolver and shape dependencies must be indexed separately.

## Required source inventories

Each inventory maps to bounded slices below; all are pending. Add all newly discovered writers, consumers, overrides, registrations and resources before disposition.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=S-TICK-ENTRY,S-IN-01,S-IN-BASE,S-LOCAL-SNAPSHOT,S-LOCAL-UNSTUCK,S-SPRINT-START,S-SPRINT-STOP,S-FLIGHT-TOGGLE,S-FALLFLY-REQUEST,S-SINK-INPUT,S-FLIGHT-VERTICAL-INPUT,S-RIDEABLE-JUMP,S-LOCAL-INPUT-MODIFIERS,S-SQUARE-MOVE,S-VELOCITY-HORIZONTAL,S-VELOCITY-VERTICAL,S-JUMP-GATE,S-JUMP-IMPULSE,S-JUMP-LIQUID,S-LIVING-GLIDE-UPDATE,S-TRAVEL-PREP,S-FALL-DISTANCE-RESET,S-TRAVEL-GATE,S-POST-BLOCK-EFFECTS,S-POST-ANIMATION,S-TRAVEL-DISPATCH,S-TRAVEL-FLYING,S-TRAVEL-AIR,S-TRAVEL-RELATIVE,S-TRAVEL-FRICTION-SPEED,S-TRAVEL-FLUID-DISPATCH,S-TRAVEL-WATER,S-TRAVEL-LAVA,S-TRAVEL-FLUID-EXIT,S-TRAVEL-FLUID-ADJUST,S-TRAVEL-GLIDE-DISPATCH,S-TRAVEL-GLIDE-FORMULA,S-TRAVEL-CLIMB,S-PLAYER-01,S-POSE-UPDATE,S-PLAYER-AISTEP-PRE,S-PLAYER-AISTEP-POST; evidence=A/B dispositions in S-IN-01,S-IN-BASE,S-LOCAL-SNAPSHOT,S-SQUARE-MOVE,S-TICK-ENTRY; `Player.aiStep` pre/post body slices are compared, with inventory and attribute dependencies still open.
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=S-TICK-ENTRY,S-IN-BASE,S-LOCAL-SNAPSHOT,S-LOCAL-UNSTUCK,S-SPRINT-START,S-SPRINT-STOP,S-FLIGHT-TOGGLE,S-FALLFLY-REQUEST,S-SINK-INPUT,S-FLIGHT-VERTICAL-INPUT,S-RIDEABLE-JUMP,S-LOCAL-INPUT-MODIFIERS,S-SQUARE-MOVE,S-VELOCITY-HORIZONTAL,S-VELOCITY-VERTICAL,S-POSE-UPDATE,S-DIMENSIONS,S-PLAYER-AISTEP-PRE,S-PLAYER-AISTEP-POST,S-PLAYER-IMPULSE-RESPONSE,S-MOVE-RESTITUTE-SLIME,S-EXT-ENTITY-MOTION-PACKET,S-EXT-PLAYER-CORRECTIONS,S-EXT-PISTON-MOVEMENT; evidence=A/B ClientInput defaults plus pending state writer/consumer inventory; Player.aiStep timer/reset and flight impulse remain open, while direct knockback, entity-push response, and paired external correction/piston producer slices are recorded.
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=S-MOVE-POS,S-MOVE-FLAGS,S-MOVE-RESTITUTE,S-MOVE-RESTITUTE-SLIME,S-MOVE-RESTITUTE-BED,S-MOVE-BLOCK-SPEED,S-EDGE-PROBE,S-COLLISION-STEP,S-COLLISION-QUERY,S-COLLISION-AXIS,S-WORLD-01,S-EXT-PISTON-MOVEMENT; evidence=A/B Entity.move restitution, piston displacement caller and slime/bed callback/property paths are source-confirmed for bounded slices; remaining provider/registry/neighbor inventory is open.
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=S-MOVE-RESTITUTE-SLIME,S-MOVE-RESTITUTE-BED,S-WORLD-01,S-EXT-PISTON-MOVEMENT; evidence=A/B slime and bed registrations, B suppression-tag resource and paired piston movement call path are recorded; full block/fluid registry and resource inventory remains open.
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=S-SPRINT-START,S-SPRINT-STOP,S-LOCAL-INPUT-MODIFIERS,S-JUMP-GATE,S-JUMP-IMPULSE,S-TRAVEL-DISPATCH,S-TRAVEL-FLYING,S-TRAVEL-AIR,S-TRAVEL-RELATIVE,S-TRAVEL-FRICTION-SPEED,S-TRAVEL-FLUID-DISPATCH,S-TRAVEL-WATER,S-TRAVEL-LAVA,S-TRAVEL-FLUID-EXIT,S-TRAVEL-FLUID-ADJUST,S-TRAVEL-GLIDE-DISPATCH,S-TRAVEL-GLIDE-FORMULA,S-TRAVEL-CLIMB,S-MOD-01,S-PLAYER-AISTEP-POST,S-PLAYER-IMPULSE-RESPONSE,S-MOVE-RESTITUTE-BED; evidence=B movement consumers and the new BOUNCINESS attribute producer are recorded; broader producer/registration/resource inventory remains open.
- `INV-EXTERNAL` player-only externally supplied movement and direct player velocity/impulse/knockback application, plus in-scope player-facing transitions: status=pending; slice_ids=S-FALLFLY-REQUEST,S-EXT-01,S-EXT-ENTITY-MOTION-PACKET,S-EXT-PLAYER-CORRECTIONS,S-PLAYER-IMPULSE-RESPONSE; evidence=paired entity-motion packet consumer, player correction/rotation consumers, and direct knockback/entity-push response slices are compared; remaining external writer/consumer mapping remains open; exclude non-player and vehicle physics.
- `INV-EXCLUSIONS` explicit scope audit for health/food state production, attack/damage resolution, non-player movement and vehicle physics. Direct player-motion response remains in scope even if combat triggers it: status=pending; slice_ids=S-RIDEABLE-JUMP,S-CLIENT-AVATAR-STATE; evidence=campaign scope above, the vehicle-jump source range and the client-avatar consumer search; validate direct vanilla-state reads without emulating their producer systems.

## Coverage ledger

These are now 61 bounded work units, not an exhaustive inventory: 35 pending, 9 in-progress, 13 compared-no-difference, 2 findings, and 2 not-applicable. Pair comparison has started now that A is ready. B-only anchors are navigation evidence; do not infer equivalence or absence. Expand the ledger during the pair-specific inventory.

### Slice S-IN-01: Keyboard input sampling and movement-vector construction

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: `KeyboardInput.calculateImpulse` and `KeyboardInput.tick`, key-state sampling, forward/side impulse signs and vector normalization; the LocalPlayer input-tick call edge was also checked.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/KeyboardInput.java::calculateImpulse(boolean,boolean),tick(), lines 14-36`, SHA-256 `b4bbb410650444c30d2a62d70fd3c6cd1aefa104b8a12e474e2a478a62089c39`; the recomputed full-file hash equals B. `Vec2.normalized(),length(),lengthSquared()` and `Mth.sqrt(float)` were read at `Vec2.java:9-25,48-59` and `Mth.java:57-59`, hashes `c52274a57d57b452c3ec349b6336341e7069252183a10838ee7e9e4462f6ba3d` and `19d00e9745368c34692d0dfd7eddec07797790ac7f6e0bc579208b7a4b682d33`; the branch, `1.0E-4F` threshold, square-root input and divide operations match B. Both KeyboardInput methods were read: positive/negative equality yields `0.0F`, otherwise sign is `1.0F` or `-1.0F`; tick samples the same seven key states, constructs `Input`, derives forward/side impulses and normalizes the same `Vec2` in both endpoints. The sole LocalPlayer `input.tick()` call is at line 786 in both A and B, after the same pre-sampling flags and crouch calculation; caller body remainder is tracked separately in S-LOCAL-SNAPSHOT.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/KeyboardInput.java::net.minecraft.client.player.KeyboardInput#calculateImpulse(boolean,boolean),tick(), lines 14-36`, SHA-256 `b4bbb410650444c30d2a62d70fd3c6cd1aefa104b8a12e474e2a478a62089c39`; `Vec2.normalized(),length(),lengthSquared()` and `Mth.sqrt(float)` at `Vec2.java:9-25,57-68` and `Mth.java:58-60`, hashes `284ba455cf02047283a01af3d4c77929cfd9bbcc772b0677de503a3774bd4b51` and `30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17`; relevant helper bodies match A.
- State producers/writers -> consumers/readers: options key states -> `Input` keypress snapshot and `moveVector` -> LocalPlayer's single input-tick call and input consumers; the matching call edge is at `LocalPlayer.aiStep():786` in both endpoints. Other consumers remain in their own slices.
- Parent slices / dependencies / closure evidence: D-A-READY resolved; `S-LOCAL-SNAPSHOT` tracks the rest of the caller body and pre-sampling order; input application is `S-LOCAL-INPUT-MODIFIERS`.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the two exact KeyboardInput source files have identical verified SHA-256 hashes, the required method bodies were read, and the single reachable LocalPlayer caller and its relevant preceding order were checked at both endpoints. This disposition is limited to key sampling and movement-vector construction; it does not disposition downstream consumers or other input providers.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-IN-BASE: Default client input state and fallback consumers

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.input` default initialization and `ClientInput.tick`, `getMoveVector`, `hasForwardImpulse`, and `makeJump`; this is the neutral fallback before keyboard input assignment and covers the auto-jump writer used by `S-LOCAL-SNAPSHOT`.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/ClientInput.java::tick(),getMoveVector(),hasForwardImpulse(),makeJump(), fields, lines 6-32`, SHA-256 `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`; default `input` field in `LocalPlayer.java:134`; `ClientPacketListener.handleLogin` assigns `KeyboardInput` at line 535 and `handleRespawn` assigns it at line 1330. The A listener full-file SHA-256 is `eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/ClientInput.java::tick(),getMoveVector(),hasForwardImpulse(),makeJump(), fields, lines 6-32`, SHA-256 `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`; default `input` field in `LocalPlayer.java:134`; `ClientPacketListener.handleLogin` assigns `KeyboardInput` at line 535 and `handleRespawn` assigns it at line 1328. The B listener full-file SHA-256 is `9cb0cc8afebae9f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6`.
- State producers/writers -> consumers/readers: default empty `Input` and zero `moveVector` -> no-op tick/readers; `makeJump()` rewrites only jump while retaining other flags. Login/respawn replace the base object with KeyboardInput before active local-player ticks.
- Parent slices / dependencies / closure evidence: `S-IN-01`, `S-LOCAL-SNAPSHOT`, `S-TICK-ENTRY`; field initialization and both reachable assignment sites were checked on both endpoints. Pair tick call graph remains pending in `S-TICK-ENTRY`.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): both exact `ClientInput.java` files have the same full-file hash and the same methods/state defaults. The local player defaults to this class and the two observed login/respawn replacement sites use the same subclass assignments; only the independent tick-chain closure remains open.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-LOCAL-SNAPSHOT: Input snapshot, crouch selection and auto-jump ordering

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `LocalPlayer.aiStep` captures previous jump/shift/forward state before crouch selection and `input.tick`, then applies auto-jump state; lines 777-793 in both endpoints.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(), lines 777-793`, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`. Extracted full `aiStep()` bodies are equal except for a portal/loading-screen GUI accessor at line 772, outside this slice; the exact covered range 777-793 is identical in A and B.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(), lines 777-793`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- State producers/writers -> consumers/readers: prior/current input flags -> crouch, input snapshot, auto-jump timer and synthesized jump input -> sprint/jump and travel consumers.
- Parent slices / dependencies / closure evidence: S-IN-01,S-TICK-ENTRY; S-POSE-UPDATE tracks later pose-fit selection. The A/B range confirms pose-fit predicates gate crouching and input is sampled after the three saved previous-state values.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): exact covered lines 777-793 match on A and B, including pre-sampling values, crouch guards, `input.tick()`, tutorial notification and auto-jump timer/synthetic-jump order. This is bounded to this range; the separate portal GUI gate before it is outside movement-snapshot behavior and the rest of `aiStep` remains open in other slices.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-LOCAL-UNSTUCK: Local player corner probes before movement

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: when `noPhysics` is false, both endpoints call `moveTowardsClosestSpace` at four ordered X/Z corners using `0.35 * bounding-box width`. The helper tests the player's AABB against the containing cell, scans WEST/EAST/NORTH/SOUTH in order, chooses the smallest open-edge distance, and overwrites only the matching horizontal velocity with `0.1 * step`.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(),moveTowardsClosestSpace(double,double),suffocatesAt(BlockPos), lines 795-800,451-484`, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; `CollisionGetter.collidesWithSuffocatingBlock(Entity,AABB), lines 126-135`, SHA-256 `dddff4897e8d5d01e3ac132d85c474e6a3b9bae928e48b8c907aa9eabdb7fb01`; `BlockCollisions`, SHA-256 `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(),moveTowardsClosestSpace(double,double),suffocatesAt(BlockPos), lines 795-800,451-484`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; `CollisionGetter.collidesWithSuffocatingBlock(Entity,AABB), lines 126-135`, SHA-256 `8e7d1a54d27e0f187667439539c59b8892f966f1b9c8107eb162c6d43f830f6c`; `BlockCollisions`, SHA-256 `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e`.
- State producers/writers -> consumers/readers: pose-dependent width, AABB, position and `noPhysics` -> four probe coordinates -> suffocation query -> closest-space horizontal velocity write. The query delegates to the same `BlockCollisions` code at both endpoints; per-block suffocation properties and block/resource registrations are in `S-WORLD-01`.
- Parent slices / dependencies / closure evidence: S-LOCAL-SNAPSHOT,S-DIMENSIONS,S-COLLISION-QUERY,S-WORLD-01; paired helper/query bodies and operation order match, but the movement-provider inventory for suffocating block states remains open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): A/B direct probe order, axis selection, comparison boundary, constants and velocity writes match. The source-level collision query also matches; block-state and registration dependencies must close before this slice can be terminal.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-SPRINT-START: Sprint reset, start predicate and trigger timer

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: both endpoints clear the trigger timer for shift, item slowdown or backward; check the same sprint predicate; set sprint on a double-tap window or sprint key. The predicate checks mobility restriction, passenger vehicle capability versus food/flight permission, shallow water, item-use `canSprint`, fall flying and slow movement; lines 802-818,1135-1148.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(),isSprintingPossible(boolean),canStartSprinting(),vehicleCanSprint(Entity), lines 802-818,1135-1152`, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; `Player.hasEnoughFoodToDoExhaustiveManoeuvres(),isMobilityRestricted(), lines 1571-1573,1943-1945`, SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`; `LivingEntity.setSprinting(boolean), lines 2280-2287`, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(),isSprintingPossible(boolean),canStartSprinting(),vehicleCanSprint(Entity), lines 802-818,1135-1152`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; `Player.hasEnoughFoodToDoExhaustiveManoeuvres(),isMobilityRestricted(), lines 1592-1594,1964-1966`, SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; `LivingEntity.setSprinting(boolean), lines 2317-2324`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: input/shift/backward/item-use/fall-flight/crouch/crawl, sprint timer, food/mayfly, mobility-effect, shallow-water and passenger-vehicle state -> sprint flag and trigger timer -> movement-speed modifier and travel. Read vanilla food/effect state as inputs; do not emulate their producers.
- Parent slices / dependencies / closure evidence: S-LOCAL-SNAPSHOT,S-LOCAL-INPUT-MODIFIERS,S-MOD-01; the paired caller/predicates, item-use component values, and `LivingEntity.setSprinting` path are compared. Its sprint modifier is the same `0.3F` `ADD_MULTIPLIED_TOTAL` modifier on `MOVEMENT_SPEED` in both endpoints; excluded food producers are read only as state predicates.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for equal input flags, item `UseEffects`, ability/food/mobility state, fluid/vehicle state and current attributes, the branch order, trigger timer operations, predicate expressions and sprint flag writes match. The speed modifier writer removes the same ID and conditionally adds the same transient `0.3F` total multiplier. Other movement modifiers and their producers remain covered by open `S-MOD-01`; they do not change this bounded sprint transition under equivalent inputs.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-SPRINT-STOP: Run/swim sprint stopping predicates

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: after sprint-start evaluation, both endpoints select the swim or run stop predicate using the same order and conditions: sprint eligibility, water state, forward input, ground/shift and major horizontal collision; `aiStep()` lines 820-828, helpers 921-929.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(),shouldStopRunSprinting(),shouldStopSwimSprinting(), lines 820-828,921-929`, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; `Entity.isInShallowWater(), lines 1530-1532`, SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(),shouldStopRunSprinting(),shouldStopSwimSprinting(), lines 820-828,921-929`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; `Entity.isInShallowWater(), lines 1617-1619`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: water/forward/ground/shift/collision flags and sprint ability -> sprint flag -> travel. Inputs are read as vanilla state; collision and fluid state producers remain tracked by their movement inventories.
- Parent slices / dependencies / closure evidence: S-SPRINT-START,S-LOCAL-SNAPSHOT,S-MOVE-FLAGS,S-TRAVEL-WATER; paired stop predicates match, with state-writer and fluid/collision inventories still open at pair level.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): under equivalent sprint flag, water/forward input, ground/shift state, ability/item/food state and collision flags, the call order and direct run/swim stop conditions match. The predicates consume those values but do not produce them; their broader collision/fluid producers remain independently open.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-FLIGHT-TOGGLE: Creative/spectator flight toggle and launch jump

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `mayfly` and spectator checks, jump-edge/timer logic, vehicle/swimming guard, ability flip, possible ground jump impulse, ability sync and timer reset; lines 830-852.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(), lines 830-852`, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; paired branch order, timer value, ability toggle, launch-jump gate and sync calls match B.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(), lines 830-852`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- State producers/writers -> consumers/readers: jump edge, ability flags, spectator/swimming/vehicle/on-ground -> flying state and possible jump impulse -> player travel.
- Parent slices / dependencies / closure evidence: S-LOCAL-SNAPSHOT,S-JUMP-IMPULSE,S-PLAYER-01,S-FLIGHT-VERTICAL-INPUT; A/B ability fields/defaults, local toggle writes, `onUpdateAbilities` sender and `ClientPacketListener.handlePlayerAbilities` receiver were paired. The distinct ground jump impulse and surrounding jump/travel dependencies remain open in S-JUMP-IMPULSE and S-PLAYER-01.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): A/B toggle and ability packet synchronization code match; `Abilities.Packed` codec helpers differ but keep the same defaults for movement fields. The direct jump impulse and its movement consumers remain open, so this slice stays in-progress.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-FALLFLY-REQUEST: Local jump edge requests fall-flying

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: B checks jump edge, not a flight toggle, not climbable, and `tryToStartFallFlying`, then sends start-fall-flying command; it records current fall-flying state immediately afterward; lines 854-858.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(), lines 854-858`, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; `Player.tryToStartFallFlying(),startFallFlying(), lines 1442-1453`, SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(), lines 854-858`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; `Player.tryToStartFallFlying(),startFallFlying(), lines 1463-1474`, SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.
- State producers/writers -> consumers/readers: previous/current jump state and glide eligibility -> packet -> externally supplied fall-flight state -> travel branch.
- Parent slices / dependencies / closure evidence: S-LOCAL-SNAPSHOT,S-TRAVEL-GLIDE-DISPATCH,S-EXT-01; eligibility, packet handler and server-supplied boundary pending.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): A/B jump-edge/climbable/flight-toggle guards, eligibility call, command action and immediate state snapshot match. `canGlide` equipment/effect dependencies and the server-supplied state/packet path remain open.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-SINK-INPUT: Local shift input adds downward water velocity

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `isInWater && shift && isAffectedByFluids` invokes `goDownInWater`; lines 859-861. Compare call order to input tick and superclass aiStep.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(), lines 859-861`, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; `LivingEntity.goDownInWater(), lines 2366-2368`, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`; `Player.isAffectedByFluids(), lines 875-877`, SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(), lines 859-861`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; `LivingEntity.goDownInWater(), lines 2403-2405`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; `Player.isAffectedByFluids(), lines 875-877`, SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.
- State producers/writers -> consumers/readers: shift/current fluid state -> fixed Y impulse -> fluid travel.
- Parent slices / dependencies / closure evidence: S-LOCAL-SNAPSHOT,S-JUMP-LIQUID,S-TRAVEL-WATER; A/B gate and helper bodies match, including the player fluid-affected predicate `!abilities.flying`.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for equivalent water/shift/fluid-affected state, both versions add exactly `(0.0, -0.04F, 0.0)` to the current velocity at the same pre-super point. This closes only the local sink impulse, not fluid travel or fluid-state production.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-FLIGHT-VERTICAL-INPUT: Creative flight key input adds vertical velocity

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: when flying and camera-controlled, A/B map shift/jump to integer input and add `inputYa * abilities.getFlyingSpeed() * 3.0F` to Y velocity; `LocalPlayer.aiStep(), lines 871-884`. The `flyingSpeed` field is initialized to `0.05F`; the client ability packet handler and spectator scroll path are the direct runtime writers found in the paired source trees.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(), lines 871-884`, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; `Abilities` default/getter/setter, lines 13-28, SHA-256 `0be56964201d7cae7ff4ba7c296afd568f9a65cd74f8926145c15714f82edae7`; `ClientPacketListener.handlePlayerAbilities`, lines 2087-2095, SHA-256 `eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82`; `MouseHandler.onScroll`, lines 191-216, SHA-256 `887e11f879c5172c773d35020ea9ae5c98452b184607ec19b684a26c357a60dd`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(), lines 871-884`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; `Abilities` default/getter/setter, lines 14-29, SHA-256 `e96537b6f633aa2b16e6ffd7b324c06f75ee0dd15597e7c583db833a6acb1a32`; `ClientPacketListener.handlePlayerAbilities`, lines 2089-2097, SHA-256 `9cb0cc8afeba9e4f42f428a52719c645817d03893dc371e6711c7bd16eac1b6`; `MouseHandler.onScroll`, lines 186-211, SHA-256 `049c4f21b6e1b724e1f0ed8b6784e55eae11c50ccd88c2a483a7253429ef8c15`; `ClientboundPlayerAbilitiesPacket` encode/decode fields, lines 9-67, SHA-256 `2c13a4dcf511c99ab48b84b204c413835e3ccee3d8ad8b10d5a7bd13133f2c69` (identical in A).
- State producers/writers -> consumers/readers: external ability packet or spectator scroll -> flying speed field -> guarded LocalPlayer shift/jump vertical input -> player travel. Client packet constructor/decoder/handler and spectator scroll update the same fields under equivalent values. `Abilities.Packed` codec spelling changes in B, while both serialized defaults remain `flySpeed=0.05F` and `walkSpeed=0.1F`; this codec representation is not the per-tick input path.
- Parent slices / dependencies / closure evidence: S-FLIGHT-TOGGLE,S-PLAYER-01,S-TRAVEL-FLYING; the direct input, field getter/default, packet input and spectator modifier paths are paired. The velocity formula consumes the same effective field and the same shift/jump signs and float multiplication order.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for equivalent flying state, camera-control state, shift/jump keys and current flying speed, both endpoints perform the same ordered multiplication and Y-velocity addition. The client packet writers assign the incoming speed identically; spectator scroll computes `clamp(current + wheelY * 0.005F, 0.0F, 0.2F)` in both versions. The `Abilities.Packed` codec's optional-field helper changes, but its movement-speed defaults remain the same and no different per-tick input behavior is established by that representation change.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-PLAYER-IMPULSE-RESPONSE: Player knockback and entity-push velocity application

- Inventory ID(s): INV-STATE, INV-MODIFIERS, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: inherited `LivingEntity.knockback` scales power by knockback resistance, handles near-zero horizontal direction, normalizes/scales the impulse and writes player delta movement; `LivingEntity.aiStep` calls `pushEntities` after travel, which queries nearby pushable entities and calls `doPush(entity) -> entity.push(this)`. That route reaches `Entity.push(Entity)` and its finite-checked `push(double,double,double)` accumulation on the LocalPlayer when the other entity pushes the player. Damage resolution and non-player trajectories are outside this bounded slice.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::knockback(double,double,double),push(Entity),aiStep,pushEntities,doPush,isPushable, lines 1612-1630,2304-2308,3100-3107,3165-3189,3214-3216,3310-3312`, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`; `Entity.java::push(Entity),push(Vec3),push(double,double,double), lines 1789-1831`, SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`; `Level.java::getEntities(Entity,AABB,Predicate),getPushableEntities(Entity,AABB), lines 769-785,852-854`, SHA-256 `77cc835fc1a79cd970bc3f4897fe5f398958fcbc874400c297f465f0c7819f01`; `EntitySelector.java::pushableBy(Entity), lines 29-55`, SHA-256 `0735f67b48018b7fc12737e06085cdf194a138edb75f5f95b83c0a9451be589b`; `LocalPlayer.aiStep()` delegates to `super.aiStep()` at line 914; `RemotePlayer.tick()` calls `pushEntities()` at lines 64-69. `LocalPlayer.java` SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; `RemotePlayer.java` SHA-256 `b9154c415720ecf330e1aee b87b6de6f26b03168bc652fbff5ac1cc08ff6be94`.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::knockback(double,double,double),push(Entity),aiStep,pushEntities,doPush,isPushable, lines 1612-1630,2304-2308,3100-3107,3165-3189,3214-3216,3310-3312`, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`; `Entity.java::push(Entity),push(Vec3),push(double,double,double), lines 1789-1831`, SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`; `Level.java::getEntities(Entity,AABB,Predicate),getPushableEntities(Entity,AABB), lines 769-785,852-854`, SHA-256 `77cc835fc1a79cd970bc3f4897fe5f398958fcbc874400c297f465f0c7819f01`; `EntitySelector.java::pushableBy(Entity), lines 29-55`, SHA-256 `0735f67b48018b7fc12737e06085cdf194a138edb75f5f95b83c0a9451be589b`; `LocalPlayer.aiStep()` delegates to `super.aiStep()` at line 914; `RemotePlayer.tick()` calls `pushEntities()` at lines 64-69. `LocalPlayer.java` SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; `RemotePlayer.java` SHA-256 `b9154c415720ecf330e1aeeb87b6de6f26b03168bc652fbff5ac1cc08ff6be94`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::knockback(double,double,double,DamageSource,float,boolean),knockback(double,double,double,DamageSource,float),push(Entity),aiStep,pushEntities,doPush,isPushable, lines 1641-1663,2341-2345,3167-3174,3232-3255,3281-3283,3376-3378`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; `Entity.java::push(Entity),push(Vec3),push(double,double,double), lines 1877-1918`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; `Level.java::getEntities(Entity,AABB,Predicate),getPushableEntities(Entity,AABB), lines 774-790,857-859`, SHA-256 `1ae4f565ad909f4e90786089b5b4f125e8a7d18e2ef7d8c376172a5787fc5639`; `EntitySelector.java::pushableBy(Entity), lines 29-55`, SHA-256 `0735f67b48018b7fc12737e06085cdf194a138edb75f5f95b83c0a9451be589b`; `LocalPlayer.aiStep()` delegates to `super.aiStep()` at line 914; `RemotePlayer.tick()` calls `pushEntities()` at lines 64-69. `LocalPlayer.java` SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; `RemotePlayer.java` SHA-256 `b9154c415720ecf330e1aeeb87b6de6f26b03168bc652fbff5ac1cc08ff6be94`.
- State producers/writers -> consumers/readers: `LivingEntity.aiStep` reaches push response after travel for LocalPlayer; `RemotePlayer.tick` supplies a second client-side caller that can select the local player. The paired query and selector use the pusher box, candidate `isPushable`, client/local-player identity, spectator and team collision rules. `LivingEntity.isPushable` requires alive, non-spectator and not on a climbable block; `Entity.push(Entity)` excludes same-vehicle and no-physics pairs, then computes the same horizontal direction/normalization/clamp/`0.05F` factor and conditional writes on both entities. `LivingEntity.push(Entity)` checks whether the receiver is sleeping. `Entity.push(Vec3)` and its scalar overload reject non-finite input, add to current velocity, and set `needsSync`. These guards and arithmetic match A/B. Neighbor position/velocity and world entity selection are input state; their non-player producers are outside scope. A/B knockback uses the same resistance scaling, near-zero fallback, normalization, operation order, grounded Y cap, and writes. B's source/damage overload parameters are unused. `KNOCKBACK_RESISTANCE` has a different server-side permitted minimum (A `0.0`, B `-2.0`) but is not synchronized to clients; damage production remains excluded, and the packet consumer is separately tracked.
- Parent slices / dependencies / closure evidence: S-TICK-ENTRY,S-MOVE-POS,S-COLLISION-QUERY,S-EXT-ENTITY-MOTION-PACKET,S-VELOCITY-HORIZONTAL; the exact LocalPlayer/RemotePlayer push callers, query, selector, player pushability predicate, receiver guard and velocity application were compared across A/B and their cited source hashes match the ready manifests. Other correction paths remain in S-EXT-01. Broader state, collision-provider and external-influence inventories remain open at pair level.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for equivalent player position/velocity, grounded/alive/spectator/climbable/passenger/team/no-physics state, candidate entity state and input impulse, the direct player knockback and entity-push response bodies have no source-level difference across these endpoints. The LocalPlayer superclass path and the client-side RemotePlayer caller are reachable; this disposition makes no claim for damage resolution, non-player trajectories, vehicle physics, or other open correction channels.
- Finding IDs or checked absence/replacement path: no client-side movement finding from the server-only knockback-resistance range change; the client `ClientboundSetEntityMotionPacket` replacement/consumer path is in `S-EXT-ENTITY-MOTION-PACKET`.

### Slice S-RIDEABLE-JUMP: Local charge/release jump for jumpable vehicles

- Inventory ID(s): INV-TICK, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: consumes a `PlayerRideableJumping` controlled vehicle's cooldown and held/released jump edges; charges rider jump scale, invokes the vehicle callback and sends `START_RIDING_JUMP`; lines 886-913, with the callback type predicate and packet sender checked.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(),jumpableVehicle(),sendRidingJump(), lines 886-913,600-604,392-398`, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::aiStep(),jumpableVehicle(),sendRidingJump(), lines 886-913,600-604,392-398`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- State producers/writers -> consumers/readers: riding jump key edges -> local charge timer/scale -> controlled vehicle `onPlayerJump` callback and outbound riding-jump packet; this path does not write player delta movement or invoke player `move`.
- Parent slices / dependencies / closure evidence: `INV-EXCLUSIONS`; `jumpableVehicle()` requires the controlled vehicle implement `PlayerRideableJumping` and `canJump()`, and the packet action is `START_RIDING_JUMP`. Vehicle physics is explicitly out of scope.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): both endpoint source paths match, and the reachable operation supplies jump control to a vehicle while only advancing local charge bookkeeping; the actual motion target is the vehicle. Parent campaign scope excludes vehicle physics, so this slice has no in-scope player-motion result. The vehicle callback implementation is not traversed.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-LOCAL-INPUT-MODIFIERS: Local input slowdown, sneak speed and base fallback

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: virtual `applyInput` camera guard; `modifyInput` applies initial 0.98F scale, item-use multiplier when not passenger, sneaking-speed attribute while moving slowly, then square-movement helper; non-camera fallback delegates to LivingEntity's 0.98F X/Z scaling; LocalPlayer lines 692-723, LivingEntity A line 3113.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::applyInput(),modifyInput(Vec2),itemUseSpeedMultiplier(),isMovingSlowly(),isControlledCamera(), lines 692-723,567-569,687-689,744-746`, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; inherited `LivingEntity.applyInput(), lines 3113-3116`, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`; `UseEffects` default/codec and `DataComponents.USE_EFFECTS` registration/default at `UseEffects.java:9-28`, `DataComponents.java:119-121,420-421`; the only Java item override found in `Item.java:542-543` uses `UseEffects(true,false,1.0F)`. `SNEAKING_SPEED` registration is default `0.3`, range `0.0..1.0`, syncable at `Attributes.java:79-81`; Player default attributes include it at `Player.java:208-218`; Swift Sneak adds `LevelBasedValue.perLevel(0.15F)` at `Enchantments.java:579-587`. Source hashes are in the A artifact manifest. The A client jar contains no `data/minecraft/items/*.json` entries.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::applyInput(),modifyInput(Vec2),itemUseSpeedMultiplier(),isMovingSlowly(),isControlledCamera(), lines 692-723,567-569,687-689,744-746`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; `LivingEntity.applyInput(), lines 3180-3183`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; matching component/default and item values are at `UseEffects.java:9-28`, `DataComponents.java:120-122,424-425`, `Item.java:543-544`; matching `SNEAKING_SPEED` registration, Player default and Swift Sneak modifier are at `Attributes.java:92-94`, `Player.java:209-219`, `Enchantments.java:580-588`. Source hashes are in the B artifact manifest. The B client jar contains no `data/minecraft/items/*.json` entries.
- State producers/writers -> consumers/readers: keyboard vector and camera/using-item/passenger/sneaking state plus item `UseEffects` and `SNEAKING_SPEED` values -> modified input vector or fallback X/Z fields -> travel. A/B direct scale order is `0.98F`, item `speedMultiplier` when using an item outside a vehicle, float-cast sneaking attribute when crouching/crawling, then square-movement adjustment; component default, item override, attribute default and Swift Sneak modifier are the same.
- Parent slices / dependencies / closure evidence: S-IN-01,S-TICK-ENTRY,S-SQUARE-MOVE,S-LOCAL-SNAPSHOT,S-POSE-UPDATE,S-MOD-01; item/component/attribute code and vanilla defaults match, but crouch/crawl state production and the complete movement-modifier inventory remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): A/B direct methods, fallback and the identified vanilla component/attribute sources match. This slice remains in-progress because the crouch/crawl state writers and full movement-modifier consumer inventory remain open; direct vanilla-state reads remain inputs and excluded health/food producers are not emulated.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-SQUARE-MOVE: Square-movement vector cap and direction reconstruction

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: helper returns zero input unchanged; otherwise normalizes direction, computes distance to unit square using branch-selected ratio, caps modified length at 1.0F and rescales; `distanceToUnitSquare`; compare `LocalPlayer.java:725-742` plus relevant Vec2/Mth methods.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::modifyInputSpeedForSquareMovement(Vec2),distanceToUnitSquare(Vec2), lines 725-742`, SHA-256 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; helper bodies match B. Relevant Vec2 `scale(),length(),lengthSquared()` lines 28-30,53-59 and Mth `sqrt(float),square(float)` lines 57-59,616-618 match B; file hashes are recorded in `S-IN-01` and checked against both source manifests.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java::modifyInputSpeedForSquareMovement(Vec2),distanceToUnitSquare(Vec2), lines 725-742`, SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- State producers/writers -> consumers/readers: modified input vector -> direction/length branch and arithmetic -> travel input.
- Parent slices / dependencies / closure evidence: S-IN-01,S-LOCAL-INPUT-MODIFIERS; the matching `modifyInput` call at LocalPlayer.java:722 was read; helper math dependencies Vec2 and Mth are compared above.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): exact A/B code for `modifyInputSpeedForSquareMovement` and `distanceToUnitSquare` matches at lines 725-742, including zero guard, float division, branch ratio, `Mth.sqrt(1.0F + Mth.square(tan))`, `Math.min` cap and direction rescale. Relevant Vec2 and Mth helper bodies were also compared, so this disposition includes the helper operation order, not merely identical caller text.
- Finding IDs or checked absence/replacement path: none yet.
### Slice S-VELOCITY-HORIZONTAL: Player-specific pre-input horizontal dead-zone

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B snapshots delta movement, enters the `EntityTypes.PLAYER` branch, compares `horizontalDistanceSqr()` against `9.0E-6`, and zeroes X/Z together; keep the non-player per-axis branch as scope context, not movement behavior; lines 3060-3077.
- A evidence: pending exact method and player-type guard correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(), lines 3060-3077`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: prior tick/network/external X/Z velocity -> horizontal threshold and player guard -> rewritten delta -> input/jump/travel.
- Parent slices / dependencies / closure evidence: S-TICK-ENTRY,S-EXT-01; all player velocity writers and subsequent consumers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B exact branch read; threshold and operation-order comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-VELOCITY-VERTICAL: Pre-input vertical velocity dead-zone

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B independently tests `Math.abs(movement.y) < 0.003` and rewrites Y to zero before `setDeltaMovement`; lines 3079-3083.
- A evidence: pending exact method and writer/caller correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(), lines 3079-3083`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: prior vertical velocity -> strict threshold -> rewritten velocity -> jump/travel branch.
- Parent slices / dependencies / closure evidence: S-VELOCITY-HORIZONTAL,S-EXT-01; A/B direct velocity writer/packet inventory pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B comparison threshold read; no A result.
- Finding IDs or checked absence/replacement path: none yet.
### Slice S-JUMP-GATE: Player jump edge, fluid-depth gates and delay

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B jump handling requires `jumping && isAffectedByFluids`; selects fluid height, compares with threshold, checks ground/shallow-fluid state and `noJumpDelay`, dispatches ground or liquid impulse, and clears delay otherwise; lines 3098-3123.
- A evidence: pending exact caller/method and fluid-state correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(), lines 3098-3123`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: input jump state, fluid contact/height, on-ground state and delay -> ground/liquid jump helper and cooldown -> travel.
- Parent slices / dependencies / closure evidence: S-IN-01,S-TRAVEL-FLUID-DISPATCH; fluid tags/height helpers and A jump sequence pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B gates read; no pairwise conclusion.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-JUMP-IMPULSE: Ground jump power and sprint impulse

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: B jump power positive threshold, max with current Y, sprint-angle horizontal addition and sync flag in `jumpFromGround`; inspect `getJumpPower`/attribute/effect producer chain; lines 2376-2401.
- A evidence: pending exact methods and jump-power dependency closure.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::getJumpPower(),jumpFromGround(), lines 2376-2401`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: jump strength attribute/effect, existing velocity, sprint flag and yaw -> ground impulse -> travel.
- Parent slices / dependencies / closure evidence: S-JUMP-GATE; jump strength, jump boost and sprint-state provenance pending (do not emulate hunger/food producers).
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; exact A math/effect chain pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-JUMP-LIQUID: Liquid jump and sink impulses

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `goDownInWater` and `jumpInLiquid` add vertical impulses with float literals; lines 2403-2409. Confirm separate water/lava callers from both tick and local input paths.
- A evidence: pending exact helpers and caller mapping.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::goDownInWater(),jumpInLiquid(TagKey), lines 2403-2409`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: local shift/jump input and liquid branch gates -> Y velocity impulse -> fluid travel.
- Parent slices / dependencies / closure evidence: S-JUMP-GATE,S-LOCAL-SNAPSHOT,S-LOCAL-UNSTUCK,S-SPRINT-START,S-SPRINT-STOP; exact call timing/order pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B helpers read; no pairwise result.
- Finding IDs or checked absence/replacement path: none yet.
### Slice S-LIVING-GLIDE-UPDATE: Fall-flying eligibility update before travel

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: B tests `isFallFlying()` after jump handling and calls `updateFallFlying()` before travel; lines 3125-3130. Trace the eligibility flag and equipment/effect conditions separately.
- A evidence: pending exact tick order, glide-state writer and caller correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(), lines 3125-3130`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: equipment/effects/ground/passenger state -> glide flag/update -> travel dispatch.
- Parent slices / dependencies / closure evidence: S-FALLFLY-REQUEST,S-TRAVEL-GLIDE-DISPATCH; `updateFallFlying`, `canGlide`, equipment/item data and A timing pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B call order read; exact A path unresolved.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-PREP: Bounding-box snapshot and movement input construction

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B captures `beforeTravelBox`, constructs `Vec3` from xxa/yya/zza, and does so immediately before travel gating; lines 3131-3132.
- A evidence: pending exact fields, call order and method correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(), lines 3131-3132`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: `applyInput` xxa/yya/zza and current AABB -> travel vector and pre-movement AABB used by later tick logic.
- Parent slices / dependencies / closure evidence: S-LOCAL-INPUT-MODIFIERS,S-POSE-UPDATE; A input fields/call order pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B method body read; pair comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-FALL-DISTANCE-RESET: Slow-falling/levitation fall-distance reset before travel

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: B resets fall distance when Slow Falling or Levitation is active before travel; lines 3133-3135. Determine whether any in-scope movement consumer reads the field; damage/fall-damage production remains excluded.
- A evidence: pending exact effect check, writer and consumer closure.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(), lines 3133-3135`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: active movement effect -> fall-distance write; inspect every in-scope reader and exclude damage-only consequence.
- Parent slices / dependencies / closure evidence: S-TRAVEL-AIR,S-TRAVEL-GLIDE-FORMULA; fall-distance readers and A method pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B guard read; disposition remains open until consumer inventory proves scope.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-GATE: Ridden-player-controller versus self-travel dispatch

- Inventory ID(s): INV-TICK, INV-STATE, INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: B selects `travelRidden` when a controlling passenger is a live Player; otherwise calls self travel only when movement simulation and effective AI gates pass; lines 3137-3141.
- A evidence: pending exact gate and mount/player caller correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(), lines 3137-3141`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: vehicle/controller/alive/simulation/AI state -> movement branch and input; only local player movement and player-facing mount transitions are in scope, not independent vehicle physics.
- Parent slices / dependencies / closure evidence: S-TRAVEL-PREP,S-EXT-01,S-PLAYER-01; A player-side mount state/callers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B gate read; branch ownership must be resolved before disposition.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-POST-BLOCK-EFFECTS: Block effects after movement

- Inventory ID(s): INV-TICK, INV-WORLD-MOVEMENT, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: after travel, B applies block effects only on server or authoritative local instance; lines 3143-3145. Trace movement-history collection, block callback/provider dispatch and player movement writers.
- A evidence: pending exact guard, call order and block-effect dependency closure.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(), lines 3143-3145`; `Entity.applyEffectsFromBlocks()`, `lines 903-915`; source hashes in manifest.
- State producers/writers -> consumers/readers: recorded movement segments and encountered block states -> block effects/callbacks -> any movement-relevant player flags or velocity.
- Parent slices / dependencies / closure evidence: S-TRAVEL-GATE,S-COLLISION-QUERY,S-WORLD-01; callback method and registration/resource inventory pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B call order identified; providers and A behavior unresolved.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-POST-ANIMATION: Client animation update after travel

- Inventory ID(s): INV-TICK, INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: B client-only branch calls `calculateEntityAnimation(omnidirectionalAirMover())` after block effects; lines 3147-3149. Check its state writes/readers to establish whether any feed movement or can be explicitly not-applicable.
- A evidence: pending exact call/method and consumer audit.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::aiStep(),calculateEntityAnimation(boolean), lines 3147-3149,2662-2675`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: post-travel position delta -> animation state; inspect whether any movement predicate reads those animation fields before assigning a scope disposition.
- Parent slices / dependencies / closure evidence: S-TRAVEL-GATE,S-STATE inventory; all readers and A method pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B branch read; not assumed irrelevant without consumer evidence.
- Finding IDs or checked absence/replacement path: none yet.
### Slice S-TRAVEL-DISPATCH: Fluid, fall-flying or air travel branch selection

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `LivingEntity.travel` selects `shouldTravelInFluid`, fall-flying, or ordinary air path; `shouldTravelInFluid` requires in-water/lava, affected-by-fluids, and not standing on the current fluid; lines 2430-2446.
- A evidence: pending exact method, enclosing tick call and correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::travel(Vec3),shouldTravelInFluid(FluidState), lines 2430-2446`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: fluid contacts/standing state and fall-flying state -> branch selection -> branch-specific velocity/position consumers.
- Parent slices / dependencies / closure evidence: S-LIVING-GLIDE-UPDATE,S-TRAVEL-PREP,S-FALL-DISTANCE-RESET,S-TRAVEL-GATE,S-POST-BLOCK-EFFECTS,S-POST-ANIMATION,S-PLAYER-01; fluid contact and gliding state writers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B methods read; no pairwise branch disposition.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-FLYING: Generic fluid/air flying travel

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `travelFlying` has separate water, lava and air input speeds, movement calls and per-branch damping; lines 2448-2466.
- A evidence: pending exact source and caller applicability.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::travelFlying(Vec3,float),travelFlying(Vec3,float,float,float), lines 2448-2466`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: flight/input speed and current velocity -> moveRelative/move -> water/lava/air damping -> next movement tick.
- Parent slices / dependencies / closure evidence: S-TRAVEL-DISPATCH; all player callers and abilities pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B branch read; player reachability and A behavior unresolved.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-AIR: Ground/air acceleration, gravity and damping integration

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: B `travelInAir` chooses supporting-block friction, gets movement from the relative-friction helper, applies levitation/gravity/chunk guards, then air-drag and vertical-friction terms; `getAirDrag`; lines 2468-2502.
- A evidence: pending exact method body and support/attribute correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::travelInAir(Vec3),getAirDrag(), lines 2468-2502`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: on-ground/support-block friction and input -> movement vector; levitation/gravity/chunk state -> vertical movement; drag attributes -> delta movement.
- Parent slices / dependencies / closure evidence: S-MOVE-FLAGS,S-TRAVEL-RELATIVE,S-TRAVEL-FRICTION-SPEED; block friction, attributes, effects and helper chains pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B bodies read; exact math and pair comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-RELATIVE: Relative input acceleration and in-air climb response

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B relative-friction helper calls `moveRelative`, applies climbable motion handling, moves, then has a separate horizontal-collision/jump climb or powder-snow vertical override; lines 2676-2686.
- A evidence: pending exact helper, caller and block-state dependencies.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::handleRelativeFrictionAndCalculateMovement(Vec3,float), lines 2676-2686`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: input/yaw/speed and collision/climbing/powder-snow state -> relative acceleration/movement -> velocity/position.
- Parent slices / dependencies / closure evidence: S-TRAVEL-AIR,S-TRAVEL-CLIMB; `moveRelative`, climbable and powder-snow support providers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B helper read; no A correspondence or disposition.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-FRICTION-SPEED: Ground friction conversion to input speed

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: B `getFrictionInfluencedSpeed` uses a ground-only cubic block-friction expression above its threshold and the flying-speed path otherwise; lines 2720-2726.
- A evidence: pending exact method and block-friction/speed producer chain.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::getFrictionInfluencedSpeed(float), lines 2720-2726`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: ground flag/block friction/speed attribute -> moveRelative acceleration.
- Parent slices / dependencies / closure evidence: S-TRAVEL-AIR,S-WORLD-01,S-MOD-01; property and attribute chains pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B method read; comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-FLUID-DISPATCH: Water versus lava travel setup

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B computes falling state, old Y and effective gravity once, then selects water travel plus float-while-ridden or lava travel; lines 2504-2514.
- A evidence: pending exact source and branch/caller correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::travelInFluid(Vec3), lines 2504-2514`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: fluid state/current Y/vertical velocity/effective gravity -> water/lava parameters -> branch movement.
- Parent slices / dependencies / closure evidence: S-TRAVEL-DISPATCH; fluid-height and gravity dependency chains pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B method read; no pairwise conclusion.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-WATER: Water acceleration, slowdown and falling adjustment

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: B sprinting slowdown/water slowdown, movement-efficiency scaling and airborne half factor, Dolphin's Grace override, movement/collision, climb response, slowdown and fluid vertical adjustment; lines 2516-2547.
- A evidence: pending exact body and attribute/effect/data chain.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::travelInWater(Vec3,double,boolean,double), lines 2516-2543`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: sprint/ground/fluid state, WATER_MOVEMENT_EFFICIENCY and Dolphin's Grace -> speed/slowdown -> delta movement.
- Parent slices / dependencies / closure evidence: S-TRAVEL-FLUID-DISPATCH,S-TRAVEL-FLUID-ADJUST; effect/attribute/enchantment/resource providers and fluid data pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; no pairwise formula disposition.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-LAVA: Lava acceleration and depth-dependent damping

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: B lava movement-relative step, move, shallow-fluid test, branch-specific damping, gravity quarter-step and fluid-exit helper call; lines 2549-2565.
- A evidence: pending exact source and fluid-threshold correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::travelInLava(Vec3,double,boolean,double), lines 2549-2565`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: fluid height/threshold, gravity and input -> lava movement/damping -> delta movement.
- Parent slices / dependencies / closure evidence: S-TRAVEL-FLUID-DISPATCH,S-TRAVEL-FLUID-EXIT; tags and height consumers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B method read; pairwise comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-FLUID-EXIT: Horizontal-collision exit impulse from water/lava

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `jumpOutOfFluid` checks horizontal collision and a clearance query with `movement.y + 0.6F - this.getY() + oldY`, then sets vertical velocity to `0.3F`; lines 2567-2572.
- A evidence: pending exact method, caller and clearance-query correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::jumpOutOfFluid(double), lines 2567-2572`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: fluid travel old Y/current velocity and horizontal collision -> free-space query -> vertical velocity.
- Parent slices / dependencies / closure evidence: S-TRAVEL-WATER,S-TRAVEL-LAVA; AABB/free-space query dependency pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B helper read; no pair conclusion.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-FLUID-ADJUST: Vertical fluid falling and tiny-velocity adjustment

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B nonzero gravity and non-sprinting guard; falling plus epsilon comparisons choose `-0.003`, otherwise subtract gravity/16; lines 2688-2701.
- A evidence: pending exact helper and caller correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::getFluidFallingAdjustedMovement(double,boolean,Vec3), lines 2688-2701`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: gravity/sprint/falling/current Y velocity -> adjusted fluid velocity -> water/lava travel writers.
- Parent slices / dependencies / closure evidence: S-TRAVEL-WATER,S-TRAVEL-LAVA; effective-gravity producer pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B exact comparisons read; A arithmetic/order pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-GLIDE-DISPATCH: Fall-flying branch and collision response

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B climbable condition falls back to air travel and stops fall-flying; otherwise updates glide velocity, moves, then runs server-side fall-flying collision handler; lines 2581-2600.
- A evidence: pending exact branch and player glide-state correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::travelFallFlying(Vec3),stopFallFlying(),handleFallFlyingCollisions(double,double), lines 2581-2600,2629-2636`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: fall-flying/climbable/collision state -> glide movement or air fallback and flag transition; server damage response is excluded, but its movement-state dependencies remain bounded.
- Parent slices / dependencies / closure evidence: S-TRAVEL-DISPATCH,S-TRAVEL-GLIDE-FORMULA,S-TRAVEL-CLIMB; Elytra/player transition inputs pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B method read; no A/elytra dependency closure.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-GLIDE-FORMULA: Fall-flying velocity update arithmetic

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: B `updateFallFlyingMovement` computes look/lean lengths, gravity lift, downward/forward conversion, horizontal alignment and final per-axis drag; lines 2602-2635.
- A evidence: pending exact helper and look/attribute correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::updateFallFlyingMovement(Vec3), lines 2602-2627`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: look vector, pitch, velocity, gravity and elytra state -> exact arithmetic -> glide delta movement.
- Parent slices / dependencies / closure evidence: S-TRAVEL-GLIDE-DISPATCH; gravity, equipment and item-component applicability pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B formulas read; no pairwise operation-order conclusion.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-TRAVEL-CLIMB: Climbable velocity clamps and player ladder predicate

- Inventory ID(s): INV-TICK, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: B `handleOnClimbable` resets fall distance, clamps X/Z and downward Y, then applies the player-only ladder-slide suppression condition with scaffolding check; lines 2703-2718.
- A evidence: pending exact helper, player predicate and climbable block-state correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java::handleOnClimbable(Vec3), lines 2703-2718; `onClimbable()`, lines 1722-1739`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`.
- State producers/writers -> consumers/readers: block/entity climbing state, player identity and scaffolding state -> velocity clamps/predicate -> air/water travel.
- Parent slices / dependencies / closure evidence: S-TRAVEL-RELATIVE,S-TRAVEL-WATER,S-TRAVEL-GLIDE-DISPATCH; all climbable providers and exact predicate line/hash pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B helper read; player-only precondition and A comparison unverified.
- Finding IDs or checked absence/replacement path: none yet.
### Slice S-PLAYER-01: Player swimming and ability-flight travel wrapper

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: `Player.travel(Vec3)` passenger, swim-look, flight and superclass path, lines 1402-1429; downstream conditions and abilities are dependencies.
- A evidence: pending exact source and inheritance correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/player/Player.java::net.minecraft.world.entity.player.Player#travel(Vec3), lines 1402-1429`, SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.
- State producers/writers -> consumers/readers: passenger/swimming/abilities/look/fluid state -> vertical delta writer and living travel; abilities and fluid checks pending.
- Parent slices / dependencies / closure evidence: S-LIVING-GLIDE-UPDATE,S-TRAVEL-PREP,S-FALL-DISTANCE-RESET,S-TRAVEL-GATE,S-POST-BLOCK-EFFECTS,S-POST-ANIMATION; abilities/defaults and shape/fluid queries pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; no A pair yet.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-POSE-UPDATE: Player pose selection, fit predicate and post-super tick timing

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: `Player.tick()` invokes pose selection after `super.tick()` returns (283-285); `updatePlayerPose`, `getDesiredPose` and `canPlayerFitWithinBlocksAndEntitiesWhen` determine requested/actual pose and collision fit (343-375). Dynamic/local callers and exact A order remain to trace.
- A evidence: pending exact caller/member source and override correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/player/Player.java::tick(),updatePlayerPose(),getDesiredPose(),canPlayerFitWithinBlocksAndEntitiesWhen(Pose), lines 231-285,343-375`, SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.
- State producers/writers -> consumers/readers: input/swimming/sleeping/fall-flying/shift/flight and fit query -> pose state -> dimension/eye-height/bounding-box consumers.
- Parent slices / dependencies / closure evidence: S-TICK-ENTRY,S-LOCAL-SNAPSHOT,S-LOCAL-UNSTUCK,S-SPRINT-START,S-SPRINT-STOP,S-FLIGHT-TOGGLE,S-FALLFLY-REQUEST,S-SINK-INPUT,S-FLIGHT-VERTICAL-INPUT,S-RIDEABLE-JUMP,S-DIMENSIONS,S-COLLISION-QUERY; all pose writers and fit-query dependencies pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B methods read; post-super call order is only a comparison target until A is ready.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-DIMENSIONS: Pose-dependent dimensions, eye height and bounding-box refresh

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: B `Entity.setPose`, `getDimensions`, `refreshDimensions`, `reapplyPosition`, bounding-box creation and eye-height getters; include constructor/default and state writers when pair inventory is built.
- A evidence: pending exact source, defaults and method correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::setPose(Pose),makeBoundingBox(Vec3),refreshDimensions(),getEyeHeight(Pose),getEyeHeight(),getDimensions(Pose), lines 439-445,476-482,3393-3409,3462-3468,3703-3705`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: pose/entity type/dimension defaults -> width/height/eye height, position reapplication, AABB -> movement collision, fluid and support queries.
- Parent slices / dependencies / closure evidence: S-POSE-UPDATE; LivingEntity dimension override, Player type/default dimensions, Pose definitions and all resize callers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B methods read; no pairwise disposition or equivalence claim.
- Finding IDs or checked absence/replacement path: none yet.
### Slice S-MOVE-POS: Entity movement clipping, position integration and zero-motion threshold

- Inventory ID(s): INV-TICK, INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: B `Entity.move` call from edge adjustment through collision result acceptance, movement record, and `setPos`; lines 737-757. Compare its enclosing no-physics/piston/stuck guards separately.
- A evidence: pending exact source and caller correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3), lines 737-757`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: edge-adjusted requested movement -> collided movement and squared-length threshold -> movement history and position -> later collision/support/tick readers.
- Parent slices / dependencies / closure evidence: S-TICK-ENTRY,S-TRAVEL-DISPATCH,S-TRAVEL-FLYING,S-TRAVEL-AIR,S-TRAVEL-RELATIVE,S-TRAVEL-FRICTION-SPEED,S-TRAVEL-FLUID-DISPATCH,S-TRAVEL-WATER,S-TRAVEL-LAVA,S-TRAVEL-FLUID-EXIT,S-TRAVEL-FLUID-ADJUST,S-TRAVEL-GLIDE-DISPATCH,S-TRAVEL-GLIDE-FORMULA,S-TRAVEL-CLIMB; `collide`, AABB and collision result producers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read only; A comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-MOVE-FLAGS: Collision-axis, vertical support and on-ground state updates

- Inventory ID(s): INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: B `Entity.move` computes X/Z and vertical collision flags, vertical-below, then calls `setOnGroundWithMovement`; lines 759-778. Keep damage processing excluded while tracing any movement-state writers and callers.
- A evidence: pending exact source and caller/member correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3),setOnGroundWithMovement(boolean,boolean,Vec3), lines 759-778, 671-706`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: requested vs clipped movement -> collision/ground flags -> next jump, friction, support and travel gates.
- Parent slices / dependencies / closure evidence: S-MOVE-POS,S-COLLISION-STEP; support-position lookup and flag consumers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; player authoritative guard and A semantics pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-MOVE-RESTITUTE: Post-collision player velocity response

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `Entity.move` restitution call gate plus `restituteMovementAfterCollisions`, including horizontal axes, vertical restitution, gravity/drag compensation and bounce event; lines 781-786 and 802-843.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3), lines 781-785`, and `net/minecraft/world/level/block/SlimeBlock.java::updateEntityMovementAfterFallOn(BlockGetter,Entity),bounceUp(Entity), lines 32-46`; the paired slime callback path is closed in child slice `S-MOVE-RESTITUTE-SLIME`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3),restituteMovementAfterCollisions(BlockState,boolean,boolean,Vec3), lines 781-786,802-843`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: current velocity, collision flags, effect block, gravity/air-drag attributes and bounce-suppression tag -> restitution -> rewritten velocity and sync/event state -> next tick/travel.
- Parent slices / dependencies / closure evidence: S-MOVE-FLAGS,S-WORLD-01,S-MOD-01,S-MOVE-RESTITUTE-SLIME,S-MOVE-RESTITUTE-BED,S-EXT-PISTON-MOVEMENT; slime low-speed and bed high-speed branches have source snapshots, while remaining restitution block registrations, entity attributes/modifiers, suppression data/tags and all other collision-state routes remain pending.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the slime low-speed and bed high-speed branches have separate bounded snapshots; other horizontal and non-bed restitution behavior remains open.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-MOVE-RESTITUTE-SLIME: Low-speed player landing on slime

- Inventory ID(s): INV-COLLISION, INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: A `Entity.move` dispatch after vertical clipping and the `SlimeBlock` callback; B `Entity.move` collision restitution, including the low-speed gravity threshold and Y write. The bounded case is an ordinary unsuppressed local-player landing on registered slime.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3), lines 781-785`; `ready/26.1.2/unobfuscated/net/minecraft/world/level/block/SlimeBlock.java::updateEntityMovementAfterFallOn(BlockGetter,Entity),bounceUp(Entity), lines 32-46`; `Blocks.java` slime registration lines 3221-3223. Hashes are recorded in the artifact manifest and finding.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3),restituteMovementAfterCollisions(BlockState,boolean,boolean,Vec3), lines 784-786,802-843`; `Blocks.java` slime registration lines 2976-2980; resource `data/minecraft/tags/block/suppresses_bounce.json`. Hashes are recorded in the artifact manifest and finding.
- State producers/writers -> consumers/readers: clipped downward movement and current Y velocity -> A slime callback or B restitution threshold -> player Y velocity -> subsequent travel/ticks. `Player.canSimulateMovement()` allows the local client player; shift state suppresses bounce; B suppression tag lists honey only. Effective gravity and broader travel consumers remain covered by open slices.
- Parent slices / dependencies / closure evidence: S-MOVE-RESTITUTE,S-MOVE-FLAGS,S-WORLD-01,S-TICK-ENTRY. The bounded branch is closed with paired source, registration, resource and reachable-player evidence in `findings/F-26.2-SLIME-LANDING-RESTITUTION.md`; generic restitution, full registry inventory and full movement call graph remain open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): for unsuppressed downward local-player movement on an existing slime block where `0 < -currentMovement.y < getEffectiveGravity()`, A changes negative Y velocity to positive for LivingEntity, while B selects restitution zero and writes Y velocity zero. This is source-confirmed; no runtime trajectory was tested.
- Finding IDs or checked absence/replacement path: `F-26.2-SLIME-LANDING-RESTITUTION`; B `Entity.move` calls restitution instead of the A block callback path.

### Slice S-MOVE-RESTITUTE-BED: Bed landing response under restitution

- Inventory ID(s): INV-COLLISION, INV-WORLD-MOVEMENT, INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: A's vertical collision path dispatches to `BedBlock.updateEntityMovementAfterFallOn`, which uses `0.66F` for living entities when bounce is not suppressed. B replaces that callback with generic collision restitution. The B bed registration sets block restitution to `0.75F`; B's default living-entity bounciness is zero and syncable, and its vertical restitution applies the greater of entity and block restitution only when the downward speed meets/exceeds effective gravity and the block is not suppression-tagged.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3), lines 781-785`, SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`; `BedBlock.updateEntityMovementAfterFallOn,bounceUp`, lines 138-151, SHA-256 `996e4ca63f4bfaea14b215648f2318d52c49f6c802317381a670d87c0a6d0b03`; `Blocks.registerBed`, lines 674-689,7069-7078, SHA-256 `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::move,restituteMovementAfterCollisions`, lines 784-786,802-843, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; `Blocks.BED` palette registration, lines 696-706, SHA-256 `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435`; `Attributes.BOUNCINESS`, line 31, SHA-256 `4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c`; `LivingEntity.createLivingAttributes,getEntityBounciness`, lines 332-359,2202-2204, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; `BlockTags.SUPPRESSES_BOUNCE`, line 243, SHA-256 `9834ecbe2facd79cf3d9fc79babd452ed0e000684029b2bb5e9f90aa4f51529e`; resource `data/minecraft/tags/block/suppresses_bounce.json`, SHA-256 `a477a87ac4bcb97971cb0b445f4cc9b6b8e02cd31ba3d01bc842b17a6a8477a8`.
- State producers/writers -> consumers/readers: vertical clipped local-player movement and bed support state -> A bed callback or B restitution formula / bed restitution property / living BOUNCINESS -> player Y velocity -> subsequent travel. Player reachability and the exact `0.66F` versus `0.75` formulas, default attribute, and suppression conditions are captured in the immutable finding snapshot.
- Parent slices / dependencies / closure evidence: S-MOVE-RESTITUTE,S-MOVE-FLAGS,S-WORLD-01,S-TICK-ENTRY,S-MOD-01; the bounded bed landing formula, registration, attribute default and suppression resource are closed for this case. Generic restitution, non-bed block values, movement producer/consumer inventory and full pair audit remain open.
- Status: findings
- Disposition and rationale (including concrete reachability/preconditions): for a nonsuppressed local-player downward collision on a bed with `-currentMovement.y >= getEffectiveGravity()` and BOUNCINESS no greater than `0.75`, A writes `-currentMovement.y * 0.66F`; B resolves zero clipped Y movement and writes `-currentMovement.y * 0.75`. Under the same qualifying input these differ. B's low-speed zero branch is not used for this finding; the separate slime snapshot covers its bounded low-speed case.
- Finding IDs or checked absence/replacement path: `F-26.2-BED-LANDING-RESTITUTION`; immutable finding snapshot commit `118cc0dd1a` (SHA-256 `ce0a428abe3a3c66e15a90be9d289b41227d650616d1f8945f7e9e088b650fc5`).

### Slice S-MOVE-BLOCK-SPEED: Post-move block speed factor applied to horizontal velocity

- Inventory ID(s): INV-STATE, INV-WORLD-MOVEMENT
- Exact behavior boundary and enclosing guards/order checked: A/B `Entity.move` scales X and Z of the current delta movement by `getBlockSpeedFactor()` after collision/response and movement emissions. The getter uses the block at `blockPosition()`, falling back to the block below that affects movement when the current factor is exactly `1.0F`, except water and bubble columns, which use the current factor. The full-source property search found exactly two non-default `.speedFactor(0.4F)` registrations in each endpoint (soul sand and honey); other blocks retain the `1.0F` property default.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java::move,getBlockSpeedFactor, lines 795-796,1021-1030`, SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`; `Block.getSpeedFactor(), lines 506-508`, SHA-256 `1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977`; `BlockBehaviour` speed factor default/copy/registration, lines 987,1052,1102-1103, SHA-256 `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13`; `Blocks` soul-sand and honey registrations at lines 2144-2153,5929-5932, SHA-256 `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::move,getBlockSpeedFactor, lines 795-796,1085-1094`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; `Block.getSpeedFactor(), lines 502-504`, SHA-256 `cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab`; `BlockBehaviour` speed factor default/copy/registration, lines 993,1059,1110-1111, SHA-256 `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e`; `Blocks` soul-sand and honey registrations at lines 2045-2054,4964-4967, SHA-256 `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435`.
- State producers/writers -> consumers/readers: current/support block state and movement speed-factor property -> post-collision horizontal velocity scale -> subsequent tick/travel. Both endpoints have identical `1.0F` defaults and `0.4F` soul-sand/honey properties. Collision restitution before this call remains separately tracked in `S-MOVE-RESTITUTE`.
- Parent slices / dependencies / closure evidence: S-MOVE-POS,S-MOVE-FLAGS,S-MOVE-RESTITUTE,S-WORLD-01; exact getter, factor registration scan and post-move multiplication were compared. The full block/fluid registry and unrelated movement properties remain open in `S-WORLD-01`.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): for equivalent position/support block states and incoming delta movement, A/B use the same property fallback conditions and multiply X/Z by the same float factor. The complete setter/override source search found the same sole exceptional values (soul sand and honey at `0.4F`) and the same default (`1.0F`) in both endpoints. No block-speed consumer difference was found.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-EDGE-PROBE: Movement edge backoff hook

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `Entity.move` invokes `maybeBackOffFromEdge(delta,moverType)` before collision; base implementation currently returns the input unchanged. Enumerate player-reachable overrides before deciding applicability.
- A evidence: pending exact method, subclasses and caller inventory.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::move(MoverType,Vec3),maybeBackOffFromEdge(Vec3,MoverType), lines 737,1097-1099`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: input movement and mover type -> virtual edge adjustment -> collision query; player-specific override/callability must be verified.
- Parent slices / dependencies / closure evidence: S-TICK-ENTRY,S-MOVE-POS; inheritance/override search on both trees pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B base method read; no no-difference claim from base alone.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-COLLISION-STEP: Candidate movement and step-up selection

- Inventory ID(s): INV-COLLISION, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: B `Entity.collide` clipping, collision-axis tests, step-up guard, grounded/expanded boxes, candidate iteration and horizontal-distance winner; `collectCandidateStepUpHeights` candidate filtering and sort; lines 1141-1192.
- A evidence: pending exact methods and caller/member correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::collide(Vec3),collectCandidateStepUpHeights(AABB,List<VoxelShape>,float,float), lines 1141-1192`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: input movement/on-ground/max-step -> candidate heights from collision shapes -> ordered step candidate and clipped movement -> position/ground flags.
- Parent slices / dependencies / closure evidence: S-MOVE-POS,S-MOVE-FLAGS; AABB, shape coordinate ordering and candidate dependencies pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; no pair result.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-COLLISION-QUERY: Collider gathering and axis clipping

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: B collision-bounding-box entry points and source-specific collider gathering, including entity collisions, close world-border shape and block-context collision queries; compare axis order/clipping separately in `collideWithShapes`; lines 1195-1243.
- A evidence: pending exact methods and world-call correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::collideBoundingBox(...),collectAllColliders(...),collectCollidersIgnoringWorldBorder(...), lines 1195-1243`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: entity/player collision context and expanded AABB -> world/entity/border/block shape lists -> clipping.
- Parent slices / dependencies / closure evidence: S-COLLISION-STEP; Level collision-query, border, entity and block provider dependencies pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B body read; A and provider closure pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-COLLISION-AXIS: Sequential clipping axis order

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: B `Entity.collideWithShapes` iterates `Direction.axisStepOrder(movement)`, carries resolved movement into the next-axis bounding box, and calls `Shapes.collide`; lines 1244-1262.
- A evidence: pending exact method and vector/shape-helper correspondence.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::collideWithShapes(Vec3,AABB,List<VoxelShape>), lines 1244-1262`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: movement vector/axis order/shape list -> per-axis resolved vector -> final clipped movement.
- Parent slices / dependencies / closure evidence: S-COLLISION-QUERY; `Direction.axisStepOrder`, `Shapes.collide`, `VoxelShape` dependencies pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B method body read; pair comparison pending.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-WORLD-01: Block/fluid movement providers, registrations and resource data
 Block/fluid movement properties, callbacks, registries and resource data

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: enumerate every player-reachable shape provider/override, movement callback, friction/speed/jump/bounce property, fluid push/height/flow input, block registration, state/context and neighbor dependency; this is an inventory root to be expanded into bounded individual slices, not a disposition.
- A evidence: pending exact source, registry and resource inventory.
- B evidence: B seed files include Block, BlockBehaviour, Blocks, all registered block subclasses, Fluid/FlowingFluid, shape classes and client-jar data; individual members/entry hashes not yet inventoried.
- State producers/writers -> consumers/readers: block/fluid registration/state/resource values -> player shape/contact/property/fluid queries -> movement, support, velocity and callback consumers.
- Parent slices / dependencies / closure evidence: S-POSE-UPDATE,S-DIMENSIONS,S-MOVE-POS,S-MOVE-FLAGS,S-MOVE-RESTITUTE,S-MOVE-BLOCK-SPEED,S-EDGE-PROBE,S-COLLISION-STEP,S-COLLISION-QUERY,S-COLLISION-AXIS,S-TRAVEL-DISPATCH,S-TRAVEL-FLYING,S-TRAVEL-AIR,S-TRAVEL-RELATIVE,S-TRAVEL-FRICTION-SPEED,S-TRAVEL-FLUID-DISPATCH,S-TRAVEL-WATER,S-TRAVEL-LAVA,S-TRAVEL-FLUID-EXIT,S-TRAVEL-FLUID-ADJUST,S-TRAVEL-GLIDE-DISPATCH,S-TRAVEL-GLIDE-FORMULA,S-TRAVEL-CLIMB; registry/resource inventory and all reachable providers pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B root inventory unbuilt; no pairwise no-difference or absence conclusion.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-MOD-01: Movement attributes, effects, enchantments and equipment chains

- Inventory ID(s): INV-MODIFIERS, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: consumer -> aggregation/default -> attribute/effect/enchantment registration and application/removal/conditions -> slots/data/tags for movement-affecting values; exclude health/food producers and combat simulations.
- A evidence: pending exact source and resource inventory.
- B evidence: B seed consumers include LivingEntity, Player, LocalPlayer, Attributes and effect/enchantment helpers; exact producer/consumer ranges and data entry hashes pending.
- State producers/writers -> consumers/readers: effect/equipment/server attribute inputs -> movement modifier aggregation -> travel/sprint/jump/flight/contact consumers.
- Parent slices / dependencies / closure evidence: S-LOCAL-SNAPSHOT,S-LOCAL-UNSTUCK,S-SPRINT-START,S-SPRINT-STOP,S-JUMP-GATE,S-JUMP-IMPULSE,S-JUMP-LIQUID,S-TRAVEL-DISPATCH,S-TRAVEL-FLYING,S-TRAVEL-AIR,S-TRAVEL-RELATIVE,S-TRAVEL-FRICTION-SPEED,S-TRAVEL-FLUID-DISPATCH,S-TRAVEL-WATER,S-TRAVEL-LAVA,S-TRAVEL-FLUID-EXIT,S-TRAVEL-FLUID-ADJUST,S-TRAVEL-GLIDE-DISPATCH,S-TRAVEL-GLIDE-FORMULA,S-TRAVEL-CLIMB,S-PLAYER-01; resource/tag and server-synchronization provenance pending.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): B dependency inventory unbuilt; no difference claim.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-EXT-01: External player movement inputs and client consumers

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: client player velocity/position corrections, movement synchronization, pushes, piston displacement, mount/dismount transitions and launch inputs; exclude independent non-player movement and damage/combat simulation.
- A evidence: paired packet consumers are `ClientPacketListener.handleSetEntityMotion,handleMovePlayer,setValuesFromPositionPacket,handleRotatePlayer`, plus position-sync, teleport and relative-move guards, in the cited ClientPacketListener source hash.
- B evidence: paired packet consumers are `ClientPacketListener.handleSetEntityMotion,handleMovePlayer,setValuesFromPositionPacket,handleRotatePlayer`, plus position-sync, teleport and relative-move guards, in the cited ClientPacketListener source hash.
- State producers/writers -> consumers/readers: server/external packet or world callback -> player position/velocity/pose/vehicle state -> local tick/travel.
- Parent slices / dependencies / closure evidence: S-POSE-UPDATE,S-DIMENSIONS,S-MOVE-POS,S-MOVE-FLAGS,S-MOVE-RESTITUTE,S-EXT-ENTITY-MOTION-PACKET,S-EXT-PLAYER-CORRECTIONS,S-EXT-PISTON-MOVEMENT; paired client velocity and player correction/rotation consumers and the piston displacement caller are checked, while remaining direct-velocity packets, vehicle transitions, and launch-input producer chains remain pending.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): direct packet consumers for server-supplied velocity, position correction, and rotation now have paired dispositions in S-EXT-ENTITY-MOTION-PACKET and S-EXT-PLAYER-CORRECTIONS. The external movement inventory is still incomplete: piston displacement, remaining direct player velocity writers, mount/dismount and vehicle-associated player-facing cases, and launch-input source chains must be enumerated and compared; no blanket equivalence is claimed.
- Finding IDs or checked absence/replacement path: none for this broad slice yet.

### Slice S-TICK-ENTRY: Local player tick, superclass chain and virtual aiStep dispatch

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: A/B `LocalPlayer.tick()` -> `AbstractClientPlayer.tick()` -> `Player.tick()` -> `LivingEntity.tick()` -> `Entity.tick()/baseTick()`; then virtual `LivingEntity.aiStep()` dispatches through `LocalPlayer`, `AbstractClientPlayer`, `Player`, and `LivingEntity` super calls. `Player.tick()` performs pose update after its superclass returns. Movement-specific Player.aiStep work is split into the next two slices; client avatar visual state is excluded in `S-CLIENT-AVATAR-STATE`.
- A evidence: `LocalPlayer.tick(), lines 227-251` (file SHA `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`); `AbstractClientPlayer.tick(), lines 44-47` (file SHA `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc`); `Player.tick(), lines 231-284` (file SHA `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`); `LivingEntity.tick(), lines 2699-2814` (file SHA `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`); `Entity.tick(),baseTick(), lines 499-501,503-557` (file SHA `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`). Method bodies were extracted and compared with B; all five tick bodies are identical.
- B evidence: `LocalPlayer.tick(), lines 227-251` (file SHA `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`); `AbstractClientPlayer.tick(), lines 44-47` (file SHA `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc`); `Player.tick(), lines 232-285` (file SHA `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`); `LivingEntity.tick(), lines 2765-2880` (file SHA `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`); `Entity.tick(),baseTick(), lines 506-508,510-564` (file SHA `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`). Method bodies were extracted and compared with A; all five tick bodies are identical.
- State producers/writers -> consumers/readers: `Entity.baseTick()` state update -> LivingEntity tick -> virtual player aiStep chain -> movement slices and travel; then `Player.tick()` applies pose after `super.tick()` returns. This slice records dispatch/order only; health and regeneration producers are excluded.
- Parent slices / dependencies / closure evidence: S-IN-01,S-LOCAL-SNAPSHOT,S-LOCAL-UNSTUCK,S-SPRINT-START,S-SPRINT-STOP,S-FLIGHT-TOGGLE,S-FALLFLY-REQUEST,S-SINK-INPUT,S-FLIGHT-VERTICAL-INPUT,S-RIDEABLE-JUMP,S-LIVING-GLIDE-UPDATE,S-TRAVEL-PREP,S-FALL-DISTANCE-RESET,S-TRAVEL-GATE,S-POST-BLOCK-EFFECTS,S-POST-ANIMATION,S-POSE-UPDATE,S-PLAYER-AISTEP-PRE,S-PLAYER-AISTEP-POST,S-CLIENT-AVATAR-STATE; behavior within those slices remains independently accounted.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): the complete five-method tick path is identical in A and B, and both class inheritance chains plus the `aiStep` virtual/super call sites were read. This closes tick topology/order only; it does not close any called movement body.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-EXT-ENTITY-MOTION-PACKET: Client consumption of server-supplied entity velocity

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `ClientPacketListener.handleSetEntityMotion` schedules on the client packet processor, looks up the entity by packet ID, and if present calls `Entity.lerpMotion(packet.movement())`; `Entity.lerpMotion` directly assigns delta movement.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/multiplayer/ClientPacketListener.java::handleSetEntityMotion(ClientboundSetEntityMotionPacket), lines 623-630`, SHA-256 `eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82`; `ready/26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java::lerpMotion(Vec3), lines 2580-2582`, SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/multiplayer/ClientPacketListener.java::handleSetEntityMotion(ClientboundSetEntityMotionPacket), lines 623-629`, SHA-256 `9cb0cc8afeba9e4f42f428a52719c645817d03893dc371e6711c7bd16eac1b6`; `ready/26.2/unobfuscated/net/minecraft/world/entity/Entity.java::lerpMotion(Vec3), lines 2647-2649`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: server-supplied packet movement vector -> client entity lookup -> `setDeltaMovement` -> subsequent local-player travel/tick. The packet value is an external input; its server-side damage/knockback producer is not emulated in this client movement slice.
- Parent slices / dependencies / closure evidence: S-EXT-01,S-PLAYER-IMPULSE-RESPONSE,S-TICK-ENTRY,S-VELOCITY-HORIZONTAL,S-VELOCITY-VERTICAL. Both handler and assignment helper match exactly; other correction packet and push-caller routes remain open in their parent slices.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): when a motion packet names a present local player entity, both endpoints assign the received vector directly through the same `lerpMotion` body. No client-computed knockback formula is run in this handler.
- Finding IDs or checked absence/replacement path: none; the paired packet handler and `Entity.lerpMotion` helper are the checked consumer path.

### Slice S-EXT-PLAYER-CORRECTIONS: Client player position and rotation corrections

- Inventory ID(s): INV-EXTERNAL, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `ClientPacketListener.handleMovePlayer` applies a server position/rotation correction only when the local player is not a passenger, then always acknowledges the teleport, echoes the resulting position/rotation with on-ground and horizontal-collision flags false, and notifies the block-state prediction handler. Its shared `setValuesFromPositionPacket` computes absolute values from the current position/velocity/rotations and relative flags, compares squared displacement with `4096.0`, then either interpolates or writes position, velocity, rotation, and old position/rotation. The player handler passes `interpolate=false`, so its player correction always follows the direct-write branch. `handleRotatePlayer` resolves relative yaw/pitch against current state, writes both rotations, resets old rotation, and echoes them. Entity-position sync, entity teleport, and relative move packets were also checked for the local-player-authoritative guard and any direct player-facing fallback.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/multiplayer/ClientPacketListener.java::handleMovePlayer(ClientboundPlayerPositionPacket),setValuesFromPositionPacket(PositionMoveRotation,Set<Relative>,Entity,boolean),handleRotatePlayer(ClientboundPlayerRotationPacket), lines 796-841`, SHA-256 `eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82`; related `handleEntityPositionSync,handleTeleportEntity,handleMoveEntity`, lines 642-752, same SHA-256.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/client/multiplayer/ClientPacketListener.java::handleMovePlayer(ClientboundPlayerPositionPacket),setValuesFromPositionPacket(PositionMoveRotation,Set<Relative>,Entity,boolean),handleRotatePlayer(ClientboundPlayerRotationPacket), lines 795-840`, SHA-256 `9cb0cc8afeba9e4f42f428a52719c645817d03893dc371e6711c7bd16eac1b6`; related `handleEntityPositionSync,handleTeleportEntity,handleMoveEntity`, lines 641-751, same SHA-256.
- State producers/writers -> consumers/readers: server position/rotation correction plus relative-component set -> current local-player position, velocity and rotation -> direct snap and old-position/rotation reset -> subsequent local movement/tick. For authoritative player-controlled entities, position-sync and relative-move packets update only the position-delta codec base; they do not apply the packet transform to entity position. The removed-player-vehicle teleport fallback does directly apply the position packet to the player and sends a position/rotation response. A non-authoritative vehicle's passenger placement can reposition the player, but that vehicle-produced trajectory is out of scope.
- Parent slices / dependencies / closure evidence: S-EXT-01,S-EXT-ENTITY-MOTION-PACKET,S-TICK-ENTRY,S-VELOCITY-HORIZONTAL,S-VELOCITY-VERTICAL,S-POSE-UPDATE,S-DIMENSIONS; the paired correction and rotation consumer bodies and their shared value-calculation method are compared. Packet producers/server reconciliation, teleport acknowledgement response, active player vehicle transitions, remaining direct-velocity writers, and downstream movement consumers remain open in S-EXT-01 and dependency inventory.
- Status: compared-no-difference
- Disposition and rationale (including concrete reachability/preconditions): equivalent packets and current player state take identical guards and operation order in both endpoints, including the `4096.0` squared-distance threshold, direct position/velocity/yaw/pitch writes for player correction, relative rotation calculation, old-state update, and serverbound acknowledgement/echo. `handleEntityPositionSync` does not overwrite the authoritative local player's transform; vehicle passenger placement remains outside scope. No direct correction-body difference was found.
- Finding IDs or checked absence/replacement path: none; the paired handlers and helper cover the checked player correction consumers. Remaining producer and other external-input paths stay open.

### Slice S-EXT-PISTON-MOVEMENT: Player displacement by moving piston blocks

- Inventory ID(s): INV-EXTERNAL, INV-STATE, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: moving piston block entity tick increments movement progress by `0.5F`, runs `moveCollidedEntities` and `moveStuckEntities`, then clamps progress to `1.0F`. The collision path selects entities from the movement area, filters `PushReaction.IGNORE`, treats a moved slime block as a direction-axis velocity assignment for non-`ServerPlayer` entities, intersects each moved block-shape AABB with the entity box, then calls `Entity.move(MoverType.PISTON, delta * direction.step)` with source-piston base correction when applicable. Honey-block horizontal movement also carries grounded entities meeting the support/position predicate. `moveEntityByPiston` brackets `Entity.move` with a thread-local no-clip direction and records/removes block-effect movement bookkeeping. `Entity.move` limits piston displacement per axis over the game tick using accumulated piston deltas, then proceeds through collision and movement-state updates.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java::moveCollidedEntities,moveEntityByPiston,moveStuckEntities,matchesStickyCritera,moveByPositionAndProgress,tick,getCollisionShape, lines 120-262,310-394`, SHA-256 `67bceb946c172c5c022663fda32d123a6bbb85ca4171705ca9dc2c54105ab6e6`; `Entity.move(MoverType,Vec3),limitPistonMovement(Vec3),applyPistonMovementRestriction(Axis,double), lines 704-800,1037-1072`, SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java::moveCollidedEntities,moveEntityByPiston,moveStuckEntities,matchesStickyCritera,moveByPositionAndProgress,tick,getCollisionShape, lines 120-262,310-394`, SHA-256 `706bab1a13ba99bbd334aad853b99f72df58fce5b6b67d30d41f902b63dd8e06`; `Entity.move(MoverType,Vec3),limitPistonMovement(Vec3),applyPistonMovementRestriction(Axis,double), lines 711-807,1101-1136`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
- State producers/writers -> consumers/readers: moving piston block entity progress and moving block shape -> player/entity selection and overlap amount -> direct slime direction-velocity assignment (for the local client player path) and/or `MoverType.PISTON` displacement -> collision/ground/restitution state -> subsequent local travel. Piston movement methods and piston-delta clamp match A/B; the broader `Entity.move` post-collision response does not: B adds a `canSimulateMovement()`-guarded restitution call after collision flag calculation, so the piston-triggered reachability of that delta is retained under S-MOVE-RESTITUTE and is not declared equivalent here.
- Parent slices / dependencies / closure evidence: S-EXT-01,S-MOVE-POS,S-MOVE-FLAGS,S-MOVE-RESTITUTE,S-MOVE-RESTITUTE-SLIME,S-WORLD-01; paired piston caller, predicates, direction math, progress increment, and piston movement accumulator are recorded. Generic restitution consequences and collision provider/shape closure remain open in parent slices; moved-state registration and piston world updates remain within `INV-WORLD-MOVEMENT`.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): the piston producer and direct displacement operation are source-confirmed and pair-matched, but this route reaches a version-different post-collision response in `Entity.move`; consequences outside the already bounded slime landing case require disposition in S-MOVE-RESTITUTE. No additional piston-specific finding is claimed until that shared collision response is closed.
- Finding IDs or checked absence/replacement path: `F-26.2-SLIME-LANDING-RESTITUTION` covers its bounded unsuppressed slime landing condition; piston route is cross-referenced as an additional caller of the still-open generic restitution slice, with no separate disposition.

### Slice S-PLAYER-AISTEP-PRE: Jump-trigger countdown and flight fall-distance reset

- Inventory ID(s): INV-TICK, INV-STATE
- Exact behavior boundary and enclosing guards/order checked: `Player.aiStep` decrements a positive `jumpTriggerTime`, calls regeneration and inventory tick, then resets fall distance when flight is enabled and the player is not a passenger, all before `super.aiStep()`; lines 443-453.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java::aiStep(), lines 443-453`, SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`; inherited `Entity.resetFallDistance(), lines 2841-2843`, SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`. LocalPlayer sets/resets `jumpTriggerTime` in S-FLIGHT-TOGGLE.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/player/Player.java::aiStep(), lines 443-453`, SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; inherited `Entity.resetFallDistance(), lines 2908-2910`, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`. The movement-relevant calls and order match A.
- State producers/writers -> consumers/readers: LocalPlayer flight-toggle jump timer -> Player countdown; abilities/passenger state -> fall-distance zeroing -> next LivingEntity travel/fall update.
- Parent slices / dependencies / closure evidence: S-TICK-ENTRY,S-FLIGHT-TOGGLE,S-TRAVEL-AIR,S-FALL-DISTANCE-RESET; `inventory.tick()` may update movement-relevant item/equipment state and remains in D-DEPENDENCIES/INV-MODIFIERS.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): movement-specific direct code and reset helper were compared; the pre-super inventory tick's movement-modifier consequences remain unclosed. `tickRegeneration()` is an excluded health producer and is not emulated.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-PLAYER-AISTEP-POST: Post-travel cached movement speed update

- Inventory ID(s): INV-TICK, INV-STATE, INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: after `super.aiStep()` returns from LivingEntity travel, Player updates swing/head rotation and calls `setSpeed((float)getAttributeValue(Attributes.MOVEMENT_SPEED))`; lines 453-456. The speed write occurs before later pickup work.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java::aiStep(), lines 453-456`, SHA-256 `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`; `LivingEntity.setSpeed(float), lines 2679-2681`, SHA-256 `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`.
- B evidence: `ready/26.2/unobfuscated/net/minecraft/world/entity/player/Player.java::aiStep(), lines 453-456`, SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; `LivingEntity.setSpeed(float), lines 2736-2738`, SHA-256 `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`. The setter and call expression match A.
- State producers/writers -> consumers/readers: movement-speed attribute value -> cached living-entity speed after current travel -> subsequent relative movement; its attribute source/modifiers remain in `INV-MODIFIERS`.
- Parent slices / dependencies / closure evidence: S-TICK-ENTRY,S-TRAVEL-RELATIVE,S-MOD-01; movement-speed attribute defaults/modifiers and all writers remain open.
- Status: in-progress
- Disposition and rationale (including concrete reachability/preconditions): call order and setter bodies match, but the attribute producer/condition chain must be closed before disposition.
- Finding IDs or checked absence/replacement path: none yet.

### Slice S-CLIENT-AVATAR-STATE: Client animation state from position, velocity and bob

- Inventory ID(s): INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: `AbstractClientPlayer.tick()` records position/velocity and updates cloak interpolation; `AbstractClientPlayer.aiStep()` updates bob before `super.aiStep()`; `ClientAvatarState` stores prior movement, cloak and bob values. Consumers were searched in A/B camera and avatar renderer paths.
- A evidence: `ready/26.1.2/unobfuscated/net/minecraft/client/player/AbstractClientPlayer.java::tick(),aiStep(),updateBob(), lines 44-47,76-90`, SHA-256 `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc`; `ClientAvatarState.tick(Vec3,Vec3),updateBob(float), lines 19-22,75-78`, SHA-256 `12508a7c8e595ab17fc6cf12979657c2c4b8c345d55a1f0e7556c3e4b566caaa`.
- B evidence: `AbstractClientPlayer.java::tick(),aiStep(),updateBob(), lines 44-47,76-90`, SHA-256 `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc`; `ClientAvatarState.java::tick(Vec3,Vec3),updateBob(float), lines 19-22,75-78`, SHA-256 `12508a7c8e595ab17fc6cf12979657c2c4b8c345d55a1f0e7556c3e4b566caaa`; consumer references `Camera.java:150-152` and `AvatarRenderer.java:218` read avatar state into camera/render state. These consumers write presentation state and do not feed player position, velocity or dimensions.
- State producers/writers -> consumers/readers: player position/velocity -> client avatar bob/cloak/interpolation state -> camera/rendering; no movement-state write or physics consumer found in the call path.
- Parent slices / dependencies / closure evidence: `INV-EXCLUSIONS`; rendering-only and camera presentation state are outside player movement scope.
- Status: not-applicable
- Disposition and rationale (including concrete reachability/preconditions): the state is consumed by client camera/avatar presentation and only stores interpolation/bob/cloak values. It is not an input to LocalPlayer/Player movement or collision, so this is a rendering-only path excluded by campaign scope.
- Finding IDs or checked absence/replacement path: none yet.

## Dependency queue and blockers

- D-A-READY / D-A-SOURCE: resolved. Exact current `26.1.2/unobfuscated` marker, source tree, manifests, client artifact identity, diagnostics and provenance were verified on 2026-10-07; cited source file hashes match the source manifest. Owner: shared source-preparation owner.
- D-B-PROVENANCE: exact command, source/artifact hashes, toolchain and success excerpt are now recorded in `ready/26.2/unobfuscated.provenance.json`; the original client jar was rehashed against its marker. The source owner's personal name and full Gradle stdout are absent (only the success excerpt persists). These recordkeeping gaps remain open but do not block pair source comparison.
- D-DEPENDENCIES: all pair-specific state writers, source overrides, registry/resource entries, tags, data, shape providers, attributes/effects/equipment and external packet dependencies found by the seven inventories. Add each as a child dependency with a parent slice and close against both endpoints.
- Open dependencies: D-B-PROVENANCE (missing owner name/full stdout only), D-DEPENDENCIES.

## Finding index

Two source-confirmed candidates are recorded: `F-26.2-SLIME-LANDING-RESTITUTION`, in incremental snapshot commit `8eb345d5973158e7e1bc26a2044d63fb77b45e19` (finding SHA-256 `c14302ffa5b0a773d1e27adbb7f8fbe12188066c80763bdac4a8a02ef0446a05`), and `F-26.2-BED-LANDING-RESTITUTION`, in immutable snapshot commit `118cc0dd1a` (finding SHA-256 `ce0a428abe3a3c66e15a90be9d289b41227d650616d1f8945f7e9e088b650fc5`). Independent review is pending. The full pair remains active; neither snapshot freezes coverage or claims endpoint-line completeness.

## Resume checkpoint

- Last dispositioned slices: S-IN-01,S-IN-BASE,S-LOCAL-SNAPSHOT,S-SPRINT-START,S-SPRINT-STOP,S-SQUARE-MOVE,S-TICK-ENTRY,S-EXT-ENTITY-MOTION-PACKET,S-EXT-PLAYER-CORRECTIONS,S-PLAYER-IMPULSE-RESPONSE,S-SINK-INPUT,S-FLIGHT-VERTICAL-INPUT,S-MOVE-BLOCK-SPEED; `S-RIDEABLE-JUMP` and `S-CLIENT-AVATAR-STATE` are not applicable; `S-MOVE-RESTITUTE-SLIME` and `S-MOVE-RESTITUTE-BED` each have one source-confirmed finding. `S-LOCAL-UNSTUCK`, flight toggle/fall-flying request, input modifiers, Player aiStep pre/post, `S-EXT-01`, `S-EXT-PISTON-MOVEMENT`, and generic `S-MOVE-RESTITUTE` remain in progress. The active ledger has 61 units: 35 pending, 9 in-progress, 13 compared-no-difference, 2 findings, 2 not-applicable.
- Next bounded slice and exact files/members/body ranges to open: finish the LocalPlayer pre-travel dependencies, beginning with `S-LOCAL-UNSTUCK`'s suffocation query/block-property inventory and then close the sprint, flight, fall-flying and input modifier predicates; continue with `LocalPlayer.aiStep()` and player-specific `LivingEntity.aiStep()/travel()` in each endpoint. Rehash newly cited source files before disposition.
- Outstanding dependencies and owners: D-B-PROVENANCE (shared source owner), D-DEPENDENCIES (worker inventory).
- Current assumptions requiring verification: no movement-relevant resource/data is omitted. No release introduction point can be inferred from these two endpoints alone.

## Implementation reconciliation

- Reconciliation status: pending; outside this source-only assignment and prohibited until the source-only report is frozen and an explicit follow-up owner is assigned.
- Repository revision inspected: none.
- Finding -> implementation disposition/evidence: none; source findings are not yet confirmed.
- Existing implementation without a frozen source finding: not inspected.
- Coverage gaps routed back to discovery slices: pending; no implementation files inspected.

## Independent source audit

- Reviewer: not assigned in available task instructions; reviewer must not be a discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: none; discovery currently has B-only prep.
- Concrete missed-slice routes (or `none found`): not audited.
- Misses routed to slice/finding IDs and owners: not applicable before reviewer assignment.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: 35 pending; 9 in-progress; 13 compared-no-difference; 2 findings; 2 not-applicable; 0 blocked. This remains a working inventory; expand it as source/provider reachability is enumerated.
- Required inventory status and evidence: `INV-TICK` pending, with `S-IN-01`, `S-IN-BASE`, `S-LOCAL-SNAPSHOT`, `S-SQUARE-MOVE`, `S-TICK-ENTRY`, `S-SINK-INPUT`, and `S-PLAYER-IMPULSE-RESPONSE` compared; `S-RIDEABLE-JUMP` and `S-CLIENT-AVATAR-STATE` routed to `INV-EXCLUSIONS`; `S-LOCAL-UNSTUCK`, sprint start/stop, flight toggle/fall-flying request, `S-LOCAL-INPUT-MODIFIERS`, `S-PLAYER-AISTEP-PRE`, `S-PLAYER-AISTEP-POST`, and `S-FLIGHT-VERTICAL-INPUT` remain in-progress; other slices await source/provider closure.
- Open dependencies: D-B-PROVENANCE (missing owner name/full stdout only), D-DEPENDENCIES. D-A-READY is resolved for exact `26.1.2/unobfuscated`.
- Unresolved gaps and limits: current dispositions cover keyboard sampling/vector construction, neutral client-input fallback, the selected pre-travel input snapshot range and square-movement math; paired tick topology and direct flight/knockback velocity formulas have bounded source comparisons but retain open dependencies. Input modifier producers and conditions, player-facing external impulse/correction routes, the full call-path/provider inventory, findings, source freeze, independent audit and downstream reconciliation remain open.
- Evidence/hash/correspondence audit: A readiness JSON verified `versionId=26.1.2`, `unobfuscated`, `ready`; marker-cited source, artifact and diagnostics manifest hashes recomputed and match. A success log reports build success/exit code 0; provenance records the exact single-version command and tool versions. Six A source hashes recomputed and match the source manifest. B readiness/manifests and six cited B files were previously verified. Both `KeyboardInput.java` full-file hashes are equal; method bodies, key order, sign/normalization operations and LocalPlayer call location were read on both sides. Other slices remain unpaired.
- Blind freeze: pending.
- Implementation reconciliation: pending.
- Independent audit: pending.
- Runtime validation: not performed; runtime is not authorized.










