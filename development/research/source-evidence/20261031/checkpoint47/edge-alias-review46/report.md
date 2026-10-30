# Independent product range integration review — edge alias 46

## Verdict

**ACCEPT, bounded to the reviewed source range and code path.** The version-resolver range is structurally correct, and the 1.20.2 handler reproduces the audited `maybeBackOffFromEdge` control flow, arithmetic, and collision-probe composition for 1.19 through 1.20.4. The initial review raised a candidate-box/world-border mismatch in the resolved collision-query hook; resolver inspection corrected that concern: the only query hook is V1_17_1, older than selected 1.19, so it is excluded. PlayerMixin passes `null` and `SneakEdgeBackoff` calls native `player.level().noCollision(player, candidate)`, matching the helper's query.

## Reviewed identity and scope

Reviewed the final integration commits `82caf0eea084f2b33f84e9d8db67fd13b610581e` and `d5973a7adf74e6891532aea2ad1db004ae058cec`. Their combined net diff from the parent of `82caf0e` deletes `change/v1_19_3/SneakEdgeNativeFence.java`, edits the source comments on `change/v1_20_2/SneakEdge.java`, and leaves no `change/v1_20_5/SneakEdgeNativeFence.java` (introduced in the first commit, then removed in the correction commit). No product file was edited in this review.

Inputs inspected:

- `development/research/findings/1.20.4--1.20.6/edge-backoff-probe-geometry.md`.
- `development/research/source-evidence/20261031/checkpoint45/119-edge-alias45/report.md`, SHA-256 `6bb3ae3009846a65c83c7dd0b52bcf12f0bc934cd8183084926a51a4643b39f2`.
- Exact Mojmap `Player.java` at 1.19.3, 1.19.4, 1.20.4, 1.20.5 and 1.20.6; exact Mojmap `CollisionGetter.java` at 1.19.3 and 1.20.4, in the shared read-only decompiler source tree.
- Product `ChangeResolver`, `PlayerMixin`, `SneakEdge`, `SneakEdgeBackoff`, `SneakEdgeCollisionQuery`, and `SneakEdgeCollisionSupport`.

## Confirmed range and helper behavior

For selected 1.19–1.20.4 profiles, removing the V1_19_3 `SneakEdgeHook` candidate causes `ChangeResolver` to choose the V1_20_2 handler, the nearest applicable `emulates >= selected` implementation. V1_18_2 remains selected for 1.18.2 profiles because the V1_20_2 candidate is farther than its V1_18_2 handler. For selected 1.20.5 and later, V1_20_2 is older than the selection and is excluded; no method-level hook resolves, so native `maybeBackOffFromEdge` runs. Therefore no V1_20_5 upper fence is required by resolver mechanics.

The 1.20.2 adapter’s outer test matches the 1.19.3, 1.19.4, and 1.20.4 source order: not flying; requested Y `<= 0`; SELF or PLAYER mover; `isStayingOnGroundSurface`; then `isAboveGround`. PlayerMixin supplies the native `isStayingOnGroundSurface()` result and passes the resolved edge distance based on `player.maxUpStep()`. The adapter preserves X, then Z, then joint reduction; the asymmetric `value < 0.05 && value >= -0.05` cutoff; 0.05 signed steps; and original Y in the returned vector. These match the audited source helpers.

The old source field `fallDistance` is float. In the current target it is double, so the explicit `(float) player.fallDistance` followed by float subtraction into `downwardOffset` preserves the historical comparison and offset rounding before widening for `AABB.move`. For ordinary player movement, no SneakEdgeDistanceHook is applicable at 1.19+, and PlayerMixin falls back to the player's current `maxUpStep()` value (normally 0.6F). Although 1.19.4/1.20.4 call virtual `maxUpStep()` and `LivingEntity` overrides it for mobs with a controlling player, the actual Player/LocalPlayer hierarchy inherits `Entity.getControllingPassenger()`, whose implementation returns `null`; therefore the inherited override returns the same backing step value here. The 1.19–1.19.3 direct-field form and the later getter form agree for the actual vanilla Player hierarchy.

## Resolver selection and query composition

`ChangeResolver` admits only candidates whose `emulates` is greater than or equal to the selected profile, then picks the lowest ordinal. The discovered `SneakEdgeCollisionQueryHook` changes are annotated V1_8_9, V1_10_1, V1_15_2, V1_16, and V1_17_1. All are older than selected 1.19 and therefore none resolves for selected 1.19–1.20.4. `PlayerMixin` supplies `null`; `SneakEdgeBackoff.noCollision` then executes `player.level().noCollision(player, candidate)`. Mojmap 1.19.3 and 1.20.4 `CollisionGetter.noCollision(entity, AABB)` use that candidate for block collisions, entity collisions, and the border intersection. Thus the earlier concern that `V1_17_1/SneakEdgeCollisionQuery` could change these calls was a resolver-direction mistake and is withdrawn.

The separately dispatched `SneakEdgeProbeHook` is different: its sole V1_21_4 change is newer than selected 1.20.5+, so it is eligible when native 1.20.5+ `maybeBackOffFromEdge` reaches `canFallAtLeast`. This preserves the feet-slab probe calculation while leaving that version's native signed-step/cutoff loops intact. For selected 1.19–1.20.4, the method-level V1_20_2 hook cancels native `maybeBackOffFromEdge`, so that later native-only helper is not used by this path.

## Disposition and limits

The V1_19_3 fence is redundant for the reviewed 1.19–1.20.4 alias range: V1_20_2 is the nearest applicable method handler and its current resolved inputs and query fallback match the audited old operation. No V1_20_5 upper fence is needed: V1_20_2 is too old to resolve for 1.20.5+, leaving the native method available. The handler remains independent from the 1.20.5+ probe operation. This is source/integration correspondence only; it does not establish runtime parity. No build or runtime verification was performed or requested.
