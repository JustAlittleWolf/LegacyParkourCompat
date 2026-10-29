package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.EntityInsideBlocksQueryBoxHook;

/**
 * Sources: 1.21.2 and 1.21.3 Mojmap
 * net.minecraft.world.entity.Entity.applyEffectsFromBlocks(Vec3, Vec3) and
 * Entity.checkInsideBlocks, lines 736-767 and 1038-1078. The compared
 * Entity.java files are byte-identical across these releases. The list scan
 * deflates the final entity box once before iterating recorded movements and
 * reuses that query box for each.
 */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class EntityInsideBlocksSharedQueryBox implements EntityInsideBlocksQueryBoxHook {
    @Override
    public boolean shareFinalQueryBox() {
        return true;
    }
}
