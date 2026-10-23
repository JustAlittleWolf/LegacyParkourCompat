package me.wolfii.legacyparkourcompat.mixin;

import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;

/** Accesses the private movement record only at the callback traversal hook. */
@Mixin(targets = "net.minecraft.world.entity.Entity$Movement")
public interface EntityMovementAccessor {
    @Accessor("from")
    Vec3 legacyParkourCompat$from();

    @Accessor("to")
    Vec3 legacyParkourCompat$to();

    @Accessor("axisDependentOriginalMovement")
    Optional<Vec3> axisDependentOriginalMovement();
}
