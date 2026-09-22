package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.ClientMoveInputBehavior;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.Vec2;

/** Earlier keyboard input kept cardinal components at 1 on diagonal movement. */
@MovementChange(emulates = ParkourVersion.V1_20)
public final class RawKeyboardInput implements ClientMoveInputBehavior {
    @Override
    public Vec2 moveVector(Input keys, Vec2 vanilla) {
        float left = (keys.left() ? 1.0F : 0.0F) - (keys.right() ? 1.0F : 0.0F);
        float forward = (keys.forward() ? 1.0F : 0.0F) - (keys.backward() ? 1.0F : 0.0F);
        return new Vec2(left, forward);
    }
}
