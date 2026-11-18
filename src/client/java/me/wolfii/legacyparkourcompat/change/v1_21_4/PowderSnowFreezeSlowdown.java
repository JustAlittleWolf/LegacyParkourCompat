package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.MovementSpeedModifierIdentity;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.PowderSnowFreezeSlowdownHook;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Mojmap 1.21.4 LivingEntity.aiStep calls removeFrost and tryAddFrost after travel.
 * Mojmap 1.21.5 and unobfuscated 26.2 LivingEntity.aiStep restrict those calls
 * to the server freeze section. The client adapter runs before the "push" section.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class PowderSnowFreezeSlowdown implements PowderSnowFreezeSlowdownHook {
    @Override
    public void recompute(Player player, VanillaFn<BlockState> frostSample) {
        var id = MovementSpeedModifierIdentity.FROST;
        var speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null && speed.getModifier(id) != null) {
            speed.removeModifier(id);
        }

        if (frostSample.get().isAir() || player.getTicksFrozen() <= 0) {
            return;
        }
        speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) {
            float amount = -0.05F * player.getPercentFrozen();
            speed.addTransientModifier(new AttributeModifier(id, amount, AttributeModifier.Operation.ADD_VALUE));
        }
    }
}
