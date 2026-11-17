# Mixed DoorBlock pair reachability: ordinary item placement

Pair: Minecraft 1.20.2 Mojmap → 1.20.4 Mojmap. This is a bounded source-only check of the already identified `DoorBlock.updateShape` mixed-class opposite-half join. It checks the ordinary `BlockItem` player-placement route and its survival gate; it does not establish pair coverage, arbitrary-world-state reachability, or runtime behavior.

## Exact source identities

- 1.20.2: canonical source `decompiled_minecraft/1.20.2/mojmap/`; actual version-root provenance `decompiled_minecraft/1.20.2/mojmap.provenance.json` SHA-256 `1DAD9F725F6CB25B6568700FA0294F83B66EA9633B0D62ED354D7BD8B45D4F05`; ready marker `decompiled_minecraft/1.20.2/mojmap.ready.json` SHA-256 `5FF3322F287C250EE914C7CA3840D053BFFC3C2C5F35E91B59314D948292483F`, version `1.20.2`, mapping `mojmap`; source manifest `decompiled_minecraft/1.20.2/mojmap.sources.sha256` SHA-256 `E439FF7E1D3F1D31E01BE1EDD141F94D917A9DC7FA0DE5F69904544CBBE62887`.
- 1.20.4: canonical source `decompiled_minecraft/1.20.4/mojmap/`; actual version-root provenance `decompiled_minecraft/1.20.4/mojmap.provenance.json` SHA-256 `10914D9B7B3FC29DE066CA24F7D905E4D4F0A451A6C81729A2D1981ADDFC1385`; ready marker `decompiled_minecraft/1.20.4/mojmap.ready.json` SHA-256 `6225B97979420CC743C8B7156A0CE512EF27D0DBF48BFFFBC40D3E1C8CB044D1`, version `1.20.4`, mapping `mojmap`; source manifest `decompiled_minecraft/1.20.4/mojmap.sources.sha256` SHA-256 `FD3C8668483E5FF208C847474F6CBD3952A909C4AA602ED98B24E3D11A9602A1`.

Each ready marker's declared source-manifest hash matches its version-root manifest. Every cited Java file's current SHA-256 matches the corresponding source-manifest entry. The actual available source identity is version-root sidecars; no hidden in-namespace provenance path is assumed.

## Ordinary placement route

Both `DoorBlock.getStateForPlacement` methods (1.20.2 `DoorBlock.java:127–142`; 1.20.4 `:137–152`) create a lower-half state for the block item being placed only when the space above is replaceable. Both `DoorBlock.setPlacedBy` methods (`:144–147`; `:154–157`) install the upper half using that same block state, so an ordinary single-door item placement creates two halves of the same `DoorBlock` instance.

The common `BlockItem` path is byte-identical between endpoints (`BlockItem.java:55–93, 116–120, 148–155`; SHA is identical in both manifests). `getPlacementState` asks the chosen block for its placement state and returns it only if `canPlace` succeeds. The default `mustSurvive()` is true; `canPlace` therefore checks `state.canSurvive(level, clickedPos)` before calling `setBlock`.

For a lower door half, both `DoorBlock.canSurvive` methods (`DoorBlock.java:227–231`; 1.20.4 `:237–241`) require the block below to support the `UP` face with `SupportType.FULL`. The common base implementation of `BlockBehaviour.getBlockSupportShape` delegates to the collision shape (`BlockBehaviour.java:245` / `:289`), while `DoorBlock` does not override it. Base collision-shape handling delegates to the state shape when collision is enabled (`BlockBehaviour.java:285–291` / `:329–335`). The four door shape boxes are each only 3/16 thick in one horizontal axis and full-height (`DoorBlock.java:43–46` / `:50–53`); `SupportType.FULL` calls `Block.isFaceFull`, which checks whether that face shape is a full block (`SupportType.java:13–18`; `Block.java:240–250` / `:247–257`). A door's top face is thus not full support.

Consequently, placing a new lower-half door directly above an existing upper-half door fails the ordinary `BlockItem.canPlace` survival gate, regardless of which `DoorBlock` subclass supplied the lower half. Other successful ordinary door placement installs both halves from one item and one block state. This ordinary item path does not create the mixed vertical pair required by the changed join.

## Conditional join and limits

The separate bounded inventory records the actual method difference: 1.20.2 `DoorBlock.updateShape` requires `neighbor.is(this)` before copying the neighbor's facing/open/hinge/powered values into the current block state (`:86–100`); 1.20.4 accepts any `DoorBlock` and returns the neighbor state with the current half value (`:94–103`). If an already-existing mixed-class pair reaches this branch, the newer method can replace the current door block identity and copy geometry properties; the ordinary item-placement route traced here does not establish such a pair.

This report does not audit commands, world generation, data loading, other mods, or arbitrary state injection; it cannot rule out a preconstructed mixed pair from those sources. It establishes no ordinary-player movement difference and drafts no finding. The registry/provider inventory and the 1.20.2→1.20.4 pair remain partial.

## Cited Java file SHA-256

| Relative path | 1.20.2 | 1.20.4 |
| --- | --- | --- |
| `net/minecraft/world/level/block/DoorBlock.java` | `1a36e987e71d707a84e0ba53af9c6bf9db641df2758b3eee739a22c114beb1a0` | `c2c8880fc9d247bdd598e087e9752e1f27eca45c184d07e47f5f34ccb056be10` |
| `net/minecraft/world/item/BlockItem.java` | `216747d68c28054899d7ef4c6f59b0137be0b28e75081bbac05cdbd93ad4e782` | `216747d68c28054899d7ef4c6f59b0137be0b28e75081bbac05cdbd93ad4e782` |
| `net/minecraft/world/level/block/Block.java` | `0c2df3d958078929c4cc2985e0d6a6a766cbbbb02066e6c9ac471981aa858656` | `9a777d44c9c6b55677132fb5a56d46c9aa322e42faf956749ef80443817416c4` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `a6d88726ce4642fc0a61f46a01347bb0b74887b61ef55af7e7c76723789a0b57` | `6284a7d08a6d0d475cd4fe8aea62b35153ae8fbc0bce5f51788389f679233b54` |
| `net/minecraft/world/level/block/SupportType.java` | `2604faefc3d4732eafe16363f4e9db68f7a3772e81e062e7ef4441a54709f5c6` | `2604faefc3d4732eafe16363f4e9db68f7a3772e81e062e7ef4441a54709f5c6` |

No source generation, artifact inspection, build, test, game/runtime launch, backlog/wiki/product/history review, or canonical write was performed.

