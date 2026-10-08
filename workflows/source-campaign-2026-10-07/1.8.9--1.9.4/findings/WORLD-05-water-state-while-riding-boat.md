# WORLD-05: boat passenger skips player water collision update

- Older version A: Java 1.8.9
- Newer version B: Java 1.9.4
- Mechanic / coverage slice IDs: player water-state and fluid-drag update; `WORLD-05`, `INV-WORLD-MOVEMENT`, `INV-STATE`, `INV-EXTERNAL`
- Classification: changed behavior
- Confidence: source-confirmed for the paired source bodies; revised-derived artifact identity caveat remains
- Applicability: player interaction with another entity
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
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/entity/Entity.java`; `net.minecraft.entity.Entity#checkWaterCollisions()Z`, lines 746-759, SHA-256 `d4c10932cb5bb1067a5b58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`.

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
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/entity/Entity.java`; `net.minecraft.entity.Entity#checkWaterCollisions()Z`, lines 849-864, SHA-256 `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`.

## Source-level difference

A always calls `World.applyLiquidDrag()` for the contracted entity box with `Material.WATER`. If water is detected, it can reset `fallDistance`, set `inWater`, and clear `onFireTimer`; the World call can also add fluid-flow velocity. B first checks `this.getMount() instanceof BoatEntity`; in that case it sets `inWater` false and skips the entire water query and its state/velocity writes. The check executes in the inherited `Entity.tick()` on both endpoints. A `World.applyLiquidDrag()` source is `World.java` lines 1486-1527 (SHA-256 `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee`); B is lines 1560-1602 (SHA-256 `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`).

## Reachability and dependencies

The local player tick reaches inherited `Entity.tick()` through the paired player/living superclass chain; `Entity.tick()` calls `checkWaterCollisions()` at A line 300 and B line 361. Under the concrete precondition that the player is mounted on a `BoatEntity` and its contracted box intersects water, A performs the water query and B skips it. A's `World.applyLiquidDrag()` calls `Block.applyMaterialDrag()` for water cells; the paired `LiquidBlock` override contributes `getFlow()` to the accumulated vector. `PlayerEntity.hasLiquidCollision()` returns `!this.abilities.flying` in both versions (A `PlayerEntity.java` lines 1617-1620; B lines 1717-1720), so for a non-flying player the world fluid-flow path may write the player's X/Y/Z velocity. Boat movement itself remains out of scope.

## Consequence and uncertainty

Source proves that B suppresses the player's water-state query, fall-distance reset, fire-timer reset and fluid-flow callback while mounted on a boat; A does not. It also proves that a non-flying player qualifies for liquid-flow velocity application when A's world scan produces a nonzero flow vector. The source does not establish a subsequent mounted-player velocity consumer or a world trajectory. Full fluid-producer and source-artifact identity dependencies remain open. No runtime behavior was tested.

## Handoff

Player state/velocity response only; no boat trajectory or boat physics is claimed. Related scope: `WORLD-02` covers the direct boat passenger position/orientation callback. Blind review is pending; first changed release remains unknown within the paired endpoints.
