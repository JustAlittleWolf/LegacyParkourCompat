package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidInteractionBoxHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.21.11/mojmap/net/minecraft/world/entity/Avatar.java:
 *   class declaration / field initialization.
 * 1.21.11/mojmap/net/minecraft/world/entity/Entity.java:
 *   baseTick.
 *   positionRider.
 *   updateInWaterStateAndDoFluidPushing.
 *   updateInWaterStateAndDoWaterCurrentPushing.
 *   updateFluidHeightAndDoFluidPushing.
 * 1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep.
 * 1.21.11/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isPushedByFluid.
 *   isAffectedByFluids.
 * 1.21.11/mojmap/net/minecraft/world/entity/vehicle/boat/AbstractBoat.java:
 *   interact.
 * 1.21.11/mojmap/net/minecraft/world/entity/vehicle/boat/Boat.java:
 *   rideHeight.
 * 1.21.11/mojmap/net/minecraft/world/level/material/FlowingFluid.java:
 *   getFlow.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/Avatar.java:
 *   class declaration / field initialization.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java:
 *   baseTick.
 *   getFluidInteractionBox.
 *   updateFluidInteraction.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/EntityDimensions.java:
 *   makeBoundingBox.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/EntityFluidInteraction.java:
 *   update.
 *   applyCurrentTo.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java:
 *   isPushedByFluid.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/vehicle/boat/AbstractBoat.java:
 *   modifyPassengerFluidInteractionBox.
 * 26.2/unobfuscated/net/minecraft/world/entity/Entity.java:
 *   getFluidInteractionBox.
 *   updateFluidInteraction.
 * 26.2/unobfuscated/net/minecraft/world/entity/EntityFluidInteraction.java:
 *   update.
 * 26.2/unobfuscated/net/minecraft/world/entity/vehicle/boat/AbstractBoat.java:
 *   modifyPassengerFluidInteractionBox.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/vehicle/boat/Boat.java:
 *   rideHeight.
 * 26.1.2/unobfuscated/net/minecraft/world/level/material/FlowingFluid.java:
 *   getFlow.
 *   ACCEPT bounded fixed dry-boat player LAVA witness; exact standalone binding may remain open.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class MountedFluidInteractionBox implements FluidInteractionBoxHook {
    /**
     * 1.21.11 scans lava with the deflated entity box even when a non-underwater
     * boat carries the player. In 26.2, AbstractBoat can make the native box null
     * when the boat covers that box, so restore the historical body scan box.
     * The paired fluid-state change preserves the old water exclusion; this hook
     * describes body sampling and does not establish historical eye-fluid parity.
     */
    @Override
    public @Nullable AABB interactionBox(Entity entity, @Nullable AABB vanilla) {
        if (entity instanceof Player player
            && player.getVehicle() instanceof AbstractBoat boat && !boat.isUnderWater()) {
            return player.getBoundingBox().deflate(0.001);
        }
        return vanilla;
    }
}
