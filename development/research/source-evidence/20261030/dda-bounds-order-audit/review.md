# Independent audit: 1.8.9 shared bounds lifecycle

## Verdict

Accept the report's conditional source claim: for the registered 1.8.9 chest, trapped chest, and anvil singleton objects, a bounds write for position P can remain in the instance fields and be read by ordinary movement collision collection at position Q without a refresh at Q. The source proves the lifecycle under those stated calls and positions. It does not prove how often a normal camera/render schedule produces that sequence or that a particular movement trajectory encounters it.

This is not an implementation-readiness verdict. The evidence is a 1.8.9 lifecycle trace plus only the 26.2 `Minecraft.tick` scheduling slice. It does not compare 26.2 collision-shape providers or their movement readers with 1.8.9, establish which modern operation needs emulation, or close a cross-version applicability gate.

## Evidence retraced

- In 1.8.9 `Block.java:942,1141-1142`, registrations 54 and 146 construct distinct `ChestBlock(0)` and `ChestBlock(1)` instances; registration 145 constructs one `AnvilBlock`. `ChestBlock.java:32-37,56-68` shows its constructor initializing single-chest bounds and `updateShape` writing its own bounds based on neighboring blocks in north, south, west, east order, with single-chest fallback. `AnvilBlock.java:67-73` writes one of two bounds based on the facing axis at the supplied position. `Block.java:198-202` initializes a base instance to a full cube.
- `Block.java:456-465` calls virtual `updateShape(world, pos)` before clipping against the instance bounds. `Entity.java:1029-1034` sends the camera ray to `World.rayTrace` with `false, false, true`; `World.java:648-650,743-749` shows that the false collision filter allows eligible ray-traceable cells to proceed to `Block.rayTrace`. The ray stops at a hit or leaves its final visited write on a miss.
- Ordinary movement collection is `Entity.java:439-459,469` → `World.java:891-923` → `Block.java:339-350`. `Block.getCollisionShape` builds a box by translating the current instance fields by Q and does not refresh them. Neither `ChestBlock` nor `AnvilBlock` overrides these inherited methods or `resetShape`; base `Block.resetShape` is empty (`Block.java:654-655`). Thus a prior P write survives this read if no intervening writer refreshes the same instance.
- The ordinary tick ordering is correct with its gates stated: `Minecraft.tick` invokes camera `pick(1.0F)` at 1.8.9 line 1314 before its world entity-tick block at lines 1610-1636. Picking can do work only with a camera and world (`GameRenderer.java:318-325`). `world.tickEntities()` runs only with a non-null world while unpaused, and ticks entities in its active loops (`World.java:1159+`). The local player's own `tick` checks that its current chunk is loaded before calling `super.tick` (`LocalClientPlayerEntity.java:105-115`); `PlayerEntity.tick` and `LivingEntity.tick` reach `mobTick` (`PlayerEntity.java:183-228`; `LivingEntity.java:1259-1296`). The local mob-tick override reaches `PlayerEntity.mobTick` and then `LivingEntity.mobTick` (`LocalClientPlayerEntity.java:632`; `PlayerEntity.java:431-448`), which reaches `moveRelative` (`LivingEntity.java:1446-1451`). That method reaches `Entity.move` in the applicable locally controlled, medium-specific branches (`LivingEntity.java:1119-1123,1165,1190,1218`); the local-player override reports locally controlled (`LocalClientPlayerEntity.java:381-383`). This establishes ordering for eligible movement work, not a guarantee that every tick moves or that the ray visits a movement candidate.
- The current 26.2 `Minecraft.java` slice has `pick(1.0F)` at line 1787 before the `!pause && level != null` entity-tick block and `level.tickEntities()` at line 1809. This confirms only the stated scheduling contract.
- The four local-player `pushAwayFrom` calls are at `LocalClientPlayerEntity.java:546-549`. The override gates on `noClip`, then calls `canSurvive`; that method reads each block's `isSolid()` predicate from block state (`LocalClientPlayerEntity.java:292-345`). It does not use collision lists, `getBlockCollisions`, or `isFullCube`.

## Limits and remaining gates

The report's limits are appropriate: no runtime frequency, actual ray visitation, render/tick interleaving outside `Minecraft.tick`, or guaranteed P-to-Q player trajectory is established. The conditional equal-instance lifecycle itself needs no additional server-side producer to be true. Before using it to implement a compatibility change, compare the corresponding newer-version providers, readers, and call gates, and identify the concrete movement operation and applicability interval. The 26.2 scheduling match alone does not close those gates.

The top-level `artifact-hashes.sha256` is the ledger location in this artifact; it binds `report.md`, `source-hashes.sha256`, and `accepted-input-hashes.sha256`. All three listed hashes matched. Every listed 1.8.9 source-file digest and the 26.2 `Minecraft.java` digest matched the canonical read-only tree. The 1.8.9 and 26.2 source-manifest and ready/provenance digests also matched the values in the source ledger. Caller-chain files independently checked for this review are bound below.

## Independent caller-chain source hashes

`D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java` — SHA-256 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`.

`D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` — SHA-256 `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`.

Static source review only. No product implementation, wiki, backlog, Git history, tests, builds, decompilation, game launch, or runtime evidence was consulted or produced.
