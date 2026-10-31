package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.mechanic.hook.worldborder.StaticWorldBorderCollisionShapeHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/** Shared client-local resolution of the accepted static-border geometry. */
public final class StaticWorldBorderCollisionShape {
    private StaticWorldBorderCollisionShape() {
    }

    public static Optional<VoxelShape> resolve(WorldBorder border, Entity source) {
        if (!(source instanceof LocalPlayer player)
            || Minecraft.getInstance().player != player
            || !MovementRuntime.appliesTo(player)
            || player.level().getWorldBorder() != border
            || border.getLerpTime() != 0L
            || border.getLerpSpeed() != 0.0) {
            return Optional.empty();
        }
        return MovementRuntime.find(StaticWorldBorderCollisionShapeHook.class, player)
            .flatMap(hook -> hook.collisionShape(
                border.getCenterX(), border.getCenterZ(), border.getSize(), border.getAbsoluteMaxSize()
            ));
    }
}
