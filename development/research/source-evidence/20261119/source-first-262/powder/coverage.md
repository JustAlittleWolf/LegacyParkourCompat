# Powder snow collision selection, 26.1.2 to 26.2

Both exact release sources use published unobfuscated names. The 26.1.2 client SHA-256 is b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0 and the 26.2 client SHA-256 is 40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290 per their artifact manifests. Source manifest SHA-256 values match both ready markers, respectively 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 and a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894.

## Paired coverage

| Operation | 26.1.2 | 26.2 |
| --- | --- | --- |
| Block registration | Blocks.java:6211–6221 | Blocks.java:5249–5259 |
| Shape selection and equipment predicate | PowderSnowBlock.java:42–43, 114–137, 144–150 | PowderSnowBlock.java:42–43, 114–137, 144–150 |
| Full-shape fallback and state dispatch | BlockBehaviour.java:329–335, 684–686 | BlockBehaviour.java:323–329, 690–692 |
| Move and fall update order | Entity.java:731–770, 1466–1485 | Entity.java:738–778, 1553–1572 |
| Entity movement and step-up shape gathering | Entity.java:1077–1105, 1131–1158 | Entity.java:1141–1169, 1195–1200, 1209–1229 |
| Blocks, including positional-support query | CollisionGetter.java:90–106, BlockCollisions.java:77–106 | CollisionGetter.java:90–106, BlockCollisions.java:77–106 |
| Entity context factory, descending/height snapshot, shape dispatch | CollisionContext.java:23–34, EntityCollisionContext.java:38–48, 67–89 | CollisionContext.java:23–34, EntityCollisionContext.java:38–48, 67–89 |
| Player descending and equipment inputs | Entity.java:2638–2640, LocalPlayer.java:678–680, 786, LivingEntity.java:2253–2255, EntityEquipment.java:34 | Entity.java:2705–2707, LocalPlayer.java:678–680, 786, LivingEntity.java:2290–2292, EntityEquipment.java:34 |

## Verdict

No delta in reachable shape selection for the same player inputs. Both versions register the dynamic powder snow shape and query it for regular entity movement and the step-up search. BlockCollisions invokes the context with the block state and translates the selected shape. The entity context snapshots entity Y and descending. LocalPlayer descending is the shift input, independently of the sign of vertical motion.

The selector first gates out placement and contexts without an entity. At stored double fall distance strictly greater than 2.5 it returns the shape with top at the widened float literal 0.9F, even while descending or without boots. Otherwise, the walkability predicate checks the entity-type tag or the living entity's FEET item against Items.LEATHER_BOOTS. For a player in leather boots, a full collision shape is reachable if player Y is strictly greater than block Y + 1.0 - 1.0E-5F and shift is clear. At or below the tolerance, while shift is held, or without walkability, the ordinary player returns the empty movement shape. FallingBlockEntity bypasses the walkability, above and descending predicates after the fall-distance branch, a path outside player movement. Its presence does not change the player verdict. The inside-collision wrapper substitutes a full shape for an empty result in both releases. The 26.2 selector's only whole-file source difference replaces the LivingEntity cast with an equivalent pattern binding.

Ordinary travel collision runs before the move's fall-distance accumulation. Both Entity.checkFallDamage methods subtract the float-cast resolved Y on downward movement while outside water, and reset fall distance after on-ground handling. These inputs and their collision ordering remain equivalent within the traced slice. The raw selector reads live fall distance and equipment through the entity reference.

The added 26.2 position context is used by particle motion (Particle.java:152) and potent sulfur geysers (PotentSulfurBlockEntity.java:191, 210, 231), outside the ordinary entity movement call path. The 26.2 source's Entity overload still reaches CollisionGetter.getBlockCollisions(Entity,...). The collider gathering helper name changed, and both helpers for an Entity retain the same world-border collision gate. This comparison does not cover changes elsewhere in movement or world-border processing.

## Open dependencies

The exact vanilla contents of the powder_snow_walkable_mobs tag were unavailable in the paired decompiled source directories and artifact manifests. EntityTypeTags and the predicate show the same lookup. Resource equality remains unverified. A tag change could affect a bare-foot player's walkability if it introduced or removed the player type. The leather-boots, shift and fall-distance coverage above is source-closed subject to this shared input for the bare-foot branch. Other upstream changes to fall distance, Y, shape solving or equipment synchronization are outside the paired selector assignment.

## Verified source file identities

| Relative path | 26.1.2 SHA-256 | 26.2 SHA-256 |
| --- | --- | --- |
| net/minecraft/world/level/block/PowderSnowBlock.java | 5733056253c3f9c7d843e8e7c29deaee67d235d830c63c0641f3a7b70604970f | 2b693be4a4d3cf2b1895096431c16d7ba3ae51b9cf4320773d384bdd146ba417 |
| net/minecraft/world/phys/shapes/CollisionContext.java | 8702d23bea0f119e0d63ce15ff59088559a319283186372fd69adebeed4dcd69 | 0af02ec998df9e5b093a1b1887255619dd9b9a08604de40b452c11922fbf3bca |
| net/minecraft/world/phys/shapes/EntityCollisionContext.java | 2f0a2bfd72a3d35121e3ef4ab80755f04cefaa7dd933988e2246b39ff5c39291 | 2f0a2bfd72a3d35121e3ef4ab80755f04cefaa7dd933988e2246b39ff5c39291 |
| net/minecraft/world/level/BlockCollisions.java | eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e | eb8a8f6f07b1d6384f17987818f056d41adfd6b2ad120d26774bfa550f4fc51e |
| net/minecraft/world/level/CollisionGetter.java | dddff4897e8d5d01e3ac132d85c474e6a3b9bae928e48b8c907aa9eabdb7fb01 | 8e7d1a54d27e0f187667439539c59b8892f966f1b9c8107eb162c6d43f830f6c |
| net/minecraft/world/level/block/state/BlockBehaviour.java | 9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13 | 9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e |
| net/minecraft/world/level/block/Blocks.java | ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f | f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435 |
| net/minecraft/world/entity/Entity.java | 8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf | 7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5 |
| net/minecraft/world/entity/LivingEntity.java | c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd | 7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a |
| net/minecraft/world/entity/EntityEquipment.java | 4aecd6c65cd9b306f671f732c75c723217f71a5e9e78b7608c815fab01c9fd3d | 4aecd6c65cd9b306f671f732c75c723217f71a5e9e78b7608c815fab01c9fd3d |
| net/minecraft/client/player/LocalPlayer.java | 433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe | 8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6 |
| net/minecraft/tags/EntityTypeTags.java | c8b0514f6e3adc670f5093a705a183f0eaedbc954621e16260648af70722a51c | e46052c098bbd50b6eef4bf5570e3fd266f4b85e5dedd6199a449fe0b772fbcc |
