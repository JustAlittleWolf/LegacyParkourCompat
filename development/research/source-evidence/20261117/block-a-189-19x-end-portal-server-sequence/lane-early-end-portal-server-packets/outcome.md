# Bounded source comparison outcome

Pair: Minecraft 1.8.9 → 1.9.4; Ornithe Feather on both sides. Source roster was frozen before body inspection; see `roster-freeze.md` and its handler/client-reset supplements. The ready markers identify exact version IDs, Feather artifacts and source-manifest digests. For each side, the local verification marker reports `verified-identical-source-and-input-artifacts` with zero source-file differences; the source-manifest digests and the reviewed-file hashes are recorded in the roster.

## Operation outcomes

1. End portal server transfer: **bounded source difference confirmed**. In 1.8.9 a non-null End forced spawn point causes a pre-respawn absolute teleport; 1.9.4 has no such branch. Both paths send the dimension respawn before writing the destination End spawn coordinates and send a final absolute move packet after the destination position write. See `operation-end-destination-order.md`.

2. Client packet application: **bounded source difference confirmed**. Respawn/reset and final move application are the same relevant sequence, apart from 1.8.9's optional pre-respawn correction. The 1.9.4 move packet adds a teleport ID that the client acknowledges after applying position. Both client receivers zero velocity components for absolute move axes; no velocity packet is constructed by this transfer sequence. See `operation-end-packet-client-application.md`.

## Boundaries

The 1.8.9 preliminary correction depends on the server-provided End forced spawn point being non-null. The source comparison does not establish runtime packet arrival order or a concrete server's forced-spawn input. The entry precondition is a server-side ordinary eligible player path; the report does not cover portal shape/geometry behavior, End exit/return, Nether transfer, other placement rules, or runtime parity. No tests, builds, game launches, network lookups, Git reads, or source writes were performed.
