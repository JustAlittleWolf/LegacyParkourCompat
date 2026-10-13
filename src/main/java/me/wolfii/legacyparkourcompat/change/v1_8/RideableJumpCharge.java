package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.RideableJumpHook;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick() {; lines 488-637.
 *   Source SHA-256: 1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053.
 * 1.8.9/ornithe-feather/net/minecraft/entity/living/mob/passive/animal/HorseBaseEntity.java:
 *   public void setJumpStrength(int strength) {; lines 1316-1331.
 *   Source SHA-256: 83b1f42af08544280f5e0f381c116986c521b35e5dc80c54432bdde283305431.
 * 1.8.9/ornithe-feather/net/minecraft/util/math/MathHelper.java:
 *   public static int floor(float x) {; lines 30-33.
 *   public static int floor(double x) {; lines 39-42.
 *   Source SHA-256: de0aa811e3ef80cfce9ec3cf8752627b8d2de5261c7e567b34ac1d611d167366.
 * 1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick() {; lines 604-760.
 *   Source SHA-256: 8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d.
 * 1.9.4/ornithe-feather/net/minecraft/entity/living/mob/passive/animal/HorseBaseEntity.java:
 *   public void setJumpStrength(int strength) {; lines 1289-1304.
 *   Source SHA-256: dae2a372adb29488c8cecff6a86e49c8c87ced7fdb21cc6e3a6bc49a1093b31c.
 * 1.9.4/ornithe-feather/net/minecraft/util/math/MathHelper.java:
 *   public static int floor(float x) {; lines 31-34.
 *   public static int floor(double x) {; lines 40-43.
 *   Source SHA-256: 43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class RideableJumpCharge implements RideableJumpHook {
    @Override
    public void onPlayerJump(Player player, PlayerRideableJumping mount, int strength, VanillaCall vanilla) {
        // 1.8 sends the charge packet without applying the charge to the local mount.
        if (!(mount instanceof net.minecraft.world.entity.Entity entity)) {
            throw new IllegalStateException("Rideable jumping mount is not an entity");
        }
        if (!me.wolfii.legacyparkourcompat.change.common.HistoricalRideables.contains(entity, ParkourVersion.V1_8)) {
            vanilla.run();
        }
    }
}
