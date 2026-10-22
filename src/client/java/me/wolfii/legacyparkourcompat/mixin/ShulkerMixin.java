package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.shulker.ShulkerPeekPushQueryHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.function.Predicate;

@Mixin(Shulker.class)
abstract class ShulkerMixin {
    @WrapOperation(
        method = "onPeekAmountChange",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"
        )
    )
    private List<Entity> legacyparkourcompat$playerScopedPeekPushQuery(
        Level level,
        Entity except,
        AABB vanillaBounds,
        Predicate<? super Entity> selector,
        Operation<List<Entity>> original,
        @Local(name = "direction") Direction direction,
        @Local(name = "physicalPeekOld") float progressFrom,
        @Local(name = "physicalPeek") float progressTo
    ) {
        Shulker shulker = (Shulker) (Object) this;
        if (!level.isClientSide()) {
            return original.call(level, except, vanillaBounds, selector);
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || player.level() != level) {
            return original.call(level, except, vanillaBounds, selector);
        }

        var hook = MovementRuntime.find(ShulkerPeekPushQueryHook.class, player);
        if (hook.isEmpty()) {
            return original.call(level, except, vanillaBounds, selector);
        }

        ParkourVersion selected = MovementRuntime.profile(player).target();
        if (selected.olderThan(ParkourVersion.V1_20_5)) {
            return original.call(level, except, vanillaBounds, selector);
        }

        AABB historicalBounds = hook.get().bounds(
            shulker.getScale(), direction, progressFrom, progressTo, shulker.position()
        );
        AABB queryBounds = vanillaBounds.minmax(historicalBounds);
        Predicate<Entity> scopedSelector = entity -> {
            AABB applicableBounds = entity == player ? historicalBounds : vanillaBounds;
            return entity.getBoundingBox().intersects(applicableBounds) && selector.test(entity);
        };
        return original.call(level, except, queryBounds, scopedSelector);
    }
}
