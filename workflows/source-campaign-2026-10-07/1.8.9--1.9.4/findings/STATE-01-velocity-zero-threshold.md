# STATE-01: per-axis velocity cutoff is lower

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: living pre-travel velocity cutoff; `STATE-01`
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A: source manifest A; `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; `LivingEntity#mobTick()V`, lines 1406-1416; SHA-256 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`.
- B: source manifest B; `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; `LivingEntity#mobTick()V`, lines 1666-1676; SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`.

## Source-level difference

Before jump and travel, both versions independently test the absolute value of `velocityX`, `velocityY`, and `velocityZ` and replace a component with zero only when it is strictly below the threshold. A uses `0.005`; B uses `0.003`. For a component whose absolute value is at least `0.003` and below `0.005`, B retains the value while A zeros it. Equality at either boundary is not zeroed by that component's strict comparison.

## Reachability and dependencies

The cutoffs are in `LivingEntity.mobTick`, before the AI/jump/travel sections and before `moveRelative(FF)V`. The compared fields can carry prior or externally supplied velocity. All velocity writers and later branch consumers remain in the open `INV-STATE` and `INV-EXTERNAL` inventories.

## Consequence and uncertainty

The source proves a changed pre-travel velocity state for the specified component interval. It does not establish any observed position or trajectory. Exact introduction point is unknown within the endpoint interval.

## Handoff

Independent delta: strict per-component near-zero cutoff in living tick. Runtime validation is deferred.
