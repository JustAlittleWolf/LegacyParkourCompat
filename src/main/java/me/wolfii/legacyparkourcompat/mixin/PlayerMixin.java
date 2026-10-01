package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.FlightFallDistanceBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
abstract class PlayerMixin {
    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;resetFallDistance()V")
    )
    private void legacyParkourCompat$resetFallDistanceBeforeMovement(Entity entity) {
        Player player = (Player) (Object) this;
        var behavior = MovementRuntime.find(FlightFallDistanceBehavior.class, player);
        if (behavior.isEmpty() || !player.getAbilities().flying || player.isPassenger()) {
            entity.resetFallDistance();
        } else {
            behavior.orElseThrow().beforeMovement(player, entity::resetFallDistance);
        }
    }

}
