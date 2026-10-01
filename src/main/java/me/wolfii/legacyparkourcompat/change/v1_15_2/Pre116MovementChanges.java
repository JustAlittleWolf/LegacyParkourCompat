package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.FluidCurrentMinimumBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FluidJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.LavaCurrentBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.LavaTravelBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintTriggerBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterDescentBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class Pre116MovementChanges implements SprintTriggerBehavior, WaterDescentBehavior,
    FluidJumpBehavior, LavaTravelBehavior, FluidCurrentMinimumBehavior, LavaCurrentBehavior {

    @Override
    public boolean shouldReset(Player player, boolean anotherResetCause) {
        return anotherResetCause;
    }

    @Override
    public boolean mayDescend(Player player, boolean vanillaAffectedByFluids) {
        return true;
    }

    @Override
    public boolean mayJumpInFluid(Player player, boolean vanillaAffectedByFluids) {
        return true;
    }

    @Override
    public boolean isShallowLavaForGroundJump(Player player, boolean vanillaShallow) {
        return false;
    }

    @Override
    public boolean isShallowForTravel(Player player, boolean vanillaShallow) {
        return false;
    }

    @Override
    public boolean applyMinimum(Entity entity) {
        return false;
    }

    @Override
    public boolean allowLavaCurrent(Entity entity, boolean vanilla) {
        return false;
    }
}
