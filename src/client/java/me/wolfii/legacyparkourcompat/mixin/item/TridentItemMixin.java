package me.wolfii.legacyparkourcompat.mixin.item;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(net.minecraft.world.item.TridentItem.class)
public abstract class TridentItemMixin {
    @ModifyExpressionValue(
        method = "use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;nextDamageWillBreak()Z"
        )
    )
    private boolean legacyparkourcompat$useDurabilityGate(
        boolean vanillaResult, @Local ItemStack stack, @Local(argsOnly = true) Player player
    ) {
        return vanillaResult && !MovementRuntime.tridentUseDurability(player)
            .map(hook -> hook.allowsUse(stack, EnchantmentHelper.getTridentSpinAttackStrength(stack, player)))
            .orElse(false);
    }

    @ModifyExpressionValue(
        method = "releaseUsing(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;I)Z",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;nextDamageWillBreak()Z")
    )
    private boolean legacyparkourcompat$releaseDurabilityGate(
        boolean vanillaResult, @Local(argsOnly = true) ItemStack stack, @Local Player player,
        @Local float riptideStrength
    ) {
        return vanillaResult && !MovementRuntime.tridentUseDurability(player)
            .map(hook -> hook.allowsUse(stack, riptideStrength))
            .orElse(false);
    }
}
