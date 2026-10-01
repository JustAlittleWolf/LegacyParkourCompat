package me.wolfii.legacyparkourcompat.change.v1_18_2;

import java.util.Set;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintFallFlyingGateBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.VehicleSprintBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class SprintEligibility_1_18_2_SprintFallFlyingGate implements SprintFallFlyingGateBehavior {
    @Override
    public boolean fallFlyingForSprintGate(Player player, boolean vanilla) {
        return false;
    }
}
