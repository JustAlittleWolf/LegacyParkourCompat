# Discarded candidate: Shulker Bullet target reacquisition and Player Levitation

- **1.21.5**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.5--1.21.8/findings/F-SHULKER-BULLET-PLAYER-LEVITATION.md); lines 193-254.
- **1.21.8**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.5--1.21.8/findings/F-SHULKER-BULLET-PLAYER-LEVITATION.md); lines 192-251.

## Difference

- 1.21.5 evidence: `ShulkerBullet` stores a target entity reference (`finalTarget`) and a serialized UUID `targetId`; in `tick()` lines 193-254 it resolves `targetId` only if `finalTarget == null` (lines 197-201), then aims using the retained entity at lines 204-252.
- 1.21.8 evidence: `finalTarget` is an `EntityReference<Entity>`; `tick()` lines 192-251 resolves it through `EntityReference.get(finalTarget, level, Entity.class)` every server tick (lines 194-198), then uses that resolved target for steering and next-direction selection.

The 1.21.8 reference resolution can recover an entity by UUID from the level lookup while 1.21.5 retains the original removed entity object and does not repeat its `targetId` lookup unless the field is null.
- Artifact provenance: exact 1.21.5/1.21.8 Mojmap source manifests and readiness records are recorded in `run.md`; cited source files were verified against those manifests.

No derived mapped jar or bytecode inference is used.

## Movement meaning

- Disposition: The only changed path is target reacquisition/steering of the non-player projectile and its resulting hit/effect application.

The Player's direct movement consumer for Levitation is unchanged.

Per the audit scope, projectile physics and effect-state production are excluded; do not reconcile this delta as a player movement mechanic.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.5--1.21.8/findings/F-SHULKER-BULLET-PLAYER-LEVITATION.md).
