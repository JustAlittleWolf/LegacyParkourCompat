# 1.12.1 → 1.12.2 chunk-unload player-overlap correction r2

## Bound inputs

- Base bounded report: `build/source-early-patches-20261117/1.12.1--1.12.2/chunk-unload-player-overlap.md`, SHA-256 `B1A4C209E0E9CFB9A985DF46ADFD86DEBF116BF84EB4BDC6A9005BEDFF36CAE0`.
- RC verdict binding that accepted the base report's ordinary-unload exclusion and bounded respawn-order observation: SHA-256 `41C5127C015D5668A0324F1839D460D2426DE20C01BE907EFC1A9618EEDFACF3`.
- Exact endpoint source identities for the relevant handler files are bound in the base report; the inspected 1.12.1 `ClientPlayNetworkHandler.java` is SHA-256 `15E5E391DF2E799CE04CAC91AF6FEB7B9DC866A8963CFB03BC1BD04F375B986F`, and 1.12.2 is `FCE21D9902E555FD46545FB04BDB6C4A912EEF6398790776AD1A122E09960C7C`.

## Dimension-branch correction

This correction narrows only the client world-swap statement in r1. In both endpoint implementations, `ClientPlayNetworkHandler.handlePlayerRespawn()` replaces `this.world` only inside the condition `packet.getDimension() != this.minecraft.player.dimension` (1.12.1 `ClientPlayNetworkHandler.java:905-925`; the same method was inspected in 1.12.2). If that condition is false, the handler skips world construction/assignment but still calls `minecraft.respawnPlayer(packet.getDimension())`.

Accordingly, r1's sentence “The client swaps worlds when it handles the respawn packet, so later forget packets apply to the new world” is withdrawn as unconditional. A same-dimension respawn does not take that branch; the source does not support claiming the forget packets necessarily target a new `ClientWorld`. In the same-dimension death-respawn path, `PlayerManager.respawn()` removes the player from the old `ChunkMap` before sending `PlayerRespawnS2CPacket`, so its forget packets are sent before that response packet. If a client task/world tick interleaves after a forget packet and before respawn handling, that chunk would still belong to the current world. The source task queue does not establish whether that interleaving occurs, and the player's eligible state at that moment also remains unproven.

For the distinct dimension-changing path, `PlayerManager.changeDimension()` sends `PlayerRespawnS2CPacket` before `onChangedDimension()` removes the player from the old `ChunkMap` (1.12.1 `PlayerManager.java:487-505`). The client takes the replacement branch when the packet dimension differs from the current player's dimension. This orders the source-side send calls but does not establish that every resulting forget task must execute after the client world assignment relative to a world tick; retain the already-open exact task-drain/order gate without asserting a target world categorically.

## Retained and open scope

The base report's ordinary movement/view-distance exclusion is unchanged: the watched radius is clamped to at least three chunks and those unloads cannot overlap the local player. The exact respawn lifecycle remains a conditional source observation, not a movement finding. Its only unresolved question here is whether a forget task for an overlapping populated chunk can be followed by an eligible client world tick before the applicable respawn task completes (including the same-dimension path, where no `ClientWorld` replacement occurs). No pair freeze or runtime conclusion is asserted.
