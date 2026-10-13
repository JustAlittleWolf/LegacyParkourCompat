# F-05: underwater passenger dismount gate depends on entity-type tag

- **1.19.3**, mojmap: `Cow.java`; lines 325–379.
- **1.19.4**, mojmap: `LivingEntity#baseTick()V`; lines 320–374.

## Difference

For a player already riding an untagged LivingEntity such as Cow while underwater, 1.19.3's inherited false predicate is negated by the server gate, so 1.19.3 detaches the player.

With 1.19.4's built-in tag, Cow is absent and the positive predicate is false, so that gate leaves the player mounted.

The outcome in 1.19.4 depends on the effective server tag set.

Skeleton Horse was checked as a control: 1.19.3 explicitly returns true from its `rideableUnderWater` override, while 1.19.4's built-in tag omits Skeleton Horse.

Both versions therefore leave that passenger mounted under this gate; it is not the changed case.

## Movement meaning

In 1.19.3, `LivingEntity#dismountVehicle()` selects a vehicle-provided passenger dismount position and dispatches through `ServerPlayer#dismountTo`; the player removes its vehicle and the connection sends the dismount position/rotation. `ServerGamePacketListenerImpl#dismount` teleports with the dismount flag.

The client packet handler removes the local vehicle when it receives that flag and applies the supplied coordinates.

Source hashes: 1.19.3 `ServerPlayer.java` , `ServerGamePacketListenerImpl.java` , and `ClientPacketListener.java` .

In 1.19.4, `ServerPlayer#dismountTo(DDD)V`, lines 937–942, removes the vehicle and writes the selected position. `ServerEntity#sendChanges()`, lines 80–89, sends passenger-list changes and, for a removed player passenger, a position teleport.

Source hashes: 1.19.4 `ServerPlayer.java`  and `ServerEntity.java` .

1.19.3 client-only compatibility hook can respond to the server's resulting passenger/position update, but it cannot change whether the authoritative 1.19.4 server executes `stopRiding()` or which dismount position it selects.

Both decisions are made on the server using server state and its effective tag data.

This finding is about direct player passenger state and the resulting player dismount position.

No network-arrival ordering relative to a local tick is inferred from source.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.3--1.19.4/findings/F-05-underwater-passenger-dismount.md).
