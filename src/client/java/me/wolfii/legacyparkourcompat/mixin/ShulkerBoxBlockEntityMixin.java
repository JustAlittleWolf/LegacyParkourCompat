package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.ShulkerBoxOpeningQueryHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.shulker.ShulkerOpeningPushAmountHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ShulkerBoxBlockEntity.class)
abstract class ShulkerBoxBlockEntityMixin {
    @Shadow private float progressOld;
    @Shadow private float progress;

    @Unique private @Nullable LocalPlayer legacyparkourcompat$excludedPlayer;

    // The second progress write is the opening increment, before the clamp and neighbor updates.
    @Inject(method = "updateAnimation", at = @At(value = "FIELD",
        target = "Lnet/minecraft/world/level/block/entity/ShulkerBoxBlockEntity;progress:F",
        opcode = Opcodes.PUTFIELD, ordinal = 1, shift = At.Shift.AFTER))
    private void legacyparkourcompat$queryOpeningPlayer(Level level, BlockPos pos, BlockState tickState,
            CallbackInfo callback, @Share("openingPlayer") LocalRef<LocalPlayer> handled) {
        if (!level.isClientSide()) {
            return;
        }
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || player.level() != level) {
            return;
        }
        var query = MovementRuntime.find(ShulkerBoxOpeningQueryHook.class, player);
        if (query.isEmpty()) {
            return;
        }
        ParkourVersion target = MovementRuntime.profile(player).target();
        if (!query.get().supports(tickState.getBlock(), target)) {
            return;
        }
        handled.set(player);
        BlockState state = level.getBlockState(pos);
        if (!query.get().supports(state.getBlock(), target)) {
            return;
        }
        Direction facing = state.getValue(ShulkerBoxBlock.FACING);
        AABB lid = query.get().bounds(pos, facing, this.progressOld, this.progress);
        if (level.getEntities(null, lid).contains(player) && player.getPistonPushReaction() != PushReaction.IGNORE) {
            var amount = MovementRuntime.find(ShulkerOpeningPushAmountHook.class, player).orElseThrow();
            player.move(MoverType.SHULKER_BOX, amount.pushAmount(lid, facing, player));
        }
    }

    @WrapOperation(method = "updateAnimation", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/level/block/entity/ShulkerBoxBlockEntity;moveCollidedEntities(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V"))
    private void legacyparkourcompat$moveRemainingEntities(ShulkerBoxBlockEntity box, Level level, BlockPos pos,
            BlockState state, Operation<Void> original, @Share("openingPlayer") LocalRef<LocalPlayer> handled) {
        LocalPlayer previous = this.legacyparkourcompat$excludedPlayer;
        this.legacyparkourcompat$excludedPlayer = handled.get();
        try {
            original.call(box, level, pos, state);
        } finally {
            this.legacyparkourcompat$excludedPlayer = previous;
        }
    }

    @WrapOperation(method = "moveCollidedEntities", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"))
    private List<Entity> legacyparkourcompat$excludeHandledPlayer(Level level, @Nullable Entity excluded,
            AABB bounds, Operation<List<Entity>> original) {
        LocalPlayer player = this.legacyparkourcompat$excludedPlayer;
        this.legacyparkourcompat$excludedPlayer = null;
        return original.call(level, player == null ? excluded : player, bounds);
    }
}
