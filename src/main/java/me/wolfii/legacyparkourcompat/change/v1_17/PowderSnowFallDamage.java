package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockFallBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/** Powder snow inherited ordinary fall damage in 1.17; 1.18 suppressed it. */
@MovementChange(emulates = ParkourVersion.V1_17)
public final class PowderSnowFallDamage implements BlockFallBehavior {
    private static final String BLOCK_ID = BuiltInRegistries.BLOCK.getKey(Blocks.POWDER_SNOW).toString();

    public static void register(MovementChangeRegistry registry) {
        registry.register(new PowderSnowFallDamage());
    }

    @Override
    public String blockId() {
        return BLOCK_ID;
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance, VanillaCall vanilla) {
        if (MovementRuntime.profile(entity).target() != ParkourVersion.V1_17) {
            vanilla.run();
            return;
        }
        entity.causeFallDamage(fallDistance, 1.0F, entity.damageSources().fall());
    }
}
