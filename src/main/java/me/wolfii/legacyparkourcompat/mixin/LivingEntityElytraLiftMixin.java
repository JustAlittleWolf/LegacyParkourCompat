package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.ElytraLiftForceBehavior;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityElytraLiftMixin {
    @Redirect(
        method = "updateFallFlyingMovement(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;square(D)D", ordinal = 0)
    )
    private double legacyparkourcompat$selectElytraLiftForce(double cosine) {
        double vanilla = Mth.square(cosine);
        if (!((Object) this instanceof Player player)) {
            return vanilla;
        }
        return MovementRuntime.find(ElytraLiftForceBehavior.class, player)
            .map(behavior -> behavior.liftForce(player, vanilla))
            .orElse(vanilla);
    }
}
