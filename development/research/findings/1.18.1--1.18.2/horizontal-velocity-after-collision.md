# Horizontal velocity after collision

1.18.1, Mojmap. `net/minecraft/world/entity/Entity.java`, `move`, lines 553–600, and `net/minecraft/util/Mth.java`, `equal(double, double)`, lines 194–196.

1.18.2, Mojmap. `net/minecraft/world/entity/Entity.java`, `move`, lines 555–610, and `net/minecraft/util/Mth.java`, `equal(double, double)`, lines 194–196.

After the fall check and removal guard, 1.18.1 snapshots the current velocity and compares the requested and clipped components with exact double inequality. It clears X first and then clears Z using the same snapshot. 1.18.2 computes the axis mismatch flags with `!Mth.equal` when setting `horizontalCollision`, using a strict absolute tolerance of the double-promoted `1.0E-5F`. After the fall check and removal guard it clears only the flagged components, together in one velocity write. Both releases perform the applicable vertical landing and step callbacks afterward.

When a moving player hits perpendicular walls tall enough to prevent stepping, and clips on both horizontal axes, 1.18.1 keeps the original X velocity because the second write restores it. 1.18.2 clears X and Z when both differences meet the tolerance. For a single nonzero clipping difference smaller than the tolerance, 1.18.1 also clears the clipped axis while 1.18.2 preserves it. This changes the velocity carried into the remainder of travel and later ticks.
