# Independent correction audit — chunk unload/player overlap r2

## Verdict

**ACCEPT — bounded correction only.** The r2 report withdraws the unconditional world-swap/target statement and correctly makes replacement conditional on a respawn dimension differing from the current player dimension. It accurately retains the client task/world-tick ordering and local-player eligibility as unresolved. No pair freeze or movement finding is inferred.

## Bound inputs

- r2 report: `build/source-early-patches-20261117/1.12.1--1.12.2/chunk-unload-player-overlap-r2.md`, SHA-256 `20E166EA60E974DB1F76781C121EC8095688173DCC41C1943FA6BB4272CF9835` (independently recomputed; matches).
- Base report r1: `build/source-early-patches-20261117/1.12.1--1.12.2/chunk-unload-player-overlap.md`, SHA-256 `B1A4C209E0E9CFB9A985DF46ADFD86DEBF116BF84EB4BDC6A9005BEDFF36CAE0` (recomputed; matches).
- Correction request: `build/source-early-patches-20261117/1.12.1--1.12.2/chunk-unload-player-overlap-audit/verdict.md`, SHA-256 `41C5127C015D5668A0324F1839D460D2426DE20C01BE907EFC1A9618EEDFACF3` (recomputed; matches).

## Correction verification

- In both paired `ClientPlayNetworkHandler.handlePlayerRespawn()` methods, creation/assignment of a new `ClientWorld` is inside `packet.getDimension() != this.minecraft.player.dimension`. The same-dimension path skips that branch and still invokes `minecraft.respawnPlayer`; that method removes/recreates the local client player in the current world and does not replace `Minecraft.world`.
- The r2 report explicitly withdraws r1's unconditional statement that respawn swaps worlds and that forget handling necessarily targets a new world. For same-dimension respawn, it correctly says a forget handler before respawn handling unloads from the current world.
- The added changed-dimension server ordering is supported by exact 1.12.1 `PlayerManager.changeDimension()`: it sends `PlayerRespawnS2CPacket`, then calls `onChangedDimension`; that method removes membership from the previous world's `ChunkMap`, which can send old-watch forget packets. The client replacement branch is selected for that dimension mismatch. The report does not overstate how these sends interleave with client ticks/tasks.
- The retained ordinary tracking exclusion and open task-order/local-player eligibility gates match the prior accepted bounded audit. This correction audit does not reopen those source claims or the already-reviewed block-entity timing claim.

## Scope boundary

Source-only correction audit for exact 1.12.1→1.12.2 respawn/unload wording. Runtime reachability, local-player eligibility during a queued tick, movement effect, and exact-pair coverage/freeze remain unasserted.