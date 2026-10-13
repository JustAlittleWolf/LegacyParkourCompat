# EXT-01: local player movement packet selection and correction acknowledgment differ

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void sendMovementToServer() {`, lines 117-178.
1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`, `public void handlePlayerMove(PlayerMoveS2CPacket packet) {`, lines 517-565.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void sendMovementToServer() {`, lines 159-220.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`, `public void handlePlayerMove(PlayerMoveS2CPacket packet) {`, lines 546-595.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`, `public void handleAcceptTeleport(AcceptTeleportC2SPacket packet) {`, lines 348-364.

Difference: With the position counter reset to zero and no position delta above the `9.0E-4` squared-distance threshold, 1.8.9 checks the current count and then increments it, so the forced resend branch becomes true on the 21st subsequent call. 1.9.4 increments first, so it becomes true on the 20th.

On a camera tick with no position or angle report and no on-ground transition, 1.8.9 emits a ground-only movement packet; 1.9.4 emits none. 1.9.4 records its sent ground state after selecting the packet.

These claims describe client packet selection only.

For a received correction, both client handlers preserve velocity components for relative axes and set absolute-axis velocity to zero before applying resolved position and rotation.

The server release gate differs: 1.8.9 accepts a positional response within squared distance `0.25` of the pending target; 1.9.4 accepts a matching teleport ID and retains a pending target until that acknowledgment.

Both server handlers then have source paths that move/update the player and ground state.

These are code-path observations, not a simulated packet exchange or trajectory.

Movement meaning: `LocalClientPlayerEntity.tick()` reaches its packet sender after `super.tick()` only while its current chunk is loaded.

The sender is gated by `isCamera()`. 1.8.9 riding tick sends player angles and input; 1.9.4 additionally emits a vehicle movement packet when the vehicle is locally authoritative.

Vehicle movement is excluded from this finding.

The paired sources prove different outbound packet timing/content and different server-side pending-teleport release gates.

Both server handlers assign the client-reported ground state and continue into player movement/collision handling after their respective gates.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements EXT-01: local player movement packet selection and correction acknowledgment differ; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java",
      "member": "public void sendMovementToServer() {",
      "lines": "117-178",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053; not bound to a source-file digest in this record. Checked full declaration/body; snippet SHA-256 b5590b92cc4eac0ff98e6824e527c108dc453fd75ad6f8bca907953ce539b4f4."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/client/network/handler/ClientPlayNetworkHandler.java",
      "member": "public void handlePlayerMove(PlayerMoveS2CPacket packet) {",
      "lines": "517-565",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 fb3b190c12d94a37342f6cac4315ba67da461b7f3db020be8e033232398d808a."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/server/network/handler/ServerPlayNetworkHandler.java",
      "member": "ServerPlayNetworkHandler#handleAcceptTeleport",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 47ef077a7fa0f74cf44e1c8bda449d1b220bf56503b2fef98fd8016e8092df5b (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java",
      "member": "public void sendMovementToServer() {",
      "lines": "159-220",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 7b8e21ba5fa7eefc1275f95bec155dfe92e4ba381d279c00ab44368337533b3b."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/client/network/handler/ClientPlayNetworkHandler.java",
      "member": "public void handlePlayerMove(PlayerMoveS2CPacket packet) {",
      "lines": "546-595",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 5a2fc039b81ec0e77df3354826aaaf00dfa6e0d1296cf111864900932e811a4c; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 0e16a3ed88bee0def6a878e4b41f0e80ceba4957c8ac26ab10408bcf8b9cbbec."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/server/network/handler/ServerPlayNetworkHandler.java",
      "member": "public void handleAcceptTeleport(AcceptTeleportC2SPacket packet) {",
      "lines": "348-364",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f96587355f356f95362dbca2c57c043fdb3ee8ee2fe81589f4c3379ab1a0336b; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 7c8c55a798d4e19bd6d9aa52a20cc047d75341e994bb23e1bc302d5dccfdc3d6."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-EXT-01-player-authority-packet-differences is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-EXT-01-player-authority-packet-differences",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for EXT-01-player-authority-packet-differences",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
