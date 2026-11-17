# Source coverage: 1.11.2 → 1.12

## Assignment and exact inputs

Scope is the exact source-only comparison of `decompiled_minecraft/1.11.2/ornithe-feather/` to `decompiled_minecraft/1.12/ornithe-feather/`, targeting deterministic local-client player movement. Sources were read-only. No product implementation/history, wiki material, other pair reports, build, test, game, or runtime was consulted. The frozen 1.11.2→1.12.2 endpoint inventory is excluded and does not provide acceptance for this exact edge.

- 1.11.2 publication: root `ornithe-feather.ready.json` SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`; `ornithe-feather.sources.sha256` SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; 1,921 source entries.
- 1.12 publication: mapping-local `.decompile-provenance.json` SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`; 2,048 source entries. Missing root readiness markers are not a source input gap.
- Neutral projection validation: 1,905 common paths, 143 added, 16 removed, 573 common-file hashes changed, zero manifest/source mismatches across 3,969 entries. Changed path counts by classification include 117 block, 91 entity and 145 client Java paths; these are source inventory counts, not behavior findings.

### Exact hashes for inspected sources

| Source | 1.11.2 SHA-256 | 1.12 SHA-256 |
| --- | --- | --- |
| `net/minecraft/block/FenceBlock.java` | `856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7` | `633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e` |
| `net/minecraft/block/WallBlock.java` | `17da6d881365e4dce5134f88cb9a9e5273d1fa1734812d13410dc6924a3acbfd` | `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50` |
| `net/minecraft/block/PaneBlock.java` | `1e8968e7d642358cce90f0d3156f07f19c5bca28d2008d5d2d4bf0eccc7266a9` | `83862f6633b8241c3788e9c9a7e4fd0275c010ec790019b8003908996f4814b3` |
| `net/minecraft/block/BedBlock.java` | `84f56aab19adae4b953a27040e5f431f65c8923d6bd169995ac5a5f629a024e1` | `48c127b2df425cb8d7ff60a284207de10a8408e90ee18a8449522a004a0b4369` |
| `net/minecraft/block/LiquidBlock.java` | `c91618c8445dd488dd61f4a01aed0799dae2faaf3585bc6f2d5164891e988244` | `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260` |
| `net/minecraft/block/FenceGateBlock.java` | `62cf70369634810d50137d846be46f3901def38df38ee0366df2a0b06268bb67` | `38ae9e6aa1057a8e9b65ff78414d0d6a9a70cf2645f1ea42141ff2815ad6a477` |
| `net/minecraft/block/StairsBlock.java` | `5df1782da6eb7acdb9935b3910a0484d6417661bfd6230ecf81df0de6d782627` | `c52a011a759b6f3ef6d45162d3b61f464ceceee5d2d8735de6f6e25a56e55611` |
| `net/minecraft/block/Block.java` | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| `net/minecraft/world/World.java` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8` |
| `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` | `6a9d5f420b13625195b149c0d9468cf1284d5eb7dc1b2a46cf01b952a27ee063` |

## Pair-specific source candidates

- Fence neighbor-face collision: `findings/fence-neighbor-face-collision.md`, SHA-256 `3c5bb22d0700858739e0815ffc295c119aab0787d846705c02fd9db4835c6066`.
- Wall neighbor-face collision: `findings/wall-neighbor-face-collision.md`, SHA-256 `d9012e7c589f6a72a0716f17e19ab43a060772c8c1e5bd93df371e5656ac1d8d`.
- Pane neighbor-face collision: `findings/pane-neighbor-face-collision.md`, SHA-256 `a9d9c1ecc4ab32b8e8b805d38fdda431a6903988bdc1bfca624db4980c21ca2e`.
- Bed bounce on vertical collision: `findings/bed-bounce-collision-response.md`, SHA-256 `30b0194e7b5ac3b4da60e0b6a3a97fcb853774f1d7609e919d552f4fefd56d4a`.
- Liquid flow face classification: `findings/liquid-flow-face-classification.md`, SHA-256 `54839855489d9f07faf9b8444b20832919ffa507e350201f43ed9b620b120b74`. 1.11.2 treats solid-material stairs as solid; 1.12 excludes stairs, changing the downward adjustment of flow for `LEVEL >= 8` liquids and the normalized 0.014 local-player velocity input.

These drafts are source candidates only. No independent finding acceptance is claimed and this report does not claim pair freeze.

## Examined movement/provider slices

### Collision routes and candidates

- In 1.12, `block/state/StateDefinition.java` state forwarding at `State.getFaceShape(WorldView, BlockPos, Direction)` delegates to `this.block.getFaceShape(world, this, pos, face)` (415–416). The 1.11.2 source has no corresponding face-shape API; this forwarding is the dynamic query route used by the three collision-connection predicates and the liquid flow rule.
- `FenceBlock.shouldConnectTo` changes from accepting a same-material fence, any gate, or a solid-blocking cube except pumpkin (1.11.2: 124–131) to querying the neighbor face shape (1.12: 124–134). `resolveVirtualProperties` feeds the directional bits used by unchanged `addCollisions` emission (59–80). Center and four arm boxes are unchanged and rise to Y=1.5. `FenceGateBlock.getFaceShape` in 1.12 reports `MIDDLE_POLE` only on horizontal faces whose clockwise-Y axis matches the gate's facing axis (175–181); other faces are `UNDEFINED`. `StairsBlock.getFaceShape` (140–161) adds shape/facing/face-dependent solid-face classifications.
- `WallBlock.shouldConnectTo` changes from same wall / any gate / solid-blocking cube except pumpkin (1.11.2: 148–157) to a direction-aware face-shape rule (1.12: 146–152). Neighbor bits feed unchanged shape-indexed wall collision boxes (`addCollisions` and `getCollisionShape`); the table contains boxes reaching 1.5 high. Gate orientation and stair face shape can change these bits.
- `PaneBlock.shouldConnectTo` changes from default-state cube or glass/pane block checks (1.11.2: 136–144) to non-exception `SOLID` faces or `MIDDLE_POLE_THIN` faces (1.12: 136–159). `AbstractLeavesBlock` inherits `Block.getFaceShape` default `SOLID` in 1.12, but is explicitly excluded. Its old default state is a cube and has no old pane exception, so a pane's directional collision arm changes beside leaves. Pane collision emission and arm boxes are unchanged (54–78).
- In all three connection families, the resolved virtual connection state is computed while collision shapes are obtained; these are not persistent historical map-state transformations. `World.getCollisions` collects block collisions, and `Entity.move` queries an expanded box and sequentially clips Y/X/Z, so a differing arm can alter local-player clipping.
- `BedBlock` adds `beforeCollision(World, Entity)` in 1.12 (141–150). Both versions' `Entity.move` call the block hook when vertical displacement is clipped (around 698–727). In 1.11.2, the bed inherits `Block.beforeCollision`, which sets `velocityY = 0`; in 1.12, a non-sneaking entity with negative vertical velocity receives `velocityY = -velocityY * 0.66F`; sneaking still uses the base hook. The 0.8 multiplier is only for non-living entities. This direct landing response is distinct from excluded bed-entry eligibility.
- `Block.beforeCollision` and `SlimeBlock.beforeCollision` bodies were spot-compared as unchanged; only the bed adds the distinct override.
- `LiquidBlock.getFlow` keeps the same accumulation and normalization. Its deep-level neighbor-side test changes because `isFaceSolid` now uses attachment exceptions and FaceShape; `World.applyLiquidDrag` still normalizes and adds the result to entity velocity. `PlayerEntity.hasLiquidCollision` remains `!abilities.flying`, and water collision checks call the world method, closing the player route for this bounded behavior.
- A paired body-diff screen across common block sources examined `addCollisions`, `getCollisionShape`, `getShape`, `isFaceSolid`, `beforeCollision`, `applyMaterialDrag`, `onEntityCollision`, `onSteppedOn`, `shouldConnectTo`, `resolveVirtualProperties`, `getFaceShape`, `getFlow`, `canSurvive`, and `canAttach`. Changed movement-relevant bodies were the fence/wall/pane connection rules, bed collision response, and liquid face/flow rule described above. The other changed bodies located by that screen were FaceShape declarations or placement/support/survival logic; no other changed collision-box constants or `Box` constructor lines were found in the block diff screen. Treat this as a bounded source scan; the fresh auditor should validate the extraction and close each remaining provider/input dependency.
- `FenceBlock` provider emission, `WallBlock` shape-table selection and `PaneBlock` provider emission were checked within the compared class bodies. `World.getCollisions` and collision clipping in `Entity.move` were inspected at their relevant paths; full line-by-line method and dependency comparison remains open.

### Tick and other source screens

- `LivingEntity.tickMovement` passes two input axes to `moveRelative` in 1.11.2 and passes `sidewaysSpeed`, `verticalSpeed`, and `forwardSpeed` in the 1.12 three-axis form. The complete source search finds the only 1.12 write to `verticalSpeed` in `MobEntity`, a sibling of `PlayerEntity`; there is no player-hierarchy write, so the local player value stays at its default zero. With that zero, the added vertical term in `Entity.updateVelocity` contributes no local-player axis input and the horizontal formula reduces to the old two-axis path. This is a bounded no-difference result for the extra movement axis; the complete travel body remains open.
- The gliding branch uses `getLookVector`. This resolves to `LivingEntity.getLookVector()` using head yaw in 1.11.2 and inherited `Entity.getLookVector()` using yaw in 1.12. The local player enters `serverTickAi()` through `LivingEntity.tickMovement` before travel; `PlayerEntity.serverTickAi()` assigns `headYaw = yaw` in both versions, so these vectors coincide for the local player at that call. This closes the observed look-vector change for the checked local-player gliding path.
- The input, keyboard-input, client-player and local-client-player files were diff-screened. `Input` and `KeyboardInput` showed no textual diff. `LocalClientPlayerEntity` movement-network logic showed no movement hunk; additions inspected were recipe-book/tutor related. `ClientPlayerEntity` had no diff hunk in the queried movement scan. These checks do not establish complete player tick closure.
- ClientPlayNetworkHandler `handlePlayerMove` relative-coordinate / velocity-zero handling and `handleExplosion` player velocity addition were spot-compared and are unchanged. This bounds those two network response bodies only; other packet and event writers remain unreviewed.
- `LivingEntity.stopRiding` invokes the changed `dismountRider` only when `!world.isClient`; this bounds that changed placement path away from the local-client player. Vehicle behavior remains outside the movement scope.
- `Entity.checkWaterState` is new and the searched caller is `FlyingWaterAvoidingWanderAroundGoal` (non-player AI). `Entity.onBlockCollision` is an empty base hook; its only override found by the search is `ServerPlayerEntity`, not local client. These call-site screens bound those paths away from local-player movement, subject to the still-open complete tick graph.
- Changed FaceShape screening identified 63 changed block Java files with collision/face-shape markers; 40 contain collision-provider methods and 62 contain `getFaceShape`. This only locates candidates, it is not a body audit. The changed group includes fence, wall, pane, gate, stairs, button, torch, tripwire hook, liquid, piston base/head, snow layer, ladder and other providers/consumers.
- Vine, ladder, button, lever, torch, snow and tripwire now use face-shape rules in placement/survival or support-state paths. These were screened as state-production behavior and are not promoted to movement findings while preserving vanilla block states. Their complete callback/state dependency disposition is still open.
- `ChorusPlantBlock` adds a face-shape result of `UNDEFINED`; its provider geometry was spot-checked with no paired geometry delta. `ShulkerBoxBlock` dynamic collision/push/move logic was spot-checked unchanged and is an attachment exception. These are bounded no-delta screens only.

## Still partial: required coverage not closed

- Full changed collision-provider review is still open despite the bounded method-body scan above: static/dynamic shape dependencies, state and neighboring-block queries, callbacks and provider consumers need a full inventory and independent paired disposition.
- Full ordered local-client movement tick: input pre-travel decisions; water/lava/elytra/travel branches and post-travel; jump/sprint/sneak state; position/velocity/pose/dimension/attribute/equipment/effect writers; and their inherited callers/consumers are not exhaustively closed.
- Full direct player response/event inventory: piston, portal/teleport, status/effect, block collision/step, fluid and other client event writers were not fully paired. A route must be traced before excluding it as server-only, non-player, rendering, damage calculation or vehicle behavior.
- Fence/wall stair state/facing/direction combinations, and all newly classified neighboring faces, need full dependency enumeration.

## Closure and next action

Pair coverage remains **partial**. A fresh blind independent reviewer must retrace the four immutable candidates and complete the paired tick, provider and external-response inventories under this exact input identity. Every terminal row needs paired evidence and closed dependencies before pair freeze. Do not transfer the 1.11.2→1.12.2 endpoint verdict to this edge. No permanent finding was published by this worker.


