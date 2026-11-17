# 1.21.5 → 1.21.6 Mojmap: fluid tags and WATERLOGGED fluid state

## Evidence identity

- Older endpoint: 1.21.5, Mojmap source at `decompiled_minecraft/1.21.5/mojmap/`. Its `mojmap.ready.json` identifies version `1.21.5`, mapping `mojmap`, source manifest SHA-256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`, and exact client SHA-256 `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`. The permitted `development/research/source-evidence/20261103/checkpoint03/1.21.5-resource-manifest.json` repeats that client pin and records the two resource paths and digests below. It records `historicalMetadataMatch: false`; the current catalog metadata is a locator, not an alternate historical identity.
- Newer endpoint: 1.21.6, Mojmap source at `decompiled_minecraft/1.21.6/mojmap/`, with hidden `.decompile-provenance.json`. The local `client.jar` matches its `client.jar.pin.json` SHA-256: `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a`. Its provenance pins the client artifact URL ending in object `740a125b83dd3447feaa3c5e891ead7fbb21ae28` to that same digest.
- The exact 1.21.6 local client ZIP was readable. No 1.21.5 raw client ZIP was present in `.minecraft-decompile-cache/1.21.5` or the artifact directory declared by the 1.21.5 source-run sidecar. Accordingly, 1.21.5 resource bytes below are verified as recorded manifest digests, not re-read from a local 1.21.5 ZIP. The 1.21.5 source manifest is published evidence of those pinned resource digests; absent local raw bytes are not evidence that a resource was removed.

## WATER tag entries — closed, no endpoint difference

- 1.21.5 manifest: `data/minecraft/tags/fluid/water.json`, SHA-256 `dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004`.
- 1.21.6 pinned client ZIP: same path exists, same SHA-256. Parsed JSON values: `minecraft:water`, `minecraft:flowing_water`.
- Exact Mojmap source `net/minecraft/data/tags/FluidTagsProvider.java`, `addTags` (line 17), is byte-identical in both endpoints (SHA-256 `ab9fa0839166ea355bd34ca6c3f789415ee5dddc93779d97c2d6f6d0a931d2b7`); it adds `Fluids.WATER` and `Fluids.FLOWING_WATER` to `FluidTags.WATER`.
- Disposition: the available exact endpoint evidence supports unchanged built-in WATER membership. Local direct ZIP re-reading of the 1.21.5 member remains unavailable.

## LAVA tag entries — closed, no endpoint difference

- 1.21.5 manifest: `data/minecraft/tags/fluid/lava.json`, SHA-256 `71f50fb9092d78260bc7434731fc5fd426a44e5284a6ad084ec71cb725630c6b`.
- 1.21.6 pinned client ZIP: same path exists, same SHA-256. Parsed JSON values: `minecraft:lava`, `minecraft:flowing_lava`.
- The byte-identical `FluidTagsProvider.addTags` source cited above adds `Fluids.LAVA` and `Fluids.FLOWING_LAVA` to `FluidTags.LAVA`.
- Disposition: the available exact endpoint evidence supports unchanged built-in LAVA membership. Local direct ZIP re-reading of the 1.21.5 member remains unavailable.

## WATERLOGGED dry/wet fluid-state dependency — closed, no endpoint difference

- `net/minecraft/world/level/material/FluidState.java`, `is(TagKey<Fluid>)` (lines 108–110), has identical SHA-256 in both endpoints (`1bfc76676cc7cf136220c05f3506ae4d931cfa33ce0833d307b28048a5d28c61`): it checks the type's built-in registry holder against the tag.
- Representative vanilla state-to-fluid implementations are byte-identical in both endpoints: `WaterloggedTransparentBlock.java` (SHA-256 `4ebddce2840903ce1749a6b094a5d96dc3484b3347b2631c0c4eefdca156fc83`), `TrapDoorBlock.java` (`dc18356849fa509dcaf15fe9d6bc4688cfcf7c6e22f443cd47a43702148b8185`) and `WallBlock.java` (`30f65426c97e2507c13e55edbd078ab86f12d24a1496adb80e5d3cd44b2880f4`). In the first class, `getFluidState` (line 53) returns `Fluids.WATER.getSource(true)` when `WATERLOGGED` is true and the superclass state otherwise. The trapdoor and wall implementations likewise return a water source for the wet state and their superclass fluid state for the dry state. This gives the wet state a type covered by the built-in WATER tag; the unchanged tag also contains flowing water for other fluid-state queries.
- 1.21.6 adds `net/minecraft/world/level/block/DriedGhastBlock.java`; no 1.21.5 counterpart exists. Its `WATERLOGGED` state and `getFluidState` (lines 169–171) use the same dry/wet pattern: wet returns `Fluids.WATER.getSource(false)`, dry delegates to the superclass. This is a newly added block implementation, not a change to the shared WATERLOGGED conversion or either default fluid tag.\n- Disposition: the checked shared dry/wet conversions and tag-membership predicate are unchanged; 1.21.6 adds the Dried Ghast waterlogged state producer described above. The result is a source-level semantic check, not a claim about datapacks, runtime fluid simulation, or overall fluid movement parity.

## Terminal scope

These three bounded checks are closed for the exact 1.21.5-to-1.21.6 endpoint comparison. They establish no source difference in the requested default tag entries or checked vanilla WATERLOGGED fluid-state dependency. They do not establish an intermediate patch boundary or any datapack/runtime/fluid-movement parity result.