package me.wolfii.legacyparkourcompat.mixin.network;

import me.wolfii.legacyparkourcompat.mechanic.network.ClientPacketPositionState;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/** Per-instance state replaces the old Entity packet accumulators and LivingEntity lerp fields. */
@Mixin(LocalPlayer.class)
public abstract class LocalPlayerPacketStateMixin implements ClientPacketPositionState {
    @Unique private long legacyPacketX;
    @Unique private long legacyPacketY;
    @Unique private long legacyPacketZ;
    @Unique private double legacyLerpX;
    @Unique private double legacyLerpY;
    @Unique private double legacyLerpZ;
    @Unique private double legacyLerpYRot;
    @Unique private double legacyLerpXRot;
    @Unique private int legacyLerpSteps;

    @Override public long legacyPacketX() { return this.legacyPacketX; }
    @Override public long legacyPacketY() { return this.legacyPacketY; }
    @Override public long legacyPacketZ() { return this.legacyPacketZ; }

    @Override
    public void legacyPacketCoordinates(long x, long y, long z) {
        this.legacyPacketX = x;
        this.legacyPacketY = y;
        this.legacyPacketZ = z;
    }

    @Override
    public void legacyQueue(double x, double y, double z, double yRot, double xRot, int steps) {
        this.legacyLerpX = x;
        this.legacyLerpY = y;
        this.legacyLerpZ = z;
        this.legacyLerpYRot = yRot;
        this.legacyLerpXRot = xRot;
        this.legacyLerpSteps = steps;
    }

    @Override public int legacySteps() { return this.legacyLerpSteps; }
    @Override public double legacyX() { return this.legacyLerpX; }
    @Override public double legacyY() { return this.legacyLerpY; }
    @Override public double legacyZ() { return this.legacyLerpZ; }
    @Override public double legacyYRot() { return this.legacyLerpYRot; }
    @Override public double legacyXRot() { return this.legacyLerpXRot; }
    @Override public void legacyDecrementSteps() { this.legacyLerpSteps--; }
    @Override public void legacyClearInterpolation() { this.legacyLerpSteps = 0; }
}
