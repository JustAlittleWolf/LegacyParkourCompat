# Source comparison: ordinary vertical air integration, 1.18.2 to 1.19

## Verdict

**No in-scope difference found** for a local, dry, ordinary living-player air tick with no vehicle, gliding, flying, status effects, or block/fluid/climbable interactions. The scoped travel branch applies the same vertical gravity and drag in the same order, and the shared movement path makes the same vertical position request and fall-distance update. There is no canonical difference finding for this slice.

## Pinned evidence identity

Both sides are exact Mojmap source trees. The three root identity files match the assignment pins (SHA-256):

| Release | `mojmap.ready.json` | `mojmap.provenance.json` | `mojmap.sources.sha256` |
|---|---|---|---|
| 1.18.2 | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |
| 1.19 | `b00a68db8600ec8cdc3917357aff59eb0537be6f382e621e5ed3eeb900898b88` | `833cf50d6eab0f24475dcc11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` |

Relevant Java files are present in each corresponding source manifest, and their local SHA-256 values match the manifest entries:

| Release | Source file and compared member | File SHA-256 |
|---|---|---|
| 1.18.2 | `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2009–2145 | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |
| 1.19 | `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2042–2178 | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` |
| 1.18.2 | `net/minecraft/world/entity/Entity.java`, `move(MoverType, Vec3)`, lines 543 onward; `checkFallDamage(double, boolean, BlockState, BlockPos)`, lines 979–992; `resetFallDistance()`, lines 2114–2116 | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |
| 1.19 | `net/minecraft/world/entity/Entity.java`, `move(MoverType, Vec3)`, lines 547 onward; `checkFallDamage(double, boolean, BlockState, BlockPos)`, lines 991–1002; `resetFallDistance()`, lines 2138–2140 | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` |

The cited travel bodies run through their closing brace at 1.18.2 line 2145 and 1.19 line 2178. The compared entity movement and fall-state methods are present with intact bodies in the manifest-pinned files.

## Compared behavior and coverage

- `LivingEntity.travel(Vec3)` has the same movement-control gate and ordinary non-fluid/non-fall-flying `else` branch in both releases. With the assigned no-effect assumptions, the ordinary branch starts from the same post-relative-movement Y velocity, subtracts the same `0.08` double gravity constant when gravity is enabled, then either leaves Y unchanged under `shouldDiscardFriction()` or multiplies it by `0.98F`. The gravity-free gate and the `shouldDiscardFriction()` split are unchanged. Order, constants, types, and writes match (1.18.2 lines 2009–2017, 2117–2141; 1.19 lines 2042–2050, 2150–2175).
- The ordinary movement helper moves using the current velocity before the gravity/drag update; the identical branch then stores the post-move Y velocity. Treating collision as the requested boundary, `move(MoverType.SELF, getDeltaMovement())` and the Y position addition are unchanged. The shared collision method's vertical collision predicate remains `requestedY != resolvedY`, and its grounded predicate remains vertical collision with a negative requested Y (1.18.2 `Entity.java` lines 543–594; 1.19 lines 547–598).
- Common fall-distance behavior is unchanged: on ground it resets to `0.0F`; otherwise, for a negative resolved Y displacement, it subtracts that displacement cast to `float`. The reset method still writes `0.0F` (1.18.2 `Entity.java` lines 979–992, 2114–2116; 1.19 lines 991–1002, 2138–2140). The ordinary `LivingEntity` override still delegates to this method after its server-side effects (1.18.2 `LivingEntity.java` lines 290–310; 1.19 lines 292–312).
- Numeric witness for the no-collision, gravity-enabled, non-discarded-friction boundary: with incoming vertical velocity `0.0`, both releases request `+0.0` Y for movement, then store `(-0.08) * 0.98F = -0.0784000015258789` Y velocity. The next ordinary tick uses that same value for the Y request. This is a calculation from the inspected source expressions, not a runtime trace.

## Dependencies, exclusions, and boundaries

Closed for this slice: `LivingEntity.travel` ordinary branch, its call into `handleRelativeFrictionAndCalculateMovement`/`Entity.move`, the vertical collision/grounded predicates at the movement boundary, fall-distance accumulation/reset, and the `LivingEntity` override's delegation to the common handler.

Assumed or excluded as assigned: input/jump causation; effects and equipment; angle/horizontal motion; fluids, statuses, climbables, blocks, support and collision-shape/provider behavior; vehicle, gliding and flying branches; visual/network behavior; damage calculation and server-only fall effects. `Entity.move` changes the support-position lookup name (`getOnPos` to `getOnPosLegacy`) and nearby step-on/game-event logic across releases; those support/block/event details are outside this bounded vertical integration comparison. They do not change the compared requested Y, Y position addition, vertical collision/grounded expressions, or fall-distance arithmetic at this boundary.

## Coverage status

Examined: exact source identities; complete `LivingEntity.travel(Vec3)` method boundaries and scoped ordinary branch; `Entity.move` vertical request/position/collision/fall-handler call; common `Entity.checkFallDamage` accumulation/reset and `resetFallDistance`; `LivingEntity.checkFallDamage` delegation.

Not examined: other movement branches and dependencies explicitly excluded above; no claim of full-pair inventory closure or independent full-pair audit.

Canonical relative finding(s): **none** (no scoped difference).
