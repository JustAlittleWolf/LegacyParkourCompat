# F-WATER-STATE — 1.13.2 adds submerged-depth and swimming state to player movement

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public boolean checkWaterCollisions() {`, lines 948-964.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public void updateSwimming() {`, lines 953-959.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public boolean isSubmergedInWater() {`, lines 943-945.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `private void m_03231680() {`, lines 947-951.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public boolean m_69693160(Tag<Fluid> tag) {`, lines 2509-2571.

Difference: 1.13.2 adds a sampled fluid depth and a swimming flag transition.

Already-swimming transition uses sprint + contact with water + not riding; entry uses sprint + submerged-in-water + not riding.

PlayerEntity suppresses swimming while flying. 1.12.2 has no submerged-depth/swimming state.

Movement meaning: - Reachability and dependencies: 1.13.2 base-tick water update runs before LivingEntity.mobTick input/jump/travel route.

State is consumed by local sprint/flight gates, PlayerEntity pose/moveRelative, and LivingEntity water jump decisions.

Full Entity/Player tick order, fluid grid boundaries and resource-data closure remain pending.

Source difference does not establish exact gameplay trajectories.

Original evidence: [F-WATER-STATE.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-STATE.md).
