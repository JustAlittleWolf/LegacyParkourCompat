# Audit-bound ordinary-core follow-up

This is a new supplement only. Preserve `report.md` and `supplement.md` unchanged.

## Claim binding

- `report.md` SHA-256: `84ED37D35E8A1932868516081AF999D88C9ADB15834E90A4B006E90B20281E6C`.
- `supplement.md` SHA-256: `58B28BCFB820A88B49B1C4D65A7DC818DF21C0A5DDA6419EE5E316507DFFADBB`.
- Blind audit SHA-256 provided by coordinator: `92d425d0ee158f9e4d690dcdfdac7dba4114127b4eae752dbae9c52ba8b5e1c5`.

The corrections below clarify earlier wording without editing either bound report.

## Exact source identities and hashes

The pair roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.12.2\ornithe-feather` and `...\1.13.2\ornithe-feather`. Their `ornithe-feather.provenance.json`, `.ready.json`, and `.sources.sha256` manifests identify the exact releases and Feather mapping family. Listed source hashes match `Get-FileHash SHA256` for each file below.

| Release | Relative path | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1D6EC8B80F74635401745C2C027BF36555C85348CA5693764F2668363B17D269` |
| 1.12.2 | `net/minecraft/world/World.java` | `E9FA9B8D6D31AD57A5B876F5F63A5E3C554A23437DECF1845A493DAF48233594` |
| 1.12.2 | `net/minecraft/util/math/Box.java` | `F788B8146B14F299CCB58EA0854609F845C298A963295D503DE2E30E15D66F3A` |
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java` | `190E9AC551538E015D9E4D6C42856E5BA32B593131CF6D93895E7B29533F1EE6` |
| 1.13.2 | same | `BB691358C9A43C9F46E85575BF4D0A4AD671D0EB912502ACC3A6A3F625E42F1C` |
| 1.12.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `01A58E94D8C6FF98A8E3794227CDC76A5FCBDBAD795C70C9CF28854AFF9823CC` |
| 1.13.2 | same | `2583495F3A02B4791AA036E6A8D354D7596C4984761969A0F29B29D8D9BF42BF` |
| 1.12.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `E4E0FDBE07A7D0A0AE4A70CBB6739A287C9D045A4A12B409895C220B5D91FE1E` |
| 1.13.2 | same | `4ED22F6C5A3C55D67EED782070AC722201DF4D624ADBC90779F1FD29C2876633` |

## Corrections to prior wording and classification

- In 1.12.2, `Entity.checkWaterCollisions()` passes `getShape().grown(0.0, -0.4F, 0.0).contract(0.001)` to `World.applyLiquidDrag` (Entity.java:948-963). `Box.grown` subtracts `dy` from minY and adds it to maxY (Box.java:132-140); negative `dy` therefore shrinks by 0.4 on each vertical face, then the explicit contract shrinks by another 0.001. The earlier report's shorthand “vertically expanded” should be read as the separate `checkWaterState()` -20.0 query, not the -0.4 collision/contact query.
- Ordinary jump handling for 1.13.2 is not “at/below surface or on ground” without qualification. In `LivingEntity.mobTick()` (1903-1917), while jumping: when `f_85121000` is positive, the outer gate routes to water impulse if airborne or if grounded and height exceeds 0.4; otherwise it takes the lava branch. The ordinary `jump()` branch requires `(onGround || (0 < height <= 0.4)) && jumpingCooldown == 0`. Airborne positive-height water uses `m_74407200(WATER)`, adding `0.04F` (1470-1472). With zero water height, normal ground jump also requires cooldown zero. 1.12.2 instead calls `jumpInWater()` whenever `isInWater()` (LivingEntity.java:1827-1839), whose water impulse is also +0.04F (1413-1415); the material distinction and shallow-water ordinary-jump gate are the movement changes.
- The 1.12.2 in-water travel branch subtracts `0.02` from vertical velocity each tick when gravity is enabled (LivingEntity.java:1555-1584). In 1.13.2, the corresponding branch applies vertical drag `*=0.8F`, then, if gravity is enabled and the player is not sprinting, subtracts `d/16.0` with `d=0.08` normally (0.005), or `d=0.01` under falling Slow Falling (0.000625); a near-0.005 vertical velocity is snapped to -0.003 (LivingEntity.java:1617-1650). Sprinting suppresses that gravity operation. This normal water-gravity delta applies to the pre-existing water travel path and is separate from sprint/swimming-only additions.
- Swimming-pitch steering, swimming pose dimensions, spin-attack pose, submerged-water sprint-start allowance, Dolphins Grace drag, and Slow Falling are additions to 1.13 absent from 1.12.2, so do not phrase those as modifications of a 1.12.2 swimming mechanic. Distinguish them from changes to already-existing sprint input, water-contact and water-travel behavior.

## Direct sprint input gates: paired exact operation

1.12.2 `LocalClientPlayerEntity.mobTick()` (694-745): reads prior jump/sneak/forward state, ticks input, applies item-use scaling and autojump, then computes sprint eligibility. Ground double-tap branch requires `onGround`, forward >= 0.8, no sneak/blindness/item use, enough food or flight, and sprint not already active. The direct sprint-key branch at 734-741 has forward/food/item/blindness/sprint gates, but no `isInWater` or submersion condition; a player satisfying the other gates can start sprinting while only shallowly in water. Sprint stop checks only forward < 0.8, horizontal collision, or insufficient food/flight (743-745).

1.13.2 `LocalClientPlayerEntity.mobTick()` (698-758): after the same input refresh and push steps, double-tap start requires `onGround || isSubmergedInWater()` (723-736); direct sprint-key start requires `!isInWater() || isSubmergedInWater()` (738-746). Thus non-submerged water blocks initiation through the direct key path and is ineligible for the double-tap path unless grounded. Stop logic defines `bl7 = bl6 || collidingHorizontally || isInWater() && !isSubmergedInWater()` (748-758), but applies `bl7` only in the **non-swimming** branch. If `isSwimming()` is already true, the special branch stops only for its airborne/non-sneak/low-forward-or-food condition or when no longer in water; it does not directly stop merely because water becomes non-submerged. This stateful exception matters: do not summarize 1.13 as unconditionally dropping sprint on every shallow-water tick.

Movement consequence: compared with 1.12.2, a direct sprint key can no longer initiate sprint while in shallow/non-submerged water. The new submerged double-tap path enables sprint/swim initiation off-ground. A non-swimming sprint state is stopped in shallow water; a swimming state can persist under its separate gate. These are eligible direct local-player movement state changes. A temporary candidate draft is in `sprint-water-input.md`.

## Water contact geometry and force producer

1.12.2 `Entity.checkWaterCollisions()` (Entity.java:948-963) special-cases a boat, otherwise calls `World.applyLiquidDrag()` with a box shrunk vertically by 0.4 each side and 0.001 each face. `World.applyLiquidDrag()` scans integer cells within that AABB (World.java:1579-1609), checks material equality and `bounds.ceil(maxY) >= liquidSurfaceY`, invokes each block's `applyMaterialDrag` (1600-1605), then, when accumulated flow is nonzero and the entity has liquid collision, normalizes the combined vector and adds `0.014 * unitVector` to velocity (1611-1620). Thus the **contact producer** and the **current-force producer** share one query but are two effects of it.

1.13.2 `Entity.checkWaterState()` (Entity.java:961-977) calls `m_69693160(WATER)`. That method contracts the entity box by 0.001 on all axes, scans fluid states in its integer-cell extent, and accepts a tagged fluid only where its surface is at or above box.minY; it accumulates the greatest surface-minus-box-bottom height into `d` (Entity.java:2509-2553). It averages flow across counted fluid cells; scales each sampled flow by `d` when `d<0.4`; skips normalization specifically for PlayerEntity; adds 0.014 times the resulting vector to velocity when nonzero (2554-2567); then writes that same maximum depth to `f_85121000` (2569-2570). The return value supplies `inWater`, so this one method produces both the contact flag and the depth used by the new water-jump gate.

Movement candidate: the `inWater` producer has a materially different geometric gate (old vertically contracted box/material query and liquid-surface check versus new almost-full entity box/tagged FluidState check against its bottom). The force calculation also changes from normalized block material-drag accumulation to averaged, depth-scaled fluid flow with player-specific unnormalized magnitude. The exact behavior of liquid-collision eligibility, FluidState flow, and historical fluid/block providers is owned/dependent on the collision/fluid lane; the ordinary-core report cannot close that dependency. A temporary candidate draft is in `water-contact-travel.md`.

## Operation separation

For reconciliation, keep distinct topics rather than grouping all aquatic changes:

1. Existing water travel vertical gravity update (new operation inside old water travel).
2. Existing local sprint-key eligibility and shallow-water stopping gates.
3. Existing Entity `inWater` contact and current-force producer geometry/math (dependent on fluid/collision provider evidence).
4. New swimming-only operations: pitch steering before travel, pose/dimension selection, spin attack pose, swimming state transitions/underwater sprint start, Dolphin Grace. Slow Falling's altered ordinary gravity is a new effect, separate from water gravity.

No code was changed, no tests/builds/runtime/decompilation/network were used. These are source candidates and an audit correction record only; do not treat them as accepted or as full-pair freeze.
