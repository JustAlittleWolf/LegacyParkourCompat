package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.ShallowLavaCurrentCutoffHook;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.21.11/mojmap/net/minecraft/data/worldgen/DimensionTypes.java:
 *   bootstrap; lines 81.
 *   Source SHA-256: 532319fc7694da6007767e3ce521e5e7ea3a7926c1e782eba132caf03cd202d3.
 * 1.21.11/mojmap/net/minecraft/world/entity/Entity.java:
 *   baseTick; lines 501-507.
 *   updateFluidHeightAndDoFluidPushing; lines 3509-3582.
 *   Source SHA-256: 32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3.
 * 1.21.11/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isPushedByFluid; lines 1679-1681.
 *   Source SHA-256: 8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81.
 * 1.21.11/mojmap/net/minecraft/world/level/material/FlowingFluid.java:
 *   getNewLiquid; lines 160-195.
 *   getFlow; lines 55-100.
 *   Source SHA-256: 905e5a02ab10b23b5f6e64a0f732ee552ae8895801e1944d9850a41cc8bce91a.
 * 1.21.11/mojmap/net/minecraft/world/level/material/FluidState.java:
 *   getHeight; lines 53-55.
 *   Source SHA-256: 146a5e6a19a93da631a6f4ed57020791bde31087fcedc6d52c68b1c582b71b0f.
 * 1.21.11/mojmap/net/minecraft/world/level/material/LavaFluid.java:
 *   getDropOff; lines 164-166.
 *   Source SHA-256: 8fabbb9a75d7620963f7a44141280bb08e0363ece7a425fcd37334e422af5abb.
 * 1.21.11/mojmap/net/minecraft/world/phys/Vec3.java:
 *   normalize; lines 80-83.
 *   normalize; lines 80-86.
 *   Source SHA-256: a4050765738a0cfdb1100b83f2ba4effd40155de99bb703a8381dbe775148ec1.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java:
 *   baseTick; lines 522-528.
 *   updateFluidInteraction; lines 1558-1583.
 *   getFluidInteractionBox; lines 4015-4025.
 *   Source SHA-256: 8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/EntityFluidInteraction.java:
 *   update; lines 32-129.
 *   applyCurrentTo; lines 171-189.
 *   Source SHA-256: 5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java:
 *   isPushedByFluid; lines 1673-1675.
 *   Source SHA-256: 44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d.
 * 26.1.2/unobfuscated/net/minecraft/world/level/material/FlowingFluid.java:
 *   getNewLiquid; lines 161-195.
 *   getFlow; lines 55-100.
 *   Source SHA-256: f8c0c4430e81efdf39b221968272ed8e3a4d030fa5192e04e04d34c9fbd37b28.
 * 26.1.2/unobfuscated/net/minecraft/world/level/material/FluidState.java:
 *   getHeight; lines 49-51.
 *   Source SHA-256: e35608d5775013c67653ea350309b5150a97f36aa65c27f1c6247ae2aa840f18.
 * 26.1.2/unobfuscated/net/minecraft/world/level/material/LavaFluid.java:
 *   getDropOff; lines 165-167.
 *   Source SHA-256: 423ff44a57c316f58c21a66903a4ef2c1b9c3d26316bdc4d301727575f93703f.
 * 26.1.2/unobfuscated/net/minecraft/world/phys/Vec3.java:
 *   normalize; lines 83-86.
 *   Source SHA-256: 57b08ae818868a4fffdc9b5dadba2b127138c8945c2194f6f4b01e6150cd3bcb.
 * Bounded source decisions:
 *   Review a76e28419f6b9767eab2e78147883c5459ed60c4; ACCEPT corrected exact-coordinate unmounted Nether LAVA witness.
 *   Reviewed snapshot b2b493a2521b036bca2fa590feeda3194c49bd4c; SHA-256 69146a6130e72d7fffdf4bfa71954c41c62dc57aebfa0246f040e079c5f28907.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class ShallowLavaCurrentCutoff implements ShallowLavaCurrentCutoffHook {
    @Override
    public boolean bypassWeakCurrentCutoff(double lavaHeight) {
        return lavaHeight > 0.0 && lavaHeight < 0.4;
    }
}
