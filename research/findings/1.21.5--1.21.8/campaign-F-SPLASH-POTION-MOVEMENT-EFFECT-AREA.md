# Discarded candidate: splash-potion impact geometry and movement-effect state

- **1.21.5**, mojmap: `AbstractThrownPotion#onHit()`; lines 73-87.
- **1.21.8**, mojmap: `AbstractThrownPotion#onHit()`; lines 70-84.

## Difference

- 1.21.5 evidence: `AbstractThrownPotion#onHit()` passes the directly hit entity (or null on a block hit) to `onHitAsPotion()` (1.21.5 lines 73-87). `ThrownSplashPotion#onHitAsPotion()` lines 39-75 searches an area around the potion's bounding box, computes distance to each LivingEntity position, gives a directly hit entity scale 1, and otherwise uses `1 - sqrt(distanceSquared)/4`; that scale changes instantaneous effect strength or non-instantaneous effect duration.
- 1.21.8 evidence: `AbstractThrownPotion#onHit()` now passes the complete `HitResult` to the same server-only callback (1.21.8 lines 70-84). `ThrownSplashPotion#onHitAsPotion()` lines 39-72 anchors the search box at `HitResult#getLocation()`, inflates each target bounding box by `ProjectileUtil.computeMargin(this)`, computes squared distance from the shifted impact box to that inflated bounding box, and applies one falloff formula without 1.21.5's direct-hit special case.
- Artifact provenance: exact 1.21.5/1.21.8 Mojmap source manifests and readiness records are recorded in `run.md`; both cited source files were verified against those manifests.

No derived mapped jar or bytecode inference is used.

## Movement meaning

- Player reachability: the callback enumerates `LivingEntity` targets, including Player, and applies every effect present in `PotionContents`. 1.21.5/1.21.8 `Potions.java` is source-identical For a `PotionContents` list carrying one of these effects, the changed scale changes its applied duration; for an instantaneous movement effect, it changes applied effect strength.

Jar resources and default item-component bindings remain outside this source-only boundary.
- Disposition: This changes potion effect application geometry/state production; it does not change the direct Player movement numeric consumers.

Per the audit scope, effect-state production remains excluded, so do not reconcile this delta as a player movement mechanic.

Resource-backed item bindings remain uninspected.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.5--1.21.8/findings/F-SPLASH-POTION-MOVEMENT-EFFECT-AREA.md).
