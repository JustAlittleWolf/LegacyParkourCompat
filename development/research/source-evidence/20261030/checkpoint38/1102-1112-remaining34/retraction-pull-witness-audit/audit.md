# Independent source audit: retraction pull witness

## Verdict

**At the stipulated response-entry AABB, the paired response sequence closes at source level under the explicit world and piston-budget assumptions. The witness does not establish a complete prior-control trajectory.** The 1.11.2 primary request, clipping, full-cell overlap, equal-clearance gate, second request, and conditional accumulated-cap arithmetic are supported. The 1.10.2 request and final westward displacement also remain as reported, but its cited moved-block shape is wrong: the source-retraction event gives the old moving block an **unextended full-block base state**, not the extended 0.75-wide base. This changes the old union's maximum X, not its minimum X or the claimed requested/final westward movement in the stated unobstructed case.

The verdict is therefore not unconditional for a produced gameplay trajectory. The stipulated AABB is collision-compatible with the preceding upward `+0.05` request on both sides, but source history that generates that request was deliberately not traced. Also make the sticky-piston arrangement explicit: air at `(2,0,0)` is needed so the retraction does not pull a second block into `(1,0,0)` and add another moving-block collider. The 1.11.2 piston budget likewise assumes no earlier PISTON displacement on this player's X axis at that same world time (or an explicitly known accumulator).

## Exact source identities

The canonical read-only roots are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.10.2,1.11.2}/ornithe-feather`, mapping `ornithe-feather`. The source manifest SHA-256 values match the witness: 1.10.2 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; 1.11.2 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`. Both verification records identify the published source tree as verified identical to the input source files (1.10.2: 1,845 files, zero source differences; 1.11.2: 1,921 files, zero source differences). Audited witness SHA-256: `8d994f9cf76187ee0cfbe7d6f9940071eb2982fe3e9af3219502c67408fb58fc`.

The SHA-256s below are rehashed from the canonical files inspected for this audit:

| Relative source file | 1.10.2 SHA-256 | 1.11.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/block/entity/MovingBlockEntity.java` | `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1` | `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` |
| `net/minecraft/block/PistonBaseBlock.java` | `4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36` | `3a055f33949305827c3159bb5fbddf8bf51d0ec942f1b30f1fa59ff1bab9be64` |
| `net/minecraft/block/PistonHeadBlock.java` | `9e8bd56219d3a00e57082481d6e17a2c9383e994dac6cecac2534918f4aeb579` | `da30307abf1e88698a220ab3b61538be9b2f5f16d6648c8f41a2671dc06b7ed9` |
| `net/minecraft/block/MovingBlock.java` | `4f84cdf7c0d88c51ca065299c8755341d01b126d62c282c31fc0fa46bde1daf6` | `f2f99070cad0b64d8ecede16b44e0d0383e11c5b4e0c787edeaa2b8fc5c8af18` |
| `net/minecraft/util/math/Box.java` | `f529ef075bd933b88e3bace9020a5e23cb18b65e816154520f10bd2f26f61155` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` |
| `net/minecraft/world/World.java` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `af3efba0a1d5a0824c29c4af4ac7f8e4484d1787a82208e4d72bcc65f957e656` | `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175` |
| `net/minecraft/client/Minecraft.java` | `0979d770e8742a07bb8c54cf7fd3449a8c43a4f48910600ffabf75f1f67ba2d9` | `3ca93038adf4cea512c21ae86d653376e963396640a02eb50cb42a67302f6236` |
| `net/minecraft/util/math/Direction.java` | `10bd074fa46e27658e7109836fe5456fa1f2017a15401f28fc12900d942f02ef` | `116a61f67e93f895d29c71128b392fbde5f1ca85f1b51d9f7fce55a0dd5960bf` |

The original witness's hashes for its eight listed files match. Additional hashes above bind the exact source files used for this audit.

## Findings and corrections

### Retraction creation and ordering

Both `PistonBaseBlock.checkExtended` methods schedule type `1` with `direction.getId()` when the extended piston should retract: 1.10.2 `PistonBaseBlock.java:112–121`, 1.11.2 `:116–125`. `World.addBlockEvent` directly invokes the current block state's `doEvent` in both endpoints (old `World.java:2577–2579`, new `:2553–2555`); the client packet handler makes that call after `PacketUtils.ensureOnSameThread` (old `ClientPlayNetworkHandler.java:1047–1051`, new `:1079–1082`). The type-1 case replaces the base position with a moving-block state and creates the source BE with `(extending=false, source=true)` (old `PistonBaseBlock.java:168–182`, new `:172–186`). `Direction.EAST` has id `5` (old `Direction.java:18`, new `:20`). The base `World.tickEntities` phase calls `tickPlayers`, then iterates ordinary entities, then block entities (old `World.java:1270–1315`, new `:1271–1315`); the local player is inserted into the client world entity list during world setup (old `Minecraft.java:1906–1914`, new `:1887–1895`). This supports a retraction BE response after the player's movement phase when the block event has already been handled before that tick.

### Prior `+0.05` move

For the stated start box `x=[1.01,1.61], y=[0.70,2.50], z=[0.20,0.80]`, the requested upward `+0.05` becomes the claimed response-entry box if the ordinary player dimensions are held fixed. Its Y-positive clip test cannot activate against the source head/base colliders: the player's starting `maxY=2.50` is already above their minimum Y; the 1.11.2 moving head arm ends at `0.625`, its plate starts at `x=1.75`, and the extended source-base collider ends at `x=0.75`. The old corresponding collision test is also unclipped because its positive-Y `Box.intersectY` branch requires `player.maxY <= obstacle.minY` (old `Entity.java:514–522`, `Box.java:163–177`). This establishes local collision compatibility of the stipulated request, not its production by an input/velocity history.

Terminology correction: 1.11.2 has `Entity.move(MoverType,...)`, so the prior call may be specified as `MoverType.SELF`. In 1.10.2 `Entity.move` is untyped (`Entity.java:446`); describe the corresponding old call as the ordinary local/entity movement request, not as a source `SELF` enum value. The precise prior controls/velocity and call site remain unverified by the witness, which explicitly leaves this history open.

### 1.11.2 primary push, pull gate, and budget

At the first response tick the 1.11.2 BE has stored progress `0`; `tick` computes `f=progress+0.5`, calls `moveEntities(f)`, then stores `progress=f` (`MovingBlockEntity.java:272–289`). Thus the helper uses `d=0.5` while `moveBox` uses the current progress offset of `1` (`:116–123,203–208`). A source retraction selects a full east-facing piston-head state for direct movement (`:107–114`); the EAST plate translated by one has `[1.75,2]` in X, and its westward half-step swept area is `[1.25,1.75]` (`PistonHeadBlock.java:25,64–68,70–85`; `MovingBlockEntity.java:210–229`). The entry box intersects that swept area, giving `e=1.61-1.25=0.36`, and request `min(e,0.5)+0.01=0.37` west (`MovingBlockEntity.java:143–160,247–249`).

For the call, `NOCLIP_DIR=WEST` causes `addCollisions` to add the extended base shape `[0,0.75]` at P and suppress the moving head collider while progress is below one (`MovingBlockEntity.java:318–345`; `PistonBaseBlock.java:28–68`). The negative-X solver clips at `0.75-1.01=-0.26`, so the resulting box is `[0.75,1.35]×[0.75,2.55]×[0.20,0.80]`; it intersects the full piston cell with positive volume. The source-only helper sees both full-cell clearances as `0.26` (`MovingBlockEntity.java:231–245,247–249`), passes the strict `<0.01` comparison with difference zero, and requests `min(0.26,0.5)+0.01=0.27` east.

`Entity.move(Piston)` caps and records the requested per-axis delta before collision solving, with the `[-0.51,0.51]` bounds (`Entity.java:464–499`). From zero prior X accumulator, the first request records `-0.37`; then the opposite `+0.27` remains wholly within bounds (net recorded delta `-0.10`). This confirms both calls only under the witness's explicit no-earlier-X-PISTON-move condition. `LocalClientPlayerEntity.move` calls `autoJump` afterward, but its auto-jump predicate requires `onGround` (`LocalClientPlayerEntity.java:823–838`). Starting airborne and making horizontal PISTON moves leaves `onGround` false; the disabled auto-jump setting is consequently sufficient but unnecessary for this particular state.

### 1.10.2 primary response: correct moved-state shape

In both `PistonBaseBlock.doEvent` implementations, the type-1 BE constructor receives `this.getStateFromMetadata(data)`. Here `data` is the scheduled direction id `5`, so `getStateFromMetadata` sets `EXTENDED` false because `(metadata & 8) > 0` is false (old `PistonBaseBlock.java:182,348–350`; new `:186,336–338`). **In 1.10.2 this is an unextended base state.** Its `getShape` returns the full block shape, not the 0.75-wide extended east shape (old `PistonBaseBlock.java:48–68`). The old BE does not replace this with a head or force `EXTENDED` true: `MovingBlockEntity.getShape` asks `movedState.getShape` and unions the two translated shapes (old `MovingBlockEntity.java:80–89`). At first response, progress/current offsets are `0.5` and `1`, so the correct old union is `[0.5,2]×[0,1]×[0,1]`, not `[0.5,1.75]×[0,1]×[0,1]`.

This correction does not change the witness's old direct response arithmetic: the box still has `minX=0.5`, so the request is `1.61-0.5+0.01=1.12` west. The player begins overlapping the old moving block collider (`minX=1.01 < maxX=2`); the old negative-X clipping branch requires `player.minX >= obstacle.maxX`, which is false (old `Box.java:143–160`). With the old swept route free of other colliders and no special sneaking/step condition changing this request, the full `-1.12` applies, yielding X `[-0.11,0.49]`. There is no old second helper call (`MovingBlockEntity.java:91–150,166–184`).

### Remaining setup gates

1. The scenario says sticky piston but does not specify the block at `(2,0,0)`. Retraction code checks that block and may call the ordinary pull mover, creating a second moving block at `(1,0,0)` (`PistonBaseBlock.java:183–204` in 1.10.2; `:187–208` in 1.11.2). Add that `(2,0,0)` is air (or choose a nonsticky piston) for the calculated isolated response.
2. Keep the 1.11.2 accumulator condition explicit: zero X PISTON delta from earlier same-tick movement. If another moving BE has already pushed this player, recalculation must include its recorded delta.
3. The `+0.05` prior request is a stipulated entry-state construction. Without tracing an exact input/velocity route, the source evidence establishes the paired callback response for that AABB, not reachability of the complete preceding player trajectory.

## Audit boundary

This was a static exact-source audit only. I did not consult wiki material, product implementation/history, the backlog, or use tests, builds, game launches, simulation, or decompilation. No canonical decompiled source was modified.
