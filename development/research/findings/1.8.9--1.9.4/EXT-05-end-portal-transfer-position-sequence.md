# EXT-05: End portal transfer has an extra forced-spawn teleport in 1.8.9

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/server/PlayerManager.java`, `public void changeDimension(ServerPlayerEntity player, int dimension) {`, lines 435-458.
1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/server/PlayerManager.java`, `public void changeDimension(Entity entity, int dimension, ServerWorld fromWorld, ServerWorld toWorld) {`, lines 460-513.
1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/server/entity/living/player/ServerPlayerEntity.java`, `public void changeDimension(int dimension) {`, lines 440-464.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/server/PlayerManager.java`, `public void changeDimension(ServerPlayerEntity player, int dimension) {`, lines 489-514.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/server/PlayerManager.java`, `public void changeDimension(Entity entity, int dimension, ServerWorld fromWorld, ServerWorld toWorld) {`, lines 516-569.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/server/entity/living/player/ServerPlayerEntity.java`, `public Entity changeDimension(int dimension) {`, lines 458-488.

Difference: When 1.8.9's forced spawn point is non-null, 1.8.9 first teleports the player to that coordinate and sends the corresponding server-to-client position packet.

The subsequent `PlayerManager` transfer helper replaces that position with the destination world's End spawn point, and the manager sends its final teleport packet. 1.9.4 skips the early forced-spawn teleport and performs the shared destination-spawn assignment/final teleport, while also using its newer teleport acknowledgement state.

The source establishes an extra intermediate position write and correction packet in 1.8.9; it does not establish a client tick or player trajectory between packets.

The final destination position writer is the same.

Movement meaning: When 1.8.9's forced spawn point is non-null, 1.8.9 first teleports the player to that coordinate and sends the corresponding server-to-client position packet.

The subsequent `PlayerManager` transfer helper replaces that position with the destination world's End spawn point, and the manager sends its final teleport packet. 1.9.4 skips the early forced-spawn teleport and performs the shared destination-spawn assignment/final teleport, while also using its newer teleport acknowledgement state.

The source establishes an extra intermediate position write and correction packet in 1.8.9; it does not establish a client tick or player trajectory between packets.

The final destination position writer is the same.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements EXT-05: End portal transfer has an extra forced-spawn teleport in 1.8.9; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/server/PlayerManager.java",
      "member": "public void changeDimension(ServerPlayerEntity player, int dimension) {",
      "lines": "435-458",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 0498b44f26782daa8b82db0c0823cef615c6bd7e7234a43c32456e15647d9a5d; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 616271b240b2c906dc6a8d0c473ce9756cca4d7a6758e5d622cf1fc9e4c77fc5."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/server/PlayerManager.java",
      "member": "public void changeDimension(Entity entity, int dimension, ServerWorld fromWorld, ServerWorld toWorld) {",
      "lines": "460-513",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 0498b44f26782daa8b82db0c0823cef615c6bd7e7234a43c32456e15647d9a5d; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 081a17c5b124c6f7d7d3df39f98daa853f87eed8524a5f00fc612edf20f9f49b."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/server/entity/living/player/ServerPlayerEntity.java",
      "member": "public void changeDimension(int dimension) {",
      "lines": "440-464",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 047158bda6fa8fe2ea8a097972c098c5abe492ffbbe280006ae7592cd97fbb81; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 cf115ad6fe5b6f75e3926df5bed3dad3acc1ffe65fbeb477d8205069b247a5c9."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/server/PlayerManager.java",
      "member": "public void changeDimension(ServerPlayerEntity player, int dimension) {",
      "lines": "489-514",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4b77ddc263a0d9e17ffa359370ef56f9d1b59834ec53a21f29aeb8cc02a571c3; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 58cb50d866c0d85dbdbf9112295190ff859b45a4408cf81a17492e6dcb5ce6b1."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/server/PlayerManager.java",
      "member": "public void changeDimension(Entity entity, int dimension, ServerWorld fromWorld, ServerWorld toWorld) {",
      "lines": "516-569",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4b77ddc263a0d9e17ffa359370ef56f9d1b59834ec53a21f29aeb8cc02a571c3; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 081a17c5b124c6f7d7d3df39f98daa853f87eed8524a5f00fc612edf20f9f49b."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/server/entity/living/player/ServerPlayerEntity.java",
      "member": "public Entity changeDimension(int dimension) {",
      "lines": "458-488",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8e65663709c6a41f845ddb703b9ad96ecf05a67cc17bfadcda98328900cd67cd; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 7dd1c8214a02d7102580062d0dac6f568435c08559fbe4f75cda5bca3f572daf."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-EXT-05-end-portal-transfer-position-sequence is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-EXT-05-end-portal-transfer-position-sequence",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for EXT-05-end-portal-transfer-position-sequence",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
