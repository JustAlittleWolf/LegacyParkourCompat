package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.HistoricalRideables;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.BoatSideAccelerationHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.BoatSneakAccelerationHook;
import net.minecraft.world.entity.Entity;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/Input.java:
 *   public void tick() {; lines 9-10.
 *   Source SHA-256: aabf9cfed6156121c67c9f16002ccc02f14fa6213d5717732dcf9a1d26368b74.
 * 1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java:
 *   public void tick() {; lines 13-38.
 *   Source SHA-256: a5e5b2033322f8867cd845e4095cea7a82888f6382cbfafefc559cf9ba3a76dd.
 * 1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void serverTickAi() {; lines 470-481.
 *   public boolean isLocal() {; lines 261-263.
 *   Source SHA-256: 1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053.
 * 1.8.9/ornithe-feather/net/minecraft/entity/vehicle/BoatEntity.java:
 *   public void tick() {; lines 176-363.
 *   public void updateRiderPositon() {; lines 366-372.
 *   Source SHA-256: 77ef72c28024b754ff76f2c67e14dd61832394bac2bd7b62b29708560e588da2.
 * 1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/Input.java:
 *   public void tick() {; lines 13-14.
 *   Source SHA-256: 206dea2ff5977599596c907c54f36300c4e72d07d6778d82e8850f57bb0fb009.
 * 1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java:
 *   public void tick() {; lines 13-50.
 *   Source SHA-256: 7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a.
 * 1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void serverTickAi() {; lines 586-597.
 *   public void rideTick() {; lines 763-771.
 *   public boolean isLocal() {; lines 306-308.
 *   Source SHA-256: 8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d.
 * 1.9.4/ornithe-feather/net/minecraft/entity/vehicle/BoatEntity.java:
 *   public void tick() {; lines 207-281.
 *   public void updateRiderPositon(Entity rider) {; lines 558-586.
 *   Source SHA-256: 3d41b9df0ef0d36158105235c92b35dac4b51070476e7ef688520961d1d56e57.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class BoatRiderInput implements BoatSideAccelerationHook, BoatSneakAccelerationHook {
    @Override
    public float sideOnlyAcceleration(
        Entity boat,
        ParkourVersion selected,
        float vanilla,
        boolean left,
        boolean right,
        boolean forward,
        boolean backward
    ) {
        return HistoricalRideables.contains(boat, selected) && left != right && !forward && !backward ? 0.0F : vanilla;
    }

    @Override
    public float sneakingAcceleration(Entity boat, ParkourVersion selected, float vanilla, boolean sneaking) {
        return HistoricalRideables.contains(boat, selected) && sneaking ? vanilla * 0.3F : vanilla;
    }
}
