package me.wolfii.legacyparkourcompat.mechanic.hook.shulker;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.phys.Vec3;

/** Supplies the historical opening-push vector for an admitted player. */
@MechanicType("shulker.opening_push_amount")
public interface ShulkerOpeningPushAmountHook extends VersionedMechanic {
    Vec3 pushAmount(ShulkerBoxBlockEntity box, Entity player, Vec3 nativeAmount);
}
