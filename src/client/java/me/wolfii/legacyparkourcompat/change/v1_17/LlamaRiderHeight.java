package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.llama.LlamaPassengerPlacement;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.TraderLlama;

import java.util.Optional;

/**
 * Ornithe Feather 1.11 and 1.11.1
 * {@code net.minecraft.entity.living.mob.passive.animal.LlamaEntity.updateRiderPositon(Entity)}
 * write {@code y + getMountHeight() + getRideHeight()};
 * {@code LlamaEntity.getMountHeight()} returns {@code height * 0.67} and
 * {@code PlayerEntity.getRideHeight()} returns {@code -0.35}. Accepted adjacent Feather aliases
 * through 1.13.2→1.14 retain that direct-player Y operation. Yarn 1.14
 * {@code net.minecraft.entity.passive.LlamaEntity.updatePassengerPosition(Entity)} uses the same
 * left-associative sum with {@code LlamaEntity.getMountedHeightOffset()} and
 * {@code PlayerEntity.getHeightOffset()}; that exact era first includes
 * {@code TraderLlamaEntity}. Mojmap 1.14.4 and 1.15
 * {@code net.minecraft.world.entity.animal.horse.Llama.positionRider(Entity)} use
 * {@code Llama.getRideHeight()} and {@code Player.getRidingHeight()}; Mojmap 1.16–1.16.5 use
 * {@code getPassengersRidingOffset()} and {@code Player.getMyRidingOffset()}. The accepted
 * 1.16.5→1.17 and 1.17→1.17.1 pairs continue the factor 0.67. The closest-applicable resolver
 * selects this V1_17 provider for V1_11 through V1_17 when no same-hook provider intervenes;
 * V1_10_1 preserves native placement earlier, and V1_17_1 has its existing provider. Mojmap 1.18
 * changes the factor to 0.6. Trader Llama placement uses the historical provider only from V1_14,
 * its first exact source era; before that, the current TraderLlama entity falls back to native.
 */
@MovementChange(emulates = ParkourVersion.V1_17)
public final class LlamaRiderHeight implements PlayerPassengerPlacementHook {
    @Override
    public Optional<Placement> placement(LocalPlayer passenger, Entity vehicle) {
        if (vehicle instanceof TraderLlama
            && MovementRuntime.profile(passenger).target().olderThan(ParkourVersion.V1_14)) {
            return Optional.empty();
        }
        return LlamaPassengerPlacement.defaultAdult(passenger, vehicle, 0.67);
    }
}
