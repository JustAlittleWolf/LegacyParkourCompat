# 1.20.2 → 1.20.4 block registry and collision-provider inventory

## Scope and verdict

This is a bounded source-only inventory of `Blocks` registry declarations and their directly registered shape/state providers that could affect player collision or movement-facing block-state gates. It is **PARTIAL** and does not establish pair coverage, ordinary-world reachability for malformed states, runtime parity, or a source finding ready for implementation.

## Exact sources

Both canonical inputs are exact `mojmap` publications: `decompiled_minecraft/1.20.2/mojmap/` and `decompiled_minecraft/1.20.4/mojmap/`. The provenance sidecars are at the version roots, not inside the namespace directories:

| Release | Provenance sidecar and SHA-256 | Ready sidecar and source-manifest SHA-256 | Declared source count |
| --- | --- | --- | --- |
| 1.20.2 | `decompiled_minecraft/1.20.2/mojmap.provenance.json` — `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05` | `mojmap.ready.json` — `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f`; `mojmap.sources.sha256` — `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` | 4,904 |
| 1.20.4 | `decompiled_minecraft/1.20.4/mojmap.provenance.json` — `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` | `mojmap.ready.json` — `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1`; `mojmap.sources.sha256` — `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | 5,048 |

The provenance sidecars declare matching release IDs and `namespace=mojmap`/`mappingOption=mojmap`; ready markers say `ready` and their source-manifest digests match the sidecars. Every Java file cited below was rehashed and matched its source-manifest entry. The full source trees and client artifacts were not rehashed/inspected.

## Registry inventory

A lexical tally of `public static final Block … = register(...)` declarations in `Blocks.java` found 1,003 such fields in 1.20.2 and 1,058 in 1.20.4: 1,002 field names are common, 56 appear only in the newer source, and `GRASS` is replaced by `SHORT_GRASS`. This tally is not a runtime registry enumeration. The added constants include tuff variants, 1.21 copper blocks/doors/trapdoors/grates/bulbs, the crafter and trial spawner. Their feature/default availability and packaged registry data are not resolved here; new post-1.20.2 content is not a historical 1.20.2 block-shape claim.

The broad common-declaration churn is mostly constructor/API and copied-property spelling changes. For shape-family registrations, 1.20.2 `BlockBehaviour.Properties.copy` copies the same legacy fields copied by 1.20.4 `ofLegacyCopy` (including collision, occlusion, friction, speed factor, dynamic shape, and required features). `ofFullCopy` adds jump factor, redstone-conductor/spawn/suffocation/view-blocking/post-process predicates and drops. Existing copper registrations use the newer full-copy form; their directly used copper base properties contain no explicit assignments to those added predicates/factors, so this scan establishes no copper shape delta from the copy spelling alone. It does not exhaustively validate every registry property consumer.

## Paired provider checks

- **Stairs, slabs, walls, fences:** the paired state/shape method bodies checked are equal after whitespace normalization: `StairBlock#getShape`, `getStateForPlacement`, `updateShape` (1.20.2 lines 100–192; 1.20.4 101–133); `SlabBlock#getShape`, `getStateForPlacement`, `updateShape` (49–115; 56–122); `WallBlock#getShape`, `getCollisionShape`, `getStateForPlacement`, `updateShape` including the private neighbor-state builder (122–210; 129–217); `CrossCollisionBlock#getShape` and `getCollisionShape` (90–97; 94–101); and `FenceBlock#getOcclusionShape` / state-neighbor methods (39–104; 46–111). Their changed class text adds codecs/abstract codec plumbing. Existing stair registrations that move from `Properties.copy` to `legacyStair` still construct `StairBlock` with the same default base state and legacy-copied properties. Shape-family equality here is limited to these methods and provider paths.
- **Fence gates / trapdoors:** checked `getShape`, collision/occlusion methods, placement/state updates and manual `use` transitions are equal for the paired source methods (`FenceGateBlock` 1.20.2 lines 53–171 vs 1.20.4 66–203; `TrapDoorBlock` 59–165 vs 72–194). The newer methods add codecs and a factored `toggle` helper; the reported open/closed shape is still selected by the same state fields. The 1.20.4 `onExplosionHit` additions for doors, trapdoors, fence gates and buttons use the `TRIGGER_BLOCK` explosion path; the old-era producer/configuration route is not covered here.
- **Door shape and an open state-pair candidate:** `DoorBlock#getShape` uses the same direction/open/hinge boxes in both sources (1.20.2 lines 68–83; 1.20.4 lines 80–91), expressed as `switch` versus switch-expression. Placement, manual use, `setOpen`, and neighbor power handling have the same paired bodies. `DoorBlock#updateShape` differs at the opposite-half join: 1.20.2 requires the neighbor state to be the exact same block (`$$2.is(this)`, lines 86–100); 1.20.4 accepts any `DoorBlock` subclass (`$$2.getBlock() instanceof DoorBlock`) and returns that neighbor state with the opposite `HALF` (lines 94–103). This can replace a door half's block identity and copy the neighbor's facing/open/hinge/powered values in a mixed vertical pair. Since door collision boxes depend on facing/open/hinge, the changed join could change a cell's box if distinct adjacent door states with different geometry reach this path. This inventory does not establish a normal placement/world-generation route to such a mixed pair, so it is a candidate state gate, not an ordinary player-movement finding.
- **Pressure-plate/button response gates:** 1.20.2 pressure plates store `Sensitivity` and select `Entity` versus `LivingEntity`; 1.20.4 reads `BlockSetType.pressurePlateSensitivity` (PressurePlateBlock 35–41 vs 43–49). The registered legacy types map wooden/default types to `EVERYTHING`, and stone/polished-blackstone to `MOBS`, matching the old explicit registrations. Likewise, old wooden/stone button helper booleans (`arrowsCanPress`) become `BlockSetType.canButtonBeActivatedByArrows`; the legacy wood defaults are true and the stone types false, matching the old helper. Button shape/use/state-query bodies remain equal; the trigger representation changes but no legacy player-sensitive gate delta was established.

## Remaining gates

The mixed-door join's ordinary-state reachability and any actual changed collision box remain unexamined. Other `Blocks.java` changes outside the directly sampled providers, worldgen/data-pack state inputs, full feature-flag defaults, and other state-producing/callback routes remain open. No block registry/resource membership or full-pair terminal closure is claimed. No build, test, runtime, product/wiki/backlog/history review, or source generation was performed.

## Exact cited Java-file SHA-256 values

| Relative source path | 1.20.2 | 1.20.4 |
| --- | --- | --- |
| `net/minecraft/world/level/block/Blocks.java` | `75e8e77adab4a07b02ef95619be83a004f5f79810cf3f07157a713d714881253` | `5392c4349317e55c2989865be38c17b94e6e0543e7017e6a2a64d79217e83643` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `a6d88726ce4642fc0a61f46a01347bb0b74887b61ef55af7e7c76723789a0b57` | `6284a7d08a6d0d475cd4fe8aea62b35153ae8fbc0bce5f51788389f679233b54` |
| `net/minecraft/world/level/block/StairBlock.java` | `439bf404f76646daa6d71e14318ec688a7303134dec06ecadb6dcb5f60d85850` | `e86fdf4eed2fa62eaaf7747a8001b98fc76cd5b4b93f6be0b48267465812e9e1` |
| `net/minecraft/world/level/block/SlabBlock.java` | `95a008f51158ebccd448c5e04af8859b20104d4df4c7fae9e45764ddb228df5f` | `10503cc2634eadc59dc43ce7121e14f81272be28408db245031774440c6aad89` |
| `net/minecraft/world/level/block/WallBlock.java` | `be4c22212a08cea16a5b3b5e83096f92497c81a7fd903f70dcb64cd4bbf5db4e` | `e7091d269e038461d190f131b8b279de687026e98af383694cf0e9d15fa93bbb` |
| `net/minecraft/world/level/block/CrossCollisionBlock.java` | `de1c71e81995e9d7e66d829904e7aeaf2d15dbc88d30a30c20c6dd384b5a4c79` | `60faee3288389c1a82980adc8838a2e29ca14f6cdd09c3781c031d6d82b38e0a` |
| `net/minecraft/world/level/block/FenceBlock.java` | `b9b38034baf9829bcd80a18d1eb60aabfdd9fb9885f37c69f4126fcbdc42c174` | `f0fb8e9db3f05dce3ec6d573cc0c70f1556b14f66644d3daea4f64323f3d31da` |
| `net/minecraft/world/level/block/DoorBlock.java` | `1a36e987e71d707a84e0ba53af9c6bf9db641df2758b3eee739a22c114beb1a0` | `c2c8880fc9d247bdd598e087e9752e1f27eca45c184d07e47f5f34ccb056be10` |
| `net/minecraft/world/level/block/TrapDoorBlock.java` | `2151e4f2c3ea35c3dceab7dcc78e53c0d7e0357f4fbd1197f54c6d772fd6cabb` | `57845eed989b08137bba8b41ad718af49832d6a59650680de3419bff081b83ee` |
| `net/minecraft/world/level/block/FenceGateBlock.java` | `2c0d4edb8de61176b69c2595bc74168c3a62d5ff6ea71c345abcb4e4404ea030` | `babda6ea4919efdde2b2d6086b7e41b2d41caf12362064f247e0cb01c9884232` |
| `net/minecraft/world/level/block/PressurePlateBlock.java` | `4c184bd28b372c6ab2e6083cea971a3f56ef7e72b2faac40a14abab6fdfe504c` | `ab2910e6322fb5ee6eeb93d93caa9233d28360c4e87d09bc8ec81310f0b38923` |
| `net/minecraft/world/level/block/ButtonBlock.java` | `957f0a5b9b3d6835ee5db73f787693b14cf8bbe582e7c16a4a01cd09e5f1f0a2` | `3aef2a341e19591627a516cc0aeb1366fbd4b5efda48254e056b3ccbd0ccdc15` |
| `net/minecraft/world/level/block/state/properties/BlockSetType.java` | `53b9b5e2aa232f581fa70b07dacb6c3478c824621d6cbc429cfc8c2b5284c097` | `cfa24b6b1a31aa5311b71be77abef7f2f498d3e5c67e7e25958d438aef78af1c` |
