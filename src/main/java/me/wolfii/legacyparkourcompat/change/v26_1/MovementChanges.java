package me.wolfii.legacyparkourcompat.change.v26_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockLandingBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlimeBlock;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new CollisionRestitution());
        for (Block block : BuiltInRegistries.BLOCK) {
            boolean slime = block instanceof SlimeBlock;
            if (slime || block instanceof BedBlock) {
                Identifier id = BuiltInRegistries.BLOCK.getKey(block);
                if (id == null) {
                    throw new IllegalStateException("Registered bounce block has no identifier: " + block);
                }
                registry.register(
                    BlockLandingBehavior.class,
                    ParkourVersion.V26_1,
                    new BlockLanding(id.toString(), slime)
                );
            }
        }
    }
}
