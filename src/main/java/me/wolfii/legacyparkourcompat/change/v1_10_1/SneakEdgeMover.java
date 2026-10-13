package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeMoverHook;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class SneakEdgeMover implements SneakEdgeMoverHook {
    @Override
    public MoverType edgeMoverType(Player player, MoverType vanilla) {
        return vanilla == MoverType.PISTON ? MoverType.SELF : vanilla;
    }
}
