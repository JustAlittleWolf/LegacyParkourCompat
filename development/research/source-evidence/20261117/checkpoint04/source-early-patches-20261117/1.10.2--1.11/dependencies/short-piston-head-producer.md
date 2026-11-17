# Piston-head `SHORT` state producer — 1.10.2 → 1.11

**Disposition:** The unresolved producer/caller gate is source-closed for this bounded slice. This does not accept a piston trajectory, final clipped movement, or the exact pair; pair coverage remains partial.

## Exact source identity

Source namespace: `decompiled_minecraft/<release>/ornithe-feather/` (Ornithe/Feather).

| Release | Ready marker SHA-256 | Source manifest SHA-256 | Manifest rows / verified files |
| --- | --- | --- | ---: |
| 1.10.2 | `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | 1,845 / 1,845 |
| 1.11 | `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | 1,918 / 1,918 |

The exact relevant manifest rows are:

| Source file | 1.10.2 SHA-256 | 1.11 SHA-256 |
| --- | --- | --- |
| `net/minecraft/block/PistonHeadBlock.java` | `9e8bd56219d3a00e57082481d6e17a2c9383e994dac6cecac2534918f4aeb579` | `efe5e85a67947fcfdcbc131fe247d5b263a1e23a4b6b46d58fafee9a8374a34f` |
| `net/minecraft/block/PistonBaseBlock.java` | `4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36` | `9b08e98e0f194077e6d77168a7ce1a93503f4ace31429cbf42010083a89e19ea` |
| `net/minecraft/block/MovingBlock.java` | `4f84cdf7c0d88c51ca065299c8755341d01b126d62c282c31fc0fa46bde1daf6` | `35799c009f0e01655622c1939e6b35342789c5abd19016c5a09528b3fac9e620` |
| `net/minecraft/block/entity/MovingBlockEntity.java` | `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1` | `0185504476d365cf05c91365e692497f618a1e536fc311d09211b7caefe8aa7a` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `af3efba0a1d5a0824c29c4af4ac7f8e4484d1787a82208e4d72bcc65f957e656` | `cef5d643eca0b32119571eff5201705cc8c99125f8266f763172202d89e3a40b` |
| `net/minecraft/world/World.java` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` |
| `net/minecraft/block/state/BlockState.java` | `3fb6f06df86c3b89716b5efaed5d97eee670d007d3c4535e47eb0fdd993e92eb` | `3fb6f06df86c3b89716b5efaed5d97eee670d007d3c4535e47eb0fdd993e92eb` |

Each listed file hash equals its release's `ornithe-feather.sources.sha256` row. Pair-level marker/manifest identities and complete row verification are also recorded in the bounded provider inventory at `build/source-early-patches-20261117/1.10.2--1.11/dependencies/provider-inventory.md`.

## State production and selection

- Both `PistonHeadBlock` versions declare `SHORT`, include it in their state definition, and initialize its default to `false`. `getStateFromMetadata` reconstructs facing/type from legacy metadata and leaves `SHORT` at that default. In 1.10.2, `getArmShape` ignores `SHORT`; in 1.11 it selects a direction-specific short-arm box when true. Thus ordinary stored/metadata-decoded piston-head states do not make the short state true by themselves.
- In both versions, `PistonBaseBlock.move` constructs the extension head's `movedState` from `PISTON_HEAD.defaultState()` plus facing/type. On the extension branch, 1.10.2 calls `createMovingBlockEntity(..., facing, true, false)`; 1.11 calls it with `(..., facing, true, true)`. For retraction, `PistonBaseBlock.doEvent` creates the source moving entity at the piston base with `extending=false, source=true` in both versions.
- `MovingBlock.createMovingBlockEntity` constructs `MovingBlockEntity(movedState, facing, extending, source)`. Its block provider obtains the block entity from the queried world position and delegates `addCollisions` to it when it is a `MovingBlockEntity` (1.11 `MovingBlock.java`, `getMovingBlockEntity` / `addCollisions`).
- The 1.11 `MovingBlockEntity.addCollisions` source branch synthesizes a fresh `PISTON_HEAD.defaultState()`, sets its facing, and sets `SHORT` to `extending != 1.0F - progress < 0.25F`; it then calls that state's `addCollisions`, which reaches `PistonHeadBlock.getArmShape`. The temporary state is passed directly to the provider and is not written to `World` or retained in the entity. The 1.10.2 `MovingBlockEntity` has no `addCollisions` method and no `SHORT` writer; its moved head remains the default-false state for this path.
- `MovingBlockEntity.writeNbt/readNbt` persist moved block ID/metadata, facing, progress and extending. They do not persist `source` or a `SHORT` value. The legacy metadata methods for `PistonHeadBlock` also omit `SHORT`; `getStateFromMetadata` therefore uses false. This transient collision state is recalculated by the 1.11 collision callback, not transported as a persistent block-state property.

## Client player-query reachability

The source supports a static local-client route to the 1.11 producer:

1. `ClientPlayNetworkHandler.handleBlockEvent` forwards a received piston block-event packet to `World.addBlockEvent`.
2. `World.addBlockEvent` dispatches through the current state's `doEvent`. `PistonBaseBlock.doEvent` runs the piston event body on the client as well as the server; for the relevant extend/retract event it calls `move` or builds the retracting source entity. The extension move installs `MOVING_BLOCK` and its `MovingBlockEntity` with `source=true` in 1.11. No natural-world or runtime observation is needed for this static caller path.
3. The accepted provider inventory traces `Entity.move` through `World.getCollisions(entity, box)` to `BlockState.addCollisions`, which passes the entity to `MovingBlock.addCollisions`; that resolves the block entity at the collision position and calls `MovingBlockEntity.addCollisions`. A local player's entity-aware collision query can therefore receive the synthesized SHORT head state through client world state, conditional on the piston event having produced a source moving-block entity at a position included by that query.

This closes only the SHORT producer's source caller boundary. The exact runtime timing/trajectory, overlap/clipping result, and remaining pair tick/provider dependencies are outside this artifact.
