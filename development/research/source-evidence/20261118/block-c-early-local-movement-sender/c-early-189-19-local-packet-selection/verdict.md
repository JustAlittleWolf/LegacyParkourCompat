# Independent source audit: 1.8.9 → initial 1.9 local packet selection

## Verdict

**Accepted for the bounded claims below.** This verdict is bound to the frozen report SHA-256 `6e2777ad4a49b1a43334d3bebfab060195b57c5364f660e4f1680d881c7ae649` and the four exact source-file digests recorded below. The report is not a full-pair coverage audit.

Frozen claim: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-early-189-19-local-packet-selection/report.md`.

## Evidence identity checks

- 1.8.9 source root: `decompiled_minecraft/1.8.9/ornithe-feather/`. Its adjacent provenance JSON SHA-256 is `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`; it declares `versionId: 1.8.9`, `namespace: ornithe-feather`, and Feather Gen2 1.8.9 build.2 (`feather-gen2-1.8.9+build.2-mergedv2.jar` and `.tiny`). The source-hash sidecar SHA-256 is `9e75f46c0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; its entries for both relevant source files match their actual bytes.
- Initial 1.9 source root: `decompiled_minecraft/1.9/ornithe-feather/`. Its `.decompile-provenance.json` SHA-256 is `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`. Its pinned inputs identify the 1.9 client artifact (SHA-256 `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307`), Feather Gen2 1.9 build.2 mapping artifact (SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`), and 1.9 Feather metadata (SHA-256 `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371`). Both source-file entries in its embedded `sources` map match their actual bytes.
- Actual source-file SHA-256 values, each independently recomputed and matched to the listed source manifest:

| Release | Source file | SHA-256 |
|---|---|---|
| 1.8.9 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `1762b116e6b06d682b7daa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` |
| 1.8.9 | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` |
| 1.9 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06` |
| 1.9 | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6` |

The frozen report SHA-256 was independently recomputed as stated above. Exact report and source-file hashes bind this audit; the provenance files are evidence of the local source publication and pinned inputs, not independent publisher authentication.

## Independent trace

### Ordinary local tick and packet selection

- 1.8.9 `LocalClientPlayerEntity.tick()` checks the loaded-chunk condition and calls `sendMovementToServer()` on the non-riding path (`LocalClientPlayerEntity.java:105–114`). The sender's `isCamera()` guard encloses the selected movement packet and its counter/baseline writes (`:140–177`). It computes the position threshold from position deltas or `ticksSinceSentPosition >= 20` before incrementing the counter (`:141–147`, `:165–171`). If neither positional nor rotational branch matches, it unconditionally constructs `PlayerMoveC2SPacket(this.onGround)` (`:148–158`).
- Initial 1.9 has the corresponding loaded-chunk/non-riding call path (`LocalClientPlayerEntity.java:141–154`) and the same `isCamera()` outer guard (`:180`). It computes the same position-delta comparison, increments the counter, then checks `>= 20` (`:182–189`). Therefore, with the same sender path and no earlier positional send, old 1.8.9 tests counter value 20 on its 21st call after a reset, whereas initial 1.9 reaches 20 and tests it on call 20. The report's one-call timing conclusion is source-supported and conditional on both calls reaching this guarded path.
- In initial 1.9 the ground-only construction is the final `else if` and is conditioned on `sentOnGround != onGround` (`:194–202`). A positional or rotation packet takes precedence and already carries `onGround`; `sentOnGround` is updated after packet selection on every guarded sender call (`:216`). Thus the report's distinction is sound: separate ground-only packets cease being unconditional and only occur on a ground-state transition when no earlier position/rotation branch is selected.
- In both versions, `sentX/Y/Z` and `ticksSinceSentPosition` are updated/reset only under `bl3` (`1.8.9 :165–171`; 1.9 `:204–209`); `sentYaw/Pitch` are updated only under `bl4` (`1.8.9 :173–176`; 1.9 `:211–214`). Initial 1.9 additionally stores `sentOnGround` every guarded call. Field declarations show these sender baselines are instance fields (`1.8.9 :56–63`; 1.9 `:71–79`); no alternate writes appear in the inspected local-player or correction-handler files.

### Normal player-position correction

- 1.8.9 `ClientPlayNetworkHandler.handlePlayerMove()` applies the correction and immediately sends a position-and-angles packet with ground flag `false` (`ClientPlayNetworkHandler.java:517–557`); it sends no teleport acknowledgement in this method. Its initial-start `lastX/Y/Z` initialization occurs afterward (`:558–564`).
- Initial 1.9 applies the correction, sends `AcceptTeleportC2SPacket(packet.getTeleportId())`, then sends the immediate position-and-angles packet with ground flag `false` (`ClientPlayNetworkHandler.java:545–586`). The initial-start `lastX/Y/Z` writes remain after those writes (`:587–593`).
- Neither inspected correction handler writes the periodic sender's `ticksSinceSentPosition`, `sentX/Y/Z`, `sentYaw/Pitch`, or `sentOnGround`. The report correctly separates the new immediate acknowledgement from the ordinary sender's periodic counter and position/rotation baseline gates. Correction math and actual network timing were not adjudicated.

## Bounds

This accepts only the frozen report's bounded local packet-selection and normal correction-handler claims for exact 1.8.9 versus initial 1.9 Feather sources. It does not establish complete movement-tick inventory, full-pair freeze, intermediate patch cutover, runtime/network parity, server physics, or implementation correctness.
