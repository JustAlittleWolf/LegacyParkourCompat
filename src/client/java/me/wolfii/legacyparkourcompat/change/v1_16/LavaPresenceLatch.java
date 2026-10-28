package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.fluid.LavaPresenceLatchContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaPresenceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaMaterialScanUpperBoundHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import java.util.Optional;

/**
 * Replays the 1.16.1 movement-scoped lava-presence latch for the local player.
 *
 * <p>In Mojmap 1.16.1, {@code Entity.move} resets {@code isTouchingLava} after
 * collision and fall handling, then {@code checkInsideBlocks} scans the inset
 * box when {@code hasChunksAt} admits the range. {@code LiquidBlock.entityInside}
 * sets the flag for lava when the source surface predicate passes. In Mojmap
 * 1.16.2, {@code Entity.baseTick} refreshes cached fluid heights and
 * {@code Entity.isInLava} reads the first-tick-gated numeric lava height; this
 * provider therefore ends at the 1.16.1 boundary.
 */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class LavaPresenceLatch implements LavaPresenceHook {
    private static final double BOX_INSET = 0.001;

    public LavaPresenceLatch() {
    }

    @Override
    public Optional<Boolean> isInLava(Entity entity, Optional<LavaMaterialScanUpperBoundHook> scanUpperBound) {
        if (!(entity instanceof LavaPresenceLatchContext context)) {
            throw new IllegalStateException("Local player is missing lava-presence latch state");
        }
        return Optional.of(context.legacyparkourcompat$isLavaPresenceLatched());
    }

    @Override
    public void afterMove(Entity entity) {
        if (!(entity instanceof LocalPlayer) || !entity.level().isClientSide()) {
            return;
        }

        if (!(entity instanceof LavaPresenceLatchContext context)) {
            throw new IllegalStateException("Local player is missing lava-presence latch state");
        }
        context.legacyparkourcompat$setLavaPresenceLatched(false);

        AABB entityBox = entity.getBoundingBox();
        AABB scanBox = entityBox.deflate(BOX_INSET);
        BlockPos min = BlockPos.containing(scanBox.minX, scanBox.minY, scanBox.minZ);
        BlockPos max = BlockPos.containing(scanBox.maxX, scanBox.maxY, scanBox.maxZ);
        if (!entity.level().hasChunksAt(min, max)) {
            return;
        }

        BlockPos.MutableBlockPos position = new BlockPos.MutableBlockPos();
        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int y = min.getY(); y <= max.getY(); y++) {
                for (int z = min.getZ(); z <= max.getZ(); z++) {
                    position.set(x, y, z);
                    BlockState state = entity.level().getBlockState(position);
                    if (!(state.getBlock() instanceof LiquidBlock)) {
                        continue;
                    }
                    var fluid = state.getFluidState();
                    if (!fluid.is(FluidTags.LAVA)) {
                        continue;
                    }

                    float surfaceY = y + fluid.getHeight(entity.level(), position);
                    if (entityBox.minY < surfaceY || surfaceY > entityBox.maxY) {
                        context.legacyparkourcompat$setLavaPresenceLatched(true);
                        return;
                    }
                }
            }
        }
    }
}
