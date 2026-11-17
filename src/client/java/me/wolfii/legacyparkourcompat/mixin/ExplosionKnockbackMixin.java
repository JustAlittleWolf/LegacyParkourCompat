package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.explosion.ExplosionKnockbackHook;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(targets = "net.minecraft.client.multiplayer.ClientPacketListener")
abstract class ExplosionKnockbackMixin {
    @Redirect(
        method = "handleExplosion",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/Optional;ifPresent(Ljava/util/function/Consumer;)V"
        )
    )
    private void legacyparkourcompat$applyExplosionKnockback(
        Optional<Vec3> knockback,
        Consumer<Vec3> addDeltaMovement
    ) {
        Player player = Minecraft.getInstance().player;
        Optional<ExplosionKnockbackHook> hook = MovementRuntime.find(ExplosionKnockbackHook.class, player);
        if (hook.isPresent()) {
            hook.get().apply(knockback, addDeltaMovement);
        } else {
            knockback.ifPresent(addDeltaMovement);
        }
    }
}
