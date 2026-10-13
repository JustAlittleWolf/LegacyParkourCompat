package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidInteractionFluidHook;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.21.11/mojmap/net/minecraft/world/entity/Avatar.java:
 *   class declaration / field initialization.
 * 1.21.11/mojmap/net/minecraft/world/entity/Entity.java:
 *   baseTick.
 *   positionRider.
 *   updateInWaterStateAndDoFluidPushing.
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
 * 1.8.9/ornithe-feather/net/minecraft/entity/Entity.java:
 *   public boolean checkWaterCollisions().
 *   public boolean checkWaterCollisions().
 * 1.9.4/ornithe-feather/net/minecraft/entity/Entity.java:
 *   public boolean checkWaterCollisions().
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
 * 26.1.2/unobfuscated/net/minecraft/world/entity/vehicle/boat/Boat.java:
 *   rideHeight.
 * 26.1.2/unobfuscated/net/minecraft/world/level/material/FlowingFluid.java:
 *   getFlow.
 *   ACCEPT bounded fixed dry-boat player LAVA witness; exact standalone binding may remain open.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-WORLD-05-water-state-while-riding-boat is unknown within (1.8.9, 1.9.4]
 *   Exact independent source verdict/snapshot identity for WORLD-05-water-state-while-riding-boat
 *   Exact independent source verdict/snapshot identity for WORLD-09-boat-water-travel-branch
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class MountedBoatWaterInteraction implements FluidInteractionFluidHook {
    @Override
    public FluidState fluidState(Entity entity, FluidState vanilla) {
        if (entity instanceof Player player
            && player.getVehicle() instanceof AbstractBoat boat && !boat.isUnderWater()
            && vanilla.is(FluidTags.WATER)) {
            return Fluids.EMPTY.defaultFluidState();
        }
        return vanilla;
    }
}
