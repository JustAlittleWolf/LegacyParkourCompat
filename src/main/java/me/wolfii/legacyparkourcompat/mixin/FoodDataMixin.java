package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.FastRegenerationBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.RegenerationExhaustionBehavior;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FoodData.class)
abstract class FoodDataMixin {
    @Redirect(
        method = "tick",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;isHurt()Z", ordinal = 0)
    )
    private boolean legacyparkourcompat$allowFastRegeneration(ServerPlayer player) {
        return MovementRuntime.find(FastRegenerationBehavior.class, player)
            .map(behavior -> behavior.isHurtForFastRegeneration(player.isHurt()))
            .orElseGet(player::isHurt);
    }

    @Redirect(
        method = "tick",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;addExhaustion(F)V", ordinal = 1)
    )
    private void legacyparkourcompat$naturalRegenerationExhaustion(
        FoodData foodData,
        float exhaustion,
        ServerPlayer player
    ) {
        float historical = MovementRuntime.find(RegenerationExhaustionBehavior.class, player)
            .map(behavior -> behavior.slowRegenerationExhaustion(exhaustion))
            .orElse(exhaustion);
        foodData.addExhaustion(historical);
    }
}
