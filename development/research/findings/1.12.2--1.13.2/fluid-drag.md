# F-FLUID-DRAG — 1.13.2 replaces material drag sampling with fluid-state flow sampling

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public boolean checkWaterCollisions() {`, lines 948-964.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/world/World.java`, `public boolean applyLiquidDrag(Box bounds, Material material, Entity entity) {`, lines 1579-1621.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `private void m_03231680() {`, lines 947-951.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public boolean m_69693160(Tag<Fluid> tag) {`, lines 2509-2571.

Difference: 1.12.2 delegates water contact/drag to the World material callback. 1.13.2 samples fluid states intersecting the contracted player box, accumulates fluid flow vectors and adds `0.014`-scaled flow components to velocity; it stores maximum fluid depth for other movement gates.

Movement meaning: - Reachability and dependencies: Entity fluid check is inherited by the player base tick and feeds water contact plus F-WATER-STATE.

This is not a block-state historical emulation claim.
