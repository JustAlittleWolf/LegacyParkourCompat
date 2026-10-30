package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeMoverHook;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;

/**
 * 1.10.2 Ornithe Feather Entity.move(double,double,double) applies sneak-edge
 * backoff to every grounded sneaking player move. MovingBlockEntity.moveEntities
 * and ShulkerEntity.tick both use that untyped call. In 1.11/1.11.1/1.11.2
 * Ornithe Feather, those callers pass PISTON and SHULKER; Entity.move restricts
 * backoff to SELF/PLAYER. The untyped Shulker call and predicate are retained
 * across 1.9/1.9.1/1.9.2/1.9.3/1.9.4/1.10/1.10.1/1.10.2.
 *
 * Only the argument to the edge-backoff operation is translated; the actual
 * mover type used by piston limiting and the rest of movement is unchanged.
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class SneakEdgeMover implements SneakEdgeMoverHook {
    @Override
    public MoverType edgeMoverType(Player player, MoverType vanilla) {
        return vanilla == MoverType.PISTON || vanilla == MoverType.SHULKER ? MoverType.SELF : vanilla;
    }
}
