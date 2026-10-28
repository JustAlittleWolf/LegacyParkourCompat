# 1.20.1–1.20.2 local-player passenger-placement operation closure

## Bound result

**The changed operation directly writes the riding Player's position and bounding box on the logical client.** In 1.20.1 the base writer uses vehicle Y plus `getPassengersRidingOffset()` plus the Player's `getMyRidingOffset()` and leaves vehicle X/Z unchanged. In 1.20.2 it obtains a vehicle-specific `getPassengerRidingPosition(passenger)` and adds the Player's vehicle-aware offset. The Player offset changes from `-0.35` (double) to `-0.6F` (float). The new attachment API also moves many individual seat formulas out of old `positionRider` / `getPassengersRidingOffset` methods.

This report closes the source-operation inventory requested for a Player passenger in the two available Mojmap trees. It does not review product code, establish any other movement-area coverage, assert intermediate patch boundaries, or establish runtime parity. Vehicle motion is only an upstream source of vehicle pose and is excluded. Logical-server execution and remote-player rendering/interpolation are kept separate.

## Exact source identity and integrity

Read-only mapping trees:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.20.1\mojmap`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.20.2\mojmap`

Each release has both `mojmap.provenance.json` and `mojmap.ready.json`; **1.20.1 provenance is present, not missing**. Both name exact release IDs and Mojmap, and identify one run (`1.20.1,1.20.2,1.20.4,1.20.6`, Gradle 9.7.1, Java 25.0.3+9-LTS, TinyRemapper 0.14.1, Mapping IO 0.9.1, Vineflower 1.12.0). Each ready marker's version metadata ID matches its version ID. The referenced campaign staging, artifacts and ready directories are no longer present, so the original input archives and full Gradle log could not be reopened; the claims below are bound to the present source manifests and surviving provenance/ready metadata, not independently re-established from those absent inputs.

| Release | provenance | ready marker | source manifest SHA-256 | source count | client SHA-256 in ready marker |
| --- | --- | --- | --- | ---: | --- |
| 1.20.1 Mojmap | present; `versionId=1.20.1` | present; `f8dc81374dc42521ee9d17a04ea0dc77f1c5384548cfc01c9621b3d84b670fed` | `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465` | 4,786 | `56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f` |
| 1.20.2 Mojmap | present; `versionId=1.20.2` | present; `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f` | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` | 4,904 | `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda` |

The tree source files used below were checked against the named `mojmap.sources.sha256` manifest in each release parent. The following core hashes were recomputed from disk and match those manifest entries:

| Relative Java path | 1.20.1 SHA-256 | 1.20.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/Entity.java` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` |
| `net/minecraft/world/entity/LivingEntity.java` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` |
| `net/minecraft/world/entity/player/Player.java` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` |
| `net/minecraft/client/multiplayer/ClientLevel.java` | `8d7434e668bbb868f3d25d638abab4cd612ac89f1b54ae701fa81f212694fbc5` | `6204df69a17b375e46f4405fe67a106def336c819e589ec0794c04920a31c5d7` |
| `net/minecraft/client/player/LocalPlayer.java` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |

Other vehicle files and all 4,786/4,904 source paths were searched in their entirety. The exact source manifests provide the hash binding for the complete declaration inventories below.

## Reachability, order, and state writes

1. `Entity.startRiding(target, force)` first rejects the current vehicle, a target that cannot accept passengers, and a passenger cycle. Without `force`, it additionally requires `this.canRide(target)` (base: shift not down and boarding cooldown elapsed) and `target.canAddPassenger(this)` (base: no existing passenger). `force` skips those latter two gates, but not target `couldAcceptPassenger` or cycle checks. On success it removes the previous vehicle relation if any, sets the rider pose to standing, assigns `vehicle`, then calls the target's `addPassenger`. The 1.20.1 and 1.20.2 sequence is the same. `LocalPlayer.startRiding` has the same minecart sound side effect in both versions; it does not alter the relation or coordinates itself.
2. Client passenger dispatch is also unchanged: `ClientLevel.tickEntities` ticks each nonremoved root vehicle, and `tickNonPassenger` then visits its passengers. `tickPassenger` prunes removed/mismatched passengers; otherwise it ticks a passenger if it is a `Player` (which includes the local player) or is in the ticking-entity set. It snapshots old position/rotation, increments `tickCount`, invokes `rideTick`, then recurses into the passenger's own children.
3. `Player.rideTick` stops a passenger only when `!level().isClientSide && wantsToStopRiding()`. On the logical client it takes the common path through `LivingEntity.rideTick` and `Entity.rideTick`. `Entity.rideTick` writes rider delta movement to zero, calls `tick()`, then if still a passenger calls `vehicle.positionRider(this)`. `LocalPlayer.rideTick` calls `super.rideTick()` first, then forwards directional inputs to a controlled Boat and updates `handsBusy`. These gates and this order match.
4. The vehicle's tick therefore precedes the rider's tick/placement. The rider's `tick()` happens before the attachment write. The public `positionRider` uses `Entity::setPos` on this path. `Entity.setPos(x,y,z)` calls `setPosRaw` then rebuilds `boundingBox` using current dimensions. Thus the new formula overwrites player position and collision-box position each passenger tick. The earlier old-position snapshot remains the pre-rider-tick value. Player ride statistics are computed after `super.rideTick()` from the final placed coordinates.
5. The vehicle relation is removed by `removeVehicle`/`stopRiding`: clear `rider.vehicle`, call old vehicle `removePassenger`, replace passenger list, set `boardingCooldown=60`, emit dismount event. `Player.removeVehicle` then resets the player's cooldown to zero. These detach writes are unchanged and do not call `setPos`. On a subsequent client tick, a detached local player is no longer dispatched through `rideTick` and resumes the regular root-player `tick` path. A dismount's chosen destination/authoritative correction is a separate operation and is not attributed to the seat-placement formula here.

## Generic writer and player offset

- **1.20.1 `Entity.java:1817–1844`.** `rideTick` zeros velocity, ticks, and dispatches to the vehicle if `isPassenger`. `positionRider(Entity)` delegates to the protected method with `Entity::setPos`; the protected writer checks `hasPassenger(passenger)` and writes `(vehicle.x, vehicle.y + vehicle.getPassengersRidingOffset() + passenger.getMyRidingOffset(), vehicle.z)`. Base vehicle offset is `dimensions.height * 0.75`; base passenger offset is zero.
- **1.20.2 `Entity.java:1827–1863`.** Same `rideTick` order. Public `positionRider` checks `hasPassenger` then delegates. The protected writer obtains `getPassengerRidingPosition` and adds `passenger.getMyRidingOffset(vehicle)`. Base `Entity.getPassengerRidingPosition` obtains attachment `(0, entityDimensions.height, 0)`, rotates the local vector by negative vehicle yaw in radians, and adds vehicle position. Base attachment uses vehicle's current `dimensions`; base rider offset is `0.0F`.
- **LivingEntity vehicles, 1.20.2 `LivingEntity.java:3427–3436`.** Override `getPassengerRidingPosition` to use dimensions for current pose and the vehicle's scale, rotate the attachment by negative `yBodyRot`, and add position. Override `getMyRidingOffset(vehicle)` to multiply `ridingOffset(vehicle)` by scale.
- **Player offset.** 1.20.1 `Player.java:1022–1024` returns double `-0.35`. 1.20.2 `Player.java:1026–1028` returns float `-0.6F` from `ridingOffset(Entity)`. In both paths a normal-scale local player is a `LivingEntity`, so the latter is multiplied by `getScale()` (normally `1.0F`). The numeric type, value, and formula differ. This is the only player-specific riding-offset override: 1.20.2's other `ridingOffset` overrides are methods on non-Player entities and do not dispatch for a Player passenger.

## Full declaration inventory for Player-passenger placement

The inventory below comes from a declaration search over both full Mojmap source trees. In 1.20.1 there are no `getPassengerAttachmentPoint` declarations; the shared writer's vehicle height is `getPassengersRidingOffset`. The 16 old declarations are base `Entity` plus 15 entity/vehicle overrides. In 1.20.2 there are 45 `getPassengerAttachmentPoint` declarations: the base `Entity` method plus the 44 listed entity overrides below. `LivingEntity` separately overrides `getPassengerRidingPosition` to apply pose dimensions, scale and body yaw. Each `getPassengerAttachmentPoint` override is reached by the common writer for an attached Player unless its own gate/index logic rejects that passenger. It supplies an attachment vector, not vehicle physics. The list is deliberately not limited to familiar vehicles; special AI mounting and forced relationships can make otherwise unusual entity passenger paths reachable. User-driven vehicle boarding exclusions do not negate a server/entity passenger relationship already present in `ClientLevel`.

### `positionRider` overrides

| Version | Relative path and line | Player-path behavior |
| --- | --- | --- |
| 1.20.1 | `world/entity/Entity.java:1825,1829` | Base writer and has-passenger guard. |
| 1.20.1 | `world/entity/animal/Chicken.java:170–179` | Calls base then overwrites position with body-yaw side offset `0.1F` and `getY(0.5) + player offset`; copies body yaw only if passenger is `LivingEntity`. |
| 1.20.1 | `world/entity/animal/camel/Camel.java:463–483` | Requires passenger index >= 0; index controls front/rear seat and animation Y, second-seat side offset; adds passenger offset and clamps rider rotation. |
| 1.20.1 | `world/entity/animal/horse/AbstractHorse.java:979–990` | Calls base; when `standAnimO>0`, writes stand-animation X/Y/Z adjustment and copies body yaw for living rider. |
| 1.20.1 | `world/entity/animal/horse/Llama.java:161–173` | Checks `hasPassenger`; body-yaw rotated side offset `0.3F`, vehicle offset and passenger offset. |
| 1.20.1 | `world/entity/vehicle/Boat.java:667–695` | Checks `hasPassenger`; seats side-by-side when multiple; rotated side offset; adjusts rider yaw/head yaw/body yaw and clamps yaw/head. |
| 1.20.2 | `world/entity/Entity.java:1835,1841` | Base writer and outer has-passenger guard; the protected writer has no second guard. |
| 1.20.2 | `world/entity/animal/Chicken.java:171–176` | Calls base, copies body yaw if rider is living. Coordinate adjustment moved to its attachment method. |
| 1.20.2 | `world/entity/animal/horse/AbstractHorse.java:980–985` | Calls base, copies body yaw if rider is living. Stand animation moved to attachment method. |
| 1.20.2 | `world/entity/vehicle/Boat.java:700–710` | Calls base, adjusts yaw/head yaw/body yaw and clamps, with the same animal-only rider branch. |

The Camel and Llama overrides disappear as `positionRider` overrides in 1.20.2; their seat placement is now their attachment point. The changed base uses the public has-passenger gate before dynamically dispatching to subclass `positionRider`; `teleportPassengers` separately calls the protected virtual method with `Entity::moveTo` for listed passengers.

### Old `getPassengersRidingOffset` overrides (1.20.1 only)

`Entity.java:1843–1845` is the base (`dimensions.height * 0.75`). All 15 direct overrides found in 1.20.1, with their exact returned formula, are listed below. Vehicle custom `positionRider` methods can bypass or add to these values; those are listed in the preceding table.

| Relative path and line | Exact 1.20.1 result / corresponding 1.20.2 operation |
| --- | --- |
| `world/entity/animal/camel/Camel.java:524–526` | `height(current sitting-or-standing pose) - (baby ? 0.35F : 0.6F)`; new Camel point at `Camel.java:462–478` uses passenger index, removed state, body-anchor animation, dimensions/scale and side-seat logic. |
| `world/entity/animal/horse/AbstractChestedHorse.java:60–62` | `super.getPassengersRidingOffset() - 0.25`; family now uses `AbstractHorse` attachment at `AbstractHorse.java:1143–1149`. |
| `world/entity/animal/horse/Llama.java:171–173` | `getBbHeight()*0.6`; new Llama point at `Llama.java:487–489`. |
| `world/entity/animal/horse/SkeletonHorse.java:103–105` | `super.getPassengersRidingOffset() - 0.1875`; new point at `SkeletonHorse.java:106–108`. |
| `world/entity/animal/sniffer/Sniffer.java:376–378` | `1.8`; new point at `Sniffer.java:374–376`. |
| `world/entity/boss/enderdragon/EnderDragon.java:926–928` | `body.getBbHeight()`; new point at `EnderDragon.java:921–923`. |
| `world/entity/monster/hoglin/Hoglin.java:223–225` | `getBbHeight() - (baby ? 0.2 : 0.15)`; new point at `Hoglin.java:226–228`. |
| `world/entity/monster/Phantom.java:238–240` | `getEyeHeight()`; new point at `Phantom.java:238–240`. |
| `world/entity/monster/piglin/Piglin.java:282–284` | `getBbHeight()*0.92`; new family point at `AbstractPiglin.java:60–62`. |
| `world/entity/monster/Ravager.java:124–126` | `2.1`; new point at `Ravager.java:126–128`. |
| `world/entity/monster/Spider.java:67–69` | `getBbHeight()*0.5F`; new point at `Spider.java:69–71`. |
| `world/entity/monster/Strider.java:193–195` | `getBbHeight() - 0.19 + 0.12F*cos(walkPosition*1.5F)*2*min(0.25F,walkSpeed)`; new point at `Strider.java:192–194` uses the new height input plus a scale-multiplied bob, without the old `-0.19` term. |
| `world/entity/monster/Zoglin.java:198–200` | `getBbHeight() - (baby ? 0.2 : 0.15)`; new point at `Zoglin.java:200–202`. |
| `world/entity/vehicle/AbstractMinecart.java:151–153` | `0.0`; new point at `AbstractMinecart.java:154–157` is `0.1875F` for Player and zero only for Villager/WanderingTrader. |
| `world/entity/vehicle/Boat.java:151–153` | `bamboo ? 0.25 : -0.1`; new point at `Boat.java:153–169` uses side seat and supplied dimensions (`height*0.8888889F` bamboo, otherwise `height/3`). |

There is no direct `AbstractHorse.getPassengersRidingOffset` declaration in 1.20.1; the relevant shared old override is `AbstractChestedHorse`. The other older rider-position overrides sometimes directly re-compute seat Y instead of relying on this API (Boat, Chicken, Camel, AbstractHorse and Llama; all separately listed above). The complete old API inventory is this table's 15 overrides plus base `Entity`.
### New `getPassengerAttachmentPoint` overrides (1.20.2)

The complete-source declaration scan found the 44 overrides below. Each accepts an `Entity` passenger and supplies its local attachment vector; the base writer then adds vehicle position and rotates it by vehicle yaw, or by body yaw for a LivingEntity vehicle. The separate base `Entity.java:1861–1863` point is `(0, dimensions.height, 0)`. No override in this table rejects a Player passenger by type. Passenger-dependent branches are stated explicitly.

| Relative path and line | Exact attachment formula (local X, Y, Z) |
| --- | --- |
| `world/entity/animal/Cat.java:457–459` | `(0, height - 0.1875F*scale, 0)` |
| `world/entity/animal/camel/Camel.java:462–478` | `(0, anchorYOffset, side*scale)`: anchor Y uses passenger index, removed state, current dimensions/scale and body-anchor animation; rear seat side is `-0.7F`, otherwise `0.5F`, with `+0.2F` when passenger is an Animal. |
| `world/entity/animal/Chicken.java:179–181` | `(0, height, -0.1F*scale)` |
| `world/entity/animal/Cow.java:106–108` | `(0, height - 0.03125F*scale, 0)` |
| `world/entity/animal/Fox.java:683–685` | `(0, height - 0.0625F*scale, -0.25F*scale)` |
| `world/entity/animal/frog/Frog.java:354–356` | `(0, height - 0.125F*scale, -0.25F*scale)` |
| `world/entity/animal/goat/Goat.java:365–367` | `(0, height - 0.1875F*scale, 0)` |
| `world/entity/animal/horse/AbstractHorse.java:1143–1149` | `(0, passengerHeight + 0.15F*standAnimO*scale, -0.7F*standAnimO*scale)`; `passengerHeight = poseHeight + (baby ? 0.125F : -0.15625F)*scale`. |
| `world/entity/animal/horse/Llama.java:487–489` | `(0, height - (baby ? 0.8125F : 0.5F)*scale, -0.3F*scale)` |
| `world/entity/animal/horse/SkeletonHorse.java:106–108` | `(0, height - (baby ? 0.03125F : 0.28125F)*scale, 0)` |
| `world/entity/animal/Ocelot.java:282–284` | `(0, height - 0.0625F*scale, 0)` |
| `world/entity/animal/Panda.java:688–690` | `(0, height - (baby ? 0.4375F : 0)*scale, 0)` |
| `world/entity/animal/Parrot.java:452–454` | `(0, height - 0.4375F*scale, 0)` |
| `world/entity/animal/Pig.java:275–277` | `(0, height - 0.03125F*scale, 0)` |
| `world/entity/animal/Sheep.java:385–387` | `(0, height - 0.0625F*scale, 0)` |
| `world/entity/animal/sniffer/Sniffer.java:374–376` | `(0, height + 0.34375F*scale, 0)` |
| `world/entity/animal/Turtle.java:342–344` | `(0, height + (baby ? 0 : 0.15625F)*scale, -0.25F*scale)` |
| `world/entity/animal/Wolf.java:524–526` | `(0, height - 0.03125F*scale, -0.0625F*scale)` |
| `world/entity/boss/enderdragon/EnderDragon.java:921–923` | `(0, body.getBbHeight(), 0)` |
| `world/entity/monster/AbstractIllager.java:44–46` | `(0, height + 0.05F*scale, 0)` |
| `world/entity/monster/CaveSpider.java:67–69` | `(0, height, 0)` |
| `world/entity/monster/ElderGuardian.java:83–85` | `(0, height + 0.353125F*scale, 0)` |
| `world/entity/monster/EnderMan.java:238–240` | `(0, height - 0.09375F*scale, 0)` |
| `world/entity/monster/Endermite.java:153–155` | `(0, height - 0.0625F*scale, 0)` |
| `world/entity/monster/Ghast.java:136–138` | `(0, height + 0.0625F*scale, 0)` |
| `world/entity/monster/Guardian.java:376–378` | `(0, height + 0.125F*scale, 0)` |
| `world/entity/monster/hoglin/Hoglin.java:226–228` | `(0, height + 0.09375F*scale, 0)` |
| `world/entity/monster/Husk.java:84–86` | `(0, height + 0.125F*scale, 0)` |
| `world/entity/monster/Phantom.java:238–240` | `(0, height*0.675F, 0)` |
| `world/entity/monster/piglin/AbstractPiglin.java:60–62` | `(0, height + 0.0625F*scale, 0)` |
| `world/entity/monster/Ravager.java:126–128` | `(0, height + 0.0625F*scale, -0.0625F*scale)` |
| `world/entity/monster/Silverfish.java:136–138` | `(0, height - 0.0625F*scale, 0)` |
| `world/entity/monster/Slime.java:257–259` | `(0, height - 0.015625F*size*scale, 0)` |
| `world/entity/monster/Spider.java:69–71` | `(0, height*0.85F, 0)` |
| `world/entity/monster/Strider.java:192–194` | `(0, height + bob*scale, 0)`, `bob=0.12F*cos(walkPosition*1.5F)*2*min(0.25F,walkSpeed)` |
| `world/entity/monster/Vex.java:232–234` | `(0, height - 0.0625F*scale, 0)` |
| `world/entity/monster/warden/Warden.java:570–572` | `(0, height + 0.25F*scale, 0)` |
| `world/entity/monster/Witch.java:279–281` | `(0, height + 0.3125F*scale, 0)` |
| `world/entity/monster/Zoglin.java:200–202` | `(0, height + 0.09375F*scale, 0)` |
| `world/entity/monster/Zombie.java:534–536` | `(0, height + 0.0625F*scale, 0)` |
| `world/entity/monster/ZombieVillager.java:350–352` | `(0, height + 0.175F*scale, 0)` |
| `world/entity/monster/ZombifiedPiglin.java:257–259` | `(0, height + 0.05F*scale, 0)` |
| `world/entity/vehicle/AbstractMinecart.java:154–157` | `(0, passenger is Villager/WanderingTrader ? 0 : 0.1875F, 0)`; Player takes `0.1875F`. |
| `world/entity/vehicle/Boat.java:153–169` | `(0, bamboo ? height*0.8888889F : height/3.0F, side)`. Side is single-passenger offset, or `0.2F` first / `-0.6F` second; `+0.2F` only for an Animal passenger. |

The non-Player `ridingOffset` overrides found elsewhere in 1.20.2 (Allay, AbstractIllager, AbstractSkeleton, CaveSpider, Endermite, Ghast, Giant, Phantom, Piglin, Spider, Vex, WitherSkeleton and Zombie) describe those entities as passengers on some other vehicle. They do not dispatch for a Player passenger. Only `Player.ridingOffset(Entity)` contributes the Player-specific offset through `LivingEntity.getMyRidingOffset(Entity)`.
## Rotation and clamp side effects

- Boat placement continues to add `deltaRotation` to passenger Y rotation and head rotation, then clamps body/yaw/head to boat yaw. `clampRotation` wraps relative yaw, clamps it to `[-105,105]`, adjusts `yRotO` by the clamp delta, and sets body/head yaw. The full passenger coordinate formula moved to its attachment point; this Boat-specific rotation work remains in `positionRider` in both releases.
- AbstractHorse still copies vehicle body yaw to any LivingEntity passenger after base placement; Camel in 1.20.1 specifically called `clampRotation`, but that custom Camel override is removed in 1.20.2, so do not infer the same Camel clamp from its new attachment formula. The new horse-family `positionRider` still copies body yaw.
- Chicken copies body yaw for living passengers in both releases; old X/Y/Z placement was custom and new placement is base writer + attachment vector.
- Llama has no rider yaw side effect in either path. Other 1.20.2 attachment-only overrides feed base positional writing without an additional `positionRider` rotation adjustment unless inherited from a class noted above.

## Scope separation and remaining evidence limit

- **Confirmed:** client-side player passenger placement runs after vehicle tick and player tick; attachment output reaches `Entity.setPos` and its bounding-box rebuild. The position formula, type/offset API and many seat points differ between the named releases.
- **Remote players:** client passenger dispatch tests `instanceof Player`, so it includes remote Player passengers as well as LocalPlayer. This report only claims local-player compatibility relevance; it makes no remote interpolation/rendering claim.
- **Server-only behavior:** server passenger management, server-side `Player.rideTick` shift-to-dismount branch, server dismount destination selection and authoritative physics are distinct from the client rider-placement call. Historical movement should not be inferred for server execution.
- **Vehicle physics:** excluded. It only supplies the current mount pose and its animation/rotation state to the placement formula.
- **Unclosed source gap:** no algorithmic operation gap remains in the Java declaration inventory. The original decompile staging/cache/client artifacts and full success log referenced by provenance are absent, so original artifact provenance cannot be independently reopened. Resource/datapack assets are not part of this Java-only seat-operation assignment. There is no missing 1.20.1 provenance file.




