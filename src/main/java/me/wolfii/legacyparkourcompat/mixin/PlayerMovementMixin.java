package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeBehavior;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
abstract class PlayerMovementMixin {
    @Inject(method = "maybeBackOffFromEdge", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$maybeBackOffFromEdge(
        Vec3 movement,
        MoverType moverType,
        CallbackInfoReturnable<Vec3> callback
    ) {
        Player player = (Player) (Object) this;
        MovementRuntime.find(SneakEdgeBehavior.class, player).ifPresent(behavior -> {
            boolean stayingOnGroundSurface = (moverType == MoverType.SELF || moverType == MoverType.PLAYER)
                && player.onGround()
                && ((PlayerMovementAccessor) player).legacyparkourcompat$isStayingOnGroundSurface();
            callback.setReturnValue(behavior.maybeBackOffFromEdge(
                player,
                movement,
                moverType,
                stayingOnGroundSurface
            ));
        });
    }
}
