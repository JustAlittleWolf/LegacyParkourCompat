package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.MovementChunkLookupBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

@MovementChange(emulates = ParkourVersion.V1_8)
public final class TruncatedMovementChunkLookup implements MovementChunkLookupBehavior {
    @Override
    public boolean hasChunkAt(LivingEntity entity, Level level, BlockPos vanillaPosition, VanillaFn<Boolean> vanilla) {
        if (!level.isClientSide()) {
            return vanilla.get();
        }
        return level.hasChunkAt(new BlockPos((int) entity.getX(), 0, (int) entity.getZ()));
    }
}
