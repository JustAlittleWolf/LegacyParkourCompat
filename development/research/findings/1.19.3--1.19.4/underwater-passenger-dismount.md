# underwater passenger dismount gate depends on entity-type tag

1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, dismountsUnderwater 1981-1983.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, baseTick 325-379; rideableUnderWater 555-557.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, baseTick 320-374.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/server/commands/RideCommand.java`, mount 62-86.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/tags/TagLoader.java`, load 44-65.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/server/packs/resources/MultiPackResourceManager.java`, listResourceStacks 97-106.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, handleUpdateTags 1505-1507.

Difference: For a player already riding an untagged LivingEntity such as Cow while underwater, 1.19.3's inherited false predicate is negated by the server gate, so 1.19.3 detaches the player. With 1.19.4's built-in tag, Cow is absent and the positive predicate is false, so that gate leaves the player mounted. The outcome in 1.19.4 depends on the effective server tag set. Skeleton Horse was checked as a control: 1.19.3 explicitly returns true from its `rideableUnderWater` override, while 1.19.4's built-in tag omits Skeleton Horse. Both versions therefore leave that passenger mounted under this gate; it is not the changed case.

Movement meaning: In 1.19.3, `LivingEntity#dismountVehicle()` selects a vehicle-provided passenger dismount position and dispatches through `ServerPlayer#dismountTo`; the player removes its vehicle and the connection sends the dismount position/rotation. `ServerGamePacketListenerImpl#dismount` teleports with the dismount flag. The client packet handler removes the local vehicle when it receives that flag and applies the supplied coordinates.
