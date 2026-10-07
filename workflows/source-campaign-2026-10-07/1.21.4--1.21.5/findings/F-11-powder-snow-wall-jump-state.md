# F-11: Powder-snow wall-jump check switches to previous-tick contact state

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S3-CLIMB, S3-AIR, S2-STATE-WRITERS
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A: `ready/1.21.4/mojmap.sources.sha256`; `LivingEntity#handleRelativeFrictionAndCalculateMovement()` lines 2362-2372 tests `getInBlockState().is(Blocks.POWDER_SNOW)` when horizontal collision or jumping, then writes vertical velocity `0.2` if `canEntityWalkOnPowderSnow` is true (LivingEntity SHA-256 `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`). `Entity#getInBlockState()` lines 3360-3365 lazily reads the block at `blockPosition()` (Entity SHA-256 `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`). `Entity#tick()` lines 451-452 snapshots and clears the powder-snow flags; `PowderSnowBlock#entityInside()` lines 60-82 sets `isInPowderSnow` (PowderSnowBlock SHA-256 `a018411167f5d8b60b131c3f6387dcdd256b736ef64126bdcf33fd4457151654`).
- B: `ready/1.21.5/mojmap.sources.sha256`; `LivingEntity#handleRelativeFrictionAndCalculateMovement()` lines 2394-2404 tests `wasInPowderSnow` under the same horizontal-collision-or-jumping guard, then writes the same `0.2` vertical velocity (LivingEntity SHA-256 `a8aed863d4fdc515c751dd2878a8bbc13179228cb8dbb50edf1d19cd5404271`). `Entity#tick()` lines 459-460 snapshots `isInPowderSnow` to `wasInPowderSnow` and clears the current flag (Entity SHA-256 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`). The queued `InsideBlockEffectType.FREEZE` effect sets the current flag when powder-snow contact is applied (InsideBlockEffectType SHA-256 `2b3d7b27bc5242bc1394843a8c9ae227deb550c730d2a4c24dd3fb2b59df2d68`).

## Source-level difference

For horizontal-collision or jump cases where the powder-snow walk condition matters, A queries the block state returned by `getInBlockState()` during travel. B instead checks `wasInPowderSnow`, a field copied from `isInPowderSnow` at the entity tick boundary. B therefore uses the previous contact flag rather than the same-tick block-state predicate; the results can diverge around entering or leaving powder snow. The `0.2` upward velocity assignment and `canEntityWalkOnPowderSnow` condition are unchanged.

## Reachability and dependencies

Player `LivingEntity.travelInAir` reaches `handleRelativeFrictionAndCalculateMovement` before `Entity.move`, then evaluates this predicate when horizontal collision or jumping is true. Powder-snow contact updates the current flag through `entityInside` (A directly; B through the inside-effect collector), and `Entity.tick` rolls that flag into `wasInPowderSnow` at the next tick boundary. `canEntityWalkOnPowderSnow`/equipment conditions remain a modifier dependency; F-01 covers the separate callback-application path.

## Consequence and uncertainty

The source proves the predicate reads different state: a lazily accessed block state in A and the prior tick's contact flag in B. This can change whether the `0.2` vertical velocity is applied when a collision or jump coincides with a powder-snow entry/exit boundary. No runtime trajectory was measured.

## Handoff

Independent delta: powder-snow wall-jump activation reads prior contact state in B. Related finding IDs: F-01, F-08. Applicability requires horizontal collision or jumping, and the powder-snow walking predicate to be relevant. Exact first release within the pair is unknown.
