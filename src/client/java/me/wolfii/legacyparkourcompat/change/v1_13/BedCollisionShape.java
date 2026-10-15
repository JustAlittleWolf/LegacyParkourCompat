package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Ornithe Feather 1.8.9 BedBlock.setDefaultShape and 1.13.2 BedBlock.SHAPE /
 * getShape use the full-footprint box through 9/16. Ornithe Feather 1.14
 * BedBlock.getShape replaces its bottom three sixteenths with corner legs;
 * 1.14.4 retains that geometry. Only collision changes, not rendered shape,
 * bed state or entry eligibility. Non-red beds are modern-only before 1.12
 * and retain native collision in those profiles.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class BedCollisionShape implements BlockCollisionShapeHook {
    private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);
    private final String blockId;

    public BedCollisionShape(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<BedCollisionShape> variants() {
        return Arrays.stream(DyeColor.values())
            .map(color -> new BedCollisionShape("minecraft:" + color.getName() + "_bed")).toList();
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public boolean supports(ParkourVersion selected) {
        return this.blockId.equals("minecraft:red_bed") || selected.newerThanOrEqual(ParkourVersion.V1_12);
    }

    @Override
    public Optional<VoxelShape> collisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Optional.of(SHAPE);
    }
}
