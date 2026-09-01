package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/**
 * Historical eye height ({@code Entity#getEyeHeight()}). Independent of pose
 * selection so a version can keep a full-height box while still lowering the
 * camera (1.8 sneaking).
 */
@MechanicType("player.eye_height")
public interface EyeHeightBehavior extends VersionedMechanic {
    float eyeHeight(Player player, float vanilla);
}
