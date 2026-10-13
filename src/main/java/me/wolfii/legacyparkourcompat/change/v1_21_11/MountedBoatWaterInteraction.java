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
 *   class declaration / field initialization; lines 16-26.
 *   Source SHA-256: a3f54b9ff81ee203f77efcf5dcab50d69ec9a3d2c5430ce4c4b2ae0abb4bfca9.
 * 1.21.11/mojmap/net/minecraft/world/entity/Entity.java:
 *   baseTick; lines 501-507.
 *   positionRider; lines 2262-2282.
 *   updateInWaterStateAndDoFluidPushing; lines 1501-1522.
 *   updateFluidHeightAndDoFluidPushing; lines 3509-3582.
 *   Source SHA-256: 32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3.
 * 1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines 2931-2957.
 *   Source SHA-256: 19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8.
 * 1.21.11/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isPushedByFluid; lines 1679-1681.
 *   isAffectedByFluids; lines 884-886.
 *   Source SHA-256: 8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81.
 * 1.21.11/mojmap/net/minecraft/world/entity/vehicle/boat/AbstractBoat.java:
 *   interact; lines 694-703.
 *   Source SHA-256: e7d22b0d7a7c204eea153d1dcac7f74b4e4738fe33bdd613aff9ebcc924aa3cf.
 * 1.21.11/mojmap/net/minecraft/world/entity/vehicle/boat/Boat.java:
 *   rideHeight; lines 14-17.
 *   Source SHA-256: 476fb64c69c742495f3043608508f20499db109ab0a3a6cafe02c8e503e1af3b.
 * 1.21.11/mojmap/net/minecraft/world/level/material/FlowingFluid.java:
 *   getFlow; lines 55-100.
 *   Source SHA-256: 905e5a02ab10b23b5f6e64a0f732ee552ae8895801e1944d9850a41cc8bce91a.
 * 1.8.9/ornithe-feather/net/minecraft/entity/Entity.java:
 *   public boolean checkWaterCollisions() {; lines 746-760.
 *   public boolean checkWaterCollisions() {; lines 746-760.
 *   Source SHA-256: d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b.
 * 1.9.4/ornithe-feather/net/minecraft/entity/Entity.java:
 *   public boolean checkWaterCollisions() {; lines 849-865.
 *   Source SHA-256: bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/Avatar.java:
 *   class declaration / field initialization; lines 17-27.
 *   Source SHA-256: 01a8092bc2637e7d42a261fb564bffcabadd28625cc02b120e943559e2d4fe78.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java:
 *   baseTick; lines 522-528.
 *   getFluidInteractionBox; lines 4015-4026.
 *   updateFluidInteraction; lines 1558-1583.
 *   Source SHA-256: 8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/EntityDimensions.java:
 *   makeBoundingBox; lines 15-22.
 *   Source SHA-256: 27fa1fb5c3e9bf49123a238675f6bea29e65ad5b0362a9a64dcb5d5f5407376a.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/EntityFluidInteraction.java:
 *   update; lines 32-101.
 *   applyCurrentTo; lines 171-189.
 *   Source SHA-256: 5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java:
 *   isPushedByFluid; lines 1673-1675.
 *   Source SHA-256: 44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/vehicle/boat/AbstractBoat.java:
 *   modifyPassengerFluidInteractionBox; lines 783-795.
 *   Source SHA-256: 8d681d6ac6e93a648640fd39cde921cc8ed9d4aec03a49a9e9e43b28cb7be840.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/vehicle/boat/Boat.java:
 *   rideHeight; lines 14-17.
 *   Source SHA-256: b4ad2291573e1e352fc24ab0b337d252c67cf6158ca3d49acf97d75a94cf2840.
 * 26.1.2/unobfuscated/net/minecraft/world/level/material/FlowingFluid.java:
 *   getFlow; lines 55-103.
 *   Source SHA-256: f8c0c4430e81efdf39b221968272ed8e3a4d030fa5192e04e04d34c9fbd37b28.
 * Bounded source decisions:
 *   ACCEPT bounded fixed dry-boat player LAVA witness; exact standalone binding may remain open.
 *   Reviewed snapshot 019d5fcef181f6dde2a9630e0b882acfa99d6de9; SHA-256 a49bfdd5e363f9faae5b99fe29cfefe393a958019460063fbb175ef2b053e034.
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
