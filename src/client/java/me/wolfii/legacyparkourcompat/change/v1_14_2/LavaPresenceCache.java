package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.LavaPresenceScan;
import me.wolfii.legacyparkourcompat.mechanic.fluid.LavaPresenceLatchContext;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaMaterialScanUpperBoundHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaPresenceHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import java.util.Optional;

/**
 * 1.14 through 1.14.4 clears the lava flag after movement collision resolution,
 * scans the 0.001-inset box when loaded, and exposes that cached result to
 * travel and jump predicates. The same operation persists through 1.14.4.
 * Sources: 1.14.4 Ornithe Feather Gen2
 * {@code net/minecraft/entity/Entity#move/checkBlockCollisions/isInLava},
 * {@code net/minecraft/block/LiquidBlock#onEntityCollision}; 26.2 unobfuscated
 * {@code net/minecraft/world/entity/Entity#move/isInLava}.
 *
 * <p>The resolver annotation is the latest verified release in this unchanged
 * 1.14 operation range; it also covers the V1_14 and V1_14_1 aliases.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class LavaPresenceCache implements LavaPresenceHook {
    @Override
    public Optional<Boolean> isInLava(Entity entity, Optional<LavaMaterialScanUpperBoundHook> scanUpperBound) {
        if (!(entity instanceof LocalPlayer) || !entity.level().isClientSide()) {
            return Optional.empty();
        }
        if (!(entity instanceof LavaPresenceLatchContext context)) {
            throw new IllegalStateException("Local player is missing lava-presence cache state");
        }
        return Optional.of(context.legacyparkourcompat$isLavaPresenceLatched());
    }

    @Override
    public void afterMove(Entity entity) {
        if (!(entity instanceof LocalPlayer) || !entity.level().isClientSide() || entity.noPhysics) {
            return;
        }
        if (!(entity instanceof LavaPresenceLatchContext context)) {
            throw new IllegalStateException("Local player is missing lava-presence cache state");
        }

        context.legacyparkourcompat$setLavaPresenceLatched(false);
        context.legacyparkourcompat$setLavaPresenceLatched(
            LavaPresenceScan.containsInclusive(entity, entity.getBoundingBox().deflate(0.001), true)
        );
    }
}
