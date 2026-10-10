# F-010: Honey Block contact scan reaches a narrower player-overlap band

- Older version A: 1.19.2
- Newer version B: 1.19.3
- Mechanic / coverage slice IDs: post-move inside-block scan and Honey Block slide response; S4-honey-contact-scan, S4-honey-slide-response
- Classification: changed behavior
- Confidence: source-confirmed conditional player movement response
- Applicability: historical player behavior, when the post-move player AABB overlaps a registered Honey Block cell by more than `1.0E-7` and less than `0.001` on an axis, and Honey Block's airborne/downward/side-distance predicates pass
- First changed release: unknown within (1.19.2, 1.19.3]
- Runtime validation: not performed
- Manifest artifact reference: `../run.md` Artifact manifest A/B sections; aligned original Mojmap source publications

## Paired evidence

### A artifact identity

- Evidence artifact record ID: original A publication, `../run.md` Artifact manifest A
- Publication status: original-verified
- Revision ID (revised evidence only): not applicable
- Immutable evidence path: `build/movement-campaign-2026-10-07/ready/1.19.2/mojmap/`
- Evidence artifact SHA-256: source manifest `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`
- Evidence manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.19.2/mojmap.sources.sha256` / `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.19.2/artifacts.sha256` / `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490`
- Original derived-artifact availability and SHA-256 or expected hash: published Mojmap source tree and mapped jar available; mapped jar SHA-256 `a257c4c97ceac50fbc069dc6051dff5f0263716547ede05e85c44d675ade592f`
- Source/raw-input hash relation and verification reference: verified against the original A readiness and artifact manifests recorded in `../run.md`; each cited Java file hash matches the A source manifest
- Revised-to-original derived-artifact equivalence and evidence reference: not applicable; original publication
- Provenance limitations: exact requested/resolved release and official Mojang mapping provenance are recorded in `../run.md`
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `net/minecraft/world/entity/Entity.java`, `Entity.move` call site line 657; `Entity.tryCheckInsideBlocks()` lines 685-694, excerpt SHA-256 `1728dd40b3ab42d89f52536fba63d5337e6b1e6ebd1a89a8d119daf65b4af39f`; `Entity.checkInsideBlocks()` lines 886-912, excerpt SHA-256 `0650c89cc809e7c6eca8289b59a34468991b64df9d2262e43763cdfaa704f86c`; full `Entity.java` SHA-256 `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`. `net/minecraft/world/level/block/HoneyBlock.java`, `entityInside(BlockState,Level,BlockPos,Entity)` lines 56-64, excerpt SHA-256 `a6ed70220d53b4410eb126287fa4cdbbdcd5bbc52c0b5a645895c0a73a8dd37e`; `isSlidingDown(BlockPos,Entity)` lines 66-83, excerpt SHA-256 `b84f030732d9e818acf77c15e5c7f65c64b5bc63545146eb7ba699c655a2c94a`; `doSlideMovement(Entity)` lines 91-101, excerpt SHA-256 `74a48094df8c61cbab8ef97fdb3443d61f20433a0df8535a76310991dccfda4e`; full `HoneyBlock.java` SHA-256 `5ffc5f58a81f82823305c7ed02f9a465c7b3bfd2c1799da566e1485d0cd99ad5`. `net/minecraft/world/level/block/Blocks.java`, `HONEY_BLOCK` registration lines 3369-3378, full file SHA-256 `f532585f836ee7ad685d368889ed13d735c570ef38971db0c1d2a7debb4cf27b`. `net/minecraft/world/entity/EntityType.java`, `PLAYER` default dimensions at line 527, full file SHA-256 `4d2fb12f5458e43fcf94ec8f40058f0ef5ba545049fd5a73c0cf755cb4764836`.

### B artifact identity

- Evidence artifact record ID: original B publication, `../run.md` Artifact manifest B
- Publication status: original-verified
- Revision ID (revised evidence only): not applicable
- Immutable evidence path: `build/movement-campaign-2026-10-07/ready/1.19.3/mojmap/`
- Evidence artifact SHA-256: source manifest `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`
- Evidence manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.19.3/mojmap.sources.sha256` / `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.19.3/artifacts.sha256` / `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652`
- Original derived-artifact availability and SHA-256 or expected hash: published Mojmap source tree and mapped jar available; mapped jar SHA-256 `3be15cd54092cbac9853a67445a42b38d5c3b0d61503bd79b0d76589357d871e`
- Source/raw-input hash relation and verification reference: verified against the original B readiness and artifact manifests recorded in `../run.md`; each cited Java file hash matches the B source manifest
- Revised-to-original derived-artifact equivalence and evidence reference: not applicable; original publication
- Provenance limitations: exact requested/resolved release and official Mojang mapping provenance are recorded in `../run.md`
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `net/minecraft/world/entity/Entity.java`, `Entity.move` call site line 657; `Entity.tryCheckInsideBlocks()` lines 685-694, excerpt SHA-256 `1728dd40b3ab42d89f52536fba63d5337e6b1e6ebd1a89a8d119daf65b4af39f`; `Entity.checkInsideBlocks()` lines 894-920, excerpt SHA-256 `4a4f1232320785153d960c2badc8e65c753506351b0e04b7e4ba12127811e7d1`; full `Entity.java` SHA-256 `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6548983667adc32`. `net/minecraft/world/level/block/HoneyBlock.java`, corresponding callback, gate and writer excerpts have the same hashes as A; full file is byte-identical with SHA-256 `5ffc5f58a81f82823305c7ed02f9a465c7b3bfd2c1799da566e1485d0cd99ad5`. `net/minecraft/world/level/block/Blocks.java`, `HONEY_BLOCK` registration lines 3899-3908, full file SHA-256 `9944877c941fa209d2a8b9a7d4eb6333edf2dfca255a3e90601242483eb7a476`. `net/minecraft/world/entity/EntityType.java`, `PLAYER` default dimensions at line 536, full file SHA-256 `b63be8727a2fa406bef2ae45c1c9ebe06a56440e01b6dc85c443cbb738c32c06`.

## Source-level difference

After `Entity.move` resolves movement, it calls `tryCheckInsideBlocks`, whose wrapper calls `checkInsideBlocks`. Both versions use the resulting entity AABB to construct min/max block positions and iterate the covered cells inclusively before invoking each block state's `entityInside` callback. A insets all six bounds by `0.001`; B insets them by `1.0E-7`. Therefore a cell just across a horizontal AABB edge can be visited in B but omitted in A when the overlap is greater than `1.0E-7` and less than `0.001`.

Both versions register the same Honey Block and have byte-identical Honey Block callback code. When the additional B cell is Honey, `entityInside` calls `isSlidingDown`. That method returns false for a grounded entity, an entity above block Y + `0.9375 - 1.0E-7`, or vertical velocity at least `-0.08`. It then requires either horizontal center separation plus `1.0E-7` to exceed `0.4375 + entity width/2`. For the default player width `0.6F`, a narrow side overlap in the scan band satisfies that center-distance test. The callback calls `doSlideMovement`, which sets Y velocity to `-0.05`; when prior Y is below `-0.13`, it scales X/Z by `-0.05 / y`, otherwise preserves X/Z. It resets fall distance afterward. These callback predicates and writes are identical; the difference is whether the Honey cell is visited.

## Reachability and dependencies

The local player reaches this callback through its movement call to `Entity.move` and the post-movement `tryCheckInsideBlocks` call. A concrete conditional state is an airborne default-width player whose resolved AABB overlaps the side of a registered Honey Block by more than `1.0E-7` and less than `0.001`, with the entity Y and downward velocity satisfying `isSlidingDown`. In that state B scans the Honey cell and writes player velocity/fall-distance state; A omits the cell. The source establishes the conditional player response. It does not establish that ordinary collision trajectories produce this very narrow overlap, and no ordinary-entry or external producer is claimed.

## Consequence and uncertainty

Source-proven: under the stated AABB and Honey gate conditions, B can replace the player's vertical velocity with `-0.05`, may scale horizontal velocity, and resets fall distance where A does not run that Honey callback. The exact resulting position over subsequent ticks and how a player enters this narrow overlap are not established. No trajectory or fix has been runtime validated.

## Handoff

Independent delta: B's smaller post-move AABB inset can add an adjacent Honey Block to the contact callback scan and conditionally apply its existing player slide response. Related slices: S4-honey-contact-scan and S4-honey-slide-response; no separate callback-code finding is needed because Honey Block behavior is identical. Applicability is limited to the explicit narrow-overlap and movement-gate conditions. The release boundary is unresolved within 1.19.2–1.19.3. Implementation and testing decisions are deferred.

**Inventories/slices:** INV-TICK, INV-COLLISION, INV-STATE, INV-WORLD-MOVEMENT; S4-honey-contact-scan, S4-honey-slide-response. **Implementation disposition:** deferred until blind review and freeze.
