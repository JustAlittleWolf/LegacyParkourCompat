package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Registers one {@link me.wolfii.legacyparkourcompat.mechanic.MovementChange}
 * instance per matching block. Change classes own the predicate so
 * {@code MovementChanges} stays a list of {@code Foo.register(registry)}.
 */
public final class BlockChanges {
    private BlockChanges() {
    }

    public static void registerEach(
        MovementChangeRegistry registry,
        Predicate<Block> match,
        Function<String, Object> factory
    ) {
        for (Block block : BuiltInRegistries.BLOCK) {
            if (!match.test(block)) {
                continue;
            }
            Identifier id = BuiltInRegistries.BLOCK.getKey(block);
            if (id != null) {
                registry.register(factory.apply(id.toString()));
            }
        }
    }
}
