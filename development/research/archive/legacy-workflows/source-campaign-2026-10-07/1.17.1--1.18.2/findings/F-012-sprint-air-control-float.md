# F-012: sprint air-control coefficient rounds one float step lower in A

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: T-PLAYER-AIR-CONTROL, INV-TICK, INV-STATE
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player sprint state set after a tick and consumed during following airborne land movement with nonzero input
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Paired evidence

- A `Player#aiStep`, lines 504-510, sets `flyingSpeed = 0.02F` and, while sprinting, assigns `(float)(this.flyingSpeed + 0.005999999865889549)`. `Player.java` SHA-256 `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481`.
- B `Player#aiStep`, lines 506-512, sets the same `0.02F` and, while sprinting, uses compound float addition `this.flyingSpeed += 0.006F`. `Player.java` SHA-256 `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a`.
- For the fixed base value, A's double-literal expression rounds to float `0.025999998673796654` (bits `0x3cd4fdf3`); B's float-literal addition yields `0.026000000536441803` (bits `0x3cd4fdf4`). This is a one-ULP difference.
- A `LivingEntity#handleRelativeFrictionAndCalculateMovement/#getFrictionInfluencedSpeed`, lines 2155-2201, uses `flyingSpeed` as the friction-influenced speed when `onGround` is false; `moveRelative` applies the movement input with that coefficient. `LivingEntity.java` SHA-256 `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f`.
- B same methods, lines 2161-2207, retain that consumer formula. `LivingEntity.java` SHA-256 `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782`.
- The writer runs after `super.aiStep()` in Player. The LocalPlayer tick invokes the Player movement tick; the value set at that end-of-tick writer is available to the next airborne, non-fluid travel before the next writer runs.

## Reachability and consequence

When a player is sprinting during `Player#aiStep`, the two versions store adjacent float values. If the following movement tick is airborne, uses ordinary non-fluid/non-fall-flying travel, and has nonzero input, `getFrictionInfluencedSpeed` supplies the differing value to `moveRelative`; the acceleration and resulting requested movement vector can therefore differ. No block, damage, or runtime trajectory is needed to reach the source path.

This is a source-proven coefficient and consumer delta, not an observed displacement. Collision resolution and later accumulated movement are not simulated here.

## Handoff

Independent player movement math finding. The direct formula is one ULP different in the stated reachable state. No implementation or runtime disposition is assigned.
