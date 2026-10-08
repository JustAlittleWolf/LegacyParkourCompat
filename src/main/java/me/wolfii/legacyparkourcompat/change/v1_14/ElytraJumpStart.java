package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingStartBehavior;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;

@MovementChange(emulates = ParkourVersion.V1_14)
public final class ElytraJumpStart implements FallFlyingStartBehavior {
    @Override
    public boolean shouldStartFallFlying(Player player) {
        if (player.onGround()
            || !(player.getDeltaMovement().y < 0.0)
            || player.isFallFlying()
            || player.getAbilities().flying) {
            return false;
        }

        var chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
        return chestItem.is(Items.ELYTRA)
            && chestItem.getDamageValue() < chestItem.getMaxDamage() - 1;
    }

    @Override
    public boolean isOnClimbableForFallFlyingStart(Player player, boolean vanilla) {
        return false;
    }

    @Override
    public boolean justToggledCreativeFlightForStart(Player player, boolean vanilla) {
        return false;
    }
}
