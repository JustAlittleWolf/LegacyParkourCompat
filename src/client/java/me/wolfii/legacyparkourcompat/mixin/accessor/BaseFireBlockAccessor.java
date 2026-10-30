package me.wolfii.legacyparkourcompat.mixin.accessor;

import net.minecraft.world.level.block.BaseFireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BaseFireBlock.class)
public interface BaseFireBlockAccessor {
    @Accessor("fireDamage")
    float legacyParkourCompat$fireDamage();
}
