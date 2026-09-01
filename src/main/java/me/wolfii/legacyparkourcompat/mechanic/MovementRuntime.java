package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.api.ActiveMovementProfile;
import me.wolfii.legacyparkourcompat.api.MovementController;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Mixin-facing lookup. Change authors should not call this; they implement
 * {@link VersionedMechanic} hooks instead.
 */
public final class MovementRuntime {
    private static final ThreadLocal<Entity> CURRENT_ENTITY = new ThreadLocal<>();

    private MovementRuntime() {
    }

    public static boolean isPlayer(@Nullable Entity entity) {
        return entity instanceof Player;
    }

    public static void enter(Entity entity) {
        CURRENT_ENTITY.set(entity);
    }

    public static void exit() {
        CURRENT_ENTITY.remove();
    }

    public static @Nullable Entity currentEntity() {
        return CURRENT_ENTITY.get();
    }

    public static ActiveMovementProfile profile(@Nullable Entity entity) {
        return MovementController.get().profileFor(entity);
    }

    public static <T extends VersionedMechanic> Optional<T> find(Class<T> type, @Nullable Entity entity) {
        if (entity != null && !isPlayer(entity)) {
            return Optional.empty();
        }
        return profile(entity).get(type);
    }

    public static <T extends VersionedMechanic> Optional<T> find(Class<T> type, String variant, @Nullable Entity entity) {
        if (entity != null && !isPlayer(entity)) {
            return Optional.empty();
        }
        return profile(entity).get(type, variant);
    }

    public static <T extends VersionedMechanic> Optional<T> find(Class<T> type, Block block, @Nullable Entity entity) {
        return find(type, blockId(block), entity);
    }

    public static @Nullable Entity playerFrom(CollisionContext context) {
        if (context instanceof EntityCollisionContext entityContext) {
            Entity entity = entityContext.getEntity();
            return isPlayer(entity) ? entity : null;
        }
        return null;
    }

    public static String blockId(Block block) {
        Identifier id = BuiltInRegistries.BLOCK.getKey(block);
        return id == null ? "" : id.toString();
    }
}
