package me.wolfii.legacyparkourcompat.change.common;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import java.util.Objects;

/** Shared applicability for the identical historical chest-Elytra start gates. */
public final class HistoricalElytra {
    private HistoricalElytra() {}

    public static boolean applies(Player player, ParkourVersion selected) {
        var chest = player.getItemBySlot(EquipmentSlot.CHEST);
        return selected.newerThanOrEqual(ParkourVersion.V1_9)
            && chest.is(Items.ELYTRA)
            && LivingEntity.canGlideUsing(chest, EquipmentSlot.CHEST)
            && Objects.equals(chest.get(DataComponents.MAX_DAMAGE),
                Items.ELYTRA.getDefaultInstance().get(DataComponents.MAX_DAMAGE));
    }
}
