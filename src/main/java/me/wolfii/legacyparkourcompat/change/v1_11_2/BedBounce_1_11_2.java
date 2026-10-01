package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockBounceBehavior;

@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class BedBounce_1_11_2 implements BlockBounceBehavior {
    @Override
    public String blockId() {
        return "minecraft:red_bed";
    }

    @Override
    public float bounceRestitution(float vanilla) {
        return 0.0F;
    }
}
