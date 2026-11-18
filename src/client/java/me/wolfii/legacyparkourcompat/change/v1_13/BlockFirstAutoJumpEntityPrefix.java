package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpEntityPrefixHook;

/** 1.13.2 Feather Gen2 build.2 World.getCollisions (1754–1758) appends entity collisions. */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class BlockFirstAutoJumpEntityPrefix implements AutoJumpEntityPrefixHook { }
