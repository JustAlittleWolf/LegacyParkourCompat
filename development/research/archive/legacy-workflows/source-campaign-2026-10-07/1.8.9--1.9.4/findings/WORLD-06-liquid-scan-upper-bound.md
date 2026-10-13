# WORLD-06: liquid-drag scan excludes exact upper-bound cells in B

- Older version A: Java 1.8.9
- Newer version B: Java 1.9.4
- Mechanic / coverage slice IDs: player liquid sampling and fluid-flow accumulation; `WORLD-06`, `INV-WORLD-MOVEMENT`, `INV-COLLISION`, `INV-STATE`, `INV-EXTERNAL`
- Classification: changed behavior
- Confidence: source-confirmed for the paired source bodies; revised-derived artifact identity caveat remains
- Applicability: historical player behavior
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

### A artifact identity

- Evidence artifact record ID: pair source record A (run.md Artifact Manifest, 1.8.9)
- Publication status: revised-derived
- Revision ID: `feather-r1-2026-10-07`
- Immutable evidence path: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.8.9/ornithe-feather/client-ornithe-feather.jar`
- Evidence artifact SHA-256: `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`
- Evidence manifest path / SHA-256: revision `artifact.sha256` / revision JSON SHA-256 `95e2dc4aa3edba2d287f2bab092c61c0f66874f790af1b8c5d98196c980e105d`
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.8.9/artifacts.sha256` / `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`
- Original derived-artifact availability and SHA-256 or expected hash: unavailable; originally recorded SHA-256 `e36a366fd30d0adda0a803a4956782bab1f67cd644e396d4db6a1cd885548c09`
- Source/raw-input hash relation and verification reference: source manifest `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather.sources.sha256`, SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; all cited source rows and raw inputs matched the admitted records.
- Revised-to-original derived-artifact equivalence and evidence reference: unproven; see run.md revised derived-artifact snapshot section.
- Provenance limitations: no metadata-only or source-equivalent claim for the unavailable original derived JAR.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/world/World.java`; `net.minecraft.world.World#applyLiquidDrag(Box,Material,Entity)Z`, lines 1486-1527, SHA-256 `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee`.

### B artifact identity

- Evidence artifact record ID: pair source record B (run.md Artifact Manifest, 1.9.4)
- Publication status: revised-derived
- Revision ID: `feather-r1-2026-10-07`
- Immutable evidence path: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.9.4/ornithe-feather/client-ornithe-feather.jar`
- Evidence artifact SHA-256: `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`
- Evidence manifest path / SHA-256: revision `artifact.sha256` / revision JSON SHA-256 `df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915`
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.9.4/artifacts.sha256` / `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`
- Original derived-artifact availability and SHA-256 or expected hash: unavailable; originally recorded SHA-256 `0df10c862f7fd4848d08597a76c0cf02cec5a9d9886975a57230a033cfb86b3a`
- Source/raw-input hash relation and verification reference: source manifest `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather.sources.sha256`, SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; all cited source rows and raw inputs matched the admitted records.
- Revised-to-original derived-artifact equivalence and evidence reference: unproven; see run.md revised derived-artifact snapshot section.
- Provenance limitations: no metadata-only or source-equivalent claim for the unavailable original derived JAR.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/world/World.java`; `net.minecraft.world.World#applyLiquidDrag(Box,Material,Entity)Z`, lines 1560-1602, SHA-256 `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`.

## Source-level difference

A derives each exclusive loop end as `MathHelper.floor(bounds.maxAxis + 1.0)` for X/Y/Z. B derives it as `MathHelper.ceil(bounds.maxAxis)`. For non-integer maxima these values match; at an exact integer maximum A visits the cell at that coordinate while B excludes it. A and B use the same lower bound `floor(bounds.minAxis)`. Both then filter candidate blocks by material and the liquid-height test before setting `bl` and invoking `Block.applyMaterialDrag()`; both normalize and add the resulting vector at scale `0.014` when `entity.hasLiquidCollision()`.

The bounding box comes from the player/entity shape grown by `(0.0, -0.4F, 0.0)` and contracted by `0.001` on each axis. A `Box.contract(double,double,double)` adds each amount to minima and subtracts it from maxima; B `Box.contract(double)` delegates to symmetric expansion with the negative amount, so the input bounds otherwise match.

## Reachability and dependencies

Local player tick -> inherited `Entity.tick()` -> `checkWaterCollisions()` -> `World.applyLiquidDrag()` -> liquid block's `applyMaterialDrag()`/`getFlow()` -> possible player velocity write. The normal, non-boat route is present in both endpoints; B's boat early branch is documented separately in `WORLD-05`. A player not flying passes `PlayerEntity.hasLiquidCollision()` in both versions. Concrete boundary precondition: at least one contracted box maximum is an exact integer and the world cell at that coordinate is water with its liquid surface meeting the scan's vertical bound. A scans that cell; B does not. The candidate is client-side source evidence; server-provided world state and subsequent trajectory are not established here.

## Consequence and uncertainty

Source proves the differing upper-bound calculation and the one-cell inclusion rule at exact integer maxima. If the included cell passes the liquid material/height filter, A can set the water result and invoke its liquid drag callback for a cell B omits. Any resulting flow vector depends on paired `LiquidBlock.getFlow()` and neighboring state predicates; no trajectory is inferred. Revised source snapshots are verified against source manifests, while original mapped-JAR identity remains unproven. No runtime behavior was tested.

## Handoff

Independent of the boat guard in WORLD-05; shared water-state/drag path. Full liquid flow/provider inventory and blind review remain pending. First changed release remains unknown within the paired endpoints.
