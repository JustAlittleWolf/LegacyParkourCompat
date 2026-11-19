package me.wolfii.legacyparkourcompat.mixin.accessor;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.EnderDragonPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EnderDragon.class)
public interface EnderDragonAccessor {
    // 1.20.1 Mojmap EnderDragon.getPassengersRidingOffset reads this part's bounding box height.
    @Accessor("body")
    EnderDragonPart legacyparkourcompat$body();
}
