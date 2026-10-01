package me.wolfii.legacyparkourcompat.change.pending;

import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.ClientInputBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FlightFallDistanceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.MovementChunkLookupBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.NaturalRegenerationBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintDurationBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Source-confirmed 1.8 behavior formulas awaiting the exact group-base boundary
 * check. This class is deliberately not annotated or registered yet.
 */
public final class MovementChanges_1_8 {
    private MovementChanges_1_8() {
    }

    public static final class SprintDuration implements SprintDurationBehavior {
        private final Map<Player, Integer> sprintTimers = new WeakHashMap<>();

        @Override
        public void onSetSprinting(Player player, boolean sprinting) {
            this.sprintTimers.put(player, sprinting ? 600 : 0);
        }

        @Override
        public void tick(Player player) {
            int timer = this.sprintTimers.getOrDefault(player, 0);
            if (timer > 0) {
                timer--;
                this.sprintTimers.put(player, timer);
                if (timer == 0) {
                    player.setSprinting(false);
                }
            }
        }
    }

    public static final class CreativeFlightSneakInput implements ClientInputBehavior {
        @Override
        public Vec2 modify(
            Vec2 raw,
            Vec2 vanilla,
            boolean usingItem,
            boolean movingSlowly,
            float sneakingSpeed,
            boolean flyingSneak
        ) {
            return flyingSneak ? vanilla.scale(0.3F) : vanilla;
        }
    }

    public static final class TruncatedMovementChunkLookup implements MovementChunkLookupBehavior {
        @Override
        public boolean hasChunkAt(LivingEntity entity, Level level, BlockPos vanillaPosition, VanillaFn<Boolean> vanilla) {
            if (!level.isClientSide()) {
                return vanilla.get();
            }
            return level.hasChunkAt(new BlockPos((int) entity.getX(), 0, (int) entity.getZ()));
        }
    }

    public static final class FlightFallDistance implements FlightFallDistanceBehavior {
        @Override
        public void beforeMovement(Player player, VanillaCall vanilla) {
            // The historical reset runs after the player's movement operation.
        }

        @Override
        public void afterMovement(Player player) {
            player.resetFallDistance();
        }
    }

    public static final class NaturalRegeneration implements NaturalRegenerationBehavior {
        @Override
        public boolean isHurtForFastRegeneration(boolean vanilla) {
            return false;
        }

        @Override
        public float slowRegenerationExhaustion(float vanilla) {
            return 3.0F;
        }
    }
}
