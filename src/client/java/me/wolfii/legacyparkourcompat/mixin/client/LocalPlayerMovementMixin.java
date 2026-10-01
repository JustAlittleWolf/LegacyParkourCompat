package me.wolfii.legacyparkourcompat.mixin.client;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.ClientUnstuckBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintTriggerBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterDescentBehavior;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
abstract class LocalPlayerMovementMixin {
    @Shadow
    private boolean isSlowDueToUsingItem() {
        throw new AssertionError();
    }

    @Redirect(
        method = "aiStep",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/player/LocalPlayer;sprintTriggerTime:I",
            opcode = Opcodes.PUTFIELD,
            ordinal = 1
        )
    )
    private void legacyparkourcompat$sprintTriggerReset(LocalPlayer player, int value) {
        LocalPlayerAccessor accessor = (LocalPlayerAccessor) player;
        // The pre-1.16 path still cleared the timer for item use. The newer
        // crouch and backward-input cancellations belong to this assignment,
        // so only preserve the item-use cause when emulating the older path.
        boolean anotherResetCause = this.isSlowDueToUsingItem() && !player.isPassenger();
        boolean shouldReset = MovementRuntime.find(SprintTriggerBehavior.class, player)
            .map(behavior -> behavior.shouldReset(player, anotherResetCause))
            .orElse(true);
        if (shouldReset) {
            accessor.legacyparkourcompat$setSprintTriggerTime(value);
        }
    }

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isAffectedByFluids()Z")
    )
    private boolean legacyparkourcompat$waterDescentGate(LocalPlayer player) {
        boolean vanilla = player.isAffectedByFluids();
        return MovementRuntime.find(WaterDescentBehavior.class, player)
            .map(behavior -> behavior.mayDescend(player, vanilla))
            .orElse(vanilla);
    }

    @Inject(method = "suffocatesAt", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$suffocationQuery(BlockPos pos, CallbackInfoReturnable<Boolean> callback) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        MovementRuntime.find(ClientUnstuckBehavior.class, player).ifPresent(behavior ->
            callback.setReturnValue(behavior.suffocatesAt(player, pos, callback::getReturnValue))
        );
    }
}
