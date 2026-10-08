package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingSavedStateBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerRestSafetyBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerRestSafetyQueryBehavior;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.storage.ValueInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(ServerPlayer.class)
abstract class ServerPlayerMixin {
    @ModifyExpressionValue(
        method = "startSleepInBed(Lnet/minecraft/core/BlockPos;)Lcom/mojang/datafixers/util/Either;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;isCreative()Z")
    )
    private boolean legacyparkourcompat$restSafetyCreativeGate(boolean vanilla) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        return MovementRuntime.find(PlayerRestSafetyQueryBehavior.class, player)
            .map(behavior -> behavior.skipSafetyQuery(vanilla))
            .orElse(vanilla);
    }

    @ModifyArg(
        method = "startSleepInBed(Lnet/minecraft/core/BlockPos;)Lcom/mojang/datafixers/util/Either;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/EntityGetter;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"
        ),
        index = 2
    )
    private Predicate<Monster> legacyparkourcompat$restSafetyPredicate(
        Predicate<Monster> vanillaPredicate
    ) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        return monster -> MovementRuntime.find(PlayerRestSafetyBehavior.class, player)
            .map(behavior -> behavior.preventsRest(
                monster,
                () -> vanillaPredicate.test(monster)
            ))
            .orElseGet(() -> vanillaPredicate.test(monster));
    }

    @Inject(
        method = "readAdditionalSaveData(Lnet/minecraft/world/level/storage/ValueInput;)V",
        at = @At("TAIL")
    )
    private void legacyparkourcompat$loadFallFlyingState(ValueInput input, CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        MovementRuntime.find(FallFlyingSavedStateBehavior.class, player)
            .ifPresent(behavior -> behavior.afterFallFlyingStateLoaded(
                player,
                MovementRuntime.profile(player).target()
            ));
    }
}
