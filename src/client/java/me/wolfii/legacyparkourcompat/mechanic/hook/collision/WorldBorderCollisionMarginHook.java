package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.level.border.WorldBorder;

/** Point-with-margin admission used only by the player border-collision gate. */
@MechanicType("collision.world_border_margin")
public interface WorldBorderCollisionMarginHook extends VersionedMechanic {
    boolean isWithinCollisionMargin(WorldBorder border, double x, double z, double margin);
}
