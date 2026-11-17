package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockBounceHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * Restores the 1.12 bed response for a falling, non-sneaking player.
 *
 * <p>In Ornithe Feather 1.11.2, {@code Block.beforeCollision(World, Entity)}
 * is inherited by beds and sets vertical velocity to zero; {@code Entity.move}
 * calls it after vertical clipping. In Ornithe Feather 1.12,
 * {@code BedBlock.beforeCollision(World, Entity)} reflects negative vertical
 * velocity at {@code 0.66F} for non-sneaking entities, while
 * {@code Entity.move} retains the same vertical-clipping callback. The extra
 * {@code 0.8} multiplier is limited to non-living entities, so it does not
 * apply to players.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class BedBounce implements BlockBounceHook {
    @Override
    public String blockId() {
        return "minecraft:red_bed";
    }

    @Override
    public float bounceRestitution(float vanilla, Entity entity) {
        if (entity instanceof Player player
            && !player.isShiftKeyDown()
            && player.getDeltaMovement().y < 0.0) {
            return 0.66F;
        }

        return vanilla;
    }
}
