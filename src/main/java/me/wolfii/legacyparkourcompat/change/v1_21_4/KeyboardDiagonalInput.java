package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.KeyboardDiagonalInputHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.20.6/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick; lines 21-34.
 *   Source SHA-256: a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0.
 * 1.20.6/mojmap/net/minecraft/world/entity/Entity.java:
 *   getInputVector; lines 1316-1325.
 *   Source SHA-256: 71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines 2671-2672.
 *   Source SHA-256: c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2.
 * 1.20.6/mojmap/net/minecraft/world/phys/Vec3.java:
 *   normalize; lines 67-70.
 *   Source SHA-256: dc05337c94e64d5c137138ef98b7f10768a420af23bffb0812b68a8320c7c813.
 * 1.21.11/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick; lines 23-36.
 *   Source SHA-256: d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3.
 * 1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   modifyInput; lines 668-705.
 *   Source SHA-256: 948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477.
 * 1.21.11/mojmap/net/minecraft/util/Mth.java:
 *   class declaration / field initialization; lines 26-38.
 *   Source SHA-256: 1b547c6b896c9b149d4bc03e3ed106ece485d7ac481a792ae30f600d0239726c.
 * 1.21.11/mojmap/net/minecraft/world/entity/Entity.java:
 *   getInputVector; lines 1613-1622.
 *   Source SHA-256: 32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3.
 * 1.21.11/mojmap/net/minecraft/world/phys/Vec2.java:
 *   normalized; lines 48-51.
 *   Source SHA-256: c30f834079433af143151a684cf49d86de4c1102397221fe006031576046d0ff.
 * 1.21.4/mojmap/net/minecraft/client/player/ClientInput.java:
 *   class declaration / field initialization; lines 5-13.
 *   Source SHA-256: 0f2e4d03749ab7495e386af05aa77a0414b5d1c3ef76256db2b514f42cc6e3c4.
 * 1.21.4/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick; lines 20-31.
 *   Source SHA-256: c885bcc709e445c3ff91f03bf37acf9320e17fc9daf1bd2ba46ba20487f1d9ed.
 * 1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   serverAiStep; lines 607-617.
 *   Source SHA-256: 145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611.
 * 1.21.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   getInputVector; lines 1429-1441.
 *   Source SHA-256: 05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad.
 * 1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines 2766-2784.
 *   Source SHA-256: e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30.
 * 1.21.5/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick; lines 22-31.
 *   Source SHA-256: d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3.
 * 1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   modifyInput; lines 623-658.
 *   applyInput; lines 607-621.
 *   Source SHA-256: f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef.
 * 1.21.5/mojmap/net/minecraft/world/entity/Entity.java:
 *   getInputVector; lines 1458-1470.
 *   Source SHA-256: fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4.
 * 1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines 2712-2717.
 *   Source SHA-256: a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271.
 * 1.21.5/mojmap/net/minecraft/world/phys/Vec2.java:
 *   normalized; lines 48-51.
 *   Source SHA-256: b637ef6899de5d8892e19273b114ffaf0397761e509170a56d52dd068f54bffe.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class KeyboardDiagonalInput implements KeyboardDiagonalInputHook {
    @Override
    public Vec2 diagonalInput(
        Player player,
        Vec2 rawImpulses,
        Vec2 vanilla,
        boolean keyboard,
        boolean diagonal,
        boolean movingSlowly,
        boolean usingItem
    ) {
        if (keyboard && diagonal && !movingSlowly && !usingItem) {
            return rawImpulses.scale(0.98F);
        }
        return vanilla;
    }
}
