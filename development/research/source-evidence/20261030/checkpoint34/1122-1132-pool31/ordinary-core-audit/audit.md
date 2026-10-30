# Independent ordinary-core source audit

## Bound inputs and method

Audited only the supplied ordinary-core report and supplement against the canonical read-only `ornithe-feather` sources for 1.12.2, 1.13, 1.13.1 and 1.13.2. Read `README.md`, `development/workflows/discovery/WORKFLOW.md`, and `development/workflows/discovery/source/WORKFLOW.md` for scope and evidence rules. No wiki inventories, product code/history, other research reports, or backlog were inspected.

The audited inputs match their requested SHA-256 identities:

| Input | SHA-256 |
|---|---|
| `ordinary-core/report.md` | `84ed37d35e8a1932868516081af999d88c9adb15834e90a4b006e90b20281e6c` |
| `ordinary-core/supplement.md` | `58b28bcfb820a88b49b1c4d65a7dc818df21c0a5dda6419ee5e316507dffadbb` |

For all four releases, the `ornithe-feather.ready.json` `sourceManifestSha256` matches the current `ornithe-feather.sources.sha256` hash. Provenance and ready markers identify the requested exact version and Feather mapping build; each cited `LivingEntity.java` hash also matches its entry in the source manifest. Manifest hashes:

| Release | `ornithe-feather.sources.sha256` SHA-256 |
|---|---|
| 1.12.2 | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13 | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 1.13.1 | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |
| 1.13.2 | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |

Relevant source hashes:

| Release | Relative source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.13 | same | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` |
| 1.13.1 | same | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.13.2 | same | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |

1.13.1 and 1.13.2 have the same `LivingEntity.java` bytes. Other checked hashes:

| Release | Relative source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.13.2 | same | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.12.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `e4e0fdbbe07a0d0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| 1.13.2 | same | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.12.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| 1.13.2 | same | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/util/math/Box.java` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |
| 1.13.2 | same | `0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d` |

`Input.java` (`9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e`) and `KeyboardInput.java` (`7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`) match byte-for-byte in 1.12.2 and 1.13.2.

## Findings and corrections

### Ground acceleration: confirmed, directly in scope

The report's core claim is correct: `LivingEntity.moveRelative` uses `0.16277136F` in 1.12.2 (line 1485) and `0.16277137F` in 1.13 (line 1526), then feeds `getSpeed() * constant/(friction*friction*friction)` to `updateVelocity` only on the grounded branch (1.12.2 lines 1481-1493; 1.13 lines 1521-1534). The latter literal is present unchanged in 1.13.1 and 1.13.2 (line 1545). The literal values are adjacent representable `float`s. This is a change to existing ground acceleration, not a modern-only feature, and is within README scope. The local player's inherited travel reaches this method. The report appropriately leaves the exact introduction patch unknown within the endpoints.

### Water jump: an existing mechanic changes, but the report paraphrases its gate too loosely

1.12.2 `LivingEntity.mobTick` applies `jumpInWater()` whenever jump is held and `isInWater()` is true, with no cooldown gate on that branch; `jumpInWater()` adds `0.04F` (lines 1413-1415, 1827-1839). In 1.13/1.13.1/1.13.2, `mobTick` branches on `f_85121000` and `onGround` (1.13 lines 1885-1895; 1.13.1/1.13.2 lines 1903-1917), while `m_74407200(Tag<Fluid>)` still adds `0.04F` (1.13 line 1452; 1.13.1/1.13.2 line 1471). The impulse magnitude is not the change; the selection/gating is.

Let `h` denote the stored fluid height. The 1.13 ordinary-jump branch is selected for dry ground (`h <= 0`, if grounded) or for a grounded player with `0 < h <= 0.4`, and the ordinary jump requires `jumpingCooldown == 0`. A positive-height airborne player takes the `0.04F` fluid impulse even when `h <= 0.4`; a grounded player with `h > 0.4` also takes it. Thus “shallow fluid enables ordinary jump” needs the grounded condition, and “at/below the surface” is not a precise description of `h`. This is a source-confirmed change to the pre-existing water jump response and is in scope. The paired endpoints establish only an unknown 1.12.2-to-1.13.2 boundary; the intermediate check establishes the logic by 1.13 and its retention through 1.13.1/1.13.2.

### Water travel: report misses a pre-existing baseline change

The report is right that the 1.13 water branch chooses `0.9F` drag while sprinting, keeps `getBaseMovementSpeedMultiplier()` for a non-sprinter, and suppresses the gravity section while sprinting (1.13 lines 1598-1630; 1.13.1/1.13.2 lines 1617-1649). However, “non-sprint water travel keeps ... `d/16` gravity” does not describe continuity with 1.12.2. In 1.12.2 the water branch applies `velocityY -= 0.02` when gravity is enabled (lines 1573-1580). In 1.13 the normal `d` is `0.08`, so the corresponding decrement is `0.005`, with a near-zero snap to `-0.003` in the stated velocity range (lines 1461-1465, 1625-1630). This is a material change to existing non-sprinting water travel and should be separately covered as in-scope. The new slow-falling override of `d` is a different, modern-only effect and is not itself a 1.12.2 emulation target.

The sprint multiplier/gravity changes also should not be characterized only as “swimming.” The 1.12.2 local sprint-key path can set sprinting while in water: `LocalClientPlayerEntity.mobTick` has a direct sprint-key branch at lines 734-741 with no water exclusion, and its stop branch at 743-745 has no water-specific condition. Therefore 1.13's water-travel behavior while `isSprinting()` is reachable against an existing 1.12.2 sprint state. Aquatic swimming's new pose and propulsion remain modern-only, but the changed water travel for the old sprint state needs explicit scope review and coverage.

### Contact scan / fluid-height timing: producer is located; contact geometry description is wrong and response coverage remains open

The supplement correctly locates the 1.13.2 normal-tick update order: `Entity.baseTick` calls `m_03231680` after `tickSprintingEffect` (Entity.java lines 393-395); that helper calls `checkWaterState`, caches eye submersion, then updates swimming (lines 947-981). `checkWaterState` calls `m_69693160(FluidTags.WATER)` (lines 961-977), which writes `f_85121000` at line 2569 before `LivingEntity.mobTick` performs jump/travel. The scan uses the contracted entity box, checks fluid-tag states and surface heights, and accumulates `d = max(surface - box.minY)` (lines 2509-2571). It returns early when the region is not loaded (line 2517), before assigning `f_85121000`, so the field is not necessarily refreshed on that path. `PlayerEntity.tick` separately updates eye submersion before `super.tick()` (lines 206-208, 257-260), and updates pose after it (lines 252-255); those timing statements are supported.

The supplement's description of the 1.12.2 water-contact box as “vertically expanded” is incorrect. The actual base-tick producer is `Entity.checkWaterCollisions` (baseTick lines 381-383; body lines 948-964), which passes `getShape().grown(0.0, -0.4F, 0.0).contract(0.001)` to `World.applyLiquidDrag`. `Box.grown` computes `minY - dy` and `maxY + dy` (Box.java lines 132-139), so negative `dy` contracts the box by 0.4 at both vertical faces; it does not expand it. The 1.13.2 water scan instead contracts the current box only by 0.001 and tests fluid surface height against its bottom. This is a changed producer for the existing `inWater` predicate and merits a separate paired movement finding/closure, not merely a fluid-height dependency note.

The producer replacement also leaves direct flow response unpaired. 1.12.2 `World.applyLiquidDrag` obtains per-block material drag via `Block.applyMaterialDrag`, normalizes the sum, and adds `0.014` when `hasLiquidCollision` (World.java lines 1579-1620). 1.13.2 `Entity.m_69693160` uses `FluidState.getFlow`, conditionally scales each sample by `d` below 0.4, averages samples, and adds `0.014` without the same player normalization (Entity.java lines 2538-2567). The helper/block flow details and the paired player consequence have not been closed by these reports. Treat current/fluid force as an explicit open movement dependency; do not infer a final trajectory without following the old and new helper semantics.

### Swimming-only player behavior: source facts are plausible; do not implement for 1.12.2

The supplemental source observations are supported: 1.13.2 `PlayerEntity.moveRelative` applies look-pitch vertical steering only under `isSwimming() && !isRiding()` and its stated look/jump/block-fluid gate, before inherited movement (lines 1442-1465). The swimming 0.6-by-0.6 pose branch appears before sneaking and spin attack (updatePlayerPose lines 334-360); Entity updates its swimming flag from sprint/water/submersion/riding conditions (Entity.java lines 947-959), with player flying/spectator exclusions. These are additions tied to 1.13 swimming (and spin attack), which did not exist in 1.12.2. README says post-profile features retain modern behavior. They are modern-only additions, not historical changes to emulate in a 1.12.2 profile. The report's “candidate” phrasing should not imply they are 1.12.2 fixes. Pose collision-query API semantics remain correctly open; there is no need to resolve them for a swimming-only behavior excluded by profile scope, although separate collision coverage may independently own that API.

### Local sprint gates: split modern swimming additions from changes to existing sprint behavior

The source comparison is accurate but the supplement narrows the meaning too much. In 1.12.2, direct sprint-key initiation has no `isInWater` gate, and sprint stop checks only forward input, horizontal collision and food/fly eligibility (LocalClientPlayerEntity.java lines 719-745). In 1.13.2, direct initiation requires `!isInWater() || isSubmergedInWater()` (lines 738-746), while stop logic distinguishes `isSwimming()` and also cancels non-swimming sprint when in water but not submerged (lines 748-757). The new double-tap path permitting initiation while submerged is a swimming addition and modern-only. But the altered direct/stop rules also change pre-existing sprint behavior in wet, non-submerged states, where the 1.12.2 direct path could sprint. That state-gate delta should be reviewed as a change to existing sprinting and its water travel, rather than blanket-dispositioned as swimming-only.

### Input comparison: limited claim confirmed

`Input.java` and `KeyboardInput.java` are byte-identical at both endpoint releases; keyboard axis/key/sneak behavior therefore matches for those classes. `LocalClientPlayerEntity.serverTickAi` also copies sideways, forward and jump input to the movement controls in both releases (1.12.2 lines 631-636; 1.13.2 lines 635-640). This supports the supplement's bounded claim. It does not mean the full input-to-sprint behavior is unchanged: the local sprint control gates differ as described above. The original report's “no input traversal” is superseded by the supplement for these slices only; it is not evidence for all possible input providers or platform-specific plumbing.

## Audit disposition

The report's two confirmed candidates are not exhaustive: ground acceleration is accepted as stated; fluid jump is accepted with the gate correction above. The report/supplement have established an intermediate presence check for the LivingEntity candidates but not full-pair freeze. Add/close separate findings or explicit scoped dispositions for (1) the default non-sprint water-gravity reduction, (2) changed existing water-contact geometry / `inWater` production, (3) direct fluid-flow response, and (4) the pre-existing sprint-in-water travel and local gate changes. Keep actual swimming pose, swim-pitch propulsion, new submerged double-tap sprint initiation, Dolphins Grace and Slow Falling as modern-only behavior for a 1.12.2 profile. Remaining broad movement inventories (effects/equipment producers, collision dependencies, external direct player responses) remain open as the report and supplement state; this audit does not close the pair.
