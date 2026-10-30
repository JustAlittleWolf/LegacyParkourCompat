# Correction: collision shapes are not clipped to the query bounds

This immutable correction supersedes the geometric conclusion in `report-supplement.md` that a returned entity shape could not intersect the +0.51F side segment at 0.2F player height. The query predicates test intersection; they do not clip shapes.

## Exact returned shapes

1.13.2 `EntityView.getEntityCollisions` lines 21–32 first gets `e.getCollisionShape()` / the requesting entity's `getCollisionAgainstShape(e)`, filters with `s.intersects(box)`, then maps the whole result through `VoxelShapes.block(s)`. `VoxelShapes.block(Box)` lines 45–70 preserves the supplied bounds through an `ArrayVoxelShape` when the coordinates are not representable on its small bit grid; it does not intersect the returned shape with `box`. Base `Entity.getCollisionShape` and `getCollisionAgainstShape` return null; the relevant concrete providers are boats (collision shape = whole `getShape()`) and living shulkers (whole `getShape()` while alive). Thus a tall boat or living shulker box can intersect the 0.2F scan and extend up to the +0.51F segment.

1.14.4 `EntityGetter.getEntityCollisions` lines 53–66 similarly maps each full `getCollideBox()` or `getCollideAgainstBox(entity)` through `Shapes.create` after testing `aABB2::intersects`. `Shapes.create(AABB)` preserves the supplied bounds; it does not clip to `aABB2`. Base `Entity` returns null for both; boats return full bounding boxes, and shulkers return their full bounding box while alive.

Consequently the earlier vertical-disjointness argument was invalid and is withdrawn. A tall emitted shape may overlap both the scan and the side segment. The semantic distinction still remains: the older provider queries entities in `scan.expand(0.25)` then filters whole collision boxes against the original scan; the newer provider queries entities in `scan.inflate(1.0E-7)` then filters full boxes against that inflated scan. The candidate boxes are not clipped by either provider.

## Correctly closed reachability gate for the requested poses

The source-level endpoint query difference does not reach timer=1 through the requested local-player sleeping/dying pose paths because the required movement-input gate is false before auto-jump's headspace and collision scan:

- At both endpoints, Minecraft's tick checks `player.isSleeping()` / health <= 0 and opens the ordinary sleep/death screen before ticking the level's entities (`1.13.2 Minecraft.tick` lines 1094–1101; `1.14.4 Minecraft.tick` lines 1260–1267). Opening any non-null screen releases all bindings (`1.13.2 Minecraft.openScreen` lines 643–663 calls `KeyBinding.releaseAll`; `1.14.4 Minecraft.setScreen` lines 729–750 calls `KeyMapping.releaseAll`). If another screen is already open, it likewise already released bindings.
- Before `move` calls auto-jump, the local input tick recomputes movement directly from those key bindings (`1.13.2 LocalClientPlayerEntity` lines 698–703 and `KeyboardInput.tick` lines 13–42; `1.14.4 LocalPlayer` lines 624–630 and `KeyboardInput.tick` lines 13–27). With released movement bindings, `getMovement()` / `getMoveVector()` is zero. Auto-jump's early input gate (`1.13.2 LocalClientPlayerEntity.autoJump` lines 882–886; `1.14.4 LocalPlayer.updateAutoJump` lines 856–860) returns before testing headspace or collision shapes.
- A previously armed timer does not yield a jump while these poses persist: timer expiry sets the local input jumping flag before superclass AI; dying is immobile at health <= 0 and sleeping is immobile in Player, and `LivingEntity.aiStep` clears `jumping` and horizontal impulses when immobile (`1.14.4 Player.isImmobile` lines 980–982 and `LivingEntity.aiStep` lines 2187–2195). The ordinary client screen also keeps movement bindings released on later ticks. In either endpoint, the client input tick's next-jump request is not a resulting movement jump in those states.

This closes the assigned local sleeping/dying 0.2F reachability check negatively for the entity-query-induced timer/jump consequence. It does not assert that such an entity shape is geometrically impossible, nor does it close any other tick inventory, block-only behavior, or intermediate release boundary.

## Hashes for newly examined source files

- 1.13.2 `net/minecraft/entity/Entity.java`: `1D6EC8B80F74635401745C2C027BF36555C85348CA5693764F2668363B17D269`.
- 1.13.2 `net/minecraft/entity/vehicle/BoatEntity.java`: `3D9C248C8CB5846AC157B7973E4C6F448FE12BDC63C8D33931DACE98682C0F66`.
- 1.13.2 `net/minecraft/entity/living/mob/monster/ShulkerEntity.java`: `35D3F858F7E06C035EE66B74B08420B678665DCDFF9E4CF0619D68570966AFA5`.
- 1.13.2 `net/minecraft/client/entity/living/player/KeyboardInput.java`: `7BE11425906BE051C83E275F359816546E4677B16D212156380E8D2E9258654A`.
- 1.14.4 `net/minecraft/world/entity/Entity.java`: `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7`.
- 1.14.4 `net/minecraft/world/entity/vehicle/Boat.java`: `41C287AFD9430B1576FBCCDC98CA699586FE138C198A0DE54380D7469E400A5D`.
- 1.14.4 `net/minecraft/world/entity/monster/Shulker.java`: `A2627786D594D2DF1876B4A192E0077CD357C4A0295DF091711D95AFBBEC3320`.
- 1.14.4 `net/minecraft/client/player/KeyboardInput.java`: `33DAA0833A95E09E728C1B8F509020DD13B70E38ABA922E2B1E10EC5C9B7739A`.
