# F022: Contextual fluid height in the local submerged-water cache

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: cached eye-fluid submersion input to swimming/sprint predicates; S051
- Classification: changed behavior candidate; fluid-state reachability remains unresolved
- Confidence: candidate (paired source and hashes verified; producer/synchronization dependency open; independent review open)
- Applicability: historical player behavior, conditional on the exact eye-cell fluid-state pair
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A source root: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `Entity.java`::`m_61708540` and `isSubmergedIn`, lines 979-981 and 1042-1051, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`; `FlowingFluid.java`::`getSpreadFluid` and `getHeight`, lines 188-196 and 448-450, SHA-256 `2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253`; `SlabBlock.java`::`getFluidState`, lines 132-134, SHA-256 `2e7ff3fcfa5faa83aa4af7ea9b68f07af8d8fe7047e08ee3a9d7d37b78c7128f`; `LocalClientPlayerEntity.java`::`tick`, lines 181-198, SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
- B source root: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `Entity.java`::`m_61708540`, `isSubmergedIn`, and `m_69330161`, lines 974-976 and 1035-1052, SHA-256 `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`; `FlowingFluid.java`::`getSpreadFluid`, `m_50081546`, and `getHeight`, lines 181-189 and 442-454, SHA-256 `6afb033d18e6d28ff6d7deda88f5f1432914903b0fef100c44fb8372d3e148ce`; `SlabBlock.java`::`getFluidState`, lines 93-95, SHA-256 `91f2ebc592e5cb07d5ce8a6faa123ec0d4b7bf10aa6323d6aceb343bc0f73a53`; `LocalClientPlayerEntity.java`::`tick`, lines 178-196, SHA-256 `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce`.
- A artifact note: the source tree and file hashes match the manifest. The source owner published revised A derived artifact `feather-r1-2026-10-07`; the original derived JAR is unavailable and equivalence is unproven. This finding relies on the cited, manifest-verified decompiled source files and makes no byte-equivalence claim about the unavailable original JAR.
- B artifact note: source tree and cited file hashes match the paired 1.14.4 Feather manifest.

## Source-level difference

A `Entity.isSubmergedIn` tests the eye-cell fluid tag and compares the eye Y against `blockY + (fluidState.getHeight() + 0.11111111F)`. A's `FlowingFluid.getHeight(FluidState)` is `state.getLevel() / 9.0F` and has no world context. B's base-tick helper calls `m_69330161(tag, true)`, which applies the same eye-cell test plus a loaded-chunk guard, then calls `FluidState.getHeight(world,pos)`. B's `FlowingFluid.getHeight` returns `1.0F` when the eye-cell fluid and the fluid above are the same exact fluid instance; otherwise it delegates to the same level/9 calculation.

The source-visible conditional predicate difference is therefore: for an eye-cell state with level below 8, the same flowing-fluid instance above, and an eye fraction greater than A's `level/9.0F + 0.11111111F` but below 1, B can return true where A returns false. B's extra chunk guard cannot reject the ordinary local-player path: `LocalClientPlayerEntity.tick` checks that the chunk at the player's X/Z is loaded before calling the superclass tick, and the eye query has the same X/Z.

## Reachability and dependencies

`Entity.baseTick` refreshes `submergedInWater` before `updateSwimming` and the local player's `mobTick`; the cached value feeds `isSubmergedInWater`, sprint gates and water travel. Both `FlowingFluid.getSpreadFluid` methods set a downward level-8 state when the same flowing-fluid instance above can flow upward. Waterlogged slabs produce the source-water instance. A lower-level flowing state with that same flowing-fluid instance above would therefore require a precise transition or a source configuration not yet closed by this slice. The paired decompiled sources do not establish whether the player client can observe that exact combination before the fluid update/synchronization path repairs it. `INV-TICK`, `INV-STATE`, `INV-WORLD-MOVEMENT`, and `INV-EXTERNAL` remain open for fluid-state timing, tags, client synchronization, and complete submerged-state producer/consumer closure.

## Consequence and uncertainty

The source proves different height calculation and a conditional boolean outcome. It does not yet prove that the distinguishing eye-cell state is reachable on the local player during a vanilla tick, nor that a stable-world movement outcome differs. Treat the movement consequence as conditional; no runtime trajectory is claimed.

## Handoff

Candidate only. Independent arithmetic/source review and exact fluid producer/synchronization closure are open. No implementation handoff is ready.
