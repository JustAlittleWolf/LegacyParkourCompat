package me.wolfii.legacyparkourcompat.mixin.client;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintWindowBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintingBehavior;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(LocalPlayer.class)
abstract class LocalPlayerSprintMixin {
    @Redirect(
        method = "aiStep()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;"),
        slice = @Slice(from = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/Options;sprintWindow()Lnet/minecraft/client/OptionInstance;"
        ))
    )
    private Object legacy$doubleTapSprintWindow(OptionInstance<Integer> option) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        int vanilla = option.get();
        return MovementRuntime.find(SprintWindowBehavior.class, player)
            .map(behavior -> behavior.doubleTapWindow((Player) player, vanilla))
            .orElse(vanilla);
    }

    @Redirect(
        method = "isSprintingPossible(Z)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;isInShallowWater()Z"
        )
    )
    private boolean legacy$shallowWaterSprintEligibility(Entity entity) {
        boolean vanilla = entity.isInShallowWater();
        if (!(entity instanceof LocalPlayer player)) {
            return vanilla;
        }
        boolean sprintKeyDown = player.input.keyPresses.sprint();
        return MovementRuntime.find(SprintingBehavior.class, player)
            .map(behavior -> behavior.isInShallowWaterForSprintEligibility(
                player,
                sprintKeyDown,
                player.isSprinting(),
                vanilla
            ))
            .orElse(vanilla);
    }
}
