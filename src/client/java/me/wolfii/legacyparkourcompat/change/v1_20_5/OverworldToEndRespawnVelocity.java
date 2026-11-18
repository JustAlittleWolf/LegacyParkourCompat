package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.RespawnVelocityHook;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Ornithe Feather 1.8 ClientPlayNetworkHandler#handlePlayerRespawn and
 * Minecraft#respawnPlayer replace and zero the local player. Mojmap 1.20.6
 * ClientPacketListener#handleRespawn still zeros velocity via LocalPlayer#resetPos.
 * 26.2 unobfuscated ClientPacketListener#handleRespawn instead copies the old
 * velocity under KEEP_ENTITY_DATA. Restore zero for the Overworld-to-End
 * dimension pair observed at that copy. The keys identify the dimensions only.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class OverworldToEndRespawnVelocity implements RespawnVelocityHook {
    @Override
    public Vec3 replacementVelocity(ResourceKey<Level> oldDimension, ResourceKey<Level> newDimension, Vec3 vanillaVelocity) {
        return Level.OVERWORLD.equals(oldDimension) && Level.END.equals(newDimension) ? Vec3.ZERO : vanillaVelocity;
    }
}
