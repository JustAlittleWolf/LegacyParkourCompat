package me.wolfii.legacyparkourcompat.mixin.item;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.item.TridentUseDurabilityHook;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(net.minecraft.world.item.TridentItem.class)
public abstract class TridentItemMixin {
    @Redirect(
        method = "use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;nextDamageWillBreak()Z"
        )
    )
    private boolean legacyparkourcompat$useDurabilityGate(
        ItemStack stack, Level level, Player player, InteractionHand hand
    ) {
        boolean vanillaResult = stack.nextDamageWillBreak();
        if (!MovementRuntime.appliesTo(player)) {
            return vanillaResult;
        }

        if (MovementRuntime.profile(player).target().olderThan(ParkourVersion.V1_13)) {
            return vanillaResult;
        }
        return MovementRuntime.find(TridentUseDurabilityHook.class, player)
            .map(hook -> hook.rejectsUse(player, stack, vanillaResult))
            .orElse(vanillaResult);
    }
}
