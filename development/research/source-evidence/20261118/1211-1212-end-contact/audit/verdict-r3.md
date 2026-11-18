# Canonical finding adjudication r3

## Immutable evidence binding

- Current canonical finding `author/development/research/findings/1.21.1--1.21.2/keep-entity-data-respawn-velocity-copy.md` — SHA-256 `f86c0df5cd93665f4fd46db627e20fb9b8be617bf9f8b1a3d75e3dfd3b63cc80` (verified).
- Prior adjudication `audit/verdict-r2.md` — SHA-256 `ebe33825efc0ef046a250b2d030f781b88dc790f771080cf81e2b9b6419b1da0` (verified).
- r2 coverage `author/coverage-r2.md` — SHA-256 `31d3690a437974fd40b69b9551044f67c3aa60e0609fe3a65feb48edfaf994cd` (verified).
- Frozen blind audit `audit/blind.md` — SHA-256 `3e640d9eb09e7d26a74635ded60394186b6085ee2500f36ee88b909c84165cde` (verified).

## Predicate verdict: ACCEPT

The canonical finding is source-confirmed under its exact claim and bounded reachability. `ClientboundRespawnPacket` defines `KEEP_ENTITY_DATA` as byte `2`, and `shouldKeep` applies a bit test in both releases. The End-bound ServerPlayer transfer sends `dataToKeep=3` in both versions, so the same keep predicate is true for an ordinary Overworld-to-End End Portal transfer. This binds the same input byte and equivalent destination context; it does not claim byte-for-byte equality of the full version-specific respawn packet.

Given that input, 1.21.1 creates a replacement `LocalPlayer`, does not copy the old delta movement, and then calls `resetPos`, which sets delta to `Vec3.ZERO`. In 1.21.2 the keep-entity-data branch explicitly copies the old player's delta to its replacement, and `resetPos` is limited to the false branch. A nonzero old local-player delta therefore yields zero on the old replacement and remains nonzero on the new replacement immediately after `handleRespawn`.

The subsequent position correction does not erase the difference on the reachable End route. The old ServerPlayer path sends an absolute correction without relative arguments; the old client handler sets delta to zero. The new End Portal transition sends a zero delta operand with relative-delta flags; the new client resolves zero relative to its current movement, preserving the copied delta. The stable claim is limited to the state after this ordered respawn-plus-position-packet sequence and before subsequent simulation or later corrections. The relative-delta flags are a server-produced persistence dependency, not the cause attributed to the respawn-copy operation.

The finding accurately names a generic client `KEEP_ENTITY_DATA` replacement-player movement-state operation and supplies the ordinary End Portal route as a concrete witness. It does not overclaim identical complete packets or prove effects for other keep-data contexts. No broader route applicability is adjudicated here.

## Scope and authority boundary

This is an eligible direct local-player movement-state write in the client packet handler under the stated predicate. Server contact, End destination selection, and later relative-delta packet construction remain server-produced dependencies; this verdict does not authorize or imply historical server movement. The separate End Gateway same-tick entry-position result remains the server-selected destination dependency recorded in r2, and is not part of this finding.

Source pins and relevant file hashes are those bound in the immutable r2 coverage and earlier blind audit. This ACCEPT applies only to the exact finding SHA above; product applicability, implementation, intermediate patch boundary, complete pair coverage, and runtime parity are not decided here.