package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new NegligibleSpeed());
        registry.register(new StandingPose());
        registry.register(new NoClientEntityPush());
        registry.register(new LadderCollision());
        registry.register(new LilyPadCollision());
        registry.register(new PistonHeadCollision());
        registry.register(new AnvilCollision("minecraft:anvil"));
        registry.register(new AnvilCollision("minecraft:chipped_anvil"));
        registry.register(new AnvilCollision("minecraft:damaged_anvil"));
        registry.register(new ChestCollision("minecraft:chest"));
        registry.register(new ChestCollision("minecraft:trapped_chest"));
        for (Block block : BuiltInRegistries.BLOCK) {
            Identifier id = BuiltInRegistries.BLOCK.getKey(block);
            if (id != null && isLegacyPane(id.toString())) {
                registry.register(new PaneCollision(id.toString()));
            }
        }
    }

    private static boolean isLegacyPane(String id) {
        return "minecraft:glass_pane".equals(id)
            || "minecraft:iron_bars".equals(id)
            || id.startsWith("minecraft:") && id.endsWith("_stained_glass_pane");
    }
}
