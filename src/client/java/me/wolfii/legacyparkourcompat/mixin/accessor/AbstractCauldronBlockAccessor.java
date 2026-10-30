package me.wolfii.legacyparkourcompat.mixin.accessor;

import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractCauldronBlock.class)
public interface AbstractCauldronBlockAccessor {
    @Invoker("getContentHeight")
    double legacyParkourCompat$contentHeight(BlockState state);
}
