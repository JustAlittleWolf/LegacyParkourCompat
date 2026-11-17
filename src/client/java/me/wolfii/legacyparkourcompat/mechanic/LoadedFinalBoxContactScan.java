package me.wolfii.legacyparkourcompat.mechanic;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

import java.util.function.Consumer;

/** Enumerates loaded cells in historical final-box X/Y/Z order. */
public final class LoadedFinalBoxContactScan {
    private LoadedFinalBoxContactScan() {
    }

    public static boolean visit(Entity entity, double inset, Consumer<BlockPos> contact) {
        AABB box = entity.getBoundingBox();
        BlockPos min = BlockPos.containing(box.minX + inset, box.minY + inset, box.minZ + inset);
        BlockPos max = BlockPos.containing(box.maxX - inset, box.maxY - inset, box.maxZ - inset);
        if (!entity.level().hasChunksAt(min, max)) {
            return false;
        }

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int y = min.getY(); y <= max.getY(); y++) {
                for (int z = min.getZ(); z <= max.getZ(); z++) {
                    if (!entity.isAlive()) {
                        return false;
                    }
                    contact.accept(pos.set(x, y, z));
                }
            }
        }
        return true;
    }
}
