package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.InsideBlockContactHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.18.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   checkInsideBlocks; lines 872-886.
 *   makeStuckInBlock; lines 2149-2152.
 *   move; lines 543-674.
 *   Source SHA-256: 2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a.
 * 1.18.2/mojmap/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization; lines 294-296.
 *   Source SHA-256: cc6b87d2c5897e71e5244b889444ac040e3fa0a139e392523e87fec99805a0f2.
 * 1.18.2/mojmap/net/minecraft/world/level/block/WebBlock.java:
 *   entityInside; lines 16.
 *   Source SHA-256: eb8f4433705367aa7ce23c5a6071955b4d67728e299a2993285c6405ce638e49.
 * 1.19.2/mojmap/net/minecraft/server/commands/TeleportCommand.java:
 *   teleportToPos; lines 188-229.
 *   Source SHA-256: 3abf6877a502398a71ce00be3f4f0149e0c9d5c07095b89336580bd6995e6c6a.
 * 1.19.2/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java:
 *   teleport; lines 994-1010.
 *   Source SHA-256: 976992cd0cf4ce387e12543861aab66cc7cf8a58f078f00e0fe8f91df47b4cd2.
 * 1.19.2/mojmap/net/minecraft/util/Mth.java:
 *   floor; lines 65-68.
 *   Source SHA-256: 61727897223a78846a5f1fa270d57aa714c21b3c8d6f2b0e12fea4a07e6c327f.
 * 1.19.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   move; lines 657.
 *   tryCheckInsideBlocks; lines 685-694.
 *   checkInsideBlocks; lines 886-912.
 *   class declaration / field initialization; lines 190-214.
 *   Source SHA-256: 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6.
 * 1.19.2/mojmap/net/minecraft/world/entity/EntityDimensions.java:
 *   makeBoundingBox; lines 21-25.
 *   Source SHA-256: ced817743f3bd35462c94c0112adf6f54eabf6132b0a349b05160e519bad3502.
 * 1.19.2/mojmap/net/minecraft/world/entity/EntityType.java:
 *   class declaration / field initialization; lines 527.
 *   Source SHA-256: 4d2fb12f5458e43fcf94ec8f40058f0ef5ba545049fd5a73c0cf755cb4764836.
 * 1.19.2/mojmap/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization; lines 3369-3378.
 *   Source SHA-256: f532585f836ee7ad685d368889ed13d735c570ef38971db0c1d2a7debb4cf27b.
 * 1.19.2/mojmap/net/minecraft/world/level/block/HoneyBlock.java:
 *   entityInside; lines 56-64.
 *   Source SHA-256: 5ffc5f58a81f82823305c7ed02f9a465c7b3bfd2c1799da566e1485d0cd99ad5.
 * 1.19.3/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java:
 *   teleport; lines 988-1004.
 *   Source SHA-256: 81288e1e99d88796e096c1386e8a1f108a077078467d4843a56d14f7cb48006b.
 * 1.19.3/mojmap/net/minecraft/world/entity/Entity.java:
 *   move; lines 657.
 *   tryCheckInsideBlocks; lines 685-694.
 *   class declaration / field initialization; lines 190-214.
 *   Source SHA-256: 8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32.
 * 1.19.3/mojmap/net/minecraft/world/entity/EntityDimensions.java:
 *   makeBoundingBox; lines 21-25.
 *   Source SHA-256: ced817743f3bd35462c94c0112adf6f54eabf6132b0a349b05160e519bad3502.
 * 1.19.3/mojmap/net/minecraft/world/entity/EntityType.java:
 *   class declaration / field initialization; lines 527.
 *   Source SHA-256: b63be8727a2fa406bef2ae45c1c9ebe06a56440e01b6dc85c443cbb738c32c06.
 * 1.19.3/mojmap/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization; lines 3369-3378.
 *   Source SHA-256: 9944877c941fa209d2a8b9a7d4eb6333edf2dfca255a3e90601242483eb7a476.
 * 1.19.3/mojmap/net/minecraft/world/level/block/HoneyBlock.java:
 *   entityInside; lines 56-64.
 *   Source SHA-256: 5ffc5f58a81f82823305c7ed02f9a465c7b3bfd2c1799da566e1485d0cd99ad5.
 * 1.19.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   checkInsideBlocks; lines 896-910.
 *   move; lines 549-681.
 *   Source SHA-256: 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b.
 * 1.19.4/mojmap/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization; lines 294-296.
 *   Source SHA-256: a4f2df87c2cae5a3827a818f39f7ad123ee5b18905ead9947efe654421c30ff5.
 * 1.19.4/mojmap/net/minecraft/world/level/block/WebBlock.java:
 *   entityInside; lines 16.
 *   Source SHA-256: eb8f4433705367aa7ce23c5a6071955b4d67728e299a2993285c6405ce638e49.
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
 *   Source SHA-256: a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9.
 * 1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines 2690-2779.
 *   Source SHA-256: 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52.
 * 1.21.3/mojmap/net/minecraft/world/level/BlockGetter.java:
 *   boxTraverseBlocks; lines 172-190.
 *   Source SHA-256: 5d6fde6fef7928603ff97f4a4015efbb48442f9e583f1d0cfe368c5168e01c39.
 * 1.21.3/mojmap/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization; lines 701-706.
 *   Source SHA-256: 28a66da36ce98800226ec6d8e5fb4904fc7b137322a31f6d7ec822450374e62a.
 * 1.21.3/mojmap/net/minecraft/world/level/block/WebBlock.java:
 *   entityInside; lines 26-33.
 *   Source SHA-256: ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414.
 * 1.21.3/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java:
 *   getEntityInsideCollisionShape; lines 353-355.
 *   Source SHA-256: 4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133.
 * 1.21.3/mojmap/net/minecraft/world/phys/shapes/Shapes.java:
 *   block; lines 42-44.
 *   Source SHA-256: ab6c69c27c07c3cfa82e85906888036c34a46188e1bde5663e980dea3f6c2a75.
 * Bounded source decisions:
 *   Review dded99fb6d78d4d253ab7f367edc029e981b6b91; ACCEPT, bounded stable/command witness.
 *   Reviewed snapshot b66cde90c5f34970aa94553f838ec8c65e764ea6.
 * Remaining source/applicability gates:
 *   Current V1_19 group cannot distinguish accepted1.19.2/1.19.3 behavior; earlier aliases and effects of new keys on each existing operation require a corrected decision table.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class InsideBlockContact implements InsideBlockContactHook {
    @Override
    public double inset(Player player, double vanilla) {
        return 0.001;
    }
}
