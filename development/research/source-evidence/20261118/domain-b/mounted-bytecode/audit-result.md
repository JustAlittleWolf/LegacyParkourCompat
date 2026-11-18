# Independent exact-source audit: vehicle collision exclusion

Verdict: ACCEPT, limited to the named 1.8.9 local mounted-player collision query and the fresh remap input below.

## Bound claim and payload

Existing claim: development/research/findings/1.8.9--1.9.4/vehicle-collision-exclusion.md
Claim Git blob: 8c0fa8a4c3bf34a9641fc501b1428736b123c5c4
Claim LF SHA256: 51d28a19c4efc7943c7168e0609f059e3c500d57ca6bbd7c821bc44ec640b86b
Immutable author payload SHA256: d462f8d56113e2a0396c8240c32487b8dd9c6163e426611d21cf9d5d651000d8

This acceptance adds a separately bound remap input. It does not assert byte equivalence with the unavailable prior mapped jar beginning e36a366f.

## Independently verified identities

- Raw 1.8.9 client jar SHA256: 14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298.
- Fresh Feather Gen2 build.2 mapped jar SHA256: e7f172281a7a5bc0b17b667dfd3883c3db0e2673c4f743dc5edfbf1e868c80d2.
- net/minecraft/world/World.class entry SHA256: c56509d9f9e5226c8110a90182033ba8cbfdee87c6e8e55cd97de43f02ce24fa.
- Fresh 1.8.9 World.java SHA256: 3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee.
- Fresh 1.8.9 BoatEntity.java SHA256: 77ef72c28024b754ff76f2c67e14dd61832394bac2bd7b62b29708560e588da2.
- Canonical 1.9 World.java SHA256: d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98.
- Canonical 1.9 Entity.java SHA256: 74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a.

The fresh source digests for Entity.java, LivingEntity.java, PlayerEntity.java and LocalClientPlayerEntity.java also match the author payload.

## 1.8.9 bytecode and candidate result

I inspected only World.class with static javap output for getCollisions(Entity, Box); no class was executed. In the entity loop:

- At offsets 300-306, bytecode loads argument entity.rider and then local 17, the List returned by getEntities; if_acmpeq branches to 402, skipping the loop body.
- At offsets 309-318, it loads argument entity.vehicle and then that same List; if_acmpne enters the callback body at 321, while equality falls through to the skip jump at 402.
- The indexed candidate is first fetched from local 17 at offsets 321-330, after both reference comparisons. That candidate receives getCollisionShape at 333-336 and is passed to getCollisionAgainstShape at 361-377. Each returned shape is independently required to be non-null and intersect the query box before append.

Thus both if_acmp checks compare rider/vehicle references to the result List, not to the indexed candidate. The guard does not exclude the mounted player's boat.

The source confirms the candidate is reachable: World.getCollisions calls getEntities(entity, shape grown by 0.25); WorldChunk.getEntities excludes the query entity and applies candidate-box intersection plus the filter, with no rider/vehicle exclusion. For the named ordinary mounted state, BoatEntity.getCollisionShape returns its current entity box. BoatEntity.updateRiderPositon places the player at boat.y + (-0.3) + player ride height (-0.35), with a 0.4 horizontal offset; their boxes overlap. The player inherits Entity.getCollisionAgainstShape, which returns null. When the mounted player's ordinary move query runs with noClip false, the boat is therefore present as an entity candidate and its current collision box is appended to the 1.8.9 collision-result List.

The named local route is World.tickEntity(vehicle) -> recursively tick the rider -> PlayerEntity.rideTick -> LivingEntity.rideTick -> Entity.rideTick -> virtual LocalClientPlayerEntity.tick -> PlayerEntity.tick -> LivingEntity.tick -> LocalClientPlayerEntity.mobTick -> PlayerEntity.mobTick -> LivingEntity.mobTick. LocalClientPlayerEntity is locally controlled; the mounted PlayerEntity.moveRelative branch delegates to LivingEntity.moveRelative, whose local movement path calls Entity.move. Entity.move calls World.getCollisions in its collision-aware branch. The acceptance is for this reachable shape admission and result-list entry, subject to ordinary non-spectator collision movement and the described candidate state.

## Immediate 1.9 gate

Canonical 1.9 World.getCollisions retrieves the indexed candidate entity2 and gates both shape callbacks on !entity.hasSameVehicle(entity2). Canonical Entity.getVehicle walks through mounts to the root; hasSameVehicle compares those root references. A mounted player and their boat therefore share a root and the candidate is skipped before either callback. This verifies the immediate 1.9 gate; it does not identify an intervening patch cutoff.

## Remaining gates and limits

- The unavailable e36a366f... mapped input remains unavailable. This acceptance is bound to the freshly verified raw input, fresh mapped jar, World.class entry, named route and old result above; it does not transfer the old input identity.
- No exact transition release within 1.8.9-1.9 was audited.
- This audit confirms collision-shape admission only. It does not calculate final position, velocity or trajectory after Entity.move's collision resolution.
- The previously bound 1.9.4 evidence was not re-audited here; the immediate comparison performed here is canonical 1.9.

No product files were edited. No tests, launches, builds, class execution or network access were used.
