package me.wolfii.legacyparkourcompat.mechanic.hook.equipment;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

@MechanicType("equipment.use")
public interface EquipmentUseHook extends VersionedMechanic {
    @Nullable InteractionResult result(ParkourVersion target, EquipmentSlot slot, ItemStack held, ItemStack equipped);
}
