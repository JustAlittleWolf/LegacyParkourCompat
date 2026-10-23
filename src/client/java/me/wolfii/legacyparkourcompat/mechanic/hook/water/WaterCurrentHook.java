package me.wolfii.legacyparkourcompat.mechanic.hook.water;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Optional;

@MechanicType("water.current_accumulation")
public interface WaterCurrentHook extends VersionedMechanic {
    boolean replacesNativeCurrent();
    Optional<Vec3> flow(Level level, BlockPos pos, BlockState state);
    Vec3 accumulate(Vec3 total, Vec3 flow);
    Vec3 impulse(Vec3 total, boolean flying);
}
