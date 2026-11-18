# Frozen source scope: fluid exposure and ordinary travel

Worker: /root/fluids_scheduler/fluid_1216_1218_source
Host/environment: Windows 11 host; Windows PowerShell in D:\Javastuff\LegacyParkourCompat.
Comparison pair: 1.21.6 Mojmap -> 1.21.7 Mojmap, and 1.21.7 Mojmap -> 1.21.8 Mojmap.
Owned operation/caller contract: direct local-player body fluid exposure (water/lava contact and fluid height, current preflight/accumulation/scale), eye fluid sampling and temporal state, and the ordinary non-ability-flight water/lava travel branches. Follow only stable gates needed to reach these operations. Follow narrow FlowingFluid/FluidState behavior and vanilla fluid tag plus dimension defaults used by them. Custom datapack tag membership is conditional. Exclude portals, bubble/block callbacks, equipment and ability/sprint transitions, and solid geometry.

Frozen exact Java evidence roster, for each of 1.21.6/mojmap, 1.21.7/mojmap, and 1.21.8/mojmap:
- net/minecraft/client/player/LocalPlayer.java
- net/minecraft/world/entity/player/Player.java
- net/minecraft/world/entity/LivingEntity.java
- net/minecraft/world/entity/Entity.java
- net/minecraft/world/level/material/FlowingFluid.java
- net/minecraft/world/level/material/FluidState.java
- net/minecraft/tags/FluidTags.java
- net/minecraft/world/level/dimension/DimensionType.java

Frozen resource/default evidence roster, each version's canonical output if present:
- data/minecraft/tags/fluid/water.json, data/minecraft/tags/fluid/lava.json and their versioned vanilla tag definitions, if emitted in canonical tree;
- associated dimension type vanilla defaults only where directly consumed by frozen operations.

Input gates to verify before comparison:
- 1.21.6 Mojmap provenance SHA-256: 56B036C61094D1D936882BDF5B2B59044512189CB33ADBB075880C727DA9B19E
- 1.21.7 Mojmap provenance SHA-256: 45BF296D921B2239FD0AEDB2684E2C1CB9B7D7532B4106B8A2FD9B09309CA82D
- 1.21.8 uses version-level mojmap.ready.json, mojmap.provenance.json and mojmap.sources.sha256; verify their links and referenced artifact identities before comparison.

Coverage is limited to these operations and dependencies. No other fluid-related paths are claimed covered. This scope/roster was frozen before inspecting the paired terminal method bodies.
