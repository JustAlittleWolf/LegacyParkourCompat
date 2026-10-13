package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.ShallowWaterCurrentCutoffHook;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.21.11/mojmap/net/minecraft/world/entity/Entity.java:
 *   updateInWaterStateAndDoWaterCurrentPushing; lines 1509-1520.
 *   updateFluidHeightAndDoFluidPushing; lines 3509-3582.
 *   Source SHA-256: 32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3.
 * 1.21.11/mojmap/net/minecraft/world/entity/EntityType.java:
 *   class declaration / field initialization; lines 1196.
 *   Source SHA-256: fc2abb3e905b9a63a27f0b027fad089d9d40e5d3d45608406b5da2a5ec47a73e.
 * 1.21.11/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isPushedByFluid; lines 1679-1681.
 *   Source SHA-256: 8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81.
 * 1.21.11/mojmap/net/minecraft/world/level/material/FlowingFluid.java:
 *   getFlow; lines 55-100.
 *   getFlow; lines 55-103.
 *   getNewLiquid; lines 160-195.
 *   Source SHA-256: 905e5a02ab10b23b5f6e64a0f732ee552ae8895801e1944d9850a41cc8bce91a.
 * 1.21.11/mojmap/net/minecraft/world/level/material/FluidState.java:
 *   getFlow; lines 95-97.
 *   Source SHA-256: 146a5e6a19a93da631a6f4ed57020791bde31087fcedc6d52c68b1c582b71b0f.
 * 1.21.11/mojmap/net/minecraft/world/level/material/WaterFluid.java:
 *   getDropOff; lines 107.
 *   Source SHA-256: bcc55994c0a150a975bacd7920c161b09a6d00e3a78af209c924ad03a4b949d6.
 * 1.21.11/mojmap/net/minecraft/world/phys/Vec3.java:
 *   normalize; lines 80-86.
 *   Source SHA-256: a4050765738a0cfdb1100b83f2ba4effd40155de99bb703a8381dbe775148ec1.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java:
 *   baseTick; lines 522-528.
 *   updateFluidInteraction; lines 1558-1583.
 *   getFluidInteractionBox; lines 4015-4025.
 *   Source SHA-256: 8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/EntityFluidInteraction.java:
 *   update; lines 32-89.
 *   applyCurrentTo; lines 171-189.
 *   Source SHA-256: 5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/EntityType.java:
 *   class declaration / field initialization; lines 1196.
 *   Source SHA-256: e5f37ee2dd639b7271197e58f7a6f3bdbedc0f0f1d618039d159a0e7de315229.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java:
 *   isPushedByFluid; lines 1673-1675.
 *   Source SHA-256: 44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d.
 * 26.1.2/unobfuscated/net/minecraft/world/level/material/FlowingFluid.java:
 *   getNewLiquid; lines 160-195.
 *   Source SHA-256: f8c0c4430e81efdf39b221968272ed8e3a4d030fa5192e04e04d34c9fbd37b28.
 * Bounded source decisions:
 *   Review f222615162499441f959899600744813b1276614; ACCEPT, finding snapshot only.
 *   Reviewed snapshot 6374ff904018e102afd8353864d3cbcbb1b99ddd; SHA-256 9d2c273a1c3b358eac7ebaa1e4cbe9e91a126648a90e89d8d6bdeb1953159cdd.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class ShallowWaterCurrentCutoff implements ShallowWaterCurrentCutoffHook {
    @Override
    public boolean bypassWeakCurrentCutoff(double waterHeight) {
        return waterHeight > 0.0 && waterHeight < 0.4;
    }
}
