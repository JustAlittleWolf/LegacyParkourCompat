# Fluid movement bounded source freeze: 1.16.3 -> 1.16.4

## Identity and verification

Compared only the canonical `decompiled_minecraft/1.16.3/mojmap` and `decompiled_minecraft/1.16.4/mojmap` trees and each tree's embedded `.decompile-provenance.json`. The directory/mapping family is Mojang mappings (`mojmap`). The provenance manifest input records identify:

| Release | Client artifact object / recorded SHA-256 | Mapping (`client.txt`) object / recorded SHA-256 | Version manifest / recorded SHA-256 |
|---|---|---|---|
| 1.16.3 | `1321521b2caf934f7fc9665aab7e059a7b2bfcdf/client.jar` / `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa` | `faac5028fbca3859db970cc4ca041aeec55f6d9d/client.txt` / `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e` | `6485dd131ef68c968041a9f6fd73094b027e42e1/1.16.3.json` / `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead` |
| 1.16.4 | `1952d94a0784e7abda230aae6a1e8fc0522dba99/client.jar` / `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0` | `0837de813d1a6b67e23da3c520a84e872c8d2f0e/client.txt` / `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe` | `596ad61fda7612d9edf8881cf81869276bdb7f82/1.16.4.json` / `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5` |

For every compared Java file listed below, I independently computed SHA-256 from each canonical tree; each value matched that tree's embedded provenance `sources` entry. The corresponding values are identical across these two releases:

| Java source | SHA-256 (both releases) |
|---|---|
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/world/level/material/FluidState.java` | `665b4024a32f659d92ce1259b25f529616e44b6f3df8a6aab4a4a2c57c7bc26d` |
| `net/minecraft/world/level/material/Fluid.java` | `b37e7870f93cf1c7d4c96456a668c3f42ee48b5c5b9882e9c7cecea55717ae2f` |
| `net/minecraft/world/level/material/FlowingFluid.java` | `a85a5cd6625b1b4a047bd693399e1f78c2978ae207af927d4abfac3b34229552` |
| `net/minecraft/world/level/material/WaterFluid.java` | `d3f28e4be4be66a0e222eccad96c0c90d57f55380fdd39cf8e5840c871cc052b` |
| `net/minecraft/world/level/material/LavaFluid.java` | `6e98f387c7561bff3b66f2e86ce54bab54a8ff3337639cb106e5e6aaad8ada9b` |
| `net/minecraft/world/phys/Vec3.java` | `b889010c321c45b497e71ff782d108a718ed23a5f605d6162904045e1e01fd48` |
| `net/minecraft/world/phys/AABB.java` | `514558cf4827679d84a4debd4f400d7d65bd4b8982b7ae8124a0d83a916ba878` |

## Bounded claim and verdict

**Verdict: scoped equivalence; no 1.16.3-to-1.16.4 movement difference found in the assigned fluid slice.** This claim is conditional on equal effective world/fluid states, loaded-chunk answers, player bounding box and movement/ability/effect inputs, and the same client-controlled ordinary player state. It is not a claim about all movement or an exact introduction boundary outside these endpoints.

Reachable witness: a locally controlled, non-riding, non-flying player in a loaded area whose bounding box intersects the top of a water or lava fluid state. In both releases `Entity.tick` calls `baseTick`; `baseTick` updates fluid body state before `LivingEntity`'s living tick dispatches jump and then `travel` (`Entity.java:354-382`; `LivingEntity.java:2426-2460`). `Player.travel` delegates to `LivingEntity.travel` in the non-flying branch (`Player.java:1387-1417`). The compared inputs can therefore reach these gates in ordinary client play.

### (1) Body admission and stored water/lava height

In both releases, `Entity.updateInWaterStateAndDoFluidPushing` clears the stored height map, scans water first and lava second, using water push strength `0.014` and lava strength `0.007` in ultra-warm dimensions or `0.0023333333333333335` otherwise (`Entity.java:957-979`). The water wrapper sets `wasTouchingWater` when its scan succeeds; `isInWater()` reads that cached flag (`Entity.java:920-922`). Lava membership instead reads positive stored lava height and is false on `firstTick` (`Entity.java:1071-1072`).

For each tag, `updateFluidHeightAndDoFluidPushing` in both releases deflates the player box by `0.001`, floors minima and ceils maxima, returns false if the required chunks are unavailable, and iterates the resulting integer ranges with exclusive upper bounds. For each matching fluid state it computes `q + fluidState.getHeight(level, pos)`; a top at or above the inset box bottom admits the state. It stores the maximum `top - insetBottom` as fluid height and returns whether any state admitted. Thus partial body overlap is enough; full submersion is not required. `FluidState.getHeight` delegates to its type; `FlowingFluid.getHeight` returns `1.0F` when same fluid exists above, otherwise its own height (`amount / 9.0F`). These definitions and the `WaterFluid`/`LavaFluid` consumers have identical source hashes across the pair (`Entity.java:2641-2705`; `FluidState.java:40-42`; `FlowingFluid.java:463-475`).

### (2) Current accumulation, normalization and push

Both releases use the same nested X/Y/Z scan and fluid-flow inputs. For each admitted matching state, they update the maximum height; if `isPushedByFluid()` is true they get the state's flow, scale that flow by the current maximum height when that maximum is below `0.4`, add it to the running vector, and increment the sample count. A nonzero vector is averaged by count. The normalization branch is explicitly skipped for `Player`; non-player entities normalize. The player vector is then scaled by `d * 1.0`. If the old X and Z speeds are each below `0.003` and the scaled current length is below `0.0045000000000000005`, the current is normalized and raised to that minimum length. The result is added to current delta movement. These gates, constants and operation order are byte-identical in both `Entity.java` files (`2641-2705`). A normal non-flying player passes the push gate because `Player.isPushedByFluid()` returns `!abilities.flying` (`Player.java:1789-1791`). `FluidState.getFlow` delegates to the fluid type (`FluidState.java:82-83`); `FlowingFluid.getFlow` reads horizontal neighbors and computes the vector from neighbor own-heights (`FlowingFluid.java:52-94`).

### (3) First water/lava travel and drag/gravity gates

The `LivingEntity.travel` fluid branch is the same in both releases (`LivingEntity.java:1914-1978`). It runs only for effective AI or a locally controlled instance. It begins with gravity amount `0.08`, sets it to `0.01` and clears fall distance when descending with Slow Falling, then reads the fluid at the player's block position. Water travel requires cached `isInWater`, `isAffectedByFluids`, and failure of `canStandOnFluid`; lava requires cached positive lava height, the same affected gate, and failure of that predicate. `Player.isAffectedByFluids` is `!abilities.flying`; the inherited `canStandOnFluid` returns false for `LivingEntity` (`LivingEntity.java:1910-1912`; `Player.java:1004-1007`).

Water travel uses sprint slowdown `0.9F` or water slowdown, input acceleration `0.02F`, depth strider capped at 3 and halved off ground, and Dolphin's Grace slowdown `0.96F`. It moves, applies horizontal-climb correction, multiplies velocity by `(f, 0.8F, f)`, then applies fluid-fall adjustment. That adjustment changes vertical velocity only when gravity is enabled and the player is not sprinting; its near-terminal condition uses `bl` (`old y <= 0`), `0.005`, and `0.003`, otherwise it subtracts `d / 16.0` (`LivingEntity.java:2074-2086`).

Lava travel uses input acceleration `0.02F` and moves first. At or below the fluid-jump threshold it multiplies velocity by `(0.5, 0.8F, 0.5)` and applies the same fluid-fall adjustment; above threshold it scales the whole vector by `0.5`. It then adds `-d / 4.0` Y only if gravity is enabled. Both branches retain the same horizontal-collision escape gate. `Player.travel` has a same-source swimming pre-adjustment before calling the base travel method; the flying branch is excluded by the ordinary non-flying witness and `abilities.flying` gates.

## Coverage boundary

This freeze covers ordinary non-riding player water/lava body admission and stored height, current accumulation/normalization/push, and the first water/lava travel branches and their drag/gravity gates. It does not decide resource/archive changes, creative or Elytra movement, geometry, damage, full-pair inventory/freeze, implementation, or runtime parity.