package me.wolfii.legacyparkourcompat.mixin.client;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LocalPlayer.class)
public interface LocalPlayerAccessor {
    @Accessor("sprintTriggerTime")
    void legacyparkourcompat$setSprintTriggerTime(int value);
}
