# Independent audit: 1.9.3 → 1.9.4 local packet selection

## Verdict

**Accepted for the bounded claim:** the normal local-player movement packet selection and sent-state bookkeeping, plus the adjacent local player correction acknowledgement/response path, have no source difference between these exact prepared releases. This does not establish full movement-pair coverage or runtime parity.

## Inputs and source identity

- Audited draft: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-early-193-194-local-packet-selection/report.md`, SHA-256 `951a53f6cc51bb1444515d29aecfcc29658272701ee5ef232215e32dc056617a`.
- 1.9.3 canonical source: `decompiled_minecraft/1.9.3/ornithe-feather/`, `.decompile-provenance.json` SHA-256 `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`. Provenance pins Minecraft 1.9.3 client SHA-256 `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b`, Feather `1.9.3+build.2` merged mapping SHA-256 `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`, and the audited source hashes below.
- 1.9.4 canonical source: `decompiled_minecraft/1.9.4/ornithe-feather/`; provenance SHA-256 `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd`, ready marker SHA-256 `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6`, and source manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. These identify exact version 1.9.4 and Feather `1.9.4+build.2`; the verification record reports 1,819 source files checked, zero differences and zero missing files (record SHA-256 `9ef993b06552037873604f912cce078845241b6e6f3b9799a83795abd1adf688`).

## Independent trace

`LocalClientPlayerEntity.java` is byte-identical on both sides (SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`; 1.9.3 provenance entry and 1.9.4 source manifest entry agree). In `tick()` lines 143–157, a loaded-chunk local tick uses the riding packet branch or calls `sendMovementToServer()`. In that writer (lines 159–220), the camera gate encloses the ordinary writer. Position delta is measured from `x`, `getShape().minY`, and `z`; the position predicate is squared displacement `> 9.0E-4` or `ticksSinceSentPosition >= 20`; angle changes use exact inequality. The ordinary selection order is combined position+angles, position, angles, then on-ground-only. Riding emits its sentinel position/angles packet and clears the position-send predicate. Sent position and counter reset update only when the position predicate remains true; sent angles update only when angle changes; sent-on-ground updates inside the camera gate. The compared full source hashes establish identical statements and order, including these gates and writes.

`ClientPlayNetworkHandler.java` is also byte-identical on both sides (SHA-256 `5a2fc039b81ec0e77df3354826aaaf00dfa6e0d1296cf111864900932e811a4c`; provenance and source manifest entries agree). `handlePlayerMove(PlayerMoveS2CPacket)` lines 546–595 applies the correction using its relative-argument flags, updates the local player position/angles, sends `AcceptTeleportC2SPacket`, then immediately sends an absolute `PlayerMoveC2SPacket.PositionAndAngles` response using local `x`, shape `minY`, `z`, yaw and pitch, with on-ground false. The local acknowledgement/response sequence and authority are unchanged across the pair.

## Scope boundary

This verdict covers only the normal local-player movement-packet writer and this adjacent player-correction acknowledgement/response handler. Other movement slices and intermediate release introduction points were not audited. No product code, decompilation, network activity, tests or runtime was used.