# 1.16.2 → 1.16.3 movement resource dependency closure

Status: **bounded source/resource closure candidate; independent audit pending**. This supplements the exact-pair source coverage report without claiming pair freeze.

## Source identity and unchanged code paths

The pair source report and its independent audit are bound in `input-reference.json`. The endpoints are exact Mojmap 1.16.2 and 1.16.3; roots, source-manifest hashes, provenance records, client pins, and matching source-file hashes are recorded there. The exact movement and tag-binding classes inspected have identical SHA-256 at both endpoints.

The directly read default tags in the ordinary player dependency are:

- `minecraft:soul_speed_blocks`: `LivingEntity.onSoulSpeedBlock` and overridden `getBlockSpeedFactor` determine the surface multiplier applied by `Entity.move` when the player has Soul Speed.
- `minecraft:climbable`: `LivingEntity.onClimbable` gates ordinary climb behavior; `Entity.move` also reads it for movement-distance accounting.
- `minecraft:fences` and `minecraft:walls`: `Entity.getOnPos` selects a fence/wall support block for the movement-affecting position. `FenceGateBlock` also checks walls for its in-wall state.
- `minecraft:wooden_fences`: `FenceBlock.isSameFence` uses both fence tags to select connected fence state, which feeds collision shape.
- `minecraft:shulker_boxes`: `Block.isExceptionForConnection` is called by `FenceBlock` and `WallBlock` connection logic, affecting their collision shapes.
- `minecraft:wall_post_override`: `WallBlock` consults it when building the post collision shape.
- `minecraft:signs`: `FlowingFluid.canHoldFluid` checks whether fluid can occupy a neighboring block. It is a fluid-environment dependency.

Nested references in those actual tag payloads close the packaged set: `fences` → `wooden_fences`; `wall_post_override` → `signs`, `banners`, `pressure_plates`; `signs` → `standing_signs`, `wall_signs`; `pressure_plates` → `wooden_pressure_plates`, `stone_pressure_plates`. All 14 block-tag entries are included per endpoint; their exact sizes and SHA-256 values are recorded in the input reference and match across releases.

`FluidTags.WATER` and `FluidTags.LAVA` are used by unchanged entity/living-entity fluid-state, pushing, travel, and jump gates. Their exact endpoint entries are reused from two previously independently ACCEPTed packets; paths and audit hashes are bound in `input-reference.json`. Water and lava entry hashes match across endpoints.

## Packaged-resource result

The official 1.16.2 and 1.16.3 client JARs were fetched by pinned URLs, SHA-256 checked against their endpoint pins, and opened only for the 14 exact block-tag entries per endpoint listed in `input-reference.json`. Extracted files are retained under their endpoint subdirectories. Temporary JARs were removed after verification. No other archive entries were opened or extracted.

## Boundaries

This closes only the default packaged static resource inputs identified from unchanged source paths and nested tag references. It does not cover external datapack/resource overrides, generated-world occurrence, runtime behavior, all block-state creation paths, or the pair-wide independent coverage/freeze gate. The separate exact source coverage report found no movement Java source delta; this packet establishes equality only for the listed resources. `minecraft:portals` was excluded because its only player-adjacent read is vehicle dismount; `unstable_bottom_center` is placement support; `fire` affects fire state/damage; `guarded_by_piglins` is unrelated to ordinary player movement.
