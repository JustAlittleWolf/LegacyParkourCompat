package me.wolfii.legacyparkourcompat.mixin.border;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.wolfii.legacyparkourcompat.mechanic.BorderAdmissionContext;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public abstract class LivingEntityWakeContextMixin {
    @WrapMethod(method = "stopSleeping")
    private void legacyparkourcompat$wakeContext(Operation<Void> original) {
        Entity entity = (Entity) (Object) this;
        BorderAdmissionContext.withWakingPlayer(MovementRuntime.appliesTo(entity) ? entity : null,
            () -> original.call());
    }
}
