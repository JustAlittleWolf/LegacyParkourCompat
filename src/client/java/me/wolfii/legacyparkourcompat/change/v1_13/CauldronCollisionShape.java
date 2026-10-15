package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;

/**
 * Ornithe Feather 1.13.2 CauldronBlock.SHAPE / getShape subtract only the
 * upper interior (2,4,2)-(14,16,14) from the full block. Ornithe Feather 1.14
 * CauldronBlock.SHAPE adds bottom cutouts, retained by 1.14.4. Preserve the
 * old full floor for empty and water-filled cauldrons, which represent the
 * old LEVEL states. Lava/powder-snow cauldrons are modern-only and untouched.
 * Pre-1.13 geometry differs (1.8.9 uses a 5/16 floor) and remains unbound.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class CauldronCollisionShape implements BlockCollisionShapeHook {
    private static final VoxelShape SHAPE = Shapes.join(Shapes.block(),
        Block.box(2.0, 4.0, 2.0, 14.0, 16.0, 14.0), BooleanOp.ONLY_FIRST);
    private final String blockId;

    public CauldronCollisionShape(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<CauldronCollisionShape> variants() {
        return List.of(new CauldronCollisionShape("minecraft:cauldron"),
            new CauldronCollisionShape("minecraft:water_cauldron"));
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public boolean supports(ParkourVersion selected) {
        return selected.newerThanOrEqual(ParkourVersion.V1_13);
    }

    @Override
    public Optional<VoxelShape> collisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Optional.of(SHAPE);
    }
}
