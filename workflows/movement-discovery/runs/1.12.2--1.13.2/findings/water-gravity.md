# 1.13.2 reduces ordinary water-travel gravity

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: 3.4 (water travel gravity)
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player movement in water present in both versions
- First changed release: unknown within (1.12.2, 1.13.2]
- Runtime validation: not performed

## Paired evidence

- A: `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative(FFF)V`, lines 1555-1584; SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`. In its water branch, after vertical damping, if gravity applies it subtracts `0.02` from `velocityY`.
- B: corresponding method, lines 1617-1655; SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`. With gravity enabled and sprinting false, it uses `d / 16.0` and a near-terminal-value clamp. `d` defaults to `0.08`; only when `velocityY <= 0.0` and Slow Falling is active does the prelude set `d = 0.01` (B lines 1478-1484). Thus without Slow Falling the ordinary subtraction is `0.08 / 16.0 = 0.005`, not `0.02`.
- Reachability: A/B `LivingEntity.mobTick()` calls virtual `moveRelative()` during movement (A lines 1842-1848; B lines 1920-1926). Local player state reaches this water branch whenever the player is in water and not fall-flying/otherwise taking the water path.

## Source-level difference

For a non-sprinting player in water with gravity enabled and no Slow Falling effect, A subtracts `0.02` after multiplying vertical velocity by `0.8F`; B subtracts `0.005` after the same damping. B also applies a narrow near-zero adjustment before that subtraction. The resulting water vertical integration differs even when the new Slow Falling effect is absent.

## Reachability and dependencies

This is the water case of the `LivingEntity.moveRelative()` dispatcher. Water contact is maintained by the entity fluid-state path; the local player invokes the same inherited movement route. Sprinting skips B's water gravity update and is separately documented in `water-sprint-travel.md`. Fluid flow, depth strider, player swimming pitch input, collision and server-provided state remain dependencies in stages 4-6.

## Consequence and uncertainty

Source proves B's ordinary downward adjustment is one quarter of A's before the narrow clamp. It predicts different vertical velocity and water travel, but no trajectory was measured. Slow Falling changes `d` and should be classified separately as a new effect; this finding excludes it from the stated comparison.

## Handoff

Ordinary water gravity adjustment. Related to sprinting water travel and swim controls, but scoped to non-sprinting gravity integration.
