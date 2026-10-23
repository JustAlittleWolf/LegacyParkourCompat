package me.wolfii.legacyparkourcompat.mechanic.hook.swimming;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** The final feet-water gate when entering swimming, after the other entry gates pass. */
@MechanicType("swimming.entry.feet_water")
public interface SwimmingEntryFeetWaterHook extends VersionedMechanic {
    default boolean supports(ParkourVersion selected) {
        return selected.newerThanOrEqual(ParkourVersion.V1_13);
    }

    boolean test(VanillaFn<Boolean> vanilla);
}
