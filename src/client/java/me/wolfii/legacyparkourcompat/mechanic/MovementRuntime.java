package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.api.ActiveMovementProfile;
import me.wolfii.legacyparkourcompat.api.MovementController;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.PistonMovementHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.MovementPositionReconstructionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.position.PoseBoxHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.EmptyShapeMovementHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.HorizontalVelocityCollisionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.HorizontalVelocityWriteHook;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Mixin-facing lookup. Change authors should not call this; they implement
 * {@link VersionedMechanic} hooks instead.
 */
public final class MovementRuntime {
    private static final ThreadLocal<Entity> CURRENT_ENTITY = new ThreadLocal<>();
    private static final ThreadLocal<LocalPlayer> FLUID_CURRENT_PLAYER = new ThreadLocal<>();

    private MovementRuntime() {
    }

    /**
     * {@code false} when movement emulation is disabled (native/latest) or the
     * entity is not a client-side player. This logical-side gate also excludes
     * the integrated server. Mixins must call vanilla unchanged in that case.
     */
    public static boolean appliesTo(@Nullable Entity entity) {
        if (!(entity instanceof Player) || !entity.level().isClientSide()) {
            return false;
        }
        return MovementController.get().isEnabled(entity);
    }

    public static boolean limitPistonMovement(Entity entity) {
        return find(PistonMovementHook.class, entity)
            .map(PistonMovementHook::limitPistonMovement).orElse(true);
    }

    /** Pure calculation, shared by movement bookkeeping and the eventual write. */
    private static Optional<MovementPositionReconstructionHook.Position> movementPosition(Entity entity, Vec3 movement,
                                                                                         @Nullable AABB resolvedBox) {
        if (!(entity instanceof Player player)) {
            return Optional.empty();
        }
        return find(MovementPositionReconstructionHook.class, player)
            .flatMap(hook -> hook.resolve(player, profile(player).target(), movement, resolvedBox));
    }

    public static Vec3 movementEndpoint(Entity entity, Vec3 movement, Vec3 nativePosition, @Nullable AABB resolvedBox) {
        return movementPosition(entity, movement, resolvedBox)
            .map(MovementPositionReconstructionHook.Position::position).orElse(nativePosition);
    }

    public static void writeMovementPosition(Entity entity, Vec3 movement, VanillaCall vanilla) {
        writeMovementPosition(entity, movement, null, vanilla);
    }

    public static void writeMovementPosition(Entity entity, Vec3 movement, @Nullable AABB resolvedBox, VanillaCall vanilla) {
        var historical = movementPosition(entity, movement, resolvedBox);
        if (historical.isEmpty()) {
            vanilla.run();
            return;
        }
        var resolved = historical.get();
        entity.setBoundingBox(resolved.box());
        entity.setPosRaw(resolved.position().x, resolved.position().y, resolved.position().z);
    }

    public static void refreshPoseBox(Entity entity, EntityDimensions previous, EntityDimensions next, VanillaCall vanilla) {
        Optional<AABB> box = find(PoseBoxHook.class, entity)
            .flatMap(hook -> hook.poseBox(entity, previous, next));
        if (box.isPresent()) {
            entity.setBoundingBox(box.get());
        } else {
            vanilla.run();
        }
    }

    public static boolean isPlayer(@Nullable Entity entity) {
        return entity instanceof Player;
    }

    public static int epoch() {
        return MovementController.get().epoch();
    }

    public static void enter(Entity entity) {
        if (appliesTo(entity)) {
            CURRENT_ENTITY.set(entity);
        }
    }

    public static void exit() {
        CURRENT_ENTITY.remove();
    }

    public static @Nullable Entity currentEntity() {
        return CURRENT_ENTITY.get();
    }

    /**
     * Scopes a native fluid-flow calculation to the local player's fluid scan.
     * Nested calls and exceptions restore the previous player context.
     */
    public static <T> T withLocalPlayerFluidCurrent(Entity entity, VanillaFn<T> query) {
        LocalPlayer previous = FLUID_CURRENT_PLAYER.get();
        if (entity instanceof LocalPlayer player && appliesTo(player)) {
            FLUID_CURRENT_PLAYER.set(player);
        } else {
            FLUID_CURRENT_PLAYER.remove();
        }
        try {
            return query.get();
        } finally {
            if (previous == null) {
                FLUID_CURRENT_PLAYER.remove();
            } else {
                FLUID_CURRENT_PLAYER.set(previous);
            }
        }
    }

    public static @Nullable LocalPlayer localPlayerFluidCurrent() {
        return FLUID_CURRENT_PLAYER.get();
    }

    public static ActiveMovementProfile profile(@Nullable Entity entity) {
        return MovementController.get().profileFor(entity);
    }

    public static <T extends VersionedMechanic> Optional<T> find(Class<T> type, @Nullable Entity entity) {
        if (!appliesTo(entity)) {
            return Optional.empty();
        }
        return profile(entity).get(type);
    }

    public static <T extends VersionedMechanic> Optional<T> find(Class<T> type, String variant, @Nullable Entity entity) {
        if (!appliesTo(entity)) {
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

    /** Returns whether horizontal restitution has been supplied by resolved hooks. */
    public static boolean clearHorizontalCollisionVelocity(Entity entity, Vec3 requested, Vec3 resolved) {
        Optional<HorizontalVelocityCollisionHook> predicate = find(HorizontalVelocityCollisionHook.class, entity);
        Optional<Boolean> historicalX = predicate.flatMap(hook -> hook.shouldClear(requested.x, resolved.x));
        Optional<Boolean> historicalZ = predicate.flatMap(hook -> hook.shouldClear(requested.z, resolved.z));
        boolean clearX = historicalX.orElseGet(() -> !Mth.equal(requested.x, resolved.x));
        boolean clearZ = historicalZ.orElseGet(() -> !Mth.equal(requested.z, resolved.z));
        Vec3 captured = entity.getDeltaMovement();
        Optional<Vec3> historicalWrite = find(HorizontalVelocityWriteHook.class, entity)
            .flatMap(hook -> hook.clear(captured, clearX, clearZ));
        if (historicalWrite.isEmpty() && historicalX.isEmpty() && historicalZ.isEmpty()) {
            return false;
        }
        if (clearX || clearZ) {
            entity.setDeltaMovement(historicalWrite.orElseGet(
                () -> new Vec3(clearX ? 0.0 : captured.x, captured.y, clearZ ? 0.0 : captured.z)));
        }
        return true;
    }

    public static Vec3 resolveEmptyShapeMovement(@Nullable Entity entity, Vec3 movement,
                                                List<VoxelShape> shapes, VanillaFn<Vec3> vanilla) {
        // Use the exact admission decision and shape from this call, including
        // moving borders whose shape getter allocates a new object every time.
        var borderFrame = CollisionBorderShapeContext.current();
        if (borderFrame != null && borderFrame.isSoleHistoricalBorder(shapes)) {
            Optional<Vec3> historical = find(EmptyShapeMovementHook.class, entity)
                .flatMap(hook -> hook.resolve(movement));
            if (historical.isPresent()) {
                return historical.get();
            }
        }
        return vanilla.get();
    }
}
