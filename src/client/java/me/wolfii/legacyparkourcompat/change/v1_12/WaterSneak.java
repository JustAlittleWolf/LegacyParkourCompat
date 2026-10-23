package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.WaterSneakHook;
import net.minecraft.world.entity.player.Player;
import me.wolfii.legacyparkourcompat.mechanic.water.WaterContext;

/**
 * 1.12/1.12.1/1.12.2 Feather LocalClientPlayerEntity.mobTick has no water-sneak
 * impulse. 1.13 Feather LocalClientPlayerEntity.mobTick/LivingEntity.knockDownwards
 * introduces it; 26.2 unobfuscated LocalPlayer.aiStep/LivingEntity.goDownInWater
 * subtracts 0.04F. The shared ordinary-water context bounds this operation.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class WaterSneak implements WaterSneakHook {
    @Override
    public boolean shouldApplyDownwardImpulse(Player player, boolean vanilla) {
        return !((WaterContext)player).legacyparkourcompat$hasSupportedWaterContext() && vanilla;
    }
}
