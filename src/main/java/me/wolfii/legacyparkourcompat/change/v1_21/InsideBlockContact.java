package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.InsideBlockContactHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.21.1/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   tick; lines 191-210.
 *   Source SHA-256: c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583.
 * 1.21.1/mojmap/net/minecraft/world/entity/Entity.java:
 *   move; lines 598-727.
 *   Source SHA-256: b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850.
 * 1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines 2586-2681.
 *   Source SHA-256: 324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8.
 * 1.21.1/mojmap/net/minecraft/world/entity/player/Player.java:
 *   aiStep; lines 513-535.
 *   Source SHA-256: ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71.
 * 1.21.1/mojmap/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization; lines 765-770.
 *   Source SHA-256: 538ac50c164484ca4c2367a2a6c07cf2faedcac0c4d5117d8817d36c2d1bde6d.
 * 1.21.1/mojmap/net/minecraft/world/level/block/WebBlock.java:
 *   entityInside; lines 26-33.
 *   Source SHA-256: ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414.
 * 1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   isEffectiveAi; lines 478-480.
 *   Source SHA-256: fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0.
 * 1.21.3/mojmap/net/minecraft/world/entity/Entity.java:
 *   isControlledByLocalInstance; lines 3149-3151.
 *   applyEffectsFromBlocks; lines 736-738.
 *   applyEffectsFromBlocks; lines 736-771.
 *   applyEffectsFromBlocks; lines 740-784.
 *   setAsInsidePortal; lines 2214-2225.
 *   Source SHA-256: a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9.
 * 1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines 2690-2779.
 *   aiStep; lines 2775-2777.
 *   Source SHA-256: 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52.
 * 1.21.3/mojmap/net/minecraft/world/level/BlockGetter.java:
 *   boxTraverseBlocks; lines 172-190.
 *   Source SHA-256: 5d6fde6fef7928603ff97f4a4015efbb48442f9e583f1d0cfe368c5168e01c39.
 * 1.21.3/mojmap/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization; lines 701-706.
 *   Source SHA-256: 28a66da36ce98800226ec6d8e5fb4904fc7b137322a31f6d7ec822450374e62a.
 * 1.21.3/mojmap/net/minecraft/world/level/block/EndPortalBlock.java:
 *   class declaration / field initialization; lines 25-63.
 *   Source SHA-256: 725bbf4f36daa1f57f343d3f3123a6c527f13d7bf3c51fba1b27515b65c8c863.
 * 1.21.3/mojmap/net/minecraft/world/level/block/WebBlock.java:
 *   entityInside; lines 26-33.
 *   Source SHA-256: ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414.
 * 1.21.3/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java:
 *   getEntityInsideCollisionShape; lines 353-355.
 *   Source SHA-256: 4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133.
 * 1.21.3/mojmap/net/minecraft/world/phys/shapes/Shapes.java:
 *   block; lines 42-44.
 *   Source SHA-256: ab6c69c27c07c3cfa82e85906888036c34a46188e1bde5663e980dea3f6c2a75.
 * 1.21.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   applyEffectsFromBlocks; lines 751-797.
 *   move; lines 627-712.
 *   applyEffectsFromBlocks; lines 747-778.
 *   checkInsideBlocks; lines 1050-1090.
 *   Source SHA-256: 05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad.
 * 1.21.4/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java:
 *   getEntityInsideCollisionShape; lines 353-355.
 *   Source SHA-256: a549c5dcf7768f89f18519bdd2f985ef498d1bd4f6b329a30e0088e437819ddf.
 * 1.21.4/mojmap/net/minecraft/world/phys/shapes/Shapes.java:
 *   block; lines 42-44.
 *   Source SHA-256: ab6c69c27c07c3cfa82e85906888036c34a46188e1bde5663e980dea3f6c2a75.
 * 1.21.5/mojmap/net/minecraft/world/entity/Entity.java:
 *   move; lines 636-733.
 *   applyEffectsFromBlocks; lines 768-815.
 *   checkInsideBlocks; lines 1079-1122.
 *   Source SHA-256: fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4.
 * 1.21.5/mojmap/net/minecraft/world/level/block/HoneyBlock.java:
 *   entityInside; lines 63-71.
 *   Source SHA-256: c636983d5451a90164833f5b77e3c0933d7198f0f54f3d8b5fc46821fdf17170.
 * 1.21.5/mojmap/net/minecraft/world/level/block/PowderSnowBlock.java:
 *   entityInside; lines 62-96.
 *   Source SHA-256: de72fb6e70616d7727bf324b0da4a98250cabd97ad39ecbe1062732d47f39770.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class InsideBlockContact implements InsideBlockContactHook {
    @Override
    public double inset(Player player, double vanilla) {
        return 1.0E-7;
    }
}
