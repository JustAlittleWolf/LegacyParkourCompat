package me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback;

import it.unimi.dsi.fastutil.longs.LongSet;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

@MechanicType("block_callback.contact")
public interface BlockCallbackContactHook extends VersionedMechanic {
    Optional<Boolean> visit(Entity entity, BlockPos pos, int step, boolean precise,
                          InsideBlockEffectApplier.StepBasedCollector collector, LongSet visited,
                          Consumer<BlockState> onInsideBlock, Predicate<List<AABB>> sweptIntersection);
}
