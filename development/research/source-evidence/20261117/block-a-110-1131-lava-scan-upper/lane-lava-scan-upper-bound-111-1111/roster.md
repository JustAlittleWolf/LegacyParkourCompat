# Source roster: lava presence scan and travel consumer

Assignment: exact Minecraft 1.11 → 1.11.1, Ornithe Feather. Read-only source comparison; no aliases, wiki lanes, product implementations, other lava effects, tests, builds, or runtime.

## Evidence identities

- 1.11: `decompiled_minecraft/1.11/ornithe-feather.provenance.json`; resolved/requested `1.11`; mapping option `feather`; client SHA-256 `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634`; Feather merged mapping SHA-256 `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44`; Tiny SHA-256 `46bd6702a0983ad667109aadad90b2e6390ed5cd5999561a8a904956f4baf9c0`; source manifest SHA-256 `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`.
- 1.11.1: `decompiled_minecraft/1.11.1/ornithe-feather.provenance.json`; resolved/requested `1.11.1`; mapping option `feather`; client SHA-256 `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6`; Feather merged mapping SHA-256 `83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928`; Tiny SHA-256 `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`; source manifest SHA-256 `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d`.
- Exact source provenance reports Java 25.0.3, Gradle 9.7.1, Vineflower 1.12.0 and successful `decompileMinecraft` runs. Both ready markers name the exact release and `ornithe-feather` mapping. No per-source provenance manifest was present inside the mapping directories; six relevant source files per side were verified byte-for-byte against the published source SHA-256 manifest (all matched).

## Ordered coverage

1. Direct entity predicate: `net/minecraft/entity/Entity.java`, `isInLava()` — 1.11: lines 978–980; 1.11.1: 1037–1039. Checked the called shape operation and exact arguments.
2. Query implementation: `net/minecraft/world/World.java`, `containsMaterial(Box, Material)` — 1.11: lines 1636–1658; 1.11.1: 1615–1637. Checked scan bounds, loop bounds/order, block-state/material read, early return, mutable-position release, and area/chunk guards. Also checked `World.getBlockState(BlockPos)`, both versions lines 676–683.
3. Shape math: `net/minecraft/util/math/Box.java`, `grown(double,double,double)` — both: lines 132–140. Checked negative-delta min/max operation order.
4. Travel consumer: `net/minecraft/entity/living/LivingEntity.java`, `moveRelative(float,float)` — 1.11: lines 1386–1560; 1.11.1: 1388–1561. Checked controlling-side, water-before-lava branch order, lava branch operations, and chunk-loading guard placement in ordinary movement.
5. Player wrapper: `net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative(float,float)` — 1.11: lines 1385–1403; 1.11.1: 1387–1405. Checked abilities-flying path, `super.moveRelative` order, restoration and stats consumer.
6. Local client gate: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `isLocal()` — both: lines 323–325; `isLocallyControlled()` — both: lines 468–470. Checked both predicates return true.
7. Tick caller: `PlayerEntity.java`, `mobTick()` — both: lines 417–435. Confirmed delegation to `LivingEntity.mobTick`; that method damps sideways/forward speed, runs `flyingTick`, then calls `moveRelative`.

Excluded: other direct lava effects, other entity callers, damage/fire, water traversal, block state and block collision geometry. No terminal source in the assigned two slices was left unexamined.