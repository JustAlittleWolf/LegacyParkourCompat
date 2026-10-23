package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.swimming.SwimmingEntryFeetWaterHook;

/**
 * Minecraft 1.17 Mojmap, net.minecraft.world.entity.Entity#updateSwimming:
 * entry requires sprinting, virtual isUnderWater(), and no passenger, without
 * checking water at blockPosition. Minecraft 1.17.1 Mojmap, the same member,
 * appends level.getFluidState(blockPosition).is(FluidTags.WATER). Minecraft
 * 26.2 unobfuscated, the same member, retains that added final gate.
 *
 * <p>Only that final gate is removed. The virtual underwater call stays native:
 * in Minecraft 1.16.5 and 1.17 Mojmap and 26.2 unobfuscated,
 * net.minecraft.client.player.LocalPlayer#isUnderWater returns wasUnderwater;
 * net.minecraft.world.entity.player.Player#updateIsUnderwater populates it from
 * the previous eye-fluid state before super.tick in Player#tick. Do not replace
 * this with Entity's body-water-and-eye predicate or add another state cache.
 * Player#updateSwimming retains the flying veto. Entity#updateSwimming's
 * already-swimming branch uses sprinting, body-water and no passenger in all
 * these versions, so it also stays native.
 *
 * <p>This replaces the existing whole-update SwimmingUpdate change without
 * altering its V1_13 through V1_17 applicability; the hook retains native
 * behavior before swimming's introduction. Earlier coverage is preserved,
 * not newly accepted by the exact 1.17 to 1.17.1 comparison. The retained older
 * source references are 1.16.2 and 1.16.5 Mojmap,
 * net.minecraft.world.entity.Entity#updateSwimming and #isUnderWater.
 * Runtime parity remains separate from source correspondence.
 */
@MovementChange(emulates = ParkourVersion.V1_17)
public final class SwimmingEntryWithoutFeetWater implements SwimmingEntryFeetWaterHook {
    @Override
    public boolean test(VanillaFn<Boolean> vanilla) {
        return true;
    }
}
