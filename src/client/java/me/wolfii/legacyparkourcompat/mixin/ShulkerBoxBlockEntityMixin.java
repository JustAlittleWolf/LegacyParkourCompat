package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.shulker.ShulkerOpeningPushAmountHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.shulker.ShulkerOpeningPushQueryHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

/** Dispatches historical shulker box opening pushes for the local player. */
@Mixin(ShulkerBoxBlockEntity.class)
abstract class ShulkerBoxBlockEntityMixin {
    @WrapOperation(
        method = "moveCollidedEntities",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"
        )
    )
    private List<Entity> legacyparkourcompat$openingPushQuery(Level level, Entity except, AABB nativeBounds,
                                                               Operation<List<Entity>> original) {
        if (!level.isClientSide()) {
            return original.call(level, except, nativeBounds);
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || player.level() != level || !MovementRuntime.appliesTo(player)
            || MovementRuntime.profile(player).target().olderThan(ParkourVersion.V1_11)) {
            return original.call(level, except, nativeBounds);
        }

        var hook = MovementRuntime.find(ShulkerOpeningPushQueryHook.class, player);
        if (hook.isEmpty()) {
            return original.call(level, except, nativeBounds);
        }

        AABB historicalBounds = hook.get().openingPushBounds((ShulkerBoxBlockEntity) (Object) this);
        return level.getEntities(except, nativeBounds.minmax(historicalBounds), entity -> {
            AABB applicableBounds = entity == player ? historicalBounds : nativeBounds;
            return applicableBounds.intersects(entity.getBoundingBox()) && EntitySelector.NO_SPECTATORS.test(entity);
        });
    }

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
