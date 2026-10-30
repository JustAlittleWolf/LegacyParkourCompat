# Local sprint eligibility in shallow water

1.12.2, Feather: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick()`, lines 694-745.
1.13.2, Feather: same relative file/member, lines 698-758.

Difference: the 1.12.2 sprint-key start path has no water-state gate, while 1.13.2 requires `!isInWater() || isSubmergedInWater()`. The 1.13.2 double-tap path adds `onGround || isSubmergedInWater()`. Stop logic treats shallow non-submerged water as a stop condition only when `isSwimming()` is false; the swimming branch has its own airborne/sneak/forward-food and still-in-water conditions.

Movement meaning: on otherwise eligible input, sprint key may start sprint in shallow water in 1.12.2 but is rejected there in 1.13.2; 1.13.2 also supports submerged off-ground sprint initiation. Existing swimming sprint can persist through a shallow transition under the special swimming branch. Exact pair provenance and source hashes are bound in `audit-followup.md`.
