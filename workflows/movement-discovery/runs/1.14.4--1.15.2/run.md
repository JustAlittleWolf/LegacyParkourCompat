# Discovery: 1.14.4 to 1.15.2

- Status: partial
- Scope: client player movement; older A = exact Java Edition 1.14.4; newer B = exact Java Edition 1.15.2.
- Repository revision and start date: `c133c2999b6673874e35bbdb26759548407f3e11`; 2026-09-26.
- Selected naming namespace and alignment: Mojang official names / Mojmap for both releases. Each exact release used its own official `client.txt` mapping artifact. This is the same naming family, not cross-application of one release's mapping to the other. Requested and resolved IDs match both sides.
- Source preparation: A owner command `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.14.4 --mappings=mojmap`; owner checkpoint `29f6a2d` records `Finished 1.14.4 using mojmap`. B command `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.15.2 --mappings=mojmap`; success transcript extract is [Source preparation record](#source-preparation-record).
- Log limitation: neither owner's raw console log is retained. A's committed provenance record is checkpoint `29f6a2d`; B's successful-output transcription is labeled as an extract, not a raw log. Both report exact-version successful completion.
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1; decompiler JVM 25.0.3+9-LTS; target bytecode Java 8; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; Gson 2.14.0; ASM 9.10.1; default decompiler heap 4G. Separate isolated Gradle user homes were used.

## Artifact manifest

### A — Minecraft 1.14.4

- Source root: `decompiled_minecraft/1.14.4/mojmap` (shared ignored source root).
- Exact requested/resolved release: `1.14.4` / `1.14.4`.
- Original client jar SHA-256: `B3B2A798E2D67B566008FE4A03767AE2C7FF3F8C7BA6751E7B71FC7299672D0A`; size 25,191,691 bytes. Client artifact object `8c325a0c5bd674dd747d6ebaa4c791fd363ad8a9`.
- Version metadata SHA-256: `615F466A39D2C19AE9B6E2401AA7BD07DBDA337F976BCCE383326B8D6BA4E532`.
- CLI mode/namespace: `mojmap`, Mojang official names. Mapping object `6073e4ba6949217eb708c4512be2ccc1850a603f` (`client.txt`); mapping file SHA-256 `2DD53A5E70BA493CF6E33C0FC52BBDF4C57F9429C7A13842565AA825FD44D910`.
- Remapped client jar SHA-256: `7781BDCC8E8D9173173731F2564866F17C07CB43FE602A9A9F14B2753DC34665`.
- Decompiled source count reported by the owner: 3,250 Java files. Independently checked anchor `net/minecraft/client/player/LocalPlayer.java` SHA-256 `0795C1223198CE5ACF5D2ED9E5DB8435BBEC4CD52B96F96B1DDF2865BAAAE85F`.
- Owner reported three remapper invalid-access warnings for `ClientPacketListener` / `MapRenderer$MapInstance` and an access repair for one class; Vineflower reported duplicate `ModelBakery` lambda processing. No warning was identified for the movement slices used below; inspect any newly discovered relevant damaged body before relying on it.
- Owner source generation/provenance checkpoint: `29f6a2d`; raw log was not retained.

### B — Minecraft 1.15.2

- Source root: `decompiled_minecraft/1.15.2/mojmap` (shared ignored source root).
- Exact requested/resolved release: `1.15.2` / `1.15.2`.
- Original client jar SHA-256: `4A73008A73F3824B7C711750A5A37556DF8614F193C0A531E292DAD159A73A7C`; size 15,531,492 bytes. Client artifact object `e3f78cd16f9eb9a52307ed96ebec64241cc5b32d`.
- Version metadata SHA-256: `E974511243E845B2427AB635EBA5612481CB420A70C0630AA041EAB3EEB590C5`.
- CLI mode/namespace: `mojmap`, Mojang official names. Mapping object `1fbf9f0bc9c326af859b3ccf71c2a8f5edc47ef8` (`client.txt`); mapping file SHA-256 `65AD295B6CF63821F5D8F961C128128475E6D39386358D22EB238A3CE6E31777`.
- Remapped client jar SHA-256: `9B78CB6363696CA878D18B3A51A2E5D4AC626C9E06A4FA91B0B827FA85C2961A`.
- Source preparation extract: [Source preparation record](#source-preparation-record); decompiler warnings and exact successful completion are recorded there. The task reported no decompilation error.

### Per-finding source/resource hashes

Record each cited source path and SHA-256 in its finding. Paired tag resources and their hashes are in [Paired block and fluid tags](#paired-block-and-fluid-tags). Do not commit generated source trees, client jars or mapping files.

## Correspondence and call order

The two sources use a shared Mojmap namespace. Resolve each compared role through exact class/member signatures, inheritance, callers and state order in the coverage ledger and individual findings. Same-name classes or methods alone do not establish correspondence. Source-level candidate paths are indexed in [Newer-side source inventory](#newer-side-source-inventory).

## Coverage ledger

See [Detailed coverage ledger](#detailed-coverage-ledger) for the stage-by-stage audit, terminal rows, and open coverage gaps.

## Finding index

Findings are tracked one per file under `findings/`; add only after the bounded slice and dependency closure are source-confirmed.

## Resume checkpoint

- Completed: exact source provenance for both releases; input correspondence; paired block/fluid tags; friction, Soul Sand, Honey speed/jump/slide, portal dismount and Bee player-knockback findings.
- Additional paired work: F08–F14; ability storage and network transfer; bounded collision clipping, support-cell and edge-backoff paths; common player-facing push overrides and piston restriction; movement attributes and Depth Strider; mounted End Gateway teleport propagation; hidden movement-effect fallback.
- Next: continue the open rows in [Detailed coverage ledger](#detailed-coverage-ledger), especially remaining player-state writers, living travel, block/fluid callbacks and formulas, remaining effects/equipment and mounted/external updates. The bounded movement-attribute and Depth Strider correspondence is recorded in [Stage 6 modifier correspondence](#stage-6-modifier-correspondence); incoming entity-motion packet encoding/application and its bounded server send paths are recorded in [Stage 7 client correction correspondence](#stage-7-client-correction-correspondence).
- Exact next safe action: resume source-only work by inventorying the remaining player-state writers and consumers in both exact-release source roots, recording bounded method ranges and producer/consumer dependencies in the detailed coverage ledger. Keep the run partial until each candidate is closed with paired evidence or explicitly left open.
- Local-main handoff reconciliation: merged the verified local `main` tips `3b2b4c0a3fc309f5ddcc8661ff5d736fbe0c989f` and, after `main` advanced during the merge, `d8f3956602da94bf0cf67753cc0a9f4665397729`.
- Assumptions: no gameplay trajectory was observed; findings are source-level. Server-synchronized state is identified as externally authoritative rather than locally computed.

## Source audit closure

- Coverage status: partial; fourteen source-confirmed findings, a terminal input-refactor row, and bounded no-difference ability-transfer, collision, push, piston, attribute/effect, Depth Strider, water-current, player position-correction, and incoming entity-motion correspondences are recorded. Other navigation stages have explicit open coverage gaps in [Detailed coverage ledger](#detailed-coverage-ledger).
- Unresolved gaps: remaining paired player-state, living-travel, collision-order, block/fluid consumer, effect/equipment and external-update audits.
- Evidence/hash/correspondence audit: exact original client jars and release-specific Mojmap mappings are recorded. Each finding records cited source hashes; paired tags and resource hashes are in [Paired block and fluid tags](#paired-block-and-fluid-tags).
- Runtime validation: not performed; gameplay trajectory validation is a separate workflow.


## Supporting audit evidence

### Detailed coverage ledger

##### Coverage ledger

The stage numbers follow the established source-navigation order. Rows marked terminal have paired endpoint evidence or a bounded source-level disposition; queued rows remain open and prevent an overall-complete status.

| Stage | Slice | A/B correspondence and source boundary | Status |
|---|---|---|---|
| 1. Input and tick order | Keyboard input sampling, sneak/crawl scaling, LocalPlayer call order | A `LocalPlayer.aiStep -> Input.tick(boolean, boolean) -> KeyboardInput.tick`; B `LocalPlayer.aiStep -> Input.tick(boolean) -> KeyboardInput.tick`. Predicates and tick ordering were compared in [Stage 1 input correspondence](#stage-1-input-correspondence). | Terminal: no movement difference established for corresponding states. |
| 2. Player state and gates | Pose, dimensions, abilities, food, sprint and player attributes | [Stage 2 pose and dimensions correspondence](#stage-2-pose-and-dimensions-correspondence) pairs pose choice, collision gate, dimensions and eye height. Player movement-speed base value and sprint modifier are paired in [Stage 6 modifier correspondence](#stage-6-modifier-correspondence). [Stage 2 sprint gate correspondence](#stage-2-sprint-gate-correspondence) pairs local sprint start/stop predicates, impulse threshold, double-tap timer and food-level read; F14 traces a changed hidden Blindness lifetime into its sprint gate. [Stage 2 ability-state transfer correspondence](#stage-2-ability-state-transfer-correspondence) pairs ability storage and network transfer. [Stage 2 auto-jump correspondence](#stage-2-auto-jump-correspondence) pairs the bounded obstacle scan for shared block factors and routes B's Honey factor exclusion to F04. F12 traces B's same-tick flight-toggle guard into Elytra start sequencing. | in-progress: pose/dimensions, sprint, bounded auto-jump and ability-transfer paths paired; F12/F14 record local ordering/effect-state differences; other ability producers and player-state writers remain pending. |
| 3. Living movement integration | Jump, travel, ground friction, fluid travel, ladder, flight/fall-flying and movement attributes | [Stage 3 travel and jump correspondence](#stage-3-travel-and-jump-correspondence) pairs the full `LivingEntity.travel`, `jumpFromGround` and climbable helper bodies. F01 establishes the support-cell friction lookup difference; F04 records the Honey jump-factor delta. The other paired branches preserve fall-flying, ordinary air/ground, water/lava, gravity, ladder boost, Slow Falling, Levitation, Depth Strider and Dolphin's Grace formulas. Stage 5 pairs the separate water-current velocity producer; Stage 6 pairs Depth Strider and speed-effect modifier inputs. F08 records B's hidden weaker Speed/Slowness effect restoration and player attribute update; F14 traces the same generic fallback to jump, fall, fluid-travel and sprint-gate readers. F10 establishes that B can enter the shared fall-flying travel branch while still rising; F12 records the additional same-tick flight-ability toggle guard. Other fluid collision/flow consumers and movement-state producers remain open. | in-progress: travel/jump bodies compared with F01/F04/F08/F10/F12/F14 dependencies routed; remaining entry-state producers and fluid/block consumers pending. |
| 4. Entity movement and collision | move order, collision resolution, step/edge behavior, block-cell callbacks, impulses and packet corrections | F02 establishes Soul Sand callback multiplicity. F05 establishes Honey contact slide. F06 identifies server position placement; exact prediction path remains separate. F09 establishes a changed Riptide activation gate before a shared player impulse. [Stage 4 collision correspondence](#stage-4-collision-correspondence) pairs the bounded axis/step clipping and player edge-backoff paths; F11 records the changed grounded step callback gate reaching slime velocity damping. [Stage 4 block velocity callbacks](#stage-4-block-velocity-callback-correspondence) pairs bed/slime bounce, bubble-column vertical impulse and web/berry movement factors. [Stage 4 piston displacement](#stage-4-piston-displacement-correspondence) pairs shared piston push motion and routes Honey's B-only sticky push outside A's historical map states. [Stage 4 collision-shape provider inventory](#stage-4-collision-shape-provider-inventory) pairs explicit collision overrides, with the Honey addition routed to F03–F05; [Stage 4 block shape method inventory](#stage-4-block-shape-method-inventory) pairs the `getShape` provider methods. Other collision callbacks, block/fluid consumers and impulse ordering remain queued. | in-progress: F02/F05/F06/F09/F11, bounded clipping/edge, shared piston motion, shape overrides and selected callback formulas paired; broader callback/correction inventory pending. |
| 5. Blocks and fluids | Registrations, collision shapes, friction/speed/jump factors, callbacks, fluid tags and flow | F01/F02 cover snow, ice and Soul Sand; F03–F05 cover B-only Honey behavior; F11 establishes a grounded slime `stepOn` gate delta that writes player horizontal velocity. [Stage 5 block movement trait registrations](#stage-5-block-movement-trait-registrations) pairs the shared registered friction constants and routes Soul Sand/Honey factors to their existing findings. [Stage 5 water-current correspondence](#stage-5-water-current-correspondence) pairs the fluid-vector formula and player velocity consumer; [Stage 5 fluid-entry predicates](#stage-5-fluid-entry-predicates) pairs the water-height and lava-state inputs to travel. Paired tag inventory reports unchanged ice/water/lava tags and non-movement bamboo representation delta. Other movement-relevant blocks, fluid collision shapes and fluid callbacks are not exhaustively audited. | in-progress: findings F01–F05/F11, registered friction values, paired fluid entry/current paths and tag review; broader consumer inventory pending. |
| 6. Effects, enchantments, attributes and equipment | speed/slowness/jump/levitation/slow-fall/dolphin effects; depth strider/frost walker/riptide; item-use and armor paths | [Stage 6 modifier correspondence](#stage-6-modifier-correspondence) pairs the player movement-speed base value, sprint modifier, Speed/Slowness registrations and Depth Strider registration/lookup; their compared formulas and constants match. F08 traces the changed Speed/Slowness hidden-effect lifecycle into the player movement-speed attribute; F14 traces hidden restoration for Jump Boost, Slow Falling, Levitation, Dolphin's Grace and the Blindness sprint gate. F09 traces the Riptide item's activation gate into a player impulse. Frost Walker's registration, enchantment implementation and `LivingEntity.onChangedBlock` hook are paired without a body change. Fall-flying firework attachment is paired in [Stage 7 external movement correspondence](#stage-7-external-movement-correspondence). The paired LivingEntity travel/jump excerpt resolves unchanged formulas for Jump Boost addition, Slow Falling, Levitation, Depth Strider and Dolphin's Grace. Honey jump-factor integration is F04. Other movement-linked item-use/equipment consumers remain under audit. | in-progress: F08/F09/F14 and selected attribute/effect/enchantment paths compared; remaining effect/enchantment/equipment consumers pending. |
| 7. External influences | Server position/velocity, mounts, portals, other entities and world callbacks | F06 is server-side portal dismount placement with entity-tracking/correction boundary; F07 is B-only Bee sting knockback through common `LivingEntity.hurt`. F10 pairs local Elytra prediction with server acceptance. The common hurt-to-knockback velocity response and fall-flying firework attachment, non-mounted Ender Pearl/Chorus Fruit teleports, the ordinary inbound server movement route, and the mounted End Gateway selection/teleport chain in F13 are paired. [Stage 7 client correction correspondence](#stage-7-client-correction-correspondence) pairs player position correction, incoming entity-motion packet application, and ordinary local movement reporting. Other tracked-entity and external writers remain open. | in-progress: findings F06/F07/F10/F13; bounded client/server position and velocity correction, local report and mounted gateway routes paired; remaining external writers pending. |

###### Source-confirmed findings

- F01 — ground friction support-cell sampling changes under tall partial surfaces.
- F02 — Soul Sand horizontal multiplier applies once per overlapping cell in A and once by selected block factor in B.
- F03 — Honey Block horizontal speed factor (B-only block).
- F04 — Honey Block jump factor (B-only block).
- F05 — Honey Block descent sliding contact callback (B-only block).
- F06 — portal-aware server-side dismount placement.
- F07 — Bee sting can add knockback to player velocity (B-only entity interaction).
- F08 — a hidden weaker Speed/Slowness effect resumes in B and updates movement speed after a stronger effect expires.
- F09 — B rejects Riptide Trident use when one durability remains, preventing the shared release impulse.
- F10 — B permits Elytra flight to start while the player is rising, before the fall-flying travel branch already shared by both versions.
- F11 — B invokes grounded `stepOn` for flying or passenger players where A suppresses the callback; on slime this newly applies horizontal velocity damping.
- F12 — B suppresses Elytra activation on the same input tick that toggles creative flight, which A can process as both flight-off and Elytra-start while falling.
- F13 — B End Gateway teleports the root vehicle of the first intersecting entity and propagates the teleport through passengers; A targets the first intersecting entity directly.
- F14 — B retains and resumes weaker, longer movement effects after a stronger instance expires; A discards the weaker instance.

###### Explicit resource dispositions

- The one changed common tag, `bamboo_plantable_on`, expands the same five substrate values inline; only bamboo support/growth consumers were found. No player movement path is established.
- B-only bee hive/growable, crop, flower, tall-flower, shulker-box tags are not movement mechanics by themselves. Bee attack's player velocity effect is covered by F07; independent Bee movement remains out of scope.
- B-only `portals` tag participates in F06. Identical water/lava and ice tag bytes do not by themselves establish identical Java flow/collision formulas.

##### Dependency queue and blockers

- `PLAYER-STATE`: finish pairing remaining player-state producers and consumers beyond the completed dimensions, sprint, pose and bounded ability-transfer slices.
- `LIVING-TRAVEL`: finish movement-state producer closure and remaining fluid/block travel consumers. The full paired travel/jump formulas and separate water-current producer are recorded in Stage 3 and Stage 5.
- `COLLISION-ORDER`: pair collision clipping, step height, edge probing, callbacks and velocity mutation ordering beyond F02/F05.
- `BLOCK-FLUID-CONSUMERS`: exhaustively enumerate relevant movement registrations and paired fluid/block consumers.
- `EFFECT-EQUIPMENT`: resolve each effect, enchantment and equipment consumer enumerated in source inventory.
- `EXTERNAL-INPUTS`: finish inventorying tracked-entity position/velocity replication and other reachable position/velocity writers; bounded inbound player position/velocity correction and mounted End Gateway chains are paired.

These are audit coverage gaps, not source-generation blockers. The catalog remains in progress until those rows are resolved or each candidate is closed with paired evidence.

### Stage 3 travel and jump correspondence

The full paired `LivingEntity.travel` bodies preserve their branch order and math for Slow Falling gravity selection; water/lava entry gates; fall-flying lift, steering, drag, collision damage and landing flag; ordinary air/ground friction, relative movement, ladder boost, Levitation and gravity; water movement with Depth Strider and Dolphin's Grace; and the final movement-animation update. In the corresponding expressions B uses position accessors where A reads the same fields. Those accessors return the stored coordinates. F10 and F12 isolate differences in the local player predicates that enter the shared fall-flying branch; neither changes this common travel body. The player water-current vector is a separate producer paired in Stage 5.

The jump path preserves the Jump Boost additive term and the sprint-jump horizontal impulse. B's `getJumpPower` multiplies the base `0.42F` by `getBlockJumpFactor()` before Jump Boost is added; A returns `0.42F`. For shared blocks the factor is the default `1.0F`; B-only Honey has factor `0.5F`, so this consumer is covered by F04. The climbable helper preserves horizontal clamps, downward speed floor, fall-distance reset and scaffolding exception. A's player shift check calls `isSneaking`; B calls `isSuppressingSlidingDownLadder`, which returns `isShiftKeyDown`; both read the same shared entity flag as established in Stage 2. Thus no additional ladder predicate delta is established here.

The inherited jump segment in `LivingEntity.aiStep` also matches: both decrement `noJumpDelay` at entry, apply the same water-height test, route a jump to lava or water before the grounded/shallow-water jump, require the same zero-delay gate for `jumpFromGround`, set the delay to 10, and clear it when jumping is false. Stage 1 pairs the keyboard input route and its local-player call order; Stage 2 covers auto-jump's synthetic jump. After this segment both versions decay the movement inputs, update fall-flying state, and call `travel` in the same order. A additionally zeroes `yRotA` in the immobile branch and damps it before travel; B has removed that rotation field, while the translational jump and travel inputs are unchanged in the compared statements.

The ordinary ground/air branch's friction sample is a known exception: A constructs its support position from `x, boundingBox.minY - 1.0, z`; B calls `getBlockPosBelowThatAffectsMyMovement`, whose Y offset is `0.5000001`. F01 records the resulting partial-surface support-cell behavior and remains the relevant finding. No further branch formula delta is established in these paired bodies.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/entity/LivingEntity.java` | `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681` | `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54` | `travel`, `jumpFromGround`, `getJumpPower` and `handleOnClimbable` compared. Common travel formulas and ladder predicate match; base jump factor is routed to F04. |
| `net/minecraft/world/entity/Entity.java` | `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7` | `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E` | B support position helper uses the `0.5000001` offset versus A's travel-local `1.0` offset; F01 records the movement consequence. The corresponding position getters read the stored coordinate fields. |
| `net/minecraft/world/entity/player/Player.java` | `E9CE5EB18C5581FFE2B610B273FFD85786587A00BA853E5DBA0AC96593622B12` | `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793` | Player flying ability gate is present in the same water/lava branches; block jump factor access is paired with the Entity helper and F04. |

Disposition: the common jump/travel formula bodies are paired, with F01/F04 and the F10/F12 branch-entry differences retained as separate findings. This does not close the producers of movement state, effect/equipment chains, block/fluid callbacks or every world-driven travel input.

### Stage 5 water-current correspondence

The player water-current producer is paired through `Entity.checkAndHandleWater` and `FlowingFluid.getFlow`. Both entity handlers deflate the player bounding box by `0.001`, scan intersected fluid cells in X/Y/Z order, track the maximum submersion depth, and, when `isPushedByWater()` is true, accumulate each qualifying cell's flow. Per-cell flow is scaled by the running depth when it is below `0.4`; the sum is averaged by sample count, left unnormalized for players, then added to delta movement with scale `0.014`. `Player.isPushedByWater()` returns `!abilities.flying` in both releases.

`FluidState.getFlow` delegates to the same fluid-type method in both versions. `FlowingFluid.java` is byte-identical, including its horizontal neighbor order, height-difference calculation, falling-fluid side-wall check (the `-6.0` vertical bias), and final normalization. The water tag values are identical in the paired resource inventory. This closes the bounded water-current vector producer and player velocity consumer; it does not close fluid collision shapes, water/lava travel, or other fluid callbacks.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7` | `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E` | `checkAndHandleWater` has the same box deflation, cell scan, depth scaling, average and player velocity write. |
| `net/minecraft/world/entity/player/Player.java` | `E9CE5EB18C5581FFE2B610B273FFD85786587A00BA853E5DBA0AC96593622B12` | `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793` | `isPushedByWater` remains `!abilities.flying`. |
| `net/minecraft/world/level/material/FlowingFluid.java` | `E8A395552E53F33BCB5648325615D31BC1796CE2A8AEB501BDDA3127622DCCDF` | `E8A395552E53F33BCB5648325615D31BC1796CE2A8AEB501BDDA3127622DCCDF` | Neighbor-flow gradients, falling-fluid wall bias and vector normalization are byte-identical. |
| `net/minecraft/world/level/material/FluidState.java` | `26070B6D83813243A7A7C34CF9AF9E3E4217681A8E7A97285C6DBB7D473F480D` | `F88BFF7D54EA0220EE67E0FE36A78DF67871E52AE636E584EA26DD107F652C15` | `getHeight`, `getOwnHeight` and `getFlow` delegate to the same fluid-type methods; surrounding API has other changes outside this consumer path. |

Disposition: no water-current or direct player velocity delta is established for these paired methods. Other fluid travel/collision and block-fluid consumers remain open.

### Stage 5 fluid-entry predicates

The player travel branch predicates are paired through their state producers. `isInWater()` reads `wasInWater` in both versions; `updateInWaterState` preserves the boat exclusion, calls the same water scan, and writes the same flag. `checkAndHandleWater`'s AABB deflation, X/Y/Z cell scan, fluid-height test and maximum submersion depth are already documented with the water-current producer above. `isUnderLiquid` also keeps the same eye-height threshold and `fluidState.getHeight(...) + 0.11111111F` comparison; B's `getEyeY/getX/getZ` accesses the same stored position and eye-height expression as A. This state feeds the same `wasUnderWater` and swimming gates.

The lava flag is cleared before `Entity.checkInsideBlocks` and set by `LiquidBlock.entityInside` only when the block's fluid is tagged lava. The inside-block AABB inset and X/Y/Z iteration order, `entityInside` dispatch and `onInsideBlock` order match; `LiquidBlock.entityInside` has the same lava-tag predicate and `setInLava()` call. Thus the `isInWater`/`isInLava` state supplied to the paired travel branches has no delta established in these bounded methods. Fluid collision/contact shapes and other callbacks remain open.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7` | `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E` | `Entity.tick` calls `updateWaterState` after sprint-state update in both; `updateInWaterState`, `isUnderLiquid`, `checkInsideBlocks` and water-height scan preserve the same player-relevant state predicates and call order. |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681` | `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54` | `checkFallDamage` retains the same conditional `updateInWaterState` refresh before its fall-damage branch. |
| `net/minecraft/world/level/block/LiquidBlock.java` | `23412EB14108456DA401A810E9ACC18C8374C38A026075E7EE64C6D8FB25F34B` | `CC7638D876A7E70CD111891CDB7BE424741A4A5BDD4851F3623881C30734C24E` | `entityInside` keeps the same lava-tag check and `setInLava` write. |
| `net/minecraft/world/level/material/FluidState.java` | `26070B6D83813243A7A7C34CF9AF9E3E4217681A8E7A97285C6DBB7D473F480D` | `F88BFF7D54EA0220EE67E0FE36A78DF67871E52AE636E584EA26DD107F652C15` | `getHeight` delegates to the same fluid-type method used by the paired height scan. |

Disposition: no delta is established for the paired water/lava travel-entry state producers and predicates. Other fluid shapes and block-fluid callbacks remain open.

### Stage 5 block movement trait registrations

In `Blocks.java`, the registered friction overrides for the shared ICE, SLIME_BLOCK, PACKED_ICE, FROSTED_ICE and BLUE_ICE blocks retain their values across both releases: `0.98F`, `0.8F`, `0.98F`, `0.98F` and `0.989F`, respectively. The added `noOcclusion()` builder calls on B's ice, slime and frosted-ice registrations are not friction inputs; collision/shape callbacks remain part of the open block inventory.

B also registers Honey Block with speed factor `0.4F` and jump factor `0.5F`; F03–F05 trace those player movement consumers. Soul Sand's representation moves from A's `SoulsandBlock.entityInside` multiplying X/Z delta by `0.4` to B's registered speed factor, whose player effect is traced with the per-overlapping-cell behavior in F02. The shared block-construction source rows establish these registration and representation changes, not an exhaustive inventory of all block callback overrides or shape providers.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/level/block/Blocks.java` | `983D0CDE25F55DDB055015B682BBDF3B131394208561805F26D9B2A240DEE9A9` | `0CEF66FEACBF9D7D5BD38AC1D2065E71384A73043B0956EEAF314FEDBF5CC7D9` | Shared friction overrides retain the listed constants; B's Honey speed/jump factors are routed to F03/F04 and its Soul Sand speed-factor registration to F02. |
| `net/minecraft/world/level/block/SoulsandBlock.java` | `3F2CE73AAE23FB7E13CF001123343C6C750F00684B5331A5597AE33B011EF4E4` | `355F0CC25E78DCE76CBC9C89D8BD8FF1956D4B53780CBA8E3FB5C23F485802CF` | A applies the `0.4` horizontal multiplier in `entityInside`; B removes that callback and relies on the registered block speed factor covered in F02. |

Disposition: no new registered friction-factor delta is established for the listed shared ice/slime blocks. Honey's factors and Soul Sand's representation are already source-confirmed findings. The stage remains open for the wider block/fluid callback and collision-shape consumer inventory.

### Stage 6 modifier correspondence

This bounded comparison follows movement-speed attribute inputs and the Depth Strider producer/consumer chain. The exact source files are in the paired 1.14.4 and 1.15.2 Mojmap trees named in the artifact manifest.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `E9CE5EB18C5581FFE2B610B273FFD85786587A00BA853E5DBA0AC96593622B12` | `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793` | `registerAttributes` sets movement-speed base value to `0.1F` in both. `aiStep` takes ability walking speed on the server, updates flying speed by the same sprint constant, and copies the attribute value into the living movement-speed field; the compared statements match. Player `getSpeed` reads the same movement-speed attribute. |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681` | `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54` | Sprint modifier UUID, description, `0.3F` amount and `MULTIPLY_TOTAL` operation match. `setSprinting` removes an existing modifier with that UUID before conditionally adding the same modifier. `LivingEntity.getSpeed` still returns the stored speed field; no delta is established in this slice. |
| `net/minecraft/world/effect/MobEffects.java` | `472B25EDE6BED3AD3ED1090DB3310CEA9708B637F111981BCF083F492C030412` | `472B25EDE6BED3AD3ED1090DB3310CEA9708B637F111981BCF083F492C030412` | Speed applies the same movement-speed `MULTIPLY_TOTAL` modifier (`0.2F`); Slowness applies the same modifier (`-0.15F`). Jump Boost and Dolphin's Grace registrations are byte-identical; their movement consumers are in the paired travel/jump comparison. |
| `net/minecraft/world/item/enchantment/EnchantmentHelper.java` | `EDD49C2FBC3B557441FAE63D5FBCAAE0374DE85562376EC7859AA877AA83A5F4` | `AC48EA19FD7B49226D4887086B1C907297E79454243C37559AE49DCAA504FCDD` | `getDepthStrider` delegates to the same `getEnchantmentLevel(Enchantments.DEPTH_STRIDER, livingEntity)`. The compared method bodies match. |
| `net/minecraft/world/item/enchantment/Enchantments.java` | `8051D941C7A008D7801CAB4F45DACC8E7EC13B45F7880AC664ED982185879490` | `8051D941C7A008D7801CAB4F45DACC8E7EC13B45F7880AC664ED982185879490` | Depth Strider remains registered as `WaterWalkerEnchantment(RARE, ARMOR_SLOTS)`; Frost Walker and Riptide registrations are also identical. |
| `net/minecraft/world/item/enchantment/WaterWalkerEnchantment.java` | `A6DD0D9C1A20A308F288984F23E38E520CDD13E94BAA3BD6125C3C42B887432D` | `A6DD0D9C1A20A308F288984F23E38E520CDD13E94BAA3BD6125C3C42B887432D` | Identical armor-feet category, level bounds and Frost Walker incompatibility for Depth Strider. |
| `net/minecraft/world/item/enchantment/FrostWalkerEnchantment.java` | `FE60B0B7FF9689B721DC8E8904ACAFD3544C217EC72BFBEBC7C11C3788B1208A` | `FE60B0B7FF9689B721DC8E8904ACAFD3544C217EC72BFBEBC7C11C3788B1208A` | Identical frosted-ice placement bounds, eligibility and scheduled melt. `LivingEntity.onChangedBlock` invokes the same lookup and callback body in both sources. |
| `net/minecraft/world/item/enchantment/TridentRiptideEnchantment.java` | `F8CEBAC038B65AD4DF4C2C11AF400B343FA0A6780BF7CDDCDC5C672E90B66AE6` | `F8CEBAC038B65AD4DF4C2C11AF400B343FA0A6780BF7CDDCDC5C672E90B66AE6` | Identical equipment slot, level bounds and compatibility; the changed item-use durability check is separately recorded in F09. |

Disposition: no movement delta is established for the bounded player base-speed, sprint-modifier, Speed/Slowness registration, or Depth Strider lookup paths. F08 separately records the changed effect-instance lifecycle, and F09 records the Riptide item-use gate. Potion/equipment lifecycle writers beyond these paths, remaining movement-linked item-use consumers, and externally synchronized attributes remain queued under `EFFECT-EQUIPMENT` and `EXTERNAL-INPUTS`.

### Stage 7 external movement correspondence

The attached firework path is a player-only external velocity producer. Both `FireworkRocketItem.use` methods spawn an attached rocket for an already fall-flying player on the server and shrink the item outside creative mode. The corresponding `FireworkRocketEntity.tick` branch checks `attachedToEntity.isFallFlying()`, reads the player's look vector and current velocity, then writes the same three component expressions to player velocity: current component plus `look * 0.1 + (look * 1.5 - current) * 0.5`. B uses `getX/getY/getZ` to reposition the rocket where A reads raw fields; the attached-player velocity arithmetic and following copy of player velocity into the rocket match. The neighboring unattached rocket motion and explosion/damage behavior are not player movement paths in this slice.

The common `LivingEntity.knockback` response is also paired for player targets. After a successful hurt path supplies an attacker direction, both versions apply the same knockback-resistance random gate, set `hasImpulse`, normalize the horizontal direction and scale by the requested strength, halve the prior horizontal velocity, and set vertical velocity to `min(0.4, oldY / 2 + strength)` when grounded (otherwise preserve old Y). The `hurt` call site derives the attacker-to-target X/Z vector and invokes the same `0.4F` knockback helper in both versions; B reads those coordinates through getters. This records the player's direct velocity response only; damage acceptance and attack resolution remain outside campaign scope.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/item/FireworkRocketItem.java` | `8C263D79FA09F92F9495013F264E5382D844A44C22A60E111430B6A87E3D57DA` | `85680DCE68EA4013CE7827D6AD538D4D230C139C4938C09DB22BDDAEBAE17F53` | `use` has the same fall-flying gate and attached-rocket constructor path. The changed `useOn` places an unattached rocket offset from the clicked face in B; it does not affect the attached player path. |
| `net/minecraft/world/entity/projectile/FireworkRocketEntity.java` | `EBBB6705D9535FFA54E362C3FF352BF938C24C0FA665EC70F1B5A2DD29078B02` | `9EDC7C59FB28BDFADCB376447D7670B90BA5607596923DF2D7CC762D385578FC` | Attached-entity player impulse formula is unchanged; B replaces raw player position field reads with getters when positioning the rocket. |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681` | `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54` | The common hurt-to-knockback invocation and direct velocity response formula match; B's coordinate getter substitutions read the same fields. |

Disposition: no player movement delta is established for the firework attachment or common hurt-knockback response methods. Damage/attack resolution remains excluded. Other knockback sources, packet corrections, portal dismount and player-only velocity/position callbacks remain separately covered or open in the stage 7 queue.

### Stage 4 collision correspondence

The bounded `Entity.move` collision response was paired across the two sources. The initial collision call, step-height alternative selection, horizontal-distance comparison, and the legacy/contextual collision-shape clipping methods retain the same decision order and axis order. Both versions clip Y first, then choose Z before X when `abs(x) < abs(z)`; otherwise they clip X before Z. The per-axis clipping operations and direct-versus-step candidate comparisons match in this slice.

Player edge backoff moved from `Entity.applySneaking` in A to `Player.maybeBackOffFromEdge` in B. Both guard on SELF/PLAYER movement, grounded state, and the player's sneak/shift state; both apply the same `0.05` decrement loops in X, Z, then diagonal order using the same `noCollision` probes at `-maxUpStep`. The A flag read (`isSneaking`) and B flag read (`isStayingOnGroundSurface`) both resolve to the shared shift flag. The block under the player is also selected equivalently: floor X/Z and Y minus `0.2`, then use the block below when the first cell is air and the lower block is a fence, wall or fence gate. These paired slices establish no clipping, step-choice, support-cell or edge-backoff delta for the compared methods.

The post-collision block callback gate is a separate difference. A calls `stepOn` inside its step-sound/passenger condition; B calls it for `onGround && !isSteppingCarefully()` before testing movement noise or passenger state. For grounded players, the ordinary non-flying, non-passenger case corresponds, but flying or passenger players can reach the B callback while A suppresses it. F11 traces this gate through `SlimeBlock.stepOn` into a horizontal player-velocity write. Callback gates for other blocks may produce non-movement side effects and are not closed by F11.

The bounded common entity-push path is also paired. `LivingEntity.pushEntities` obtains the same pushable entity list, preserves the cramming count/gate and calls `doPush`; `doPush` still delegates to `entity.push(this)`. `LivingEntity.push(Entity)` retains the same sleeping guard, and `Entity.push(Entity)` keeps the same shared-vehicle/no-physics guards, horizontal normalization, `0.05F` scale, pushthrough multiplier, and vehicle suppression in the same order. B substitutes `getX/getZ` for A's position fields in the same subtraction. `Entity.push(double, double, double)` still adds the impulse to delta movement and sets `hasImpulse`. The player-reachable Boat and AbstractMinecart overrides keep their same vertical/vehicle guards and impulse formulas, with B using position getters; Slime still delegates to the common push then handles its same non-player Iron Golem damage case, and Shulker still suppresses pushes. Bat's empty `pushEntities` and ArmorStand's minecart-only push list do not write player motion. This closes the compared push dispatch paths for player response; non-player and vehicle motion outcomes remain outside scope.

Piston displacement also retains the same bounded `Entity.move` input path and restriction formula: the squared-length cutoff, once-per-game-tick accumulator reset, X/Y/Z priority, `[-0.51, 0.51]` clamp, subtraction of accumulated prior displacement, and `1.0E-5F` zeroing threshold are unchanged. The moving-piston producer path is paired in [Stage 4 piston displacement](#stage-4-piston-displacement-correspondence); piston activation and remaining block-state dependencies are still open.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7` | `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E` | `move`, `collide`, legacy/context shape clipping and support-block selection compared; clipping and direct/step choice match. A `applySneaking` and B Player `maybeBackOffFromEdge` retain equivalent player edge-reduction loops. Common `push` impulse and piston restriction methods retain their formulas and order. The differing `stepOn` invocation gate is F11. |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681` | `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54` | `pushEntities`, `doPush` and sleeping-gated `push(Entity)` retain the same player-response call path; the separately audited effect/travel methods are recorded in Stage 6 and teleport correspondence. |
| `net/minecraft/world/entity/vehicle/Boat.java` | `41C287AFD9430B1576FBCCDC98CA699586FE138C198A0DE54380D7469E400A5D` | `4F7D74F414A3949869FDD987082B6153CF66B21EBFD25D44A642B7AE00DB4047` | `push(Entity)` retains the same boat/other-entity vertical overlap guards and calls the paired common push method. |
| `net/minecraft/world/entity/vehicle/AbstractMinecart.java` | `AAF52C1A3358213FC3A290CD122BBF2813DE4F5F22D44562D94CD6A0DCC18975` | `B53347BCEEE6BBDB057F3D366D0411E02165D9563C7DD7A88EF6D52CFCB25AF9` | `push(Entity)` retains the same server/no-physics/passenger guards, normalization and pushthrough scaling; the player-target branch keeps the same impulse arithmetic. B uses position getters for the compared coordinate reads. |
| `net/minecraft/world/entity/monster/Slime.java` | `38B6F6E097556AEA4F4E27F59E460A2C218D4DDD207631BB608364EC8B9CD5ED` | `ACF213F751DE54C5B9E9A0E1A73F4204C63BF361307DA1038BD40CE62EE51FDA` | `push(Entity)` delegates to the same common push method; its added damage branch is limited to Iron Golems and is outside this player movement response. |
| `net/minecraft/world/entity/monster/Shulker.java` | `A2627786D594D2DF1876B4A192E0077CD357C4A0295DF091711D95AFBBEC3320` | `EC230631B5E0FFB7896890BD0F6AD31D54A540EFF018828D6E7A93A29584C7E6` | `push(Entity)` is empty in both versions and suppresses player push from this override. |
| `net/minecraft/world/entity/player/Player.java` | `E9CE5EB18C5581FFE2B610B273FFD85786587A00BA853E5DBA0AC96593622B12` | `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793` | A `makeStepSound()` returns `!abilities.flying`; B `isMovementNoisy()` retains corresponding grounded movement-noise conditions, but B's `stepOn` call is outside that gate. B edge-backoff guard uses `isStayingOnGroundSurface`, which reads the same shift state. |
| `net/minecraft/world/level/block/SlimeBlock.java` | `C3C88920F4E895B265042557708CB658075D00948ABBE700240F28EB9791A137` | `E45CE854171972D6F46D9B0FFE4AD66217D074E6DE0297DE5551BC0D2405D7C5` | Slime horizontal damping formula is the same; B changes `isSneaking()` to the corresponding `isSteppingCarefully()` player shift predicate. Invocation reachability differs under the `Entity.move` gate (F11). |

Disposition: no delta is established for the bounded clipping, step candidate, support-block, edge-backoff, common living-entity push, piston restriction or explicit collision-shape provider methods. F11 is a source-confirmed callback-reachability delta. Per-type push overrides, general block callbacks, piston activation inputs, fluid flow/collision and remaining impulse writers remain open; this correspondence does not close `COLLISION-ORDER`.

### Stage 4 block velocity callback correspondence

The compared post-landing callbacks for Bed and Slime blocks preserve the same downward-velocity bounce formulas. Bed writes `-velocityY * 0.66F * factor` (factor `1.0` for living entities, `0.8` otherwise); Slime writes `-velocityY * factor` with the same entity split. B factors the body through a `bounceUp` helper. The suppress-bounce conditions also correspond: A reads `isSneaking`, while B reads `isSuppressingBounce`, which delegates to `isShiftKeyDown` and the shared flag. Their `fallOn` methods only route fall damage, which is excluded from the movement campaign. Slime's separate grounded `stepOn` horizontal damping formula also matches; its changed reachability is F11.

Bubble Column `entityInside` calls `Entity.onAboveBubbleCol` under the same air-above gate in both sources. The helper retains the same vertical cap and arithmetic for drag-down (`max(-0.9, y - 0.03)`) and upward (`min(1.8, y + 0.1)`) flow. Web contact applies the same `(0.25, 0.05, 0.25)` stuck-in-block factor. Sweet Berry Bush applies the same `(0.8, 0.75, 0.8)` factor to players in both versions; B's added Bee exclusion does not change the player path. These are bounded callback/math correspondences; they do not close all callback registration, collision reachability or neighboring-block provider inventory.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/level/block/BedBlock.java` | `01415D1783F2346375A282F84E203625F00FCDB377E5875A9F22C7DBD8A10C17` | `E2496C30E0FF922F5BB492F951E2389C1E1FBBBDCD7AED2D4D308574FA7A4B0F` | Landing bounce formula and shift-flag suppression are equivalent; B extracts the unchanged velocity write into `bounceUp`. |
| `net/minecraft/world/level/block/SlimeBlock.java` | `C3C88920F4E895B265042557708CB658075D00948ABBE700240F28EB9791A137` | `E45CE854171972D6F46D9B0FFE4AD66217D074E6DE0297DE5551BC0D2405D7C5` | Landing bounce and grounded damping formulas match. B's suppression name is the same shift flag; `stepOn` callback reachability differs in F11. |
| `net/minecraft/world/level/block/BubbleColumnBlock.java` | `2CAB08014222E6E3173986078B3FCB64348384B978C868E583CD2CAA30C93BE2` | `0F4B28EF76195B31CEC9E45E3E3DD94FCA26D1E851CE3C0A72748EA607A05879` | `entityInside` calls the same player vertical impulse helper under the same above-block air check. |
| `net/minecraft/world/entity/Entity.java` | `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7` | `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E` | `onAboveBubbleCol` cap and delta expressions match; `isSuppressingBounce` maps to the same shared shift flag as A `isSneaking`. |
| `net/minecraft/world/level/block/WebBlock.java` | `033EB88D391BCA202051A745F4E19A425209E7F236B74E67D6FC63FF619A27DF` | `587EE85D4A829C4C473F78D804F11BB31B026F488B63FA4E70961AAFBBD89505` | Player stuck-in-block movement factor is identical. |
| `net/minecraft/world/level/block/SweetBerryBushBlock.java` | `5F3C822E369109FC22C61767091F5E5CF7D6D1F152662788E83F3612261CC0CA` | `4FBAE9A3A8A5A920A3F7B2798C6B241089BDF9DF5F78CE42D37A10C44FAE0F4D` | Player stuck-in-block factor matches; the new Bee type exclusion is outside player reachability. |

Disposition: no additional player velocity-factor delta is established for these bounded callbacks. F11 remains the separate Slime `stepOn` gate finding. The broader collision callback and block/fluid consumer inventories remain open.

### Stage 4 collision-shape provider inventory

The declared `getCollisionShape` provider set has 16 classes in A and 17 in B. All 15 shared methods other than Scaffolding are identical at the method-body level. Scaffolding's `isSneaking()` context test became `isDescending()`; B's `EntityCollisionContext` obtains this from `Entity.isDescending() -> isShiftKeyDown()`, and the local player overrides map that to the same current shift-key input read by A's `LocalPlayer.isSneaking() -> isTryingToSneak()`. For server players both paths read shared entity flag 1. A captures the entity bottom from bounding-box min Y and B from entity Y; both are the player's lower position. B adds `HoneyBlock` as a new collision-shape provider, routed to the Honey findings and modern-only block boundary.

This inventory covers explicit `getCollisionShape` overrides. It does not exhaust `getShape` implementations used by the base provider, neighbor-provided shapes, or block/fluid callback consumers.

| Provider source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/level/block/BambooBlock.java` | `B09EE556FFBE5E5DB461366BBD1F3C31A95BBCE8A2346D586792D4D30EA263BA` | `C6DF0ADCF3D1203D66EB196404CA3CB006E7C5AA6E1CB9D7D3471BB7903B5B6B` | `getCollisionShape` body matches. |
| `net/minecraft/world/level/block/BellBlock.java` | `C03EB9F192409D00C961D268E279095EDB27C5B032BBBD37229C6701F11BA82A` | `E16DA46FC837B526C41571ED1221CB612BF83522899BAA6DCF8693A41CE81A24` | `getCollisionShape` body matches. |
| `net/minecraft/world/level/block/Block.java` | `276022CFC5BC00437FE65A23BBCDC9C078026E68D88930EB3244581F6DBFCF5F` | `A5819A4C676D2B7E08CCE7F80AE80A13726DD17B15E0B29EFDCE7E37EFA2BF0D` | Base `getCollisionShape` delegates to the same collision flag and block-state shape path. |
| `net/minecraft/world/level/block/CactusBlock.java` | `E5E3F0E2A0D3CD05112B6E47A201D9F7B963F7675D2CF2891391A49D70B8B4E5` | `D3734CA45940417C6F3FB83039CD0686320B3DAD3862F9D8BC868DC4D4B08428` | `getCollisionShape` body matches. |
| `net/minecraft/world/level/block/ComposterBlock.java` | `C0531E955DD6D8CA166F19553E1B77DB84A84D9AB443EB654F794E550FD42FB0` | `A52C4867B8AEB55B11157C25217503FAA62DFCFAF39AC768D2A1825E8BCFE407` | `getCollisionShape` body matches. |
| `net/minecraft/world/level/block/CrossCollisionBlock.java` | `5088EB166CA1CA36EB496AB120338D713C30BEC84FF5815EC266A52EA1444ECD` | `5088EB166CA1CA36EB496AB120338D713C30BEC84FF5815EC266A52EA1444ECD` | `getCollisionShape` body is byte-identical. |
| `net/minecraft/world/level/block/FenceGateBlock.java` | `51C17E70F96D37767D9CD077301D7F08B75D5CB3600754B1745E10B78B5C68E5` | `A141DC6AAFF85797FBEC5552BD2B8499B418F05DA00207B2C68E9BE1BCCDFD29` | `getCollisionShape` body matches. |
| `net/minecraft/world/level/block/GrindstoneBlock.java` | `BBB8934E7F0FE6D87DD867BB2AD49C00CF071665E64E48B5C55F94ADCF41E7DA` | `A1C070AB45B0D58EBCFD0821939701C6DE41FD8295EC23E96916DE50ADD43197` | `getCollisionShape` body matches. |
| `net/minecraft/world/level/block/LecternBlock.java` | `862597A3731DDE6DE60D1CA0EEBD1784B6853582F78BC7BC3501F29443F80237` | `740AFA10689D0AC1B7C88CB14BFC854C2A35C9128CC3F1A62F7E3E55F1AEB908` | `getCollisionShape` body matches. |
| `net/minecraft/world/level/block/piston/MovingPistonBlock.java` | `2ADBBCE0D023A61D2A966A6B4E0D448533E33AF468C0035179973D43E6EAB247` | `7B767F2F15974A36734037B7342C00F609B13D85505FF818CDE18620D733A79E` | `getCollisionShape` body matches. |
| `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java` | `05C9880861B5A620614FD916DBFE6A02730EB1F0F4244E7E41A908846E4FD5E5` | `43EF9A249446CAC093EB1F7555BD55C4DB5416474FA1668772F0F1257672E57A` | Moving piston collision-shape body matches; piston entity-motion production remains a separate open dependency. |
| `net/minecraft/world/level/block/ScaffoldingBlock.java` | `C9304EA86ADC2707FB419F8440DDAE23633D8F2C106E78EDA68ACAD954FB8DAA` | `9A9E7A7E26E2B001A285E3944178B94B1FF149B5000D3A0A5B9B719392662D84` | Shape body differs only in the sneak/descending context predicate, paired above. |
| `net/minecraft/world/level/block/SnowLayerBlock.java` | `DBE1D2884C7D577AF49AA214EBD0CAF6B7B6D71A3128FD746D9EE796DF2DA48F` | `89A20299049E7B33F92BC5A6D18F3C0C5D429A7891D85179315B68845A1FF486` | `getCollisionShape` body matches; F01 separately covers its movement-friction support lookup. |
| `net/minecraft/world/level/block/SoulsandBlock.java` | `3F2CE73AAE23FB7E13CF001123343C6C750F00684B5331A5597AE33B011EF4E4` | `355F0CC25E78DCE76CBC9C89D8BD8FF1956D4B53780CBA8E3FB5C23F485802CF` | The 14/16-height collision shape body matches; F02 covers its contact movement path. |
| `net/minecraft/world/level/block/state/BlockState.java` | `2BCC0167CB566DF8745ABBE967F0A8750038AD5A9B7843F5FB25BAB714D73188` | `77320FD1E50E58D7A3ED593CCBCF21853C97D125FCCEBDE2E0E305DC47524F21` | State collision-shape dispatch body matches. |
| `net/minecraft/world/level/block/WallBlock.java` | `4AD01BFD8D84B77ABE161289294667E9F482CF9477CD81F725162AA9DC891850` | `4AD01BFD8D84B77ABE161289294667E9F482CF9477CD81F725162AA9DC891850` | `getCollisionShape` body is byte-identical. |
| `net/minecraft/world/phys/shapes/CollisionContext.java` | `DA5B700FDC06B7EF3A58BA14753F73CA9A5BA40120736BE79B5E795A925F0A82` | `25B72516E924F382FBE581457D1621CC0BEC78810E4BA44D3D6E525A0C2D3C70` | Context predicate is renamed from `isSneaking` to `isDescending`; the entity context and local-player paths below establish the player mapping. |
| `net/minecraft/world/phys/shapes/EntityCollisionContext.java` | `07C5F8BC5F075A61B078BC434EC635FE10C27F2C5250EBD70DBABD37EE60F5C4` | `54C29529FD45CCD8926825A99443AC2AFB1B6BF11BF1A0ED06BFCF7C093C733F` | A captures `entity.isSneaking()`; B captures `entity.isDescending()`. The lower-position read is bounding-box min Y in A and entity Y in B. |
| `net/minecraft/world/entity/Entity.java` | `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7` | `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E` | B `isDescending()` delegates to `isShiftKeyDown()`, which reads the same shared flag as A `isSneaking()`. Entity Y is its lower coordinate. |
| `net/minecraft/client/player/LocalPlayer.java` | `0795C1223198CE5ACF5D2ED9E5DB8435BBEC4CD52B96F96B1DDF2865BAAAE85F` | `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD` | Local A `isSneaking()` and B `isShiftKeyDown()` both read the current input's sneak/shift key state. |
| `net/minecraft/world/level/block/HoneyBlock.java` | — | `40760AEB3C084F1143E87E1E057F18165492EB01B8FCE0815DFDD0882CDC8A03` | B-only collision-shape provider for a B-only block; Honey movement behavior is covered by F03–F05. |

Disposition: no new shared-block collision-shape delta is established by this explicit override set. Scaffolding's context rename preserves the player shift input. The broader shape-provider and collision callback inventories remain open.

### Stage 4 block shape method inventory

The declared `getShape` provider set is identical across both sources: 96 classes. Their 96 method bodies were paired; 95 are identical. Bed's body changes how it computes the connected orientation: A selects facing for the head and opposite-facing for the foot, while B factors the same relation into `getConnectedDirection(blockState).getOpposite()`. The four shared bed shape constants are byte-for-byte the same. This helper refactor selects the same collision shape for every facing and head/foot state.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/level/block/BedBlock.java` | `01415D1783F2346375A282F84E203625F00FCDB377E5875A9F22C7DBD8A10C17` | `E2496C30E0FF922F5BB492F951E2389C1E1FBBBDCD7AED2D4D308574FA7A4B0F` | `getShape` uses an equivalent head/foot direction calculation; all four shared bed shape constants match. |

Disposition: no player collision-shape delta is established by the paired explicit `getShape` provider methods. This does not close neighboring-block shape providers or all movement callbacks.

### Stage 4 piston displacement correspondence

The shared moving-piston push path retains the same per-shape swept-AABB calculation and entity iteration order. It keeps the same `PushReaction.IGNORE` filter, slime-block axis velocity replacement, maximum overlap distance, `+0.01` movement margin, `MoverType.PISTON` call under the `NOCLIP` direction, and source-piston base correction. B factors the sweep-area expression into `PistonMath.getMovementArea` and the guarded entity move into `moveEntityByPiston`; both helpers preserve the A expressions and operation order. Its `getMovement` direction switch is equivalent to A's axis-specific delta helpers. The moving-piston tick still calls shared `moveCollidedEntities(f)` before advancing `progress`.

B additionally calls `moveStuckEntities(f)` after the shared push path. Its `isStickyForEntities` predicate accepts only `Blocks.HONEY_BLOCK`, which does not exist in A's target era and is covered as a modern-only block boundary by F03–F05. No additional piston push is established for blocks represented in A. Piston activation and block-state producers remain open.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java` | `05C9880861B5A620614FD916DBFE6A02730EB1F0F4244E7E41A908846E4FD5E5` | `43EF9A249446CAC093EB1F7555BD55C4DB5416474FA1668772F0F1257672E57A` | Shared collision push and piston-base correction formulas match; B adds a Honey-only sticky entity route. |
| `net/minecraft/world/level/block/piston/PistonMath.java` | — | `8F88D47AD37E4DB579B0EC84A979F50CC1BA829C3E31EA3EFB844E38D76C8B5D` | B's extracted `getMovementArea` body matches A's inline helper expression. |

Disposition: no piston-displacement delta is established for shared historical block states in the paired moving-piston path. The Honey-only sticky path is outside A's era; piston activation inputs remain open.

#### Stage 7 server movement acceptance and correction

The paired `ServerGamePacketListenerImpl.handleMovePlayer` paths retain the same packet-coordinate defaults, packet-rate and moved-too-quickly checks, player collision checks, movement call, attempted-position reconciliation, corrective teleport decision, floating predicate, chunk update, and last-good-position writes. The `n` residual test remains `n > -0.5 || n < 0.5` in both versions; its `||` operation is preserved. B adds `fallDistance = 0.0F` for a positive requested vertical delta before the jump-from-ground check; this changes fall state, not a position or velocity write in this handler, and is outside the movement-response slice. `ServerGamePacketListenerImpl.teleport` also sends the same absolute/relative player coordinate construction and calls the same `absMoveTo` in both versions; B uses position getters for the relative offsets.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `912F528C03DA34FA703646B6383C2B49AC3493EFD7BA78CB8FF27AA7F693B67D` | `3D46E455509891A3E0EF50472610D565255F5991987E971BA00AF2140D25982C` | `handleMovePlayer` pairs ordinary player movement acceptance and server correction; no position/velocity delta is established. `teleport` retains the same packet/position route. |

Disposition: no changed ordinary inbound player movement or server correction behavior was established in these methods. Fall-distance lifecycle, vehicle packet handling and other server-authoritative writers remain in the external-input inventory; client correction handling is paired in the following section.

### Stage 7 client correction correspondence

The paired `ClientPacketListener.handleMovePlayer` handlers preserve the same relative-X/Y/Z interpretation: relative axes add packet deltas to player position and retain that velocity component; absolute axes replace the coordinate and zero that velocity component. Both acknowledge the teleport ID, apply the absolute position and rotation, then send the same `PosRot` response. B factors the three relative-argument tests into booleans and reads position through getters. B also calls `setPosRaw` and copies the target into `xo/yo/zo` before `absMoveTo`; `setPosRaw` only writes x/y/z, and `absMoveTo` subsequently applies the clamp, rebuilds the bounding box and sets old position in both versions. A performs the equivalent position and bounding-box writes inside `absMoveTo`. The local first-start initialization is likewise equivalent: A copies the corrected coordinates into old position then; B has already done so for every correction.

Rotation has a representational detail: A's `absMoveTo` keeps the raw yaw in `yRotO` while `setRot` wraps current yaw; B wraps yaw before assigning both current and old yaw. The final current rotation is wrapped in both; the old-yaw interpolation representation can differ around the wrap boundary. This comparison establishes no change to corrected player position or velocity, and does not close camera/render interpolation behavior.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `6DDDF37B79830D5413D91675E0BED8B4A3F0A179AC4535616DC57B510B778B45` | `D806D286AF4B93D7C61C3C884F344A5870CE1871FB0A811506031ADF78FAFC30` | `handleMovePlayer` preserves relative-coordinate additions, absolute-axis velocity resets, teleport acknowledgement and response packet; B's intermediate writes are folded into the final same position. `handleSetEntityMotion` also resolves the entity id and passes the three decoded components divided by `8000.0` to `lerpMotion` in both. |
| `net/minecraft/world/entity/Entity.java` | `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7` | `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E` | `absMoveTo`, `setPos` and B `setPosRaw` compared for correction position/bounding-box effects; B's raw field write is followed by the same clamped position and bounding-box update. |
| `net/minecraft/client/player/LocalPlayer.java` | `0795C1223198CE5ACF5D2ED9E5DB8435BBEC4CD52B96F96B1DDF2865BAAAE85F` | `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD` | `tick` and `sendPosition` preserve ordinary position/rotation thresholds and packet ordering; sneak action names map to the same shared shift flag. |
| `net/minecraft/network/protocol/game/ClientboundSetEntityMotionPacket.java` | `753FB60E5EA296A64CFDF96A58BBC254F056B6D8DBCB19619FFAEC2A83848762` | `753FB60E5EA296A64CFDF96A58BBC254F056B6D8DBCB19619FFAEC2A83848762` | Packet construction clamps each velocity component to `[-3.9, 3.9]`, scales by `8000.0` and casts to int; read/write use the same entity id VarInt and three short components. |
| `net/minecraft/server/level/ServerEntity.java` | `3C89567CF8071AA205C6CACE0EFC767ACF6863990F379245324B95A30DB89A4D` | `DC5892E3B2D7C6A141F235053C414284A6C77876981DB3BA1BA8419AF08D5527` | `sendChanges` emits tracked velocity on the same delta/impulse conditions and broadcasts hurt-marked motion; `sendPairingData` uses the same tracking/fall-flying gate for initial velocity. |
| `net/minecraft/world/entity/player/Player.java` | `E9CE5EB18C5581FFE2B610B273FFD85786587A00BA853E5DBA0AC96593622B12` | `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793` | `attack` sends the hurt-marked target `ServerPlayer`'s velocity, clears that marker and restores the attacker's saved velocity in both. |
| `net/minecraft/world/entity/Entity.java` | `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7` | `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E` | `lerpMotion` delegates to `setDeltaMovement(d, e, f)` in both, applying the decoded server components directly. |

The incoming entity-motion path is therefore paired from packet encoding through the client velocity write. The server tracker sends motion under the same delta/impulse and initial-tracking conditions; `Player.attack` also sends a hurt-marked `ServerPlayer` target's velocity before clearing that marker and restoring the attacker's saved velocity. These bounded sender and consumer paths establish no change to the synchronized velocity payload or its application. They do not close all tracked-entity replication or every producer that can set velocity; those dependencies remain in `EXTERNAL-INPUTS`.

The ordinary local client report path is paired through `LocalPlayer.tick()` and `sendPosition()`. The chunk-presence gate, passenger packet sequence, sprint state update, 20-tick position resend interval, `9.0E-4` squared-distance threshold, rotation comparison and packet choice order match. A uses bounding-box `minY` where B uses player Y for the reported and saved Y coordinate; these are the same lower player position at this point. Sneak reporting is renamed: A emits START/STOP_SNEAKING based on `isTryingToSneak`, while B emits PRESS/RELEASE_SHIFT_KEY based on `isShiftKeyDown`; the server handlers set/clear the shared shift flag in both. The passenger input packet also renames the sneak boolean to shift without changing its position or jump fields. Vehicle movement itself remains outside this player-report comparison.

Disposition: no player position/velocity correction, incoming entity-motion payload/application, or ordinary local-report delta is established for the paired paths. Wrapped old-yaw interpolation representation differs. The mounted End Gateway propagation path is recorded in F13; vehicle correction and other external position/velocity writers remain open.

#### Stage 7 player teleport-item correspondence

The non-mounted Ender Pearl impact path and Chorus Fruit candidate search were paired through their player-position calls. The item launch speeds, target sampling formulas, 16 Chorus Fruit attempts, downward support search, collision/liquid checks, and fall-distance reset are unchanged; B uses position getters where A reads the same entity fields. Chorus Fruit's passenger dismount check is also present in both. Ender Pearl consumes the stack before spawning in A and after spawn in B, but both `use` methods create the projectile with `shootFromRotation(..., 1.5F, 1.0F)`.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/item/EnderpearlItem.java` | `046E99836398D47D5CD2963D9778477934BF0B99D4F963F03C79074CA48793FA` | `6D8F6D100DD78F4B316702A86BAF793F40237774F628ABB175B4A88712725F0C` | Same projectile spawn, shooter and launch-speed path. Stack shrink order moved after entity spawn in B; no player movement difference is established by that ordering. |
| `net/minecraft/world/entity/projectile/ThrownEnderpearl.java` | `0F31E38341EB5DC4923A29598A88ED3F2C8A27E7BD73A6D012DEB05326E4D7A6` | `6BE17996602ADE94B52A77085C41D2C69EC869F47197B948FEA41BEF81B04DC6` | Ordinary player impact still teleports to the pearl position and clears fall distance; B uses coordinate getters. End Gateway contact remains a delegated separate path. |
| `net/minecraft/world/item/ChorusFruitItem.java` | `F4F149D0D56F0A461817FF4FF83ED0DB9283D9284453DDE290038F1BC0C89334` | `D6E1F698FA831FE5FCC3E9D21F48F8F253CCB4B819DC5E1976E18B2CEABBD6B7` | Same 16 randomized target attempts and `randomTeleport` call; differences in position access are getters replacing raw fields. |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681` | `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54` | `randomTeleport` retains the same downward support search, collision/liquid acceptance, fallback restore, and success event. B keeps candidate coordinates local until it calls `teleportTo`; A temporarily writes the candidate into entity coordinates and restores on failure. |
| `net/minecraft/world/entity/Entity.java` | `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7` | `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E` | A base `teleportTo` moves only its target and updates that entity's chunk position. B moves the target, then updates chunk positions and teleport flags for the target plus passengers and repositions direct passengers. Player reachability is established through the paired End Gateway path below and `ServerPlayer.teleportTo`. |

The End Gateway tick chooses the first entity intersecting its block. A passes that entity directly to `teleportEntity`; B calls `getRootVehicle()` first. If the intersecting entity is a player riding another entity, A's `ServerPlayer.teleportTo` sends the player teleport through its connection, while B teleports the root vehicle. B's `Entity.teleportTo` then updates the root and passenger chunk/teleport state and repositions direct passengers, reaching the mounted player through the common passenger chain. The gateway exit selection and cooldown logic otherwise match. Independent vehicle movement remains outside scope; this comparison covers the player position route and its passenger propagation.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/level/block/entity/TheEndGatewayBlockEntity.java` | `EC4279C2B216F60F36ADF382F404689C23F836C6FB9CE1919A34A7D3B832A3AF` | `4BE661E53F4EE7E06C2754CEF1D4F5837766F776B0AC51AF4ADB3AC855668D10` | Tick selects the same first intersecting entity, then B alone substitutes its root vehicle as the teleport target. `teleportEntity` exit position and cooldown path match. |
| `net/minecraft/world/entity/Entity.java` | `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7` | `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E` | B's root-target teleport propagates chunk updates and teleport flags through passengers and repositions direct passengers; A's base method only updates the selected entity. |
| `net/minecraft/server/level/ServerPlayer.java` | `0B07BB15E2E3001F03DAB2DC943CBCA4814ADA36C82B6D83F933BE4FF01C4B92` | `719416651F82FF78E9990A77E5BEA0998938BF8070898865C37AE2DF6E3B22DA` | Both override `teleportTo` to send a connection teleport. The End Gateway's target choice means A can invoke this on the intersecting player, while B invokes the root vehicle path and reaches the rider through passenger repositioning. |

Disposition: F13 establishes a mounted-player End Gateway target/propagation difference. No difference is established for the ordinary non-mounted Ender Pearl/Chorus Fruit player teleport inputs and candidates. Other tracked-entity and external position/velocity writers remain queued.

### Newer-side resource inventory

#### Newer-side resource inventory (Minecraft 1.15.2)

Original input jar: `build/minecraft-decompile-cache/1.15.2/client.jar`; SHA-256 `4A73008A73F3824B7C711750A5A37556DF8614F193C0A531E292DAD159A73A7C`.

The following exact jar entries were inspected directly from the original client jar. Hashes are SHA-256 of the uncompressed entry bytes.

| Jar entry | SHA-256 | Content relevant to navigation |
|---|---|---|
| `data/minecraft/tags/blocks/ice.json` | `801D74E956B208F0C5106D6FDFEF47D249145948793EAE454C3BEF80B7C8D2A6` | `ice`, `packed_ice`, `blue_ice`, `frosted_ice` |
| `data/minecraft/tags/fluids/water.json` | `698E1662335B6241879B380A58D478EE019A1E789362B004050B5CCAC421AD18` | `water`, `flowing_water` |
| `data/minecraft/tags/fluids/lava.json` | `F3A67622F1F6A4C69E7792F1E3731B04236962B5BE1821671ACA0D1B59C316DD` | `lava`, `flowing_lava` |
| `data/minecraft/tags/blocks/bee_growables.json` | `9C4180B3A1015D700E15D5BBD699A19A46F4FE759BD31CE4C9EA92860DEEAC80` | `crops`, `sweet_berry_bush` |
| `data/minecraft/tags/blocks/beehives.json` | `8B9102847F605DD72AA69DD6B7E4BCFFDCA25F385FD2F9FFD8EF862A28A18415` | `bee_nest`, `beehive` |

These tag values are not themselves a movement difference. They are recorded for later consumer tracing. The B jar contains no `data/minecraft/enchantments/` entries; this is not absence evidence because enchantments in this version are code-registered. A and B tag contents and referenced values are paired in [Paired block and fluid tags](#paired-block-and-fluid-tags) using the verified jar identities in `run.md`.

### Paired block and fluid tags

#### Paired block/fluid tag inventory

Input jars: A 1.14.4 SHA-256 `B3B2A798E2D67B566008FE4A03767AE2C7FF3F8C7BA6751E7B71FC7299672D0A`; B 1.15.2 SHA-256 `4A73008A73F3824B7C711750A5A37556DF8614F193C0A531E292DAD159A73A7C`. The inventories were read directly from both original client jars after independently verifying A's hash against its owner's provenance.

##### Inventory result

- A contains 52 `data/minecraft/tags/blocks/*.json` and `.../tags/fluids/*.json` entries; B contains 58.
- 51 entry paths are common. Fifty have identical uncompressed bytes. The only common entry whose bytes changed is `data/minecraft/tags/blocks/bamboo_plantable_on.json`.
- A-only entry: `data/minecraft/tags/blocks/dirt_like.json` (SHA-256 `CBEDCEDB203119E9312A8FBA25E282DDA4ED7C1E73121AE5F8746232B8B73DDE`). Its values are dirt, grass block, podzol, coarse dirt and mycelium.
- B-only entries: `bee_growables.json`, `beehives.json`, `crops.json`, `flowers.json`, `portals.json`, `shulker_boxes.json`, and `tall_flowers.json`, all under `data/minecraft/tags/blocks/`.

##### Relevant unchanged values

| Entry | A SHA-256 | B SHA-256 | Result |
|---|---|---|---|
| `data/minecraft/tags/blocks/ice.json` | `801D74E956B208F0C5106D6FDFEF47D249145948793EAE454C3BEF80B7C8D2A6` | `801D74E956B208F0C5106D6FDFEF47D249145948793EAE454C3BEF80B7C8D2A6` | Same four ice blocks |
| `data/minecraft/tags/fluids/water.json` | `698E1662335B6241879B380A58D478EE019A1E789362B004050B5CCAC421AD18` | `698E1662335B6241879B380A58D478EE019A1E789362B004050B5CCAC421AD18` | Same still/flowing water values |
| `data/minecraft/tags/fluids/lava.json` | `F3A67622F1F6A4C69E7792F1E3731B04236962B5BE1821671ACA0D1B59C316DD` | `F3A67622F1F6A4C69E7792F1E3731B04236962B5BE1821671ACA0D1B59C316DD` | Same still/flowing lava values |

##### Changed and new entries

- `bamboo_plantable_on.json`: A SHA-256 `C906DD6EAF99018B42E85D4DBD99D2125E56B9A139E7E6BD1980C8FE0B697401`; B SHA-256 `9D806316F075528EC519BC9DAAEA7B886DA346162854C17A611F83783C3F51C4`. A expresses dirt-like substrates through `#minecraft:dirt_like`; B expands the same five values inline. The only consumers found in the versioned Java trees are `BambooBlock` and `BambooSaplingBlock` support/growth checks. This changes tag representation, not the accepted substrate set. No difference in player movement is established by this tag delta.
- `data/minecraft/tags/blocks/portals.json`: B SHA-256 `FECDF2F3A61C8521647BB59802F4ECC18569D4B86786700499AC11E5718F80A9`; values are Nether portal, End portal and End gateway. A has no such resource. B consumes `BlockTags.PORTALS` in `LivingEntity.findStandUpPosition` during server-side dismount handling; see finding `F06`.
- `bee_growables.json`: B SHA-256 `9C4180B3A1015D700E15D5BBD699A19A46F4FE759BD31CE4C9EA92860DEEAC80`; values are crops and sweet berry bush.
- `beehives.json`: B SHA-256 `8B9102847F605DD72AA69DD6B7E4BCFFDCA25F385FD2F9FFD8EF862A28A18415`; values are bee nest and beehive.
- Bee-related tags are consumed by the new Bee entity's target/hive behavior. Bee is inspected only for its attack effect on player velocity; its independent motion is outside scope. See finding `F07`.
- Remaining B-only crop, flower and shulker-box tag values are not movement mechanics on their own. Their consumers must be considered only if they form a dependency of player movement or an external player velocity/position update.

##### Scope note

These resources are datapack inputs. Identical values or missing files alone do not establish a Java behavior conclusion. Findings cite their consumer, registration and the exact jar entries where those data values matter.

### Newer-side source inventory

#### Newer-side source navigation inventory (Minecraft 1.15.2)

Source root: `decompiled_minecraft/1.15.2/mojmap`. This is a navigation inventory, not the completed pair correspondence. All entries below are for the generated exact 1.15.2 output. Relative file paths are from that root. SHA-256 values were computed from the source files on 2026-09-26.

| Stage | Source paths / members to resolve | B source SHA-256 |
|---|---|---|
| 1. Local input and tick ordering | `net/minecraft/client/player/LocalPlayer.java`: `tick`, `aiStep`, `isMovingSlowly`, input dispatch; `KeyboardInput.java`: `tick(boolean)`; `Input.java`: input state and tick signature | `LocalPlayer.java` `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD`; `KeyboardInput.java` `746EA654CF4F46A5F4B94A237C4307652252A44807A488B606DC993E088396F7`; `Input.java` `367C3A9B0B21D8F106A21FD2C73A3018685DBF07D9C8A9340E2D4C9D73359201` |
| 2. Player state and gates | `net/minecraft/world/entity/player/Player.java`: `tick`, `updatePlayerPose`, dimensions, attributes; `Abilities.java`; `world/food/FoodData.java`; `LocalPlayer.java`: `isCrouching`, `isMovingSlowly`, `serverAiStep` | `Player.java` `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793`; `Abilities.java` `FBEE269C9150B34FA9CCA5369307F47CE46A96E609E192C3D4092BDE318853D4`; `FoodData.java` `22595659140B58A2C8B97E68B34A09E9390353A1BAF3925B4276639A6EA05256` |
| 3. Living movement integration | `net/minecraft/world/entity/LivingEntity.java`: `jumpFromGround`, `travel`, `aiStep`, `getSpeed`; fluid travel and attribute consumers/callees | `LivingEntity.java` `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54`; `world/entity/monster/SharedMonsterAttributes.java` (hash to add when cited) |
| 4. Entity movement and collision | `net/minecraft/world/entity/Entity.java`: `tick`, `move`, `getBlockSpeedFactor`, `moveRelative`; `world/phys/AABB.java`; `world/phys/shapes/VoxelShape.java`, `Shapes.java`; client packet listener | `Entity.java` `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E`; `AABB.java` `82324C0E6A3D69E80424656E6098B39CE41F0F17A5E6EFBE6542FE8EACA24A18`; `VoxelShape.java` `5FACEEA0E4CE9AD0F2A1A2C72A89D9A768AB877C8728AE2968FE34AA1E03D3EA`; `Shapes.java` `81BF70D2C8A1D1336DF72BEEDB30B0C9504F14DECC9A06814A794AABF38203C2`; `ClientPacketListener.java` `D806D286AF4B93D7C61C3C884F344A5870CE1871FB0A811506031ADF78FAFC30` |
| 5. Blocks and fluids | `net/minecraft/world/level/block/Blocks.java`; `HoneyBlock.java`; block subclasses and registrations; `world/level/material/FluidState.java`, `FlowingFluid.java` | `Blocks.java` `0CEF66FEACBF9D7D5BD38AC1D2065E71384A73043B0956EEAF314FEDBF5CC7D9`; `HoneyBlock.java` `40760AEB3C084F1143E87E1E057F18165492EB01B8FCE0815DFDD0882CDC8A03`; `FluidState.java` `F88BFF7D54EA0220EE67E0FE36A78DF67871E52AE636E584EA26DD107F652C15`; `FlowingFluid.java` `E8A395552E53F33BCB5648325615D31BC1796CE2A8AEB501BDDA3127622DCCDF` |
| 6. Effects, enchantments, attributes, equipment | `net/minecraft/world/effect/MobEffects.java`, `MobEffect.java`, `MobEffectInstance.java`; `world/item/enchantment/Enchantments.java`, `EnchantmentHelper.java`; `world/entity/monster/SharedMonsterAttributes.java`; effect application and equipment paths | `MobEffects.java` `472B25EDE6BED3AD3ED1090DB3310CEA9708B637F111981BCF083F492C030412`; `MobEffect.java` `F218A040FF9F77272395245C4FFECC3F2266335600B79BE0A5B2823C46AD7DD0`; `MobEffectInstance.java` `4999D12CD468E402506F067DBD8694B1C8125DD9131E4F8A2FCC1848F616F180`; `EnchantmentHelper.java` `AC48EA19FD7B49226D4887086B1C907297E79454243C37559AE49DCAA504FCDD`; `Enchantments.java` `8051D941C7A008D7801CAB4F45DACC8E7EC13B45F7880AC664ED982185879490` |
| 7. External influences | `net/minecraft/client/multiplayer/ClientPacketListener.java`, `world/entity/Entity.java`, and movement callbacks once reachable dependencies are indexed | `ClientPacketListener.java` and `Entity.java` hashes recorded above |

##### Candidate leads to resolve after pair provenance

- Input refactor: 1.14.4's observed navigation tree has `KeyboardInput.tick(boolean, boolean)` and LocalPlayer calls it with `isVisuallySneaking() || isVisuallyCrawling()` plus `isSpectator()`. B has `KeyboardInput.tick(boolean)` and calls it with `isMovingSlowly()`. B's `isMovingSlowly()` delegates to `isCrouching() || isVisuallyCrawling()`. Determine exact correspondence, state timing, spectator behavior, and crouch pose gates. This is a lead only until A provenance is established.
- Honey block: B registers `Blocks.HONEY_BLOCK` with `speedFactor(0.4F)` and `jumpFactor(0.5F)`. `HoneyBlock.fallOn` passes `0.2F` to `causeFallDamage`; `entityInside` conditionally applies sliding. `isSlidingDown` requires airborne state, Y/velocity thresholds and horizontal offset; `doSlideMovement` can scale X/Z and writes Y `-0.05`, then clears `fallDistance`. Check the exact class/member/lines, registration, player reachability and A-side absence before creating a modern-only finding. Do not imply this block's behavior belongs in 1.14.4 emulation.

##### Resource inventory boundary

The B original client jar is available at `build/minecraft-decompile-cache/1.15.2/client.jar`. `jar tf` confirms it includes data-driven block/fluid tags and many other `data/minecraft` JSON resources; it contains no movement-resource conclusion by itself. Relevant entries, content hashes and referenced tag closure remain to be inventoried with the corresponding A jar using the verified 1.14.4 owner provenance recorded in `run.md` and the paired inventory in [Paired block and fluid tags](#paired-block-and-fluid-tags). Enchantment/effect registrations in this release are being traced through source code; absence of a dedicated JSON directory is not evidence of absence.

### Stage 1 input correspondence

#### Stage 1 correspondence note: keyboard movement input

A `LocalPlayer.aiStep()` calls `Input.tick(bl4, isSpectator())`; B calls `Input.tick(isMovingSlowly())`. The call occurs before both versions copy movement impulses into the local player tick path. Exact source hashes: A `LocalPlayer.java` `0795C1223198CE5ACF5D2ED9E5DB8435BBEC4CD52B96F96B1DDF2865BAAAE85F`, `KeyboardInput.java` `33DAA0833A95E09E728C1B8F509020DD13B70E38ABA922E2B1E10EC5C9B7739A`; B `LocalPlayer.java` `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD`, `KeyboardInput.java` `746EA654CF4F46A5F4B94A237C4307652252A44807A488B606DC993E088396F7`.

In A the slowdown predicate is `!isSpectator && (sneakKeyDown || (isVisuallySneaking || isVisuallyCrawling))`. In B it is `isMovingSlowly`, whose predicate is `isCrouching || isVisuallyCrawling`; `isCrouching` uses the same crouch pose-entry/shift-or-obstructed-standing conditions and excludes flying and swimming. A's `isVisuallySneaking` has matching flying/swimming and pose-entry conditions. Player pose update in each version selects standing when flying rather than crouching; spectator input therefore is not slowed by either route. The signature refactor has no movement delta established for corresponding ordinary, crawling, swimming, flying, or spectator states. This row is resolved as a no-difference correspondence, not a finding. The two sources are Mojmap-named but state equivalence was checked through predicates and call order.

### Stage 2 sprint gate correspondence

In A and B, `LocalPlayer.aiStep` samples the previous jumping and sneak/shift states, advances keyboard input, then uses the same sprint-start gates: grounded or underwater, not sneaking, sufficient forward impulse, not already sprinting, food level above `6.0F` or `mayfly`, not using an item, and no Blindness. The double-tap window remains 7 ticks; the sprint key route and sprint-stop conditions for insufficient forward input, horizontal collision, and water/swimming retain the same order. `hasEnoughImpulseToStartSprinting` is unchanged: underwater it asks `input.hasForwardImpulse()`, otherwise it compares `forwardImpulse >= 0.8`.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `0795C1223198CE5ACF5D2ED9E5DB8435BBEC4CD52B96F96B1DDF2865BAAAE85F` | `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD` | Sprint predicates and helper match; A `sneakKeyDown` corresponds to B `shiftKeyDown`, consistent with the input key correspondence above. The food-level producer is excluded by campaign scope; only the identical `> 6.0F || mayfly` movement gate is recorded here. |

Disposition: no sprint threshold or gate-order difference is established in this slice. Flight/ability transitions and other player-state dependencies remain open under stage 2; pose/dimension closure is recorded below.

### Stage 2 ability-state transfer correspondence

The ability-state container is byte-identical, including saved `flying`, `mayfly`, `flyingSpeed` and `walkingSpeed` fields. The compared transfer path is also unchanged: local `onUpdateAbilities` sends the same serverbound ability packet; the server accepts `flying` only when the packet requests it and the authoritative player has `mayfly`; `ServerPlayerGameMode.setGameModeForPlayer` applies `GameType.updatePlayerAbilities` and calls the same update hook; server `onUpdateAbilities` sends the clientbound ability packet; and the client copies flying, mayfly, and speed fields from that packet in the same order. F12 is the local tick difference layered over this unchanged transfer path: B marks a flight toggle with `bl8` and blocks same-tick Elytra start.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/entity/player/Abilities.java` | `FBEE269C9150B34FA9CCA5369307F47CE46A96E609E192C3D4092BDE318853D4` | `FBEE269C9150B34FA9CCA5369307F47CE46A96E609E192C3D4092BDE318853D4` | Ability fields, defaults, serialization and accessors are byte-identical. |
| `net/minecraft/client/player/LocalPlayer.java` | `0795C1223198CE5ACF5D2ED9E5DB8435BBEC4CD52B96F96B1DDF2865BAAAE85F` | `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD` | `onUpdateAbilities` sends the same packet; local flight-toggle and Elytra sequence differs as recorded by F10/F12. |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `912F528C03DA34FA703646B6383C2B49AC3493EFD7BA78CB8FF27AA7F693B67D` | `3D46E455509891A3E0EF50472610D565255F5991987E971BA00AF2140D25982C` | `handlePlayerAbilities` stores `packet.isFlying() && player.abilities.mayfly` in both versions. |
| `net/minecraft/server/level/ServerPlayerGameMode.java` | `2B5020F0DCFAD9A8D9D5E7555680257DA64E287067114A2532D50BC9DBBF6B80` | `EBC98844EB5329C06E5881D7E90DD3F04711309C6B738F836FADA8C8A0EF0FCB` | `setGameModeForPlayer` applies the selected game's abilities and invokes `onUpdateAbilities` in the same order. |
| `net/minecraft/world/level/GameType.java` | `DD80614B1C44D9BB8834C82D5A90D145299F3A1D31C4DD0571510FB69896351F` | `DD80614B1C44D9BB8834C82D5A90D145299F3A1D31C4DD0571510FB69896351F` | `updatePlayerAbilities` assigns mayfly, flying, creative and build flags identically for each game type. |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `6DDDF37B79830D5413D91675E0BED8B4A3F0A179AC4535616DC57B510B778B45` | `D806D286AF4B93D7C61C3C884F344A5870CE1871FB0A811506031ADF78FAFC30` | `handlePlayerAbilities` copies flying, instabuild, invulnerability, mayfly and movement speeds in the same order. |

Disposition: no ability serialization or transfer-path delta is established in these bounded methods. Local movement sequencing still differs in F10/F12, and game-type producers, respawn/join restoration and other ability writers remain open.

### Stage 2 auto-jump correspondence

A `LocalPlayer.move()` always runs `updateAutoJump` after the common collision move. B preserves that call order and its obstacle scan, directional rejection threshold, collision-shape probes, Jump Boost height allowance, maximum height, and final `autoJumpTime = 1` decision. B factors out `canAutoJump()`: its enabled/timer/grounded/passenger checks match A; `isStayingOnGroundSurface()` reads the same shift flag as A `isSneaking()`; and `isMoving()` tests the same two nonzero input-vector components A checked inline. B additionally requires `getBlockJumpFactor() >= 1.0F`. On blocks represented in A, the compared factor path has the default `1.0F`; B's new Honey Block factor is `0.5F`, so it does not trigger auto-jump. F04 records both Honey jump consumers. No auto-jump delta is established for shared historical block states in this bounded comparison.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `0795C1223198CE5ACF5D2ED9E5DB8435BBEC4CD52B96F96B1DDF2865BAAAE85F` | `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD` | `move` calls the auto-jump probe after superclass movement in both versions. The obstacle scan matches; B's explicit block-jump-factor cutoff only excludes its Honey factor case, recorded in F04. |
| `net/minecraft/world/entity/player/Player.java` | `E9CE5EB18C5581FFE2B610B273FFD85786587A00BA853E5DBA0AC96593622B12` | `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793` | B's `getBlockJumpFactor` caller is shared with the ground-jump path; see F04 and the paired Entity helper cited there. |

Disposition: no auto-jump delta is established for shared block factors; B's Honey exclusion is a modern-only dependency, not behavior for A's maps.

### Stage 2 pose and dimensions correspondence

`Player.updatePlayerPose` preserves the same priority order: swimming eligibility, fall-flying, sleeping, swimming, spin attack, sneaking/crouching, then standing; if the chosen pose is blocked, it falls back to the crouch pose or swimming. A names the crouch pose `SNEAKING` and reads `isSneaking`; B names it `CROUCHING` and reads `isShiftKeyDown`. Both accessors read shared flag bit 1. `canEnterPose` has the same `level.noCollision(this, getBoundingBoxForPose(pose))` body.

Player dimensions match by pose: standing `0.6F x 1.8F`, crouch/sneak `0.6F x 1.5F`, and fall-flying, swimming and spin attack each `0.6F x 0.6F`; sleeping and dying dimensions are inherited or mapped the same way. Both `Player.getDimensions` methods return the mapped pose dimensions with standing fallback. `Player.getEyeHeight` uses `0.2F` only when sleeping and delegates to the same standing-eye-height calculation otherwise. `LivingEntity.getDimensions` applies the same sleeping special case and scale, and its `getEyeHeight` delegates to the same pose/dimension-based calculation.

| Source | A SHA-256 | B SHA-256 | Compared slice and result |
|---|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `E9CE5EB18C5581FFE2B610B273FFD85786587A00BA853E5DBA0AC96593622B12` | `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793` | `updatePlayerPose`, pose dimensions map, `getDimensions` and player eye-height methods correspond; only the pose/method names for the shared shift flag changed. |
| `net/minecraft/world/entity/Entity.java` | `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7` | `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E` | `canEnterPose` and pose bounding-box checks match; A `isSneaking` and B `isShiftKeyDown` both return shared flag bit 1. B's `isCrouching` is a pose query, not a new state writer. |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681` | `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54` | Living dimensions scaling and pose/dimension eye-height delegation retain the same expressions. |
| `net/minecraft/world/entity/EntityDimensions.java` | `8EDE00F21F12636F26CCAD099307EE740FD1A2366A860A4653D5E2088E778C58` | `8EDE00F21F12636F26CCAD099307EE740FD1A2366A860A4653D5E2088E778C58` | Dimension factories, scaling and bounding-box conversion are byte-identical. |
| `net/minecraft/world/entity/Pose.java` | `06C647FC22586DCD1897AACD509256DE3B19835A593B53F8C591487725E9D2C2` | `3E3D6C8F1D28686E5BEE8B2E8A043832FD8AF03E4EB8DF35551A5DB3D2BACEBD` | `SNEAKING` is renamed `CROUCHING`; the other pose constants remain in the same order and the player dimensions map assigns the same shape. |

Disposition: no pose, collision-entry, dimension, or eye-height movement delta is established in this correspondence. Flight/ability state transitions and other pose writers remain open under stage 2.

### Source preparation record

#### Source preparation transcript extract (transcribed from task output; not raw Gradle log)

- Command: `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.15.2 --mappings=mojmap`
- Repository revision: `c133c2999b6673874e35bbdb26759548407f3e11`
- Requested release resolved exactly: `Decompiling Minecraft 1.15.2 (requested '1.15.2')`
- JDK: `25.0.3+9-LTS` (forked from `C:\Program Files\Eclipse Adoptium\jdk-25.0.3.9-hotspot`)
- Mapping mode: `mojmap`; official Mojang mapping file downloaded and applied.
- Output: `decompiled_minecraft/1.15.2/mojmap`
- Decompiler: Vineflower; `Finished 1.15.2 using mojmap`
- Warnings: three invalid-access remapper warnings for `MapRenderer$MapInstance` and fixer action for 1 class; Vineflower reported `ModelBakery.lambda$loadModel$25` and `$26` processed twice. No decompilation error was reported in the task output.
- Result: `BUILD SUCCESSFUL in 3m 49s` (Gradle 9.7.1).
- Limitation: this extract is a transcription of task output, not a raw saved log.
