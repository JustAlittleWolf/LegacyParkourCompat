package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.accessor.JumpPowerAccess;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityJumpAccessor extends JumpPowerAccess {
    @Override
    @Invoker("getBlockJumpFactor")
    float legacyparkour$getBlockJumpFactor();
}
