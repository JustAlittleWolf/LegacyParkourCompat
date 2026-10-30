# Fence query-time collision connectivity: 1.10.2–1.11.2

## Exact source identity

All four sides are exact `ornithe-feather` decompilations in the Feather Gen2 `+build.2` mapping family. Provenance identifies `1.10.2` and `1.11.2` from the 1.10-to-1.12 batch; `1.11` and `1.11.1` have individual exact-version runs. Mapping artifacts: `feather-gen2-1.10.2+build.2`, `feather-gen2-1.11+build.2`, `feather-gen2-1.11.1+build.2`, `feather-gen2-1.11.2+build.2` (mergedv2 jars and Tiny files). The source-file SHA-256 values below were recomputed and match each release's `ornithe-feather.sources.sha256` manifest.

| Exact release | `FenceBlock.java` SHA-256 | `World.java` SHA-256 | `StairsBlock.java` SHA-256 |
|---|---|---|---|
| 1.10.2 | `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | `ee143aced2a6902563cddbae77e78d1d1a512244f278987684ebe5bcacba37cb` |
| 1.11 | `790f3f58a2902a8f2ade860221c4bf1161ba9e82e92cd6ba4596b3b4002794e5` | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` | `f1ec8567bac6140dd43770d4adcd9b589c25807845c8bac3b01a4b5b61289391` |
| 1.11.1 | `856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | `5df1782da6eb7acdb9935b3910a0484d6417661bfd6230ecf81df0de6d782627` |
| 1.11.2 | `856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | `5df1782da6eb7acdb9935b3910a0484d6417661bfd6230ecf81df0de6d782627` |

## Comparison and movement meaning

In all four exact releases, `FenceBlock.shouldConnectTo(WorldView, BlockPos)` reads the adjacent raw `BlockState`, rejects barriers, accepts same-material fences and fence gates, and otherwise returns true only for `block.material.isSolidBlocking() && blockState.isCube()` with pumpkins excluded (`block/FenceBlock.java`: 1.10.2 lines 120–130; 1.11 lines 121–131; 1.11.1 and 1.11.2 lines 124–134). `resolveVirtualProperties` recomputes the four directional booleans from those world-neighbor queries (`FenceBlock.java`: 1.10.2 lines 159–164; 1.11 lines 156–161; 1.11.1 and 1.11.2 lines 159–164). The predicate and direction mapping are semantically unchanged across the four versions.

A stair is rejected by that predicate in every examined release: `StairsBlock.isCube(BlockState)` returns `false` (`block/StairsBlock.java`: 1.10.2 lines 143–146; 1.11 lines 142–145; 1.11.1 and 1.11.2 lines 144–147). Therefore, for the same fence and neighboring stair states, none of these releases derives a fence arm toward the stair, and none adds that arm's collision box.

The player-movement query path reaches the dynamic resolver in every release. `Entity.move` asks `World.getCollisions` for collision boxes (1.10.2 `entity/Entity.java:514,547`; 1.11 `:523,569`; 1.11.1 and 1.11.2 `:564,610`). `World.getCollisions` dispatches to each queried state's `addCollisions` (1.10.2 `world/World.java:903–938`; 1.11 `:960–995`; 1.11.1 and 1.11.2 `:960–998`). In 1.10.2 and 1.11, `FenceBlock.addCollisions` always resolves virtual properties before adding the center and enabled arm boxes (`block/FenceBlock.java:58–76` and `:59–77`). In 1.11.1 and 1.11.2 it resolves only when `forceShape` is false (`:59–80`); the movement collision caller passes `false` (`world/World.java:997`). The added parameter therefore does not alter this query-time movement result.

## Applicability boundary

The old material/full-cube fence predicate is proven in 1.10.2, 1.11, 1.11.1, and 1.11.2. This bounded comparison establishes no fence stair-connection behavior change within those sampled releases. It does not prove the exact release that later introduced a directional face-shape test; versions outside this assignment were not inspected here. The endpoint audit supplied as context reports that 1.12.2 uses directional face-shape predicates, but the exact introduction point between 1.11.2 and 1.12.2 remains unproven by this report.

This conclusion concerns only query-time virtual fence connectivity and collision geometry for identical world-state inputs. It makes no claim about placement, support, neighbor updates, or world-state production, and does not describe server-side historical physics.
