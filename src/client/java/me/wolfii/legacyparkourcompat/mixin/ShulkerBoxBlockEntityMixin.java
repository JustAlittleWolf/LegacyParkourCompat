package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.shulker.ShulkerOpeningPushAmountHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/** Dispatches the historical opening amount only for the local player. */
@Mixin(ShulkerBoxBlockEntity.class)
abstract class ShulkerBoxBlockEntityMixin {
    @Redirect(
        method = "moveCollidedEntities",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V"
        )
    )
    private void legacyparkourcompat$openingPushAmount(Entity entity, MoverType moverType, Vec3 nativeAmount) {
        if (!(entity instanceof LocalPlayer player) || !MovementRuntime.appliesTo(player)
            || MovementRuntime.profile(player).target().olderThan(ParkourVersion.V1_11)) {
            entity.move(moverType, nativeAmount);
            return;
        }

        Vec3 amount = MovementRuntime.find(ShulkerOpeningPushAmountHook.class, player)
            .map(hook -> hook.pushAmount((ShulkerBoxBlockEntity) (Object) this, player, nativeAmount))
            .orElse(nativeAmount);
        entity.move(moverType, amount);
    }
}
