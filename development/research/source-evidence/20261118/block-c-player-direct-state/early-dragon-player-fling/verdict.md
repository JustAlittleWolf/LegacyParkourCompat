# Independent audit: Ender Dragon creative-player contact fling

## Verdict

COMPLETED — the bounded movement claims are supported, with two precision corrections below. The frozen comparison is unchanged and its SHA-256 is `d51e3a2f30dd54733dfc565af4c66ca7ec88b07e15d6e48e5cdc965b37cf87c7`.

## Claim checks

- **Server authority:** Confirmed. In 1.13.2 the wing-contact and head/neck block is guarded by `!world.isClient && damagedTimer == 0`. The 1.14, 1.14.1, 1.14.2, 1.14.3, and 1.14.4 contact blocks retain the logical-server guard (with the corresponding hurt timer). The dragon helper's direct velocity mutation is therefore not a logical-client-local write in the examined releases.
- **Creative target filtering:** Confirmed. In 1.14–1.14.4 both wing queries pass the creative-and-spectator exclusion predicate. Its exact definition accepts a non-player, or a player only when the player is neither spectator nor creative. In 1.13.2 the call has no explicit filter argument, but the two-argument `EntityView.getEntities` overload supplies `EntityFilter.NOT_SPECTATOR`; that predicate excludes spectators only, so an ordinary creative player remains eligible. `flingMobs` applies `addVelocity` to selected `LivingEntity` instances.
- **Packet receiver/producer:** Confirmed within the bounded evidence. The 1.13.2 client packet handler finds the entity and applies each velocity component divided by 8000 using `lerpVelocity`. On the producer side, `addVelocity` sets `velocityDirty`, and `TrackedEntity` can emit an `EntityVelocityS2CPacket` after the dirty/delta checks. This establishes a server producer and a client consumer, not delivery to the owning local player.
- **Owning-player limitation:** The ordinary tracked-recipient list excludes the entity's own server player (`updatePlayer` checks `player != this.entity`), and the dirty-velocity branch uses `sendPacket`, which iterates that list. A separate `sendPacketToAll` method explicitly adds the owning player, but its observed call here is the `entity.damaged` path. The checked evidence does not establish that the dragon's velocity-dirty update reaches the owning client; leave that effect unclaimed.

## Evidence and provenance

All eight source-file SHA-256 values listed in the frozen comparison were recomputed and match. In addition, the local Yarn trees for 1.14.1, 1.14.2, and 1.14.3 each contain a hidden `yarn/.decompile-provenance.json`; each identifies its version-specific Yarn metadata input, and each dragon source hash matches its manifest entry. The prior comparison's statement that these trees had no local provenance manifest is stale. This improves local source provenance; no online lookup was used.

Precision correction: describe 1.13.2 as having no **explicit creative exclusion** rather than “no selector.” The overload applies an implicit not-spectator filter, which still includes ordinary creative players.

## Bounds

Only the exact local decompiled sources and local provenance were examined for 1.13.2, 1.14, 1.14.1, 1.14.2, 1.14.3, and 1.14.4. Scope is the dragon's direct player velocity response and the bounded packet producer/consumer check. Dragon motion, damage calculation, and actual owning-player packet delivery remain outside the verdict. No product/Wiki/Git/backlog material, code changes, tests, runtime launches, generation, or delegation were used.

**Terminal:** COMPLETED — movement claim checks above; local provenance is available for 1.14.1–1.14.3, while owning-player delivery remains unclaimed.