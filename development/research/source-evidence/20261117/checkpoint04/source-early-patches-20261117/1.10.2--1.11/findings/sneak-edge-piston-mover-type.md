# Sneak edge protection is limited by movement source

1.10.2, Ornithe Feather: `net/minecraft/entity/Entity.java`, `move(double, double, double)` lines 446–523. For a grounded sneaking `PlayerEntity`, the edge-protection reduction runs for every movement call. Its support queries move the prospective player box down by `1.0`.

1.11, Ornithe Feather: `net/minecraft/entity/Entity.java`, `move(MoverType, double, double, double)` lines 455–524. The same grounded/sneaking/player gate is additionally restricted to `MoverType.SELF` or `MoverType.PLAYER`; the downward support query uses `-stepHeight`.

Difference: 1.10.2 has no movement-source argument, so the crouch edge guard also trims a piston displacement. In 1.11 `MovingBlockEntity.moveEntities(float)` invokes the player with `MoverType.PISTON`; the new guard does not run for that source. `LivingEntity.moveRelative` uses `SELF` for local travel, preserving the guard during ordinary input movement. `MoverType.PLAYER` is also used by server network movement handlers, which are not evidence of client-side historical physics.

Movement meaning: for a grounded sneaking local player at a platform edge, a horizontal piston push toward an unsupported side can be reduced to zero by the old generic edge guard; the corresponding 1.11 piston-tagged displacement bypasses that guard and proceeds through normal block clipping. In both releases, `Minecraft.tick` calls `World.tickEntities`; the world entity tick loop ticks loaded, in-border block entities without a logical-side branch, so this response path exists in the local client. The candidate is conditional on the same push reaching the player; the two `MovingBlockEntity` push algorithms also differ and are independently documented as a separate open dependency.

The client `World.tickEntities` path ticks block entities without a logical-side gate; `Minecraft.tick` calls this method for its client world. The exact local-player call path is therefore possible on the client. This is a source candidate only; independent audit and pair coverage are still required.
