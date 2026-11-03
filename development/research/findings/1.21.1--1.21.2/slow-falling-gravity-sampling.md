# Slow Falling gravity samples velocity after air movement

1.21.1, Mojmap: `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2091–2097 and air-travel branch lines 2195–2204; `handleRelativeFrictionAndCalculateMovement(Vec3,float)`, lines 2252–2256.
1.21.2, Mojmap: `net/minecraft/world/entity/LivingEntity.java`, `getEffectiveGravity()`, lines 2173–2175; `travelInAir(Vec3)`, lines 2191–2201; `handleRelativeFrictionAndCalculateMovement(Vec3,float)`, lines 2351–2355.

Difference: In 1.21.1, `travel` reads whether Y velocity is nonpositive and applies the Slow Falling cap before air movement; that sampled gravity is subtracted after the movement helper. In 1.21.2, the helper moves the entity before `getEffectiveGravity()` samples Y velocity for the subtraction. The base `getGravity()` value and cap expression `Math.min(getGravity(), 0.01)` are the same; the changed operation is when the sign predicate is read.

Movement meaning: During ordinary local-player air travel with Slow Falling and downward velocity, an unsuppressed bed or slime landing can reverse Y velocity in the helper's `move(SELF, ...)` (`Entity.move`, 1.21.1 lines 657–660; 1.21.2 lines 682–686; `BedBlock`/`SlimeBlock.updateEntityAfterFallOn` in 1.21.1 and `updateEntityMovementAfterFallOn` in 1.21.2). The 1.21.2 clipped-Y bounce callback is guarded by `isControlledByLocalInstance` in `Entity.move` lines 682–686; the local player in this witness satisfies that gate. The 1.21.1 tick retains its pre-move nonpositive sample and subtracts capped gravity; 1.21.2 samples the positive bounced velocity and subtracts full gravity. The resulting Y velocity differs after travel. A normal floor collision that leaves Y at zero still takes the capped branch in both versions.

This is a gravity-sampling timing finding. It does not claim a change to the base gravity value or to separate fall-distance accumulation, reset, or landing effects; `Entity.move` invokes fall-damage bookkeeping before the post-move block bounce callback.

## Dry applicability: ordinary air travel

In each of the 32 exact stable releases listed below, a locally controlled, gravity-enabled player in a loaded chunk with Slow Falling and nonpositive pre-move Y velocity takes the ordinary air-travel path that samples that predicate before movement, selects the Slow Falling gravity cap from that sample, calls `move(SELF, ...)`, and then subtracts the saved gravity in the non-fluid, non-Elytra, non-Levitation branch. Thus an intervening movement response that makes Y velocity positive still leaves the pre-move Slow Falling sample in effect through 1.20.4. This is a source applicability check, not a claim that every tick enters that branch. The existing 1.21.1→1.21.2 bounce witness remains the concrete changed case.

The ranges below are in the canonical exact-version source tree `decompiled_minecraft/`; each names that release’s `LivingEntity` method containing both the sample and later ordinary-branch gravity subtraction. The 1.13 implementation is in `moveRelative`; later rows use `travel`. Release IDs are enumerated to make the checked set explicit.

| Exact release(s) | Mapping / source | Sample-to-subtraction lines |
| --- | --- | --- |
| 1.13 | Ornithe Feather, `1.13/ornithe-feather/net/minecraft/entity/living/LivingEntity.java` | 1461–1569 |
| 1.13.1, 1.13.2 | Ornithe Feather, `<version>/ornithe-feather/net/minecraft/entity/living/LivingEntity.java` | 1480–1588 |
| 1.14 | Ornithe Feather, `1.14/ornithe-feather/net/minecraft/entity/living/LivingEntity.java` | 1782–1856 |
| 1.14.1 | Yarn, `1.14.1/yarn/net/minecraft/entity/LivingEntity.java` | 1763–1840 |
| 1.14.2 | Yarn, `1.14.2/yarn/net/minecraft/entity/LivingEntity.java` | 1769–1846 |
| 1.14.3 | Yarn, `1.14.3/yarn/net/minecraft/entity/LivingEntity.java` | 1779–1856 |
| 1.14.4 | Mojmap, `1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java` | 1782–1859 |
| 1.15, 1.15.1, 1.15.2 | Mojmap, `<version>/mojmap/net/minecraft/world/entity/LivingEntity.java` | 1831–1908 |
| 1.16, 1.16.1 | Mojmap, `<version>/mojmap/net/minecraft/world/entity/LivingEntity.java` | 1912–2036 |
| 1.16.2, 1.16.3, 1.16.4, 1.16.5 | Mojmap, `<version>/mojmap/net/minecraft/world/entity/LivingEntity.java` | 1914–2038 |
| 1.17 | Mojmap, `1.17/mojmap/net/minecraft/world/entity/LivingEntity.java` | 1999–2123 |
| 1.17.1 | Mojmap, `1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java` | 2003–2127 |
| 1.18, 1.18.1 | Mojmap, `<version>/mojmap/net/minecraft/world/entity/LivingEntity.java` | 2005–2129 |
| 1.18.2 | Mojmap, `1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java` | 2009–2133 |
| 1.19, 1.19.1, 1.19.2 | Mojmap, `<version>/mojmap/net/minecraft/world/entity/LivingEntity.java` | 2042–2166 |
| 1.19.3 | Mojmap, `1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java` | 2052–2173 |
| 1.19.4 | Mojmap, `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java` | 1983–2104 |
| 1.20, 1.20.1 | Mojmap, `<version>/mojmap/net/minecraft/world/entity/LivingEntity.java` | 2004–2123 |
| 1.20.2 | Mojmap, `1.20.2/mojmap/net/minecraft/world/entity/LivingEntity.java` | 2038–2157 |
| 1.20.3, 1.20.4 | Mojmap, `<version>/mojmap/net/minecraft/world/entity/LivingEntity.java` | 2035–2154 |

The 1.14.1 and 1.14.2 Yarn sources name the effect `StatusEffects.field_5906`; each same-version `entity/effect/StatusEffects.java` registers that field as `"slow_falling"` (line 85). The 1.14 Feather source names it `StatusEffects.SLOW_FALLING`; its same-version `entity/living/effect/StatusEffects.java` registers it as `"slow_falling"` (line 86). From 1.14.3 onward the relevant effect is named `SLOW_FALLING` in these mappings.

### 1.20.5 attribute transition

`1.20.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, `getDefaultGravity()` and `travel(Vec3)`, lines 2100–2110 and 2212–2217, plus `handleRelativeFrictionAndCalculateMovement(Vec3,float)`, lines 2269–2273: the default gravity now comes from `Attributes.GRAVITY`, but `travel` still reads the downward-velocity predicate and applies `Math.min(gravity, 0.01)` before calling the ordinary movement helper at line 2212. That helper calls `move(SELF, ...)` at line 2272, after which `travel` subtracts the saved gravity at line 2217. The attribute-backed base therefore does not introduce the post-move sampling seen in 1.21.2. This records the applicability boundary without treating the independent base-gravity change as part of this finding.

### 1.12.2 absence

The exact `1.12.2/ornithe-feather/net/minecraft/entity/living/effect/StatusEffects.java` registry (lines 1–74) has no Slow Falling effect, and `LivingEntity.java` has no Slow Falling travel predicate; its ordinary air branch subtracts fixed `0.08` after movement (lines 1514–1527). The Slow Falling applicability claim therefore does not extend to 1.12.2.

This supplement does not analyze the fall-distance reset adjacent to the Slow Falling gate or any independent reset path; it excludes water/lava movement and Elytra travel. It performs no runtime or trajectory test.
