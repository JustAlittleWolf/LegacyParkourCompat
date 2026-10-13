# F-S4-PLAYER-ENTITY-PUSH — source finding snapshot

- **1.21.10**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.10--1.21.11/findings/F-S4-PLAYER-ENTITY-PUSH.md).
- **1.21.11**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.10--1.21.11/findings/F-S4-PLAYER-ENTITY-PUSH.md).

## Difference

For a Player in the paired `can_glide_through` block states while fall-flying, the 1.21.11 source can apply a direct Player velocity response from nearby entity pushing where 1.21.10 does not.

This is a separate consumer of the climbable tag difference recorded in `F-S3-GLIDE-THROUGH-CLIMBABLE`; it does not broaden that finding's fall-flying travel claim.

## Movement meaning

The bounded state requires an alive, nonspectator Player who is fall-flying with the flight ability disabled, and whose current block state is one of the seven states added to the 1.21.11 `can_glide_through` tag expansion.

Existing `onClimbable()` logic then returns true in 1.21.10 and false in 1.21.11.

The vanilla tick path reaches `LivingEntity.pushEntities()` through virtual `tick()`/`aiStep()` dispatch and Player's super calls.

If a nearby pushable entity is returned, the entities are not in the same vehicle, neither relevant entity is `noPhysics`, the Player is not a vehicle, and the horizontal separation meets `Mth.absMax(dx,dz) >= 0.01F`, the Player's argument-side push guard rejects the direct velocity write in 1.21.10 and permits it in 1.21.11.

The write adds the computed vector to Player delta movement. 1.21.10/1.21.11 preserve the same push formula; 1.21.11 checks all three vector components are finite and sets `needsSync`, while 1.21.10 adds the vector and sets `hasImpulse`.

This is a source-level movement consequence only.

`S4-player-entity-push` closes the direct Player pushability dependency `D-S3-CLIMBABLE-PUSHABILITY`.

Non-player movement, vehicle movement, damage, and combat are excluded.

Independent finding-specific blind source review is pending.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.10--1.21.11/findings/F-S4-PLAYER-ENTITY-PUSH.md).
