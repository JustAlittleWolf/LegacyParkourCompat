package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.world.WaterFlowScanBoundHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.OptionalInt;

/**
 * Ornithe Feather 1.8.9 World.applyLiquidDrag uses floor(max + 1.0) for each
 * exclusive upper bound. Ornithe Feather 1.9–1.9.4 World.applyLiquidDrag uses
 * ceil(max). Ornithe Feather 1.10 through 1.11.2 World.applyLiquidDrag retains
 * ceil(max), and Entity.checkWaterCollisions still supplies the symmetrically
 * narrowed and inset box. Apply the ceil bound to that historical current query
 * for the supported unmounted local player. The body-contact result and native
 * fluid tracker are resolved independently.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class WaterFlowScanBound implements WaterFlowScanBoundHook {
    @Override
    public boolean supports(Player player) {
        return player instanceof LocalPlayer && !player.isPassenger() && !player.getAbilities().flying;
    }

    @Override
    public OptionalInt exclusiveUpperBound(double boundsMax) {
        return OptionalInt.of(Mth.ceil(boundsMax));
    }
}
