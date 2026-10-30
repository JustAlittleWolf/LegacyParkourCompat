package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeMoverHook;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;

/**
 * 1.8.9 Ornithe Feather Entity.move has the untyped piston sneak-edge path.
 * ShulkerEntity is absent there and present in 1.9 Ornithe Feather; its modern
 * response stays native for these earlier profiles. Retain the existing piston
 * argument policy while fencing the later Shulker policy independently.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class PreShulkerSneakEdgeMover implements SneakEdgeMoverHook {
    @Override
    public MoverType edgeMoverType(Player player, MoverType vanilla) {
        return vanilla == MoverType.PISTON ? MoverType.SELF : vanilla;
    }
}
