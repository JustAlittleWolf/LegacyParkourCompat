package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fishing.FishingRetrievalImpulseHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fishing.FishingRetrievalTargetHook;

/**
 * Native fallback below the proven range: 1.10–1.10.2 Ornithe Feather Gen2
 * FishingBobberEntity.pullHookedEntity/onDataValueChanged/tick/doEvent versus
 * 1.11–1.11.2 in the same mappings establishes only the 1.11 cutover.
 * No historical response is asserted for 1.9 or earlier.
 */
@MovementChange(emulates = ParkourVersion.V1_9)
public final class UnprovenFishingRetrieval implements FishingRetrievalImpulseHook, FishingRetrievalTargetHook {
}
