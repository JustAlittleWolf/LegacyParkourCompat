package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.api.ActiveMovementProfile;
import me.wolfii.legacyparkourcompat.api.MovementController;
import me.wolfii.legacyparkourcompat.mechanic.attribute.MovementSpeedModifierHistory;
import me.wolfii.legacyparkourcompat.mechanic.attribute.MovementSpeedModifierHistoryAccess;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerMovementSpeedHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.PistonMovementHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.MovementPositionReconstructionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.position.PoseBoxHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.EmptyShapeMovementHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.AxisClippingHook;
import net.minecraft.core.Direction;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.HorizontalVelocityCollisionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.HorizontalVelocityWriteHook;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;
import me.wolfii.legacyparkourcompat.mechanic.collision.MovementCollisionContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionCollectionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockMovementCollisionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockComponentClipHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;

import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;

/**
 * Mixin-facing lookup. Change authors should not call this; they implement
 * {@link VersionedMechanic} hooks instead.
 */
public final class MovementRuntime {
    private static final ThreadLocal<Entity> CURRENT_ENTITY = new ThreadLocal<>();
    private static final ThreadLocal<LocalPlayer> FLUID_CURRENT_PLAYER = new ThreadLocal<>();

    private MovementRuntime() {
    }

    /** Capture provenance even before a profile or player query is available. */
    public static void observeMovementSpeedModifiers(AttributeInstance attribute,
                                                      MovementSpeedModifierHistory history,
                                                      Map<AttributeModifier.Operation, Map<Identifier, AttributeModifier>> operations) {
        if (attribute.getAttribute().is(Attributes.MOVEMENT_SPEED)) {
            history.observe(operations);
        }
    }

    public static Optional<Float> playerMovementSpeed(Player player) {
        return find(PlayerMovementSpeedHook.class, player).flatMap(change -> {
            AttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attribute == null) {
                throw new IllegalStateException("Player has no movement-speed attribute");
            }
            MovementSpeedModifierHistory history = ((MovementSpeedModifierHistoryAccess)attribute)
                .legacyparkourcompat$movementSpeedModifierHistory();
            var currentModifiers = attribute.getModifiers();
            List<PlayerMovementSpeedHook.Modifier> modifiers = new ArrayList<>(currentModifiers.size());
            for (AttributeModifier modifier : currentModifiers) {
                int operation = switch (modifier.operation()) {
                    case ADD_VALUE -> 0;
                    case ADD_MULTIPLIED_BASE -> 1;
                    case ADD_MULTIPLIED_TOTAL -> 2;
                };
                modifiers.add(new PlayerMovementSpeedHook.Modifier(
                    modifier.id(), modifier.amount(), operation));
            }
            return change.movementSpeed(new PlayerMovementSpeedHook.Snapshot(
                attribute.getBaseValue(), modifiers, history.snapshot()));
        });
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

    /** Only Entity's movement collector installs this context; ordinary queries remain unchanged. */
    public static CollisionContext movementCollisionContext(CollisionContext original, AABB query) {
        Entity player = playerFrom(original);
        return appliesTo(player) && !original.isPlacement() && !original.alwaysCollideWithFluid()
            ? new MovementCollisionContext(original, player, query) : original;
    }

    public static VoxelShape collectMovementBlock(CollisionContext context, BlockState state,
                                                 CollisionGetter level, BlockPos pos, VoxelShape nativeShape) {
        if (!(context instanceof MovementCollisionContext movement)) {
            return nativeShape;
        }
        Entity player = playerFrom(context);
        var collector = find(BlockMovementCollisionHook.class, state.getBlock(), player);
        if (collector.isEmpty()) {
            return nativeShape;
        }
        var shapeHook = find(BlockCollisionShapeHook.class, state.getBlock(), player)
            .filter(hook -> hook.supports(profile(player).target()));
        var providedParts = shapeHook.flatMap(hook -> hook.collisionShapes(state, level, pos, movement.delegate()));
        if (shapeHook.isPresent() && providedParts.isEmpty()) {
            return nativeShape;
        }
        var parts = providedParts.orElse(List.of(nativeShape));
        var clip = find(BlockComponentClipHook.class, state.getBlock(), player).orElse(null);
        return collector.get().collect(player, pos, movement.query(), nativeShape, parts, clip);
    }

    /**
     * Replaces the native block stream only when the complete 1.13.0 slab-only
     * query can be reconstructed from the original Entity.move request.
     */
    public static Iterable<VoxelShape> collectHistoricalMovementBlocks(
        Entity entity,
        Level level,
        CollisionContext context,
        AABB sweptBox,
        List<VoxelShape> entityCandidates,
        VanillaFn<Iterable<VoxelShape>> vanilla
    ) {
        CollisionAxisOrderContext.clearCollectedBlockShape();
        if (!(entity instanceof LocalPlayer)
            || !appliesTo(entity)
            || CollisionAxisOrderContext.blockCollectionDisabled()
            || !(context instanceof MovementCollisionContext)
            || !entityCandidates.isEmpty()) {
            return vanilla.get();
        }
        var collector = find(BlockCollisionCollectionHook.class, entity);
        if (collector.isEmpty()) {
            return vanilla.get();
        }
        var streamOrder = collector.get().streamOrder();
        if (streamOrder == BlockCollisionCollectionHook.StreamOrder.NATIVE) {
            return vanilla.get();
        }

        var movement = CollisionAxisOrderContext.movementQuery();
        if (movement == null || movement.entity() != entity || movement.requestedMovement().lengthSqr() == 0.0
            || !movement.unadjustedPrimaryMovement()
            || !movement.startingBox().equals(entity.getBoundingBox())
            || !movement.sweptBox().equals(sweptBox)) {
            return vanilla.get();
        }
        // A grounded or descending move may enter the separate step-up sweep.
        // Keep that entire route native because it does not reuse this aggregate.
        boolean stepUpFree = !entity.onGround() && movement.requestedMovement().y >= 0.0;
        if (!stepUpFree) {
            return vanilla.get();
        }

        // 26.2 has a different entity query envelope and filter order. Any entity
        // near the old expanded bounds keeps the complete native route unchanged.
        AABB historicalEntityEnvelope = sweptBox.inflate(0.25);
        if (!level.getEntities(entity, historicalEntityEnvelope, other -> other != entity).isEmpty()) {
            return vanilla.get();
        }

        var border = level.getWorldBorder();
        boolean borderCheck = !border.isWithinBounds(entity.getBoundingBox())
            || border.isInsideCloseToBorder(entity, sweptBox);
        boolean historicalBorderCollision = find(
            me.wolfii.legacyparkourcompat.mechanic.hook.border.WorldBorderCollisionHook.class, entity)
            .map(hook -> hook.collision(border, entity, sweptBox)).orElse(null) != null;
        if (borderCheck || historicalBorderCollision) {
            return vanilla.get();
        }

        AABB startBox = movement.startingBox();
        Vec3 requested = movement.requestedMovement();
        AABB paddedSweep = sweptBox.inflate(1.0E-7);
        AABB shiftedStart = startBox.move(
            requested.x > 0.0 ? -1.0E-7 : 1.0E-7,
            requested.y > 0.0 ? -1.0E-7 : 1.0E-7,
            requested.z > 0.0 ? -1.0E-7 : 1.0E-7
        );
        boolean exactBounds = sourceBoxConversionMatches(startBox)
            && sourceBoxConversionMatches(paddedSweep)
            && sourceBoxConversionMatches(shiftedStart);
        if (!exactBounds) {
            return vanilla.get();
        }
        VoxelShape exclusion = Shapes.create(startBox);
        VoxelShape bounds = Shapes.joinUnoptimized(
            Shapes.create(paddedSweep), Shapes.create(shiftedStart), BooleanOp.ONLY_FIRST);

        var provider = new SlabMovementCollisionProvider(level, context, entity);
        boolean slabOnlyEnvelope = provider.supportsCompleteEnvelope(bounds, streamOrder);
        var query = new BlockCollisionCollectionHook.CollectorQuery(
            bounds,
            exclusion,
            exactBounds,
            sourceBoxConversionMatches(startBox),
            slabOnlyEnvelope,
            slabOnlyEnvelope,
            true,
            true,
            stepUpFree,
            false,
            false
        );
        var historical = collector.get().collect(query, provider);
        if (historical.isEmpty() || !provider.isStillSupported()) {
            return vanilla.get();
        }

        if (streamOrder == BlockCollisionCollectionHook.StreamOrder.AGGREGATE_X_Z_Y
            && historical.get().size() == 1) {
            CollisionAxisOrderContext.collectedBlockShape(historical.get().getFirst());
        }
        return historical.get();
    }

    private static final Map<String, String> CURRENT_TO_LEGACY_113_SLAB_KEYS = Map.ofEntries(
        Map.entry("minecraft:oak_slab", "minecraft:oak_slab"),
        Map.entry("minecraft:spruce_slab", "minecraft:spruce_slab"),
        Map.entry("minecraft:birch_slab", "minecraft:birch_slab"),
        Map.entry("minecraft:jungle_slab", "minecraft:jungle_slab"),
        Map.entry("minecraft:acacia_slab", "minecraft:acacia_slab"),
        Map.entry("minecraft:dark_oak_slab", "minecraft:dark_oak_slab"),
        Map.entry("minecraft:smooth_stone_slab", "minecraft:stone_slab"),
        Map.entry("minecraft:sandstone_slab", "minecraft:sandstone_slab"),
        Map.entry("minecraft:petrified_oak_slab", "minecraft:petrified_oak_slab"),
        Map.entry("minecraft:cobblestone_slab", "minecraft:cobblestone_slab"),
        Map.entry("minecraft:brick_slab", "minecraft:brick_slab"),
        Map.entry("minecraft:stone_brick_slab", "minecraft:stone_brick_slab"),
        Map.entry("minecraft:nether_brick_slab", "minecraft:nether_brick_slab"),
        Map.entry("minecraft:quartz_slab", "minecraft:quartz_slab"),
        Map.entry("minecraft:red_sandstone_slab", "minecraft:red_sandstone_slab"),
        Map.entry("minecraft:purpur_slab", "minecraft:purpur_slab"),
        Map.entry("minecraft:prismarine_slab", "minecraft:prismarine_slab"),
        Map.entry("minecraft:prismarine_brick_slab", "minecraft:prismarine_brick_slab"),
        Map.entry("minecraft:dark_prismarine_slab", "minecraft:dark_prismarine_slab")
    );
    private static final Set<String> LEGACY_113_EMPTY_KEYS = Set.of(
        "minecraft:air", "minecraft:cave_air", "minecraft:void_air"
    );

    private static final class SlabMovementCollisionProvider
        implements BlockCollisionCollectionHook.BlockCollisionProvider {
        private final Level level;
        private final CollisionContext context;
        private final Entity entity;
        private final Map<BlockPos, BlockCollisionCollectionHook.CollisionCell> cells = new HashMap<>();
        private final Map<Long, BlockGetter> collisionChunks = new HashMap<>();
        private final Set<Long> missingCollisionChunks = new HashSet<>();
        private final net.minecraft.world.level.border.WorldBorder border;
        private final BlockGetter noLoadBlockView = new BlockGetter() {
            @Override
            public @Nullable BlockEntity getBlockEntity(BlockPos position) {
                BlockGetter chunk = SlabMovementCollisionProvider.this.collisionChunk(position);
                return chunk == null ? null : chunk.getBlockEntity(position);
            }

            @Override
            public BlockState getBlockState(BlockPos position) {
                BlockGetter chunk = SlabMovementCollisionProvider.this.collisionChunk(position);
                return chunk == null ? Blocks.VOID_AIR.defaultBlockState() : chunk.getBlockState(position);
            }

            @Override
            public FluidState getFluidState(BlockPos position) {
                BlockGetter chunk = SlabMovementCollisionProvider.this.collisionChunk(position);
                return chunk == null ? Blocks.VOID_AIR.defaultBlockState().getFluidState() : chunk.getFluidState(position);
            }

            @Override
            public int getHeight() {
                return SlabMovementCollisionProvider.this.level.getHeight();
            }

            @Override
            public int getMinY() {
                return SlabMovementCollisionProvider.this.level.getMinY();
            }
        };
        private boolean supported = true;

        private SlabMovementCollisionProvider(Level level, CollisionContext context, Entity entity) {
            this.level = level;
            this.context = context;
            this.entity = entity;
            this.border = level.getWorldBorder();
        }

        @Override
        public boolean isChunkLoaded(BlockPos oldProbe) {
            // 1.13 client isChunkLoaded(..., allowEmpty=true) exposes VOID_AIR
            // for an absent chunk; the modern null collision chunk has the same
            // empty collision geometry. Keep the scan and synthesize that state
            // below without calling Level.getBlockState.
            this.collisionChunk(oldProbe);
            return true;
        }

        @Override
        public BlockCollisionCollectionHook.CollisionCell get(BlockPos position) {
            return this.cells.computeIfAbsent(position.immutable(), this::readCell);
        }

        private BlockCollisionCollectionHook.CollisionCell readCell(BlockPos position) {
            BlockGetter chunk = this.collisionChunk(position);
            BlockState state = chunk == null ? Blocks.VOID_AIR.defaultBlockState() : chunk.getBlockState(position);
            CollisionContext shapeContext = ((MovementCollisionContext) this.context).delegate();
            VoxelShape shape = chunk == null ? Shapes.empty()
                : state.getCollisionShape(this.noLoadBlockView, position, shapeContext);
            Identifier registeredKey = BuiltInRegistries.BLOCK.getKey(state.getBlock());
            String key = registeredKey == null ? "" : registeredKey.toString();
            String historicalSlabKey = CURRENT_TO_LEGACY_113_SLAB_KEYS.get(key);
            boolean canonicalFull = shape == Shapes.block();
            boolean supportedCell = find(BlockMovementCollisionHook.class, state.getBlock(), this.entity).isEmpty()
                && find(BlockCollisionShapeHook.class, state.getBlock(), this.entity).isEmpty()
                && find(BlockComponentClipHook.class, state.getBlock(), this.entity).isEmpty();

            if (historicalSlabKey != null) {
                supportedCell &= matchingLegacySlabState(state, shape);
            } else if (shape.isEmpty()) {
                // These keys are noCollision in both 1.13 and 26.2; an empty
                // shape from any other unclosed block key is not sufficient proof.
                supportedCell &= LEGACY_113_EMPTY_KEYS.contains(key);
            } else {
                supportedCell = false;
            }
            this.supported &= supportedCell;
            return new BlockCollisionCollectionHook.CollisionCell(
                state, historicalSlabKey, shape, canonicalFull, supportedCell);
        }

        private BlockGetter collisionChunk(BlockPos position) {
            long key = ((long) position.getX() >> 4) << 32 ^ (((long) position.getZ() >> 4) & 0xffffffffL);
            if (this.missingCollisionChunks.contains(key)) {
                return null;
            }
            BlockGetter cached = this.collisionChunks.get(key);
            if (cached != null) {
                return cached;
            }
            BlockGetter chunk = this.level.getChunkForCollisions(position.getX() >> 4, position.getZ() >> 4);
            if (chunk == null) {
                this.missingCollisionChunks.add(key);
            } else {
                this.collisionChunks.put(key, chunk);
            }
            return chunk;
        }

        @Override
        public boolean supportsCompleteEnvelope(VoxelShape bounds,
            BlockCollisionCollectionHook.StreamOrder streamOrder) {
            if (bounds.isEmpty()) {
                return false;
            }
            int minX = Mth.floor(bounds.min(Direction.Axis.X)) - 1;
            int maxX = Mth.ceil(bounds.max(Direction.Axis.X)) + 1;
            int minY = Mth.floor(bounds.min(Direction.Axis.Y)) - 1;
            int maxY = Mth.ceil(bounds.max(Direction.Axis.Y)) + 1;
            int minZ = Mth.floor(bounds.min(Direction.Axis.Z)) - 1;
            int maxZ = Mth.ceil(bounds.max(Direction.Axis.Z)) + 1;
            if (!(this.border.getMinX() < minX && maxX < this.border.getMaxX()
                && this.border.getMinZ() < minZ && maxZ < this.border.getMaxZ())) {
                return false;
            }
            long scanCells = (long) (maxX - minX) * (maxY - minY) * (maxZ - minZ);
            if (scanCells <= 0 || scanCells > 4096) {
                return false;
            }
            if (streamOrder == BlockCollisionCollectionHook.StreamOrder.AGGREGATE_X_Z_Y) {
                for (int x = minX; x < maxX; x++) {
                    for (int z = minZ; z < maxZ; z++) {
                        boolean xPerimeter = x == minX || x == maxX - 1;
                        boolean zPerimeter = z == minZ || z == maxZ - 1;
                        if ((xPerimeter && zPerimeter)
                            || !this.isChunkLoaded(new BlockPos(x, 64, z))) {
                            continue;
                        }
                        for (int y = minY; y < maxY; y++) {
                            if ((xPerimeter && zPerimeter) || ((xPerimeter || zPerimeter) && y == maxY - 1)) {
                                continue;
                            }
                            this.get(new BlockPos(x, y, z));
                        }
                    }
                }
            } else {
                for (int z = minZ; z < maxZ; z++) {
                    boolean zPerimeter = z == minZ || z == maxZ - 1;
                    for (int y = minY; y < maxY; y++) {
                        boolean yPerimeter = y == minY || y == maxY - 1;
                        for (int x = minX; x < maxX; x++) {
                            boolean xPerimeter = x == minX || x == maxX - 1;
                            if ((!xPerimeter || !yPerimeter) && (!yPerimeter || !zPerimeter)
                                && (!zPerimeter || !xPerimeter) && this.isChunkLoaded(new BlockPos(x, y, z))) {
                                this.get(new BlockPos(x, y, z));
                            }
                        }
                    }
                }
            }
            return this.supported;
        }

        private boolean isStillSupported() {
            return this.supported;
        }

        private static boolean matchingLegacySlabState(BlockState state, VoxelShape shape) {
            if (!(state.getBlock() instanceof SlabBlock)
                || !state.hasProperty(SlabBlock.TYPE)
                || !state.hasProperty(SlabBlock.WATERLOGGED)) {
                return false;
            }
            SlabType type = state.getValue(SlabBlock.TYPE);
            if (type == SlabType.DOUBLE) {
                return shape == Shapes.block();
            }
            if (shape == Shapes.block()) {
                return false;
            }
            AABB expected = type == SlabType.BOTTOM
                ? new AABB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0)
                : new AABB(0.0, 0.5, 0.0, 1.0, 1.0, 1.0);
            List<AABB> boxes = shape.toAabbs();
            if (boxes.size() != 1) {
                return false;
            }
            AABB box = boxes.getFirst();
            return box.minX == expected.minX && box.minY == expected.minY && box.minZ == expected.minZ
                && box.maxX == expected.maxX && box.maxY == expected.maxY && box.maxZ == expected.maxZ;
        }
    }

    private static boolean sourceBoxConversionMatches(AABB box) {
        if (!Double.isFinite(box.minX) || !Double.isFinite(box.minY) || !Double.isFinite(box.minZ)
            || !Double.isFinite(box.maxX) || !Double.isFinite(box.maxY) || !Double.isFinite(box.maxZ)
            || !(box.maxX - box.minX >= 1.0E-7)
            || !(box.maxY - box.minY >= 1.0E-7)
            || !(box.maxZ - box.minZ >= 1.0E-7)) {
            return false;
        }
        int oldX = legacyShapeBits(box.minX, box.maxX);
        int oldY = legacyShapeBits(box.minY, box.maxY);
        int oldZ = legacyShapeBits(box.minZ, box.maxZ);
        int currentX = currentShapeBits(box.minX, box.maxX);
        int currentY = currentShapeBits(box.minY, box.maxY);
        int currentZ = currentShapeBits(box.minZ, box.maxZ);
        if (oldX != currentX || oldY != currentY || oldZ != currentZ) {
            return false;
        }
        if (oldX == 0 && oldY == 0 && oldZ == 0) {
            return box.minX <= 0.5 && box.maxX >= 0.5
                && box.minY <= 0.5 && box.maxY >= 0.5
                && box.minZ <= 0.5 && box.maxZ >= 0.5;
        }
        return true;
    }

    private static int legacyShapeBits(double min, double max) {
        if (min < -1.0E-7 || max > 1.0000001) {
            return -1;
        }
        for (int bits = 0; bits <= 3; bits++) {
            int intervals = 1 << bits;
            double scaledMin = min * intervals;
            double scaledMax = max * intervals;
            if (Math.abs(scaledMin - Math.floor(scaledMin)) < 1.0E-7
                && Math.abs(scaledMax - Math.floor(scaledMax)) < 1.0E-7) {
                return bits;
            }
        }
        return -1;
    }

    private static int currentShapeBits(double min, double max) {
        if (min < -1.0E-7 || max > 1.0000001) {
            return -1;
        }
        for (int bits = 0; bits <= 3; bits++) {
            int intervals = 1 << bits;
            double scaledMin = min * intervals;
            double scaledMax = max * intervals;
            if (Math.abs(scaledMin - Math.round(scaledMin)) < 1.0E-7 * intervals
                && Math.abs(scaledMax - Math.round(scaledMax)) < 1.0E-7 * intervals) {
                return bits;
            }
        }
        return -1;
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

    public static double clipMovementAxis(@Nullable Entity entity, Direction.Axis axis,
                                          AABB moving, Iterable<VoxelShape> shapes, double distance,
                                          VanillaFn<Double> vanilla) {
        var hook = find(AxisClippingHook.class, entity);
        if (hook.isPresent()) {
            var clipped = hook.get().clip(axis, moving, shapes, distance);
            if (clipped.isPresent()) {
                return clipped.getAsDouble();
            }
        }
        return vanilla.get();
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
