# 1.13.2 water-current player velocity impulse

Status: source-only bounded producer finding; independent review pending.

## Scope and catalog linkage

This memo isolates the direct player-velocity producer in the catalog's open **Water and Lava** row (article 3122), for the 1.13.2 Ornithe Feather source set. It covers the flow vector supplied for each intersecting water cell and the impulse added by `Entity.m_69693160(FluidTags.WATER)`.

This is not a finding about the complete fluid overlap geometry, every flow-height boundary, or a cross-version introduction/cutover. Those boundaries and release deltas remain open. It does not change the catalog, resolve any other producer or consumer, or claim runtime parity.

## Source identity

The ready source root is `build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather`. The ready marker raw SHA-256 is `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`; the source manifest raw SHA-256 is `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

Individually checked source files, each matching the source manifest:

| Source file | SHA-256 |
| --- | --- |
| `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| `net/minecraft/fluid/FlowingFluid.java` | `2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253` |
| `net/minecraft/fluid/state/FluidState.java` | `05e2df9377a3bc1e91645ce4282bcaa4edebe93d7ef1907407961baaecff23a6` |
| `net/minecraft/fluid/WaterFluid.java` | `183894d0a4f7a85becd97eb2c0f6cedc1d5adc4648dc6d3d2b5624df173df28b` |

The separately recorded 1.13.2 mapped-JAR manifest mismatch means that JAR is not used here for bytecode corroboration. This finding rests on the individually manifest-matched ready source files above.

## Observed operation

`Entity.tick()` calls `baseTick()`, and `baseTick()` reaches `checkWaterState()`. The water branch calls `m_69693160(FluidTags.WATER)` (`Entity.java:961–965`, `2509–2569`). The method contracts the entity shape by `0.001`, scans candidate cells, and considers tagged fluid cells whose surface reaches the contracted box's lower Y. Its water-presence result and stored water depth are adjacent behavior, but this memo only adjudicates the current-to-velocity operation.

For a cell that passes those checks, the method obtains `fluidState.getFlow(world, pos)`. It scales the vector by the current maximum intersecting fluid depth `d` when `d < 0.4`, adds it to an accumulated vector, and increments the cell count. If the accumulated vector has positive length, it averages it by `1/o` when `o > 0`. It then normalizes only when the entity is not a `PlayerEntity`. Finally it adds `0.014` times that vector directly to `velocityX`, `velocityY`, and `velocityZ` (`Entity.java:2521–2566`).

For players, `hasLiquidCollision()` is overridden to return `!abilities.flying` (`PlayerEntity.java:1826–1828`). That boolean gates whether a qualifying intersecting cell contributes its flow vector. It does not suppress water detection or the final impulse block if another route produces a nonzero accumulated vector; with this method's own accumulation, a flying player receives no current vector from these cells. The non-normalization for players preserves the magnitude after averaging and shallow-depth scaling.

`FluidState.getFlow(world,pos)` delegates to its fluid's `getFlow` (`FluidState.java:67–68`). `FlowingFluid.getFlow` derives a horizontal vector from neighboring fluid-height differences and returns its normalized vector. For a falling fluid adjacent to a solid face, it normalizes the horizontal vector, adds `(0,-6,0)`, then normalizes the resulting vector (`FlowingFluid.java:54–98`). `WaterFluid` inherits this implementation; it has no replacement flow equation in this source set. This identifies the flow-vector producer used by the impulse, without claiming the full neighbor eligibility or exact surface-height boundary audit is complete.

The direct velocity write makes this operation player-movement eligible. Player tick calls `super.tick()` (`PlayerEntity.java:208`), whose inherited entity tick path reaches the water-state check. This establishes the source call path, not a runtime trajectory result.

## Finding boundary

- Source-supported: 1.13.2 player water-current accumulation, shallow-depth scale, averaging, player normalization exception, flying gate, `0.014` velocity increment, and the delegated `FlowingFluid` vector algorithm described above.
- Still open: complete fluid-cell scan/height-boundary coverage; all neighboring flow-eligibility and solid-face edge cases; corresponding lava behavior; exact historical changes at earlier/later releases; independent review; and runtime/TAS parity.
- Excluded due to the separate provenance incident: bytecode corroboration from the 1.13.2 mapped JAR until the prep owner reconciles its artifact publication.
