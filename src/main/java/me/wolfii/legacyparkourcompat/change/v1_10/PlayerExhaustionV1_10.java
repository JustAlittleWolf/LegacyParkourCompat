package me.wolfii.legacyparkourcompat.change.v1_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.MovementExhaustionBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class PlayerExhaustionV1_10 implements MovementExhaustionBehavior {
    @Override
    public float jumpExhaustion(Player player, JumpKind kind, float vanilla) {
        return kind == JumpKind.SPRINTING ? 0.8F : 0.2F;
    }

    @Override
    public float movementExhaustionFactor(Player player, MovementKind kind, float vanilla) {
        return switch (kind) {
            case SWIMMING, UNDERWATER_WALKING, WATER_WALKING -> 0.015F;
            case SPRINTING -> 0.099999994F;
            case SNEAKING -> 0.005F;
            case WALKING -> 0.01F;
        };
    }
}
