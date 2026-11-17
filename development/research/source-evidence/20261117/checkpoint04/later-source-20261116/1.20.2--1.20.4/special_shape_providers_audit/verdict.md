# Independent audit: 1.20.2 → 1.20.4 special shape providers

## Verdict

**ACCEPT** the bounded NO_DIFF report at `build/later-source-20261116/1.20.2--1.20.4/special_shape_providers/coverage.md`, SHA-256 `D40B4B469F4A039F36ACE53D263C79EBE1425275AE519D853C9FB011DBB74C88`.

The audit found no correction necessary within the assigned cauldron, lily-pad, hopper, and End Portal Frame shape/provider paths. This accepts only the cited source slice; it is not pair-wide coverage, ordinary-world reachability, or runtime parity.

## Identity and hashes

Both endpoint ready markers identify the expected versions and `mojmap` namespace, are marked ready, and bind the source manifests listed in the report. The version-root provenance records identify the matching `versionId`, `namespace=mojmap`, and `mappingOption=mojmap`. Ready/provenance/source-manifest/artifact-manifest SHA-256 values match the report. The 1.20.2 and 1.20.4 source counts are 4,904 and 5,048.

All 15 cited source-table rows were checked against local SHA-256 and the matching `mojmap.sources.sha256` entry at each endpoint. Every present file matched. `PowderSnowCauldronBlock.java` is present and hash-bound in 1.20.2, and absent in 1.20.4 as reported; the newer `Blocks.POWDER_SNOW_CAULDRON` registration constructs `LayeredCauldronBlock`.

## Bounded source checks

- `AbstractCauldronBlock` builds and returns the same state-independent shape at both endpoints. Cauldron, layered, lava, and powder-snow variants inherit that provider. The old powder-snow subtype extends `LayeredCauldronBlock`; the newer registration uses that base directly. `CAULDRON` still starts with collision-enabled properties; the cauldron variants use the corresponding legacy copy operation. The copied `hasCollision` and `canOcclude` fields, and the `noOcclusion` versus `noCollission` setters, were checked and preserve the reported collision path.
- `WaterlilyBlock` uses the same `(1, 0, 1)` to `(15, 1.5, 15)` box and returns it directly. `BushBlock` adds no collision override. The `LILY_PAD` registration retains the same provider and property chain.
- `HopperBlock` retains the listed box constants, boolean-shape operations, direction-to-shape selector, and `FACING=DOWN, ENABLED=true` default. `Hopper.INSIDE` is byte-identical. Its registration retains the same provider and collision-relevant properties.
- `EndPortalFrameBlock` retains the base and eye boxes, their union, the `HAS_EYE`-selected shape, and `FACING=NORTH, HAS_EYE=false` default. The facing value does not affect this shape selector. Its registrations do not disable collision.
- None of the assigned providers adds a collision/support override. `BlockBehaviour.getBlockSupportShape` delegates to empty-context collision shape; the default collision path returns the state shape when `hasCollision` is true. `noOcclusion` changes `canOcclude` only, while `noCollission` clears `hasCollision`. The selected registrations and the default collision-enabled property therefore support the reported collision/support dispatch. `Block.box` coordinate scaling and the identical `Shapes.java` hash also support the stated geometry.

The reviewed source establishes the claimed geometry and selectors only for these named providers. Cauldron contents/contact callbacks, boat interaction, hopper inventory, portal activation/transfer, resource membership, state availability/reachability, other providers, and full-pair/runtime closure remain outside scope.
