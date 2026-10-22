package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.GroundedBlockStepCallbackHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Moves the 1.21.1 grounded Block.stepOn callback to its original point after
 * ground/fall state and before movement-emission and inside-block callbacks.
 * Mojmap 1.21.1 Entity.move calls Block.stepOn after checkFallDamage when on
 * ground; 26.2 unobfuscated Entity.applyEffectsFromBlocks calls it after travel.
 * The mixin supplies getOnPosLegacy, the same position used by 1.21.1 Entity.move.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class ImmediateGroundedBlockStepCallback implements GroundedBlockStepCallbackHook {
    @Override
    public void afterGroundState(Entity entity, BlockPos pos, BlockState state) {
        if (this.applies(entity) && entity.onGround()) {
            state.getBlock().stepOn(entity.level(), pos, state, entity);
        }
    }

    @Override
    public boolean suppressPostTravel(Entity entity) {
        return this.applies(entity);
    }

    private boolean applies(Entity entity) {
        return entity instanceof LocalPlayer && entity.level().isClientSide() && !entity.isRemoved() && !entity.noPhysics;
    }
}
