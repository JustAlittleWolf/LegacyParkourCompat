package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.RespawnVelocityHook;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * In 1.21 and 1.21.1 Mojmap, ClientPacketListener#handleRespawn copies entity data,
 * then calls LocalPlayer#resetPos, which sets the replacement player's velocity to zero.
 * In 1.21.2 Mojmap and 26.2 unobfuscated, ClientPacketListener#handleRespawn instead
 * copies the old player's velocity in the keep-entity-data branch.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class RespawnVelocity implements RespawnVelocityHook {
    @Override
    public Vec3 replacementVelocity(ResourceKey<Level> oldDimension, ResourceKey<Level> newDimension, Vec3 vanillaVelocity) {
        return Vec3.ZERO;
    }
}
