package me.wolfii.legacyparkourcompat.mixin.accessor;

import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityVisitedBlocksAccessor {
    @Accessor("visitedBlocks")
    LongSet legacyparkourcompat$visitedBlocks();
}
