package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BubbleColumnAboveContactHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BubbleColumnBlock.class)
public abstract class BubbleColumnBlockMixin {
    @ModifyVariable(
        method = "entityInside(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/InsideBlockEffectApplier;Z)V",
        at = @At(value = "STORE"),
        name = "nothingAbove",
        expect = 1
    )
    private boolean legacyparkourcompat$useHistoricalAboveContact(
        boolean vanillaAboveContact,
        @Local(name = "stateAbove") BlockState stateAbove,
        @Local(argsOnly = true) Entity entity
    ) {
        return MovementRuntime.find(BubbleColumnAboveContactHook.class, entity)
            .map(change -> change.isAboveContact(MovementRuntime.profile(entity).target(), stateAbove, vanillaAboveContact))
            .orElse(vanillaAboveContact);
    }
}
