package me.wolfii.legacyparkourcompat.mechanic.hook.item;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.item.ItemStack;

/** Resolves the durability exception that permits a charged Riptide launch. */
@MechanicType("item.trident.use_durability")
public interface TridentUseDurabilityHook extends VersionedMechanic {
    boolean allowsUse(ItemStack stack, float riptideStrength);
}
