package me.wolfii.legacyparkourcompat.mixin;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/** Reads the raw synchronized shared-flags byte without invoking gameplay setters. */
@Mixin(Entity.class)
public interface EntitySharedFlagsAccessor {
    @Accessor("DATA_SHARED_FLAGS_ID")
    static EntityDataAccessor<Byte> legacyparkourcompat$getSharedFlagsAccessor() {
        throw new AssertionError();
    }
}
