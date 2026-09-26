## Coverage ledger

The stage numbers follow the established source-navigation order. Rows marked terminal have paired endpoint evidence or a bounded source-level disposition; queued rows remain open and prevent an overall-complete status.

| Stage | Slice | A/B correspondence and source boundary | Status |
|---|---|---|---|
| 1. Input and tick order | Keyboard input sampling, sneak/crawl scaling, LocalPlayer call order | A `LocalPlayer.aiStep -> Input.tick(boolean, boolean) -> KeyboardInput.tick`; B `LocalPlayer.aiStep -> Input.tick(boolean) -> KeyboardInput.tick`. Predicates and tick ordering were compared in `stage-1-input-correspondence.md`. | Terminal: no movement difference established for corresponding states. |
| 2. Player state and gates | Pose, dimensions, abilities, food, sprint and player attributes | Player pose/crouch and flying gates were consulted for stage 1. Full comparison of dimensions, hunger/sprint thresholds and attributes has not been completed. | pending: compare exact formulas and pose transitions. |
| 3. Living movement integration | Jump, travel, ground friction, fluid travel, ladder, flight/fall-flying and movement attributes | F01 establishes grounded partial-snow friction lookup difference. Honey jump factor is separately F04. The paired LivingEntity.travel excerpt confirms matching branch order and formulas for fall-flying, ordinary air/ground travel, water/lava, gravity, ladder boost, Slow Falling, Levitation, Depth Strider and Dolphin's Grace; A uses raw coordinates where B uses accessors in these corresponding expressions. Jump Boost additive math is the same; the base jump hook changes through the block factor in F04. Water/lava flow and other entity/world consumers are not audited here. | in-progress: F01/F04 findings; several common formulas compared; other dependencies pending. |
| 4. Entity movement and collision | move order, collision resolution, step/edge behavior, block-cell callbacks, impulses and packet corrections | F02 establishes Soul Sand callback multiplicity. F05 establishes Honey contact slide. F06 identifies server position placement; exact prediction path remains separate. General collision/step/edge and impulse ordering were not exhaustively paired. | in-progress: findings F02/F05/F06; general collision and correction slices pending. |
| 5. Blocks and fluids | Registrations, collision shapes, friction/speed/jump factors, callbacks, fluid tags and flow | F01/F02 cover snow, ice and Soul Sand; F03–F05 cover B-only Honey behavior. Paired tag inventory reports unchanged ice/water/lava tags and non-movement bamboo representation delta. Other movement-relevant blocks and fluid formula consumers are not exhaustively audited. | in-progress: findings F01–F05 and paired tag review; broader consumer inventory pending. |
| 6. Effects, enchantments, attributes and equipment | speed/slowness/jump/levitation/slow-fall/dolphin effects; depth strider/frost walker/riptide; item-use and armor paths | The paired LivingEntity travel/jump excerpt resolves unchanged formulas for Jump Boost addition, Slow Falling, Levitation, Depth Strider and Dolphin's Grace. Honey jump-factor integration is F04. Registration and consumer inventory exists in source-inventory-b.md, but Speed/Slowness, remaining enchantments and equipment/item-use paths have no paired formula audit. | in-progress: selected travel formulas compared; remaining effect/enchantment/equipment consumers pending. |
| 7. External influences | Server position/velocity, mounts, portals, other entities and world callbacks | F06 is server-side portal dismount placement with entity-tracking/correction boundary; F07 is B-only Bee sting knockback through common `LivingEntity.hurt`. Their independent entity motion is out of scope. | in-progress: findings F06/F07; remaining external writers and local correction route pending. |

### Source-confirmed findings

- F01 — ground friction support-cell sampling changes under tall partial surfaces.
- F02 — Soul Sand horizontal multiplier applies once per overlapping cell in A and once by selected block factor in B.
- F03 — Honey Block horizontal speed factor (B-only block).
- F04 — Honey Block jump factor (B-only block).
- F05 — Honey Block descent sliding contact callback (B-only block).
- F06 — portal-aware server-side dismount placement.
- F07 — Bee sting can add knockback to player velocity (B-only entity interaction).

### Explicit resource dispositions

- The one changed common tag, `bamboo_plantable_on`, expands the same five substrate values inline; only bamboo support/growth consumers were found. No player movement path is established.
- B-only bee hive/growable, crop, flower, tall-flower, shulker-box tags are not movement mechanics by themselves. Bee attack's player velocity effect is covered by F07; independent Bee movement remains out of scope.
- B-only `portals` tag participates in F06. Identical water/lava and ice tag bytes do not by themselves establish identical Java flow/collision formulas.

## Dependency queue and blockers

- `PLAYER-STATE`: complete paired audit of dimensions, hunger/sprint, pose and ability gates.
- `LIVING-TRAVEL`: pair jump timing, gravity, water/lava travel and flow, ladders, flight/fall-flying and attribute consumers.
- `COLLISION-ORDER`: pair collision clipping, step height, edge probing, callbacks and velocity mutation ordering beyond F02/F05.
- `BLOCK-FLUID-CONSUMERS`: exhaustively enumerate relevant movement registrations and paired fluid/block consumers.
- `EFFECT-EQUIPMENT`: resolve each effect, enchantment and equipment consumer enumerated in source inventory.
- `EXTERNAL-INPUTS`: distinguish inbound player corrections from tracked-entity position/velocity replication for all reachable position/velocity writers.

These are audit coverage gaps, not source-generation blockers. The catalog remains in progress until those rows are resolved or each candidate is closed with paired evidence.
