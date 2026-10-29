package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Selects whether a recorded-movement callback scan shares its final query box. */
@MechanicType("entity.inside-blocks.query-box")
public interface EntityInsideBlocksQueryBoxHook extends VersionedMechanic {
    boolean shareFinalQueryBox();
}
