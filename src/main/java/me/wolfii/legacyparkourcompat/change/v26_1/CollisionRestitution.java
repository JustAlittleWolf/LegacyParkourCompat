package me.wolfii.legacyparkourcompat.change.v26_1;

import java.util.Optional;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockBounceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockLandingHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.CollisionRestitutionHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 26.1.2/unobfuscated/net/minecraft/client/player/AbstractClientPlayer.java:
 *   class declaration / field initialization; lines 19.
 *   Source SHA-256: 7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc.
 * 26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java:
 *   isShiftKeyDown; lines 678-680.
 *   aiStep; lines 914.
 *   class declaration / field initialization; lines 108.
 *   aiStep; lines 914.
 *   Source SHA-256: 433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe.
 * 26.1.2/unobfuscated/net/minecraft/util/Mth.java:
 *   lerp; lines 540-542.
 *   Source SHA-256: 19d00e9745368c34692d0dfd7eddec07797790ac7f6e0bc579208b7a4b682d33.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/Avatar.java:
 *   class declaration / field initialization; lines 13.
 *   Source SHA-256: 01a8092bc2637e7d42a261fb564bffcabadd28625cc02b120e943559e2d4fe78.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java:
 *   move; lines 781-785.
 *   isSuppressingBounce; lines 2630-2632.
 *   collideWithShapes; lines 1161-1177.
 *   move; lines 757-785.
 *   Source SHA-256: 8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines 3073.
 *   travelInAir; lines 2430-2453.
 *   handleRelativeFrictionAndCalculateMovement; lines 2623-2638.
 *   shouldDiscardFriction; lines 653-655.
 *   aiStep; lines 3073.
 *   Source SHA-256: c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java:
 *   canSimulateMovement; lines 1269-1271.
 *   travel; lines 1381-1403.
 *   travel; lines 1381-1404.
 *   class declaration / field initialization; lines 125.
 *   canSimulateMovement; lines 1269-1271.
 *   Source SHA-256: 44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d.
 * 26.1.2/unobfuscated/net/minecraft/world/level/block/BedBlock.java:
 *   updateEntityMovementAfterFallOn; lines 138-151.
 *   class declaration / field initialization; lines 57-62.
 *   updateEntityMovementAfterFallOn; lines 138-150.
 *   Source SHA-256: 996e4ca63f4bfaea14b215648f2318d52c49f6c802317381a670d87c0a6d0b03.
 * 26.1.2/unobfuscated/net/minecraft/world/level/block/Block.java:
 *   updateEntityMovementAfterFallOn; lines 498-500.
 *   updateEntityMovementAfterFallOn; lines 498-500.
 *   Source SHA-256: 1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977.
 * 26.1.2/unobfuscated/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization; lines 674-689.
 *   registerBed; lines 7069-7078.
 *   class declaration / field initialization; lines 3221-3223.
 *   Source SHA-256: ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f.
 * 26.1.2/unobfuscated/net/minecraft/world/level/block/SlimeBlock.java:
 *   updateEntityMovementAfterFallOn; lines 32-46.
 *   updateEntityMovementAfterFallOn; lines 33-45.
 *   Source SHA-256: 84d22cf526d6bf1b4ec4b0b642a76fc2c90380a71f05ab92a7de9935f0d1c38e.
 * 26.1.2/unobfuscated/net/minecraft/world/phys/shapes/Shapes.java:
 *   collide; lines 233.
 *   Source SHA-256: cfa63cbed77c922a68e7e8a6a3b78b09a22c7de51e0146e481e9c9d26db66f58.
 * 26.2/unobfuscated/net/minecraft/client/player/AbstractClientPlayer.java:
 *   class declaration / field initialization; lines 19.
 *   Source SHA-256: 7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc.
 * 26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java:
 *   isShiftKeyDown; lines 678-680.
 *   aiStep; lines 914.
 *   class declaration / field initialization; lines 108.
 *   aiStep; lines 914.
 *   Source SHA-256: 8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6.
 * 26.2/unobfuscated/net/minecraft/tags/BlockTags.java:
 *   class declaration / field initialization; lines 243.
 *   Source SHA-256: 9834ecbe2facd79cf3d9fc79babd452ed0e000684029b2bb5e9f90aa4f51529e.
 * 26.2/unobfuscated/net/minecraft/util/Mth.java:
 *   lerp; lines 558-560.
 *   Source SHA-256: 30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17.
 * 26.2/unobfuscated/net/minecraft/world/entity/Avatar.java:
 *   class declaration / field initialization; lines 13.
 *   Source SHA-256: 01a8092bc2637e7d42a261fb564bffcabadd28625cc02b120e943559e2d4fe78.
 * 26.2/unobfuscated/net/minecraft/world/entity/Entity.java:
 *   move; lines 784-786.
 *   restituteMovementAfterCollisions; lines 802-843.
 *   isSuppressingBounce; lines 2697-2699.
 *   collideWithShapes; lines 1244-1259.
 *   move; lines 764-785.
 *   Source SHA-256: 7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5.
 * 26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java:
 *   createLivingAttributes; lines 332-359.
 *   getEntityBounciness; lines 2202-2204.
 *   aiStep; lines 3073.
 *   travel; lines 2430-2453.
 *   shouldDiscardFriction; lines 676-678.
 *   getEntityBounciness; lines 2202-2204.
 *   aiStep; lines 3073.
 *   Source SHA-256: 7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a.
 * 26.2/unobfuscated/net/minecraft/world/entity/ai/attributes/Attributes.java:
 *   class declaration / field initialization; lines 31.
 *   class declaration / field initialization; lines 31.
 *   Source SHA-256: 4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c.
 * 26.2/unobfuscated/net/minecraft/world/entity/player/Player.java:
 *   canSimulateMovement; lines 1290-1292.
 *   travel; lines 1402-1425.
 *   class declaration / field initialization; lines 126.
 *   canSimulateMovement; lines 1290-1292.
 *   Source SHA-256: 8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531.
 * 26.2/unobfuscated/net/minecraft/world/level/block/BedBlock.java:
 *   class declaration / field initialization; lines 55-60.
 *   Source SHA-256: 22f515c272d52eebd75456e4a78eb7f38708d682f8d2cb5fdc170e7c3105af2b.
 * 26.2/unobfuscated/net/minecraft/world/level/block/Block.java:
 *   getBounceRestitution; lines 494-496.
 *   Source SHA-256: cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab.
 * 26.2/unobfuscated/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization; lines 696-706.
 *   class declaration / field initialization; lines 2976-2980.
 *   class declaration / field initialization; lines 696-704.
 *   Source SHA-256: f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435.
 * 26.2/unobfuscated/net/minecraft/world/phys/shapes/Shapes.java:
 *   collide; lines 234.
 *   Source SHA-256: 6ed9460d58727770bdec59c4f3184f075644f3c411be1eeab2eae17e8ece1891.
 * Bounded source decisions:
 *   ACCEPT bounded fresh-entity first-travel witness; exact standalone binding may remain open.
 *   Reviewed snapshot d90a4c13d7ac63984a758aa3f3a6ce320cecac69; SHA-256 c776cc145aa9e6373d83b24c209fb1c025d914dc63c64041b4aaf7d515fc227d.
 *   Review d6664c08d015b6d301b5498302d9f64f32ebec92; ACCEPT, bounded source finding only.
 *   Reviewed snapshot 8eb345d5973158e7e1bc26a2044d63fb77b45e19; SHA-256 c14302ffa5b0a773d1e27adbb7f8fbe12188066c80763bdac4a8a02ef0446a05.
 * Remaining source/applicability gates:
 *   Archived-to-canonical source identity for these checked secondary citations: 26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java, 26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java, 26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java. Neither a matching archived full-file hash nor an archived matching canonical source-manifest digest was resolved for them.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V26_1)
public final class CollisionRestitution implements CollisionRestitutionHook {
    @Override
    public void restituteAfterCollisions(
        Entity entity,
        BlockState effectState,
        boolean xCollision,
        boolean zCollision,
        boolean yCollision,
        Vec3 movement,
        Optional<BlockLandingHook> blockLanding,
        Optional<BlockBounceHook> bounce,
        VanillaCall vanilla
    ) {
        Vec3 velocity = entity.getDeltaMovement();
        double x = xCollision ? 0.0 : velocity.x;
        double z = zCollision ? 0.0 : velocity.z;
        entity.setDeltaMovement(x, velocity.y, z);

        if (yCollision) {
            VanillaCall landing = () -> entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0, 0.0, 1.0));
            blockLanding.ifPresentOrElse(
                behavior -> behavior.updateMovementAfterFallOn(entity.level(), entity, bounce, landing),
                landing::run
            );
        }
    }
}
