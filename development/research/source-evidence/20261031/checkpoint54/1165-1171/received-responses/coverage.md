# Mojmap 1.16.5 → 1.17 → 1.17.1 direct player responses

## Bounded scope and disposition

This blind source inventory covers direct local-player movement responses to entity overlap, blocks/callbacks, moving pistons, and the three principal received client packets that directly write player position or velocity. Ordinary tick math, general collision/shape internals, input-device sampling, server-side state/impulse production, and vehicle/non-player physics are outside this slice. Work was read-only against the three exact Mojmap source trees; no product code, implementation history, wiki material, builds, tests, launches, or source generation was inspected or performed.

One eligible source-level change is established: `Entity.push(Entity)` changes the horizontal separation square root from `Mth.sqrt(double)` (float result) to `Math.sqrt(double)` (double result) beginning in 1.17. This changes the precision of a locally computed entity-push impulse applied to a player when the entities are not in the same vehicle, neither has `noPhysics`, and `Mth.absMax(dx, dz) >= 0.01`. The 1.17.1 body is identical to 1.17. The impulse can differ at double precision boundaries; a complete tick trajectory / position divergence was not measured.

The 1.16.5 `pushthrough` multiplier is also removed in 1.17. In the inspected vanilla source universe, the field has no writer, so its vanilla value remains the JVM default `0.0F`; that deletion alone does not change an eligible vanilla response. It is not counted as a separate difference.

## Exact source identity

All sides are the supplied `decompiled_minecraft/<version>/mojmap/` trees. Endpoint source identity and all-source manifest checks are reused from the permitted 20261030 source-only endpoint reports (`1165-1171-pool38/direct_input_response_census/report.md` and `1165-1171-collision39/reconciliation.md`):

- **1.16.5 Mojmap:** 3,523 Java source entries; source-manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`; ready marker `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee`; provenance `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d`.
- **1.17 Mojmap:** provenance file SHA-256 `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357`; its 4,141 source entries were individually checked against the recorded hashes, with zero missing or mismatching files. Provenance records the 1.17 client artifact SHA-256 `14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f` and official client mapping file SHA-256 `191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471`.
- **1.17.1 Mojmap:** 4,142 Java source entries; source-manifest SHA-256 `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`; ready marker `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b`; provenance `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333`.

The endpoint records establish consistency with their local source manifests, not independent comparison against raw client JAR bytes. For the intermediate source, every recorded decompiled-source hash matched; this report does not reverify raw artifact bytes.

## Paired changed slice: entity-to-player horizontal push

- **1.16.5 Mojmap**, `net/minecraft/world/entity/Entity.java:1180-1216`, `push(Entity)` / `push(double,double,double)`: after same-vehicle, `noPhysics`, and `0.01F` gates, the code sets `f = Mth.absMax(dx,dz)`, then `f = Mth.sqrt(f)`. The applicable `net/minecraft/util/Mth.java:37-39` overload is `public static float sqrt(double d) { return (float)Math.sqrt(d); }`; assigning its result to local `double f` therefore retains single-precision rounding. The method divides both coordinate deltas by that rounded value, applies the capped inverse and `0.05F` factors, and adds/subtracts the resulting impulse through `push(double,double,double)`.
- **1.17 Mojmap**, `net/minecraft/world/entity/Entity.java:1276-1312`, same members: same gates and subsequent operation order, but `f = Math.sqrt(f)`, retaining a double result. The legacy multiplications by `1.0F - this.pushthrough` are gone.
- **1.17.1 Mojmap**, `net/minecraft/world/entity/Entity.java:1278-1314`, same members: body matches 1.17, including the `Math.sqrt` call and removal of the unused-in-vanilla `pushthrough` field factor.

Reachability/dependency closure for this response: `LivingEntity.tick()` reaches `pushEntities()` (1.16.5 `LivingEntity.java:2467,2502-2550`; 1.17 `:2569,2610-2658`; 1.17.1 `:2573,2614-2662`). That method obtains nearby `EntitySelector.pushableBy(this)` entities and calls `entity.push(this)`. A local player runs this client-side path without a logical-server guard; an overlapping pushable entity can therefore calculate and apply a changed impulse to the player from the local entity state. The final impulse is the product fact; later position/collision effects remain a trajectory inference. This finding is bounded to the direct response member and does not close the ordinary-tick pair.

## Paired no-difference slices

### Received client position / velocity packets

`net/minecraft/client/multiplayer/ClientPacketListener.java` was compared for `handleSetEntityMotion`, `handleMovePlayer`, and `handleExplosion` in each adjacent pair. The motion correction (`1.16.5:563-573`, `1.17/1.17.1:445-455`) still divides each packet component by `8000.0` and calls `entity.lerpMotion` under the same non-null entity gate. Explosion response (`1.16.5:1119-1137`, `1.17:974-992`, `1.17.1:973-991`) still finalizes the visual explosion and adds the received three-component knockback vector to the local player's current delta movement in the same order.

The position correction body starts at `ClientPacketListener.java:671` in 1.16.5, `:550` in 1.17, and `:549` in 1.17.1. It preserves relative-axis resolution, old-position updates, raw-position and velocity writes, absolute move, teleport acknowledgement, and response packet order. The only player-state gate change is vehicle detachment: 1.16.5 conditionally detaches when `tickCount > 0 && getVehicle() != null`; 1.17 uses the packet's `requestDismountVehicle()` flag before applying the correction. This concerns vehicle/passenger state and is excluded from the assigned player-on-foot response slice.

### Block and contact callbacks

The permitted prior callback scan checked both endpoint bodies and existing block providers; this pass bridged the same members through 1.17 and checked the adjacent source bodies. No player position/velocity callback delta was found in these paired slices:

- `Entity.move` dispatches block `updateEntityAfterFallOn` and `stepOn` after the same resolved movement/collision gates and in the same order (1.16.5 `Entity.java:485-535`; 1.17 and 1.17.1 `:534-586`). The `stepOn` API adds a `BlockState` parameter in 1.17; existing Slime behavior and the base callback's movement effect remain the same.
- Base `Block.updateEntityAfterFallOn` remains the horizontal-preserving / vertical-zeroing delta setter (`Block.java:351-352` in 1.16.5; `:401-402` in 1.17 and 1.17.1). Slime `updateEntityAfterFallOn`, `fallOn`, and `stepOn` retain their bounce multiplier, guards, and setter order (`SlimeBlock.java:17-50` old; `:19-52` in 1.17 and 1.17.1).
- Honey `fallOn` / `entityInside` preserve their slide and velocity writes (`HoneyBlock.java:39-58,80-93` old; `:44-63,85-98` in 1.17 and 1.17.1). Bubble-column contact preserves callback selection and vertical impulse dispatch (`BubbleColumnBlock.java:36-70` old; `:40-74` in 1.17 and 1.17.1; `Entity.onAboveBubbleCol` / `onInsideBubbleColumn` old `Entity.java:1932-1955`, new `:2089-2113` in 1.17 and `:2091-2115` in 1.17.1). Web contact still supplies `(0.25, 0.05F, 0.25)` to `makeStuckInBlock` (`WebBlock.java:16-18`, byte-identical across the three releases; `Entity.makeStuckInBlock` old `Entity.java:1991-1994`, new `:2148-2151` in 1.17 and `:2150-2153` in 1.17.1).
- `Block.fallOn` changes signature to include state and changes damage plumbing; damage calculation is excluded. No separate movement write was found in the base implementation.

### Piston movement callback

`net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java` changes from instance logic to level/position-parameterized static helpers in 1.17. The movement response operations remain the same: collision and sticky-entity candidate checks, progress delta, movement area, `+0.01` separation, `Math.abs(e-f) < 0.01`, `Math.min(e,d)+0.01`, and `Entity.move(MoverType.PISTON, axisDelta)`. In particular, `moveEntityByPiston` is 1.16.5 `:174-178`, 1.17/1.17.1 `:177-181`; `moveStuckEntities` is old `:180-193`, new `:183-196`; and the sticky predicate is old `:195-202`, new `:198-205`. The whole source file is byte-identical between 1.17 and 1.17.1. The endpoint callback audit independently reported no piston math/order delta.

### Other audited response boundaries

- `LivingEntity.knockback` changes from float strength/resistance-product rounding in 1.16.5 (`LivingEntity.java:1319-1326`) to double in 1.17 (`:1379-1385`). This is a server-produced combat/damage impulse: the inspected `hurt` path returns on the client before applying it (`:939-946,1052-1062` old; `:1013-1020,1122-1132` new). Server-side impulse production is excluded; the received-motion and explosion packet application methods above are unchanged.
- The server explosion producer changes `Mth.sqrt` to `Math.sqrt` at 1.17 in `Explosion.explode`; the player receives only the supplied vector through the unchanged client packet handler. Server/state production is excluded, so this is not a separate local-response finding. Its endpoint boundary and exact lines were previously reviewed in `direct_input_response_census/report.md`.
- New 1.17 Powder Snow contact is modern-only for a 1.16.5 profile and was not treated as a historical-block delta.

## Coverage inventory and remaining eligible members

Examined and paired across 1.16.5→1.17 and 1.17→1.17.1: entity push/impulse helper; its living-entity discovery/call edge; received set-motion, player-position and explosion packets; base/Slime block move callbacks; Honey, Bubble Column and Web contact; bubble-column entity response helpers; piston collision/sticky movement helpers; and the client/server boundary for combat knockback and explosion-produced impulse. The endpoint-level report also covered input correction/firework paths, block-provider callbacks, and other selected contact behavior; its closed no-difference slices were reused rather than repeated.

Still eligible and not closed by this bounded response slice: every other client packet path that can mutate the local player's position, velocity, ground state, or movement predicates; the full set of existing block/entity callbacks and all their reachable state predicates; and a full changed-method census in all player-response producers. In particular, item/effect-specific local velocity writes and every special entity interaction were not exhaustively enumerated here. Callback dependencies into ordinary tick/collision behavior remain owned by those separate lanes. Source discovery acceptance, pair freeze, independent full-coverage audit and runtime parity are not established by this inventory.
