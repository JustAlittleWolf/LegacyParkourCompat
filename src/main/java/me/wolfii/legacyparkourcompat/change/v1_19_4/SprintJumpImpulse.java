package me.wolfii.legacyparkourcompat.change.v1_19_4;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.SprintJumpImpulseHook;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   jumpFromGround; lines [[1955, 1965]].
 *   Source SHA-256: c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165.
 * Candidate locators only: operation fidelity, independent review and profile applicability remain open.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class SprintJumpImpulse implements SprintJumpImpulseHook {
    @Override
    public Vec3 impulse(LivingEntity entity, Vec3 vanilla) {
        float angle = entity.getYRot() * (float)(Math.PI / 180.0);
        return new Vec3(-me.wolfii.legacyparkourcompat.change.common.LegacyTrig.sin(angle) * 0.2F, 0.0, me.wolfii.legacyparkourcompat.change.common.LegacyTrig.cos(angle) * 0.2F);
    }
}
