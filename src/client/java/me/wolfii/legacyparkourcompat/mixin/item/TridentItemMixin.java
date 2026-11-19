package me.wolfii.legacyparkourcompat.mixin.item;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import me.wolfii.legacyparkourcompat.mechanic.hook.item.RiptideReleaseDurabilityHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.item.RiptideReleaseTrigHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.item.TridentUseDurabilityHook;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(net.minecraft.world.item.TridentItem.class)
public abstract class TridentItemMixin {
    @Unique
    private static final String RELEASE_USING =
        "releaseUsing(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;I)Z";

    @ModifyExpressionValue(
        method = "use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;nextDamageWillBreak()Z")
    )
    private boolean legacyparkourcompat$useDurabilityGate(
        boolean vanillaResult, @Local ItemStack stack, @Local(argsOnly = true) Player player
    ) {
        return vanillaResult && !MovementRuntime.tridentUseDurability(player)
            .map(hook -> hook.allowsUse(stack, EnchantmentHelper.getTridentSpinAttackStrength(stack, player)))
            .orElse(false);
    }

    @ModifyExpressionValue(
        method = RELEASE_USING,
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;nextDamageWillBreak()Z"),
        require = 1
    )
    private boolean legacyparkourcompat$riptideReleaseDurability(
        boolean vanillaResult, @Local(argsOnly = true) ItemStack stack,
        @Local(argsOnly = true) LivingEntity entity, @Local(ordinal = 0) float strength
    ) {
        boolean result = vanillaResult;
        if (entity instanceof Player player) {
            result = result && !MovementRuntime.tridentUseDurability(player)
                .map(hook -> hook.allowsUse(stack, strength))
                .orElse(false);
        }
        return legacyparkourcompat$findTridentHook(RiptideReleaseDurabilityHook.class, entity)
            .map(hook -> hook.rejectsRelease(strength, result))
            .orElse(result);
    }

    @WrapOperation(
        method = RELEASE_USING,
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;sin(D)F"),
        require = 2
    )
    private float legacyparkourcompat$riptideReleaseSin(
        double radians, Operation<Float> vanilla, @Local(argsOnly = true) LivingEntity entity
    ) {
        return legacyparkourcompat$findTridentHook(RiptideReleaseTrigHook.class, entity)
            .map(hook -> hook.sin((float) radians))
            .orElseGet(() -> vanilla.call(radians));
    }

    @WrapOperation(
        method = RELEASE_USING,
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;cos(D)F"),
        require = 3
    )
    private float legacyparkourcompat$riptideReleaseCos(
        double radians, Operation<Float> vanilla, @Local(argsOnly = true) LivingEntity entity
    ) {
        return legacyparkourcompat$findTridentHook(RiptideReleaseTrigHook.class, entity)
            .map(hook -> hook.cos((float) radians))
            .orElseGet(() -> vanilla.call(radians));
    }

    @Unique
    private static <T extends VersionedMechanic> Optional<T> legacyparkourcompat$findTridentHook(
        Class<T> type, LivingEntity entity
    ) {
        if (!MovementRuntime.appliesTo(entity)
            || MovementRuntime.profile(entity).target().olderThan(ParkourVersion.V1_13)) {
            return Optional.empty();
        }
        return MovementRuntime.find(type, entity);
    }
}
