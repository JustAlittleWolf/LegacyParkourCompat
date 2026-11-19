package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.RespawnVelocityHook;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Ornithe Feather 1.8.9 Minecraft#respawnPlayer and Mojmap 1.20.6
 * ClientPacketListener#handleRespawn replace the local player without copying
 * its velocity. In 26.2 unobfuscated, ClientPacketListener#handleRespawn copies
 * that velocity under KEEP_ENTITY_DATA.
 *
 * Restore zero at that copy for Overworld to End and both Overworld/Nether
 * directions. Mojmap 1.21.1 and unobfuscated 26.2
 * NetherPortalBlock#getPortalDestination select the opposite dimension key.
 * The 26.2 correction applies zero relative delta to the replacement's
 * velocity, with possible yaw rotation. The keys identify the dimensions only.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class DimensionTransferRespawnVelocity implements RespawnVelocityHook {
    @Override
    public Vec3 replacementVelocity(ResourceKey<Level> oldDimension, ResourceKey<Level> newDimension, Vec3 vanillaVelocity) {
        boolean overworldToEnd = Level.OVERWORLD.equals(oldDimension) && Level.END.equals(newDimension);
        boolean overworldNetherPair = (Level.OVERWORLD.equals(oldDimension) && Level.NETHER.equals(newDimension))
            || (Level.NETHER.equals(oldDimension) && Level.OVERWORLD.equals(newDimension));
        return overworldToEnd || overworldNetherPair ? Vec3.ZERO : vanillaVelocity;
    }
}
