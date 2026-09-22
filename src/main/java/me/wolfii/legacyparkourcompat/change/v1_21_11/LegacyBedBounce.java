package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.BlockChanges;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockBounceBehavior;
import net.minecraft.world.level.block.BedBlock;

/** Beds used 0.66F restitution through 1.21.11; 26.2 uses 0.75F. */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class LegacyBedBounce implements BlockBounceBehavior {
    private final String blockId;

    public LegacyBedBounce(String blockId) {
        this.blockId = blockId;
    }

    public static void register(MovementChangeRegistry registry) {
        BlockChanges.registerEach(registry, block -> block instanceof BedBlock, LegacyBedBounce::new);
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public float bounceRestitution(float vanilla) {
        return 0.66F;
    }
}
