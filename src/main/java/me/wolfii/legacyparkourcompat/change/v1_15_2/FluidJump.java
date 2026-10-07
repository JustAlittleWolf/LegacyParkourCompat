package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.FluidJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.LavaGroundJumpBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class FluidJump implements FluidJumpBehavior, LavaGroundJumpBehavior {
    @Override
    public boolean mayJumpInFluid(Player player, boolean vanillaAffectedByFluids) {
        return true;
    }

    @Override
    public boolean isShallowLavaForGroundJump(Player player, boolean vanillaShallow) {
        return false;
    }
}
