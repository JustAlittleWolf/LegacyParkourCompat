package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.OnPositionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.PowderSnowFrostBlockSampleHook;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Minecraft 1.19.4, Mojmap, Entity#getOnPos(float): floor x, y minus the
 * caller's float offset, then z; only if that position is air, select the block
 * below when it is a fence, wall or fence gate, in that evaluation order.
 * Minecraft 1.20, Mojmap, Entity#getOnPos(float) instead consults
 * mainSupportingBlockPos, populated by Entity#checkSupportingBlock, and removes
 * the air/fence/wall/gate fallback when no tracked support is present.
 * Minecraft 26.2, unobfuscated, Entity#getOnPos(float) retains this newer policy.
 * The public getOnPos()/getOnPosLegacy() keep their native offset arguments.
 */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class OnPosition implements OnPositionHook, PowderSnowFrostBlockSampleHook {
    @Override
    public BlockPos onPosition(Entity entity, float offset, VanillaFn<BlockPos> vanilla) {
        return legacyPosition(entity, offset);
    }

    @Override
    public BlockState sample(Player player) {
        // 1.19.4 Mojmap LivingEntity.tryAddFrost uses the floor-based Entity.getBlockStateOnLegacy;
        // 1.20 Mojmap Entity.getBlockStateOnLegacy uses the tracked support position when present.
        // Resolve this sample independently from the general on-position hook's earlier native fence.
        return player.level().getBlockState(legacyPosition(player, 0.2F));
    }

    private static BlockPos legacyPosition(Entity entity, float offset) {
        Vec3 position = entity.position();
        int x = Mth.floor(position.x);
        int y = Mth.floor(position.y - offset);
        int z = Mth.floor(position.z);
        BlockPos onPosition = new BlockPos(x, y, z);
        if (entity.level().getBlockState(onPosition).isAir()) {
            BlockPos below = onPosition.below();
            BlockState state = entity.level().getBlockState(below);
            if (state.is(BlockTags.FENCES) || state.is(BlockTags.WALLS) || state.getBlock() instanceof FenceGateBlock) {
                return below;
            }
        }
        return onPosition;
    }
}
