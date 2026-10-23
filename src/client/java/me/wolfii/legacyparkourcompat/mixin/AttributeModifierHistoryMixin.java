package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.attribute.MovementSpeedModifierHistory;
import me.wolfii.legacyparkourcompat.mechanic.attribute.MovementSpeedModifierHistoryAccess;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(AttributeInstance.class)
abstract class AttributeModifierHistoryMixin implements MovementSpeedModifierHistoryAccess {
    @Shadow @Final
    private Map<AttributeModifier.Operation, Map<Identifier, AttributeModifier>> modifiersByOperation;

    @Unique
    private final MovementSpeedModifierHistory legacyparkourcompat$modifierHistory = new MovementSpeedModifierHistory();

    @Inject(method = "setDirty", at = @At("HEAD"))
    private void legacyparkourcompat$observeModifierHistory(CallbackInfo callback) {
        MovementRuntime.observeMovementSpeedModifiers((AttributeInstance)(Object)this,
            legacyparkourcompat$modifierHistory, modifiersByOperation);
    }

    @Override
    public MovementSpeedModifierHistory legacyparkourcompat$movementSpeedModifierHistory() {
        MovementRuntime.observeMovementSpeedModifiers((AttributeInstance)(Object)this,
            legacyparkourcompat$modifierHistory, modifiersByOperation);
        return legacyparkourcompat$modifierHistory;
    }
}
