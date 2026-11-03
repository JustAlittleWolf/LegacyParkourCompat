package me.wolfii.legacyparkourcompat.mechanic.hook.itemuse;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Controls whether mining state suppresses the client's item-use start operation. */
@MechanicType("client.item_use.mining_gate")
public interface MiningUseGateHook extends VersionedMechanic {
    boolean blocksItemUseWhileDestroying();
}
