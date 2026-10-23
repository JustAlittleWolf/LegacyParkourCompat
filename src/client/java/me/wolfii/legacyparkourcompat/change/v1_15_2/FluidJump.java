package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.FluidJumpHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.LavaGroundJumpHook;
import net.minecraft.world.entity.player.Player;

/**
 * Mojmap 1.15.2 LivingEntity#aiStep gates this jump dispatcher only on
 * {@code jumping}. Mojmap 1.16.5 LivingEntity#aiStep also checks
 * {@code isAffectedByFluids}; Player#isAffectedByFluids returns
 * {@code !abilities.flying}. The existing dispatcher redirect returns true
 * for this 1.15.2 change, preserving the earlier ground-jump dispatch while
 * separate fluid-jump hooks continue to govern liquid jumps.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class FluidJump implements FluidJumpHook, LavaGroundJumpHook {
    @Override
    public boolean mayJumpInFluid(Player player, boolean vanillaAffectedByFluids) {
        return true;
    }

    @Override
    public boolean isShallowLavaForGroundJump(Player player, boolean vanillaShallow) {
        return false;
    }
}
