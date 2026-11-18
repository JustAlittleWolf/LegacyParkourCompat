# 1.20.1 → 1.20.2 active Elytra route at climbable and ordinary feet

## Exact source identity

Both sides are canonical exact-release Mojmap source trees. Their `mojmap.ready.json` markers report `status: ready`, exact `versionId` values `1.20.1` and `1.20.2`, and `mapping: mojmap`. I recomputed each source-manifest SHA-256 and checked it against the marker; I also recomputed the cited Java-file SHA-256 values and checked them against the manifest entries.

| Release | Marker source-manifest SHA-256 | `LivingEntity.java` SHA-256 | `Player.java` SHA-256 |
|---|---|---|---|
| 1.20.1 | `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` |
| 1.20.2 | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` |

Marker-side exact client input SHA-256 values are `56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f` (1.20.1) and `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda` (1.20.2). The hidden `mojmap.provenance.json` on each side binds the matching exact release to the Mojmap decompile run. No source generation was run.

## Scoped route and comparison

- Tick order in `LivingEntity.aiStep`: after travel inputs are damped, `updateFallFlying()` runs before `travel(...)` (`1.20.1` `LivingEntity.java:2559-2573`; `1.20.2`: `2588-2602`). After travel and the remaining tick work, the fall-flying tick counter increments when `isFallFlying()` is true and resets to zero otherwise (`1.20.1`: `2348-2352`; `1.20.2`: `2382-2386`). This order is unchanged.
- `updateFallFlying()` first reads shared flag 7, then short-circuits the ordered outer gate `flag && !onGround() && !isPassenger() && !hasEffect(LEVITATION)`. Only when that gate passes does it read the chest slot and require `ELYTRA && ElytraItem.isFlyEnabled(stack)`. Either failed gate assigns the local temporary false; the success path leaves it true and computes `fallFlyTicks + 1`. The only `setSharedFlag(7, value)` is guarded by `!level().isClientSide`, so this method does not directly clear the active flag on the local client when any cancellation predicate fails. `onClimbable()` is not a cancellation predicate. Source: 1.20.1 `LivingEntity.java:2610-2634`; 1.20.2 `2639-2663`. Predicate order and assignments match.
- For an on-foot local player with abilities flight off, `Player.travel` reaches `super.travel(input)`; it bypasses that only when abilities flight is on and the player is not a passenger (`1.20.1` `Player.java:1446-1473`; `1.20.2`: `1450-1477`). In `LivingEntity.travel`, fluid branches precede the Elytra branch, which precedes ordinary friction movement (`1.20.1`: `2012-2068`; `1.20.2`: `2046-2102`). Under the assigned dry, active-glide conditions, the Elytra branch wins before the ordinary branch's climb handling, whether feet are ordinary or climbable.
- Ordinary movement calls `handleRelativeFrictionAndCalculateMovement`; it applies `handleOnClimbable` to velocity before `move`, then may set returned Y velocity to `0.2` on `(horizontalCollision || jumping) && (onClimbable || walkable powder snow)` (`1.20.1` `LivingEntity.java:2171-2181`; `1.20.2`: `2205-2215`). The climb cap helper is identical: after resetting fall distance it clamps X/Z to `[-0.15F, 0.15F]`, floors Y with `max(y, -0.15F)`, and for the ordered non-scaffolding / suppressing-ladder / Player condition changes negative Y to zero (`1.20.1`: `2199-2214`; `1.20.2`: `2233-2248`).
- The glide branch itself computes the glide deltas, applies `multiply(0.99F, 0.98F, 0.99F)`, and calls its first `move(MoverType.SELF, delta)` immediately (`1.20.1` `LivingEntity.java:2068-2094`; `1.20.2`: `2102-2128`). There is no `handleOnClimbable` call or equivalent `±0.15F` / `-0.15F` velocity cap before this first glide move in either version. The cap belongs to the later ordinary-friction route, which glide does not enter. Branch ordering, cap shape, and first-glide-move phase are unchanged.

## Bounded result

No difference was found in the assigned already-active default-chest Elytra, on-foot local-player tick/flag route, direct cancellation gate, movement-branch precedence, climb cap, or cap-to-first-glide-move phase from 1.20.1 to 1.20.2. This is a source comparison for these slices only; it does not establish start eligibility, final tick accumulation beyond the cited counter write, collision/contact behavior, other release boundaries, or runtime parity.
