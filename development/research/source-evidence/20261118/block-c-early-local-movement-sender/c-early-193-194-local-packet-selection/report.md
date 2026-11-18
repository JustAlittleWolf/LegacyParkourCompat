# 1.9.3 → 1.9.4 local movement packet writer

## Result

No source difference found in the assigned normal local-player movement-packet selection and last-sent bookkeeping, or in the related player-position correction acknowledgement/response path. This is a bounded source comparison, not complete movement-pair coverage and not a runtime-parity result.

## Exact source identity

Both sides are the prepared `ornithe-feather` decompilation.

- **1.9.3:** `decompiled_minecraft/1.9.3/ornithe-feather/.decompile-provenance.json` records Feather `1.9.3+build.2`, source metadata `1.9.3`, raw client SHA-256 `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b`, and the mapping artifact SHA-256 `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`. The provenance manifest SHA-256 is `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`.
- **1.9.4:** `decompiled_minecraft/1.9.4/ornithe-feather.provenance.json` records exact version `1.9.4`, namespace `ornithe-feather`, and Feather `1.9.4+build.2` artifacts. `ornithe-feather.ready.json` identifies the prepared source manifest, source count (1,819), raw client SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`, and source-manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; that is also the current SHA-256 of `ornithe-feather.sources.sha256`. The 1.9.4 Feather mapped jar and Tiny mapping artifact hashes recorded in `artifacts.sha256` are `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125` and `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`.
- The 1.9.4 `ornithe-feather.verification.json` records `verified-identical-source-and-input-artifacts`: all 1,819 published source files verified with zero source differences and no missing sources; the only artifact difference was a regenerated derived remap intermediate. The current `LocalClientPlayerEntity.java` SHA-256 matches its 1.9.3 provenance entry and the 1.9.4 source-manifest entry.

## Compared operation

`net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`:

- `tick()` lines 143–157 routes loaded-chunk, non-riding local ticks to `sendMovementToServer()`; its riding branch sends angles and input and conditionally sends the vehicle packet.
- `sendMovementToServer()` lines 159–220 includes sprint/sneak transitions; camera gating; position deltas using `x`, `getShape().minY`, and `z`; angle deltas; the `9.0E-4` squared-distance threshold or 20-tick position resend; riding sentinel packet; packet-form selection; and conditional sent-position/tick and sent-angle updates plus the on-ground update.
- Both releases have identical full-file SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`. The source bodies and operation order above are identical; there is no changed packet form, predicate, threshold, or last-sent-state write in this class.

For the allowed teleport bookkeeping check, `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` is also byte-identical on both sides (SHA-256 `5a2fc039b81ec0e77df3354826aaaf00dfa6e0d1296cf111864900932e811a4c`). In `handlePlayerMove(PlayerMoveS2CPacket)` lines 546–595, the same correction application is followed by `AcceptTeleportC2SPacket` and the same immediate absolute `PlayerMoveC2SPacket.PositionAndAngles` response. This path shows no changed writer authority or acknowledgement behavior in the compared releases.

## Boundary

Only the normal local-player tick packet writer and the adjacent teleport acknowledgement/response path were examined. Other player movement operations and exact introduction points outside this adjacent pair remain unreviewed.
