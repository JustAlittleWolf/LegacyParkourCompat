package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerRestSafetyHook;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;

import java.util.function.BooleanSupplier;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.11.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   public PlayerEntity.SleepAllowedStatus trySleep(BlockPos pos) {; lines 1194-1249.
 *   Source SHA-256: 87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b.
 * 1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   public PlayerEntity.SleepAllowedStatus trySleep(BlockPos pos) {; lines 1185-1243.
 *   Source SHA-256: e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class SleepSafetyTransition implements PlayerRestSafetyHook {
    @Override
    public boolean preventsRest(Monster monster, BooleanSupplier vanilla) {
        if (monster instanceof ZombifiedPiglin) {
            return true;
        }
        return vanilla.getAsBoolean();
    }
}
