# Implementation review: fence arm beside End Portal Frame — ACCEPT

## Review identity

- Implementation commit reviewed: `51ecf89cfdb2d077a34a56e7e1c05a9d1236f46d` (`fix/fence-end-portal-frame-1-9`).
- Implementation handoff tip reviewed: `44882452607d58457d44e1aa4940f15dca9e72c8`.
- Accepted source snapshot: `103786e872f13a44aa0232a562f2eaab8eb5e185`, `workflows/wiki-audit-2026-10-07/fence-end-portal-frame-1.8.9-to-1.9.4.md`, SHA-256 `e5edb59eaa858f4905ec9f87862344bfbece0fe5e22315e86c531c071e638852`.
- Provenance re-review: `0979bee9878fc1351a03c183a50ff96c28d79e21`, decision ACCEPT, report SHA-256 `20df73aca35e20e16479eea7ca212ac902824ba27eed0042f2246fe1d8251dab`.
- Current `main` merged into this isolated review branch: `64895d266fcee5da774c3236295ba949045200c7`; final review merge commit: `866bb2059421e0c94d033df8bbf032107fd519c3`.

## Review findings

The implementation restores only the collision arm whose side neighbor is an End Portal Frame and whose corresponding modern fence connection property is false. It checks the four neighboring block states directly; it does not alter fence or frame block states, fence placement/update rules, or any other neighbor predicate. For all other sides, the current state properties determine whether the arm is included. If no End Portal Frame needs an added arm, the hook returns empty so the native collision shape is used.

The source delta is correctly bounded. In 1.8.9, `FenceBlock.shouldConnectTo()` accepts a neighbor whose material is solid-blocking and whose inherited `Block.isCube()` is true, except pumpkins. `EndPortalFrameBlock` uses `Material.STONE` and inherits `isCube() == true`, so the fence's collision collector adds that side's arm. In 1.9.4, `FenceBlock.shouldConnectTo()` checks `BlockState.isCube()`; `StateDefinition.BlockStateImpl.isCube()` delegates to the block, and `EndPortalFrameBlock.isCube(state)` returns false. The corresponding connection property and arm are therefore absent. `Material.STONE` remains solid-blocking in both releases.

The replacement shape is the same solid union as the current 26.2 fence collision shape, plus the missing frame-side arm. The current fence builds a 4/16-wide, 24/16-high central post and four 4/16-wide arms whose source boxes extend 8/16 from the centerline. The post/arm union reaches 0.375–0.625 across the center and to the block edge on connected sides. The implementation expresses that same union with explicit arm boxes: north `x=.375..625,z=0..625`; east `x=.375..1,z=.375..625`; south `x=.375..625,z=.375..1`; west `x=0..625,z=.375..625`; all use `y=0..1.5`. The wider overlap in each explicit arm lies inside the central post and does not change the collision set. Existing connected sides and the post are retained when the hook supplies the shape.

Registration is limited to the seven fences present in 1.8.9: the six wood fences and Nether Brick Fence. The current `oak_fence` registry entry corresponds to the 1.8.9 `fence` entry; the other listed IDs match the old Spruce, Birch, Jungle, Acacia, Dark Oak and Nether Brick entries. Later additions such as Pale Oak are not registered. The change is annotated `V1_8`; resolver selection applies it only to the 1.8 profile, while later historical profiles and `CURRENT` keep native behavior. The existing collision mixin resolves by block ID variant, so these seven variants do not overlap pane variants.

The current collision binding is narrow and player-only. 26.2 `Entity.move()` collects block collisions with the entity collision context; `BlockCollisions.computeNext()` asks that context for the state collision shape, which calls the three-argument `BlockStateBase.getCollisionShape(level, pos, context)` overload. `BlockStateCollisionShapeMixin` injects after that native virtual shape call, resolves a player from `EntityCollisionContext`, and dispatches the block-ID-specific hook. `MovementRuntime.playerFrom()` rejects non-player entities. The hook therefore affects player collision through this generic path and does not replace visual shapes, state updates, or non-player collision behavior.

## Exact source identities

All sources below are from the canonical ready tree at `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready`. The cited Java-file hashes were recomputed from that tree and match the source manifests.

- **1.8.9, `ornithe-feather`** — ready source-manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; artifact-manifest SHA-256 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`; client SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`.
  - `net/minecraft/block/FenceBlock.java`, `addCollisions()` lines 36–81 and `shouldConnectTo()` lines 128–136, SHA-256 `8d6a803988d77fc51f364520f2e0cd5567b1d5c29a4d28d01cdcf93e6d5890d9`.
  - `net/minecraft/block/EndPortalFrameBlock.java`, constructor/material lines 23–25 and no `isCube()` override, SHA-256 `c2c876aefe34d001ff0e3eebddd0df01ee85b0bf499e205eb1eb95ec44857b9e`.
  - `net/minecraft/block/Block.java`, inherited `isCube()` lines 245–247, SHA-256 `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`.
  - `net/minecraft/block/material/Material.java`, `STONE` lines 5–8, `blocksMovement()` lines 68–70 and `isSolidBlocking()` lines 100–101, SHA-256 `017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2`.
  - `net/minecraft/block/Blocks.java`, fence registry entries `FENCE`, Spruce/Birch/Jungle/Dark Oak/Acacia and Nether Brick Fence at lines 99–104, 136, 307–312 and 344, SHA-256 `1da2d85406ed991e8f6bc1f420babf0bd43598478f347c6a4dc9fea322ed0695`.
- **1.9.4, `ornithe-feather`** — ready source-manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; artifact-manifest SHA-256 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`; client SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`.
  - `net/minecraft/block/FenceBlock.java`, collision shape and collector lines 45–76, `shouldConnectTo()` lines 120–130, SHA-256 `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909`.
  - `net/minecraft/block/EndPortalFrameBlock.java`, constructor/material lines 33–35 and `isCube(BlockState)` lines 108–111, SHA-256 `1c6495a6d6c5d777eb643983c7e7b8151bb5b99ef1a4a3b991655792a0ad58eb`.
  - `net/minecraft/block/state/StateDefinition.java`, resolved `BlockStateImpl.isCube()` lines 267–270, SHA-256 `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493`.
  - `net/minecraft/block/material/Material.java`, `STONE` lines 7–10, `blocksMovement()` lines 70–72 and `isSolidBlocking()` lines 102–103, SHA-256 `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19`.
  - `net/minecraft/block/Blocks.java`, corresponding old registry entries at lines 103–108, 140, 334–339 and 371, SHA-256 `dc561d17e9e12767774d9ef01057f0c981e5a9bb666e2d11cba3517062cc8dd7`.
- **26.2, `unobfuscated`** — ready source-manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; artifact-manifest SHA-256 `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`.
  - `net/minecraft/world/level/block/FenceBlock.java`, constructor collision dimensions lines 37–42 and neighbor/state handling lines 60–127, SHA-256 `be5e1aa19a07d68457ff21968fb4e264669588fb6e83a86a6f51e76833f17f93`.
  - `net/minecraft/world/level/block/CrossCollisionBlock.java`, post/arm union construction lines 53–69 and collision shape return lines 82–83, SHA-256 `dcd2dbbcd093a68dee4ee282e5f3101c519d633f3f173dd33383bd4b548c1ba5`.
  - `net/minecraft/world/level/block/Block.java`, pixel-scaled `column()` lines 177–185 and `boxZ()` lines 187–198, SHA-256 `cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab`; `Shapes.rotateHorizontal()` maps its north template to cardinal directions at `net/minecraft/world/phys/shapes/Shapes.java` lines 404–421, SHA-256 `6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891`.
  - `net/minecraft/world/level/BlockCollisions.java`, context-based collision-shape query lines 93–100, SHA-256 `eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e`.
  - `net/minecraft/world/level/block/state/BlockBehaviour.java`, context overload and state-to-block delegation lines 686–691, SHA-256 `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e`.
  - `net/minecraft/world/entity/Entity.java`, context-bearing block collision collection line 1228, SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`.
  - `net/minecraft/world/level/block/Blocks.java`, Oak Fence line 2021, Nether Brick Fence line 2541, End Portal Frame line 2601, Spruce/Birch/Jungle/Acacia/Dark Oak Fence lines 3498–3557, SHA-256 `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435`.

## Code bindings and main integration

- `FencePortalFrameConnection.java` lines 20–82 contains the V1_8 annotation, seven block IDs, directional arm boxes, End Portal Frame checks, and shape union.
- `change/v1_8/MovementChanges.java` lines 12–16 registers the seven fence variants alongside the existing pane variants.
- `BlockStateCollisionShapeMixin.java` lines 21–44 targets the context overload, filters to player contexts, and dispatches by block ID. `MovementRuntime.playerFrom()` at lines 81–88 rejects non-players.
- `ChangeResolver.java` returns no deltas for `CURRENT` and filters older-emulating changes out when the selected profile advances; `MechanicKey` includes the block variant, keeping fence and pane registrations independent.

Commits on current `main` after the implementation's recorded base add Elytra-start, sneak-edge and swimming-pitch movement changes, including edits to the shared `PlayerMixin`. They do not modify `BlockStateCollisionShapeMixin`, `BlockCollisionShape`, `MovementRuntime`, the collision resolver, or `change.v1_8.MovementChanges`. The shared player mixin behavior is separate from block collision dispatch; the current-main merge completed without conflicts and no semantic conflict was found.

The latest main advance after the first review merge, `64895d266fcee5da774c3236295ba949045200c7`, changes only Elytra review and integration documentation; it also has no fence or collision-hook overlap.

## Limits

The accepted source pair establishes a net difference in `(1.8.9, 1.9.4]`; it does not establish the first snapshot containing the change. The Minecraft Wiki Hitbox URL was a discovery lead only; direct retrieval was blocked by robots.txt and it is not evidence for the specific End Portal Frame predicate. The finding is supported by the exact decompiled Feather source files and verified manifests. Equivalence of the revised Feather-derived JARs to unavailable original derived JARs remains unproven. Runtime validation was not performed; no tests, build, game client, TAS, gym, server, Docker or push was run as instructed.
