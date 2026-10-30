# Terminal progress final-pass witness: 1.10.2 → 1.11.2

## Ruling

**Gate remains open: no paired, source-reachable local-player response witness is established.** The old-only terminal call is real and its candidate and push math can move an overlapping eligible player. The layout below demonstrates that conditional response, but not that an ordinary paired client history leaves the same eligible player in that overlap after the preceding `0.5 → 1.0` moving-block response and then after the player's own tick. I cannot conclude either that such a paired state exists or that it is impossible from the reviewed sources.

## Exact phase trace

The sequence uses stored float progress; render interpolation is not part of collision or direct-response selection.

1. In both endpoints, normal moving-block progress is stored as `0.0F → 0.5F → 1.0F`. In 1.10.2 `MovingBlockEntity.tick()` first copies `progress` to `lastProgress`, increments/clamps `progress`, then calls `moveEntities()`; on the next tick it copies `1.0F` into `lastProgress`, calls `moveEntities()` again, and only then removes/completes the block entity (`net/minecraft/block/entity/MovingBlockEntity.java:166–184`).
2. In 1.11.2 the `0.5 → 1.0` tick computes `f`, calls `moveEntities(f)` while the stored field is still `0.5F`, then commits `progress=1.0F`; the next tick copies `1.0F` to `lastProgress` and removes/completes without calling `moveEntities` (`:273–290`). This is the exact old-only call under review.
3. `World.tickEntities()` ticks players and ordinary entities before the ticking block-entity list in both versions (`1.10.2 World.java:1270–1324`; `1.11.2 World.java:1271–1324`). So on the terminal tick the local player runs first. Its collision query reads the stored moving-block state at progress one (and, in 1.10.2, `getShape()` also unions the stored `lastProgress`; after the preceding phase that is `0.5F`). Then 1.10.2's block-entity phase runs the final response; 1.11.2's phase removes/completes the moving block entity without that response.
4. The render interpolation method is `getProgress(tickDelta)` and is consumed by the render-offset methods (`MovingBlockEntity.java:72–89` old; `:64–90` new). The old direct response uses stored `progress` and `lastProgress` via `getShape()` (`:80–89`); the new collision provider reads stored `progress` in `addCollisions()` (`:318–345`). Neither terminal path uses a render-interpolated fractional progress.

## Candidate and overlap gate

In 1.10.2, terminal `moveEntities()` forms `this.getShape(world,pos).move(pos)`, passes that box to `world.getEntities(null, box)`, and processes candidates whose piston behavior is not `IGNORE` (`MovingBlockEntity.java:91–100`). The world delegates to loaded chunks; `WorldChunk.getEntities` requires strict entity-box intersection (`World.java:2156–2175`; `WorldChunk.java:667–679`). A normal nonspectator local player is not excluded by that query, and base `Entity.getPistonMoveBehavior()` is `NORMAL` (`Entity.java:2306–2308`). The query therefore requires a strict AABB overlap at the instant of the final pass; merely having been in the prior half-step's swept area is not enough.

For an explicit conditional calculation, take a retracting EAST-facing source piston at `(0,0,0)`. Its moved state is the extended EAST piston base, whose outline at zero retraction offset is `[0,0.75] × [0,1] × [0,1]` (`PistonBaseBlock.java:28–35,47–68`; source construction passes the current base state and `extending=false, source=true`, `:174–182`). If a nonspectator local-player box after its own tick were `[0.50,1.10] × [0.90,2.70] × [0.20,0.80]`, it strictly intersects that old terminal response box. The old algorithm chooses WEST, computes `entity.maxX - box.minX + 0.01 = 1.11`, and requests `entity.move(-1.11,0,0)` (`MovingBlockEntity.java:91–147`). With no west-side obstacle and no special movement gate, the existing overlap means `Box.intersectX` does not clip that westward request: it clips only if the player is already fully beyond the obstacle's EAST face (`Entity.java:446–537`; `Box.java:143–160`).

There is no corresponding terminal `moveEntities` call in 1.11.2. At stored progress one, its source collision collector adds the extended base and a `SHORT` source head. The EAST short arm is inside the base box, with the plate abutting it (`MovingBlockEntity.java:318–345`; `PistonHeadBlock.java:25,41,76–92`; `PistonBaseBlock.java:29,52–82`). This confirms the corrected short-arm closure does not supply a different terminal response: it is a player collision-list result before removal, whereas the old-only operation here is a later direct entity move.

## Why the conditional layout does not close the gate

The proposed player box overlaps the constant extended-base volume itself. It is not supported as an ordinary reachable post-player AABB by these sources. In particular, the comparison must begin before the previous block-entity phase: 1.10.2's preceding `0.5 → 1.0` call queries the union of its previous and endpoint outline boxes, while 1.11.2 executes its swept per-collision-box algorithm for that same transition before committing progress. The local player's subsequent ordinary tick also resolves against the now-terminal moving-block provider. No inspected accepted closure carries one common local-player AABB through those prior responses, the entity tick, and the proposed final overlap.

I cannot turn that gap into a proof of unreachability: the ordinary axis solver clips movement when the player is separated from a collision face, but it is not a general depenetration routine for an already-overlapping AABB. Thus an assumed embedded box can survive some movement calls, but the assumed box is not itself a source-derived history. A clear westward corridor and a nonsneaking, non-`noClip`, non-cobweb player would make the conditional old request straightforward; they do not establish how the player entered the base overlap or that the same paired state survives the preceding half-step.

The accepted extending-stair response is a different shape and earlier half-step response (`piston-response-closure/report.md`). The corrected piston-head closure is a terminal collision-list witness before the block-entity phase, not this later direct response. The World/fence closure establishes a pose-query/provider route and explicitly leaves its retraction trajectory open. None can be transferred to this final-pass condition. Keep the paired terminal response gate open; do not label it source-unreachable or movement-relevant on this conditional geometry alone.

## Evidence identity

Canonical read-only exact source roots are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.10.2,1.11.2}/ornithe-feather`, mapping family `ornithe-feather`. Source-manifest SHA-256: 1.10.2 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; 1.11.2 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`.

Directly rechecked relevant source-file SHA-256:

| Relative path under `net/minecraft/` | 1.10.2 | 1.11.2 |
|---|---|---|
| `block/entity/MovingBlockEntity.java` | `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1` | `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` |
| `block/MovingBlock.java` | `4f84cdf7c0d88c51ca065299c8755341d01b126d62c282c31fc0fa46bde1daf6` | `f2f99070cad0b64d8ecede16b44e0d0383e11c5b4e0c787edeaa2b8fc5c8af18` |
| `block/PistonBaseBlock.java` | `4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36` | `3a055f33949305827c3159bb5fbddf8bf51d0ec942f1b30f1fa59ff1bab9be64` |
| `entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` |
| `world/World.java` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` |
| `world/chunk/WorldChunk.java` | `dcfcda41f28cc205f17a547c69efdf70aede8f4770a43101d20ead08e8ed86b5` | `b80c4962661b31c9015faa9bb3d42e390528b59473956ba20e424c76625b844f` |
| `util/math/Box.java` | `f529ef075bd933b88e3bace9020a5e23cb18b65e816154520f10bd2f26f61155` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |

Supplied source-only evidence read (SHA-256):

| Input | SHA-256 |
|---|---|
| `moving-block-algorithm/closure.md` | `75453ea71abc269c57cf0611c829cfc73a66969f0b32232f59c0c3f9f8c3c304` |
| `moving-block-algorithm-audit/audit.md` | `282ebea57d6aecc60c4b85e86fa95c6fda82825b909ef8645e6eb5ccf319476f` |
| `piston-response-closure/report.md` | `2395b1f9c28502162d6c2e9467c319add9cf89def3841b0c085b9fc67075a6a1` |
| `piston-terminal-reachability/closure.md` | `1a31f80468852a5b2202a1e1f83fe4e29a8737114730e1065632b5a4f36ea622` |
| `block-entities-correction/correction-supplement.md` | `2deaea0c3c28fda56e546cda662435cd159368da9c34621c6435747269b1eeefd` |
| `world-moving/report.md` | `515d347cac54e6cca941a4839ae4e35860899390183f532ea78565d114ff3e51` |
| `fence-pose-reachability/closure.md` | `11eef02d786be42de91c1cfc769f67ede19041eeb4c6774639555bd950d6910e` |

Static exact-source trace and hashing only. No wiki, product implementation/history, backlog, tests, build, game/TAS launch, runtime inspection or canonical-source write was used. This witness does not establish an intermediate patch boundary, complete pair freeze, or runtime parity.
