package me.wolfii.legacyparkourcompat.mechanic.hook.item;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** Resolves the durability rejection used when a player starts using a trident. */
@MechanicType("item.trident.use_durability")
public interface TridentUseDurabilityHook extends VersionedMechanic {
    /** Returns the use rejection while preserving the native result for ordinary throws. */
    boolean rejectsUse(Player player, ItemStack stack, boolean vanillaResult);
}
