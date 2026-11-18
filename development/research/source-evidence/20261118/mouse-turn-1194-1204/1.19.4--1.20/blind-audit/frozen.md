# Blind source audit: 1.19.4 to 1.20

## Scope and verdict

Independent source-only audit of the ordinary local mouse-turn callback path and the first active player flight movement paths. Compared exact Mojmap `1.19.4` and official release ID `1.20`. This is a bounded verdict, not a complete player-tick inventory or pair freeze.

- Ordinary local, captured, window-active, smooth-camera-off, unscoped callback with finite accumulated deltas: no source difference in delta arithmetic, branch order, reset order, Y inversion, or call into the player.
- `Entity.turn(double,double)` and its virtual pitch setter/clamp sequence: no source difference.
- Creative flight movement and Elytra flight movement: no difference in active movement arithmetic/order or their examined gates in `LocalPlayer.aiStep`, `Player.travel`, and the `isFallFlying()` branch of `LivingEntity.travel`.
- Residual directly adjacent to the flight travel path: the slow-falling gravity preamble in `LivingEntity.travel` resets fall distance in 1.19.4 and no longer does so in 1.20 when vertical velocity is nonpositive and slow falling is active. The checked Elytra velocity/position calculation remains textually equivalent; this removed counter write is a player movement-state difference whose later damage consequence is outside this audit's scope.

## Exact source identity and integrity

Canonical inputs were read only from `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.19.4,1.20}/mojmap/`.

For 1.19.4, `mojmap.ready.json` reports `status=ready`, `versionId=1.19.4`, `mapping=mojmap`, and source manifest SHA-256 `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`. The source manifest file has that SHA-256. The adjacent provenance file SHA-256 is `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34`; the ready marker SHA-256 is `59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0`. Pinned artifacts include raw client `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec`, official client mappings `5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606`, and mapped client jar `efbf38c89b396faae60cfe3bdb91671cb8d1ec3ccf2e27d3ffab4d261acd016d`.

For 1.20, the embedded `.decompile-provenance.json` SHA-256 is `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f`. It records raw client SHA-256 `5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff` and exact version metadata URL `https://piston-meta.mojang.com/v1/packages/9e7847f933f4e81e45dd1dfb24bab7d4324ca023/1.20.json` with SHA-256 `24f4700f86471b6f3d3e4d68fefcd4177820dade5ce003c095fcf0e429abbc5f`. The directory is the canonical `1.20/mojmap` output and its embedded source-hash map matches each consumed source file's SHA-256. This older embedded manifest does not separately record an official mapping-artifact URL/hash; the output mapping family and exact consumed mapped source bodies are identified, while that input-level mapping pin is a provenance limitation.

Consumed source file SHA-256 values (actual bytes match the 1.19.4 source manifest / 1.20 embedded source map):

| Source | 1.19.4 | 1.20 |
|---|---|---|
| `net/minecraft/client/MouseHandler.java` | `45e708b9d74447b2f9fa1d44b5af8098ba2414eac4552be9195b55cd299d4a32` | `702359936208db4060f390cf9443520389e987d27c2d3c5237130fea408589e4` |
| `net/minecraft/world/entity/Entity.java` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| `net/minecraft/client/player/LocalPlayer.java` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` |
| `net/minecraft/world/entity/player/Player.java` | `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` |
| `net/minecraft/world/entity/LivingEntity.java` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` |

## Compared operations

### Mouse callback delta calculation

1.19.4 Mojmap `net/minecraft/client/MouseHandler.java`, `turnPlayer()`, lines 212-253; 1.20 same member, lines 212-253. With the callback entry gate satisfied and smooth camera disabled, both evaluate sensitivity as `get() * 0.6F + 0.2F`, cube it, then compute the normal scale with `* 8.0`. The first-person-scope branch is false by the bounded precondition, so both reset `smoothTurnX` and `smoothTurnY` and assign accumulated X/Y deltas multiplied by the normal scale. Both zero accumulated deltas before choosing integer Y inversion (`1` or `-1`), call tutorial `onMouse(x,y)`, then if player is non-null call `player.turn(x, y * inversion)`. Outside the callback gate both clear accumulators. The compared method bodies are identical. Device callback plumbing and production of accumulated deltas are excluded.

Reachability witness: local player exists, window active, mouse grabbed; finite accumulated deltas; `smoothCamera=false`; not both first-person and scoping. For those values the same finite doubles reach the player call in the same order.

### `Entity.turn` and setter/clamp

1.19.4 Mojmap `net/minecraft/world/entity/Entity.java`, `turn(double,double)`, lines 391-403; 1.20 lines 395-407. Both cast Y input to `float` then multiply by `0.15F`; cast X input then multiply by `0.15F`; call virtual `this.setXRot(currentX + pitchDelta)` and virtual `this.setYRot(currentY + yawDelta)`; clamp the resulting current pitch with `Mth.clamp(...,-90.0F,90.0F)` through the virtual pitch setter; then add deltas to old pitch and yaw, clamp only old pitch, and notify the vehicle if present. Order and arithmetic match.

The inherited `Entity.setXRot(float)` is the effective player setter in both mapped trees (no player override): it rejects non-finite input via `Util.logAndPauseIfInIde`, otherwise writes `xRot`. The setter is not itself a pitch clamp; the explicit current-pitch clamp follows the first setter call. The virtual dispatch point/order is unchanged. The 1.20 setter is at lines 3273-3279; 1.19.4 at 3155-3161.

### First active flight movement

The examined client flight activation/input path is `LocalPlayer.aiStep()`: 1.19.4 lines 712-765, 1.20 lines 718-771. The `mayfly`/always-flying or jump-toggle gates and the `flying && isControlledCamera()` vertical input branch match in behavior: shift decrements an integer, jumping increments it, and nonzero input adds `sign * getFlyingSpeed() * 3.0F` to Y velocity. `Player.travel(Vec3)`, 1.19.4 lines 1439-1467 and 1.20 lines 1445-1473, has the same active `abilities.flying && !isPassenger()` branch: save prior Y velocity, delegate to superclass travel, keep resulting X/Z but replace Y with saved Y times `0.6`, reset fall distance, clear shared flag 7, then run movement statistics. No active-flight operation difference was found in these slices.

For Elytra flight, `LivingEntity.travel(Vec3)` is entered only under `isControlledByLocalInstance()`; fluid branches are checked before `isFallFlying()`. In the active `isFallFlying()` branch (1.19.4 lines 2048-2087; 1.20 lines 2068-2107), both first call `checkSlowFallDistance()`, use the same velocity/look/pitch snapshots and same ordered trigonometric and vector arithmetic, multiply final velocity by `(0.99F,0.98F,0.99F)`, set velocity, then call `move(MoverType.SELF, velocity)`. The post-move collision/damage and ground-flag gates also match apart from accessor spelling for client-side/on-ground state. Elytra activation path in `LocalPlayer.aiStep()` retains the same jump, not-already-flying, not-passenger, not-climbable, Elytra-enabled and start-flight checks.

Adjacent residual: before the fluid/Elytra branch selection, `LivingEntity.travel()` calculates `$$2 = deltaMovement.y <= 0.0`. If that is true and slow falling is active, 1.19.4 sets gravity `$$1 = 0.01` and calls `resetFallDistance()` (lines 1983-1990); 1.20 sets the same gravity but omits the reset (lines 2004-2010). This branch precedes `isFallFlying()`, so the condition can hold on a tick that later takes the Elytra branch. It changes fall-distance state, not the checked Elytra velocity equations. Fall-distance effects on eventual damage are not evaluated here.

## Coverage boundary

No whole-tick inventory, non-flight travel branches, all player movement, collision, external response, or intermediate release boundary was audited. The result only closes the callback/turn slices and the named active creative/Elytra flight slices, with the slow-fall preamble discrepancy retained as a residual.