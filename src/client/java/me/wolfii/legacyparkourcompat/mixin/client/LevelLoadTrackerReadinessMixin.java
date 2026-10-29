package me.wolfii.legacyparkourcompat.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerChunkReadinessHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ViewArea;
import net.minecraft.client.renderer.chunk.CompiledSectionMesh;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;

@Mixin(targets = "net.minecraft.client.multiplayer.LevelLoadTracker$WaitingForPlayerChunk")
abstract class LevelLoadTrackerReadinessMixin {
    @Shadow @Final private LocalPlayer player;
    @Shadow @Final private ClientLevel level;
    @Shadow @Final private long timeoutAfter;

    @WrapOperation(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/multiplayer/LevelLoadTracker$WaitingForPlayerChunk;isReady()Z"
        )
    )
    private boolean legacyparkourcompat$playerChunkReadiness(
        @Coerce Object waitingForPlayerChunk,
        Operation<Boolean> original
    ) {
        LocalPlayer player = this.player;
        ClientLevel level = this.level;
        long timeoutAfter = this.timeoutAfter;

        return MovementRuntime.find(PlayerChunkReadinessHook.class, player)
            .map(hook -> hook.isReady(new PlayerChunkReadinessHook.Probe() {
                private BlockPos playerPosition;

                private BlockPos playerPosition() {
                    if (this.playerPosition == null) {
                        this.playerPosition = player.blockPosition();
                    }
                    return this.playerPosition;
                }

                @Override
                public boolean nativeReady() {
                    return original.call(waitingForPlayerChunk);
                }

                @Override
                public boolean outsideBuildHeight() {
                    return level.isOutsideBuildHeight(this.playerPosition().getY());
                }

                @Override
                public boolean playerSectionCompiled() {
                    ViewArea viewArea = Minecraft.getInstance().levelRenderer.viewArea();
                    if (viewArea == null) {
                        return false;
                    }

                    var renderSection = viewArea.getRenderSectionAt(this.playerPosition());
                    return renderSection != null
                        && renderSection.getSectionMesh() != CompiledSectionMesh.UNCOMPILED;
                }

                @Override
                public boolean spectator() {
                    return player.isSpectator();
                }

                @Override
                public boolean alive() {
                    return player.isAlive();
                }

                @Override
                public long currentTimeMillis() {
                    return Util.getMillis();
                }

                @Override
                public long timeoutAfterMillis() {
                    return timeoutAfter;
                }

                @Override
                public void logTimeoutWarning() {
                    // The current source emits the same timeout warning inside isReady().
                    // The historical strict deadline check has already succeeded here.
                    original.call(waitingForPlayerChunk);
                }

                @Override
                public boolean playerSectionCompiledAndVisible() {
                    return Minecraft.getInstance().levelRenderer
                        .isSectionCompiledAndVisible(this.playerPosition());
                }
            }))
            .orElseGet(() -> original.call(waitingForPlayerChunk));
    }
}
