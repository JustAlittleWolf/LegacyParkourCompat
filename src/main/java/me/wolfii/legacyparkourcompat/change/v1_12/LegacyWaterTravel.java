package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.FluidTravelBehavior;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;

/** Water travel before 1.13's sprint drag and conditional buoyancy. */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class LegacyWaterTravel implements FluidTravelBehavior {
    @Override
    public void travelInFluid(Player player, Vec3 input, VanillaCall vanilla) {
        if (!player.isInWater()) {
            vanilla.run();
            return;
        }

        double oldY = player.getY();
        float drag = 0.8F;
        float speed = 0.02F;
        int depthStrider = EnchantmentHelper.getEnchantmentLevel(
            player.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.DEPTH_STRIDER),
            player
        );
        float level = Math.min(depthStrider, 3);
        if (!player.onGround()) {
            level *= 0.5F;
        }
        if (level > 0.0F) {
            drag += (0.54600006F - drag) * level / 3.0F;
            speed += (player.getSpeed() - speed) * level / 3.0F;
        }

        moveRelative(player, input, speed);
        player.move(MoverType.SELF, player.getDeltaMovement());
        Vec3 movement = player.getDeltaMovement();
        double x = movement.x * drag;
        double y = movement.y * 0.8F;
        double z = movement.z * drag;
        if (!player.isNoGravity()) {
            y -= 0.02;
        }
        player.setDeltaMovement(x, y, z);
        if (player.horizontalCollision && player.isFree(x, y + 0.6F - player.getY() + oldY, z)) {
            player.setDeltaMovement(x, 0.3F, z);
        }
    }

    private static void moveRelative(Player player, Vec3 input, float speed) {
        float sideways = (float) input.x;
        float upward = (float) input.y;
        float forward = (float) input.z;
        if (Math.abs(sideways) > 0.7F && Math.abs(forward) > 0.7F) {
            sideways = Math.copySign(0.98F, sideways);
            forward = Math.copySign(0.98F, forward);
        }
        float magnitude = sideways * sideways + upward * upward + forward * forward;
        if (magnitude < 1.0E-4F) {
            return;
        }
        magnitude = Mth.sqrt(magnitude);
        if (magnitude < 1.0F) {
            magnitude = 1.0F;
        }
        magnitude = speed / magnitude;
        sideways *= magnitude;
        upward *= magnitude;
        forward *= magnitude;
        float radians = player.getYRot() * (float) (Math.PI / 180.0);
        float sin = Mth.sin(radians);
        float cos = Mth.cos(radians);
        Vec3 movement = player.getDeltaMovement();
        player.setDeltaMovement(
            movement.x + (sideways * cos - forward * sin),
            movement.y + upward,
            movement.z + (forward * cos + sideways * sin)
        );
    }
}
