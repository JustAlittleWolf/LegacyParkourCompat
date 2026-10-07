# F006: Ground acceleration operation order

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: ground acceleration; S005
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `LivingEntity.java`::`moveRelative(float,float,float)`, lines 1537-1553, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `LivingEntity.java`::`m_70235197(float)` and caller in `moveRelative(Vec3d)`, lines 1841-1845 and 1961-1962, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.

## Source-level difference

A computes `t = slipperiness * 0.91F`, then `u = 0.16277137F / (t * t * t)`, then multiplies `getSpeed() * u`. B's helper returns `getSpeed() * (0.21600002F / (slipperiness * slipperiness * slipperiness))` when grounded. The algebraic forms are close, but constants and float operation order differ.

## Reachability and dependencies

Ordinary ground/air travel samples the block below the entity shape for slipperiness and uses onGround to select the helper result. Block slipperiness assignments/registrations and helper call coverage remain open in INV-WORLD-MOVEMENT and INV-TICK.

## Consequence and uncertainty

Source proves different float constants and intermediate rounding. A per-input numeric difference is plausible but not established by an executed numeric comparison; no trajectory claim is made.

## Handoff

Independent arithmetic delta; first changed release unknown. Implementation/testing deferred.
