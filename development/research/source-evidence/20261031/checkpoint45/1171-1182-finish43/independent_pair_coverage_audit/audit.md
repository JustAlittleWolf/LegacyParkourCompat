# Independent pair coverage audit: 1.17.1 → 1.18.2

## Verdict

The joined changed-path inventory and the assigned follow-up rosters close without a missing or multiply owned path. I independently rehashed all four canonical Mojmap trees, matched every one of the 4,118 route-row per-version hash cells to those manifests, and verified the disjoint open-route roster. The local-player tick/writer graph is covered through its movement terminals, world queries, contact callbacks, and direct response writers under the source assignment's equal-input assumptions. I found no unsupported material/fluid closure; the late fluid report's selected source hashes match the canonical files.

One newly isolated movement-relevant difference must be retained: `LocalPlayer.startRiding(Entity, boolean)` writes local player previous/current/head yaw from the boat yaw after a successful Boat mount in 1.17.1, 1.18 and 1.18.1; 1.18.2 removes those writes. This is a direct player orientation response consumed by player look/movement, not vehicle-computed pose. The report's exact mount gate and source comparison are independently verified below. Conditional tag and moving-piston receiver/query differences also remain evidence-backed. These are source findings, not runtime or trajectory claims.

No raw artifact re-download, packet/state producer equality, resource/tag payload equality, runtime parity, or trajectory witness is treated as a coverage prerequisite. Equal logical player inputs, valid block/entity state and tags, received values, and event/tick sequence are the comparison assumptions specified by the assignment. This audit makes no product, implementation or test claim.

## Exact source identity and join checks

The read-only source roots were `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.17.1,1.18,1.18.1,1.18.2}\mojmap`. I verified all manifest entries against file bytes and compared manifest path sets to recursive Java-file inventories:

| Release | Manifest rows | Java files | Missing / extra | File hash errors | Manifest SHA-256 |
|---|---:|---:|---:|---:|---|
| 1.17.1 | 4,142 | 4,142 | 0 / 0 | 0 | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` |
| 1.18 | 4,203 | 4,203 | 0 / 0 | 0 | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` |
| 1.18.1 | 4,203 | 4,203 | 0 / 0 | 0 | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` |
| 1.18.2 | 4,236 | 4,236 | 0 / 0 | 0 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |

The route table has 4,118 unique path rows, no duplicate paths, and no blank classifications. Its four hash columns match the independently rehashed manifest entries or expected absences: zero disagreements. The edge ledger has 12,839 rows and 12,839 unique edge/path keys; its non-unchanged rows reduce to 4,118 unique changed paths. This reconciles to 4,116 endpoint-changed paths plus two transient intermediate-only paths. The route classifications sum exactly to 4,118: 131 matched closed method/member routes, 1,159 open source paths, 356 previously unresolved routes, and 2,472 source-backed scope exclusions.

The 1,159 open rows now form an exact disjoint union:

| Follow-up roster | Rows |
|---|---:|
| Core, transport, NBT, and client timing | 73 |
| Client/network/resource/tag/tick/util | 305 |
| World entity/item/effect/inventory/food/physics | 491 |
| World/level, including its 221-row block sub-roster | 289 |
| `LocalPlayer.java`, separately owned by the tick/writer audit | 1 |
| **Union** | **1,159** |

Path-set comparison found zero overlaps, zero missing open rows and zero extra rows. The 356-row unresolved audit's disposition roster matches its input route set exactly (zero missing/extra paths) and gives every row a reason code and source-backed scope route. The block audit's 221 rows are contained within the 289-row world/level roster; they are not counted twice. Its named query/provider sources, the 68 other world/level paths, and their separate world/chunk, collision, entity-collision and material/fluid reports account for that broader route family.

## Independently retraced local-player movement graph

The source graph is continuous across all four releases under equal logical inputs:

1. `Minecraft.runTick` applies queued client tasks and calls the same capped number of `Minecraft.tick` iterations; `Minecraft.tick` and `ClientLevel.tickEntities` retain the phase order that reaches the local player.
2. `LocalPlayer.tick` reaches inherited living/player tick and the virtual `LocalPlayer.aiStep`; input and pre-travel decisions enter the `Player`/`LivingEntity` travel branches.
3. Travel can call virtual `LocalPlayer.move`; the wrapper calls inherited `Entity.move` and then evaluates auto-jump from committed horizontal displacement.
4. `Entity.move` clips against block, entity and world-border collision providers; commits position and collision flags; dispatches fall/contact and inside-block callbacks; updates fall and movement state; and applies block velocity factors. Later local-player ticks consume stored collision flags and movement state.
5. Out-of-band direct player writers reachable from the assigned source set include local responses to sleep exit, boat boarding, piston/collision callbacks, firework boost, and received position/motion/explosion/effect/respawn updates. Each is dispositioned by a named report; payload and event ordering remain explicit inputs.

The supporting source reports compare their named terminals and dependencies rather than inferring them from unchanged callers. World/chunk query and selected palette reads, collision iteration/providers, entity-collision enumeration, 119 shape-provider registrations/census, block callbacks, fluid/current calculations, client packet application, and the assigned core/transport path rows are each routed to bounded evidence or a source-backed exclusion. No uncovered changed route remains in the 1,159-row remainder.

## Movement differences and exact remaining members

The following conditional movement differences remain as source evidence and must not be collapsed into generic server, network, vehicle or world-state exclusions:

- `LocalPlayer.aiStep`: the sprint-stop continuation has a 1.18 minor-horizontal-collision exemption for an already sprinting player on the non-swimming path under the documented forward-impulse/food-or-flight gates.
- `LivingEntity.travel`: fall-flying lift changes from float table/cosine behavior to `Math.cos` and retained-double lift arithmetic when the local player reaches that branch.
- `LivingEntity.stopSleeping` → `BedBlock.findStandUpPosition`: beginning in 1.18, a candidate additionally requires strict X/Z overlap with the world border. This changes local wake position when an otherwise valid candidate is outside; bed-entry eligibility remains excluded.
- `Entity.move` / fall accounting (1.18.1→1.18.2): the pre-position fall-reset ray, tolerance-based X/Z velocity cancellation, and fall-distance cast order are source-confirmed conditional movement changes. The ray's block/fluid selector and `FALLDAMAGE_RESETTING` membership are query inputs.
- `ClientPacketListener.handleUpdateTags`: 1.18.2 binds each nonempty registry payload without the older in-memory-connection guard. Preserve as a conditional receiver difference for a valid nonempty tag packet on an in-memory connection; do not generalize to ordinary remote clients.
- `ClientPacketListener.handleBlockEntityData` → `PistonMovingBlockEntity.load` / `getCollisionShape`: the typed receiver accepts a piston block-entity payload that the 1.17.1 numeric allowlist skipped. The 1.18.2 source-piston extra-shape branch also adds a `PistonBaseBlock` type gate. Retain the report's conditional received-state/query case; ordinary piston source creation does not meet its extra-shape predicate.
- `LocalPlayer.startRiding(Entity, boolean)`: after `super.startRiding` succeeds and the entity is a `Boat`, 1.17.1/1.18/1.18.1 copy boat yaw into `yRotO`, current yaw and head yaw. The 1.18.2 override has no Boat yaw branch. This is a direct player orientation write and is in movement scope because local player orientation is consumed by player look/input/travel. It is conditional on successful boarding a Boat; it says nothing about boat position or physics.

Other changed candidates have explicit non-movement or no-difference dispositions: `LocalPlayer.sendPosition` changes outbound report cadence but has no immediate local position/velocity write under the equal-packet-sequence premise; the attached-firework hand anchor changes a non-player projectile hit query, while its direct local-player boost precedes that query and remains unchanged; and `ClientboundExplodePacket`'s direct receiver vector write is unchanged for equal valid payload and dispatch order. These are not generic taxonomy dismissals: each route has a named terminal comparison.

## Cross-checks, limits, and report revisions

- The fluid/material audit's 13 selected-source hash rows independently match the canonical bytes. `Material`'s relevant getters and `FlowingFluid.getFlow`'s material/ice gates do not differ under equal block-state inputs; fluid query/tag APIs preserve the queried result under equal membership. No material/fluid discrepancy was found. The separate 1.18.2 WATER fall-reset ray remains a previously identified movement operation, not a new fluid query delta.
- The Boat report's exact successful-mount and `instanceof Boat` gates and direct yaw writes are confirmed from the four canonical sources. Its initially published LocalPlayer hash cells were wrong; the author corrected them before this audit's final revision. Correct 1.18 and 1.18.2 source hashes are `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` and `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095`. The corrected report revision hash is listed below.
- Hashes bind the exact source/report revisions used. Selected method reports are bounded evidence and do not claim every changed byte of a class has movement meaning. Resource-defined tag membership, chunk readiness, raw inputs and packet/tick ordering remain stipulated inputs, not silently closed producer behavior.
- No test, build, game/server launch, source generation, decompilation, product source/history, wiki, or backlog was used.

## Immutable input/report hashes

| Artifact | SHA-256 |
|---|---|
| Pool38 full-source manifest integrity report | `7ef115fff11261303078b71109a533391364f8a9e3d0a3d709d5cc48555345f4` |
| Pool38 current coverage update | `799762eb31e998601c9f8ae2e2d7c71df176f1428c373fb81dcf29fc0e2b90af` |
| Pool38 scope correction | `f40354192569becd9efd395428a79848b3c0592e08ca33097ae75fcdbbc04e96` |
| Pool38 reconciliation | `cab0cfbc4cf28634a1564d63a69e55a0165453a2d2a1c5459e2a63bc749af942` |
| Route join report | `6f368c144bad200e6b71c944d7337133fdcb68ddfb06db9a4f88835b1911919c` |
| Changed-path route TSV | `5608d1ea9c9b1f4b3cf9aa8c2579d9c4cc566c426705596a27a9b05bd9aa9d95` |
| Edge path delta TSV | `5728ab3b19417c11f0fd6310c33cabc246f6ebac1aea38772e07b012a8609697` |
| Unresolved input roster | `e9aaedffc6d6968e3d3b9927ce8815efcae611e602f8df73669be71d410fd528` |
| Tick/writer graph audit | `999c9b59ef32c4706ff3e1c409e0487e3e37a50657616a33f5fb37e469cd8c3c` |
| Route unresolved audit | `9ce0100abd368e3fff0bd71509a424d74228019d9d9d01c6094c0c70fdb5842e` |
| Core/transport audit | `4f8b605ccb50e6da20d9c40fff784b15df041b19333655e9b5e2caf84fb55ac5` |
| Client/network audit | `733251d499ca54e9f5a1de179e5f4aded2ed6bfb6b9eec70c722fa94893fef9d` |
| Client world-gates/piston audit | `8d27a92677907d755fd5be8d2f2a3b2564f6697c7f0f961bb352e5e1170d725f` |
| World/entity audit | `2c2a8362224c66430866263bb96f240060218302f1a75250f10b5a46d3a4fde4` |
| Attached-firework follow-up | `cacc015bb9d1b581ac2f6a917c89f25d478894fedf56ddac140e27cd14312a08` |
| World/level block audit | `5490ca064b35814ac3f690bc95a04e4daf9f1b1da3acccea67f2865bdae29222` |
| World/chunk query audit | `f171427d7ac78d527e98c668d9ffa109bcc8bc5021403664b5f864d4d284b735` |
| Fluid/material query audit | `b41d409c94d1e40e87bf2444ef7ac5cde07f26c6f1fe97d5221cf1622edbcd76` |
| Boat yaw scope audit (corrected LocalPlayer source hashes) | `d84d19ba0ee08aec1bbe57e93dca0f2d3c06e6c2d39d62a4b355554a02c9a686` |
| Explosion packet receiver audit | `b91ead916a3679fca9bed1d10ba3500affb5ff70f00aee24f23a501ba47515c3` |
