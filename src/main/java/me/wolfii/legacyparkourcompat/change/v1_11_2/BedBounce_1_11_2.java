package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockBounceBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class BedBounce_1_11_2 implements BlockBounceBehavior {
    @Override
    public String blockId() {
        return "minecraft:red_bed";
    }

    @Override
    public float bounceRestitution(float vanilla, Entity entity) {
        return entity instanceof Player ? 0.0F : vanilla;
    }
}
