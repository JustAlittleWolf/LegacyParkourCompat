package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.RespawnMovementAttributeHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * In 1.14.4 Mojmap ClientPacketListener.handleRespawn replaces the local player
 * without copying attributes, and Player.registerAttributes gives that player a
 * movement-speed base of 0.1F. Starting in 1.15 Mojmap,
 * ClientPacketListener.handleRespawn copies attributes through
 * BaseAttributeMap.assignValues and AttributeInstance.copyFrom. Restore the
 * 1.14.4 fresh-player movement-speed value after either 26.2 unobfuscated
 * ClientPacketListener.handleRespawn branch, assignAllValues or assignBaseValues.
 * Other attributes remain native.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class ResetRespawnMovementSpeed implements RespawnMovementAttributeHook {
    @Override
    public void afterRespawn(LocalPlayer replacementPlayer) {
        AttributeInstance movementSpeed = replacementPlayer.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed == null) {
            throw new IllegalStateException("Replacement player has no movement-speed attribute");
        }
        movementSpeed.setBaseValue(0.1F);
        movementSpeed.removeModifiers();
    }
}
