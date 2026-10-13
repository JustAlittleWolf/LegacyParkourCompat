package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.LegacyParkourCompat;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/** Client collision cache maintenance after selecting a movement profile. */
public final class MovementCollisionCache {
    private MovementCollisionCache() {
    }

    /**
     * Rebuilds the empty-context collision cache used by some world queries.
     * Player movement collision uses {@code CollisionContext} and is looked up
     * live, so it updates without this. Outline/cosmetic shapes are not hooked.
     */
    public static void invalidate() {
        try {
            for (Block block : BuiltInRegistries.BLOCK) {
                for (BlockState state : block.getStateDefinition().getPossibleStates()) {
                    state.initCache();
                }
            }
        } catch (Exception exception) {
            LegacyParkourCompat.LOGGER.warn("Could not rebuild block collision cache", exception);
        }
    }
}
