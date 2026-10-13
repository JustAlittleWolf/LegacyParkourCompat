package me.wolfii.legacyparkourcompat.mechanic.input;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.UseEffects;
import net.minecraft.world.phys.Vec2;

/** Shared native-feature applicability and unchanged movement-copy arithmetic. */
public final class KeyboardInputSupport {
    private KeyboardInputSupport() {}

    /** KeyboardInput constructs signed unit axes; opposing keys cancel. */
    public static float impulse(boolean positive, boolean negative) {
        return positive == negative ? 0.0F : positive ? 1.0F : -1.0F;
    }

    public static boolean supports(Player player, ParkourVersion selected) {
        if (player.getAttributeValue(Attributes.SCALE) != 1.0
            || player.getAttributeValue(Attributes.SNEAKING_SPEED) != 0.3) {
            return false;
        }
        if (selected.olderThan(ParkourVersion.V1_13) && player.isSwimming()
            || selected.olderThan(ParkourVersion.V1_14) && player.getPose() == Pose.SWIMMING && !player.isSwimming()) {
            return false;
        }
        if (player.isUsingItem() && !player.isPassenger()) {
            UseEffects effects = player.getUseItem().getOrDefault(DataComponents.USE_EFFECTS, UseEffects.DEFAULT);
            return effects.speedMultiplier() == UseEffects.DEFAULT.speedMultiplier()
                && effects.canSprint() == UseEffects.DEFAULT.canSprint();
        }
        return true;
    }

    /**
     * Mojmap 1.14.4 and 1.21.4 LocalPlayer.serverAiStep copy stored input;
     * LivingEntity.aiStep scales those copies without writing back to input.
     */
    public static Vec2 travelInput(Vec2 input) {
        return new Vec2(input.x * 0.98F, input.y * 0.98F);
    }
}
