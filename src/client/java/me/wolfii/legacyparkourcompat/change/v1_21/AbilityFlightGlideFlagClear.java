package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.AbilityFlightGlideFlagHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;

/**
 * Restores the direct post-movement flag-7 clear for local ability flight with
 * an already-live Elytra glide. In 1.9.1 and 1.12.2 (Ornithe Feather Gen2),
 * {@code PlayerEntity#moveRelative} runs inherited movement before
 * {@code setFlag(7,false)}. The exact 1.12.2–1.20.5 adjacent source archive
 * follows the same operation through {@code PlayerEntity#moveRelative}
 * (Feather Gen2), {@code PlayerEntity#travel} (Yarn 1.14.2–1.14.3), and
 * {@code Player#travel(Vec3)} (Mojmap 1.14.4–1.20.5). In 1.21.1 (Mojmap),
 * {@code Player#travel(Vec3)} clears shared flag 7 after {@code super.travel};
 * in 26.2 (unobfuscated), {@code Player#travel(Vec3)} omits that clear even
 * though the existing flag still selects {@code LivingEntity#travel(Vec3)}
 * and {@code LivingEntity#travelFallFlying(Vec3)} for the movement.
 *
 * <p>The V1_8_9 native fence preserves profiles before Elytra, and this
 * V1_21 provider resolves the source-confirmed 1.9–1.21.1 interval. Later
 * profiles have no applicable provider. The direct clear remains separate
 * from the fall-distance reset and is local-only; no server-authority or
 * runtime-parity claim is made.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class AbilityFlightGlideFlagClear implements AbilityFlightGlideFlagHook {
    @Override
    public boolean shouldClearAfterTravel(Player player) {
        return player instanceof LocalPlayer
            && player.level().isClientSide()
            && player.getAbilities().flying
            && player.isFallFlying()
            && !player.isPassenger()
            && !player.isInWater()
            && !player.isInLava()
            && player.getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA);
    }
}
