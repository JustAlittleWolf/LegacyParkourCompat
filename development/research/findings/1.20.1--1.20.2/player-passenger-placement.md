# Player passenger placement changes to vehicle attachment points

1.20.1, Mojmap: `1.20.1/mojmap/net/minecraft/world/entity/Entity.java`, `positionRider(Entity, MoveFunction)` and `getPassengersRidingOffset`; `player/Player.java`, `getMyRidingOffset`; vehicle overrides in Boat, Camel, Chicken, AbstractHorse and Llama.
1.20.2, Mojmap: corresponding `Entity.positionRider`, `getPassengerRidingPosition`, `LivingEntity.getMyRidingOffset(Entity)` and vehicle `getPassengerAttachmentPoint` overrides.

Difference: the old writer adds vehicle-specific riding height and the Player's double `-0.35` offset. The newer writer uses vehicle attachment positions and the Player's float `-0.6F` offset. The transition also reorganizes carrier-specific seat coordinates, animation operands and rotation effects; replacing only the Player constant does not reconstruct the complete operation. The old ChestBoat one-passenger side offset is `0.15F`, and the BAMBOO boat variant has a distinct riding height.

Movement meaning: this callback directly changes a locally riding player's position and bounding box. Its source operands include carrier pose, passenger order and animation state. Vehicle motion and logical-server simulation are separate operations. Exact 1.20 and 1.20.1 movement-source equality establishes the older profile alias for this operation; compilation does not establish runtime parity.
