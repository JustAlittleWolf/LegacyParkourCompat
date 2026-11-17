# Independent S10 source coverage verdict: 1.20.1 → 1.20.2

## Evidence identity and roster derivation

I read the project README, shared discovery procedure, source lane procedure, and buildSrc decompiler guide. The exact source roots are `decompiled_minecraft/1.20.1/mojmap/` and `decompiled_minecraft/1.20.2/mojmap/`. Ready markers identify the exact releases and Mojmap. The source manifest SHA-256 values match the ready markers and the actual files:

- 1.20.1 `mojmap.sources.sha256`: `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`; client SHA-256 `56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f`.
- 1.20.2 `mojmap.sources.sha256`: `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`; client SHA-256 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`.

Independently filtering each manifest's `net/minecraft/world/level/block/**/*.java` paths for `getCollisionShape` or `getShape(` and checking the corresponding files produced 135 paths on each side, with identical relative-path sets. The 19 previously audited paths comprise the 18 named collision-provider subset and `PinkPetalsBlock.java`; removing them leaves the roster's explicit 116-path remainder.

I checked group assignments against that derived remainder, sorted by ordinal complete relative path. Group 1 covers indices 1–29, group 2 30–58, group 3 59–87, and group 4 88–116. Each report/file manifest lists 29 entries. The union has 116 entries, zero repeats, zero missing entries, and zero extra entries; every entry matches its assigned 29-index slice. Group 2's author report is `audit-author-r2.md`; I did not treat the separate current reviewer report as the author record.

## Changed dependencies and gates

**Shulker animation notifications:** I confirmed the changed `ShulkerBoxBlockEntity` route in source. In 1.20.1 `triggerEvent` invokes `doNeighborUpdates` immediately when open count reaches 0 or 1. In 1.20.2 those calls are removed there; `updateAnimation` performs notifications at animation transition points, and `doNeighborUpdates` also adds `Level.updateNeighborsAt`. This changes neighboring-shape/redstone notification timing on the loaded route. The Shulker's direct `getShape` and support-shape selectors still use its bounding box / closed state and facing, and the collision/support dispatch gates are stable. The changed notification timing leaves secondary adjacent-state effects unresolved by this bounded shape-provider audit; it is not evidence of full movement-graph closure.

**BlockBehaviour and registry gate:** In both releases `BlockBehaviour.getCollisionShape` returns `hasCollision ? state.getShape(...) : Shapes.empty()`. `PinkPetalsBlock` remains registered with `.noCollission()` in both `Blocks.java` files. That property sets `hasCollision=false`; therefore the changed 1.20.2 Pink Petals outline (`singleEncompassing`) is not used by the player solid-collision or inherited support-shape route. The relevant `BlockBehaviour` collision method is unchanged. The changed cache branch in 1.20.2 gates *occlusion-face* caching on `canOcclude`; it does not replace the collision predicate or support shape selection. `BlockBehaviour` source hashes: 1.20.1 `5f37987191165379c222c412949a0c80a904bf78a453035b1f9d86c6454510fa`; 1.20.2 `a6d88726ce4642fc0a61f46a01347bb0b74887b61ef55af7e7c76723789a0b57`.

The canonical source manifest contains decompiled Java, not packaged assets. Pink Petals' changed outline is a Java outline operation; resource/model data does not enter the inspected collision/support call path. I did not audit external resource packs or non-Java data definitions.

**Diode support-update candidate:** After deriving the roster, I checked the independent `review-1201-1202-diode-support-updates/verdict.md`. It narrows the source difference to 1.20.2's added DOWN-neighbor `updateShape` checks for repeaters/comparators, while the rigid-face support predicate and same-state collision plate stay unchanged. Ordinary support removal already synchronously removes the diode in the reviewed 1.20.1 server route. The extra path is limited to support writes that propagate shape updates without bit 1; no vanilla player-reachable instance of that exact flag/precondition was established, and client packet writes suppress shape propagation. Treat this as a reviewed world-update-path delta, not an established player-movement collision finding.

## Verdict and limits

The frozen 135-path shape-provider roster and the 116-path group coverage partition are independently verified with no missing or repeated paths. The group reports support their scoped provider dispositions; group 4 correctly retains the Shulker secondary notification timing as unresolved. The diode review likewise does not establish a direct same-state shape difference or a player-reachable movement effect under the required update precondition.

This verdict covers only roster construction, the four bounded slices, and the named dependencies. It does not certify complete movement tick-graph coverage, close the Shulker secondary world-update route, establish resource-pack behavior, freeze the entire 1.20.1→1.20.2 pair, or establish implementation/runtime parity. No product/wiki sources, Git operations, builds, tests, launches, or decompilation were used.

## Key source hashes

Hashes below are actual SHA-256 values of the listed primary source files, verified against their release source manifests.

| File | 1.20.1 | 1.20.2 |
|---|---|---|
| `PinkPetalsBlock.java` | `f28fcc1bbc8a0fc11f8ba083f0d8a51f704a646cb83333ed497aed6ab78fa32e` | `156b7cb8222b250076b721e889e88946eefb032ab826fec7e87ac4f9560bb9cc` |
| `Blocks.java` | `1e7095d1fbfdc8198191f40037997feeca2ec993cc54dd53f2636375cae66b51` | `75e8e77adab4a07b02ef95619be83a004f5f79810cf3f07157a713d714881253` |
| `ShulkerBoxBlock.java` | `cbac44132ca5feae1563c7590afbd941a581860941b7f2306b29cfd85e75224c` | `7d04605ccfc899fec7f448b465edecf79f2953d21cbf4bcefc46ffc3e4fe593e` |
| `ShulkerBoxBlockEntity.java` | `54d5bff4c0541fcd762c65d818e88bf88fe3b6115bc6c67489126aa20e161721` | `7884f7fd47585e5b4c1c98de22cdd5123a478d01b08e2a9debdc286fee371565` |
| `DiodeBlock.java` | `573ea1b5095f072aff259f3d4e00f21a93f193500cdd15f67e295e26d5904d69` | `3467f696369b5454f7f58ac29a7f923bfa488d469b12207c7cba4118f704be25` |
| `RepeaterBlock.java` | `02ad6b8e76f2403dc12d147baf6272362f2286f6c314b2b919fd609dff3171ca` | `b9e1ed1523b661666874977113fcda9ff055e3624e3a0bad3b7e81e7728f309a` |
| `ComparatorBlock.java` | `b498e556be0872662c5c26f160fd5b37f91fdcaf5f578724a4bee61330bca9f9` | `635d5a3a718b79c566ebf3c20ed0bfc5ac56cc78f9d650417e7dd122ffe6893b` |

