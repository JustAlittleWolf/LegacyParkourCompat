# Independent review verdict: fluid movement, 1.16.3 -> 1.16.4

## Immutable bindings

- Author claim: `../source/comparison.md`, SHA-256 `a7b339489e6ef5f36b41deaa3b3325e0af24b5529434ecc34f2273452da7ae09` — independently recomputed and matched.
- Blind freeze: `frozen.md`, SHA-256 `1df66a690fa77d08a4602662734c5680f753bbde974c7acdd1159b43617eb930` — independently recomputed and matched; preserved unchanged.
- Compared sources: only canonical `decompiled_minecraft/{1.16.3,1.16.4}/mojmap` Java files and each tree's `.decompile-provenance.json`. Release manifest paths identify 1.16.3 and 1.16.4. Client artifact and Mojang `client.txt` mapping hashes match the manifest records cited in both bound documents. Relevant on-disk Java hashes matched their own provenance `sources` records.

## Operation verdicts

All verdicts are **accepted as scoped equivalence**, conditional on the same effective player state, loaded-chunk answers, dimension, block/fluid states and neighbors, effects/enchantments, and current movement inputs. These are source-parity verdicts for the assigned operations only.

1. **Body admission and stored water/lava height — accepted.** `Entity.java` is byte-identical across the pair. The identical call path clears `fluidHeight`, scans water before lava, applies the same fluid-specific push amounts, and uses the same inset box, bounds, chunk check, tag test, fluid surface comparison, and maximum surface-depth store. The water flag is based on successful admission; lava membership uses positive stored height and the first-tick gate. `FluidState.getHeight` delegates to the fluid type, and the identical `FlowingFluid.getHeight` keeps the same same-fluid-above/full-height and own-height calculation. A reachable witness is a loaded, locally controlled, non-riding, non-flying player whose box has positive overlap with a water or lava surface; the same inputs reach the same admission and cached-state gates in each version.
2. **Current accumulation, normalization and push — accepted.** The identical `Entity.updateFluidHeightAndDoFluidPushing` preserves scan order, accumulated-height scaling below `0.4`, flow summing/counting, averaging, Player normalization exclusion, caller-supplied scale, low-horizontal-speed minimum push, and delta addition in the same order. The identical `Player.isPushedByFluid` permits this path when not flying. `FluidState` delegates flow to its type, and `FlowingFluid.getFlow` uses the same neighbor-derived vector. The helper math/dependency files `AABB`, `Vec3`, `Fluid`, `WaterFluid`, and `LavaFluid` were also checked: each on-disk hash matches its own provenance record and is identical across the pair.
3. **First water/lava travel and drag/gravity gates — accepted.** `Player.travel` and `LivingEntity.travel` are byte-identical across the pair. For the local player, the same player-level swim adjustment precedes base travel; the ordinary non-flying path reaches the same effective-AI/local-control gate, Slow Falling gravity selection, water-first/lava-second predicates, fluid movement, friction, fluid-fall adjustment, gravity, and collision-lift ordering. The same Player override disables fluid travel/current response only for flying. No difference in the claimed first fluid travel procedure or its drag/gravity gates is supported by these exact endpoints.

## Review evidence and residuals

The author's `Entity`, `LivingEntity`, `Player`, `FluidState`, and `FlowingFluid` SHA-256 values all matched both provenance entries and both on-disk files. Independent dependency checks added the following identical 1.16.3/1.16.4 hashes (each matched the corresponding provenance entry):

| Dependency | SHA-256 in both releases |
|---|---|
| `net/minecraft/world/level/material/Fluid.java` | `b37e7870f93cf1c7d4c96456a668c3f42ee48b5c5b9882e9c7cecea55717ae2f` |
| `net/minecraft/world/level/material/WaterFluid.java` | `d3f28e4be4be66a0e222eccad96c0c90d57f55380fdd39cf8e5840c871cc052b` |
| `net/minecraft/world/level/material/LavaFluid.java` | `6e98f387c7561bff3b66f2e86ce54bab54a8ff3337639cb106e5e6aaad8ada9b` |
| `net/minecraft/world/phys/AABB.java` | `514558cf4827679d84a4debd4f400d7d65bd4b8982b7ae8124a0d83a916ba878` |
| `net/minecraft/world/phys/Vec3.java` | `b889010c321c45b497e71ff782d108a718ed23a5f605d6162904045e1e01fd48` |

No claim-level discrepancy remains within the assigned scope. Resource/tag membership, full tick coverage, creative/Elytra behavior, geometry, damage, implementation, and runtime parity remain outside this review. No exact intermediate patch boundary is asserted.