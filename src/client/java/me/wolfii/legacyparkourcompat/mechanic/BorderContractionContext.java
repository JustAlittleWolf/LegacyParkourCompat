package me.wolfii.legacyparkourcompat.mechanic;

import java.util.Optional;
import me.wolfii.legacyparkourcompat.mechanic.hook.border.BorderCollisionAdmissionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.border.BorderOutsideStateHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.border.BorderStatus;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/** Tracks one observed inward sweep and its resulting center for the next outward sweep. */
final class BorderContractionContext {
    private static final ThreadLocal<Pending> ACTIVE = new ThreadLocal<>();

    private BorderContractionContext() {}

    static void setSize(ClientLevel level, WorldBorder border, double size, VanillaCall setSize) {
        ACTIVE.remove();
        LocalPlayer player = Minecraft.getInstance().player;
        Bounds before = Bounds.stationary(border, border.getSize());
        Bounds after = Bounds.stationary(border, size);
        if (!eligible(player) || player.level() != level || before == null || after == null
            || !before.matches(border) || !after.strictlyInside(before)
            || !before.contains(player.getBoundingBox())) {
            setSize.run();
            return;
        }
        var transition = MovementRuntime.find(BorderOutsideStateHook.class, player);
        Vec3 center = player.position();
        AABB box = player.getBoundingBox();
        setSize.run();
        if (!eligible(player) || player.level() != level || level.getWorldBorder() != border
            || border.getSize() != size || !after.matches(border)
            || !center.equals(player.position()) || !box.equals(player.getBoundingBox())) {
            return;
        }
        if (transition.isEmpty() || !after.outside(transition.get(), false, center)
            || !after.outside(transition.get(), true, center)) {
            return;
        }
        for (Direction face : Direction.Plane.HORIZONTAL) {
            if (outward(face, center) >= after.edge(face) + 1.0
                && outwardMin(face, box) >= after.roundedEdge(face)
                && after.insideOtherAxis(face, center)) {
                ACTIVE.set(new Pending(player, level, border, after, face, new Destination(center, box)));
                return;
            }
        }
    }

    static void clear() {
        ACTIVE.remove();
    }

    static void invalidate(WorldBorder border) {
        Pending pending = ACTIVE.get();
        if (pending != null && pending.border == border) clear();
    }

    static void move(Entity entity, MoverType mover, VanillaCall movement) {
        Pending pending = ACTIVE.get();
        if (pending == null || pending.player != entity) {
            movement.run();
            return;
        }
        if (!pending.matches() || mover != MoverType.SELF || pending.moving) {
            clear();
            movement.run();
            return;
        }

        pending.moving = true;
        boolean returned = false;
        try {
            movement.run();
            returned = true;
        } finally {
            if (ACTIVE.get() == pending) {
                if (returned && !pending.outward && pending.written && pending.inwardEnd != null
                    && pending.inwardEnd.matches(entity)) {
                    pending.current = pending.inwardEnd;
                    pending.outward = true;
                    pending.moving = false;
                } else {
                    clear();
                }
            }
        }
    }

    static Optional<Boolean> admission(WorldBorder border, Entity entity, AABB sweep) {
        Pending pending = ACTIVE.get();
        if (pending == null || pending.player != entity) return Optional.empty();
        var query = CollisionAxisOrderContext.movementQuery();
        if (!pending.matches() || border != pending.border || !pending.moving || query == null
            || query.entity() != entity || query.moverType() != MoverType.SELF
            || !query.unadjustedPrimaryMovement() || !query.startingBox().equals(pending.current.box)
            || !query.sweptBox().equals(sweep) || pending.written) {
            clear();
            return Optional.empty();
        }

        Vec3 delta = query.requestedMovement();
        if (!finite(delta) || delta.y != 0.0 || Math.abs(outward(pending.face, delta)) > 4_096.0
            || pending.face.getAxis().choose(delta.z, 0.0, delta.x) != 0.0
            || !emptyLoadedEnvelope(pending, sweep)) {
            clear();
            return Optional.empty();
        }

        double distance = outward(pending.face, delta);
        var outside = MovementRuntime.find(BorderOutsideStateHook.class, entity).orElse(null);
        if (outside == null) {
            clear();
            return Optional.empty();
        }
        AABB endBox = pending.current.box.move(delta);
        Vec3 endCenter = new Vec3((endBox.minX + endBox.maxX) / 2.0, endBox.minY,
            (endBox.minZ + endBox.maxZ) / 2.0);
        if (!pending.outward) {
            if (pending.inwardEnd != null || distance >= 0.0
                || !pending.bounds.outside(outside, false, pending.current.center)
                || !pending.bounds.outside(outside, true, pending.current.center)
                || !pending.bounds.outside(outside, true, endCenter)
                || !pending.bounds.contains(endBox)) {
                clear();
            } else {
                pending.inwardEnd = new Destination(endCenter, endBox);
                pending.inwardDelta = delta;
            }
            return Optional.empty();
        }

        clear();
        var from = pending.current;
        boolean stillOutside = pending.bounds.outside(outside, true, from.center);
        if (distance <= 0.0 || !pending.bounds.contains(from.box)
            || !pending.bounds.insideOtherAxis(pending.face, from.center)
            || outwardMax(pending.face, endBox) <= pending.bounds.roundedEdge(pending.face) || !stillOutside) {
            return Optional.empty();
        }
        return MovementRuntime.find(BorderCollisionAdmissionHook.class, entity)
            .flatMap(hook -> hook.includeBorderAfterContraction(true, stillOutside));
    }

    static void movementWrite(Entity entity, Vec3 movement, VanillaCall setter) {
        Pending pending = ACTIVE.get();
        if (pending == null || pending.player != entity || pending.inwardEnd == null) {
            setter.run();
            return;
        }
        if (!pending.moving || pending.outward || pending.written || !movement.equals(pending.inwardDelta)) {
            clear();
            setter.run();
            return;
        }
        pending.writing = true;
        try {
            setter.run();
        } finally {
            pending.writing = false;
        }
    }

    static void positionWrite(Entity entity, double x, double y, double z) {
        Pending pending = ACTIVE.get();
        if (pending == null || pending.player != entity) return;
        if (entity.getX() == x && entity.getY() == y && entity.getZ() == z) return;
        if (!pending.writing || pending.written || pending.inwardEnd == null
            || !pending.inwardEnd.center.equals(new Vec3(x, y, z))) {
            clear();
        } else {
            pending.written = true;
        }
    }

    private static boolean eligible(@Nullable LocalPlayer player) {
        return player != null && Minecraft.getInstance().player == player && MovementRuntime.appliesTo(player)
            && player.getPose() == Pose.STANDING && OrdinaryPlayerContext.matches(player)
            && !player.noPhysics && !player.isSpectator() && player.getBbWidth() == 0.6F
            && player.getBbHeight() == 1.8F && finite(player.position());
    }

    private static boolean emptyLoadedEnvelope(Pending pending, AABB sweep) {
        // Covers both Feather query expansions before candidate pruning and
        // the native one-cell candidate margin, including partial edge cells.
        if (sweep.minX < Integer.MIN_VALUE + 2.0 || sweep.maxX > Integer.MAX_VALUE - 2.0
            || sweep.minZ < Integer.MIN_VALUE + 2.0 || sweep.maxZ > Integer.MAX_VALUE - 2.0) return false;
        int minX = Mth.floor(sweep.minX - 1.0E-7) - 1;
        int minY = Mth.floor(sweep.minY - 1.0E-7) - 1;
        int minZ = Mth.floor(sweep.minZ - 1.0E-7) - 1;
        int maxX = Mth.floor(sweep.maxX + 1.0E-7) + 2;
        int maxY = Mth.floor(sweep.maxY + 1.0E-7) + 2;
        int maxZ = Mth.floor(sweep.maxZ + 1.0E-7) + 2;
        long cells = ((long) maxX - minX) * (maxY - minY) * ((long) maxZ - minZ);
        if (minX >= maxX || minZ >= maxZ || minY < 0 || maxY > 256
            || minY >= maxY || cells <= 0 || cells > 4_096
            || !pending.level.getEntities(pending.player, sweep.inflate(0.2500002), entity -> true).isEmpty()) {
            return false;
        }
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int x = minX; x < maxX; x++) {
            for (int z = minZ; z < maxZ; z++) {
                var chunk = pending.level.getChunkForCollisions(
                    SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z));
                if (chunk == null) return false;
                for (int y = minY; y < maxY; y++) {
                    cursor.set(x, y, z);
                    if (pending.level.isOutsideBuildHeight(cursor) || !chunk.getBlockState(cursor).is(Blocks.AIR)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean finite(Vec3 vector) {
        return Double.isFinite(vector.x) && Double.isFinite(vector.y) && Double.isFinite(vector.z);
    }

    private static double outward(Direction face, Vec3 vector) {
        return face.getAxis().choose(vector.x, 0.0, vector.z) * face.getAxisDirection().getStep();
    }

    private static double outwardMin(Direction face, AABB box) {
        return face.getAxisDirection() == Direction.AxisDirection.POSITIVE
            ? box.min(face.getAxis()) : -box.max(face.getAxis());
    }

    private static double outwardMax(Direction face, AABB box) {
        return face.getAxisDirection() == Direction.AxisDirection.POSITIVE
            ? box.max(face.getAxis()) : -box.min(face.getAxis());
    }

    private record Destination(Vec3 center, AABB box) {
        boolean matches(Entity entity) {
            return this.center.equals(entity.position()) && this.box.equals(entity.getBoundingBox());
        }
    }

    private record Bounds(double minX, double maxX, double minZ, double maxZ) {
        static @Nullable Bounds stationary(WorldBorder border, double size) {
            if (border.getStatus() != BorderStatus.STATIONARY || !Double.isFinite(size) || size <= 0.0) return null;
            double half = size / 2.0;
            double max = border.getAbsoluteMaxSize();
            double minX = border.getCenterX() - half;
            double maxX = border.getCenterX() + half;
            double minZ = border.getCenterZ() - half;
            double maxZ = border.getCenterZ() + half;
            // The old asymmetric min/max and the new two-sided clamp agree
            // exactly when neither clamps an endpoint.
            return -max <= minX && minX < maxX && maxX <= max && -max <= minZ && minZ < maxZ && maxZ <= max
                ? new Bounds(minX, maxX, minZ, maxZ) : null;
        }

        boolean matches(WorldBorder border) {
            return border.getStatus() == BorderStatus.STATIONARY && this.minX == border.getMinX()
                && this.maxX == border.getMaxX() && this.minZ == border.getMinZ() && this.maxZ == border.getMaxZ();
        }

        boolean strictlyInside(Bounds previous) {
            return this.minX > previous.minX && this.maxX < previous.maxX
                && this.minZ > previous.minZ && this.maxZ < previous.maxZ;
        }

        boolean contains(AABB box) {
            return box.minX > this.minX && box.maxX < this.maxX && box.minZ > this.minZ && box.maxZ < this.maxZ;
        }

        double edge(Direction face) {
            return switch (face) {
                case EAST -> this.maxX;
                case WEST -> -this.minX;
                case SOUTH -> this.maxZ;
                case NORTH -> -this.minZ;
                default -> throw new IllegalArgumentException("Expected a horizontal border face");
            };
        }

        double roundedEdge(Direction face) {
            return Math.ceil(this.edge(face));
        }

        boolean insideOtherAxis(Direction face, Vec3 center) {
            return face.getAxis() == Direction.Axis.X
                ? center.z > this.minZ + 1.0 && center.z < this.maxZ - 1.0
                : center.x > this.minX + 1.0 && center.x < this.maxX - 1.0;
        }

        boolean outside(BorderOutsideStateHook hook, boolean wasOutside, Vec3 center) {
            return hook.outside(wasOutside, center.x, center.z, this.minX, this.maxX, this.minZ, this.maxZ)
                .orElse(false);
        }
    }

    private static final class Pending {
        private final LocalPlayer player;
        private final ClientLevel level;
        private final WorldBorder border;
        private final Bounds bounds;
        private final Direction face;
        private Destination current;
        private @Nullable Destination inwardEnd;
        private @Nullable Vec3 inwardDelta;
        private boolean moving;
        private boolean outward;
        private boolean writing;
        private boolean written;

        private Pending(LocalPlayer player, ClientLevel level, WorldBorder border, Bounds bounds, Direction face,
                        Destination start) {
            this.player = player;
            this.level = level;
            this.border = border;
            this.bounds = bounds;
            this.face = face;
            this.current = start;
        }

        private boolean matches() {
            return eligible(this.player) && this.player.level() == this.level
                && this.level.getWorldBorder() == this.border && this.bounds.matches(this.border)
                && this.current.matches(this.player);
        }
    }
}
