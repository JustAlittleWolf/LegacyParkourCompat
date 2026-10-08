# F-05: Player horizontal velocity is cut off by vector length in 1.21.5

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S3-GRAVITY-DRAG
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A: `ready/1.21.4/mojmap.sources.sha256`; `net/minecraft/world/entity/LivingEntity.java`; `LivingEntity#aiStep()` lines 2708-2724 independently zeroes `x` and `z` when `Math.abs(component) < 0.003`; the same `0.003` cutoff applies to `y` (SHA-256 `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`).
- B: `ready/1.21.5/mojmap.sources.sha256`; `LivingEntity.java`; `LivingEntity#aiStep()` lines 2686-2709 uses a special `EntityType.PLAYER` branch: if `horizontalDistanceSqr() < 9.0E-6`, both `x` and `z` are zeroed; non-player entities retain the old per-component comparisons. The `y` cutoff remains `Math.abs(y) < 0.003` (SHA-256 `a8aed863d4fdc515c751dd2878a8bbc13179228cb8dbb50edf1d19cd5404271`).

## Source-level difference

For a player with horizontal velocity `(0.0025, 0.0025)`, A zeros both components because each absolute value is below `0.003`. B preserves both because their squared horizontal length, `1.25E-5`, is greater than the `9.0E-6` cutoff. The reverse shape of difference is not possible: if a player's squared horizontal length is below `9.0E-6`, each component is necessarily below `0.003`, so both versions zero both. Thus B retains some diagonal low-speed vectors that A removes. Vertical cutoff and non-player cutoff are unchanged.

## Reachability and dependencies

`LivingEntity.aiStep` executes this velocity cleanup before player input, jumping and travel. LocalPlayer reaches the inherited method via `super.aiStep()`. The changed components are written through `setDeltaMovement` and then consumed by travel. The branch is selected by the vanilla `EntityType.PLAYER` identity; server/client movement authority controls later travel but not this cutoff.

## Consequence and uncertainty

The source proves the differing threshold geometry and a concrete velocity precondition. B can carry a retained diagonal low-speed horizontal velocity into subsequent travel where A starts that stage at zero horizontal velocity. No trajectory or persistence length was measured; later drag, input, collision and branch selection affect the result.

## Handoff

Independent delta: player-specific horizontal velocity dead-zone predicate. Related finding IDs: F-03. Applicability requires both horizontal components individually below `0.003` while their squared sum is at least `9.0E-6`. Exact first release within the pair is unknown.
