package me.wolfii.legacyparkourcompat.change.v1_19_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.OnPositionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

/**
 * Applicability fence: the accepted Entity#getOnPos(float) comparison covers
 * 1.19.4 and the first changed release, 1.20 (both Mojmap), against the native
 * 26.2 unobfuscated Entity#getOnPos(float). It does not establish earlier
 * profiles. Keep their existing native fallback until independently covered;
 * this provider makes no claim that native behavior reproduces 1.19.3.
 */
@MovementChange(emulates = ParkourVersion.V1_19_3)
public final class NativeOnPosition implements OnPositionHook {
    @Override
    public BlockPos onPosition(Entity entity, float offset, VanillaFn<BlockPos> vanilla) {
        return vanilla.get();
    }
}
