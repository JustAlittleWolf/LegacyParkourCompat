package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.itemuse.MiningUseGateHook;

/**
 * Restores 1.8's ability to start item use while an ordinary survival block is being mined.
 *
 * <p>Exact source comparison (Ornithe Feather mappings): 1.8
 * {@code net.minecraft.client.Minecraft#doUse()} has no mining-state guard
 * (Minecraft.java:1166–1209). In 1.8.1 the same method wraps its whole body in
 * {@code !interactionManager.isMiningBlock()}, calling
 * {@code net.minecraft.client.ClientPlayerInteractionManager#isMiningBlock()}
 * (Minecraft.java:1169–1214); the gate remains in 1.8.2 (Minecraft.java:1189–1235).
 * The preceding attack click can set that flag before use, so this change only
 * removes the item-use admission gate for 1.8. Bow dispatch and its local
 * item-in-use state remain vanilla operations.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class MiningUseGate implements MiningUseGateHook {
    public MiningUseGate() {
    }

    @Override
    public boolean blocksItemUseWhileDestroying() {
        return false;
    }
}
