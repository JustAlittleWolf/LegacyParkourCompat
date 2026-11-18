# Blind source audit: 26.1.1 to 26.1.2 fluid movement

## Scope and verdict

Independent blind review of official published unobfuscated decompilations for ordinary, non-riding local-player water/lava movement under equal effective world, resources, and player inputs. Checked (1) water/lava contact admission and stored height, (2) current sampling and push, and (3) the first ordinary water/lava movement's drag and gravity gates. No source difference was found in these bounded slices. This is a bounded equivalence verdict, not full-pair coverage or runtime parity.

## Source identity

Both canonical trees are `decompiled_minecraft/26.1.1/unobfuscated/` and `decompiled_minecraft/26.1.2/unobfuscated/`. Their ready markers identify the exact versions and `unobfuscated` mapping; provenance identifies published unobfuscated source (no remapping), Vineflower 1.12.0, Java 25.0.3+9-LTS, and resolved metadata IDs matching each requested release. Each tree has 6,882 files; byte totals match its ready marker (26.1.1: 32,992,043; 26.1.2: 32,992,615).

Ready-marker source-manifest SHA-256 values were verified against the manifest files:

- 26.1.1: `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3`
- 26.1.2: `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`

The compared Java files below match their respective source-manifest entries. Each listed Java file has the same SHA-256 in both releases:

| Source file | SHA-256 |
| --- | --- |
| `net/minecraft/world/entity/EntityFluidInteraction.java` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/world/level/material/FluidState.java` | `e35608d5775013c67653ea350309b5150a97f36aa65c27f1c6247ae2aa840f18` |
| `net/minecraft/world/level/material/Fluid.java` | `e7759a85dea543266b734086b2061332bc9f62ba3330be0f203f1f8baaa2c80d` |
| `net/minecraft/world/level/material/FlowingFluid.java` | `f8c0c4430e81efdf39b221968272ed8e3a4d030fa5192e04e04d34c9fbd37b28` |

## Ordered trace and coverage

1. **Contact admission and stored height.** `Entity.baseTick` records prior eye-water state, then calls `updateFluidInteraction` before swimming update and before the ordinary player movement step (`Entity.java:503-524`). `EntityFluidInteraction.update` resets trackers, obtains the fluid interaction box, floors minima and ceils maxima minus one, checks loaded sections for any fluid over the expanded horizontal area, and scans x/y/z in that order (`EntityFluidInteraction.java:32-90`). For each nonempty fluid state whose top is not below the box minimum, it resolves the water/lava tracker, checks eye inclusion against the fluid bottom/top, and stores `max(fluidTop - entity bounding-box minY, prior height)`. `Entity.getFluidInteractionBox` applies the same 0.001 deflation and passenger-box adjustment hook (`Entity.java:4015-4024`). The code, order, types, and conditions match byte-for-byte.

2. **State admission and current push.** `Entity.updateFluidInteraction` calls the tracker update with `ignoreCurrent = !isPushedByFluid`, reads water/lava from positive stored height, resets fall distance for water, gates splash on prior water contact and `!firstTick`, stores `wasTouchingWater`, then applies water and lava current only when pushed by fluid (`Entity.java:1558-1581`). The ordinary player uses `isPushedByFluid() == true` (`Player.java:1673-1675`). Current sampling calls `FluidState.getFlow`; `FlowingFluid.getFlow` scans horizontal neighbors, accumulates flow in direction iteration order, handles falling-fluid side contact, and normalizes (`FlowingFluid.java:55-101`). A sampled flow is scaled by tracker height only when height is below 0.4; the tracker accumulates the vector and count in traversal order. Applying current skips zero-count or squared accumulated length below `1.0E-5F`; a `Player` receives the arithmetic mean, then the fluid scale (water 0.014; lava dimension-dependent 0.007 or 0.0023333333333333335) and the low-horizontal-speed minimum impulse rule are applied before adding movement (`EntityFluidInteraction.java:66-84,132-145,155-188`; `Entity.java:1558-1581`). These operations and constants match byte-for-byte. The lava dimension value is an equal effective resource/input assumption.

3. **First ordinary water/lava move.** `Player.travel` first delegates passengers to superclass; otherwise it applies the same swimming vertical adjustment, then handles flight or delegates to `LivingEntity.travel` (`Player.java:1381-1409`). For the bounded ordinary non-riding, non-flying, non-swimming path, `LivingEntity.travel` admits fluid travel iff stored water or lava contact is true, the entity is affected by fluids, and it cannot stand on the fluid at its block position (`LivingEntity.java:2392-2408`). `travelInFluid` captures falling state, old Y, and effective gravity, then selects water when `isInWater`, otherwise lava (`LivingEntity.java:2455-2464`). Water computes sprint/water slowdown and water-walker speed adjustments, then Dolphin's Grace override; it applies relative input and moves before ladder adjustment, component-wise slowdown `(slowDown, 0.8F, slowDown)`, fluid-falling adjustment, and jump-out check (`LivingEntity.java:2467-2494`). Lava applies relative input and moves, then branches on stored lava height versus the fluid-jump threshold: the low-height branch applies `(0.5, 0.8F, 0.5)` before falling adjustment; the high-height branch scales by 0.5. Nonzero gravity is subtracted as `baseGravity / 4.0` afterward, followed by the jump-out check (`LivingEntity.java:2496-2510`). The shared fluid-falling gate and tolerance math (`baseGravity != 0.0 && !isSprinting`, falling checks, 0.003 thresholds, and `/16.0`) match byte-for-byte (`LivingEntity.java:2635-2647`). No changed drag/gravity gate or operation ordering was found.

## Boundaries

Excluded: riding/passenger behavior, swimming travel, creative/flying and Elytra paths, solid geometry/collision, portals, damage, and behavior outside these three slices. Equal effective resources include fluid tags and dimension `FAST_LAVA`; equal effective player inputs/state include movement attributes, effects, sprinting, ground/collision state, and prior stored fluid state. No trajectory claim beyond the source-level equivalence is made.