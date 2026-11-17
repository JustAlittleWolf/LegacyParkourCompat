package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.explosion.ExplosionKnockbackHook;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Restores the 1.21.1 client packet response, including float payload precision
 * and the unconditional zero-vector addition when no impulse is present.
 *
 * <p>Source: 1.21.1 Mojmap {@code ClientPacketListener.handleExplosion}
 * (lines 1172–1188) adds the packet's three decoded float components to player
 * delta movement without an optional-presence gate; {@code ClientboundExplodePacket}
 * stores those fields as floats. In 1.21.2 Mojmap,
 * {@code ClientPacketListener.handleExplosion} (lines 1225–1241) conditionally
 * applies {@code Optional<Vec3>} and {@code ClientboundExplodePacket} carries
 * that vector through its double codec. The current seam is 26.2 unobfuscated
 * {@code ClientPacketListener.handleExplosion} (lines 1359–1376), where
 * {@code packet.playerKnockback().ifPresent(this.minecraft.player::addDeltaMovement)}
 * applies the local-player response.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class ExplosionKnockback implements ExplosionKnockbackHook {
    @Override
    public void apply(Optional<Vec3> knockback, Consumer<Vec3> addDeltaMovement) {
        Vec3 applied = knockback
            .map(vector -> new Vec3((float) vector.x, (float) vector.y, (float) vector.z))
            .orElse(Vec3.ZERO);
        addDeltaMovement.accept(applied);
    }
}
