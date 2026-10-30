package me.wolfii.legacyparkourcompat.change.v1_13_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jumpboost.JumpBoostEffectReceiverHook;
import net.minecraft.world.entity.player.Player;

import java.util.OptionalInt;

/**
 * Lower resolver fence so this change does not affect profiles before the verified
 * 1.14.x receiver. Boundary evidence is 1.14.4 Mojmap ClientPacketListener
 * handleUpdateMobEffect/handleRemoveMobEffect and LivingEntity tickEffects/jumpFromGround,
 * compared with current 26.2 unobfuscated ClientPacketListener handlers and
 * LivingEntity tickEffects/getJumpBoostPower.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class NativeJumpBoostEffectReceiver implements JumpBoostEffectReceiverHook {
    public NativeJumpBoostEffectReceiver() {
    }

    @Override
    public void receiveUpdate(Player player, int amplifier, int duration) {
    }

    @Override
    public void receiveRemoval(Player player) {
    }

    @Override
    public void tick(Player player) {
    }

    @Override
    public OptionalInt amplifier(Player player) {
        return OptionalInt.empty();
    }
}
