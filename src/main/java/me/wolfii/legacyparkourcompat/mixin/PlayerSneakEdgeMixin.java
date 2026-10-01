package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeDistanceBehavior;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
abstract class PlayerSneakEdgeMixin {
    @Redirect(
        method = "maybeBackOffFromEdge(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/MoverType;)Lnet/minecraft/world/phys/Vec3;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;maxUpStep()F")
    )
    private float legacyparkourcompat$edgeProbeDistance(Player player) {
        float vanillaDistance = player.maxUpStep();
        return MovementRuntime.find(SneakEdgeDistanceBehavior.class, player)
            .map(behavior -> behavior.edgeFallDistance(player, vanillaDistance))
            .orElse(vanillaDistance);
    }
}
