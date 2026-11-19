package me.wolfii.legacyparkourcompat.mixin.equipment;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.equipment.EquipmentUseHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Equippable.class)
abstract class EquippableMixin {
    @Shadow
    public abstract EquipmentSlot slot();

    @Inject(
        method = "swapWithEquipmentSlot(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/InteractionResult;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;has(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/core/component/DataComponentType;)Z"),
        cancellable = true
    )
    private void legacyparkourcompat$equipmentUse(ItemStack held, Player player, CallbackInfoReturnable<InteractionResult> callback) {
        if (player instanceof LocalPlayer) {
            MovementRuntime.find(EquipmentUseHook.class, player)
                .map(hook -> hook.result(MovementRuntime.profile(player).target(), this.slot(), held, player.getItemBySlot(this.slot())))
                .ifPresent(callback::setReturnValue);
        }
    }
}
